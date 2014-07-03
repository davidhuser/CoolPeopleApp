package ch.fhnw.coin.coolpeople;


import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

class Wikipage extends Input {


    Wikipage(String url){
        readContent(url);
    }

    private void readContent(String url){
        try {
            // load wikipedia site
            Document doc = Jsoup.connect(url).get();
            System.out.println("Loading: " + url);

            //select all elements within the div
            Elements divcontent = doc.select("div#mw-content-text");

            // get selected elements without HTML-Tags
            for (Element e : divcontent) {
                content = content + e.text();
            }
            System.out.println(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
