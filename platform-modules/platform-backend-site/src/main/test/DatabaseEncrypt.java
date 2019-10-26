package org.yingzuidou.cms.cmsweb;

import org.jasypt.encryption.StringEncryptor;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 类功能描述
 *
 * @author 鹰嘴豆
 * @date 2019/8/15
 * <p>
 * 时间           作者          版本        描述
 * ====================================================
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class DatabaseEncrypt {
    @Autowired
    StringEncryptor encryptor;

    @Test
    public void getPass() {
        String url = encryptor.encrypt("jdbc:mysql://url:3306/database?useSSL=false");
        String name = encryptor.encrypt("root");
        String password = encryptor.encrypt("password");
        System.out.println("url:[" + url + "]");
        System.out.println("name:[" + name + "]");
        System.out.println("password:[" + password + "]");
        Assert.assertTrue(name.length() > 0);
        Assert.assertTrue(password.length() > 0);
    }
}