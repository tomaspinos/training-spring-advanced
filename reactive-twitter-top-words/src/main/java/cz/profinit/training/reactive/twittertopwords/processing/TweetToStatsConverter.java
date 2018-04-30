package cz.profinit.training.reactive.twittertopwords.processing;

import cz.profinit.training.reactive.twittertopwords.model.TweetStats;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.reducing;

@Component
public class TweetToStatsConverter {

    private final AtomicInteger counter = new AtomicInteger(1);

    private static final Logger log = LoggerFactory.getLogger(TweetToStatsConverter.class);

    public TweetStats toStats(Tweet tweet) {
        log.trace("Processing tweet number {}. Id: {}", counter.getAndIncrement(), tweet.getId());

        List<String> words = words(tweet.getText());

        Map<String, Integer> wordCounts = countWords(words);

        return new TweetStats(wordCounts);
    }

    List<String> words(String text) {
        String[] words = text.split("\\s+");
        for (int i = 0; i < words.length; i++) {
            words[i] = words[i].replaceAll("[^\\w]", "");
        }
        return Arrays.asList(words);
    }

    Map<String, Integer> countWords(List<String> words) {
        return words.stream().map(String::toLowerCase).collect(groupingBy(word -> word, reducing(0, e -> 1, Integer::sum)));
    }
}
