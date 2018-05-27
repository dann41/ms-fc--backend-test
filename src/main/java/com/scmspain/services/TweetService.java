package com.scmspain.services;

import com.scmspain.entities.Tweet;

import java.util.List;

public interface TweetService {

    /**
     * Push tweet to repository
     * @param publisher creator of the Tweet
     * @param text Content of the Tweet
     */
    void publishTweet(String publisher, String text);

    /**
     * Recover tweet from repository
     * @param id id of the Tweet to retrieve
     * @return retrieved Tweet
     */
    Tweet getTweet(Long id);

    /**
     * Retrieves all tweets from repository
     * @return retrieved Tweet
     */
    List<Tweet> listAllTweets();

    /**
     * Discards a tweet
     * @param tweetId
     */
    void discardTweet(Long tweetId);

    /**
     * Retrieves all discarded tweets from repository
     * @return retrieved discarded tweets
     */
    List<Tweet> listDiscardedTweets();
}
