package com.huangge1199.aiagent.controller;

import com.alibaba.dashscope.aigc.generation.GenerationOutput;
import com.alibaba.dashscope.common.Message;
import com.alibaba.dashscope.common.Role;
import com.alibaba.dashscope.exception.InputRequiredException;
import com.alibaba.dashscope.exception.NoApiKeyException;
import com.huangge1199.aiagent.Service.AiService;
import com.huangge1199.aiagent.common.R;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import reactor.core.publisher.Flux;
import com.alibaba.dashscope.aigc.generation.Generation;
import com.alibaba.dashscope.aigc.generation.GenerationParam;

import java.io.IOException;
import java.util.Arrays;

/**
 * AiController
 *
 * @author huangge1199
 * @since 2025/6/10 14:35:39
 */
@RestController
@RequestMapping("/ai")
@Tag(name = "AI")
public class AiController {

    @Resource
    private AiService aiService;

    @Value("${bailian.API-KEY}")
    private String apiKey;

    @Operation(summary = "同步调用")
    @GetMapping("/sync")
    public String doChatWithSync(String message, String chatId) {
        return aiService.doChat(message, chatId);
    }

    @Operation(summary = "SSE 流式调用")
    @GetMapping(value = "/sse", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> doChatWithSse(String message, String chatId) {
        return aiService.doChatByStream(message, chatId);
    }

//    @GetMapping(value = "/sse")
//    public Flux<ServerSentEvent<String>> doChatWithSse(String message, String chatId) {
//        return aiService.doChatByStream(message, chatId)
//                .map(chunk -> ServerSentEvent.<String>builder()
//                        .data(chunk)
//                        .build());
//    }

    @Operation(summary = "SSE Emitter 流式调用")
    @GetMapping("/emitter")
    public SseEmitter doChatWithSseEmitter(String message, String chatId) {
        // 创建一个超时时间较长的 SseEmitter
        // 3分钟超时
        SseEmitter emitter = new SseEmitter(180000L);
        // 获取 Flux 数据流并直接订阅
        aiService.doChatByStream(message, chatId)
                .subscribe(
                        // 处理每条消息
                        chunk -> {
                            try {
                                emitter.send(chunk);
                            } catch (IOException e) {
                                emitter.completeWithError(e);
                            }
                        },
                        // 处理错误
                        emitter::completeWithError,
                        // 处理完成
                        emitter::complete
                );
        // 返回emitter
        return emitter;
    }

    @Operation(summary = "云百炼测试")
    @GetMapping("/yun")
    public R<String> yunTest(String message, String model) throws NoApiKeyException, InputRequiredException {
        Generation gen = new Generation();
        Message systemMsg = Message.builder()
                .role(Role.SYSTEM.getValue())
                .content("You are a helpful assistant.")
                .build();
        Message userMsg = Message.builder()
                .role(Role.USER.getValue())
                .content(message)
                .build();
        GenerationParam param = GenerationParam.builder()
                // 若没有配置环境变量，请用百炼API Key将下行替换为：.apiKey("sk-xxx")
                .apiKey(apiKey)
                .model(model)
                .messages(Arrays.asList(systemMsg, userMsg))
                .resultFormat(GenerationParam.ResultFormat.MESSAGE)
                .build();
        GenerationOutput output = gen.call(param).getOutput();
        String text = output.getChoices().get(0).getMessage().getContent();
        return R.ok(text);
    }
}
