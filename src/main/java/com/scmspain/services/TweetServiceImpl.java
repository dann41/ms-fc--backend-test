package com.scmspain.services;

import com.scmspain.entities.Tweet;
import com.scmspain.repository.TweetRepository;
import org.springframework.boot.actuate.metrics.writer.Delta;
import org.springframework.boot.actuate.metrics.writer.MetricWriter;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class TweetServiceImpl implements TweetService {

    private final TweetRepository repository;
    private final MetricWriter metricWriter;

    public TweetServiceImpl(TweetRepository repository, MetricWriter metricWriter) {
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
        if (publisher != null && publisher.length() > 0 && text != null && text.length() > 0 && text.length() < 140) {
            Tweet tweet = new Tweet();
            tweet.setTweet(text);
            tweet.setPublisher(publisher);

            this.metricWriter.increment(new Delta<Number>("published-tweets", 1));
            repository.save(tweet);
        } else {
            throw new IllegalArgumentException("Tweet must not be greater than 140 characters");
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
}
