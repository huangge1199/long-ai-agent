package com.huangge1199.aiagent.controller;

import com.huangge1199.aiagent.common.R;
import com.huangge1199.aiagent.config.MyLoggerAdvisor;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.List;

/**
 * ResControlle
 *
 * @author huangge1199
 * @since 2025/5/17 12:23:36
 */
@RestController
@RequestMapping("/res")
@Tag(name = "ChatClient返回响应")
public class ResController {

    private final ChatClient chatClient;

    public ResController(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    record ActorFilms(String actor, List<String> movies) {
    }

    // AI 调用 MCP 服务

    @Resource
    private ToolCallbackProvider toolCallbackProvider;

    @PostMapping("/chatRes")
    @Operation(summary = "返回 ChatResponse")
    public R<ChatResponse> chatResRes(@RequestBody String question) {
        ChatResponse chatResponse = chatClient.prompt()
                .user(question)
                .call()
                .chatResponse();
        return R.ok(chatResponse);
    }

    @PostMapping("/entity")
    @Operation(summary = "返回实体-单个实体")
    public R<ActorFilms> entityRes(@RequestBody String question) {
        ActorFilms actorFilms = chatClient.prompt()
                .user(question)
                .call()
                .entity(ActorFilms.class);
        return R.ok(actorFilms);
    }

    @PostMapping("/actorFilmsRes")
    @Operation(summary = "返回实体-泛型")
    public R<List<ActorFilms>> actorFilmsRes(@RequestBody String question) {
        List<ActorFilms> actorFilms = chatClient.prompt()
                .user(question)
                .call()
                .entity(new ParameterizedTypeReference<List<ActorFilms>>() {
                });
        return R.ok(actorFilms);
    }


    @PostMapping("/fluxRes")
    @Operation(summary = "流式响应：适用于打字机效果")
    public R<Flux<String>> fluxRes(@RequestBody String question) {
        Flux<String> output = chatClient.prompt()
                .user(question)
                .stream()
                .content();
        return R.ok(output);
    }

    @PostMapping("/fluxChatRes")
    @Operation(summary = "流式响应：返回ChatResponse")
    public R<Flux<ChatResponse>> fluxChatRes(@RequestBody String question) {
        Flux<ChatResponse> output = chatClient.prompt()
                .user(question)
                .stream()
                .chatResponse();
        return R.ok(output);
    }

    @PostMapping("/doChatWithMcp")
    @Operation(summary = "使用mcp返回会话")
    public R<String> doChatWithMcp(@RequestBody String question) {
        ChatResponse chatResponse = chatClient
                .prompt()
                .user(question)
                // 开启日志，便于观察效果
                .advisors(new MyLoggerAdvisor())
                .tools(toolCallbackProvider)
                .call()
                .chatResponse();
        String content = chatResponse.getResult().getOutput().getText();
        return R.ok(content);
    }
}
