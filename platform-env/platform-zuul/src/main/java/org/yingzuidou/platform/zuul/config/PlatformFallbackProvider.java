package org.yingzuidou.platform.zuul.config;

import com.netflix.hystrix.exception.HystrixTimeoutException;
import org.apache.http.conn.HttpHostConnectException;
import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketTimeoutException;

/**
 * 类功能描述
 * 客户端请求通过zuul访问其他服务时超时或者其他服务不可用时在这个类中做熔断
 *
 * @author 鹰嘴豆
 * @date 2019/7/1
 * <p>
 * 时间           作者          版本        描述
 * ====================================================
 */
@Component
public class PlatformFallbackProvider implements FallbackProvider {

    private static final String TIMEOUT_FALLBACK = "访问服务超时";

    @Override
    public String getRoute() {
        return "*";
    }

    @Override
    public ClientHttpResponse fallbackResponse(String route, Throwable cause) {
        if (cause instanceof HystrixTimeoutException) {
            return response(HttpStatus.GATEWAY_TIMEOUT, TIMEOUT_FALLBACK);
        } else if (cause instanceof SocketTimeoutException){
            return response(HttpStatus.GATEWAY_TIMEOUT, TIMEOUT_FALLBACK);
        } else if (cause instanceof HttpHostConnectException) {
            return response(HttpStatus.GATEWAY_TIMEOUT, TIMEOUT_FALLBACK);
        } else {
            return response(HttpStatus.INTERNAL_SERVER_ERROR, cause.getMessage());
        }
    }

    private ClientHttpResponse response(final HttpStatus status, String message) {
        return new ClientHttpResponse() {
            @Override
            public HttpStatus getStatusCode() throws IOException {
                return status;
            }

            @Override
            public int getRawStatusCode() throws IOException {
                return status.value();
            }

            @Override
            public String getStatusText() throws IOException {
                return status.getReasonPhrase();
            }

            @Override
            public void close() {
            }

            @Override
            public InputStream getBody() throws IOException {
                return new ByteArrayInputStream(message.getBytes());
            }

            @Override
            public HttpHeaders getHeaders() {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                return headers;
            }
        };
    }
}
