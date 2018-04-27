package cz.profinit.training.reactive.twittertopwords.processing;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class TweetStats {

    public static final TweetStats EMPTY = new TweetStats(Collections.emptyMap());

    private Map<String, Integer> wordCounts;

    public TweetStats() {
    }

    public TweetStats(Map<String, Integer> wordCounts) {
        this.wordCounts = wordCounts;
    }

    public Map<String, Integer> getWordCounts() {
        return wordCounts;
    }

    public TweetStats merge(TweetStats tweetStats) {
        HashMap<String, Integer> mergedWordCounts = new HashMap<>(wordCounts);
        tweetStats.wordCounts.forEach((word, count) ->
                mergedWordCounts.merge(word, count, (count1, count2) -> count1 + count2));
        return new TweetStats(mergedWordCounts);
    }

    @Override
    public String toString() {
        return "TweetStats{" +
                "wordCounts=" + wordCounts +
                '}';
    }
}
