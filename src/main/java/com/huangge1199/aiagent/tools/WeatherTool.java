package com.huangge1199.aiagent.tools;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;

/**
 * WeatherTools
 *
 * @author huangge1199
 * @since 2025/5/27 15:01:04
 */
@Slf4j
public class WeatherTool {

    @Tool(description = "Get current weather for a location")
    public String getWeather(@ToolParam(description = "The city name") String city) {
        log.info("Current weather in {}: Sunny, 25°", city);
        return "Current weather in " + city + ": Sunny, 25°";
    }
}
