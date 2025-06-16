package com.huangge1199.aiagent.Service;

import reactor.core.publisher.Flux;

/**
 * AiService
 *
 * @author huangge1199
 * @since 2025/6/10 14:53:53
 */
public interface AiService {

    /**
     * AI 基础对话（支持多轮对话记忆）
     *
     * @param message 传入信息
     * @param chatId 会话ID
     * @return 返回信息
     */
    String doChat(String message, String chatId);

    /**
     * AI 基础对话（支持多轮对话记忆，SSE 流式传输）
     *
     * @param message 传入信息
     * @param chatId 会话ID
     * @return 返回信息
     */
    Flux<String> doChatByStream(String message, String chatId);
}
