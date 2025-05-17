package com.huangge1199.aiagent.controller;

import com.huangge1199.aiagent.common.R;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping("/entity")
    @Operation(summary = "实体返回")
    public R<ActorFilms> entityRes(@RequestBody String question ) {
        ActorFilms actorFilms = chatClient.prompt()
                .user(question)
                .call()
                .entity(ActorFilms.class);
        return R.ok(actorFilms);
    }
}
