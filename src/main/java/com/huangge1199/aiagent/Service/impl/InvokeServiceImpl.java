package com.huangge1199.aiagent.Service.impl;

import java.util.Arrays;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatModel;
import com.alibaba.dashscope.aigc.generation.Generation;
import com.alibaba.dashscope.aigc.generation.GenerationParam;
import com.alibaba.dashscope.common.Message;
import com.alibaba.dashscope.common.Role;
import com.alibaba.dashscope.exception.InputRequiredException;
import com.alibaba.dashscope.exception.NoApiKeyException;
import com.huangge1199.aiagent.Service.InvokeService;
import jakarta.annotation.Resource;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * InvokeServiceImpl
 *
 * @author huangge1199
 * @since 2025/5/14 12:27:47
 */
@Service
public class InvokeServiceImpl implements InvokeService {

    @Value("${bailian.API-KEY}")
    private String baiLianKey;

    @Resource
    private DashScopeChatModel dashscopeChatModel;

    @Resource
    private OllamaChatModel ollamaChatModel;

    @Override
    public JSONObject callWithMessage(String question) throws NoApiKeyException, InputRequiredException {
        Generation gen = new Generation();
        Message systemMsg = Message.builder()
                .role(Role.SYSTEM.getValue())
                .content("You are a helpful assistant.")
                .build();
        Message userMsg = Message.builder()
                .role(Role.USER.getValue())
                .content(question)
                .build();
        GenerationParam param = GenerationParam.builder()
                // 若没有配置环境变量，请用百炼API Key将下行替换为：.apiKey("sk-xxx")
                .apiKey(baiLianKey)
                // 此处以qwen-plus为例，可按需更换模型名称。模型列表：https://help.aliyun.com/zh/model-studio/getting-started/models
                .model("qwen-plus")
                .messages(Arrays.asList(systemMsg, userMsg))
                .resultFormat(GenerationParam.ResultFormat.MESSAGE)
                .build();
        return JSONUtil.parseObj(gen.call(param));
    }

    @Override
    public JSONObject getMsgByHttp(String question) {
        // API URL
        String url = "https://dashscope.aliyuncs.com/api/v1/services/aigc/text-generation/generation";

        // 构造请求数据
        JSONObject requestBody = new JSONObject();
        requestBody.put("model", "qwen-plus");

        // 构建 messages 数组
        JSONObject systemMessage = new JSONObject();
        systemMessage.put("role", "system");
        systemMessage.put("content", "You are a helpful assistant.");

        JSONObject userMessage = new JSONObject();
        userMessage.put("role", "user");
        userMessage.put("content", question);

        // 将 messages 数组放入 input 对象
        JSONObject input = new JSONObject();
        input.put("messages", new Object[]{systemMessage, userMessage});
        requestBody.put("input", input);

        // 构建 parameters 对象
        JSONObject parameters = new JSONObject();
        parameters.put("result_format", "message");
        requestBody.put("parameters", parameters);

        // 将请求体转换为字符串
        String jsonData = requestBody.toString();

        // 发送请求
        HttpResponse response = HttpRequest.post(url)
                .header("Authorization", "Bearer " + baiLianKey)
                .header("Content-Type", "application/json")
                .body(jsonData)
                .execute();

        // 获取响应内容
        return JSONUtil.parseObj(response.body());
    }

    @Override
        public String getMsgBySpringAi(String question) {
        AssistantMessage output = dashscopeChatModel.call(new Prompt(question))
                .getResult()
                .getOutput();
        return output.getText();
    }

    @Override
    public String getMsgBySpringAiOllam(String question) {
        AssistantMessage output = ollamaChatModel.call(new Prompt(question))
                .getResult()
                .getOutput();
        return output.getText();
    }
}
