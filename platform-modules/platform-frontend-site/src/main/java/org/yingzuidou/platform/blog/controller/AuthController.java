package org.yingzuidou.platform.blog.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.yingzuidou.platform.auth.client.core.util.PlatformContext;
import org.yingzuidou.platform.blog.dto.UserDTO;
import org.yingzuidou.platform.blog.service.UserService;
import org.yingzuidou.platform.common.entity.CmsUserEntity;
import org.yingzuidou.platform.common.exception.BusinessException;
import org.yingzuidou.platform.common.utils.CmsBeanUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 类功能描述
 *
 * @author 鹰嘴豆
 * @date 2019/7/23
 * <p>
 * 时间           作者          版本        描述
 * ====================================================
 */
@RestController
@RequestMapping("/third-party")
public class AuthController {

    @Value("github.clientId")
    private String clientId;

    @Value("github.clientSecret")
    private String clientSecret;

    @Value("${github.frontendUrl}")
    private String frontendUrl;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private UserService userService;


    /**
     * <p>使用github第三方登录的方式登录本博客系统，根据github返回的用户信息中的唯一id判断是否已经存在用户，如果不存在则
     * 在cms_user表中创建用户并完成普通用户角色的授权。用户创建好以后将账号密码生成jwt返回给客户端用于后续的鉴权
     *
     * @param code github返回的code，用于换取accessToken  AUTH2鉴权协议
     * @param response 响应实体，用户回写html页面传递生成的jwt
     */
    @GetMapping("/login")
    public void thirdPartyLogin(@RequestParam("code") String code, HttpServletResponse response) {
        String uri = "https://github.com/login/oauth/access_token?client_id={clientId}&client_secret={clientSecret}&code={code}";
        String tokenResult;
        try {
            tokenResult = restTemplate.postForObject(uri, null, String.class,clientId, clientSecret, code);
        } catch (Exception e) {
            throw new BusinessException("连接github服务器失败");
        }
        if (StringUtils.hasText(tokenResult)) {
            String[] values = tokenResult.split("&");
            String accessToken = values[0].split("=")[1];
            uri = "https://api.github.com/user?access_token={accessToken}";
            JSONObject exchange;
            try {
                exchange = restTemplate.getForObject(uri, JSONObject.class, accessToken);
            } catch (Exception e) {
                throw new BusinessException("连接github服务器失败");
            }
            String defaultUserAccount = CmsBeanUtils.objectToString(exchange.get("id"));
            String defaultPassword = CmsBeanUtils.objectToString(exchange.get("id"));
            CmsUserEntity user = userService.generateUser(new UserDTO()
                    .setUserName(CmsBeanUtils.objectToString(exchange.get("name")))
                    .setAvatarUrl(CmsBeanUtils.objectToString(exchange.get("avatar_url")))
                    .setThirdPartyId(CmsBeanUtils.objectToInt(exchange.get("id")))
                    // 默认账号密码为第三方登录传递的唯一标志ID
                    .setUserAccount(defaultUserAccount)
                    .setUserPassword(defaultPassword));
            try {
                response.setContentType("text/html;charset=UTF-8");
                // 返回用户账号和加密密码用于第三方登录
                response.getWriter().write(returnToFrontend(user.getUserAccount(), user.getUserPassword()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String returnToFrontend(String defaultUserAccount, String defaultPassword) {
        String backStr = "ThirdPartyLogin-" + defaultUserAccount + "-" + defaultPassword;
        return "<!doctype html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta name=\"viewport\"\n" +
                "          content=\"width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0\">\n" +
                "    <meta http-equiv=\"X-UA-Compatible\" content=\"ie=edge\">\n" +
                "    <title>oauth github</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "登陆中...\n" +
                "<script>\n" +
                "    window.onload = function () {\n" +
                "        window.opener.postMessage(\"" + backStr + "\", \"" + frontendUrl + "\");\n" +
                "        window.close();\n" +
                "    }\n" +
                "</script>\n" +
                "</body>\n" +
                "</html>";
    }

}
