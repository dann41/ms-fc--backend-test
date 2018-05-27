package com.scmspain.services;

import com.scmspain.builders.TweetBuilder;
import com.scmspain.entities.Tweet;
import com.scmspain.repository.TweetRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.actuate.metrics.writer.MetricWriter;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TweetServiceImplTest {

    private TweetBuilder builder;
    private TweetRepository repository;
    private MetricWriter metricWriter;
    private TweetService tweetService;

    @Before
    public void setUp() throws Exception {
        builder = mock(TweetBuilder.class);
        repository = mock(TweetRepository.class);
        metricWriter = mock(MetricWriter.class);
        tweetService = new TweetServiceImpl(builder, repository, metricWriter);
    }

    @Test
    public void shouldInsertANewTweet() throws Exception {
        // given
        String publisher = "Guybrush Threepwood";
        String text = "I am Guybrush Threepwood, mighty pirate.";
        when(builder.buildTweet(anyString(), anyString())).thenReturn(getTweet(publisher, text));

        // when
        tweetService.publishTweet(publisher, text);

        // then
        verify(repository).save(any(Tweet.class));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowAnExceptionWhenTweetLengthIsInvalid() throws Exception {
        // given
        String publisher = "Pirate";
        String text = "LeChuck? He's the guy that went to the Governor's for dinner and never wanted to leave. He fell for her in a big way, but she told him to drop dead. So he did. Then things really got ugly.";
        when(builder.buildTweet(anyString(), anyString())).thenReturn(getTweet(publisher, text));

        // when
        tweetService.publishTweet(publisher, text);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowAnExceptionWhenPublisherIsEmpty() throws Exception {
        // given
        String publisher = "";
        String text = "LeChuck? He's the guy that went to the Governor's for dinner and never wanted to leave. He fell for her in a big way, but she told him to drop dead. So he did. Then things really got ugly.";
        when(builder.buildTweet(anyString(), anyString())).thenReturn(getTweet(publisher, text));

        // when
        tweetService.publishTweet(publisher, text);
    }

    private Tweet getTweet(String publisher, String text) {
        Tweet tweet = new Tweet();
        tweet.setPublisher(publisher);
        tweet.setTweet(text);
        return tweet;
    }
}
