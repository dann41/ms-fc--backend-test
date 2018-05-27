package com.scmspain.builders;

import com.scmspain.entities.Tweet;
import com.scmspain.entities.TweetLink;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class TweetWithLinksBuilder implements TweetBuilder {

    // A link is any set of non-whitespace consecutive characters starting with http:// or https:// and finishing with a space.
    private static final Pattern PATTERN_HREF = Pattern.compile("(?:http|https)://\\S+ ");

    @Override
    public Tweet buildTweet(String publisher, String text) {
        Tweet tweet = new Tweet();
        tweet.setPublisher(publisher);

        LinkExtractor extractor = getLinkedTweet(text);
        tweet.setTweet(extractor.getShortText());
        tweet.setLinks(toTweetLinks(tweet, extractor.getLinks()));

        return tweet;
    }

    private LinkExtractor getLinkedTweet(String text) {
        return new LinkExtractor(text);
    }

    private List<TweetLink> toTweetLinks(Tweet tweet, List<String> links) {
        return links.stream()
                .map(link -> toTweetLink(tweet, link))
                .collect(Collectors.toList());
    }

    private TweetLink toTweetLink(Tweet tweet, String link) {
        TweetLink tweetLink = new TweetLink();
        tweetLink.setHref(link);
        tweetLink.setTweet(tweet);
        return tweetLink;
    }

    static class LinkExtractor {

        private final String text;
        private String shortText;
        private List<String> links;

        LinkExtractor(String text) {
            this.text = text;
            extractLinks();
        }

        private void extractLinks() {
            shortText = PATTERN_HREF.splitAsStream(text).collect(Collectors.joining(""));

            Matcher matcher = PATTERN_HREF.matcher(text);
            links = new ArrayList<>();
            while (matcher.find()) {
                links.add(matcher.group().trim());
            }
        }

        String getShortText() {
            return shortText;
        }

        List<String> getLinks() {
            return links;
        }

    }
}
