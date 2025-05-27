package com.huangge1199.aiagent.Service.impl;

import com.huangge1199.aiagent.Service.ToolsService;
import com.huangge1199.aiagent.config.MyLoggerAdvisor;
import com.huangge1199.aiagent.tools.WeatherTools;
import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.stereotype.Service;

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

    @Override
    public String getWeather(String question) {
        return ChatClient.create(ollamaChatModel)
                .prompt(question)
                .advisors(new MyLoggerAdvisor())
                .tools(new WeatherTools())
                .call().content();
    }
}
