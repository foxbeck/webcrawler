package uk.co.foxbeck.webcrawler;

import org.jsoup.nodes.Document;

import java.util.List;

public interface LinkFinderRule {
    List<Link> findLinks(Document document);
}
