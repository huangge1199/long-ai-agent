package com.huangge1199.aiagent.controller;

import cn.hutool.json.JSONObject;
import com.alibaba.dashscope.exception.ApiException;
import com.alibaba.dashscope.exception.InputRequiredException;
import com.alibaba.dashscope.exception.NoApiKeyException;
import com.huangge1199.aiagent.Service.InvokeService;
import com.huangge1199.aiagent.common.R;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * InvokeController
 *
 * @author huangge1199
 * @since 2025/5/14 12:56:17
 */
@RestController
@RequestMapping("/mbgk/data")
@Tag(name = "AI大模型接入")
public class InvokeController {

    @Resource
    private InvokeService invokeService;

    @PostMapping("/sdk")
    @Operation(summary = "sdk接入")
    public R<JSONObject> sdkAiInvoke(@RequestBody String question) {
        try {
            JSONObject result = invokeService.callWithMessage(question);
            return R.ok(result);
        } catch (ApiException | NoApiKeyException | InputRequiredException e) {
            return R.fail(e.getMessage());
        }
    }

    @PostMapping("/http")
    @Operation(summary = "http接入")
    public R<JSONObject> httpAiInvoke(@RequestBody String question) {
        try {
            JSONObject result = invokeService.getMsgByHttp(question);
            return R.ok(result);
        } catch (ApiException e) {
            return R.fail(e.getMessage());
        }
    }

    @PostMapping("/springAi")
    @Operation(summary = "spring ai 接入")
    public R<String> springAiInvoke(@RequestBody String question) {
        try {
            String result = invokeService.getMsgBySpringAi(question);
            return R.ok(result);
        } catch (ApiException e) {
            return R.fail(e.getMessage());
        }
    }

    @PostMapping("/ollama")
    @Operation(summary = "spring ai 引入 ollama")
    public R<String> ollamaInvoke(@RequestBody String question) {
        try {
            String result = invokeService.getMsgBySpringAiOllam(question);
            return R.ok(result);
        } catch (ApiException e) {
            return R.fail(e.getMessage());
        }
    }
}
