package com.scmspain.services;

import com.scmspain.entities.Tweet;
import com.scmspain.repository.TweetRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.actuate.metrics.writer.MetricWriter;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class TweetServiceImplTest {
    private TweetRepository repository;
    private MetricWriter metricWriter;
    private TweetService tweetService;

    @Before
    public void setUp() throws Exception {
        this.repository = mock(TweetRepository.class);
        this.metricWriter = mock(MetricWriter.class);
        this.tweetService = new TweetServiceImpl(repository, metricWriter);
    }

    @Test
    public void shouldInsertANewTweet() throws Exception {
        tweetService.publishTweet("Guybrush Threepwood", "I am Guybrush Threepwood, mighty pirate.");

        verify(repository).save(any(Tweet.class));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowAnExceptionWhenTweetLengthIsInvalid() throws Exception {
        tweetService.publishTweet("Pirate", "LeChuck? He's the guy that went to the Governor's for dinner and never wanted to leave. He fell for her in a big way, but she told him to drop dead. So he did. Then things really got ugly.");
    }
}
