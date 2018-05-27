package com.scmspain.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.List;

@Entity
public class Tweet {

    public static final int MAX_TWEET_LENGTH = 140;

    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false)
    private String publisher;
    @Column(nullable = false, length = MAX_TWEET_LENGTH)
    private String tweet;
    @Column (nullable=true)
    private Long pre2015MigrationStatus = 0L;

    @OneToMany(targetEntity=TweetLink.class, cascade=CascadeType.ALL, mappedBy="tweet")
    @Column
    @JsonBackReference
    private List<TweetLink> links;

    public Tweet() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getTweet() {
        return tweet;
    }

    public void setTweet(String tweet) {
        this.tweet = tweet;
    }

    public Long getPre2015MigrationStatus() {
        return pre2015MigrationStatus;
    }

    public void setPre2015MigrationStatus(Long pre2015MigrationStatus) {
        this.pre2015MigrationStatus = pre2015MigrationStatus;
    }

    public void setLinks(List<TweetLink> links) {
        this.links = links;
    }

    public List<TweetLink> getLinks() {
        return links;
    }
}
