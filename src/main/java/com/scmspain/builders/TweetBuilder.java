package com.scmspain.builders;

import com.scmspain.entities.Tweet;

public interface TweetBuilder {

    /**
     * Given a plaint text tweet, extracts the links
     * @param publisher publisher of the tweet
     * @param text tweet
     * @return new tweet object
     */
    Tweet buildTweet(String publisher, String text);

}
