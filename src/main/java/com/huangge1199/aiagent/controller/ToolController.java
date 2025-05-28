package com.huangge1199.aiagent.controller;

import cn.hutool.json.JSONObject;
import com.huangge1199.aiagent.Service.ToolsService;
import com.huangge1199.aiagent.common.R;
import com.huangge1199.aiagent.util.CheckUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
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

    @PostMapping("/getAiWeather")
    @Operation(summary = "AI获取天气")
    public R<String> getAiWeather(@RequestBody String question) {
        String result = toolsService.getWeather(question);
        return R.ok(result);
    }

    @PostMapping("/writeFile")
    @Operation(summary = "写文件测试")
    public R<?> writeFile(@RequestBody JSONObject params) {
        String context = params.getStr("context");
        String name = params.getStr("name");
        CheckUtils.checkEmpty(context, "内容");
        CheckUtils.checkEmpty(name, "文件名");
        String result = toolsService.writeFileTest(context, name);
        return R.ok(result);
    }

    @PostMapping("/readFile")
    @Operation(summary = "读文件测试")
    public R<?> readFile(@RequestBody JSONObject params) {
        String name = params.getStr("name");
        CheckUtils.checkEmpty(name, "文件名");
        String result = toolsService.readFileTest(name);
        return R.ok(result);
    }

    @PostMapping("/aiWriteFile")
    @Operation(summary = "AI写文件")
    public R<String> aiWriteFile(@RequestBody String question) {
        CheckUtils.checkEmpty(question, "问题");
        String result = toolsService.aiWriteFile(question);
        return R.ok(result);
    }

    @PostMapping("/webSearch")
    @Operation(summary = "联网搜索")
    public R<List<String>> webSearch(@RequestBody String question) {
        CheckUtils.checkEmpty(question, "问题");
        List<String> result = toolsService.webSearch(question);
        return R.ok(result);
    }
}
