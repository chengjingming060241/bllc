/*
 Navicat Premium Data Transfer

 Source Server         : java
 Source Server Type    : MySQL
 Source Server Version : 50723
 Source Host           : localhost:3306
 Source Schema         : brown

 Target Server Type    : MySQL
 Target Server Version : 50723
 File Encoding         : 65001

 Date: 17/12/2019 16:08:00
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for user_device
-- ----------------------------
DROP TABLE IF EXISTS `user_device`;
CREATE TABLE `user_device`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键,自增长',
  `ctime` datetime(0) NOT NULL COMMENT '添加时间',
  `utime` datetime(0) DEFAULT NULL COMMENT '更新时间',
  `sno` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '对应设备序列号',
  `mac` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '对应设备MAC',
  `wechat_device_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '微信设备ID',
  `openid` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'openId',
  `user_id` int(11) NOT NULL COMMENT '所属用户id',
  `is_bind` int(1) DEFAULT 1 COMMENT '是否绑定',
  `owner_id` int(11) DEFAULT NULL COMMENT '拥有者经销商或者运营商',
  `is_deleted` int(1) DEFAULT 0 COMMENT '是否删除：0:未删除 1：删除',
  `mobile` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `is_manager` int(1) DEFAULT NULL,
  `room_id` int(11) DEFAULT NULL COMMENT '房间id',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `index_sno`(`sno`) USING BTREE,
  INDEX `index_openId`(`openid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '用户绑定设备表' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
