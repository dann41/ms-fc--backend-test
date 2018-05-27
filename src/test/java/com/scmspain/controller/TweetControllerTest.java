package com.scmspain.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.scmspain.configuration.TestConfiguration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import javax.persistence.EntityManager;
import java.util.List;

import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestConfiguration.class)
@Sql("tweets.sql")
public class TweetControllerTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        this.mockMvc = webAppContextSetup(this.context).build();
    }

    @Test
    public void shouldReturn201WhenInsertingAValidTweet() throws Exception {
        mockMvc.perform(newTweet("Prospect", "Breaking the law"))
            .andExpect(status().is(201));
    }

    @Test
    public void shouldReturn201WhenInsertingAValidTweetWithLink() throws Exception {
        mockMvc.perform(newTweet("Schibsted Spain", "We are Schibsted Spain (look at our home page http://www.schibsted.es/ ), we own Vibbo, InfoJobs, fotocasa, coches.net and milanuncios. Welcome!"))
                .andExpect(status().is(201));
    }

    @Test
    public void shouldReturn400WhenInsertingAnInvalidTweet() throws Exception {
        mockMvc.perform(newTweet("Schibsted Spain", "We are Schibsted Spain (look at our home page http://www.schibsted.es/ for more info), we own Vibbo, InfoJobs, fotocasa, coches.net, habitaclia and milanuncios. Welcome!"))
                .andExpect(status().is(400));
    }

    @Test
    public void shouldReturnAllPublishedTweets() throws Exception {
        MvcResult getResult = mockMvc.perform(get("/tweet"))
                .andExpect(status().is(200))
                .andReturn();

        String content = getResult.getResponse().getContentAsString();
        assertThat(new ObjectMapper().readValue(content, List.class).size()).isEqualTo(2);
    }

    @Test
    public void shouldReturn200WhenDiscardingAnUnknownTweet() throws Exception {
        mockMvc.perform(discardTweet(1L))
                .andExpect(status().is(200));
    }

    @Test
    public void shouldReturn404WhenDiscardingAnUnknownTweet() throws Exception {
        mockMvc.perform(discardTweet(5L))
                .andExpect(status().is(404));
    }

    @Test
    public void shouldReturnAllDiscardedTweets() throws Exception {
        MvcResult getResult = mockMvc.perform(get("/discarded"))
                .andExpect(status().is(200))
                .andReturn();

        String content = getResult.getResponse().getContentAsString();
        assertThat(new ObjectMapper().readValue(content, List.class).size()).isEqualTo(1);
    }

    private MockHttpServletRequestBuilder newTweet(String publisher, String tweet) {
        return post("/tweet")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(format("{\"publisher\": \"%s\", \"tweet\": \"%s\"}", publisher, tweet));
    }

    private MockHttpServletRequestBuilder discardTweet(Long id) {
        return post("/discarded")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(format("{\"tweet\": %d}", id));
    }

}
