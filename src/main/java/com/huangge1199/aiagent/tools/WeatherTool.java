package com.huangge1199.aiagent.tools;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;

/**
 * WeatherTools
 *
 * @author huangge1199
 * @since 2025/5/27 15:01:04
 */
public class WeatherTool {

    @Tool(description = "Get current weather for a location")
    public String getWeather(@ToolParam(description = "The city name") String city) {
        return "Current weather in " + city +": Sunny, 25°";
    }
}
