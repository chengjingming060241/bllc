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

 Date: 24/12/2019 17:50:16
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for advertisement_display
-- ----------------------------
DROP TABLE IF EXISTS `advertisement_display`;
CREATE TABLE `advertisement_display`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键,自增长',
  `ctime` datetime(0) NOT NULL COMMENT '添加时间',
  `utime` datetime(0) DEFAULT NULL COMMENT '更新时间',
  `picture` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '广告图片',
  `show_time` int(10) DEFAULT NULL COMMENT '展示时间，单位（秒）',
  `sys_user_id` int(11) DEFAULT NULL COMMENT '创建人',
  `sys_user_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建人名称',
  `is_deleted` int(1) NOT NULL DEFAULT 0 COMMENT '是否删除：0,否；1,是',
  `url` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '跳转链接',
  `name` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '广告名',
  `type` int(1) DEFAULT NULL COMMENT '展示区域，1设备列表，2滤材',
  `sort` int(1) DEFAULT NULL COMMENT '排序字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '广告展示表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for agent
-- ----------------------------
DROP TABLE IF EXISTS `agent`;
CREATE TABLE `agent`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键,自增长',
  `ctime` datetime(0) NOT NULL COMMENT '添加时间',
  `utime` datetime(0) DEFAULT NULL COMMENT '更新时间',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '代理商名称',
  `industry` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '所属行业',
  `web_site` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '公司官网',
  `logo_url` varchar(150) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '代理商logo url',
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '企业电话',
  `mobile` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '手机号码',
  `contact` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '联系人',
  `department` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '部门',
  `email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '电子邮件',
  `qq` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'QQ号码',
  `province` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '省',
  `city` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '市',
  `area` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '区/县',
  `address` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '详细地址',
  `parent_agent_id` int(11) DEFAULT NULL COMMENT '父级代理商ID',
  `sys_account_id` int(11) DEFAULT NULL COMMENT '代理商对应的系统用户id',
  `sys_user_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建人名称',
  `status` int(1) NOT NULL DEFAULT 1 COMMENT '状态：1,待分配 2,正常 3,暂停',
  `sys_user_id` int(11) DEFAULT NULL COMMENT '创建人',
  `is_deleted` int(1) NOT NULL DEFAULT 0 COMMENT '是否删除，0,未删除 1,已删除',
  `stock_number` int(10) DEFAULT NULL COMMENT '库存量',
  `cover_level` int(1) DEFAULT 1 COMMENT '代理商级别：1，国家级；2，省级；3，市级；4，区县级',
  `remarks` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `agent_sys_account_id_uindex`(`sys_account_id`) USING BTREE,
  INDEX `idx_sys_user_id`(`sys_user_id`) USING BTREE,
  INDEX `idx_parent_agent_id`(`parent_agent_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 67 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '代理商表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of agent
-- ----------------------------
INSERT INTO `agent` VALUES (66, '2019-11-15 16:59:32', '2019-11-15 17:54:00', '苏州经销商', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '江苏省', '苏州市', '吴中区', '创意产业园', NULL, NULL, '厂商', 2, 596, 0, NULL, 1, NULL);

-- ----------------------------
-- Table structure for app_version
-- ----------------------------
DROP TABLE IF EXISTS `app_version`;
CREATE TABLE `app_version`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键,自增长',
  `ctime` datetime(0) NOT NULL COMMENT '添加时间',
  `utime` datetime(0) DEFAULT NULL COMMENT '更新时间',
  `version` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '版本号',
  `url` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '下载地址',
  `type` int(1) DEFAULT 1 COMMENT 'app：1用户端 2管理端',
  `description` varchar(450) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '更新说明',
  `is_deleted` int(1) NOT NULL DEFAULT 0 COMMENT '是否删除：0未删除 1删除',
  `last_version` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '上个版本号',
  `sys_user_id` int(11) DEFAULT NULL COMMENT '创建人',
  `sys_user_name` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建人姓名',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = 'app版本记录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for card
-- ----------------------------
DROP TABLE IF EXISTS `card`;
CREATE TABLE `card`  (
  `card_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '微信卡券ID',
  `title` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '卡券名',
  `card_type` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '卡券类型。代金券：CASH; 折扣券：DISCOUNT;',
  `status` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '卡券状态 CARD_STATUS_NOT_VERIFY:待审核 CARD_STATUS_VERIFY_FAIL:审核失败 CARD_STATUS_VERIFY_OK:通过审核 CARD_STATUS_DELETE:卡券被商户删除 CARD_STATUS_DISPATCH:在公众平台投放过的卡券',
  `date_type` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '使用时间的类型 DATE_TYPE_FIX_TIME_RANGE 表示固定日期区间，DATE_TYPE_FIX_TERM表示固定时长（自领取后按天算）',
  `date_begin_timestamp` datetime(0) DEFAULT NULL COMMENT 'DATE_TYPE_FIX_TIME_RANGE时专用 ，表示起用时间。从1970年1月1日00:00:00至起用时间的秒数。（单位为秒）',
  `date_end_timestamp` datetime(0) DEFAULT NULL COMMENT 'DATE_TYPE_FIX_TIME_RANGE时专用 ，表示结束时间。（单位为秒）',
  `date_fixed_term` int(11) DEFAULT NULL COMMENT 'DATE_TYPE_FIX_TERM时专用 ，表示自领取后多少天内有效，领取后当天有效填写0。 （单位为天）',
  `date_fixed_begin_term` int(11) DEFAULT NULL COMMENT 'DATE_TYPE_FIX_TERM时专用 ，表示自领取后多少天开始生效。（单位为天）',
  `quantity` int(11) NOT NULL COMMENT '卡券现有库存的数量',
  `least_cost` int(11) DEFAULT NULL COMMENT '代金券专用，表示起用金额（单位为分）',
  `reduce_cost` int(11) DEFAULT NULL COMMENT '代金券专用，表示减免金额（单位为分）',
  `discount` int(11) DEFAULT NULL COMMENT '折扣券专用字段，表示打折额度（百分比）',
  `sys_user_id` int(11) NOT NULL COMMENT '卡券创建者, 系统用户ID',
  `dispatch_web` int(11) NOT NULL DEFAULT 0 COMMENT '微信投放 0:否, 1:是',
  `dispatch_app` int(11) NOT NULL DEFAULT 0 COMMENT 'APP投放 0:否, 1:是',
  `cover` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '卡券封面',
  `sequence` int(11) DEFAULT NULL COMMENT '卡券展示顺序',
  `product_id` int(11) DEFAULT NULL COMMENT '卡券适用产品ID, NULL为全部产品适用',
  `operator_ids` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '卡券适用运营商ID, NULL为全部运营商适用, 多个运营商ID使用,分隔',
  `ctime` datetime(0) NOT NULL COMMENT '添加时间',
  `utime` datetime(0) DEFAULT NULL COMMENT '更新时间',
  `sync_time` datetime(0) DEFAULT NULL COMMENT '同步时间',
  `receive_limit` int(11) DEFAULT NULL COMMENT '每人可领券的数量限制',
  `time_limit` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT '可用时段 JSON数组',
  PRIMARY KEY (`card_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '卡券' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for card_consume_record
-- ----------------------------
DROP TABLE IF EXISTS `card_consume_record`;
CREATE TABLE `card_consume_record`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL COMMENT '用户ID',
  `card_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '卡券ID',
  `consume_count` int(11) NOT NULL COMMENT '使用次数',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `user_id`(`user_id`, `card_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '卡券使用次数记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for card_event
-- ----------------------------
DROP TABLE IF EXISTS `card_event`;
CREATE TABLE `card_event`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `wx_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '卡券的微信号',
  `card_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '卡券ID',
  `user_openid` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '领券用户的openid',
  `code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '卡券code',
  `event_time` datetime(0) NOT NULL COMMENT '消息创建时间',
  `is_give_by_friend` int(11) NOT NULL COMMENT '是否为转赠领取，1代表是，0代表否',
  `friend_openid` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '代表发起转赠用户的openid',
  `old_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '代表转赠前的卡券code',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '微信卡券领取事件' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for card_receive_record
-- ----------------------------
DROP TABLE IF EXISTS `card_receive_record`;
CREATE TABLE `card_receive_record`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL COMMENT '用户ID',
  `card_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '卡券ID',
  `receive_count` int(11) NOT NULL COMMENT '领取次数',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `user_id`(`user_id`, `card_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '卡券领取次数记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for charge_setting
-- ----------------------------
DROP TABLE IF EXISTS `charge_setting`;
CREATE TABLE `charge_setting`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `ctime` datetime(0) NOT NULL COMMENT '创建时间',
  `utime` datetime(0) DEFAULT NULL COMMENT '更新时间',
  `money` double(12, 2) NOT NULL COMMENT '值',
  `product_id` int(11) DEFAULT NULL COMMENT '产品id',
  `sys_user_id` int(11) NOT NULL COMMENT '创建人',
  `sys_user_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '创建人名称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '充值设定' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for china_area
-- ----------------------------
DROP TABLE IF EXISTS `china_area`;
CREATE TABLE `china_area`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '省市县的名字',
  `code` int(6) UNSIGNED NOT NULL DEFAULT 0 COMMENT '行政区编码',
  `parent_code` int(6) UNSIGNED NOT NULL DEFAULT 0,
  `parent_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '' COMMENT '所属父级的中文名称',
  `is_leaf` int(11) DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_parent_code`(`parent_code`) USING BTREE,
  INDEX `idx_code`(`code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10490 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '全国省市行政编码表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of china_area
-- ----------------------------
INSERT INTO `china_area` VALUES (6884, '北京', 110000, 0, '中国', 0);
INSERT INTO `china_area` VALUES (6885, '北京城区', 110100, 110000, '北京市', 0);
INSERT INTO `china_area` VALUES (6886, '昌平区', 110114, 110100, '北京城区', 0);
INSERT INTO `china_area` VALUES (6887, '朝阳区', 110105, 110100, '北京城区', 0);
INSERT INTO `china_area` VALUES (6888, '大兴区', 110115, 110100, '北京城区', 0);
INSERT INTO `china_area` VALUES (6889, '东城区', 110101, 110100, '北京城区', 0);
INSERT INTO `china_area` VALUES (6890, '房山区', 110111, 110100, '北京城区', 0);
INSERT INTO `china_area` VALUES (6891, '丰台区', 110106, 110100, '北京城区', 0);
INSERT INTO `china_area` VALUES (6892, '海淀区', 110108, 110100, '北京城区', 0);
INSERT INTO `china_area` VALUES (6893, '怀柔区', 110116, 110100, '北京城区', 0);
INSERT INTO `china_area` VALUES (6894, '密云区', 110118, 110100, '北京城区', 0);
INSERT INTO `china_area` VALUES (6895, '门头沟区', 110109, 110100, '北京城区', 0);
INSERT INTO `china_area` VALUES (6896, '平谷区', 110117, 110100, '北京城区', 0);
INSERT INTO `china_area` VALUES (6897, '石景山区', 110107, 110100, '北京城区', 0);
INSERT INTO `china_area` VALUES (6898, '顺义区', 110113, 110100, '北京城区', 0);
INSERT INTO `china_area` VALUES (6899, '通州区', 110112, 110100, '北京城区', 0);
INSERT INTO `china_area` VALUES (6900, '西城区', 110102, 110100, '北京城区', 0);
INSERT INTO `china_area` VALUES (6901, '延庆区', 110119, 110100, '北京城区', 0);
INSERT INTO `china_area` VALUES (6902, '重庆', 500000, 0, '中国', 0);
INSERT INTO `china_area` VALUES (6903, '重庆郊县', 500200, 500000, '重庆市', 0);
INSERT INTO `china_area` VALUES (6904, '城口县', 500229, 500200, '重庆郊县', 0);
INSERT INTO `china_area` VALUES (6905, '垫江县', 500231, 500200, '重庆郊县', 0);
INSERT INTO `china_area` VALUES (6906, '丰都县', 500230, 500200, '重庆郊县', 0);
INSERT INTO `china_area` VALUES (6907, '奉节县', 500236, 500200, '重庆郊县', 0);
INSERT INTO `china_area` VALUES (6908, '梁平区', 500155, 500200, '重庆郊县', 0);
INSERT INTO `china_area` VALUES (6909, '彭水苗族土家族自治县', 500243, 500200, '重庆郊县', 0);
INSERT INTO `china_area` VALUES (6910, '石柱土家族自治县', 500240, 500200, '重庆郊县', 0);
INSERT INTO `china_area` VALUES (6911, '巫山县', 500237, 500200, '重庆郊县', 0);
INSERT INTO `china_area` VALUES (6912, '巫溪县', 500238, 500200, '重庆郊县', 0);
INSERT INTO `china_area` VALUES (6913, '武隆区', 500156, 500200, '重庆郊县', 0);
INSERT INTO `china_area` VALUES (6914, '秀山土家族苗族自治县', 500241, 500200, '重庆郊县', 0);
INSERT INTO `china_area` VALUES (6915, '酉阳土家族苗族自治县', 500242, 500200, '重庆郊县', 0);
INSERT INTO `china_area` VALUES (6916, '云阳县', 500235, 500200, '重庆郊县', 0);
INSERT INTO `china_area` VALUES (6917, '忠县', 500233, 500200, '重庆郊县', 0);
INSERT INTO `china_area` VALUES (6918, '重庆城区', 500100, 500000, '重庆市', 0);
INSERT INTO `china_area` VALUES (6919, '巴南区', 500113, 500100, '重庆城区', 0);
INSERT INTO `china_area` VALUES (6920, '北碚区', 500109, 500100, '重庆城区', 0);
INSERT INTO `china_area` VALUES (6921, '璧山区', 500120, 500100, '重庆城区', 0);
INSERT INTO `china_area` VALUES (6922, '大足区', 500111, 500100, '重庆城区', 0);
INSERT INTO `china_area` VALUES (6923, '大渡口区', 500104, 500100, '重庆城区', 0);
INSERT INTO `china_area` VALUES (6924, '涪陵区', 500102, 500100, '重庆城区', 0);
INSERT INTO `china_area` VALUES (6925, '合川区', 500117, 500100, '重庆城区', 0);
INSERT INTO `china_area` VALUES (6926, '江津区', 500116, 500100, '重庆城区', 0);
INSERT INTO `china_area` VALUES (6927, '江北区', 500105, 500100, '重庆城区', 0);
INSERT INTO `china_area` VALUES (6928, '九龙坡区', 500107, 500100, '重庆城区', 0);
INSERT INTO `china_area` VALUES (6929, '开州区', 500154, 500100, '重庆城区', 0);
INSERT INTO `china_area` VALUES (6930, '南岸区', 500108, 500100, '重庆城区', 0);
INSERT INTO `china_area` VALUES (6931, '南川区', 500119, 500100, '重庆城区', 0);
INSERT INTO `china_area` VALUES (6932, '綦江区', 500110, 500100, '重庆城区', 0);
INSERT INTO `china_area` VALUES (6933, '黔江区', 500114, 500100, '重庆城区', 0);
INSERT INTO `china_area` VALUES (6934, '荣昌区', 500153, 500100, '重庆城区', 0);
INSERT INTO `china_area` VALUES (6935, '沙坪坝区', 500106, 500100, '重庆城区', 0);
INSERT INTO `china_area` VALUES (6936, '潼南区', 500152, 500100, '重庆城区', 0);
INSERT INTO `china_area` VALUES (6937, '铜梁区', 500151, 500100, '重庆城区', 0);
INSERT INTO `china_area` VALUES (6938, '万州区', 500101, 500100, '重庆城区', 0);
INSERT INTO `china_area` VALUES (6939, '永川区', 500118, 500100, '重庆城区', 0);
INSERT INTO `china_area` VALUES (6940, '渝中区', 500103, 500100, '重庆城区', 0);
INSERT INTO `china_area` VALUES (6941, '渝北区', 500112, 500100, '重庆城区', 0);
INSERT INTO `china_area` VALUES (6942, '长寿区', 500115, 500100, '重庆城区', 0);
INSERT INTO `china_area` VALUES (6943, '澳门特别行政区', 820000, 0, '中国', 0);
INSERT INTO `china_area` VALUES (6944, '花王堂区', 820002, 820000, '澳门特别行政区', 0);
INSERT INTO `china_area` VALUES (6945, '望德堂区', 820003, 820000, '澳门特别行政区', 0);
INSERT INTO `china_area` VALUES (6946, '大堂区', 820004, 820000, '澳门特别行政区', 0);
INSERT INTO `china_area` VALUES (6947, '风顺堂区', 820005, 820000, '澳门特别行政区', 0);
INSERT INTO `china_area` VALUES (6948, '花地玛堂区', 820001, 820000, '澳门特别行政区', 0);
INSERT INTO `china_area` VALUES (6949, '嘉模堂区', 820006, 820000, '澳门特别行政区', 0);
INSERT INTO `china_area` VALUES (6950, '圣方济各堂区', 820008, 820000, '澳门特别行政区', 0);
INSERT INTO `china_area` VALUES (6951, '路凼填海区', 820007, 820000, '澳门特别行政区', 0);
INSERT INTO `china_area` VALUES (6952, '广东省', 440000, 0, '中国', 0);
INSERT INTO `china_area` VALUES (6953, '中山市', 442000, 440000, '广东省', 0);
INSERT INTO `china_area` VALUES (6954, '南头镇', 442001, 442000, '中山市', 0);
INSERT INTO `china_area` VALUES (6955, '小榄镇', 442002, 442000, '中山市', 0);
INSERT INTO `china_area` VALUES (6956, '东凤镇', 442003, 442000, '中山市', 0);
INSERT INTO `china_area` VALUES (6957, '神湾镇', 442004, 442000, '中山市', 0);
INSERT INTO `china_area` VALUES (6958, '南朗镇', 442005, 442000, '中山市', 0);
INSERT INTO `china_area` VALUES (6959, '环城街道', 442006, 442000, '中山市', 0);
INSERT INTO `china_area` VALUES (6960, '坦洲镇', 442007, 442000, '中山市', 0);
INSERT INTO `china_area` VALUES (6961, '中山港街道', 442008, 442000, '中山市', 0);
INSERT INTO `china_area` VALUES (6962, '南区街道', 442009, 442000, '中山市', 0);
INSERT INTO `china_area` VALUES (6963, '五桂山街道', 442010, 442000, '中山市', 0);
INSERT INTO `china_area` VALUES (6964, '石歧区街道', 442011, 442000, '中山市', 0);
INSERT INTO `china_area` VALUES (6965, '西区街道', 442012, 442000, '中山市', 0);
INSERT INTO `china_area` VALUES (6966, '东区街道', 442013, 442000, '中山市', 0);
INSERT INTO `china_area` VALUES (6967, '三乡镇', 442014, 442000, '中山市', 0);
INSERT INTO `china_area` VALUES (6968, '大涌镇', 442015, 442000, '中山市', 0);
INSERT INTO `china_area` VALUES (6969, '黄圃镇', 442016, 442000, '中山市', 0);
INSERT INTO `china_area` VALUES (6970, '石岐区街道', 442017, 442000, '中山市', 0);
INSERT INTO `china_area` VALUES (6971, '沙溪镇', 442018, 442000, '中山市', 0);
INSERT INTO `china_area` VALUES (6972, '东升镇', 442019, 442000, '中山市', 0);
INSERT INTO `china_area` VALUES (6973, '阜沙镇', 442020, 442000, '中山市', 0);
INSERT INTO `china_area` VALUES (6974, '民众镇', 442021, 442000, '中山市', 0);
INSERT INTO `china_area` VALUES (6975, '横栏镇', 442022, 442000, '中山市', 0);
INSERT INTO `china_area` VALUES (6976, '三角镇', 442023, 442000, '中山市', 0);
INSERT INTO `china_area` VALUES (6977, '板芙镇', 442024, 442000, '中山市', 0);
INSERT INTO `china_area` VALUES (6978, '港口镇', 442025, 442000, '中山市', 0);
INSERT INTO `china_area` VALUES (6979, '古镇镇', 442026, 442000, '中山市', 0);
INSERT INTO `china_area` VALUES (6980, '东沙群岛', 442100, 440000, '广东省', 0);
INSERT INTO `china_area` VALUES (6981, '潮州市', 445100, 440000, '广东省', 0);
INSERT INTO `china_area` VALUES (6982, '潮安区', 445103, 445100, '潮州市', 0);
INSERT INTO `china_area` VALUES (6983, '饶平县', 445122, 445100, '潮州市', 0);
INSERT INTO `china_area` VALUES (6984, '湘桥区', 445102, 445100, '潮州市', 0);
INSERT INTO `china_area` VALUES (6985, '东莞市', 441900, 440000, '广东省', 0);
INSERT INTO `china_area` VALUES (6986, '东坑镇', 441901, 441900, '东莞市', 0);
INSERT INTO `china_area` VALUES (6987, '企石镇', 441902, 441900, '东莞市', 0);
INSERT INTO `china_area` VALUES (6988, '大岭山镇', 441903, 441900, '东莞市', 0);
INSERT INTO `china_area` VALUES (6989, '沙田镇', 441904, 441900, '东莞市', 0);
INSERT INTO `china_area` VALUES (6990, '道滘镇', 441905, 441900, '东莞市', 0);
INSERT INTO `china_area` VALUES (6991, '虎门镇', 441906, 441900, '东莞市', 0);
INSERT INTO `china_area` VALUES (6992, '望牛墩镇', 441907, 441900, '东莞市', 0);
INSERT INTO `china_area` VALUES (6993, '南城街道', 441908, 441900, '东莞市', 0);
INSERT INTO `china_area` VALUES (6994, '莞城街道', 441909, 441900, '东莞市', 0);
INSERT INTO `china_area` VALUES (6995, '东城街道', 441910, 441900, '东莞市', 0);
INSERT INTO `china_area` VALUES (6996, '万江街道', 441911, 441900, '东莞市', 0);
INSERT INTO `china_area` VALUES (6997, '石排镇', 441912, 441900, '东莞市', 0);
INSERT INTO `china_area` VALUES (6998, '厚街镇', 441913, 441900, '东莞市', 0);
INSERT INTO `china_area` VALUES (6999, '长安镇', 441914, 441900, '东莞市', 0);
INSERT INTO `china_area` VALUES (7000, '石碣镇', 441915, 441900, '东莞市', 0);
INSERT INTO `china_area` VALUES (7001, '横沥镇', 441916, 441900, '东莞市', 0);
INSERT INTO `china_area` VALUES (7002, '大朗镇', 441917, 441900, '东莞市', 0);
INSERT INTO `china_area` VALUES (7003, '樟木头镇', 441918, 441900, '东莞市', 0);
INSERT INTO `china_area` VALUES (7004, '清溪镇', 441919, 441900, '东莞市', 0);
INSERT INTO `china_area` VALUES (7005, '麻涌镇', 441920, 441900, '东莞市', 0);
INSERT INTO `china_area` VALUES (7006, '茶山镇', 441921, 441900, '东莞市', 0);
INSERT INTO `china_area` VALUES (7007, '凤岗镇', 441922, 441900, '东莞市', 0);
INSERT INTO `china_area` VALUES (7008, '高埗镇', 441923, 441900, '东莞市', 0);
INSERT INTO `china_area` VALUES (7009, '中堂镇', 441924, 441900, '东莞市', 0);
INSERT INTO `china_area` VALUES (7010, '桥头镇', 441925, 441900, '东莞市', 0);
INSERT INTO `china_area` VALUES (7011, '谢岗镇', 441926, 441900, '东莞市', 0);
INSERT INTO `china_area` VALUES (7012, '黄江镇', 441927, 441900, '东莞市', 0);
INSERT INTO `china_area` VALUES (7013, '塘厦镇', 441928, 441900, '东莞市', 0);
INSERT INTO `china_area` VALUES (7014, '寮步镇', 441929, 441900, '东莞市', 0);
INSERT INTO `china_area` VALUES (7015, '洪梅镇', 441930, 441900, '东莞市', 0);
INSERT INTO `china_area` VALUES (7016, '常平镇', 441931, 441900, '东莞市', 0);
INSERT INTO `china_area` VALUES (7017, '石龙镇', 441932, 441900, '东莞市', 0);
INSERT INTO `china_area` VALUES (7018, '广州市', 440100, 440000, '广东省', 0);
INSERT INTO `china_area` VALUES (7019, '白云区', 440111, 440100, '广州市', 0);
INSERT INTO `china_area` VALUES (7020, '从化区', 440117, 440100, '广州市', 0);
INSERT INTO `china_area` VALUES (7021, '番禺区', 440113, 440100, '广州市', 0);
INSERT INTO `china_area` VALUES (7022, '海珠区', 440105, 440100, '广州市', 0);
INSERT INTO `china_area` VALUES (7023, '花都区', 440114, 440100, '广州市', 0);
INSERT INTO `china_area` VALUES (7024, '黄埔区', 440112, 440100, '广州市', 0);
INSERT INTO `china_area` VALUES (7025, '荔湾区', 440103, 440100, '广州市', 0);
INSERT INTO `china_area` VALUES (7026, '南沙区', 440115, 440100, '广州市', 0);
INSERT INTO `china_area` VALUES (7027, '天河区', 440106, 440100, '广州市', 0);
INSERT INTO `china_area` VALUES (7028, '越秀区', 440104, 440100, '广州市', 0);
INSERT INTO `china_area` VALUES (7029, '增城区', 440118, 440100, '广州市', 0);
INSERT INTO `china_area` VALUES (7030, '佛山市', 440600, 440000, '广东省', 0);
INSERT INTO `china_area` VALUES (7031, '禅城区', 440604, 440600, '佛山市', 0);
INSERT INTO `china_area` VALUES (7032, '高明区', 440608, 440600, '佛山市', 0);
INSERT INTO `china_area` VALUES (7033, '南海区', 440605, 440600, '佛山市', 0);
INSERT INTO `china_area` VALUES (7034, '三水区', 440607, 440600, '佛山市', 0);
INSERT INTO `china_area` VALUES (7035, '顺德区', 440606, 440600, '佛山市', 0);
INSERT INTO `china_area` VALUES (7036, '河源市', 441600, 440000, '广东省', 0);
INSERT INTO `china_area` VALUES (7037, '东源县', 441625, 441600, '河源市', 0);
INSERT INTO `china_area` VALUES (7038, '和平县', 441624, 441600, '河源市', 0);
INSERT INTO `china_area` VALUES (7039, '连平县', 441623, 441600, '河源市', 0);
INSERT INTO `china_area` VALUES (7040, '龙川县', 441622, 441600, '河源市', 0);
INSERT INTO `china_area` VALUES (7041, '源城区', 441602, 441600, '河源市', 0);
INSERT INTO `china_area` VALUES (7042, '紫金县', 441621, 441600, '河源市', 0);
INSERT INTO `china_area` VALUES (7043, '惠州市', 441300, 440000, '广东省', 0);
INSERT INTO `china_area` VALUES (7044, '博罗县', 441322, 441300, '惠州市', 0);
INSERT INTO `china_area` VALUES (7045, '惠东县', 441323, 441300, '惠州市', 0);
INSERT INTO `china_area` VALUES (7046, '惠阳区', 441303, 441300, '惠州市', 0);
INSERT INTO `china_area` VALUES (7047, '惠城区', 441302, 441300, '惠州市', 0);
INSERT INTO `china_area` VALUES (7048, '龙门县', 441324, 441300, '惠州市', 0);
INSERT INTO `china_area` VALUES (7049, '揭阳市', 445200, 440000, '广东省', 0);
INSERT INTO `china_area` VALUES (7050, '惠来县', 445224, 445200, '揭阳市', 0);
INSERT INTO `china_area` VALUES (7051, '揭西县', 445222, 445200, '揭阳市', 0);
INSERT INTO `china_area` VALUES (7052, '揭东区', 445203, 445200, '揭阳市', 0);
INSERT INTO `china_area` VALUES (7053, '普宁市', 445281, 445200, '揭阳市', 0);
INSERT INTO `china_area` VALUES (7054, '榕城区', 445202, 445200, '揭阳市', 0);
INSERT INTO `china_area` VALUES (7055, '江门市', 440700, 440000, '广东省', 0);
INSERT INTO `china_area` VALUES (7056, '恩平市', 440785, 440700, '江门市', 0);
INSERT INTO `china_area` VALUES (7057, '鹤山市', 440784, 440700, '江门市', 0);
INSERT INTO `china_area` VALUES (7058, '江海区', 440704, 440700, '江门市', 0);
INSERT INTO `china_area` VALUES (7059, '开平市', 440783, 440700, '江门市', 0);
INSERT INTO `china_area` VALUES (7060, '蓬江区', 440703, 440700, '江门市', 0);
INSERT INTO `china_area` VALUES (7061, '台山市', 440781, 440700, '江门市', 0);
INSERT INTO `china_area` VALUES (7062, '新会区', 440705, 440700, '江门市', 0);
INSERT INTO `china_area` VALUES (7063, '茂名市', 440900, 440000, '广东省', 0);
INSERT INTO `china_area` VALUES (7064, '电白区', 440904, 440900, '茂名市', 0);
INSERT INTO `china_area` VALUES (7065, '高州市', 440981, 440900, '茂名市', 0);
INSERT INTO `china_area` VALUES (7066, '化州市', 440982, 440900, '茂名市', 0);
INSERT INTO `china_area` VALUES (7067, '茂南区', 440902, 440900, '茂名市', 0);
INSERT INTO `china_area` VALUES (7068, '信宜市', 440983, 440900, '茂名市', 0);
INSERT INTO `china_area` VALUES (7069, '梅州市', 441400, 440000, '广东省', 0);
INSERT INTO `china_area` VALUES (7070, '大埔县', 441422, 441400, '梅州市', 0);
INSERT INTO `china_area` VALUES (7071, '丰顺县', 441423, 441400, '梅州市', 0);
INSERT INTO `china_area` VALUES (7072, '蕉岭县', 441427, 441400, '梅州市', 0);
INSERT INTO `china_area` VALUES (7073, '梅江区', 441402, 441400, '梅州市', 0);
INSERT INTO `china_area` VALUES (7074, '梅县区', 441403, 441400, '梅州市', 0);
INSERT INTO `china_area` VALUES (7075, '平远县', 441426, 441400, '梅州市', 0);
INSERT INTO `china_area` VALUES (7076, '五华县', 441424, 441400, '梅州市', 0);
INSERT INTO `china_area` VALUES (7077, '兴宁市', 441481, 441400, '梅州市', 0);
INSERT INTO `china_area` VALUES (7078, '汕尾市', 441500, 440000, '广东省', 0);
INSERT INTO `china_area` VALUES (7079, '城区', 441502, 441500, '汕尾市', 0);
INSERT INTO `china_area` VALUES (7080, '海丰县', 441521, 441500, '汕尾市', 0);
INSERT INTO `china_area` VALUES (7081, '陆河县', 441523, 441500, '汕尾市', 0);
INSERT INTO `china_area` VALUES (7082, '陆丰市', 441581, 441500, '汕尾市', 0);
INSERT INTO `china_area` VALUES (7083, '汕头市', 440500, 440000, '广东省', 0);
INSERT INTO `china_area` VALUES (7084, '潮南区', 440514, 440500, '汕头市', 0);
INSERT INTO `china_area` VALUES (7085, '潮阳区', 440513, 440500, '汕头市', 0);
INSERT INTO `china_area` VALUES (7086, '澄海区', 440515, 440500, '汕头市', 0);
INSERT INTO `china_area` VALUES (7087, '濠江区', 440512, 440500, '汕头市', 0);
INSERT INTO `china_area` VALUES (7088, '金平区', 440511, 440500, '汕头市', 0);
INSERT INTO `china_area` VALUES (7089, '龙湖区', 440507, 440500, '汕头市', 0);
INSERT INTO `china_area` VALUES (7090, '南澳县', 440523, 440500, '汕头市', 0);
INSERT INTO `china_area` VALUES (7091, '韶关市', 440200, 440000, '广东省', 0);
INSERT INTO `china_area` VALUES (7092, '乐昌市', 440281, 440200, '韶关市', 0);
INSERT INTO `china_area` VALUES (7093, '南雄市', 440282, 440200, '韶关市', 0);
INSERT INTO `china_area` VALUES (7094, '曲江区', 440205, 440200, '韶关市', 0);
INSERT INTO `china_area` VALUES (7095, '仁化县', 440224, 440200, '韶关市', 0);
INSERT INTO `china_area` VALUES (7096, '乳源瑶族自治县', 440232, 440200, '韶关市', 0);
INSERT INTO `china_area` VALUES (7097, '始兴县', 440222, 440200, '韶关市', 0);
INSERT INTO `china_area` VALUES (7098, '翁源县', 440229, 440200, '韶关市', 0);
INSERT INTO `china_area` VALUES (7099, '武江区', 440203, 440200, '韶关市', 0);
INSERT INTO `china_area` VALUES (7100, '新丰县', 440233, 440200, '韶关市', 0);
INSERT INTO `china_area` VALUES (7101, '浈江区', 440204, 440200, '韶关市', 0);
INSERT INTO `china_area` VALUES (7102, '清远市', 441800, 440000, '广东省', 0);
INSERT INTO `china_area` VALUES (7103, '佛冈县', 441821, 441800, '清远市', 0);
INSERT INTO `china_area` VALUES (7104, '连南瑶族自治县', 441826, 441800, '清远市', 0);
INSERT INTO `china_area` VALUES (7105, '连山壮族瑶族自治县', 441825, 441800, '清远市', 0);
INSERT INTO `china_area` VALUES (7106, '连州市', 441882, 441800, '清远市', 0);
INSERT INTO `china_area` VALUES (7107, '清城区', 441802, 441800, '清远市', 0);
INSERT INTO `china_area` VALUES (7108, '清新区', 441803, 441800, '清远市', 0);
INSERT INTO `china_area` VALUES (7109, '阳山县', 441823, 441800, '清远市', 0);
INSERT INTO `china_area` VALUES (7110, '英德市', 441881, 441800, '清远市', 0);
INSERT INTO `china_area` VALUES (7111, '深圳市', 440300, 440000, '广东省', 0);
INSERT INTO `china_area` VALUES (7112, '宝安区', 440306, 440300, '深圳市', 0);
INSERT INTO `china_area` VALUES (7113, '福田区', 440304, 440300, '深圳市', 0);
INSERT INTO `china_area` VALUES (7114, '龙华区', 440309, 440300, '深圳市', 0);
INSERT INTO `china_area` VALUES (7115, '龙岗区', 440307, 440300, '深圳市', 0);
INSERT INTO `china_area` VALUES (7116, '罗湖区', 440303, 440300, '深圳市', 0);
INSERT INTO `china_area` VALUES (7117, '南山区', 440305, 440300, '深圳市', 0);
INSERT INTO `china_area` VALUES (7118, '坪山区', 440310, 440300, '深圳市', 0);
INSERT INTO `china_area` VALUES (7119, '盐田区', 440308, 440300, '深圳市', 0);
INSERT INTO `china_area` VALUES (7120, '阳江市', 441700, 440000, '广东省', 0);
INSERT INTO `china_area` VALUES (7121, '江城区', 441702, 441700, '阳江市', 0);
INSERT INTO `china_area` VALUES (7122, '阳西县', 441721, 441700, '阳江市', 0);
INSERT INTO `china_area` VALUES (7123, '阳东区', 441704, 441700, '阳江市', 0);
INSERT INTO `china_area` VALUES (7124, '阳春市', 441781, 441700, '阳江市', 0);
INSERT INTO `china_area` VALUES (7125, '云浮市', 445300, 440000, '广东省', 0);
INSERT INTO `china_area` VALUES (7126, '罗定市', 445381, 445300, '云浮市', 0);
INSERT INTO `china_area` VALUES (7127, '新兴县', 445321, 445300, '云浮市', 0);
INSERT INTO `china_area` VALUES (7128, '郁南县', 445322, 445300, '云浮市', 0);
INSERT INTO `china_area` VALUES (7129, '云安区', 445303, 445300, '云浮市', 0);
INSERT INTO `china_area` VALUES (7130, '云城区', 445302, 445300, '云浮市', 0);
INSERT INTO `china_area` VALUES (7131, '湛江市', 440800, 440000, '广东省', 0);
INSERT INTO `china_area` VALUES (7132, '赤坎区', 440802, 440800, '湛江市', 0);
INSERT INTO `china_area` VALUES (7133, '雷州市', 440882, 440800, '湛江市', 0);
INSERT INTO `china_area` VALUES (7134, '廉江市', 440881, 440800, '湛江市', 0);
INSERT INTO `china_area` VALUES (7135, '麻章区', 440811, 440800, '湛江市', 0);
INSERT INTO `china_area` VALUES (7136, '坡头区', 440804, 440800, '湛江市', 0);
INSERT INTO `china_area` VALUES (7137, '遂溪县', 440823, 440800, '湛江市', 0);
INSERT INTO `china_area` VALUES (7138, '吴川市', 440883, 440800, '湛江市', 0);
INSERT INTO `china_area` VALUES (7139, '霞山区', 440803, 440800, '湛江市', 0);
INSERT INTO `china_area` VALUES (7140, '徐闻县', 440825, 440800, '湛江市', 0);
INSERT INTO `china_area` VALUES (7141, '珠海市', 440400, 440000, '广东省', 0);
INSERT INTO `china_area` VALUES (7142, '斗门区', 440403, 440400, '珠海市', 0);
INSERT INTO `china_area` VALUES (7143, '金湾区', 440404, 440400, '珠海市', 0);
INSERT INTO `china_area` VALUES (7144, '香洲区', 440402, 440400, '珠海市', 0);
INSERT INTO `china_area` VALUES (7145, '肇庆市', 441200, 440000, '广东省', 0);
INSERT INTO `china_area` VALUES (7146, '德庆县', 441226, 441200, '肇庆市', 0);
INSERT INTO `china_area` VALUES (7147, '鼎湖区', 441203, 441200, '肇庆市', 0);
INSERT INTO `china_area` VALUES (7148, '封开县', 441225, 441200, '肇庆市', 0);
INSERT INTO `china_area` VALUES (7149, '端州区', 441202, 441200, '肇庆市', 0);
INSERT INTO `china_area` VALUES (7150, '高要区', 441204, 441200, '肇庆市', 0);
INSERT INTO `china_area` VALUES (7151, '广宁县', 441223, 441200, '肇庆市', 0);
INSERT INTO `china_area` VALUES (7152, '怀集县', 441224, 441200, '肇庆市', 0);
INSERT INTO `china_area` VALUES (7153, '四会市', 441284, 441200, '肇庆市', 0);
INSERT INTO `china_area` VALUES (7154, '福建省', 350000, 0, '中国', 0);
INSERT INTO `china_area` VALUES (7155, '福州市', 350100, 350000, '福建省', 0);
INSERT INTO `china_area` VALUES (7156, '仓山区', 350104, 350100, '福州市', 0);
INSERT INTO `china_area` VALUES (7157, '福清市', 350181, 350100, '福州市', 0);
INSERT INTO `china_area` VALUES (7158, '鼓楼区', 350102, 350100, '福州市', 0);
INSERT INTO `china_area` VALUES (7159, '晋安区', 350111, 350100, '福州市', 0);
INSERT INTO `china_area` VALUES (7160, '连江县', 350122, 350100, '福州市', 0);
INSERT INTO `china_area` VALUES (7161, '马尾区', 350105, 350100, '福州市', 0);
INSERT INTO `china_area` VALUES (7162, '罗源县', 350123, 350100, '福州市', 0);
INSERT INTO `china_area` VALUES (7163, '闽侯县', 350121, 350100, '福州市', 0);
INSERT INTO `china_area` VALUES (7164, '闽清县', 350124, 350100, '福州市', 0);
INSERT INTO `china_area` VALUES (7165, '平潭县', 350128, 350100, '福州市', 0);
INSERT INTO `china_area` VALUES (7166, '台江区', 350103, 350100, '福州市', 0);
INSERT INTO `china_area` VALUES (7167, '永泰县', 350125, 350100, '福州市', 0);
INSERT INTO `china_area` VALUES (7168, '长乐市', 350182, 350100, '福州市', 0);
INSERT INTO `china_area` VALUES (7169, '龙岩市', 350800, 350000, '福建省', 0);
INSERT INTO `china_area` VALUES (7170, '连城县', 350825, 350800, '龙岩市', 0);
INSERT INTO `china_area` VALUES (7171, '上杭县', 350823, 350800, '龙岩市', 0);
INSERT INTO `china_area` VALUES (7172, '武平县', 350824, 350800, '龙岩市', 0);
INSERT INTO `china_area` VALUES (7173, '新罗区', 350802, 350800, '龙岩市', 0);
INSERT INTO `china_area` VALUES (7174, '永定区', 350803, 350800, '龙岩市', 0);
INSERT INTO `china_area` VALUES (7175, '漳平市', 350881, 350800, '龙岩市', 0);
INSERT INTO `china_area` VALUES (7176, '长汀县', 350821, 350800, '龙岩市', 0);
INSERT INTO `china_area` VALUES (7177, '南平市', 350700, 350000, '福建省', 0);
INSERT INTO `china_area` VALUES (7178, '光泽县', 350723, 350700, '南平市', 0);
INSERT INTO `china_area` VALUES (7179, '建瓯市', 350783, 350700, '南平市', 0);
INSERT INTO `china_area` VALUES (7180, '建阳区', 350703, 350700, '南平市', 0);
INSERT INTO `china_area` VALUES (7181, '浦城县', 350722, 350700, '南平市', 0);
INSERT INTO `china_area` VALUES (7182, '邵武市', 350781, 350700, '南平市', 0);
INSERT INTO `china_area` VALUES (7183, '顺昌县', 350721, 350700, '南平市', 0);
INSERT INTO `china_area` VALUES (7184, '松溪县', 350724, 350700, '南平市', 0);
INSERT INTO `china_area` VALUES (7185, '武夷山市', 350782, 350700, '南平市', 0);
INSERT INTO `china_area` VALUES (7186, '延平区', 350702, 350700, '南平市', 0);
INSERT INTO `china_area` VALUES (7187, '政和县', 350725, 350700, '南平市', 0);
INSERT INTO `china_area` VALUES (7188, '宁德市', 350900, 350000, '福建省', 0);
INSERT INTO `china_area` VALUES (7189, '福鼎市', 350982, 350900, '宁德市', 0);
INSERT INTO `china_area` VALUES (7190, '福安市', 350981, 350900, '宁德市', 0);
INSERT INTO `china_area` VALUES (7191, '古田县', 350922, 350900, '宁德市', 0);
INSERT INTO `china_area` VALUES (7192, '蕉城区', 350902, 350900, '宁德市', 0);
INSERT INTO `china_area` VALUES (7193, '屏南县', 350923, 350900, '宁德市', 0);
INSERT INTO `china_area` VALUES (7194, '寿宁县', 350924, 350900, '宁德市', 0);
INSERT INTO `china_area` VALUES (7195, '霞浦县', 350921, 350900, '宁德市', 0);
INSERT INTO `china_area` VALUES (7196, '柘荣县', 350926, 350900, '宁德市', 0);
INSERT INTO `china_area` VALUES (7197, '周宁县', 350925, 350900, '宁德市', 0);
INSERT INTO `china_area` VALUES (7198, '莆田市', 350300, 350000, '福建省', 0);
INSERT INTO `china_area` VALUES (7199, '城厢区', 350302, 350300, '莆田市', 0);
INSERT INTO `china_area` VALUES (7200, '涵江区', 350303, 350300, '莆田市', 0);
INSERT INTO `china_area` VALUES (7201, '荔城区', 350304, 350300, '莆田市', 0);
INSERT INTO `china_area` VALUES (7202, '仙游县', 350322, 350300, '莆田市', 0);
INSERT INTO `china_area` VALUES (7203, '秀屿区', 350305, 350300, '莆田市', 0);
INSERT INTO `china_area` VALUES (7204, '厦门市', 350200, 350000, '福建省', 0);
INSERT INTO `china_area` VALUES (7205, '海沧区', 350205, 350200, '厦门市', 0);
INSERT INTO `china_area` VALUES (7206, '湖里区', 350206, 350200, '厦门市', 0);
INSERT INTO `china_area` VALUES (7207, '集美区', 350211, 350200, '厦门市', 0);
INSERT INTO `china_area` VALUES (7208, '思明区', 350203, 350200, '厦门市', 0);
INSERT INTO `china_area` VALUES (7209, '同安区', 350212, 350200, '厦门市', 0);
INSERT INTO `china_area` VALUES (7210, '翔安区', 350213, 350200, '厦门市', 0);
INSERT INTO `china_area` VALUES (7211, '三明市', 350400, 350000, '福建省', 0);
INSERT INTO `china_area` VALUES (7212, '大田县', 350425, 350400, '三明市', 0);
INSERT INTO `china_area` VALUES (7213, '将乐县', 350428, 350400, '三明市', 0);
INSERT INTO `china_area` VALUES (7214, '建宁县', 350430, 350400, '三明市', 0);
INSERT INTO `china_area` VALUES (7215, '梅列区', 350402, 350400, '三明市', 0);
INSERT INTO `china_area` VALUES (7216, '明溪县', 350421, 350400, '三明市', 0);
INSERT INTO `china_area` VALUES (7217, '宁化县', 350424, 350400, '三明市', 0);
INSERT INTO `china_area` VALUES (7218, '清流县', 350423, 350400, '三明市', 0);
INSERT INTO `china_area` VALUES (7219, '三元区', 350403, 350400, '三明市', 0);
INSERT INTO `china_area` VALUES (7220, '沙县', 350427, 350400, '三明市', 0);
INSERT INTO `china_area` VALUES (7221, '泰宁县', 350429, 350400, '三明市', 0);
INSERT INTO `china_area` VALUES (7222, '永安市', 350481, 350400, '三明市', 0);
INSERT INTO `china_area` VALUES (7223, '尤溪县', 350426, 350400, '三明市', 0);
INSERT INTO `china_area` VALUES (7224, '泉州市', 350500, 350000, '福建省', 0);
INSERT INTO `china_area` VALUES (7225, '安溪县', 350524, 350500, '泉州市', 0);
INSERT INTO `china_area` VALUES (7226, '德化县', 350526, 350500, '泉州市', 0);
INSERT INTO `china_area` VALUES (7227, '丰泽区', 350503, 350500, '泉州市', 0);
INSERT INTO `china_area` VALUES (7228, '惠安县', 350521, 350500, '泉州市', 0);
INSERT INTO `china_area` VALUES (7229, '金门县', 350527, 350500, '泉州市', 0);
INSERT INTO `china_area` VALUES (7230, '晋江市', 350582, 350500, '泉州市', 0);
INSERT INTO `china_area` VALUES (7231, '鲤城区', 350502, 350500, '泉州市', 0);
INSERT INTO `china_area` VALUES (7232, '洛江区', 350504, 350500, '泉州市', 0);
INSERT INTO `china_area` VALUES (7233, '南安市', 350583, 350500, '泉州市', 0);
INSERT INTO `china_area` VALUES (7234, '泉港区', 350505, 350500, '泉州市', 0);
INSERT INTO `china_area` VALUES (7235, '石狮市', 350581, 350500, '泉州市', 0);
INSERT INTO `china_area` VALUES (7236, '永春县', 350525, 350500, '泉州市', 0);
INSERT INTO `china_area` VALUES (7237, '漳州市', 350600, 350000, '福建省', 0);
INSERT INTO `china_area` VALUES (7238, '东山县', 350626, 350600, '漳州市', 0);
INSERT INTO `china_area` VALUES (7239, '华安县', 350629, 350600, '漳州市', 0);
INSERT INTO `china_area` VALUES (7240, '龙文区', 350603, 350600, '漳州市', 0);
INSERT INTO `china_area` VALUES (7241, '龙海市', 350681, 350600, '漳州市', 0);
INSERT INTO `china_area` VALUES (7242, '南靖县', 350627, 350600, '漳州市', 0);
INSERT INTO `china_area` VALUES (7243, '平和县', 350628, 350600, '漳州市', 0);
INSERT INTO `china_area` VALUES (7244, '芗城区', 350602, 350600, '漳州市', 0);
INSERT INTO `china_area` VALUES (7245, '云霄县', 350622, 350600, '漳州市', 0);
INSERT INTO `china_area` VALUES (7246, '诏安县', 350624, 350600, '漳州市', 0);
INSERT INTO `china_area` VALUES (7247, '漳浦县', 350623, 350600, '漳州市', 0);
INSERT INTO `china_area` VALUES (7248, '长泰县', 350625, 350600, '漳州市', 0);
INSERT INTO `china_area` VALUES (7249, '海南省', 460000, 0, '中国', 0);
INSERT INTO `china_area` VALUES (7250, '儋州市', 460400, 460000, '海南省', 0);
INSERT INTO `china_area` VALUES (7251, '海头镇', 460401, 460400, '儋州市', 0);
INSERT INTO `china_area` VALUES (7252, '白马井镇', 460402, 460400, '儋州市', 0);
INSERT INTO `china_area` VALUES (7253, '新英湾街道', 460403, 460400, '儋州市', 0);
INSERT INTO `china_area` VALUES (7254, '那大街道', 460404, 460400, '儋州市', 0);
INSERT INTO `china_area` VALUES (7255, '木棠镇', 460405, 460400, '儋州市', 0);
INSERT INTO `china_area` VALUES (7256, '新州镇', 460406, 460400, '儋州市', 0);
INSERT INTO `china_area` VALUES (7257, '中和镇', 460407, 460400, '儋州市', 0);
INSERT INTO `china_area` VALUES (7258, '峨蔓镇', 460408, 460400, '儋州市', 0);
INSERT INTO `china_area` VALUES (7259, '光村镇', 460409, 460400, '儋州市', 0);
INSERT INTO `china_area` VALUES (7260, '排浦镇', 460410, 460400, '儋州市', 0);
INSERT INTO `china_area` VALUES (7261, '王五镇', 460411, 460400, '儋州市', 0);
INSERT INTO `china_area` VALUES (7262, '大成镇', 460412, 460400, '儋州市', 0);
INSERT INTO `china_area` VALUES (7263, '和庆镇', 460413, 460400, '儋州市', 0);
INSERT INTO `china_area` VALUES (7264, '兰洋镇', 460414, 460400, '儋州市', 0);
INSERT INTO `china_area` VALUES (7265, '雅星镇', 460415, 460400, '儋州市', 0);
INSERT INTO `china_area` VALUES (7266, '三都镇', 460416, 460400, '儋州市', 0);
INSERT INTO `china_area` VALUES (7267, '东成镇', 460417, 460400, '儋州市', 0);
INSERT INTO `china_area` VALUES (7268, '那大镇', 460418, 460400, '儋州市', 0);
INSERT INTO `china_area` VALUES (7269, '兰训乡', 460419, 460400, '儋州市', 0);
INSERT INTO `china_area` VALUES (7270, '海口市', 460100, 460000, '海南省', 0);
INSERT INTO `china_area` VALUES (7271, '龙华区', 460106, 460100, '海口市', 0);
INSERT INTO `china_area` VALUES (7272, '美兰区', 460108, 460100, '海口市', 0);
INSERT INTO `china_area` VALUES (7273, '琼山区', 460107, 460100, '海口市', 0);
INSERT INTO `china_area` VALUES (7274, '秀英区', 460105, 460100, '海口市', 0);
INSERT INTO `china_area` VALUES (7275, '三亚市', 460200, 460000, '海南省', 0);
INSERT INTO `china_area` VALUES (7276, '海棠区', 460202, 460200, '三亚市', 0);
INSERT INTO `china_area` VALUES (7277, '吉阳区', 460203, 460200, '三亚市', 0);
INSERT INTO `china_area` VALUES (7278, '天涯区', 460204, 460200, '三亚市', 0);
INSERT INTO `china_area` VALUES (7279, '崖州区', 460205, 460200, '三亚市', 0);
INSERT INTO `china_area` VALUES (7280, '三沙市', 460300, 460000, '海南省', 0);
INSERT INTO `china_area` VALUES (7281, '南沙群岛', 460322, 460300, '三沙市', 0);
INSERT INTO `china_area` VALUES (7282, '西沙群岛', 460321, 460300, '三沙市', 0);
INSERT INTO `china_area` VALUES (7283, '中沙群岛的岛礁及其海域', 460323, 460300, '三沙市', 0);
INSERT INTO `china_area` VALUES (7284, '白沙黎族自治县', 469025, 460000, '海南省', 0);
INSERT INTO `china_area` VALUES (7285, '细水乡', 469026, 469025, '白沙黎族自治县', 0);
INSERT INTO `china_area` VALUES (7286, '阜龙乡', 469027, 469025, '白沙黎族自治县', 0);
INSERT INTO `china_area` VALUES (7287, '邦溪镇', 469028, 469025, '白沙黎族自治县', 0);
INSERT INTO `china_area` VALUES (7288, '南开乡', 469029, 469025, '白沙黎族自治县', 0);
INSERT INTO `china_area` VALUES (7289, '七坊镇', 469030, 469025, '白沙黎族自治县', 0);
INSERT INTO `china_area` VALUES (7290, '金波乡', 469031, 469025, '白沙黎族自治县', 0);
INSERT INTO `china_area` VALUES (7291, '牙叉镇', 469032, 469025, '白沙黎族自治县', 0);
INSERT INTO `china_area` VALUES (7292, '青松乡', 469033, 469025, '白沙黎族自治县', 0);
INSERT INTO `china_area` VALUES (7293, '打安镇', 469034, 469025, '白沙黎族自治县', 0);
INSERT INTO `china_area` VALUES (7294, '荣邦乡', 469035, 469025, '白沙黎族自治县', 0);
INSERT INTO `china_area` VALUES (7295, '元门乡', 469036, 469025, '白沙黎族自治县', 0);
INSERT INTO `china_area` VALUES (7296, '保亭黎族苗族自治县', 469029, 460000, '海南省', 0);
INSERT INTO `china_area` VALUES (7297, '毛岸镇', 469030, 469029, '保亭黎族苗族自治县', 0);
INSERT INTO `china_area` VALUES (7298, '三道镇', 469031, 469029, '保亭黎族苗族自治县', 0);
INSERT INTO `china_area` VALUES (7299, '保城镇', 469032, 469029, '保亭黎族苗族自治县', 0);
INSERT INTO `china_area` VALUES (7300, '六弓乡', 469033, 469029, '保亭黎族苗族自治县', 0);
INSERT INTO `china_area` VALUES (7301, '什玲镇', 469034, 469029, '保亭黎族苗族自治县', 0);
INSERT INTO `china_area` VALUES (7302, '响水镇', 469035, 469029, '保亭黎族苗族自治县', 0);
INSERT INTO `china_area` VALUES (7303, '毛感乡', 469036, 469029, '保亭黎族苗族自治县', 0);
INSERT INTO `china_area` VALUES (7304, '新政镇', 469037, 469029, '保亭黎族苗族自治县', 0);
INSERT INTO `china_area` VALUES (7305, '南林乡', 469038, 469029, '保亭黎族苗族自治县', 0);
INSERT INTO `china_area` VALUES (7306, '加茂镇', 469039, 469029, '保亭黎族苗族自治县', 0);
INSERT INTO `china_area` VALUES (7307, '昌江黎族自治县', 469026, 460000, '海南省', 0);
INSERT INTO `china_area` VALUES (7308, '石碌镇', 469027, 469026, '昌江黎族自治县', 0);
INSERT INTO `china_area` VALUES (7309, '乌烈镇', 469028, 469026, '昌江黎族自治县', 0);
INSERT INTO `china_area` VALUES (7310, '七叉镇', 469029, 469026, '昌江黎族自治县', 0);
INSERT INTO `china_area` VALUES (7311, '海尾镇', 469030, 469026, '昌江黎族自治县', 0);
INSERT INTO `china_area` VALUES (7312, '叉河镇', 469031, 469026, '昌江黎族自治县', 0);
INSERT INTO `china_area` VALUES (7313, '十月田镇', 469032, 469026, '昌江黎族自治县', 0);
INSERT INTO `china_area` VALUES (7314, '昌化镇', 469033, 469026, '昌江黎族自治县', 0);
INSERT INTO `china_area` VALUES (7315, '王下乡', 469034, 469026, '昌江黎族自治县', 0);
INSERT INTO `china_area` VALUES (7316, '澄迈县', 469023, 460000, '海南省', 0);
INSERT INTO `china_area` VALUES (7317, '加乐镇', 469024, 469023, '澄迈县', 0);
INSERT INTO `china_area` VALUES (7318, '福山镇', 469025, 469023, '澄迈县', 0);
INSERT INTO `china_area` VALUES (7319, '瑞溪镇', 469026, 469023, '澄迈县', 0);
INSERT INTO `china_area` VALUES (7320, '文儒镇', 469027, 469023, '澄迈县', 0);
INSERT INTO `china_area` VALUES (7321, '仁兴镇', 469028, 469023, '澄迈县', 0);
INSERT INTO `china_area` VALUES (7322, '永发镇', 469029, 469023, '澄迈县', 0);
INSERT INTO `china_area` VALUES (7323, '大丰镇', 469030, 469023, '澄迈县', 0);
INSERT INTO `china_area` VALUES (7324, '桥头镇', 469031, 469023, '澄迈县', 0);
INSERT INTO `china_area` VALUES (7325, '中兴镇', 469032, 469023, '澄迈县', 0);
INSERT INTO `china_area` VALUES (7326, '老城镇', 469033, 469023, '澄迈县', 0);
INSERT INTO `china_area` VALUES (7327, '金江镇', 469034, 469023, '澄迈县', 0);
INSERT INTO `china_area` VALUES (7328, '定安县', 469021, 460000, '海南省', 0);
INSERT INTO `china_area` VALUES (7329, '龙河镇', 469022, 469021, '定安县', 0);
INSERT INTO `china_area` VALUES (7330, '翰林镇', 469023, 469021, '定安县', 0);
INSERT INTO `china_area` VALUES (7331, '黄竹镇', 469024, 469021, '定安县', 0);
INSERT INTO `china_area` VALUES (7332, '龙门镇', 469025, 469021, '定安县', 0);
INSERT INTO `china_area` VALUES (7333, '定城镇', 469026, 469021, '定安县', 0);
INSERT INTO `china_area` VALUES (7334, '富文镇', 469027, 469021, '定安县', 0);
INSERT INTO `china_area` VALUES (7335, '龙州乡', 469028, 469021, '定安县', 0);
INSERT INTO `china_area` VALUES (7336, '龙湖镇', 469029, 469021, '定安县', 0);
INSERT INTO `china_area` VALUES (7337, '居丁镇', 469030, 469021, '定安县', 0);
INSERT INTO `china_area` VALUES (7338, '雷鸣镇', 469031, 469021, '定安县', 0);
INSERT INTO `china_area` VALUES (7339, '岭口镇', 469032, 469021, '定安县', 0);
INSERT INTO `china_area` VALUES (7340, '新竹镇', 469033, 469021, '定安县', 0);
INSERT INTO `china_area` VALUES (7341, '东方市', 469007, 460000, '海南省', 0);
INSERT INTO `china_area` VALUES (7342, '新龙镇', 469008, 469007, '东方市', 0);
INSERT INTO `china_area` VALUES (7343, '江边乡', 469009, 469007, '东方市', 0);
INSERT INTO `china_area` VALUES (7344, '四更镇', 469010, 469007, '东方市', 0);
INSERT INTO `china_area` VALUES (7345, '板桥镇', 469011, 469007, '东方市', 0);
INSERT INTO `china_area` VALUES (7346, '感城镇', 469012, 469007, '东方市', 0);
INSERT INTO `china_area` VALUES (7347, '大田镇', 469013, 469007, '东方市', 0);
INSERT INTO `china_area` VALUES (7348, '天安乡', 469014, 469007, '东方市', 0);
INSERT INTO `china_area` VALUES (7349, '罗带乡', 469015, 469007, '东方市', 0);
INSERT INTO `china_area` VALUES (7350, '三家镇', 469016, 469007, '东方市', 0);
INSERT INTO `china_area` VALUES (7351, '东河镇', 469017, 469007, '东方市', 0);
INSERT INTO `china_area` VALUES (7352, '八所镇', 469018, 469007, '东方市', 0);
INSERT INTO `china_area` VALUES (7353, '乐东黎族自治县', 469027, 460000, '海南省', 0);
INSERT INTO `china_area` VALUES (7354, '大安镇', 469028, 469027, '乐东黎族自治县', 0);
INSERT INTO `china_area` VALUES (7355, '莺歌海镇', 469029, 469027, '乐东黎族自治县', 0);
INSERT INTO `china_area` VALUES (7356, '黄流镇', 469030, 469027, '乐东黎族自治县', 0);
INSERT INTO `china_area` VALUES (7357, '万冲镇', 469031, 469027, '乐东黎族自治县', 0);
INSERT INTO `china_area` VALUES (7358, '志仲镇', 469032, 469027, '乐东黎族自治县', 0);
INSERT INTO `china_area` VALUES (7359, '九所镇', 469033, 469027, '乐东黎族自治县', 0);
INSERT INTO `china_area` VALUES (7360, '利国镇', 469034, 469027, '乐东黎族自治县', 0);
INSERT INTO `china_area` VALUES (7361, '佛罗镇', 469035, 469027, '乐东黎族自治县', 0);
INSERT INTO `china_area` VALUES (7362, '抱由镇', 469036, 469027, '乐东黎族自治县', 0);
INSERT INTO `china_area` VALUES (7363, '尖峰镇', 469037, 469027, '乐东黎族自治县', 0);
INSERT INTO `china_area` VALUES (7364, '冲坡镇', 469038, 469027, '乐东黎族自治县', 0);
INSERT INTO `china_area` VALUES (7365, '千家镇', 469039, 469027, '乐东黎族自治县', 0);
INSERT INTO `china_area` VALUES (7366, '临高县', 469024, 460000, '海南省', 0);
INSERT INTO `china_area` VALUES (7367, '东英镇', 469025, 469024, '临高县', 0);
INSERT INTO `china_area` VALUES (7368, '皇桐镇', 469026, 469024, '临高县', 0);
INSERT INTO `china_area` VALUES (7369, '南宝镇', 469027, 469024, '临高县', 0);
INSERT INTO `china_area` VALUES (7370, '和舍镇', 469028, 469024, '临高县', 0);
INSERT INTO `china_area` VALUES (7371, '多文镇', 469029, 469024, '临高县', 0);
INSERT INTO `china_area` VALUES (7372, '波莲镇', 469030, 469024, '临高县', 0);
INSERT INTO `china_area` VALUES (7373, '临城镇', 469031, 469024, '临高县', 0);
INSERT INTO `china_area` VALUES (7374, '博厚镇', 469032, 469024, '临高县', 0);
INSERT INTO `china_area` VALUES (7375, '新盈镇', 469033, 469024, '临高县', 0);
INSERT INTO `china_area` VALUES (7376, '调楼镇', 469034, 469024, '临高县', 0);
INSERT INTO `china_area` VALUES (7377, '加来镇', 469035, 469024, '临高县', 0);
INSERT INTO `china_area` VALUES (7378, '陵水黎族自治县', 469028, 460000, '海南省', 0);
INSERT INTO `china_area` VALUES (7379, '光坡镇', 469029, 469028, '陵水黎族自治县', 0);
INSERT INTO `china_area` VALUES (7380, '大里乡', 469030, 469028, '陵水黎族自治县', 0);
INSERT INTO `china_area` VALUES (7381, '三才镇', 469031, 469028, '陵水黎族自治县', 0);
INSERT INTO `china_area` VALUES (7382, '英州镇', 469032, 469028, '陵水黎族自治县', 0);
INSERT INTO `china_area` VALUES (7383, '本号镇', 469033, 469028, '陵水黎族自治县', 0);
INSERT INTO `china_area` VALUES (7384, '群英乡', 469034, 469028, '陵水黎族自治县', 0);
INSERT INTO `china_area` VALUES (7385, '隆广镇', 469035, 469028, '陵水黎族自治县', 0);
INSERT INTO `china_area` VALUES (7386, '提蒙乡', 469036, 469028, '陵水黎族自治县', 0);
INSERT INTO `china_area` VALUES (7387, '椰林镇', 469037, 469028, '陵水黎族自治县', 0);
INSERT INTO `china_area` VALUES (7388, '新村镇', 469038, 469028, '陵水黎族自治县', 0);
INSERT INTO `china_area` VALUES (7389, '黎安镇', 469039, 469028, '陵水黎族自治县', 0);
INSERT INTO `china_area` VALUES (7390, '文罗镇', 469040, 469028, '陵水黎族自治县', 0);
INSERT INTO `china_area` VALUES (7391, '琼海市', 469002, 460000, '海南省', 0);
INSERT INTO `china_area` VALUES (7392, '博鳌镇', 469003, 469002, '琼海市', 0);
INSERT INTO `china_area` VALUES (7393, '石壁镇', 469004, 469002, '琼海市', 0);
INSERT INTO `china_area` VALUES (7394, '会山镇', 469005, 469002, '琼海市', 0);
INSERT INTO `china_area` VALUES (7395, '大路镇', 469006, 469002, '琼海市', 0);
INSERT INTO `china_area` VALUES (7396, '万泉镇', 469007, 469002, '琼海市', 0);
INSERT INTO `china_area` VALUES (7397, '潭门镇', 469008, 469002, '琼海市', 0);
INSERT INTO `china_area` VALUES (7398, '加积街道', 469009, 469002, '琼海市', 0);
INSERT INTO `china_area` VALUES (7399, '中原镇', 469010, 469002, '琼海市', 0);
INSERT INTO `china_area` VALUES (7400, '阳江镇', 469011, 469002, '琼海市', 0);
INSERT INTO `china_area` VALUES (7401, '朝阳乡', 469012, 469002, '琼海市', 0);
INSERT INTO `china_area` VALUES (7402, '长坡镇', 469013, 469002, '琼海市', 0);
INSERT INTO `china_area` VALUES (7403, '塔洋镇', 469014, 469002, '琼海市', 0);
INSERT INTO `china_area` VALUES (7404, '嘉积镇', 469015, 469002, '琼海市', 0);
INSERT INTO `china_area` VALUES (7405, '龙江镇', 469016, 469002, '琼海市', 0);
INSERT INTO `china_area` VALUES (7406, '琼中黎族苗族自治县', 469030, 460000, '海南省', 0);
INSERT INTO `china_area` VALUES (7407, '中平镇', 469031, 469030, '琼中黎族苗族自治县', 0);
INSERT INTO `china_area` VALUES (7408, '和平镇', 469032, 469030, '琼中黎族苗族自治县', 0);
INSERT INTO `china_area` VALUES (7409, '什运乡', 469033, 469030, '琼中黎族苗族自治县', 0);
INSERT INTO `china_area` VALUES (7410, '营根镇', 469034, 469030, '琼中黎族苗族自治县', 0);
INSERT INTO `china_area` VALUES (7411, '红毛镇', 469035, 469030, '琼中黎族苗族自治县', 0);
INSERT INTO `china_area` VALUES (7412, '吊罗山乡', 469036, 469030, '琼中黎族苗族自治县', 0);
INSERT INTO `china_area` VALUES (7413, '上安乡', 469037, 469030, '琼中黎族苗族自治县', 0);
INSERT INTO `china_area` VALUES (7414, '湾岭镇', 469038, 469030, '琼中黎族苗族自治县', 0);
INSERT INTO `china_area` VALUES (7415, '黎母山镇', 469039, 469030, '琼中黎族苗族自治县', 0);
INSERT INTO `china_area` VALUES (7416, '屯昌县', 469022, 460000, '海南省', 0);
INSERT INTO `china_area` VALUES (7417, '南吕镇', 469023, 469022, '屯昌县', 0);
INSERT INTO `china_area` VALUES (7418, '西昌镇', 469024, 469022, '屯昌县', 0);
INSERT INTO `china_area` VALUES (7419, '新兴镇', 469025, 469022, '屯昌县', 0);
INSERT INTO `china_area` VALUES (7420, '乌坡镇', 469026, 469022, '屯昌县', 0);
INSERT INTO `china_area` VALUES (7421, '枫木镇', 469027, 469022, '屯昌县', 0);
INSERT INTO `china_area` VALUES (7422, '坡心镇', 469028, 469022, '屯昌县', 0);
INSERT INTO `china_area` VALUES (7423, '屯城镇', 469029, 469022, '屯昌县', 0);
INSERT INTO `china_area` VALUES (7424, '南坤镇', 469030, 469022, '屯昌县', 0);
INSERT INTO `china_area` VALUES (7425, '万宁市', 469006, 460000, '海南省', 0);
INSERT INTO `china_area` VALUES (7426, '龙滚镇', 469007, 469006, '万宁市', 0);
INSERT INTO `china_area` VALUES (7427, '礼纪镇', 469008, 469006, '万宁市', 0);
INSERT INTO `china_area` VALUES (7428, '后安镇', 469009, 469006, '万宁市', 0);
INSERT INTO `china_area` VALUES (7429, '大茂镇', 469010, 469006, '万宁市', 0);
INSERT INTO `china_area` VALUES (7430, '北大镇', 469011, 469006, '万宁市', 0);
INSERT INTO `china_area` VALUES (7431, '万城镇', 469012, 469006, '万宁市', 0);
INSERT INTO `china_area` VALUES (7432, '南桥镇', 469013, 469006, '万宁市', 0);
INSERT INTO `china_area` VALUES (7433, '长丰镇', 469014, 469006, '万宁市', 0);
INSERT INTO `china_area` VALUES (7434, '东澳镇', 469015, 469006, '万宁市', 0);
INSERT INTO `china_area` VALUES (7435, '三更罗镇', 469016, 469006, '万宁市', 0);
INSERT INTO `china_area` VALUES (7436, '和乐镇', 469017, 469006, '万宁市', 0);
INSERT INTO `china_area` VALUES (7437, '山根镇', 469018, 469006, '万宁市', 0);
INSERT INTO `china_area` VALUES (7438, '文昌市', 469005, 460000, '海南省', 0);
INSERT INTO `china_area` VALUES (7439, '重兴镇', 469006, 469005, '文昌市', 0);
INSERT INTO `china_area` VALUES (7440, '文城镇', 469007, 469005, '文昌市', 0);
INSERT INTO `china_area` VALUES (7441, '潭牛镇', 469008, 469005, '文昌市', 0);
INSERT INTO `china_area` VALUES (7442, '会文镇', 469009, 469005, '文昌市', 0);
INSERT INTO `china_area` VALUES (7443, '冯坡镇', 469010, 469005, '文昌市', 0);
INSERT INTO `china_area` VALUES (7444, '东路镇', 469011, 469005, '文昌市', 0);
INSERT INTO `china_area` VALUES (7445, '文教镇', 469012, 469005, '文昌市', 0);
INSERT INTO `china_area` VALUES (7446, '锦山镇', 469013, 469005, '文昌市', 0);
INSERT INTO `china_area` VALUES (7447, '蓬莱镇', 469014, 469005, '文昌市', 0);
INSERT INTO `china_area` VALUES (7448, '东阁镇', 469015, 469005, '文昌市', 0);
INSERT INTO `china_area` VALUES (7449, '昌洒镇', 469016, 469005, '文昌市', 0);
INSERT INTO `china_area` VALUES (7450, '铺前镇', 469017, 469005, '文昌市', 0);
INSERT INTO `china_area` VALUES (7451, '翁田镇', 469018, 469005, '文昌市', 0);
INSERT INTO `china_area` VALUES (7452, '东郊镇', 469019, 469005, '文昌市', 0);
INSERT INTO `china_area` VALUES (7453, '抱罗镇', 469020, 469005, '文昌市', 0);
INSERT INTO `china_area` VALUES (7454, '公坡镇', 469021, 469005, '文昌市', 0);
INSERT INTO `china_area` VALUES (7455, '龙楼镇', 469022, 469005, '文昌市', 0);
INSERT INTO `china_area` VALUES (7456, '五指山市', 469001, 460000, '海南省', 0);
INSERT INTO `china_area` VALUES (7457, '番阳镇', 469002, 469001, '五指山市', 0);
INSERT INTO `china_area` VALUES (7458, '冲山镇', 469003, 469001, '五指山市', 0);
INSERT INTO `china_area` VALUES (7459, '毛阳镇', 469004, 469001, '五指山市', 0);
INSERT INTO `china_area` VALUES (7460, '水满乡', 469005, 469001, '五指山市', 0);
INSERT INTO `china_area` VALUES (7461, '通什镇', 469006, 469001, '五指山市', 0);
INSERT INTO `china_area` VALUES (7462, '南圣镇', 469007, 469001, '五指山市', 0);
INSERT INTO `china_area` VALUES (7463, '毛道乡', 469008, 469001, '五指山市', 0);
INSERT INTO `china_area` VALUES (7464, '畅好乡', 469009, 469001, '五指山市', 0);
INSERT INTO `china_area` VALUES (7465, '山西省', 140000, 0, '中国', 0);
INSERT INTO `china_area` VALUES (7466, '大同市', 140200, 140000, '山西省', 0);
INSERT INTO `china_area` VALUES (7467, '城区', 140202, 140200, '大同市', 0);
INSERT INTO `china_area` VALUES (7468, '大同县', 140227, 140200, '大同市', 0);
INSERT INTO `china_area` VALUES (7469, '广灵县', 140223, 140200, '大同市', 0);
INSERT INTO `china_area` VALUES (7470, '浑源县', 140225, 140200, '大同市', 0);
INSERT INTO `china_area` VALUES (7471, '矿区', 140203, 140200, '大同市', 0);
INSERT INTO `china_area` VALUES (7472, '灵丘县', 140224, 140200, '大同市', 0);
INSERT INTO `china_area` VALUES (7473, '南郊区', 140211, 140200, '大同市', 0);
INSERT INTO `china_area` VALUES (7474, '天镇县', 140222, 140200, '大同市', 0);
INSERT INTO `china_area` VALUES (7475, '新荣区', 140212, 140200, '大同市', 0);
INSERT INTO `china_area` VALUES (7476, '阳高县', 140221, 140200, '大同市', 0);
INSERT INTO `china_area` VALUES (7477, '左云县', 140226, 140200, '大同市', 0);
INSERT INTO `china_area` VALUES (7478, '晋城市', 140500, 140000, '山西省', 0);
INSERT INTO `china_area` VALUES (7479, '城区', 140502, 140500, '晋城市', 0);
INSERT INTO `china_area` VALUES (7480, '高平市', 140581, 140500, '晋城市', 0);
INSERT INTO `china_area` VALUES (7481, '陵川县', 140524, 140500, '晋城市', 0);
INSERT INTO `china_area` VALUES (7482, '沁水县', 140521, 140500, '晋城市', 0);
INSERT INTO `china_area` VALUES (7483, '阳城县', 140522, 140500, '晋城市', 0);
INSERT INTO `china_area` VALUES (7484, '泽州县', 140525, 140500, '晋城市', 0);
INSERT INTO `china_area` VALUES (7485, '晋中市', 140700, 140000, '山西省', 0);
INSERT INTO `china_area` VALUES (7486, '和顺县', 140723, 140700, '晋中市', 0);
INSERT INTO `china_area` VALUES (7487, '介休市', 140781, 140700, '晋中市', 0);
INSERT INTO `china_area` VALUES (7488, '灵石县', 140729, 140700, '晋中市', 0);
INSERT INTO `china_area` VALUES (7489, '平遥县', 140728, 140700, '晋中市', 0);
INSERT INTO `china_area` VALUES (7490, '祁县', 140727, 140700, '晋中市', 0);
INSERT INTO `china_area` VALUES (7491, '寿阳县', 140725, 140700, '晋中市', 0);
INSERT INTO `china_area` VALUES (7492, '太谷县', 140726, 140700, '晋中市', 0);
INSERT INTO `china_area` VALUES (7493, '昔阳县', 140724, 140700, '晋中市', 0);
INSERT INTO `china_area` VALUES (7494, '榆次区', 140702, 140700, '晋中市', 0);
INSERT INTO `china_area` VALUES (7495, '榆社县', 140721, 140700, '晋中市', 0);
INSERT INTO `china_area` VALUES (7496, '左权县', 140722, 140700, '晋中市', 0);
INSERT INTO `china_area` VALUES (7497, '吕梁市', 141100, 140000, '山西省', 0);
INSERT INTO `china_area` VALUES (7498, '汾阳市', 141182, 141100, '吕梁市', 0);
INSERT INTO `china_area` VALUES (7499, '方山县', 141128, 141100, '吕梁市', 0);
INSERT INTO `china_area` VALUES (7500, '交城县', 141122, 141100, '吕梁市', 0);
INSERT INTO `china_area` VALUES (7501, '交口县', 141130, 141100, '吕梁市', 0);
INSERT INTO `china_area` VALUES (7502, '岚县', 141127, 141100, '吕梁市', 0);
INSERT INTO `china_area` VALUES (7503, '离石区', 141102, 141100, '吕梁市', 0);
INSERT INTO `china_area` VALUES (7504, '临县', 141124, 141100, '吕梁市', 0);
INSERT INTO `china_area` VALUES (7505, '柳林县', 141125, 141100, '吕梁市', 0);
INSERT INTO `china_area` VALUES (7506, '石楼县', 141126, 141100, '吕梁市', 0);
INSERT INTO `china_area` VALUES (7507, '文水县', 141121, 141100, '吕梁市', 0);
INSERT INTO `china_area` VALUES (7508, '孝义市', 141181, 141100, '吕梁市', 0);
INSERT INTO `china_area` VALUES (7509, '兴县', 141123, 141100, '吕梁市', 0);
INSERT INTO `china_area` VALUES (7510, '中阳县', 141129, 141100, '吕梁市', 0);
INSERT INTO `china_area` VALUES (7511, '临汾市', 141000, 140000, '山西省', 0);
INSERT INTO `china_area` VALUES (7512, '安泽县', 141026, 141000, '临汾市', 0);
INSERT INTO `china_area` VALUES (7513, '大宁县', 141030, 141000, '临汾市', 0);
INSERT INTO `china_area` VALUES (7514, '汾西县', 141034, 141000, '临汾市', 0);
INSERT INTO `china_area` VALUES (7515, '浮山县', 141027, 141000, '临汾市', 0);
INSERT INTO `china_area` VALUES (7516, '古县', 141025, 141000, '临汾市', 0);
INSERT INTO `china_area` VALUES (7517, '洪洞县', 141024, 141000, '临汾市', 0);
INSERT INTO `china_area` VALUES (7518, '侯马市', 141081, 141000, '临汾市', 0);
INSERT INTO `china_area` VALUES (7519, '霍州市', 141082, 141000, '临汾市', 0);
INSERT INTO `china_area` VALUES (7520, '吉县', 141028, 141000, '临汾市', 0);
INSERT INTO `china_area` VALUES (7521, '蒲县', 141033, 141000, '临汾市', 0);
INSERT INTO `china_area` VALUES (7522, '曲沃县', 141021, 141000, '临汾市', 0);
INSERT INTO `china_area` VALUES (7523, '隰县', 141031, 141000, '临汾市', 0);
INSERT INTO `china_area` VALUES (7524, '襄汾县', 141023, 141000, '临汾市', 0);
INSERT INTO `china_area` VALUES (7525, '乡宁县', 141029, 141000, '临汾市', 0);
INSERT INTO `china_area` VALUES (7526, '尧都区', 141002, 141000, '临汾市', 0);
INSERT INTO `china_area` VALUES (7527, '翼城县', 141022, 141000, '临汾市', 0);
INSERT INTO `china_area` VALUES (7528, '永和县', 141032, 141000, '临汾市', 0);
INSERT INTO `china_area` VALUES (7529, '朔州市', 140600, 140000, '山西省', 0);
INSERT INTO `china_area` VALUES (7530, '怀仁县', 140624, 140600, '朔州市', 0);
INSERT INTO `china_area` VALUES (7531, '平鲁区', 140603, 140600, '朔州市', 0);
INSERT INTO `china_area` VALUES (7532, '山阴县', 140621, 140600, '朔州市', 0);
INSERT INTO `china_area` VALUES (7533, '朔城区', 140602, 140600, '朔州市', 0);
INSERT INTO `china_area` VALUES (7534, '应县', 140622, 140600, '朔州市', 0);
INSERT INTO `china_area` VALUES (7535, '右玉县', 140623, 140600, '朔州市', 0);
INSERT INTO `china_area` VALUES (7536, '太原市', 140100, 140000, '山西省', 0);
INSERT INTO `china_area` VALUES (7537, '古交市', 140181, 140100, '太原市', 0);
INSERT INTO `china_area` VALUES (7538, '尖草坪区', 140108, 140100, '太原市', 0);
INSERT INTO `china_area` VALUES (7539, '晋源区', 140110, 140100, '太原市', 0);
INSERT INTO `china_area` VALUES (7540, '娄烦县', 140123, 140100, '太原市', 0);
INSERT INTO `china_area` VALUES (7541, '清徐县', 140121, 140100, '太原市', 0);
INSERT INTO `china_area` VALUES (7542, '万柏林区', 140109, 140100, '太原市', 0);
INSERT INTO `china_area` VALUES (7543, '小店区', 140105, 140100, '太原市', 0);
INSERT INTO `china_area` VALUES (7544, '杏花岭区', 140107, 140100, '太原市', 0);
INSERT INTO `china_area` VALUES (7545, '阳曲县', 140122, 140100, '太原市', 0);
INSERT INTO `china_area` VALUES (7546, '迎泽区', 140106, 140100, '太原市', 0);
INSERT INTO `china_area` VALUES (7547, '忻州市', 140900, 140000, '山西省', 0);
INSERT INTO `china_area` VALUES (7548, '保德县', 140931, 140900, '忻州市', 0);
INSERT INTO `china_area` VALUES (7549, '代县', 140923, 140900, '忻州市', 0);
INSERT INTO `china_area` VALUES (7550, '定襄县', 140921, 140900, '忻州市', 0);
INSERT INTO `china_area` VALUES (7551, '繁峙县', 140924, 140900, '忻州市', 0);
INSERT INTO `china_area` VALUES (7552, '河曲县', 140930, 140900, '忻州市', 0);
INSERT INTO `china_area` VALUES (7553, '静乐县', 140926, 140900, '忻州市', 0);
INSERT INTO `china_area` VALUES (7554, '岢岚县', 140929, 140900, '忻州市', 0);
INSERT INTO `china_area` VALUES (7555, '宁武县', 140925, 140900, '忻州市', 0);
INSERT INTO `china_area` VALUES (7556, '偏关县', 140932, 140900, '忻州市', 0);
INSERT INTO `china_area` VALUES (7557, '神池县', 140927, 140900, '忻州市', 0);
INSERT INTO `china_area` VALUES (7558, '五寨县', 140928, 140900, '忻州市', 0);
INSERT INTO `china_area` VALUES (7559, '五台县', 140922, 140900, '忻州市', 0);
INSERT INTO `china_area` VALUES (7560, '忻府区', 140902, 140900, '忻州市', 0);
INSERT INTO `china_area` VALUES (7561, '原平市', 140981, 140900, '忻州市', 0);
INSERT INTO `china_area` VALUES (7562, '阳泉市', 140300, 140000, '山西省', 0);
INSERT INTO `china_area` VALUES (7563, '城区', 140302, 140300, '阳泉市', 0);
INSERT INTO `china_area` VALUES (7564, '郊区', 140311, 140300, '阳泉市', 0);
INSERT INTO `china_area` VALUES (7565, '矿区', 140303, 140300, '阳泉市', 0);
INSERT INTO `china_area` VALUES (7566, '平定县', 140321, 140300, '阳泉市', 0);
INSERT INTO `china_area` VALUES (7567, '盂县', 140322, 140300, '阳泉市', 0);
INSERT INTO `china_area` VALUES (7568, '长治市', 140400, 140000, '山西省', 0);
INSERT INTO `china_area` VALUES (7569, '城区', 140402, 140400, '长治市', 0);
INSERT INTO `china_area` VALUES (7570, '壶关县', 140427, 140400, '长治市', 0);
INSERT INTO `china_area` VALUES (7571, '郊区', 140411, 140400, '长治市', 0);
INSERT INTO `china_area` VALUES (7572, '黎城县', 140426, 140400, '长治市', 0);
INSERT INTO `china_area` VALUES (7573, '潞城市', 140481, 140400, '长治市', 0);
INSERT INTO `china_area` VALUES (7574, '平顺县', 140425, 140400, '长治市', 0);
INSERT INTO `china_area` VALUES (7575, '沁县', 140430, 140400, '长治市', 0);
INSERT INTO `china_area` VALUES (7576, '沁源县', 140431, 140400, '长治市', 0);
INSERT INTO `china_area` VALUES (7577, '屯留县', 140424, 140400, '长治市', 0);
INSERT INTO `china_area` VALUES (7578, '武乡县', 140429, 140400, '长治市', 0);
INSERT INTO `china_area` VALUES (7579, '襄垣县', 140423, 140400, '长治市', 0);
INSERT INTO `china_area` VALUES (7580, '长治县', 140421, 140400, '长治市', 0);
INSERT INTO `china_area` VALUES (7581, '长子县', 140428, 140400, '长治市', 0);
INSERT INTO `china_area` VALUES (7582, '运城市', 140800, 140000, '山西省', 0);
INSERT INTO `china_area` VALUES (7583, '河津市', 140882, 140800, '运城市', 0);
INSERT INTO `china_area` VALUES (7584, '绛县', 140826, 140800, '运城市', 0);
INSERT INTO `china_area` VALUES (7585, '稷山县', 140824, 140800, '运城市', 0);
INSERT INTO `china_area` VALUES (7586, '临猗县', 140821, 140800, '运城市', 0);
INSERT INTO `china_area` VALUES (7587, '平陆县', 140829, 140800, '运城市', 0);
INSERT INTO `china_area` VALUES (7588, '芮城县', 140830, 140800, '运城市', 0);
INSERT INTO `china_area` VALUES (7589, '万荣县', 140822, 140800, '运城市', 0);
INSERT INTO `china_area` VALUES (7590, '闻喜县', 140823, 140800, '运城市', 0);
INSERT INTO `china_area` VALUES (7591, '夏县', 140828, 140800, '运城市', 0);
INSERT INTO `china_area` VALUES (7592, '新绛县', 140825, 140800, '运城市', 0);
INSERT INTO `china_area` VALUES (7593, '盐湖区', 140802, 140800, '运城市', 0);
INSERT INTO `china_area` VALUES (7594, '永济市', 140881, 140800, '运城市', 0);
INSERT INTO `china_area` VALUES (7595, '垣曲县', 140827, 140800, '运城市', 0);
INSERT INTO `china_area` VALUES (7596, '台湾省', 710000, 0, '中国', 0);
INSERT INTO `china_area` VALUES (7597, '贵州省', 520000, 0, '中国', 0);
INSERT INTO `china_area` VALUES (7598, '安顺市', 520400, 520000, '贵州省', 0);
INSERT INTO `china_area` VALUES (7599, '关岭布依族苗族自治县', 520424, 520400, '安顺市', 0);
INSERT INTO `china_area` VALUES (7600, '平坝区', 520403, 520400, '安顺市', 0);
INSERT INTO `china_area` VALUES (7601, '普定县', 520422, 520400, '安顺市', 0);
INSERT INTO `china_area` VALUES (7602, '西秀区', 520402, 520400, '安顺市', 0);
INSERT INTO `china_area` VALUES (7603, '镇宁布依族苗族自治县', 520423, 520400, '安顺市', 0);
INSERT INTO `china_area` VALUES (7604, '紫云苗族布依族自治县', 520425, 520400, '安顺市', 0);
INSERT INTO `china_area` VALUES (7605, '毕节市', 520500, 520000, '贵州省', 0);
INSERT INTO `china_area` VALUES (7606, '大方县', 520521, 520500, '毕节市', 0);
INSERT INTO `china_area` VALUES (7607, '赫章县', 520527, 520500, '毕节市', 0);
INSERT INTO `china_area` VALUES (7608, '金沙县', 520523, 520500, '毕节市', 0);
INSERT INTO `china_area` VALUES (7609, '纳雍县', 520525, 520500, '毕节市', 0);
INSERT INTO `china_area` VALUES (7610, '七星关区', 520502, 520500, '毕节市', 0);
INSERT INTO `china_area` VALUES (7611, '黔西县', 520522, 520500, '毕节市', 0);
INSERT INTO `china_area` VALUES (7612, '威宁彝族回族苗族自治县', 520526, 520500, '毕节市', 0);
INSERT INTO `china_area` VALUES (7613, '织金县', 520524, 520500, '毕节市', 0);
INSERT INTO `china_area` VALUES (7614, '贵阳市', 520100, 520000, '贵州省', 0);
INSERT INTO `china_area` VALUES (7615, '白云区', 520113, 520100, '贵阳市', 0);
INSERT INTO `china_area` VALUES (7616, '观山湖区', 520115, 520100, '贵阳市', 0);
INSERT INTO `china_area` VALUES (7617, '花溪区', 520111, 520100, '贵阳市', 0);
INSERT INTO `china_area` VALUES (7618, '开阳县', 520121, 520100, '贵阳市', 0);
INSERT INTO `china_area` VALUES (7619, '南明区', 520102, 520100, '贵阳市', 0);
INSERT INTO `china_area` VALUES (7620, '清镇市', 520181, 520100, '贵阳市', 0);
INSERT INTO `china_area` VALUES (7621, '乌当区', 520112, 520100, '贵阳市', 0);
INSERT INTO `china_area` VALUES (7622, '息烽县', 520122, 520100, '贵阳市', 0);
INSERT INTO `china_area` VALUES (7623, '修文县', 520123, 520100, '贵阳市', 0);
INSERT INTO `china_area` VALUES (7624, '云岩区', 520103, 520100, '贵阳市', 0);
INSERT INTO `china_area` VALUES (7625, '六盘水市', 520200, 520000, '贵州省', 0);
INSERT INTO `china_area` VALUES (7626, '六枝特区', 520203, 520200, '六盘水市', 0);
INSERT INTO `china_area` VALUES (7627, '盘州市', 520222, 520200, '六盘水市', 0);
INSERT INTO `china_area` VALUES (7628, '水城县', 520221, 520200, '六盘水市', 0);
INSERT INTO `china_area` VALUES (7629, '钟山区', 520201, 520200, '六盘水市', 0);
INSERT INTO `china_area` VALUES (7630, '黔西南布依族苗族自治州', 522300, 520000, '贵州省', 0);
INSERT INTO `china_area` VALUES (7631, '安龙县', 522328, 522300, '黔西南布依族苗族自治州', 0);
INSERT INTO `china_area` VALUES (7632, '册亨县', 522327, 522300, '黔西南布依族苗族自治州', 0);
INSERT INTO `china_area` VALUES (7633, '普安县', 522323, 522300, '黔西南布依族苗族自治州', 0);
INSERT INTO `china_area` VALUES (7634, '晴隆县', 522324, 522300, '黔西南布依族苗族自治州', 0);
INSERT INTO `china_area` VALUES (7635, '望谟县', 522326, 522300, '黔西南布依族苗族自治州', 0);
INSERT INTO `china_area` VALUES (7636, '兴仁县', 522322, 522300, '黔西南布依族苗族自治州', 0);
INSERT INTO `china_area` VALUES (7637, '兴义市', 522301, 522300, '黔西南布依族苗族自治州', 0);
INSERT INTO `china_area` VALUES (7638, '贞丰县', 522325, 522300, '黔西南布依族苗族自治州', 0);
INSERT INTO `china_area` VALUES (7639, '黔东南苗族侗族自治州', 522600, 520000, '贵州省', 0);
INSERT INTO `china_area` VALUES (7640, '岑巩县', 522626, 522600, '黔东南苗族侗族自治州', 0);
INSERT INTO `china_area` VALUES (7641, '从江县', 522633, 522600, '黔东南苗族侗族自治州', 0);
INSERT INTO `china_area` VALUES (7642, '丹寨县', 522636, 522600, '黔东南苗族侗族自治州', 0);
INSERT INTO `china_area` VALUES (7643, '黄平县', 522622, 522600, '黔东南苗族侗族自治州', 0);
INSERT INTO `china_area` VALUES (7644, '剑河县', 522629, 522600, '黔东南苗族侗族自治州', 0);
INSERT INTO `china_area` VALUES (7645, '锦屏县', 522628, 522600, '黔东南苗族侗族自治州', 0);
INSERT INTO `china_area` VALUES (7646, '凯里市', 522601, 522600, '黔东南苗族侗族自治州', 0);
INSERT INTO `china_area` VALUES (7647, '雷山县', 522634, 522600, '黔东南苗族侗族自治州', 0);
INSERT INTO `china_area` VALUES (7648, '黎平县', 522631, 522600, '黔东南苗族侗族自治州', 0);
INSERT INTO `china_area` VALUES (7649, '麻江县', 522635, 522600, '黔东南苗族侗族自治州', 0);
INSERT INTO `china_area` VALUES (7650, '三穗县', 522624, 522600, '黔东南苗族侗族自治州', 0);
INSERT INTO `china_area` VALUES (7651, '榕江县', 522632, 522600, '黔东南苗族侗族自治州', 0);
INSERT INTO `china_area` VALUES (7652, '施秉县', 522623, 522600, '黔东南苗族侗族自治州', 0);
INSERT INTO `china_area` VALUES (7653, '台江县', 522630, 522600, '黔东南苗族侗族自治州', 0);
INSERT INTO `china_area` VALUES (7654, '天柱县', 522627, 522600, '黔东南苗族侗族自治州', 0);
INSERT INTO `china_area` VALUES (7655, '镇远县', 522625, 522600, '黔东南苗族侗族自治州', 0);
INSERT INTO `china_area` VALUES (7656, '黔南布依族苗族自治州', 522700, 520000, '贵州省', 0);
INSERT INTO `china_area` VALUES (7657, '都匀市', 522701, 522700, '黔南布依族苗族自治州', 0);
INSERT INTO `china_area` VALUES (7658, '独山县', 522726, 522700, '黔南布依族苗族自治州', 0);
INSERT INTO `china_area` VALUES (7659, '福泉市', 522702, 522700, '黔南布依族苗族自治州', 0);
INSERT INTO `china_area` VALUES (7660, '贵定县', 522723, 522700, '黔南布依族苗族自治州', 0);
INSERT INTO `china_area` VALUES (7661, '惠水县', 522731, 522700, '黔南布依族苗族自治州', 0);
INSERT INTO `china_area` VALUES (7662, '荔波县', 522722, 522700, '黔南布依族苗族自治州', 0);
INSERT INTO `china_area` VALUES (7663, '龙里县', 522730, 522700, '黔南布依族苗族自治州', 0);
INSERT INTO `china_area` VALUES (7664, '罗甸县', 522728, 522700, '黔南布依族苗族自治州', 0);
INSERT INTO `china_area` VALUES (7665, '平塘县', 522727, 522700, '黔南布依族苗族自治州', 0);
INSERT INTO `china_area` VALUES (7666, '三都水族自治县', 522732, 522700, '黔南布依族苗族自治州', 0);
INSERT INTO `china_area` VALUES (7667, '瓮安县', 522725, 522700, '黔南布依族苗族自治州', 0);
INSERT INTO `china_area` VALUES (7668, '长顺县', 522729, 522700, '黔南布依族苗族自治州', 0);
INSERT INTO `china_area` VALUES (7669, '铜仁市', 520600, 520000, '贵州省', 0);
INSERT INTO `china_area` VALUES (7670, '碧江区', 520602, 520600, '铜仁市', 0);
INSERT INTO `china_area` VALUES (7671, '德江县', 520626, 520600, '铜仁市', 0);
INSERT INTO `china_area` VALUES (7672, '江口县', 520621, 520600, '铜仁市', 0);
INSERT INTO `china_area` VALUES (7673, '石阡县', 520623, 520600, '铜仁市', 0);
INSERT INTO `china_area` VALUES (7674, '松桃苗族自治县', 520628, 520600, '铜仁市', 0);
INSERT INTO `china_area` VALUES (7675, '思南县', 520624, 520600, '铜仁市', 0);
INSERT INTO `china_area` VALUES (7676, '万山区', 520603, 520600, '铜仁市', 0);
INSERT INTO `china_area` VALUES (7677, '沿河土家族自治县', 520627, 520600, '铜仁市', 0);
INSERT INTO `china_area` VALUES (7678, '印江土家族苗族自治县', 520625, 520600, '铜仁市', 0);
INSERT INTO `china_area` VALUES (7679, '玉屏侗族自治县', 520622, 520600, '铜仁市', 0);
INSERT INTO `china_area` VALUES (7680, '遵义市', 520300, 520000, '贵州省', 0);
INSERT INTO `china_area` VALUES (7681, '播州区', 520304, 520300, '遵义市', 0);
INSERT INTO `china_area` VALUES (7682, '赤水市', 520381, 520300, '遵义市', 0);
INSERT INTO `china_area` VALUES (7683, '道真仡佬族苗族自治县', 520325, 520300, '遵义市', 0);
INSERT INTO `china_area` VALUES (7684, '凤冈县', 520327, 520300, '遵义市', 0);
INSERT INTO `china_area` VALUES (7685, '红花岗区', 520302, 520300, '遵义市', 0);
INSERT INTO `china_area` VALUES (7686, '汇川区', 520303, 520300, '遵义市', 0);
INSERT INTO `china_area` VALUES (7687, '湄潭县', 520328, 520300, '遵义市', 0);
INSERT INTO `china_area` VALUES (7688, '仁怀市', 520382, 520300, '遵义市', 0);
INSERT INTO `china_area` VALUES (7689, '绥阳县', 520323, 520300, '遵义市', 0);
INSERT INTO `china_area` VALUES (7690, '桐梓县', 520322, 520300, '遵义市', 0);
INSERT INTO `china_area` VALUES (7691, '习水县', 520330, 520300, '遵义市', 0);
INSERT INTO `china_area` VALUES (7692, '务川仡佬族苗族自治县', 520326, 520300, '遵义市', 0);
INSERT INTO `china_area` VALUES (7693, '余庆县', 520329, 520300, '遵义市', 0);
INSERT INTO `china_area` VALUES (7694, '正安县', 520324, 520300, '遵义市', 0);
INSERT INTO `china_area` VALUES (7695, '广西壮族自治区', 450000, 0, '中国', 0);
INSERT INTO `china_area` VALUES (7696, '北海市', 450500, 450000, '广西壮族自治区', 0);
INSERT INTO `china_area` VALUES (7697, '海城区', 450502, 450500, '北海市', 0);
INSERT INTO `china_area` VALUES (7698, '合浦县', 450521, 450500, '北海市', 0);
INSERT INTO `china_area` VALUES (7699, '铁山港区', 450512, 450500, '北海市', 0);
INSERT INTO `china_area` VALUES (7700, '银海区', 450503, 450500, '北海市', 0);
INSERT INTO `china_area` VALUES (7701, '百色市', 451000, 450000, '广西壮族自治区', 0);
INSERT INTO `china_area` VALUES (7702, '德保县', 451024, 451000, '百色市', 0);
INSERT INTO `china_area` VALUES (7703, '靖西市', 451081, 451000, '百色市', 0);
INSERT INTO `china_area` VALUES (7704, '乐业县', 451028, 451000, '百色市', 0);
INSERT INTO `china_area` VALUES (7705, '凌云县', 451027, 451000, '百色市', 0);
INSERT INTO `china_area` VALUES (7706, '隆林各族自治县', 451031, 451000, '百色市', 0);
INSERT INTO `china_area` VALUES (7707, '那坡县', 451026, 451000, '百色市', 0);
INSERT INTO `china_area` VALUES (7708, '平果县', 451023, 451000, '百色市', 0);
INSERT INTO `china_area` VALUES (7709, '田东县', 451022, 451000, '百色市', 0);
INSERT INTO `china_area` VALUES (7710, '田林县', 451029, 451000, '百色市', 0);
INSERT INTO `china_area` VALUES (7711, '田阳县', 451021, 451000, '百色市', 0);
INSERT INTO `china_area` VALUES (7712, '西林县', 451030, 451000, '百色市', 0);
INSERT INTO `china_area` VALUES (7713, '右江区', 451002, 451000, '百色市', 0);
INSERT INTO `china_area` VALUES (7714, '崇左市', 451400, 450000, '广西壮族自治区', 0);
INSERT INTO `china_area` VALUES (7715, '大新县', 451424, 451400, '崇左市', 0);
INSERT INTO `china_area` VALUES (7716, '扶绥县', 451421, 451400, '崇左市', 0);
INSERT INTO `china_area` VALUES (7717, '江州区', 451402, 451400, '崇左市', 0);
INSERT INTO `china_area` VALUES (7718, '龙州县', 451423, 451400, '崇左市', 0);
INSERT INTO `china_area` VALUES (7719, '宁明县', 451422, 451400, '崇左市', 0);
INSERT INTO `china_area` VALUES (7720, '凭祥市', 451481, 451400, '崇左市', 0);
INSERT INTO `china_area` VALUES (7721, '天等县', 451425, 451400, '崇左市', 0);
INSERT INTO `china_area` VALUES (7722, '防城港市', 450600, 450000, '广西壮族自治区', 0);
INSERT INTO `china_area` VALUES (7723, '东兴市', 450681, 450600, '防城港市', 0);
INSERT INTO `china_area` VALUES (7724, '防城区', 450603, 450600, '防城港市', 0);
INSERT INTO `china_area` VALUES (7725, '港口区', 450602, 450600, '防城港市', 0);
INSERT INTO `china_area` VALUES (7726, '上思县', 450621, 450600, '防城港市', 0);
INSERT INTO `china_area` VALUES (7727, '桂林市', 450300, 450000, '广西壮族自治区', 0);
INSERT INTO `china_area` VALUES (7728, '叠彩区', 450303, 450300, '桂林市', 0);
INSERT INTO `china_area` VALUES (7729, '恭城瑶族自治县', 450332, 450300, '桂林市', 0);
INSERT INTO `china_area` VALUES (7730, '灌阳县', 450327, 450300, '桂林市', 0);
INSERT INTO `china_area` VALUES (7731, '荔浦县', 450331, 450300, '桂林市', 0);
INSERT INTO `china_area` VALUES (7732, '临桂区', 450312, 450300, '桂林市', 0);
INSERT INTO `china_area` VALUES (7733, '灵川县', 450323, 450300, '桂林市', 0);
INSERT INTO `china_area` VALUES (7734, '龙胜各族自治县', 450328, 450300, '桂林市', 0);
INSERT INTO `china_area` VALUES (7735, '平乐县', 450330, 450300, '桂林市', 0);
INSERT INTO `china_area` VALUES (7736, '七星区', 450305, 450300, '桂林市', 0);
INSERT INTO `china_area` VALUES (7737, '全州县', 450324, 450300, '桂林市', 0);
INSERT INTO `china_area` VALUES (7738, '象山区', 450304, 450300, '桂林市', 0);
INSERT INTO `china_area` VALUES (7739, '兴安县', 450325, 450300, '桂林市', 0);
INSERT INTO `china_area` VALUES (7740, '秀峰区', 450302, 450300, '桂林市', 0);
INSERT INTO `china_area` VALUES (7741, '阳朔县', 450321, 450300, '桂林市', 0);
INSERT INTO `china_area` VALUES (7742, '雁山区', 450311, 450300, '桂林市', 0);
INSERT INTO `china_area` VALUES (7743, '永福县', 450326, 450300, '桂林市', 0);
INSERT INTO `china_area` VALUES (7744, '资源县', 450329, 450300, '桂林市', 0);
INSERT INTO `china_area` VALUES (7745, '贵港市', 450800, 450000, '广西壮族自治区', 0);
INSERT INTO `china_area` VALUES (7746, '港南区', 450803, 450800, '贵港市', 0);
INSERT INTO `china_area` VALUES (7747, '港北区', 450802, 450800, '贵港市', 0);
INSERT INTO `china_area` VALUES (7748, '桂平市', 450881, 450800, '贵港市', 0);
INSERT INTO `china_area` VALUES (7749, '平南县', 450821, 450800, '贵港市', 0);
INSERT INTO `china_area` VALUES (7750, '覃塘区', 450804, 450800, '贵港市', 0);
INSERT INTO `china_area` VALUES (7751, '河池市', 451200, 450000, '广西壮族自治区', 0);
INSERT INTO `china_area` VALUES (7752, '巴马瑶族自治县', 451227, 451200, '河池市', 0);
INSERT INTO `china_area` VALUES (7753, '大化瑶族自治县', 451229, 451200, '河池市', 0);
INSERT INTO `china_area` VALUES (7754, '东兰县', 451224, 451200, '河池市', 0);
INSERT INTO `china_area` VALUES (7755, '凤山县', 451223, 451200, '河池市', 0);
INSERT INTO `china_area` VALUES (7756, '都安瑶族自治县', 451228, 451200, '河池市', 0);
INSERT INTO `china_area` VALUES (7757, '环江毛南族自治县', 451226, 451200, '河池市', 0);
INSERT INTO `china_area` VALUES (7758, '金城江区', 451202, 451200, '河池市', 0);
INSERT INTO `china_area` VALUES (7759, '罗城仫佬族自治县', 451225, 451200, '河池市', 0);
INSERT INTO `china_area` VALUES (7760, '南丹县', 451221, 451200, '河池市', 0);
INSERT INTO `china_area` VALUES (7761, '天峨县', 451222, 451200, '河池市', 0);
INSERT INTO `china_area` VALUES (7762, '宜州区', 451203, 451200, '河池市', 0);
INSERT INTO `china_area` VALUES (7763, '贺州市', 451100, 450000, '广西壮族自治区', 0);
INSERT INTO `china_area` VALUES (7764, '八步区', 451102, 451100, '贺州市', 0);
INSERT INTO `china_area` VALUES (7765, '富川瑶族自治县', 451123, 451100, '贺州市', 0);
INSERT INTO `china_area` VALUES (7766, '平桂区', 451103, 451100, '贺州市', 0);
INSERT INTO `china_area` VALUES (7767, '昭平县', 451121, 451100, '贺州市', 0);
INSERT INTO `china_area` VALUES (7768, '钟山县', 451122, 451100, '贺州市', 0);
INSERT INTO `china_area` VALUES (7769, '来宾市', 451300, 450000, '广西壮族自治区', 0);
INSERT INTO `china_area` VALUES (7770, '合山市', 451381, 451300, '来宾市', 0);
INSERT INTO `china_area` VALUES (7771, '金秀瑶族自治县', 451324, 451300, '来宾市', 0);
INSERT INTO `china_area` VALUES (7772, '武宣县', 451323, 451300, '来宾市', 0);
INSERT INTO `china_area` VALUES (7773, '忻城县', 451321, 451300, '来宾市', 0);
INSERT INTO `china_area` VALUES (7774, '象州县', 451322, 451300, '来宾市', 0);
INSERT INTO `china_area` VALUES (7775, '兴宾区', 451302, 451300, '来宾市', 0);
INSERT INTO `china_area` VALUES (7776, '柳州市', 450200, 450000, '广西壮族自治区', 0);
INSERT INTO `china_area` VALUES (7777, '城中区', 450202, 450200, '柳州市', 0);
INSERT INTO `china_area` VALUES (7778, '柳城县', 450222, 450200, '柳州市', 0);
INSERT INTO `china_area` VALUES (7779, '柳北区', 450205, 450200, '柳州市', 0);
INSERT INTO `china_area` VALUES (7780, '柳江区', 450206, 450200, '柳州市', 0);
INSERT INTO `china_area` VALUES (7781, '柳南区', 450204, 450200, '柳州市', 0);
INSERT INTO `china_area` VALUES (7782, '鹿寨县', 450223, 450200, '柳州市', 0);
INSERT INTO `china_area` VALUES (7783, '融安县', 450224, 450200, '柳州市', 0);
INSERT INTO `china_area` VALUES (7784, '融水苗族自治县', 450225, 450200, '柳州市', 0);
INSERT INTO `china_area` VALUES (7785, '三江侗族自治县', 450226, 450200, '柳州市', 0);
INSERT INTO `china_area` VALUES (7786, '鱼峰区', 450203, 450200, '柳州市', 0);
INSERT INTO `china_area` VALUES (7787, '南宁市', 450100, 450000, '广西壮族自治区', 0);
INSERT INTO `china_area` VALUES (7788, '宾阳县', 450126, 450100, '南宁市', 0);
INSERT INTO `china_area` VALUES (7789, '横县', 450127, 450100, '南宁市', 0);
INSERT INTO `china_area` VALUES (7790, '江南区', 450105, 450100, '南宁市', 0);
INSERT INTO `china_area` VALUES (7791, '良庆区', 450108, 450100, '南宁市', 0);
INSERT INTO `china_area` VALUES (7792, '隆安县', 450123, 450100, '南宁市', 0);
INSERT INTO `china_area` VALUES (7793, '马山县', 450124, 450100, '南宁市', 0);
INSERT INTO `china_area` VALUES (7794, '青秀区', 450103, 450100, '南宁市', 0);
INSERT INTO `china_area` VALUES (7795, '上林县', 450125, 450100, '南宁市', 0);
INSERT INTO `china_area` VALUES (7796, '武鸣区', 450110, 450100, '南宁市', 0);
INSERT INTO `china_area` VALUES (7797, '西乡塘区', 450107, 450100, '南宁市', 0);
INSERT INTO `china_area` VALUES (7798, '兴宁区', 450102, 450100, '南宁市', 0);
INSERT INTO `china_area` VALUES (7799, '邕宁区', 450109, 450100, '南宁市', 0);
INSERT INTO `china_area` VALUES (7800, '钦州市', 450700, 450000, '广西壮族自治区', 0);
INSERT INTO `china_area` VALUES (7801, '灵山县', 450721, 450700, '钦州市', 0);
INSERT INTO `china_area` VALUES (7802, '浦北县', 450722, 450700, '钦州市', 0);
INSERT INTO `china_area` VALUES (7803, '钦北区', 450703, 450700, '钦州市', 0);
INSERT INTO `china_area` VALUES (7804, '钦南区', 450702, 450700, '钦州市', 0);
INSERT INTO `china_area` VALUES (7805, '梧州市', 450400, 450000, '广西壮族自治区', 0);
INSERT INTO `china_area` VALUES (7806, '苍梧县', 450421, 450400, '梧州市', 0);
INSERT INTO `china_area` VALUES (7807, '岑溪市', 450481, 450400, '梧州市', 0);
INSERT INTO `china_area` VALUES (7808, '龙圩区', 450406, 450400, '梧州市', 0);
INSERT INTO `china_area` VALUES (7809, '蒙山县', 450423, 450400, '梧州市', 0);
INSERT INTO `china_area` VALUES (7810, '藤县', 450422, 450400, '梧州市', 0);
INSERT INTO `china_area` VALUES (7811, '万秀区', 450403, 450400, '梧州市', 0);
INSERT INTO `china_area` VALUES (7812, '长洲区', 450405, 450400, '梧州市', 0);
INSERT INTO `china_area` VALUES (7813, '玉林市', 450900, 450000, '广西壮族自治区', 0);
INSERT INTO `china_area` VALUES (7814, '博白县', 450923, 450900, '玉林市', 0);
INSERT INTO `china_area` VALUES (7815, '北流市', 450981, 450900, '玉林市', 0);
INSERT INTO `china_area` VALUES (7816, '福绵区', 450903, 450900, '玉林市', 0);
INSERT INTO `china_area` VALUES (7817, '陆川县', 450922, 450900, '玉林市', 0);
INSERT INTO `china_area` VALUES (7818, '容县', 450921, 450900, '玉林市', 0);
INSERT INTO `china_area` VALUES (7819, '兴业县', 450924, 450900, '玉林市', 0);
INSERT INTO `china_area` VALUES (7820, '玉州区', 450902, 450900, '玉林市', 0);
INSERT INTO `china_area` VALUES (7821, '甘肃省', 620000, 0, '中国', 0);
INSERT INTO `china_area` VALUES (7822, '白银市', 620400, 620000, '甘肃省', 0);
INSERT INTO `china_area` VALUES (7823, '白银区', 620402, 620400, '白银市', 0);
INSERT INTO `china_area` VALUES (7824, '会宁县', 620422, 620400, '白银市', 0);
INSERT INTO `china_area` VALUES (7825, '靖远县', 620421, 620400, '白银市', 0);
INSERT INTO `china_area` VALUES (7826, '景泰县', 620423, 620400, '白银市', 0);
INSERT INTO `china_area` VALUES (7827, '平川区', 620403, 620400, '白银市', 0);
INSERT INTO `china_area` VALUES (7828, '甘南藏族自治州', 623000, 620000, '甘肃省', 0);
INSERT INTO `china_area` VALUES (7829, '迭部县', 623024, 623000, '甘南藏族自治州', 0);
INSERT INTO `china_area` VALUES (7830, '合作市', 623001, 623000, '甘南藏族自治州', 0);
INSERT INTO `china_area` VALUES (7831, '临潭县', 623021, 623000, '甘南藏族自治州', 0);
INSERT INTO `china_area` VALUES (7832, '碌曲县', 623026, 623000, '甘南藏族自治州', 0);
INSERT INTO `china_area` VALUES (7833, '玛曲县', 623025, 623000, '甘南藏族自治州', 0);
INSERT INTO `china_area` VALUES (7834, '夏河县', 623027, 623000, '甘南藏族自治州', 0);
INSERT INTO `china_area` VALUES (7835, '舟曲县', 623023, 623000, '甘南藏族自治州', 0);
INSERT INTO `china_area` VALUES (7836, '卓尼县', 623022, 623000, '甘南藏族自治州', 0);
INSERT INTO `china_area` VALUES (7837, '定西市', 621100, 620000, '甘肃省', 0);
INSERT INTO `china_area` VALUES (7838, '安定区', 621102, 621100, '定西市', 0);
INSERT INTO `china_area` VALUES (7839, '临洮县', 621124, 621100, '定西市', 0);
INSERT INTO `china_area` VALUES (7840, '陇西县', 621122, 621100, '定西市', 0);
INSERT INTO `china_area` VALUES (7841, '岷县', 621126, 621100, '定西市', 0);
INSERT INTO `china_area` VALUES (7842, '通渭县', 621121, 621100, '定西市', 0);
INSERT INTO `china_area` VALUES (7843, '渭源县', 621123, 621100, '定西市', 0);
INSERT INTO `china_area` VALUES (7844, '漳县', 621125, 621100, '定西市', 0);
INSERT INTO `china_area` VALUES (7845, '嘉峪关市', 620200, 620000, '甘肃省', 0);
INSERT INTO `china_area` VALUES (7846, '嘉峪关乡', 620201, 620200, '嘉峪关市', 0);
INSERT INTO `china_area` VALUES (7847, '前进路街道', 620202, 620200, '嘉峪关市', 0);
INSERT INTO `china_area` VALUES (7848, '五一路街道', 620203, 620200, '嘉峪关市', 0);
INSERT INTO `china_area` VALUES (7849, '建设路街道', 620204, 620200, '嘉峪关市', 0);
INSERT INTO `china_area` VALUES (7850, '新华路街道', 620205, 620200, '嘉峪关市', 0);
INSERT INTO `china_area` VALUES (7851, '前进街道', 620206, 620200, '嘉峪关市', 0);
INSERT INTO `china_area` VALUES (7852, '峪泉镇', 620207, 620200, '嘉峪关市', 0);
INSERT INTO `china_area` VALUES (7853, '新城镇', 620208, 620200, '嘉峪关市', 0);
INSERT INTO `china_area` VALUES (7854, '文殊镇', 620209, 620200, '嘉峪关市', 0);
INSERT INTO `china_area` VALUES (7855, '酒泉市', 620900, 620000, '甘肃省', 0);
INSERT INTO `china_area` VALUES (7856, '阿克塞哈萨克族自治县', 620924, 620900, '酒泉市', 0);
INSERT INTO `china_area` VALUES (7857, '敦煌市', 620982, 620900, '酒泉市', 0);
INSERT INTO `china_area` VALUES (7858, '瓜州县', 620922, 620900, '酒泉市', 0);
INSERT INTO `china_area` VALUES (7859, '金塔县', 620921, 620900, '酒泉市', 0);
INSERT INTO `china_area` VALUES (7860, '肃州区', 620902, 620900, '酒泉市', 0);
INSERT INTO `china_area` VALUES (7861, '肃北蒙古族自治县', 620923, 620900, '酒泉市', 0);
INSERT INTO `china_area` VALUES (7862, '玉门市', 620981, 620900, '酒泉市', 0);
INSERT INTO `china_area` VALUES (7863, '金昌市', 620300, 620000, '甘肃省', 0);
INSERT INTO `china_area` VALUES (7864, '金川区', 620302, 620300, '金昌市', 0);
INSERT INTO `china_area` VALUES (7865, '永昌县', 620321, 620300, '金昌市', 0);
INSERT INTO `china_area` VALUES (7866, '兰州市', 620100, 620000, '甘肃省', 0);
INSERT INTO `china_area` VALUES (7867, '安宁区', 620105, 620100, '兰州市', 0);
INSERT INTO `china_area` VALUES (7868, '城关区', 620102, 620100, '兰州市', 0);
INSERT INTO `china_area` VALUES (7869, '皋兰县', 620122, 620100, '兰州市', 0);
INSERT INTO `china_area` VALUES (7870, '红古区', 620111, 620100, '兰州市', 0);
INSERT INTO `china_area` VALUES (7871, '七里河区', 620103, 620100, '兰州市', 0);
INSERT INTO `china_area` VALUES (7872, '西固区', 620104, 620100, '兰州市', 0);
INSERT INTO `china_area` VALUES (7873, '永登县', 620121, 620100, '兰州市', 0);
INSERT INTO `china_area` VALUES (7874, '榆中县', 620123, 620100, '兰州市', 0);
INSERT INTO `china_area` VALUES (7875, '陇南市', 621200, 620000, '甘肃省', 0);
INSERT INTO `china_area` VALUES (7876, '成县', 621221, 621200, '陇南市', 0);
INSERT INTO `china_area` VALUES (7877, '宕昌县', 621223, 621200, '陇南市', 0);
INSERT INTO `china_area` VALUES (7878, '徽县', 621227, 621200, '陇南市', 0);
INSERT INTO `china_area` VALUES (7879, '康县', 621224, 621200, '陇南市', 0);
INSERT INTO `china_area` VALUES (7880, '礼县', 621226, 621200, '陇南市', 0);
INSERT INTO `china_area` VALUES (7881, '两当县', 621228, 621200, '陇南市', 0);
INSERT INTO `china_area` VALUES (7882, '文县', 621222, 621200, '陇南市', 0);
INSERT INTO `china_area` VALUES (7883, '武都区', 621202, 621200, '陇南市', 0);
INSERT INTO `china_area` VALUES (7884, '西和县', 621225, 621200, '陇南市', 0);
INSERT INTO `china_area` VALUES (7885, '临夏回族自治州', 622900, 620000, '甘肃省', 0);
INSERT INTO `china_area` VALUES (7886, '东乡族自治县', 622926, 622900, '临夏回族自治州', 0);
INSERT INTO `china_area` VALUES (7887, '广河县', 622924, 622900, '临夏回族自治州', 0);
INSERT INTO `china_area` VALUES (7888, '和政县', 622925, 622900, '临夏回族自治州', 0);
INSERT INTO `china_area` VALUES (7889, '积石山保安族东乡族撒拉族自治县', 622927, 622900, '临夏回族自治州', 0);
INSERT INTO `china_area` VALUES (7890, '康乐县', 622922, 622900, '临夏回族自治州', 0);
INSERT INTO `china_area` VALUES (7891, '临夏市', 622901, 622900, '临夏回族自治州', 0);
INSERT INTO `china_area` VALUES (7892, '临夏县', 622921, 622900, '临夏回族自治州', 0);
INSERT INTO `china_area` VALUES (7893, '永靖县', 622923, 622900, '临夏回族自治州', 0);
INSERT INTO `china_area` VALUES (7894, '平凉市', 620800, 620000, '甘肃省', 0);
INSERT INTO `china_area` VALUES (7895, '崇信县', 620823, 620800, '平凉市', 0);
INSERT INTO `china_area` VALUES (7896, '华亭县', 620824, 620800, '平凉市', 0);
INSERT INTO `china_area` VALUES (7897, '静宁县', 620826, 620800, '平凉市', 0);
INSERT INTO `china_area` VALUES (7898, '泾川县', 620821, 620800, '平凉市', 0);
INSERT INTO `china_area` VALUES (7899, '崆峒区', 620802, 620800, '平凉市', 0);
INSERT INTO `china_area` VALUES (7900, '灵台县', 620822, 620800, '平凉市', 0);
INSERT INTO `china_area` VALUES (7901, '庄浪县', 620825, 620800, '平凉市', 0);
INSERT INTO `china_area` VALUES (7902, '庆阳市', 621000, 620000, '甘肃省', 0);
INSERT INTO `china_area` VALUES (7903, '合水县', 621024, 621000, '庆阳市', 0);
INSERT INTO `china_area` VALUES (7904, '华池县', 621023, 621000, '庆阳市', 0);
INSERT INTO `china_area` VALUES (7905, '环县', 621022, 621000, '庆阳市', 0);
INSERT INTO `china_area` VALUES (7906, '宁县', 621026, 621000, '庆阳市', 0);
INSERT INTO `china_area` VALUES (7907, '庆城县', 621021, 621000, '庆阳市', 0);
INSERT INTO `china_area` VALUES (7908, '西峰区', 621002, 621000, '庆阳市', 0);
INSERT INTO `china_area` VALUES (7909, '镇原县', 621027, 621000, '庆阳市', 0);
INSERT INTO `china_area` VALUES (7910, '正宁县', 621025, 621000, '庆阳市', 0);
INSERT INTO `china_area` VALUES (7911, '天水市', 620500, 620000, '甘肃省', 0);
INSERT INTO `china_area` VALUES (7912, '甘谷县', 620523, 620500, '天水市', 0);
INSERT INTO `china_area` VALUES (7913, '麦积区', 620503, 620500, '天水市', 0);
INSERT INTO `china_area` VALUES (7914, '秦安县', 620522, 620500, '天水市', 0);
INSERT INTO `china_area` VALUES (7915, '秦州区', 620502, 620500, '天水市', 0);
INSERT INTO `china_area` VALUES (7916, '清水县', 620521, 620500, '天水市', 0);
INSERT INTO `china_area` VALUES (7917, '武山县', 620524, 620500, '天水市', 0);
INSERT INTO `china_area` VALUES (7918, '张家川回族自治县', 620525, 620500, '天水市', 0);
INSERT INTO `china_area` VALUES (7919, '武威市', 620600, 620000, '甘肃省', 0);
INSERT INTO `china_area` VALUES (7920, '古浪县', 620622, 620600, '武威市', 0);
INSERT INTO `china_area` VALUES (7921, '凉州区', 620602, 620600, '武威市', 0);
INSERT INTO `china_area` VALUES (7922, '民勤县', 620621, 620600, '武威市', 0);
INSERT INTO `china_area` VALUES (7923, '天祝藏族自治县', 620623, 620600, '武威市', 0);
INSERT INTO `china_area` VALUES (7924, '张掖市', 620700, 620000, '甘肃省', 0);
INSERT INTO `china_area` VALUES (7925, '甘州区', 620702, 620700, '张掖市', 0);
INSERT INTO `china_area` VALUES (7926, '高台县', 620724, 620700, '张掖市', 0);
INSERT INTO `china_area` VALUES (7927, '临泽县', 620723, 620700, '张掖市', 0);
INSERT INTO `china_area` VALUES (7928, '民乐县', 620722, 620700, '张掖市', 0);
INSERT INTO `china_area` VALUES (7929, '山丹县', 620725, 620700, '张掖市', 0);
INSERT INTO `china_area` VALUES (7930, '肃南裕固族自治县', 620721, 620700, '张掖市', 0);
INSERT INTO `china_area` VALUES (7931, '河南省', 410000, 0, '中国', 0);
INSERT INTO `china_area` VALUES (7932, '安阳市', 410500, 410000, '河南省', 0);
INSERT INTO `china_area` VALUES (7933, '安阳县', 410522, 410500, '安阳市', 0);
INSERT INTO `china_area` VALUES (7934, '北关区', 410503, 410500, '安阳市', 0);
INSERT INTO `china_area` VALUES (7935, '滑县', 410526, 410500, '安阳市', 0);
INSERT INTO `china_area` VALUES (7936, '林州市', 410581, 410500, '安阳市', 0);
INSERT INTO `china_area` VALUES (7937, '龙安区', 410506, 410500, '安阳市', 0);
INSERT INTO `china_area` VALUES (7938, '内黄县', 410527, 410500, '安阳市', 0);
INSERT INTO `china_area` VALUES (7939, '汤阴县', 410523, 410500, '安阳市', 0);
INSERT INTO `china_area` VALUES (7940, '文峰区', 410502, 410500, '安阳市', 0);
INSERT INTO `china_area` VALUES (7941, '殷都区', 410505, 410500, '安阳市', 0);
INSERT INTO `china_area` VALUES (7942, '鹤壁市', 410600, 410000, '河南省', 0);
INSERT INTO `china_area` VALUES (7943, '鹤山区', 410602, 410600, '鹤壁市', 0);
INSERT INTO `china_area` VALUES (7944, '浚县', 410621, 410600, '鹤壁市', 0);
INSERT INTO `china_area` VALUES (7945, '淇滨区', 410611, 410600, '鹤壁市', 0);
INSERT INTO `china_area` VALUES (7946, '淇县', 410622, 410600, '鹤壁市', 0);
INSERT INTO `china_area` VALUES (7947, '山城区', 410603, 410600, '鹤壁市', 0);
INSERT INTO `china_area` VALUES (7948, '焦作市', 410800, 410000, '河南省', 0);
INSERT INTO `china_area` VALUES (7949, '博爱县', 410822, 410800, '焦作市', 0);
INSERT INTO `china_area` VALUES (7950, '解放区', 410802, 410800, '焦作市', 0);
INSERT INTO `china_area` VALUES (7951, '马村区', 410804, 410800, '焦作市', 0);
INSERT INTO `china_area` VALUES (7952, '孟州市', 410883, 410800, '焦作市', 0);
INSERT INTO `china_area` VALUES (7953, '沁阳市', 410882, 410800, '焦作市', 0);
INSERT INTO `china_area` VALUES (7954, '山阳区', 410811, 410800, '焦作市', 0);
INSERT INTO `china_area` VALUES (7955, '温县', 410825, 410800, '焦作市', 0);
INSERT INTO `china_area` VALUES (7956, '武陟县', 410823, 410800, '焦作市', 0);
INSERT INTO `china_area` VALUES (7957, '修武县', 410821, 410800, '焦作市', 0);
INSERT INTO `china_area` VALUES (7958, '中站区', 410803, 410800, '焦作市', 0);
INSERT INTO `china_area` VALUES (7959, '开封市', 410200, 410000, '河南省', 0);
INSERT INTO `china_area` VALUES (7960, '鼓楼区', 410204, 410200, '开封市', 0);
INSERT INTO `china_area` VALUES (7961, '兰考县', 410225, 410200, '开封市', 0);
INSERT INTO `china_area` VALUES (7962, '龙亭区', 410202, 410200, '开封市', 0);
INSERT INTO `china_area` VALUES (7963, '杞县', 410221, 410200, '开封市', 0);
INSERT INTO `china_area` VALUES (7964, '顺河回族区', 410203, 410200, '开封市', 0);
INSERT INTO `china_area` VALUES (7965, '通许县', 410222, 410200, '开封市', 0);
INSERT INTO `china_area` VALUES (7966, '尉氏县', 410223, 410200, '开封市', 0);
INSERT INTO `china_area` VALUES (7967, '祥符区', 410212, 410200, '开封市', 0);
INSERT INTO `china_area` VALUES (7968, '禹王台区', 410205, 410200, '开封市', 0);
INSERT INTO `china_area` VALUES (7969, '漯河市', 411100, 410000, '河南省', 0);
INSERT INTO `china_area` VALUES (7970, '临颍县', 411122, 411100, '漯河市', 0);
INSERT INTO `china_area` VALUES (7971, '舞阳县', 411121, 411100, '漯河市', 0);
INSERT INTO `china_area` VALUES (7972, '郾城区', 411103, 411100, '漯河市', 0);
INSERT INTO `china_area` VALUES (7973, '源汇区', 411102, 411100, '漯河市', 0);
INSERT INTO `china_area` VALUES (7974, '召陵区', 411104, 411100, '漯河市', 0);
INSERT INTO `china_area` VALUES (7975, '洛阳市', 410300, 410000, '河南省', 0);
INSERT INTO `china_area` VALUES (7976, '瀍河回族区', 410304, 410300, '洛阳市', 0);
INSERT INTO `china_area` VALUES (7977, '吉利区', 410306, 410300, '洛阳市', 0);
INSERT INTO `china_area` VALUES (7978, '涧西区', 410305, 410300, '洛阳市', 0);
INSERT INTO `china_area` VALUES (7979, '老城区', 410302, 410300, '洛阳市', 0);
INSERT INTO `china_area` VALUES (7980, '栾川县', 410324, 410300, '洛阳市', 0);
INSERT INTO `china_area` VALUES (7981, '洛宁县', 410328, 410300, '洛阳市', 0);
INSERT INTO `china_area` VALUES (7982, '洛龙区', 410311, 410300, '洛阳市', 0);
INSERT INTO `china_area` VALUES (7983, '孟津县', 410322, 410300, '洛阳市', 0);
INSERT INTO `china_area` VALUES (7984, '汝阳县', 410326, 410300, '洛阳市', 0);
INSERT INTO `china_area` VALUES (7985, '嵩县', 410325, 410300, '洛阳市', 0);
INSERT INTO `china_area` VALUES (7986, '西工区', 410303, 410300, '洛阳市', 0);
INSERT INTO `china_area` VALUES (7987, '新安县', 410323, 410300, '洛阳市', 0);
INSERT INTO `china_area` VALUES (7988, '偃师市', 410381, 410300, '洛阳市', 0);
INSERT INTO `china_area` VALUES (7989, '伊川县', 410329, 410300, '洛阳市', 0);
INSERT INTO `china_area` VALUES (7990, '宜阳县', 410327, 410300, '洛阳市', 0);
INSERT INTO `china_area` VALUES (7991, '濮阳市', 410900, 410000, '河南省', 0);
INSERT INTO `china_area` VALUES (7992, '范县', 410926, 410900, '濮阳市', 0);
INSERT INTO `china_area` VALUES (7993, '华龙区', 410902, 410900, '濮阳市', 0);
INSERT INTO `china_area` VALUES (7994, '南乐县', 410923, 410900, '濮阳市', 0);
INSERT INTO `china_area` VALUES (7995, '濮阳县', 410928, 410900, '濮阳市', 0);
INSERT INTO `china_area` VALUES (7996, '清丰县', 410922, 410900, '濮阳市', 0);
INSERT INTO `china_area` VALUES (7997, '台前县', 410927, 410900, '濮阳市', 0);
INSERT INTO `china_area` VALUES (7998, '南阳市', 411300, 410000, '河南省', 0);
INSERT INTO `china_area` VALUES (7999, '邓州市', 411381, 411300, '南阳市', 0);
INSERT INTO `china_area` VALUES (8000, '方城县', 411322, 411300, '南阳市', 0);
INSERT INTO `china_area` VALUES (8001, '南召县', 411321, 411300, '南阳市', 0);
INSERT INTO `china_area` VALUES (8002, '内乡县', 411325, 411300, '南阳市', 0);
INSERT INTO `china_area` VALUES (8003, '社旗县', 411327, 411300, '南阳市', 0);
INSERT INTO `china_area` VALUES (8004, '唐河县', 411328, 411300, '南阳市', 0);
INSERT INTO `china_area` VALUES (8005, '桐柏县', 411330, 411300, '南阳市', 0);
INSERT INTO `china_area` VALUES (8006, '宛城区', 411302, 411300, '南阳市', 0);
INSERT INTO `china_area` VALUES (8007, '卧龙区', 411303, 411300, '南阳市', 0);
INSERT INTO `china_area` VALUES (8008, '淅川县', 411326, 411300, '南阳市', 0);
INSERT INTO `china_area` VALUES (8009, '西峡县', 411323, 411300, '南阳市', 0);
INSERT INTO `china_area` VALUES (8010, '新野县', 411329, 411300, '南阳市', 0);
INSERT INTO `china_area` VALUES (8011, '镇平县', 411324, 411300, '南阳市', 0);
INSERT INTO `china_area` VALUES (8012, '平顶山市', 410400, 410000, '河南省', 0);
INSERT INTO `china_area` VALUES (8013, '宝丰县', 410421, 410400, '平顶山市', 0);
INSERT INTO `china_area` VALUES (8014, '郏县', 410425, 410400, '平顶山市', 0);
INSERT INTO `china_area` VALUES (8015, '鲁山县', 410423, 410400, '平顶山市', 0);
INSERT INTO `china_area` VALUES (8016, '汝州市', 410482, 410400, '平顶山市', 0);
INSERT INTO `china_area` VALUES (8017, '石龙区', 410404, 410400, '平顶山市', 0);
INSERT INTO `china_area` VALUES (8018, '卫东区', 410403, 410400, '平顶山市', 0);
INSERT INTO `china_area` VALUES (8019, '舞钢市', 410481, 410400, '平顶山市', 0);
INSERT INTO `china_area` VALUES (8020, '新华区', 410402, 410400, '平顶山市', 0);
INSERT INTO `china_area` VALUES (8021, '叶县', 410422, 410400, '平顶山市', 0);
INSERT INTO `china_area` VALUES (8022, '湛河区', 410411, 410400, '平顶山市', 0);
INSERT INTO `china_area` VALUES (8023, '商丘市', 411400, 410000, '河南省', 0);
INSERT INTO `china_area` VALUES (8024, '梁园区', 411402, 411400, '商丘市', 0);
INSERT INTO `china_area` VALUES (8025, '民权县', 411421, 411400, '商丘市', 0);
INSERT INTO `china_area` VALUES (8026, '宁陵县', 411423, 411400, '商丘市', 0);
INSERT INTO `china_area` VALUES (8027, '睢县', 411422, 411400, '商丘市', 0);
INSERT INTO `china_area` VALUES (8028, '睢阳区', 411403, 411400, '商丘市', 0);
INSERT INTO `china_area` VALUES (8029, '夏邑县', 411426, 411400, '商丘市', 0);
INSERT INTO `china_area` VALUES (8030, '永城市', 411481, 411400, '商丘市', 0);
INSERT INTO `china_area` VALUES (8031, '虞城县', 411425, 411400, '商丘市', 0);
INSERT INTO `china_area` VALUES (8032, '柘城县', 411424, 411400, '商丘市', 0);
INSERT INTO `china_area` VALUES (8033, '三门峡市', 411200, 410000, '河南省', 0);
INSERT INTO `china_area` VALUES (8034, '湖滨区', 411202, 411200, '三门峡市', 0);
INSERT INTO `china_area` VALUES (8035, '灵宝市', 411282, 411200, '三门峡市', 0);
INSERT INTO `china_area` VALUES (8036, '卢氏县', 411224, 411200, '三门峡市', 0);
INSERT INTO `china_area` VALUES (8037, '渑池县', 411221, 411200, '三门峡市', 0);
INSERT INTO `china_area` VALUES (8038, '陕州区', 411203, 411200, '三门峡市', 0);
INSERT INTO `china_area` VALUES (8039, '义马市', 411281, 411200, '三门峡市', 0);
INSERT INTO `china_area` VALUES (8040, '信阳市', 411500, 410000, '河南省', 0);
INSERT INTO `china_area` VALUES (8041, '固始县', 411525, 411500, '信阳市', 0);
INSERT INTO `china_area` VALUES (8042, '光山县', 411522, 411500, '信阳市', 0);
INSERT INTO `china_area` VALUES (8043, '淮滨县', 411527, 411500, '信阳市', 0);
INSERT INTO `china_area` VALUES (8044, '潢川县', 411526, 411500, '信阳市', 0);
INSERT INTO `china_area` VALUES (8045, '罗山县', 411521, 411500, '信阳市', 0);
INSERT INTO `china_area` VALUES (8046, '平桥区', 411503, 411500, '信阳市', 0);
INSERT INTO `china_area` VALUES (8047, '商城县', 411524, 411500, '信阳市', 0);
INSERT INTO `china_area` VALUES (8048, '浉河区', 411502, 411500, '信阳市', 0);
INSERT INTO `china_area` VALUES (8049, '息县', 411528, 411500, '信阳市', 0);
INSERT INTO `china_area` VALUES (8050, '新县', 411523, 411500, '信阳市', 0);
INSERT INTO `china_area` VALUES (8051, '新乡市', 410700, 410000, '河南省', 0);
INSERT INTO `china_area` VALUES (8052, '封丘县', 410727, 410700, '新乡市', 0);
INSERT INTO `china_area` VALUES (8053, '凤泉区', 410704, 410700, '新乡市', 0);
INSERT INTO `china_area` VALUES (8054, '红旗区', 410702, 410700, '新乡市', 0);
INSERT INTO `china_area` VALUES (8055, '辉县市', 410782, 410700, '新乡市', 0);
INSERT INTO `china_area` VALUES (8056, '获嘉县', 410724, 410700, '新乡市', 0);
INSERT INTO `china_area` VALUES (8057, '牧野区', 410711, 410700, '新乡市', 0);
INSERT INTO `china_area` VALUES (8058, '卫辉市', 410781, 410700, '新乡市', 0);
INSERT INTO `china_area` VALUES (8059, '卫滨区', 410703, 410700, '新乡市', 0);
INSERT INTO `china_area` VALUES (8060, '新乡县', 410721, 410700, '新乡市', 0);
INSERT INTO `china_area` VALUES (8061, '延津县', 410726, 410700, '新乡市', 0);
INSERT INTO `china_area` VALUES (8062, '原阳县', 410725, 410700, '新乡市', 0);
INSERT INTO `china_area` VALUES (8063, '长垣县', 410728, 410700, '新乡市', 0);
INSERT INTO `china_area` VALUES (8064, '许昌市', 411000, 410000, '河南省', 0);
INSERT INTO `china_area` VALUES (8065, '建安区', 411003, 411000, '许昌市', 0);
INSERT INTO `china_area` VALUES (8066, '魏都区', 411002, 411000, '许昌市', 0);
INSERT INTO `china_area` VALUES (8067, '襄城县', 411025, 411000, '许昌市', 0);
INSERT INTO `china_area` VALUES (8068, '鄢陵县', 411024, 411000, '许昌市', 0);
INSERT INTO `china_area` VALUES (8069, '禹州市', 411081, 411000, '许昌市', 0);
INSERT INTO `china_area` VALUES (8070, '长葛市', 411082, 411000, '许昌市', 0);
INSERT INTO `china_area` VALUES (8071, '郑州市', 410100, 410000, '河南省', 0);
INSERT INTO `china_area` VALUES (8072, '登封市', 410185, 410100, '郑州市', 0);
INSERT INTO `china_area` VALUES (8073, '二七区', 410103, 410100, '郑州市', 0);
INSERT INTO `china_area` VALUES (8074, '管城回族区', 410104, 410100, '郑州市', 0);
INSERT INTO `china_area` VALUES (8075, '巩义市', 410181, 410100, '郑州市', 0);
INSERT INTO `china_area` VALUES (8076, '惠济区', 410108, 410100, '郑州市', 0);
INSERT INTO `china_area` VALUES (8077, '金水区', 410105, 410100, '郑州市', 0);
INSERT INTO `china_area` VALUES (8078, '上街区', 410106, 410100, '郑州市', 0);
INSERT INTO `china_area` VALUES (8079, '荥阳市', 410182, 410100, '郑州市', 0);
INSERT INTO `china_area` VALUES (8080, '新密市', 410183, 410100, '郑州市', 0);
INSERT INTO `china_area` VALUES (8081, '新郑市', 410184, 410100, '郑州市', 0);
INSERT INTO `china_area` VALUES (8082, '中原区', 410102, 410100, '郑州市', 0);
INSERT INTO `china_area` VALUES (8083, '中牟县', 410122, 410100, '郑州市', 0);
INSERT INTO `china_area` VALUES (8084, '周口市', 411600, 410000, '河南省', 0);
INSERT INTO `china_area` VALUES (8085, '沈丘县', 411624, 411600, '周口市', 0);
INSERT INTO `china_area` VALUES (8086, '川汇区', 411602, 411600, '周口市', 0);
INSERT INTO `china_area` VALUES (8087, '郸城县', 411625, 411600, '周口市', 0);
INSERT INTO `china_area` VALUES (8088, '扶沟县', 411621, 411600, '周口市', 0);
INSERT INTO `china_area` VALUES (8089, '淮阳县', 411626, 411600, '周口市', 0);
INSERT INTO `china_area` VALUES (8090, '鹿邑县', 411628, 411600, '周口市', 0);
INSERT INTO `china_area` VALUES (8091, '商水县', 411623, 411600, '周口市', 0);
INSERT INTO `china_area` VALUES (8092, '太康县', 411627, 411600, '周口市', 0);
INSERT INTO `china_area` VALUES (8093, '西华县', 411622, 411600, '周口市', 0);
INSERT INTO `china_area` VALUES (8094, '项城市', 411681, 411600, '周口市', 0);
INSERT INTO `china_area` VALUES (8095, '驻马店市', 411700, 410000, '河南省', 0);
INSERT INTO `china_area` VALUES (8096, '泌阳县', 411726, 411700, '驻马店市', 0);
INSERT INTO `china_area` VALUES (8097, '平舆县', 411723, 411700, '驻马店市', 0);
INSERT INTO `china_area` VALUES (8098, '确山县', 411725, 411700, '驻马店市', 0);
INSERT INTO `china_area` VALUES (8099, '汝南县', 411727, 411700, '驻马店市', 0);
INSERT INTO `china_area` VALUES (8100, '上蔡县', 411722, 411700, '驻马店市', 0);
INSERT INTO `china_area` VALUES (8101, '遂平县', 411728, 411700, '驻马店市', 0);
INSERT INTO `china_area` VALUES (8102, '西平县', 411721, 411700, '驻马店市', 0);
INSERT INTO `china_area` VALUES (8103, '新蔡县', 411729, 411700, '驻马店市', 0);
INSERT INTO `china_area` VALUES (8104, '驿城区', 411702, 411700, '驻马店市', 0);
INSERT INTO `china_area` VALUES (8105, '正阳县', 411724, 411700, '驻马店市', 0);
INSERT INTO `china_area` VALUES (8106, '济源市', 419001, 410000, '河南省', 0);
INSERT INTO `china_area` VALUES (8107, '坡头镇', 419002, 419001, '济源市', 0);
INSERT INTO `china_area` VALUES (8108, '轵城镇', 419003, 419001, '济源市', 0);
INSERT INTO `china_area` VALUES (8109, '大峪镇', 419004, 419001, '济源市', 0);
INSERT INTO `china_area` VALUES (8110, '天坛街道', 419005, 419001, '济源市', 0);
INSERT INTO `china_area` VALUES (8111, '济水街道', 419006, 419001, '济源市', 0);
INSERT INTO `china_area` VALUES (8112, '玉泉街道', 419007, 419001, '济源市', 0);
INSERT INTO `china_area` VALUES (8113, '邵原镇', 419008, 419001, '济源市', 0);
INSERT INTO `china_area` VALUES (8114, '思礼镇', 419009, 419001, '济源市', 0);
INSERT INTO `china_area` VALUES (8115, '下冶镇', 419010, 419001, '济源市', 0);
INSERT INTO `china_area` VALUES (8116, '五龙口镇', 419011, 419001, '济源市', 0);
INSERT INTO `china_area` VALUES (8117, '梨林镇', 419012, 419001, '济源市', 0);
INSERT INTO `china_area` VALUES (8118, '王屋镇', 419013, 419001, '济源市', 0);
INSERT INTO `china_area` VALUES (8119, '克井镇', 419014, 419001, '济源市', 0);
INSERT INTO `china_area` VALUES (8120, '承留镇', 419015, 419001, '济源市', 0);
INSERT INTO `china_area` VALUES (8121, '河北省', 130000, 0, '中国', 0);
INSERT INTO `china_area` VALUES (8122, '保定市', 130600, 130000, '河北省', 0);
INSERT INTO `china_area` VALUES (8123, '安国市', 130683, 130600, '保定市', 0);
INSERT INTO `china_area` VALUES (8124, '安新县', 130632, 130600, '保定市', 0);
INSERT INTO `china_area` VALUES (8125, '博野县', 130637, 130600, '保定市', 0);
INSERT INTO `china_area` VALUES (8126, '定州市', 130682, 130600, '保定市', 0);
INSERT INTO `china_area` VALUES (8127, '定兴县', 130626, 130600, '保定市', 0);
INSERT INTO `china_area` VALUES (8128, '阜平县', 130624, 130600, '保定市', 0);
INSERT INTO `china_area` VALUES (8129, '高碑店市', 130684, 130600, '保定市', 0);
INSERT INTO `china_area` VALUES (8130, '高阳县', 130628, 130600, '保定市', 0);
INSERT INTO `china_area` VALUES (8131, '竞秀区', 130602, 130600, '保定市', 0);
INSERT INTO `china_area` VALUES (8132, '涞水县', 130623, 130600, '保定市', 0);
INSERT INTO `china_area` VALUES (8133, '涞源县', 130630, 130600, '保定市', 0);
INSERT INTO `china_area` VALUES (8134, '蠡县', 130635, 130600, '保定市', 0);
INSERT INTO `china_area` VALUES (8135, '莲池区', 130606, 130600, '保定市', 0);
INSERT INTO `china_area` VALUES (8136, '满城区', 130607, 130600, '保定市', 0);
INSERT INTO `china_area` VALUES (8137, '曲阳县', 130634, 130600, '保定市', 0);
INSERT INTO `china_area` VALUES (8138, '清苑区', 130608, 130600, '保定市', 0);
INSERT INTO `china_area` VALUES (8139, '容城县', 130629, 130600, '保定市', 0);
INSERT INTO `china_area` VALUES (8140, '顺平县', 130636, 130600, '保定市', 0);
INSERT INTO `china_area` VALUES (8141, '唐县', 130627, 130600, '保定市', 0);
INSERT INTO `china_area` VALUES (8142, '望都县', 130631, 130600, '保定市', 0);
INSERT INTO `china_area` VALUES (8143, '雄县', 130638, 130600, '保定市', 0);
INSERT INTO `china_area` VALUES (8144, '徐水区', 130609, 130600, '保定市', 0);
INSERT INTO `china_area` VALUES (8145, '易县', 130633, 130600, '保定市', 0);
INSERT INTO `china_area` VALUES (8146, '涿州市', 130681, 130600, '保定市', 0);
INSERT INTO `china_area` VALUES (8147, '沧州市', 130900, 130000, '河北省', 0);
INSERT INTO `china_area` VALUES (8148, '沧县', 130921, 130900, '沧州市', 0);
INSERT INTO `china_area` VALUES (8149, '东光县', 130923, 130900, '沧州市', 0);
INSERT INTO `china_area` VALUES (8150, '海兴县', 130924, 130900, '沧州市', 0);
INSERT INTO `china_area` VALUES (8151, '河间市', 130984, 130900, '沧州市', 0);
INSERT INTO `china_area` VALUES (8152, '黄骅市', 130983, 130900, '沧州市', 0);
INSERT INTO `china_area` VALUES (8153, '孟村回族自治县', 130930, 130900, '沧州市', 0);
INSERT INTO `china_area` VALUES (8154, '南皮县', 130927, 130900, '沧州市', 0);
INSERT INTO `china_area` VALUES (8155, '泊头市', 130981, 130900, '沧州市', 0);
INSERT INTO `china_area` VALUES (8156, '青县', 130922, 130900, '沧州市', 0);
INSERT INTO `china_area` VALUES (8157, '任丘市', 130982, 130900, '沧州市', 0);
INSERT INTO `china_area` VALUES (8158, '肃宁县', 130926, 130900, '沧州市', 0);
INSERT INTO `china_area` VALUES (8159, '吴桥县', 130928, 130900, '沧州市', 0);
INSERT INTO `china_area` VALUES (8160, '献县', 130929, 130900, '沧州市', 0);
INSERT INTO `china_area` VALUES (8161, '新华区', 130902, 130900, '沧州市', 0);
INSERT INTO `china_area` VALUES (8162, '盐山县', 130925, 130900, '沧州市', 0);
INSERT INTO `china_area` VALUES (8163, '运河区', 130903, 130900, '沧州市', 0);
INSERT INTO `china_area` VALUES (8164, '承德市', 130800, 130000, '河北省', 0);
INSERT INTO `china_area` VALUES (8165, '承德县', 130821, 130800, '承德市', 0);
INSERT INTO `china_area` VALUES (8166, '丰宁满族自治县', 130826, 130800, '承德市', 0);
INSERT INTO `china_area` VALUES (8167, '宽城满族自治县', 130827, 130800, '承德市', 0);
INSERT INTO `china_area` VALUES (8168, '隆化县', 130825, 130800, '承德市', 0);
INSERT INTO `china_area` VALUES (8169, '滦平县', 130824, 130800, '承德市', 0);
INSERT INTO `china_area` VALUES (8170, '平泉市', 130881, 130800, '承德市', 0);
INSERT INTO `china_area` VALUES (8171, '双桥区', 130802, 130800, '承德市', 0);
INSERT INTO `china_area` VALUES (8172, '双滦区', 130803, 130800, '承德市', 0);
INSERT INTO `china_area` VALUES (8173, '围场满族蒙古族自治县', 130828, 130800, '承德市', 0);
INSERT INTO `china_area` VALUES (8174, '兴隆县', 130822, 130800, '承德市', 0);
INSERT INTO `china_area` VALUES (8175, '鹰手营子矿区', 130804, 130800, '承德市', 0);
INSERT INTO `china_area` VALUES (8176, '邯郸市', 130400, 130000, '河北省', 0);
INSERT INTO `china_area` VALUES (8177, '成安县', 130424, 130400, '邯郸市', 0);
INSERT INTO `china_area` VALUES (8178, '磁县', 130427, 130400, '邯郸市', 0);
INSERT INTO `china_area` VALUES (8179, '丛台区', 130403, 130400, '邯郸市', 0);
INSERT INTO `china_area` VALUES (8180, '大名县', 130425, 130400, '邯郸市', 0);
INSERT INTO `china_area` VALUES (8181, '肥乡区', 130407, 130400, '邯郸市', 0);
INSERT INTO `china_area` VALUES (8182, '峰峰矿区', 130406, 130400, '邯郸市', 0);
INSERT INTO `china_area` VALUES (8183, '复兴区', 130404, 130400, '邯郸市', 0);
INSERT INTO `china_area` VALUES (8184, '广平县', 130432, 130400, '邯郸市', 0);
INSERT INTO `china_area` VALUES (8185, '馆陶县', 130433, 130400, '邯郸市', 0);
INSERT INTO `china_area` VALUES (8186, '邯山区', 130402, 130400, '邯郸市', 0);
INSERT INTO `china_area` VALUES (8187, '鸡泽县', 130431, 130400, '邯郸市', 0);
INSERT INTO `china_area` VALUES (8188, '临漳县', 130423, 130400, '邯郸市', 0);
INSERT INTO `china_area` VALUES (8189, '邱县', 130430, 130400, '邯郸市', 0);
INSERT INTO `china_area` VALUES (8190, '曲周县', 130435, 130400, '邯郸市', 0);
INSERT INTO `china_area` VALUES (8191, '涉县', 130426, 130400, '邯郸市', 0);
INSERT INTO `china_area` VALUES (8192, '魏县', 130434, 130400, '邯郸市', 0);
INSERT INTO `china_area` VALUES (8193, '武安市', 130481, 130400, '邯郸市', 0);
INSERT INTO `china_area` VALUES (8194, '永年区', 130408, 130400, '邯郸市', 0);
INSERT INTO `china_area` VALUES (8195, '衡水市', 131100, 130000, '河北省', 0);
INSERT INTO `china_area` VALUES (8196, '安平县', 131125, 131100, '衡水市', 0);
INSERT INTO `china_area` VALUES (8197, '阜城县', 131128, 131100, '衡水市', 0);
INSERT INTO `china_area` VALUES (8198, '故城县', 131126, 131100, '衡水市', 0);
INSERT INTO `china_area` VALUES (8199, '冀州区', 131103, 131100, '衡水市', 0);
INSERT INTO `china_area` VALUES (8200, '景县', 131127, 131100, '衡水市', 0);
INSERT INTO `china_area` VALUES (8201, '饶阳县', 131124, 131100, '衡水市', 0);
INSERT INTO `china_area` VALUES (8202, '深州市', 131182, 131100, '衡水市', 0);
INSERT INTO `china_area` VALUES (8203, '桃城区', 131102, 131100, '衡水市', 0);
INSERT INTO `china_area` VALUES (8204, '武邑县', 131122, 131100, '衡水市', 0);
INSERT INTO `china_area` VALUES (8205, '武强县', 131123, 131100, '衡水市', 0);
INSERT INTO `china_area` VALUES (8206, '枣强县', 131121, 131100, '衡水市', 0);
INSERT INTO `china_area` VALUES (8207, '廊坊市', 131000, 130000, '河北省', 0);
INSERT INTO `china_area` VALUES (8208, '霸州市', 131081, 131000, '廊坊市', 0);
INSERT INTO `china_area` VALUES (8209, '安次区', 131002, 131000, '廊坊市', 0);
INSERT INTO `china_area` VALUES (8210, '大厂回族自治县', 131028, 131000, '廊坊市', 0);
INSERT INTO `china_area` VALUES (8211, '大城县', 131025, 131000, '廊坊市', 0);
INSERT INTO `china_area` VALUES (8212, '固安县', 131022, 131000, '廊坊市', 0);
INSERT INTO `china_area` VALUES (8213, '广阳区', 131003, 131000, '廊坊市', 0);
INSERT INTO `china_area` VALUES (8214, '三河市', 131082, 131000, '廊坊市', 0);
INSERT INTO `china_area` VALUES (8215, '文安县', 131026, 131000, '廊坊市', 0);
INSERT INTO `china_area` VALUES (8216, '香河县', 131024, 131000, '廊坊市', 0);
INSERT INTO `china_area` VALUES (8217, '永清县', 131023, 131000, '廊坊市', 0);
INSERT INTO `china_area` VALUES (8218, '秦皇岛市', 130300, 130000, '河北省', 0);
INSERT INTO `china_area` VALUES (8219, '北戴河区', 130304, 130300, '秦皇岛市', 0);
INSERT INTO `china_area` VALUES (8220, '昌黎县', 130322, 130300, '秦皇岛市', 0);
INSERT INTO `china_area` VALUES (8221, '抚宁区', 130306, 130300, '秦皇岛市', 0);
INSERT INTO `china_area` VALUES (8222, '海港区', 130302, 130300, '秦皇岛市', 0);
INSERT INTO `china_area` VALUES (8223, '卢龙县', 130324, 130300, '秦皇岛市', 0);
INSERT INTO `china_area` VALUES (8224, '青龙满族自治县', 130321, 130300, '秦皇岛市', 0);
INSERT INTO `china_area` VALUES (8225, '山海关区', 130303, 130300, '秦皇岛市', 0);
INSERT INTO `china_area` VALUES (8226, '石家庄市', 130100, 130000, '河北省', 0);
INSERT INTO `china_area` VALUES (8227, '藁城区', 130109, 130100, '石家庄市', 0);
INSERT INTO `china_area` VALUES (8228, '高邑县', 130127, 130100, '石家庄市', 0);
INSERT INTO `china_area` VALUES (8229, '井陉县', 130121, 130100, '石家庄市', 0);
INSERT INTO `china_area` VALUES (8230, '晋州市', 130183, 130100, '石家庄市', 0);
INSERT INTO `china_area` VALUES (8231, '井陉矿区', 130107, 130100, '石家庄市', 0);
INSERT INTO `china_area` VALUES (8232, '灵寿县', 130126, 130100, '石家庄市', 0);
INSERT INTO `china_area` VALUES (8233, '鹿泉区', 130110, 130100, '石家庄市', 0);
INSERT INTO `china_area` VALUES (8234, '栾城区', 130111, 130100, '石家庄市', 0);
INSERT INTO `china_area` VALUES (8235, '平山县', 130131, 130100, '石家庄市', 0);
INSERT INTO `china_area` VALUES (8236, '桥西区', 130104, 130100, '石家庄市', 0);
INSERT INTO `china_area` VALUES (8237, '深泽县', 130128, 130100, '石家庄市', 0);
INSERT INTO `china_area` VALUES (8238, '无极县', 130130, 130100, '石家庄市', 0);
INSERT INTO `china_area` VALUES (8239, '辛集市', 130181, 130100, '石家庄市', 0);
INSERT INTO `china_area` VALUES (8240, '行唐县', 130125, 130100, '石家庄市', 0);
INSERT INTO `china_area` VALUES (8241, '新乐市', 130184, 130100, '石家庄市', 0);
INSERT INTO `china_area` VALUES (8242, '新华区', 130105, 130100, '石家庄市', 0);
INSERT INTO `china_area` VALUES (8243, '裕华区', 130108, 130100, '石家庄市', 0);
INSERT INTO `china_area` VALUES (8244, '元氏县', 130132, 130100, '石家庄市', 0);
INSERT INTO `china_area` VALUES (8245, '赞皇县', 130129, 130100, '石家庄市', 0);
INSERT INTO `china_area` VALUES (8246, '长安区', 130102, 130100, '石家庄市', 0);
INSERT INTO `china_area` VALUES (8247, '正定县', 130123, 130100, '石家庄市', 0);
INSERT INTO `china_area` VALUES (8248, '赵县', 130133, 130100, '石家庄市', 0);
INSERT INTO `china_area` VALUES (8249, '唐山市', 130200, 130000, '河北省', 0);
INSERT INTO `china_area` VALUES (8250, '曹妃甸区', 130209, 130200, '唐山市', 0);
INSERT INTO `china_area` VALUES (8251, '丰南区', 130207, 130200, '唐山市', 0);
INSERT INTO `china_area` VALUES (8252, '丰润区', 130208, 130200, '唐山市', 0);
INSERT INTO `china_area` VALUES (8253, '古冶区', 130204, 130200, '唐山市', 0);
INSERT INTO `china_area` VALUES (8254, '开平区', 130205, 130200, '唐山市', 0);
INSERT INTO `china_area` VALUES (8255, '乐亭县', 130225, 130200, '唐山市', 0);
INSERT INTO `china_area` VALUES (8256, '路南区', 130202, 130200, '唐山市', 0);
INSERT INTO `china_area` VALUES (8257, '路北区', 130203, 130200, '唐山市', 0);
INSERT INTO `china_area` VALUES (8258, '滦县', 130223, 130200, '唐山市', 0);
INSERT INTO `china_area` VALUES (8259, '滦南县', 130224, 130200, '唐山市', 0);
INSERT INTO `china_area` VALUES (8260, '迁西县', 130227, 130200, '唐山市', 0);
INSERT INTO `china_area` VALUES (8261, '迁安市', 130283, 130200, '唐山市', 0);
INSERT INTO `china_area` VALUES (8262, '玉田县', 130229, 130200, '唐山市', 0);
INSERT INTO `china_area` VALUES (8263, '遵化市', 130281, 130200, '唐山市', 0);
INSERT INTO `china_area` VALUES (8264, '邢台市', 130500, 130000, '河北省', 0);
INSERT INTO `china_area` VALUES (8265, '柏乡县', 130524, 130500, '邢台市', 0);
INSERT INTO `china_area` VALUES (8266, '广宗县', 130531, 130500, '邢台市', 0);
INSERT INTO `china_area` VALUES (8267, '巨鹿县', 130529, 130500, '邢台市', 0);
INSERT INTO `china_area` VALUES (8268, '临城县', 130522, 130500, '邢台市', 0);
INSERT INTO `china_area` VALUES (8269, '临西县', 130535, 130500, '邢台市', 0);
INSERT INTO `china_area` VALUES (8270, '隆尧县', 130525, 130500, '邢台市', 0);
INSERT INTO `china_area` VALUES (8271, '内丘县', 130523, 130500, '邢台市', 0);
INSERT INTO `china_area` VALUES (8272, '南和县', 130527, 130500, '邢台市', 0);
INSERT INTO `china_area` VALUES (8273, '南宫市', 130581, 130500, '邢台市', 0);
INSERT INTO `china_area` VALUES (8274, '宁晋县', 130528, 130500, '邢台市', 0);
INSERT INTO `china_area` VALUES (8275, '平乡县', 130532, 130500, '邢台市', 0);
INSERT INTO `china_area` VALUES (8276, '桥东区', 130502, 130500, '邢台市', 0);
INSERT INTO `china_area` VALUES (8277, '桥西区', 130503, 130500, '邢台市', 0);
INSERT INTO `china_area` VALUES (8278, '清河县', 130534, 130500, '邢台市', 0);
INSERT INTO `china_area` VALUES (8279, '任县', 130526, 130500, '邢台市', 0);
INSERT INTO `china_area` VALUES (8280, '沙河市', 130582, 130500, '邢台市', 0);
INSERT INTO `china_area` VALUES (8281, '威县', 130533, 130500, '邢台市', 0);
INSERT INTO `china_area` VALUES (8282, '新河县', 130530, 130500, '邢台市', 0);
INSERT INTO `china_area` VALUES (8283, '邢台县', 130521, 130500, '邢台市', 0);
INSERT INTO `china_area` VALUES (8284, '张家口市', 130700, 130000, '河北省', 0);
INSERT INTO `china_area` VALUES (8285, '赤城县', 130732, 130700, '张家口市', 0);
INSERT INTO `china_area` VALUES (8286, '崇礼区', 130709, 130700, '张家口市', 0);
INSERT INTO `china_area` VALUES (8287, '沽源县', 130724, 130700, '张家口市', 0);
INSERT INTO `china_area` VALUES (8288, '怀安县', 130728, 130700, '张家口市', 0);
INSERT INTO `china_area` VALUES (8289, '怀来县', 130730, 130700, '张家口市', 0);
INSERT INTO `china_area` VALUES (8290, '康保县', 130723, 130700, '张家口市', 0);
INSERT INTO `china_area` VALUES (8291, '桥东区', 130702, 130700, '张家口市', 0);
INSERT INTO `china_area` VALUES (8292, '桥西区', 130703, 130700, '张家口市', 0);
INSERT INTO `china_area` VALUES (8293, '尚义县', 130725, 130700, '张家口市', 0);
INSERT INTO `china_area` VALUES (8294, '万全区', 130708, 130700, '张家口市', 0);
INSERT INTO `china_area` VALUES (8295, '蔚县', 130726, 130700, '张家口市', 0);
INSERT INTO `china_area` VALUES (8296, '下花园区', 130706, 130700, '张家口市', 0);
INSERT INTO `china_area` VALUES (8297, '宣化区', 130705, 130700, '张家口市', 0);
INSERT INTO `china_area` VALUES (8298, '阳原县', 130727, 130700, '张家口市', 0);
INSERT INTO `china_area` VALUES (8299, '张北县', 130722, 130700, '张家口市', 0);
INSERT INTO `china_area` VALUES (8300, '涿鹿县', 130731, 130700, '张家口市', 0);
INSERT INTO `china_area` VALUES (8301, '安徽省', 340000, 0, '中国', 0);
INSERT INTO `china_area` VALUES (8302, '安庆市', 340800, 340000, '安徽省', 0);
INSERT INTO `china_area` VALUES (8303, '大观区', 340803, 340800, '安庆市', 0);
INSERT INTO `china_area` VALUES (8304, '怀宁县', 340822, 340800, '安庆市', 0);
INSERT INTO `china_area` VALUES (8305, '潜山县', 340824, 340800, '安庆市', 0);
INSERT INTO `china_area` VALUES (8306, '宿松县', 340826, 340800, '安庆市', 0);
INSERT INTO `china_area` VALUES (8307, '太湖县', 340825, 340800, '安庆市', 0);
INSERT INTO `china_area` VALUES (8308, '桐城市', 340881, 340800, '安庆市', 0);
INSERT INTO `china_area` VALUES (8309, '望江县', 340827, 340800, '安庆市', 0);
INSERT INTO `china_area` VALUES (8310, '宜秀区', 340811, 340800, '安庆市', 0);
INSERT INTO `china_area` VALUES (8311, '迎江区', 340802, 340800, '安庆市', 0);
INSERT INTO `china_area` VALUES (8312, '岳西县', 340828, 340800, '安庆市', 0);
INSERT INTO `china_area` VALUES (8313, '蚌埠市', 340300, 340000, '安徽省', 0);
INSERT INTO `china_area` VALUES (8314, '蚌山区', 340303, 340300, '蚌埠市', 0);
INSERT INTO `china_area` VALUES (8315, '固镇县', 340323, 340300, '蚌埠市', 0);
INSERT INTO `china_area` VALUES (8316, '怀远县', 340321, 340300, '蚌埠市', 0);
INSERT INTO `china_area` VALUES (8317, '淮上区', 340311, 340300, '蚌埠市', 0);
INSERT INTO `china_area` VALUES (8318, '龙子湖区', 340302, 340300, '蚌埠市', 0);
INSERT INTO `china_area` VALUES (8319, '五河县', 340322, 340300, '蚌埠市', 0);
INSERT INTO `china_area` VALUES (8320, '禹会区', 340304, 340300, '蚌埠市', 0);
INSERT INTO `china_area` VALUES (8321, '亳州市', 341600, 340000, '安徽省', 0);
INSERT INTO `china_area` VALUES (8322, '利辛县', 341623, 341600, '亳州市', 0);
INSERT INTO `china_area` VALUES (8323, '蒙城县', 341622, 341600, '亳州市', 0);
INSERT INTO `china_area` VALUES (8324, '谯城区', 341602, 341600, '亳州市', 0);
INSERT INTO `china_area` VALUES (8325, '涡阳县', 341621, 341600, '亳州市', 0);
INSERT INTO `china_area` VALUES (8326, '滁州市', 341100, 340000, '安徽省', 0);
INSERT INTO `china_area` VALUES (8327, '定远县', 341125, 341100, '滁州市', 0);
INSERT INTO `china_area` VALUES (8328, '凤阳县', 341126, 341100, '滁州市', 0);
INSERT INTO `china_area` VALUES (8329, '来安县', 341122, 341100, '滁州市', 0);
INSERT INTO `china_area` VALUES (8330, '琅琊区', 341102, 341100, '滁州市', 0);
INSERT INTO `china_area` VALUES (8331, '明光市', 341182, 341100, '滁州市', 0);
INSERT INTO `china_area` VALUES (8332, '南谯区', 341103, 341100, '滁州市', 0);
INSERT INTO `china_area` VALUES (8333, '全椒县', 341124, 341100, '滁州市', 0);
INSERT INTO `china_area` VALUES (8334, '天长市', 341181, 341100, '滁州市', 0);
INSERT INTO `china_area` VALUES (8335, '池州市', 341700, 340000, '安徽省', 0);
INSERT INTO `china_area` VALUES (8336, '东至县', 341721, 341700, '池州市', 0);
INSERT INTO `china_area` VALUES (8337, '贵池区', 341702, 341700, '池州市', 0);
INSERT INTO `china_area` VALUES (8338, '青阳县', 341723, 341700, '池州市', 0);
INSERT INTO `china_area` VALUES (8339, '石台县', 341722, 341700, '池州市', 0);
INSERT INTO `china_area` VALUES (8340, '阜阳市', 341200, 340000, '安徽省', 0);
INSERT INTO `china_area` VALUES (8341, '阜南县', 341225, 341200, '阜阳市', 0);
INSERT INTO `china_area` VALUES (8342, '界首市', 341282, 341200, '阜阳市', 0);
INSERT INTO `china_area` VALUES (8343, '临泉县', 341221, 341200, '阜阳市', 0);
INSERT INTO `china_area` VALUES (8344, '太和县', 341222, 341200, '阜阳市', 0);
INSERT INTO `china_area` VALUES (8345, '颍东区', 341203, 341200, '阜阳市', 0);
INSERT INTO `china_area` VALUES (8346, '颍州区', 341202, 341200, '阜阳市', 0);
INSERT INTO `china_area` VALUES (8347, '颍泉区', 341204, 341200, '阜阳市', 0);
INSERT INTO `china_area` VALUES (8348, '颍上县', 341226, 341200, '阜阳市', 0);
INSERT INTO `china_area` VALUES (8349, '淮南市', 340400, 340000, '安徽省', 0);
INSERT INTO `china_area` VALUES (8350, '八公山区', 340405, 340400, '淮南市', 0);
INSERT INTO `china_area` VALUES (8351, '大通区', 340402, 340400, '淮南市', 0);
INSERT INTO `china_area` VALUES (8352, '凤台县', 340421, 340400, '淮南市', 0);
INSERT INTO `china_area` VALUES (8353, '潘集区', 340406, 340400, '淮南市', 0);
INSERT INTO `china_area` VALUES (8354, '寿县', 340422, 340400, '淮南市', 0);
INSERT INTO `china_area` VALUES (8355, '田家庵区', 340403, 340400, '淮南市', 0);
INSERT INTO `china_area` VALUES (8356, '谢家集区', 340404, 340400, '淮南市', 0);
INSERT INTO `china_area` VALUES (8357, '合肥市', 340100, 340000, '安徽省', 0);
INSERT INTO `china_area` VALUES (8358, '包河区', 340111, 340100, '合肥市', 0);
INSERT INTO `china_area` VALUES (8359, '巢湖市', 340181, 340100, '合肥市', 0);
INSERT INTO `china_area` VALUES (8360, '肥西县', 340123, 340100, '合肥市', 0);
INSERT INTO `china_area` VALUES (8361, '肥东县', 340122, 340100, '合肥市', 0);
INSERT INTO `china_area` VALUES (8362, '庐阳区', 340103, 340100, '合肥市', 0);
INSERT INTO `china_area` VALUES (8363, '庐江县', 340124, 340100, '合肥市', 0);
INSERT INTO `china_area` VALUES (8364, '蜀山区', 340104, 340100, '合肥市', 0);
INSERT INTO `china_area` VALUES (8365, '瑶海区', 340102, 340100, '合肥市', 0);
INSERT INTO `china_area` VALUES (8366, '长丰县', 340121, 340100, '合肥市', 0);
INSERT INTO `china_area` VALUES (8367, '淮北市', 340600, 340000, '安徽省', 0);
INSERT INTO `china_area` VALUES (8368, '杜集区', 340602, 340600, '淮北市', 0);
INSERT INTO `china_area` VALUES (8369, '烈山区', 340604, 340600, '淮北市', 0);
INSERT INTO `china_area` VALUES (8370, '濉溪县', 340621, 340600, '淮北市', 0);
INSERT INTO `china_area` VALUES (8371, '相山区', 340603, 340600, '淮北市', 0);
INSERT INTO `china_area` VALUES (8372, '黄山市', 341000, 340000, '安徽省', 0);
INSERT INTO `china_area` VALUES (8373, '黄山区', 341003, 341000, '黄山市', 0);
INSERT INTO `china_area` VALUES (8374, '徽州区', 341004, 341000, '黄山市', 0);
INSERT INTO `china_area` VALUES (8375, '祁门县', 341024, 341000, '黄山市', 0);
INSERT INTO `china_area` VALUES (8376, '歙县', 341021, 341000, '黄山市', 0);
INSERT INTO `china_area` VALUES (8377, '屯溪区', 341002, 341000, '黄山市', 0);
INSERT INTO `china_area` VALUES (8378, '休宁县', 341022, 341000, '黄山市', 0);
INSERT INTO `china_area` VALUES (8379, '黟县', 341023, 341000, '黄山市', 0);
INSERT INTO `china_area` VALUES (8380, '马鞍山市', 340500, 340000, '安徽省', 0);
INSERT INTO `china_area` VALUES (8381, '博望区', 340506, 340500, '马鞍山市', 0);
INSERT INTO `china_area` VALUES (8382, '当涂县', 340521, 340500, '马鞍山市', 0);
INSERT INTO `china_area` VALUES (8383, '含山县', 340522, 340500, '马鞍山市', 0);
INSERT INTO `china_area` VALUES (8384, '和县', 340523, 340500, '马鞍山市', 0);
INSERT INTO `china_area` VALUES (8385, '花山区', 340503, 340500, '马鞍山市', 0);
INSERT INTO `china_area` VALUES (8386, '雨山区', 340504, 340500, '马鞍山市', 0);
INSERT INTO `china_area` VALUES (8387, '六安市', 341500, 340000, '安徽省', 0);
INSERT INTO `china_area` VALUES (8388, '霍山县', 341525, 341500, '六安市', 0);
INSERT INTO `china_area` VALUES (8389, '霍邱县', 341522, 341500, '六安市', 0);
INSERT INTO `china_area` VALUES (8390, '金安区', 341502, 341500, '六安市', 0);
INSERT INTO `china_area` VALUES (8391, '金寨县', 341524, 341500, '六安市', 0);
INSERT INTO `china_area` VALUES (8392, '舒城县', 341523, 341500, '六安市', 0);
INSERT INTO `china_area` VALUES (8393, '叶集区', 341504, 341500, '六安市', 0);
INSERT INTO `china_area` VALUES (8394, '裕安区', 341503, 341500, '六安市', 0);
INSERT INTO `china_area` VALUES (8395, '铜陵市', 340700, 340000, '安徽省', 0);
INSERT INTO `china_area` VALUES (8396, '枞阳县', 340722, 340700, '铜陵市', 0);
INSERT INTO `china_area` VALUES (8397, '郊区', 340711, 340700, '铜陵市', 0);
INSERT INTO `china_area` VALUES (8398, '铜官区', 340705, 340700, '铜陵市', 0);
INSERT INTO `china_area` VALUES (8399, '义安区', 340706, 340700, '铜陵市', 0);
INSERT INTO `china_area` VALUES (8400, '宿州市', 341300, 340000, '安徽省', 0);
INSERT INTO `china_area` VALUES (8401, '砀山县', 341321, 341300, '宿州市', 0);
INSERT INTO `china_area` VALUES (8402, '灵璧县', 341323, 341300, '宿州市', 0);
INSERT INTO `china_area` VALUES (8403, '泗县', 341324, 341300, '宿州市', 0);
INSERT INTO `china_area` VALUES (8404, '萧县', 341322, 341300, '宿州市', 0);
INSERT INTO `china_area` VALUES (8405, '埇桥区', 341302, 341300, '宿州市', 0);
INSERT INTO `china_area` VALUES (8406, '芜湖市', 340200, 340000, '安徽省', 0);
INSERT INTO `china_area` VALUES (8407, '繁昌县', 340222, 340200, '芜湖市', 0);
INSERT INTO `china_area` VALUES (8408, '鸠江区', 340207, 340200, '芜湖市', 0);
INSERT INTO `china_area` VALUES (8409, '镜湖区', 340202, 340200, '芜湖市', 0);
INSERT INTO `china_area` VALUES (8410, '南陵县', 340223, 340200, '芜湖市', 0);
INSERT INTO `china_area` VALUES (8411, '三山区', 340208, 340200, '芜湖市', 0);
INSERT INTO `china_area` VALUES (8412, '无为县', 340225, 340200, '芜湖市', 0);
INSERT INTO `china_area` VALUES (8413, '芜湖县', 340221, 340200, '芜湖市', 0);
INSERT INTO `china_area` VALUES (8414, '弋江区', 340203, 340200, '芜湖市', 0);
INSERT INTO `china_area` VALUES (8415, '宣城市', 341800, 340000, '安徽省', 0);
INSERT INTO `china_area` VALUES (8416, '广德县', 341822, 341800, '宣城市', 0);
INSERT INTO `china_area` VALUES (8417, '绩溪县', 341824, 341800, '宣城市', 0);
INSERT INTO `china_area` VALUES (8418, '泾县', 341823, 341800, '宣城市', 0);
INSERT INTO `china_area` VALUES (8419, '旌德县', 341825, 341800, '宣城市', 0);
INSERT INTO `china_area` VALUES (8420, '郎溪县', 341821, 341800, '宣城市', 0);
INSERT INTO `china_area` VALUES (8421, '宁国市', 341881, 341800, '宣城市', 0);
INSERT INTO `china_area` VALUES (8422, '宣州区', 341802, 341800, '宣城市', 0);
INSERT INTO `china_area` VALUES (8423, '湖南省', 430000, 0, '中国', 0);
INSERT INTO `china_area` VALUES (8424, '常德市', 430700, 430000, '湖南省', 0);
INSERT INTO `china_area` VALUES (8425, '安乡县', 430721, 430700, '常德市', 0);
INSERT INTO `china_area` VALUES (8426, '鼎城区', 430703, 430700, '常德市', 0);
INSERT INTO `china_area` VALUES (8427, '汉寿县', 430722, 430700, '常德市', 0);
INSERT INTO `china_area` VALUES (8428, '津市市', 430781, 430700, '常德市', 0);
INSERT INTO `china_area` VALUES (8429, '澧县', 430723, 430700, '常德市', 0);
INSERT INTO `china_area` VALUES (8430, '临澧县', 430724, 430700, '常德市', 0);
INSERT INTO `china_area` VALUES (8431, '石门县', 430726, 430700, '常德市', 0);
INSERT INTO `china_area` VALUES (8432, '桃源县', 430725, 430700, '常德市', 0);
INSERT INTO `china_area` VALUES (8433, '武陵区', 430702, 430700, '常德市', 0);
INSERT INTO `china_area` VALUES (8434, '郴州市', 431000, 430000, '湖南省', 0);
INSERT INTO `china_area` VALUES (8435, '安仁县', 431028, 431000, '郴州市', 0);
INSERT INTO `china_area` VALUES (8436, '北湖区', 431002, 431000, '郴州市', 0);
INSERT INTO `china_area` VALUES (8437, '桂阳县', 431021, 431000, '郴州市', 0);
INSERT INTO `china_area` VALUES (8438, '桂东县', 431027, 431000, '郴州市', 0);
INSERT INTO `china_area` VALUES (8439, '嘉禾县', 431024, 431000, '郴州市', 0);
INSERT INTO `china_area` VALUES (8440, '临武县', 431025, 431000, '郴州市', 0);
INSERT INTO `china_area` VALUES (8441, '汝城县', 431026, 431000, '郴州市', 0);
INSERT INTO `china_area` VALUES (8442, '苏仙区', 431003, 431000, '郴州市', 0);
INSERT INTO `china_area` VALUES (8443, '宜章县', 431022, 431000, '郴州市', 0);
INSERT INTO `china_area` VALUES (8444, '永兴县', 431023, 431000, '郴州市', 0);
INSERT INTO `china_area` VALUES (8445, '资兴市', 431081, 431000, '郴州市', 0);
INSERT INTO `china_area` VALUES (8446, '衡阳市', 430400, 430000, '湖南省', 0);
INSERT INTO `china_area` VALUES (8447, '常宁市', 430482, 430400, '衡阳市', 0);
INSERT INTO `china_area` VALUES (8448, '衡南县', 430422, 430400, '衡阳市', 0);
INSERT INTO `china_area` VALUES (8449, '衡山县', 430423, 430400, '衡阳市', 0);
INSERT INTO `china_area` VALUES (8450, '衡阳县', 430421, 430400, '衡阳市', 0);
INSERT INTO `china_area` VALUES (8451, '衡东县', 430424, 430400, '衡阳市', 0);
INSERT INTO `china_area` VALUES (8452, '耒阳市', 430481, 430400, '衡阳市', 0);
INSERT INTO `china_area` VALUES (8453, '南岳区', 430412, 430400, '衡阳市', 0);
INSERT INTO `china_area` VALUES (8454, '祁东县', 430426, 430400, '衡阳市', 0);
INSERT INTO `china_area` VALUES (8455, '石鼓区', 430407, 430400, '衡阳市', 0);
INSERT INTO `china_area` VALUES (8456, '雁峰区', 430406, 430400, '衡阳市', 0);
INSERT INTO `china_area` VALUES (8457, '蒸湘区', 430408, 430400, '衡阳市', 0);
INSERT INTO `china_area` VALUES (8458, '珠晖区', 430405, 430400, '衡阳市', 0);
INSERT INTO `china_area` VALUES (8459, '娄底市', 431300, 430000, '湖南省', 0);
INSERT INTO `china_area` VALUES (8460, '冷水江市', 431381, 431300, '娄底市', 0);
INSERT INTO `china_area` VALUES (8461, '涟源市', 431382, 431300, '娄底市', 0);
INSERT INTO `china_area` VALUES (8462, '娄星区', 431302, 431300, '娄底市', 0);
INSERT INTO `china_area` VALUES (8463, '双峰县', 431321, 431300, '娄底市', 0);
INSERT INTO `china_area` VALUES (8464, '新化县', 431322, 431300, '娄底市', 0);
INSERT INTO `china_area` VALUES (8465, '邵阳市', 430500, 430000, '湖南省', 0);
INSERT INTO `china_area` VALUES (8466, '北塔区', 430511, 430500, '邵阳市', 0);
INSERT INTO `china_area` VALUES (8467, '城步苗族自治县', 430529, 430500, '邵阳市', 0);
INSERT INTO `china_area` VALUES (8468, '大祥区', 430503, 430500, '邵阳市', 0);
INSERT INTO `china_area` VALUES (8469, '洞口县', 430525, 430500, '邵阳市', 0);
INSERT INTO `china_area` VALUES (8470, '隆回县', 430524, 430500, '邵阳市', 0);
INSERT INTO `china_area` VALUES (8471, '邵东县', 430521, 430500, '邵阳市', 0);
INSERT INTO `china_area` VALUES (8472, '邵阳县', 430523, 430500, '邵阳市', 0);
INSERT INTO `china_area` VALUES (8473, '双清区', 430502, 430500, '邵阳市', 0);
INSERT INTO `china_area` VALUES (8474, '绥宁县', 430527, 430500, '邵阳市', 0);
INSERT INTO `china_area` VALUES (8475, '武冈市', 430581, 430500, '邵阳市', 0);
INSERT INTO `china_area` VALUES (8476, '新邵县', 430522, 430500, '邵阳市', 0);
INSERT INTO `china_area` VALUES (8477, '新宁县', 430528, 430500, '邵阳市', 0);
INSERT INTO `china_area` VALUES (8478, '湘潭市', 430300, 430000, '湖南省', 0);
INSERT INTO `china_area` VALUES (8479, '韶山市', 430382, 430300, '湘潭市', 0);
INSERT INTO `china_area` VALUES (8480, '湘潭县', 430321, 430300, '湘潭市', 0);
INSERT INTO `china_area` VALUES (8481, '湘乡市', 430381, 430300, '湘潭市', 0);
INSERT INTO `china_area` VALUES (8482, '雨湖区', 430302, 430300, '湘潭市', 0);
INSERT INTO `china_area` VALUES (8483, '岳塘区', 430304, 430300, '湘潭市', 0);
INSERT INTO `china_area` VALUES (8484, '湘西土家族苗族自治州', 433100, 430000, '湖南省', 0);
INSERT INTO `china_area` VALUES (8485, '保靖县', 433125, 433100, '湘西土家族苗族自治州', 0);
INSERT INTO `china_area` VALUES (8486, '凤凰县', 433123, 433100, '湘西土家族苗族自治州', 0);
INSERT INTO `china_area` VALUES (8487, '古丈县', 433126, 433100, '湘西土家族苗族自治州', 0);
INSERT INTO `china_area` VALUES (8488, '花垣县', 433124, 433100, '湘西土家族苗族自治州', 0);
INSERT INTO `china_area` VALUES (8489, '吉首市', 433101, 433100, '湘西土家族苗族自治州', 0);
INSERT INTO `china_area` VALUES (8490, '龙山县', 433130, 433100, '湘西土家族苗族自治州', 0);
INSERT INTO `china_area` VALUES (8491, '泸溪县', 433122, 433100, '湘西土家族苗族自治州', 0);
INSERT INTO `china_area` VALUES (8492, '永顺县', 433127, 433100, '湘西土家族苗族自治州', 0);
INSERT INTO `china_area` VALUES (8493, '益阳市', 430900, 430000, '湖南省', 0);
INSERT INTO `china_area` VALUES (8494, '安化县', 430923, 430900, '益阳市', 0);
INSERT INTO `china_area` VALUES (8495, '赫山区', 430903, 430900, '益阳市', 0);
INSERT INTO `china_area` VALUES (8496, '南县', 430921, 430900, '益阳市', 0);
INSERT INTO `china_area` VALUES (8497, '桃江县', 430922, 430900, '益阳市', 0);
INSERT INTO `china_area` VALUES (8498, '沅江市', 430981, 430900, '益阳市', 0);
INSERT INTO `china_area` VALUES (8499, '资阳区', 430902, 430900, '益阳市', 0);
INSERT INTO `china_area` VALUES (8500, '永州市', 431100, 430000, '湖南省', 0);
INSERT INTO `china_area` VALUES (8501, '道县', 431124, 431100, '永州市', 0);
INSERT INTO `china_area` VALUES (8502, '东安县', 431122, 431100, '永州市', 0);
INSERT INTO `china_area` VALUES (8503, '江华瑶族自治县', 431129, 431100, '永州市', 0);
INSERT INTO `china_area` VALUES (8504, '江永县', 431125, 431100, '永州市', 0);
INSERT INTO `china_area` VALUES (8505, '冷水滩区', 431103, 431100, '永州市', 0);
INSERT INTO `china_area` VALUES (8506, '蓝山县', 431127, 431100, '永州市', 0);
INSERT INTO `china_area` VALUES (8507, '零陵区', 431102, 431100, '永州市', 0);
INSERT INTO `china_area` VALUES (8508, '宁远县', 431126, 431100, '永州市', 0);
INSERT INTO `china_area` VALUES (8509, '祁阳县', 431121, 431100, '永州市', 0);
INSERT INTO `china_area` VALUES (8510, '双牌县', 431123, 431100, '永州市', 0);
INSERT INTO `china_area` VALUES (8511, '新田县', 431128, 431100, '永州市', 0);
INSERT INTO `china_area` VALUES (8512, '株洲市', 430200, 430000, '湖南省', 0);
INSERT INTO `china_area` VALUES (8513, '茶陵县', 430224, 430200, '株洲市', 0);
INSERT INTO `china_area` VALUES (8514, '荷塘区', 430202, 430200, '株洲市', 0);
INSERT INTO `china_area` VALUES (8515, '醴陵市', 430281, 430200, '株洲市', 0);
INSERT INTO `china_area` VALUES (8516, '芦淞区', 430203, 430200, '株洲市', 0);
INSERT INTO `china_area` VALUES (8517, '石峰区', 430204, 430200, '株洲市', 0);
INSERT INTO `china_area` VALUES (8518, '天元区', 430211, 430200, '株洲市', 0);
INSERT INTO `china_area` VALUES (8519, '炎陵县', 430225, 430200, '株洲市', 0);
INSERT INTO `china_area` VALUES (8520, '攸县', 430223, 430200, '株洲市', 0);
INSERT INTO `china_area` VALUES (8521, '株洲县', 430221, 430200, '株洲市', 0);
INSERT INTO `china_area` VALUES (8522, '张家界市', 430800, 430000, '湖南省', 0);
INSERT INTO `china_area` VALUES (8523, '慈利县', 430821, 430800, '张家界市', 0);
INSERT INTO `china_area` VALUES (8524, '桑植县', 430822, 430800, '张家界市', 0);
INSERT INTO `china_area` VALUES (8525, '武陵源区', 430811, 430800, '张家界市', 0);
INSERT INTO `china_area` VALUES (8526, '永定区', 430802, 430800, '张家界市', 0);
INSERT INTO `china_area` VALUES (8527, '岳阳市', 430600, 430000, '湖南省', 0);
INSERT INTO `china_area` VALUES (8528, '华容县', 430623, 430600, '岳阳市', 0);
INSERT INTO `china_area` VALUES (8529, '君山区', 430611, 430600, '岳阳市', 0);
INSERT INTO `china_area` VALUES (8530, '临湘市', 430682, 430600, '岳阳市', 0);
INSERT INTO `china_area` VALUES (8531, '汨罗市', 430681, 430600, '岳阳市', 0);
INSERT INTO `china_area` VALUES (8532, '平江县', 430626, 430600, '岳阳市', 0);
INSERT INTO `china_area` VALUES (8533, '湘阴县', 430624, 430600, '岳阳市', 0);
INSERT INTO `china_area` VALUES (8534, '云溪区', 430603, 430600, '岳阳市', 0);
INSERT INTO `china_area` VALUES (8535, '岳阳县', 430621, 430600, '岳阳市', 0);
INSERT INTO `china_area` VALUES (8536, '岳阳楼区', 430602, 430600, '岳阳市', 0);
INSERT INTO `china_area` VALUES (8537, '长沙市', 430100, 430000, '湖南省', 0);
INSERT INTO `china_area` VALUES (8538, '芙蓉区', 430102, 430100, '长沙市', 0);
INSERT INTO `china_area` VALUES (8539, '开福区', 430105, 430100, '长沙市', 0);
INSERT INTO `china_area` VALUES (8540, '浏阳市', 430181, 430100, '长沙市', 0);
INSERT INTO `china_area` VALUES (8541, '宁乡市', 430124, 430100, '长沙市', 0);
INSERT INTO `china_area` VALUES (8542, '天心区', 430103, 430100, '长沙市', 0);
INSERT INTO `china_area` VALUES (8543, '望城区', 430112, 430100, '长沙市', 0);
INSERT INTO `china_area` VALUES (8544, '雨花区', 430111, 430100, '长沙市', 0);
INSERT INTO `china_area` VALUES (8545, '岳麓区', 430104, 430100, '长沙市', 0);
INSERT INTO `china_area` VALUES (8546, '长沙县', 430121, 430100, '长沙市', 0);
INSERT INTO `china_area` VALUES (8547, '怀化市', 431200, 430000, '湖南省', 0);
INSERT INTO `china_area` VALUES (8548, '辰溪县', 431223, 431200, '怀化市', 0);
INSERT INTO `china_area` VALUES (8549, '鹤城区', 431202, 431200, '怀化市', 0);
INSERT INTO `china_area` VALUES (8550, '洪江市', 431281, 431200, '怀化市', 0);
INSERT INTO `china_area` VALUES (8551, '会同县', 431225, 431200, '怀化市', 0);
INSERT INTO `china_area` VALUES (8552, '靖州苗族侗族自治县', 431229, 431200, '怀化市', 0);
INSERT INTO `china_area` VALUES (8553, '麻阳苗族自治县', 431226, 431200, '怀化市', 0);
INSERT INTO `china_area` VALUES (8554, '通道侗族自治县', 431230, 431200, '怀化市', 0);
INSERT INTO `china_area` VALUES (8555, '新晃侗族自治县', 431227, 431200, '怀化市', 0);
INSERT INTO `china_area` VALUES (8556, '溆浦县', 431224, 431200, '怀化市', 0);
INSERT INTO `china_area` VALUES (8557, '沅陵县', 431222, 431200, '怀化市', 0);
INSERT INTO `china_area` VALUES (8558, '中方县', 431221, 431200, '怀化市', 0);
INSERT INTO `china_area` VALUES (8559, '芷江侗族自治县', 431228, 431200, '怀化市', 0);
INSERT INTO `china_area` VALUES (8560, '上海', 310000, 0, '中国', 0);
INSERT INTO `china_area` VALUES (8561, '上海城区', 310100, 310000, '上海市', 0);
INSERT INTO `china_area` VALUES (8562, '宝山区', 310113, 310100, '上海城区', 0);
INSERT INTO `china_area` VALUES (8563, '崇明区', 310151, 310100, '上海城区', 0);
INSERT INTO `china_area` VALUES (8564, '奉贤区', 310120, 310100, '上海城区', 0);
INSERT INTO `china_area` VALUES (8565, '虹口区', 310109, 310100, '上海城区', 0);
INSERT INTO `china_area` VALUES (8566, '黄浦区', 310101, 310100, '上海城区', 0);
INSERT INTO `china_area` VALUES (8567, '嘉定区', 310114, 310100, '上海城区', 0);
INSERT INTO `china_area` VALUES (8568, '金山区', 310116, 310100, '上海城区', 0);
INSERT INTO `china_area` VALUES (8569, '静安区', 310106, 310100, '上海城区', 0);
INSERT INTO `china_area` VALUES (8570, '闵行区', 310112, 310100, '上海城区', 0);
INSERT INTO `china_area` VALUES (8571, '青浦区', 310118, 310100, '上海城区', 0);
INSERT INTO `china_area` VALUES (8572, '浦东新区', 310115, 310100, '上海城区', 0);
INSERT INTO `china_area` VALUES (8573, '普陀区', 310107, 310100, '上海城区', 0);
INSERT INTO `china_area` VALUES (8574, '松江区', 310117, 310100, '上海城区', 0);
INSERT INTO `china_area` VALUES (8575, '徐汇区', 310104, 310100, '上海城区', 0);
INSERT INTO `china_area` VALUES (8576, '杨浦区', 310110, 310100, '上海城区', 0);
INSERT INTO `china_area` VALUES (8577, '长宁区', 310105, 310100, '上海城区', 0);
INSERT INTO `china_area` VALUES (8578, '湖北省', 420000, 0, '中国', 0);
INSERT INTO `china_area` VALUES (8579, '鄂州市', 420700, 420000, '湖北省', 0);
INSERT INTO `china_area` VALUES (8580, '鄂城区', 420704, 420700, '鄂州市', 0);
INSERT INTO `china_area` VALUES (8581, '华容区', 420703, 420700, '鄂州市', 0);
INSERT INTO `china_area` VALUES (8582, '梁子湖区', 420702, 420700, '鄂州市', 0);
INSERT INTO `china_area` VALUES (8583, '恩施土家族苗族自治州', 422800, 420000, '湖北省', 0);
INSERT INTO `china_area` VALUES (8584, '巴东县', 422823, 422800, '恩施土家族苗族自治州', 0);
INSERT INTO `china_area` VALUES (8585, '恩施市', 422801, 422800, '恩施土家族苗族自治州', 0);
INSERT INTO `china_area` VALUES (8586, '鹤峰县', 422828, 422800, '恩施土家族苗族自治州', 0);
INSERT INTO `china_area` VALUES (8587, '建始县', 422822, 422800, '恩施土家族苗族自治州', 0);
INSERT INTO `china_area` VALUES (8588, '来凤县', 422827, 422800, '恩施土家族苗族自治州', 0);
INSERT INTO `china_area` VALUES (8589, '利川市', 422802, 422800, '恩施土家族苗族自治州', 0);
INSERT INTO `china_area` VALUES (8590, '咸丰县', 422826, 422800, '恩施土家族苗族自治州', 0);
INSERT INTO `china_area` VALUES (8591, '宣恩县', 422825, 422800, '恩施土家族苗族自治州', 0);
INSERT INTO `china_area` VALUES (8592, '黄冈市', 421100, 420000, '湖北省', 0);
INSERT INTO `china_area` VALUES (8593, '红安县', 421122, 421100, '黄冈市', 0);
INSERT INTO `china_area` VALUES (8594, '黄梅县', 421127, 421100, '黄冈市', 0);
INSERT INTO `china_area` VALUES (8595, '黄州区', 421102, 421100, '黄冈市', 0);
INSERT INTO `china_area` VALUES (8596, '罗田县', 421123, 421100, '黄冈市', 0);
INSERT INTO `china_area` VALUES (8597, '麻城市', 421181, 421100, '黄冈市', 0);
INSERT INTO `china_area` VALUES (8598, '蕲春县', 421126, 421100, '黄冈市', 0);
INSERT INTO `china_area` VALUES (8599, '团风县', 421121, 421100, '黄冈市', 0);
INSERT INTO `china_area` VALUES (8600, '浠水县', 421125, 421100, '黄冈市', 0);
INSERT INTO `china_area` VALUES (8601, '武穴市', 421182, 421100, '黄冈市', 0);
INSERT INTO `china_area` VALUES (8602, '英山县', 421124, 421100, '黄冈市', 0);
INSERT INTO `china_area` VALUES (8603, '黄石市', 420200, 420000, '湖北省', 0);
INSERT INTO `china_area` VALUES (8604, '大冶市', 420281, 420200, '黄石市', 0);
INSERT INTO `china_area` VALUES (8605, '黄石港区', 420202, 420200, '黄石市', 0);
INSERT INTO `china_area` VALUES (8606, '铁山区', 420205, 420200, '黄石市', 0);
INSERT INTO `china_area` VALUES (8607, '下陆区', 420204, 420200, '黄石市', 0);
INSERT INTO `china_area` VALUES (8608, '西塞山区', 420203, 420200, '黄石市', 0);
INSERT INTO `china_area` VALUES (8609, '阳新县', 420222, 420200, '黄石市', 0);
INSERT INTO `china_area` VALUES (8610, '荆门市', 420800, 420000, '湖北省', 0);
INSERT INTO `china_area` VALUES (8611, '东宝区', 420802, 420800, '荆门市', 0);
INSERT INTO `china_area` VALUES (8612, '掇刀区', 420804, 420800, '荆门市', 0);
INSERT INTO `china_area` VALUES (8613, '京山县', 420821, 420800, '荆门市', 0);
INSERT INTO `china_area` VALUES (8614, '沙洋县', 420822, 420800, '荆门市', 0);
INSERT INTO `china_area` VALUES (8615, '钟祥市', 420881, 420800, '荆门市', 0);
INSERT INTO `china_area` VALUES (8616, '荆州市', 421000, 420000, '湖北省', 0);
INSERT INTO `china_area` VALUES (8617, '公安县', 421022, 421000, '荆州市', 0);
INSERT INTO `china_area` VALUES (8618, '洪湖市', 421083, 421000, '荆州市', 0);
INSERT INTO `china_area` VALUES (8619, '监利县', 421023, 421000, '荆州市', 0);
INSERT INTO `china_area` VALUES (8620, '江陵县', 421024, 421000, '荆州市', 0);
INSERT INTO `china_area` VALUES (8621, '荆州区', 421003, 421000, '荆州市', 0);
INSERT INTO `china_area` VALUES (8622, '沙市区', 421002, 421000, '荆州市', 0);
INSERT INTO `china_area` VALUES (8623, '石首市', 421081, 421000, '荆州市', 0);
INSERT INTO `china_area` VALUES (8624, '松滋市', 421087, 421000, '荆州市', 0);
INSERT INTO `china_area` VALUES (8625, '十堰市', 420300, 420000, '湖北省', 0);
INSERT INTO `china_area` VALUES (8626, '丹江口市', 420381, 420300, '十堰市', 0);
INSERT INTO `china_area` VALUES (8627, '房县', 420325, 420300, '十堰市', 0);
INSERT INTO `china_area` VALUES (8628, '茅箭区', 420302, 420300, '十堰市', 0);
INSERT INTO `china_area` VALUES (8629, '郧西县', 420322, 420300, '十堰市', 0);
INSERT INTO `china_area` VALUES (8630, '郧阳区', 420304, 420300, '十堰市', 0);
INSERT INTO `china_area` VALUES (8631, '张湾区', 420303, 420300, '十堰市', 0);
INSERT INTO `china_area` VALUES (8632, '竹溪县', 420324, 420300, '十堰市', 0);
INSERT INTO `china_area` VALUES (8633, '竹山县', 420323, 420300, '十堰市', 0);
INSERT INTO `china_area` VALUES (8634, '随州市', 421300, 420000, '湖北省', 0);
INSERT INTO `china_area` VALUES (8635, '曾都区', 421303, 421300, '随州市', 0);
INSERT INTO `china_area` VALUES (8636, '广水市', 421381, 421300, '随州市', 0);
INSERT INTO `china_area` VALUES (8637, '随县', 421321, 421300, '随州市', 0);
INSERT INTO `china_area` VALUES (8638, '襄阳市', 420600, 420000, '湖北省', 0);
INSERT INTO `china_area` VALUES (8639, '保康县', 420626, 420600, '襄阳市', 0);
INSERT INTO `china_area` VALUES (8640, '樊城区', 420606, 420600, '襄阳市', 0);
INSERT INTO `china_area` VALUES (8641, '谷城县', 420625, 420600, '襄阳市', 0);
INSERT INTO `china_area` VALUES (8642, '老河口市', 420682, 420600, '襄阳市', 0);
INSERT INTO `china_area` VALUES (8643, '南漳县', 420624, 420600, '襄阳市', 0);
INSERT INTO `china_area` VALUES (8644, '襄城区', 420602, 420600, '襄阳市', 0);
INSERT INTO `china_area` VALUES (8645, '襄州区', 420607, 420600, '襄阳市', 0);
INSERT INTO `china_area` VALUES (8646, '宜城市', 420684, 420600, '襄阳市', 0);
INSERT INTO `china_area` VALUES (8647, '枣阳市', 420683, 420600, '襄阳市', 0);
INSERT INTO `china_area` VALUES (8648, '孝感市', 420900, 420000, '湖北省', 0);
INSERT INTO `china_area` VALUES (8649, '安陆市', 420982, 420900, '孝感市', 0);
INSERT INTO `china_area` VALUES (8650, '大悟县', 420922, 420900, '孝感市', 0);
INSERT INTO `china_area` VALUES (8651, '汉川市', 420984, 420900, '孝感市', 0);
INSERT INTO `china_area` VALUES (8652, '孝昌县', 420921, 420900, '孝感市', 0);
INSERT INTO `china_area` VALUES (8653, '孝南区', 420902, 420900, '孝感市', 0);
INSERT INTO `china_area` VALUES (8654, '应城市', 420981, 420900, '孝感市', 0);
INSERT INTO `china_area` VALUES (8655, '云梦县', 420923, 420900, '孝感市', 0);
INSERT INTO `china_area` VALUES (8656, '武汉市', 420100, 420000, '湖北省', 0);
INSERT INTO `china_area` VALUES (8657, '蔡甸区', 420114, 420100, '武汉市', 0);
INSERT INTO `china_area` VALUES (8658, '东西湖区', 420112, 420100, '武汉市', 0);
INSERT INTO `china_area` VALUES (8659, '汉南区', 420113, 420100, '武汉市', 0);
INSERT INTO `china_area` VALUES (8660, '汉阳区', 420105, 420100, '武汉市', 0);
INSERT INTO `china_area` VALUES (8661, '洪山区', 420111, 420100, '武汉市', 0);
INSERT INTO `china_area` VALUES (8662, '黄陂区', 420116, 420100, '武汉市', 0);
INSERT INTO `china_area` VALUES (8663, '江夏区', 420115, 420100, '武汉市', 0);
INSERT INTO `china_area` VALUES (8664, '江汉区', 420103, 420100, '武汉市', 0);
INSERT INTO `china_area` VALUES (8665, '江岸区', 420102, 420100, '武汉市', 0);
INSERT INTO `china_area` VALUES (8666, '硚口区', 420104, 420100, '武汉市', 0);
INSERT INTO `china_area` VALUES (8667, '青山区', 420107, 420100, '武汉市', 0);
INSERT INTO `china_area` VALUES (8668, '武昌区', 420106, 420100, '武汉市', 0);
INSERT INTO `china_area` VALUES (8669, '新洲区', 420117, 420100, '武汉市', 0);
INSERT INTO `china_area` VALUES (8670, '咸宁市', 421200, 420000, '湖北省', 0);
INSERT INTO `china_area` VALUES (8671, '赤壁市', 421281, 421200, '咸宁市', 0);
INSERT INTO `china_area` VALUES (8672, '崇阳县', 421223, 421200, '咸宁市', 0);
INSERT INTO `china_area` VALUES (8673, '嘉鱼县', 421221, 421200, '咸宁市', 0);
INSERT INTO `china_area` VALUES (8674, '通城县', 421222, 421200, '咸宁市', 0);
INSERT INTO `china_area` VALUES (8675, '通山县', 421224, 421200, '咸宁市', 0);
INSERT INTO `china_area` VALUES (8676, '咸安区', 421202, 421200, '咸宁市', 0);
INSERT INTO `china_area` VALUES (8677, '宜昌市', 420500, 420000, '湖北省', 0);
INSERT INTO `china_area` VALUES (8678, '点军区', 420504, 420500, '宜昌市', 0);
INSERT INTO `china_area` VALUES (8679, '当阳市', 420582, 420500, '宜昌市', 0);
INSERT INTO `china_area` VALUES (8680, '伍家岗区', 420503, 420500, '宜昌市', 0);
INSERT INTO `china_area` VALUES (8681, '五峰土家族自治县', 420529, 420500, '宜昌市', 0);
INSERT INTO `china_area` VALUES (8682, '西陵区', 420502, 420500, '宜昌市', 0);
INSERT INTO `china_area` VALUES (8683, '猇亭区', 420505, 420500, '宜昌市', 0);
INSERT INTO `china_area` VALUES (8684, '兴山县', 420526, 420500, '宜昌市', 0);
INSERT INTO `china_area` VALUES (8685, '夷陵区', 420506, 420500, '宜昌市', 0);
INSERT INTO `china_area` VALUES (8686, '宜都市', 420581, 420500, '宜昌市', 0);
INSERT INTO `china_area` VALUES (8687, '远安县', 420525, 420500, '宜昌市', 0);
INSERT INTO `china_area` VALUES (8688, '长阳土家族自治县', 420528, 420500, '宜昌市', 0);
INSERT INTO `china_area` VALUES (8689, '枝江市', 420583, 420500, '宜昌市', 0);
INSERT INTO `china_area` VALUES (8690, '秭归县', 420527, 420500, '宜昌市', 0);
INSERT INTO `china_area` VALUES (8691, '潜江市', 429005, 420000, '湖北省', 0);
INSERT INTO `china_area` VALUES (8692, '积玉口镇', 429006, 429005, '潜江市', 0);
INSERT INTO `china_area` VALUES (8693, '浩口镇', 429007, 429005, '潜江市', 0);
INSERT INTO `china_area` VALUES (8694, '张金镇', 429008, 429005, '潜江市', 0);
INSERT INTO `china_area` VALUES (8695, '龙湾镇', 429009, 429005, '潜江市', 0);
INSERT INTO `china_area` VALUES (8696, '杨市街道', 429010, 429005, '潜江市', 0);
INSERT INTO `china_area` VALUES (8697, '广华街道', 429011, 429005, '潜江市', 0);
INSERT INTO `china_area` VALUES (8698, '周矶街道', 429012, 429005, '潜江市', 0);
INSERT INTO `china_area` VALUES (8699, '园林街道', 429013, 429005, '潜江市', 0);
INSERT INTO `china_area` VALUES (8700, '泽口街道', 429014, 429005, '潜江市', 0);
INSERT INTO `china_area` VALUES (8701, '渔洋镇', 429015, 429005, '潜江市', 0);
INSERT INTO `china_area` VALUES (8702, '兴隆镇', 429016, 429005, '潜江市', 0);
INSERT INTO `china_area` VALUES (8703, '老新镇', 429017, 429005, '潜江市', 0);
INSERT INTO `china_area` VALUES (8704, '竹根滩镇', 429018, 429005, '潜江市', 0);
INSERT INTO `china_area` VALUES (8705, '高石碑镇', 429019, 429005, '潜江市', 0);
INSERT INTO `china_area` VALUES (8706, '熊口镇', 429020, 429005, '潜江市', 0);
INSERT INTO `china_area` VALUES (8707, '王场镇', 429021, 429005, '潜江市', 0);
INSERT INTO `china_area` VALUES (8708, '神农架林区', 429021, 420000, '湖北省', 0);
INSERT INTO `china_area` VALUES (8709, '新华镇', 429022, 429021, '神农架林区', 0);
INSERT INTO `china_area` VALUES (8710, '宋洛乡', 429023, 429021, '神农架林区', 0);
INSERT INTO `china_area` VALUES (8711, '阳日镇', 429024, 429021, '神农架林区', 0);
INSERT INTO `china_area` VALUES (8712, '九湖乡', 429025, 429021, '神农架林区', 0);
INSERT INTO `china_area` VALUES (8713, '下谷坪土家族乡', 429026, 429021, '神农架林区', 0);
INSERT INTO `china_area` VALUES (8714, '木鱼镇', 429027, 429021, '神农架林区', 0);
INSERT INTO `china_area` VALUES (8715, '红坪镇', 429028, 429021, '神农架林区', 0);
INSERT INTO `china_area` VALUES (8716, '松柏镇', 429029, 429021, '神农架林区', 0);
INSERT INTO `china_area` VALUES (8717, '天门市', 429006, 420000, '湖北省', 0);
INSERT INTO `china_area` VALUES (8718, '净潭乡', 429007, 429006, '天门市', 0);
INSERT INTO `china_area` VALUES (8719, '麻洋镇', 429008, 429006, '天门市', 0);
INSERT INTO `china_area` VALUES (8720, '多祥镇', 429009, 429006, '天门市', 0);
INSERT INTO `china_area` VALUES (8721, '拖市镇', 429010, 429006, '天门市', 0);
INSERT INTO `china_area` VALUES (8722, '汪场镇', 429011, 429006, '天门市', 0);
INSERT INTO `china_area` VALUES (8723, '蒋场镇', 429012, 429006, '天门市', 0);
INSERT INTO `china_area` VALUES (8724, '马湾镇', 429013, 429006, '天门市', 0);
INSERT INTO `china_area` VALUES (8725, '横林镇', 429014, 429006, '天门市', 0);
INSERT INTO `china_area` VALUES (8726, '九真镇', 429015, 429006, '天门市', 0);
INSERT INTO `china_area` VALUES (8727, '石河镇', 429016, 429006, '天门市', 0);
INSERT INTO `china_area` VALUES (8728, '张港镇', 429017, 429006, '天门市', 0);
INSERT INTO `china_area` VALUES (8729, '小板镇', 429018, 429006, '天门市', 0);
INSERT INTO `china_area` VALUES (8730, '彭市镇', 429019, 429006, '天门市', 0);
INSERT INTO `china_area` VALUES (8731, '岳口街道', 429020, 429006, '天门市', 0);
INSERT INTO `china_area` VALUES (8732, '候口街道', 429021, 429006, '天门市', 0);
INSERT INTO `china_area` VALUES (8733, '杨林街道', 429022, 429006, '天门市', 0);
INSERT INTO `china_area` VALUES (8734, '竟陵街道', 429023, 429006, '天门市', 0);
INSERT INTO `china_area` VALUES (8735, '皂市镇', 429024, 429006, '天门市', 0);
INSERT INTO `china_area` VALUES (8736, '岳口镇', 429025, 429006, '天门市', 0);
INSERT INTO `china_area` VALUES (8737, '渔薪镇', 429026, 429006, '天门市', 0);
INSERT INTO `china_area` VALUES (8738, '卢市镇', 429027, 429006, '天门市', 0);
INSERT INTO `china_area` VALUES (8739, '多宝镇', 429028, 429006, '天门市', 0);
INSERT INTO `china_area` VALUES (8740, '胡市镇', 429029, 429006, '天门市', 0);
INSERT INTO `china_area` VALUES (8741, '佛子山镇', 429030, 429006, '天门市', 0);
INSERT INTO `china_area` VALUES (8742, '黄潭镇', 429031, 429006, '天门市', 0);
INSERT INTO `china_area` VALUES (8743, '干驿镇', 429032, 429006, '天门市', 0);
INSERT INTO `china_area` VALUES (8744, '仙桃市', 429004, 420000, '湖北省', 0);
INSERT INTO `china_area` VALUES (8745, '郑场镇', 429005, 429004, '仙桃市', 0);
INSERT INTO `china_area` VALUES (8746, '西流河镇', 429006, 429004, '仙桃市', 0);
INSERT INTO `china_area` VALUES (8747, '郭河镇', 429007, 429004, '仙桃市', 0);
INSERT INTO `china_area` VALUES (8748, '干河街道', 429008, 429004, '仙桃市', 0);
INSERT INTO `china_area` VALUES (8749, '毛嘴镇', 429009, 429004, '仙桃市', 0);
INSERT INTO `china_area` VALUES (8750, '龙华山街道', 429010, 429004, '仙桃市', 0);
INSERT INTO `china_area` VALUES (8751, '沙咀街道', 429011, 429004, '仙桃市', 0);
INSERT INTO `china_area` VALUES (8752, '袁市街道', 429012, 429004, '仙桃市', 0);
INSERT INTO `china_area` VALUES (8753, '沙嘴街道', 429013, 429004, '仙桃市', 0);
INSERT INTO `china_area` VALUES (8754, '剅河镇', 429014, 429004, '仙桃市', 0);
INSERT INTO `china_area` VALUES (8755, '三伏潭镇', 429015, 429004, '仙桃市', 0);
INSERT INTO `china_area` VALUES (8756, '胡场镇', 429016, 429004, '仙桃市', 0);
INSERT INTO `china_area` VALUES (8757, '长埫口镇', 429017, 429004, '仙桃市', 0);
INSERT INTO `china_area` VALUES (8758, '张沟镇', 429018, 429004, '仙桃市', 0);
INSERT INTO `china_area` VALUES (8759, '陈场镇', 429019, 429004, '仙桃市', 0);
INSERT INTO `china_area` VALUES (8760, '通海口镇', 429020, 429004, '仙桃市', 0);
INSERT INTO `china_area` VALUES (8761, '彭场镇', 429021, 429004, '仙桃市', 0);
INSERT INTO `china_area` VALUES (8762, '杨林尾镇', 429022, 429004, '仙桃市', 0);
INSERT INTO `china_area` VALUES (8763, '沙湖镇', 429023, 429004, '仙桃市', 0);
INSERT INTO `china_area` VALUES (8764, '沔城回族镇', 429024, 429004, '仙桃市', 0);
INSERT INTO `china_area` VALUES (8765, '黑龙江省', 230000, 0, '中国', 0);
INSERT INTO `china_area` VALUES (8766, '大庆市', 230600, 230000, '黑龙江省', 0);
INSERT INTO `china_area` VALUES (8767, '大同区', 230606, 230600, '大庆市', 0);
INSERT INTO `china_area` VALUES (8768, '杜尔伯特蒙古族自治县', 230624, 230600, '大庆市', 0);
INSERT INTO `china_area` VALUES (8769, '红岗区', 230605, 230600, '大庆市', 0);
INSERT INTO `china_area` VALUES (8770, '林甸县', 230623, 230600, '大庆市', 0);
INSERT INTO `china_area` VALUES (8771, '龙凤区', 230603, 230600, '大庆市', 0);
INSERT INTO `china_area` VALUES (8772, '萨尔图区', 230602, 230600, '大庆市', 0);
INSERT INTO `china_area` VALUES (8773, '让胡路区', 230604, 230600, '大庆市', 0);
INSERT INTO `china_area` VALUES (8774, '肇州县', 230621, 230600, '大庆市', 0);
INSERT INTO `china_area` VALUES (8775, '肇源县', 230622, 230600, '大庆市', 0);
INSERT INTO `china_area` VALUES (8776, '大兴安岭地区', 232700, 230000, '黑龙江省', 0);
INSERT INTO `china_area` VALUES (8777, '呼玛县', 232721, 232700, '大兴安岭地区', 0);
INSERT INTO `china_area` VALUES (8778, '加格达奇区', 232701, 232700, '大兴安岭地区', 0);
INSERT INTO `china_area` VALUES (8779, '漠河县', 232723, 232700, '大兴安岭地区', 0);
INSERT INTO `china_area` VALUES (8780, '塔河县', 232722, 232700, '大兴安岭地区', 0);
INSERT INTO `china_area` VALUES (8781, '哈尔滨市', 230100, 230000, '黑龙江省', 0);
INSERT INTO `china_area` VALUES (8782, '阿城区', 230112, 230100, '哈尔滨市', 0);
INSERT INTO `china_area` VALUES (8783, '巴彦县', 230126, 230100, '哈尔滨市', 0);
INSERT INTO `china_area` VALUES (8784, '宾县', 230125, 230100, '哈尔滨市', 0);
INSERT INTO `china_area` VALUES (8785, '道里区', 230102, 230100, '哈尔滨市', 0);
INSERT INTO `china_area` VALUES (8786, '道外区', 230104, 230100, '哈尔滨市', 0);
INSERT INTO `china_area` VALUES (8787, '方正县', 230124, 230100, '哈尔滨市', 0);
INSERT INTO `china_area` VALUES (8788, '呼兰区', 230111, 230100, '哈尔滨市', 0);
INSERT INTO `china_area` VALUES (8789, '木兰县', 230127, 230100, '哈尔滨市', 0);
INSERT INTO `china_area` VALUES (8790, '南岗区', 230103, 230100, '哈尔滨市', 0);
INSERT INTO `china_area` VALUES (8791, '平房区', 230108, 230100, '哈尔滨市', 0);
INSERT INTO `china_area` VALUES (8792, '尚志市', 230183, 230100, '哈尔滨市', 0);
INSERT INTO `china_area` VALUES (8793, '双城区', 230113, 230100, '哈尔滨市', 0);
INSERT INTO `china_area` VALUES (8794, '松北区', 230109, 230100, '哈尔滨市', 0);
INSERT INTO `china_area` VALUES (8795, '通河县', 230128, 230100, '哈尔滨市', 0);
INSERT INTO `china_area` VALUES (8796, '五常市', 230184, 230100, '哈尔滨市', 0);
INSERT INTO `china_area` VALUES (8797, '香坊区', 230110, 230100, '哈尔滨市', 0);
INSERT INTO `china_area` VALUES (8798, '延寿县', 230129, 230100, '哈尔滨市', 0);
INSERT INTO `china_area` VALUES (8799, '依兰县', 230123, 230100, '哈尔滨市', 0);
INSERT INTO `china_area` VALUES (8800, '鹤岗市', 230400, 230000, '黑龙江省', 0);
INSERT INTO `china_area` VALUES (8801, '东山区', 230406, 230400, '鹤岗市', 0);
INSERT INTO `china_area` VALUES (8802, '工农区', 230403, 230400, '鹤岗市', 0);
INSERT INTO `china_area` VALUES (8803, '萝北县', 230421, 230400, '鹤岗市', 0);
INSERT INTO `china_area` VALUES (8804, '南山区', 230404, 230400, '鹤岗市', 0);
INSERT INTO `china_area` VALUES (8805, '绥滨县', 230422, 230400, '鹤岗市', 0);
INSERT INTO `china_area` VALUES (8806, '向阳区', 230402, 230400, '鹤岗市', 0);
INSERT INTO `china_area` VALUES (8807, '兴安区', 230405, 230400, '鹤岗市', 0);
INSERT INTO `china_area` VALUES (8808, '兴山区', 230407, 230400, '鹤岗市', 0);
INSERT INTO `china_area` VALUES (8809, '鸡西市', 230300, 230000, '黑龙江省', 0);
INSERT INTO `china_area` VALUES (8810, '城子河区', 230306, 230300, '鸡西市', 0);
INSERT INTO `china_area` VALUES (8811, '滴道区', 230304, 230300, '鸡西市', 0);
INSERT INTO `china_area` VALUES (8812, '恒山区', 230303, 230300, '鸡西市', 0);
INSERT INTO `china_area` VALUES (8813, '虎林市', 230381, 230300, '鸡西市', 0);
INSERT INTO `china_area` VALUES (8814, '鸡冠区', 230302, 230300, '鸡西市', 0);
INSERT INTO `china_area` VALUES (8815, '鸡东县', 230321, 230300, '鸡西市', 0);
INSERT INTO `china_area` VALUES (8816, '梨树区', 230305, 230300, '鸡西市', 0);
INSERT INTO `china_area` VALUES (8817, '麻山区', 230307, 230300, '鸡西市', 0);
INSERT INTO `china_area` VALUES (8818, '密山市', 230382, 230300, '鸡西市', 0);
INSERT INTO `china_area` VALUES (8819, '黑河市', 231100, 230000, '黑龙江省', 0);
INSERT INTO `china_area` VALUES (8820, '爱辉区', 231102, 231100, '黑河市', 0);
INSERT INTO `china_area` VALUES (8821, '北安市', 231181, 231100, '黑河市', 0);
INSERT INTO `china_area` VALUES (8822, '嫩江县', 231121, 231100, '黑河市', 0);
INSERT INTO `china_area` VALUES (8823, '孙吴县', 231124, 231100, '黑河市', 0);
INSERT INTO `china_area` VALUES (8824, '五大连池市', 231182, 231100, '黑河市', 0);
INSERT INTO `china_area` VALUES (8825, '逊克县', 231123, 231100, '黑河市', 0);
INSERT INTO `china_area` VALUES (8826, '佳木斯市', 230800, 230000, '黑龙江省', 0);
INSERT INTO `china_area` VALUES (8827, '东风区', 230805, 230800, '佳木斯市', 0);
INSERT INTO `china_area` VALUES (8828, '富锦市', 230882, 230800, '佳木斯市', 0);
INSERT INTO `china_area` VALUES (8829, '抚远市', 230883, 230800, '佳木斯市', 0);
INSERT INTO `china_area` VALUES (8830, '桦川县', 230826, 230800, '佳木斯市', 0);
INSERT INTO `china_area` VALUES (8831, '桦南县', 230822, 230800, '佳木斯市', 0);
INSERT INTO `china_area` VALUES (8832, '郊区', 230811, 230800, '佳木斯市', 0);
INSERT INTO `china_area` VALUES (8833, '前进区', 230804, 230800, '佳木斯市', 0);
INSERT INTO `china_area` VALUES (8834, '汤原县', 230828, 230800, '佳木斯市', 0);
INSERT INTO `china_area` VALUES (8835, '同江市', 230881, 230800, '佳木斯市', 0);
INSERT INTO `china_area` VALUES (8836, '向阳区', 230803, 230800, '佳木斯市', 0);
INSERT INTO `china_area` VALUES (8837, '牡丹江市', 231000, 230000, '黑龙江省', 0);
INSERT INTO `china_area` VALUES (8838, '爱民区', 231004, 231000, '牡丹江市', 0);
INSERT INTO `china_area` VALUES (8839, '东宁市', 231086, 231000, '牡丹江市', 0);
INSERT INTO `china_area` VALUES (8840, '东安区', 231002, 231000, '牡丹江市', 0);
INSERT INTO `china_area` VALUES (8841, '海林市', 231083, 231000, '牡丹江市', 0);
INSERT INTO `china_area` VALUES (8842, '林口县', 231025, 231000, '牡丹江市', 0);
INSERT INTO `china_area` VALUES (8843, '穆棱市', 231085, 231000, '牡丹江市', 0);
INSERT INTO `china_area` VALUES (8844, '宁安市', 231084, 231000, '牡丹江市', 0);
INSERT INTO `china_area` VALUES (8845, '绥芬河市', 231081, 231000, '牡丹江市', 0);
INSERT INTO `china_area` VALUES (8846, '西安区', 231005, 231000, '牡丹江市', 0);
INSERT INTO `china_area` VALUES (8847, '阳明区', 231003, 231000, '牡丹江市', 0);
INSERT INTO `china_area` VALUES (8848, '七台河市', 230900, 230000, '黑龙江省', 0);
INSERT INTO `china_area` VALUES (8849, '勃利县', 230921, 230900, '七台河市', 0);
INSERT INTO `china_area` VALUES (8850, '茄子河区', 230904, 230900, '七台河市', 0);
INSERT INTO `china_area` VALUES (8851, '桃山区', 230903, 230900, '七台河市', 0);
INSERT INTO `china_area` VALUES (8852, '新兴区', 230902, 230900, '七台河市', 0);
INSERT INTO `china_area` VALUES (8853, '齐齐哈尔市', 230200, 230000, '黑龙江省', 0);
INSERT INTO `china_area` VALUES (8854, '昂昂溪区', 230205, 230200, '齐齐哈尔市', 0);
INSERT INTO `china_area` VALUES (8855, '拜泉县', 230231, 230200, '齐齐哈尔市', 0);
INSERT INTO `china_area` VALUES (8856, '富拉尔基区', 230206, 230200, '齐齐哈尔市', 0);
INSERT INTO `china_area` VALUES (8857, '富裕县', 230227, 230200, '齐齐哈尔市', 0);
INSERT INTO `china_area` VALUES (8858, '甘南县', 230225, 230200, '齐齐哈尔市', 0);
INSERT INTO `china_area` VALUES (8859, '建华区', 230203, 230200, '齐齐哈尔市', 0);
INSERT INTO `china_area` VALUES (8860, '克山县', 230229, 230200, '齐齐哈尔市', 0);
INSERT INTO `china_area` VALUES (8861, '克东县', 230230, 230200, '齐齐哈尔市', 0);
INSERT INTO `china_area` VALUES (8862, '龙沙区', 230202, 230200, '齐齐哈尔市', 0);
INSERT INTO `china_area` VALUES (8863, '龙江县', 230221, 230200, '齐齐哈尔市', 0);
INSERT INTO `china_area` VALUES (8864, '梅里斯达斡尔族区', 230208, 230200, '齐齐哈尔市', 0);
INSERT INTO `china_area` VALUES (8865, '讷河市', 230281, 230200, '齐齐哈尔市', 0);
INSERT INTO `china_area` VALUES (8866, '碾子山区', 230207, 230200, '齐齐哈尔市', 0);
INSERT INTO `china_area` VALUES (8867, '泰来县', 230224, 230200, '齐齐哈尔市', 0);
INSERT INTO `china_area` VALUES (8868, '铁锋区', 230204, 230200, '齐齐哈尔市', 0);
INSERT INTO `china_area` VALUES (8869, '依安县', 230223, 230200, '齐齐哈尔市', 0);
INSERT INTO `china_area` VALUES (8870, '绥化市', 231200, 230000, '黑龙江省', 0);
INSERT INTO `china_area` VALUES (8871, '安达市', 231281, 231200, '绥化市', 0);
INSERT INTO `china_area` VALUES (8872, '北林区', 231202, 231200, '绥化市', 0);
INSERT INTO `china_area` VALUES (8873, '海伦市', 231283, 231200, '绥化市', 0);
INSERT INTO `china_area` VALUES (8874, '兰西县', 231222, 231200, '绥化市', 0);
INSERT INTO `china_area` VALUES (8875, '明水县', 231225, 231200, '绥化市', 0);
INSERT INTO `china_area` VALUES (8876, '青冈县', 231223, 231200, '绥化市', 0);
INSERT INTO `china_area` VALUES (8877, '庆安县', 231224, 231200, '绥化市', 0);
INSERT INTO `china_area` VALUES (8878, '绥棱县', 231226, 231200, '绥化市', 0);
INSERT INTO `china_area` VALUES (8879, '望奎县', 231221, 231200, '绥化市', 0);
INSERT INTO `china_area` VALUES (8880, '肇东市', 231282, 231200, '绥化市', 0);
INSERT INTO `china_area` VALUES (8881, '双鸭山市', 230500, 230000, '黑龙江省', 0);
INSERT INTO `china_area` VALUES (8882, '宝山区', 230506, 230500, '双鸭山市', 0);
INSERT INTO `china_area` VALUES (8883, '宝清县', 230523, 230500, '双鸭山市', 0);
INSERT INTO `china_area` VALUES (8884, '集贤县', 230521, 230500, '双鸭山市', 0);
INSERT INTO `china_area` VALUES (8885, '尖山区', 230502, 230500, '双鸭山市', 0);
INSERT INTO `china_area` VALUES (8886, '岭东区', 230503, 230500, '双鸭山市', 0);
INSERT INTO `china_area` VALUES (8887, '饶河县', 230524, 230500, '双鸭山市', 0);
INSERT INTO `china_area` VALUES (8888, '四方台区', 230505, 230500, '双鸭山市', 0);
INSERT INTO `china_area` VALUES (8889, '友谊县', 230522, 230500, '双鸭山市', 0);
INSERT INTO `china_area` VALUES (8890, '伊春市', 230700, 230000, '黑龙江省', 0);
INSERT INTO `china_area` VALUES (8891, '翠峦区', 230706, 230700, '伊春市', 0);
INSERT INTO `china_area` VALUES (8892, '带岭区', 230713, 230700, '伊春市', 0);
INSERT INTO `china_area` VALUES (8893, '红星区', 230715, 230700, '伊春市', 0);
INSERT INTO `china_area` VALUES (8894, '嘉荫县', 230722, 230700, '伊春市', 0);
INSERT INTO `china_area` VALUES (8895, '金山屯区', 230709, 230700, '伊春市', 0);
INSERT INTO `china_area` VALUES (8896, '美溪区', 230708, 230700, '伊春市', 0);
INSERT INTO `china_area` VALUES (8897, '南岔区', 230703, 230700, '伊春市', 0);
INSERT INTO `china_area` VALUES (8898, '上甘岭区', 230716, 230700, '伊春市', 0);
INSERT INTO `china_area` VALUES (8899, '汤旺河区', 230712, 230700, '伊春市', 0);
INSERT INTO `china_area` VALUES (8900, '铁力市', 230781, 230700, '伊春市', 0);
INSERT INTO `china_area` VALUES (8901, '乌马河区', 230711, 230700, '伊春市', 0);
INSERT INTO `china_area` VALUES (8902, '乌伊岭区', 230714, 230700, '伊春市', 0);
INSERT INTO `china_area` VALUES (8903, '五营区', 230710, 230700, '伊春市', 0);
INSERT INTO `china_area` VALUES (8904, '西林区', 230705, 230700, '伊春市', 0);
INSERT INTO `china_area` VALUES (8905, '新青区', 230707, 230700, '伊春市', 0);
INSERT INTO `china_area` VALUES (8906, '伊春区', 230702, 230700, '伊春市', 0);
INSERT INTO `china_area` VALUES (8907, '友好区', 230704, 230700, '伊春市', 0);
INSERT INTO `china_area` VALUES (8908, '内蒙古自治区', 150000, 0, '中国', 0);
INSERT INTO `china_area` VALUES (8909, '阿拉善盟', 152900, 150000, '内蒙古自治区', 0);
INSERT INTO `china_area` VALUES (8910, '阿拉善右旗', 152922, 152900, '阿拉善盟', 0);
INSERT INTO `china_area` VALUES (8911, '阿拉善左旗', 152921, 152900, '阿拉善盟', 0);
INSERT INTO `china_area` VALUES (8912, '额济纳旗', 152923, 152900, '阿拉善盟', 0);
INSERT INTO `china_area` VALUES (8913, '巴彦淖尔市', 150800, 150000, '内蒙古自治区', 0);
INSERT INTO `china_area` VALUES (8914, '磴口县', 150822, 150800, '巴彦淖尔市', 0);
INSERT INTO `china_area` VALUES (8915, '杭锦后旗', 150826, 150800, '巴彦淖尔市', 0);
INSERT INTO `china_area` VALUES (8916, '临河区', 150802, 150800, '巴彦淖尔市', 0);
INSERT INTO `china_area` VALUES (8917, '乌拉特前旗', 150823, 150800, '巴彦淖尔市', 0);
INSERT INTO `china_area` VALUES (8918, '乌拉特中旗', 150824, 150800, '巴彦淖尔市', 0);
INSERT INTO `china_area` VALUES (8919, '乌拉特后旗', 150825, 150800, '巴彦淖尔市', 0);
INSERT INTO `china_area` VALUES (8920, '五原县', 150821, 150800, '巴彦淖尔市', 0);
INSERT INTO `china_area` VALUES (8921, '包头市', 150200, 150000, '内蒙古自治区', 0);
INSERT INTO `china_area` VALUES (8922, '白云鄂博矿区', 150206, 150200, '包头市', 0);
INSERT INTO `china_area` VALUES (8923, '达尔罕茂明安联合旗', 150223, 150200, '包头市', 0);
INSERT INTO `china_area` VALUES (8924, '东河区', 150202, 150200, '包头市', 0);
INSERT INTO `china_area` VALUES (8925, '固阳县', 150222, 150200, '包头市', 0);
INSERT INTO `china_area` VALUES (8926, '九原区', 150207, 150200, '包头市', 0);
INSERT INTO `china_area` VALUES (8927, '昆都仑区', 150203, 150200, '包头市', 0);
INSERT INTO `china_area` VALUES (8928, '青山区', 150204, 150200, '包头市', 0);
INSERT INTO `china_area` VALUES (8929, '石拐区', 150205, 150200, '包头市', 0);
INSERT INTO `china_area` VALUES (8930, '土默特右旗', 150221, 150200, '包头市', 0);
INSERT INTO `china_area` VALUES (8931, '赤峰市', 150400, 150000, '内蒙古自治区', 0);
INSERT INTO `china_area` VALUES (8932, '阿鲁科尔沁旗', 150421, 150400, '赤峰市', 0);
INSERT INTO `china_area` VALUES (8933, '敖汉旗', 150430, 150400, '赤峰市', 0);
INSERT INTO `china_area` VALUES (8934, '巴林右旗', 150423, 150400, '赤峰市', 0);
INSERT INTO `china_area` VALUES (8935, '巴林左旗', 150422, 150400, '赤峰市', 0);
INSERT INTO `china_area` VALUES (8936, '红山区', 150402, 150400, '赤峰市', 0);
INSERT INTO `china_area` VALUES (8937, '喀喇沁旗', 150428, 150400, '赤峰市', 0);
INSERT INTO `china_area` VALUES (8938, '克什克腾旗', 150425, 150400, '赤峰市', 0);
INSERT INTO `china_area` VALUES (8939, '林西县', 150424, 150400, '赤峰市', 0);
INSERT INTO `china_area` VALUES (8940, '宁城县', 150429, 150400, '赤峰市', 0);
INSERT INTO `china_area` VALUES (8941, '松山区', 150404, 150400, '赤峰市', 0);
INSERT INTO `china_area` VALUES (8942, '翁牛特旗', 150426, 150400, '赤峰市', 0);
INSERT INTO `china_area` VALUES (8943, '元宝山区', 150403, 150400, '赤峰市', 0);
INSERT INTO `china_area` VALUES (8944, '鄂尔多斯市', 150600, 150000, '内蒙古自治区', 0);
INSERT INTO `china_area` VALUES (8945, '达拉特旗', 150621, 150600, '鄂尔多斯市', 0);
INSERT INTO `china_area` VALUES (8946, '东胜区', 150602, 150600, '鄂尔多斯市', 0);
INSERT INTO `china_area` VALUES (8947, '鄂托克旗', 150624, 150600, '鄂尔多斯市', 0);
INSERT INTO `china_area` VALUES (8948, '鄂托克前旗', 150623, 150600, '鄂尔多斯市', 0);
INSERT INTO `china_area` VALUES (8949, '杭锦旗', 150625, 150600, '鄂尔多斯市', 0);
INSERT INTO `china_area` VALUES (8950, '康巴什区', 150603, 150600, '鄂尔多斯市', 0);
INSERT INTO `china_area` VALUES (8951, '乌审旗', 150626, 150600, '鄂尔多斯市', 0);
INSERT INTO `china_area` VALUES (8952, '伊金霍洛旗', 150627, 150600, '鄂尔多斯市', 0);
INSERT INTO `china_area` VALUES (8953, '准格尔旗', 150622, 150600, '鄂尔多斯市', 0);
INSERT INTO `china_area` VALUES (8954, '呼和浩特市', 150100, 150000, '内蒙古自治区', 0);
INSERT INTO `china_area` VALUES (8955, '和林格尔县', 150123, 150100, '呼和浩特市', 0);
INSERT INTO `china_area` VALUES (8956, '回民区', 150103, 150100, '呼和浩特市', 0);
INSERT INTO `china_area` VALUES (8957, '清水河县', 150124, 150100, '呼和浩特市', 0);
INSERT INTO `china_area` VALUES (8958, '赛罕区', 150105, 150100, '呼和浩特市', 0);
INSERT INTO `china_area` VALUES (8959, '托克托县', 150122, 150100, '呼和浩特市', 0);
INSERT INTO `china_area` VALUES (8960, '土默特左旗', 150121, 150100, '呼和浩特市', 0);
INSERT INTO `china_area` VALUES (8961, '武川县', 150125, 150100, '呼和浩特市', 0);
INSERT INTO `china_area` VALUES (8962, '新城区', 150102, 150100, '呼和浩特市', 0);
INSERT INTO `china_area` VALUES (8963, '玉泉区', 150104, 150100, '呼和浩特市', 0);
INSERT INTO `china_area` VALUES (8964, '呼伦贝尔市', 150700, 150000, '内蒙古自治区', 0);
INSERT INTO `china_area` VALUES (8965, '阿荣旗', 150721, 150700, '呼伦贝尔市', 0);
INSERT INTO `china_area` VALUES (8966, '陈巴尔虎旗', 150725, 150700, '呼伦贝尔市', 0);
INSERT INTO `china_area` VALUES (8967, '额尔古纳市', 150784, 150700, '呼伦贝尔市', 0);
INSERT INTO `china_area` VALUES (8968, '鄂伦春自治旗', 150723, 150700, '呼伦贝尔市', 0);
INSERT INTO `china_area` VALUES (8969, '鄂温克族自治旗', 150724, 150700, '呼伦贝尔市', 0);
INSERT INTO `china_area` VALUES (8970, '根河市', 150785, 150700, '呼伦贝尔市', 0);
INSERT INTO `china_area` VALUES (8971, '海拉尔区', 150702, 150700, '呼伦贝尔市', 0);
INSERT INTO `china_area` VALUES (8972, '满洲里市', 150781, 150700, '呼伦贝尔市', 0);
INSERT INTO `china_area` VALUES (8973, '莫力达瓦达斡尔族自治旗', 150722, 150700, '呼伦贝尔市', 0);
INSERT INTO `china_area` VALUES (8974, '新巴尔虎右旗', 150727, 150700, '呼伦贝尔市', 0);
INSERT INTO `china_area` VALUES (8975, '新巴尔虎左旗', 150726, 150700, '呼伦贝尔市', 0);
INSERT INTO `china_area` VALUES (8976, '牙克石市', 150782, 150700, '呼伦贝尔市', 0);
INSERT INTO `china_area` VALUES (8977, '扎兰屯市', 150783, 150700, '呼伦贝尔市', 0);
INSERT INTO `china_area` VALUES (8978, '扎赉诺尔区', 150703, 150700, '呼伦贝尔市', 0);
INSERT INTO `china_area` VALUES (8979, '乌海市', 150300, 150000, '内蒙古自治区', 0);
INSERT INTO `china_area` VALUES (8980, '海南区', 150303, 150300, '乌海市', 0);
INSERT INTO `china_area` VALUES (8981, '海勃湾区', 150302, 150300, '乌海市', 0);
INSERT INTO `china_area` VALUES (8982, '乌达区', 150304, 150300, '乌海市', 0);
INSERT INTO `china_area` VALUES (8983, '乌兰察布市', 150900, 150000, '内蒙古自治区', 0);
INSERT INTO `china_area` VALUES (8984, '察哈尔右翼后旗', 150928, 150900, '乌兰察布市', 0);
INSERT INTO `china_area` VALUES (8985, '察哈尔右翼中旗', 150927, 150900, '乌兰察布市', 0);
INSERT INTO `china_area` VALUES (8986, '察哈尔右翼前旗', 150926, 150900, '乌兰察布市', 0);
INSERT INTO `china_area` VALUES (8987, '丰镇市', 150981, 150900, '乌兰察布市', 0);
INSERT INTO `china_area` VALUES (8988, '化德县', 150922, 150900, '乌兰察布市', 0);
INSERT INTO `china_area` VALUES (8989, '集宁区', 150902, 150900, '乌兰察布市', 0);
INSERT INTO `china_area` VALUES (8990, '凉城县', 150925, 150900, '乌兰察布市', 0);
INSERT INTO `china_area` VALUES (8991, '商都县', 150923, 150900, '乌兰察布市', 0);
INSERT INTO `china_area` VALUES (8992, '四子王旗', 150929, 150900, '乌兰察布市', 0);
INSERT INTO `china_area` VALUES (8993, '兴和县', 150924, 150900, '乌兰察布市', 0);
INSERT INTO `china_area` VALUES (8994, '卓资县', 150921, 150900, '乌兰察布市', 0);
INSERT INTO `china_area` VALUES (8995, '锡林郭勒盟', 152500, 150000, '内蒙古自治区', 0);
INSERT INTO `china_area` VALUES (8996, '阿巴嘎旗', 152522, 152500, '锡林郭勒盟', 0);
INSERT INTO `china_area` VALUES (8997, '东乌珠穆沁旗', 152525, 152500, '锡林郭勒盟', 0);
INSERT INTO `china_area` VALUES (8998, '多伦县', 152531, 152500, '锡林郭勒盟', 0);
INSERT INTO `china_area` VALUES (8999, '二连浩特市', 152501, 152500, '锡林郭勒盟', 0);
INSERT INTO `china_area` VALUES (9000, '苏尼特右旗', 152524, 152500, '锡林郭勒盟', 0);
INSERT INTO `china_area` VALUES (9001, '苏尼特左旗', 152523, 152500, '锡林郭勒盟', 0);
INSERT INTO `china_area` VALUES (9002, '太仆寺旗', 152527, 152500, '锡林郭勒盟', 0);
INSERT INTO `china_area` VALUES (9003, '锡林浩特市', 152502, 152500, '锡林郭勒盟', 0);
INSERT INTO `china_area` VALUES (9004, '西乌珠穆沁旗', 152526, 152500, '锡林郭勒盟', 0);
INSERT INTO `china_area` VALUES (9005, '镶黄旗', 152528, 152500, '锡林郭勒盟', 0);
INSERT INTO `china_area` VALUES (9006, '正蓝旗', 152530, 152500, '锡林郭勒盟', 0);
INSERT INTO `china_area` VALUES (9007, '正镶白旗', 152529, 152500, '锡林郭勒盟', 0);
INSERT INTO `china_area` VALUES (9008, '通辽市', 150500, 150000, '内蒙古自治区', 0);
INSERT INTO `china_area` VALUES (9009, '霍林郭勒市', 150581, 150500, '通辽市', 0);
INSERT INTO `china_area` VALUES (9010, '开鲁县', 150523, 150500, '通辽市', 0);
INSERT INTO `china_area` VALUES (9011, '科尔沁区', 150502, 150500, '通辽市', 0);
INSERT INTO `china_area` VALUES (9012, '科尔沁左翼中旗', 150521, 150500, '通辽市', 0);
INSERT INTO `china_area` VALUES (9013, '科尔沁左翼后旗', 150522, 150500, '通辽市', 0);
INSERT INTO `china_area` VALUES (9014, '库伦旗', 150524, 150500, '通辽市', 0);
INSERT INTO `china_area` VALUES (9015, '奈曼旗', 150525, 150500, '通辽市', 0);
INSERT INTO `china_area` VALUES (9016, '扎鲁特旗', 150526, 150500, '通辽市', 0);
INSERT INTO `china_area` VALUES (9017, '兴安盟', 152200, 150000, '内蒙古自治区', 0);
INSERT INTO `china_area` VALUES (9018, '阿尔山市', 152202, 152200, '兴安盟', 0);
INSERT INTO `china_area` VALUES (9019, '科尔沁右翼前旗', 152221, 152200, '兴安盟', 0);
INSERT INTO `china_area` VALUES (9020, '科尔沁右翼中旗', 152222, 152200, '兴安盟', 0);
INSERT INTO `china_area` VALUES (9021, '突泉县', 152224, 152200, '兴安盟', 0);
INSERT INTO `china_area` VALUES (9022, '乌兰浩特市', 152201, 152200, '兴安盟', 0);
INSERT INTO `china_area` VALUES (9023, '扎赉特旗', 152223, 152200, '兴安盟', 0);
INSERT INTO `china_area` VALUES (9024, '宁夏回族自治区', 640000, 0, '中国', 0);
INSERT INTO `china_area` VALUES (9025, '固原市', 640400, 640000, '宁夏回族自治区', 0);
INSERT INTO `china_area` VALUES (9026, '泾源县', 640424, 640400, '固原市', 0);
INSERT INTO `china_area` VALUES (9027, '隆德县', 640423, 640400, '固原市', 0);
INSERT INTO `china_area` VALUES (9028, '彭阳县', 640425, 640400, '固原市', 0);
INSERT INTO `china_area` VALUES (9029, '西吉县', 640422, 640400, '固原市', 0);
INSERT INTO `china_area` VALUES (9030, '原州区', 640402, 640400, '固原市', 0);
INSERT INTO `china_area` VALUES (9031, '石嘴山市', 640200, 640000, '宁夏回族自治区', 0);
INSERT INTO `china_area` VALUES (9032, '大武口区', 640202, 640200, '石嘴山市', 0);
INSERT INTO `china_area` VALUES (9033, '惠农区', 640205, 640200, '石嘴山市', 0);
INSERT INTO `china_area` VALUES (9034, '平罗县', 640221, 640200, '石嘴山市', 0);
INSERT INTO `china_area` VALUES (9035, '吴忠市', 640300, 640000, '宁夏回族自治区', 0);
INSERT INTO `china_area` VALUES (9036, '红寺堡区', 640303, 640300, '吴忠市', 0);
INSERT INTO `china_area` VALUES (9037, '利通区', 640302, 640300, '吴忠市', 0);
INSERT INTO `china_area` VALUES (9038, '青铜峡市', 640381, 640300, '吴忠市', 0);
INSERT INTO `china_area` VALUES (9039, '同心县', 640324, 640300, '吴忠市', 0);
INSERT INTO `china_area` VALUES (9040, '盐池县', 640323, 640300, '吴忠市', 0);
INSERT INTO `china_area` VALUES (9041, '银川市', 640100, 640000, '宁夏回族自治区', 0);
INSERT INTO `china_area` VALUES (9042, '贺兰县', 640122, 640100, '银川市', 0);
INSERT INTO `china_area` VALUES (9043, '金凤区', 640106, 640100, '银川市', 0);
INSERT INTO `china_area` VALUES (9044, '灵武市', 640181, 640100, '银川市', 0);
INSERT INTO `china_area` VALUES (9045, '西夏区', 640105, 640100, '银川市', 0);
INSERT INTO `china_area` VALUES (9046, '兴庆区', 640104, 640100, '银川市', 0);
INSERT INTO `china_area` VALUES (9047, '永宁县', 640121, 640100, '银川市', 0);
INSERT INTO `china_area` VALUES (9048, '中卫市', 640500, 640000, '宁夏回族自治区', 0);
INSERT INTO `china_area` VALUES (9049, '海原县', 640522, 640500, '中卫市', 0);
INSERT INTO `china_area` VALUES (9050, '沙坡头区', 640502, 640500, '中卫市', 0);
INSERT INTO `china_area` VALUES (9051, '中宁县', 640521, 640500, '中卫市', 0);
INSERT INTO `china_area` VALUES (9052, '江西省', 360000, 0, '中国', 0);
INSERT INTO `china_area` VALUES (9053, '抚州市', 361000, 360000, '江西省', 0);
INSERT INTO `china_area` VALUES (9054, '崇仁县', 361024, 361000, '抚州市', 0);
INSERT INTO `china_area` VALUES (9055, '东乡区', 361003, 361000, '抚州市', 0);
INSERT INTO `china_area` VALUES (9056, '广昌县', 361030, 361000, '抚州市', 0);
INSERT INTO `china_area` VALUES (9057, '金溪县', 361027, 361000, '抚州市', 0);
INSERT INTO `china_area` VALUES (9058, '黎川县', 361022, 361000, '抚州市', 0);
INSERT INTO `china_area` VALUES (9059, '乐安县', 361025, 361000, '抚州市', 0);
INSERT INTO `china_area` VALUES (9060, '临川区', 361002, 361000, '抚州市', 0);
INSERT INTO `china_area` VALUES (9061, '南城县', 361021, 361000, '抚州市', 0);
INSERT INTO `china_area` VALUES (9062, '南丰县', 361023, 361000, '抚州市', 0);
INSERT INTO `china_area` VALUES (9063, '宜黄县', 361026, 361000, '抚州市', 0);
INSERT INTO `china_area` VALUES (9064, '资溪县', 361028, 361000, '抚州市', 0);
INSERT INTO `china_area` VALUES (9065, '赣州市', 360700, 360000, '江西省', 0);
INSERT INTO `china_area` VALUES (9066, '安远县', 360726, 360700, '赣州市', 0);
INSERT INTO `china_area` VALUES (9067, '崇义县', 360725, 360700, '赣州市', 0);
INSERT INTO `china_area` VALUES (9068, '大余县', 360723, 360700, '赣州市', 0);
INSERT INTO `china_area` VALUES (9069, '定南县', 360728, 360700, '赣州市', 0);
INSERT INTO `china_area` VALUES (9070, '赣县区', 360704, 360700, '赣州市', 0);
INSERT INTO `china_area` VALUES (9071, '会昌县', 360733, 360700, '赣州市', 0);
INSERT INTO `china_area` VALUES (9072, '龙南县', 360727, 360700, '赣州市', 0);
INSERT INTO `china_area` VALUES (9073, '南康区', 360703, 360700, '赣州市', 0);
INSERT INTO `china_area` VALUES (9074, '宁都县', 360730, 360700, '赣州市', 0);
INSERT INTO `china_area` VALUES (9075, '全南县', 360729, 360700, '赣州市', 0);
INSERT INTO `china_area` VALUES (9076, '瑞金市', 360781, 360700, '赣州市', 0);
INSERT INTO `china_area` VALUES (9077, '上犹县', 360724, 360700, '赣州市', 0);
INSERT INTO `china_area` VALUES (9078, '石城县', 360735, 360700, '赣州市', 0);
INSERT INTO `china_area` VALUES (9079, '兴国县', 360732, 360700, '赣州市', 0);
INSERT INTO `china_area` VALUES (9080, '信丰县', 360722, 360700, '赣州市', 0);
INSERT INTO `china_area` VALUES (9081, '寻乌县', 360734, 360700, '赣州市', 0);
INSERT INTO `china_area` VALUES (9082, '于都县', 360731, 360700, '赣州市', 0);
INSERT INTO `china_area` VALUES (9083, '章贡区', 360702, 360700, '赣州市', 0);
INSERT INTO `china_area` VALUES (9084, '吉安市', 360800, 360000, '江西省', 0);
INSERT INTO `china_area` VALUES (9085, '安福县', 360829, 360800, '吉安市', 0);
INSERT INTO `china_area` VALUES (9086, '吉州区', 360802, 360800, '吉安市', 0);
INSERT INTO `china_area` VALUES (9087, '吉安县', 360821, 360800, '吉安市', 0);
INSERT INTO `china_area` VALUES (9088, '吉水县', 360822, 360800, '吉安市', 0);
INSERT INTO `china_area` VALUES (9089, '井冈山市', 360881, 360800, '吉安市', 0);
INSERT INTO `china_area` VALUES (9090, '青原区', 360803, 360800, '吉安市', 0);
INSERT INTO `china_area` VALUES (9091, '泰和县', 360826, 360800, '吉安市', 0);
INSERT INTO `china_area` VALUES (9092, '遂川县', 360827, 360800, '吉安市', 0);
INSERT INTO `china_area` VALUES (9093, '万安县', 360828, 360800, '吉安市', 0);
INSERT INTO `china_area` VALUES (9094, '峡江县', 360823, 360800, '吉安市', 0);
INSERT INTO `china_area` VALUES (9095, '新干县', 360824, 360800, '吉安市', 0);
INSERT INTO `china_area` VALUES (9096, '永新县', 360830, 360800, '吉安市', 0);
INSERT INTO `china_area` VALUES (9097, '永丰县', 360825, 360800, '吉安市', 0);
INSERT INTO `china_area` VALUES (9098, '景德镇市', 360200, 360000, '江西省', 0);
INSERT INTO `china_area` VALUES (9099, '昌江区', 360202, 360200, '景德镇市', 0);
INSERT INTO `china_area` VALUES (9100, '浮梁县', 360222, 360200, '景德镇市', 0);
INSERT INTO `china_area` VALUES (9101, '乐平市', 360281, 360200, '景德镇市', 0);
INSERT INTO `china_area` VALUES (9102, '珠山区', 360203, 360200, '景德镇市', 0);
INSERT INTO `china_area` VALUES (9103, '九江市', 360400, 360000, '江西省', 0);
INSERT INTO `china_area` VALUES (9104, '德安县', 360426, 360400, '九江市', 0);
INSERT INTO `china_area` VALUES (9105, '都昌县', 360428, 360400, '九江市', 0);
INSERT INTO `china_area` VALUES (9106, '共青城市', 360482, 360400, '九江市', 0);
INSERT INTO `china_area` VALUES (9107, '湖口县', 360429, 360400, '九江市', 0);
INSERT INTO `china_area` VALUES (9108, '九江县', 360421, 360400, '九江市', 0);
INSERT INTO `china_area` VALUES (9109, '濂溪区', 360402, 360400, '九江市', 0);
INSERT INTO `china_area` VALUES (9110, '庐山市', 360483, 360400, '九江市', 0);
INSERT INTO `china_area` VALUES (9111, '彭泽县', 360430, 360400, '九江市', 0);
INSERT INTO `china_area` VALUES (9112, '瑞昌市', 360481, 360400, '九江市', 0);
INSERT INTO `china_area` VALUES (9113, '武宁县', 360423, 360400, '九江市', 0);
INSERT INTO `china_area` VALUES (9114, '修水县', 360424, 360400, '九江市', 0);
INSERT INTO `china_area` VALUES (9115, '浔阳区', 360403, 360400, '九江市', 0);
INSERT INTO `china_area` VALUES (9116, '永修县', 360425, 360400, '九江市', 0);
INSERT INTO `china_area` VALUES (9117, '南昌市', 360100, 360000, '江西省', 0);
INSERT INTO `china_area` VALUES (9118, '安义县', 360123, 360100, '南昌市', 0);
INSERT INTO `china_area` VALUES (9119, '东湖区', 360102, 360100, '南昌市', 0);
INSERT INTO `china_area` VALUES (9120, '进贤县', 360124, 360100, '南昌市', 0);
INSERT INTO `china_area` VALUES (9121, '南昌县', 360121, 360100, '南昌市', 0);
INSERT INTO `china_area` VALUES (9122, '青云谱区', 360104, 360100, '南昌市', 0);
INSERT INTO `china_area` VALUES (9123, '青山湖区', 360111, 360100, '南昌市', 0);
INSERT INTO `china_area` VALUES (9124, '湾里区', 360105, 360100, '南昌市', 0);
INSERT INTO `china_area` VALUES (9125, '西湖区', 360103, 360100, '南昌市', 0);
INSERT INTO `china_area` VALUES (9126, '新建区', 360112, 360100, '南昌市', 0);
INSERT INTO `china_area` VALUES (9127, '萍乡市', 360300, 360000, '江西省', 0);
INSERT INTO `china_area` VALUES (9128, '安源区', 360302, 360300, '萍乡市', 0);
INSERT INTO `china_area` VALUES (9129, '莲花县', 360321, 360300, '萍乡市', 0);
INSERT INTO `china_area` VALUES (9130, '芦溪县', 360323, 360300, '萍乡市', 0);
INSERT INTO `china_area` VALUES (9131, '上栗县', 360322, 360300, '萍乡市', 0);
INSERT INTO `china_area` VALUES (9132, '湘东区', 360313, 360300, '萍乡市', 0);
INSERT INTO `china_area` VALUES (9133, '上饶市', 361100, 360000, '江西省', 0);
INSERT INTO `china_area` VALUES (9134, '德兴市', 361181, 361100, '上饶市', 0);
INSERT INTO `china_area` VALUES (9135, '广丰区', 361103, 361100, '上饶市', 0);
INSERT INTO `china_area` VALUES (9136, '横峰县', 361125, 361100, '上饶市', 0);
INSERT INTO `china_area` VALUES (9137, '鄱阳县', 361128, 361100, '上饶市', 0);
INSERT INTO `china_area` VALUES (9138, '铅山县', 361124, 361100, '上饶市', 0);
INSERT INTO `china_area` VALUES (9139, '上饶县', 361121, 361100, '上饶市', 0);
INSERT INTO `china_area` VALUES (9140, '万年县', 361129, 361100, '上饶市', 0);
INSERT INTO `china_area` VALUES (9141, '婺源县', 361130, 361100, '上饶市', 0);
INSERT INTO `china_area` VALUES (9142, '信州区', 361102, 361100, '上饶市', 0);
INSERT INTO `china_area` VALUES (9143, '弋阳县', 361126, 361100, '上饶市', 0);
INSERT INTO `china_area` VALUES (9144, '余干县', 361127, 361100, '上饶市', 0);
INSERT INTO `china_area` VALUES (9145, '玉山县', 361123, 361100, '上饶市', 0);
INSERT INTO `china_area` VALUES (9146, '新余市', 360500, 360000, '江西省', 0);
INSERT INTO `china_area` VALUES (9147, '分宜县', 360521, 360500, '新余市', 0);
INSERT INTO `china_area` VALUES (9148, '渝水区', 360502, 360500, '新余市', 0);
INSERT INTO `china_area` VALUES (9149, '宜春市', 360900, 360000, '江西省', 0);
INSERT INTO `china_area` VALUES (9150, '奉新县', 360921, 360900, '宜春市', 0);
INSERT INTO `china_area` VALUES (9151, '丰城市', 360981, 360900, '宜春市', 0);
INSERT INTO `china_area` VALUES (9152, '高安市', 360983, 360900, '宜春市', 0);
INSERT INTO `china_area` VALUES (9153, '靖安县', 360925, 360900, '宜春市', 0);
INSERT INTO `china_area` VALUES (9154, '上高县', 360923, 360900, '宜春市', 0);
INSERT INTO `china_area` VALUES (9155, '铜鼓县', 360926, 360900, '宜春市', 0);
INSERT INTO `china_area` VALUES (9156, '万载县', 360922, 360900, '宜春市', 0);
INSERT INTO `china_area` VALUES (9157, '宜丰县', 360924, 360900, '宜春市', 0);
INSERT INTO `china_area` VALUES (9158, '袁州区', 360902, 360900, '宜春市', 0);
INSERT INTO `china_area` VALUES (9159, '樟树市', 360982, 360900, '宜春市', 0);
INSERT INTO `china_area` VALUES (9160, '鹰潭市', 360600, 360000, '江西省', 0);
INSERT INTO `china_area` VALUES (9161, '贵溪市', 360681, 360600, '鹰潭市', 0);
INSERT INTO `china_area` VALUES (9162, '余江县', 360622, 360600, '鹰潭市', 0);
INSERT INTO `china_area` VALUES (9163, '月湖区', 360602, 360600, '鹰潭市', 0);
INSERT INTO `china_area` VALUES (9164, '江苏省', 320000, 0, '中国', 0);
INSERT INTO `china_area` VALUES (9165, '常州市', 320400, 320000, '江苏省', 0);
INSERT INTO `china_area` VALUES (9166, '金坛区', 320413, 320400, '常州市', 0);
INSERT INTO `china_area` VALUES (9167, '溧阳市', 320481, 320400, '常州市', 0);
INSERT INTO `china_area` VALUES (9168, '天宁区', 320402, 320400, '常州市', 0);
INSERT INTO `china_area` VALUES (9169, '武进区', 320412, 320400, '常州市', 0);
INSERT INTO `china_area` VALUES (9170, '新北区', 320411, 320400, '常州市', 0);
INSERT INTO `china_area` VALUES (9171, '钟楼区', 320404, 320400, '常州市', 0);
INSERT INTO `china_area` VALUES (9172, '淮安市', 320800, 320000, '江苏省', 0);
INSERT INTO `china_area` VALUES (9173, '洪泽区', 320813, 320800, '淮安市', 0);
INSERT INTO `china_area` VALUES (9174, '淮安区', 320803, 320800, '淮安市', 0);
INSERT INTO `china_area` VALUES (9175, '淮阴区', 320804, 320800, '淮安市', 0);
INSERT INTO `china_area` VALUES (9176, '金湖县', 320831, 320800, '淮安市', 0);
INSERT INTO `china_area` VALUES (9177, '涟水县', 320826, 320800, '淮安市', 0);
INSERT INTO `china_area` VALUES (9178, '清江浦区', 320812, 320800, '淮安市', 0);
INSERT INTO `china_area` VALUES (9179, '盱眙县', 320830, 320800, '淮安市', 0);
INSERT INTO `china_area` VALUES (9180, '连云港市', 320700, 320000, '江苏省', 0);
INSERT INTO `china_area` VALUES (9181, '东海县', 320722, 320700, '连云港市', 0);
INSERT INTO `china_area` VALUES (9182, '赣榆区', 320707, 320700, '连云港市', 0);
INSERT INTO `china_area` VALUES (9183, '灌南县', 320724, 320700, '连云港市', 0);
INSERT INTO `china_area` VALUES (9184, '灌云县', 320723, 320700, '连云港市', 0);
INSERT INTO `china_area` VALUES (9185, '海州区', 320706, 320700, '连云港市', 0);
INSERT INTO `china_area` VALUES (9186, '连云区', 320703, 320700, '连云港市', 0);
INSERT INTO `china_area` VALUES (9187, '南京市', 320100, 320000, '江苏省', 0);
INSERT INTO `china_area` VALUES (9188, '高淳区', 320118, 320100, '南京市', 0);
INSERT INTO `china_area` VALUES (9189, '鼓楼区', 320106, 320100, '南京市', 0);
INSERT INTO `china_area` VALUES (9190, '江宁区', 320115, 320100, '南京市', 0);
INSERT INTO `china_area` VALUES (9191, '建邺区', 320105, 320100, '南京市', 0);
INSERT INTO `china_area` VALUES (9192, '溧水区', 320117, 320100, '南京市', 0);
INSERT INTO `china_area` VALUES (9193, '六合区', 320116, 320100, '南京市', 0);
INSERT INTO `china_area` VALUES (9194, '浦口区', 320111, 320100, '南京市', 0);
INSERT INTO `china_area` VALUES (9195, '栖霞区', 320113, 320100, '南京市', 0);
INSERT INTO `china_area` VALUES (9196, '秦淮区', 320104, 320100, '南京市', 0);
INSERT INTO `china_area` VALUES (9197, '玄武区', 320102, 320100, '南京市', 0);
INSERT INTO `china_area` VALUES (9198, '雨花台区', 320114, 320100, '南京市', 0);
INSERT INTO `china_area` VALUES (9199, '南通市', 320600, 320000, '江苏省', 0);
INSERT INTO `china_area` VALUES (9200, '崇川区', 320602, 320600, '南通市', 0);
INSERT INTO `china_area` VALUES (9201, '港闸区', 320611, 320600, '南通市', 0);
INSERT INTO `china_area` VALUES (9202, '海安县', 320621, 320600, '南通市', 0);
INSERT INTO `china_area` VALUES (9203, '海门市', 320684, 320600, '南通市', 0);
INSERT INTO `china_area` VALUES (9204, '启东市', 320681, 320600, '南通市', 0);
INSERT INTO `china_area` VALUES (9205, '如东县', 320623, 320600, '南通市', 0);
INSERT INTO `china_area` VALUES (9206, '如皋市', 320682, 320600, '南通市', 0);
INSERT INTO `china_area` VALUES (9207, '通州区', 320612, 320600, '南通市', 0);
INSERT INTO `china_area` VALUES (9208, '宿迁市', 321300, 320000, '江苏省', 0);
INSERT INTO `china_area` VALUES (9209, '沭阳县', 321322, 321300, '宿迁市', 0);
INSERT INTO `china_area` VALUES (9210, '泗洪县', 321324, 321300, '宿迁市', 0);
INSERT INTO `china_area` VALUES (9211, '泗阳县', 321323, 321300, '宿迁市', 0);
INSERT INTO `china_area` VALUES (9212, '宿豫区', 321311, 321300, '宿迁市', 0);
INSERT INTO `china_area` VALUES (9213, '宿城区', 321302, 321300, '宿迁市', 0);
INSERT INTO `china_area` VALUES (9214, '苏州市', 320500, 320000, '江苏省', 0);
INSERT INTO `china_area` VALUES (9215, '常熟市', 320581, 320500, '苏州市', 0);
INSERT INTO `china_area` VALUES (9216, '姑苏区', 320508, 320500, '苏州市', 0);
INSERT INTO `china_area` VALUES (9217, '虎丘区', 320505, 320500, '苏州市', 0);
INSERT INTO `china_area` VALUES (9218, '昆山市', 320583, 320500, '苏州市', 0);
INSERT INTO `china_area` VALUES (9219, '太仓市', 320585, 320500, '苏州市', 0);
INSERT INTO `china_area` VALUES (9220, '吴江区', 320509, 320500, '苏州市', 0);
INSERT INTO `china_area` VALUES (9221, '吴中区', 320506, 320500, '苏州市', 0);
INSERT INTO `china_area` VALUES (9222, '相城区', 320507, 320500, '苏州市', 0);
INSERT INTO `china_area` VALUES (9223, '张家港市', 320582, 320500, '苏州市', 0);
INSERT INTO `china_area` VALUES (9224, '泰州市', 321200, 320000, '江苏省', 0);
INSERT INTO `china_area` VALUES (9225, '高港区', 321203, 321200, '泰州市', 0);
INSERT INTO `china_area` VALUES (9226, '海陵区', 321202, 321200, '泰州市', 0);
INSERT INTO `china_area` VALUES (9227, '姜堰区', 321204, 321200, '泰州市', 0);
INSERT INTO `china_area` VALUES (9228, '靖江市', 321282, 321200, '泰州市', 0);
INSERT INTO `china_area` VALUES (9229, '泰兴市', 321283, 321200, '泰州市', 0);
INSERT INTO `china_area` VALUES (9230, '兴化市', 321281, 321200, '泰州市', 0);
INSERT INTO `china_area` VALUES (9231, '无锡市', 320200, 320000, '江苏省', 0);
INSERT INTO `china_area` VALUES (9232, '滨湖区', 320211, 320200, '无锡市', 0);
INSERT INTO `china_area` VALUES (9233, '惠山区', 320206, 320200, '无锡市', 0);
INSERT INTO `china_area` VALUES (9234, '江阴市', 320281, 320200, '无锡市', 0);
INSERT INTO `china_area` VALUES (9235, '梁溪区', 320213, 320200, '无锡市', 0);
INSERT INTO `china_area` VALUES (9236, '锡山区', 320205, 320200, '无锡市', 0);
INSERT INTO `china_area` VALUES (9237, '新吴区', 320214, 320200, '无锡市', 0);
INSERT INTO `china_area` VALUES (9238, '宜兴市', 320282, 320200, '无锡市', 0);
INSERT INTO `china_area` VALUES (9239, '徐州市', 320300, 320000, '江苏省', 0);
INSERT INTO `china_area` VALUES (9240, '丰县', 320321, 320300, '徐州市', 0);
INSERT INTO `china_area` VALUES (9241, '鼓楼区', 320302, 320300, '徐州市', 0);
INSERT INTO `china_area` VALUES (9242, '贾汪区', 320305, 320300, '徐州市', 0);
INSERT INTO `china_area` VALUES (9243, '沛县', 320322, 320300, '徐州市', 0);
INSERT INTO `china_area` VALUES (9244, '邳州市', 320382, 320300, '徐州市', 0);
INSERT INTO `china_area` VALUES (9245, '泉山区', 320311, 320300, '徐州市', 0);
INSERT INTO `china_area` VALUES (9246, '睢宁县', 320324, 320300, '徐州市', 0);
INSERT INTO `china_area` VALUES (9247, '铜山区', 320312, 320300, '徐州市', 0);
INSERT INTO `china_area` VALUES (9248, '新沂市', 320381, 320300, '徐州市', 0);
INSERT INTO `china_area` VALUES (9249, '云龙区', 320303, 320300, '徐州市', 0);
INSERT INTO `china_area` VALUES (9250, '盐城市', 320900, 320000, '江苏省', 0);
INSERT INTO `china_area` VALUES (9251, '滨海县', 320922, 320900, '盐城市', 0);
INSERT INTO `china_area` VALUES (9252, '大丰区', 320904, 320900, '盐城市', 0);
INSERT INTO `china_area` VALUES (9253, '东台市', 320981, 320900, '盐城市', 0);
INSERT INTO `china_area` VALUES (9254, '阜宁县', 320923, 320900, '盐城市', 0);
INSERT INTO `china_area` VALUES (9255, '建湖县', 320925, 320900, '盐城市', 0);
INSERT INTO `china_area` VALUES (9256, '射阳县', 320924, 320900, '盐城市', 0);
INSERT INTO `china_area` VALUES (9257, '亭湖区', 320902, 320900, '盐城市', 0);
INSERT INTO `china_area` VALUES (9258, '响水县', 320921, 320900, '盐城市', 0);
INSERT INTO `china_area` VALUES (9259, '盐都区', 320903, 320900, '盐城市', 0);
INSERT INTO `china_area` VALUES (9260, '扬州市', 321000, 320000, '江苏省', 0);
INSERT INTO `china_area` VALUES (9261, '宝应县', 321023, 321000, '扬州市', 0);
INSERT INTO `china_area` VALUES (9262, '高邮市', 321084, 321000, '扬州市', 0);
INSERT INTO `china_area` VALUES (9263, '广陵区', 321002, 321000, '扬州市', 0);
INSERT INTO `china_area` VALUES (9264, '邗江区', 321003, 321000, '扬州市', 0);
INSERT INTO `china_area` VALUES (9265, '江都区', 321012, 321000, '扬州市', 0);
INSERT INTO `china_area` VALUES (9266, '仪征市', 321081, 321000, '扬州市', 0);
INSERT INTO `china_area` VALUES (9267, '镇江市', 321100, 320000, '江苏省', 0);
INSERT INTO `china_area` VALUES (9268, '丹徒区', 321112, 321100, '镇江市', 0);
INSERT INTO `china_area` VALUES (9269, '丹阳市', 321181, 321100, '镇江市', 0);
INSERT INTO `china_area` VALUES (9270, '京口区', 321102, 321100, '镇江市', 0);
INSERT INTO `china_area` VALUES (9271, '句容市', 321183, 321100, '镇江市', 0);
INSERT INTO `china_area` VALUES (9272, '润州区', 321111, 321100, '镇江市', 0);
INSERT INTO `china_area` VALUES (9273, '扬中市', 321182, 321100, '镇江市', 0);
INSERT INTO `china_area` VALUES (9274, '吉林省', 220000, 0, '中国', 0);
INSERT INTO `china_area` VALUES (9275, '白山市', 220600, 220000, '吉林省', 0);
INSERT INTO `china_area` VALUES (9276, '抚松县', 220621, 220600, '白山市', 0);
INSERT INTO `china_area` VALUES (9277, '浑江区', 220602, 220600, '白山市', 0);
INSERT INTO `china_area` VALUES (9278, '江源区', 220605, 220600, '白山市', 0);
INSERT INTO `china_area` VALUES (9279, '靖宇县', 220622, 220600, '白山市', 0);
INSERT INTO `china_area` VALUES (9280, '临江市', 220681, 220600, '白山市', 0);
INSERT INTO `china_area` VALUES (9281, '长白朝鲜族自治县', 220623, 220600, '白山市', 0);
INSERT INTO `china_area` VALUES (9282, '白城市', 220800, 220000, '吉林省', 0);
INSERT INTO `china_area` VALUES (9283, '大安市', 220882, 220800, '白城市', 0);
INSERT INTO `china_area` VALUES (9284, '洮北区', 220802, 220800, '白城市', 0);
INSERT INTO `china_area` VALUES (9285, '洮南市', 220881, 220800, '白城市', 0);
INSERT INTO `china_area` VALUES (9286, '通榆县', 220822, 220800, '白城市', 0);
INSERT INTO `china_area` VALUES (9287, '镇赉县', 220821, 220800, '白城市', 0);
INSERT INTO `china_area` VALUES (9288, '吉林市', 220200, 220000, '吉林省', 0);
INSERT INTO `china_area` VALUES (9289, '昌邑区', 220202, 220200, '吉林市', 0);
INSERT INTO `china_area` VALUES (9290, '船营区', 220204, 220200, '吉林市', 0);
INSERT INTO `china_area` VALUES (9291, '丰满区', 220211, 220200, '吉林市', 0);
INSERT INTO `china_area` VALUES (9292, '桦甸市', 220282, 220200, '吉林市', 0);
INSERT INTO `china_area` VALUES (9293, '蛟河市', 220281, 220200, '吉林市', 0);
INSERT INTO `china_area` VALUES (9294, '龙潭区', 220203, 220200, '吉林市', 0);
INSERT INTO `china_area` VALUES (9295, '磐石市', 220284, 220200, '吉林市', 0);
INSERT INTO `china_area` VALUES (9296, '舒兰市', 220283, 220200, '吉林市', 0);
INSERT INTO `china_area` VALUES (9297, '永吉县', 220221, 220200, '吉林市', 0);
INSERT INTO `china_area` VALUES (9298, '辽源市', 220400, 220000, '吉林省', 0);
INSERT INTO `china_area` VALUES (9299, '东丰县', 220421, 220400, '辽源市', 0);
INSERT INTO `china_area` VALUES (9300, '东辽县', 220422, 220400, '辽源市', 0);
INSERT INTO `china_area` VALUES (9301, '龙山区', 220402, 220400, '辽源市', 0);
INSERT INTO `china_area` VALUES (9302, '西安区', 220403, 220400, '辽源市', 0);
INSERT INTO `china_area` VALUES (9303, '四平市', 220300, 220000, '吉林省', 0);
INSERT INTO `china_area` VALUES (9304, '公主岭市', 220381, 220300, '四平市', 0);
INSERT INTO `china_area` VALUES (9305, '梨树县', 220322, 220300, '四平市', 0);
INSERT INTO `china_area` VALUES (9306, '双辽市', 220382, 220300, '四平市', 0);
INSERT INTO `china_area` VALUES (9307, '铁东区', 220303, 220300, '四平市', 0);
INSERT INTO `china_area` VALUES (9308, '铁西区', 220302, 220300, '四平市', 0);
INSERT INTO `china_area` VALUES (9309, '伊通满族自治县', 220323, 220300, '四平市', 0);
INSERT INTO `china_area` VALUES (9310, '松原市', 220700, 220000, '吉林省', 0);
INSERT INTO `china_area` VALUES (9311, '扶余市', 220781, 220700, '松原市', 0);
INSERT INTO `china_area` VALUES (9312, '乾安县', 220723, 220700, '松原市', 0);
INSERT INTO `china_area` VALUES (9313, '宁江区', 220702, 220700, '松原市', 0);
INSERT INTO `china_area` VALUES (9314, '前郭尔罗斯蒙古族自治县', 220721, 220700, '松原市', 0);
INSERT INTO `china_area` VALUES (9315, '长岭县', 220722, 220700, '松原市', 0);
INSERT INTO `china_area` VALUES (9316, '通化市', 220500, 220000, '吉林省', 0);
INSERT INTO `china_area` VALUES (9317, '东昌区', 220502, 220500, '通化市', 0);
INSERT INTO `china_area` VALUES (9318, '二道江区', 220503, 220500, '通化市', 0);
INSERT INTO `china_area` VALUES (9319, '辉南县', 220523, 220500, '通化市', 0);
INSERT INTO `china_area` VALUES (9320, '集安市', 220582, 220500, '通化市', 0);
INSERT INTO `china_area` VALUES (9321, '柳河县', 220524, 220500, '通化市', 0);
INSERT INTO `china_area` VALUES (9322, '梅河口市', 220581, 220500, '通化市', 0);
INSERT INTO `china_area` VALUES (9323, '通化县', 220521, 220500, '通化市', 0);
INSERT INTO `china_area` VALUES (9324, '延边朝鲜族自治州', 222400, 220000, '吉林省', 0);
INSERT INTO `china_area` VALUES (9325, '安图县', 222426, 222400, '延边朝鲜族自治州', 0);
INSERT INTO `china_area` VALUES (9326, '敦化市', 222403, 222400, '延边朝鲜族自治州', 0);
INSERT INTO `china_area` VALUES (9327, '和龙市', 222406, 222400, '延边朝鲜族自治州', 0);
INSERT INTO `china_area` VALUES (9328, '珲春市', 222404, 222400, '延边朝鲜族自治州', 0);
INSERT INTO `china_area` VALUES (9329, '龙井市', 222405, 222400, '延边朝鲜族自治州', 0);
INSERT INTO `china_area` VALUES (9330, '图们市', 222402, 222400, '延边朝鲜族自治州', 0);
INSERT INTO `china_area` VALUES (9331, '汪清县', 222424, 222400, '延边朝鲜族自治州', 0);
INSERT INTO `china_area` VALUES (9332, '延吉市', 222401, 222400, '延边朝鲜族自治州', 0);
INSERT INTO `china_area` VALUES (9333, '长春市', 220100, 220000, '吉林省', 0);
INSERT INTO `china_area` VALUES (9334, '朝阳区', 220104, 220100, '长春市', 0);
INSERT INTO `china_area` VALUES (9335, '德惠市', 220183, 220100, '长春市', 0);
INSERT INTO `china_area` VALUES (9336, '二道区', 220105, 220100, '长春市', 0);
INSERT INTO `china_area` VALUES (9337, '九台区', 220113, 220100, '长春市', 0);
INSERT INTO `china_area` VALUES (9338, '宽城区', 220103, 220100, '长春市', 0);
INSERT INTO `china_area` VALUES (9339, '绿园区', 220106, 220100, '长春市', 0);
INSERT INTO `china_area` VALUES (9340, '南关区', 220102, 220100, '长春市', 0);
INSERT INTO `china_area` VALUES (9341, '农安县', 220122, 220100, '长春市', 0);
INSERT INTO `china_area` VALUES (9342, '双阳区', 220112, 220100, '长春市', 0);
INSERT INTO `china_area` VALUES (9343, '榆树市', 220182, 220100, '长春市', 0);
INSERT INTO `china_area` VALUES (9344, '辽宁省', 210000, 0, '中国', 0);
INSERT INTO `china_area` VALUES (9345, '本溪市', 210500, 210000, '辽宁省', 0);
INSERT INTO `china_area` VALUES (9346, '本溪满族自治县', 210521, 210500, '本溪市', 0);
INSERT INTO `china_area` VALUES (9347, '桓仁满族自治县', 210522, 210500, '本溪市', 0);
INSERT INTO `china_area` VALUES (9348, '明山区', 210504, 210500, '本溪市', 0);
INSERT INTO `china_area` VALUES (9349, '南芬区', 210505, 210500, '本溪市', 0);
INSERT INTO `china_area` VALUES (9350, '平山区', 210502, 210500, '本溪市', 0);
INSERT INTO `china_area` VALUES (9351, '溪湖区', 210503, 210500, '本溪市', 0);
INSERT INTO `china_area` VALUES (9352, '鞍山市', 210300, 210000, '辽宁省', 0);
INSERT INTO `china_area` VALUES (9353, '海城市', 210381, 210300, '鞍山市', 0);
INSERT INTO `china_area` VALUES (9354, '立山区', 210304, 210300, '鞍山市', 0);
INSERT INTO `china_area` VALUES (9355, '千山区', 210311, 210300, '鞍山市', 0);
INSERT INTO `china_area` VALUES (9356, '台安县', 210321, 210300, '鞍山市', 0);
INSERT INTO `china_area` VALUES (9357, '铁东区', 210302, 210300, '鞍山市', 0);
INSERT INTO `china_area` VALUES (9358, '铁西区', 210303, 210300, '鞍山市', 0);
INSERT INTO `china_area` VALUES (9359, '岫岩满族自治县', 210323, 210300, '鞍山市', 0);
INSERT INTO `china_area` VALUES (9360, '朝阳市', 211300, 210000, '辽宁省', 0);
INSERT INTO `china_area` VALUES (9361, '北票市', 211381, 211300, '朝阳市', 0);
INSERT INTO `china_area` VALUES (9362, '朝阳县', 211321, 211300, '朝阳市', 0);
INSERT INTO `china_area` VALUES (9363, '建平县', 211322, 211300, '朝阳市', 0);
INSERT INTO `china_area` VALUES (9364, '喀喇沁左翼蒙古族自治县', 211324, 211300, '朝阳市', 0);
INSERT INTO `china_area` VALUES (9365, '凌源市', 211382, 211300, '朝阳市', 0);
INSERT INTO `china_area` VALUES (9366, '龙城区', 211303, 211300, '朝阳市', 0);
INSERT INTO `china_area` VALUES (9367, '双塔区', 211302, 211300, '朝阳市', 0);
INSERT INTO `china_area` VALUES (9368, '大连市', 210200, 210000, '辽宁省', 0);
INSERT INTO `china_area` VALUES (9369, '甘井子区', 210211, 210200, '大连市', 0);
INSERT INTO `china_area` VALUES (9370, '金州区', 210213, 210200, '大连市', 0);
INSERT INTO `china_area` VALUES (9371, '旅顺口区', 210212, 210200, '大连市', 0);
INSERT INTO `china_area` VALUES (9372, '普兰店区', 210214, 210200, '大连市', 0);
INSERT INTO `china_area` VALUES (9373, '沙河口区', 210204, 210200, '大连市', 0);
INSERT INTO `china_area` VALUES (9374, '瓦房店市', 210281, 210200, '大连市', 0);
INSERT INTO `china_area` VALUES (9375, '西岗区', 210203, 210200, '大连市', 0);
INSERT INTO `china_area` VALUES (9376, '长海县', 210224, 210200, '大连市', 0);
INSERT INTO `china_area` VALUES (9377, '庄河市', 210283, 210200, '大连市', 0);
INSERT INTO `china_area` VALUES (9378, '中山区', 210202, 210200, '大连市', 0);
INSERT INTO `china_area` VALUES (9379, '丹东市', 210600, 210000, '辽宁省', 0);
INSERT INTO `china_area` VALUES (9380, '东港市', 210681, 210600, '丹东市', 0);
INSERT INTO `china_area` VALUES (9381, '凤城市', 210682, 210600, '丹东市', 0);
INSERT INTO `china_area` VALUES (9382, '宽甸满族自治县', 210624, 210600, '丹东市', 0);
INSERT INTO `china_area` VALUES (9383, '元宝区', 210602, 210600, '丹东市', 0);
INSERT INTO `china_area` VALUES (9384, '振安区', 210604, 210600, '丹东市', 0);
INSERT INTO `china_area` VALUES (9385, '振兴区', 210603, 210600, '丹东市', 0);
INSERT INTO `china_area` VALUES (9386, '阜新市', 210900, 210000, '辽宁省', 0);
INSERT INTO `china_area` VALUES (9387, '阜新蒙古族自治县', 210921, 210900, '阜新市', 0);
INSERT INTO `china_area` VALUES (9388, '海州区', 210902, 210900, '阜新市', 0);
INSERT INTO `china_area` VALUES (9389, '清河门区', 210905, 210900, '阜新市', 0);
INSERT INTO `china_area` VALUES (9390, '太平区', 210904, 210900, '阜新市', 0);
INSERT INTO `china_area` VALUES (9391, '细河区', 210911, 210900, '阜新市', 0);
INSERT INTO `china_area` VALUES (9392, '新邱区', 210903, 210900, '阜新市', 0);
INSERT INTO `china_area` VALUES (9393, '彰武县', 210922, 210900, '阜新市', 0);
INSERT INTO `china_area` VALUES (9394, '抚顺市', 210400, 210000, '辽宁省', 0);
INSERT INTO `china_area` VALUES (9395, '东洲区', 210403, 210400, '抚顺市', 0);
INSERT INTO `china_area` VALUES (9396, '抚顺县', 210421, 210400, '抚顺市', 0);
INSERT INTO `china_area` VALUES (9397, '清原满族自治县', 210423, 210400, '抚顺市', 0);
INSERT INTO `china_area` VALUES (9398, '顺城区', 210411, 210400, '抚顺市', 0);
INSERT INTO `china_area` VALUES (9399, '望花区', 210404, 210400, '抚顺市', 0);
INSERT INTO `china_area` VALUES (9400, '新宾满族自治县', 210422, 210400, '抚顺市', 0);
INSERT INTO `china_area` VALUES (9401, '新抚区', 210402, 210400, '抚顺市', 0);
INSERT INTO `china_area` VALUES (9402, '葫芦岛市', 211400, 210000, '辽宁省', 0);
INSERT INTO `china_area` VALUES (9403, '建昌县', 211422, 211400, '葫芦岛市', 0);
INSERT INTO `china_area` VALUES (9404, '连山区', 211402, 211400, '葫芦岛市', 0);
INSERT INTO `china_area` VALUES (9405, '龙港区', 211403, 211400, '葫芦岛市', 0);
INSERT INTO `china_area` VALUES (9406, '南票区', 211404, 211400, '葫芦岛市', 0);
INSERT INTO `china_area` VALUES (9407, '绥中县', 211421, 211400, '葫芦岛市', 0);
INSERT INTO `china_area` VALUES (9408, '兴城市', 211481, 211400, '葫芦岛市', 0);
INSERT INTO `china_area` VALUES (9409, '锦州市', 210700, 210000, '辽宁省', 0);
INSERT INTO `china_area` VALUES (9410, '北镇市', 210782, 210700, '锦州市', 0);
INSERT INTO `china_area` VALUES (9411, '古塔区', 210702, 210700, '锦州市', 0);
INSERT INTO `china_area` VALUES (9412, '黑山县', 210726, 210700, '锦州市', 0);
INSERT INTO `china_area` VALUES (9413, '凌海市', 210781, 210700, '锦州市', 0);
INSERT INTO `china_area` VALUES (9414, '凌河区', 210703, 210700, '锦州市', 0);
INSERT INTO `china_area` VALUES (9415, '太和区', 210711, 210700, '锦州市', 0);
INSERT INTO `china_area` VALUES (9416, '义县', 210727, 210700, '锦州市', 0);
INSERT INTO `china_area` VALUES (9417, '辽阳市', 211000, 210000, '辽宁省', 0);
INSERT INTO `china_area` VALUES (9418, '白塔区', 211002, 211000, '辽阳市', 0);
INSERT INTO `china_area` VALUES (9419, '灯塔市', 211081, 211000, '辽阳市', 0);
INSERT INTO `china_area` VALUES (9420, '弓长岭区', 211005, 211000, '辽阳市', 0);
INSERT INTO `china_area` VALUES (9421, '宏伟区', 211004, 211000, '辽阳市', 0);
INSERT INTO `china_area` VALUES (9422, '辽阳县', 211021, 211000, '辽阳市', 0);
INSERT INTO `china_area` VALUES (9423, '太子河区', 211011, 211000, '辽阳市', 0);
INSERT INTO `china_area` VALUES (9424, '文圣区', 211003, 211000, '辽阳市', 0);
INSERT INTO `china_area` VALUES (9425, '盘锦市', 211100, 210000, '辽宁省', 0);
INSERT INTO `china_area` VALUES (9426, '大洼区', 211104, 211100, '盘锦市', 0);
INSERT INTO `china_area` VALUES (9427, '盘山县', 211122, 211100, '盘锦市', 0);
INSERT INTO `china_area` VALUES (9428, '双台子区', 211102, 211100, '盘锦市', 0);
INSERT INTO `china_area` VALUES (9429, '兴隆台区', 211103, 211100, '盘锦市', 0);
INSERT INTO `china_area` VALUES (9430, '铁岭市', 211200, 210000, '辽宁省', 0);
INSERT INTO `china_area` VALUES (9431, '昌图县', 211224, 211200, '铁岭市', 0);
INSERT INTO `china_area` VALUES (9432, '调兵山市', 211281, 211200, '铁岭市', 0);
INSERT INTO `china_area` VALUES (9433, '开原市', 211282, 211200, '铁岭市', 0);
INSERT INTO `china_area` VALUES (9434, '清河区', 211204, 211200, '铁岭市', 0);
INSERT INTO `china_area` VALUES (9435, '铁岭县', 211221, 211200, '铁岭市', 0);
INSERT INTO `china_area` VALUES (9436, '西丰县', 211223, 211200, '铁岭市', 0);
INSERT INTO `china_area` VALUES (9437, '银州区', 211202, 211200, '铁岭市', 0);
INSERT INTO `china_area` VALUES (9438, '沈阳市', 210100, 210000, '辽宁省', 0);
INSERT INTO `china_area` VALUES (9439, '沈北新区', 210113, 210100, '沈阳市', 0);
INSERT INTO `china_area` VALUES (9440, '沈河区', 210103, 210100, '沈阳市', 0);
INSERT INTO `china_area` VALUES (9441, '大东区', 210104, 210100, '沈阳市', 0);
INSERT INTO `china_area` VALUES (9442, '法库县', 210124, 210100, '沈阳市', 0);
INSERT INTO `china_area` VALUES (9443, '和平区', 210102, 210100, '沈阳市', 0);
INSERT INTO `china_area` VALUES (9444, '浑南区', 210112, 210100, '沈阳市', 0);
INSERT INTO `china_area` VALUES (9445, '皇姑区', 210105, 210100, '沈阳市', 0);
INSERT INTO `china_area` VALUES (9446, '康平县', 210123, 210100, '沈阳市', 0);
INSERT INTO `china_area` VALUES (9447, '辽中区', 210115, 210100, '沈阳市', 0);
INSERT INTO `china_area` VALUES (9448, '苏家屯区', 210111, 210100, '沈阳市', 0);
INSERT INTO `china_area` VALUES (9449, '铁西区', 210106, 210100, '沈阳市', 0);
INSERT INTO `china_area` VALUES (9450, '新民市', 210181, 210100, '沈阳市', 0);
INSERT INTO `china_area` VALUES (9451, '于洪区', 210114, 210100, '沈阳市', 0);
INSERT INTO `china_area` VALUES (9452, '营口市', 210800, 210000, '辽宁省', 0);
INSERT INTO `china_area` VALUES (9453, '鲅鱼圈区', 210804, 210800, '营口市', 0);
INSERT INTO `china_area` VALUES (9454, '大石桥市', 210882, 210800, '营口市', 0);
INSERT INTO `china_area` VALUES (9455, '盖州市', 210881, 210800, '营口市', 0);
INSERT INTO `china_area` VALUES (9456, '老边区', 210811, 210800, '营口市', 0);
INSERT INTO `china_area` VALUES (9457, '西市区', 210803, 210800, '营口市', 0);
INSERT INTO `china_area` VALUES (9458, '站前区', 210802, 210800, '营口市', 0);
INSERT INTO `china_area` VALUES (9459, '山东省', 370000, 0, '中国', 0);
INSERT INTO `china_area` VALUES (9460, '滨州市', 371600, 370000, '山东省', 0);
INSERT INTO `china_area` VALUES (9461, '滨城区', 371602, 371600, '滨州市', 0);
INSERT INTO `china_area` VALUES (9462, '博兴县', 371625, 371600, '滨州市', 0);
INSERT INTO `china_area` VALUES (9463, '惠民县', 371621, 371600, '滨州市', 0);
INSERT INTO `china_area` VALUES (9464, '无棣县', 371623, 371600, '滨州市', 0);
INSERT INTO `china_area` VALUES (9465, '阳信县', 371622, 371600, '滨州市', 0);
INSERT INTO `china_area` VALUES (9466, '沾化区', 371603, 371600, '滨州市', 0);
INSERT INTO `china_area` VALUES (9467, '邹平县', 371626, 371600, '滨州市', 0);
INSERT INTO `china_area` VALUES (9468, '德州市', 371400, 370000, '山东省', 0);
INSERT INTO `china_area` VALUES (9469, '德城区', 371402, 371400, '德州市', 0);
INSERT INTO `china_area` VALUES (9470, '乐陵市', 371481, 371400, '德州市', 0);
INSERT INTO `china_area` VALUES (9471, '临邑县', 371424, 371400, '德州市', 0);
INSERT INTO `china_area` VALUES (9472, '陵城区', 371403, 371400, '德州市', 0);
INSERT INTO `china_area` VALUES (9473, '平原县', 371426, 371400, '德州市', 0);
INSERT INTO `china_area` VALUES (9474, '宁津县', 371422, 371400, '德州市', 0);
INSERT INTO `china_area` VALUES (9475, '齐河县', 371425, 371400, '德州市', 0);
INSERT INTO `china_area` VALUES (9476, '庆云县', 371423, 371400, '德州市', 0);
INSERT INTO `china_area` VALUES (9477, '武城县', 371428, 371400, '德州市', 0);
INSERT INTO `china_area` VALUES (9478, '夏津县', 371427, 371400, '德州市', 0);
INSERT INTO `china_area` VALUES (9479, '禹城市', 371482, 371400, '德州市', 0);
INSERT INTO `china_area` VALUES (9480, '东营市', 370500, 370000, '山东省', 0);
INSERT INTO `china_area` VALUES (9481, '东营区', 370502, 370500, '东营市', 0);
INSERT INTO `china_area` VALUES (9482, '广饶县', 370523, 370500, '东营市', 0);
INSERT INTO `china_area` VALUES (9483, '河口区', 370503, 370500, '东营市', 0);
INSERT INTO `china_area` VALUES (9484, '垦利区', 370505, 370500, '东营市', 0);
INSERT INTO `china_area` VALUES (9485, '利津县', 370522, 370500, '东营市', 0);
INSERT INTO `china_area` VALUES (9486, '菏泽市', 371700, 370000, '山东省', 0);
INSERT INTO `china_area` VALUES (9487, '曹县', 371721, 371700, '菏泽市', 0);
INSERT INTO `china_area` VALUES (9488, '成武县', 371723, 371700, '菏泽市', 0);
INSERT INTO `china_area` VALUES (9489, '单县', 371722, 371700, '菏泽市', 0);
INSERT INTO `china_area` VALUES (9490, '定陶区', 371703, 371700, '菏泽市', 0);
INSERT INTO `china_area` VALUES (9491, '东明县', 371728, 371700, '菏泽市', 0);
INSERT INTO `china_area` VALUES (9492, '巨野县', 371724, 371700, '菏泽市', 0);
INSERT INTO `china_area` VALUES (9493, '鄄城县', 371726, 371700, '菏泽市', 0);
INSERT INTO `china_area` VALUES (9494, '牡丹区', 371702, 371700, '菏泽市', 0);
INSERT INTO `china_area` VALUES (9495, '郓城县', 371725, 371700, '菏泽市', 0);
INSERT INTO `china_area` VALUES (9496, '济宁市', 370800, 370000, '山东省', 0);
INSERT INTO `china_area` VALUES (9497, '嘉祥县', 370829, 370800, '济宁市', 0);
INSERT INTO `china_area` VALUES (9498, '金乡县', 370828, 370800, '济宁市', 0);
INSERT INTO `china_area` VALUES (9499, '梁山县', 370832, 370800, '济宁市', 0);
INSERT INTO `china_area` VALUES (9500, '曲阜市', 370881, 370800, '济宁市', 0);
INSERT INTO `china_area` VALUES (9501, '任城区', 370811, 370800, '济宁市', 0);
INSERT INTO `china_area` VALUES (9502, '泗水县', 370831, 370800, '济宁市', 0);
INSERT INTO `china_area` VALUES (9503, '微山县', 370826, 370800, '济宁市', 0);
INSERT INTO `china_area` VALUES (9504, '汶上县', 370830, 370800, '济宁市', 0);
INSERT INTO `china_area` VALUES (9505, '兖州区', 370812, 370800, '济宁市', 0);
INSERT INTO `china_area` VALUES (9506, '鱼台县', 370827, 370800, '济宁市', 0);
INSERT INTO `china_area` VALUES (9507, '邹城市', 370883, 370800, '济宁市', 0);
INSERT INTO `china_area` VALUES (9508, '济南市', 370100, 370000, '山东省', 0);
INSERT INTO `china_area` VALUES (9509, '槐荫区', 370104, 370100, '济南市', 0);
INSERT INTO `china_area` VALUES (9510, '济阳县', 370125, 370100, '济南市', 0);
INSERT INTO `china_area` VALUES (9511, '历城区', 370112, 370100, '济南市', 0);
INSERT INTO `china_area` VALUES (9512, '历下区', 370102, 370100, '济南市', 0);
INSERT INTO `china_area` VALUES (9513, '平阴县', 370124, 370100, '济南市', 0);
INSERT INTO `china_area` VALUES (9514, '市中区', 370103, 370100, '济南市', 0);
INSERT INTO `china_area` VALUES (9515, '商河县', 370126, 370100, '济南市', 0);
INSERT INTO `china_area` VALUES (9516, '天桥区', 370105, 370100, '济南市', 0);
INSERT INTO `china_area` VALUES (9517, '长清区', 370113, 370100, '济南市', 0);
INSERT INTO `china_area` VALUES (9518, '章丘区', 370114, 370100, '济南市', 0);
INSERT INTO `china_area` VALUES (9519, '聊城市', 371500, 370000, '山东省', 0);
INSERT INTO `china_area` VALUES (9520, '茌平县', 371523, 371500, '聊城市', 0);
INSERT INTO `china_area` VALUES (9521, '东昌府区', 371502, 371500, '聊城市', 0);
INSERT INTO `china_area` VALUES (9522, '东阿县', 371524, 371500, '聊城市', 0);
INSERT INTO `china_area` VALUES (9523, '高唐县', 371526, 371500, '聊城市', 0);
INSERT INTO `china_area` VALUES (9524, '冠县', 371525, 371500, '聊城市', 0);
INSERT INTO `china_area` VALUES (9525, '临清市', 371581, 371500, '聊城市', 0);
INSERT INTO `china_area` VALUES (9526, '莘县', 371522, 371500, '聊城市', 0);
INSERT INTO `china_area` VALUES (9527, '阳谷县', 371521, 371500, '聊城市', 0);
INSERT INTO `china_area` VALUES (9528, '莱芜市', 371200, 370000, '山东省', 0);
INSERT INTO `china_area` VALUES (9529, '钢城区', 371203, 371200, '莱芜市', 0);
INSERT INTO `china_area` VALUES (9530, '莱城区', 371202, 371200, '莱芜市', 0);
INSERT INTO `china_area` VALUES (9531, '临沂市', 371300, 370000, '山东省', 0);
INSERT INTO `china_area` VALUES (9532, '费县', 371325, 371300, '临沂市', 0);
INSERT INTO `china_area` VALUES (9533, '河东区', 371312, 371300, '临沂市', 0);
INSERT INTO `china_area` VALUES (9534, '莒南县', 371327, 371300, '临沂市', 0);
INSERT INTO `china_area` VALUES (9535, '兰陵县', 371324, 371300, '临沂市', 0);
INSERT INTO `china_area` VALUES (9536, '兰山区', 371302, 371300, '临沂市', 0);
INSERT INTO `china_area` VALUES (9537, '临沭县', 371329, 371300, '临沂市', 0);
INSERT INTO `china_area` VALUES (9538, '蒙阴县', 371328, 371300, '临沂市', 0);
INSERT INTO `china_area` VALUES (9539, '罗庄区', 371311, 371300, '临沂市', 0);
INSERT INTO `china_area` VALUES (9540, '平邑县', 371326, 371300, '临沂市', 0);
INSERT INTO `china_area` VALUES (9541, '郯城县', 371322, 371300, '临沂市', 0);
INSERT INTO `china_area` VALUES (9542, '沂水县', 371323, 371300, '临沂市', 0);
INSERT INTO `china_area` VALUES (9543, '沂南县', 371321, 371300, '临沂市', 0);
INSERT INTO `china_area` VALUES (9544, '日照市', 371100, 370000, '山东省', 0);
INSERT INTO `china_area` VALUES (9545, '东港区', 371102, 371100, '日照市', 0);
INSERT INTO `china_area` VALUES (9546, '莒县', 371122, 371100, '日照市', 0);
INSERT INTO `china_area` VALUES (9547, '岚山区', 371103, 371100, '日照市', 0);
INSERT INTO `china_area` VALUES (9548, '五莲县', 371121, 371100, '日照市', 0);
INSERT INTO `china_area` VALUES (9549, '青岛市', 370200, 370000, '山东省', 0);
INSERT INTO `china_area` VALUES (9550, '城阳区', 370214, 370200, '青岛市', 0);
INSERT INTO `china_area` VALUES (9551, '黄岛区', 370211, 370200, '青岛市', 0);
INSERT INTO `china_area` VALUES (9552, '即墨市', 370282, 370200, '青岛市', 0);
INSERT INTO `china_area` VALUES (9553, '胶州市', 370281, 370200, '青岛市', 0);
INSERT INTO `china_area` VALUES (9554, '莱西市', 370285, 370200, '青岛市', 0);
INSERT INTO `china_area` VALUES (9555, '李沧区', 370213, 370200, '青岛市', 0);
INSERT INTO `china_area` VALUES (9556, '崂山区', 370212, 370200, '青岛市', 0);
INSERT INTO `china_area` VALUES (9557, '平度市', 370283, 370200, '青岛市', 0);
INSERT INTO `china_area` VALUES (9558, '市南区', 370202, 370200, '青岛市', 0);
INSERT INTO `china_area` VALUES (9559, '市北区', 370203, 370200, '青岛市', 0);
INSERT INTO `china_area` VALUES (9560, '泰安市', 370900, 370000, '山东省', 0);
INSERT INTO `china_area` VALUES (9561, '岱岳区', 370911, 370900, '泰安市', 0);
INSERT INTO `china_area` VALUES (9562, '东平县', 370923, 370900, '泰安市', 0);
INSERT INTO `china_area` VALUES (9563, '肥城市', 370983, 370900, '泰安市', 0);
INSERT INTO `china_area` VALUES (9564, '宁阳县', 370921, 370900, '泰安市', 0);
INSERT INTO `china_area` VALUES (9565, '泰山区', 370902, 370900, '泰安市', 0);
INSERT INTO `china_area` VALUES (9566, '新泰市', 370982, 370900, '泰安市', 0);
INSERT INTO `china_area` VALUES (9567, '威海市', 371000, 370000, '山东省', 0);
INSERT INTO `china_area` VALUES (9568, '环翠区', 371002, 371000, '威海市', 0);
INSERT INTO `china_area` VALUES (9569, '乳山市', 371083, 371000, '威海市', 0);
INSERT INTO `china_area` VALUES (9570, '荣成市', 371082, 371000, '威海市', 0);
INSERT INTO `china_area` VALUES (9571, '文登区', 371003, 371000, '威海市', 0);
INSERT INTO `china_area` VALUES (9572, '潍坊市', 370700, 370000, '山东省', 0);
INSERT INTO `china_area` VALUES (9573, '安丘市', 370784, 370700, '潍坊市', 0);
INSERT INTO `china_area` VALUES (9574, '昌乐县', 370725, 370700, '潍坊市', 0);
INSERT INTO `china_area` VALUES (9575, '昌邑市', 370786, 370700, '潍坊市', 0);
INSERT INTO `china_area` VALUES (9576, '坊子区', 370704, 370700, '潍坊市', 0);
INSERT INTO `china_area` VALUES (9577, '高密市', 370785, 370700, '潍坊市', 0);
INSERT INTO `china_area` VALUES (9578, '寒亭区', 370703, 370700, '潍坊市', 0);
INSERT INTO `china_area` VALUES (9579, '奎文区', 370705, 370700, '潍坊市', 0);
INSERT INTO `china_area` VALUES (9580, '临朐县', 370724, 370700, '潍坊市', 0);
INSERT INTO `china_area` VALUES (9581, '青州市', 370781, 370700, '潍坊市', 0);
INSERT INTO `china_area` VALUES (9582, '寿光市', 370783, 370700, '潍坊市', 0);
INSERT INTO `china_area` VALUES (9583, '潍城区', 370702, 370700, '潍坊市', 0);
INSERT INTO `china_area` VALUES (9584, '诸城市', 370782, 370700, '潍坊市', 0);
INSERT INTO `china_area` VALUES (9585, '烟台市', 370600, 370000, '山东省', 0);
INSERT INTO `china_area` VALUES (9586, '福山区', 370611, 370600, '烟台市', 0);
INSERT INTO `china_area` VALUES (9587, '海阳市', 370687, 370600, '烟台市', 0);
INSERT INTO `china_area` VALUES (9588, '莱山区', 370613, 370600, '烟台市', 0);
INSERT INTO `china_area` VALUES (9589, '莱阳市', 370682, 370600, '烟台市', 0);
INSERT INTO `china_area` VALUES (9590, '莱州市', 370683, 370600, '烟台市', 0);
INSERT INTO `china_area` VALUES (9591, '龙口市', 370681, 370600, '烟台市', 0);
INSERT INTO `china_area` VALUES (9592, '牟平区', 370612, 370600, '烟台市', 0);
INSERT INTO `china_area` VALUES (9593, '蓬莱市', 370684, 370600, '烟台市', 0);
INSERT INTO `china_area` VALUES (9594, '栖霞市', 370686, 370600, '烟台市', 0);
INSERT INTO `china_area` VALUES (9595, '招远市', 370685, 370600, '烟台市', 0);
INSERT INTO `china_area` VALUES (9596, '芝罘区', 370602, 370600, '烟台市', 0);
INSERT INTO `china_area` VALUES (9597, '长岛县', 370634, 370600, '烟台市', 0);
INSERT INTO `china_area` VALUES (9598, '淄博市', 370300, 370000, '山东省', 0);
INSERT INTO `china_area` VALUES (9599, '博山区', 370304, 370300, '淄博市', 0);
INSERT INTO `china_area` VALUES (9600, '高青县', 370322, 370300, '淄博市', 0);
INSERT INTO `china_area` VALUES (9601, '桓台县', 370321, 370300, '淄博市', 0);
INSERT INTO `china_area` VALUES (9602, '临淄区', 370305, 370300, '淄博市', 0);
INSERT INTO `china_area` VALUES (9603, '沂源县', 370323, 370300, '淄博市', 0);
INSERT INTO `china_area` VALUES (9604, '张店区', 370303, 370300, '淄博市', 0);
INSERT INTO `china_area` VALUES (9605, '周村区', 370306, 370300, '淄博市', 0);
INSERT INTO `china_area` VALUES (9606, '淄川区', 370302, 370300, '淄博市', 0);
INSERT INTO `china_area` VALUES (9607, '枣庄市', 370400, 370000, '山东省', 0);
INSERT INTO `china_area` VALUES (9608, '山亭区', 370406, 370400, '枣庄市', 0);
INSERT INTO `china_area` VALUES (9609, '市中区', 370402, 370400, '枣庄市', 0);
INSERT INTO `china_area` VALUES (9610, '台儿庄区', 370405, 370400, '枣庄市', 0);
INSERT INTO `china_area` VALUES (9611, '滕州市', 370481, 370400, '枣庄市', 0);
INSERT INTO `china_area` VALUES (9612, '薛城区', 370403, 370400, '枣庄市', 0);
INSERT INTO `china_area` VALUES (9613, '峄城区', 370404, 370400, '枣庄市', 0);
INSERT INTO `china_area` VALUES (9614, '新疆维吾尔自治区', 650000, 0, '中国', 0);
INSERT INTO `china_area` VALUES (9615, '阿克苏地区', 652900, 650000, '新疆维吾尔自治区', 0);
INSERT INTO `china_area` VALUES (9616, '阿克苏市', 652901, 652900, '阿克苏地区', 0);
INSERT INTO `china_area` VALUES (9617, '阿瓦提县', 652928, 652900, '阿克苏地区', 0);
INSERT INTO `china_area` VALUES (9618, '拜城县', 652926, 652900, '阿克苏地区', 0);
INSERT INTO `china_area` VALUES (9619, '柯坪县', 652929, 652900, '阿克苏地区', 0);
INSERT INTO `china_area` VALUES (9620, '库车县', 652923, 652900, '阿克苏地区', 0);
INSERT INTO `china_area` VALUES (9621, '沙雅县', 652924, 652900, '阿克苏地区', 0);
INSERT INTO `china_area` VALUES (9622, '温宿县', 652922, 652900, '阿克苏地区', 0);
INSERT INTO `china_area` VALUES (9623, '乌什县', 652927, 652900, '阿克苏地区', 0);
INSERT INTO `china_area` VALUES (9624, '新和县', 652925, 652900, '阿克苏地区', 0);
INSERT INTO `china_area` VALUES (9625, '阿勒泰地区', 654300, 650000, '新疆维吾尔自治区', 0);
INSERT INTO `china_area` VALUES (9626, '阿勒泰市', 654301, 654300, '阿勒泰地区', 0);
INSERT INTO `china_area` VALUES (9627, '布尔津县', 654321, 654300, '阿勒泰地区', 0);
INSERT INTO `china_area` VALUES (9628, '富蕴县', 654322, 654300, '阿勒泰地区', 0);
INSERT INTO `china_area` VALUES (9629, '福海县', 654323, 654300, '阿勒泰地区', 0);
INSERT INTO `china_area` VALUES (9630, '哈巴河县', 654324, 654300, '阿勒泰地区', 0);
INSERT INTO `china_area` VALUES (9631, '吉木乃县', 654326, 654300, '阿勒泰地区', 0);
INSERT INTO `china_area` VALUES (9632, '青河县', 654325, 654300, '阿勒泰地区', 0);
INSERT INTO `china_area` VALUES (9633, '巴音郭楞蒙古自治州', 652800, 650000, '新疆维吾尔自治区', 0);
INSERT INTO `china_area` VALUES (9634, '博湖县', 652829, 652800, '巴音郭楞蒙古自治州', 0);
INSERT INTO `china_area` VALUES (9635, '和静县', 652827, 652800, '巴音郭楞蒙古自治州', 0);
INSERT INTO `china_area` VALUES (9636, '和硕县', 652828, 652800, '巴音郭楞蒙古自治州', 0);
INSERT INTO `china_area` VALUES (9637, '库尔勒市', 652801, 652800, '巴音郭楞蒙古自治州', 0);
INSERT INTO `china_area` VALUES (9638, '轮台县', 652822, 652800, '巴音郭楞蒙古自治州', 0);
INSERT INTO `china_area` VALUES (9639, '且末县', 652825, 652800, '巴音郭楞蒙古自治州', 0);
INSERT INTO `china_area` VALUES (9640, '若羌县', 652824, 652800, '巴音郭楞蒙古自治州', 0);
INSERT INTO `china_area` VALUES (9641, '尉犁县', 652823, 652800, '巴音郭楞蒙古自治州', 0);
INSERT INTO `china_area` VALUES (9642, '焉耆回族自治县', 652826, 652800, '巴音郭楞蒙古自治州', 0);
INSERT INTO `china_area` VALUES (9643, '博尔塔拉蒙古自治州', 652700, 650000, '新疆维吾尔自治区', 0);
INSERT INTO `china_area` VALUES (9644, '阿拉山口市', 652702, 652700, '博尔塔拉蒙古自治州', 0);
INSERT INTO `china_area` VALUES (9645, '博乐市', 652701, 652700, '博尔塔拉蒙古自治州', 0);
INSERT INTO `china_area` VALUES (9646, '精河县', 652722, 652700, '博尔塔拉蒙古自治州', 0);
INSERT INTO `china_area` VALUES (9647, '温泉县', 652723, 652700, '博尔塔拉蒙古自治州', 0);
INSERT INTO `china_area` VALUES (9648, '昌吉回族自治州', 652300, 650000, '新疆维吾尔自治区', 0);
INSERT INTO `china_area` VALUES (9649, '昌吉市', 652301, 652300, '昌吉回族自治州', 0);
INSERT INTO `china_area` VALUES (9650, '阜康市', 652302, 652300, '昌吉回族自治州', 0);
INSERT INTO `china_area` VALUES (9651, '呼图壁县', 652323, 652300, '昌吉回族自治州', 0);
INSERT INTO `china_area` VALUES (9652, '吉木萨尔县', 652327, 652300, '昌吉回族自治州', 0);
INSERT INTO `china_area` VALUES (9653, '玛纳斯县', 652324, 652300, '昌吉回族自治州', 0);
INSERT INTO `china_area` VALUES (9654, '木垒哈萨克自治县', 652328, 652300, '昌吉回族自治州', 0);
INSERT INTO `china_area` VALUES (9655, '奇台县', 652325, 652300, '昌吉回族自治州', 0);
INSERT INTO `china_area` VALUES (9656, '哈密市', 650500, 650000, '新疆维吾尔自治区', 0);
INSERT INTO `china_area` VALUES (9657, '巴里坤哈萨克自治县', 650521, 650500, '哈密市', 0);
INSERT INTO `china_area` VALUES (9658, '伊州区', 650502, 650500, '哈密市', 0);
INSERT INTO `china_area` VALUES (9659, '伊吾县', 650522, 650500, '哈密市', 0);
INSERT INTO `china_area` VALUES (9660, '和田地区', 653200, 650000, '新疆维吾尔自治区', 0);
INSERT INTO `china_area` VALUES (9661, '策勒县', 653225, 653200, '和田地区', 0);
INSERT INTO `china_area` VALUES (9662, '和田市', 653201, 653200, '和田地区', 0);
INSERT INTO `china_area` VALUES (9663, '和田县', 653221, 653200, '和田地区', 0);
INSERT INTO `china_area` VALUES (9664, '洛浦县', 653224, 653200, '和田地区', 0);
INSERT INTO `china_area` VALUES (9665, '民丰县', 653227, 653200, '和田地区', 0);
INSERT INTO `china_area` VALUES (9666, '墨玉县', 653222, 653200, '和田地区', 0);
INSERT INTO `china_area` VALUES (9667, '皮山县', 653223, 653200, '和田地区', 0);
INSERT INTO `china_area` VALUES (9668, '于田县', 653226, 653200, '和田地区', 0);
INSERT INTO `china_area` VALUES (9669, '喀什地区', 653100, 650000, '新疆维吾尔自治区', 0);
INSERT INTO `china_area` VALUES (9670, '巴楚县', 653130, 653100, '喀什地区', 0);
INSERT INTO `china_area` VALUES (9671, '伽师县', 653129, 653100, '喀什地区', 0);
INSERT INTO `china_area` VALUES (9672, '喀什市', 653101, 653100, '喀什地区', 0);
INSERT INTO `china_area` VALUES (9673, '麦盖提县', 653127, 653100, '喀什地区', 0);
INSERT INTO `china_area` VALUES (9674, '莎车县', 653125, 653100, '喀什地区', 0);
INSERT INTO `china_area` VALUES (9675, '疏附县', 653121, 653100, '喀什地区', 0);
INSERT INTO `china_area` VALUES (9676, '疏勒县', 653122, 653100, '喀什地区', 0);
INSERT INTO `china_area` VALUES (9677, '塔什库尔干塔吉克自治县', 653131, 653100, '喀什地区', 0);
INSERT INTO `china_area` VALUES (9678, '叶城县', 653126, 653100, '喀什地区', 0);
INSERT INTO `china_area` VALUES (9679, '英吉沙县', 653123, 653100, '喀什地区', 0);
INSERT INTO `china_area` VALUES (9680, '岳普湖县', 653128, 653100, '喀什地区', 0);
INSERT INTO `china_area` VALUES (9681, '泽普县', 653124, 653100, '喀什地区', 0);
INSERT INTO `china_area` VALUES (9682, '克孜勒苏柯尔克孜自治州', 653000, 650000, '新疆维吾尔自治区', 0);
INSERT INTO `china_area` VALUES (9683, '阿克陶县', 653022, 653000, '克孜勒苏柯尔克孜自治州', 0);
INSERT INTO `china_area` VALUES (9684, '阿图什市', 653001, 653000, '克孜勒苏柯尔克孜自治州', 0);
INSERT INTO `china_area` VALUES (9685, '阿合奇县', 653023, 653000, '克孜勒苏柯尔克孜自治州', 0);
INSERT INTO `china_area` VALUES (9686, '乌恰县', 653024, 653000, '克孜勒苏柯尔克孜自治州', 0);
INSERT INTO `china_area` VALUES (9687, '克拉玛依市', 650200, 650000, '新疆维吾尔自治区', 0);
INSERT INTO `china_area` VALUES (9688, '白碱滩区', 650204, 650200, '克拉玛依市', 0);
INSERT INTO `china_area` VALUES (9689, '独山子区', 650202, 650200, '克拉玛依市', 0);
INSERT INTO `china_area` VALUES (9690, '克拉玛依区', 650203, 650200, '克拉玛依市', 0);
INSERT INTO `china_area` VALUES (9691, '乌尔禾区', 650205, 650200, '克拉玛依市', 0);
INSERT INTO `china_area` VALUES (9692, '塔城地区', 654200, 650000, '新疆维吾尔自治区', 0);
INSERT INTO `china_area` VALUES (9693, '额敏县', 654221, 654200, '塔城地区', 0);
INSERT INTO `china_area` VALUES (9694, '和布克赛尔蒙古自治县', 654226, 654200, '塔城地区', 0);
INSERT INTO `china_area` VALUES (9695, '沙湾县', 654223, 654200, '塔城地区', 0);
INSERT INTO `china_area` VALUES (9696, '塔城市', 654201, 654200, '塔城地区', 0);
INSERT INTO `china_area` VALUES (9697, '托里县', 654224, 654200, '塔城地区', 0);
INSERT INTO `china_area` VALUES (9698, '乌苏市', 654202, 654200, '塔城地区', 0);
INSERT INTO `china_area` VALUES (9699, '裕民县', 654225, 654200, '塔城地区', 0);
INSERT INTO `china_area` VALUES (9700, '吐鲁番市', 650400, 650000, '新疆维吾尔自治区', 0);
INSERT INTO `china_area` VALUES (9701, '高昌区', 650402, 650400, '吐鲁番市', 0);
INSERT INTO `china_area` VALUES (9702, '鄯善县', 650421, 650400, '吐鲁番市', 0);
INSERT INTO `china_area` VALUES (9703, '托克逊县', 650422, 650400, '吐鲁番市', 0);
INSERT INTO `china_area` VALUES (9704, '乌鲁木齐市', 650100, 650000, '新疆维吾尔自治区', 0);
INSERT INTO `china_area` VALUES (9705, '达坂城区', 650107, 650100, '乌鲁木齐市', 0);
INSERT INTO `china_area` VALUES (9706, '米东区', 650109, 650100, '乌鲁木齐市', 0);
INSERT INTO `china_area` VALUES (9707, '沙依巴克区', 650103, 650100, '乌鲁木齐市', 0);
INSERT INTO `china_area` VALUES (9708, '水磨沟区', 650105, 650100, '乌鲁木齐市', 0);
INSERT INTO `china_area` VALUES (9709, '天山区', 650102, 650100, '乌鲁木齐市', 0);
INSERT INTO `china_area` VALUES (9710, '头屯河区', 650106, 650100, '乌鲁木齐市', 0);
INSERT INTO `china_area` VALUES (9711, '乌鲁木齐县', 650121, 650100, '乌鲁木齐市', 0);
INSERT INTO `china_area` VALUES (9712, '新市区', 650104, 650100, '乌鲁木齐市', 0);
INSERT INTO `china_area` VALUES (9713, '伊犁哈萨克自治州', 654000, 650000, '新疆维吾尔自治区', 0);
INSERT INTO `china_area` VALUES (9714, '察布查尔锡伯自治县', 654022, 654000, '伊犁哈萨克自治州', 0);
INSERT INTO `china_area` VALUES (9715, '巩留县', 654024, 654000, '伊犁哈萨克自治州', 0);
INSERT INTO `china_area` VALUES (9716, '霍尔果斯市', 654004, 654000, '伊犁哈萨克自治州', 0);
INSERT INTO `china_area` VALUES (9717, '霍城县', 654023, 654000, '伊犁哈萨克自治州', 0);
INSERT INTO `china_area` VALUES (9718, '奎屯市', 654003, 654000, '伊犁哈萨克自治州', 0);
INSERT INTO `china_area` VALUES (9719, '尼勒克县', 654028, 654000, '伊犁哈萨克自治州', 0);
INSERT INTO `china_area` VALUES (9720, '特克斯县', 654027, 654000, '伊犁哈萨克自治州', 0);
INSERT INTO `china_area` VALUES (9721, '新源县', 654025, 654000, '伊犁哈萨克自治州', 0);
INSERT INTO `china_area` VALUES (9722, '伊宁县', 654021, 654000, '伊犁哈萨克自治州', 0);
INSERT INTO `china_area` VALUES (9723, '伊宁市', 654002, 654000, '伊犁哈萨克自治州', 0);
INSERT INTO `china_area` VALUES (9724, '昭苏县', 654026, 654000, '伊犁哈萨克自治州', 0);
INSERT INTO `china_area` VALUES (9725, '阿拉尔市', 659002, 650000, '新疆维吾尔自治区', 0);
INSERT INTO `china_area` VALUES (9726, '托喀依乡', 659003, 659002, '阿拉尔市', 0);
INSERT INTO `china_area` VALUES (9727, '北屯市', 659005, 650000, '新疆维吾尔自治区', 0);
INSERT INTO `china_area` VALUES (9728, '北屯镇', 659006, 659005, '北屯市', 0);
INSERT INTO `china_area` VALUES (9729, '可克达拉市', 659008, 650000, '新疆维吾尔自治区', 0);
INSERT INTO `china_area` VALUES (9730, '昆玉市', 659009, 650000, '新疆维吾尔自治区', 0);
INSERT INTO `china_area` VALUES (9731, '石河子市', 659001, 650000, '新疆维吾尔自治区', 0);
INSERT INTO `china_area` VALUES (9732, '向阳街道', 659002, 659001, '石河子市', 0);
INSERT INTO `china_area` VALUES (9733, '老街街道', 659003, 659001, '石河子市', 0);
INSERT INTO `china_area` VALUES (9734, '东城街道', 659004, 659001, '石河子市', 0);
INSERT INTO `china_area` VALUES (9735, '新城镇', 659005, 659001, '石河子市', 0);
INSERT INTO `china_area` VALUES (9736, '红山街道', 659006, 659001, '石河子市', 0);
INSERT INTO `china_area` VALUES (9737, '北泉镇', 659007, 659001, '石河子市', 0);
INSERT INTO `china_area` VALUES (9738, '石河子镇', 659008, 659001, '石河子市', 0);
INSERT INTO `china_area` VALUES (9739, '双河市', 659007, 650000, '新疆维吾尔自治区', 0);
INSERT INTO `china_area` VALUES (9740, '铁门关市', 659006, 650000, '新疆维吾尔自治区', 0);
INSERT INTO `china_area` VALUES (9741, '图木舒克市', 659003, 650000, '新疆维吾尔自治区', 0);
INSERT INTO `china_area` VALUES (9742, '四十九团九连', 659004, 659003, '图木舒克市', 0);
INSERT INTO `china_area` VALUES (9743, '五家渠市', 659004, 650000, '新疆维吾尔自治区', 0);
INSERT INTO `china_area` VALUES (9744, '梧桐镇', 659005, 659004, '五家渠市', 0);
INSERT INTO `china_area` VALUES (9745, '天津', 120000, 0, '中国', 0);
INSERT INTO `china_area` VALUES (9746, '天津城区', 120100, 120000, '天津市', 0);
INSERT INTO `china_area` VALUES (9747, '北辰区', 120113, 120100, '天津城区', 0);
INSERT INTO `china_area` VALUES (9748, '宝坻区', 120115, 120100, '天津城区', 0);
INSERT INTO `china_area` VALUES (9749, '滨海新区', 120116, 120100, '天津城区', 0);
INSERT INTO `china_area` VALUES (9750, '东丽区', 120110, 120100, '天津城区', 0);
INSERT INTO `china_area` VALUES (9751, '河北区', 120105, 120100, '天津城区', 0);
INSERT INTO `china_area` VALUES (9752, '河东区', 120102, 120100, '天津城区', 0);
INSERT INTO `china_area` VALUES (9753, '河西区', 120103, 120100, '天津城区', 0);
INSERT INTO `china_area` VALUES (9754, '和平区', 120101, 120100, '天津城区', 0);
INSERT INTO `china_area` VALUES (9755, '红桥区', 120106, 120100, '天津城区', 0);
INSERT INTO `china_area` VALUES (9756, '蓟州区', 120119, 120100, '天津城区', 0);
INSERT INTO `china_area` VALUES (9757, '静海区', 120118, 120100, '天津城区', 0);
INSERT INTO `china_area` VALUES (9758, '津南区', 120112, 120100, '天津城区', 0);
INSERT INTO `china_area` VALUES (9759, '南开区', 120104, 120100, '天津城区', 0);
INSERT INTO `china_area` VALUES (9760, '宁河区', 120117, 120100, '天津城区', 0);
INSERT INTO `china_area` VALUES (9761, '武清区', 120114, 120100, '天津城区', 0);
INSERT INTO `china_area` VALUES (9762, '西青区', 120111, 120100, '天津城区', 0);
INSERT INTO `china_area` VALUES (9763, '青海省', 630000, 0, '中国', 0);
INSERT INTO `china_area` VALUES (9764, '果洛藏族自治州', 632600, 630000, '青海省', 0);
INSERT INTO `china_area` VALUES (9765, '班玛县', 632622, 632600, '果洛藏族自治州', 0);
INSERT INTO `china_area` VALUES (9766, '达日县', 632624, 632600, '果洛藏族自治州', 0);
INSERT INTO `china_area` VALUES (9767, '甘德县', 632623, 632600, '果洛藏族自治州', 0);
INSERT INTO `china_area` VALUES (9768, '久治县', 632625, 632600, '果洛藏族自治州', 0);
INSERT INTO `china_area` VALUES (9769, '玛沁县', 632621, 632600, '果洛藏族自治州', 0);
INSERT INTO `china_area` VALUES (9770, '玛多县', 632626, 632600, '果洛藏族自治州', 0);
INSERT INTO `china_area` VALUES (9771, '海东市', 630200, 630000, '青海省', 0);
INSERT INTO `china_area` VALUES (9772, '互助土族自治县', 630223, 630200, '海东市', 0);
INSERT INTO `china_area` VALUES (9773, '化隆回族自治县', 630224, 630200, '海东市', 0);
INSERT INTO `china_area` VALUES (9774, '乐都区', 630202, 630200, '海东市', 0);
INSERT INTO `china_area` VALUES (9775, '民和回族土族自治县', 630222, 630200, '海东市', 0);
INSERT INTO `china_area` VALUES (9776, '平安区', 630203, 630200, '海东市', 0);
INSERT INTO `china_area` VALUES (9777, '循化撒拉族自治县', 630225, 630200, '海东市', 0);
INSERT INTO `china_area` VALUES (9778, '海南藏族自治州', 632500, 630000, '青海省', 0);
INSERT INTO `china_area` VALUES (9779, '贵德县', 632523, 632500, '海南藏族自治州', 0);
INSERT INTO `china_area` VALUES (9780, '贵南县', 632525, 632500, '海南藏族自治州', 0);
INSERT INTO `china_area` VALUES (9781, '共和县', 632521, 632500, '海南藏族自治州', 0);
INSERT INTO `china_area` VALUES (9782, '同德县', 632522, 632500, '海南藏族自治州', 0);
INSERT INTO `china_area` VALUES (9783, '兴海县', 632524, 632500, '海南藏族自治州', 0);
INSERT INTO `china_area` VALUES (9784, '海北藏族自治州', 632200, 630000, '青海省', 0);
INSERT INTO `china_area` VALUES (9785, '刚察县', 632224, 632200, '海北藏族自治州', 0);
INSERT INTO `china_area` VALUES (9786, '海晏县', 632223, 632200, '海北藏族自治州', 0);
INSERT INTO `china_area` VALUES (9787, '门源回族自治县', 632221, 632200, '海北藏族自治州', 0);
INSERT INTO `china_area` VALUES (9788, '祁连县', 632222, 632200, '海北藏族自治州', 0);
INSERT INTO `china_area` VALUES (9789, '海西蒙古族藏族自治州', 632800, 630000, '青海省', 0);
INSERT INTO `china_area` VALUES (9790, '德令哈市', 632802, 632800, '海西蒙古族藏族自治州', 0);
INSERT INTO `china_area` VALUES (9791, '都兰县', 632822, 632800, '海西蒙古族藏族自治州', 0);
INSERT INTO `china_area` VALUES (9792, '格尔木市', 632801, 632800, '海西蒙古族藏族自治州', 0);
INSERT INTO `china_area` VALUES (9793, '海西蒙古族藏族自治州直辖', 632825, 632800, '海西蒙古族藏族自治州', 0);
INSERT INTO `china_area` VALUES (9794, '天峻县', 632823, 632800, '海西蒙古族藏族自治州', 0);
INSERT INTO `china_area` VALUES (9795, '乌兰县', 632821, 632800, '海西蒙古族藏族自治州', 0);
INSERT INTO `china_area` VALUES (9796, '黄南藏族自治州', 632300, 630000, '青海省', 0);
INSERT INTO `china_area` VALUES (9797, '河南蒙古族自治县', 632324, 632300, '黄南藏族自治州', 0);
INSERT INTO `china_area` VALUES (9798, '尖扎县', 632322, 632300, '黄南藏族自治州', 0);
INSERT INTO `china_area` VALUES (9799, '同仁县', 632321, 632300, '黄南藏族自治州', 0);
INSERT INTO `china_area` VALUES (9800, '泽库县', 632323, 632300, '黄南藏族自治州', 0);
INSERT INTO `china_area` VALUES (9801, '西宁市', 630100, 630000, '青海省', 0);
INSERT INTO `china_area` VALUES (9802, '城北区', 630105, 630100, '西宁市', 0);
INSERT INTO `china_area` VALUES (9803, '城中区', 630103, 630100, '西宁市', 0);
INSERT INTO `china_area` VALUES (9804, '城东区', 630102, 630100, '西宁市', 0);
INSERT INTO `china_area` VALUES (9805, '大通回族土族自治县', 630121, 630100, '西宁市', 0);
INSERT INTO `china_area` VALUES (9806, '城西区', 630104, 630100, '西宁市', 0);
INSERT INTO `china_area` VALUES (9807, '湟中县', 630122, 630100, '西宁市', 0);
INSERT INTO `china_area` VALUES (9808, '湟源县', 630123, 630100, '西宁市', 0);
INSERT INTO `china_area` VALUES (9809, '玉树藏族自治州', 632700, 630000, '青海省', 0);
INSERT INTO `china_area` VALUES (9810, '称多县', 632723, 632700, '玉树藏族自治州', 0);
INSERT INTO `china_area` VALUES (9811, '囊谦县', 632725, 632700, '玉树藏族自治州', 0);
INSERT INTO `china_area` VALUES (9812, '曲麻莱县', 632726, 632700, '玉树藏族自治州', 0);
INSERT INTO `china_area` VALUES (9813, '玉树市', 632701, 632700, '玉树藏族自治州', 0);
INSERT INTO `china_area` VALUES (9814, '杂多县', 632722, 632700, '玉树藏族自治州', 0);
INSERT INTO `china_area` VALUES (9815, '治多县', 632724, 632700, '玉树藏族自治州', 0);
INSERT INTO `china_area` VALUES (9816, '陕西省', 610000, 0, '中国', 0);
INSERT INTO `china_area` VALUES (9817, '安康市', 610900, 610000, '陕西省', 0);
INSERT INTO `china_area` VALUES (9818, '白河县', 610929, 610900, '安康市', 0);
INSERT INTO `china_area` VALUES (9819, '汉阴县', 610921, 610900, '安康市', 0);
INSERT INTO `china_area` VALUES (9820, '汉滨区', 610902, 610900, '安康市', 0);
INSERT INTO `china_area` VALUES (9821, '岚皋县', 610925, 610900, '安康市', 0);
INSERT INTO `china_area` VALUES (9822, '宁陕县', 610923, 610900, '安康市', 0);
INSERT INTO `china_area` VALUES (9823, '平利县', 610926, 610900, '安康市', 0);
INSERT INTO `china_area` VALUES (9824, '石泉县', 610922, 610900, '安康市', 0);
INSERT INTO `china_area` VALUES (9825, '旬阳县', 610928, 610900, '安康市', 0);
INSERT INTO `china_area` VALUES (9826, '镇坪县', 610927, 610900, '安康市', 0);
INSERT INTO `china_area` VALUES (9827, '紫阳县', 610924, 610900, '安康市', 0);
INSERT INTO `china_area` VALUES (9828, '宝鸡市', 610300, 610000, '陕西省', 0);
INSERT INTO `china_area` VALUES (9829, '陈仓区', 610304, 610300, '宝鸡市', 0);
INSERT INTO `china_area` VALUES (9830, '凤县', 610330, 610300, '宝鸡市', 0);
INSERT INTO `china_area` VALUES (9831, '凤翔县', 610322, 610300, '宝鸡市', 0);
INSERT INTO `china_area` VALUES (9832, '扶风县', 610324, 610300, '宝鸡市', 0);
INSERT INTO `china_area` VALUES (9833, '金台区', 610303, 610300, '宝鸡市', 0);
INSERT INTO `china_area` VALUES (9834, '麟游县', 610329, 610300, '宝鸡市', 0);
INSERT INTO `china_area` VALUES (9835, '陇县', 610327, 610300, '宝鸡市', 0);
INSERT INTO `china_area` VALUES (9836, '眉县', 610326, 610300, '宝鸡市', 0);
INSERT INTO `china_area` VALUES (9837, '岐山县', 610323, 610300, '宝鸡市', 0);
INSERT INTO `china_area` VALUES (9838, '千阳县', 610328, 610300, '宝鸡市', 0);
INSERT INTO `china_area` VALUES (9839, '太白县', 610331, 610300, '宝鸡市', 0);
INSERT INTO `china_area` VALUES (9840, '渭滨区', 610302, 610300, '宝鸡市', 0);
INSERT INTO `china_area` VALUES (9841, '汉中市', 610700, 610000, '陕西省', 0);
INSERT INTO `china_area` VALUES (9842, '城固县', 610722, 610700, '汉中市', 0);
INSERT INTO `china_area` VALUES (9843, '略阳县', 610727, 610700, '汉中市', 0);
INSERT INTO `china_area` VALUES (9844, '佛坪县', 610730, 610700, '汉中市', 0);
INSERT INTO `china_area` VALUES (9845, '汉台区', 610702, 610700, '汉中市', 0);
INSERT INTO `china_area` VALUES (9846, '留坝县', 610729, 610700, '汉中市', 0);
INSERT INTO `china_area` VALUES (9847, '勉县', 610725, 610700, '汉中市', 0);
INSERT INTO `china_area` VALUES (9848, '南郑县', 610721, 610700, '汉中市', 0);
INSERT INTO `china_area` VALUES (9849, '宁强县', 610726, 610700, '汉中市', 0);
INSERT INTO `china_area` VALUES (9850, '西乡县', 610724, 610700, '汉中市', 0);
INSERT INTO `china_area` VALUES (9851, '洋县', 610723, 610700, '汉中市', 0);
INSERT INTO `china_area` VALUES (9852, '镇巴县', 610728, 610700, '汉中市', 0);
INSERT INTO `china_area` VALUES (9853, '商洛市', 611000, 610000, '陕西省', 0);
INSERT INTO `china_area` VALUES (9854, '丹凤县', 611022, 611000, '商洛市', 0);
INSERT INTO `china_area` VALUES (9855, '洛南县', 611021, 611000, '商洛市', 0);
INSERT INTO `china_area` VALUES (9856, '山阳县', 611024, 611000, '商洛市', 0);
INSERT INTO `china_area` VALUES (9857, '商南县', 611023, 611000, '商洛市', 0);
INSERT INTO `china_area` VALUES (9858, '商州区', 611002, 611000, '商洛市', 0);
INSERT INTO `china_area` VALUES (9859, '柞水县', 611026, 611000, '商洛市', 0);
INSERT INTO `china_area` VALUES (9860, '镇安县', 611025, 611000, '商洛市', 0);
INSERT INTO `china_area` VALUES (9861, '西安市', 610100, 610000, '陕西省', 0);
INSERT INTO `china_area` VALUES (9862, '灞桥区', 610111, 610100, '西安市', 0);
INSERT INTO `china_area` VALUES (9863, '碑林区', 610103, 610100, '西安市', 0);
INSERT INTO `china_area` VALUES (9864, '高陵区', 610117, 610100, '西安市', 0);
INSERT INTO `china_area` VALUES (9865, '鄠邑区', 610118, 610100, '西安市', 0);
INSERT INTO `china_area` VALUES (9866, '蓝田县', 610122, 610100, '西安市', 0);
INSERT INTO `china_area` VALUES (9867, '莲湖区', 610104, 610100, '西安市', 0);
INSERT INTO `china_area` VALUES (9868, '临潼区', 610115, 610100, '西安市', 0);
INSERT INTO `china_area` VALUES (9869, '未央区', 610112, 610100, '西安市', 0);
INSERT INTO `china_area` VALUES (9870, '新城区', 610102, 610100, '西安市', 0);
INSERT INTO `china_area` VALUES (9871, '雁塔区', 610113, 610100, '西安市', 0);
INSERT INTO `china_area` VALUES (9872, '阎良区', 610114, 610100, '西安市', 0);
INSERT INTO `china_area` VALUES (9873, '长安区', 610116, 610100, '西安市', 0);
INSERT INTO `china_area` VALUES (9874, '周至县', 610124, 610100, '西安市', 0);
INSERT INTO `china_area` VALUES (9875, '铜川市', 610200, 610000, '陕西省', 0);
INSERT INTO `china_area` VALUES (9876, '王益区', 610202, 610200, '铜川市', 0);
INSERT INTO `china_area` VALUES (9877, '耀州区', 610204, 610200, '铜川市', 0);
INSERT INTO `china_area` VALUES (9878, '宜君县', 610222, 610200, '铜川市', 0);
INSERT INTO `china_area` VALUES (9879, '印台区', 610203, 610200, '铜川市', 0);
INSERT INTO `china_area` VALUES (9880, '咸阳市', 610400, 610000, '陕西省', 0);
INSERT INTO `china_area` VALUES (9881, '彬县', 610427, 610400, '咸阳市', 0);
INSERT INTO `china_area` VALUES (9882, '淳化县', 610430, 610400, '咸阳市', 0);
INSERT INTO `china_area` VALUES (9883, '乾县', 610424, 610400, '咸阳市', 0);
INSERT INTO `china_area` VALUES (9884, '泾阳县', 610423, 610400, '咸阳市', 0);
INSERT INTO `china_area` VALUES (9885, '礼泉县', 610425, 610400, '咸阳市', 0);
INSERT INTO `china_area` VALUES (9886, '秦都区', 610402, 610400, '咸阳市', 0);
INSERT INTO `china_area` VALUES (9887, '三原县', 610422, 610400, '咸阳市', 0);
INSERT INTO `china_area` VALUES (9888, '渭城区', 610404, 610400, '咸阳市', 0);
INSERT INTO `china_area` VALUES (9889, '武功县', 610431, 610400, '咸阳市', 0);
INSERT INTO `china_area` VALUES (9890, '兴平市', 610481, 610400, '咸阳市', 0);
INSERT INTO `china_area` VALUES (9891, '旬邑县', 610429, 610400, '咸阳市', 0);
INSERT INTO `china_area` VALUES (9892, '杨陵区', 610403, 610400, '咸阳市', 0);
INSERT INTO `china_area` VALUES (9893, '永寿县', 610426, 610400, '咸阳市', 0);
INSERT INTO `china_area` VALUES (9894, '长武县', 610428, 610400, '咸阳市', 0);
INSERT INTO `china_area` VALUES (9895, '渭南市', 610500, 610000, '陕西省', 0);
INSERT INTO `china_area` VALUES (9896, '白水县', 610527, 610500, '渭南市', 0);
INSERT INTO `china_area` VALUES (9897, '澄城县', 610525, 610500, '渭南市', 0);
INSERT INTO `china_area` VALUES (9898, '大荔县', 610523, 610500, '渭南市', 0);
INSERT INTO `china_area` VALUES (9899, '富平县', 610528, 610500, '渭南市', 0);
INSERT INTO `china_area` VALUES (9900, '合阳县', 610524, 610500, '渭南市', 0);
INSERT INTO `china_area` VALUES (9901, '韩城市', 610581, 610500, '渭南市', 0);
INSERT INTO `china_area` VALUES (9902, '华州区', 610503, 610500, '渭南市', 0);
INSERT INTO `china_area` VALUES (9903, '华阴市', 610582, 610500, '渭南市', 0);
INSERT INTO `china_area` VALUES (9904, '临渭区', 610502, 610500, '渭南市', 0);
INSERT INTO `china_area` VALUES (9905, '蒲城县', 610526, 610500, '渭南市', 0);
INSERT INTO `china_area` VALUES (9906, '潼关县', 610522, 610500, '渭南市', 0);
INSERT INTO `china_area` VALUES (9907, '延安市', 610600, 610000, '陕西省', 0);
INSERT INTO `china_area` VALUES (9908, '安塞区', 610603, 610600, '延安市', 0);
INSERT INTO `china_area` VALUES (9909, '宝塔区', 610602, 610600, '延安市', 0);
INSERT INTO `china_area` VALUES (9910, '富县', 610628, 610600, '延安市', 0);
INSERT INTO `china_area` VALUES (9911, '甘泉县', 610627, 610600, '延安市', 0);
INSERT INTO `china_area` VALUES (9912, '黄龙县', 610631, 610600, '延安市', 0);
INSERT INTO `china_area` VALUES (9913, '黄陵县', 610632, 610600, '延安市', 0);
INSERT INTO `china_area` VALUES (9914, '洛川县', 610629, 610600, '延安市', 0);
INSERT INTO `china_area` VALUES (9915, '吴起县', 610626, 610600, '延安市', 0);
INSERT INTO `china_area` VALUES (9916, '延川县', 610622, 610600, '延安市', 0);
INSERT INTO `china_area` VALUES (9917, '延长县', 610621, 610600, '延安市', 0);
INSERT INTO `china_area` VALUES (9918, '宜川县', 610630, 610600, '延安市', 0);
INSERT INTO `china_area` VALUES (9919, '志丹县', 610625, 610600, '延安市', 0);
INSERT INTO `china_area` VALUES (9920, '子长县', 610623, 610600, '延安市', 0);
INSERT INTO `china_area` VALUES (9921, '榆林市', 610800, 610000, '陕西省', 0);
INSERT INTO `china_area` VALUES (9922, '定边县', 610825, 610800, '榆林市', 0);
INSERT INTO `china_area` VALUES (9923, '府谷县', 610822, 610800, '榆林市', 0);
INSERT INTO `china_area` VALUES (9924, '横山区', 610803, 610800, '榆林市', 0);
INSERT INTO `china_area` VALUES (9925, '佳县', 610828, 610800, '榆林市', 0);
INSERT INTO `china_area` VALUES (9926, '靖边县', 610824, 610800, '榆林市', 0);
INSERT INTO `china_area` VALUES (9927, '米脂县', 610827, 610800, '榆林市', 0);
INSERT INTO `china_area` VALUES (9928, '清涧县', 610830, 610800, '榆林市', 0);
INSERT INTO `china_area` VALUES (9929, '神木市', 610881, 610800, '榆林市', 0);
INSERT INTO `china_area` VALUES (9930, '绥德县', 610826, 610800, '榆林市', 0);
INSERT INTO `china_area` VALUES (9931, '吴堡县', 610829, 610800, '榆林市', 0);
INSERT INTO `china_area` VALUES (9932, '榆阳区', 610802, 610800, '榆林市', 0);
INSERT INTO `china_area` VALUES (9933, '子洲县', 610831, 610800, '榆林市', 0);
INSERT INTO `china_area` VALUES (9934, '西藏自治区', 540000, 0, '中国', 0);
INSERT INTO `china_area` VALUES (9935, '阿里地区', 542500, 540000, '西藏自治区', 0);
INSERT INTO `china_area` VALUES (9936, '措勤县', 542527, 542500, '阿里地区', 0);
INSERT INTO `china_area` VALUES (9937, '噶尔县', 542523, 542500, '阿里地区', 0);
INSERT INTO `china_area` VALUES (9938, '改则县', 542526, 542500, '阿里地区', 0);
INSERT INTO `china_area` VALUES (9939, '革吉县', 542525, 542500, '阿里地区', 0);
INSERT INTO `china_area` VALUES (9940, '普兰县', 542521, 542500, '阿里地区', 0);
INSERT INTO `china_area` VALUES (9941, '日土县', 542524, 542500, '阿里地区', 0);
INSERT INTO `china_area` VALUES (9942, '札达县', 542522, 542500, '阿里地区', 0);
INSERT INTO `china_area` VALUES (9943, '昌都市', 540300, 540000, '西藏自治区', 0);
INSERT INTO `china_area` VALUES (9944, '八宿县', 540326, 540300, '昌都市', 0);
INSERT INTO `china_area` VALUES (9945, '边坝县', 540330, 540300, '昌都市', 0);
INSERT INTO `china_area` VALUES (9946, '察雅县', 540325, 540300, '昌都市', 0);
INSERT INTO `china_area` VALUES (9947, '丁青县', 540324, 540300, '昌都市', 0);
INSERT INTO `china_area` VALUES (9948, '贡觉县', 540322, 540300, '昌都市', 0);
INSERT INTO `china_area` VALUES (9949, '江达县', 540321, 540300, '昌都市', 0);
INSERT INTO `china_area` VALUES (9950, '卡若区', 540302, 540300, '昌都市', 0);
INSERT INTO `china_area` VALUES (9951, '类乌齐县', 540323, 540300, '昌都市', 0);
INSERT INTO `china_area` VALUES (9952, '洛隆县', 540329, 540300, '昌都市', 0);
INSERT INTO `china_area` VALUES (9953, '芒康县', 540328, 540300, '昌都市', 0);
INSERT INTO `china_area` VALUES (9954, '左贡县', 540327, 540300, '昌都市', 0);
INSERT INTO `china_area` VALUES (9955, '拉萨市', 540100, 540000, '西藏自治区', 0);
INSERT INTO `china_area` VALUES (9956, '达孜县', 540126, 540100, '拉萨市', 0);
INSERT INTO `china_area` VALUES (9957, '城关区', 540102, 540100, '拉萨市', 0);
INSERT INTO `china_area` VALUES (9958, '当雄县', 540122, 540100, '拉萨市', 0);
INSERT INTO `china_area` VALUES (9959, '堆龙德庆区', 540103, 540100, '拉萨市', 0);
INSERT INTO `china_area` VALUES (9960, '林周县', 540121, 540100, '拉萨市', 0);
INSERT INTO `china_area` VALUES (9961, '墨竹工卡县', 540127, 540100, '拉萨市', 0);
INSERT INTO `china_area` VALUES (9962, '尼木县', 540123, 540100, '拉萨市', 0);
INSERT INTO `china_area` VALUES (9963, '曲水县', 540124, 540100, '拉萨市', 0);
INSERT INTO `china_area` VALUES (9964, '林芝市', 540400, 540000, '西藏自治区', 0);
INSERT INTO `china_area` VALUES (9965, '巴宜区', 540402, 540400, '林芝市', 0);
INSERT INTO `china_area` VALUES (9966, '察隅县', 540425, 540400, '林芝市', 0);
INSERT INTO `china_area` VALUES (9967, '波密县', 540424, 540400, '林芝市', 0);
INSERT INTO `china_area` VALUES (9968, '工布江达县', 540421, 540400, '林芝市', 0);
INSERT INTO `china_area` VALUES (9969, '朗县', 540426, 540400, '林芝市', 0);
INSERT INTO `china_area` VALUES (9970, '米林县', 540422, 540400, '林芝市', 0);
INSERT INTO `china_area` VALUES (9971, '墨脱县', 540423, 540400, '林芝市', 0);
INSERT INTO `china_area` VALUES (9972, '那曲地区', 542400, 540000, '西藏自治区', 0);
INSERT INTO `china_area` VALUES (9973, '安多县', 542425, 542400, '那曲地区', 0);
INSERT INTO `china_area` VALUES (9974, '巴青县', 542429, 542400, '那曲地区', 0);
INSERT INTO `china_area` VALUES (9975, '班戈县', 542428, 542400, '那曲地区', 0);
INSERT INTO `china_area` VALUES (9976, '比如县', 542423, 542400, '那曲地区', 0);
INSERT INTO `china_area` VALUES (9977, '嘉黎县', 542422, 542400, '那曲地区', 0);
INSERT INTO `china_area` VALUES (9978, '那曲县', 542421, 542400, '那曲地区', 0);
INSERT INTO `china_area` VALUES (9979, '尼玛县', 542430, 542400, '那曲地区', 0);
INSERT INTO `china_area` VALUES (9980, '聂荣县', 542424, 542400, '那曲地区', 0);
INSERT INTO `china_area` VALUES (9981, '申扎县', 542426, 542400, '那曲地区', 0);
INSERT INTO `china_area` VALUES (9982, '双湖县', 542431, 542400, '那曲地区', 0);
INSERT INTO `china_area` VALUES (9983, '索县', 542427, 542400, '那曲地区', 0);
INSERT INTO `china_area` VALUES (9984, '山南市', 540500, 540000, '西藏自治区', 0);
INSERT INTO `china_area` VALUES (9985, '措美县', 540526, 540500, '山南市', 0);
INSERT INTO `china_area` VALUES (9986, '错那县', 540530, 540500, '山南市', 0);
INSERT INTO `china_area` VALUES (9987, '贡嘎县', 540522, 540500, '山南市', 0);
INSERT INTO `china_area` VALUES (9988, '加查县', 540528, 540500, '山南市', 0);
INSERT INTO `china_area` VALUES (9989, '浪卡子县', 540531, 540500, '山南市', 0);
INSERT INTO `china_area` VALUES (9990, '隆子县', 540529, 540500, '山南市', 0);
INSERT INTO `china_area` VALUES (9991, '洛扎县', 540527, 540500, '山南市', 0);
INSERT INTO `china_area` VALUES (9992, '乃东区', 540502, 540500, '山南市', 0);
INSERT INTO `china_area` VALUES (9993, '曲松县', 540525, 540500, '山南市', 0);
INSERT INTO `china_area` VALUES (9994, '琼结县', 540524, 540500, '山南市', 0);
INSERT INTO `china_area` VALUES (9995, '桑日县', 540523, 540500, '山南市', 0);
INSERT INTO `china_area` VALUES (9996, '扎囊县', 540521, 540500, '山南市', 0);
INSERT INTO `china_area` VALUES (9997, '日喀则市', 540200, 540000, '西藏自治区', 0);
INSERT INTO `china_area` VALUES (9998, '白朗县', 540228, 540200, '日喀则市', 0);
INSERT INTO `china_area` VALUES (9999, '昂仁县', 540226, 540200, '日喀则市', 0);
INSERT INTO `china_area` VALUES (10000, '定结县', 540231, 540200, '日喀则市', 0);
INSERT INTO `china_area` VALUES (10001, '定日县', 540223, 540200, '日喀则市', 0);
INSERT INTO `china_area` VALUES (10002, '岗巴县', 540237, 540200, '日喀则市', 0);
INSERT INTO `china_area` VALUES (10003, '吉隆县', 540234, 540200, '日喀则市', 0);
INSERT INTO `china_area` VALUES (10004, '江孜县', 540222, 540200, '日喀则市', 0);
INSERT INTO `china_area` VALUES (10005, '康马县', 540230, 540200, '日喀则市', 0);
INSERT INTO `china_area` VALUES (10006, '拉孜县', 540225, 540200, '日喀则市', 0);
INSERT INTO `china_area` VALUES (10007, '南木林县', 540221, 540200, '日喀则市', 0);
INSERT INTO `china_area` VALUES (10008, '聂拉木县', 540235, 540200, '日喀则市', 0);
INSERT INTO `china_area` VALUES (10009, '仁布县', 540229, 540200, '日喀则市', 0);
INSERT INTO `china_area` VALUES (10010, '萨迦县', 540224, 540200, '日喀则市', 0);
INSERT INTO `china_area` VALUES (10011, '萨嘎县', 540236, 540200, '日喀则市', 0);
INSERT INTO `china_area` VALUES (10012, '桑珠孜区', 540202, 540200, '日喀则市', 0);
INSERT INTO `china_area` VALUES (10013, '谢通门县', 540227, 540200, '日喀则市', 0);
INSERT INTO `china_area` VALUES (10014, '亚东县', 540233, 540200, '日喀则市', 0);
INSERT INTO `china_area` VALUES (10015, '仲巴县', 540232, 540200, '日喀则市', 0);
INSERT INTO `china_area` VALUES (10016, '四川省', 510000, 0, '中国', 0);
INSERT INTO `china_area` VALUES (10017, '阿坝藏族羌族自治州', 513200, 510000, '四川省', 0);
INSERT INTO `china_area` VALUES (10018, '阿坝县', 513231, 513200, '阿坝藏族羌族自治州', 0);
INSERT INTO `china_area` VALUES (10019, '红原县', 513233, 513200, '阿坝藏族羌族自治州', 0);
INSERT INTO `china_area` VALUES (10020, '黑水县', 513228, 513200, '阿坝藏族羌族自治州', 0);
INSERT INTO `china_area` VALUES (10021, '金川县', 513226, 513200, '阿坝藏族羌族自治州', 0);
INSERT INTO `china_area` VALUES (10022, '九寨沟县', 513225, 513200, '阿坝藏族羌族自治州', 0);
INSERT INTO `china_area` VALUES (10023, '理县', 513222, 513200, '阿坝藏族羌族自治州', 0);
INSERT INTO `china_area` VALUES (10024, '茂县', 513223, 513200, '阿坝藏族羌族自治州', 0);
INSERT INTO `china_area` VALUES (10025, '马尔康市', 513201, 513200, '阿坝藏族羌族自治州', 0);
INSERT INTO `china_area` VALUES (10026, '壤塘县', 513230, 513200, '阿坝藏族羌族自治州', 0);
INSERT INTO `china_area` VALUES (10027, '若尔盖县', 513232, 513200, '阿坝藏族羌族自治州', 0);
INSERT INTO `china_area` VALUES (10028, '松潘县', 513224, 513200, '阿坝藏族羌族自治州', 0);
INSERT INTO `china_area` VALUES (10029, '汶川县', 513221, 513200, '阿坝藏族羌族自治州', 0);
INSERT INTO `china_area` VALUES (10030, '小金县', 513227, 513200, '阿坝藏族羌族自治州', 0);
INSERT INTO `china_area` VALUES (10031, '巴中市', 511900, 510000, '四川省', 0);
INSERT INTO `china_area` VALUES (10032, '巴州区', 511902, 511900, '巴中市', 0);
INSERT INTO `china_area` VALUES (10033, '恩阳区', 511903, 511900, '巴中市', 0);
INSERT INTO `china_area` VALUES (10034, '南江县', 511922, 511900, '巴中市', 0);
INSERT INTO `china_area` VALUES (10035, '平昌县', 511923, 511900, '巴中市', 0);
INSERT INTO `china_area` VALUES (10036, '通江县', 511921, 511900, '巴中市', 0);
INSERT INTO `china_area` VALUES (10037, '成都市', 510100, 510000, '四川省', 0);
INSERT INTO `china_area` VALUES (10038, '成华区', 510108, 510100, '成都市', 0);
INSERT INTO `china_area` VALUES (10039, '崇州市', 510184, 510100, '成都市', 0);
INSERT INTO `china_area` VALUES (10040, '大邑县', 510129, 510100, '成都市', 0);
INSERT INTO `china_area` VALUES (10041, '都江堰市', 510181, 510100, '成都市', 0);
INSERT INTO `china_area` VALUES (10042, '简阳市', 510185, 510100, '成都市', 0);
INSERT INTO `china_area` VALUES (10043, '金堂县', 510121, 510100, '成都市', 0);
INSERT INTO `china_area` VALUES (10044, '金牛区', 510106, 510100, '成都市', 0);
INSERT INTO `china_area` VALUES (10045, '锦江区', 510104, 510100, '成都市', 0);
INSERT INTO `china_area` VALUES (10046, '龙泉驿区', 510112, 510100, '成都市', 0);
INSERT INTO `china_area` VALUES (10047, '彭州市', 510182, 510100, '成都市', 0);
INSERT INTO `china_area` VALUES (10048, '郫都区', 510117, 510100, '成都市', 0);
INSERT INTO `china_area` VALUES (10049, '蒲江县', 510131, 510100, '成都市', 0);
INSERT INTO `china_area` VALUES (10050, '青白江区', 510113, 510100, '成都市', 0);
INSERT INTO `china_area` VALUES (10051, '青羊区', 510105, 510100, '成都市', 0);
INSERT INTO `china_area` VALUES (10052, '邛崃市', 510183, 510100, '成都市', 0);
INSERT INTO `china_area` VALUES (10053, '双流区', 510116, 510100, '成都市', 0);
INSERT INTO `china_area` VALUES (10054, '温江区', 510115, 510100, '成都市', 0);
INSERT INTO `china_area` VALUES (10055, '武侯区', 510107, 510100, '成都市', 0);
INSERT INTO `china_area` VALUES (10056, '新津县', 510132, 510100, '成都市', 0);
INSERT INTO `china_area` VALUES (10057, '新都区', 510114, 510100, '成都市', 0);
INSERT INTO `china_area` VALUES (10058, '达州市', 511700, 510000, '四川省', 0);
INSERT INTO `china_area` VALUES (10059, '达川区', 511703, 511700, '达州市', 0);
INSERT INTO `china_area` VALUES (10060, '大竹县', 511724, 511700, '达州市', 0);
INSERT INTO `china_area` VALUES (10061, '开江县', 511723, 511700, '达州市', 0);
INSERT INTO `china_area` VALUES (10062, '渠县', 511725, 511700, '达州市', 0);
INSERT INTO `china_area` VALUES (10063, '通川区', 511702, 511700, '达州市', 0);
INSERT INTO `china_area` VALUES (10064, '万源市', 511781, 511700, '达州市', 0);
INSERT INTO `china_area` VALUES (10065, '宣汉县', 511722, 511700, '达州市', 0);
INSERT INTO `china_area` VALUES (10066, '广元市', 510800, 510000, '四川省', 0);
INSERT INTO `china_area` VALUES (10067, '苍溪县', 510824, 510800, '广元市', 0);
INSERT INTO `china_area` VALUES (10068, '朝天区', 510812, 510800, '广元市', 0);
INSERT INTO `china_area` VALUES (10069, '剑阁县', 510823, 510800, '广元市', 0);
INSERT INTO `china_area` VALUES (10070, '利州区', 510802, 510800, '广元市', 0);
INSERT INTO `china_area` VALUES (10071, '青川县', 510822, 510800, '广元市', 0);
INSERT INTO `china_area` VALUES (10072, '旺苍县', 510821, 510800, '广元市', 0);
INSERT INTO `china_area` VALUES (10073, '昭化区', 510811, 510800, '广元市', 0);
INSERT INTO `china_area` VALUES (10074, '广安市', 511600, 510000, '四川省', 0);
INSERT INTO `china_area` VALUES (10075, '广安区', 511602, 511600, '广安市', 0);
INSERT INTO `china_area` VALUES (10076, '华蓥市', 511681, 511600, '广安市', 0);
INSERT INTO `china_area` VALUES (10077, '邻水县', 511623, 511600, '广安市', 0);
INSERT INTO `china_area` VALUES (10078, '前锋区', 511603, 511600, '广安市', 0);
INSERT INTO `china_area` VALUES (10079, '武胜县', 511622, 511600, '广安市', 0);
INSERT INTO `china_area` VALUES (10080, '岳池县', 511621, 511600, '广安市', 0);
INSERT INTO `china_area` VALUES (10081, '德阳市', 510600, 510000, '四川省', 0);
INSERT INTO `china_area` VALUES (10082, '广汉市', 510681, 510600, '德阳市', 0);
INSERT INTO `china_area` VALUES (10083, '旌阳区', 510603, 510600, '德阳市', 0);
INSERT INTO `china_area` VALUES (10084, '罗江县', 510626, 510600, '德阳市', 0);
INSERT INTO `china_area` VALUES (10085, '绵竹市', 510683, 510600, '德阳市', 0);
INSERT INTO `china_area` VALUES (10086, '什邡市', 510682, 510600, '德阳市', 0);
INSERT INTO `china_area` VALUES (10087, '中江县', 510623, 510600, '德阳市', 0);
INSERT INTO `china_area` VALUES (10088, '甘孜藏族自治州', 513300, 510000, '四川省', 0);
INSERT INTO `china_area` VALUES (10089, '巴塘县', 513335, 513300, '甘孜藏族自治州', 0);
INSERT INTO `china_area` VALUES (10090, '白玉县', 513331, 513300, '甘孜藏族自治州', 0);
INSERT INTO `china_area` VALUES (10091, '德格县', 513330, 513300, '甘孜藏族自治州', 0);
INSERT INTO `china_area` VALUES (10092, '道孚县', 513326, 513300, '甘孜藏族自治州', 0);
INSERT INTO `china_area` VALUES (10093, '丹巴县', 513323, 513300, '甘孜藏族自治州', 0);
INSERT INTO `china_area` VALUES (10094, '稻城县', 513337, 513300, '甘孜藏族自治州', 0);
INSERT INTO `china_area` VALUES (10095, '得荣县', 513338, 513300, '甘孜藏族自治州', 0);
INSERT INTO `china_area` VALUES (10096, '甘孜县', 513328, 513300, '甘孜藏族自治州', 0);
INSERT INTO `china_area` VALUES (10097, '九龙县', 513324, 513300, '甘孜藏族自治州', 0);
INSERT INTO `china_area` VALUES (10098, '康定市', 513301, 513300, '甘孜藏族自治州', 0);
INSERT INTO `china_area` VALUES (10099, '理塘县', 513334, 513300, '甘孜藏族自治州', 0);
INSERT INTO `china_area` VALUES (10100, '泸定县', 513322, 513300, '甘孜藏族自治州', 0);
INSERT INTO `china_area` VALUES (10101, '炉霍县', 513327, 513300, '甘孜藏族自治州', 0);
INSERT INTO `china_area` VALUES (10102, '色达县', 513333, 513300, '甘孜藏族自治州', 0);
INSERT INTO `china_area` VALUES (10103, '石渠县', 513332, 513300, '甘孜藏族自治州', 0);
INSERT INTO `china_area` VALUES (10104, '乡城县', 513336, 513300, '甘孜藏族自治州', 0);
INSERT INTO `china_area` VALUES (10105, '新龙县', 513329, 513300, '甘孜藏族自治州', 0);
INSERT INTO `china_area` VALUES (10106, '雅江县', 513325, 513300, '甘孜藏族自治州', 0);
INSERT INTO `china_area` VALUES (10107, '乐山市', 511100, 510000, '四川省', 0);
INSERT INTO `china_area` VALUES (10108, '峨眉山市', 511181, 511100, '乐山市', 0);
INSERT INTO `china_area` VALUES (10109, '峨边彝族自治县', 511132, 511100, '乐山市', 0);
INSERT INTO `china_area` VALUES (10110, '夹江县', 511126, 511100, '乐山市', 0);
INSERT INTO `china_area` VALUES (10111, '犍为县', 511123, 511100, '乐山市', 0);
INSERT INTO `china_area` VALUES (10112, '金口河区', 511113, 511100, '乐山市', 0);
INSERT INTO `china_area` VALUES (10113, '井研县', 511124, 511100, '乐山市', 0);
INSERT INTO `china_area` VALUES (10114, '马边彝族自治县', 511133, 511100, '乐山市', 0);
INSERT INTO `china_area` VALUES (10115, '沐川县', 511129, 511100, '乐山市', 0);
INSERT INTO `china_area` VALUES (10116, '沙湾区', 511111, 511100, '乐山市', 0);
INSERT INTO `china_area` VALUES (10117, '市中区', 511102, 511100, '乐山市', 0);
INSERT INTO `china_area` VALUES (10118, '五通桥区', 511112, 511100, '乐山市', 0);
INSERT INTO `china_area` VALUES (10119, '凉山彝族自治州', 513400, 510000, '四川省', 0);
INSERT INTO `china_area` VALUES (10120, '布拖县', 513429, 513400, '凉山彝族自治州', 0);
INSERT INTO `china_area` VALUES (10121, '德昌县', 513424, 513400, '凉山彝族自治州', 0);
INSERT INTO `china_area` VALUES (10122, '甘洛县', 513435, 513400, '凉山彝族自治州', 0);
INSERT INTO `china_area` VALUES (10123, '会东县', 513426, 513400, '凉山彝族自治州', 0);
INSERT INTO `china_area` VALUES (10124, '会理县', 513425, 513400, '凉山彝族自治州', 0);
INSERT INTO `china_area` VALUES (10125, '金阳县', 513430, 513400, '凉山彝族自治州', 0);
INSERT INTO `china_area` VALUES (10126, '雷波县', 513437, 513400, '凉山彝族自治州', 0);
INSERT INTO `china_area` VALUES (10127, '美姑县', 513436, 513400, '凉山彝族自治州', 0);
INSERT INTO `china_area` VALUES (10128, '冕宁县', 513433, 513400, '凉山彝族自治州', 0);
INSERT INTO `china_area` VALUES (10129, '木里藏族自治县', 513422, 513400, '凉山彝族自治州', 0);
INSERT INTO `china_area` VALUES (10130, '宁南县', 513427, 513400, '凉山彝族自治州', 0);
INSERT INTO `china_area` VALUES (10131, '普格县', 513428, 513400, '凉山彝族自治州', 0);
INSERT INTO `china_area` VALUES (10132, '西昌市', 513401, 513400, '凉山彝族自治州', 0);
INSERT INTO `china_area` VALUES (10133, '喜德县', 513432, 513400, '凉山彝族自治州', 0);
INSERT INTO `china_area` VALUES (10134, '盐源县', 513423, 513400, '凉山彝族自治州', 0);
INSERT INTO `china_area` VALUES (10135, '越西县', 513434, 513400, '凉山彝族自治州', 0);
INSERT INTO `china_area` VALUES (10136, '昭觉县', 513431, 513400, '凉山彝族自治州', 0);
INSERT INTO `china_area` VALUES (10137, '泸州市', 510500, 510000, '四川省', 0);
INSERT INTO `china_area` VALUES (10138, '古蔺县', 510525, 510500, '泸州市', 0);
INSERT INTO `china_area` VALUES (10139, '合江县', 510522, 510500, '泸州市', 0);
INSERT INTO `china_area` VALUES (10140, '江阳区', 510502, 510500, '泸州市', 0);
INSERT INTO `china_area` VALUES (10141, '龙马潭区', 510504, 510500, '泸州市', 0);
INSERT INTO `china_area` VALUES (10142, '泸县', 510521, 510500, '泸州市', 0);
INSERT INTO `china_area` VALUES (10143, '纳溪区', 510503, 510500, '泸州市', 0);
INSERT INTO `china_area` VALUES (10144, '叙永县', 510524, 510500, '泸州市', 0);
INSERT INTO `china_area` VALUES (10145, '眉山市', 511400, 510000, '四川省', 0);
INSERT INTO `china_area` VALUES (10146, '丹棱县', 511424, 511400, '眉山市', 0);
INSERT INTO `china_area` VALUES (10147, '东坡区', 511402, 511400, '眉山市', 0);
INSERT INTO `china_area` VALUES (10148, '洪雅县', 511423, 511400, '眉山市', 0);
INSERT INTO `china_area` VALUES (10149, '彭山区', 511403, 511400, '眉山市', 0);
INSERT INTO `china_area` VALUES (10150, '青神县', 511425, 511400, '眉山市', 0);
INSERT INTO `china_area` VALUES (10151, '仁寿县', 511421, 511400, '眉山市', 0);
INSERT INTO `china_area` VALUES (10152, '绵阳市', 510700, 510000, '四川省', 0);
INSERT INTO `china_area` VALUES (10153, '安州区', 510705, 510700, '绵阳市', 0);
INSERT INTO `china_area` VALUES (10154, '北川羌族自治县', 510726, 510700, '绵阳市', 0);
INSERT INTO `china_area` VALUES (10155, '涪城区', 510703, 510700, '绵阳市', 0);
INSERT INTO `china_area` VALUES (10156, '江油市', 510781, 510700, '绵阳市', 0);
INSERT INTO `china_area` VALUES (10157, '平武县', 510727, 510700, '绵阳市', 0);
INSERT INTO `china_area` VALUES (10158, '三台县', 510722, 510700, '绵阳市', 0);
INSERT INTO `china_area` VALUES (10159, '盐亭县', 510723, 510700, '绵阳市', 0);
INSERT INTO `china_area` VALUES (10160, '游仙区', 510704, 510700, '绵阳市', 0);
INSERT INTO `china_area` VALUES (10161, '梓潼县', 510725, 510700, '绵阳市', 0);
INSERT INTO `china_area` VALUES (10162, '南充市', 511300, 510000, '四川省', 0);
INSERT INTO `china_area` VALUES (10163, '高坪区', 511303, 511300, '南充市', 0);
INSERT INTO `china_area` VALUES (10164, '嘉陵区', 511304, 511300, '南充市', 0);
INSERT INTO `china_area` VALUES (10165, '阆中市', 511381, 511300, '南充市', 0);
INSERT INTO `china_area` VALUES (10166, '南部县', 511321, 511300, '南充市', 0);
INSERT INTO `china_area` VALUES (10167, '蓬安县', 511323, 511300, '南充市', 0);
INSERT INTO `china_area` VALUES (10168, '顺庆区', 511302, 511300, '南充市', 0);
INSERT INTO `china_area` VALUES (10169, '西充县', 511325, 511300, '南充市', 0);
INSERT INTO `china_area` VALUES (10170, '仪陇县', 511324, 511300, '南充市', 0);
INSERT INTO `china_area` VALUES (10171, '营山县', 511322, 511300, '南充市', 0);
INSERT INTO `china_area` VALUES (10172, '内江市', 511000, 510000, '四川省', 0);
INSERT INTO `china_area` VALUES (10173, '东兴区', 511011, 511000, '内江市', 0);
INSERT INTO `china_area` VALUES (10174, '隆昌市', 511028, 511000, '内江市', 0);
INSERT INTO `china_area` VALUES (10175, '市中区', 511002, 511000, '内江市', 0);
INSERT INTO `china_area` VALUES (10176, '威远县', 511024, 511000, '内江市', 0);
INSERT INTO `china_area` VALUES (10177, '资中县', 511025, 511000, '内江市', 0);
INSERT INTO `china_area` VALUES (10178, '攀枝花市', 510400, 510000, '四川省', 0);
INSERT INTO `china_area` VALUES (10179, '东区', 510402, 510400, '攀枝花市', 0);
INSERT INTO `china_area` VALUES (10180, '米易县', 510421, 510400, '攀枝花市', 0);
INSERT INTO `china_area` VALUES (10181, '仁和区', 510411, 510400, '攀枝花市', 0);
INSERT INTO `china_area` VALUES (10182, '西区', 510403, 510400, '攀枝花市', 0);
INSERT INTO `china_area` VALUES (10183, '盐边县', 510422, 510400, '攀枝花市', 0);
INSERT INTO `china_area` VALUES (10184, '遂宁市', 510900, 510000, '四川省', 0);
INSERT INTO `china_area` VALUES (10185, '安居区', 510904, 510900, '遂宁市', 0);
INSERT INTO `china_area` VALUES (10186, '大英县', 510923, 510900, '遂宁市', 0);
INSERT INTO `china_area` VALUES (10187, '船山区', 510903, 510900, '遂宁市', 0);
INSERT INTO `china_area` VALUES (10188, '蓬溪县', 510921, 510900, '遂宁市', 0);
INSERT INTO `china_area` VALUES (10189, '射洪县', 510922, 510900, '遂宁市', 0);
INSERT INTO `china_area` VALUES (10190, '雅安市', 511800, 510000, '四川省', 0);
INSERT INTO `china_area` VALUES (10191, '宝兴县', 511827, 511800, '雅安市', 0);
INSERT INTO `china_area` VALUES (10192, '汉源县', 511823, 511800, '雅安市', 0);
INSERT INTO `china_area` VALUES (10193, '芦山县', 511826, 511800, '雅安市', 0);
INSERT INTO `china_area` VALUES (10194, '名山区', 511803, 511800, '雅安市', 0);
INSERT INTO `china_area` VALUES (10195, '石棉县', 511824, 511800, '雅安市', 0);
INSERT INTO `china_area` VALUES (10196, '天全县', 511825, 511800, '雅安市', 0);
INSERT INTO `china_area` VALUES (10197, '荥经县', 511822, 511800, '雅安市', 0);
INSERT INTO `china_area` VALUES (10198, '雨城区', 511802, 511800, '雅安市', 0);
INSERT INTO `china_area` VALUES (10199, '宜宾市', 511500, 510000, '四川省', 0);
INSERT INTO `china_area` VALUES (10200, '翠屏区', 511502, 511500, '宜宾市', 0);
INSERT INTO `china_area` VALUES (10201, '高县', 511525, 511500, '宜宾市', 0);
INSERT INTO `china_area` VALUES (10202, '珙县', 511526, 511500, '宜宾市', 0);
INSERT INTO `china_area` VALUES (10203, '江安县', 511523, 511500, '宜宾市', 0);
INSERT INTO `china_area` VALUES (10204, '南溪区', 511503, 511500, '宜宾市', 0);
INSERT INTO `china_area` VALUES (10205, '屏山县', 511529, 511500, '宜宾市', 0);
INSERT INTO `china_area` VALUES (10206, '兴文县', 511528, 511500, '宜宾市', 0);
INSERT INTO `china_area` VALUES (10207, '宜宾县', 511521, 511500, '宜宾市', 0);
INSERT INTO `china_area` VALUES (10208, '筠连县', 511527, 511500, '宜宾市', 0);
INSERT INTO `china_area` VALUES (10209, '长宁县', 511524, 511500, '宜宾市', 0);
INSERT INTO `china_area` VALUES (10210, '资阳市', 512000, 510000, '四川省', 0);
INSERT INTO `china_area` VALUES (10211, '安岳县', 512021, 512000, '资阳市', 0);
INSERT INTO `china_area` VALUES (10212, '乐至县', 512022, 512000, '资阳市', 0);
INSERT INTO `china_area` VALUES (10213, '雁江区', 512002, 512000, '资阳市', 0);
INSERT INTO `china_area` VALUES (10214, '自贡市', 510300, 510000, '四川省', 0);
INSERT INTO `china_area` VALUES (10215, '大安区', 510304, 510300, '自贡市', 0);
INSERT INTO `china_area` VALUES (10216, '富顺县', 510322, 510300, '自贡市', 0);
INSERT INTO `china_area` VALUES (10217, '贡井区', 510303, 510300, '自贡市', 0);
INSERT INTO `china_area` VALUES (10218, '荣县', 510321, 510300, '自贡市', 0);
INSERT INTO `china_area` VALUES (10219, '沿滩区', 510311, 510300, '自贡市', 0);
INSERT INTO `china_area` VALUES (10220, '自流井区', 510302, 510300, '自贡市', 0);
INSERT INTO `china_area` VALUES (10221, '香港特别行政区', 810000, 0, '中国', 0);
INSERT INTO `china_area` VALUES (10222, '油尖旺区', 810005, 810000, '香港特别行政区', 0);
INSERT INTO `china_area` VALUES (10223, '湾仔区', 810002, 810000, '香港特别行政区', 0);
INSERT INTO `china_area` VALUES (10224, '北区', 810013, 810000, '香港特别行政区', 0);
INSERT INTO `china_area` VALUES (10225, '龙跃头乡', 810014, 810013, '北区', 0);
INSERT INTO `china_area` VALUES (10226, '大埔区', 810014, 810000, '香港特别行政区', 0);
INSERT INTO `china_area` VALUES (10227, '东区', 810003, 810000, '香港特别行政区', 0);
INSERT INTO `china_area` VALUES (10228, '观塘区', 810009, 810000, '香港特别行政区', 0);
INSERT INTO `china_area` VALUES (10229, '黄大仙区', 810008, 810000, '香港特别行政区', 0);
INSERT INTO `china_area` VALUES (10230, '九龙城区', 810007, 810000, '香港特别行政区', 0);
INSERT INTO `china_area` VALUES (10231, '葵青区', 810017, 810000, '香港特别行政区', 0);
INSERT INTO `china_area` VALUES (10232, '离岛区', 810018, 810000, '香港特别行政区', 0);
INSERT INTO `china_area` VALUES (10233, '梅窝乡', 810019, 810018, '离岛区', 0);
INSERT INTO `china_area` VALUES (10234, '南区', 810004, 810000, '香港特别行政区', 0);
INSERT INTO `china_area` VALUES (10235, '荃湾区', 810010, 810000, '香港特别行政区', 0);
INSERT INTO `china_area` VALUES (10236, '沙田区', 810016, 810000, '香港特别行政区', 0);
INSERT INTO `china_area` VALUES (10237, '深水埗区', 810006, 810000, '香港特别行政区', 0);
INSERT INTO `china_area` VALUES (10238, '屯门区', 810011, 810000, '香港特别行政区', 0);
INSERT INTO `china_area` VALUES (10239, '西贡区', 810015, 810000, '香港特别行政区', 0);
INSERT INTO `china_area` VALUES (10240, '元朗区', 810012, 810000, '香港特别行政区', 0);
INSERT INTO `china_area` VALUES (10241, '逢吉乡', 810013, 810012, '元朗区', 0);
INSERT INTO `china_area` VALUES (10242, '中西区', 810001, 810000, '香港特别行政区', 0);
INSERT INTO `china_area` VALUES (10243, '云南省', 530000, 0, '中国', 0);
INSERT INTO `china_area` VALUES (10244, '保山市', 530500, 530000, '云南省', 0);
INSERT INTO `china_area` VALUES (10245, '昌宁县', 530524, 530500, '保山市', 0);
INSERT INTO `china_area` VALUES (10246, '隆阳区', 530502, 530500, '保山市', 0);
INSERT INTO `china_area` VALUES (10247, '龙陵县', 530523, 530500, '保山市', 0);
INSERT INTO `china_area` VALUES (10248, '施甸县', 530521, 530500, '保山市', 0);
INSERT INTO `china_area` VALUES (10249, '腾冲市', 530581, 530500, '保山市', 0);
INSERT INTO `china_area` VALUES (10250, '楚雄彝族自治州', 532300, 530000, '云南省', 0);
INSERT INTO `china_area` VALUES (10251, '楚雄市', 532301, 532300, '楚雄彝族自治州', 0);
INSERT INTO `china_area` VALUES (10252, '大姚县', 532326, 532300, '楚雄彝族自治州', 0);
INSERT INTO `china_area` VALUES (10253, '禄丰县', 532331, 532300, '楚雄彝族自治州', 0);
INSERT INTO `china_area` VALUES (10254, '牟定县', 532323, 532300, '楚雄彝族自治州', 0);
INSERT INTO `china_area` VALUES (10255, '南华县', 532324, 532300, '楚雄彝族自治州', 0);
INSERT INTO `china_area` VALUES (10256, '双柏县', 532322, 532300, '楚雄彝族自治州', 0);
INSERT INTO `china_area` VALUES (10257, '武定县', 532329, 532300, '楚雄彝族自治州', 0);
INSERT INTO `china_area` VALUES (10258, '姚安县', 532325, 532300, '楚雄彝族自治州', 0);
INSERT INTO `china_area` VALUES (10259, '永仁县', 532327, 532300, '楚雄彝族自治州', 0);
INSERT INTO `china_area` VALUES (10260, '元谋县', 532328, 532300, '楚雄彝族自治州', 0);
INSERT INTO `china_area` VALUES (10261, '大理白族自治州', 532900, 530000, '云南省', 0);
INSERT INTO `china_area` VALUES (10262, '宾川县', 532924, 532900, '大理白族自治州', 0);
INSERT INTO `china_area` VALUES (10263, '大理市', 532901, 532900, '大理白族自治州', 0);
INSERT INTO `china_area` VALUES (10264, '洱源县', 532930, 532900, '大理白族自治州', 0);
INSERT INTO `china_area` VALUES (10265, '鹤庆县', 532932, 532900, '大理白族自治州', 0);
INSERT INTO `china_area` VALUES (10266, '剑川县', 532931, 532900, '大理白族自治州', 0);
INSERT INTO `china_area` VALUES (10267, '弥渡县', 532925, 532900, '大理白族自治州', 0);
INSERT INTO `china_area` VALUES (10268, '南涧彝族自治县', 532926, 532900, '大理白族自治州', 0);
INSERT INTO `china_area` VALUES (10269, '巍山彝族回族自治县', 532927, 532900, '大理白族自治州', 0);
INSERT INTO `china_area` VALUES (10270, '祥云县', 532923, 532900, '大理白族自治州', 0);
INSERT INTO `china_area` VALUES (10271, '漾濞彝族自治县', 532922, 532900, '大理白族自治州', 0);
INSERT INTO `china_area` VALUES (10272, '永平县', 532928, 532900, '大理白族自治州', 0);
INSERT INTO `china_area` VALUES (10273, '云龙县', 532929, 532900, '大理白族自治州', 0);
INSERT INTO `china_area` VALUES (10274, '德宏傣族景颇族自治州', 533100, 530000, '云南省', 0);
INSERT INTO `china_area` VALUES (10275, '梁河县', 533122, 533100, '德宏傣族景颇族自治州', 0);
INSERT INTO `china_area` VALUES (10276, '陇川县', 533124, 533100, '德宏傣族景颇族自治州', 0);
INSERT INTO `china_area` VALUES (10277, '芒市', 533103, 533100, '德宏傣族景颇族自治州', 0);
INSERT INTO `china_area` VALUES (10278, '瑞丽市', 533102, 533100, '德宏傣族景颇族自治州', 0);
INSERT INTO `china_area` VALUES (10279, '盈江县', 533123, 533100, '德宏傣族景颇族自治州', 0);
INSERT INTO `china_area` VALUES (10280, '迪庆藏族自治州', 533400, 530000, '云南省', 0);
INSERT INTO `china_area` VALUES (10281, '德钦县', 533422, 533400, '迪庆藏族自治州', 0);
INSERT INTO `china_area` VALUES (10282, '维西傈僳族自治县', 533423, 533400, '迪庆藏族自治州', 0);
INSERT INTO `china_area` VALUES (10283, '香格里拉市', 533401, 533400, '迪庆藏族自治州', 0);
INSERT INTO `china_area` VALUES (10284, '红河哈尼族彝族自治州', 532500, 530000, '云南省', 0);
INSERT INTO `china_area` VALUES (10285, '个旧市', 532501, 532500, '红河哈尼族彝族自治州', 0);
INSERT INTO `china_area` VALUES (10286, '红河县', 532529, 532500, '红河哈尼族彝族自治州', 0);
INSERT INTO `china_area` VALUES (10287, '河口瑶族自治县', 532532, 532500, '红河哈尼族彝族自治州', 0);
INSERT INTO `china_area` VALUES (10288, '建水县', 532524, 532500, '红河哈尼族彝族自治州', 0);
INSERT INTO `china_area` VALUES (10289, '金平苗族瑶族傣族自治县', 532530, 532500, '红河哈尼族彝族自治州', 0);
INSERT INTO `china_area` VALUES (10290, '开远市', 532502, 532500, '红河哈尼族彝族自治州', 0);
INSERT INTO `china_area` VALUES (10291, '泸西县', 532527, 532500, '红河哈尼族彝族自治州', 0);
INSERT INTO `china_area` VALUES (10292, '绿春县', 532531, 532500, '红河哈尼族彝族自治州', 0);
INSERT INTO `china_area` VALUES (10293, '弥勒市', 532504, 532500, '红河哈尼族彝族自治州', 0);
INSERT INTO `china_area` VALUES (10294, '蒙自市', 532503, 532500, '红河哈尼族彝族自治州', 0);
INSERT INTO `china_area` VALUES (10295, '屏边苗族自治县', 532523, 532500, '红河哈尼族彝族自治州', 0);
INSERT INTO `china_area` VALUES (10296, '石屏县', 532525, 532500, '红河哈尼族彝族自治州', 0);
INSERT INTO `china_area` VALUES (10297, '元阳县', 532528, 532500, '红河哈尼族彝族自治州', 0);
INSERT INTO `china_area` VALUES (10298, '临沧市', 530900, 530000, '云南省', 0);
INSERT INTO `china_area` VALUES (10299, '沧源佤族自治县', 530927, 530900, '临沧市', 0);
INSERT INTO `china_area` VALUES (10300, '凤庆县', 530921, 530900, '临沧市', 0);
INSERT INTO `china_area` VALUES (10301, '耿马傣族佤族自治县', 530926, 530900, '临沧市', 0);
INSERT INTO `china_area` VALUES (10302, '临翔区', 530902, 530900, '临沧市', 0);
INSERT INTO `china_area` VALUES (10303, '双江拉祜族佤族布朗族傣族自治县', 530925, 530900, '临沧市', 0);
INSERT INTO `china_area` VALUES (10304, '永德县', 530923, 530900, '临沧市', 0);
INSERT INTO `china_area` VALUES (10305, '云县', 530922, 530900, '临沧市', 0);
INSERT INTO `china_area` VALUES (10306, '镇康县', 530924, 530900, '临沧市', 0);
INSERT INTO `china_area` VALUES (10307, '昆明市', 530100, 530000, '云南省', 0);
INSERT INTO `china_area` VALUES (10308, '安宁市', 530181, 530100, '昆明市', 0);
INSERT INTO `china_area` VALUES (10309, '呈贡区', 530114, 530100, '昆明市', 0);
INSERT INTO `china_area` VALUES (10310, '东川区', 530113, 530100, '昆明市', 0);
INSERT INTO `china_area` VALUES (10311, '富民县', 530124, 530100, '昆明市', 0);
INSERT INTO `china_area` VALUES (10312, '官渡区', 530111, 530100, '昆明市', 0);
INSERT INTO `china_area` VALUES (10313, '晋宁区', 530115, 530100, '昆明市', 0);
INSERT INTO `china_area` VALUES (10314, '禄劝彝族苗族自治县', 530128, 530100, '昆明市', 0);
INSERT INTO `china_area` VALUES (10315, '盘龙区', 530103, 530100, '昆明市', 0);
INSERT INTO `china_area` VALUES (10316, '石林彝族自治县', 530126, 530100, '昆明市', 0);
INSERT INTO `china_area` VALUES (10317, '嵩明县', 530127, 530100, '昆明市', 0);
INSERT INTO `china_area` VALUES (10318, '五华区', 530102, 530100, '昆明市', 0);
INSERT INTO `china_area` VALUES (10319, '西山区', 530112, 530100, '昆明市', 0);
INSERT INTO `china_area` VALUES (10320, '寻甸回族彝族自治县', 530129, 530100, '昆明市', 0);
INSERT INTO `china_area` VALUES (10321, '宜良县', 530125, 530100, '昆明市', 0);
INSERT INTO `china_area` VALUES (10322, '丽江市', 530700, 530000, '云南省', 0);
INSERT INTO `china_area` VALUES (10323, '古城区', 530702, 530700, '丽江市', 0);
INSERT INTO `china_area` VALUES (10324, '华坪县', 530723, 530700, '丽江市', 0);
INSERT INTO `china_area` VALUES (10325, '宁蒗彝族自治县', 530724, 530700, '丽江市', 0);
INSERT INTO `china_area` VALUES (10326, '永胜县', 530722, 530700, '丽江市', 0);
INSERT INTO `china_area` VALUES (10327, '玉龙纳西族自治县', 530721, 530700, '丽江市', 0);
INSERT INTO `china_area` VALUES (10328, '怒江傈僳族自治州', 533300, 530000, '云南省', 0);
INSERT INTO `china_area` VALUES (10329, '福贡县', 533323, 533300, '怒江傈僳族自治州', 0);
INSERT INTO `china_area` VALUES (10330, '贡山独龙族怒族自治县', 533324, 533300, '怒江傈僳族自治州', 0);
INSERT INTO `china_area` VALUES (10331, '兰坪白族普米族自治县', 533325, 533300, '怒江傈僳族自治州', 0);
INSERT INTO `china_area` VALUES (10332, '泸水市', 533301, 533300, '怒江傈僳族自治州', 0);
INSERT INTO `china_area` VALUES (10333, '普洱市', 530800, 530000, '云南省', 0);
INSERT INTO `china_area` VALUES (10334, '江城哈尼族彝族自治县', 530826, 530800, '普洱市', 0);
INSERT INTO `china_area` VALUES (10335, '景谷傣族彝族自治县', 530824, 530800, '普洱市', 0);
INSERT INTO `china_area` VALUES (10336, '景东彝族自治县', 530823, 530800, '普洱市', 0);
INSERT INTO `china_area` VALUES (10337, '澜沧拉祜族自治县', 530828, 530800, '普洱市', 0);
INSERT INTO `china_area` VALUES (10338, '孟连傣族拉祜族佤族自治县', 530827, 530800, '普洱市', 0);
INSERT INTO `china_area` VALUES (10339, '墨江哈尼族自治县', 530822, 530800, '普洱市', 0);
INSERT INTO `china_area` VALUES (10340, '宁洱哈尼族彝族自治县', 530821, 530800, '普洱市', 0);
INSERT INTO `china_area` VALUES (10341, '思茅区', 530802, 530800, '普洱市', 0);
INSERT INTO `china_area` VALUES (10342, '西盟佤族自治县', 530829, 530800, '普洱市', 0);
INSERT INTO `china_area` VALUES (10343, '镇沅彝族哈尼族拉祜族自治县', 530825, 530800, '普洱市', 0);
INSERT INTO `china_area` VALUES (10344, '曲靖市', 530300, 530000, '云南省', 0);
INSERT INTO `china_area` VALUES (10345, '富源县', 530325, 530300, '曲靖市', 0);
INSERT INTO `china_area` VALUES (10346, '会泽县', 530326, 530300, '曲靖市', 0);
INSERT INTO `china_area` VALUES (10347, '陆良县', 530322, 530300, '曲靖市', 0);
INSERT INTO `china_area` VALUES (10348, '罗平县', 530324, 530300, '曲靖市', 0);
INSERT INTO `china_area` VALUES (10349, '马龙县', 530321, 530300, '曲靖市', 0);
INSERT INTO `china_area` VALUES (10350, '麒麟区', 530302, 530300, '曲靖市', 0);
INSERT INTO `china_area` VALUES (10351, '师宗县', 530323, 530300, '曲靖市', 0);
INSERT INTO `china_area` VALUES (10352, '宣威市', 530381, 530300, '曲靖市', 0);
INSERT INTO `china_area` VALUES (10353, '沾益区', 530303, 530300, '曲靖市', 0);
INSERT INTO `china_area` VALUES (10354, '文山壮族苗族自治州', 532600, 530000, '云南省', 0);
INSERT INTO `china_area` VALUES (10355, '富宁县', 532628, 532600, '文山壮族苗族自治州', 0);
INSERT INTO `china_area` VALUES (10356, '广南县', 532627, 532600, '文山壮族苗族自治州', 0);
INSERT INTO `china_area` VALUES (10357, '马关县', 532625, 532600, '文山壮族苗族自治州', 0);
INSERT INTO `china_area` VALUES (10358, '麻栗坡县', 532624, 532600, '文山壮族苗族自治州', 0);
INSERT INTO `china_area` VALUES (10359, '丘北县', 532626, 532600, '文山壮族苗族自治州', 0);
INSERT INTO `china_area` VALUES (10360, '文山市', 532601, 532600, '文山壮族苗族自治州', 0);
INSERT INTO `china_area` VALUES (10361, '西畴县', 532623, 532600, '文山壮族苗族自治州', 0);
INSERT INTO `china_area` VALUES (10362, '砚山县', 532622, 532600, '文山壮族苗族自治州', 0);
INSERT INTO `china_area` VALUES (10363, '西双版纳傣族自治州', 532800, 530000, '云南省', 0);
INSERT INTO `china_area` VALUES (10364, '景洪市', 532801, 532800, '西双版纳傣族自治州', 0);
INSERT INTO `china_area` VALUES (10365, '勐腊县', 532823, 532800, '西双版纳傣族自治州', 0);
INSERT INTO `china_area` VALUES (10366, '勐海县', 532822, 532800, '西双版纳傣族自治州', 0);
INSERT INTO `china_area` VALUES (10367, '昭通市', 530600, 530000, '云南省', 0);
INSERT INTO `china_area` VALUES (10368, '大关县', 530624, 530600, '昭通市', 0);
INSERT INTO `china_area` VALUES (10369, '鲁甸县', 530621, 530600, '昭通市', 0);
INSERT INTO `china_area` VALUES (10370, '巧家县', 530622, 530600, '昭通市', 0);
INSERT INTO `china_area` VALUES (10371, '水富县', 530630, 530600, '昭通市', 0);
INSERT INTO `china_area` VALUES (10372, '绥江县', 530626, 530600, '昭通市', 0);
INSERT INTO `china_area` VALUES (10373, '威信县', 530629, 530600, '昭通市', 0);
INSERT INTO `china_area` VALUES (10374, '盐津县', 530623, 530600, '昭通市', 0);
INSERT INTO `china_area` VALUES (10375, '彝良县', 530628, 530600, '昭通市', 0);
INSERT INTO `china_area` VALUES (10376, '永善县', 530625, 530600, '昭通市', 0);
INSERT INTO `china_area` VALUES (10377, '镇雄县', 530627, 530600, '昭通市', 0);
INSERT INTO `china_area` VALUES (10378, '昭阳区', 530602, 530600, '昭通市', 0);
INSERT INTO `china_area` VALUES (10379, '玉溪市', 530400, 530000, '云南省', 0);
INSERT INTO `china_area` VALUES (10380, '澄江县', 530422, 530400, '玉溪市', 0);
INSERT INTO `china_area` VALUES (10381, '峨山彝族自治县', 530426, 530400, '玉溪市', 0);
INSERT INTO `china_area` VALUES (10382, '华宁县', 530424, 530400, '玉溪市', 0);
INSERT INTO `china_area` VALUES (10383, '红塔区', 530402, 530400, '玉溪市', 0);
INSERT INTO `china_area` VALUES (10384, '江川区', 530403, 530400, '玉溪市', 0);
INSERT INTO `china_area` VALUES (10385, '通海县', 530423, 530400, '玉溪市', 0);
INSERT INTO `china_area` VALUES (10386, '新平彝族傣族自治县', 530427, 530400, '玉溪市', 0);
INSERT INTO `china_area` VALUES (10387, '易门县', 530425, 530400, '玉溪市', 0);
INSERT INTO `china_area` VALUES (10388, '元江哈尼族彝族傣族自治县', 530428, 530400, '玉溪市', 0);
INSERT INTO `china_area` VALUES (10389, '浙江省', 330000, 0, '中国', 0);
INSERT INTO `china_area` VALUES (10390, '湖州市', 330500, 330000, '浙江省', 0);
INSERT INTO `china_area` VALUES (10391, '安吉县', 330523, 330500, '湖州市', 0);
INSERT INTO `china_area` VALUES (10392, '德清县', 330521, 330500, '湖州市', 0);
INSERT INTO `china_area` VALUES (10393, '南浔区', 330503, 330500, '湖州市', 0);
INSERT INTO `china_area` VALUES (10394, '吴兴区', 330502, 330500, '湖州市', 0);
INSERT INTO `china_area` VALUES (10395, '长兴县', 330522, 330500, '湖州市', 0);
INSERT INTO `china_area` VALUES (10396, '杭州市', 330100, 330000, '浙江省', 0);
INSERT INTO `china_area` VALUES (10397, '滨江区', 330108, 330100, '杭州市', 0);
INSERT INTO `china_area` VALUES (10398, '淳安县', 330127, 330100, '杭州市', 0);
INSERT INTO `china_area` VALUES (10399, '富阳区', 330111, 330100, '杭州市', 0);
INSERT INTO `china_area` VALUES (10400, '拱墅区', 330105, 330100, '杭州市', 0);
INSERT INTO `china_area` VALUES (10401, '建德市', 330182, 330100, '杭州市', 0);
INSERT INTO `china_area` VALUES (10402, '江干区', 330104, 330100, '杭州市', 0);
INSERT INTO `china_area` VALUES (10403, '临安市', 330185, 330100, '杭州市', 0);
INSERT INTO `china_area` VALUES (10404, '上城区', 330102, 330100, '杭州市', 0);
INSERT INTO `china_area` VALUES (10405, '桐庐县', 330122, 330100, '杭州市', 0);
INSERT INTO `china_area` VALUES (10406, '西湖区', 330106, 330100, '杭州市', 0);
INSERT INTO `china_area` VALUES (10407, '下城区', 330103, 330100, '杭州市', 0);
INSERT INTO `china_area` VALUES (10408, '萧山区', 330109, 330100, '杭州市', 0);
INSERT INTO `china_area` VALUES (10409, '余杭区', 330110, 330100, '杭州市', 0);
INSERT INTO `china_area` VALUES (10410, '嘉兴市', 330400, 330000, '浙江省', 0);
INSERT INTO `china_area` VALUES (10411, '海盐县', 330424, 330400, '嘉兴市', 0);
INSERT INTO `china_area` VALUES (10412, '海宁市', 330481, 330400, '嘉兴市', 0);
INSERT INTO `china_area` VALUES (10413, '嘉善县', 330421, 330400, '嘉兴市', 0);
INSERT INTO `china_area` VALUES (10414, '南湖区', 330402, 330400, '嘉兴市', 0);
INSERT INTO `china_area` VALUES (10415, '平湖市', 330482, 330400, '嘉兴市', 0);
INSERT INTO `china_area` VALUES (10416, '桐乡市', 330483, 330400, '嘉兴市', 0);
INSERT INTO `china_area` VALUES (10417, '秀洲区', 330411, 330400, '嘉兴市', 0);
INSERT INTO `china_area` VALUES (10418, '金华市', 330700, 330000, '浙江省', 0);
INSERT INTO `china_area` VALUES (10419, '东阳市', 330783, 330700, '金华市', 0);
INSERT INTO `china_area` VALUES (10420, '金东区', 330703, 330700, '金华市', 0);
INSERT INTO `china_area` VALUES (10421, '兰溪市', 330781, 330700, '金华市', 0);
INSERT INTO `china_area` VALUES (10422, '磐安县', 330727, 330700, '金华市', 0);
INSERT INTO `china_area` VALUES (10423, '浦江县', 330726, 330700, '金华市', 0);
INSERT INTO `china_area` VALUES (10424, '婺城区', 330702, 330700, '金华市', 0);
INSERT INTO `china_area` VALUES (10425, '武义县', 330723, 330700, '金华市', 0);
INSERT INTO `china_area` VALUES (10426, '义乌市', 330782, 330700, '金华市', 0);
INSERT INTO `china_area` VALUES (10427, '永康市', 330784, 330700, '金华市', 0);
INSERT INTO `china_area` VALUES (10428, '丽水市', 331100, 330000, '浙江省', 0);
INSERT INTO `china_area` VALUES (10429, '缙云县', 331122, 331100, '丽水市', 0);
INSERT INTO `china_area` VALUES (10430, '景宁畲族自治县', 331127, 331100, '丽水市', 0);
INSERT INTO `china_area` VALUES (10431, '莲都区', 331102, 331100, '丽水市', 0);
INSERT INTO `china_area` VALUES (10432, '龙泉市', 331181, 331100, '丽水市', 0);
INSERT INTO `china_area` VALUES (10433, '青田县', 331121, 331100, '丽水市', 0);
INSERT INTO `china_area` VALUES (10434, '庆元县', 331126, 331100, '丽水市', 0);
INSERT INTO `china_area` VALUES (10435, '松阳县', 331124, 331100, '丽水市', 0);
INSERT INTO `china_area` VALUES (10436, '遂昌县', 331123, 331100, '丽水市', 0);
INSERT INTO `china_area` VALUES (10437, '云和县', 331125, 331100, '丽水市', 0);
INSERT INTO `china_area` VALUES (10438, '宁波市', 330200, 330000, '浙江省', 0);
INSERT INTO `china_area` VALUES (10439, '北仑区', 330206, 330200, '宁波市', 0);
INSERT INTO `china_area` VALUES (10440, '慈溪市', 330282, 330200, '宁波市', 0);
INSERT INTO `china_area` VALUES (10441, '奉化区', 330213, 330200, '宁波市', 0);
INSERT INTO `china_area` VALUES (10442, '海曙区', 330203, 330200, '宁波市', 0);
INSERT INTO `china_area` VALUES (10443, '江北区', 330205, 330200, '宁波市', 0);
INSERT INTO `china_area` VALUES (10444, '宁海县', 330226, 330200, '宁波市', 0);
INSERT INTO `china_area` VALUES (10445, '象山县', 330225, 330200, '宁波市', 0);
INSERT INTO `china_area` VALUES (10446, '鄞州区', 330212, 330200, '宁波市', 0);
INSERT INTO `china_area` VALUES (10447, '余姚市', 330281, 330200, '宁波市', 0);
INSERT INTO `china_area` VALUES (10448, '镇海区', 330211, 330200, '宁波市', 0);
INSERT INTO `china_area` VALUES (10449, '衢州市', 330800, 330000, '浙江省', 0);
INSERT INTO `china_area` VALUES (10450, '常山县', 330822, 330800, '衢州市', 0);
INSERT INTO `china_area` VALUES (10451, '江山市', 330881, 330800, '衢州市', 0);
INSERT INTO `china_area` VALUES (10452, '开化县', 330824, 330800, '衢州市', 0);
INSERT INTO `china_area` VALUES (10453, '柯城区', 330802, 330800, '衢州市', 0);
INSERT INTO `china_area` VALUES (10454, '龙游县', 330825, 330800, '衢州市', 0);
INSERT INTO `china_area` VALUES (10455, '衢江区', 330803, 330800, '衢州市', 0);
INSERT INTO `china_area` VALUES (10456, '绍兴市', 330600, 330000, '浙江省', 0);
INSERT INTO `china_area` VALUES (10457, '柯桥区', 330603, 330600, '绍兴市', 0);
INSERT INTO `china_area` VALUES (10458, '嵊州市', 330683, 330600, '绍兴市', 0);
INSERT INTO `china_area` VALUES (10459, '上虞区', 330604, 330600, '绍兴市', 0);
INSERT INTO `china_area` VALUES (10460, '新昌县', 330624, 330600, '绍兴市', 0);
INSERT INTO `china_area` VALUES (10461, '越城区', 330602, 330600, '绍兴市', 0);
INSERT INTO `china_area` VALUES (10462, '诸暨市', 330681, 330600, '绍兴市', 0);
INSERT INTO `china_area` VALUES (10463, '台州市', 331000, 330000, '浙江省', 0);
INSERT INTO `china_area` VALUES (10464, '黄岩区', 331003, 331000, '台州市', 0);
INSERT INTO `china_area` VALUES (10465, '椒江区', 331002, 331000, '台州市', 0);
INSERT INTO `china_area` VALUES (10466, '临海市', 331082, 331000, '台州市', 0);
INSERT INTO `china_area` VALUES (10467, '路桥区', 331004, 331000, '台州市', 0);
INSERT INTO `china_area` VALUES (10468, '三门县', 331022, 331000, '台州市', 0);
INSERT INTO `china_area` VALUES (10469, '天台县', 331023, 331000, '台州市', 0);
INSERT INTO `china_area` VALUES (10470, '温岭市', 331081, 331000, '台州市', 0);
INSERT INTO `china_area` VALUES (10471, '仙居县', 331024, 331000, '台州市', 0);
INSERT INTO `china_area` VALUES (10472, '玉环市', 331021, 331000, '台州市', 0);
INSERT INTO `china_area` VALUES (10473, '温州市', 330300, 330000, '浙江省', 0);
INSERT INTO `china_area` VALUES (10474, '苍南县', 330327, 330300, '温州市', 0);
INSERT INTO `china_area` VALUES (10475, '洞头区', 330305, 330300, '温州市', 0);
INSERT INTO `china_area` VALUES (10476, '乐清市', 330382, 330300, '温州市', 0);
INSERT INTO `china_area` VALUES (10477, '龙湾区', 330303, 330300, '温州市', 0);
INSERT INTO `china_area` VALUES (10478, '鹿城区', 330302, 330300, '温州市', 0);
INSERT INTO `china_area` VALUES (10479, '瓯海区', 330304, 330300, '温州市', 0);
INSERT INTO `china_area` VALUES (10480, '平阳县', 330326, 330300, '温州市', 0);
INSERT INTO `china_area` VALUES (10481, '瑞安市', 330381, 330300, '温州市', 0);
INSERT INTO `china_area` VALUES (10482, '泰顺县', 330329, 330300, '温州市', 0);
INSERT INTO `china_area` VALUES (10483, '文成县', 330328, 330300, '温州市', 0);
INSERT INTO `china_area` VALUES (10484, '永嘉县', 330324, 330300, '温州市', 0);
INSERT INTO `china_area` VALUES (10485, '舟山市', 330900, 330000, '浙江省', 0);
INSERT INTO `china_area` VALUES (10486, '岱山县', 330921, 330900, '舟山市', 0);
INSERT INTO `china_area` VALUES (10487, '定海区', 330902, 330900, '舟山市', 0);
INSERT INTO `china_area` VALUES (10488, '普陀区', 330903, 330900, '舟山市', 0);
INSERT INTO `china_area` VALUES (10489, '嵊泗县', 330922, 330900, '舟山市', 0);

-- ----------------------------
-- Table structure for device
-- ----------------------------
DROP TABLE IF EXISTS `device`;
CREATE TABLE `device`  (
  `sno` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '设备序列号',
  `ctime` datetime(0) NOT NULL COMMENT '添加时间',
  `utime` datetime(0) DEFAULT NULL COMMENT '更新时间',
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '设备名称',
  `imei` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'IMEI码',
  `sn2` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'SN2码',
  `sn1` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'SN1码',
  `mac` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'MAC地址,通讯用',
  `work_status` int(1) NOT NULL DEFAULT 2 COMMENT '设备工作状态,2离线,3:使用中,4:空闲 5:禁用 6:故障 7待机',
  `online_status` int(1) DEFAULT 2 COMMENT '在线状态，1在线，2离线',
  `status` int(1) DEFAULT 0 COMMENT '是否串货，0否，1是，默认否',
  `longitude` decimal(19, 6) DEFAULT NULL COMMENT '经度',
  `latitude` decimal(19, 6) DEFAULT NULL COMMENT '维度',
  `last_online_time` datetime(0) DEFAULT NULL COMMENT '最后上线时间',
  `operator_id` int(11) DEFAULT NULL COMMENT '经办人对应的系统账号',
  `operator_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '经办人名称',
  `launch_area_id` int(11) DEFAULT NULL COMMENT '投放点ID',
  `launch_area_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '投放点名称',
  `product_id` int(11) DEFAULT NULL COMMENT '所属品类',
  `product_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '所属品类名称',
  `product_category_id` int(11) DEFAULT NULL COMMENT '产品id',
  `is_deleted` int(1) DEFAULT 0 COMMENT '删除标识，0：未删除，1：已删除',
  `service_id` int(11) DEFAULT NULL COMMENT '收费模式ID',
  `service_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '收费模式名称',
  `giz_did` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '机智云设备did',
  `giz_host` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '机智云websocket操作主页',
  `giz_wss_port` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '机智云websocket加密端口',
  `giz_ws_port` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '机智云websocket端口',
  `giz_pass_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '机智云设备passcode',
  `fault_status` int(1) DEFAULT 8 COMMENT '设备故障状态，6故障，8正常',
  `agent_id` int(11) DEFAULT NULL COMMENT '代理商id',
  `sys_user_id` int(11) NOT NULL,
  `wx_ticket` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '微信ticket',
  `wx_did` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '微信did',
  `content_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '二维码url',
  `owner_id` int(11) DEFAULT NULL COMMENT '拥有者，默认为创建者',
  `group_id` int(11) DEFAULT NULL COMMENT '设备组id',
  `entry_time` datetime(0) DEFAULT NULL COMMENT '入库时间',
  `shift_out_time` datetime(0) DEFAULT NULL COMMENT '出库时间',
  `activate_status` int(1) DEFAULT 1 COMMENT '激活状态：1未激活 2激活',
  `activated_time` datetime(0) DEFAULT NULL COMMENT '激活时间',
  `origin` int(1) DEFAULT 1 COMMENT '数据来源：1手工录入 2云端上报',
  `abnormal_times` int(11) DEFAULT 0 COMMENT '连续下单异常的次数',
  `lock` tinyint(1) DEFAULT 0 COMMENT '多次异常后锁定设备',
  `owner_name` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `install_user_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '安装用户姓名',
  `install_user_province` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '安装用户所在省',
  `install_user_city` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '安装用户所在市',
  `install_user_area` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '安装用户所在区',
  `install_user_address` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '安装用户详细地址',
  `install_user_mobile` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '安装用户手机号',
  `country` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '国家',
  `province` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '省',
  `city` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '市',
  `remarks` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
  `control_type` bit(1) DEFAULT b'0' COMMENT '设备控制器类型',
  `batch` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '入库批次',
  `warehousing_id` int(10) DEFAULT NULL COMMENT '入库员的系统账号',
  `warehousing_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '入库员',
  `sweep_code_status` int(2) DEFAULT NULL COMMENT '扫码状态',
  PRIMARY KEY (`sno`) USING BTREE,
  INDEX `idx_mac`(`mac`) USING BTREE,
  INDEX `index_owner_id`(`owner_id`, `status`, `ctime`) USING BTREE,
  INDEX `index_product_id`(`product_id`, `status`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '设备表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of device
-- ----------------------------
INSERT INTO `device` VALUES ('04106089620936508960', '2019-12-02 13:48:43', '2019-12-02 14:34:42', 'SPA床垫', 'dsa', 'kasjdkjsakdjkassjd', 'kdjfkajkdjaksjdkasasj', '869587038886913', 3, 1, 11, NULL, NULL, NULL, NULL, NULL, 234, '测试添加仓库', 1, '能量SPA床垫', NULL, 0, 2, 'SPA舱按次收费', NULL, NULL, NULL, NULL, NULL, 8, NULL, 596, NULL, NULL, 'http://lease.iotsdk.com/app/wx/init?deviceId=04106089620936508960', 596, NULL, NULL, NULL, 1, NULL, 3, 0, 0, '昆山测试运营', NULL, NULL, NULL, NULL, NULL, NULL, '中国', '江苏省', '苏州市', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `device` VALUES ('04106089620936508961', '2019-12-04 13:51:29', '2019-12-06 14:26:48', 'aaa', '11', '111', '111', '11111', 2, 2, 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, '能量SPA床垫', NULL, 0, 2, NULL, NULL, NULL, NULL, NULL, NULL, 8, NULL, 596, NULL, NULL, NULL, 596, NULL, NULL, NULL, 1, NULL, 1, 0, 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '', '', '', NULL, b'0', NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for device_alarm
-- ----------------------------
DROP TABLE IF EXISTS `device_alarm`;
CREATE TABLE `device_alarm`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键,自增长',
  `ctime` datetime(0) NOT NULL COMMENT '添加时间',
  `utime` datetime(0) DEFAULT NULL COMMENT '更新时间',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '故障名称',
  `attr` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '故障参数/对应数据点表的identity_name',
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '内容',
  `happen_time` datetime(0) DEFAULT NULL COMMENT '故障发生时间',
  `fixed_time` datetime(0) DEFAULT NULL COMMENT '故障修复时间',
  `status` int(1) NOT NULL COMMENT '故障状态,0:未修复 1:已修复',
  `mac` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'MAC地址',
  `longitude` decimal(19, 2) DEFAULT NULL COMMENT '经度',
  `latitude` decimal(19, 2) DEFAULT NULL COMMENT '维度',
  `notify_user_id` int(11) DEFAULT NULL COMMENT '需要通知的人员ID',
  `sno` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '对应设备序列号',
  `alarm_type` int(1) DEFAULT 1 COMMENT '告警类型:0,报警;1,故障',
  `product_key` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `remark` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_sno`(`sno`) USING BTREE,
  INDEX `idx_mac`(`mac`) USING BTREE,
  INDEX `idx_ctime`(`ctime`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '设备故障(警告)记录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for device_assign_record
-- ----------------------------
DROP TABLE IF EXISTS `device_assign_record`;
CREATE TABLE `device_assign_record`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `ctime` datetime(0) NOT NULL COMMENT '创建时间',
  `sno` varchar(30) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL COMMENT '设备sno',
  `mac` varchar(30) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL COMMENT '设备mac',
  `source_operator` int(11) DEFAULT NULL COMMENT '原运营商',
  `destination_operator` int(11) NOT NULL COMMENT '现运营商',
  `sys_user_id` int(11) NOT NULL COMMENT '创建人',
  `sys_user_name` varchar(30) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL COMMENT '创建人名称',
  `operate_type` varchar(16) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL DEFAULT 'ASSIGN' COMMENT '操作类型:ASSIGN和UNBIND',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci COMMENT = '设备分配记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for device_da
-- ----------------------------
DROP TABLE IF EXISTS `device_da`;
CREATE TABLE `device_da`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键,自增长',
  `mac` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'MAC地址,通讯用',
  `content_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '二维码url',
  `sno` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '设备序列号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '设备数据转化表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for device_ext_for_majiang
-- ----------------------------
DROP TABLE IF EXISTS `device_ext_for_majiang`;
CREATE TABLE `device_ext_for_majiang`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ctime` datetime(0) NOT NULL COMMENT '添加时间',
  `utime` datetime(0) DEFAULT NULL COMMENT '更新时间',
  `sno` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '设备序列号',
  `mode` int(1) DEFAULT NULL COMMENT '游戏模式：1 极速 2静音',
  `game_type` int(1) DEFAULT NULL COMMENT '游戏类型：1标准 2自定义音',
  `game_no` int(3) DEFAULT NULL COMMENT '游戏序号，如果是自定义时为空',
  `command` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '下发指令',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `sno_index`(`sno`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '设备扩展表(麻将机)' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for device_ext_port
-- ----------------------------
DROP TABLE IF EXISTS `device_ext_port`;
CREATE TABLE `device_ext_port`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键,自增长',
  `ctime` datetime(0) NOT NULL COMMENT '添加时间',
  `utime` datetime(0) DEFAULT NULL COMMENT '更新时间',
  `sno` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '设备sno',
  `mac` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'MAC地址,通讯用',
  `port` int(1) DEFAULT NULL COMMENT '出水口号',
  `port_type` int(1) DEFAULT NULL COMMENT '出水类型：1常温，2热水，3冰水',
  `status` int(1) DEFAULT NULL COMMENT '状态：3使用中  4空闲',
  `sort` int(1) DEFAULT NULL COMMENT '排序字段',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `sno_index`(`sno`) USING BTREE,
  INDEX `port_index`(`port`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '设备扩展表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for device_ext_weather
-- ----------------------------
DROP TABLE IF EXISTS `device_ext_weather`;
CREATE TABLE `device_ext_weather`  (
  `sno` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '设备序列号',
  `ctime` datetime(0) NOT NULL COMMENT '添加时间',
  `utime` datetime(0) DEFAULT NULL COMMENT '更新时间',
  `longitude` decimal(19, 6) NOT NULL COMMENT '经度',
  `latitude` decimal(19, 6) NOT NULL COMMENT '维度',
  `source` int(2) NOT NULL COMMENT '来源，1：和风，2：阿里',
  `city_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '城市ID',
  `tmp` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '温度',
  `hum` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '湿度',
  `pm25` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'pm2.5',
  `qlty` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '空气质量',
  `province` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '省',
  `city` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '市',
  `area` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '区',
  `product_id` int(11) NOT NULL COMMENT '所属产品',
  PRIMARY KEY (`sno`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '设备-天气拓展表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for device_group
-- ----------------------------
DROP TABLE IF EXISTS `device_group`;
CREATE TABLE `device_group`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '组名称',
  `ctime` datetime(0) NOT NULL COMMENT '创建时间',
  `utime` datetime(0) DEFAULT NULL COMMENT '更新时间',
  `is_deleted` int(11) NOT NULL DEFAULT 0 COMMENT '是否删除，0:未删除，1:已删除',
  `sys_user_id` int(11) NOT NULL COMMENT '创建人',
  `sys_user_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '创建人名称',
  `assigned_account_id` int(11) DEFAULT NULL COMMENT '被分配的运营商或代理商的系统帐号',
  `assigned_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '运营商或代理商名称',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `device_group_id_uindex`(`id`) USING BTREE,
  INDEX `idx_sys_user_id`(`sys_user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '设备组' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for device_group_to_device
-- ----------------------------
DROP TABLE IF EXISTS `device_group_to_device`;
CREATE TABLE `device_group_to_device`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `ctime` datetime(0) NOT NULL COMMENT '创建时间',
  `utime` datetime(0) DEFAULT NULL,
  `device_group_id` int(11) NOT NULL COMMENT '设备组id',
  `device_sno` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '设备sno',
  `is_deleted` int(1) NOT NULL DEFAULT 0 COMMENT '是否删除：0未删除 1已删除',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `device_group_to_device_id_uindex`(`id`) USING BTREE,
  INDEX `idx_device_group_id`(`device_group_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '设备组与设备的关系' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for device_launch_area
-- ----------------------------
DROP TABLE IF EXISTS `device_launch_area`;
CREATE TABLE `device_launch_area`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键,自增长',
  `ctime` datetime(0) NOT NULL COMMENT '添加时间',
  `utime` datetime(0) DEFAULT NULL COMMENT '更新时间',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '投放点名称',
  `province` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '省',
  `city` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '市',
  `area` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '区/县',
  `address` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '详细地址',
  `sys_user_id` int(11) NOT NULL COMMENT '创建者id',
  `sys_user_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '创建人姓名',
  `operator_id` int(13) DEFAULT NULL COMMENT '运营商对应的系统账号',
  `operator_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '运营商名称',
  `maintainer_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT ' 维护人员姓名',
  `maintainer_id` int(11) DEFAULT NULL COMMENT '维护人员id',
  `longitude` decimal(19, 6) NOT NULL COMMENT '经度',
  `latitude` decimal(19, 6) NOT NULL COMMENT '纬度',
  `person_in_charge` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '负责人姓名',
  `person_in_charge_id` int(11) DEFAULT NULL COMMENT '负责人id',
  `mobile` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '负责人电话',
  `status` int(11) DEFAULT 1 COMMENT '状态：0:禁用，1:启用',
  `is_deleted` int(11) DEFAULT 0 COMMENT '是否删除：0，否，1，是',
  `owner_id` int(11) DEFAULT NULL COMMENT '归属人',
  `picture_url` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '图片地址',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_operator`(`operator_id`) USING BTREE,
  INDEX `idx_name`(`name`) USING BTREE,
  INDEX `idx_sys_user_id`(`sys_user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 235 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '设备投放点表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of device_launch_area
-- ----------------------------
INSERT INTO `device_launch_area` VALUES (234, '2019-11-18 15:47:46', '2019-11-18 15:47:41', '测试添加仓库', '江苏省', '苏州市', '昆山市', '昆山商厦', 596, 'suzhou', NULL, NULL, NULL, NULL, 120.956637, 31.375881, '大橙子', NULL, '15618816266', 1, 0, 596, '/launch-area/images/6518c00f-2dc4-4045-af50-62b842b4463f.jpg');

-- ----------------------------
-- Table structure for device_plan
-- ----------------------------
DROP TABLE IF EXISTS `device_plan`;
CREATE TABLE `device_plan`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ctime` datetime(0) DEFAULT NULL,
  `utime` datetime(0) DEFAULT NULL,
  `content` longtext CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT '定时任务内容，json格式',
  `mac` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `is_deleted` int(1) DEFAULT 0 COMMENT '是否删除，0未删除，1已删除',
  `user_id` int(11) DEFAULT NULL COMMENT '用户id，保留的',
  `is_used` int(1) DEFAULT 0 COMMENT '是否使用，0不使用，1使用，默认使用',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for device_running_record
-- ----------------------------
DROP TABLE IF EXISTS `device_running_record`;
CREATE TABLE `device_running_record`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键,自增长',
  `ctime` datetime(0) NOT NULL COMMENT '添加时间',
  `utime` datetime(0) DEFAULT NULL COMMENT '更新时间',
  `sno` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '对应设备序列号',
  `mac` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'MAC地址',
  `work_status` int(1) NOT NULL COMMENT '设备在线状态,1:在线,2:离线 3:使用中 4:空闲 5:禁用 6:故障 ',
  `content` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '报文内容',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_sno`(`sno`) USING BTREE,
  INDEX `idx_mac`(`mac`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '设备运行记录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for device_service_mode_setting
-- ----------------------------
DROP TABLE IF EXISTS `device_service_mode_setting`;
CREATE TABLE `device_service_mode_setting`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sno` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '设备序列号',
  `sys_account_id` int(11) NOT NULL COMMENT '为设备设置收费模式的系统用户',
  `is_free` int(1) NOT NULL DEFAULT 0 COMMENT '是否免费：0，收费，1，免费',
  `ctime` datetime(0) NOT NULL,
  `utime` datetime(0) DEFAULT NULL,
  `assign_account_id` int(11) DEFAULT NULL COMMENT '分配对象的accountId，即设备的owner_id',
  `is_deleted` int(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '设备收费模式设定(麻将机系统特有需求)' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for device_stock
-- ----------------------------
DROP TABLE IF EXISTS `device_stock`;
CREATE TABLE `device_stock`  (
  `sno` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '设备序列号',
  `ctime` datetime(0) DEFAULT NULL,
  `utime` datetime(0) DEFAULT NULL,
  `mac` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `sn1` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '控制器码',
  `sn2` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `imei` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `sweep_code_status` int(1) DEFAULT NULL COMMENT '扫码状态',
  `operator_id` int(10) DEFAULT NULL COMMENT '经办人的系统账号',
  `operator_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '经办人',
  `warehousing_id` int(10) DEFAULT NULL COMMENT '入库员的系统账号',
  `warehousing_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '入库员',
  `out_of_stock_id` int(10) DEFAULT NULL COMMENT '出库员的系统账号',
  `out_of_stock_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '出库员',
  `launch_area_id` int(10) DEFAULT NULL COMMENT '仓库ID',
  `launch_area_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '仓库名称',
  `product_id` int(10) DEFAULT NULL COMMENT '所属品类id',
  `product_name` int(50) DEFAULT NULL COMMENT '所属品类名称',
  `product_category_id` int(10) DEFAULT NULL COMMENT '所属产品Id',
  `batch` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '入库批次',
  `out_batch` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '出库批次',
  `agent_id` int(10) DEFAULT NULL COMMENT '经销商ID',
  `sys_user_id` int(10) DEFAULT NULL COMMENT '创建人',
  `entry_time` datetime(0) DEFAULT NULL COMMENT '入库时间',
  `shift_out_time` datetime(0) DEFAULT NULL COMMENT '出库时间',
  `sweep_code_time` datetime(0) DEFAULT NULL COMMENT '扫码时间',
  `control_type` bit(1) DEFAULT NULL COMMENT '设备控制器类型',
  `supplier_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '供应商',
  `remarks` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '备注',
  `is_deleted` int(1) DEFAULT NULL COMMENT ' 库存删除标识，0：未删除，1：已删除',
  `is_deleted_put` int(1) DEFAULT NULL COMMENT ' 入库删除标识，0：未删除，1：已删除',
  `is_deleted_out` int(1) DEFAULT NULL COMMENT '出库删除标识，0：未删除，1：已删除',
  PRIMARY KEY (`sno`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for device_to_product_service_mode
-- ----------------------------
DROP TABLE IF EXISTS `device_to_product_service_mode`;
CREATE TABLE `device_to_product_service_mode`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键,自增长',
  `ctime` datetime(0) NOT NULL COMMENT '添加时间',
  `utime` datetime(0) DEFAULT NULL COMMENT '更新时间',
  `sno` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '设备序列号',
  `service_mode_id` int(11) NOT NULL COMMENT ' 收费模式ID',
  `sys_user_id` int(11) DEFAULT NULL COMMENT '操作人员ID',
  `sys_user_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '操作人员名称',
  `is_deleted` int(1) DEFAULT 0 COMMENT '是否删除：0未删除 1删除',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_sno_mode`(`sno`, `service_mode_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '设备收费模式表(多对多)' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for feedback_business
-- ----------------------------
DROP TABLE IF EXISTS `feedback_business`;
CREATE TABLE `feedback_business`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键,自增长',
  `ctime` datetime(0) NOT NULL COMMENT '添加时间',
  `utime` datetime(0) DEFAULT NULL COMMENT '更新时间',
  `username` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用户账号',
  `nick_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用户昵称',
  `mobile` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '发件人手机号',
  `avatar` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '头像地址',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '内容',
  `picture_url` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '图片地址',
  `picture_num` int(2) DEFAULT NULL COMMENT '图片数',
  `user_id` int(11) DEFAULT NULL COMMENT '系统用户ID',
  `recipient_id` int(11) DEFAULT NULL COMMENT '收件人ID',
  `recipient_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '收件人名称',
  `is_read` int(1) NOT NULL DEFAULT 0 COMMENT '是否已读：0 未读，1已读',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `recipient_id_index`(`recipient_id`) USING BTREE,
  INDEX `user_id_index`(`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '业务系统表' ROW_FORMAT = Dynamic;

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

-- ----------------------------
-- Table structure for install_fee_order
-- ----------------------------
DROP TABLE IF EXISTS `install_fee_order`;
CREATE TABLE `install_fee_order`  (
  `order_no` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `sno` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '设备sno',
  `mac` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '设备mac',
  `rule_id` int(11) NOT NULL COMMENT '规则id',
  `product_id` int(11) NOT NULL COMMENT '适应产品id',
  `fee` decimal(10, 2) NOT NULL COMMENT '装机金额',
  `status` tinyint(2) NOT NULL COMMENT '状态',
  `pay_type` tinyint(2) DEFAULT NULL COMMENT '支付类型',
  `pay_time` datetime(0) DEFAULT NULL COMMENT '支付时间',
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `sys_user_id` int(11) NOT NULL COMMENT '用户id',
  `ctime` datetime(0) DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `utime` datetime(0) DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `del` bit(1) DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`order_no`) USING BTREE,
  INDEX `idx_sno`(`sno`) USING BTREE,
  INDEX `idx_mac`(`mac`) USING BTREE,
  INDEX `idx_rule_id`(`rule_id`) USING BTREE,
  INDEX `idx_product_id`(`product_id`) USING BTREE,
  INDEX `idx_user_id`(`user_id`) USING BTREE,
  INDEX `idx_sys_user_id`(`sys_user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '初装费订单' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for install_fee_rule
-- ----------------------------
DROP TABLE IF EXISTS `install_fee_rule`;
CREATE TABLE `install_fee_rule`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '规则名称',
  `product_id` int(11) NOT NULL COMMENT '适应产品id',
  `fee` decimal(10, 2) NOT NULL COMMENT '装机金额',
  `sys_user_id` int(11) NOT NULL COMMENT '创建人id',
  `ctime` datetime(0) DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `utime` datetime(0) DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `del` bit(1) DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_product_id`(`product_id`) USING BTREE,
  INDEX `idx_sys_user_id`(`sys_user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '初装费规则' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for install_fee_rule_to_agent
-- ----------------------------
DROP TABLE IF EXISTS `install_fee_rule_to_agent`;
CREATE TABLE `install_fee_rule_to_agent`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `rule_id` int(11) NOT NULL COMMENT '规则id',
  `agent_id` int(11) NOT NULL COMMENT '代理商id',
  `sys_account_id` int(11) NOT NULL COMMENT '代理商管理员id',
  `sys_user_id` int(11) NOT NULL COMMENT '创建人id',
  `ctime` datetime(0) DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `utime` datetime(0) DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `del` bit(1) DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_rule_id`(`rule_id`) USING BTREE,
  INDEX `idx_agent_id`(`agent_id`) USING BTREE,
  INDEX `idx_sys_account_id`(`sys_account_id`) USING BTREE,
  INDEX `idx_sys_user_id`(`sys_user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '初装费规则关联代理商' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for install_fee_rule_to_operator
-- ----------------------------
DROP TABLE IF EXISTS `install_fee_rule_to_operator`;
CREATE TABLE `install_fee_rule_to_operator`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `rule_id` int(11) NOT NULL COMMENT '规则id',
  `operator_id` int(11) NOT NULL COMMENT '运营商id',
  `sys_account_id` int(11) NOT NULL COMMENT '运营商管理员id',
  `sys_user_id` int(11) NOT NULL COMMENT '创建人id',
  `ctime` datetime(0) DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `utime` datetime(0) DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `del` bit(1) DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_rule_id`(`rule_id`) USING BTREE,
  INDEX `idx_operator_id`(`operator_id`) USING BTREE,
  INDEX `idx_sys_account_id`(`sys_account_id`) USING BTREE,
  INDEX `idx_sys_user_id`(`sys_user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '初装费规则关联运营商' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for manufacturer
-- ----------------------------
DROP TABLE IF EXISTS `manufacturer`;
CREATE TABLE `manufacturer`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键,自增长',
  `ctime` datetime(0) NOT NULL COMMENT '添加时间',
  `utime` datetime(0) DEFAULT NULL COMMENT '更新时间',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '企业名称',
  `industry` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '所属行业',
  `web_site` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '公司官网',
  `sub_domain` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '子域名',
  `logo_url` varchar(150) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '公司logo url',
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '企业电话',
  `mobile` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '手机号码',
  `contact` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '联系人',
  `department` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '部门',
  `email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '电子邮件',
  `qq` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'QQ号码',
  `province` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '省',
  `city` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '市',
  `area` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '区/县',
  `address` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '详细地址',
  `parent_manufacturer_id` int(11) DEFAULT NULL COMMENT '父级企业ID',
  `sys_user_id` int(11) DEFAULT NULL COMMENT '创建人',
  `enterprise_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '企业id,添加企业时手工录入',
  `enterprise_secret` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '企业secret,添加企业时手动录入',
  `is_deleted` int(1) NOT NULL DEFAULT 0 COMMENT '是否删除，0,未删除 1,已删除',
  `sys_account_id` int(11) DEFAULT NULL COMMENT '厂商绑定的系统帐号',
  `sys_user_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建人名称',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `manufacturer_sys_account_id_uindex`(`sys_account_id`) USING BTREE,
  INDEX `idx_name`(`name`) USING BTREE,
  INDEX `idx_contact`(`contact`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '厂商(或企业)表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for message_template
-- ----------------------------
DROP TABLE IF EXISTS `message_template`;
CREATE TABLE `message_template`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键,自增长',
  `ctime` datetime(0) NOT NULL COMMENT '添加时间',
  `utime` datetime(0) DEFAULT NULL COMMENT '更新时间',
  `title` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '标题',
  `content` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '消息模版内容',
  `command` varchar(450) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '触发条件',
  `type` int(1) NOT NULL COMMENT '消息模版类型：2故障消息  4设备消息 5 租赁消息',
  `product_id` int(11) NOT NULL COMMENT '产品id',
  `sys_user_id` int(11) NOT NULL COMMENT '创建人',
  `sys_user_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建人名称',
  `is_deleted` int(1) NOT NULL DEFAULT 0 COMMENT '是否删除：0,否；1,是',
  `service_id` int(11) DEFAULT NULL COMMENT '当提醒类型为租赁类型时：填写收费模式id',
  `service_name` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '当提醒类型为租赁类型时：填写收费模式名称',
  `depend_on_data_point` int(1) NOT NULL DEFAULT 0 COMMENT '是否依靠数据点上报：0不依靠，1依靠',
  `rate` double DEFAULT 0 COMMENT '当不依靠数据点时所设判断条件:百分比/数值',
  `rate_type` int(1) DEFAULT 1 COMMENT '当不依靠数据点时所设判断条件: 1 百分比 2数值',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `index_productId`(`product_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '消息模版表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for order_base
-- ----------------------------
DROP TABLE IF EXISTS `order_base`;
CREATE TABLE `order_base`  (
  `order_no` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '主键,订单号,按照一定规则生成',
  `ctime` datetime(0) NOT NULL COMMENT '添加时间',
  `utime` datetime(0) DEFAULT NULL COMMENT '更新时间',
  `sno` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '对应设备序列号',
  `mac` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '对应设备MAC',
  `command` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '发货指令',
  `service_mode_id` int(11) NOT NULL COMMENT '服务方式ID',
  `service_mode_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '服务方名称',
  `order_status` int(2) NOT NULL COMMENT '订单状态,-1:过期 0:创建 1:支付中 2:支付完成 3:支付失败 4:使用中 5:订单完成 6:退款中 7:已退款 8:退款失败',
  `pay_time` datetime(0) DEFAULT NULL COMMENT '订单支付时间',
  `pay_type` int(2) NOT NULL DEFAULT 1 COMMENT '支付类型:1,公众号支付;2,微信APP支付;3,支付宝支付;4,充值卡支付;5,钱包支付,6微信支付,7微信H5支付',
  `trade_no` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '支付订单号',
  `transaction_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '支付系统生成的交易号,用于退款处理',
  `amount` double(12, 2) NOT NULL DEFAULT 0.00 COMMENT '订单总价',
  `user_id` int(11) DEFAULT NULL COMMENT '用户ID',
  `user_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '用户姓名',
  `sys_user_id` int(11) DEFAULT NULL COMMENT '订单所属的微信运营配置中的sys_user_id',
  `is_deleted` int(1) DEFAULT NULL,
  `promotion_money` double(11, 2) DEFAULT NULL COMMENT '订单的优惠金额，与real_money合起来就是amount，此部分不参与分润',
  `pay_card_num` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '支付方式为充值卡支付时的卡号',
  `is_archive` int(1) NOT NULL DEFAULT 0 COMMENT '是否归档，解绑后设置：0,未归档 1,归档',
  `service_mode_detail_id` int(11) DEFAULT NULL COMMENT '具体的服务方式ID',
  `service_start_time` datetime(0) DEFAULT NULL COMMENT '服务开始时间',
  `service_end_time` datetime(0) DEFAULT NULL COMMENT '服务结束时间（预计）',
  `early_end` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否提前结束',
  `early_end_time` datetime(0) DEFAULT NULL COMMENT '提前结束时间',
  `refund` decimal(12, 2) NOT NULL DEFAULT 0.00 COMMENT '退款金额',
  `refund_version` int(11) NOT NULL DEFAULT 0 COMMENT '退款版本号',
  `launch_area_id` int(11) DEFAULT NULL COMMENT '投放点id',
  `launch_area_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '投放点名称',
  `abnormal_reason` tinyint(2) DEFAULT NULL COMMENT '异常原因',
  `original_price` decimal(12, 2) DEFAULT NULL COMMENT '订单(商品)原价，优惠前的价格',
  `card_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '使用的卡券ID',
  `card_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '使用的卡券Code',
  `card_discount` decimal(12, 2) DEFAULT NULL COMMENT '卡券优惠金额',
  `renew_order_no` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '续费单号',
  `remark` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `tree_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用户路径',
  `end_type` int(1) DEFAULT 1 COMMENT '订单结束方式：1正常结束 2job定时结束',
  `refund_result` varchar(450) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '退款理由',
  PRIMARY KEY (`order_no`) USING BTREE,
  INDEX `idx_ctime`(`ctime`) USING BTREE,
  INDEX `idx_user_id`(`user_id`) USING BTREE,
  INDEX `index_sys_user_id_ctime`(`sys_user_id`, `ctime`) USING BTREE,
  INDEX `index_sno_ctime`(`sno`, `ctime`) USING BTREE,
  INDEX `index_order_status`(`order_status`, `order_no`) USING BTREE,
  INDEX `index_user_id`(`user_id`, `order_status`, `ctime`) USING BTREE,
  INDEX `index_sys_user_id`(`ctime`, `order_status`, `sys_user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '订单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for order_data_flow
-- ----------------------------
DROP TABLE IF EXISTS `order_data_flow`;
CREATE TABLE `order_data_flow`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `order_no` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '对应的订单id',
  `sno` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '对应的设备sno',
  `mac` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '对应的设备mac',
  `route` int(1) NOT NULL COMMENT '数据方向：1业务云到设备，2设备到业务云',
  `type` int(2) NOT NULL COMMENT '类型：1设备原状态，2下发的指令，3设备使用中，4设备异常，5设备结束使用，6设备其他上报',
  `data` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '数据内容',
  `remark` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
  `sys_user_id` int(11) NOT NULL COMMENT '设备拥有者',
  `ctime` datetime(0) DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_order_no`(`order_no`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '订单指令跟踪表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for order_ext_by_quantity
-- ----------------------------
DROP TABLE IF EXISTS `order_ext_by_quantity`;
CREATE TABLE `order_ext_by_quantity`  (
  `order_no` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '主键,与订单保持一致',
  `ctime` datetime(0) NOT NULL COMMENT '添加时间',
  `utime` datetime(0) DEFAULT NULL COMMENT '更新时间',
  `quantity` double(7, 2) NOT NULL COMMENT '购买的量',
  `price` double(6, 2) NOT NULL COMMENT '单价,元',
  `unit` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '单位,立方',
  `service_type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '服务类型',
  `last_rest` double(7, 2) DEFAULT NULL COMMENT '如果存在续费单：上个订单剩余量',
  `last_used` double(7, 2) DEFAULT NULL COMMENT '如果存在续费单：上个订单已使用量',
  PRIMARY KEY (`order_no`) USING BTREE,
  UNIQUE INDEX `order_no`(`order_no`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '订单扩展记录表(按量)' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for order_ext_by_time
-- ----------------------------
DROP TABLE IF EXISTS `order_ext_by_time`;
CREATE TABLE `order_ext_by_time`  (
  `order_no` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '主键,与订单保持一致',
  `ctime` datetime(0) NOT NULL COMMENT '添加时间',
  `utime` datetime(0) DEFAULT NULL COMMENT '更新时间',
  `start_time` datetime(0) NOT NULL COMMENT '服务开始时间',
  `end_time` datetime(0) NOT NULL COMMENT '服务结束时间',
  `duration` double(5, 2) NOT NULL COMMENT '购买时长',
  `price` double(6, 2) NOT NULL COMMENT '单价,元',
  `unit` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '单位,分钟/小时/天',
  PRIMARY KEY (`order_no`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '订单扩展表(按时)' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for order_ext_port
-- ----------------------------
DROP TABLE IF EXISTS `order_ext_port`;
CREATE TABLE `order_ext_port`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键,自增长',
  `ctime` datetime(0) NOT NULL COMMENT '添加时间',
  `utime` datetime(0) DEFAULT NULL COMMENT '更新时间',
  `order_no` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '订单号',
  `sno` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '设备sno',
  `port` int(1) DEFAULT NULL COMMENT '出水口号',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `sno_index`(`sno`) USING BTREE,
  INDEX `order_no`(`order_no`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '订单扩展表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for order_pay_record
-- ----------------------------
DROP TABLE IF EXISTS `order_pay_record`;
CREATE TABLE `order_pay_record`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `order_no` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '订单号',
  `ctime` datetime(0) NOT NULL,
  `utime` datetime(0) DEFAULT NULL COMMENT '更新时间',
  `pay_type` int(2) NOT NULL COMMENT '支付类型:1,公众号支付;2,微信APP支付;3,支付宝支付;4,充值卡支付',
  `params` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '支付提交参数',
  `status` int(2) DEFAULT 1 COMMENT '订单状态,0:创建 1:支付中 2:支付完成 3:服务中 4:订单完成 5:订单失败',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '订单支付记录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for order_share_profit
-- ----------------------------
DROP TABLE IF EXISTS `order_share_profit`;
CREATE TABLE `order_share_profit`  (
  `order_no` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '订单号',
  `share_profit_level` tinyint(4) NOT NULL COMMENT '分润层级 总部:1',
  `share_profit_user` int(11) NOT NULL COMMENT '分润对象用户',
  `order_money` decimal(11, 2) NOT NULL COMMENT '订单金额',
  `share_profit_percent` decimal(6, 2) DEFAULT NULL COMMENT '分润比例 0~100',
  `share_money` decimal(11, 2) DEFAULT NULL COMMENT '分润金额',
  `share_profit_bill_no` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '分润账单号',
  `ctime` datetime(0) NOT NULL COMMENT '创建时间',
  `utime` datetime(0) DEFAULT NULL COMMENT '更新时间',
  `status` int(1) DEFAULT NULL COMMENT '分润状态：2待分润，4分润成功 5分润失败',
  `pay_type` int(1) DEFAULT NULL COMMENT '支付类型',
  `trade_no` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '分润执行商户订单号',
  `payment_no` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '分润成功时的微信订单号',
  `payment_time` datetime(0) DEFAULT NULL COMMENT '微信付款成功时间',
  `is_try_again` int(1) NOT NULL DEFAULT 0 COMMENT '是否使用trade_no重试支付，如果此字段为1，则分润单不可修改，只再次支付',
  `is_generate` int(1) DEFAULT 0 COMMENT '是否已生成分润单',
  `personal` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '归属人姓名',
  `share_benefit_result` varchar(1150) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '分润结果',
  PRIMARY KEY (`order_no`, `share_profit_level`) USING BTREE,
  INDEX `level_index`(`share_profit_level`) USING BTREE,
  INDEX `trade_no_index`(`trade_no`) USING BTREE,
  INDEX `user_status_index`(`share_profit_user`, `status`) USING BTREE,
  INDEX `order_no_index`(`order_no`, `share_profit_user`, `is_generate`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '订单的分润信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for order_status_flow
-- ----------------------------
DROP TABLE IF EXISTS `order_status_flow`;
CREATE TABLE `order_status_flow`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键,自增',
  `order_no` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `pre_status` int(2) DEFAULT NULL COMMENT '订单前置状态',
  `now_status` int(2) NOT NULL COMMENT '当前状态',
  `ctime` datetime(0) NOT NULL,
  `creator_id` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '操作者',
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_order_no`(`order_no`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '订单状态流转表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for order_timer
-- ----------------------------
DROP TABLE IF EXISTS `order_timer`;
CREATE TABLE `order_timer`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `order_no` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '关联订单号',
  `sno` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '设备SNO',
  `week_day` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '定时周几执行，多个日期用逗号隔开：1周一；2周二；3周三；4周四；5周五；6周六；7周日',
  `time` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '执行时间，24小时制，如: 14:30:00',
  `is_enable` int(1) NOT NULL DEFAULT 1 COMMENT '是否启用：0，否；1，是',
  `is_expire` int(1) NOT NULL DEFAULT 0 COMMENT '是否过期，订单过期后所有定时无效',
  `is_deleted` int(1) NOT NULL DEFAULT 0 COMMENT '是否删除：0，否；1，是',
  `command` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '控制指令内容',
  `ctime` datetime(0) NOT NULL COMMENT '创建时间',
  `utime` datetime(0) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `order_timer_order_no_index`(`order_no`) USING BTREE,
  INDEX `order_timer_device_sno_index`(`sno`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '订单定时任务' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for panel
-- ----------------------------
DROP TABLE IF EXISTS `panel`;
CREATE TABLE `panel`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `utime` datetime(0) DEFAULT NULL COMMENT '更新时间',
  `ctime` datetime(0) NOT NULL COMMENT '创建时间',
  `type` int(11) NOT NULL COMMENT '1.数据，2.图表',
  `module` int(11) NOT NULL COMMENT '1.设备分析，2.用户分析，3.订单分析',
  `module_item` int(11) NOT NULL COMMENT '模块中的具体项，比如当前设备总数',
  `sort` int(11) NOT NULL DEFAULT 0 COMMENT '排序',
  `uri` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '图表数据时前端请求的服务uri',
  `condition` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '后续使用的条件等信息',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_resolve_item`(`type`, `module_item`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 42 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of panel
-- ----------------------------
INSERT INTO `panel` VALUES (1, '2017-07-15 16:55:58', '2017-07-15 16:56:01', 1, 1, 1, 1, NULL, NULL);
INSERT INTO `panel` VALUES (2, '2017-07-15 16:56:37', '2017-07-15 16:56:39', 1, 1, 2, 2, NULL, NULL);
INSERT INTO `panel` VALUES (3, '2017-07-15 16:56:48', '2017-07-15 16:56:51', 1, 1, 3, 3, NULL, NULL);
INSERT INTO `panel` VALUES (4, '2017-07-15 16:57:15', '2017-07-15 16:57:18', 1, 1, 4, 4, NULL, NULL);
INSERT INTO `panel` VALUES (5, '2017-07-15 16:57:29', '2017-07-15 16:57:31', 1, 2, 5, 12, NULL, NULL);
INSERT INTO `panel` VALUES (7, '2017-07-15 16:58:05', '2017-07-15 16:58:07', 1, 2, 7, 13, NULL, NULL);
INSERT INTO `panel` VALUES (8, '2017-07-15 16:58:20', '2017-07-15 16:58:22', 1, 2, 8, 14, NULL, NULL);
INSERT INTO `panel` VALUES (9, '2017-07-15 16:58:33', '2017-07-15 16:58:35', 1, 3, 9, 18, NULL, NULL);
INSERT INTO `panel` VALUES (10, '2017-07-15 16:58:54', '2017-07-15 16:58:56', 1, 3, 10, 19, NULL, NULL);
INSERT INTO `panel` VALUES (11, '2017-07-15 16:59:08', '2017-07-15 16:59:10', 1, 3, 11, 20, NULL, NULL);
INSERT INTO `panel` VALUES (12, '2017-07-15 16:59:21', '2017-07-15 16:59:23', 1, 3, 12, 21, NULL, NULL);
INSERT INTO `panel` VALUES (13, '2017-07-15 16:59:21', '2017-07-15 16:59:23', 1, 4, 13, 22, NULL, NULL);
INSERT INTO `panel` VALUES (14, '2017-07-15 16:59:21', '2017-07-15 16:59:23', 1, 4, 14, 23, NULL, NULL);
INSERT INTO `panel` VALUES (15, '2017-07-15 16:59:21', '2017-07-15 16:59:23', 1, 4, 15, 24, NULL, NULL);
INSERT INTO `panel` VALUES (16, '2017-07-15 16:59:37', '2017-07-15 16:59:39', 2, 1, 16, 27, NULL, NULL);
INSERT INTO `panel` VALUES (17, '2017-07-15 16:59:51', '2017-07-15 16:59:53', 2, 1, 17, 28, NULL, NULL);
INSERT INTO `panel` VALUES (18, '2017-07-15 17:00:01', '2017-07-15 17:00:03', 2, 1, 18, 29, NULL, NULL);
INSERT INTO `panel` VALUES (19, '2017-07-15 17:00:17', '2017-07-15 17:00:19', 2, 1, 19, 30, NULL, NULL);
INSERT INTO `panel` VALUES (20, '2017-07-15 17:00:32', '2017-07-15 17:00:34', 2, 1, 20, 31, NULL, NULL);
INSERT INTO `panel` VALUES (22, '2017-07-15 17:00:59', '2017-07-15 17:01:01', 2, 2, 22, 32, NULL, NULL);
INSERT INTO `panel` VALUES (23, '2017-07-15 17:01:23', '2017-07-15 17:01:25', 2, 2, 23, 33, NULL, NULL);
INSERT INTO `panel` VALUES (24, '2017-07-15 17:01:38', '2017-07-15 17:01:40', 2, 2, 24, 34, NULL, NULL);
INSERT INTO `panel` VALUES (25, '2017-07-15 17:01:53', '2017-07-15 17:01:55', 2, 2, 25, 35, NULL, NULL);
INSERT INTO `panel` VALUES (26, '2017-07-15 17:02:04', '2017-07-15 17:02:06', 2, 2, 26, 36, NULL, NULL);
INSERT INTO `panel` VALUES (27, '2017-07-15 17:02:28', '2017-07-15 17:02:30', 2, 2, 27, 37, NULL, NULL);
INSERT INTO `panel` VALUES (28, '2017-07-15 17:03:28', '2017-07-15 17:03:30', 2, 2, 28, 38, NULL, NULL);
INSERT INTO `panel` VALUES (29, '2017-07-15 17:03:44', '2017-07-15 17:03:46', 2, 3, 29, 39, NULL, NULL);
INSERT INTO `panel` VALUES (30, '2017-07-15 17:04:08', '2017-07-15 17:04:10', 2, 3, 30, 40, NULL, NULL);
INSERT INTO `panel` VALUES (31, '2017-08-18 17:27:15', '2017-08-18 17:27:17', 1, 5, 31, 25, NULL, NULL);
INSERT INTO `panel` VALUES (32, '2017-08-18 17:27:42', '2017-08-18 17:27:45', 1, 5, 32, 26, NULL, NULL);
INSERT INTO `panel` VALUES (33, '2018-01-22 15:26:07', '2018-01-22 15:26:07', 1, 1, 33, 6, NULL, NULL);
INSERT INTO `panel` VALUES (34, '2018-01-22 15:26:07', '2018-01-22 15:26:07', 1, 1, 34, 7, NULL, NULL);
INSERT INTO `panel` VALUES (36, '2018-03-07 15:47:04', '2018-03-07 15:47:06', 1, 2, 36, 15, NULL, NULL);
INSERT INTO `panel` VALUES (39, '2018-03-07 18:45:44', '2018-03-07 18:45:45', 1, 1, 42, 5, NULL, NULL);
INSERT INTO `panel` VALUES (40, '2018-03-07 16:05:15', '2018-03-07 16:05:16', 1, 2, 40, 16, NULL, NULL);
INSERT INTO `panel` VALUES (41, '2018-03-07 16:05:29', '2018-03-07 16:05:30', 1, 2, 41, 17, NULL, NULL);

-- ----------------------------
-- Table structure for personal_panel
-- ----------------------------
DROP TABLE IF EXISTS `personal_panel`;
CREATE TABLE `personal_panel`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `ctime` datetime(0) NOT NULL COMMENT '创建时间',
  `utime` datetime(0) DEFAULT NULL COMMENT '更新时间',
  `user_id` int(11) NOT NULL COMMENT '系统账号id',
  `panel_id` int(11) NOT NULL COMMENT '面板项id',
  `item_value` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '定时任务回写的值',
  `is_show` int(1) NOT NULL DEFAULT 0 COMMENT '是否显示，0.不显示，1.显示',
  `sort` int(11) NOT NULL COMMENT '排序',
  `item_odd` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '附属值',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_user_id_panel_id`(`user_id`, `panel_id`) USING BTREE,
  INDEX `idx_user_id`(`user_id`) USING BTREE COMMENT '用户id'
) ENGINE = InnoDB AUTO_INCREMENT = 20317 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of personal_panel
-- ----------------------------
INSERT INTO `personal_panel` VALUES (20263, '2019-11-14 10:53:14', NULL, 596, 1, NULL, 1, 1, NULL);
INSERT INTO `personal_panel` VALUES (20264, '2019-11-14 10:53:14', NULL, 596, 2, NULL, 1, 2, NULL);
INSERT INTO `personal_panel` VALUES (20265, '2019-11-14 10:53:14', NULL, 596, 3, NULL, 0, 3, NULL);
INSERT INTO `personal_panel` VALUES (20266, '2019-11-14 10:53:14', NULL, 596, 4, NULL, 1, 4, NULL);
INSERT INTO `personal_panel` VALUES (20267, '2019-11-14 10:53:14', NULL, 596, 5, NULL, 1, 12, NULL);
INSERT INTO `personal_panel` VALUES (20268, '2019-11-14 10:53:14', NULL, 596, 7, NULL, 0, 13, NULL);
INSERT INTO `personal_panel` VALUES (20269, '2019-11-14 10:53:14', NULL, 596, 8, NULL, 0, 14, NULL);
INSERT INTO `personal_panel` VALUES (20270, '2019-11-14 10:53:14', NULL, 596, 9, NULL, 0, 18, NULL);
INSERT INTO `personal_panel` VALUES (20271, '2019-11-14 10:53:14', NULL, 596, 10, NULL, 0, 19, NULL);
INSERT INTO `personal_panel` VALUES (20272, '2019-11-14 10:53:14', NULL, 596, 11, NULL, 0, 20, NULL);
INSERT INTO `personal_panel` VALUES (20273, '2019-11-14 10:53:14', NULL, 596, 12, NULL, 0, 21, NULL);
INSERT INTO `personal_panel` VALUES (20274, '2019-11-14 10:53:14', NULL, 596, 13, NULL, 0, 22, NULL);
INSERT INTO `personal_panel` VALUES (20275, '2019-11-14 10:53:14', NULL, 596, 14, NULL, 0, 23, NULL);
INSERT INTO `personal_panel` VALUES (20276, '2019-11-14 10:53:14', NULL, 596, 15, NULL, 0, 24, NULL);
INSERT INTO `personal_panel` VALUES (20277, '2019-11-14 10:53:14', NULL, 596, 16, NULL, 1, 27, NULL);
INSERT INTO `personal_panel` VALUES (20279, '2019-11-14 10:53:14', NULL, 596, 17, NULL, 1, 28, NULL);
INSERT INTO `personal_panel` VALUES (20280, '2019-11-14 10:53:14', NULL, 596, 18, NULL, 0, 29, NULL);
INSERT INTO `personal_panel` VALUES (20281, '2019-11-14 10:53:14', NULL, 596, 19, NULL, 1, 30, NULL);
INSERT INTO `personal_panel` VALUES (20282, '2019-11-14 10:53:14', NULL, 596, 20, NULL, 0, 31, NULL);
INSERT INTO `personal_panel` VALUES (20283, '2019-11-14 10:53:14', NULL, 596, 22, NULL, 0, 32, NULL);
INSERT INTO `personal_panel` VALUES (20284, '2019-11-14 10:53:14', NULL, 596, 23, NULL, 0, 33, NULL);
INSERT INTO `personal_panel` VALUES (20286, '2019-11-14 10:53:14', NULL, 596, 24, NULL, 0, 34, NULL);
INSERT INTO `personal_panel` VALUES (20288, '2019-11-14 10:53:14', NULL, 596, 25, NULL, 0, 35, NULL);
INSERT INTO `personal_panel` VALUES (20290, '2019-11-14 10:53:14', NULL, 596, 26, NULL, 0, 36, NULL);
INSERT INTO `personal_panel` VALUES (20292, '2019-11-14 10:53:14', NULL, 596, 27, NULL, 0, 37, NULL);
INSERT INTO `personal_panel` VALUES (20294, '2019-11-14 10:53:14', NULL, 596, 28, NULL, 0, 38, NULL);
INSERT INTO `personal_panel` VALUES (20296, '2019-11-14 10:53:14', NULL, 596, 29, NULL, 0, 39, NULL);
INSERT INTO `personal_panel` VALUES (20298, '2019-11-14 10:53:14', NULL, 596, 30, NULL, 0, 40, NULL);
INSERT INTO `personal_panel` VALUES (20300, '2019-11-14 10:53:14', NULL, 596, 31, NULL, 0, 25, NULL);
INSERT INTO `personal_panel` VALUES (20302, '2019-11-14 10:53:14', NULL, 596, 32, NULL, 0, 26, NULL);
INSERT INTO `personal_panel` VALUES (20304, '2019-11-14 10:53:14', NULL, 596, 33, NULL, 0, 6, NULL);
INSERT INTO `personal_panel` VALUES (20308, '2019-11-14 10:53:14', NULL, 596, 34, NULL, 0, 7, NULL);
INSERT INTO `personal_panel` VALUES (20310, '2019-11-14 10:53:14', NULL, 596, 36, NULL, 1, 15, NULL);
INSERT INTO `personal_panel` VALUES (20312, '2019-11-14 10:53:14', NULL, 596, 39, NULL, 0, 5, NULL);
INSERT INTO `personal_panel` VALUES (20314, '2019-11-14 10:53:14', NULL, 596, 40, NULL, 0, 16, NULL);
INSERT INTO `personal_panel` VALUES (20316, '2019-11-14 10:53:14', NULL, 596, 41, NULL, 0, 17, NULL);

-- ----------------------------
-- Table structure for product
-- ----------------------------
DROP TABLE IF EXISTS `product`;
CREATE TABLE `product`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键,自增长',
  `ctime` datetime(0) NOT NULL COMMENT '添加时间',
  `utime` datetime(0) DEFAULT NULL COMMENT '更新时间',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '产品名称',
  `img_url` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '产品图片地址',
  `gizwits_product_key` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '机智云产品key',
  `gizwits_product_secret` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '机智云产品secret',
  `status` int(2) NOT NULL DEFAULT 1 COMMENT '产品状态,1:启用, 0:禁用',
  `category_id` int(5) DEFAULT NULL COMMENT '产品类型',
  `category_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '产品类型名称',
  `manufacturer_id` int(11) NOT NULL COMMENT '所属厂商的关联的系统账号',
  `brand_id` int(11) DEFAULT NULL COMMENT '所属品牌',
  `communicate_type` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '通讯方式',
  `sys_user_id` int(11) NOT NULL COMMENT '创建人',
  `is_deleted` int(1) NOT NULL DEFAULT 0 COMMENT '删除状态，0:未删除,1:已删除',
  `gizwits_enterprise_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '机智云平台企业id',
  `gizwits_enterprise_secret` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `auth_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '产品授权id',
  `auth_secret` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '产品授权密钥',
  `subkey` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用于消息分发，唯一即可',
  `events` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '消息类型，发送给机智云平台',
  `qrcode_type` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT 'WEB' COMMENT '生成二维码的方式:WEB,网页链接;WEIXIN,微信硬件;',
  `location_type` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT 'GIZWITS' COMMENT '获取设备坐标方式:GIZWITS,机智云接口;GD,高德接口(需要相关数据点支撑)',
  `gizwits_appid` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '机智云AppId',
  `gizwits_appsecret` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `wx_product_id` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '微信硬件productId',
  `network_type` int(2) DEFAULT 0 COMMENT '通信类型，0.移动；1.联通',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_category`(`category_id`) USING BTREE,
  INDEX `idx_product_key`(`gizwits_product_key`) USING BTREE,
  INDEX `idx_sys_user_id`(`sys_user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '产品表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of product
-- ----------------------------
INSERT INTO `product` VALUES (1, '2019-10-10 11:38:05', '2019-10-10 11:38:05', '新风GPRS', NULL, '6e4de07730dc463082c370366ecee419', '7706248a20bf4dd6b9594934d0b1a859', 1, 10, '布朗', 596, NULL, NULL, 596, 0, '97736a4a433c4d918755f2b885ac9e7f', '6b90e78942d5426492f75cbb7bc32358', 'ewbgy3iaTaiEzYU5UKDtuw', 'CnOzNImxT06Js7un6sTAqw', 'GPRS_test', 'device.status.kv,device.online,device.offline,device.attr_fault,device.attr_alert', 'WEB', 'GD', 'cc79143f75d84c069d3e9f23ebbefca3', 'd90ec012fa8a4f6d85bf93391902388c', NULL, 0, NULL);
INSERT INTO `product` VALUES (2, '2019-12-10 09:39:49', '2019-12-10 09:39:52', '新风lv2', NULL, '05519d02a8ce42189527d856a9be416d', '5d08c1d7628b4e7bb15538e77508c9ab', 1, 10, NULL, 596, NULL, NULL, 596, 0, '97736a4a433c4d918755f2b885ac9e7f', '6b90e78942d5426492f75cbb7bc32358', NULL, NULL, NULL, NULL, 'WEB', 'GIZWITS', NULL, NULL, NULL, 0, NULL);

-- ----------------------------
-- Table structure for product_category
-- ----------------------------
DROP TABLE IF EXISTS `product_category`;
CREATE TABLE `product_category`  (
  `id` int(5) NOT NULL AUTO_INCREMENT COMMENT '主键,自增长',
  `ctime` datetime(0) NOT NULL COMMENT '添加时间',
  `utime` datetime(0) DEFAULT NULL COMMENT '更新时间',
  `name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '类别名称',
  `remark` varchar(225) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '描述',
  `parent_category_id` int(5) DEFAULT NULL COMMENT '父级类别ID',
  `sys_user_id` int(11) NOT NULL COMMENT '创建人',
  `sys_user_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '创建人登录名',
  `category_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '产品型号',
  `category_id` int(10) DEFAULT NULL COMMENT '品类Id',
  `category_count` int(10) DEFAULT NULL COMMENT '安全库存数量',
  `is_deleted` int(1) NOT NULL DEFAULT 0 COMMENT '是否删除：0未删除 1已删除',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `index_id`(`parent_category_id`, `sys_user_id`) USING BTREE,
  INDEX `idx_sys_user_id`(`sys_user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '产品类型' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of product_category
-- ----------------------------
INSERT INTO `product_category` VALUES (10, '2019-11-14 14:28:34', '2019-11-14 14:28:34', '测试', 'ajsdkajskfjaksjfkajskjfakjskasjkfj', 0, 596, 'suzhou', 'Y100', 1, 1000, 0);

-- ----------------------------
-- Table structure for product_command_config
-- ----------------------------
DROP TABLE IF EXISTS `product_command_config`;
CREATE TABLE `product_command_config`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `product_id` int(11) NOT NULL COMMENT '产品ID',
  `command_type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '指令类型：SERVICE,收费类型指令；CONTROL,控制类型指令；STATUS,状态类型指令',
  `status_command_type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '状态指令类型,只有在command_type为STATUS时有值：FREE,空闲指令；USING,使用中指令；FINISH,设备使用完成指令',
  `name` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '指令名称',
  `command` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '下发指令',
  `is_show` int(1) NOT NULL DEFAULT 1 COMMENT '是否在后台展示：0,不展示；1,展示',
  `ctime` datetime(0) NOT NULL,
  `utime` datetime(0) DEFAULT NULL,
  `is_deleted` int(1) DEFAULT 0 COMMENT '是否删除：0,否；1,删除',
  `is_free` int(1) NOT NULL DEFAULT 0 COMMENT '是否免费，0，收费，1，免费',
  `working_mode` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '工作模式',
  `is_clock_correct` int(1) NOT NULL DEFAULT 0 COMMENT '是否需要时钟校准,0 否,1 是',
  `calculate_value` int(11) DEFAULT NULL COMMENT '换算数据点单位',
  `error_range` int(11) DEFAULT NULL COMMENT '误差范围',
  `clock_correct_datapoint` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '时钟校准的数据点',
  `identity_name` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '数据点标识名',
  `ref_dp` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '续费时参考的数据点',
  `show_type` int(1) DEFAULT 1 COMMENT '展示形式：1 文本 2饼状图 3进度条',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `index_product_id`(`product_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '产品指令配置表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for product_data_point
-- ----------------------------
DROP TABLE IF EXISTS `product_data_point`;
CREATE TABLE `product_data_point`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键,自增长',
  `product_id` int(11) NOT NULL COMMENT '产品ID',
  `ctime` datetime(0) NOT NULL COMMENT '添加时间',
  `utime` datetime(0) DEFAULT NULL COMMENT '更新时间',
  `show_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '显示名称',
  `identity_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '标志名称',
  `read_write_type` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '读写类型',
  `data_type` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '数据类型',
  `remark` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
  `value_limit` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '值，比如枚举，数值，布尔等，从接口返回中解析',
  `is_monit` int(1) NOT NULL DEFAULT 1 COMMENT '监控数据点: 0,不监控;1,监控',
  `device_alarm_rank` int(1) DEFAULT 1 COMMENT '1:级别1；2:级别2；3:级别3...',
  `is_deleted` int(1) DEFAULT 0 COMMENT '是否删除：0未删除 1已删除',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_identity`(`identity_name`, `product_id`) USING BTREE,
  INDEX `index_product_id`(`product_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '产品数据点' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for product_data_point_ext
-- ----------------------------
DROP TABLE IF EXISTS `product_data_point_ext`;
CREATE TABLE `product_data_point_ext`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `product_id` int(11) NOT NULL COMMENT '产品id',
  `data_id` int(11) NOT NULL COMMENT '数据点id，product_data_point.id',
  `name` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '指令名称',
  `identity_name` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '标志名称',
  `show_enable` bit(1) NOT NULL DEFAULT b'1' COMMENT '是否在后台展示：0,不展示；1,展示',
  `ctime` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `utime` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `vendor` tinyint(4) NOT NULL DEFAULT 0 COMMENT '第三方api提供商，0：无；1：和风；2：阿里',
  `param` tinyint(4) NOT NULL COMMENT '1：温度；2：湿度；3：pm2.5；4：空气质量',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `prod_data_id`(`product_id`, `data_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '产品指令配置扩展表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for product_operation_history
-- ----------------------------
DROP TABLE IF EXISTS `product_operation_history`;
CREATE TABLE `product_operation_history`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `ctime` datetime(0) NOT NULL COMMENT '创建时间',
  `product_id` int(11) DEFAULT NULL COMMENT '产品id',
  `device_sno` varchar(32) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL COMMENT '设备sno',
  `operate_type` int(11) NOT NULL COMMENT '操作类型',
  `ip` varchar(20) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL COMMENT 'ip地址',
  `sys_user_id` int(11) NOT NULL COMMENT '操作人',
  `sys_user_name` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL COMMENT '操作人',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_device_sno`(`device_sno`) USING BTREE,
  INDEX `idx_product_id`(`product_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci COMMENT = '产品操作记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for product_properties
-- ----------------------------
DROP TABLE IF EXISTS `product_properties`;
CREATE TABLE `product_properties`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键,自增长',
  `ctime` datetime(0) NOT NULL COMMENT '添加时间',
  `utime` datetime(0) DEFAULT NULL COMMENT '更新时间',
  `property_key` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '属性key',
  `property_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '属性名称',
  `tips` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '提示语',
  `category_id` int(5) NOT NULL COMMENT '产品类型',
  `category_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '产品类型名称',
  `is_not_null` int(1) NOT NULL COMMENT '是否必填,1:是 0:否',
  `is_select_value` int(1) NOT NULL COMMENT '是否选择值,1:是,选择 0:否,填写',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_property_key`(`property_key`) USING BTREE,
  INDEX `idx_category`(`category_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '产品属性定义表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for product_properties_option_value
-- ----------------------------
DROP TABLE IF EXISTS `product_properties_option_value`;
CREATE TABLE `product_properties_option_value`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键,自增长',
  `ctime` datetime(0) NOT NULL COMMENT '添加时间',
  `utime` datetime(0) DEFAULT NULL COMMENT '更新时间',
  `property_key` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '属性key',
  `property_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '属性名称',
  `property_value` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '属性值',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '产品属性值表,提供选择' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for product_service_detail
-- ----------------------------
DROP TABLE IF EXISTS `product_service_detail`;
CREATE TABLE `product_service_detail`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ctime` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '创建时间',
  `utime` datetime(0) DEFAULT NULL COMMENT '修改时间',
  `service_mode_id` int(11) NOT NULL COMMENT '收费模式的id',
  `product_id` int(11) NOT NULL COMMENT '产品的id',
  `service_type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '收费类型',
  `service_type_id` int(11) DEFAULT NULL COMMENT '收费类型的id',
  `price` double(8, 2) NOT NULL COMMENT '单价',
  `num` double(8, 2) NOT NULL COMMENT '数量',
  `unit` varchar(420) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '单位',
  `status` int(11) DEFAULT 1 COMMENT '状态：0：启用，1:禁用',
  `sys_user_id` int(11) NOT NULL COMMENT '系统用户的id，创建者',
  `sys_user_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '创建人姓名',
  `command` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `is_deleted` int(1) NOT NULL DEFAULT 0 COMMENT '是否删除：0,否；1,是',
  `name` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '收费详情名称，非必填',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `index2`(`service_mode_id`, `service_type_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '产品服务详情表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for product_service_mode
-- ----------------------------
DROP TABLE IF EXISTS `product_service_mode`;
CREATE TABLE `product_service_mode`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键,自增长',
  `ctime` datetime(0) NOT NULL COMMENT '添加时间',
  `utime` datetime(0) DEFAULT NULL COMMENT '更新时间',
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '服务方式名称',
  `unit` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '单位',
  `product_id` int(11) NOT NULL COMMENT '所属产品ID',
  `service_type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '服务类型',
  `sys_user_id` int(11) NOT NULL COMMENT '创建人id',
  `sys_user_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '创建人姓名',
  `status` int(11) DEFAULT 1 COMMENT '状态：0:禁用，1:启用',
  `service_type_id` int(11) DEFAULT NULL COMMENT '服务类型ID对应product_command_config的ID',
  `command` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '在没收费模式详情的时候需要的指令,如免费模式时需要下发的指令',
  `is_deleted` int(1) NOT NULL DEFAULT 0 COMMENT '是否删除：0,否；1,是',
  `is_free` int(1) NOT NULL DEFAULT 0 COMMENT '是否收费：0，收费，1，免费，，数据从收费模式指令处获得',
  `working_mode` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '工作模式',
  `unit_price` double(8, 2) DEFAULT NULL COMMENT '单价',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `index_id`(`product_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '产品(或者设备)服务方式' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for product_to_properties
-- ----------------------------
DROP TABLE IF EXISTS `product_to_properties`;
CREATE TABLE `product_to_properties`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键,自增长',
  `ctime` datetime(0) NOT NULL COMMENT '添加时间',
  `utime` datetime(0) DEFAULT NULL COMMENT '更新时间',
  `product_id` int(11) NOT NULL COMMENT '产品ID',
  `product_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '产品名称',
  `property_id` int(11) NOT NULL COMMENT '属性id',
  `property_key` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '属性key',
  `property_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '属性名称',
  `property_value` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '属性值',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `index_id`(`product_id`, `property_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '产品属性关系表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for recharge_money
-- ----------------------------
DROP TABLE IF EXISTS `recharge_money`;
CREATE TABLE `recharge_money`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键,自增长',
  `ctime` datetime(0) NOT NULL COMMENT '添加时间',
  `utime` datetime(0) DEFAULT NULL COMMENT '更新时间',
  `type` int(1) NOT NULL COMMENT '类型 1，固定充值额，2，自定义充值额',
  `charge_money` double(12, 2) DEFAULT NULL COMMENT '充值金额',
  `discount_money` double(12, 2) DEFAULT NULL COMMENT '赠送金额',
  `rate` decimal(3, 2) DEFAULT 0.00 COMMENT '优惠比例',
  `sys_user_id` int(11) DEFAULT NULL COMMENT '创建者id',
  `project_id` int(11) DEFAULT NULL COMMENT '项目id：1艾芙芮  2卡励',
  `sort` int(1) DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_sys_user_id`(`sys_user_id`) USING BTREE,
  INDEX `idx_project_id`(`project_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '充值优惠表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for refund_apply
-- ----------------------------
DROP TABLE IF EXISTS `refund_apply`;
CREATE TABLE `refund_apply`  (
  `refund_no` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '退款单id',
  `status` int(2) NOT NULL COMMENT '状态：1待审核，2审核通过，3审核不通过，4已退款',
  `order_no` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '对应的订单id',
  `amount` double(12, 2) NOT NULL COMMENT '退款金额',
  `path` int(2) NOT NULL COMMENT '退款路径，枚举值和支付类型一样',
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `user_mobile` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用户手机号',
  `user_alipay_account` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用户支付宝帐号',
  `user_alipay_real_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用户支付宝真实姓名',
  `refund_reason` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '退款原因',
  `audit_reason` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '审核原因',
  `auditor_id` int(11) DEFAULT NULL COMMENT '审核人id',
  `audit_time` datetime(0) DEFAULT NULL COMMENT '审核时间',
  `refunder_id` int(11) DEFAULT NULL COMMENT '退款人id',
  `refund_time` datetime(0) DEFAULT NULL COMMENT '退款时间',
  `sys_user_id` int(11) NOT NULL COMMENT '和订单的sys_user_id一致',
  `ctime` datetime(0) DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `utime` datetime(0) DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `nickname` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用户昵称',
  PRIMARY KEY (`refund_no`) USING BTREE,
  INDEX `idx_order_no`(`order_no`) USING BTREE,
  INDEX `idx_user_id`(`user_id`) USING BTREE,
  INDEX `idx_auditor_id`(`auditor_id`) USING BTREE,
  INDEX `idx_refunder_id`(`refunder_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '退款申请表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for refund_base
-- ----------------------------
DROP TABLE IF EXISTS `refund_base`;
CREATE TABLE `refund_base`  (
  `refund_no` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `amount` decimal(12, 2) NOT NULL COMMENT '退款金额',
  `ctime` datetime(0) NOT NULL COMMENT '退款时间',
  `order_no` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '订单号',
  `trade_no` varchar(31) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '支付交易号',
  `refund_method` tinyint(4) NOT NULL COMMENT '退款方式：1微信，2：支付宝，3：充值卡',
  PRIMARY KEY (`refund_no`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for share_benefit_rule
-- ----------------------------
DROP TABLE IF EXISTS `share_benefit_rule`;
CREATE TABLE `share_benefit_rule`  (
  `id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '主键ID',
  `share_benefit_rule_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '分润规则名称',
  `sys_account_id` int(11) DEFAULT NULL COMMENT '运营商关联系统用户',
  `operator_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '运营商名称',
  `start_time` datetime(0) NOT NULL COMMENT '账单首次生成时间',
  `frequency` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '对账单生成频率: DAY,WEEK,MONTH,YEAR',
  `last_execute_time` datetime(0) DEFAULT NULL COMMENT '上一次分润执行的时间',
  `ctime` datetime(0) NOT NULL COMMENT '创建时间',
  `utime` datetime(0) DEFAULT NULL COMMENT '修改时间',
  `sys_user_id` int(11) NOT NULL COMMENT '分润规则的所有者',
  `is_deleted` int(1) NOT NULL DEFAULT 0,
  `operator_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `index_sys_user_id`(`sys_user_id`, `sys_account_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '分润规则表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for share_benefit_rule_detail
-- ----------------------------
DROP TABLE IF EXISTS `share_benefit_rule_detail`;
CREATE TABLE `share_benefit_rule_detail`  (
  `id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '主键ID',
  `rule_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '分润规则主表ID',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '详细分润规则名称',
  `share_percentage` decimal(6, 2) NOT NULL DEFAULT 0.00 COMMENT '分润比例，显示的是百分数',
  `share_type` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'SINGLE' COMMENT '分润类型，使用设备：ALL,所有设备；SINGLE，个别设备；',
  `ctime` datetime(0) NOT NULL COMMENT '创建时间',
  `utime` datetime(0) DEFAULT NULL COMMENT '修改时间',
  `is_deleted` int(1) NOT NULL DEFAULT 0,
  `level` int(1) DEFAULT NULL COMMENT '分润比例层级：1厂商 2代理商 3运营商 4子运营商',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `index_ruleId`(`name`, `rule_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '分润规则详细表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for share_benefit_rule_detail_device
-- ----------------------------
DROP TABLE IF EXISTS `share_benefit_rule_detail_device`;
CREATE TABLE `share_benefit_rule_detail_device`  (
  `id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '主键ID',
  `rule_detail_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '分润规则详细表ID',
  `sno` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '设备SNO',
  `ctime` datetime(0) NOT NULL COMMENT '创建时间',
  `utime` datetime(0) DEFAULT NULL,
  `is_deleted` int(1) NOT NULL DEFAULT 0,
  `share_percentage` decimal(6, 2) DEFAULT NULL COMMENT '上级为本运营商设置的分润比例',
  `children_percentage` decimal(6, 2) DEFAULT 0.00 COMMENT '此设备在本运营商下级的分润比例',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `index_rule_detail_id`(`rule_detail_id`) USING BTREE,
  INDEX `index_sno`(`sno`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '分润规则详细设备表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for share_benefit_rule_modify_limit
-- ----------------------------
DROP TABLE IF EXISTS `share_benefit_rule_modify_limit`;
CREATE TABLE `share_benefit_rule_modify_limit`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键,自增长',
  `ctime` datetime(0) NOT NULL COMMENT '添加时间',
  `utime` datetime(0) DEFAULT NULL COMMENT '更新时间',
  `sys_user_id` int(11) NOT NULL COMMENT '创建人(厂商绑定的系统帐号)',
  `before_bill_limit` int(11) NOT NULL COMMENT '首次账单生成前可修改次数',
  `period_unit` int(11) NOT NULL COMMENT '周期单位 1:每年, 2:每月',
  `period_limit` int(11) NOT NULL COMMENT '周期内可修改次数',
  `start_time` datetime(0) NOT NULL COMMENT '可允许修改时段 开始时间',
  `end_time` datetime(0) NOT NULL COMMENT '可允许修改时段 结束时间',
  `weekdays` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '1,2,3,4,5,6,7' COMMENT '可允许修改时段 1:(星期一) ... 7:(星期日)',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `sys_user_id`(`sys_user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '分润规则修改次数限制' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for share_benefit_rule_modify_record
-- ----------------------------
DROP TABLE IF EXISTS `share_benefit_rule_modify_record`;
CREATE TABLE `share_benefit_rule_modify_record`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键,自增长',
  `ctime` datetime(0) NOT NULL COMMENT '添加时间',
  `utime` datetime(0) DEFAULT NULL COMMENT '更新时间',
  `rule_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '分润规则ID',
  `sys_user_id` int(11) NOT NULL COMMENT '修改者',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '分润规则修改记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for share_benefit_sheet
-- ----------------------------
DROP TABLE IF EXISTS `share_benefit_sheet`;
CREATE TABLE `share_benefit_sheet`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `sheet_no` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '分润账单号',
  `operator_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '运营商名称',
  `status` int(2) NOT NULL COMMENT '账单状态：0，创建；1，待审核;2，待分润；3,执行分润中；4，分润成功；5，分润失败；',
  `pay_type` int(2) NOT NULL DEFAULT 1 COMMENT '分润支付类型：1，微信；2，支付宝；3，线下；',
  `order_count` int(11) NOT NULL DEFAULT 0 COMMENT '订单数',
  `total_money` double(11, 2) NOT NULL DEFAULT 0.00 COMMENT '所有订单的总合计金额',
  `share_money` double(11, 2) NOT NULL DEFAULT 0.00 COMMENT '当前运营商的分润金额',
  `ctime` datetime(0) NOT NULL,
  `utime` datetime(0) DEFAULT NULL,
  `trade_no` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '分润执行商户订单号',
  `payment_no` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '分润成功时的微信订单号',
  `payment_time` datetime(0) DEFAULT NULL COMMENT '微信付款成功时间',
  `audit_time` datetime(0) DEFAULT NULL COMMENT '审核时间',
  `pay_account` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '支付账号，存储的是公众号的信息wx_id',
  `receiver_openid` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '收款人openid',
  `receiver_name` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '收款人名称',
  `sys_user_id` int(11) DEFAULT NULL COMMENT '创建人(生成分润单时把运营商关联的系统账号写进来)',
  `is_try_again` int(1) NOT NULL DEFAULT 0 COMMENT '是否使用trade_no重试支付，如果此字段为1，则分润单不可修改，只再次支付',
  `sys_account_id` int(11) DEFAULT NULL COMMENT '运营商的账号',
  `rule_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `operator_id` int(11) DEFAULT NULL,
  `wx_total_money` double(11, 2) DEFAULT NULL COMMENT '微信订单合计',
  `card_total_money` double(11, 2) DEFAULT NULL COMMENT '刷卡订单合计',
  `alipay_total_money` double(11, 2) DEFAULT NULL COMMENT '支付宝订单合计',
  `other_total_money` double(11, 2) DEFAULT NULL COMMENT '其他订单合计',
  `share_profit_batch_id` int(11) DEFAULT NULL COMMENT '分润批次',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `index_status`(`status`) USING BTREE,
  INDEX `index_id`(`sheet_no`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '分润账单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for share_benefit_sheet_action_record
-- ----------------------------
DROP TABLE IF EXISTS `share_benefit_sheet_action_record`;
CREATE TABLE `share_benefit_sheet_action_record`  (
  `id` int(11) NOT NULL COMMENT '主键ID',
  `sheet_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '分润单ID',
  `action_type` int(2) NOT NULL DEFAULT 0 COMMENT '操作类型：0，创建分润单；1，审核通过；2、重新审核；3，执行分润',
  `user_id` int(11) NOT NULL COMMENT '操作者',
  `ctime` datetime(0) NOT NULL COMMENT '操作时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `index_id`(`sheet_id`, `user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '分润单操作记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for share_benefit_sheet_order
-- ----------------------------
DROP TABLE IF EXISTS `share_benefit_sheet_order`;
CREATE TABLE `share_benefit_sheet_order`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sheet_no` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '分润单ID',
  `operator_id` int(11) DEFAULT NULL COMMENT '运营商ID',
  `device_sno` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '设备sno',
  `order_no` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '订单号',
  `share_rule_detail_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '详细分润规则ID',
  `share_percentage` double(10, 2) NOT NULL COMMENT '直属运营商分润比例',
  `share_money` double(11, 4) DEFAULT NULL COMMENT '订单在该运营商的分润金额，四舍五入保留4位小数',
  `order_amount` double(12, 5) DEFAULT NULL COMMENT '订单金额',
  `status` int(2) NOT NULL COMMENT '状态：1、待审核；2、审核通过；3、审核不通过；4、执行分润中；5、分润成功；',
  `ctime` datetime(0) NOT NULL,
  `utime` datetime(0) DEFAULT NULL,
  `sys_account_id` int(11) DEFAULT NULL COMMENT '运营商或代理商的账号ID',
  `children_share_percentage` double(3, 2) DEFAULT NULL COMMENT '下一级在该订单的分润比例',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `index_id`(`sheet_no`, `order_no`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '所有要参与分润的订单' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for share_benefit_sheet_pay_record
-- ----------------------------
DROP TABLE IF EXISTS `share_benefit_sheet_pay_record`;
CREATE TABLE `share_benefit_sheet_pay_record`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `sheet_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '分润单ID',
  `trade_no` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '交易订单号',
  `amount` decimal(11, 2) NOT NULL DEFAULT 0.00 COMMENT '分润金额',
  `content` varchar(1500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '分润执行消息体',
  `status` int(1) NOT NULL DEFAULT 1 COMMENT '状态：1，执行分润；2，分润成功；3，分润失败；',
  `user_id` int(11) NOT NULL COMMENT '操作者',
  `ctime` datetime(0) NOT NULL,
  `utime` datetime(0) DEFAULT NULL,
  `callback_content` varchar(1500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '支付反馈结果',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `index_id`(`sheet_id`, `trade_no`, `user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '分润单支付记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for share_profit_batch
-- ----------------------------
DROP TABLE IF EXISTS `share_profit_batch`;
CREATE TABLE `share_profit_batch`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '分润批次ID',
  `frequency` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '账单生成周期 DAY,WEEK,MONTH,YEAR',
  `period_start_time` datetime(0) NOT NULL COMMENT '周期开始时间',
  `period_end_time` datetime(0) NOT NULL COMMENT '生成时间 周期结束时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '分润批次' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for share_profit_summary
-- ----------------------------
DROP TABLE IF EXISTS `share_profit_summary`;
CREATE TABLE `share_profit_summary`  (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '分润账单汇总ID',
  `batch_id` int(11) NOT NULL COMMENT '分润批次ID',
  `sys_user_id` int(11) NOT NULL COMMENT '用户及其下级的分润账单汇总',
  `order_count` int(11) NOT NULL DEFAULT 0 COMMENT '订单总数',
  `order_money` decimal(11, 2) NOT NULL DEFAULT 0.00 COMMENT '订单总金额',
  `share_money` decimal(11, 2) NOT NULL DEFAULT 0.00 COMMENT '分润账单总金额',
  `status` int(11) NOT NULL DEFAULT 1 COMMENT '状态 1:未支付 2:部分已支付 3:已完成',
  `ctime` datetime(0) NOT NULL COMMENT '创建时间',
  `utime` datetime(0) DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `batch_id`(`batch_id`, `sys_user_id`) USING BTREE,
  INDEX `sys_user_id`(`sys_user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '分润账单汇总' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for share_profit_summary_detail
-- ----------------------------
DROP TABLE IF EXISTS `share_profit_summary_detail`;
CREATE TABLE `share_profit_summary_detail`  (
  `summary_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '分润账单汇总ID',
  `bill_no` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '分润账单号',
  PRIMARY KEY (`summary_id`, `bill_no`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '分润账单汇总明细' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for stat_device_hour
-- ----------------------------
DROP TABLE IF EXISTS `stat_device_hour`;
CREATE TABLE `stat_device_hour`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键,自增',
  `ctime` datetime(0) NOT NULL COMMENT '添加时间',
  `product_id` int(11) DEFAULT NULL COMMENT '产品id',
  `sys_user_id` int(11) DEFAULT NULL COMMENT '归属系统用户id',
  `zero` int(11) DEFAULT 0 COMMENT '0点设备数量',
  `ONE` int(11) DEFAULT 0 COMMENT '1点设备数量',
  `two` int(11) DEFAULT 0 COMMENT '2点设备数量',
  `three` int(11) DEFAULT 0 COMMENT '3点设备数量',
  `four` int(11) DEFAULT 0 COMMENT '4点设备数量',
  `five` int(11) DEFAULT 0 COMMENT '5点设备数量',
  `six` int(11) DEFAULT 0 COMMENT '6点设备数量',
  `seven` int(11) DEFAULT 0 COMMENT '7点设备数量',
  `eight` int(11) DEFAULT 0 COMMENT '8点设备数量',
  `nine` int(11) DEFAULT 0 COMMENT '9点设备数量',
  `ten` int(11) DEFAULT 0 COMMENT '10点设备数量',
  `eleven` int(11) DEFAULT 0 COMMENT '11点设备数量',
  `twelve` int(11) DEFAULT 0 COMMENT '12点设备数量',
  `thriteen` int(11) DEFAULT 0 COMMENT '13点设备数量',
  `fourteen` int(11) DEFAULT 0 COMMENT '14点设备数量',
  `fifteen` int(11) DEFAULT 0 COMMENT '15点设备数量',
  `siteen` int(11) DEFAULT 0 COMMENT '16点设备数量',
  `seventeen` int(11) DEFAULT 0 COMMENT '17点设备数量',
  `eighteen` int(11) DEFAULT 0 COMMENT '18点设备数量',
  `nineteen` int(11) DEFAULT 0 COMMENT '19点设备数量',
  `twenty` int(11) DEFAULT 0 COMMENT '20点设备数量',
  `twenty_one` int(11) DEFAULT 0 COMMENT '21点设备数量',
  `twenty_two` int(11) DEFAULT 0 COMMENT '22点设备数量',
  `twenty_three` int(11) DEFAULT 0 COMMENT '23点设备数量',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_ctime`(`ctime`) USING BTREE,
  INDEX `idx_sys_user_id`(`sys_user_id`) USING BTREE,
  INDEX `idx_product_id`(`product_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '设备在线时段分析统计表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for stat_device_location
-- ----------------------------
DROP TABLE IF EXISTS `stat_device_location`;
CREATE TABLE `stat_device_location`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键,自增',
  `ctime` datetime(0) NOT NULL COMMENT '添加时间',
  `sys_user_id` int(11) DEFAULT NULL COMMENT '归属系统用户id',
  `province_id` int(11) DEFAULT NULL COMMENT '省id',
  `province` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '省名称',
  `device_count` int(11) DEFAULT 0 COMMENT '省对应的用户数量',
  `product_id` int(11) DEFAULT NULL COMMENT '产品id',
  `proportion` double(12, 2) DEFAULT 0.00 COMMENT '设备占比',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_ctime`(`ctime`) USING BTREE,
  INDEX `idx_sys_user_id`(`sys_user_id`) USING BTREE,
  INDEX `idx_product_id`(`product_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '设备地图分布统计表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for stat_device_order_widget
-- ----------------------------
DROP TABLE IF EXISTS `stat_device_order_widget`;
CREATE TABLE `stat_device_order_widget`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键,自增',
  `ctime` datetime(0) NOT NULL COMMENT '添加时间',
  `utime` datetime(0) NOT NULL COMMENT '修改时间',
  `sys_user_id` int(11) DEFAULT NULL COMMENT '归属系统用户id',
  `product_id` int(11) DEFAULT NULL COMMENT '产品id',
  `total_count` int(11) DEFAULT 0 COMMENT '当前设备总数',
  `new_count` int(11) DEFAULT 0 COMMENT '今日设备新增数',
  `ordered_percent` double(12, 2) DEFAULT 0.00 COMMENT '设备订单率（昨天下单设备数-今天下单设备数）/今天总',
  `alarm_count` int(11) DEFAULT 0 COMMENT '当前设备故障数',
  `warn_count` int(11) DEFAULT 0 COMMENT '当前设备警告数',
  `warn_record` int(11) DEFAULT 0 COMMENT '今日告警记录',
  `alarm_percent` double(12, 2) DEFAULT 0.00 COMMENT '当前设备故障率',
  `order_count_today` int(11) DEFAULT 0 COMMENT '今日订单数量',
  `order_count_yesterday` int(11) DEFAULT 0 COMMENT '昨日订单数量',
  `order_new_percent_yesterday` double(12, 2) DEFAULT 0.00 COMMENT '昨天订单新增率',
  `order_count_month` int(11) DEFAULT 0 COMMENT '本月订单数',
  `order_count_before_yesterday` int(11) DEFAULT NULL COMMENT '前天订单数',
  `share_order_count` int(11) DEFAULT 0,
  `share_order_money` double(12, 2) DEFAULT 0.00,
  `ordered_count` int(11) DEFAULT 0 COMMENT '今天下单设备数',
  `online_device_count` int(11) DEFAULT NULL COMMENT '在线设备数',
  `activated_device_count` int(11) DEFAULT NULL COMMENT '已激活设备数',
  `activated_device_count_today` int(11) DEFAULT NULL COMMENT '今日新激活设备数',
  `order_total_count` int(11) DEFAULT NULL COMMENT '所有订单数量',
  `order_finish_count` int(11) DEFAULT NULL COMMENT '所有已完成的订单数量',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_ctime`(`ctime`) USING BTREE,
  INDEX `idx_utime`(`utime`) USING BTREE,
  INDEX `idx_sys_user_id`(`sys_user_id`) USING BTREE,
  INDEX `idx_product_id`(`product_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '设备订单看板数据统计表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for stat_device_trend
-- ----------------------------
DROP TABLE IF EXISTS `stat_device_trend`;
CREATE TABLE `stat_device_trend`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键,自增',
  `ctime` datetime(0) NOT NULL COMMENT '添加时间',
  `sys_user_id` int(11) DEFAULT 0 COMMENT '归属系统用户id',
  `new_count` int(11) DEFAULT 0 COMMENT '新增上线数量',
  `ordered_percent` double(12, 2) DEFAULT 0.00 COMMENT '设备订单率',
  `ordered_count` int(11) DEFAULT 0 COMMENT '含有订单的设备数',
  `product_id` int(11) DEFAULT NULL COMMENT '对应的产品id',
  `active_count` int(11) DEFAULT 0 COMMENT '设备活跃数',
  `previous_deivce_total` int(11) DEFAULT NULL COMMENT '之前的设备总数',
  `new_activated_count` int(11) DEFAULT NULL COMMENT '今日新激活设备数',
  `fault_count` int(11) DEFAULT NULL COMMENT '故障设备数',
  `alert_count` int(11) DEFAULT NULL COMMENT '报警设备数',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_ctime`(`ctime`) USING BTREE,
  INDEX `idx_sys_user_id`(`sys_user_id`) USING BTREE,
  INDEX `idx_product_id`(`product_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '设备趋势统计表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of stat_device_trend
-- ----------------------------
INSERT INTO `stat_device_trend` VALUES (2, '2019-12-04 16:05:12', 0, 1, 0.00, 0, 1, 1, 2, NULL, 0, 0);

-- ----------------------------
-- Table structure for stat_fault_alert_type
-- ----------------------------
DROP TABLE IF EXISTS `stat_fault_alert_type`;
CREATE TABLE `stat_fault_alert_type`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键，自增',
  `ctime` datetime(0) DEFAULT NULL COMMENT '创建时间',
  `sno` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '设备号',
  `sys_user_id` int(11) DEFAULT NULL COMMENT '归属用户id',
  `show_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '显示名称',
  `identity_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '标志名称',
  `remark` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
  `count` int(11) DEFAULT 0 COMMENT '统计数量',
  `product_id` int(11) DEFAULT NULL COMMENT '产品id',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_ctime`(`ctime`) USING BTREE,
  INDEX `idx_sys_user_id`(`sys_user_id`) USING BTREE,
  INDEX `idx_product_id`(`product_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for stat_order
-- ----------------------------
DROP TABLE IF EXISTS `stat_order`;
CREATE TABLE `stat_order`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键,自增',
  `ctime` datetime(0) NOT NULL COMMENT '添加时间',
  `sys_user_id` int(11) DEFAULT NULL COMMENT '归属系统用户id',
  `sno` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '设备序列号',
  `operator_id` int(11) DEFAULT NULL COMMENT '运营商id',
  `order_amount` double(12, 2) DEFAULT 0.00 COMMENT '订单总金额',
  `order_count` int(11) DEFAULT 0 COMMENT '订单数量',
  `ordered_percent` double(12, 2) DEFAULT 0.00 COMMENT '订单增长率',
  `product_id` int(11) DEFAULT NULL COMMENT '产品id',
  `agent_id` int(11) DEFAULT NULL COMMENT '代理商id',
  `launch_area_id` int(11) DEFAULT NULL COMMENT '投放点id',
  `refund_count` int(11) DEFAULT NULL COMMENT '退款数',
  `refund_amount` double(12, 2) DEFAULT NULL COMMENT '退款金额',
  `generated_share_amount` double(12, 2) DEFAULT NULL COMMENT '已生成分润单的分润金额',
  `ungenerate_share_order_amount` double(12, 2) DEFAULT NULL COMMENT '未生成分润单的订单金额',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_operator_id`(`operator_id`) USING BTREE,
  INDEX `idx_ctime`(`ctime`) USING BTREE,
  INDEX `idx_sys_user_id`(`sys_user_id`) USING BTREE,
  INDEX `idx_sno`(`sno`) USING BTREE,
  INDEX `idx_product_id`(`product_id`) USING BTREE,
  INDEX `idx_agent_id`(`agent_id`) USING BTREE,
  INDEX `idx_launch_area_id`(`launch_area_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '订单分析统计表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for stat_user_hour
-- ----------------------------
DROP TABLE IF EXISTS `stat_user_hour`;
CREATE TABLE `stat_user_hour`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键,自增',
  `ctime` datetime(0) NOT NULL COMMENT '添加时间',
  `sys_user_id` int(11) DEFAULT NULL COMMENT '归属系统用户id',
  `zero` int(11) DEFAULT 0 COMMENT '0点用户控制设备数量',
  `ONE` int(11) DEFAULT 0 COMMENT '1点用户控制设备数量',
  `two` int(11) DEFAULT 0 COMMENT '2点用户控制设备数量',
  `three` int(11) DEFAULT 0 COMMENT '3点用户控制设备数量',
  `four` int(11) DEFAULT 0 COMMENT '4点用户控制设备数量',
  `five` int(11) DEFAULT 0 COMMENT '5点用户控制设备数量',
  `six` int(11) DEFAULT 0 COMMENT '6点用户控制设备数量',
  `seven` int(11) DEFAULT 0 COMMENT '7点用户控制设备数量',
  `eight` int(11) DEFAULT 0 COMMENT '8点用户控制设备数量',
  `nine` int(11) DEFAULT 0 COMMENT '9点用户控制设备数量',
  `ten` int(11) DEFAULT 0 COMMENT '10点用户控制设备数量',
  `eleven` int(11) DEFAULT 0 COMMENT '11点用户控制设备数量',
  `twelve` int(11) DEFAULT 0 COMMENT '12点用户控制设备数量',
  `thriteen` int(11) DEFAULT 0 COMMENT '13点用户控制设备数量',
  `fourteen` int(11) DEFAULT 0 COMMENT '14点用户控制设备数量',
  `fifteen` int(11) DEFAULT 0 COMMENT '15点用户控制设备数量',
  `siteen` int(11) DEFAULT 0 COMMENT '16点用户控制设备数量',
  `seventeen` int(11) DEFAULT 0 COMMENT '17点用户控制设备数量',
  `eighteen` int(11) DEFAULT 0 COMMENT '18点用户控制设备数量',
  `nineteen` int(11) DEFAULT 0 COMMENT '19点用户控制设备数量',
  `twenty` int(11) DEFAULT 0 COMMENT '20点用户控制设备数量',
  `twenty_one` int(11) DEFAULT 0 COMMENT '21点用户控制设备数量',
  `twenty_two` int(11) DEFAULT 0 COMMENT '22点用户控制设备数量',
  `twenty_three` int(11) DEFAULT 0 COMMENT '23点用户控制设备数量',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_ctime`(`ctime`) USING BTREE,
  INDEX `idx_sys_user_id`(`sys_user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '控制设备时段用户统计表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for stat_user_location
-- ----------------------------
DROP TABLE IF EXISTS `stat_user_location`;
CREATE TABLE `stat_user_location`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键,自增',
  `ctime` datetime(0) NOT NULL COMMENT '添加时间',
  `sys_user_id` int(11) DEFAULT NULL COMMENT '归属系统用户id',
  `province_id` int(11) DEFAULT NULL COMMENT '省id',
  `province` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '省名称',
  `user_count` int(11) DEFAULT 0 COMMENT '省对应的用户数量',
  `proportion` double(12, 2) DEFAULT 0.00 COMMENT '用户百分比',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_sys_user_id`(`sys_user_id`) USING BTREE,
  INDEX `idx_ctime`(`ctime`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '用户地图分布统计表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for stat_user_trend
-- ----------------------------
DROP TABLE IF EXISTS `stat_user_trend`;
CREATE TABLE `stat_user_trend`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键,自增',
  `ctime` datetime(0) NOT NULL COMMENT '添加时间',
  `sys_user_id` int(11) DEFAULT NULL COMMENT '归属系统用户id',
  `new_count` int(11) DEFAULT 0 COMMENT '新增用户数量',
  `active_count` int(11) DEFAULT 0 COMMENT '活跃用户数量',
  `total_count` int(11) DEFAULT 0 COMMENT '用户总数',
  `male` int(11) DEFAULT 0 COMMENT '男性用户数量',
  `female` int(11) DEFAULT 0 COMMENT '女性用户数量',
  `zero` int(11) DEFAULT 0 COMMENT '使用该产品0次用户数量',
  `one_two` int(11) DEFAULT 0 COMMENT '使用该产品1~2次用户数量',
  `three_four` int(11) DEFAULT 0 COMMENT '使用该产品3~4次用户数量',
  `five_six` int(11) DEFAULT 0 COMMENT '使用该产品5~6次用户数量',
  `seven_eight` int(11) DEFAULT 0 COMMENT '使用该产品7~8次用户数量',
  `nine_ten` int(11) DEFAULT 0 COMMENT '使用该产品9~10次用户数量',
  `ten_more` int(11) DEFAULT 0 COMMENT '使用该产品10次及其以上用户数量',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_ctime`(`ctime`) USING BTREE,
  INDEX `idx_sys_user_id`(`sys_user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '用户趋势及性别，使用次数统计表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of stat_user_trend
-- ----------------------------
INSERT INTO `stat_user_trend` VALUES (1, '2019-12-04 17:10:12', NULL, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);

-- ----------------------------
-- Table structure for stat_user_widget
-- ----------------------------
DROP TABLE IF EXISTS `stat_user_widget`;
CREATE TABLE `stat_user_widget`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键,自增',
  `ctime` datetime(0) NOT NULL COMMENT '添加时间',
  `utime` datetime(0) NOT NULL COMMENT '修改时间',
  `sys_user_id` int(11) DEFAULT NULL COMMENT '归属系统用户id',
  `total_count` int(11) DEFAULT 0 COMMENT '当前用户总数',
  `new_percent` double(12, 2) DEFAULT 0.00 COMMENT '昨天用户增长率',
  `active_count` int(11) DEFAULT 0 COMMENT '今日活跃用户数',
  `active_percent` double(12, 2) DEFAULT 0.00 COMMENT '昨天用户活跃率',
  `new_count` int(11) DEFAULT 0 COMMENT '今天新增用户数',
  `ordered_count` int(11) DEFAULT NULL COMMENT '所有使用过租赁服务的用户总数',
  `new_ordered_count` int(11) DEFAULT NULL COMMENT '新增使用过租赁服务的用户数',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_ctime`(`ctime`) USING BTREE,
  INDEX `idx_utime`(`utime`) USING BTREE,
  INDEX `idx_sys_user_id`(`sys_user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '设备订单看板数据统计表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_config
-- ----------------------------
DROP TABLE IF EXISTS `sys_config`;
CREATE TABLE `sys_config`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键,自增长',
  `ctime` datetime(0) NOT NULL COMMENT '添加时间',
  `utime` datetime(0) DEFAULT NULL COMMENT '更新时间',
  `config_key` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '配置key',
  `config_value` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '配置value',
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '描述',
  `status` int(1) NOT NULL DEFAULT 1 COMMENT '0:禁用 1:启用',
  `sys_user_id` int(11) DEFAULT NULL COMMENT '操作人员',
  `sys_user_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `index-confing`(`config_key`, `status`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 164 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '系统配置表,用来动态添加常量配置' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_config
-- ----------------------------
INSERT INTO `sys_config` VALUES (152, '2019-11-14 10:51:29', NULL, 'managerRoleId', '1', '管理员角色id', 1, NULL, NULL);
INSERT INTO `sys_config` VALUES (153, '2019-11-14 10:51:29', NULL, 'shareBenefitPayType', 'WEIXIN', '分润支付的类型: WEIXIN;ALIPAY', 1, NULL, NULL);
INSERT INTO `sys_config` VALUES (154, '2019-11-14 10:53:14', NULL, 'managerUserId', '1', '管理员用户id', 1, NULL, NULL);
INSERT INTO `sys_config` VALUES (155, '2019-11-14 10:53:14', NULL, 'defaultDisplayPanelItem', '1,2,4,5,16,17', '首页默认显示的项', 1, NULL, NULL);
INSERT INTO `sys_config` VALUES (156, '2019-11-14 11:00:41', NULL, 'messageApiKey', '53bcd45bd591ff72f0f057cfdbec3466', '云片APIKey', 1, NULL, NULL);
INSERT INTO `sys_config` VALUES (157, '2019-11-14 11:00:41', NULL, 'messageCodeTemplateId', '2044740', '短信验证码的模板ID', 1, NULL, NULL);
INSERT INTO `sys_config` VALUES (158, '2019-11-14 11:41:54', NULL, 'templateId', '1860414', '平台验证码模版id', 1, NULL, NULL);
INSERT INTO `sys_config` VALUES (159, '2019-11-14 11:41:54', NULL, 'apiKey', 'f2be677d325f630408a559f5c267ee6c', '平台apikey', 1, NULL, NULL);
INSERT INTO `sys_config` VALUES (160, '2019-11-14 11:41:54', NULL, 'templateValue', '【机智云】您的验证码是#code#。如非本人操作，请忽略本短信', '平台验证码模版值', 1, NULL, NULL);
INSERT INTO `sys_config` VALUES (161, '2019-11-18 15:49:17', NULL, 'pictureType', 'jpg,gif,png,jpeg', '图片类型', 1, NULL, NULL);
INSERT INTO `sys_config` VALUES (162, '2019-11-18 15:50:38', NULL, 'launchAreaImageURL', '/launch-area/images/', '投放点图片访问地址', 1, NULL, NULL);
INSERT INTO `sys_config` VALUES (163, '2019-12-06 14:16:28', NULL, 'defaultExportSize', '100000', '默认导出的列表大小', 1, NULL, NULL);

-- ----------------------------
-- Table structure for sys_message
-- ----------------------------
DROP TABLE IF EXISTS `sys_message`;
CREATE TABLE `sys_message`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键,自增长',
  `ctime` datetime(0) NOT NULL COMMENT '添加时间',
  `utime` datetime(0) DEFAULT NULL COMMENT '更新时间',
  `addresser_id` int(11) DEFAULT NULL COMMENT '发件人ID',
  `addresser_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '发件人名称',
  `title` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '标题',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '内容',
  `is_read` int(1) NOT NULL DEFAULT 0 COMMENT '是否已读：0 未读，1已读',
  `is_send` int(1) NOT NULL DEFAULT 1 COMMENT '是否发送：0，为发送，1，已发送',
  `is_bind_wx` int(1) DEFAULT NULL COMMENT '是否绑定微信',
  `message_type` tinyint(3) DEFAULT NULL COMMENT '消息类型：1工单消息 2设备消息 3分润账单 4 租赁消息',
  `is_deleted` int(1) DEFAULT 0 COMMENT '是否删除：0未删除 1已删除',
  `is_send_all` int(1) DEFAULT 0 COMMENT '是否发送给全部用户：0不是全部用户 1全部系统用户 2全部微信用户 3全部app用户',
  `mac` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '设备Mac',
  `is_fixed` int(1) DEFAULT 1 COMMENT '是否解决：0未解决 1已解决',
  `command` varchar(450) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '故障，滤芯，租赁消息的触发条件',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `addresser_id_index`(`addresser_id`) USING BTREE,
  INDEX `index_ctime`(`ctime`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '系统消息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_message_to_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_message_to_user`;
CREATE TABLE `sys_message_to_user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键,自增长',
  `ctime` datetime(0) NOT NULL COMMENT '添加时间',
  `utime` datetime(0) DEFAULT NULL COMMENT '更新时间',
  `sys_message_id` int(11) DEFAULT NULL COMMENT '消息系统id',
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用户名称',
  `role_id` int(11) DEFAULT NULL,
  `role_name` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `is_read` int(1) NOT NULL DEFAULT 0 COMMENT '是否已读：0 未读，1已读',
  `is_send` int(1) NOT NULL DEFAULT 1 COMMENT '是否发送：0，为发送，1，已发送',
  `is_deleted` int(1) DEFAULT 0 COMMENT '是否删除：0未删除 1已删除',
  `user_type` int(1) DEFAULT 2 COMMENT '用户类型：1系统用户 2微信用户/app 用户',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `user_id_index`(`user_id`) USING BTREE,
  INDEX `role_id_index`(`role_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '系统消息用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键,自增长',
  `ctime` datetime(0) NOT NULL COMMENT '添加时间',
  `utime` datetime(0) DEFAULT NULL COMMENT '更新时间',
  `permission_key` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '菜单标识,前端使用',
  `permission_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '菜单名称',
  `p_permission_id` int(11) DEFAULT NULL COMMENT '父级别菜单ID',
  `icon` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'logo字符串',
  `uri` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '菜单uri',
  `permission_type` int(1) NOT NULL COMMENT '1:导航菜单，2:功能模块',
  `sort` int(6) NOT NULL DEFAULT 0 COMMENT '菜单排序',
  `sys_user_id` int(11) DEFAULT NULL COMMENT '操作人员ID',
  `sys_user_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '操作人员名称',
  `is_deleted` int(1) DEFAULT 0 COMMENT '是否删除：0未删除，1已删除',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_pmenu_id`(`p_permission_id`) USING BTREE,
  INDEX `idx_sort`(`sort`) USING BTREE,
  INDEX `idx_ctime`(`ctime`) USING BTREE,
  INDEX `idx_key`(`permission_key`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 807 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '系统权限(菜单)表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_permission
-- ----------------------------
INSERT INTO `sys_permission` VALUES (1, '2017-07-10 18:47:18', '2017-07-10 18:47:20', '/sys/setting', '系统管理', 0, 'setting', '/sys/setting', 1, 13, 1, '超级管理员', 0);
INSERT INTO `sys_permission` VALUES (2, '2017-06-28 23:05:37', '2017-06-28 23:05:37', '/sysUser', '系统用户', 1, 'menu', '/sys/sysUser/getListByPage', 1, 2, 1, '超级管理员', 0);
INSERT INTO `sys_permission` VALUES (3, '2017-07-06 11:45:00', '2017-07-06 11:45:00', '/sysPermission', '权限管理', 1, 'none', '/sys/sysPermission/getListByPage', 1, 0, 1, '超级管理员', 0);
INSERT INTO `sys_permission` VALUES (4, '2017-07-06 11:45:00', '2017-07-06 11:45:00', '/sysRole', '角色管理', 1, 'none', '/sys/sysRole/getListByPage', 1, 1, 1, '超级管理员', 0);
INSERT INTO `sys_permission` VALUES (5, '2017-07-07 11:20:00', '2017-07-07 11:20:00', '/sysConfig', '系统配置', 1, 'none', '/sys/sysConfig/getListByPage', 1, 0, 1, '超级管理员', 0);
INSERT INTO `sys_permission` VALUES (6, '2017-07-07 11:20:00', '2019-11-28 16:00:03', '/sys/sysUser/add', '添加用户', 2, 'addUser', '/sys/sysUser/add', 2, 0, 1, '超级管理员', 0);
INSERT INTO `sys_permission` VALUES (7, '2017-07-07 11:20:00', '2017-07-07 11:20:00', '/addRole', '添加角色', 4, 'addRole', '/sys/sysRole/add', 2, 0, 1, '超级管理员', 0);
INSERT INTO `sys_permission` VALUES (8, '2017-07-07 11:20:00', '2017-07-07 11:20:00', '/addPermission', '添加权限', 3, 'addPermission', '/sys/sysPermission/add', 2, 0, 1, '超级管理员', 0);
INSERT INTO `sys_permission` VALUES (11, '2017-07-11 14:25:16', '2017-07-11 14:25:16', '/sys/sysPermission/delete', '删除权限', 3, 'none', '/sys/sysPermission/delete', 2, 0, NULL, '超级管理员', 0);
INSERT INTO `sys_permission` VALUES (12, '2017-07-11 14:25:16', '2018-05-21 11:17:30', '/product', '产品管理', 0, 'appstore', '/productMain', 1, 3, 1, '超级管理员', 0);
INSERT INTO `sys_permission` VALUES (13, '2017-07-11 16:28:07', '2017-07-22 18:12:55', '/productList', '产品列表', 12, 'string', '/product/product/page', 1, 2, NULL, NULL, 0);
INSERT INTO `sys_permission` VALUES (14, '2017-07-11 14:25:16', '2017-07-22 18:11:27', '/productType', '产品品类', 12, 'product', '/product/productCategory/page', 1, 1, 1, '超级管理员', 0);
INSERT INTO `sys_permission` VALUES (29, '2017-07-11 14:56:02', '2017-07-11 14:56:02', '/sys/sysPermission/update', '更新权限', 3, 'string', '/sys/sysPermission/update', 2, 0, NULL, NULL, 0);
INSERT INTO `sys_permission` VALUES (31, '2017-07-11 15:04:10', '2017-07-11 15:04:10', '/sys/sysRole/delete', '删除角色', 4, 'string', '/sys/sysRole/delete', 2, 0, NULL, NULL, 0);
INSERT INTO `sys_permission` VALUES (35, '2017-07-11 15:38:31', '2017-07-11 15:38:31', '/product/productCategory/add', '新增品类', 14, 'string', '/product/productCategory/add', 2, 0, NULL, NULL, 0);
INSERT INTO `sys_permission` VALUES (36, '2017-07-11 15:45:23', '2017-07-11 15:45:23', '/product/productCategory/update', '修改品类', 14, 'string', '/product/productCategory/update', 2, 0, NULL, NULL, 0);
INSERT INTO `sys_permission` VALUES (37, '2017-07-11 15:45:43', '2017-07-11 15:45:43', '/product/productCategory/delete', '删除品类', 14, 'string', '/product/productCategory/delete', 2, 0, NULL, NULL, 0);
INSERT INTO `sys_permission` VALUES (38, '2017-07-11 15:46:23', '2017-07-11 15:46:23', '/product/product/add', '添加产品', 13, 'string', '/product/product/add', 2, 0, NULL, NULL, 0);
INSERT INTO `sys_permission` VALUES (39, '2017-07-11 15:46:46', '2017-07-11 15:46:46', '/product/product/delete', '删除产品', 13, 'string', '/product/product/delete', 2, 0, NULL, NULL, 0);
INSERT INTO `sys_permission` VALUES (41, '2017-07-11 16:25:53', '2017-07-11 16:25:53', '/sys/sysRole/update', '修改角色', 4, 'string', '/sys/sysRole/update', 2, 0, NULL, NULL, 0);
INSERT INTO `sys_permission` VALUES (42, '2017-07-11 16:27:20', '2017-07-11 16:27:20', '/sys/sysConfig/add', '新增配置', 5, 'string', '/sys/sysConfig/add', 2, 0, NULL, NULL, 0);
INSERT INTO `sys_permission` VALUES (43, '2017-07-11 16:27:50', '2017-07-11 16:27:50', '/sys/sysConfig/update', '修改配置', 5, 'string', '/sys/sysConfig/update', 2, 0, NULL, NULL, 0);
INSERT INTO `sys_permission` VALUES (44, '2017-07-11 16:28:07', '2017-07-11 16:28:07', '/sys/sysConfig/delete', '新增权限', 5, 'string', '/sys/sysConfig/delete', 2, 0, NULL, NULL, 0);
INSERT INTO `sys_permission` VALUES (45, '2017-07-11 18:31:15', '2017-07-11 18:31:15', '/sys/sysUser/update', '修改用户', 2, 'string', '/sys/sysUser/update', 2, 0, NULL, NULL, 0);
INSERT INTO `sys_permission` VALUES (46, '2017-07-12 14:40:18', '2017-07-21 10:39:45', '/serviceMode', '收费管理', 0, 'bank', '/serviceMode', 1, 9, 1, '超级管理员', 0);
INSERT INTO `sys_permission` VALUES (47, '2017-07-17 12:24:29', '2017-07-21 10:43:26', '/deviceLaunchArea', '投放点管理', 0, 'compass', '/device/deviceLaunchArea/list', 1, 10, 1, '超级管理员', 0);
INSERT INTO `sys_permission` VALUES (48, '2017-07-18 10:42:26', '2017-07-18 10:42:26', '/sys/sysRole/detail', '角色详情', 4, 'string', '/sys/sysRole/detail', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (49, '2017-07-18 10:55:12', '2017-07-18 11:24:26', '/device/deviceLaunchArea/add', '新增投放点', 47, 'string', '/device/deviceLaunchArea/add', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (51, '2017-07-18 11:02:56', '2017-07-20 11:11:59', '/device/deviceLaunchArea/update', '修改投放点', 47, 'string', '/device/deviceLaunchArea/update', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (53, '2017-07-18 11:04:59', '2017-07-20 11:47:57', '/product/productServiceMode/add', '新增收费模式', 46, 'string', '/product/productServiceMode/add', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (56, '2017-07-18 11:07:12', '2017-07-20 11:46:26', '/product/productServiceMode/update', '修改收费模式', 46, 'string', '/product/productServiceMode/update', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (57, '2017-07-18 11:07:46', '2017-07-20 12:27:05', '/product/productServiceMode/list', '查看收费模式列表', 46, 'string', '/product/productServiceMode/list', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (60, '2017-07-18 15:05:04', '2017-07-18 15:05:04', '/device/deviceLaunchArea/list', '查看投放点列表', 47, 'string', '/device/deviceLaunchArea/list', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (61, '2017-07-18 15:42:45', '2017-07-18 15:42:45', '/product/productServiceMode/AddServiceModePageData', '添加收费模式页面所需要的数据', 46, 'string', '/product/productServiceMode/AddServiceModePageData', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (62, '2017-07-18 15:43:32', '2017-07-18 15:43:54', 'device/deviceLaunchArea/maintainer', '维护人员', 47, 'string', '/device/deviceLaunchArea/maintainer', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (63, '2017-07-19 10:59:42', '2017-07-19 11:00:04', '/China/chinaArea/area', '获取全国省市行政区', 47, 'string', '/China/chinaArea/area', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (64, '2017-07-20 15:25:30', '2017-07-20 15:25:30', '/device/deviceLaunchArea/delete', '删除投放点', 47, 'string', '/device/deviceLaunchArea/delete', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (68, '2017-07-21 11:33:58', '2017-07-21 12:01:29', '/stat', '数据分析', 0, 'area-chart', '/stat', 1, 2, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (70, '2017-07-21 11:37:38', '2017-07-21 11:54:15', '/orderAnalysis', '订单分析', 68, 'string', '/stat/statOrder', 1, 1, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (72, '2017-07-21 11:39:44', '2017-07-21 11:39:44', '/stat/statOrder/analysis', '获取折柱图接口', 70, 'string', '/stat/statOrder/analysis', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (73, '2017-07-21 11:42:07', '2017-07-21 11:54:27', '/userAnalysis', '用户分析', 68, 'string', '/stat/statUserLocation', 1, 2, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (74, '2017-07-21 11:43:03', '2017-07-21 11:43:03', '/stat/statUserLocation/userDitribution', '获取用户分布图', 73, 'string', '/stat/statUserLocation/userDitribution', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (75, '2017-07-21 11:43:37', '2017-07-21 11:43:37', '/stat/statUserLocation/userRank', '获取用户分布图排行', 73, 'string', '/stat/statUserLocation/userRank', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (76, '2017-07-21 11:44:18', '2017-07-21 11:44:18', '/stat/statUserTrend/activeTrend', '获取活跃用户趋势图接口', 73, 'string', '/stat/statUserTrend/activeTrend', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (77, '2017-07-21 11:44:51', '2017-07-21 11:44:51', '/stat/statUserTrend/newTrend', '获取新增用户趋势图接口', 73, 'string', '/stat/statUserTrend/newTrend', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (78, '2017-07-21 11:45:25', '2017-07-21 11:45:25', '/stat/statUserTrend/sex', '获取用户性别分布图接口', 73, 'string', '/stat/statUserTrend/sex', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (79, '2017-07-21 11:45:59', '2017-07-21 11:45:59', '/stat/statUserTrend/times', '获取用户性别分布图接口', 73, 'string', '/stat/statUserTrend/times', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (80, '2017-07-21 11:46:14', '2017-07-21 11:46:14', '/stat/statUserTrend/totalTrend', '获取用户总数趋势图接口', 73, 'string', '/stat/statUserTrend/totalTrend', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (83, '2017-07-21 11:47:52', '2017-07-21 11:54:40', '/deviceAnalysis', '设备分析', 68, 'string', '/statDeviceLocation', 1, 3, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (84, '2017-07-21 11:48:23', '2017-07-21 11:48:23', '/stat/statDeviceLocation/deviceDitribution', '获取设备分布图', 83, 'string', '/stat/statDeviceLocation/deviceDitribution', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (85, '2017-07-21 11:48:44', '2017-07-21 11:48:44', '/stat/statDeviceLocation/deviceRank', '获取设备分布图排行', 83, 'string', '/stat/statDeviceLocation/deviceRank', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (89, '2017-07-21 11:50:56', '2017-07-21 11:50:56', '/stat/statDeviceTrend/activeTrend', '获取设备活跃趋势图数据接口', 83, 'string', '/stat/statDeviceTrend/activeTrend', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (90, '2017-07-21 11:51:02', '2017-07-21 11:51:02', '/stat/statDeviceTrend/newTrend', '获取设备新增趋势图数据接口', 83, 'string', '/stat/statDeviceTrend/newTrend', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (91, '2017-07-21 11:51:23', '2017-07-21 11:51:23', '/stat/statDeviceTrend/usePercentTrend', '获取设备使用率趋势图数据', 83, 'string', '/stat/statDeviceTrend/usePercentTrend', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (93, '2017-07-05 12:22:13', '2018-01-17 11:26:32', '/panel/update', '保存看板', 92, 'string', '/panel/update', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (98, '2017-07-21 12:27:20', '2017-07-21 12:27:20', '/panel/main', '个人面板', 92, 'string', '/panel/main', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (103, '2017-07-21 14:41:53', '2017-08-23 10:39:10', '/order', '订单中心', 0, 'wallet', '/order/orderBase/list123', 1, 6, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (106, '2017-07-21 15:13:04', '2017-12-13 18:15:57', '/panel/edit', '编辑', 92, 'string', '/panel/edit', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (110, '2017-07-21 18:03:19', '2017-07-21 18:22:33', '/device/deviceAlarm', '售后管理', 0, 'exclamation-circle-o', '/device/deviceAlarm', 1, 5, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (111, '2017-07-21 18:05:13', '2017-07-28 16:52:21', '/datapointList', '告警级别管理', 110, 'string', '/product/productDataPoint/listPage', 1, 2, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (112, '2017-07-21 18:07:35', '2017-07-28 16:52:10', '/alarmList', '告警设备列表', 110, 'string', '/device/deviceAlarm/list', 1, 1, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (113, '2017-07-21 18:08:27', '2017-07-21 18:08:27', '/device/deviceAlarm/deviceAlarmName', '故障名称', 112, 'string', '/device/deviceAlarm/deviceAlarmName', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (114, '2017-07-21 18:09:06', '2017-07-21 18:09:06', '/device/deviceAlarm/deviceAlramDetail', '故障详情', 112, 'string', '/device/deviceAlarm/deviceAlramDetail', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (115, '2017-07-21 18:09:37', '2017-07-21 18:09:37', '/device/deviceAlarm/sendMessage', '消息推送', 112, 'string', '/device/deviceAlarm/sendMessage', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (116, '2017-07-21 18:13:53', '2017-07-21 18:13:53', '/product/productDataPoint/list/{productId}', '产品数据点', 111, 'string', '/product/productDataPoint/list/{productId}', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (117, '2017-07-21 18:18:28', '2017-07-21 18:18:28', '/product/productDataPoint/update', '更新告警级别', 111, 'string', '/product/productDataPoint/update', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (118, '2017-07-21 20:35:22', '2017-07-21 20:35:22', '/device/deviceAlarm/list', '分页列表', 112, 'string', '/device/deviceAlarm/list', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (119, '2017-07-21 20:36:10', '2017-07-21 20:36:10', '/product/productDataPoint/listPage', '告警级别分页列表', 111, 'string', '/product/productDataPoint/listPage', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (120, '2017-07-22 13:37:38', '2017-07-26 17:57:27', '/deviceListView', '设备管理', 0, 'hdd', '/device/device/view', 1, 4, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (121, '2017-07-22 14:59:54', '2017-07-22 14:59:54', '/product/productServiceMode/isExist', '收费模式是否存在', 46, 'string', '/product/productServiceMode/isExist', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (122, '2017-07-24 10:40:47', '2017-07-24 10:40:47', '/product/productCategory/detail', '详情', 14, 'string', '/product/productCategory/detail', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (124, '2017-07-24 10:44:05', '2017-07-24 10:44:05', '/product/product/detail', '详情', 13, 'string', '/product/product/detail', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (127, '2017-07-24 10:45:49', '2017-07-24 10:45:49', '/product/product/sync', '同步', 13, 'string', '/product/product/sync', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (129, '2017-07-24 12:01:35', '2017-07-24 12:01:35', '/sys/sysUser/detail', '用户详情', 2, 'string', '/sys/sysUser/detail', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (130, '2017-07-24 12:21:38', '2017-07-24 12:21:38', '/sys/sysUser/delete', '删除用户', 2, 'string', '/sys/sysUser/delete', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (131, '2017-07-25 16:31:49', '2017-07-25 16:37:17', '/product/productDataPoint/sync/{productId}', '同步数据点', 13, 'string', '/product/productDataPoint/sync', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (132, '2017-07-25 17:35:29', '2017-07-25 17:35:29', '/product/productCommandConfig/list', '产品指令列表', 13, 'string', '/product/productCommandConfig/list', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (133, '2017-07-25 17:35:53', '2017-07-25 17:35:53', '/product/productCommandConfig/add', '新增产品指令', 13, 'string', '/product/productCommandConfig/add', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (134, '2017-07-25 17:36:10', '2017-07-25 17:36:10', '/product/productCommandConfig/delete', '删除产品指令', 13, 'string', '/product/productCommandConfig/delete', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (135, '2017-07-25 17:36:26', '2017-07-25 17:36:26', '/product/productCommandConfig/update', '修改产品指令', 13, 'string', '/product/productCommandConfig/update', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (137, '2017-07-26 16:08:22', '2017-07-26 16:08:22', '/product/product/update', '修改产品', 13, 'string', '/product/product/update', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (138, '2017-07-26 17:57:45', '2017-08-15 12:06:30', '/deviceList', '设备列表', 120, 'string', '/device', 1, 1, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (139, '2017-07-26 17:58:12', '2018-05-09 16:55:52', '/deviceMap', '设备地图展示', 120, 'none', '/device/device/list1', 1, 3, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (140, '2017-07-26 18:07:08', '2017-07-26 18:07:10', '/sys/sysUser/userDetailInfo', '个人信息', 0, 'string', '/sys/sysUser/userDetailInfo', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (141, '2017-07-26 20:34:46', '2017-07-28 14:27:11', '/product/productOperationHistory/list', '操作记录列表', 12, 'string', '/product/productOperationHistory/list', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (142, '2017-07-27 16:05:22', '2017-07-27 16:05:22', '/device/device/detail', '获取设备详情', 138, 'string', '/device/device/detail', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (143, '2017-07-27 16:05:43', '2017-07-27 16:05:43', '/device/device/add', '添加', 138, 'string', '/device/device/add', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (144, '2017-07-27 16:18:04', '2017-07-27 16:18:04', '/device/device/fire', '发送控制指令', 138, 'string', '/device/device/fire', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (145, '2017-07-27 16:18:29', '2017-07-27 16:18:29', '/device/device/list', '设备列表', 138, 'string', '/device/device/list', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (146, '2017-07-27 16:18:45', '2017-07-27 16:18:45', '/device/device/update', '更新', 138, 'string', '/device/device/update', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (155, '2017-07-27 17:57:50', '2017-08-31 17:57:12', '/product/productServiceMode/deletePrice', '删除价格', 46, 'string', '/product/productServiceMode/deletePrice', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (156, '2017-07-27 18:06:47', '2017-07-27 18:06:47', '/device/device/delete', '删除', 138, 'string', '/device/device/delete', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (157, '2017-07-28 14:24:36', '2017-07-28 14:25:32', '/messageList', '消息管理', 0, 'mail', '/message/sysMessage/list', 1, 15, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (158, '2017-07-28 14:26:07', '2017-07-28 14:27:42', '/device/device/deviceRunningRecord/list', '获取运行日志', 138, 'string', '/device/deviceRunningRecord/list', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (166, '2017-07-29 16:53:09', '2017-07-29 16:53:09', '/sys/sysUser/updateInfoPersonal', '更新用户自身信息', 2, 'string', '/sys/sysUser/updateInfoPersonal', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (167, '2017-07-29 16:53:55', '2017-07-29 16:53:55', '/sys/sysUser/resetPwd', '重置密码', 2, 'string', '/sys/sysUser/resetPwd', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (168, '2017-07-29 17:58:06', '2017-07-29 17:58:06', '/device/map/detail', '地图详情', 139, 'string', '/device/map/detail', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (169, '2017-07-29 17:58:35', '2017-07-29 17:58:35', '/device/map/list', '地图列表', 139, 'string', '/device/map/list', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (172, '2017-08-02 14:27:12', '2017-08-02 14:27:12', '/userManage', '用户管理', 0, 'user', '/userManage', 1, 8, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (173, '2017-08-02 14:46:50', '2018-06-05 14:39:56', '/wxList', '用户列表', 172, 'none', '/user/page', 1, 1, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (174, '2017-08-02 14:47:33', '2017-08-05 12:50:26', '/wxBlackList', '黑名单', 172, 'none', '/blackList', 1, 2, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (181, '2017-08-02 23:11:53', '2017-08-03 16:34:03', '/operator', '运营商管理', 0, 'usergroup-add', '/operator', 1, 12, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (182, '2017-08-02 23:19:16', '2017-08-26 15:52:51', '/operatorList', '运营商列表', 181, 'none', '/operator/list', 1, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (183, '2017-08-03 16:25:39', '2017-08-03 16:59:50', '/feedback', '意见反馈', 0, 'message', '/message/feedbackUser/list', 1, 14, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (184, '2017-08-04 11:26:33', '2017-08-04 11:26:33', '/operator/bindingAccount', '运营商可绑定用户列表', 181, 'sd', '/operator/bindingAccount', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (185, '2017-08-04 11:27:07', '2017-08-04 11:27:07', '/operator/add', '添加运营商', 181, 'as', '/operator/add', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (186, '2017-08-04 11:27:30', '2017-08-04 11:27:30', '/operator/detail', '运营商详情', 181, 'asd', '/operator/detail', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (187, '2017-08-04 11:27:50', '2017-08-04 11:27:50', '/operator/listByOperator', '直接下级运营商', 181, 'asd', '/operator/listByOperator', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (188, '2017-08-04 11:28:07', '2017-08-04 11:28:07', '/operator/switch', '切换状态', 181, 'asd', '/operator/switch', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (189, '2017-08-04 11:28:23', '2017-08-04 11:28:23', '/operator/update', '更新运营商', 181, 'asd', '/operator/update', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (190, '2017-08-04 11:32:10', '2017-08-04 11:32:10', '/device/deviceLaunchArea/listByOperator', '运营商直接投放点', 47, '123', '/device/deviceLaunchArea/listByOperator', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (192, '2017-08-04 14:47:12', '2017-08-04 14:47:12', '/message/feedbackUser/detail', '详情', 183, 'string', '/message/feedbackUser/detail', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (193, '2017-08-04 14:47:39', '2017-08-04 14:47:39', '/message/feedbackUser/list', '分页列表', 183, 'string', '/message/feedbackUser/list', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (194, '2017-08-04 14:50:03', '2017-08-04 14:50:03', '/message/feedbackBusiness/detail', '业务详情', 183, 'string', '/message/feedbackBusiness/detail', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (195, '2017-08-04 14:50:24', '2017-08-04 14:50:24', '/message/feedbackBusiness/list', '业务分页列表', 183, 'string', '/message/feedbackBusiness/list', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (196, '2017-08-04 17:55:42', '2017-08-04 17:55:49', '/operator/delete', '删除运营商', 181, 'none', '/operator/delete', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (197, '2017-08-05 10:34:19', '2017-08-05 10:34:19', '/product/productCategory/page', '查看品类列表', 14, 'string', '/product/productCategory/page', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (198, '2017-08-05 11:38:06', '2017-09-02 17:03:06', '/billList1', '分润管理', 0, 'red-envelope', '/billList1', 1, 7, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (199, '2017-08-05 11:39:18', '2017-08-05 11:39:18', '/shareBenefitRule', '分润规则', 198, '123', '/shareBenefitRule', 1, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (200, '2017-08-05 11:40:13', '2017-08-05 11:40:13', '/benefit/shareBenefitRule/devices', '指定运营商下设备列表', 199, '123', '/benefit/shareBenefitRule/devices', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (201, '2017-08-05 11:40:40', '2017-08-05 11:40:40', '/benefit/shareBenefitRule/sonOperators', '获取可用子运营商', 199, '123', '/benefit/shareBenefitRule/sonOperators', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (202, '2017-08-05 11:47:38', '2018-05-31 12:19:36', '/user/detail', '用户详情', 173, 'none', '/user/detail', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (203, '2017-08-05 11:58:40', '2018-05-31 12:19:52', '/user/moveIn', '移入黑名单', 173, 'none', '/user/moveIn', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (204, '2017-08-05 11:59:34', '2018-05-31 12:20:33', '/user/moveOut', '移出黑名单', 174, 'none', '/user/moveOut', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (205, '2017-08-05 12:12:45', '2017-08-05 12:12:45', '/benefit/shareBenefitRule/nameIsExist', '校验名称是否存在', 199, '123', '/benefit/shareBenefitRule/nameIsExist', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (206, '2017-08-05 16:03:22', '2017-08-05 16:03:22', '/benefit/shareBenefitRule/add', '添加分润规则', 199, '123', '/benefit/shareBenefitRule/add', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (207, '2017-08-05 16:34:29', '2017-08-05 16:34:29', '/benefit/shareBenefitRule/list', '分润规则列表', 199, '123', '/benefit/shareBenefitRule/list', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (208, '2017-08-05 16:47:20', '2017-08-05 16:47:20', '/benefit/shareBenefitRule/detail', '分润规则详情接口', 199, '123', '/benefit/shareBenefitRule/detail', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (209, '2017-08-05 16:58:57', '2017-08-05 16:58:57', '/benefit/shareBenefitRule/delete', '删除分润规则', 199, '123', '/benefit/shareBenefitRule/delete', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (210, '2017-08-06 14:03:41', '2017-09-05 17:42:07', '/device/device/assign', '设备分配', 138, 'string', '/device/device/assign', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (211, '2017-08-06 14:16:43', '2017-08-06 14:16:43', '/device/deviceLaunchArea/associatedOperator', '投放点关联运营商', 47, '123', '/device/deviceLaunchArea/associatedOperator', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (212, '2017-08-09 11:40:34', '2017-08-09 11:40:34', '/device/deviceLaunchArea/checkIfBinded', '是否关联运营商', 47, 'string', '/device/deviceLaunchArea/checkIfBinded', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (213, '2017-08-09 11:51:33', '2017-09-02 17:03:17', '/billList', '分润账单', 198, 'string', '/billList', 1, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (214, '2017-08-09 11:51:57', '2017-08-09 11:51:57', '/benefit/shareBenefitSheet/audit', '账单审核', 213, 'string', '/benefit/shareBenefitSheet/audit', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (215, '2017-08-09 11:52:20', '2017-08-09 11:52:20', '/benefit/shareBenefitSheet/detail', '账单详情', 213, 'string', '/benefit/shareBenefitSheet/detail', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (216, '2017-08-09 11:52:45', '2017-08-09 11:52:45', '/benefit/shareBenefitSheet/execute', '分润', 213, 'string', '/benefit/shareBenefitSheet/execute', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (217, '2017-08-09 11:53:10', '2017-08-09 11:53:10', '/benefit/shareBenefitSheet/page', '列表', 213, 'string', '/benefit/shareBenefitSheet/page', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (218, '2017-08-09 11:53:33', '2017-08-09 11:53:33', '/benefit/shareBenefitSheet/reaudit', '重新审核', 213, 'string', '/benefit/shareBenefitSheet/reaudit', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (219, '2017-08-09 11:56:30', '2017-08-09 11:56:30', '/benefit/shareBenefitRule/update', '分润规则信息更新', 199, 'string', '/benefit/shareBenefitRule/update', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (220, '2017-08-09 11:57:14', '2017-08-09 11:57:14', '/benefit/shareBenefitRuleDetail/delete', '分润比例删除', 199, 'string', '/benefit/shareBenefitRuleDetail/delete', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (221, '2017-08-09 11:57:58', '2017-08-09 11:57:58', '/benefit/shareBenefitRuleDetail/detailNameIsExist', '校验分润比例名称是否存在', 199, 'string', '/benefit/shareBenefitRuleDetail/detailNameIsExist', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (222, '2017-08-09 11:58:58', '2017-08-09 11:58:58', '/benefit/shareBenefitSheetOrder/list', '参与分润的订单列表', 213, 'string', '/benefit/shareBenefitSheetOrder/list', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (223, '2017-08-16 17:05:20', '2017-08-16 17:15:49', '/device-group-controller', '设备分组', 120, 'string', '/device-group-controller', 1, 2, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (224, '2017-08-16 17:05:54', '2017-08-23 15:16:47', '/device/deviceGroup/add', '添加', 223, 'string', '/device/deviceGroup/add', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (225, '2017-08-16 17:06:25', '2017-08-16 17:06:25', '/device/deviceGroup/detail', '分组详情', 223, 'string', '/device/deviceGroup/detail', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (226, '2017-08-16 17:06:46', '2017-08-16 17:06:46', '/device/deviceGroup/deviceList', '全部设备', 223, 'string', '/device/deviceGroup/deviceList', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (227, '2017-08-16 17:07:09', '2017-08-16 17:07:09', '/device/deviceGroup/page', '列表', 223, 'string', '/device/deviceGroup/page', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (228, '2017-08-16 17:07:24', '2017-08-16 17:07:24', '/device/deviceGroup/update', '更新', 223, 'string', '/device/deviceGroup/update', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (229, '2017-08-16 17:07:49', '2017-08-16 17:07:49', '/export-controller', '导出', 0, 'string', '/export-controller', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (230, '2017-08-16 17:08:16', '2017-08-16 17:08:16', '/benefit/shareBenefitRule/export', '分润规则列表导出', 229, 'string', '/benefit/shareBenefitRule/export', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (231, '2017-08-16 17:08:40', '2017-08-16 17:08:40', '/benefit/shareBenefitSheet/export', '分润单列表导出', 229, 'string', '/benefit/shareBenefitSheet/export', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (232, '2017-08-16 17:09:00', '2017-08-16 17:09:00', '/device/device/export', '设备列表导出', 229, 'string', '/device/device/export', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (233, '2017-08-16 17:09:22', '2017-08-16 17:09:22', '/device/deviceAlarm/export', '告警设备列表导出', 229, 'string', '/device/deviceAlarm/export', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (234, '2017-08-16 17:09:40', '2017-08-16 17:09:40', '/device/deviceLaunchArea/export', '投放点列表导出', 229, 'string', '/device/deviceLaunchArea/export', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (235, '2017-08-16 17:10:02', '2017-08-16 17:10:02', '/operator/export', '运营商列表导出', 229, 'string', '/operator/export', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (236, '2017-08-16 17:10:26', '2017-08-16 17:10:26', '/order/orderBase/export', '订单列表导出', 229, 'string', '/order/orderBase/export', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (237, '2017-08-16 17:10:53', '2017-08-16 17:10:53', '/product/product/export', '产品列表导出', 229, 'string', '/product/product/export', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (238, '2017-08-16 17:11:11', '2017-08-16 17:11:11', '/product/productCategory/export', '品类列表导出', 229, 'string', '/product/productCategory/export', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (239, '2017-08-16 17:11:32', '2017-08-16 17:11:32', '/product/productServiceMode/export', '收费模式列表导出', 229, 'string', '/product/productServiceMode/export', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (240, '2017-08-16 17:11:55', '2017-08-16 17:11:55', '/user/black/export', '用户黑名单列表导出', 229, 'string', '/user/black/export', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (241, '2017-08-16 17:12:18', '2017-08-16 17:12:18', '/user/normal/export', '用户列表导出', 229, 'string', '/user/normal/export', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (242, '2017-08-16 17:12:35', '2017-08-16 17:12:35', '/wallet/userWalletChargeOrder/export', '充值记录列表导出', 229, 'string', '/wallet/userWalletChargeOrder/export', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (243, '2017-08-16 17:16:56', '2017-08-16 17:16:56', '/wallet/userWalletChargeOrder/list', '钱包充值单列表', 198, 'string', '/wallet/userWalletChargeOrder/list', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (244, '2017-08-16 17:18:01', '2017-08-16 17:18:01', '/wallet/userWallet/balance', '钱包当前余额', 198, 'string', '/wallet/userWallet/balance', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (245, '2017-08-16 17:18:33', '2017-08-16 17:18:33', '/wallet/userWallet/operation', '钱包充值或支付', 198, 'string', '/wallet/userWallet/operation', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (246, '2017-08-16 17:18:58', '2017-08-16 17:18:58', '/wallet/userWallet/recharge', '钱包充值页面', 198, 'string', '/wallet/userWallet/recharge', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (247, '2017-08-16 17:19:27', '2017-08-16 17:19:27', '/wallet/userWalletUseRecord/list', '用户钱包操作记录', 198, 'string', '/wallet/userWalletUseRecord/list', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (248, '2017-08-18 20:21:40', '2017-08-18 20:21:40', '/stat/statFaultAlertType/faultType', '售后类型占比统计', 112, 'string', '/stat/statFaultAlertType/faultType', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (249, '2017-08-21 16:38:24', '2017-08-21 16:38:24', '/message/sysMessage/role', '获取可以发送消息的角色', 157, 'none', '/message/sysMessage/role', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (250, '2017-08-21 16:56:48', '2017-08-21 16:56:48', '/message/sysMessage/add', '添加信息', 157, 'none', '/message/sysMessage/add', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (259, '2017-08-23 10:39:49', '2017-08-23 10:39:49', '/orderList', '订单列表', 103, 'none', '/order/orderBase/list', 1, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (260, '2017-08-23 10:41:25', '2017-08-23 10:41:25', '/rechargeList', '充值列表', 103, 'none', '/wallet/userWalletChargeOrder/list', 1, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (261, '2017-08-23 10:42:41', '2017-08-23 10:42:41', '/order/orderBase/listByOperator', '运营商直接订单', 259, 'none', '/order/orderBase/listByOperator', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (262, '2017-08-23 10:43:22', '2017-08-23 10:43:22', '/order/orderBase/orderDetail', '订单详情', 259, 'none', '/order/orderBase/orderDetail', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (263, '2017-08-24 12:24:22', '2017-08-24 12:24:22', '/agent', '代理商管理', 0, 'usergroup-add', '/agent', 1, 11, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (264, '2017-08-24 12:26:04', '2017-08-26 16:06:01', '/agentList', '代理商列表', 263, 'none', '/agent/list', 1, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (265, '2017-08-25 17:40:22', '2017-09-05 20:05:52', '/app/manage/device/list', '微信用户端设备列表', 138, 'none', '/app/manage/device/list', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (268, '2017-08-28 10:40:22', '2017-09-01 15:54:04', '/device/deviceAlarm/appList', 'App端分页故障列表', 110, 'none', '/device/deviceAlarm/appList', 2, 1, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (269, '2017-08-28 15:51:04', '2017-08-28 15:51:19', '/agent/delete', '删除代理商', 263, 'none', '/agent/delete', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (270, '2017-08-28 10:40:22', '2017-08-28 10:40:22', '/order/orderBase/orderAppDetail', 'App端订单详情', 259, 'none', '/order/orderBase/orderAppDetail', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (271, '2017-08-28 16:10:35', '2019-11-15 16:41:32', '/agent/add', '添加代理商', 263, 'none', '/agent/add', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (272, '2017-08-28 16:37:28', '2017-08-28 16:37:28', '/agent/detail', '代理商详情', 263, 'none', '/agent/detail', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (273, '2017-08-28 18:42:11', '2017-08-28 18:42:11', '/agent/children', '下级代理商管理', 263, 'none', '/agent/children', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (274, '2017-08-28 19:43:11', '2017-09-05 18:14:50', '/cardList1', '充值卡管理', 0, 'idcard', '/cardList1', 1, 16, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (275, '2017-08-28 19:49:48', '2017-09-05 18:16:34', '/cardList', '充值卡列表', 274, 'none', '/cardList', 1, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (278, '2017-08-29 14:17:18', '2017-08-29 14:17:18', '/agent/switch', '切换状态', 263, 'none', '/agent/switch', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (279, '2017-08-29 14:18:20', '2017-08-29 14:18:20', '/agent/update', '更新代理商', 263, 'none', '/agent/update', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (280, '2017-08-29 15:32:02', '2017-08-29 15:32:02', '/operator/uploadLogo', '上传LOGO', 181, 'none', '/operator/uploadLogo', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (290, '2017-09-01 14:52:49', '2017-09-01 14:52:49', '/depositList', '押金列表', 103, 'none', '/depositList', 1, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (292, '2017-09-02 10:21:24', '2017-09-02 10:21:24', '/device/qrcode/export', '批量导出二维码', 138, 'none', '/device/qrcode/export', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (293, '2017-09-02 10:52:26', '2017-09-02 10:52:26', '/device/qrcode/upload', '设备导入', 138, 'none', '/device/qrcode/upload', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (294, '2017-09-04 17:26:51', '2017-09-04 17:28:02', '/benefit/shareBenefitRule/range', '获取分润比例区间', 199, 'none', '/benefit/shareBenefitRule/range', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (295, '2017-09-05 17:34:24', '2017-09-05 17:34:24', '/service_mode', '查看产品收费类型', 13, 'none', '/service_mode', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (296, '2017-09-05 17:36:00', '2017-09-05 17:36:00', '/user/userChargeCard/chargeRecords', '充值记录', 275, 'string', '/user/userChargeCard/chargeRecords', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (297, '2017-09-05 17:36:23', '2017-09-05 17:36:23', '/user/userChargeCard/detail', '详情', 275, 'string', '/user/userChargeCard/detail', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (298, '2017-09-05 17:36:48', '2017-09-05 17:36:48', '/user/userChargeCard/disable', '禁用', 275, 'string', '/user/userChargeCard/disable', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (299, '2017-09-05 17:37:45', '2017-09-05 17:37:45', '/user/userChargeCard/enable', '启用', 275, 'string', '/user/userChargeCard/enable', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (301, '2017-09-05 17:38:14', '2017-09-05 17:38:14', '/control_command', '查看产品控制指令', 13, 'none', '/control_command', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (302, '2017-09-05 17:38:30', '2017-09-05 17:38:30', '/user/userChargeCard/operateRecords', '操作记录', 275, 'string', '/user/userChargeCard/operateRecords', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (303, '2017-09-05 17:38:55', '2017-09-05 17:38:55', '/user/userChargeCard/list', '列表', 275, 'string', '/user/userChargeCard/list', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (304, '2017-09-05 17:38:59', '2017-09-05 17:38:59', '/status_command', '查看产品状态指令', 13, 'none', '/status_command', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (305, '2017-09-05 17:39:20', '2017-09-05 17:39:20', '/user/userChargeCard/orders', '充值卡订单', 275, 'string', '/user/userChargeCard/orders', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (306, '2017-09-05 17:40:44', '2017-09-05 17:40:44', '/device/deviceGroup/assign', '分配', 223, 'string', '/device/deviceGroup/assign', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (307, '2017-09-05 17:41:14', '2017-09-05 17:41:14', '/device/deviceGroup/preAssign', '获取分配目标', 223, 'string', '/device/deviceGroup/preAssign', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (308, '2017-09-05 17:41:36', '2017-09-05 17:41:36', '/device/deviceGroup/unbind', '解绑', 223, 'string', '/device/deviceGroup/unbind', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (309, '2017-09-05 17:42:36', '2017-09-05 17:42:36', '/device/device/unbind', '解绑', 138, 'string', '/device/device/unbind', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (310, '2017-09-05 17:43:55', '2017-09-05 17:43:55', '/device/device/preAssign', '获取分配目标', 138, 'string', '/device/device/preAssign', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (311, '2017-09-05 17:49:06', '2017-09-05 18:12:02', '/rechargeSettings', '充值设定', 0, 'idcard', '/rechargeSettings', 1, 17, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (312, '2017-09-05 17:49:25', '2017-09-05 17:49:25', '/setting/chargeSetting/add', '添加', 311, 'string', '/setting/chargeSetting/add', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (313, '2017-09-05 17:49:38', '2017-09-05 17:49:38', '/setting/chargeSetting/delete', '删除', 311, 'string', '/setting/chargeSetting/delete', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (314, '2017-09-05 17:50:10', '2017-09-08 10:50:48', '/setting/chargeSetting/list', '列表', 311, 'string', '/setting/chargeSetting/list', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (315, '2017-09-05 17:51:16', '2017-09-05 17:51:16', '/agent/bindingAccount', '绑定已有账号下拉列表', 263, 'string', '/agent/bindingAccount', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (317, '2017-09-05 19:55:25', '2017-09-15 14:25:36', ' /majiang/user/login', '管理端系统用户登录', 0, 'none', ' /majiang/user/login', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (318, '2017-09-05 20:00:19', '2017-09-15 11:39:48', '/manager', '管理端', 0, 'none', '/manager', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (328, '2017-09-05 20:16:22', '2017-09-05 20:16:22', '/majiang/user/role', '当前用户角色', 340, 'none', '/majiang/user/role', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (329, '2017-09-05 21:05:23', '2017-09-15 15:10:38', '/mjdevice', '设备', 318, 'none', '/mjdevice', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (330, '2017-09-05 21:07:34', '2017-09-05 21:07:34', '/majiang/device/currentDevice', '当前用户设备列表', 329, 'none', '/majiang/device/currentDevice', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (338, '2017-09-05 21:12:55', '2017-09-15 14:22:03', '/majiang/device/detail', '设备详情', 329, 'none', '/majiang/device/detail', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (339, '2017-09-05 21:13:21', '2017-09-05 21:13:21', '/majiang/device/device', '设备列表', 329, 'none', '/majiang/device/device', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (343, '2017-09-07 12:07:07', '2017-09-07 12:07:07', '/product/product/pull', '产品下拉列表', 0, 'string', '/product/product/pull', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (344, '2017-09-07 12:19:13', '2017-09-07 12:19:13', '/product/product/page', '产品列表接口', 0, 'string', '/product/product/page', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (345, '2017-09-07 14:07:52', '2017-09-07 14:07:52', '/benefit/shareBenefitSheet/group', '分润订单状态汇总', 213, 'none', '/benefit/shareBenefitSheet/group', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (346, '2017-09-07 15:04:47', '2017-09-07 15:04:47', '/benefit/shareBenefitSheet/calculate', '计算订单合计金额', 213, 'none', '/benefit/shareBenefitSheet/calculate', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (349, '2017-09-08 16:09:47', '2017-09-08 16:09:47', '/wallet/userWalletChargeOrder/depositList', '获取押金列表', 290, 'string', '/wallet/userWalletChargeOrder/depositList', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (350, '2017-09-08 16:10:43', '2017-09-08 16:10:43', '/wallet/userWalletChargeOrder/create', '创建充值单', 290, 'string', '/wallet/userWalletChargeOrder/create', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (351, '2017-09-08 16:11:02', '2017-09-08 16:11:02', '/wallet/userWalletChargeOrder/depositDetail', '押金详情', 290, 'string', '/wallet/userWalletChargeOrder/depositDetail', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (354, '2017-09-08 16:15:13', '2017-09-08 16:15:13', '/wallet/userWalletChargeOrder/refund', '退款', 290, 'string', '/wallet/userWalletChargeOrder/refund', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (355, '2017-09-08 16:15:28', '2017-09-08 16:15:28', '/wallet/userWalletChargeOrder/refundInfo', '退款信息', 290, 'string', '/wallet/userWalletChargeOrder/refundInfo', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (356, '2017-09-15 11:41:41', '2017-09-15 11:41:41', '/app/deviceAlarm/detail', '故障详情', 329, 'none', '/app/deviceAlarm/detail', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (357, '2017-09-15 11:44:12', '2017-09-15 15:10:29', '/app/manager/manager/alarmList', '故障列表', 318, 'none', '/app/manager/manager/alarmList', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (358, '2017-09-15 11:45:11', '2017-09-15 15:10:24', '/app/manager/manager/launchList', '投放点列表', 318, 'none', '/app/manager/manager/launchList', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (359, '2017-09-16 11:21:51', '2017-09-16 11:21:51', '/device/deviceAlarm/detailList', '故障详情页故障列表', 112, 'ddal', '/device/deviceAlarm/detailList', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (361, '2017-09-18 21:22:19', '2017-09-18 21:22:19', '/stat/statDeviceOrderWidget/device', '设备看板', 0, 'string', '/stat/statDeviceOrderWidget/device', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (362, '2017-09-18 21:22:47', '2017-09-18 21:22:47', '/stat/statDeviceOrderWidget/alarm', '故障看板', 0, 'string', '/stat/statDeviceOrderWidget/alarm', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (363, '2017-09-18 21:23:56', '2017-09-18 21:23:56', '/stat/statUserWidget/user', '用户看板', 0, 'string', '/stat/statUserWidget/user', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (364, '2017-09-19 14:10:54', '2017-09-19 14:10:54', '/user/userChargeCard/upload', '充值卡导入', 275, 'none', '/user/userChargeCard/upload', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (365, '2017-09-28 20:47:21', '2017-09-28 20:47:21', '/batch', '投放批次', 0, 'calendar', '/batch', 1, 5, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (366, '2017-09-28 20:47:47', '2017-09-28 20:47:47', '/device/deviceLaunchBatch/list', '投放批次列表', 365, 'string', '/device/deviceLaunchBatch/list', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (367, '2017-09-28 20:48:13', '2017-09-28 20:48:13', '/device/deviceLaunchBatch/update', '修改投放批次', 365, 'string', '/device/deviceLaunchBatch/update', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (368, '2017-09-28 20:55:33', '2017-09-28 20:55:33', '/device/deviceLaunchBatch/delete', '删除投放批次', 365, 'string', '/device/deviceLaunchBatch/delete', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (369, '2017-09-29 11:02:59', '2017-09-29 11:02:59', '/device/device/batchUpdate', '批量修改设备', 138, 'string', '/device/device/batchUpdate', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (370, '2017-09-29 11:22:22', '2017-09-29 11:22:22', '/device/deviceLaunchBatch/deviceList', '投放详情下的设备列表', 365, 'string', '/device/deviceLaunchBatch/deviceList', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (371, '2017-09-29 11:22:43', '2017-09-29 11:22:43', '/device/deviceLaunchBatch/orderList', '投放批次下的订单列表', 365, 'string', '/device/deviceLaunchBatch/orderList', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (372, '2017-09-29 12:25:54', '2017-09-29 12:25:54', '/device/device/batchFire', '批量控制设备', 138, 'string', '/device/device/batchFire', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (373, '2017-09-30 09:32:24', '2017-09-30 09:32:24', '/product/productServiceMode/delete', '删除收费模式', 46, 'string', '/product/productServiceMode/delete', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (374, '2017-09-30 10:25:35', '2017-09-30 10:25:35', 'kali', '卡励', 0, 'kali', 'kali', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (375, '2017-09-30 10:26:40', '2017-09-30 10:28:33', '/app/manage/device/scan', '管理端扫码绑定', 374, 'scan', '/app/manage/device/scan', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (376, '2017-09-30 10:27:12', '2017-09-30 10:28:28', '/app/manage/device/bindList', '管理端绑定设备列表', 374, 'bindList', '/app/manage/device/bindList', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (377, '2017-09-30 14:54:35', '2017-09-30 14:54:35', '/app/manage/device/unbind', '移除设备', 374, 'none', '/app/manage/device/unbind', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (384, '2017-12-14 09:37:20', '2017-12-14 09:37:20', '/stat/statDeviceOrderWidget/order3', '测试根权限', 0, 'string', '/stat/statDeviceOrderWidget/order2', 1, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (385, '2018-05-09 15:34:05', '2018-05-09 15:34:05', '/stat/statDeviceTrend/newActivatedTrend', '设备活跃趋势', 83, 'none', '/stat/statDeviceTrend/newActivatedTrend', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (388, '2018-05-09 15:41:01', '2018-05-09 15:42:08', '/product/productDataPointExtController/query', '第三方天气数据获取', 13, 'none', '/product/productDataPointExtController/query', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (389, '2018-05-10 11:23:12', '2018-06-07 11:56:29', '/device/device/addMoreMode', '修改收费模式', 138, 'none', '/device/device/addMoreMode', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (390, '2018-05-10 11:42:03', '2018-06-07 11:58:23', '/device/device/assignModeOrArea-area', '投放按钮展示', 138, 'none', '/device/device/assignModeOrArea-area', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (391, '2018-05-10 11:44:08', '2018-06-07 11:58:35', '/device/device/assignModeOrArea-mode', '收费按钮展示', 138, 'none', '/device/device/assignModeOrArea-mode', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (400, '2017-12-08 16:48:54', '2017-12-08 16:48:54', '/workOrder', '工单管理', 110, 'none', '/workOrder', 1, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (401, '2017-12-08 16:49:18', '2018-02-27 11:29:35', '/workOrder/update', '工单状态更新', 400, 'none', '/workOrder/update', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (402, '2017-12-08 16:49:39', '2018-02-27 11:29:34', '/workOrder/manualAdd', '后台手工添加工单', 400, 'none', '/workOrder/manualAdd', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (403, '2017-12-08 16:50:02', '2018-02-27 15:53:29', '/workOrder/edit', '修改工单详情', 400, 'none', '/workOrder/edit', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (404, '2017-12-08 16:50:19', '2018-02-27 15:53:27', '/workOrder/detail', '工单详情', 400, 'none', '/workOrder/detail', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (405, '2017-12-08 16:50:38', '2018-02-27 15:53:24', '/workOrder/delete', '删除工单', 400, 'none', '/workOrder/delete', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (406, '2017-12-08 16:51:03', '2018-02-27 15:53:22', '/workOrder/allot', '批量分配维修人员', 400, 'none', '/workOrder/allot', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (407, '2017-12-08 16:51:21', '2018-02-27 15:53:21', '/workOrder/list', '工单列表', 400, 'none', '/workOrder/list', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (408, '2017-12-24 16:14:58', '2018-02-27 11:29:24', '/app/workOrder/appCount', '管理端工单数量统计', 318, 'none', '/app/workOrder/appCount', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (409, '2017-12-24 20:57:11', '2018-02-27 11:29:24', '/app/workOrder/appList', '管理端工单列表', 318, 'none', '/app/workOrder/appList', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (410, '2017-12-27 18:05:23', '2018-02-27 11:29:18', '/app/workOrder/appDetail', '管理端工单详情', 318, 'none', '/app/workOrder/appDetail', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (411, '2017-12-28 16:24:25', '2018-02-27 11:29:17', '/app/workOrder/process', '管理端开始处理工单', 318, 'none', '/app/workOrder/process', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (412, '2017-12-28 16:36:36', '2018-02-27 11:29:16', '/app/workOrder/complete', '管理端完成工单', 318, 'none', '/app/workOrder/complete', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (413, '2018-01-02 12:05:43', '2018-02-27 11:29:13', '/app/workOrder/removeDevice', '管理端工单移除设备', 318, 'none', '/app/workOrder/removeDevice', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (414, '2018-01-02 12:06:33', '2018-02-27 11:29:12', '/app/workOrder/scan', '管理端扫码关联设备', 318, 'none', '/app/workOrder/scan', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (415, '2018-01-09 16:42:15', '2018-02-27 11:29:08', '/app/workOrder/editInfo', '管理端编辑工单信息', 318, 'none', '/app/workOrder/editInfo', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (416, '2018-01-16 17:07:48', '2018-02-27 15:53:07', '/workOrder/transform', '告警设备转为工单', 112, 'none', '/workOrder/transform', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (417, '2018-01-19 15:21:25', '2018-02-27 15:53:06', '/workOrder/handler', '获取工单处理人', 400, 'none', '/workOrder/handler', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (418, '2018-01-19 15:58:12', '2018-02-27 15:53:05', '/workOrder/launchArea', '获取投放点', 400, 'string', '/workOrder/launchArea', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (419, '2018-01-25 18:40:48', '2018-02-27 15:53:04', '/workOrder/complete', '完成工单', 400, 'none', '/workOrder/complete', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (420, '2018-01-30 20:06:02', '2018-02-27 15:53:03', '/workOrder/maintainer', '获取投放点对应的维护人员', 400, 'none', '/workOrder/maintainer', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (421, '2018-01-31 12:37:30', '2018-02-27 15:53:02', '/workOrder/process', '待处理的工单进行处理', 400, 'none', '/workOrder/process', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (422, '2018-02-01 23:13:19', '2018-02-27 15:53:00', '/workOrder/close', '关闭待受理工单', 400, 'none', '/workOrder/close', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (423, '2018-03-23 16:31:36', '2018-03-23 16:31:36', '/workOrder/export', '工单导出', 229, 'none', '/workOrder/export', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (424, '2018-03-28 10:22:12', '2018-03-28 10:22:12', '/workOrder/accept', '受理工单', 400, 'none', '/workOrder/accept', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (425, '2018-04-27 13:59:38', '2018-04-27 13:59:38', '/workOrder/checkMac', '检查设备的是否在同一个投放点', 400, 'none', '/workOrder/checkMac', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (479, '2018-01-12 20:46:07', '2018-01-12 20:46:07', '/cardCoupons', '卡劵管理', 0, 'credit-card', '/card/card/list', 1, 14, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (480, '2018-01-13 12:41:00', '2018-02-02 18:17:57', '/card/card/detail', '卡劵详情', 479, 'none', '/card/card/detail', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (481, '2018-01-15 14:20:00', '2018-02-02 18:17:50', '/card/card/sync', '同步卡券', 479, 'none', '/card/card/sync', 2, 0, 149, 'icardx', 0);
INSERT INTO `sys_permission` VALUES (482, '2018-01-15 14:44:19', '2018-02-02 18:17:36', '/card/card/sync/status', '查询卡券同步状态', 479, 'none', '/card/card/sync/status', 2, 0, 149, 'icardx', 0);
INSERT INTO `sys_permission` VALUES (483, '2018-01-31 13:59:34', '2018-02-02 18:17:30', '/card/card/dispatch/info', '获取卡劵投放信息', 479, 'none', '/card/card/dispatch/info', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (484, '2018-01-31 14:55:22', '2018-02-02 18:17:16', '/card/card/dispatch', '投放卡劵', 479, 'none', '/card/card/dispatch', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (485, '2018-01-31 14:56:01', '2018-02-02 18:17:09', '/card/card/cover/upload', '上传卡劵封面', 479, 'none', '/card/card/cover/upload', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (542, '2018-02-09 11:06:39', '2018-02-27 13:42:09', '/refundManager', '退款管理', 0, 'bank', '/refund/list', 1, 7, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (545, '2018-02-09 11:08:24', '2018-03-14 10:47:45', '/refund', '退款列表', 542, 'none', '/refund/refund', 1, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (546, '2018-02-09 11:09:15', '2018-02-27 15:52:58', '/refund/apply', '退款申请', 545, 'none', '/refund/apply', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (547, '2018-02-09 11:10:41', '2018-02-27 13:42:05', '/refund/detail', '退款详情', 545, 'none', '/refund/detail', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (548, '2018-02-09 11:11:16', '2018-02-27 13:42:04', '/refund/refund', '执行退款', 545, 'none', '/refund/refund', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (549, '2018-02-09 11:11:40', '2018-02-27 13:42:03', '/refund/statistics', '已选退款订单统计', 545, 'none', '/refund/statistics', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (550, '2018-02-09 15:24:25', '2018-02-27 13:42:02', '/refund/audit', '审核退款', 545, 'none', '/refund/audit', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (551, '2018-02-09 17:17:44', '2018-02-27 15:52:57', '/refund/export', '导出退款订单', 545, 'none', '/refund/export', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (552, '2018-02-09 17:21:37', '2018-02-27 11:28:37', '/app/refund/check', 'app订单详情', 318, 'none', '/app/refund/check', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (553, '2018-02-09 17:24:20', '2018-02-27 11:28:36', '/app/refund/apply', 'app订单详情', 318, 'none', '/app/refund/apply', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (755, '2018-03-29 17:29:08', '2018-03-29 17:30:06', '/installed', '装机规则设定', 0, 'api', '/installed', 1, 5, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (756, '2018-03-29 17:30:29', '2018-04-18 12:00:17', '/installedRule', '装机规则列表', 755, 'none', '/install/installFeeRule/list', 1, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (757, '2018-04-18 12:00:52', '2018-04-18 12:00:52', '/install/installFeeRule/add', '添加装机规则', 756, 'none', '/install/installFeeRule/add', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (758, '2018-04-18 12:01:13', '2018-04-18 12:01:13', '/install/installFeeRule/delete', '删除装机规则', 756, 'none', '/install/installFeeRule/delete', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (759, '2018-04-18 12:01:45', '2018-04-18 12:01:45', '/install/installFeeRule/detail', '装机规则详情', 756, 'none', '/install/installFeeRule/detail', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (760, '2018-04-18 12:02:13', '2018-04-18 12:02:13', '/install/installFeeRule/edit', '编辑装机规则', 756, 'none', '/install/installFeeRule/edit', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (761, '2018-04-18 12:02:39', '2018-04-18 17:41:50', '/install/installFeeRule/link', '关联装机规则', 756, 'none', '/install/installFeeRule/link', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (762, '2018-04-18 17:42:10', '2018-04-28 10:19:32', '/install/installFeeRule/agentList', '规则内代理商', 756, 'none', '/install/installFeeRule/agentList', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (763, '2018-04-18 17:42:41', '2018-04-18 17:42:41', '/install/installFeeRule/operatorList', '规则内运营商', 756, 'none', '/install/installFeeRule/operatorList', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (768, '2018-04-19 11:06:19', '2018-04-19 11:08:23', '/installOrder', '初装费订单列表', 103, 'none', '/installOrder', 1, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (769, '2018-04-19 11:06:53', '2018-04-19 11:08:33', '/install/installFeeOrder/list', '列表', 768, 'none', '/install/installFeeOrder/list', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (770, '2018-04-19 11:07:10', '2018-04-19 11:08:28', '/install/installFeeOrder/detail', '详情', 768, 'none', '/install/installFeeOrder/detail', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (771, '2018-04-28 10:20:04', '2018-04-28 10:20:04', '/install/installFeeRule/agentListForLink', '可关联的代理商列表', 756, 'none', '/install/installFeeRule/agentListForLink', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (772, '2018-04-28 10:20:29', '2018-04-28 10:20:29', '/install/installFeeRule/operatorListForLink', '可关联的运营商列表', 756, 'none', '/install/installFeeRule/operatorListForLink', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (773, '2018-04-28 10:21:03', '2018-04-28 10:21:03', '/install/installFeeRule/unlink', '解除关联', 756, 'none', '/install/installFeeRule/unlink', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (774, '2018-05-11 12:05:28', '2018-05-11 12:05:28', '/faultList', '故障设备列表', 110, 'none', '/faultList', 1, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (775, '2018-05-11 12:11:45', '2018-05-11 12:11:45', '/shareBenefitSetting', '分润设置', 198, 'none', '/shareBenefitSetting', 1, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (776, '2018-05-11 12:12:19', '2018-05-11 12:12:19', '/benefit/shareBenefitRuleModifyLimit/detail', '查询分润规则修改次数限制详情', 775, 'none', '/benefit/shareBenefitRuleModifyLimit/detail', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (777, '2018-05-11 12:12:47', '2018-05-11 12:12:47', '/benefit/shareBenefitRuleModifyLimit/setting', '设置分润规则修改次数限制', 775, 'none', '/benefit/shareBenefitRuleModifyLimit/setting', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (778, '2018-05-11 12:13:17', '2018-05-11 12:13:17', '/benefit/shareBenefitRule/info', '分润信息', 198, 'none', '/benefit/shareBenefitRule/info', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (779, '2018-05-11 12:13:44', '2018-05-11 12:13:44', '/benefit/shareBenefitSheet/benefit/list', '分润列表', 198, 'none', '/benefit/shareBenefitSheet/benefit/list', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (780, '2018-05-11 14:37:16', '2018-05-11 14:37:16', '/agent/export', '代理商导出', 229, 'none', '/agent/export', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (782, '2018-05-11 14:43:43', '2018-05-11 14:43:43', '/abnormalOrder', '异常订单', 103, 'none', '/abnormalOrder', 1, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (783, '2018-05-11 14:44:32', '2018-05-11 14:44:32', '/order/orderBase/finish', '将异常订单转化为完成', 782, 'none', '/order/orderBase/finish', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (784, '2018-05-11 14:54:12', '2018-05-11 14:54:12', '/user/stat', '用户统计', 73, 'none', '/user/stat', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (786, '2018-05-16 15:07:08', '2018-05-16 15:07:08', '/dashboard', '首页', 0, 'home', '/dashboard', 1, 1, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (788, '2018-05-21 11:17:26', '2018-05-21 11:17:26', '/userMenu', '查询用户菜单', 140, 'userMenu', '/sys/sysUser/menu', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (790, '2018-05-21 11:17:29', '2018-05-21 11:17:29', '/userDetailInfo', '个人信息详情', 140, 'userDetailInfo', '/sys/sysUser/userDetailInfo', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (792, '2018-05-22 10:02:21', '2018-05-22 10:02:21', '/device/device/unlock', '解锁设备', 138, 'none', '/device/device/unlock', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (793, '2018-05-22 13:21:15', '2018-05-22 13:21:15', '/panel/update', '保存看板', 786, 'none', '/panel/update', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (794, '2018-05-22 13:21:40', '2018-05-22 13:21:40', '/panel/main', '个人面板', 786, 'none', '/panel/main', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (795, '2018-05-22 13:21:58', '2018-05-22 13:21:58', '/panel/edit', '编辑', 786, 'none', '/panel/edit', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (796, '2018-06-05 15:25:21', '2018-06-05 15:25:21', '/agent/children', '厂商建立的代理商列表', 181, 'none', '/agent/children', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (797, '2018-06-05 15:26:32', '2018-06-05 15:26:32', '/operator/children', '代理商建立的运营商列表', 181, 'none', '/operator/children', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (798, '2018-06-07 11:49:45', '2018-06-07 11:58:58', '/device/device/assignModeOrArea', '批量投放设备和批量分配收费模式', 138, 'none', '/device/device/assignModeOrArea', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (801, '2018-06-08 11:36:23', '2018-06-08 11:39:19', '/stat/statDeviceOrderWidget/order', '订单看板', 0, 'string', '/stat/statDeviceOrderWidget/order', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (802, '2018-06-12 14:46:35', '2018-06-12 14:46:35', '/product/productServiceMode/detailInfo', '收费模式详情', 46, 'none', '/product/productServiceMode/detailInfo', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (803, '2018-06-27 10:11:38', '2018-06-27 10:11:38', '/benefit/shareBenefitRule/info', '获取当前登录账号的分润规则信息', 199, 'none', '/benefit/shareBenefitRule/info', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (804, '2018-07-10 18:38:43', '2018-07-11 09:23:56', '/shareResult', '分润结果', 198, 'none', '/shareResult', 1, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (805, '2018-07-10 18:39:04', '2018-07-10 18:39:04', '/benefit/orderShareProfit/page', '分润结果列表', 804, 'none', '/benefit/orderShareProfit/page', 2, 0, 1, 'admin', 0);
INSERT INTO `sys_permission` VALUES (806, '2019-03-19 19:28:33', '2019-03-19 19:28:33', '/alarmAnalysis', '告警分析', 68, 'string', '/stat/statAlarm', 1, 0, 1, 'admin', 0);

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键,自增长',
  `ctime` datetime(0) NOT NULL COMMENT '添加时间',
  `utime` datetime(0) DEFAULT NULL COMMENT '更新时间',
  `role_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '角色名称',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注描述',
  `sys_user_id` int(11) DEFAULT NULL COMMENT '操作人员ID',
  `sys_user_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '操作人员名称',
  `is_share_data` int(1) NOT NULL DEFAULT 0 COMMENT '是否共享数据,1:共享,共享sys_user_id的数据给拥有此角色的人,0:不共享',
  `share_benefit_type` int(1) DEFAULT 1 COMMENT '分润权限类型，1：无，2：入账，3：收款',
  `is_share_service_mode` int(1) DEFAULT 0 COMMENT '是否共享收费模块数据：0否 1是',
  `is_add_more_service_mode` int(1) DEFAULT 0 COMMENT '设备是否能添加多个收费模式：0否 1是',
  `is_deleted` int(1) DEFAULT 0 COMMENT '是否删除：0未删除，1已删除',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_name`(`role_name`) USING BTREE,
  INDEX `idx_ctime`(`ctime`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '系统角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, '2017-06-28 20:57:05', '2019-09-27 15:34:06', '超级管理员', '超级管理员', 1, 'admin', 0, 0, 0, 1, 0);
INSERT INTO `sys_role` VALUES (2, '2019-09-24 19:20:48', '2019-11-14 10:53:09', '厂商管理员', '拥有管理权限', 1, 'admin', 1, 3, 1, 1, 0);
INSERT INTO `sys_role` VALUES (3, '2019-11-14 16:10:16', '2019-11-14 16:10:16', '经销商', '经销商', 1, 'admin', 0, 1, 0, 0, 0);
INSERT INTO `sys_role` VALUES (4, '2019-11-19 15:20:27', '2019-11-19 15:23:26', '操作员', '操作员扫码入库', 596, 'suzhou', 0, 1, 0, 0, 0);
INSERT INTO `sys_role` VALUES (5, '2019-11-19 15:23:00', '2019-11-19 15:23:00', '入库员', '设备入库', 596, 'suzhou', 0, 1, 0, 0, 0);

-- ----------------------------
-- Table structure for sys_role_to_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_to_permission`;
CREATE TABLE `sys_role_to_permission`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键,自增长',
  `ctime` datetime(0) NOT NULL COMMENT '添加时间',
  `utime` datetime(0) DEFAULT NULL COMMENT '更新时间',
  `role_id` int(11) NOT NULL COMMENT '角色ID',
  `permission_id` int(11) NOT NULL COMMENT '权限(菜单)ID',
  `sys_user_id` int(11) DEFAULT NULL COMMENT '操作人员ID',
  `sys_user_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '操作人员名称',
  `is_deleted` int(1) DEFAULT 0 COMMENT '是否删除：0未删除，1已删除',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_ctime`(`ctime`) USING BTREE,
  INDEX `idx_role_menu`(`role_id`, `permission_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 35323 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '角色权限关系表(多对多)' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_to_permission
-- ----------------------------
INSERT INTO `sys_role_to_permission` VALUES (1, '2017-06-28 20:58:26', '2017-06-28 20:58:28', 1, 1, 1, '测试', 0);
INSERT INTO `sys_role_to_permission` VALUES (2, '2017-07-04 11:16:45', '2017-07-04 11:16:45', 1, 2, 1, '测试', 0);
INSERT INTO `sys_role_to_permission` VALUES (3, '2017-07-06 12:16:00', '2017-07-06 12:16:00', 1, 3, 1, '测试', 0);
INSERT INTO `sys_role_to_permission` VALUES (4, '2017-07-06 12:26:00', '2017-07-06 12:26:00', 1, 4, 1, '测试', 0);
INSERT INTO `sys_role_to_permission` VALUES (5, '2017-07-07 11:20:00', '2017-07-07 11:20:00', 1, 6, 1, '测试', 0);
INSERT INTO `sys_role_to_permission` VALUES (6, '2017-07-07 14:13:00', '2017-07-07 14:13:00', 1, 7, 1, '测试', 0);
INSERT INTO `sys_role_to_permission` VALUES (7, '2017-07-07 14:13:00', '2017-07-07 14:13:00', 1, 8, 1, '测试', 0);
INSERT INTO `sys_role_to_permission` VALUES (10, '2017-07-07 15:06:00', '2017-07-07 15:06:00', 1, 11, 1, '测试', 0);
INSERT INTO `sys_role_to_permission` VALUES (14, '2017-07-07 15:06:00', '2017-07-07 15:06:00', 1, 14, 1, '测试', 0);
INSERT INTO `sys_role_to_permission` VALUES (18, '2017-07-07 15:06:00', '2017-07-07 15:06:00', 1, 41, 1, '测试', 0);
INSERT INTO `sys_role_to_permission` VALUES (60, '2017-07-18 14:13:21', NULL, 1, 51, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (62, '2017-07-18 14:13:21', NULL, 1, 49, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (64, '2017-07-18 14:13:21', NULL, 1, 57, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (65, '2017-07-18 14:13:21', NULL, 1, 56, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (67, '2017-07-18 14:13:21', NULL, 1, 53, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (68, '2017-07-18 14:13:21', NULL, 1, 46, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (70, '2017-07-18 14:13:21', NULL, 1, 38, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (71, '2017-07-18 14:13:21', NULL, 1, 37, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (72, '2017-07-18 14:13:21', NULL, 1, 36, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (73, '2017-07-18 14:13:21', NULL, 1, 35, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (74, '2017-07-18 14:13:21', NULL, 1, 44, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (75, '2017-07-18 14:13:21', NULL, 1, 43, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (76, '2017-07-18 14:13:21', NULL, 1, 42, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (77, '2017-07-18 14:13:21', NULL, 1, 5, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (78, '2017-07-18 14:13:21', NULL, 1, 29, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (79, '2017-07-18 14:13:21', NULL, 1, 48, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (80, '2017-07-18 14:13:21', NULL, 1, 31, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (81, '2017-07-18 14:13:21', NULL, 1, 45, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (82, '2017-07-18 15:03:56', NULL, 1, 39, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (83, '2017-07-18 15:03:56', NULL, 1, 13, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (86, '2017-07-18 15:56:42', NULL, 1, 60, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (87, '2017-07-18 15:56:42', NULL, 1, 61, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (88, '2017-07-18 17:37:44', NULL, 1, 62, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (90, '2017-07-19 11:01:03', NULL, 1, 63, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (97, '2017-07-20 15:49:28', NULL, 1, 64, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (98, '2017-07-20 15:49:28', NULL, 1, 47, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (101, '2017-07-21 11:51:55', NULL, 1, 91, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (102, '2017-07-21 11:51:55', NULL, 1, 90, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (103, '2017-07-21 11:51:55', NULL, 1, 89, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (107, '2017-07-21 11:51:55', NULL, 1, 85, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (108, '2017-07-21 11:51:55', NULL, 1, 84, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (109, '2017-07-21 11:51:55', NULL, 1, 83, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (111, '2017-07-21 11:51:55', NULL, 1, 80, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (112, '2017-07-21 11:51:55', NULL, 1, 79, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (113, '2017-07-21 11:51:55', NULL, 1, 78, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (114, '2017-07-21 11:51:55', NULL, 1, 77, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (115, '2017-07-21 11:51:55', NULL, 1, 76, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (116, '2017-07-21 11:51:55', NULL, 1, 75, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (117, '2017-07-21 11:51:55', NULL, 1, 74, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (118, '2017-07-21 11:51:55', NULL, 1, 73, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (119, '2017-07-21 11:51:55', NULL, 1, 72, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (120, '2017-07-21 11:51:55', NULL, 1, 70, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (121, '2017-07-21 11:51:55', NULL, 1, 68, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (123, '2017-07-21 14:43:14', NULL, 1, 103, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (124, '2017-07-21 14:43:14', NULL, 1, 98, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (125, '2017-07-21 14:43:14', NULL, 1, 93, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (127, '2017-07-21 15:21:44', NULL, 1, 106, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (129, '2017-07-21 18:18:44', NULL, 1, 115, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (130, '2017-07-21 18:18:44', NULL, 1, 114, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (131, '2017-07-21 18:18:44', NULL, 1, 113, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (132, '2017-07-21 18:18:44', NULL, 1, 112, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (133, '2017-07-21 18:18:44', NULL, 1, 117, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (134, '2017-07-21 18:18:44', NULL, 1, 116, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (135, '2017-07-21 18:18:44', NULL, 1, 111, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (136, '2017-07-21 18:18:44', NULL, 1, 110, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (137, '2017-07-21 20:36:24', NULL, 1, 118, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (138, '2017-07-21 20:36:24', NULL, 1, 119, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (139, '2017-07-22 13:37:50', NULL, 1, 120, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (140, '2017-07-22 15:00:08', NULL, 1, 121, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (219, '2017-07-24 10:46:36', NULL, 1, 127, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (222, '2017-07-24 10:46:36', NULL, 1, 124, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (223, '2017-07-24 10:46:36', NULL, 1, 122, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (224, '2017-07-24 12:04:50', NULL, 1, 129, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (225, '2017-07-24 12:21:53', NULL, 1, 130, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (226, '2017-07-25 16:31:57', NULL, 1, 131, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (227, '2017-07-25 17:36:33', NULL, 1, 135, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (228, '2017-07-25 17:36:33', NULL, 1, 134, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (229, '2017-07-25 17:36:33', NULL, 1, 133, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (230, '2017-07-25 17:36:33', NULL, 1, 132, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (392, '2017-07-26 16:08:33', NULL, 1, 137, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (393, '2017-07-26 17:58:20', NULL, 1, 139, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (394, '2017-07-26 17:58:20', NULL, 1, 138, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (396, '2017-07-26 20:34:55', NULL, 1, 140, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (397, '2017-07-26 20:34:55', NULL, 1, 141, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (403, '2017-07-27 16:13:55', NULL, 1, 143, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (404, '2017-07-27 16:13:55', NULL, 1, 142, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (405, '2017-07-27 16:19:11', NULL, 1, 146, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (406, '2017-07-27 16:19:11', NULL, 1, 145, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (407, '2017-07-27 16:19:11', NULL, 1, 144, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (409, '2017-07-27 17:58:02', NULL, 1, 155, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (410, '2017-07-27 18:07:02', NULL, 1, 156, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (411, '2017-07-28 14:25:01', NULL, 1, 157, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (412, '2017-07-28 14:26:22', NULL, 1, 158, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (446, '2017-07-28 14:26:22', NULL, 1, 166, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (447, '2017-07-28 14:26:22', NULL, 1, 167, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (448, '2017-07-29 18:00:25', NULL, 1, 168, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (449, '2017-07-29 18:00:40', NULL, 1, 169, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (615, '2017-08-04 14:48:01', '2017-08-04 14:48:01', 1, 190, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (617, '2017-08-04 14:48:01', '2017-08-04 14:48:01', 1, 193, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (668, '2017-08-04 16:32:38', '2017-08-04 16:32:38', 1, 172, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (669, '2017-08-04 16:32:38', '2017-08-04 16:32:38', 1, 173, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (670, '2017-08-04 16:32:38', '2017-08-04 16:32:38', 1, 174, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (671, '2017-08-04 16:32:38', '2017-08-04 16:32:38', 1, 181, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (672, '2017-08-04 16:32:38', '2017-08-04 16:32:38', 1, 182, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (673, '2017-08-04 16:32:38', '2017-08-04 16:32:38', 1, 183, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (674, '2017-08-04 16:32:38', '2017-08-04 16:32:38', 1, 184, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (675, '2017-08-04 16:32:38', '2017-08-04 16:32:38', 1, 185, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (676, '2017-08-04 16:32:38', '2017-08-04 16:32:38', 1, 186, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (677, '2017-08-04 16:32:38', '2017-08-04 16:32:38', 1, 187, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (678, '2017-08-04 16:32:38', '2017-08-04 16:32:38', 1, 188, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (679, '2017-08-04 16:32:38', '2017-08-04 16:32:38', 1, 189, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (680, '2017-08-04 16:32:38', '2017-08-04 16:32:38', 1, 192, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (681, '2017-08-04 16:32:38', '2017-08-04 16:32:38', 1, 194, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (682, '2017-08-04 16:32:38', '2017-08-04 16:32:38', 1, 195, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (19159, '2017-08-29 15:32:42', '2017-08-29 15:32:42', 1, 196, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (19160, '2017-08-29 15:32:42', '2017-08-29 15:32:42', 1, 197, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (19161, '2017-08-29 15:32:42', '2017-08-29 15:32:42', 1, 198, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (19162, '2017-08-29 15:32:42', '2017-08-29 15:32:42', 1, 199, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (19163, '2017-08-29 15:32:42', '2017-08-29 15:32:42', 1, 200, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (19164, '2017-08-29 15:32:42', '2017-08-29 15:32:42', 1, 201, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (19165, '2017-08-29 15:32:42', '2017-08-29 15:32:42', 1, 202, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (19166, '2017-08-29 15:32:42', '2017-08-29 15:32:42', 1, 203, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (19167, '2017-08-29 15:32:42', '2017-08-29 15:32:42', 1, 204, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (19168, '2017-08-29 15:32:42', '2017-08-29 15:32:42', 1, 205, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (19169, '2017-08-29 15:32:42', '2017-08-29 15:32:42', 1, 206, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (19170, '2017-08-29 15:32:42', '2017-08-29 15:32:42', 1, 207, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (19171, '2017-08-29 15:32:42', '2017-08-29 15:32:42', 1, 208, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (19172, '2017-08-29 15:32:42', '2017-08-29 15:32:42', 1, 209, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (19173, '2017-08-29 15:32:42', '2017-08-29 15:32:42', 1, 210, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (19174, '2017-08-29 15:32:42', '2017-08-29 15:32:42', 1, 211, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (19175, '2017-08-29 15:32:42', '2017-08-29 15:32:42', 1, 212, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (19176, '2017-08-29 15:32:42', '2017-08-29 15:32:42', 1, 213, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (19177, '2017-08-29 15:32:42', '2017-08-29 15:32:42', 1, 214, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (19178, '2017-08-29 15:32:42', '2017-08-29 15:32:42', 1, 215, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (19179, '2017-08-29 15:32:42', '2017-08-29 15:32:42', 1, 216, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (19180, '2017-08-29 15:32:42', '2017-08-29 15:32:42', 1, 217, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (19181, '2017-08-29 15:32:42', '2017-08-29 15:32:42', 1, 218, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (19182, '2017-08-29 15:32:42', '2017-08-29 15:32:42', 1, 219, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (19183, '2017-08-29 15:32:42', '2017-08-29 15:32:42', 1, 220, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (19184, '2017-08-29 15:32:42', '2017-08-29 15:32:42', 1, 221, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (19185, '2017-08-29 15:32:42', '2017-08-29 15:32:42', 1, 222, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (19186, '2017-08-29 15:32:42', '2017-08-29 15:32:42', 1, 223, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (19187, '2017-08-29 15:32:42', '2017-08-29 15:32:42', 1, 224, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (19188, '2017-08-29 15:32:42', '2017-08-29 15:32:42', 1, 225, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (19189, '2017-08-29 15:32:42', '2017-08-29 15:32:42', 1, 226, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (19190, '2017-08-29 15:32:42', '2017-08-29 15:32:42', 1, 227, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (19191, '2017-08-29 15:32:42', '2017-08-29 15:32:42', 1, 228, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (19192, '2017-08-29 15:32:42', '2017-08-29 15:32:42', 1, 229, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (19193, '2017-08-29 15:32:42', '2017-08-29 15:32:42', 1, 230, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (19194, '2017-08-29 15:32:42', '2017-08-29 15:32:42', 1, 231, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (19195, '2017-08-29 15:32:42', '2017-08-29 15:32:42', 1, 232, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (19196, '2017-08-29 15:32:42', '2017-08-29 15:32:42', 1, 233, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (19197, '2017-08-29 15:32:42', '2017-08-29 15:32:42', 1, 234, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (19198, '2017-08-29 15:32:42', '2017-08-29 15:32:42', 1, 235, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (19199, '2017-08-29 15:32:42', '2017-08-29 15:32:42', 1, 236, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (19200, '2017-08-29 15:32:42', '2017-08-29 15:32:42', 1, 237, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (19201, '2017-08-29 15:32:42', '2017-08-29 15:32:42', 1, 238, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (19202, '2017-08-29 15:32:42', '2017-08-29 15:32:42', 1, 239, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (19203, '2017-08-29 15:32:42', '2017-08-29 15:32:42', 1, 240, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (19204, '2017-08-29 15:32:42', '2017-08-29 15:32:42', 1, 241, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (19205, '2017-08-29 15:32:42', '2017-08-29 15:32:42', 1, 242, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (19206, '2017-08-29 15:32:42', '2017-08-29 15:32:42', 1, 243, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (19207, '2017-08-29 15:32:42', '2017-08-29 15:32:42', 1, 244, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (19208, '2017-08-29 15:32:42', '2017-08-29 15:32:42', 1, 245, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (19209, '2017-08-29 15:32:42', '2017-08-29 15:32:42', 1, 246, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (19210, '2017-08-29 15:32:42', '2017-08-29 15:32:42', 1, 247, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (19211, '2017-08-29 15:32:42', '2017-08-29 15:32:42', 1, 248, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (19212, '2017-08-29 15:32:42', '2017-08-29 15:32:42', 1, 249, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (19213, '2017-08-29 15:32:42', '2017-08-29 15:32:42', 1, 250, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (19214, '2017-08-29 15:32:42', '2017-08-29 15:32:42', 1, 259, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (19215, '2017-08-29 15:32:42', '2017-08-29 15:32:42', 1, 260, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (19216, '2017-08-29 15:32:42', '2017-08-29 15:32:42', 1, 261, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (19217, '2017-08-29 15:32:42', '2017-08-29 15:32:42', 1, 262, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (19218, '2017-08-29 15:32:42', '2017-08-29 15:32:42', 1, 263, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (19219, '2017-08-29 15:32:42', '2017-08-29 15:32:42', 1, 264, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (19220, '2017-08-29 15:32:42', '2017-08-29 15:32:42', 1, 265, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (19221, '2017-08-29 15:32:42', '2017-08-29 15:32:42', 1, 268, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (19222, '2017-08-29 15:32:42', '2017-08-29 15:32:42', 1, 269, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (19223, '2017-08-29 15:32:42', '2017-08-29 15:32:42', 1, 270, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (19224, '2017-08-29 15:32:42', '2017-08-29 15:32:42', 1, 271, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (19225, '2017-08-29 15:32:42', '2017-08-29 15:32:42', 1, 272, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (19226, '2017-08-29 15:32:42', '2017-08-29 15:32:42', 1, 273, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (19227, '2017-08-29 15:32:42', '2017-08-29 15:32:42', 1, 274, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (19228, '2017-08-29 15:32:42', '2017-08-29 15:32:42', 1, 275, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (19229, '2017-08-29 15:32:42', '2017-08-29 15:32:42', 1, 278, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (19230, '2017-08-29 15:32:42', '2017-08-29 15:32:42', 1, 279, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (19231, '2017-08-29 15:32:42', '2017-08-29 15:32:42', 1, 280, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (23127, '2017-09-07 12:14:46', '2017-09-07 12:14:46', 1, 290, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (23129, '2017-09-07 12:14:46', '2017-09-07 12:14:46', 1, 292, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (23130, '2017-09-07 12:14:46', '2017-09-07 12:14:46', 1, 293, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (23131, '2017-09-07 12:14:46', '2017-09-07 12:14:46', 1, 294, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (23132, '2017-09-07 12:14:46', '2017-09-07 12:14:46', 1, 295, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (23133, '2017-09-07 12:14:46', '2017-09-07 12:14:46', 1, 296, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (23134, '2017-09-07 12:14:46', '2017-09-07 12:14:46', 1, 297, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (23135, '2017-09-07 12:14:46', '2017-09-07 12:14:46', 1, 298, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (23136, '2017-09-07 12:14:46', '2017-09-07 12:14:46', 1, 299, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (23137, '2017-09-07 12:14:46', '2017-09-07 12:14:46', 1, 301, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (23138, '2017-09-07 12:14:46', '2017-09-07 12:14:46', 1, 302, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (23139, '2017-09-07 12:14:46', '2017-09-07 12:14:46', 1, 303, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (23140, '2017-09-07 12:14:46', '2017-09-07 12:14:46', 1, 304, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (23141, '2017-09-07 12:14:46', '2017-09-07 12:14:46', 1, 305, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (23142, '2017-09-07 12:14:46', '2017-09-07 12:14:46', 1, 306, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (23143, '2017-09-07 12:14:46', '2017-09-07 12:14:46', 1, 307, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (23144, '2017-09-07 12:14:46', '2017-09-07 12:14:46', 1, 308, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (23145, '2017-09-07 12:14:46', '2017-09-07 12:14:46', 1, 309, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (23146, '2017-09-07 12:14:46', '2017-09-07 12:14:46', 1, 310, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (23147, '2017-09-07 12:14:46', '2017-09-07 12:14:46', 1, 311, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (23148, '2017-09-07 12:14:46', '2017-09-07 12:14:46', 1, 312, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (23149, '2017-09-07 12:14:46', '2017-09-07 12:14:46', 1, 313, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (23150, '2017-09-07 12:14:46', '2017-09-07 12:14:46', 1, 314, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (23151, '2017-09-07 12:14:46', '2017-09-07 12:14:46', 1, 315, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (23152, '2017-09-07 12:14:46', '2017-09-07 12:14:46', 1, 317, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (23153, '2017-09-07 12:14:46', '2017-09-07 12:14:46', 1, 318, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (23162, '2017-09-07 12:14:46', '2017-09-07 12:14:46', 1, 328, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (23163, '2017-09-07 12:14:46', '2017-09-07 12:14:46', 1, 329, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (23164, '2017-09-07 12:14:46', '2017-09-07 12:14:46', 1, 330, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (23169, '2017-09-07 12:14:46', '2017-09-07 12:14:46', 1, 338, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (23170, '2017-09-07 12:14:46', '2017-09-07 12:14:46', 1, 339, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (23172, '2017-09-07 12:14:46', '2017-09-07 12:14:46', 1, 343, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (26954, '2017-09-19 14:11:42', '2017-09-19 14:11:42', 1, 344, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (26955, '2017-09-19 14:11:42', '2017-09-19 14:11:42', 1, 345, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (26956, '2017-09-19 14:11:42', '2017-09-19 14:11:42', 1, 346, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (26957, '2017-09-19 14:11:42', '2017-09-19 14:11:42', 1, 349, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (26958, '2017-09-19 14:11:42', '2017-09-19 14:11:42', 1, 350, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (26959, '2017-09-19 14:11:42', '2017-09-19 14:11:42', 1, 351, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (26960, '2017-09-19 14:11:42', '2017-09-19 14:11:42', 1, 354, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (26961, '2017-09-19 14:11:42', '2017-09-19 14:11:42', 1, 355, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (26962, '2017-09-19 14:11:42', '2017-09-19 14:11:42', 1, 356, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (26963, '2017-09-19 14:11:42', '2017-09-19 14:11:42', 1, 357, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (26964, '2017-09-19 14:11:42', '2017-09-19 14:11:42', 1, 358, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (26965, '2017-09-19 14:11:42', '2017-09-19 14:11:42', 1, 359, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (26967, '2017-09-19 14:11:42', '2017-09-19 14:11:42', 1, 361, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (26968, '2017-09-19 14:11:42', '2017-09-19 14:11:42', 1, 362, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (26969, '2017-09-19 14:11:42', '2017-09-19 14:11:42', 1, 363, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (26970, '2017-09-19 14:11:42', '2017-09-19 14:11:42', 1, 364, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34629, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 6, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34630, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 45, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34631, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 129, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34632, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 130, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34633, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 166, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34634, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 167, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34635, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 2, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34636, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 8, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34637, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 11, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34638, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 29, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34639, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 3, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34640, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 7, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34641, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 31, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34642, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 41, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34643, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 48, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34644, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 4, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34645, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 42, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34646, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 43, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34647, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 44, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34648, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 5, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34649, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 1, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34650, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 38, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34651, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 39, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34652, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 124, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34653, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 127, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34654, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 131, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34655, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 132, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34656, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 133, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34657, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 134, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34658, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 135, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34659, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 137, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34660, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 295, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34661, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 301, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34662, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 304, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34663, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 388, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34664, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 13, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34665, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 35, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34666, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 36, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34667, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 37, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34668, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 122, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34669, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 197, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34670, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 14, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34671, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 141, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34672, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 12, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34673, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 53, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34674, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 56, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34675, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 57, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34676, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 61, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34677, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 121, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34678, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 155, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34679, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 373, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34680, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 802, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34681, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 46, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34682, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 49, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34683, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 51, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34684, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 60, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34685, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 62, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34686, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 63, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34687, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 64, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34688, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 190, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34689, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 211, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34690, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 212, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34691, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 47, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34692, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 72, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34693, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 70, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34694, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 74, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34695, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 75, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34696, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 76, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34697, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 77, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34698, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 78, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34699, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 79, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34700, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 80, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34701, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 784, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34702, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 73, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34703, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 84, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34704, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 85, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34705, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 89, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34706, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 90, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34707, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 91, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34708, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 385, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34709, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 83, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34710, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 806, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34711, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 68, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34712, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 93, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34713, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 98, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34714, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 261, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34715, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 262, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34716, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 270, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34717, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 259, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34718, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 260, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34719, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 349, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34720, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 350, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34721, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 351, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34722, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 354, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34723, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 355, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34724, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 290, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34725, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 769, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34726, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 770, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34727, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 768, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34728, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 783, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34729, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 782, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34730, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 103, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34731, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 106, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34732, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 116, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34733, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 117, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34734, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 119, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34735, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 111, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34736, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 113, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34737, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 114, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34738, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 115, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34739, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 118, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34740, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 248, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34741, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 359, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34742, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 416, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34743, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 112, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34744, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 268, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34745, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 401, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34746, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 402, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34747, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 403, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34748, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 404, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34749, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 405, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34750, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 406, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34751, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 407, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34752, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 417, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34753, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 418, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34754, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 419, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34755, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 420, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34756, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 421, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34757, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 422, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34758, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 424, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34759, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 425, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34760, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 400, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34761, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 774, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34762, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 110, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34763, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 142, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34764, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 143, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34765, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 144, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34766, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 145, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34767, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 146, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34768, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 156, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34769, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 158, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34770, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 210, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34771, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 265, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34772, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 292, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34773, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 293, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34774, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 309, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34775, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 310, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34776, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 369, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34777, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 372, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34778, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 389, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34779, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 390, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34780, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 391, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34781, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 792, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34782, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 798, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34783, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 138, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34784, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 168, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34785, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 169, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34786, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 139, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34787, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 224, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34788, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 225, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34789, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 226, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34790, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 227, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34791, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 228, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34792, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 306, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34793, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 307, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34794, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 308, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34795, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 223, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34796, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 120, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34797, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 788, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34798, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 790, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34799, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 140, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34800, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 249, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34801, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 250, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34802, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 157, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34803, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 202, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34804, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 203, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34805, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 173, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34806, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 204, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34807, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 174, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34808, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 172, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34809, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 182, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34810, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 184, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34811, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 185, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34812, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 186, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34813, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 187, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34814, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 188, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34815, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 189, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34816, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 196, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34817, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 280, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34818, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 796, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34819, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 797, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34820, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 181, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34821, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 192, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34822, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 193, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34823, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 194, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34824, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 195, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34825, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 183, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34826, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 200, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34827, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 201, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34828, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 205, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34829, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 206, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34830, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 207, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34831, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 208, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34832, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 209, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34833, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 219, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34834, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 220, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34835, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 221, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34836, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 294, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34837, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 803, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34838, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 199, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34839, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 214, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34840, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 215, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34841, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 216, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34842, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 217, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34843, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 218, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34844, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 222, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34845, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 345, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34846, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 346, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34847, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 213, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34848, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 243, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34849, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 244, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34850, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 245, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34851, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 246, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34852, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 247, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34853, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 776, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34854, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 777, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34855, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 775, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34856, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 778, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34857, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 779, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34858, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 805, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34859, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 804, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34860, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 198, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34861, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 230, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34862, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 231, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34863, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 232, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34864, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 233, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34865, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 234, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34866, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 235, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34867, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 236, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34868, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 237, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34869, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 238, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34870, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 239, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34871, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 240, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34872, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 241, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34873, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 242, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34874, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 423, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34875, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 780, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34876, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 229, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34877, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 264, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34878, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 269, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34879, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 271, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34880, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 272, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34881, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 273, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34882, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 278, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34883, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 279, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34884, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 315, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34885, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 263, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34886, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 296, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34887, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 297, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34888, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 298, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34889, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 299, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34890, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 302, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34891, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 303, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34892, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 305, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34893, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 364, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34894, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 275, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34895, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 274, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34896, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 312, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34897, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 313, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34898, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 314, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34899, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 311, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34900, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 317, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34901, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 330, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34902, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 338, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34903, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 339, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34904, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 356, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34905, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 329, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34906, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 357, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34907, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 358, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34908, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 408, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34909, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 409, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34910, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 410, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34911, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 411, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34912, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 412, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34913, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 413, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34914, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 414, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34915, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 415, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34916, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 552, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34917, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 553, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34918, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 318, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34919, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 328, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34920, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 343, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34921, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 344, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34922, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 361, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34923, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 362, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34924, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 363, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34925, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 366, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34926, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 367, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34927, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 368, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34928, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 370, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34929, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 371, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34930, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 365, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34931, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 375, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34932, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 376, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34933, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 377, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34934, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 374, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34935, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 384, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34936, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 480, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34937, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 481, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34938, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 482, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34939, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 483, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34940, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 484, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34941, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 485, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34942, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 479, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34943, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 546, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34944, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 547, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34945, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 548, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34946, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 549, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34947, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 550, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34948, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 551, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34949, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 545, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34950, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 542, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34951, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 757, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34952, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 758, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34953, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 759, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34954, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 760, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34955, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 761, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34956, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 762, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34957, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 763, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34958, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 771, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34959, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 772, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34960, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 773, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34961, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 756, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34962, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 755, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34963, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 793, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34964, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 794, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34965, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 795, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34966, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 786, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34967, '2019-11-14 10:53:09', '2019-11-14 10:53:09', 2, 801, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34968, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 53, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34969, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 56, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34970, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 57, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34971, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 61, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34972, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 121, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34973, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 155, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34974, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 373, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34975, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 802, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34976, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 46, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34977, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 49, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34978, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 51, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34979, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 60, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34980, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 62, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34981, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 63, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34982, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 64, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34983, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 190, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34984, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 211, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34985, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 212, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34986, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 47, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34987, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 72, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34988, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 70, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34989, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 74, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34990, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 75, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34991, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 76, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34992, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 77, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34993, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 78, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34994, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 79, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34995, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 80, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34996, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 784, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34997, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 73, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34998, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 84, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (34999, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 85, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35000, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 89, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35001, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 90, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35002, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 91, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35003, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 385, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35004, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 83, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35005, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 806, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35006, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 68, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35007, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 93, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35008, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 98, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35009, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 261, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35010, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 262, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35011, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 270, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35012, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 259, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35013, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 260, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35014, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 349, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35015, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 350, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35016, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 351, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35017, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 354, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35018, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 355, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35019, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 290, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35020, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 769, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35021, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 770, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35022, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 768, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35023, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 783, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35024, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 782, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35025, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 103, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35026, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 106, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35027, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 116, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35028, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 117, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35029, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 119, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35030, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 111, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35031, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 113, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35032, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 114, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35033, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 115, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35034, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 118, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35035, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 248, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35036, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 359, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35037, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 416, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35038, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 112, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35039, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 268, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35040, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 401, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35041, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 402, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35042, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 403, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35043, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 404, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35044, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 405, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35045, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 406, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35046, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 407, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35047, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 417, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35048, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 418, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35049, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 419, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35050, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 420, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35051, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 421, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35052, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 422, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35053, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 424, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35054, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 425, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35055, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 400, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35056, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 774, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35057, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 110, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35058, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 142, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35059, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 143, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35060, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 144, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35061, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 145, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35062, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 146, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35063, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 156, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35064, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 158, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35065, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 210, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35066, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 265, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35067, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 292, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35068, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 293, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35069, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 309, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35070, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 310, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35071, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 369, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35072, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 372, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35073, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 389, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35074, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 390, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35075, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 391, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35076, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 792, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35077, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 798, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35078, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 138, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35079, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 168, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35080, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 169, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35081, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 139, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35082, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 224, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35083, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 225, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35084, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 226, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35085, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 227, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35086, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 228, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35087, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 306, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35088, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 307, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35089, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 308, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35090, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 223, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35091, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 120, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35092, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 788, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35093, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 790, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35094, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 140, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35095, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 249, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35096, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 250, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35097, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 157, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35098, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 202, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35099, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 203, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35100, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 173, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35101, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 204, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35102, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 174, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35103, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 172, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35104, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 192, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35105, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 193, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35106, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 194, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35107, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 195, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35108, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 183, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35109, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 200, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35110, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 201, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35111, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 205, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35112, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 206, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35113, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 207, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35114, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 208, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35115, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 209, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35116, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 219, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35117, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 220, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35118, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 221, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35119, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 294, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35120, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 803, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35121, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 199, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35122, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 214, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35123, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 215, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35124, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 216, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35125, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 217, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35126, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 218, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35127, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 222, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35128, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 345, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35129, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 346, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35130, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 213, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35131, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 243, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35132, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 244, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35133, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 245, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35134, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 246, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35135, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 247, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35136, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 776, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35137, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 777, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35138, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 775, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35139, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 778, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35140, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 779, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35141, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 805, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35142, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 804, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35143, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 198, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35144, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 230, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35145, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 231, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35146, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 232, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35147, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 233, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35148, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 234, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35149, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 235, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35150, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 236, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35151, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 237, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35152, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 238, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35153, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 239, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35154, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 240, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35155, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 241, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35156, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 242, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35157, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 423, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35158, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 780, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35159, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 229, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35160, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 296, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35161, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 297, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35162, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 298, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35163, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 299, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35164, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 302, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35165, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 303, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35166, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 305, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35167, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 364, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35168, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 275, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35169, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 274, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35170, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 312, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35171, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 313, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35172, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 314, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35173, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 311, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35174, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 317, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35175, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 330, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35176, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 338, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35177, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 339, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35178, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 356, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35179, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 329, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35180, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 357, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35181, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 358, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35182, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 408, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35183, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 409, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35184, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 410, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35185, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 411, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35186, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 412, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35187, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 413, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35188, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 414, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35189, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 415, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35190, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 552, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35191, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 553, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35192, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 318, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35193, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 328, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35194, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 343, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35195, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 344, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35196, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 361, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35197, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 362, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35198, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 363, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35199, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 366, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35200, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 367, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35201, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 368, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35202, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 370, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35203, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 371, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35204, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 365, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35205, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 375, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35206, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 376, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35207, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 377, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35208, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 374, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35209, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 384, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35210, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 480, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35211, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 481, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35212, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 482, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35213, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 483, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35214, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 484, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35215, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 485, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35216, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 479, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35217, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 546, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35218, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 547, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35219, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 548, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35220, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 549, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35221, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 550, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35222, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 551, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35223, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 545, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35224, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 542, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35225, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 757, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35226, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 758, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35227, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 759, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35228, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 760, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35229, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 761, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35230, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 762, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35231, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 763, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35232, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 771, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35233, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 772, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35234, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 773, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35235, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 756, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35236, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 755, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35237, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 793, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35238, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 794, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35239, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 795, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35240, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 786, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35241, '2019-11-14 16:10:16', '2019-11-14 16:10:16', 5, 801, 1, 'admin', 0);
INSERT INTO `sys_role_to_permission` VALUES (35242, '2019-11-19 15:20:27', '2019-11-19 15:20:27', 6, 98, 596, 'suzhou', 0);
INSERT INTO `sys_role_to_permission` VALUES (35243, '2019-11-19 15:20:27', '2019-11-19 15:20:27', 6, 106, 596, 'suzhou', 0);
INSERT INTO `sys_role_to_permission` VALUES (35244, '2019-11-19 15:20:27', '2019-11-19 15:20:27', 6, 142, 596, 'suzhou', 0);
INSERT INTO `sys_role_to_permission` VALUES (35245, '2019-11-19 15:20:27', '2019-11-19 15:20:27', 6, 143, 596, 'suzhou', 0);
INSERT INTO `sys_role_to_permission` VALUES (35246, '2019-11-19 15:20:27', '2019-11-19 15:20:27', 6, 144, 596, 'suzhou', 0);
INSERT INTO `sys_role_to_permission` VALUES (35247, '2019-11-19 15:20:27', '2019-11-19 15:20:27', 6, 145, 596, 'suzhou', 0);
INSERT INTO `sys_role_to_permission` VALUES (35248, '2019-11-19 15:20:27', '2019-11-19 15:20:27', 6, 146, 596, 'suzhou', 0);
INSERT INTO `sys_role_to_permission` VALUES (35249, '2019-11-19 15:20:27', '2019-11-19 15:20:27', 6, 156, 596, 'suzhou', 0);
INSERT INTO `sys_role_to_permission` VALUES (35250, '2019-11-19 15:20:27', '2019-11-19 15:20:27', 6, 158, 596, 'suzhou', 0);
INSERT INTO `sys_role_to_permission` VALUES (35251, '2019-11-19 15:20:27', '2019-11-19 15:20:27', 6, 210, 596, 'suzhou', 0);
INSERT INTO `sys_role_to_permission` VALUES (35252, '2019-11-19 15:20:27', '2019-11-19 15:20:27', 6, 265, 596, 'suzhou', 0);
INSERT INTO `sys_role_to_permission` VALUES (35253, '2019-11-19 15:20:27', '2019-11-19 15:20:27', 6, 292, 596, 'suzhou', 0);
INSERT INTO `sys_role_to_permission` VALUES (35254, '2019-11-19 15:20:27', '2019-11-19 15:20:27', 6, 293, 596, 'suzhou', 0);
INSERT INTO `sys_role_to_permission` VALUES (35255, '2019-11-19 15:20:27', '2019-11-19 15:20:27', 6, 309, 596, 'suzhou', 0);
INSERT INTO `sys_role_to_permission` VALUES (35256, '2019-11-19 15:20:27', '2019-11-19 15:20:27', 6, 310, 596, 'suzhou', 0);
INSERT INTO `sys_role_to_permission` VALUES (35257, '2019-11-19 15:20:27', '2019-11-19 15:20:27', 6, 369, 596, 'suzhou', 0);
INSERT INTO `sys_role_to_permission` VALUES (35258, '2019-11-19 15:20:27', '2019-11-19 15:20:27', 6, 372, 596, 'suzhou', 0);
INSERT INTO `sys_role_to_permission` VALUES (35259, '2019-11-19 15:20:27', '2019-11-19 15:20:27', 6, 389, 596, 'suzhou', 0);
INSERT INTO `sys_role_to_permission` VALUES (35260, '2019-11-19 15:20:27', '2019-11-19 15:20:27', 6, 390, 596, 'suzhou', 0);
INSERT INTO `sys_role_to_permission` VALUES (35261, '2019-11-19 15:20:27', '2019-11-19 15:20:27', 6, 391, 596, 'suzhou', 0);
INSERT INTO `sys_role_to_permission` VALUES (35262, '2019-11-19 15:20:27', '2019-11-19 15:20:27', 6, 792, 596, 'suzhou', 0);
INSERT INTO `sys_role_to_permission` VALUES (35263, '2019-11-19 15:20:27', '2019-11-19 15:20:27', 6, 798, 596, 'suzhou', 0);
INSERT INTO `sys_role_to_permission` VALUES (35264, '2019-11-19 15:20:27', '2019-11-19 15:20:27', 6, 138, 596, 'suzhou', 0);
INSERT INTO `sys_role_to_permission` VALUES (35265, '2019-11-19 15:20:27', '2019-11-19 15:20:27', 6, 120, 596, 'suzhou', 0);
INSERT INTO `sys_role_to_permission` VALUES (35266, '2019-11-19 15:23:00', '2019-11-19 15:23:00', 7, 49, 596, 'suzhou', 0);
INSERT INTO `sys_role_to_permission` VALUES (35267, '2019-11-19 15:23:00', '2019-11-19 15:23:00', 7, 51, 596, 'suzhou', 0);
INSERT INTO `sys_role_to_permission` VALUES (35268, '2019-11-19 15:23:00', '2019-11-19 15:23:00', 7, 60, 596, 'suzhou', 0);
INSERT INTO `sys_role_to_permission` VALUES (35269, '2019-11-19 15:23:00', '2019-11-19 15:23:00', 7, 62, 596, 'suzhou', 0);
INSERT INTO `sys_role_to_permission` VALUES (35270, '2019-11-19 15:23:00', '2019-11-19 15:23:00', 7, 63, 596, 'suzhou', 0);
INSERT INTO `sys_role_to_permission` VALUES (35271, '2019-11-19 15:23:00', '2019-11-19 15:23:00', 7, 64, 596, 'suzhou', 0);
INSERT INTO `sys_role_to_permission` VALUES (35272, '2019-11-19 15:23:00', '2019-11-19 15:23:00', 7, 190, 596, 'suzhou', 0);
INSERT INTO `sys_role_to_permission` VALUES (35273, '2019-11-19 15:23:00', '2019-11-19 15:23:00', 7, 211, 596, 'suzhou', 0);
INSERT INTO `sys_role_to_permission` VALUES (35274, '2019-11-19 15:23:00', '2019-11-19 15:23:00', 7, 212, 596, 'suzhou', 0);
INSERT INTO `sys_role_to_permission` VALUES (35275, '2019-11-19 15:23:00', '2019-11-19 15:23:00', 7, 47, 596, 'suzhou', 0);
INSERT INTO `sys_role_to_permission` VALUES (35276, '2019-11-19 15:23:00', '2019-11-19 15:23:00', 7, 98, 596, 'suzhou', 0);
INSERT INTO `sys_role_to_permission` VALUES (35277, '2019-11-19 15:23:00', '2019-11-19 15:23:00', 7, 142, 596, 'suzhou', 0);
INSERT INTO `sys_role_to_permission` VALUES (35278, '2019-11-19 15:23:00', '2019-11-19 15:23:00', 7, 143, 596, 'suzhou', 0);
INSERT INTO `sys_role_to_permission` VALUES (35279, '2019-11-19 15:23:00', '2019-11-19 15:23:00', 7, 144, 596, 'suzhou', 0);
INSERT INTO `sys_role_to_permission` VALUES (35280, '2019-11-19 15:23:00', '2019-11-19 15:23:00', 7, 145, 596, 'suzhou', 0);
INSERT INTO `sys_role_to_permission` VALUES (35281, '2019-11-19 15:23:00', '2019-11-19 15:23:00', 7, 146, 596, 'suzhou', 0);
INSERT INTO `sys_role_to_permission` VALUES (35282, '2019-11-19 15:23:00', '2019-11-19 15:23:00', 7, 156, 596, 'suzhou', 0);
INSERT INTO `sys_role_to_permission` VALUES (35283, '2019-11-19 15:23:00', '2019-11-19 15:23:00', 7, 158, 596, 'suzhou', 0);
INSERT INTO `sys_role_to_permission` VALUES (35284, '2019-11-19 15:23:00', '2019-11-19 15:23:00', 7, 210, 596, 'suzhou', 0);
INSERT INTO `sys_role_to_permission` VALUES (35285, '2019-11-19 15:23:00', '2019-11-19 15:23:00', 7, 265, 596, 'suzhou', 0);
INSERT INTO `sys_role_to_permission` VALUES (35286, '2019-11-19 15:23:00', '2019-11-19 15:23:00', 7, 292, 596, 'suzhou', 0);
INSERT INTO `sys_role_to_permission` VALUES (35287, '2019-11-19 15:23:00', '2019-11-19 15:23:00', 7, 293, 596, 'suzhou', 0);
INSERT INTO `sys_role_to_permission` VALUES (35288, '2019-11-19 15:23:00', '2019-11-19 15:23:00', 7, 309, 596, 'suzhou', 0);
INSERT INTO `sys_role_to_permission` VALUES (35289, '2019-11-19 15:23:00', '2019-11-19 15:23:00', 7, 310, 596, 'suzhou', 0);
INSERT INTO `sys_role_to_permission` VALUES (35290, '2019-11-19 15:23:00', '2019-11-19 15:23:00', 7, 369, 596, 'suzhou', 0);
INSERT INTO `sys_role_to_permission` VALUES (35291, '2019-11-19 15:23:00', '2019-11-19 15:23:00', 7, 372, 596, 'suzhou', 0);
INSERT INTO `sys_role_to_permission` VALUES (35292, '2019-11-19 15:23:00', '2019-11-19 15:23:00', 7, 389, 596, 'suzhou', 0);
INSERT INTO `sys_role_to_permission` VALUES (35293, '2019-11-19 15:23:00', '2019-11-19 15:23:00', 7, 390, 596, 'suzhou', 0);
INSERT INTO `sys_role_to_permission` VALUES (35294, '2019-11-19 15:23:00', '2019-11-19 15:23:00', 7, 391, 596, 'suzhou', 0);
INSERT INTO `sys_role_to_permission` VALUES (35295, '2019-11-19 15:23:00', '2019-11-19 15:23:00', 7, 792, 596, 'suzhou', 0);
INSERT INTO `sys_role_to_permission` VALUES (35296, '2019-11-19 15:23:00', '2019-11-19 15:23:00', 7, 798, 596, 'suzhou', 0);
INSERT INTO `sys_role_to_permission` VALUES (35297, '2019-11-19 15:23:00', '2019-11-19 15:23:00', 7, 138, 596, 'suzhou', 0);
INSERT INTO `sys_role_to_permission` VALUES (35298, '2019-11-19 15:23:00', '2019-11-19 15:23:00', 7, 168, 596, 'suzhou', 0);
INSERT INTO `sys_role_to_permission` VALUES (35299, '2019-11-19 15:23:00', '2019-11-19 15:23:00', 7, 169, 596, 'suzhou', 0);
INSERT INTO `sys_role_to_permission` VALUES (35300, '2019-11-19 15:23:00', '2019-11-19 15:23:00', 7, 139, 596, 'suzhou', 0);
INSERT INTO `sys_role_to_permission` VALUES (35301, '2019-11-19 15:23:00', '2019-11-19 15:23:00', 7, 224, 596, 'suzhou', 0);
INSERT INTO `sys_role_to_permission` VALUES (35302, '2019-11-19 15:23:00', '2019-11-19 15:23:00', 7, 225, 596, 'suzhou', 0);
INSERT INTO `sys_role_to_permission` VALUES (35303, '2019-11-19 15:23:00', '2019-11-19 15:23:00', 7, 226, 596, 'suzhou', 0);
INSERT INTO `sys_role_to_permission` VALUES (35304, '2019-11-19 15:23:00', '2019-11-19 15:23:00', 7, 227, 596, 'suzhou', 0);
INSERT INTO `sys_role_to_permission` VALUES (35305, '2019-11-19 15:23:00', '2019-11-19 15:23:00', 7, 228, 596, 'suzhou', 0);
INSERT INTO `sys_role_to_permission` VALUES (35306, '2019-11-19 15:23:00', '2019-11-19 15:23:00', 7, 306, 596, 'suzhou', 0);
INSERT INTO `sys_role_to_permission` VALUES (35307, '2019-11-19 15:23:00', '2019-11-19 15:23:00', 7, 307, 596, 'suzhou', 0);
INSERT INTO `sys_role_to_permission` VALUES (35308, '2019-11-19 15:23:00', '2019-11-19 15:23:00', 7, 308, 596, 'suzhou', 0);
INSERT INTO `sys_role_to_permission` VALUES (35309, '2019-11-19 15:23:00', '2019-11-19 15:23:00', 7, 223, 596, 'suzhou', 0);
INSERT INTO `sys_role_to_permission` VALUES (35310, '2019-11-19 15:23:00', '2019-11-19 15:23:00', 7, 120, 596, 'suzhou', 0);
INSERT INTO `sys_role_to_permission` VALUES (35311, '2019-11-19 15:23:00', '2019-11-19 15:23:00', 7, 788, 596, 'suzhou', 0);
INSERT INTO `sys_role_to_permission` VALUES (35312, '2019-11-19 15:23:00', '2019-11-19 15:23:00', 7, 790, 596, 'suzhou', 0);
INSERT INTO `sys_role_to_permission` VALUES (35313, '2019-11-19 15:23:00', '2019-11-19 15:23:00', 7, 140, 596, 'suzhou', 0);
INSERT INTO `sys_role_to_permission` VALUES (35314, '2019-11-19 15:23:00', '2019-11-19 15:23:00', 7, 328, 596, 'suzhou', 0);
INSERT INTO `sys_role_to_permission` VALUES (35315, '2019-11-19 15:23:00', '2019-11-19 15:23:00', 7, 343, 596, 'suzhou', 0);
INSERT INTO `sys_role_to_permission` VALUES (35316, '2019-11-19 15:23:00', '2019-11-19 15:23:00', 7, 344, 596, 'suzhou', 0);
INSERT INTO `sys_role_to_permission` VALUES (35317, '2019-11-19 15:23:26', '2019-11-19 15:23:26', 6, 788, 596, 'suzhou', 0);
INSERT INTO `sys_role_to_permission` VALUES (35318, '2019-11-19 15:23:26', '2019-11-19 15:23:26', 6, 790, 596, 'suzhou', 0);
INSERT INTO `sys_role_to_permission` VALUES (35319, '2019-11-19 15:23:26', '2019-11-19 15:23:26', 6, 140, 596, 'suzhou', 0);
INSERT INTO `sys_role_to_permission` VALUES (35320, '2019-11-19 15:23:26', '2019-11-19 15:23:26', 6, 328, 596, 'suzhou', 0);
INSERT INTO `sys_role_to_permission` VALUES (35321, '2019-11-19 15:23:26', '2019-11-19 15:23:26', 6, 343, 596, 'suzhou', 0);
INSERT INTO `sys_role_to_permission` VALUES (35322, '2019-11-19 15:23:26', '2019-11-19 15:23:26', 6, 344, 596, 'suzhou', 0);

-- ----------------------------
-- Table structure for sys_role_to_version
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_to_version`;
CREATE TABLE `sys_role_to_version`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) NOT NULL COMMENT '角色ID',
  `version_id` int(11) NOT NULL COMMENT '版本ID',
  `sys_user_id` int(11) DEFAULT NULL COMMENT '操作人员ID',
  `sys_user_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '操作人员名称',
  `ctime` datetime(0) DEFAULT CURRENT_TIMESTAMP,
  `utime` datetime(0) DEFAULT CURRENT_TIMESTAMP,
  `is_deleted` int(1) DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_role_version`(`role_id`, `version_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色对应的系统版本' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键,自增长',
  `ctime` datetime(0) NOT NULL COMMENT '添加时间',
  `utime` datetime(0) DEFAULT NULL COMMENT '更新时间',
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户名',
  `password` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '密码',
  `nick_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '昵称',
  `real_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '真实姓名',
  `gender` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '性别',
  `avatar` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '头像地址',
  `birthday` datetime(0) DEFAULT NULL COMMENT '生日',
  `mobile` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '手机',
  `address` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '地址',
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '邮件地址',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
  `sys_user_id` int(11) DEFAULT NULL COMMENT '操作人员ID',
  `sys_user_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '操作人员名称',
  `is_enable` int(1) NOT NULL DEFAULT 1 COMMENT '启用标识，0：禁用，1：启用',
  `tree_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '用户路径，比如,0,2,5,',
  `code` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '验证码',
  `is_admin` int(1) NOT NULL DEFAULT 0 COMMENT '是否是管理员，厂商，代理商，运营商的直接系统用户 0否，1是',
  `parent_admin_id` int(11) DEFAULT NULL COMMENT '上一层级管理员Id',
  `sys_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '系统名称',
  `launch_area_id` int(11) DEFAULT 0 COMMENT '仓库id',
  `job_number` int(5) NOT NULL COMMENT '工号',
  `sys_logo` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '系统图标',
  `is_deleted` int(1) DEFAULT 0 COMMENT '是否删除：0未删除 1已删除',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_user_pass`(`username`, `password`) USING BTREE,
  INDEX `idx_nickname`(`nick_name`) USING BTREE,
  INDEX `idx_ctime`(`ctime`) USING BTREE,
  INDEX `idx_sys_user_id`(`sys_user_id`) USING BTREE,
  INDEX `idx_is_admin`(`is_admin`, `parent_admin_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 612 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '系统用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, '2017-07-08 14:20:43', '2018-01-18 11:05:42', 'admin', '663d4ef2780026d94c89d690a4153a750742e4747e571117', '超级管理员1', '超级管理员', '男', '/logo.png', '2017-07-08 14:20:43', '15521304585', '广州番禺', '305795006@qq.com', '初始化账号', 1, '初始化', 1, ',', '1949', 1, NULL, NULL, NULL, 1, NULL, 0);
INSERT INTO `sys_user` VALUES (596, '2019-09-24 19:22:24', '2019-11-14 10:52:51', 'suzhou', '93092553a98ba6a53098153ec88231500b57117154284877', '厂商', NULL, NULL, NULL, NULL, '15618816266', NULL, NULL, NULL, 1, 'admin', 1, ',1,', NULL, 2, 1, '设备运营管理', NULL, 2, NULL, 0);
INSERT INTO `sys_user` VALUES (610, '2019-11-19 15:24:32', '2019-11-19 15:24:32', 'caozuoyuan', 'b4f65f51254c60b22d51c43c33f472d4b799b8ae12c4fd6e', '操作员', NULL, NULL, NULL, NULL, '15618816266', NULL, NULL, NULL, 596, 'suzhou', 1, ',1,596,', NULL, 4, 596, '操作员', NULL, 2, NULL, 0);
INSERT INTO `sys_user` VALUES (611, '2019-11-19 15:25:30', '2019-11-19 15:25:30', 'rukuyuan', 'a99116b58095c71a9fe73e94013c36a08c13e37686c5f730', '入库员', NULL, NULL, NULL, NULL, '15618816266', NULL, NULL, NULL, 596, 'suzhou', 1, ',1,596,', NULL, 5, 596, '入库员', NULL, 3, NULL, 0);

-- ----------------------------
-- Table structure for sys_user_ext
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_ext`;
CREATE TABLE `sys_user_ext`  (
  `sys_user_id` int(11) NOT NULL COMMENT '系统用户ID',
  `sys_user_name` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `ctime` datetime(0) NOT NULL COMMENT '添加时间',
  `utime` datetime(0) DEFAULT NULL COMMENT '更新时间',
  `wx_appid` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '微信appid,必须加密之后存储,分润时候使用',
  `wx_partner_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '微信支付商户号, 必须加密之后存储,分润时候使用',
  `wx_app_secret` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '微信支付key,必须加密之后存储,分润时候使用',
  `wx_partner_secret` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '微信支付时,必须加密之后存储,分润时候使用',
  `wx_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '微信id',
  `wx_token` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `wx_pay_body` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `wx_subscribe_msg` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `wx_template_id` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '模版id(用于给维护人员推送消息)',
  `wx_open_id` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'openId(用于定位维护人员的微信，推送信息)',
  `receiver_open_id` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '收款人openId',
  `receiver_wx_name` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '收款人微信实名',
  `alipay_appid` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '支付宝生活号应用ID',
  `alipay_redirect_url` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '生活号授权回调地址',
  `alipay_public_key` varchar(3000) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '支付宝公钥',
  `alipay_private_key` varchar(3000) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用户RSA2私钥',
  `alipay_notify_url` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '支付宝支付异步回调地址',
  `alipay_return_url` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '支付宝支付完成跳转页面地址',
  `alipay_self_public_key` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '支付宝生成公钥',
  `alipay_partner` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '支付宝合作身份者ID',
  `alipay_account` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '支付宝账号(支持邮箱和手机号2种格式)',
  `alipay_account_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '支付宝账户名(即支付宝账号的人的姓名)',
  `qrcode_host` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用于生成设备二维码，对应二维码内容中的域名',
  `wx_cert_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '微信支付证书路径',
  `wx_mini_qrcode` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '微信小程序二维码',
  `is_deleted` int(1) DEFAULT 0 COMMENT '是否删除：0未删除，1已删除',
  `wx_frontend_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '微信公众号前端相对路径',
  `wx_app_cert_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '微信app支付证书路径',
  `wx_pay_app_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '微信app支付应用ID',
  `wx_pay_app_secret` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '微信app支付秘钥',
  PRIMARY KEY (`sys_user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '系统用户扩展表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_ext
-- ----------------------------
INSERT INTO `sys_user_ext` VALUES (1, 'admin', '2017-07-24 12:28:48', '2017-10-11 20:04:12', 'wxc8b43597d20ec6c6', '1519897851', 'fe2d8f744218939d9032c68feee3c7cb ', 'go2ENGSPAgo2ENGSPAgo2ENGSPA20188', '1', 'asd', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 'cert/apiclient_cert.p12', 'wxc8b43597d20ec6c6', 'go2ENGSPAgo2ENGSPAgo2ENGSPA20188');
INSERT INTO `sys_user_ext` VALUES (596, 'suzhou', '2019-09-24 19:22:24', '2019-11-14 10:52:51', 'wxc8b43597d20ec6c6', '1519897851', 'fe2d8f744218939d9032c68feee3c7cb ', 'go2ENGSPAgo2ENGSPAgo2ENGSPA20188', '1', NULL, NULL, NULL, NULL, NULL, 'oJTDp5ppYpau0Cxk3-1wqfIRHML4', '刘飞', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 'cert/apiclient_cert.p12', 'wxc8b43597d20ec6c6', 'go2ENGSPAgo2ENGSPAgo2ENGSPA20188');
INSERT INTO `sys_user_ext` VALUES (610, 'caozuoyuan', '2019-11-19 15:24:32', '2019-11-19 15:24:32', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, NULL, NULL);
INSERT INTO `sys_user_ext` VALUES (611, 'rukuyuan', '2019-11-19 15:25:30', '2019-11-19 15:25:30', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, NULL, NULL);
INSERT INTO `sys_user_ext` VALUES (612, 'ceshi', '2019-11-19 17:08:42', '2019-11-19 17:14:50', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL);
INSERT INTO `sys_user_ext` VALUES (613, 'ceshs', '2019-11-19 17:14:29', '2019-11-19 17:14:50', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for sys_user_share_data
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_share_data`;
CREATE TABLE `sys_user_share_data`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sys_user_id` int(11) NOT NULL COMMENT '系统用户ID',
  `share_data` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '共享给下级的数据',
  `ctime` datetime(0) NOT NULL COMMENT '添加时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `sys_user_id`(`sys_user_id`, `share_data`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统用户共享数据表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_user_to_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_to_role`;
CREATE TABLE `sys_user_to_role`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键,自增长',
  `ctime` datetime(0) NOT NULL COMMENT '添加时间',
  `utime` datetime(0) DEFAULT NULL COMMENT '更新时间',
  `user_id` int(11) NOT NULL COMMENT '用户ID',
  `role_id` int(11) NOT NULL COMMENT '角色ID',
  `sys_user_id` int(11) DEFAULT NULL COMMENT '操作人员ID',
  `sys_user_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '操作人员名称',
  `is_deleted` int(1) DEFAULT 0 COMMENT '是否删除：0未删除，1已删除',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_role_menu`(`user_id`, `role_id`) USING BTREE,
  INDEX `idx_ctime`(`ctime`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 636 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '用户角色关系表(多对多)' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_to_role
-- ----------------------------
INSERT INTO `sys_user_to_role` VALUES (1, '2019-09-24 19:17:26', '2019-09-24 19:17:29', 1, 1, 1, 'admin', 0);
INSERT INTO `sys_user_to_role` VALUES (631, '2019-09-24 19:22:24', '2019-09-24 19:22:24', 596, 2, 1, 'admin', 0);
INSERT INTO `sys_user_to_role` VALUES (632, '2019-11-19 15:24:32', '2019-11-19 15:24:32', 610, 4, 596, 'admin', 0);
INSERT INTO `sys_user_to_role` VALUES (633, '2019-11-19 15:25:30', '2019-11-19 15:25:30', 611, 5, 596, 'admin', 0);
INSERT INTO `sys_user_to_role` VALUES (634, '2019-11-19 17:08:42', '2019-11-19 17:14:50', 612, 5, 596, 'admin', 1);
INSERT INTO `sys_user_to_role` VALUES (635, '2019-11-19 17:14:29', '2019-11-19 17:14:50', 613, 5, 596, 'admin', 1);

-- ----------------------------
-- Table structure for sys_version
-- ----------------------------
DROP TABLE IF EXISTS `sys_version`;
CREATE TABLE `sys_version`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `version_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '版本名',
  `version_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '版本标识',
  `p_permission_id` int(11) DEFAULT NULL COMMENT '对应的权限',
  `sort` int(6) DEFAULT NULL COMMENT '排序',
  `sys_user_id` int(11) DEFAULT NULL COMMENT '操作人员ID',
  `sys_user_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '操作人员名称',
  `ctime` datetime(0) DEFAULT CURRENT_TIMESTAMP,
  `utime` datetime(0) DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_p_id`(`p_permission_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统版本' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for tmall_link
-- ----------------------------
DROP TABLE IF EXISTS `tmall_link`;
CREATE TABLE `tmall_link`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ctime` datetime(0) NOT NULL COMMENT '添加时间',
  `utime` datetime(0) DEFAULT NULL COMMENT '更新时间',
  `link_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '链接名称',
  `category_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '产品型号',
  `link_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '链接地址',
  `sys_user_id` int(10) DEFAULT NULL COMMENT '操作人',
  `sys_user_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建人名称',
  `remark` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '描述',
  `is_deleted` int(1) DEFAULT 0 COMMENT '删除标识',
  `category_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '产品名称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for trade_alipay
-- ----------------------------
DROP TABLE IF EXISTS `trade_alipay`;
CREATE TABLE `trade_alipay`  (
  `trade_no` varchar(31) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '主键，与trade_base表关联',
  `alipay_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '支付宝单号',
  `appid` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '支付宝应用APPID',
  `seller_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '收款支付宝账号对应的支付宝唯一用户号',
  `subject` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '订单标题',
  `trade_status` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '交易状态：交易完成TRADE_FINISHED  交易成功TRADE_SUCCESS',
  PRIMARY KEY (`trade_no`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '支付宝交易表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for trade_base
-- ----------------------------
DROP TABLE IF EXISTS `trade_base`;
CREATE TABLE `trade_base`  (
  `trade_no` varchar(31) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '交易号，主键，按照一定规则生成',
  `ctime` datetime(0) NOT NULL COMMENT '创建时间',
  `utime` datetime(0) DEFAULT NULL COMMENT '更改时间',
  `status` int(1) NOT NULL COMMENT '交易状态，1交易创建，2交易成功，3交易失败',
  `total_fee` double(12, 2) NOT NULL COMMENT '交易金额，单位为分',
  `order_no` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '业务订单号，',
  `order_type` int(2) NOT NULL COMMENT '1用户消费订单，2分润订单，3充值订单',
  `notify_url` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '发起交易时传递的回调URL',
  `nofify_time` datetime(0) DEFAULT NULL COMMENT '交易回调时间',
  `trade_type` int(1) NOT NULL DEFAULT 1 COMMENT '交易类型：1:微信公众号，2app微信，3支付宝，4充值卡，5余额，6微信支付',
  PRIMARY KEY (`trade_no`) USING BTREE,
  INDEX `idx_order_no`(`order_no`) USING BTREE,
  INDEX `index_order_no`(`order_no`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for trade_weixin
-- ----------------------------
DROP TABLE IF EXISTS `trade_weixin`;
CREATE TABLE `trade_weixin`  (
  `trade_no` varchar(31) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键，与trade_base表关联',
  `transaction_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '微信单号',
  `appid` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '微信开放平台审核通过的应用APPID',
  `mch_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '微信支付分配的商户号',
  `body` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '商品描述',
  `time_end` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '微信端,支付完成时间',
  PRIMARY KEY (`trade_no`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

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

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (2, '18852923055', '2019-12-13 16:14:32', '2019-12-13 16:30:02', NULL, NULL, NULL, '517a5f60a95e68383f41846d438e0b86e40ae37730d49808', '18852923055', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2019-12-13 16:14:32', 0, NULL, NULL, NULL, NULL);
INSERT INTO `user` VALUES (3, '15062323285', '2019-12-13 16:50:17', NULL, NULL, NULL, NULL, NULL, '15062323285', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2019-12-13 16:50:17', 0, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for user_bind_device
-- ----------------------------
DROP TABLE IF EXISTS `user_bind_device`;
CREATE TABLE `user_bind_device`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键,自增长',
  `ctime` datetime(0) DEFAULT NULL COMMENT '添加时间',
  `utime` datetime(0) DEFAULT NULL COMMENT '更新时间',
  `sno` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '对应设备序列号',
  `mac` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '对应设备MAC',
  `account_num` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '用户账号',
  `user_id` int(11) DEFAULT NULL COMMENT '所属用户id',
  `is_deleted` int(11) DEFAULT NULL COMMENT '是否删除：0未删除 1已删除',
  `is_manage` int(11) DEFAULT NULL COMMENT '是否是管理员：0不是 1是',
  `mobile` int(6) DEFAULT NULL COMMENT '手机号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user_card
-- ----------------------------
DROP TABLE IF EXISTS `user_card`;
CREATE TABLE `user_card`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL COMMENT '用户ID',
  `card_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '卡券ID',
  `code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '卡券code',
  `receive_channel` int(11) NOT NULL COMMENT '领取渠道 1:微信 2:app',
  `status` int(11) NOT NULL COMMENT '卡券状态 1:正常 2:已使用 3:已失效',
  `received_time` datetime(0) DEFAULT NULL COMMENT '卡券领取时间',
  `consumed_time` datetime(0) DEFAULT NULL COMMENT '卡券使用时间',
  `begin_time` datetime(0) DEFAULT NULL COMMENT '卡券生效时间',
  `end_time` datetime(0) DEFAULT NULL COMMENT '卡券过期时间',
  `ctime` datetime(0) NOT NULL COMMENT '数据创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `card_id`(`card_id`, `code`) USING BTREE,
  INDEX `user_id`(`user_id`, `status`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户的卡券' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user_charge_card
-- ----------------------------
DROP TABLE IF EXISTS `user_charge_card`;
CREATE TABLE `user_charge_card`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `ctime` datetime(0) NOT NULL COMMENT '创建时间',
  `utime` datetime(0) DEFAULT NULL COMMENT '更新时间',
  `card_num` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '充值卡号',
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `user_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用户名称',
  `mobile` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '手机号',
  `money` double(12, 2) NOT NULL DEFAULT 0.00 COMMENT '卡内余额',
  `status` int(11) NOT NULL DEFAULT 1 COMMENT '充值卡状态，1,使用中 2,暂停',
  `bind_card_time` datetime(0) DEFAULT NULL COMMENT '绑定时间',
  `is_bind_wx` int(11) NOT NULL DEFAULT 0 COMMENT '是否绑定微信，0,未绑定 1,已绑定',
  `sys_user_id` int(11) DEFAULT NULL COMMENT '创建人id',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `index_card_num`(`card_num`, `user_name`, `mobile`) USING BTREE,
  INDEX `index_status`(`status`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '充值卡' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user_charge_card_operation_record
-- ----------------------------
DROP TABLE IF EXISTS `user_charge_card_operation_record`;
CREATE TABLE `user_charge_card_operation_record`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `ctime` datetime(0) NOT NULL COMMENT '创建时间',
  `card_num` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '充值卡号',
  `operate_type` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '操作类型,ENABLE和DISABLE',
  `remark` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '操作说明',
  `ip` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '访问ip',
  `sys_user_id` int(11) NOT NULL COMMENT '创建人',
  `sys_user_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '创建人名称',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `index_card_num`(`card_num`) USING BTREE,
  INDEX `index_sys_user_id`(`sys_user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '充值卡操作记录(启用/禁用)' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user_charge_card_order
-- ----------------------------
DROP TABLE IF EXISTS `user_charge_card_order`;
CREATE TABLE `user_charge_card_order`  (
  `order_no` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '充值单号',
  `ctime` datetime(0) NOT NULL COMMENT '创建时间',
  `utime` datetime(0) DEFAULT NULL COMMENT '更新时间',
  `pay_time` datetime(0) DEFAULT NULL COMMENT '支付时间',
  `card_num` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '充值卡号',
  `money` double(12, 2) NOT NULL DEFAULT 0.00 COMMENT '充值金额',
  `pay_type` int(11) NOT NULL COMMENT '支付方式',
  `status` int(11) NOT NULL COMMENT '充值订单状态',
  `username` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '充值人姓名',
  `user_id` int(11) DEFAULT NULL COMMENT '充值人id',
  PRIMARY KEY (`order_no`) USING BTREE,
  INDEX `index_card_num`(`card_num`, `pay_type`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '充值卡充值订单' ROW_FORMAT = Dynamic;

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

-- ----------------------------
-- Table structure for user_family
-- ----------------------------
DROP TABLE IF EXISTS `user_family`;
CREATE TABLE `user_family`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ctime` datetime(0) DEFAULT NULL,
  `utime` datetime(0) DEFAULT NULL,
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '家庭名称',
  `province` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '省',
  `city` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '市',
  `area` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '区',
  `user_id` int(11) DEFAULT NULL COMMENT '所属用户',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user_move_record
-- ----------------------------
DROP TABLE IF EXISTS `user_move_record`;
CREATE TABLE `user_move_record`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `ctime` datetime(0) NOT NULL COMMENT '创建时间',
  `user_id` int(11) NOT NULL COMMENT '移入/出的用户',
  `move_type` int(11) NOT NULL COMMENT '移动类型，1:移入黑名单 2:移出黑名单',
  `sys_user_id` int(11) NOT NULL COMMENT '操作人员',
  `sys_user_name` varchar(30) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL COMMENT '操作人员名称',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `index_user_id`(`user_id`) USING BTREE,
  INDEX `index_sys_user_id`(`sys_user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci COMMENT = '用户移动记录（移入/出黑名单）' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user_room
-- ----------------------------
DROP TABLE IF EXISTS `user_room`;
CREATE TABLE `user_room`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ctime` datetime(0) DEFAULT NULL,
  `utime` datetime(0) DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '房间名',
  `is_deleted` int(1) DEFAULT 0,
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user_wallet
-- ----------------------------
DROP TABLE IF EXISTS `user_wallet`;
CREATE TABLE `user_wallet`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键,自增长',
  `ctime` datetime(0) NOT NULL COMMENT '添加时间',
  `utime` datetime(0) DEFAULT NULL COMMENT '更新时间',
  `wallet_type` int(1) NOT NULL COMMENT '钱包类型，1，余额，2，押金, 3,赠送',
  `wallet_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '钱包名称,枚举中获取值',
  `money` double(12, 2) NOT NULL DEFAULT 0.00 COMMENT '钱数',
  `username` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '所属用户',
  `sys_user_id` int(11) DEFAULT NULL COMMENT '用户所属的运营商，暂时只针对“押金”类型',
  `user_id` int(11) DEFAULT NULL COMMENT ' 用户id',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_username`(`username`, `wallet_type`) USING BTREE,
  INDEX `index_wallet_type`(`wallet_type`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '用户钱包表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user_wallet_charge_order
-- ----------------------------
DROP TABLE IF EXISTS `user_wallet_charge_order`;
CREATE TABLE `user_wallet_charge_order`  (
  `charge_order_no` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '主键,充值单号',
  `ctime` datetime(0) NOT NULL COMMENT '添加时间',
  `utime` datetime(0) DEFAULT NULL COMMENT '更新时间',
  `wallet_type` int(1) NOT NULL COMMENT '钱包ID',
  `fee` double(12, 2) NOT NULL COMMENT '操作金额',
  `balance` double(12, 2) NOT NULL DEFAULT 0.00 COMMENT ' 余额',
  `status` int(1) NOT NULL COMMENT '充值状态：1：创建，2：支付中，3：支付完成，4：支付失败，5：订单完成，6：订单失败',
  `username` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '所属用户',
  `wallet_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `pay_time` datetime(0) DEFAULT NULL,
  `pay_type` int(2) DEFAULT NULL,
  `discount_money` double(12, 2) DEFAULT 0.00 COMMENT '用户的充值赠送金额,此部分的金额说明money中有多少金额是优惠金额',
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  PRIMARY KEY (`charge_order_no`) USING BTREE,
  INDEX `idx_username`(`username`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '用户钱包充值单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user_wallet_use_record
-- ----------------------------
DROP TABLE IF EXISTS `user_wallet_use_record`;
CREATE TABLE `user_wallet_use_record`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键,自增长',
  `trade_no` varchar(31) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '交易号',
  `ctime` datetime(0) NOT NULL COMMENT '添加时间',
  `utime` datetime(0) DEFAULT NULL COMMENT '更新时间',
  `wallet_type` int(1) NOT NULL COMMENT '钱包类型，1，余额，2，押金',
  `wallet_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '钱包名称,枚举中获取值',
  `fee` double(12, 2) NOT NULL COMMENT '操作金额',
  `balance` double(12, 2) NOT NULL DEFAULT 0.00 COMMENT ' 余额',
  `operation_type` int(1) NOT NULL COMMENT '操作类型：1,充值，2,消费',
  `username` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '所属用户',
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_username`(`username`) USING BTREE,
  INDEX `index_trade_no`(`trade_no`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '用户钱包操作记录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user_wx_ext
-- ----------------------------
DROP TABLE IF EXISTS `user_wx_ext`;
CREATE TABLE `user_wx_ext`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_openid` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '微信用户的unionid，对应user表的openid',
  `openid` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '微信用户openid，每个用户对一个公众号生成一个openid',
  `wx_id` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '微信公众号ID，是微信公众号的唯一标识',
  `sys_user_id` int(11) DEFAULT NULL COMMENT '微信用户所属运营用户ID',
  `ctime` datetime(0) NOT NULL,
  `utime` datetime(0) DEFAULT NULL,
  `status` int(1) NOT NULL DEFAULT 1 COMMENT '用户状态: 1,正常; 2,黑名单',
  `move_in_black_time` datetime(0) DEFAULT NULL COMMENT '移入黑名单时间',
  `move_out_black_time` datetime(0) DEFAULT NULL COMMENT '移出黑名单时间',
  `authorization_time` datetime(0) DEFAULT NULL COMMENT '授权时间',
  `is_deleted` int(1) DEFAULT 0 COMMENT '是否删除：0未删除 1已删除',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `index_user_openId`(`user_openid`) USING BTREE,
  INDEX `index3_openId`(`openid`) USING BTREE,
  INDEX `index_wxId`(`wx_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '微信用户信息' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
