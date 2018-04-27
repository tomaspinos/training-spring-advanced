package cz.profinit.training.reactive.twittertopwords.model;

import java.util.Map;

public class TopWords {

    private Map<String, Integer> topWords;

    TopWords() {
    }

    public TopWords(Map<String, Integer> topWords) {
        this.topWords = topWords;
    }

    public Map<String, Integer> getTopWords() {
        return topWords;
    }

    @Override
    public String toString() {
        return "TopWords{" +
                "topWords=" + topWords +
                '}';
    }
}
