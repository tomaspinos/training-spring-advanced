package cz.profinit.training.reactive.twittertopwords.streaming;

import cz.profinit.training.reactive.twittertopwords.TwitterTopWordsProperties;
import cz.profinit.training.reactive.twittertopwords.model.TopWords;
import cz.profinit.training.reactive.twittertopwords.model.TweetStats;
import cz.profinit.training.reactive.twittertopwords.processing.TweetToStatsConverter;
import cz.profinit.training.reactive.twittertopwords.processing.WordCountAggregator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.TopicProcessor;

@Component
public class TopWordsFluxFactory {

    private static final Logger log = LoggerFactory.getLogger(TopWordsFluxFactory.class);
    private final TwitterListener twitterListener;
    private final TweetToStatsConverter tweetToStats;
    private final WordCountAggregator wordCountAggregator;
    private final TwitterTopWordsProperties properties;

    public TopWordsFluxFactory(TwitterListener twitterListener,
                               TweetToStatsConverter tweetToStats,
                               WordCountAggregator wordCountAggregator,
                               TwitterTopWordsProperties properties) {

        this.twitterListener = twitterListener;
        this.tweetToStats = tweetToStats;
        this.wordCountAggregator = wordCountAggregator;
        this.properties = properties;
    }

    public Flux<TopWords> createTopWordsFlux() {
        log.info("Creating top word stream");

        TopicProcessor<Tweet> processor = TopicProcessor.<Tweet>builder()
                .share(true)
                .autoCancel(false)
                .build();

        Flux.create(twitterListener::start)
                .log("test.tweets")
                .subscribe(processor);

        return Flux.just(wordCountAggregator)
                .repeat()
                .zipWith(processor.map(tweetToStats::toStats)
                                .window(properties.getTweetStatsCountToTriggerTopWordsUpdate())
                                .flatMap(flux -> flux.reduce(TweetStats.EMPTY, TweetStats::merge)),
                        WordCountAggregator::aggregateTweetStats)
                .log("test.wordCounts");
    }
}
