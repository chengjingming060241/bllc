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

 Date: 04/12/2019 09:57:00
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for device_stock
-- ----------------------------
DROP TABLE IF EXISTS `device_stock`;
CREATE TABLE `device_stock`  (
  `sno` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '设备序列号',
  `ctime` datetime(0) NULL DEFAULT NULL,
  `utime` datetime(0) NULL DEFAULT NULL,
  `mac` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sn1` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '控制器码',
  `sn2` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `imei` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sweep_code_status` int(1) NULL DEFAULT NULL COMMENT '扫码状态',
  `operator_id` int(10) NULL DEFAULT NULL COMMENT '经办人的系统账号',
  `operator_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '经办人',
  `warehousing_id` int(10) NULL DEFAULT NULL COMMENT '入库员的系统账号',
  `warehousing_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '入库员',
  `out_of_stock_id` int(10) NULL DEFAULT NULL COMMENT '出库员的系统账号',
  `out_of_stock_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '出库员',
  `launch_area_id` int(10) NULL DEFAULT NULL COMMENT '仓库ID',
  `launch_area_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '仓库名称',
  `product_id` int(10) NULL DEFAULT NULL COMMENT '所属品类id',
  `product_name` int(50) NULL DEFAULT NULL COMMENT '所属品类名称',
  `product_category_id` int(10) NULL DEFAULT NULL COMMENT '所属产品Id',
  `batch` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '入库批次',
  `out_batch` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '出库批次',
  `agent_id` int(10) NULL DEFAULT NULL COMMENT '经销商ID',
  `sys_user_id` int(10) NULL DEFAULT NULL COMMENT '创建人',
  `entry_time` datetime(0) NULL DEFAULT NULL COMMENT '入库时间',
  `shift_out_time` datetime(0) NULL DEFAULT NULL COMMENT '出库时间',
  `sweep_code_time` datetime(0) NULL DEFAULT NULL COMMENT '扫码时间',
  `control_type` bit(1) NULL DEFAULT NULL COMMENT '设备控制器类型',
  `supplier_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '供应商',
  `remarks` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `is_deleted` int(1) NULL DEFAULT NULL COMMENT ' 库存删除标识，0：未删除，1：已删除',
  `is_deleted_put` int(1) NULL DEFAULT NULL COMMENT ' 入库删除标识，0：未删除，1：已删除',
  `is_deleted_out` int(1) NULL DEFAULT NULL COMMENT '出库删除标识，0：未删除，1：已删除',
  PRIMARY KEY (`sno`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
