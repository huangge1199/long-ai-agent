package com.huangge1199.aiagent.Service;

import java.util.List;

/**
 * ToolsService
 *
 * @author huangge1199
 * @since 2025/5/27 15:06:53
 */
public interface ToolsService {
    String getWeather(String question);

    String writeFileTest(String context, String name);

    String readFileTest(String name);

    String aiWriteFile(String question);

    List<String> webSearch(String question);

    List<String> webScrap(String question);

    String terminalTool(String command);

    void downloadTool(String url, String name);
}
