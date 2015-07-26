package uk.co.foxbeck.webcrawler;

import com.google.common.base.Objects;

import java.net.MalformedURLException;
import java.net.URL;

public class Link {
    private final URL url;
    private final boolean visitable;

    public static Link createVisitableLink(URL url) {
        return new Link(url, true);
    }

    public static Link createVisitableLink(String urlString) {
        return create(urlString, true);
    }

    public static Link createUnvisitableLink(String urlString) {
        return create(urlString, false);
    }


    private static Link create(String urlString, boolean visitable) {

        URL url = null;
        try {
            url = new URL(urlString);
            return new Link(url, visitable);
        } catch (MalformedURLException e) {
            if (!urlString.isEmpty()) {
                System.out.println("MALFORMED: " + urlString);
            }
            return null;
        }

    }

    private Link(URL url, boolean visitable) {
        this.url = url;
        this.visitable = visitable;
    }

    public URL getUrl() {
        return url;
    }

    public boolean isVisitable() {
        return visitable;
    }

    @Override
    public String toString() {
        String ret = visitable ? "+ " : "- ";
        ret += url.getProtocol() + "://" + url.getHost() + url.getPath();
        return ret;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Link link = (Link) o;
        return Objects.equal(toString(), link.toString());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(toString());
    }

}
