package uk.co.foxbeck.webcrawler;

import java.net.MalformedURLException;
import java.net.URL;

public class WebCrawler {

    public WebCrawler(String seedUrlString) {

        try {
            URL url = new URL(seedUrlString);
        } catch (MalformedURLException e) {
           throw new WebCrawlerException("'" + seedUrlString + "' is not a valid URL.");
        }

    }
}
