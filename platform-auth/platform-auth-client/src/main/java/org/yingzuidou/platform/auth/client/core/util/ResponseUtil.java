package org.yingzuidou.platform.auth.client.core.util;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 类功能描述
 *
 * @author 鹰嘴豆
 * @date 2019/6/30
 * <p>
 * 时间           作者          版本        描述
 * ====================================================
 */
public class ResponseUtil {

    public static void sendError(HttpServletResponse response, int code, String message) throws IOException {
        response.setStatus(code);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(message);
    }
}
