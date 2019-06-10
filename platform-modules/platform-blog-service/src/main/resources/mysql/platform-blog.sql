
use `platform-blog`;


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
	DROP TABLE IF EXISTS `blog_const`;
	CREATE TABLE `blog_const`  (
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
	DROP TABLE IF EXISTS `blog_user`;
	CREATE TABLE `blog_user`  (
		`id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户主键ID',
		`user_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户姓名',
		`user_sex` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '0: 未知 1：女， 2：男',
		`user_account` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '账号',
		`user_password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
		`user_status` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '1' COMMENT '1: 正常， 2、 锁定',
		`user_tel` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户电话',
		`user_mail` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '电子邮件',
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

	-- 资源表
	DROP TABLE IF EXISTS `resource`;
	CREATE TABLE `resource`  (
		`id` int(11) NOT NULL AUTO_INCREMENT COMMENT '资源ID',
		`resource_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '资源名字',
		`parent_id` int(11) NULL DEFAULT NULL COMMENT '上级资源ID',
		`resource_type` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '1' COMMENT 'menu：菜单 、button：按钮、page:页面',
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


 -- 基本常量数据
  INSERT INTO `blog_const` VALUES (1, '1', '资源根名称', 'root_resource', '开发者平台', '1', 1, NOW(), NULL, NULL);
	INSERT INTO `blog_const` VALUES (2, '1', '网站标题', 'system_title', '开发者平台', '1', 1, NOW(), NULL, NULL);
	INSERT INTO `blog_const` VALUES (3, '1', '鉴权方式', 'no_auth_represent', 'represent', '1', 0, NOW(), 1, NULL);

END
;;
delimiter ;

SET FOREIGN_KEY_CHECKS = 1;

call INIT_CMS_TABLE;
