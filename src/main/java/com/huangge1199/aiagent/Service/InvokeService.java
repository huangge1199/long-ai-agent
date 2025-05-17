package com.huangge1199.aiagent.Service;

import cn.hutool.json.JSONObject;
import com.alibaba.dashscope.aigc.generation.GenerationResult;
import com.alibaba.dashscope.exception.InputRequiredException;
import com.alibaba.dashscope.exception.NoApiKeyException;

/**
 * InvokeService
 *
 * @author huangge1199
 * @since 2025/5/14 13:27:08
 */
public interface InvokeService {
    JSONObject callWithMessage(String question) throws NoApiKeyException, InputRequiredException;

    JSONObject getMsgByHttp(String question);

    String getMsgBySpringAi(String question);

    String getMsgBySpringAiOllam(String question);

    void moreMessages();
}
