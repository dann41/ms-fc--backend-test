package com.scmspain.services;

import com.scmspain.builders.TweetBuilder;
import com.scmspain.entities.Tweet;
import com.scmspain.exceptions.EntityNotFoundException;
import com.scmspain.repository.TweetRepository;
import org.springframework.boot.actuate.metrics.writer.Delta;
import org.springframework.boot.actuate.metrics.writer.MetricWriter;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class TweetServiceImpl implements TweetService {

    private final TweetBuilder builder;
    private final TweetRepository repository;
    private final MetricWriter metricWriter;

    public TweetServiceImpl(TweetBuilder tweetBuilder, TweetRepository repository, MetricWriter metricWriter) {
        this.builder = tweetBuilder;
        this.repository = repository;
        this.metricWriter = metricWriter;
    }

    /**
     * Push tweet to repository
     * @param publisher creator of the Tweet
     * @param text Content of the Tweet
     */
    @Override
    public void publishTweet(String publisher, String text) {
        if (publisher != null && publisher.length() > 0 && text != null && text.length() > 0) {
            Tweet tweet = builder.buildTweet(publisher, text);
            if (tweet.getTweet().length() <= Tweet.MAX_TWEET_LENGTH) {
                metricWriter.increment(new Delta<Number>("published-tweets", 1));
                repository.save(tweet);
            } else {
                throw new IllegalArgumentException("Tweet must not be greater than 140 characters");
            }
        } else {
            throw new IllegalArgumentException("Tweet or publisher must not be empty");
        }
    }

    /**
     * Recover tweet from repository
     * @param id id of the Tweet to retrieve
     * @return retrieved Tweet
     */
    @Override
    public Tweet getTweet(Long id) {
        return repository.findOne(id);
    }

    /**
     * Retrieves all tweets from repository
     * @return retrieved Tweet
     */
    @Override
    public List<Tweet> listAllTweets() {
        this.metricWriter.increment(new Delta<Number>("times-queried-tweets", 1));
        return repository.findAllSortedByIdDesc();
    }

    @Override
    public void discardTweet(Long tweetId) {
        Tweet tweet = getTweet(tweetId);
        if (tweet == null) {
            throw new EntityNotFoundException();
        }
        tweet.setDiscarded(true);
    }
}
