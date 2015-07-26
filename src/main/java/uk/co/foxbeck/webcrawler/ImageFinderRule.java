package uk.co.foxbeck.webcrawler;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class ImageFinderRule implements LinkFinderRule {

    @Override
    public List<Link> findLinks(Document document) {
        List<Link> links = new ArrayList<>();


        Elements elements = document.select("img[src]");
        for (Element link : elements) {
            addLink(link.attr("abs:src"), links);
        }

        return links;

    }

    private void addLink(String urlString, List<Link> links) {

        Link link = Link.createUnvisitableLink(urlString);
        if (link != null) {
            links.add(link);
        }

    }
}
