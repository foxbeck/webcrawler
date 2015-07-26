package uk.co.foxbeck.webcrawler;

import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.List;

public class TestLinkFinderRule implements LinkFinderRule {
    @Override
    public List<Link> findLinks(Document document) {
        List<Link> links = new ArrayList<>();

        for (int i = 0; i < 5; ++i) {
            Link link = Link.createUnvisitableLink("http://test.example" + i);
            links.add(link);
        }
        return links;
    }
}
