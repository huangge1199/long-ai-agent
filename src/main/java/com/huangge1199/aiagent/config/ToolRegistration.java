package com.huangge1199.aiagent.config;

import com.huangge1199.aiagent.tools.*;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.ToolCallbacks;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * ToolRegistration
 *
 * @author huangge1199
 * @since 2025/5/28 16:54:55
 */
@Configuration
public class ToolRegistration {

    @Value("${search-api.api-key}")
    private String searchApiKey;

    @Bean
    public ToolCallback[] allTools() {
        FileTool fileTool = new FileTool();
        WebSearchTool webSearchTool = new WebSearchTool(searchApiKey);
        WebScrapTool webScrapingTool = new WebScrapTool();
        DownloadTool resourceDownloadTool = new DownloadTool();
        TerminalTool terminalOperationTool = new TerminalTool();
        PDFGenerationTool pdfGenerationTool = new PDFGenerationTool();
        return ToolCallbacks.from(
                fileTool,
                webSearchTool,
                webScrapingTool,
                resourceDownloadTool,
                terminalOperationTool,
                pdfGenerationTool
        );
    }
}
