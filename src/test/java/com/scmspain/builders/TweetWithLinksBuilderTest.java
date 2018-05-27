package com.scmspain.builders;

import com.scmspain.entities.Tweet;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class TweetWithLinksBuilderTest {

    private static final String HTTP_LINK = "http://es.wikipedia.org/wiki/Monkey_Island";
    private static final String HTTPS_LINK = "https://es.wikipedia.org/wiki/Monkey_Island";

    private TweetWithLinksBuilder builder;

    @Before
    public void setUp() {
        builder = new TweetWithLinksBuilder();
    }

    @Test
    public void giveTweetWithoutLinksShouldCreateValidTweet() {
        // given
        String publisher = "Guybrush Threepwood";
        String text = "I am Guybrush Threepwood, mighty pirate.";

        // when
        Tweet result = builder.buildTweet(publisher, text);

        // then
        assertThat(result.getPublisher(), is(publisher));
        assertThat(result.getTweet(), is(text));
    }

    @Test
    public void giveTweetWithHttpLinkShouldCreateValidTweet() {
        // given
        String publisher = "Guybrush Threepwood";
        String text = "I am Guybrush Threepwood, mighty pirate. ";

        // when
        Tweet result = builder.buildTweet(publisher, text + HTTP_LINK + " ");

        // then
        assertThat(result.getPublisher(), is(publisher));
        assertThat(result.getTweet(), is(text));
        assertThat(result.getLinks().size(), is(1));
        assertThat(result.getLinks().get(0).getHref(), is(HTTP_LINK));
    }

    @Test
    public void giveTweetWithHttpsLinkShouldCreateValidTweet() {
        // given
        String publisher = "Guybrush Threepwood";
        String text = "I am Guybrush Threepwood, mighty pirate. ";

        // when
        Tweet result = builder.buildTweet(publisher, text + HTTPS_LINK + " ");

        // then
        assertThat(result.getPublisher(), is(publisher));
        assertThat(result.getTweet(), is(text));
        assertThat(result.getLinks().size(), is(1));
        assertThat(result.getLinks().get(0).getHref(), is(HTTPS_LINK));
    }

    @Test
    public void giveTweetWithTwoLinksShouldCreateValidTweetWithTwoLinks() {
        // given
        String publisher = "Guybrush Threepwood";
        String text = "Visit " + HTTP_LINK + " and " + HTTPS_LINK + " to know more about Monkey Island";

        // when
        Tweet result = builder.buildTweet(publisher, text);

        // then
        assertThat(result.getPublisher(), is(publisher));
        assertThat(result.getTweet(), is("Visit and to know more about Monkey Island"));
        assertThat(result.getLinks().size(), is(2));
        assertThat(result.getLinks().get(0).getHref(), is(HTTP_LINK));
        assertThat(result.getLinks().get(1).getHref(), is(HTTPS_LINK));
    }
}
