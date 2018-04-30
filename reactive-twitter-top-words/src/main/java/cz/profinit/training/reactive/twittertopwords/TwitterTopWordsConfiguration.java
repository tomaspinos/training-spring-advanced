package cz.profinit.training.reactive.twittertopwords;

import cz.profinit.training.reactive.twittertopwords.writer.SimpleTweetWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.social.twitter.api.impl.TwitterTemplate;

@Configuration
public class TwitterTopWordsConfiguration {

    @Bean
    @Profile("simpleTweetWriter")
    public SimpleTweetWriter simpleTweetWriter(TwitterTemplate twitterTemplate) {
        return new SimpleTweetWriter(twitterTemplate);
    }
}
