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

 Date: 06/12/2019 16:26:27
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for feedback_user
-- ----------------------------
DROP TABLE IF EXISTS `feedback_user`;
CREATE TABLE `feedback_user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键,自增长',
  `ctime` datetime(0) NOT NULL COMMENT '添加时间',
  `utime` datetime(0) DEFAULT NULL COMMENT '更新时间',
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  `nick_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用户昵称',
  `avatar` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '头像地址',
  `mobile` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '手机',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '内容',
  `picture_url` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT '图片地址',
  `picture_num` int(2) DEFAULT NULL COMMENT '图片数',
  `origin` int(1) DEFAULT NULL COMMENT '消息来源：1 移动用户端,2 移动管理端 ',
  `sno` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '设备序列号',
  `mac` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'MAC地址',
  `recipient_id` int(11) DEFAULT NULL COMMENT '收件人id',
  `recipient_name` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '收件人姓名',
  `is_read` int(1) DEFAULT 0 COMMENT '是否已读：0 未读，1已读',
  `type` int(1) DEFAULT 1 COMMENT '反馈类型，1设备使用，2滤网问题，3App使用',
  `status` int(1) DEFAULT 0 COMMENT '反馈状态，0未处理，1已处理',
  `is_deleted` int(1) DEFAULT 0 COMMENT '是否删除，0未删除，1已删除',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '处理备注',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `recipient_id_index`(`recipient_id`) USING BTREE,
  INDEX `sno_index`(`sno`) USING BTREE,
  INDEX `mac_index`(`mac`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '问题反馈表' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
