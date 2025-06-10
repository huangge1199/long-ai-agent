package com.huangge1199.aiagent.config;

import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatModel;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * ChatModelConfig
 *
 * @author huangge1199
 * @since 2025/5/17 12:48:28
 */
@Configuration
public class ChatModelConfig {

    // 选择DashScope作为默认模型
    @Bean
    @Primary
    public ChatModel primaryChatModel(DashScopeChatModel dashscopeChatModel) {
        return dashscopeChatModel;
    }

//    // 或者选择Ollama：
//     @Bean
//     @Primary
//     public ChatModel primaryChatModel(OllamaChatModel ollamaChatModel) {
//         return ollamaChatModel;
//     }
}
