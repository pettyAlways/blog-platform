package org.yingzuidou.platform.zuul.intercept;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

/**
 * 类功能描述
 *
 * @author 鹰嘴豆
 * @date 2019/6/28
 * <p>
 * 时间           作者          版本        描述
 * ====================================================
 */
@Component
public class ServiceAuthIntercept extends ZuulFilter {

    @Value("${auth.token-header}")
    private String zuulHeader;

    @Value("${auth.token-value}")
    private String zuulHeaderPrefix;

    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RequestContext requestContext = RequestContext.getCurrentContext();
        requestContext.addZuulRequestHeader(zuulHeader, zuulHeaderPrefix);
        return null;
    }
}
