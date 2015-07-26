package uk.co.foxbeck.webcrawler;

import org.junit.Test;

import static org.junit.Assert.*;

public class WebCrawlerTest {

    private static final String SEED_URL = "http://jhy.io";

    @Test
    public void testConstructor() throws Exception {
        WebCrawler webCrawler = new WebCrawler(SEED_URL);

    }
}