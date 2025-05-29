package com.huangge1199.aiagent.tools;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;

import java.io.IOException;

/**
 * WebScrapTool
 *
 * @author huangge1199
 * @since 2025/5/28 15:10:11
 */
@Slf4j
public class WebScrapTool {

    @Tool(description = "Scrape the content of a web page")
    public String scrapeWebPage(@ToolParam(description = "URL of the web page to scrape") String url) {
        try {
            log.info("Scraping web page {}", url);
            Document doc = Jsoup.connect(url).get();
            log.info(doc.toString());
            return doc.html();
        } catch (IOException e) {
            return "Error scraping web page: " + e.getMessage();
        }
    }
}
