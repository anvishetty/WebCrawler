import com.google.gson.*;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class webCrawler {

    Set<String> pagesVisited = new HashSet<String>();
    Set<String> pagesToVisit = new HashSet<String>();



   /* public static void main(String[] args){
        crawl("https://www.yummly.com/recipes",new File("output.txt");
    }*/

    public void crawl(String URL, File outputFile) throws IOException {
        PrintWriter out = new PrintWriter(new FileWriter(outputFile));
        pagesToVisit.add(URL);
        while (pagesToVisit.size() > 0) {
            String currentPage = pagesToVisit.iterator().next();
            pagesToVisit.remove(currentPage);
            pagesVisited.add(currentPage);
            Connection connection = Jsoup.connect(currentPage);
            Document webDocument = connection.get();
            extractData(webDocument, out);
            Elements URLs = webDocument.select("a");
            filterAndAdd(URLs);
        }

    }

    private void extractData(Document webDocument, PrintWriter out) {
        Elements structuredData = webDocument.body().getElementsByClass("structured-data-info");
        if (structuredData == null) {
            return;
        }
        if (structuredData.size() < 1) {
            return;
        }
        String jsonString = structuredData.get(0).toString();

        StringBuilder sbJsonString = new StringBuilder(jsonString);
        sbJsonString.delete(0, jsonString.indexOf('{'));
        String newJsonString = sbJsonString.delete(sbJsonString.lastIndexOf("}") + 1, sbJsonString.length()).toString();
        JsonParser parser = new JsonParser();
        JsonObject obj = parser.parse(newJsonString).getAsJsonObject();
        JsonArray wordArray = (JsonArray) parser.parse(newJsonString).getAsJsonObject().get("itemListElement");
        if (wordArray != null) {
            for (Object o : wordArray) {
                JsonObject JO = (JsonObject) o;
                JsonElement name = ((JsonObject) o).get("name");
                if (name != null) {
                    String strName = name.toString();
                    //System.out.println("Name:"+strName);
                    out.print("[\n{\nName: " + strName+"\n");
                    out.flush();
                }
                JsonElement imageRef = ((JsonObject) o).get("image");
                if (imageRef != null) {
                    String strImage = imageRef.toString();
                    //System.out.println("Image:"+strImage);
                    out.print("Photo: " + strImage+"\n");
                    out.flush();
                }

                JsonElement url = ((JsonObject) o).get("url");
                if (url != null) {
                    String strURL = url.toString();
                    //System.out.println("URL:" + strURL);
                    pagesVisited.add(strURL);
                    out.print("URL: " + strURL+"\n");
                    out.flush();
                }

                JsonElement ingredient = ((JsonObject) o).get("recipeIngredient");
                if (ingredient != null) {
                    String strIngredient = ingredient.toString();
                    //System.out.println("Ingredients:" + strIngredient);
                    out.print("Ingredients: " + strIngredient+"\n");
                    out.flush();
                }
                JsonElement rating = ((JsonObject) o).get("aggregateRating");
                if (rating != null) {
                    String strRating = rating.toString();
                    //System.out.println("Rating:" + strRating);
                    out.print("Rating: " + strRating+"\n");
                    out.flush();
                }
                JsonElement time = ((JsonObject) o).get("totalTime");
                if (time != null) {
                    String strTime = time.toString();
                    //System.out.println("Cook Time:" + strTime);
                    out.print("Cook Time: " + strTime + "\n");
                    out.flush();
                }
                JsonElement serve = ((JsonObject) o).get("recipeYield");
                if (serve != null) {
                    String strServe = serve.toString();
                    //System.out.println("Tags:" + strServe);
                    out.print("Serve: " + strServe + "\n");
                    out.flush();
                }
                JsonElement tag = ((JsonObject) o).get("keywords");
                if (tag != null) {
                    String strTag = tag.toString();
                    //System.out.println("Tags:" + strTag);
                    out.print("Tags: " + strTag+"\n");
                    out.flush();
                }
                JsonElement recipeType = ((JsonObject) o).get("recipeCategory");
                if (recipeType != null) {
                    String strRecipeType = recipeType.toString();
                    //System.out.println("Recipe Type:" + strRecipeType);
                    out.print("Recipe Type: " + strRecipeType+"\n");
                    out.flush();
                }
            }

        }
    }



    //Filter URL by /recipe and only add those
    private void filterAndAdd(Elements URLs) {
        for (Element element : URLs) {
            String currentURL = element.absUrl("href");
            if (currentURL.contains("recipe")) {
                if (!pagesVisited.contains(currentURL)) {
                    pagesToVisit.add(currentURL);
                }}
        }
    }
}
