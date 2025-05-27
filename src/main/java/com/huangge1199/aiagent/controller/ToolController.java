package com.huangge1199.aiagent.controller;

import com.huangge1199.aiagent.Service.ToolsService;
import com.huangge1199.aiagent.common.R;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.ai.document.Document;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * ToolController
 *
 * @author huangge1199
 * @since 2025/5/27 14:59:32
 */
@RestController
@RequestMapping("/tool")
@Tag(name = "工具调用")
public class ToolController {

    @Resource
    private ToolsService toolsService;

    @PostMapping("/getWeather")
    @Operation(summary = "获取天气")
    public R<String> getWeather(@RequestBody String question) {
        String result = toolsService.getWeather(question);
        return R.ok(result);
    }
}
