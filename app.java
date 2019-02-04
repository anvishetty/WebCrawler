import java.io.File;
import java.io.IOException;

public class app {


      public static void main(String[] args) throws IOException {

          webCrawler webCrawlerTest=new webCrawler();
        webCrawlerTest.crawl("https://www.yummly.com/recipes",new File("output.txt"));

    }
}
