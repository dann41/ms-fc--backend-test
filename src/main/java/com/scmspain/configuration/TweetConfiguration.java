package com.scmspain.configuration;

import com.scmspain.controller.TweetController;
import com.scmspain.repository.TweetRepository;
import com.scmspain.builders.TweetBuilder;
import com.scmspain.builders.TweetWithLinksBuilder;
import com.scmspain.services.TweetService;
import com.scmspain.services.TweetServiceImpl;
import org.springframework.boot.actuate.metrics.writer.MetricWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TweetConfiguration {
    @Bean
    public TweetService getTweetService(TweetRepository tweetRepository, MetricWriter metricWriter) {
        return new TweetServiceImpl(getTweetBuilder(), tweetRepository, metricWriter);
    }

    @Bean
    public TweetBuilder getTweetBuilder() {
        return new TweetWithLinksBuilder();
    }

    @Bean
    public TweetController getTweetController(TweetService tweetService) {
        return new TweetController(tweetService);
    }
}
