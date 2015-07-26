package uk.co.foxbeck.webcrawler;

import com.sun.istack.internal.NotNull;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

public class WebCrawlerTest {

    private static final String SEED_URL = "http://jhy.io";

    @Test
    public void testConstructor() throws Exception {
        WebCrawler webCrawler = new WebCrawler(SEED_URL);
    }

    @Test(expected = WebCrawlerException.class)
    public void testBadUrl() throws Exception {
        WebCrawler webCrawler = new WebCrawler("bad url");
    }

    @Test(expected = WebCrawlerException.class)
    public void testEmptyUrl() throws Exception {
        WebCrawler webCrawler = new WebCrawler("");
    }

    @Test(expected = WebCrawlerException.class)
    public void testNullUrl() throws Exception {
        WebCrawler webCrawler = new WebCrawler(null);
    }

    @Test
    public void testCrawl() throws Exception {
        WebCrawler webCrawler = new WebCrawler(SEED_URL);
        List<Link> links = webCrawler.crawl();
        assertThat(links, notNullValue());
        assertThat(links.size(), greaterThan(0));
    }
}