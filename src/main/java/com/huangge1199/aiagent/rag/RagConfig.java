package com.huangge1199.aiagent.rag;

import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * RagConfig
 *
 * @author huangge1199
 * @since 2025/5/24 9:26:40
 */
@Configuration
public class RagConfig {

    @Resource
    private DocumentLoaderUtils documentLoaderUtils;

    @Resource
    private MyTokenTextSplitter myTokenTextSplitter;

    @Resource
    private MyKeywordEnricher myKeywordEnricher;

    @Bean
    ChatClient chatClient(ChatClient.Builder builder) {
        return builder.defaultSystem("你将作为一名恋爱大师，对于用户的问题作出解答")
                .build();
    }
}
