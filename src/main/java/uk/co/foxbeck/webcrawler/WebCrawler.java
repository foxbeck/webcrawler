package uk.co.foxbeck.webcrawler;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class WebCrawler {

    private final URL seedUrl;
    private final List<Link> uniqueLinks = new ArrayList<>();
    private final List<LinkFinderRule> rules = new ArrayList<>();

    public WebCrawler(String seedUrlString) {

        try {
            seedUrl = new URL(seedUrlString);
        } catch (MalformedURLException e) {
            throw new WebCrawlerException("'" + seedUrlString + "' is not a valid URL.");
        }
    }

    private void setupDefaultRulesIfNoneSetAlready() {
        if (rules.isEmpty()) {
            addRule(new ImageFinderRule());
            addRule(new ScriptFinderRule());
            addRule(new HtmlLinkFinderRule(seedUrl.getHost()));
        }

    }

    public List<Link> crawl() {
        setupDefaultRulesIfNoneSetAlready();

        Link seedLink = Link.createVisitableLink(seedUrl);
        rememberLink(seedLink);

        findNewLinks(seedLink);

        return uniqueLinks;

    }

    public void print() {
        Collections.sort(uniqueLinks, new Comparator<Link>() {
            @Override
            public int compare(Link o1, Link o2) {
                return o1.toString().compareTo(o2.toString());
            }
        });

        System.out.println();
        for (Link l : uniqueLinks) {
            System.out.println(l);
        }

    }

    public void addRule(LinkFinderRule rule) {
        rules.add(rule);
    }


    private List<Link> findAllLinksInDocument(Document document) {
        List<Link> links = new ArrayList<>();

        for (LinkFinderRule rule : rules) {
            links.addAll(rule.findLinks(document));
        }

        return links;
    }

    private void findNewLinks(Link linkNode) {
        Document document = openDocument(linkNode);
        if (document != null) {
            List<Link> links = findAllLinksInDocument(document);

            List<Link> linksToVisit = new ArrayList<>();

            for (Link link : links) {
                if (isNewLink(link)) {
                    rememberLink(link);
                    if (link.isVisitable()) {
                        linksToVisit.add(link);
                    }
                }
            }

            for (Link link : linksToVisit) {
                findNewLinks(link);
            }

        }
    }


    private Document openDocument(Link link) {
        try {
            Connection connection = Jsoup.connect(link.getUrl().toExternalForm());
            return connection.get();
        } catch (IOException e) {
            return null;
        }

    }


    private void rememberLink(Link link) {
        uniqueLinks.add(link);
        System.out.print(".");
    }


    private boolean isNewLink(Link link) {
        boolean isNew = !uniqueLinks.contains(link);


        return isNew;
    }


}
