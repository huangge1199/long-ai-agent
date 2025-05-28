package com.huangge1199.aiagent.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 * CheckUtils
 *
 * @author huangge1199
 * @since 2025/5/28 10:12:57
 */
@Component
public class CheckUtils {

    public static void checkEmpty(String str, String name) {
        if (StringUtils.isEmpty(str)) {
            throw new RuntimeException(name + "不能为空！");
        }
    }
}
