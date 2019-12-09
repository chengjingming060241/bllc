/*
 Navicat Premium Data Transfer

 Source Server         : Test
 Source Server Type    : MySQL
 Source Server Version : 50723
 Source Host           : localhost:3306
 Source Schema         : brown

 Target Server Type    : MySQL
 Target Server Version : 50723
 File Encoding         : 65001

 Date: 09/12/2019 15:38:01
 新增字段 category_name
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tmall_link
-- ----------------------------
DROP TABLE IF EXISTS `tmall_link`;
CREATE TABLE `tmall_link`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ctime` datetime(0) NOT NULL COMMENT '添加时间',
  `utime` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `link_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '链接名称',
  `category_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '产品型号',
  `link_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '链接地址',
  `sys_user_id` int(10) NULL DEFAULT NULL COMMENT '操作人',
  `sys_user_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人名称',
  `remark` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
  `is_deleted` int(1) NULL DEFAULT 0 COMMENT '删除标识',
  `category_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '产品名称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
