package uk.co.foxbeck.webcrawler;

import com.sun.istack.internal.NotNull;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

public class WebCrawlerTest {

    private static final String DOMAIN = "jhy.io";
    private static final String SEED_URL = "http://" + DOMAIN;

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

    @Test
    public void testCrawlWithTestRule() throws Exception {
        WebCrawler webCrawler = new WebCrawler(SEED_URL);
        webCrawler.addRule(new TestLinkFinderRule());
        List<Link> links = webCrawler.crawl();
        assertThat(links, notNullValue());
        assertThat(links.size(), equalTo(6));
    }

    @Test
    public void testCrawlWithImageRule() throws Exception {
        WebCrawler webCrawler = new WebCrawler(SEED_URL);
        webCrawler.addRule(new ImageFinderRule());
        List<Link> links = webCrawler.crawl();
        assertThat(links, notNullValue());
        assertThat(links.size(), equalTo(2));
    }

    @Test
    public void testCrawlWithScriptRule() throws Exception {
        WebCrawler webCrawler = new WebCrawler(SEED_URL);
        webCrawler.addRule(new ScriptFinderRule());
        List<Link> links = webCrawler.crawl();
        assertThat(links, notNullValue());
        assertThat(links.size(), equalTo(1));
    }

    @Test
    public void testCrawlWithHtmlRule() throws Exception {
        WebCrawler webCrawler = new WebCrawler(SEED_URL);
        webCrawler.addRule(new HtmlLinkFinderRule(DOMAIN));
        List<Link> links = webCrawler.crawl();
        assertThat(links, notNullValue());
        assertThat(links.size(), equalTo(16));
    }

    @Test
    public void testCrawlWithAllRules() throws Exception {
        WebCrawler webCrawler = new WebCrawler(SEED_URL);
        webCrawler.addRule(new ImageFinderRule());
        webCrawler.addRule(new ScriptFinderRule());
        webCrawler.addRule(new HtmlLinkFinderRule(DOMAIN));
        List<Link> links = webCrawler.crawl();
        assertThat(links, notNullValue());
        assertThat(links.size(), equalTo(17));
    }
}