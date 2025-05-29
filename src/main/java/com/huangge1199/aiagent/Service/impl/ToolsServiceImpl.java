package com.huangge1199.aiagent.Service.impl;

import com.huangge1199.aiagent.Service.ToolsService;
import com.huangge1199.aiagent.config.MyLoggerAdvisor;
import com.huangge1199.aiagent.tools.*;
import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ToolsServiceImpl
 *
 * @author huangge1199
 * @since 2025/5/27 15:07:06
 */
@Service
public class ToolsServiceImpl implements ToolsService {

    @Resource
    private OllamaChatModel ollamaChatModel;

    @Value("${search-api.api-key}")
    private String searchApiKey;

    @Resource
    private ToolCallback[] allTools;

    @Override
    public String getWeather(String question) {
        return ChatClient.create(ollamaChatModel)
                .prompt(question)
                .advisors(new MyLoggerAdvisor())
                .tools(new WeatherTool())
                .call().content();
    }

    @Override
    public String writeFileTest(String context, String name) {
        FileTool fileTool = new FileTool();
        return fileTool.writeFile(name, context);
    }

    @Override
    public String readFileTest(String name) {
        FileTool fileTool = new FileTool();
        return fileTool.readFile(name);
    }

    @Override
    public String aiWriteFile(String question) {
        return ChatClient.create(ollamaChatModel)
                .prompt(question)
                .advisors(new MyLoggerAdvisor())
                .tools(new FileTool())
                .call().content();
    }

    @Override
    public List<String> webSearch(String question) {
        WebSearchTool webSearchTool = new WebSearchTool(searchApiKey);
        return List.of(webSearchTool.searchWeb(question).split(","));
    }

    @Override
    public List<String> webScrap(String url) {
        WebScrapTool webScrapTool = new WebScrapTool();
        return List.of(webScrapTool.scrapeWebPage(url).split(","));
    }

    @Override
    public String terminalTool(String command) {
        TerminalTool terminalTool = new TerminalTool();
        return terminalTool.executeTerminalCommand(command);
    }

    @Override
    public void downloadTool(String url, String name) {
        DownloadTool downloadTool = new DownloadTool();
        downloadTool.downloadResource(url, name);
    }

    @Override
    public void pdfTool(String name, String context) {
        PDFGenerationTool pdfTool = new PDFGenerationTool();
        pdfTool.generatePDF(name, context);
    }

    @Override
    public String doChatWithTools(String question) {
        ChatResponse chatResponse = ChatClient.create(ollamaChatModel)
                .prompt()
                .user(question)
                // 开启日志，便于观察效果
                .advisors(new MyLoggerAdvisor())
                .tools(allTools)
                .call()
                .chatResponse();
        assert chatResponse != null;
        return chatResponse.getResult().getOutput().getText();
    }
}
