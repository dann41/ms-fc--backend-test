package com.scmspain.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;

@Entity
public class TweetLink {

    @GeneratedValue
    @Id
    private Long id;

    @Column(nullable = false)
    private String href;

    @ManyToOne
    @JoinColumn(name = "tweet_id", nullable = false)
    @JsonManagedReference
    private Tweet tweet;

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public void setTweet(Tweet tweet) {
        this.tweet = tweet;
    }
}
