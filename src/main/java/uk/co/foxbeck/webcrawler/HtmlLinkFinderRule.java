package uk.co.foxbeck.webcrawler;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class HtmlLinkFinderRule implements LinkFinderRule {
    private final String domain;

    public HtmlLinkFinderRule(String domain) {
        this.domain = domain;
    }

    @Override
    public List<Link> findLinks(Document document) {
        List<Link> links = new ArrayList<>();

        Elements elements = document.select("*[href]");
        for (Element link : elements) {
            addLink(link.attr("abs:href"), links);
        }


        return links;

    }

    private void addLink(String urlString, List<Link> links) {

        Link link = Link.createVisitableLink(urlString);
        if (link != null) {
            String host = link.getUrl().getHost();
            if (host != null && !host.isEmpty()) {
                if (host.endsWith(domain)) {
                    links.add(link);
                }
                else {
                    links.add(Link.createUnvisitableLink(urlString));
                }
            }
            else {
                links.add(link);
            }
        }

    }
}
