/*
 Navicat MySQL Data Transfer

 Source Server         : 118.25.74.179
 Source Server Type    : MySQL
 Source Server Version : 80016
 Source Host           : 118.25.74.179:3306
 Source Schema         : cms_web

 Target Server Type    : MySQL
 Target Server Version : 80016
 File Encoding         : 65001

 Date: 07/08/2019 19:16:22
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for article
-- ----------------------------
DROP TABLE IF EXISTS `article`;
CREATE TABLE `article`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `article_title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '文章标题',
  `author_id` int(11) NULL DEFAULT NULL COMMENT '作者',
  `knowledge_id` int(11) NULL DEFAULT NULL COMMENT '知识库',
  `post_time` datetime(0) NULL DEFAULT NULL COMMENT '发布时间',
  `cover_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '文章封面',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '文章内容',
  `creator` int(11) NOT NULL COMMENT '创建人',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `updator` int(11) NULL DEFAULT NULL COMMENT '更新人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `is_delete` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT 'N' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of article
-- ----------------------------
INSERT INTO `article` VALUES (1, '如何学习英标', 9, 1, '2019-08-03 02:50:50', 'http://47.98.215.5:8086/platform/blog/static/article_cover/1565101603739.jpg', '<p style=\"box-sizing: border-box; outline: 0px; margin: 0px 0px 16px; padding: 0px; font-family: Verdana, Arial, Helvetica, sans-serif; font-size: 14px; color: #333333; line-height: 26px; overflow-x: auto; overflow-wrap: break-word; background-color: #ffffff;\"><span style=\"box-sizing: border-box; outline: 0px; margin: 0px; padding: 0px; font-family: \'Microsoft YaHei\', \'SF Pro Display\', Roboto, Noto, Arial, \'PingFang SC\', sans-serif; overflow-wrap: break-word; color: #ff0000;\"><span style=\"box-sizing: border-box; outline: 0px; margin: 0px; padding: 0px; overflow-wrap: break-word; color: #000000;\">　　使用AddIISUrlRewrite方法应用IIS URL重写规则，请确保规则文件已随应用程序部署至服务器。在Windows Server IIS上运行时，不要让中间件直接使用web.config文件，规格文件应该存储于web.config之外，以避免和IIS重写模块冲突。了解更多关于IIS重写模块的规则，请参考这里和这里。</span></span></p>\n<pre class=\"language-csharp\"><code>public void Configure(IApplicationBuilder app)\n{\n    using (StreamReader apacheModRewriteStreamReader = \n        File.OpenText(\"ApacheModRewrite.txt\"))\n    using (StreamReader iisUrlRewriteStreamReader = \n        File.OpenText(\"IISUrlRewrite.xml\")) \n    {\n        var options = new RewriteOptions()\n            .AddRedirect(\"redirect-rule/(.*)\", \"redirected/$1\")\n            .AddRewrite(@\"^rewrite-rule/(\\d+)/(\\d+)\", \"rewritten?var1=$1&amp;var2=$2\", \n                skipRemainingRules: true)\n            .AddApacheModRewrite(apacheModRewriteStreamReader)\n            .AddIISUrlRewrite(iisUrlRewriteStreamReader)\n            .Add(MethodRules.RedirectXmlFileRequests)\n            .Add(MethodRules.RewriteTextFileRequests)\n            .Add(new RedirectImageRequests(\".png\", \"/png-images\"))\n            .Add(new RedirectImageRequests(\".jpg\", \"/jpg-images\"));\n\n        app.UseRewriter(options);\n    }\n\n    app.UseStaticFiles();\n\n    app.Run(context =&gt; context.Response.WriteAsync(\n        $\"Rewritten or Redirected Url: \" +\n        $\"{context.Request.Path + context.Request.QueryString}\"));\n}</code></pre>\n<p><span style=\"box-sizing: border-box; outline: 0px; margin: 0px; padding: 0px; font-family: \'Microsoft YaHei\', \'SF Pro Display\', Roboto, Noto, Arial, \'PingFang SC\', sans-serif; overflow-wrap: break-word; color: #ff0000;\"><span style=\"box-sizing: border-box; outline: 0px; margin: 0px; padding: 0px; overflow-wrap: break-word; color: #000000;\">&nbsp;</span></span></p>', 9, '2019-08-03 02:50:50', 9, '2019-08-06 14:26:51', 'N');
INSERT INTO `article` VALUES (2, '如何学习英标', 9, 2, '2019-08-03 02:56:59', NULL, '<p style=\"box-sizing: border-box; outline: 0px; margin: 0px 0px 16px; padding: 0px; font-family: Verdana, Arial, Helvetica, sans-serif; font-size: 14px; color: #333333; line-height: 26px; overflow-x: auto; overflow-wrap: break-word; background-color: #ffffff;\"><span style=\"box-sizing: border-box; outline: 0px; margin: 0px; padding: 0px; font-family: \'Microsoft YaHei\', \'SF Pro Display\', Roboto, Noto, Arial, \'PingFang SC\', sans-serif; overflow-wrap: break-word; color: #ff0000;\"><span style=\"box-sizing: border-box; outline: 0px; margin: 0px; padding: 0px; overflow-wrap: break-word; color: #000000;\">　　使用AddIISUrlRewrite方法应用IIS URL重写规则，请确保规则文件已随应用程序部署至服务器。在Windows Server IIS上运行时，不要让中间件直接使用web.config文件，规格文件应该存储于web.config之外，以避免和IIS重写模块冲突。了解更多关于IIS重写模块的规则，请参考这里和这里。</span></span></p>\n<pre class=\"language-csharp\"><code>public void Configure(IApplicationBuilder app)\n{\n    using (StreamReader apacheModRewriteStreamReader = \n        File.OpenText(\"ApacheModRewrite.txt\"))\n    using (StreamReader iisUrlRewriteStreamReader = \n        File.OpenText(\"IISUrlRewrite.xml\")) \n    {\n        var options = new RewriteOptions()\n            .AddRedirect(\"redirect-rule/(.*)\", \"redirected/$1\")\n            .AddRewrite(@\"^rewrite-rule/(\\d+)/(\\d+)\", \"rewritten?var1=$1&amp;var2=$2\", \n                skipRemainingRules: true)\n            .AddApacheModRewrite(apacheModRewriteStreamReader)\n            .AddIISUrlRewrite(iisUrlRewriteStreamReader)\n            .Add(MethodRules.RedirectXmlFileRequests)\n            .Add(MethodRules.RewriteTextFileRequests)\n            .Add(new RedirectImageRequests(\".png\", \"/png-images\"))\n            .Add(new RedirectImageRequests(\".jpg\", \"/jpg-images\"));\n\n        app.UseRewriter(options);\n    }\n\n    app.UseStaticFiles();\n\n    app.Run(context =&gt; context.Response.WriteAsync(\n        $\"Rewritten or Redirected Url: \" +\n        $\"{context.Request.Path + context.Request.QueryString}\"));\n}</code></pre>\n<p><span style=\"box-sizing: border-box; outline: 0px; margin: 0px; padding: 0px; font-family: \'Microsoft YaHei\', \'SF Pro Display\', Roboto, Noto, Arial, \'PingFang SC\', sans-serif; overflow-wrap: break-word; color: #ff0000;\"><span style=\"box-sizing: border-box; outline: 0px; margin: 0px; padding: 0px; overflow-wrap: break-word; color: #000000;\">&nbsp;</span></span></p>', 9, '2019-08-03 02:56:59', 2, '2019-08-03 03:25:38', 'Y');
INSERT INTO `article` VALUES (3, '如何学习英标', 2, 2, '2019-08-03 03:27:03', 'http://localhost:8086/platform/blog/static/article_cover/1564802850111.jpg', '<p style=\"box-sizing: border-box; outline: 0px; margin: 0px 0px 16px; padding: 0px; font-family: Verdana, Arial, Helvetica, sans-serif; font-size: 14px; color: #333333; line-height: 26px; overflow-x: auto; overflow-wrap: break-word; background-color: #ffffff;\"><span style=\"box-sizing: border-box; outline: 0px; margin: 0px; padding: 0px; font-family: \'Microsoft YaHei\', \'SF Pro Display\', Roboto, Noto, Arial, \'PingFang SC\', sans-serif; overflow-wrap: break-word; color: #ff0000;\"><span style=\"box-sizing: border-box; outline: 0px; margin: 0px; padding: 0px; overflow-wrap: break-word; color: #000000;\">　　使用AddIISUrlRewrite方法应用IIS URL重写规则，请确保规则文件已随应用程序部署至服务器。在Windows Server IIS上运行时，不要让中间件直接使用web.config文件，规格文件应该存储于web.config之外，以避免和IIS重写模块冲突。了解更多关于IIS重写模块的规则，请参考这里和这里。</span></span></p>\n<pre class=\"language-csharp\"><code>public void Configure(IApplicationBuilder app)\n{\n    using (StreamReader apacheModRewriteStreamReader = \n        File.OpenText(\"ApacheModRewrite.txt\"))\n    using (StreamReader iisUrlRewriteStreamReader = \n        File.OpenText(\"IISUrlRewrite.xml\")) \n    {\n        var options = new RewriteOptions()\n            .AddRedirect(\"redirect-rule/(.*)\", \"redirected/$1\")\n            .AddRewrite(@\"^rewrite-rule/(\\d+)/(\\d+)\", \"rewritten?var1=$1&amp;var2=$2\", \n                skipRemainingRules: true)\n            .AddApacheModRewrite(apacheModRewriteStreamReader)\n            .AddIISUrlRewrite(iisUrlRewriteStreamReader)\n            .Add(MethodRules.RedirectXmlFileRequests)\n            .Add(MethodRules.RewriteTextFileRequests)\n            .Add(new RedirectImageRequests(\".png\", \"/png-images\"))\n            .Add(new RedirectImageRequests(\".jpg\", \"/jpg-images\"));\n\n        app.UseRewriter(options);\n    }\n\n    app.UseStaticFiles();\n\n    app.Run(context =&gt; context.Response.WriteAsync(\n        $\"Rewritten or Redirected Url: \" +\n        $\"{context.Request.Path + context.Request.QueryString}\"));\n}</code></pre>\n<p><span style=\"box-sizing: border-box; outline: 0px; margin: 0px; padding: 0px; font-family: \'Microsoft YaHei\', \'SF Pro Display\', Roboto, Noto, Arial, \'PingFang SC\', sans-serif; overflow-wrap: break-word; color: #ff0000;\"><span style=\"box-sizing: border-box; outline: 0px; margin: 0px; padding: 0px; overflow-wrap: break-word; color: #000000;\">&nbsp;</span></span></p>', 2, '2019-08-03 03:27:03', 2, '2019-08-03 03:28:16', 'Y');
INSERT INTO `article` VALUES (4, '如何学习英标', 2, 2, '2019-08-03 03:33:44', 'http://192.168.0.100:8086/platform/blog/static/article_cover/1564800572667.png', '<p style=\"box-sizing: border-box; outline: 0px; margin: 0px 0px 16px; padding: 0px; font-family: Verdana, Arial, Helvetica, sans-serif; font-size: 14px; color: #333333; line-height: 26px; overflow-x: auto; overflow-wrap: break-word; background-color: #ffffff;\"><span style=\"box-sizing: border-box; outline: 0px; margin: 0px; padding: 0px; font-family: \'Microsoft YaHei\', \'SF Pro Display\', Roboto, Noto, Arial, \'PingFang SC\', sans-serif; overflow-wrap: break-word; color: #ff0000;\"><span style=\"box-sizing: border-box; outline: 0px; margin: 0px; padding: 0px; overflow-wrap: break-word; color: #000000;\">　　使用AddIISUrlRewrite方法应用IIS URL重写规则，请确保规则文件已随应用程序部署至服务器。在Windows Server IIS上运行时，不要让中间件直接使用web.config文件，规格文件应该存储于web.config之外，以避免和IIS重写模块冲突。了解更多关于IIS重写模块的规则，请参考这里和这里。</span></span></p>\n<pre class=\"language-csharp\"><code>public void Configure(IApplicationBuilder app)\n{\n    using (StreamReader apacheModRewriteStreamReader = \n        File.OpenText(\"ApacheModRewrite.txt\"))\n    using (StreamReader iisUrlRewriteStreamReader = \n        File.OpenText(\"IISUrlRewrite.xml\")) \n    {\n        var options = new RewriteOptions()\n            .AddRedirect(\"redirect-rule/(.*)\", \"redirected/$1\")\n            .AddRewrite(@\"^rewrite-rule/(\\d+)/(\\d+)\", \"rewritten?var1=$1&amp;var2=$2\", \n                skipRemainingRules: true)\n            .AddApacheModRewrite(apacheModRewriteStreamReader)\n            .AddIISUrlRewrite(iisUrlRewriteStreamReader)\n            .Add(MethodRules.RedirectXmlFileRequests)\n            .Add(MethodRules.RewriteTextFileRequests)\n            .Add(new RedirectImageRequests(\".png\", \"/png-images\"))\n            .Add(new RedirectImageRequests(\".jpg\", \"/jpg-images\"));\n\n        app.UseRewriter(options);\n    }\n\n    app.UseStaticFiles();\n\n    app.Run(context =&gt; context.Response.WriteAsync(\n        $\"Rewritten or Redirected Url: \" +\n        $\"{context.Request.Path + context.Request.QueryString}\"));\n}</code></pre>\n<p><span style=\"box-sizing: border-box; outline: 0px; margin: 0px; padding: 0px; font-family: \'Microsoft YaHei\', \'SF Pro Display\', Roboto, Noto, Arial, \'PingFang SC\', sans-serif; overflow-wrap: break-word; color: #ff0000;\"><span style=\"box-sizing: border-box; outline: 0px; margin: 0px; padding: 0px; overflow-wrap: break-word; color: #000000;\">&nbsp;</span></span></p>', 2, '2019-08-03 03:33:44', 2, '2019-08-03 03:33:57', 'Y');
INSERT INTO `article` VALUES (5, '如何学习英标', 9, 2, '2019-08-03 03:34:45', 'http://192.168.0.100:8086/platform/blog/static/article_cover/1564800572667.png', '<p style=\"box-sizing: border-box; outline: 0px; margin: 0px 0px 16px; padding: 0px; font-family: Verdana, Arial, Helvetica, sans-serif; font-size: 14px; color: #333333; line-height: 26px; overflow-x: auto; overflow-wrap: break-word; background-color: #ffffff;\"><span style=\"box-sizing: border-box; outline: 0px; margin: 0px; padding: 0px; font-family: \'Microsoft YaHei\', \'SF Pro Display\', Roboto, Noto, Arial, \'PingFang SC\', sans-serif; overflow-wrap: break-word; color: #ff0000;\"><span style=\"box-sizing: border-box; outline: 0px; margin: 0px; padding: 0px; overflow-wrap: break-word; color: #000000;\">　　使用AddIISUrlRewrite方法应用IIS URL重写规则，请确保规则文件已随应用程序部署至服务器。在Windows Server IIS上运行时，不要让中间件直接使用web.config文件，规格文件应该存储于web.config之外，以避免和IIS重写模块冲突。了解更多关于IIS重写模块的规则，请参考这里和这里。</span></span></p>\n<pre class=\"language-csharp\"><code>public void Configure(IApplicationBuilder app)\n{\n    using (StreamReader apacheModRewriteStreamReader = \n        File.OpenText(\"ApacheModRewrite.txt\"))\n    using (StreamReader iisUrlRewriteStreamReader = \n        File.OpenText(\"IISUrlRewrite.xml\")) \n    {\n        var options = new RewriteOptions()\n            .AddRedirect(\"redirect-rule/(.*)\", \"redirected/$1\")\n            .AddRewrite(@\"^rewrite-rule/(\\d+)/(\\d+)\", \"rewritten?var1=$1&amp;var2=$2\", \n                skipRemainingRules: true)\n            .AddApacheModRewrite(apacheModRewriteStreamReader)\n            .AddIISUrlRewrite(iisUrlRewriteStreamReader)\n            .Add(MethodRules.RedirectXmlFileRequests)\n            .Add(MethodRules.RewriteTextFileRequests)\n            .Add(new RedirectImageRequests(\".png\", \"/png-images\"))\n            .Add(new RedirectImageRequests(\".jpg\", \"/jpg-images\"));\n\n        app.UseRewriter(options);\n    }\n\n    app.UseStaticFiles();\n\n    app.Run(context =&gt; context.Response.WriteAsync(\n        $\"Rewritten or Redirected Url: \" +\n        $\"{context.Request.Path + context.Request.QueryString}\"));\n}</code></pre>\n<p><span style=\"box-sizing: border-box; outline: 0px; margin: 0px; padding: 0px; font-family: \'Microsoft YaHei\', \'SF Pro Display\', Roboto, Noto, Arial, \'PingFang SC\', sans-serif; overflow-wrap: break-word; color: #ff0000;\"><span style=\"box-sizing: border-box; outline: 0px; margin: 0px; padding: 0px; overflow-wrap: break-word; color: #000000;\">&nbsp;</span></span></p>', 9, '2019-08-03 03:34:45', 2, '2019-08-03 03:48:35', 'Y');
INSERT INTO `article` VALUES (6, '论合作的重要性What is Model binding', 2, 1, '2019-08-03 03:36:19', 'http://47.98.215.5:8086/platform/blog/static/article_cover/1565101621770.jpg', '<p><span style=\"font-family: \'Segoe UI\', \'Lucida Grande\', Helvetica, \'Microsoft YaHei\', Arial, sans-serif; font-size: 14px; background-color: #ffffff;\">这样就是一个基础的镜像，但是如果需要基础镜像发挥更多的作用，我们还需要其他的工作。Docker中的容器运行在操作系统中，共享了操作系统的内核。对于在Mac、Windows平台下，则是基于Linux虚拟机的内核。而Linux内核仅提供了进程管理、内存管理、文件系统管理等一些基础的管理模块。除此之外，我们还需要一些Linux下的管理工具，包括</span><code style=\"margin: 1px 5px; line-height: 1.8; vertical-align: middle; display: inline-block; color: #ffffff; font-family: Menlo, Monaco, \'Andale Mono\', \'lucida console\', \'Courier New\', monospace !important; font-size: 12px !important; background-color: #000000 !important; border: 1px solid #cccccc !important; padding: 0px 5px !important; border-radius: 3px !important;\">ls、cp、mv、tar</code><span style=\"font-family: \'Segoe UI\', \'Lucida Grande\', Helvetica, \'Microsoft YaHei\', Arial, sans-serif; font-size: 14px; background-color: #ffffff;\">以及应用程序运行依赖的一些包。因此我们就需要首先构建一个Minimal的操作系统镜像，在此基础上构建Python环境，再构建应用镜像。这样就实现了镜像文件分层，今后如果我们需要更新Python版本，那么只需要对这一层进行更新就可以</span></p>\n<pre class=\"language-java\"><code>@DeleteMapping(\"/share/delete\")\n    public CmsMap&lt;Integer&gt; deleteShareArticle(@RequestParam(\"articleId\") Integer articleId) {\n        ArticleDTO articleDTO = articleService.deleteShareArticle(articleId);\n        return CmsMap.&lt;Integer&gt;ok().setResult(articleDTO.getArticleId());\n    }</code></pre>', 2, '2019-08-03 03:36:19', 9, '2019-08-06 14:27:11', 'N');
INSERT INTO `article` VALUES (7, '如何学习英标', 2, 2, '2019-08-03 03:51:18', 'http://192.168.0.100:8086/platform/blog/static/article_cover/1564800572667.png', '<p style=\"box-sizing: border-box; outline: 0px; margin: 0px 0px 16px; padding: 0px; font-family: Verdana, Arial, Helvetica, sans-serif; font-size: 14px; color: #333333; line-height: 26px; overflow-x: auto; overflow-wrap: break-word; background-color: #ffffff;\"><span style=\"box-sizing: border-box; outline: 0px; margin: 0px; padding: 0px; font-family: \'Microsoft YaHei\', \'SF Pro Display\', Roboto, Noto, Arial, \'PingFang SC\', sans-serif; overflow-wrap: break-word; color: #ff0000;\"><span style=\"box-sizing: border-box; outline: 0px; margin: 0px; padding: 0px; overflow-wrap: break-word; color: #000000;\">　　使用AddIISUrlRewrite方法应用IIS URL重写规则，请确保规则文件已随应用程序部署至服务器。在Windows Server IIS上运行时，不要让中间件直接使用web.config文件，规格文件应该存储于web.config之外，以避免和IIS重写模块冲突。了解更多关于IIS重写模块的规则，请参考这里和这里。</span></span></p>\n<pre class=\"language-csharp\"><code>public void Configure(IApplicationBuilder app)\n{\n    using (StreamReader apacheModRewriteStreamReader = \n        File.OpenText(\"ApacheModRewrite.txt\"))\n    using (StreamReader iisUrlRewriteStreamReader = \n        File.OpenText(\"IISUrlRewrite.xml\")) \n    {\n        var options = new RewriteOptions()\n            .AddRedirect(\"redirect-rule/(.*)\", \"redirected/$1\")\n            .AddRewrite(@\"^rewrite-rule/(\\d+)/(\\d+)\", \"rewritten?var1=$1&amp;var2=$2\", \n                skipRemainingRules: true)\n            .AddApacheModRewrite(apacheModRewriteStreamReader)\n            .AddIISUrlRewrite(iisUrlRewriteStreamReader)\n            .Add(MethodRules.RedirectXmlFileRequests)\n            .Add(MethodRules.RewriteTextFileRequests)\n            .Add(new RedirectImageRequests(\".png\", \"/png-images\"))\n            .Add(new RedirectImageRequests(\".jpg\", \"/jpg-images\"));\n\n        app.UseRewriter(options);\n    }\n\n    app.UseStaticFiles();\n\n    app.Run(context =&gt; context.Response.WriteAsync(\n        $\"Rewritten or Redirected Url: \" +\n        $\"{context.Request.Path + context.Request.QueryString}\"));\n}</code></pre>\n<p><span style=\"box-sizing: border-box; outline: 0px; margin: 0px; padding: 0px; font-family: \'Microsoft YaHei\', \'SF Pro Display\', Roboto, Noto, Arial, \'PingFang SC\', sans-serif; overflow-wrap: break-word; color: #ff0000;\"><span style=\"box-sizing: border-box; outline: 0px; margin: 0px; padding: 0px; overflow-wrap: break-word; color: #000000;\">&nbsp;</span></span></p>', 2, '2019-08-03 03:51:18', 2, '2019-08-03 03:51:37', 'Y');
INSERT INTO `article` VALUES (8, '测试是个好习惯', 9, 2, '2019-08-03 03:52:29', 'http://47.98.215.5:8086/platform/blog/static/article_cover/1565101498371.jpg', '<blockquote>\n<p><span style=\"font-family: \'Segoe UI\', \'Lucida Grande\', Helvetica, \'Microsoft YaHei\', Arial, sans-serif; font-size: 14px; background-color: #ffffff;\">这样就是一个基础的镜像，但是如果需要基础镜像发挥更多的作用，我们还需要其他的工作。Docker中的容器运行在操作系统中，共享了操作系统的内核。对于在Mac、Windows平台下，则是基于Linux虚拟机的内核。而Linux内核仅提供了进程管理、内存管理、文件系统管理等一些基础的管理模块。除此之外，我们还需要一些Linux下的管理工具，包括</span><code style=\"margin: 1px 5px; line-height: 1.8; vertical-align: middle; display: inline-block; color: #ffffff; font-family: Menlo, Monaco, \'Andale Mono\', \'lucida console\', \'Courier New\', monospace !important; font-size: 12px !important; background-color: #000000 !important; border: 1px solid #cccccc !important; padding: 0px 5px !important; border-radius: 3px !important;\">ls、cp、mv、tar</code><span style=\"font-family: \'Segoe UI\', \'Lucida Grande\', Helvetica, \'Microsoft YaHei\', Arial, sans-serif; font-size: 14px; background-color: #ffffff;\">以及应用程序运行依赖的一些包。因此我们就需要首先构建一个Minimal的操作系统镜像，在此基础上构建Python环境，再构建应用镜像。这样就实现了镜像文件分层，今后如果我们需要更新Python版本，那么只需要对这一层进行更新就可以</span></p>\n</blockquote>\n<pre class=\"language-java\"><code>@DeleteMapping(\"/share/delete\")\n    public CmsMap&lt;Integer&gt; deleteShareArticle(@RequestParam(\"articleId\") Integer articleId) {\n        ArticleDTO articleDTO = articleService.deleteShareArticle(articleId);\n        return CmsMap.&lt;Integer&gt;ok().setResult(articleDTO.getArticleId());\n    }</code></pre>\n<p>&nbsp;</p>\n<p>&nbsp;</p>\n<p>增加内容 - fuck杨的希望</p>\n<p>&nbsp;</p>\n<p>再次增加内容</p>\n<p>&nbsp;</p>\n<p>&nbsp;</p>\n<p>())(_(_)!@#$%&amp;***)*(_+)+_?&gt;&lt;&lt;&gt;q312343214123412341234123543dsgfdm.,m.,m.,m.,m</p>\n<p>&lt;html&gt;</p>\n<p>&lt;body&gt;</p>\n<p>&lt;div&gt;</p>\n<p>321412341234</p>\n<p>&lt;/div&gt;</p>\n<p>&lt;/body&gt;</p>\n<p>&lt;/html&gt;</p>', 9, '2019-08-03 03:52:29', 9, '2019-08-06 14:25:20', 'N');
INSERT INTO `article` VALUES (9, '今天是写代码的日子', 2, 2, '2019-08-03 05:02:49', 'http://47.98.215.5:8086/platform/blog/static/article_cover/1565101467750.jpg', '<p>今<span style=\"font-family: 宋体;\">天是个好日子，是个很</span>好的日子，讲一下如何编写好用的代码，这个你知道吗，今今天是个好日子，是个很好的<span style=\"font-family: 仿宋;\">日子，讲一下如何编写好用的代码，这个你知道吗今天是个好日子，是个很好的日子，讲一下如何编写好用的代码，这个你知道吗</span></p>\n<p><span style=\"font-family: 仿宋;\">天是个好日子，是个很好的日子，讲一下如何编写好用的代码，这个你知道吗，今天是个好日子，是个很好的日子，讲一下如何编写好用的代码，这个你知道吗</span></p>\n<p><span style=\"font-family: 仿宋;\">今天是个好日子，是个很好的日子，讲一下如何</span>编写好用的代码，这个你知道吗今天是个好日子，是个很好的日子，讲一下如何编写好用的代码，这个你知道吗</p>\n<p>&nbsp;</p>\n<p><span style=\"font-family: 仿宋;\">今天是个好日子，是个很好的日子，讲一下如何编写好用的代码，这个你知道吗今天是个好日子，是个很好的日子，讲一下如何编写好用的代码，这个你知道吗</span></p>\n<p><span style=\"font-family: 仿宋;\">一点点一点点一点点一点点在</span></p>\n<blockquote>\n<p>试一下就可以了</p>\n</blockquote>\n<p>&nbsp;</p>\n<p>一点点一点点一点点一点点在</p>\n<p>&nbsp;</p>\n<p>一点点一点点一点点一点点在</p>\n<p>一点点一点点一点点一点点在</p>\n<p>&nbsp;</p>\n<p>一点点一点点一点点一点点在</p>\n<p>&nbsp;</p>\n<p>一点点一点点一点点一点点在</p>\n<p>一点点一点点一点点一点点在</p>\n<p>&nbsp;</p>\n<p>一点点一点点一点点一点点在</p>\n<p>&nbsp;</p>\n<p>一点点一点点一点点一点点在</p>\n<p>一点点一点点一点点一点点在</p>\n<p>&nbsp;</p>\n<p>一点点一点点一点点一点点在</p>', 2, '2019-08-03 05:02:49', 9, '2019-08-06 14:24:47', 'N');
INSERT INTO `article` VALUES (10, '鹰嘴豆添加的哦', 2, 3, '2019-08-03 05:22:37', 'http://192.168.0.100:8086/platform/blog/static/article_cover/1564809732626.jpg', '<p>这是鹰嘴豆添加的文章</p>', 2, '2019-08-03 05:22:37', NULL, NULL, 'N');
INSERT INTO `article` VALUES (11, 'What is Model binding', 9, 3, '2019-08-03 05:24:26', 'http://192.168.0.100:8086/platform/blog/static/article_cover/1564809826631.png', '<p>&nbsp;</p>\n<p>&nbsp;</p>\n<ul style=\"box-sizing: inherit; margin: 16px 0px 16px 38px; padding: 0px; list-style: none; font-family: \'Segoe UI\', SegoeUI, \'Segoe WP\', \'Helvetica Neue\', Helvetica, Tahoma, Arial, sans-serif; font-size: 16px; background-color: #ffffff;\">\n<li class=\"x-hidden-focus\" style=\"box-sizing: inherit; margin: 0px; padding: 0px; outline: 0px; list-style: disc outside none;\">Retrieves data from various sources such as route data, form fields, and query strings.</li>\n<li style=\"box-sizing: inherit; margin: 0px; padding: 0px; outline: 0px; list-style: disc outside none;\">Provides the data to controllers and Razor pages in method parameters and public properties.</li>\n<li class=\"x-hidden-focus\" style=\"box-sizing: inherit; margin: 0px; padding: 0px; outline: 0px; list-style: disc outside none;\">Converts string data to .NET types.</li>\n<li class=\"x-hidden-focus\" style=\"box-sizing: inherit; margin: 0px; padding: 0px; outline: 0px; list-style: disc outside none;\">Updates properties of complex types.</li>\n</ul>\n<h2 id=\"example\" class=\"\" style=\"box-sizing: inherit; margin: 32px 0px 12px; padding: 0px; font-size: 1.75rem; line-height: 1.3; font-family: \'Segoe UI\', SegoeUI, \'Segoe WP\', \'Helvetica Neue\', Helvetica, Tahoma, Arial, sans-serif; background-color: #ffffff;\">Example</h2>\n<p style=\"box-sizing: inherit; margin: 1rem 0px 0px; padding: 0px; overflow-wrap: break-word; font-family: \'Segoe UI\', SegoeUI, \'Segoe WP\', \'Helvetica Neue\', Helvetica, Tahoma, Arial, sans-serif; font-size: 16px; background-color: #ffffff;\">Suppose you have the following action method:</p>\n<div id=\"code-try-0\" class=\"codeHeader\" style=\"box-sizing: content-box; display: flex; flex-direction: row; font-size: 0.8rem; border-bottom: 0px; margin-top: 16px; min-height: 30px; font-family: \'Segoe UI\', SegoeUI, \'Segoe WP\', \'Helvetica Neue\', Helvetica, Tahoma, Arial, sans-serif;\" data-bi-name=\"code-header\">&nbsp;</div>\n<p class=\"\" style=\"box-sizing: inherit; margin: 1rem 0px 0px; padding: 0px; overflow-wrap: break-word; color: #000000; font-family: \'Segoe UI\', SegoeUI, \'Segoe WP\', \'Helvetica Neue\', Helvetica, Tahoma, Arial, sans-serif; font-size: 16px; font-style: normal; font-variant-ligatures: normal; font-variant-caps: normal; font-weight: 400; letter-spacing: normal; orphans: 2; text-align: start; text-indent: 0px; text-transform: none; white-space: normal; widows: 2; word-spacing: 0px; -webkit-text-stroke-width: 0px; background-color: #ffffff; text-decoration-style: initial; text-decoration-color: initial;\">Controllers and Razor pages work with data that comes from HTTP requests. For example, route data may provide a record key, and posted form fields may provide values for the properties of the model. Writing code to retrieve each of these values and convert them from strings to .NET types would be tedious and error-prone. Model binding automates this process. The model binding system:</p>\n<p class=\"\" style=\"box-sizing: inherit; margin: 1rem 0px 0px; padding: 0px; overflow-wrap: break-word; color: #000000; font-family: \'Segoe UI\', SegoeUI, \'Segoe WP\', \'Helvetica Neue\', Helvetica, Tahoma, Arial, sans-serif; font-size: 16px; font-style: normal; font-variant-ligatures: normal; font-variant-caps: normal; font-weight: 400; letter-spacing: normal; orphans: 2; text-align: start; text-indent: 0px; text-transform: none; white-space: normal; widows: 2; word-spacing: 0px; -webkit-text-stroke-width: 0px; background-color: #ffffff; text-decoration-style: initial; text-decoration-color: initial;\">&nbsp;</p>\n<p class=\"\" style=\"box-sizing: inherit; margin: 1rem 0px 0px; padding: 0px; overflow-wrap: break-word; color: #000000; font-family: \'Segoe UI\', SegoeUI, \'Segoe WP\', \'Helvetica Neue\', Helvetica, Tahoma, Arial, sans-serif; font-size: 16px; font-style: normal; font-variant-ligatures: normal; font-variant-caps: normal; font-weight: 400; letter-spacing: normal; orphans: 2; text-align: start; text-indent: 0px; text-transform: none; white-space: normal; widows: 2; word-spacing: 0px; -webkit-text-stroke-width: 0px; background-color: #ffffff; text-decoration-style: initial; text-decoration-color: initial;\">&nbsp;</p>', 9, '2019-08-03 05:24:26', NULL, NULL, 'N');
INSERT INTO `article` VALUES (12, '牛逼的文章', 2, 4, '2019-08-03 05:50:52', 'http://192.168.0.100:8086/platform/blog/static/article_cover/1564811435367.jpg', '<p>文本文档</p>', 2, '2019-08-03 05:50:52', NULL, NULL, 'N');
INSERT INTO `article` VALUES (13, '飞起来了', 2, 4, '2019-08-03 05:51:18', 'http://192.168.0.100:8086/platform/blog/static/article_cover/1564811464142.jpg', '<p>飞起来了啊</p>', 2, '2019-08-03 05:51:18', NULL, NULL, 'N');
INSERT INTO `article` VALUES (14, '1111', 9, 1, '2019-08-06 14:31:13', 'http://47.98.215.5:8086/platform/blog/static/article_cover/1565101854812.jpg', '<p>1111111</p>', 9, '2019-08-06 14:31:13', 9, '2019-08-06 14:31:25', 'Y');

-- ----------------------------
-- Table structure for article_participant
-- ----------------------------
DROP TABLE IF EXISTS `article_participant`;
CREATE TABLE `article_participant`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `article_id` int(11) NULL DEFAULT NULL COMMENT '文章ID',
  `user_id` int(11) NULL DEFAULT NULL COMMENT '协作者ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of article_participant
-- ----------------------------
INSERT INTO `article_participant` VALUES (1, 8, 2);
INSERT INTO `article_participant` VALUES (2, 6, 9);
INSERT INTO `article_participant` VALUES (3, 9, 9);

-- ----------------------------
-- Table structure for audit
-- ----------------------------
DROP TABLE IF EXISTS `audit`;
CREATE TABLE `audit`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `apply_user` int(11) NULL DEFAULT NULL COMMENT '申请人',
  `handle_user` int(11) NULL DEFAULT NULL COMMENT '处理人',
  `apply_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '申请类型：1.成为作者,2.加入知识库，',
  `apply_obj` int(11) NULL DEFAULT NULL COMMENT '申请对象ID',
  `handle_result` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '处理结果：0: 处理中，1.通过，2.不通过',
  `handle_time` datetime(0) NULL DEFAULT NULL COMMENT '处理日期',
  `reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '申请理由',
  `apply_time` datetime(0) NULL DEFAULT NULL COMMENT '申请时间',
  `reject_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '拒绝理由',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of audit
-- ----------------------------
INSERT INTO `audit` VALUES (1, 9, 1, '1', NULL, '0', NULL, '　　所以不管从情怀，还是现实出发，.NET Core 的前途都是光明的。其实没有什么是 Java、Python 可以做而 .NET Core 做不到的。如果有，那只有偏见而已。当然说起生态，\n', '2019-08-03 02:39:34', NULL);
INSERT INTO `audit` VALUES (2, 9, 2, '1', NULL, '1', '2019-08-03 02:40:20', '　　所以不管从情怀，还是现实出发，.NET Core 的前途都是光明的。其实没有什么是 Java、Python 可以做而 .NET Core 做不到的。如果有，那只有偏见而已。当然说起生态，\n', '2019-08-03 02:39:34', NULL);
INSERT INTO `audit` VALUES (3, 9, 1, '1', NULL, '0', NULL, '　　所以不管从情怀，还是现实出发，.NET Core 的前途都是光明的。其实没有什么是 Java、Python 可以做而 .NET Core 做不到的。如果有，那只有偏见而已。当然说起生态，\n', '2019-08-03 02:39:34', NULL);
INSERT INTO `audit` VALUES (4, 9, 2, '1', NULL, '0', NULL, '　　所以不管从情怀，还是现实出发，.NET Core 的前途都是光明的。其实没有什么是 Java、Python 可以做而 .NET Core 做不到的。如果有，那只有偏见而已。当然说起生态，\n', '2019-08-03 02:39:34', NULL);
INSERT INTO `audit` VALUES (5, 2, 9, '2', 1, '1', '2019-08-03 02:59:04', '11111111111', '2019-08-03 02:58:26', NULL);
INSERT INTO `audit` VALUES (6, 2, 9, '2', 2, '1', '2019-08-03 03:16:48', '1111111111', '2019-08-03 03:16:31', NULL);
INSERT INTO `audit` VALUES (7, 9, 2, '2', 3, '1', '2019-08-03 04:09:21', 'cccccccccc', '2019-08-03 04:07:49', NULL);
INSERT INTO `audit` VALUES (8, 10, 1, '1', NULL, '0', NULL, '1111111111111111111111111111111111111111111111111111111111111111', '2019-08-06 13:30:27', NULL);
INSERT INTO `audit` VALUES (9, 10, 2, '1', NULL, '0', NULL, '1111111111111111111111111111111111111111111111111111111111111111', '2019-08-06 13:30:27', NULL);
INSERT INTO `audit` VALUES (10, 9, 2, '2', 3, '1', '2019-08-06 14:19:25', '我和感兴趣，请加我把', '2019-08-06 14:18:54', NULL);

-- ----------------------------
-- Table structure for category
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `category_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `in_use` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '1' COMMENT '是否启用 1、是 2、 否',
  `category_desc` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '描述',
  `sort` int(11) NULL DEFAULT NULL COMMENT '排序',
  `is_delete` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT 'N' COMMENT '是否删除 Y:是 N:否',
  `creator` int(11) NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `updator` int(11) NULL DEFAULT NULL COMMENT '修改人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of category
-- ----------------------------
INSERT INTO `category` VALUES (1, '学习', '1', '学习', 1, 'N', 9, '2019-08-03 02:48:25', NULL, NULL);
INSERT INTO `category` VALUES (2, 'springboot', '1', 'springboot', 2, 'N', 2, '2019-08-03 05:49:46', NULL, NULL);

-- ----------------------------
-- Table structure for cms_const
-- ----------------------------
DROP TABLE IF EXISTS `cms_const`;
CREATE TABLE `cms_const`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '常量表ID',
  `type` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '1' COMMENT '1、常量配置，2、枚举配置',
  `const_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'value显示名字',
  `const_key` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '类似于code',
  `const_value` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '值',
  `in_use` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '1' COMMENT '是否启用 1、是 2、 否',
  `creator` int(11) NOT NULL COMMENT '创建人',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `updator` int(11) NULL DEFAULT NULL COMMENT '更新人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cms_const
-- ----------------------------
INSERT INTO `cms_const` VALUES (1, '1', '资源根名称', 'root_resource', '开发者平台', '1', 1, '2019-06-27 09:34:55', NULL, NULL);
INSERT INTO `cms_const` VALUES (2, '1', '网站标题', 'system_title', '开发者平台', '1', 1, '2019-06-27 09:34:55', NULL, NULL);
INSERT INTO `cms_const` VALUES (3, '1', '鉴权方式', 'no_auth_represent', 'no_represent', '1', 0, '2019-06-27 09:34:55', 1, NULL);

-- ----------------------------
-- Table structure for cms_user
-- ----------------------------
DROP TABLE IF EXISTS `cms_user`;
CREATE TABLE `cms_user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户主键ID',
  `user_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户姓名',
  `user_sex` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '0: 未知 1：女， 2：男',
  `user_account` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '账号',
  `user_password` varchar(255) CHARACTER SET utf32 COLLATE utf32_general_ci NULL DEFAULT NULL COMMENT '密码',
  `user_status` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '1' COMMENT '1: 正常， 2、 锁定',
  `user_tel` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户电话',
  `user_mail` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '电子邮件',
  `user_depart` int(11) NULL DEFAULT NULL COMMENT '所属部门',
  `creator` int(11) NOT NULL COMMENT '创建人',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `updator` int(11) NULL DEFAULT NULL COMMENT '更新人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `is_delete` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'N' COMMENT '是否删除',
  `uuid` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '提供一个uuid做加密的盐值',
  `lock_time` datetime(0) NULL DEFAULT NULL COMMENT '锁住时间',
  `login_time` datetime(0) NULL DEFAULT NULL COMMENT '登录时间',
  `third_party_id` int(11) NULL DEFAULT NULL COMMENT '第三方登录ID',
  `is_bind` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'N' COMMENT 'Y: 是， N 否',
  `user_avatar` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户头像',
  `cover_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '封面图片',
  `signature` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '个性签名',
  `introduce` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '自我介绍',
  `work` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '职业',
  `place` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '居住城市',
  `hobby` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '兴趣',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cms_user
-- ----------------------------
INSERT INTO `cms_user` VALUES (1, '超级管理员', '0', 'admin', '5684995729a64824ce148c0fd50c1037', '1', '', '', 1, 0, '2019-06-27 09:34:54', 3, NULL, 'N', '9ddac78c1ddf4301a3bc31d721ebc5a7', NULL, '2019-07-18 05:57:26', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `cms_user` VALUES (2, '鹰嘴豆', '2', 'yingzuidou', 'ef134caf30177d95a43d6f9b7a244614', '1', '18268873650', 'shangguanls1990@gmail.com', 1, 0, '2019-06-27 09:57:39', 2, '2019-08-05 15:55:41', 'N', 'e1ba5e75b0b8488583d65b93c3f01923', NULL, '2019-08-02 15:23:12', NULL, NULL, 'http://47.98.215.5:8086/platform/blog/static/user_portrait/1565020471070.jpg', 'http://47.98.215.5:8086/platform/blog/static/user_profile_cover/1565020537658.jpg', '我还可以写五年的代码', NULL, NULL, NULL, NULL);
INSERT INTO `cms_user` VALUES (9, 'Toby.Yang', '2', '123456', 'bc88ff08000a1e4cfcec6c859ec043f0', '1', NULL, 'test@qq.com', NULL, 0, NULL, 9, '2019-08-06 14:18:25', 'N', 'b6183d0ad11a44ba9ab1a248824e0ecf', NULL, '2019-08-03 02:32:29', 15923358, 'Y', 'http://47.98.215.5:8086/platform/blog/static/user_portrait/1565100929120.jpg', 'http://47.98.215.5:8086/platform/blog/static/user_profile_cover/1565101100936.jpg', '吃饭睡觉写代码就这样的过日子', '一个.net程序员，搬砖搬得很好，对生活充满激情', '程序猿', '杭州', '浙ICP备18029694号-1   鹰嘴豆知识库协同网站');
INSERT INTO `cms_user` VALUES (10, '鹰嘴豆', '0', '13352650', 'f238f9c8a3aa28697ac444683e872a69', '1', NULL, NULL, NULL, 0, NULL, NULL, NULL, 'N', '8b5fbd3d7bc047a38cbebe8d7792dc4b', NULL, '2019-08-06 13:20:37', 13352650, NULL, 'https://avatars2.githubusercontent.com/u/13352650?v=4', NULL, NULL, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `article_id` int(11) NULL DEFAULT NULL COMMENT '文章ID外键',
  `comment_content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '评论内容',
  `comment_user` int(11) NULL DEFAULT NULL COMMENT '评论人',
  `comment_time` datetime(0) NULL DEFAULT NULL COMMENT '评论时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of comment
-- ----------------------------
INSERT INTO `comment` VALUES (1, 10, 'a\nv', 9, '2019-08-03 05:44:32');
INSERT INTO `comment` VALUES (2, 1, 'aaa\naaaaaa\naaa\naaaaaa\naaa\n\naaa\naaa\naaaaaaaaa\naaa\n', 9, '2019-08-03 05:46:59');
INSERT INTO `comment` VALUES (3, 13, '111111', 2, '2019-08-03 06:43:04');
INSERT INTO `comment` VALUES (4, 11, '牛逼', 9, '2019-08-06 14:31:48');

-- ----------------------------
-- Table structure for knowledge
-- ----------------------------
DROP TABLE IF EXISTS `knowledge`;
CREATE TABLE `knowledge`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '知识库ID',
  `k_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '知识库名字',
  `k_desc` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '知识库描述',
  `k_url` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '封面图片地址',
  `k_access` char(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '2' COMMENT '访问权限：1.私有 2.公开 3.加密',
  `k_type` int(11) NULL DEFAULT NULL COMMENT '所属分类',
  `k_reserve_o` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '预留字段',
  `k_reserve_t` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '预留字段',
  `creator` int(11) NOT NULL COMMENT '创建人',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `updator` int(11) NULL DEFAULT NULL COMMENT '更新人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `edit_time` datetime(0) NULL DEFAULT NULL COMMENT '编辑时间：当知识库文章变动时的时间用于知识库页排序',
  `is_delete` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT 'N' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of knowledge
-- ----------------------------
INSERT INTO `knowledge` VALUES (1, 'ABC', '学习英语', 'http://47.98.215.5:8086/platform/blog/static/knowledge_cover/1565101424549.jpg', '2', 1, '', NULL, 9, '2019-08-03 02:49:04', 9, '2019-08-06 14:23:48', '2019-08-06 14:31:25', 'N');
INSERT INTO `knowledge` VALUES (2, 'DEF', '英语备份', 'http://47.98.215.5:8086/platform/blog/static/knowledge_cover/1565101444000.jpg', '2', 1, 'mP9LBQEDZn7NhSC8mTwYuw==', NULL, 9, '2019-08-03 02:56:34', 9, '2019-08-06 14:24:07', '2019-08-06 14:25:20', 'N');
INSERT INTO `knowledge` VALUES (3, '鹰嘴豆知识库', '鹰嘴豆知识库', 'http://47.98.215.5:8086/platform/blog/static/knowledge_cover/1565089266606.jpg', '2', 1, '', NULL, 2, '2019-08-03 04:03:48', 2, '2019-08-06 11:01:10', '2019-08-03 05:24:26', 'N');
INSERT INTO `knowledge` VALUES (4, '加密知识库', '加密知识库', 'http://47.98.215.5:8086/platform/blog/static/knowledge_cover/1565100486077.jpg', '3', 2, 'mP9LBQEDZn7NhSC8mTwYuw==', NULL, 2, '2019-08-03 05:50:20', 2, '2019-08-06 14:08:26', '2019-08-03 05:51:18', 'N');

-- ----------------------------
-- Table structure for message
-- ----------------------------
DROP TABLE IF EXISTS `message`;
CREATE TABLE `message`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `message` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '消息内容',
  `m_read` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Y:已读 N:未读',
  `user_id` int(11) NULL DEFAULT NULL COMMENT '用户ID',
  `reserve` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '预留字段',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `m_type` char(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '1.文章修改，2.文章删除，3.知识库修改，4.知识库删除，5.分类修改，6.分类删除，7.知识库加入，8.申请作者，9.移除参与者，10.作者申请结果，11.知识库加入结果',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 25 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of message
-- ----------------------------
INSERT INTO `message` VALUES (1, 'Toby.Yang申请成为作者', 'N', 1, NULL, '2019-08-03 02:39:34', '8');
INSERT INTO `message` VALUES (2, 'Toby.Yang申请成为作者', 'Y', 2, NULL, '2019-08-03 02:39:34', '8');
INSERT INTO `message` VALUES (3, 'Toby.Yang申请成为作者', 'N', 1, NULL, '2019-08-03 02:39:34', '8');
INSERT INTO `message` VALUES (4, 'Toby.Yang申请成为作者', 'Y', 2, NULL, '2019-08-03 02:39:35', '8');
INSERT INTO `message` VALUES (5, '恭喜您成为了本站第2个作者，管理员寄语：加油吧少年，一起构建完善的知识体系', 'Y', 9, NULL, '2019-08-03 02:40:23', '11');
INSERT INTO `message` VALUES (6, '鹰嘴豆申请加入知识库[ABC]', 'Y', 9, NULL, '2019-08-03 02:58:26', '7');
INSERT INTO `message` VALUES (7, '恭喜！Toby.Yang通过了您加入知识库[ABC]的申请，Toby.Yang寄语：一起努力吧，少年', 'N', 2, NULL, '2019-08-03 02:59:05', '11');
INSERT INTO `message` VALUES (8, '鹰嘴豆申请加入知识库[DEF]', 'Y', 9, NULL, '2019-08-03 03:16:31', '7');
INSERT INTO `message` VALUES (9, '恭喜！Toby.Yang通过了您加入知识库[DEF]的申请，Toby.Yang寄语：一起努力吧，少年', 'Y', 2, NULL, '2019-08-03 03:16:49', '11');
INSERT INTO `message` VALUES (11, '鹰嘴豆在知识库[DEF]下删除了您的文章《如何学习英标》,请尽快核实内容', 'Y', 9, '{\"knowledgeId\":2}', '2019-08-03 03:25:38', '2');
INSERT INTO `message` VALUES (12, '鹰嘴豆在知识库[DEF]下删除了您的文章《如何学习英标》,请尽快核实内容', 'Y', 9, '{\"knowledgeId\":2}', '2019-08-03 03:48:35', '2');
INSERT INTO `message` VALUES (13, '鹰嘴豆在知识库[DEF]下修改了您的文章《论合作的重要性》,请尽快核实内容', 'Y', 9, '{\"articleId\":8,\"knowledgeId\":2}', '2019-08-03 03:53:57', '1');
INSERT INTO `message` VALUES (14, 'Toby.Yang申请加入知识库[鹰嘴豆知识库]', 'Y', 2, NULL, '2019-08-03 04:07:49', '7');
INSERT INTO `message` VALUES (15, '恭喜！鹰嘴豆通过了您加入知识库[鹰嘴豆知识库]的申请，鹰嘴豆寄语：一起努力吧，少年', 'Y', 9, NULL, '2019-08-03 04:09:21', '11');
INSERT INTO `message` VALUES (16, '鹰嘴豆在知识库[DEF]下修改了您的文章《论合作的重要性》,请尽快核实内容', 'Y', 9, '{\"articleId\":8,\"knowledgeId\":2}', '2019-08-03 04:34:05', '1');
INSERT INTO `message` VALUES (17, '鹰嘴豆在知识库[DEF]下修改了您的文章《1111111111111111111111111111111111111》,请尽快核实内容', 'Y', 9, '{\"articleId\":8,\"knowledgeId\":2}', '2019-08-03 04:53:29', '1');
INSERT INTO `message` VALUES (18, 'Toby.Yang在知识库[ABC]下修改了您的文章《论合作的重要性》,请尽快核实内容', 'Y', 2, '{\"articleId\":6,\"knowledgeId\":1}', '2019-08-03 05:19:54', '1');
INSERT INTO `message` VALUES (19, '您已经被Toby.Yang从知识库[ABC]中开除啦', 'Y', 2, NULL, '2019-08-03 05:26:04', '9');
INSERT INTO `message` VALUES (20, '您已经被鹰嘴豆从知识库[鹰嘴豆知识库]中开除啦', 'Y', 9, NULL, '2019-08-03 05:26:37', '9');
INSERT INTO `message` VALUES (21, '鹰嘴豆在知识库[DEF]下修改了您的文章《())(_(_)!@#$%&***)*(_+)+_?><<>》,请尽快核实内容', 'Y', 9, '{\"articleId\":8,\"knowledgeId\":2}', '2019-08-03 05:29:07', '1');
INSERT INTO `message` VALUES (22, '您已经被鹰嘴豆从知识库[DEF]中开除啦', 'Y', 2, NULL, '2019-08-05 15:21:20', '9');
INSERT INTO `message` VALUES (23, '鹰嘴豆申请成为作者', 'N', 1, NULL, '2019-08-06 13:30:27', '8');
INSERT INTO `message` VALUES (24, '鹰嘴豆申请成为作者', 'Y', 2, NULL, '2019-08-06 13:30:27', '8');
INSERT INTO `message` VALUES (25, 'Toby.Yang申请加入知识库[鹰嘴豆知识库]', 'Y', 2, NULL, '2019-08-06 14:18:54', '7');
INSERT INTO `message` VALUES (26, '恭喜！鹰嘴豆通过了您加入知识库[鹰嘴豆知识库]的申请，鹰嘴豆寄语：一起努力吧，少年', 'N', 9, NULL, '2019-08-06 14:19:25', '11');
INSERT INTO `message` VALUES (27, 'Toby.Yang在知识库[DEF]下修改了您的文章《三十而立的日子》,请尽快核实内容', 'Y', 2, '{\"articleId\":9,\"knowledgeId\":2}', '2019-08-06 14:24:47', '1');
INSERT INTO `message` VALUES (28, 'Toby.Yang在知识库[ABC]下修改了您的文章《论合作的重要性What is Model binding》,请尽快核实内容', 'N', 2, '{\"articleId\":6,\"knowledgeId\":1}', '2019-08-06 14:27:11', '1');

-- ----------------------------
-- Table structure for oper_record
-- ----------------------------
DROP TABLE IF EXISTS `oper_record`;
CREATE TABLE `oper_record`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `oper_user` int(11) NULL DEFAULT NULL COMMENT '操作人',
  `oper_time` datetime(0) NULL DEFAULT NULL COMMENT '操作时间',
  `oper_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作类型：1.新增，2.删除，3.修改，4.审核，5.移除，6.申请',
  `handle_user` int(11) NULL DEFAULT NULL COMMENT '处理人（操作类型为申请时需要提交的处理人）',
  `handle_result` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '处理结果：1.通过，2.不通过',
  `obj_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '对象类型：1.知识库，2.文章，3.分类，4.用户',
  `obj` int(255) NULL DEFAULT NULL COMMENT '操作对象ID',
  `root_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '根类型：1.知识库, 2.分类',
  `root_obj` int(1) NULL DEFAULT NULL COMMENT '根对象ID',
  `reserve` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '预留字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 44 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of oper_record
-- ----------------------------
INSERT INTO `oper_record` VALUES (1, 9, '2019-08-03 02:48:25', '1', NULL, NULL, '3', 1, '2', NULL, '');
INSERT INTO `oper_record` VALUES (2, 9, '2019-08-03 02:49:04', '1', NULL, NULL, '1', 1, '1', 1, NULL);
INSERT INTO `oper_record` VALUES (3, 9, '2019-08-03 02:50:50', '1', NULL, NULL, '2', 1, '1', 1, NULL);
INSERT INTO `oper_record` VALUES (4, 9, '2019-08-03 02:56:34', '1', NULL, NULL, '1', 2, '1', 2, NULL);
INSERT INTO `oper_record` VALUES (5, 9, '2019-08-03 02:56:59', '1', NULL, NULL, '2', 2, '1', 2, NULL);
INSERT INTO `oper_record` VALUES (6, 2, '2019-08-03 03:25:39', '2', NULL, NULL, '2', 2, '1', 2, '如何学习英标');
INSERT INTO `oper_record` VALUES (7, 2, '2019-08-03 03:27:03', '1', NULL, NULL, '2', 3, '1', 2, NULL);
INSERT INTO `oper_record` VALUES (8, 2, '2019-08-03 03:27:47', '3', NULL, NULL, '2', 3, '1', 2, NULL);
INSERT INTO `oper_record` VALUES (9, 2, '2019-08-03 03:28:17', '2', NULL, NULL, '2', 3, '1', 2, '如何学习英标');
INSERT INTO `oper_record` VALUES (10, 2, '2019-08-03 03:33:44', '1', NULL, NULL, '2', 4, '1', 2, NULL);
INSERT INTO `oper_record` VALUES (11, 2, '2019-08-03 03:33:58', '2', NULL, NULL, '2', 4, '1', 2, '如何学习英标');
INSERT INTO `oper_record` VALUES (12, 9, '2019-08-03 03:34:45', '1', NULL, NULL, '2', 5, '1', 2, NULL);
INSERT INTO `oper_record` VALUES (13, 2, '2019-08-03 03:36:19', '1', NULL, NULL, '2', 6, '1', 1, NULL);
INSERT INTO `oper_record` VALUES (14, 2, '2019-08-03 03:48:36', '2', NULL, NULL, '2', 5, '1', 2, '如何学习英标');
INSERT INTO `oper_record` VALUES (15, 2, '2019-08-03 03:51:18', '1', NULL, NULL, '2', 7, '1', 2, NULL);
INSERT INTO `oper_record` VALUES (16, 2, '2019-08-03 03:51:39', '2', NULL, NULL, '2', 7, '1', 2, '如何学习英标');
INSERT INTO `oper_record` VALUES (17, 9, '2019-08-03 03:52:29', '1', NULL, NULL, '2', 8, '1', 2, NULL);
INSERT INTO `oper_record` VALUES (18, 9, '2019-08-03 03:53:10', '3', NULL, NULL, '2', 8, '1', 2, NULL);
INSERT INTO `oper_record` VALUES (19, 2, '2019-08-03 03:53:58', '3', NULL, NULL, '2', 8, '1', 2, NULL);
INSERT INTO `oper_record` VALUES (20, 9, '2019-08-03 03:54:42', '3', NULL, NULL, '2', 8, '1', 2, NULL);
INSERT INTO `oper_record` VALUES (21, 2, '2019-08-03 04:03:48', '1', NULL, NULL, '1', 3, '1', 3, NULL);
INSERT INTO `oper_record` VALUES (22, 2, '2019-08-03 04:06:57', '3', NULL, NULL, '1', 3, '1', 3, NULL);
INSERT INTO `oper_record` VALUES (23, 2, '2019-08-03 04:34:05', '3', NULL, NULL, '2', 8, '1', 2, NULL);
INSERT INTO `oper_record` VALUES (24, 2, '2019-08-03 04:53:29', '3', NULL, NULL, '2', 8, '1', 2, NULL);
INSERT INTO `oper_record` VALUES (25, 2, '2019-08-03 05:02:50', '1', NULL, NULL, '2', 9, '1', 2, NULL);
INSERT INTO `oper_record` VALUES (26, 2, '2019-08-03 05:04:11', '3', NULL, NULL, '2', 9, '1', 2, NULL);
INSERT INTO `oper_record` VALUES (27, 2, '2019-08-03 05:04:31', '3', NULL, NULL, '2', 9, '1', 2, NULL);
INSERT INTO `oper_record` VALUES (28, 9, '2019-08-03 05:19:54', '3', NULL, NULL, '2', 6, '1', 1, NULL);
INSERT INTO `oper_record` VALUES (29, 9, '2019-08-03 05:22:29', '3', NULL, NULL, '2', 8, '1', 2, NULL);
INSERT INTO `oper_record` VALUES (30, 2, '2019-08-03 05:22:37', '1', NULL, NULL, '2', 10, '1', 3, NULL);
INSERT INTO `oper_record` VALUES (31, 9, '2019-08-03 05:23:17', '3', NULL, NULL, '2', 8, '1', 2, NULL);
INSERT INTO `oper_record` VALUES (32, 9, '2019-08-03 05:24:26', '1', NULL, NULL, '2', 11, '1', 3, NULL);
INSERT INTO `oper_record` VALUES (33, 9, '2019-08-03 05:26:04', '5', NULL, NULL, '4', 2, '1', 1, NULL);
INSERT INTO `oper_record` VALUES (34, 2, '2019-08-03 05:26:38', '5', NULL, NULL, '4', 9, '1', 3, NULL);
INSERT INTO `oper_record` VALUES (35, 2, '2019-08-03 05:29:07', '3', NULL, NULL, '2', 8, '1', 2, NULL);
INSERT INTO `oper_record` VALUES (36, 2, '2019-08-03 05:49:46', '1', NULL, NULL, '3', 2, '2', NULL, '');
INSERT INTO `oper_record` VALUES (37, 2, '2019-08-03 05:50:20', '1', NULL, NULL, '1', 4, '1', 4, NULL);
INSERT INTO `oper_record` VALUES (38, 2, '2019-08-03 05:50:52', '1', NULL, NULL, '2', 12, '1', 4, NULL);
INSERT INTO `oper_record` VALUES (39, 2, '2019-08-03 05:51:18', '1', NULL, NULL, '2', 13, '1', 4, NULL);
INSERT INTO `oper_record` VALUES (40, 2, '2019-08-05 15:21:20', '5', NULL, NULL, '4', 2, '1', 2, NULL);
INSERT INTO `oper_record` VALUES (41, 2, '2019-08-06 11:01:10', '3', NULL, NULL, '1', 3, '1', 3, NULL);
INSERT INTO `oper_record` VALUES (42, 2, '2019-08-06 14:08:11', '3', NULL, NULL, '1', 4, '1', 4, NULL);
INSERT INTO `oper_record` VALUES (43, 2, '2019-08-06 14:08:26', '3', NULL, NULL, '1', 4, '1', 4, NULL);
INSERT INTO `oper_record` VALUES (44, 9, '2019-08-06 14:23:48', '3', NULL, NULL, '1', 1, '1', 1, NULL);
INSERT INTO `oper_record` VALUES (45, 9, '2019-08-06 14:24:07', '3', NULL, NULL, '1', 2, '1', 2, NULL);
INSERT INTO `oper_record` VALUES (46, 9, '2019-08-06 14:24:47', '3', NULL, NULL, '2', 9, '1', 2, NULL);
INSERT INTO `oper_record` VALUES (47, 9, '2019-08-06 14:25:20', '3', NULL, NULL, '2', 8, '1', 2, NULL);
INSERT INTO `oper_record` VALUES (48, 9, '2019-08-06 14:26:51', '3', NULL, NULL, '2', 1, '1', 1, NULL);
INSERT INTO `oper_record` VALUES (49, 9, '2019-08-06 14:27:11', '3', NULL, NULL, '2', 6, '1', 1, NULL);
INSERT INTO `oper_record` VALUES (50, 9, '2019-08-06 14:31:13', '1', NULL, NULL, '2', 14, '1', 1, NULL);
INSERT INTO `oper_record` VALUES (51, 9, '2019-08-06 14:31:26', '2', NULL, NULL, '2', 14, '1', 1, '1111');

-- ----------------------------
-- Table structure for organization
-- ----------------------------
DROP TABLE IF EXISTS `organization`;
CREATE TABLE `organization`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `parent_id` int(11) NULL DEFAULT 0 COMMENT '父组织名字',
  `org_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '组织名字',
  `expand` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '1、Y 展开 2、N 不展开',
  `creator` int(11) NOT NULL COMMENT '创建人',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `updator` int(11) NULL DEFAULT NULL COMMENT '修改人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `is_delete` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'N' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of organization
-- ----------------------------
INSERT INTO `organization` VALUES (1, 0, '鹰嘴豆总部', 'Y', 1, '2019-06-27 09:34:54', NULL, NULL, 'N');

-- ----------------------------
-- Table structure for participant
-- ----------------------------
DROP TABLE IF EXISTS `participant`;
CREATE TABLE `participant`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `knowledge_id` int(11) NULL DEFAULT NULL COMMENT '知识库ID',
  `participant_id` int(11) NULL DEFAULT NULL COMMENT '参与者ID',
  `creator` int(11) NOT NULL COMMENT '创建人',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `updator` int(11) NULL DEFAULT NULL COMMENT '更新人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of participant
-- ----------------------------
INSERT INTO `participant` VALUES (4, 3, 9, 2, '2019-08-06 14:19:25', NULL, NULL);

-- ----------------------------
-- Table structure for recent_edit
-- ----------------------------
DROP TABLE IF EXISTS `recent_edit`;
CREATE TABLE `recent_edit`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_id` int(11) NULL DEFAULT NULL COMMENT '操作人',
  `article_id` int(11) NULL DEFAULT NULL COMMENT '文章ID',
  `knowledge_id` int(11) NULL DEFAULT NULL COMMENT '知识库ID',
  `edit_time` datetime(0) NULL DEFAULT NULL COMMENT '编辑时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 26 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of recent_edit
-- ----------------------------
INSERT INTO `recent_edit` VALUES (1, 9, 1, 1, '2019-08-03 02:50:50');
INSERT INTO `recent_edit` VALUES (7, 2, 6, 1, '2019-08-03 03:36:19');
INSERT INTO `recent_edit` VALUES (9, 9, 8, 2, '2019-08-03 03:52:29');
INSERT INTO `recent_edit` VALUES (10, 9, 8, 2, '2019-08-03 03:53:10');
INSERT INTO `recent_edit` VALUES (11, 2, 8, 2, '2019-08-03 03:53:58');
INSERT INTO `recent_edit` VALUES (12, 9, 8, 2, '2019-08-03 03:54:42');
INSERT INTO `recent_edit` VALUES (13, 2, 8, 2, '2019-08-03 04:34:05');
INSERT INTO `recent_edit` VALUES (14, 2, 8, 2, '2019-08-03 04:53:29');
INSERT INTO `recent_edit` VALUES (15, 2, 9, 2, '2019-08-03 05:02:50');
INSERT INTO `recent_edit` VALUES (16, 2, 9, 2, '2019-08-03 05:04:11');
INSERT INTO `recent_edit` VALUES (17, 2, 9, 2, '2019-08-03 05:04:31');
INSERT INTO `recent_edit` VALUES (18, 9, 6, 1, '2019-08-03 05:19:54');
INSERT INTO `recent_edit` VALUES (19, 9, 8, 2, '2019-08-03 05:22:29');
INSERT INTO `recent_edit` VALUES (20, 2, 10, 3, '2019-08-03 05:22:37');
INSERT INTO `recent_edit` VALUES (21, 9, 8, 2, '2019-08-03 05:23:17');
INSERT INTO `recent_edit` VALUES (22, 9, 11, 3, '2019-08-03 05:24:26');
INSERT INTO `recent_edit` VALUES (23, 2, 8, 2, '2019-08-03 05:29:07');
INSERT INTO `recent_edit` VALUES (24, 2, 12, 4, '2019-08-03 05:50:52');
INSERT INTO `recent_edit` VALUES (25, 2, 13, 4, '2019-08-03 05:51:18');
INSERT INTO `recent_edit` VALUES (26, 9, 9, 2, '2019-08-06 14:24:47');
INSERT INTO `recent_edit` VALUES (27, 9, 8, 2, '2019-08-06 14:25:20');
INSERT INTO `recent_edit` VALUES (28, 9, 1, 1, '2019-08-06 14:26:51');
INSERT INTO `recent_edit` VALUES (29, 9, 6, 1, '2019-08-06 14:27:11');

-- ----------------------------
-- Table structure for reply
-- ----------------------------
DROP TABLE IF EXISTS `reply`;
CREATE TABLE `reply`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `comment_id` int(11) NULL DEFAULT NULL COMMENT '评论ID',
  `reply_user` int(11) NULL DEFAULT NULL COMMENT '回复人',
  `reply_content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '回复内容',
  `reply_time` datetime(0) NULL DEFAULT NULL COMMENT '回复时间',
  `reply_obj` int(11) NULL DEFAULT NULL COMMENT '回复对象',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of reply
-- ----------------------------
INSERT INTO `reply` VALUES (1, 1, 9, 'bbbb', '2019-08-03 05:44:44', NULL);
INSERT INTO `reply` VALUES (2, 3, 2, '111111111111111', '2019-08-03 06:43:26', NULL);
INSERT INTO `reply` VALUES (3, 3, 2, '111111111111', '2019-08-03 06:43:35', 2);
INSERT INTO `reply` VALUES (4, 3, 2, '11111111', '2019-08-03 06:43:45', 2);
INSERT INTO `reply` VALUES (5, 4, 9, '自己回复一下', '2019-08-06 14:31:59', NULL);
INSERT INTO `reply` VALUES (6, 4, 9, '再回复', '2019-08-06 14:32:13', 9);

-- ----------------------------
-- Table structure for resource
-- ----------------------------
DROP TABLE IF EXISTS `resource`;
CREATE TABLE `resource`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '资源ID',
  `resource_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '资源名字',
  `parent_id` int(11) NULL DEFAULT NULL COMMENT '上级资源ID',
  `resource_type` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '1' COMMENT 'module：模块，menu：菜单 、button：按钮、page:页面',
  `in_use` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '1' COMMENT '是否启用 1、是 2、 否',
  `resource_path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '资源路径',
  `resource_icon` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '资源图标',
  `resource_sort` int(11) NULL DEFAULT NULL COMMENT '排序号',
  `creator` int(11) NOT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `updator` int(11) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `is_delete` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'N',
  `alias` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '导航菜单配置标志别名',
  `default_page` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'N' COMMENT '默认页面(是：Y， 否：N)',
  `belongs` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'internal' COMMENT 'external:外部，internal：内部',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 98 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of resource
-- ----------------------------
INSERT INTO `resource` VALUES (1, '系统管理', -1, 'module', '1', '', 'sys-manage', 1, 0, '2019-06-27 09:34:54', 1, '2019-06-27 09:49:45', 'N', 'SysConfigure', 'N', 'internal');
INSERT INTO `resource` VALUES (2, '系统配置', 1, 'menu', '1', '', 'organization', 2, 0, '2019-06-27 09:34:54', 1, NULL, 'N', NULL, 'N', 'internal');
INSERT INTO `resource` VALUES (3, '资源管理', 2, 'page', '1', '/sys-config/resource-manage', 'language', 1, 0, '2019-06-27 09:34:54', 1, NULL, 'N', NULL, 'N', 'internal');
INSERT INTO `resource` VALUES (4, '组织管理', 2, 'page', '1', '/sys-config/organization-manage', 'table-manage', 3, 0, '2019-06-27 09:34:54', 1, NULL, 'N', NULL, 'N', 'internal');
INSERT INTO `resource` VALUES (5, '角色管理', 2, 'page', '1', '/sys-config/role-manage', 'table-manage', 3, 0, '2019-06-27 09:34:54', 1, NULL, 'N', NULL, 'N', 'internal');
INSERT INTO `resource` VALUES (6, '用户管理', 2, 'page', '1', '/sys-config/user-manage', 'function-manage', 3, 0, '2019-06-27 09:34:54', 1, NULL, 'N', NULL, 'N', 'internal');
INSERT INTO `resource` VALUES (7, '资源新增', 3, 'button', '1', '/permission/savePower.do', NULL, 1, 0, '2019-06-27 09:34:54', 1, NULL, 'N', NULL, 'N', 'internal');
INSERT INTO `resource` VALUES (8, '资源更新', 3, 'button', '1', '/permission/updatePower.do', NULL, 1, 0, '2019-06-27 09:34:54', 1, NULL, 'N', NULL, 'N', 'internal');
INSERT INTO `resource` VALUES (9, '资源查询', 3, 'button', '1', '/permission/listPower.do', NULL, 3, 1, '2019-06-27 09:34:54', NULL, NULL, 'N', NULL, 'N', 'internal');
INSERT INTO `resource` VALUES (10, '资源删除', 3, 'button', '1', '/permission/deletePower.do', '', 4, 0, '2019-06-27 09:34:54', 8, NULL, 'N', NULL, 'N', 'internal');
INSERT INTO `resource` VALUES (14, '配置管理', 2, 'page', '1', '/sys-config/conf-manage', 'sys-manage', 5, 0, '2019-06-27 09:34:54', 1, NULL, 'N', NULL, 'N', 'internal');
INSERT INTO `resource` VALUES (15, '常量新增', 14, 'button', '1', '/constant/save.do', '', 1, 0, '2019-06-27 09:34:54', 1, NULL, 'N', NULL, 'N', 'internal');
INSERT INTO `resource` VALUES (16, '常量更新', 14, 'button', '1', '/constant/edit.do', '', 1, 0, '2019-06-27 09:34:54', 1, NULL, 'N', NULL, 'N', 'internal');
INSERT INTO `resource` VALUES (17, '常量查询', 14, 'button', '1', '/constant/list.do', '', 3, 0, '2019-06-27 09:34:55', 1, NULL, 'N', NULL, 'N', 'internal');
INSERT INTO `resource` VALUES (18, '常量删除', 14, 'button', '1', '/constant/delete.do', '', 3, 0, '2019-06-27 09:34:55', 1, NULL, 'N', NULL, 'N', 'internal');
INSERT INTO `resource` VALUES (19, '组织新增', 4, 'button', '1', '/organization/save.do', '', 1, 8, '2019-06-27 09:34:55', NULL, NULL, 'N', NULL, 'N', 'internal');
INSERT INTO `resource` VALUES (20, '组织更新', 4, 'button', '1', '/organization/edit.do', '', 2, 8, '2019-06-27 09:34:55', NULL, NULL, 'N', NULL, 'N', 'internal');
INSERT INTO `resource` VALUES (21, '组织删除', 4, 'button', '1', '/organization/delete.do', '', 2, 8, '2019-06-27 09:34:55', NULL, NULL, 'N', NULL, 'N', 'internal');
INSERT INTO `resource` VALUES (22, '组织查询', 4, 'button', '1', '/organization/list.do', '', 2, 8, '2019-06-27 09:34:55', NULL, NULL, 'N', NULL, 'N', 'internal');
INSERT INTO `resource` VALUES (23, '角色新增', 5, 'button', '1', '/role/save.do', '', 1, 8, '2019-06-27 09:34:55', NULL, NULL, 'N', NULL, 'N', 'internal');
INSERT INTO `resource` VALUES (24, '角色更新', 5, 'button', '1', '/role/edit.do', '', 2, 8, '2019-06-27 09:34:55', NULL, NULL, 'N', NULL, 'N', 'internal');
INSERT INTO `resource` VALUES (25, '角色删除', 5, 'button', '1', '/role/delete.do', '', 2, 8, '2019-06-27 09:34:55', NULL, NULL, 'N', NULL, 'N', 'internal');
INSERT INTO `resource` VALUES (26, '角色授权', 5, 'button', '1', '/role/resourceAuth.do', '', 2, 0, '2019-06-27 09:34:55', 8, NULL, 'N', NULL, 'N', 'internal');
INSERT INTO `resource` VALUES (27, '角色查询', 5, 'button', '1', '/role/list.do', '', 5, 0, '2019-06-27 09:34:55', 8, NULL, 'N', NULL, 'N', 'internal');
INSERT INTO `resource` VALUES (28, '用户新增', 6, 'button', '1', '/user/save.do', '', 1, 8, '2019-06-27 09:34:55', NULL, NULL, 'N', NULL, 'N', 'internal');
INSERT INTO `resource` VALUES (29, '用户更新', 6, 'button', '1', '/user/edit.do', '', 1, 8, '2019-06-27 09:34:55', NULL, NULL, 'N', NULL, 'N', 'internal');
INSERT INTO `resource` VALUES (30, '用户赋角', 6, 'button', '1', '/user/authUser.do', '', 1, 8, '2019-06-27 09:34:55', NULL, NULL, 'N', NULL, 'N', 'internal');
INSERT INTO `resource` VALUES (31, '用户删除', 6, 'button', '1', '/user/authUser.do', '', 1, 0, '2019-06-27 09:34:55', 8, NULL, 'N', NULL, 'N', 'internal');
INSERT INTO `resource` VALUES (32, '用户查询', 6, 'button', '1', '/user/list.do', '', 1, 8, '2019-06-27 09:34:55', NULL, NULL, 'N', NULL, 'N', 'internal');
INSERT INTO `resource` VALUES (33, '用户在线', 2, 'page', '1', '/sys-config/online-manage', '部门／员工管理', 6, 1, '2019-06-27 09:34:55', NULL, NULL, 'N', NULL, 'N', 'internal');
INSERT INTO `resource` VALUES (34, '用户查询', 33, 'button', '1', '/online/list.do', '', 3, 0, '2019-06-27 09:34:55', 1, NULL, 'N', NULL, 'N', 'internal');
INSERT INTO `resource` VALUES (35, '用户踢出', 33, 'button', '1', '/online/kickout.do', '', 1, 1, '2019-06-27 09:34:55', NULL, NULL, 'N', NULL, 'N', 'internal');
INSERT INTO `resource` VALUES (36, '用户禁用', 33, 'button', '1', '/online/invalidUser.do', '', 2, 0, '2019-06-27 09:34:55', 1, '2019-07-03 05:24:03', 'N', NULL, 'N', 'internal');
INSERT INTO `resource` VALUES (37, '博客后台', -1, 'module', '1', '', '', 2, 0, '2019-06-27 09:40:43', 1, '2019-07-03 02:46:42', 'N', '', 'N', 'external');
INSERT INTO `resource` VALUES (38, '导航栏资源', 37, 'menu', '1', '', 'function-manage', 1, 0, '2019-06-27 09:50:44', 1, '2019-07-02 11:07:10', 'N', 'navigation', 'N', 'external');
INSERT INTO `resource` VALUES (39, '类别', 38, 'page', '1', '/platform/blog/center/category', 'category', 2, 0, '2019-06-27 09:51:40', 1, '2019-07-03 09:56:37', 'N', '', 'N', 'external');
INSERT INTO `resource` VALUES (40, '分类查询', 39, 'button', '1', '/platform/blog/cateory/search', '', 1, 1, '2019-06-27 09:52:10', NULL, NULL, 'N', '', 'N', 'external');
INSERT INTO `resource` VALUES (41, '全局资源', 37, 'menu', '1', '/platform/blog/global', '', 2, 1, '2019-06-27 09:52:46', NULL, NULL, 'N', 'global', 'N', 'external');
INSERT INTO `resource` VALUES (42, '项目资源', 41, 'page', '1', '/platform/blog/knowledge/new', '', 1, 0, '2019-06-27 09:53:24', 1, '2019-07-03 02:44:15', 'N', '', 'N', 'external');
INSERT INTO `resource` VALUES (43, '知识库创建', 42, 'button', '1', '/platform/blog/new-knowledge/create', '', 1, 0, '2019-06-27 09:56:28', 1, '2019-07-03 02:45:03', 'N', '', 'N', 'external');
INSERT INTO `resource` VALUES (44, '工作台', 38, 'page', '1', '/platform/blog/center/workbench', 'workbench', 1, 0, '2019-07-03 02:39:53', 1, '2019-07-03 09:56:27', 'N', NULL, 'N', 'external');
INSERT INTO `resource` VALUES (45, '知识库', 38, 'page', '1', '/platform/blog/center/knowledge', 'knowledge', 3, 0, '2019-07-03 02:40:33', 1, '2019-07-03 09:56:13', 'N', NULL, 'N', 'external');
INSERT INTO `resource` VALUES (46, '最近动态', 38, 'page', '1', '/platform/blog/post', 'document', 4, 0, '2019-07-03 02:41:18', 1, '2019-07-13 04:55:40', 'N', NULL, 'N', 'external');
INSERT INTO `resource` VALUES (47, '审核', 38, 'page', '1', '/platform/blog/center/audit', 'audit', 5, 0, '2019-07-03 02:42:54', 2, '2019-07-29 21:12:40', 'N', NULL, 'N', 'external');
INSERT INTO `resource` VALUES (48, '消息', 38, 'page', '1', '/platform/blog/message', 'message', 6, 0, '2019-07-03 02:43:20', 1, '2019-07-03 09:55:46', 'N', NULL, 'N', 'external');
INSERT INTO `resource` VALUES (61, '内部页面', 37, 'menu', '1', '', '', 3, 0, '2019-07-11 11:19:42', 1, '2019-07-11 11:19:59', 'N', 'internal', 'N', 'external');
INSERT INTO `resource` VALUES (62, '知识库详情', 61, 'page', '1', '/platform/blog/knowledge/detail', '', 1, 1, '2019-07-11 11:20:26', NULL, NULL, 'N', '', 'N', 'external');
INSERT INTO `resource` VALUES (63, '文章编辑', 61, 'page', '1', '/platform/blog/knowledge/article/editor', '', 2, 1, '2019-07-11 11:20:41', NULL, NULL, 'N', '', 'N', 'external');
INSERT INTO `resource` VALUES (64, '文章显示', 61, 'page', '1', '/platform/blog/knowledge/article/show', '', 3, 1, '2019-07-11 11:20:52', NULL, NULL, 'N', '', 'N', 'external');
INSERT INTO `resource` VALUES (65, '分类删除', 39, 'button', '1', '/platform/blog/category/delete', '', 2, 1, '2019-07-13 04:39:58', NULL, NULL, 'N', '', 'N', 'external');
INSERT INTO `resource` VALUES (66, '分类修改', 39, 'button', '1', '/platform/blog/category/update', '', 3, 0, '2019-07-13 04:40:13', 1, '2019-07-13 04:43:31', 'N', '', 'N', 'external');
INSERT INTO `resource` VALUES (67, '共享修改', 39, 'button', '1', '/platform/blog/category/share/update', '', 4, 0, '2019-07-13 04:40:29', 1, '2019-07-13 04:43:24', 'N', '', 'N', 'external');
INSERT INTO `resource` VALUES (68, '分类新增', 39, 'button', '1', '/platform/blog/category/add', '', 5, 0, '2019-07-13 04:41:07', 1, '2019-07-13 04:43:18', 'N', '', 'N', 'external');
INSERT INTO `resource` VALUES (69, '最新动态', 44, 'button', '1', '/platform/blog/recent/post', '', 1, 0, '2019-07-13 04:42:33', 1, '2019-07-13 04:42:42', 'N', '', 'N', 'external');
INSERT INTO `resource` VALUES (70, '最新文章', 44, 'button', '1', '/platform/blog/recent/article/edit', '', 2, 0, '2019-07-13 04:43:01', 1, '2019-07-13 04:43:11', 'N', '', 'N', 'external');
INSERT INTO `resource` VALUES (71, '最近知识库', 44, 'button', '1', '/platform/blog/recent/knowledge/edit', '', 3, 1, '2019-07-13 04:43:53', NULL, NULL, 'N', '', 'N', 'external');
INSERT INTO `resource` VALUES (72, '知识库查询', 45, 'button', '1', '/platform/blog/knowledge/list', '', 1, 1, '2019-07-13 04:45:17', NULL, NULL, 'N', '', 'N', 'external');
INSERT INTO `resource` VALUES (73, '移除参与者', 45, 'button', '1', '/platform/blog/knowledge/removeParticipant', '', 2, 1, '2019-07-13 04:45:51', NULL, NULL, 'N', '', 'N', 'external');
INSERT INTO `resource` VALUES (74, '删除知识库', 45, 'button', '1', '/platform/blog/knowledge/delete', '', 3, 0, '2019-07-13 04:46:38', 1, '2019-07-13 04:46:44', 'N', '', 'N', 'external');
INSERT INTO `resource` VALUES (75, '共享删除知识库', 45, 'button', '1', '/platform/blog/knowledge/share/delete', '', 4, 0, '2019-07-13 04:47:03', 1, '2019-07-13 05:11:32', 'N', '', 'N', 'external');
INSERT INTO `resource` VALUES (76, '更新知识库', 45, 'button', '1', '/platform/blog/knowledge/update', '', 5, 1, '2019-07-13 04:47:49', NULL, NULL, 'N', '', 'N', 'external');
INSERT INTO `resource` VALUES (77, '共享更新', 45, 'button', '1', '/platform/blog/knowledge/share/update', '', 6, 0, '2019-07-13 04:48:29', 1, '2019-07-13 04:48:36', 'N', '', 'N', 'external');
INSERT INTO `resource` VALUES (78, '新增知识库', 45, 'button', '1', '/platform/blog/knowledge/add', '', 7, 1, '2019-07-13 04:49:00', NULL, NULL, 'N', '', 'N', 'external');
INSERT INTO `resource` VALUES (79, '共享删除', 39, 'button', '1', '/platform/blog/category/share/delete', '', 6, 0, '2019-07-13 05:09:32', 1, '2019-07-13 12:29:01', 'N', '', 'N', 'external');
INSERT INTO `resource` VALUES (80, '共享移除参与者', 45, 'button', '1', '/platform/blog/knowledge/share/removeParticipant', '', 8, 1, '2019-07-13 05:10:43', NULL, NULL, 'N', '', 'N', 'external');
INSERT INTO `resource` VALUES (81, '文章列表', 62, 'button', '1', '/platform/blog/article/list', '', 1, 1, '2019-07-13 05:21:16', NULL, NULL, 'N', '', 'N', 'external');
INSERT INTO `resource` VALUES (82, '文章显示', 64, 'button', '1', '/platform/blog/article/get', '', 1, 1, '2019-07-13 05:22:17', NULL, NULL, 'N', '', 'N', 'external');
INSERT INTO `resource` VALUES (83, '文章新增', 63, 'button', '1', '/platform/blog/article/post', '', 1, 1, '2019-07-13 05:22:48', 1, '2019-07-13 12:26:49', 'Y', '', 'N', 'external');
INSERT INTO `resource` VALUES (84, '文章修改', 63, 'button', '1', '/platform/blog/article/edit', '', 2, 1, '2019-07-13 05:23:08', 1, '2019-07-13 12:26:22', 'Y', '', 'N', 'external');
INSERT INTO `resource` VALUES (85, '共享文章编辑', 63, 'button', '1', '/platform/blog/article/share/edit', '', 3, 1, '2019-07-13 05:23:38', 1, '2019-07-13 12:28:01', 'Y', '', 'N', 'external');
INSERT INTO `resource` VALUES (86, '文章删除', 64, 'button', '1', '/platform/blog/article/delete', '', 2, 1, '2019-07-13 05:24:23', NULL, NULL, 'N', '', 'N', 'external');
INSERT INTO `resource` VALUES (87, '文章共享删除', 64, 'button', '1', '/platform/blog/article/share/delete', '', 3, 0, '2019-07-13 05:24:40', 1, '2019-07-13 05:24:46', 'N', '', 'N', 'external');
INSERT INTO `resource` VALUES (88, '文章编辑', 64, 'button', '1', '/platform/blog/article/edit', '', 4, 1, '2019-07-13 12:21:57', NULL, NULL, 'N', '', 'N', 'external');
INSERT INTO `resource` VALUES (89, '文章共享修改', 64, 'button', '1', '/platform/blog/article/share/edit', '', 5, 1, '2019-07-13 12:22:58', NULL, NULL, 'N', '', 'N', 'external');
INSERT INTO `resource` VALUES (90, '文章新增', 62, 'button', '1', '/platform/blog/article/post', '', 2, 1, '2019-07-13 12:24:28', NULL, NULL, 'N', '', 'N', 'external');
INSERT INTO `resource` VALUES (91, '文章新增', 64, 'button', '1', '/platform/blog/article/post', '', 6, 1, '2019-07-13 12:24:52', 1, '2019-07-14 13:52:19', 'Y', '', 'N', 'external');
INSERT INTO `resource` VALUES (92, '作者申请', 47, 'button', '1', '/audit/author/list', '', 1, 2, '2019-07-29 21:15:49', NULL, NULL, 'N', '', 'N', 'external');
INSERT INTO `resource` VALUES (93, '审核通过', 47, 'button', '1', '/audit/author/pass', '', 2, 2, '2019-07-29 21:24:15', NULL, NULL, 'N', '', 'N', 'external');
INSERT INTO `resource` VALUES (94, '审核不通过', 47, 'button', '1', '/audit/author/no-pass', '', 3, 2, '2019-07-29 21:24:50', NULL, NULL, 'N', '', 'N', 'external');
INSERT INTO `resource` VALUES (95, '加入知识库', 47, 'button', '1', '/audit/join-knowledge/list', '', 4, 2, '2019-07-29 22:31:36', NULL, NULL, 'N', '', 'N', 'external');
INSERT INTO `resource` VALUES (96, '加入审核通过', 47, 'button', '1', '/audit/join-knowledge/pass', '', 5, 2, '2019-07-29 22:32:40', NULL, NULL, 'N', '', 'N', 'external');
INSERT INTO `resource` VALUES (97, '加入审核不通过', 47, 'button', '1', '/audit/join-knowledge/no-pass', '', 6, 2, '2019-07-29 22:33:12', NULL, NULL, 'N', '', 'N', 'external');

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `role_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色名字',
  `in_use` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '1' COMMENT '是否启用 1、是 2、 否',
  `mark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `creator` int(11) NOT NULL COMMENT '创建人',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `updator` int(11) NULL DEFAULT NULL COMMENT '修改人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `is_delete` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'N' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES (1, '超级管理员', '1', '我是超级管理员哟~', 1, '2019-06-27 09:34:54', 1, NULL, 'N');
INSERT INTO `role` VALUES (2, '博客浏览者', '1', '拥有前端简单权限', 1, '2019-06-27 09:38:32', 1, NULL, 'N');
INSERT INTO `role` VALUES (3, '博客作者', '1', '拥有自己的知识库操作权限', 1, '2019-07-13 04:57:56', NULL, NULL, 'N');
INSERT INTO `role` VALUES (4, '知识库共享者', '1', '拥有共享的操作权限', 0, '2019-07-13 04:58:27', 1, NULL, 'N');

-- ----------------------------
-- Table structure for role_resource
-- ----------------------------
DROP TABLE IF EXISTS `role_resource`;
CREATE TABLE `role_resource`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '角色资源ID',
  `role_id` int(11) NULL DEFAULT NULL COMMENT '角色ID',
  `resource_id` int(11) NULL DEFAULT NULL COMMENT '资源ID',
  `creator` int(11) NOT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `updator` int(11) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 253 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role_resource
-- ----------------------------
INSERT INTO `role_resource` VALUES (62, 2, 37, 1, '2019-07-11 11:21:12', NULL, NULL);
INSERT INTO `role_resource` VALUES (63, 2, 38, 1, '2019-07-11 11:21:12', NULL, NULL);
INSERT INTO `role_resource` VALUES (64, 2, 39, 1, '2019-07-11 11:21:12', NULL, NULL);
INSERT INTO `role_resource` VALUES (65, 2, 40, 1, '2019-07-11 11:21:12', NULL, NULL);
INSERT INTO `role_resource` VALUES (66, 2, 44, 1, '2019-07-11 11:21:12', NULL, NULL);
INSERT INTO `role_resource` VALUES (67, 2, 45, 1, '2019-07-11 11:21:12', NULL, NULL);
INSERT INTO `role_resource` VALUES (68, 2, 46, 1, '2019-07-11 11:21:12', NULL, NULL);
INSERT INTO `role_resource` VALUES (69, 2, 47, 1, '2019-07-11 11:21:12', NULL, NULL);
INSERT INTO `role_resource` VALUES (70, 2, 48, 1, '2019-07-11 11:21:12', NULL, NULL);
INSERT INTO `role_resource` VALUES (71, 2, 41, 1, '2019-07-11 11:21:12', NULL, NULL);
INSERT INTO `role_resource` VALUES (72, 2, 42, 1, '2019-07-11 11:21:12', NULL, NULL);
INSERT INTO `role_resource` VALUES (73, 2, 43, 1, '2019-07-11 11:21:12', NULL, NULL);
INSERT INTO `role_resource` VALUES (74, 2, 61, 1, '2019-07-11 11:21:12', NULL, NULL);
INSERT INTO `role_resource` VALUES (75, 2, 62, 1, '2019-07-11 11:21:12', NULL, NULL);
INSERT INTO `role_resource` VALUES (76, 2, 63, 1, '2019-07-11 11:21:12', NULL, NULL);
INSERT INTO `role_resource` VALUES (77, 2, 64, 1, '2019-07-11 11:21:12', NULL, NULL);
INSERT INTO `role_resource` VALUES (133, 4, 37, 1, '2019-07-14 13:52:35', NULL, NULL);
INSERT INTO `role_resource` VALUES (134, 4, 38, 1, '2019-07-14 13:52:35', NULL, NULL);
INSERT INTO `role_resource` VALUES (135, 4, 39, 1, '2019-07-14 13:52:35', NULL, NULL);
INSERT INTO `role_resource` VALUES (136, 4, 67, 1, '2019-07-14 13:52:35', NULL, NULL);
INSERT INTO `role_resource` VALUES (137, 4, 79, 1, '2019-07-14 13:52:35', NULL, NULL);
INSERT INTO `role_resource` VALUES (138, 4, 45, 1, '2019-07-14 13:52:36', NULL, NULL);
INSERT INTO `role_resource` VALUES (139, 4, 75, 1, '2019-07-14 13:52:36', NULL, NULL);
INSERT INTO `role_resource` VALUES (140, 4, 77, 1, '2019-07-14 13:52:36', NULL, NULL);
INSERT INTO `role_resource` VALUES (141, 4, 80, 1, '2019-07-14 13:52:36', NULL, NULL);
INSERT INTO `role_resource` VALUES (142, 4, 61, 1, '2019-07-14 13:52:36', NULL, NULL);
INSERT INTO `role_resource` VALUES (143, 4, 63, 1, '2019-07-14 13:52:36', NULL, NULL);
INSERT INTO `role_resource` VALUES (144, 4, 64, 1, '2019-07-14 13:52:36', NULL, NULL);
INSERT INTO `role_resource` VALUES (145, 4, 87, 1, '2019-07-14 13:52:36', NULL, NULL);
INSERT INTO `role_resource` VALUES (146, 4, 89, 1, '2019-07-14 13:52:36', NULL, NULL);
INSERT INTO `role_resource` VALUES (179, 3, 37, 2, '2019-07-30 07:21:19', NULL, NULL);
INSERT INTO `role_resource` VALUES (180, 3, 38, 2, '2019-07-30 07:21:19', NULL, NULL);
INSERT INTO `role_resource` VALUES (181, 3, 39, 2, '2019-07-30 07:21:19', NULL, NULL);
INSERT INTO `role_resource` VALUES (182, 3, 40, 2, '2019-07-30 07:21:19', NULL, NULL);
INSERT INTO `role_resource` VALUES (183, 3, 65, 2, '2019-07-30 07:21:19', NULL, NULL);
INSERT INTO `role_resource` VALUES (184, 3, 66, 2, '2019-07-30 07:21:19', NULL, NULL);
INSERT INTO `role_resource` VALUES (185, 3, 68, 2, '2019-07-30 07:21:19', NULL, NULL);
INSERT INTO `role_resource` VALUES (186, 3, 44, 2, '2019-07-30 07:21:19', NULL, NULL);
INSERT INTO `role_resource` VALUES (187, 3, 69, 2, '2019-07-30 07:21:20', NULL, NULL);
INSERT INTO `role_resource` VALUES (188, 3, 70, 2, '2019-07-30 07:21:20', NULL, NULL);
INSERT INTO `role_resource` VALUES (189, 3, 71, 2, '2019-07-30 07:21:20', NULL, NULL);
INSERT INTO `role_resource` VALUES (190, 3, 45, 2, '2019-07-30 07:21:20', NULL, NULL);
INSERT INTO `role_resource` VALUES (191, 3, 72, 2, '2019-07-30 07:21:20', NULL, NULL);
INSERT INTO `role_resource` VALUES (192, 3, 73, 2, '2019-07-30 07:21:20', NULL, NULL);
INSERT INTO `role_resource` VALUES (193, 3, 74, 2, '2019-07-30 07:21:20', NULL, NULL);
INSERT INTO `role_resource` VALUES (194, 3, 76, 2, '2019-07-30 07:21:20', NULL, NULL);
INSERT INTO `role_resource` VALUES (195, 3, 78, 2, '2019-07-30 07:21:20', NULL, NULL);
INSERT INTO `role_resource` VALUES (196, 3, 46, 2, '2019-07-30 07:21:20', NULL, NULL);
INSERT INTO `role_resource` VALUES (197, 3, 47, 2, '2019-07-30 07:21:20', NULL, NULL);
INSERT INTO `role_resource` VALUES (198, 3, 95, 2, '2019-07-30 07:21:20', NULL, NULL);
INSERT INTO `role_resource` VALUES (199, 3, 96, 2, '2019-07-30 07:21:20', NULL, NULL);
INSERT INTO `role_resource` VALUES (200, 3, 97, 2, '2019-07-30 07:21:20', NULL, NULL);
INSERT INTO `role_resource` VALUES (201, 3, 48, 2, '2019-07-30 07:21:20', NULL, NULL);
INSERT INTO `role_resource` VALUES (202, 3, 41, 2, '2019-07-30 07:21:20', NULL, NULL);
INSERT INTO `role_resource` VALUES (203, 3, 42, 2, '2019-07-30 07:21:20', NULL, NULL);
INSERT INTO `role_resource` VALUES (204, 3, 43, 2, '2019-07-30 07:21:20', NULL, NULL);
INSERT INTO `role_resource` VALUES (205, 3, 61, 2, '2019-07-30 07:21:20', NULL, NULL);
INSERT INTO `role_resource` VALUES (206, 3, 62, 2, '2019-07-30 07:21:20', NULL, NULL);
INSERT INTO `role_resource` VALUES (207, 3, 81, 2, '2019-07-30 07:21:20', NULL, NULL);
INSERT INTO `role_resource` VALUES (208, 3, 90, 2, '2019-07-30 07:21:20', NULL, NULL);
INSERT INTO `role_resource` VALUES (209, 3, 63, 2, '2019-07-30 07:21:20', NULL, NULL);
INSERT INTO `role_resource` VALUES (210, 3, 64, 2, '2019-07-30 07:21:20', NULL, NULL);
INSERT INTO `role_resource` VALUES (211, 3, 82, 2, '2019-07-30 07:21:20', NULL, NULL);
INSERT INTO `role_resource` VALUES (212, 3, 86, 2, '2019-07-30 07:21:20', NULL, NULL);
INSERT INTO `role_resource` VALUES (213, 3, 88, 2, '2019-07-30 07:21:20', NULL, NULL);
INSERT INTO `role_resource` VALUES (214, 1, 1, 2, '2019-07-30 07:22:06', NULL, NULL);
INSERT INTO `role_resource` VALUES (215, 1, 2, 2, '2019-07-30 07:22:06', NULL, NULL);
INSERT INTO `role_resource` VALUES (216, 1, 3, 2, '2019-07-30 07:22:06', NULL, NULL);
INSERT INTO `role_resource` VALUES (217, 1, 7, 2, '2019-07-30 07:22:06', NULL, NULL);
INSERT INTO `role_resource` VALUES (218, 1, 8, 2, '2019-07-30 07:22:06', NULL, NULL);
INSERT INTO `role_resource` VALUES (219, 1, 9, 2, '2019-07-30 07:22:06', NULL, NULL);
INSERT INTO `role_resource` VALUES (220, 1, 10, 2, '2019-07-30 07:22:06', NULL, NULL);
INSERT INTO `role_resource` VALUES (221, 1, 4, 2, '2019-07-30 07:22:06', NULL, NULL);
INSERT INTO `role_resource` VALUES (222, 1, 19, 2, '2019-07-30 07:22:06', NULL, NULL);
INSERT INTO `role_resource` VALUES (223, 1, 20, 2, '2019-07-30 07:22:06', NULL, NULL);
INSERT INTO `role_resource` VALUES (224, 1, 21, 2, '2019-07-30 07:22:06', NULL, NULL);
INSERT INTO `role_resource` VALUES (225, 1, 22, 2, '2019-07-30 07:22:06', NULL, NULL);
INSERT INTO `role_resource` VALUES (226, 1, 5, 2, '2019-07-30 07:22:06', NULL, NULL);
INSERT INTO `role_resource` VALUES (227, 1, 23, 2, '2019-07-30 07:22:06', NULL, NULL);
INSERT INTO `role_resource` VALUES (228, 1, 24, 2, '2019-07-30 07:22:06', NULL, NULL);
INSERT INTO `role_resource` VALUES (229, 1, 25, 2, '2019-07-30 07:22:06', NULL, NULL);
INSERT INTO `role_resource` VALUES (230, 1, 26, 2, '2019-07-30 07:22:06', NULL, NULL);
INSERT INTO `role_resource` VALUES (231, 1, 27, 2, '2019-07-30 07:22:06', NULL, NULL);
INSERT INTO `role_resource` VALUES (232, 1, 6, 2, '2019-07-30 07:22:06', NULL, NULL);
INSERT INTO `role_resource` VALUES (233, 1, 28, 2, '2019-07-30 07:22:06', NULL, NULL);
INSERT INTO `role_resource` VALUES (234, 1, 29, 2, '2019-07-30 07:22:06', NULL, NULL);
INSERT INTO `role_resource` VALUES (235, 1, 30, 2, '2019-07-30 07:22:06', NULL, NULL);
INSERT INTO `role_resource` VALUES (236, 1, 31, 2, '2019-07-30 07:22:06', NULL, NULL);
INSERT INTO `role_resource` VALUES (237, 1, 32, 2, '2019-07-30 07:22:06', NULL, NULL);
INSERT INTO `role_resource` VALUES (238, 1, 14, 2, '2019-07-30 07:22:06', NULL, NULL);
INSERT INTO `role_resource` VALUES (239, 1, 15, 2, '2019-07-30 07:22:06', NULL, NULL);
INSERT INTO `role_resource` VALUES (240, 1, 16, 2, '2019-07-30 07:22:06', NULL, NULL);
INSERT INTO `role_resource` VALUES (241, 1, 17, 2, '2019-07-30 07:22:06', NULL, NULL);
INSERT INTO `role_resource` VALUES (242, 1, 18, 2, '2019-07-30 07:22:07', NULL, NULL);
INSERT INTO `role_resource` VALUES (243, 1, 33, 2, '2019-07-30 07:22:07', NULL, NULL);
INSERT INTO `role_resource` VALUES (244, 1, 34, 2, '2019-07-30 07:22:07', NULL, NULL);
INSERT INTO `role_resource` VALUES (245, 1, 35, 2, '2019-07-30 07:22:07', NULL, NULL);
INSERT INTO `role_resource` VALUES (246, 1, 36, 2, '2019-07-30 07:22:07', NULL, NULL);
INSERT INTO `role_resource` VALUES (247, 1, 37, 2, '2019-07-30 07:22:07', NULL, NULL);
INSERT INTO `role_resource` VALUES (248, 1, 38, 2, '2019-07-30 07:22:07', NULL, NULL);
INSERT INTO `role_resource` VALUES (249, 1, 47, 2, '2019-07-30 07:22:07', NULL, NULL);
INSERT INTO `role_resource` VALUES (250, 1, 92, 2, '2019-07-30 07:22:07', NULL, NULL);
INSERT INTO `role_resource` VALUES (251, 1, 93, 2, '2019-07-30 07:22:07', NULL, NULL);
INSERT INTO `role_resource` VALUES (252, 1, 94, 2, '2019-07-30 07:22:07', NULL, NULL);

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户角色ID',
  `user_id` int(11) NULL DEFAULT NULL COMMENT '用户ID',
  `role_id` int(11) NULL DEFAULT NULL COMMENT '角色ID',
  `creator` int(11) NOT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES (1, 1, 1, 1, '2019-06-27 09:34:54');
INSERT INTO `user_role` VALUES (3, 2, 2, 1, '2019-07-13 05:45:36');
INSERT INTO `user_role` VALUES (4, 2, 1, 1, '2019-07-13 05:45:36');
INSERT INTO `user_role` VALUES (5, 2, 3, 1, '2019-07-13 05:45:36');
INSERT INTO `user_role` VALUES (6, 2, 4, 1, '2019-07-13 05:45:36');
INSERT INTO `user_role` VALUES (14, 9, 2, 0, '2019-08-03 02:32:29');
INSERT INTO `user_role` VALUES (15, 9, 3, 2, '2019-08-03 02:40:20');
INSERT INTO `user_role` VALUES (16, 10, 2, 0, '2019-08-06 13:20:37');

-- ----------------------------
-- Table structure for user_skill
-- ----------------------------
DROP TABLE IF EXISTS `user_skill`;
CREATE TABLE `user_skill`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_id` int(11) NULL DEFAULT NULL COMMENT '用户ID',
  `skill` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '技能',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 29 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_skill
-- ----------------------------
INSERT INTO `user_skill` VALUES (1, 9, 'HTML');
INSERT INTO `user_skill` VALUES (2, 9, 'LLL');
INSERT INTO `user_skill` VALUES (3, 9, 'LLL');
INSERT INTO `user_skill` VALUES (23, 9, 'ADFADS');
INSERT INTO `user_skill` VALUES (24, 9, 'ADFADS');

-- ----------------------------
-- Procedure structure for INIT_CMS_TABLE
-- ----------------------------
DROP PROCEDURE IF EXISTS `INIT_CMS_TABLE`;
delimiter ;;
CREATE PROCEDURE `INIT_CMS_TABLE`()
BEGIN

	DECLARE curDate datetime;
	SET curDate = NOW();

	SET NAMES utf8mb4;
	SET FOREIGN_KEY_CHECKS = 0;
  -- 创建可更改的字典表
	DROP TABLE IF EXISTS `cms_const`;
	CREATE TABLE `cms_const`  (
		`id` int(11) NOT NULL AUTO_INCREMENT COMMENT '常量表ID',
		`type` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '1' COMMENT '1、常量配置，2、枚举配置',
		`const_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'value显示名字',
		`const_key` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '类似于code',
		`const_value` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '值',
		`in_use` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '1' COMMENT '是否启用 1、是 2、 否',
		`creator` int(11) NOT NULL COMMENT '创建人',
		`create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
		`updator` int(11) NULL DEFAULT NULL COMMENT '更新人',
		`update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
		PRIMARY KEY (`id`) USING BTREE
	) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

  -- 用户表
	DROP TABLE IF EXISTS `cms_user`;
	CREATE TABLE `cms_user`  (
		`id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户主键ID',
		`user_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户姓名',
		`user_sex` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '0: 未知 1：女， 2：男',
		`user_account` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '账号',
		`user_password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
		`user_status` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '1' COMMENT '1: 正常， 2、 锁定',
		`user_tel` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户电话',
		`user_mail` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '电子邮件',
		`user_depart` int(11) NULL DEFAULT NULL COMMENT '所属部门',
		`creator` int(11) NOT NULL COMMENT '创建人',
		`create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
		`updator` int(11) NULL DEFAULT NULL COMMENT '更新人',
		`update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
		`is_delete` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'N' COMMENT '是否删除',
		`uuid` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '提供一个uuid做加密的盐值',
		`lock_time` datetime(0) NULL DEFAULT NULL COMMENT '锁住时间',
		`login_time` datetime(0) NULL DEFAULT NULL COMMENT '登录时间',
		PRIMARY KEY (`id`) USING BTREE
	) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

	-- 部门表
	DROP TABLE IF EXISTS `organization`;
	CREATE TABLE `organization`  (
		`id` int(11) NOT NULL AUTO_INCREMENT,
		`parent_id` int(11) NULL DEFAULT 0 COMMENT '父组织名字',
		`org_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '组织名字',
		`expand` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '1、Y 展开 2、N 不展开',
		`creator` int(11) NOT NULL COMMENT '创建人',
		`create_time` datetime(0) NOT NULL COMMENT '创建时间',
		`updator` int(11) NULL DEFAULT NULL COMMENT '修改人',
		`update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
		`is_delete` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'N' COMMENT '是否删除',
		PRIMARY KEY (`id`) USING BTREE
	) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

	-- 资源表
	DROP TABLE IF EXISTS `resource`;
	CREATE TABLE `resource`  (
		`id` int(11) NOT NULL AUTO_INCREMENT COMMENT '资源ID',
		`resource_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '资源名字',
		`parent_id` int(11) NULL DEFAULT NULL COMMENT '上级资源ID',
		`resource_type` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '1' COMMENT 'module：模块，menu：菜单 、button：按钮、page:页面',
		`in_use` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '1' COMMENT '是否启用 1、是 2、 否',
		`resource_path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '资源路径',
		`resource_icon` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '资源图标',
		`resource_sort` int(11) NULL DEFAULT NULL COMMENT '排序号',
		`creator` int(11) NOT NULL,
		`create_time` datetime(0) NULL DEFAULT NULL,
		`updator` int(11) NULL DEFAULT NULL,
		`update_time` datetime(0) NULL DEFAULT NULL,
		`is_delete` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'N',
		`alias` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '导航菜单配置标志别名',
		`default_page` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'N' COMMENT '默认页面(是：Y， 否：N)',
		`belongs` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'internal' COMMENT 'external:外部，internal：内部',
		PRIMARY KEY (`id`) USING BTREE
	) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

	-- 角色表
	DROP TABLE IF EXISTS `role`;
	CREATE TABLE `role`  (
		`id` int(11) NOT NULL AUTO_INCREMENT COMMENT '角色ID',
		`role_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色名字',
		`in_use` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '1' COMMENT '是否启用 1、是 2、 否',
		`mark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
		`creator` int(11) NOT NULL COMMENT '创建人',
		`create_time` datetime(0) NOT NULL COMMENT '创建时间',
		`updator` int(11) NULL DEFAULT NULL COMMENT '修改人',
		`update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
		`is_delete` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'N' COMMENT '是否删除',
		PRIMARY KEY (`id`) USING BTREE
	) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

	-- 角色资源表
	DROP TABLE IF EXISTS `role_resource`;
	CREATE TABLE `role_resource`  (
		`id` int(11) NOT NULL AUTO_INCREMENT COMMENT '角色资源ID',
		`role_id` int(11) NULL DEFAULT NULL COMMENT '角色ID',
		`resource_id` int(11) NULL DEFAULT NULL COMMENT '资源ID',
		`creator` int(11) NOT NULL,
		`create_time` datetime(0) NULL DEFAULT NULL,
		`updator` int(11) NULL DEFAULT NULL,
		`update_time` datetime(0) NULL DEFAULT NULL,
		PRIMARY KEY (`id`) USING BTREE
	) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

	-- 用户角色表
	DROP TABLE IF EXISTS `user_role`;
	CREATE TABLE `user_role`  (
		`id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户角色ID',
		`user_id` int(11) NULL DEFAULT NULL COMMENT '用户ID',
		`role_id` int(11) NULL DEFAULT NULL COMMENT '角色ID',
		`creator` int(11) NOT NULL,
		`create_time` datetime(0) NULL DEFAULT NULL,
		PRIMARY KEY (`id`) USING BTREE
	) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

 -- 重置表的数据
	truncate table cms_user;
	truncate table cms_const;
	truncate table organization;
	truncate table resource;
	truncate table role;
	truncate table role_resource;
	truncate table user_role;

 -- 生成一个根级部门，用于显示超级管理员账号
 INSERT INTO `organization` VALUES (1, 0, '鹰嘴豆总部', 'Y', 1, NOW(), NULL, NULL, 'N');

 -- 生成超级管理员
 INSERT INTO `cms_user` VALUES (1, '超级管理员', '0', 'admin', '5684995729a64824ce148c0fd50c1037', '1', '', '', 1, 0, NOW(), 3, NULL, 'N', '9ddac78c1ddf4301a3bc31d721ebc5a7', null, null);

 -- 生成角色
 INSERT INTO `role` VALUES (1, '超级管理员', '1', '我是超级管理员哟~', 1, NOW(), 1, NULL, 'N');

 -- 超级管理员与角色关联数据
 INSERT INTO `user_role` VALUES (1, 1, 1, 1, NOW());

 -- 插入资源表
INSERT INTO `resource` VALUES (1, '系统管理', -1, 'module', '1', '', 'sys-manage', 1, 0, NOW(), 1, NULL, 'N', 'SysConfigure', 'N', 'internal');
INSERT INTO `resource` VALUES (2, '系统配置', 1, 'menu', '1', '', 'organization', 2, 0, NOW(), 1, NULL, 'N', NULL, 'N', 'internal');
INSERT INTO `resource` VALUES (3, '资源管理', 2, 'page', '1', '/sys-config/resource-manage', 'language', 1, 0, NOW(), 1, NULL, 'N', NULL, 'N', 'internal');
INSERT INTO `resource` VALUES (4, '组织管理', 2, 'page', '1', '/sys-config/organization-manage', 'table-manage', 3, 0, NOW(), 1, NULL, 'N', NULL, 'N', 'internal');
INSERT INTO `resource` VALUES (5, '角色管理', 2, 'page', '1', '/sys-config/role-manage', 'table-manage', 3, 0, NOW(), 1, NULL, 'N', NULL, 'N', 'internal');
INSERT INTO `resource` VALUES (6, '用户管理', 2, 'page', '1', '/sys-config/user-manage', 'function-manage', 3, 0, NOW(), 1, NULL, 'N', NULL, 'N', 'internal');
INSERT INTO `resource` VALUES (7, '资源新增', 3, 'button', '1', '/permission/savePower.do', NULL, 1, 0, NOW(), 1, NULL, 'N', NULL, 'N', 'internal');
INSERT INTO `resource` VALUES (8, '资源更新', 3, 'button', '1', '/permission/updatePower.do', NULL, 1, 0, NOW(), 1, NULL, 'N', NULL, 'N', 'internal');
INSERT INTO `resource` VALUES (9, '资源查询', 3, 'button', '1', '/permission/listPower.do', NULL, 3, 1, NOW(), NULL, NULL, 'N', NULL, 'N', 'internal');
INSERT INTO `resource` VALUES (10, '资源删除', 3, 'button', '1', '/permission/deletePower.do', '', 4, 0, NOW(), 8, NULL, 'N', NULL, 'N', 'internal');
INSERT INTO `resource` VALUES (11, '功能服务', -1, 'module', '1', '', 'person-manage', 2, 0, NOW(), 1, NULL, 'N', 'SysService', 'N', 'internal');
INSERT INTO `resource` VALUES (12, '简单功能', 11, 'menu', '1', NULL, 'sys-manage', 1, 0, NOW(), 1, NULL, 'N', NULL, 'Y', 'internal');
INSERT INTO `resource` VALUES (13, '简单例子', 12, 'page', '1', '/service-config/hello-service', '角色管理', 1, 0, NOW(), 1, NULL, 'N', NULL, 'Y', 'internal');
INSERT INTO `resource` VALUES (14, '配置管理', 2, 'page', '1', '/sys-config/conf-manage', 'sys-manage', 5, 0, NOW(), 1, NULL, 'N', NULL, 'N', 'internal');
INSERT INTO `resource` VALUES (15, '常量新增', 14, 'button', '1', '/constant/save.do', '', 1, 0, NOW(), 1, NULL, 'N', NULL, 'N', 'internal');
INSERT INTO `resource` VALUES (16, '常量更新', 14, 'button', '1', '/constant/edit.do', '', 1, 0, NOW(), 1, NULL, 'N', NULL, 'N', 'internal');
INSERT INTO `resource` VALUES (17, '常量查询', 14, 'button', '1', '/constant/list.do', '', 3, 0, NOW(), 1, NULL, 'N', NULL, 'N', 'internal');
INSERT INTO `resource` VALUES (18, '常量删除', 14, 'button', '1', '/constant/delete.do', '', 3, 0, NOW(), 1, NULL, 'N', NULL, 'N', 'internal');
INSERT INTO `resource` VALUES (19, '组织新增', 4, 'button', '1', '/organization/save.do', '', 1, 8, NOW(), NULL, NULL, 'N', NULL, 'N', 'internal');
INSERT INTO `resource` VALUES (20, '组织更新', 4, 'button', '1', '/organization/edit.do', '', 2, 8, NOW(), NULL, NULL, 'N', NULL, 'N', 'internal');
INSERT INTO `resource` VALUES (21, '组织删除', 4, 'button', '1', '/organization/delete.do', '', 2, 8, NOW(), NULL, NULL, 'N', NULL, 'N', 'internal');
INSERT INTO `resource` VALUES (22, '组织查询', 4, 'button', '1', '/organization/list.do', '', 2, 8, NOW(), NULL, NULL, 'N', NULL, 'N', 'internal');
INSERT INTO `resource` VALUES (23, '角色新增', 5, 'button', '1', '/role/save.do', '', 1, 8, NOW(), NULL, NULL, 'N', NULL, 'N', 'internal');
INSERT INTO `resource` VALUES (24, '角色更新', 5, 'button', '1', '/role/edit.do', '', 2, 8, NOW(), NULL, NULL, 'N', NULL, 'N', 'internal');
INSERT INTO `resource` VALUES (25, '角色删除', 5, 'button', '1', '/role/delete.do', '', 2, 8, NOW(), NULL, NULL, 'N', NULL, 'N', 'internal');
INSERT INTO `resource` VALUES (26, '角色授权', 5, 'button', '1', '/role/resourceAuth.do', '', 2, 0, NOW(), 8, NULL, 'N', NULL, 'N', 'internal');
INSERT INTO `resource` VALUES (27, '角色查询', 5, 'button', '1', '/role/list.do', '', 5, 0, NOW(), 8, NULL, 'N', NULL, 'N', 'internal');
INSERT INTO `resource` VALUES (28, '用户新增', 6, 'button', '1', '/user/save.do', '', 1, 8, NOW(), NULL, NULL, 'N', NULL, 'N', 'internal');
INSERT INTO `resource` VALUES (29, '用户更新', 6, 'button', '1', '/user/edit.do', '', 1, 8, NOW(), NULL, NULL, 'N', NULL, 'N', 'internal');
INSERT INTO `resource` VALUES (30, '用户赋角', 6, 'button', '1', '/user/authUser.do', '', 1, 8, NOW(), NULL, NULL, 'N', NULL, 'N', 'internal');
INSERT INTO `resource` VALUES (31, '用户删除', 6, 'button', '1', '/user/authUser.do', '', 1, 0, NOW(), 8, NULL, 'N', NULL, 'N', 'internal');
INSERT INTO `resource` VALUES (32, '用户查询', 6, 'button', '1', '/user/list.do', '', 1, 8, NOW(), NULL, NULL, 'N', NULL, 'N', 'internal');
INSERT INTO `resource` VALUES (33, '用户在线', 2, 'page', '1', '/sys-config/online-manage', '部门／员工管理', 6, 1, NOW(), NULL, NULL, 'N', NULL, 'N', 'internal');
INSERT INTO `resource` VALUES (34, '用户查询', 33, 'button', '1', '/online/list.do', '', 3, 0, NOW(), 1, NULL, 'N', NULL, 'N', 'internal');
INSERT INTO `resource` VALUES (35, '用户踢出', 33, 'button', '1', '/online/kickout.do', '', 1, 1, NOW(), NULL, NULL, 'N', NULL, 'N', 'internal');
INSERT INTO `resource` VALUES (36, '用户禁用', 33, 'page', '1', '/online/invalidUser.do', '', 2, 0, NOW(), 1, NULL, 'N', NULL, 'N', 'internal');

 -- 超级管理员角色与资源关联
  INSERT INTO	`role_resource` VALUES (1, 1, 1, 1, NOW(), NULL, NULL);
	INSERT INTO `role_resource` VALUES (2, 1, 2, 1, NOW(), NULL, NULL);
	INSERT INTO `role_resource` VALUES (3, 1, 5, 1, NOW(), NULL, NULL);
	INSERT INTO `role_resource` VALUES (6, 1, 9, 1, NOW(), NULL, NULL);
	INSERT INTO `role_resource` VALUES (5, 1, 26, 1, NOW(), NULL, NULL);
	INSERT INTO `role_resource` VALUES (4, 1, 27, 1, NOW(), NULL, NULL);

 -- 基本常量数据
  INSERT INTO `cms_const` VALUES (1, '1', '资源根名称', 'root_resource', '开发者平台', '1', 1, NOW(), NULL, NULL);
	INSERT INTO `cms_const` VALUES (2, '1', '网站标题', 'system_title', '开发者平台', '1', 1, NOW(), NULL, NULL);
	INSERT INTO `cms_const` VALUES (3, '1', '鉴权方式', 'no_auth_represent', 'represent', '1', 0, NOW(), 1, NULL);

END
;;
delimiter ;

SET FOREIGN_KEY_CHECKS = 1;
