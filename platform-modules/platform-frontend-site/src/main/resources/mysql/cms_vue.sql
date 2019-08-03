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

 Date: 03/08/2019 09:15:17
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for article
-- ----------------------------
DROP TABLE IF EXISTS `article`;
CREATE TABLE `article`  (
	`id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
	`article_title` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '文章标题',
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
) ENGINE = InnoDB AUTO_INCREMENT = 35 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for article_participant
-- ----------------------------
DROP TABLE IF EXISTS `article_participant`;
CREATE TABLE `article_participant`  (
	`id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
	`article_id` int(11) NULL DEFAULT NULL COMMENT '文章ID',
	`user_id` int(11) NULL DEFAULT NULL COMMENT '协作者ID',
	PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

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
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

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
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

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
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

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
) ENGINE = InnoDB AUTO_INCREMENT = 19 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

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
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

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
) ENGINE = InnoDB AUTO_INCREMENT = 87 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

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
) ENGINE = InnoDB AUTO_INCREMENT = 136 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

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
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

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
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

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
) ENGINE = InnoDB AUTO_INCREMENT = 68 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

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
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

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
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user_skill
-- ----------------------------
DROP TABLE IF EXISTS `user_skill`;
CREATE TABLE `user_skill`  (
	`id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
	`user_id` int(11) NULL DEFAULT NULL COMMENT '用户ID',
	`skill` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '技能',
	PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

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
