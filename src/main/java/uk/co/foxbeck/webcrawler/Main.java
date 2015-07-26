package uk.co.foxbeck.webcrawler;

public class Main {
    public static void main(String[] args) {


        if (args.length == 1) {

            try {
                new Main(args[0]);
            } catch (WebCrawlerException e) {
                System.err.println(e.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else {
            System.err.println("Please pass in a single argument in the form of a valid URL");

        }
    }

    private Main(String urlString) {

        WebCrawler webCrawler = new WebCrawler(urlString);
        webCrawler.crawl();
        webCrawler.print();
    }
}
