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

 Date: 16/07/2019 15:13:05
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
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '文章内容',
  `creator` int(11) NOT NULL COMMENT '创建人',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `updator` int(11) NULL DEFAULT NULL COMMENT '更新人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `is_delete` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT 'N' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 44 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for article_participant
-- ----------------------------
DROP TABLE IF EXISTS `article_participant`;
CREATE TABLE `article_participant`  (
  `id` int(11) NOT NULL COMMENT '主键ID',
  `article_id` int(11) NULL DEFAULT NULL COMMENT '文章ID',
  `user_id` int(11) NULL DEFAULT NULL COMMENT '协作者ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for audit
-- ----------------------------
DROP TABLE IF EXISTS `audit`;
CREATE TABLE `audit`  (
  `id` int(11) NOT NULL COMMENT '主键ID',
  `apply_user` int(11) NULL DEFAULT NULL COMMENT '申请人',
  `handle_user` int(11) NULL DEFAULT NULL COMMENT '处理人',
  `apply_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '申请类型：1.知识库修改 2.文章修改 3.加入知识库',
  `apply_obj` int(11) NULL DEFAULT NULL COMMENT '申请对象ID',
  `handle_result` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '处理结果：1.通过，2.不通过',
  `handle_time` datetime(0) NULL DEFAULT NULL COMMENT '处理日期',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

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
) ENGINE = InnoDB AUTO_INCREMENT = 19 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

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
INSERT INTO `cms_const` VALUES (3, '1', '鉴权方式', 'no_auth_represent', 'represent', '1', 0, '2019-06-27 09:34:55', 1, NULL);

-- ----------------------------
-- Table structure for cms_user
-- ----------------------------
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
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cms_user
-- ----------------------------
INSERT INTO `cms_user` VALUES (1, '超级管理员', '0', 'admin', '5684995729a64824ce148c0fd50c1037', '1', '', '', 1, 0, '2019-06-27 09:34:54', 3, NULL, 'N', '9ddac78c1ddf4301a3bc31d721ebc5a7', NULL, '2019-07-14 13:50:07');
INSERT INTO `cms_user` VALUES (2, '鹰嘴豆', '0', 'yingzuidou', 'ef134caf30177d95a43d6f9b7a244614', '1', '18268873650', 'shangguanls1990@gmail.com', 1, 1, '2019-06-27 09:57:39', NULL, NULL, 'N', 'e1ba5e75b0b8488583d65b93c3f01923', NULL, '2019-07-03 02:38:14');
INSERT INTO `cms_user` VALUES (3, '混蛋羊', '0', 'fuckyang', '94d72f4ccd8209714caad6aa5071164d', '1', '', '', 1, 1, '2019-07-13 05:46:14', NULL, NULL, 'N', '0a392ec83d7043bc9db241c97bfd1374', NULL, NULL);

-- ----------------------------
-- Table structure for knowledge
-- ----------------------------
DROP TABLE IF EXISTS `knowledge`;
CREATE TABLE `knowledge`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '知识库ID',
  `k_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '知识库名字',
  `k_desc` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '知识库描述',
  `k_url` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '封面图片地址',
  `k_access` char(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '2' COMMENT '访问权限：1.私有 2.公开 3.加密',
  `k_type` int(11) NULL DEFAULT NULL COMMENT '所属分类',
  `k_reserve_o` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '预留字段',
  `k_reserve_t` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '预留字段',
  `creator` int(11) NOT NULL COMMENT '创建人',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `updator` int(11) NULL DEFAULT NULL COMMENT '更新人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `is_delete` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT 'N' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

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
) ENGINE = InnoDB AUTO_INCREMENT = 69 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

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
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

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
) ENGINE = InnoDB AUTO_INCREMENT = 24 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

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
) ENGINE = InnoDB AUTO_INCREMENT = 92 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

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
INSERT INTO `resource` VALUES (39, '类别', 38, 'page', '1', '/platform/blog/category', 'category', 2, 0, '2019-06-27 09:51:40', 1, '2019-07-03 09:56:37', 'N', '', 'N', 'external');
INSERT INTO `resource` VALUES (40, '分类查询', 39, 'button', '1', '/platform/blog/cateory/search', '', 1, 1, '2019-06-27 09:52:10', NULL, NULL, 'N', '', 'N', 'external');
INSERT INTO `resource` VALUES (41, '全局资源', 37, 'menu', '1', '/platform/blog/global', '', 2, 1, '2019-06-27 09:52:46', NULL, NULL, 'N', 'global', 'N', 'external');
INSERT INTO `resource` VALUES (42, '项目资源', 41, 'page', '1', '/knowledge/new', '', 1, 0, '2019-06-27 09:53:24', 1, '2019-07-03 02:44:15', 'N', '', 'N', 'external');
INSERT INTO `resource` VALUES (43, '知识库创建', 42, 'button', '1', '/platform/blog/new-knowledge/create', '', 1, 0, '2019-06-27 09:56:28', 1, '2019-07-03 02:45:03', 'N', '', 'N', 'external');
INSERT INTO `resource` VALUES (44, '工作台', 38, 'page', '1', '/platform/blog/workbench', 'workbench', 1, 0, '2019-07-03 02:39:53', 1, '2019-07-03 09:56:27', 'N', NULL, 'N', 'external');
INSERT INTO `resource` VALUES (45, '知识库', 38, 'page', '1', '/platform/blog/knowledge', 'knowledge', 3, 0, '2019-07-03 02:40:33', 1, '2019-07-03 09:56:13', 'N', NULL, 'N', 'external');
INSERT INTO `resource` VALUES (46, '最近动态', 38, 'page', '1', '/platform/blog/post', 'document', 4, 0, '2019-07-03 02:41:18', 1, '2019-07-13 04:55:40', 'N', NULL, 'N', 'external');
INSERT INTO `resource` VALUES (47, '审核', 38, 'page', '1', '/platform/blog/audit', 'audit', 5, 0, '2019-07-03 02:42:54', 1, '2019-07-03 09:55:55', 'N', NULL, 'N', 'external');
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
INSERT INTO `role` VALUES (2, '博客创作者', '1', '拥有大部分的权限', 1, '2019-06-27 09:38:32', 1, NULL, 'Y');
INSERT INTO `role` VALUES (3, '博客参与者', '1', '拥有自己的知识库操作权限', 1, '2019-07-13 04:57:56', NULL, NULL, 'N');
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
) ENGINE = InnoDB AUTO_INCREMENT = 179 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role_resource
-- ----------------------------
INSERT INTO `role_resource` VALUES (7, 1, 1, 1, '2019-06-27 09:37:51', NULL, NULL);
INSERT INTO `role_resource` VALUES (8, 1, 2, 1, '2019-06-27 09:37:51', NULL, NULL);
INSERT INTO `role_resource` VALUES (9, 1, 3, 1, '2019-06-27 09:37:51', NULL, NULL);
INSERT INTO `role_resource` VALUES (10, 1, 7, 1, '2019-06-27 09:37:51', NULL, NULL);
INSERT INTO `role_resource` VALUES (11, 1, 8, 1, '2019-06-27 09:37:51', NULL, NULL);
INSERT INTO `role_resource` VALUES (12, 1, 9, 1, '2019-06-27 09:37:51', NULL, NULL);
INSERT INTO `role_resource` VALUES (13, 1, 10, 1, '2019-06-27 09:37:51', NULL, NULL);
INSERT INTO `role_resource` VALUES (14, 1, 4, 1, '2019-06-27 09:37:51', NULL, NULL);
INSERT INTO `role_resource` VALUES (15, 1, 19, 1, '2019-06-27 09:37:51', NULL, NULL);
INSERT INTO `role_resource` VALUES (16, 1, 20, 1, '2019-06-27 09:37:51', NULL, NULL);
INSERT INTO `role_resource` VALUES (17, 1, 21, 1, '2019-06-27 09:37:51', NULL, NULL);
INSERT INTO `role_resource` VALUES (18, 1, 22, 1, '2019-06-27 09:37:51', NULL, NULL);
INSERT INTO `role_resource` VALUES (19, 1, 5, 1, '2019-06-27 09:37:51', NULL, NULL);
INSERT INTO `role_resource` VALUES (20, 1, 23, 1, '2019-06-27 09:37:51', NULL, NULL);
INSERT INTO `role_resource` VALUES (21, 1, 24, 1, '2019-06-27 09:37:51', NULL, NULL);
INSERT INTO `role_resource` VALUES (22, 1, 25, 1, '2019-06-27 09:37:51', NULL, NULL);
INSERT INTO `role_resource` VALUES (23, 1, 26, 1, '2019-06-27 09:37:51', NULL, NULL);
INSERT INTO `role_resource` VALUES (24, 1, 27, 1, '2019-06-27 09:37:51', NULL, NULL);
INSERT INTO `role_resource` VALUES (25, 1, 6, 1, '2019-06-27 09:37:51', NULL, NULL);
INSERT INTO `role_resource` VALUES (26, 1, 28, 1, '2019-06-27 09:37:51', NULL, NULL);
INSERT INTO `role_resource` VALUES (27, 1, 29, 1, '2019-06-27 09:37:51', NULL, NULL);
INSERT INTO `role_resource` VALUES (28, 1, 30, 1, '2019-06-27 09:37:51', NULL, NULL);
INSERT INTO `role_resource` VALUES (29, 1, 31, 1, '2019-06-27 09:37:51', NULL, NULL);
INSERT INTO `role_resource` VALUES (30, 1, 32, 1, '2019-06-27 09:37:51', NULL, NULL);
INSERT INTO `role_resource` VALUES (31, 1, 14, 1, '2019-06-27 09:37:51', NULL, NULL);
INSERT INTO `role_resource` VALUES (32, 1, 15, 1, '2019-06-27 09:37:51', NULL, NULL);
INSERT INTO `role_resource` VALUES (33, 1, 16, 1, '2019-06-27 09:37:51', NULL, NULL);
INSERT INTO `role_resource` VALUES (34, 1, 17, 1, '2019-06-27 09:37:51', NULL, NULL);
INSERT INTO `role_resource` VALUES (35, 1, 18, 1, '2019-06-27 09:37:51', NULL, NULL);
INSERT INTO `role_resource` VALUES (36, 1, 33, 1, '2019-06-27 09:37:51', NULL, NULL);
INSERT INTO `role_resource` VALUES (37, 1, 34, 1, '2019-06-27 09:37:51', NULL, NULL);
INSERT INTO `role_resource` VALUES (38, 1, 35, 1, '2019-06-27 09:37:52', NULL, NULL);
INSERT INTO `role_resource` VALUES (39, 1, 36, 1, '2019-06-27 09:37:52', NULL, NULL);
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
INSERT INTO `role_resource` VALUES (147, 3, 37, 1, '2019-07-14 13:52:56', NULL, NULL);
INSERT INTO `role_resource` VALUES (148, 3, 38, 1, '2019-07-14 13:52:56', NULL, NULL);
INSERT INTO `role_resource` VALUES (149, 3, 39, 1, '2019-07-14 13:52:57', NULL, NULL);
INSERT INTO `role_resource` VALUES (150, 3, 40, 1, '2019-07-14 13:52:57', NULL, NULL);
INSERT INTO `role_resource` VALUES (151, 3, 65, 1, '2019-07-14 13:52:57', NULL, NULL);
INSERT INTO `role_resource` VALUES (152, 3, 66, 1, '2019-07-14 13:52:57', NULL, NULL);
INSERT INTO `role_resource` VALUES (153, 3, 68, 1, '2019-07-14 13:52:57', NULL, NULL);
INSERT INTO `role_resource` VALUES (154, 3, 44, 1, '2019-07-14 13:52:57', NULL, NULL);
INSERT INTO `role_resource` VALUES (155, 3, 69, 1, '2019-07-14 13:52:57', NULL, NULL);
INSERT INTO `role_resource` VALUES (156, 3, 70, 1, '2019-07-14 13:52:57', NULL, NULL);
INSERT INTO `role_resource` VALUES (157, 3, 71, 1, '2019-07-14 13:52:57', NULL, NULL);
INSERT INTO `role_resource` VALUES (158, 3, 45, 1, '2019-07-14 13:52:57', NULL, NULL);
INSERT INTO `role_resource` VALUES (159, 3, 72, 1, '2019-07-14 13:52:57', NULL, NULL);
INSERT INTO `role_resource` VALUES (160, 3, 73, 1, '2019-07-14 13:52:57', NULL, NULL);
INSERT INTO `role_resource` VALUES (161, 3, 74, 1, '2019-07-14 13:52:57', NULL, NULL);
INSERT INTO `role_resource` VALUES (162, 3, 76, 1, '2019-07-14 13:52:57', NULL, NULL);
INSERT INTO `role_resource` VALUES (163, 3, 78, 1, '2019-07-14 13:52:57', NULL, NULL);
INSERT INTO `role_resource` VALUES (164, 3, 46, 1, '2019-07-14 13:52:57', NULL, NULL);
INSERT INTO `role_resource` VALUES (165, 3, 47, 1, '2019-07-14 13:52:57', NULL, NULL);
INSERT INTO `role_resource` VALUES (166, 3, 48, 1, '2019-07-14 13:52:57', NULL, NULL);
INSERT INTO `role_resource` VALUES (167, 3, 41, 1, '2019-07-14 13:52:57', NULL, NULL);
INSERT INTO `role_resource` VALUES (168, 3, 42, 1, '2019-07-14 13:52:57', NULL, NULL);
INSERT INTO `role_resource` VALUES (169, 3, 43, 1, '2019-07-14 13:52:57', NULL, NULL);
INSERT INTO `role_resource` VALUES (170, 3, 61, 1, '2019-07-14 13:52:57', NULL, NULL);
INSERT INTO `role_resource` VALUES (171, 3, 62, 1, '2019-07-14 13:52:57', NULL, NULL);
INSERT INTO `role_resource` VALUES (172, 3, 81, 1, '2019-07-14 13:52:57', NULL, NULL);
INSERT INTO `role_resource` VALUES (173, 3, 90, 1, '2019-07-14 13:52:57', NULL, NULL);
INSERT INTO `role_resource` VALUES (174, 3, 63, 1, '2019-07-14 13:52:57', NULL, NULL);
INSERT INTO `role_resource` VALUES (175, 3, 64, 1, '2019-07-14 13:52:57', NULL, NULL);
INSERT INTO `role_resource` VALUES (176, 3, 82, 1, '2019-07-14 13:52:57', NULL, NULL);
INSERT INTO `role_resource` VALUES (177, 3, 86, 1, '2019-07-14 13:52:57', NULL, NULL);
INSERT INTO `role_resource` VALUES (178, 3, 88, 1, '2019-07-14 13:52:57', NULL, NULL);

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
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES (1, 1, 1, 1, '2019-06-27 09:34:54');
INSERT INTO `user_role` VALUES (3, 2, 2, 1, '2019-07-13 05:45:36');
INSERT INTO `user_role` VALUES (4, 2, 1, 1, '2019-07-13 05:45:36');
INSERT INTO `user_role` VALUES (5, 2, 3, 1, '2019-07-13 05:45:36');
INSERT INTO `user_role` VALUES (6, 2, 4, 1, '2019-07-13 05:45:36');
INSERT INTO `user_role` VALUES (7, 3, 3, 1, '2019-07-13 05:46:28');

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
