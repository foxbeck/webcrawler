##webcrawler

This project has been written in java and built as an executable jar using maven.

The project was written using Intellij, and an Intellij project file is included.

Unit tests were run within Intellij using JUnit 4.

The project depends on two 3rd party libraries, **JSoup** for parsing HTML, and **guava** for utilities.

The project was built using Java 1.8, although it doesn't use 1.8 features.

The webcrawler currently only parses html files, so any dependencies in, for example, javascript files, will not be picked up.

The rules for parsing html can be easily extended by implementing **LinkFinderRule** and passing to **WebCrawler**, as shown in **WebCrawlerTest**.

###To Build

To build you will need to have maven installed.

To build the executable jar, run maven as follows in the root directory of the project:

```mvn clean compile assembly:single``` 

###To Run

I've only run this on my mac...

To run the application on a mac, cd to ```target``` and run 


```java -jar webcrawler-1.0-jar-with-dependencies.jar <url>```

For example:

```java -jar webcrawler-1.0-jar-with-dependencies.jar http://jhy.io```