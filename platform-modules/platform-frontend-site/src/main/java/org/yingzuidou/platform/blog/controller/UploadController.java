package org.yingzuidou.platform.blog.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.yingzuidou.platform.auth.client.core.config.WebSecurityConfig;
import org.yingzuidou.platform.common.exception.BusinessException;
import org.yingzuidou.platform.common.utils.FileUtil;
import org.yingzuidou.platform.common.vo.CmsMap;

import java.io.File;
import java.io.IOException;

/**
 * 类功能描述
 * <p>1. 资源上传的处理类，通过{@link #commonUpload}的type参数来区分何种功能的文件上传
 *
 * <p>2. 在配置中心[platform-config.yml]中通过映射磁盘目录来完成浏览器对静态资源的访问,想了解具体映射配置可以参考
 * https://www.cnblogs.com/pomer-huang/archive/2018/09/12/pomer.html 这篇文章
 *
 * <p>3. 外部访问资源的请求肯定是需要经过zuul网关的，所以需要在platform-auth-client模块的{@link WebSecurityConfig}类中
 * 做好静态资源的放行以及忽略jwt的校验，详细请看{@link WebSecurityConfig#configure}以及
 * {@link WebSecurityConfig#authenticationTokenFilter}的过滤
 *
 * @author 鹰嘴豆
 * @date 2019/7/5
 * <p>
 * 时间           作者          版本        描述
 * ====================================================
 */
@RestController
@Slf4j
@RequestMapping("/upload")
public class UploadController {

    @Value("${static.resource.uploadPath}")
    private String uploadPath;

    private static final String STATIC_RESOURCE_HOST = "http://localhost:8086/platform/blog/static/";

        @RequestMapping("/common")
        public CmsMap commonUpload(@RequestParam("file") MultipartFile file,@RequestParam("fileName") String fileName,
                @RequestParam("type") String type) {
            if (file.isEmpty()) {
                throw new BusinessException("文件不能为空");
            }
            String dirPath = type + File.separator + fileName;
            String toPath = uploadPath + File.separator + dirPath;
            FileUtil.createFile(new File(toPath));

            File dest = new File(toPath);
            try {
                file.transferTo(dest);
                log.info("上传成功");
            } catch (IOException e) {
                log.error(e.toString(), e);
            }

        return CmsMap.ok().setResult(STATIC_RESOURCE_HOST + dirPath.replace(File.separator, "/") );
    }
}
