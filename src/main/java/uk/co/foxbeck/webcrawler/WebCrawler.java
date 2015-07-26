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

    public List<Link> crawl() {
        setupDefaultRulesIfNoneSetAlready();

        Link seedLink = Link.createVisitableLink(seedUrl);
        rememberLink(seedLink);

        findAndVisitNewLinks(seedLink);

        return uniqueLinks;
    }

    private void setupDefaultRulesIfNoneSetAlready() {
        if (rules.isEmpty()) {
            addRule(new ImageFinderRule());
            addRule(new ScriptFinderRule());
            addRule(new HtmlLinkFinderRule(seedUrl.getHost()));
        }
    }

    private void findAndVisitNewLinks(Link linkNode) {
        Document document = tryToOpenDocument(linkNode);
        if (document != null) {

            List<Link> links = findAllLinksInDocument(document);

            List<Link> linksToVisit = rememberNewLinksAndReturnVisitableOnes(links);
            findAndVisitNewLinks(linksToVisit);
        }
    }

    private void findAndVisitNewLinks(List<Link> list) {
        for (Link link : list) {
            findAndVisitNewLinks(link);
        }
    }

    private List<Link> rememberNewLinksAndReturnVisitableOnes(List<Link> links) {
        List<Link> linksToVisit = new ArrayList<>();

        for (Link link : links) {
            if (isNewLink(link)) {
                rememberLink(link);
                if (link.isVisitable()) {
                    linksToVisit.add(link);
                }
            }
        }

        return linksToVisit;
    }


    private Document tryToOpenDocument(Link link) {
        try {
            Connection connection = Jsoup.connect(link.getUrl().toExternalForm());
            return connection.get();
        } catch (IOException e) {
            return null;
        }

    }


    private List<Link> findAllLinksInDocument(Document document) {
        List<Link> links = new ArrayList<>();

        for (LinkFinderRule rule : rules) {
            links.addAll(rule.findLinks(document));
        }

        return links;
    }

    public void print() {
        sortLinksAlphabetically();
        printLinks();
    }

    private void printLinks() {
        System.out.println();
        for (Link l : uniqueLinks) {
            System.out.println(l);
        }

        System.out.println("Found " + uniqueLinks.size() + " files");
    }

    public void addRule(LinkFinderRule rule) {
        rules.add(rule);
    }

    private void rememberLink(Link link) {
        uniqueLinks.add(link);
        System.out.print(".");
    }

    private boolean isNewLink(Link link) {
        return !uniqueLinks.contains(link);
    }

    private void sortLinksAlphabetically() {
        Collections.sort(uniqueLinks, new Comparator<Link>() {
            @Override
            public int compare(Link o1, Link o2) {
                return o1.toString().compareTo(o2.toString());
            }
        });
    }
}
