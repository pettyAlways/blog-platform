package org.yingzuidou.platform.knowledge.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 类功能描述
 *
 * @author 鹰嘴豆
 * @date 2019/6/5
 * <p>
 * 时间           作者          版本        描述
 * ====================================================
 */
@RestController
public class TestController {

    @GetMapping("/test/add")
    public Map<String, String> add() {
        Map<String, String> params = new HashMap<>();
        params.put("name", "yingzudou");
        return params;
    }
    @GetMapping("/test1/add1")
    public Map<String, String> add1() {
        Map<String, String> params = new HashMap<>();
        params.put("name", "yingzudou1");
        return params;
    }
}
