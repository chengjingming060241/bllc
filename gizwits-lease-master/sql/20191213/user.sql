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

 Date: 13/12/2019 18:16:02
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键,',
  `username` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户名',
  `ctime` datetime(0) NOT NULL COMMENT '添加时间',
  `utime` datetime(0) DEFAULT NULL COMMENT '更新时间',
  `nickname` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '别名',
  `openid` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '微信用户的unionId',
  `third_party` int(2) DEFAULT NULL COMMENT '第三方平台,1:微信 2:支付宝 3:百度 4:新浪',
  `password` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '密码',
  `mobile` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '手机,绑定时程序上控制唯一',
  `email` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '电子邮件,绑定时程序上控制唯一',
  `gender` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '性别',
  `avatar` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '头像地址',
  `birthday` datetime(0) DEFAULT NULL COMMENT '生日',
  `province` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '所属省份',
  `city` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '所属城市',
  `address` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '详细地址',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
  `sys_user_id` int(11) DEFAULT NULL COMMENT '微信用户所属运营商用户ID',
  `status` int(2) NOT NULL DEFAULT 1 COMMENT '用户状态,1:正常 2:黑名单 ',
  `move_in_black_time` datetime(0) DEFAULT NULL COMMENT '移入黑名单时间',
  `move_out_black_time` datetime(0) DEFAULT NULL COMMENT '移出黑名单时间',
  `authorization_time` datetime(0) DEFAULT NULL COMMENT '授权时间',
  `alipay_unionid` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '支付宝用户ID',
  `sina_unionid` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '微博用户ID',
  `baidu_unionid` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '百度用户ID',
  `code` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '验证码',
  `info_state` int(11) DEFAULT NULL COMMENT '用户信息状态 1:由用户编辑的用户信息 2:从第三方获取的用户信息',
  `last_login_time` datetime(0) NOT NULL COMMENT '最后一次登录时间',
  `is_deleted` int(1) DEFAULT 0 COMMENT '是否删除：0未删除 1已删除',
  `tencent_unionid` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '腾讯用户ID',
  `tencent_nickname` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '腾讯用户昵称',
  `wx_nickname` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '微信用户昵称',
  `first_buy_time` datetime(0) DEFAULT NULL COMMENT '第一次购买设备时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `index_username`(`username`, `id`) USING BTREE,
  INDEX `index_openId`(`openid`) USING BTREE,
  INDEX `index_mobile`(`mobile`) USING BTREE,
  INDEX `index_sys_user_id`(`sys_user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '用户表,不要前缀,因为用户模块计划抽象成通用功能' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
