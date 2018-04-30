package cz.profinit.training.reactive.twittertopwords;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class TwitterTopWordsProperties {

    private final int minWordLength;
    private final int tweetStatsCountToTriggerTopWordsUpdate;
    private final int topWordCountToKeep;
    private final int topWordCountToTriggerPruning;

    private static final Logger log = LoggerFactory.getLogger(TwitterTopWordsProperties.class);

    public TwitterTopWordsProperties(
            @Value("${twitter.bubbles.minWordLength}") int minWordLength,
            @Value("${twitter.bubbles.tweetStatsCountToTriggerTopWordsUpdate}") int tweetStatsCountToTriggerTopWordsUpdate,
            @Value("${twitter.bubbles.topWordCountToKeep}") int topWordCountToKeep,
            @Value("${twitter.bubbles.topWordCountToTriggerPruning}") int topWordCountToTriggerPruning) {

        this.minWordLength = minWordLength;
        this.tweetStatsCountToTriggerTopWordsUpdate = tweetStatsCountToTriggerTopWordsUpdate;
        this.topWordCountToKeep = topWordCountToKeep;
        this.topWordCountToTriggerPruning = topWordCountToTriggerPruning;
    }

    public int getMinWordLength() {
        return minWordLength;
    }

    public int getTweetStatsCountToTriggerTopWordsUpdate() {
        return tweetStatsCountToTriggerTopWordsUpdate;
    }

    public int getTopWordCountToKeep() {
        return topWordCountToKeep;
    }

    public int getTopWordCountToTriggerPruning() {
        return topWordCountToTriggerPruning;
    }

    @PostConstruct
    public void log() {
        log.info("{}", toString());
    }

    @Override
    public String toString() {
        return "TwitterBubblesProperties{" +
                ", minWordLength=" + minWordLength +
                ", tweetStatsCountToTriggerTopWordsUpdate=" + tweetStatsCountToTriggerTopWordsUpdate +
                ", topWordCountToKeep=" + topWordCountToKeep +
                ", topWordCountToTriggerPruning=" + topWordCountToTriggerPruning +
                '}';
    }
}
