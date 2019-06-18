
use `cms_web`;


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
INSERT INTO `resource` VALUES (1, '系统管理', -1, 'module', '1', '', 'sys-manage', 1, 0, NOW(), 1, NULL, 'N', 'SysConfigure', 'N');
INSERT INTO `resource` VALUES (2, '系统配置', 1, 'menu', '1', '', 'organization', 2, 0, NOW(), 1, NULL, 'N', NULL, 'N');
INSERT INTO `resource` VALUES (3, '资源管理', 2, 'page', '1', '/sys-config/resource-manage', 'language', 1, 0, NOW(), 1, NULL, 'N', NULL, 'N');
INSERT INTO `resource` VALUES (4, '组织管理', 2, 'page', '1', '/sys-config/organization-manage', 'table-manage', 3, 0, NOW(), 1, NULL, 'N', NULL, 'N');
INSERT INTO `resource` VALUES (5, '角色管理', 2, 'page', '1', '/sys-config/role-manage', 'table-manage', 3, 0, NOW(), 1, NULL, 'N', NULL, 'N');
INSERT INTO `resource` VALUES (6, '用户管理', 2, 'page', '1', '/sys-config/user-manage', 'function-manage', 3, 0, NOW(), 1, NULL, 'N', NULL, 'N');
INSERT INTO `resource` VALUES (7, '资源新增', 3, 'button', '1', '/permission/savePower.do', NULL, 1, 0, NOW(), 1, NULL, 'N', NULL, 'N');
INSERT INTO `resource` VALUES (8, '资源更新', 3, 'button', '1', '/permission/updatePower.do', NULL, 1, 0, NOW(), 1, NULL, 'N', NULL, 'N');
INSERT INTO `resource` VALUES (9, '资源查询', 3, 'button', '1', '/permission/listPower.do', NULL, 3, 1, NOW(), NULL, NULL, 'N', NULL, 'N');
INSERT INTO `resource` VALUES (10, '资源删除', 3, 'button', '1', '/permission/deletePower.do', '', 4, 0, NOW(), 8, NULL, 'N', NULL, 'N');
INSERT INTO `resource` VALUES (11, '功能服务', -1, 'module', '1', '', 'person-manage', 2, 0, NOW(), 1, NULL, 'N', 'SysService', 'N');
INSERT INTO `resource` VALUES (12, '简单功能', 11, 'menu', '1', NULL, 'sys-manage', 1, 0, NOW(), 1, NULL, 'N', NULL, 'Y');
INSERT INTO `resource` VALUES (13, '简单例子', 12, 'page', '1', '/service-config/hello-service', '角色管理', 1, 0, NOW(), 1, NULL, 'N', NULL, 'Y');
INSERT INTO `resource` VALUES (14, '配置管理', 2, 'page', '1', '/sys-config/conf-manage', 'sys-manage', 5, 0, NOW(), 1, NULL, 'N', NULL, 'N');
INSERT INTO `resource` VALUES (15, '常量新增', 14, 'button', '1', '/constant/save.do', '', 1, 0, NOW(), 1, NULL, 'N', NULL, 'N');
INSERT INTO `resource` VALUES (16, '常量更新', 14, 'button', '1', '/constant/edit.do', '', 1, 0, NOW(), 1, NULL, 'N', NULL, 'N');
INSERT INTO `resource` VALUES (17, '常量查询', 14, 'button', '1', '/constant/list.do', '', 3, 0, NOW(), 1, NULL, 'N', NULL, 'N');
INSERT INTO `resource` VALUES (18, '常量删除', 14, 'button', '1', '/constant/delete.do', '', 3, 0, NOW(), 1, NULL, 'N', NULL, 'N');
INSERT INTO `resource` VALUES (19, '组织新增', 4, 'button', '1', '/organization/save.do', '', 1, 8, NOW(), NULL, NULL, 'N', NULL, 'N');
INSERT INTO `resource` VALUES (20, '组织更新', 4, 'button', '1', '/organization/edit.do', '', 2, 8, NOW(), NULL, NULL, 'N', NULL, 'N');
INSERT INTO `resource` VALUES (21, '组织删除', 4, 'button', '1', '/organization/delete.do', '', 2, 8, NOW(), NULL, NULL, 'N', NULL, 'N');
INSERT INTO `resource` VALUES (22, '组织查询', 4, 'button', '1', '/organization/list.do', '', 2, 8, NOW(), NULL, NULL, 'N', NULL, 'N');
INSERT INTO `resource` VALUES (23, '角色新增', 5, 'button', '1', '/role/save.do', '', 1, 8, NOW(), NULL, NULL, 'N', NULL, 'N');
INSERT INTO `resource` VALUES (24, '角色更新', 5, 'button', '1', '/role/edit.do', '', 2, 8, NOW(), NULL, NULL, 'N', NULL, 'N');
INSERT INTO `resource` VALUES (25, '角色删除', 5, 'button', '1', '/role/delete.do', '', 2, 8, NOW(), NULL, NULL, 'N', NULL, 'N');
INSERT INTO `resource` VALUES (26, '角色授权', 5, 'button', '1', '/role/resourceAuth.do', '', 2, 0, NOW(), 8, NULL, 'N', NULL, 'N');
INSERT INTO `resource` VALUES (27, '角色查询', 5, 'button', '1', '/role/list.do', '', 5, 0, NOW(), 8, NULL, 'N', NULL, 'N');
INSERT INTO `resource` VALUES (28, '用户新增', 6, 'button', '1', '/user/save.do', '', 1, 8, NOW(), NULL, NULL, 'N', NULL, 'N');
INSERT INTO `resource` VALUES (29, '用户更新', 6, 'button', '1', '/user/edit.do', '', 1, 8, NOW(), NULL, NULL, 'N', NULL, 'N');
INSERT INTO `resource` VALUES (30, '用户赋角', 6, 'button', '1', '/user/authUser.do', '', 1, 8, NOW(), NULL, NULL, 'N', NULL, 'N');
INSERT INTO `resource` VALUES (31, '用户删除', 6, 'button', '1', '/user/authUser.do', '', 1, 0, NOW(), 8, NULL, 'N', NULL, 'N');
INSERT INTO `resource` VALUES (32, '用户查询', 6, 'button', '1', '/user/list.do', '', 1, 8, NOW(), NULL, NULL, 'N', NULL, 'N');
INSERT INTO `resource` VALUES (33, '用户在线', 2, 'page', '1', '/sys-config/online-manage', '部门／员工管理', 6, 1, NOW(), NULL, NULL, 'N', NULL, 'N');
INSERT INTO `resource` VALUES (34, '用户查询', 33, 'button', '1', '/online/list.do', '', 3, 0, NOW(), 1, NULL, 'N', NULL, 'N');
INSERT INTO `resource` VALUES (35, '用户踢出', 33, 'button', '1', '/online/kickout.do', '', 1, 1, NOW(), NULL, NULL, 'N', NULL, 'N');
INSERT INTO `resource` VALUES (36, '用户禁用', 33, 'page', '1', '/online/invalidUser.do', '', 2, 0, NOW(), 1, NULL, 'N', NULL, 'N');

 -- 超级管理员角色与资源关联
  INSERT INTO `role_resource` VALUES (1, 1, 1, 1, NOW(), NULL, NULL);
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

call INIT_CMS_TABLE;
