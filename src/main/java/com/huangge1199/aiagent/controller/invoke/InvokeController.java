package com.huangge1199.aiagent.controller.invoke;

import com.alibaba.dashscope.aigc.generation.GenerationResult;
import com.alibaba.dashscope.exception.ApiException;
import com.alibaba.dashscope.exception.InputRequiredException;
import com.alibaba.dashscope.exception.NoApiKeyException;
import com.alibaba.dashscope.utils.JsonUtils;
import com.huangge1199.aiagent.Service.InvokeService;
import com.huangge1199.aiagent.common.R;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
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
    public R<String> sdkAiInvoke() {
        try {
            GenerationResult result = invokeService.callWithMessage();
            return R.ok(JsonUtils.toJson(result));
        } catch (ApiException | NoApiKeyException | InputRequiredException e) {
            return R.fail(e.getMessage());
        }
    }
}
