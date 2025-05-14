package com.huangge1199.aiagent.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * HealthController
 *
 * @author huangge1199
 * @since 2025/5/12 12:39:40
 */
@RestController
@RequestMapping("/health")
@Tag(name = "程序健康检测")
public class HealthController {

    @GetMapping
    @Operation(summary = "简单返回")
    public String healthCheck() {
        return "OK";
    }
}
