package com.example.demo.controller;

import com.example.demo.domain.FootballInvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("pm")
@Slf4j
@RequiredArgsConstructor
public class PMController {
    private final static String BASE_URL = "https://pm.by";

    @GetMapping
    public ResponseEntity<List<FootballInvent>> main() throws IOException {
        Document page = Jsoup.connect("https://pm.by/live.html").get();
        Element element = page.body().getElementById("footballSItem");
        List<Node> nodes = element.childNodes();
        List<FootballInvent> invents = new ArrayList<>();
        nodes.forEach(node -> {
            List<Node> items = node.childNodes();
            items.forEach(item -> {
                if (item.childNodeSize() < 6) {
                    return;
                }
                String url = item.childNode(6).childNode(0).childNode(1).childNode(0).attr("href");
                try {
                    double value = getValue(BASE_URL + url);
                    if (value >= 2.65) {
                        invents.add(new FootballInvent(BASE_URL + url, value));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        });
        return ResponseEntity.ok(invents);
    }

    private Double getValue(String url) throws IOException {
        Document page = Jsoup.connect(url).get();
        Elements elements = page.body().getElementsByClass("dyn");

        Node element = findElement(elements);
        double value = 0d;
        if (element != null) {
            Node node = element.childNode(0);
            value = Double.parseDouble(node.toString());
        }
        return value;
    }

    private Node findElement(Elements elements) {

        for (Element element : elements) {
            if (element.getElementsByClass("p2r").first().childNode(0).toString().equals("Гол после 80-й минуты:")) {
                return element.childNode(3).childNode(1).childNode(0);
            }

        }
        return null;
    }
}
