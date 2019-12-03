-- MySQL dump 10.13  Distrib 5.7.17, for macos10.12 (x86_64)
--
-- Host: localhost    Database: gizwits_lease
-- ------------------------------------------------------
-- Server version	5.7.22-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `advertisement_display`
--

DROP TABLE IF EXISTS `advertisement_display`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `advertisement_display` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键,自增长',
  `ctime` datetime NOT NULL COMMENT '添加时间',
  `utime` datetime DEFAULT NULL COMMENT '更新时间',
  `picture` varchar(300) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '广告图片',
  `show_time` int(10) NOT NULL COMMENT '展示时间，单位（秒）',
  `sys_user_id` int(11) NOT NULL COMMENT '创建人',
  `sys_user_name` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建人名称',
  `is_deleted` int(1) NOT NULL DEFAULT '0' COMMENT '是否删除：0,否；1,是',
  `url` varchar(300) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '跳转链接',
  `name` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '广告名',
  `sort` int(1) NOT NULL COMMENT '排序字段',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='广告展示表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `agent`
--

DROP TABLE IF EXISTS `agent`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `agent` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键,自增长',
  `ctime` datetime NOT NULL COMMENT '添加时间',
  `utime` datetime DEFAULT NULL COMMENT '更新时间',
  `name` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '代理商名称',
  `industry` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '所属行业',
  `web_site` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '公司官网',
  `logo_url` varchar(150) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '代理商logo url',
  `phone` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '企业电话',
  `mobile` varchar(11) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '手机号码',
  `contact` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '联系人',
  `department` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '部门',
  `email` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '电子邮件',
  `qq` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'QQ号码',
  `province` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '省',
  `city` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '市',
  `area` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '区/县',
  `address` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '详细地址',
  `parent_agent_id` int(11) DEFAULT NULL COMMENT '父级代理商ID',
  `sys_account_id` int(11) NOT NULL COMMENT '代理商对应的系统用户id',
  `sys_user_name` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建人名称',
  `status` int(1) NOT NULL DEFAULT '1' COMMENT '状态：1,待分配 2,正常 3,暂停',
  `sys_user_id` int(11) NOT NULL COMMENT '创建人',
  `is_deleted` int(1) NOT NULL DEFAULT '0' COMMENT '是否删除，0,未删除 1,已删除',
  `share_benefit_rule_id` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `cover_level` int(1) NOT NULL DEFAULT '1' COMMENT '代理商级别：1，国家级；2，省级；3，市级；4，区县级',
  PRIMARY KEY (`id`),
  UNIQUE KEY `agent_sys_account_id_uindex` (`sys_account_id`),
  KEY `idx_sys_user_id` (`sys_user_id`),
  KEY `idx_parent_agent_id` (`parent_agent_id`)
) ENGINE=InnoDB AUTO_INCREMENT=65 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='代理商表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `app_version`
--

DROP TABLE IF EXISTS `app_version`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `app_version` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键,自增长',
  `ctime` datetime NOT NULL COMMENT '添加时间',
  `utime` datetime DEFAULT NULL COMMENT '更新时间',
  `version` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '版本号',
  `url` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '下载地址',
  `type` int(1) DEFAULT '1' COMMENT 'app：1用户端 2管理端',
  `description` varchar(450) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '更新说明',
  `is_deleted` int(1) NOT NULL DEFAULT '0' COMMENT '是否删除：0未删除 1删除',
  `last_version` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '上个版本号',
  `sys_user_id` int(11) DEFAULT NULL COMMENT '创建人',
  `sys_user_name` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建人姓名',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='app版本记录表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `card`
--

DROP TABLE IF EXISTS `card`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `card` (
  `card_id` varchar(32) NOT NULL COMMENT '微信卡券ID',
  `title` varchar(32) NOT NULL COMMENT '卡券名',
  `card_type` varchar(32) NOT NULL COMMENT '卡券类型。代金券：CASH; 折扣券：DISCOUNT;',
  `status` varchar(32) NOT NULL COMMENT '卡券状态 CARD_STATUS_NOT_VERIFY:待审核 CARD_STATUS_VERIFY_FAIL:审核失败 CARD_STATUS_VERIFY_OK:通过审核 CARD_STATUS_DELETE:卡券被商户删除 CARD_STATUS_DISPATCH:在公众平台投放过的卡券',
  `date_type` varchar(32) NOT NULL COMMENT '使用时间的类型 DATE_TYPE_FIX_TIME_RANGE 表示固定日期区间，DATE_TYPE_FIX_TERM表示固定时长（自领取后按天算）',
  `date_begin_timestamp` datetime DEFAULT NULL COMMENT 'DATE_TYPE_FIX_TIME_RANGE时专用 ，表示起用时间。从1970年1月1日00:00:00至起用时间的秒数。（单位为秒）',
  `date_end_timestamp` datetime DEFAULT NULL COMMENT 'DATE_TYPE_FIX_TIME_RANGE时专用 ，表示结束时间。（单位为秒）',
  `date_fixed_term` int(11) DEFAULT NULL COMMENT 'DATE_TYPE_FIX_TERM时专用 ，表示自领取后多少天内有效，领取后当天有效填写0。 （单位为天）',
  `date_fixed_begin_term` int(11) DEFAULT NULL COMMENT 'DATE_TYPE_FIX_TERM时专用 ，表示自领取后多少天开始生效。（单位为天）',
  `quantity` int(11) NOT NULL COMMENT '卡券现有库存的数量',
  `least_cost` int(11) DEFAULT NULL COMMENT '代金券专用，表示起用金额（单位为分）',
  `reduce_cost` int(11) DEFAULT NULL COMMENT '代金券专用，表示减免金额（单位为分）',
  `discount` int(11) DEFAULT NULL COMMENT '折扣券专用字段，表示打折额度（百分比）',
  `sys_user_id` int(11) NOT NULL COMMENT '卡券创建者, 系统用户ID',
  `dispatch_web` int(11) NOT NULL DEFAULT '0' COMMENT '微信投放 0:否, 1:是',
  `dispatch_app` int(11) NOT NULL DEFAULT '0' COMMENT 'APP投放 0:否, 1:是',
  `cover` varchar(1000) DEFAULT NULL COMMENT '卡券封面',
  `sequence` int(11) DEFAULT NULL COMMENT '卡券展示顺序',
  `product_id` int(11) DEFAULT NULL COMMENT '卡券适用产品ID, NULL为全部产品适用',
  `operator_ids` varchar(1000) DEFAULT NULL COMMENT '卡券适用运营商ID, NULL为全部运营商适用, 多个运营商ID使用,分隔',
  `ctime` datetime NOT NULL COMMENT '添加时间',
  `utime` datetime DEFAULT NULL COMMENT '更新时间',
  `sync_time` datetime DEFAULT NULL COMMENT '同步时间',
  `receive_limit` int(11) DEFAULT NULL COMMENT '每人可领券的数量限制',
  `time_limit` text COMMENT '可用时段 JSON数组',
  PRIMARY KEY (`card_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='卡券';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `card_consume_record`
--

DROP TABLE IF EXISTS `card_consume_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `card_consume_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL COMMENT '用户ID',
  `card_id` varchar(32) NOT NULL COMMENT '卡券ID',
  `consume_count` int(11) NOT NULL COMMENT '使用次数',
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_id` (`user_id`,`card_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='卡券使用次数记录';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `card_event`
--

DROP TABLE IF EXISTS `card_event`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `card_event` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `wx_id` varchar(32) NOT NULL COMMENT '卡券的微信号',
  `card_id` varchar(32) NOT NULL COMMENT '卡券ID',
  `user_openid` varchar(32) NOT NULL COMMENT '领券用户的openid',
  `code` varchar(32) NOT NULL COMMENT '卡券code',
  `event_time` datetime NOT NULL COMMENT '消息创建时间',
  `is_give_by_friend` int(11) NOT NULL COMMENT '是否为转赠领取，1代表是，0代表否',
  `friend_openid` varchar(32) DEFAULT NULL COMMENT '代表发起转赠用户的openid',
  `old_code` varchar(32) DEFAULT NULL COMMENT '代表转赠前的卡券code',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='微信卡券领取事件';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `card_receive_record`
--

DROP TABLE IF EXISTS `card_receive_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `card_receive_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL COMMENT '用户ID',
  `card_id` varchar(32) NOT NULL COMMENT '卡券ID',
  `receive_count` int(11) NOT NULL COMMENT '领取次数',
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_id` (`user_id`,`card_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='卡券领取次数记录';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `charge_setting`
--

DROP TABLE IF EXISTS `charge_setting`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `charge_setting` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `ctime` datetime NOT NULL COMMENT '创建时间',
  `utime` datetime DEFAULT NULL COMMENT '更新时间',
  `money` double(12,2) NOT NULL COMMENT '值',
  `product_id` int(11) DEFAULT NULL COMMENT '产品id',
  `sys_user_id` int(11) NOT NULL COMMENT '创建人',
  `sys_user_name` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '创建人名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='充值设定';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `china_area`
--

DROP TABLE IF EXISTS `china_area`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `china_area` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL DEFAULT '' COMMENT '省市县的名字',
  `code` int(6) unsigned NOT NULL DEFAULT '0' COMMENT '行政区编码',
  `parent_code` int(6) unsigned NOT NULL DEFAULT '0',
  `parent_name` varchar(20) DEFAULT '' COMMENT '所属父级的中文名称',
  `is_leaf` int(11) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `idx_parent_code` (`parent_code`),
  KEY `idx_code` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=6884 DEFAULT CHARSET=utf8 COMMENT='全国省市行政编码表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `device`
--

DROP TABLE IF EXISTS `device`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `device` (
  `sno` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '设备序列号',
  `ctime` datetime NOT NULL COMMENT '添加时间',
  `utime` datetime DEFAULT NULL COMMENT '更新时间',
  `name` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '设备名称',
  `mac` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'MAC地址,通讯用',
  `work_status` int(1) NOT NULL DEFAULT '2' COMMENT '设备工作状态,2离线,3:使用中,4:空闲 5:禁用 6:故障 7待机',
  `online_status` int(1) NOT NULL DEFAULT '2' COMMENT '在线状态，1在线，2离线',
  `status` int(1) NOT NULL DEFAULT '0' COMMENT '设备状态,0:入库 1:出库 2:服务中 3:暂停服务 4:已返厂 5:已报废 6:待入库(信息不完整)',
  `longitude` decimal(19,6) DEFAULT NULL COMMENT '经度',
  `latitude` decimal(19,6) DEFAULT NULL COMMENT '维度',
  `last_online_time` datetime DEFAULT NULL COMMENT '最后上线时间',
  `operator_id` int(11) DEFAULT NULL COMMENT '运营商对应的系统账号',
  `operator_name` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '运营商名称',
  `launch_area_id` int(11) DEFAULT NULL COMMENT '投放点ID',
  `launch_area_name` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '投放点名称',
  `product_id` int(11) NOT NULL COMMENT '所属产品',
  `product_name` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '所属产品名称',
  `is_deleted` int(1) DEFAULT '0' COMMENT '删除标识，0：未删除，1：已删除',
  `service_id` int(11) DEFAULT NULL COMMENT '收费模式ID',
  `service_name` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '收费模式名称',
  `giz_did` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '机智云设备did',
  `giz_host` varchar(128) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '机智云websocket操作主页',
  `giz_wss_port` varchar(128) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '机智云websocket加密端口',
  `giz_ws_port` varchar(128) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '机智云websocket端口',
  `giz_pass_code` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '机智云设备passcode',
  `fault_status` int(1) DEFAULT '8' COMMENT '设备故障状态，6故障，8正常',
  `agent_id` int(11) DEFAULT NULL COMMENT '代理商id',
  `sys_user_id` int(11) NOT NULL,
  `wx_ticket` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '微信ticket',
  `wx_did` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '微信did',
  `content_url` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '二维码url',
  `owner_id` int(11) DEFAULT NULL COMMENT '拥有者，默认为创建者',
  `group_id` int(11) DEFAULT NULL COMMENT '设备组id',
  `entry_time` datetime DEFAULT NULL COMMENT '入库时间',
  `shift_out_time` datetime DEFAULT NULL COMMENT '出库时间',
  `activate_status` int(1) DEFAULT '1' COMMENT '激活状态：1未激活 2激活',
  `activated_time` datetime DEFAULT NULL COMMENT '激活时间',
  `origin` int(1) DEFAULT '1' COMMENT '数据来源：1手工录入 2云端上报',
  `abnormal_times` int(11) DEFAULT '0' COMMENT '连续下单异常的次数',
  `lock` tinyint(1) DEFAULT '0' COMMENT '多次异常后锁定设备',
  `owner_name` varchar(45) CHARACTER SET utf8mb4 DEFAULT NULL,
  `install_user_name` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '安装用户姓名',
  `install_user_province` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '安装用户所在省',
  `install_user_city` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '安装用户所在市',
  `install_user_area` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '安装用户所在区',
  `install_user_address` varchar(256) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '安装用户详细地址',
  `install_user_mobile` varchar(16) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '安装用户手机号',
  `country` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '国家',
  `province` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '省',
  `city` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '市',
  PRIMARY KEY (`sno`),
  KEY `idx_mac` (`mac`),
  KEY `index_owner_id` (`owner_id`,`status`,`ctime`),
  KEY `index_product_id` (`product_id`,`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='设备表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `device_alarm`
--

DROP TABLE IF EXISTS `device_alarm`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `device_alarm` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键,自增长',
  `ctime` datetime NOT NULL COMMENT '添加时间',
  `utime` datetime DEFAULT NULL COMMENT '更新时间',
  `name` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '故障名称',
  `attr` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '故障参数/对应数据点表的identity_name',
  `happen_time` datetime DEFAULT NULL COMMENT '故障发生时间',
  `fixed_time` datetime DEFAULT NULL COMMENT '故障修复时间',
  `status` int(1) NOT NULL COMMENT '故障状态,0:未修复 1:已修复',
  `mac` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'MAC地址',
  `longitude` decimal(19,2) DEFAULT NULL COMMENT '经度',
  `latitude` decimal(19,2) DEFAULT NULL COMMENT '维度',
  `notify_user_id` int(11) DEFAULT NULL COMMENT '需要通知的人员ID',
  `sno` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '对应设备序列号',
  `alarm_type` int(1) DEFAULT '1' COMMENT '告警类型:0,报警;1,故障',
  `product_key` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `remark` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_sno` (`sno`),
  KEY `idx_mac` (`mac`),
  KEY `idx_ctime` (`ctime`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='设备故障(警告)记录表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `device_assign_record`
--

DROP TABLE IF EXISTS `device_assign_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `device_assign_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `ctime` datetime NOT NULL COMMENT '创建时间',
  `sno` varchar(30) NOT NULL COMMENT '设备sno',
  `mac` varchar(30) DEFAULT NULL COMMENT '设备mac',
  `source_operator` int(11) DEFAULT NULL COMMENT '原运营商',
  `destination_operator` int(11) NOT NULL COMMENT '现运营商',
  `sys_user_id` int(11) NOT NULL COMMENT '创建人',
  `sys_user_name` varchar(30) DEFAULT NULL COMMENT '创建人名称',
  `operate_type` varchar(16) NOT NULL DEFAULT 'ASSIGN' COMMENT '操作类型:ASSIGN和UNBIND',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=79873 DEFAULT CHARSET=latin1 COMMENT='设备分配记录';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `device_da`
--

DROP TABLE IF EXISTS `device_da`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `device_da` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键,自增长',
  `mac` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'MAC地址,通讯用',
  `content_url` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '二维码url',
  `sno` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '设备序列号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12165 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='设备数据转化表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `device_ext_for_majiang`
--

DROP TABLE IF EXISTS `device_ext_for_majiang`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `device_ext_for_majiang` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ctime` datetime NOT NULL COMMENT '添加时间',
  `utime` datetime DEFAULT NULL COMMENT '更新时间',
  `sno` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '设备序列号',
  `mode` int(1) DEFAULT NULL COMMENT '游戏模式：1 极速 2静音',
  `game_type` int(1) DEFAULT NULL COMMENT '游戏类型：1标准 2自定义音',
  `game_no` int(3) DEFAULT NULL COMMENT '游戏序号，如果是自定义时为空',
  `command` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '下发指令',
  PRIMARY KEY (`id`),
  KEY `sno_index` (`sno`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='设备扩展表(麻将机)';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `device_ext_port`
--

DROP TABLE IF EXISTS `device_ext_port`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `device_ext_port` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键,自增长',
  `ctime` datetime NOT NULL COMMENT '添加时间',
  `utime` datetime DEFAULT NULL COMMENT '更新时间',
  `sno` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '设备sno',
  `mac` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'MAC地址,通讯用',
  `port` int(1) DEFAULT NULL COMMENT '出水口号',
  `port_type` int(1) DEFAULT NULL COMMENT '出水类型：1常温，2热水，3冰水',
  `status` int(1) DEFAULT NULL COMMENT '状态：3使用中  4空闲',
  `sort` int(1) DEFAULT NULL COMMENT '排序字段',
  PRIMARY KEY (`id`),
  KEY `sno_index` (`sno`),
  KEY `port_index` (`port`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='设备扩展表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `device_ext_weather`
--

DROP TABLE IF EXISTS `device_ext_weather`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `device_ext_weather` (
  `sno` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '设备序列号',
  `ctime` datetime NOT NULL COMMENT '添加时间',
  `utime` datetime DEFAULT NULL COMMENT '更新时间',
  `longitude` decimal(19,6) NOT NULL COMMENT '经度',
  `latitude` decimal(19,6) NOT NULL COMMENT '维度',
  `source` int(2) NOT NULL COMMENT '来源，1：和风，2：阿里',
  `city_id` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '城市ID',
  `tmp` varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '温度',
  `hum` varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '湿度',
  `pm25` varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'pm2.5',
  `qlty` varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '空气质量',
  `province` varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '省',
  `city` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '市',
  `area` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '区',
  `product_id` int(11) NOT NULL COMMENT '所属产品',
  PRIMARY KEY (`sno`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='设备-天气拓展表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `device_group`
--

DROP TABLE IF EXISTS `device_group`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `device_group` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '组名称',
  `ctime` datetime NOT NULL COMMENT '创建时间',
  `utime` datetime DEFAULT NULL COMMENT '更新时间',
  `is_deleted` int(11) NOT NULL DEFAULT '0' COMMENT '是否删除，0:未删除，1:已删除',
  `sys_user_id` int(11) NOT NULL COMMENT '创建人',
  `sys_user_name` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '创建人名称',
  `assigned_account_id` int(11) DEFAULT NULL COMMENT '被分配的运营商或代理商的系统帐号',
  `assigned_name` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '运营商或代理商名称',
  PRIMARY KEY (`id`),
  UNIQUE KEY `device_group_id_uindex` (`id`),
  KEY `idx_sys_user_id` (`sys_user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='设备组';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `device_group_to_device`
--

DROP TABLE IF EXISTS `device_group_to_device`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `device_group_to_device` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `ctime` datetime NOT NULL COMMENT '创建时间',
  `utime` datetime DEFAULT NULL,
  `device_group_id` int(11) NOT NULL COMMENT '设备组id',
  `device_sno` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '设备sno',
  `is_deleted` int(1) NOT NULL DEFAULT '0' COMMENT '是否删除：0未删除 1已删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `device_group_to_device_id_uindex` (`id`),
  KEY `idx_device_group_id` (`device_group_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='设备组与设备的关系';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `device_launch_area`
--

DROP TABLE IF EXISTS `device_launch_area`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `device_launch_area` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键,自增长',
  `ctime` datetime NOT NULL COMMENT '添加时间',
  `utime` datetime DEFAULT NULL COMMENT '更新时间',
  `name` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '投放点名称',
  `province` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '省',
  `city` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '市',
  `area` varchar(30) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '区/县',
  `address` varchar(256) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '详细地址',
  `sys_user_id` int(11) NOT NULL COMMENT '创建者id',
  `sys_user_name` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '创建人姓名',
  `operator_id` int(13) DEFAULT NULL COMMENT '运营商对应的系统账号',
  `operator_name` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '运营商名称',
  `maintainer_name` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT ' 维护人员姓名',
  `maintainer_id` int(11) DEFAULT NULL COMMENT '维护人员id',
  `longitude` decimal(19,6) NOT NULL COMMENT '经度',
  `latitude` decimal(19,6) NOT NULL COMMENT '纬度',
  `person_in_charge` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '负责人姓名',
  `person_in_charge_id` int(11) DEFAULT NULL COMMENT '负责人id',
  `mobile` varchar(11) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '负责人电话',
  `status` int(11) DEFAULT '1' COMMENT '状态：0:禁用，1:启用',
  `is_deleted` int(11) DEFAULT '0' COMMENT '是否删除：0，否，1，是',
  `owner_id` int(11) DEFAULT NULL COMMENT '归属人',
  `picture_url` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '图片地址',
  PRIMARY KEY (`id`),
  KEY `idx_operator` (`operator_id`),
  KEY `idx_name` (`name`),
  KEY `idx_sys_user_id` (`sys_user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=234 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='设备投放点表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `device_running_record`
--

DROP TABLE IF EXISTS `device_running_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `device_running_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键,自增长',
  `ctime` datetime NOT NULL COMMENT '添加时间',
  `utime` datetime DEFAULT NULL COMMENT '更新时间',
  `sno` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '对应设备序列号',
  `mac` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'MAC地址',
  `work_status` int(1) NOT NULL COMMENT '设备在线状态,1:在线,2:离线 3:使用中 4:空闲 5:禁用 6:故障 ',
  `content` varchar(2000) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '报文内容',
  PRIMARY KEY (`id`),
  KEY `idx_sno` (`sno`),
  KEY `idx_mac` (`mac`)
) ENGINE=InnoDB AUTO_INCREMENT=5618772 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='设备运行记录表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `device_service_mode_setting`
--

DROP TABLE IF EXISTS `device_service_mode_setting`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `device_service_mode_setting` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sno` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '设备序列号',
  `sys_account_id` int(11) NOT NULL COMMENT '为设备设置收费模式的系统用户',
  `is_free` int(1) NOT NULL DEFAULT '0' COMMENT '是否免费：0，收费，1，免费',
  `ctime` datetime NOT NULL,
  `utime` datetime DEFAULT NULL,
  `assign_account_id` int(11) DEFAULT NULL COMMENT '分配对象的accountId，即设备的owner_id',
  `is_deleted` int(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='设备收费模式设定(麻将机系统特有需求)';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `device_to_product_service_mode`
--

DROP TABLE IF EXISTS `device_to_product_service_mode`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `device_to_product_service_mode` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键,自增长',
  `ctime` datetime NOT NULL COMMENT '添加时间',
  `utime` datetime DEFAULT NULL COMMENT '更新时间',
  `sno` varchar(64) NOT NULL COMMENT '设备序列号',
  `service_mode_id` int(11) NOT NULL COMMENT ' 收费模式ID',
  `sys_user_id` int(11) DEFAULT NULL COMMENT '操作人员ID',
  `sys_user_name` varchar(50) DEFAULT NULL COMMENT '操作人员名称',
  `is_deleted` int(1) DEFAULT '0' COMMENT '是否删除：0未删除 1删除',
  PRIMARY KEY (`id`),
  KEY `idx_sno_mode` (`sno`,`service_mode_id`)
) ENGINE=InnoDB AUTO_INCREMENT=26308 DEFAULT CHARSET=utf8mb4 COMMENT='设备收费模式表(多对多)';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `feedback_business`
--

DROP TABLE IF EXISTS `feedback_business`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `feedback_business` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键,自增长',
  `ctime` datetime NOT NULL COMMENT '添加时间',
  `utime` datetime DEFAULT NULL COMMENT '更新时间',
  `username` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用户账号',
  `nick_name` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用户昵称',
  `mobile` varchar(11) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '发件人手机号',
  `avatar` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '头像地址',
  `content` text COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '内容',
  `picture_url` text COLLATE utf8mb4_unicode_ci COMMENT '图片地址',
  `picture_num` int(2) DEFAULT NULL COMMENT '图片数',
  `user_id` int(11) DEFAULT NULL COMMENT '系统用户ID',
  `recipient_id` int(11) DEFAULT NULL COMMENT '收件人ID',
  `recipient_name` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '收件人名称',
  `is_read` int(1) NOT NULL DEFAULT '0' COMMENT '是否已读：0 未读，1已读',
  PRIMARY KEY (`id`),
  KEY `recipient_id_index` (`recipient_id`),
  KEY `user_id_index` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='业务系统表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `feedback_user`
--

DROP TABLE IF EXISTS `feedback_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `feedback_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键,自增长',
  `ctime` datetime NOT NULL COMMENT '添加时间',
  `utime` datetime DEFAULT NULL COMMENT '更新时间',
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  `nick_name` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用户昵称',
  `avatar` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '头像地址',
  `mobile` varchar(11) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '手机',
  `content` text COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '内容',
  `picture_url` text CHARACTER SET utf8mb4 COMMENT '图片地址',
  `picture_num` int(2) DEFAULT NULL COMMENT '图片数',
  `origin` int(1) DEFAULT NULL COMMENT '消息来源：1 移动用户端,2 移动管理端 ',
  `sno` varchar(64) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '设备序列号',
  `mac` varchar(32) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT 'MAC地址',
  `recipient_id` int(11) DEFAULT NULL COMMENT '收件人id',
  `recipient_name` varchar(45) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '收件人姓名',
  `is_read` int(1) DEFAULT '0' COMMENT '是否已读：0 未读，1已读',
  PRIMARY KEY (`id`),
  KEY `recipient_id_index` (`recipient_id`),
  KEY `sno_index` (`sno`),
  KEY `mac_index` (`mac`)
) ENGINE=InnoDB AUTO_INCREMENT=260 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='问题反馈表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `install_fee_order`
--

DROP TABLE IF EXISTS `install_fee_order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `install_fee_order` (
  `order_no` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL,
  `sno` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '设备sno',
  `mac` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '设备mac',
  `rule_id` int(11) NOT NULL COMMENT '规则id',
  `product_id` int(11) NOT NULL COMMENT '适应产品id',
  `fee` decimal(10,2) NOT NULL COMMENT '装机金额',
  `status` tinyint(2) NOT NULL COMMENT '状态',
  `pay_type` tinyint(2) DEFAULT NULL COMMENT '支付类型',
  `pay_time` datetime DEFAULT NULL COMMENT '支付时间',
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `sys_user_id` int(11) NOT NULL COMMENT '用户id',
  `ctime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `utime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `del` bit(1) DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`order_no`),
  KEY `idx_sno` (`sno`),
  KEY `idx_mac` (`mac`),
  KEY `idx_rule_id` (`rule_id`),
  KEY `idx_product_id` (`product_id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_sys_user_id` (`sys_user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='初装费订单';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `install_fee_rule`
--

DROP TABLE IF EXISTS `install_fee_rule`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `install_fee_rule` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '规则名称',
  `product_id` int(11) NOT NULL COMMENT '适应产品id',
  `fee` decimal(10,2) NOT NULL COMMENT '装机金额',
  `sys_user_id` int(11) NOT NULL COMMENT '创建人id',
  `ctime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `utime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `del` bit(1) DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`),
  KEY `idx_product_id` (`product_id`),
  KEY `idx_sys_user_id` (`sys_user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='初装费规则';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `install_fee_rule_to_agent`
--

DROP TABLE IF EXISTS `install_fee_rule_to_agent`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `install_fee_rule_to_agent` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `rule_id` int(11) NOT NULL COMMENT '规则id',
  `agent_id` int(11) NOT NULL COMMENT '代理商id',
  `sys_account_id` int(11) NOT NULL COMMENT '代理商管理员id',
  `sys_user_id` int(11) NOT NULL COMMENT '创建人id',
  `ctime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `utime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `del` bit(1) DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`),
  KEY `idx_rule_id` (`rule_id`),
  KEY `idx_agent_id` (`agent_id`),
  KEY `idx_sys_account_id` (`sys_account_id`),
  KEY `idx_sys_user_id` (`sys_user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='初装费规则关联代理商';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `install_fee_rule_to_operator`
--

DROP TABLE IF EXISTS `install_fee_rule_to_operator`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `install_fee_rule_to_operator` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `rule_id` int(11) NOT NULL COMMENT '规则id',
  `operator_id` int(11) NOT NULL COMMENT '运营商id',
  `sys_account_id` int(11) NOT NULL COMMENT '运营商管理员id',
  `sys_user_id` int(11) NOT NULL COMMENT '创建人id',
  `ctime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `utime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `del` bit(1) DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`),
  KEY `idx_rule_id` (`rule_id`),
  KEY `idx_operator_id` (`operator_id`),
  KEY `idx_sys_account_id` (`sys_account_id`),
  KEY `idx_sys_user_id` (`sys_user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='初装费规则关联运营商';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `manufacturer`
--

DROP TABLE IF EXISTS `manufacturer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `manufacturer` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键,自增长',
  `ctime` datetime NOT NULL COMMENT '添加时间',
  `utime` datetime DEFAULT NULL COMMENT '更新时间',
  `name` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '企业名称',
  `industry` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '所属行业',
  `web_site` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '公司官网',
  `sub_domain` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '子域名',
  `logo_url` varchar(150) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '公司logo url',
  `phone` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '企业电话',
  `mobile` varchar(11) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '手机号码',
  `contact` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '联系人',
  `department` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '部门',
  `email` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '电子邮件',
  `qq` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'QQ号码',
  `province` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '省',
  `city` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '市',
  `area` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '区/县',
  `address` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '详细地址',
  `parent_manufacturer_id` int(11) DEFAULT NULL COMMENT '父级企业ID',
  `sys_user_id` int(11) DEFAULT NULL COMMENT '创建人',
  `enterprise_id` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '企业id,添加企业时手工录入',
  `enterprise_secret` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '企业secret,添加企业时手动录入',
  `is_deleted` int(1) NOT NULL DEFAULT '0' COMMENT '是否删除，0,未删除 1,已删除',
  `sys_account_id` int(11) DEFAULT NULL COMMENT '厂商绑定的系统帐号',
  `sys_user_name` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建人名称',
  PRIMARY KEY (`id`),
  UNIQUE KEY `manufacturer_sys_account_id_uindex` (`sys_account_id`),
  KEY `idx_name` (`name`),
  KEY `idx_contact` (`contact`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='厂商(或企业)表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `message_template`
--

DROP TABLE IF EXISTS `message_template`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `message_template` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键,自增长',
  `ctime` datetime NOT NULL COMMENT '添加时间',
  `utime` datetime DEFAULT NULL COMMENT '更新时间',
  `title` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '标题',
  `content` varchar(300) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '消息模版内容',
  `command` varchar(450) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '触发条件',
  `type` int(1) NOT NULL COMMENT '消息模版类型：2故障消息  4设备消息 5 租赁消息',
  `product_id` int(11) NOT NULL COMMENT '产品id',
  `sys_user_id` int(11) NOT NULL COMMENT '创建人',
  `sys_user_name` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建人名称',
  `is_deleted` int(1) NOT NULL DEFAULT '0' COMMENT '是否删除：0,否；1,是',
  `service_id` int(11) DEFAULT NULL COMMENT '当提醒类型为租赁类型时：填写收费模式id',
  `service_name` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '当提醒类型为租赁类型时：填写收费模式名称',
  `depend_on_data_point` int(1) NOT NULL DEFAULT '0' COMMENT '是否依靠数据点上报：0不依靠，1依靠',
  `rate` double DEFAULT '0' COMMENT '当不依靠数据点时所设判断条件:百分比/数值',
  `rate_type` int(1) DEFAULT '1' COMMENT '当不依靠数据点时所设判断条件: 1 百分比 2数值',
  PRIMARY KEY (`id`),
  KEY `index_productId` (`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='消息模版表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `operator`
--

DROP TABLE IF EXISTS `operator`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `operator` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键,自增长',
  `ctime` datetime NOT NULL COMMENT '添加时间',
  `utime` datetime DEFAULT NULL COMMENT '更新时间',
  `name` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '运营商名称',
  `industry` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '所属行业',
  `web_site` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '公司官网',
  `logo_url` varchar(150) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '代理商logo url',
  `phone` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '企业电话',
  `mobile` varchar(11) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '手机号码',
  `contact` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '联系人',
  `department` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '部门',
  `description` varchar(2000) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '公司内容简介',
  `email` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '电子邮件',
  `qq` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'QQ号码',
  `province` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '省',
  `city` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '市',
  `area` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '区/县',
  `address` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '详细地址',
  `status` int(1) NOT NULL DEFAULT '1' COMMENT '运营商状态，1.待分配，2.运营中，3.暂停运营',
  `share_benefit_rule_id` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '分润规则',
  `sys_account_id` int(11) NOT NULL COMMENT '运营商绑定的系统账号',
  `sys_user_id` int(11) NOT NULL COMMENT '创建人',
  `sys_user_name` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `is_allot` int(1) NOT NULL DEFAULT '0' COMMENT '是否分配投放点：0未分配 1已分配',
  `cover_level` int(1) NOT NULL DEFAULT '1' COMMENT '代理商级别：1，国家级；2，省级；3，市级；4，区县级',
  `is_deleted` int(1) DEFAULT '0' COMMENT '是否删除：0未删除 1已删除',
  PRIMARY KEY (`id`),
  KEY `idx_sys_account_id` (`sys_account_id`),
  KEY `idx_sys_user_id` (`sys_user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=350 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='运营商表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `operator_ext`
--

DROP TABLE IF EXISTS `operator_ext`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `operator_ext` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `operator_id` int(11) NOT NULL COMMENT '对应的运营商ID字段',
  `cash_pledge` double(11,2) DEFAULT NULL COMMENT '押金(若配置此项，则用户必须交过押金才可使用设备)',
  `recharge_promotion` varchar(1000) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '充值优惠信息，若用户配置充值优惠信息，则根据用户充值金额算比例',
  `is_deleted` int(1) DEFAULT '0' COMMENT '是否删除：0未删除，1已删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=139 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='运营商信息扩展';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `order_base`
--

DROP TABLE IF EXISTS `order_base`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `order_base` (
  `order_no` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '主键,订单号,按照一定规则生成',
  `ctime` datetime NOT NULL COMMENT '添加时间',
  `utime` datetime DEFAULT NULL COMMENT '更新时间',
  `sno` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '对应设备序列号',
  `mac` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '对应设备MAC',
  `command` varchar(128) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '发货指令',
  `service_mode_id` int(11) NOT NULL COMMENT '服务方式ID',
  `service_mode_name` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '服务方名称',
  `order_status` int(2) NOT NULL COMMENT '订单状态,-1:过期 0:创建 1:支付中 2:支付完成 3:支付失败 4:使用中 5:订单完成 6:退款中 7:已退款 8:退款失败',
  `pay_time` datetime DEFAULT NULL COMMENT '订单支付时间',
  `pay_type` int(2) NOT NULL DEFAULT '1' COMMENT '支付类型:1,公众号支付;2,微信APP支付;3,支付宝支付;4,充值卡支付;5,钱包支付,6微信支付,7微信H5支付',
  `trade_no` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '支付订单号',
  `transaction_id` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '支付系统生成的交易号,用于退款处理',
  `amount` double(12,2) NOT NULL DEFAULT '0.00' COMMENT '订单总价',
  `user_id` int(11) DEFAULT NULL COMMENT '用户ID',
  `user_name` varchar(50) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '用户姓名',
  `sys_user_id` int(11) DEFAULT NULL COMMENT '订单所属的微信运营配置中的sys_user_id',
  `is_deleted` int(1) DEFAULT NULL,
  `promotion_money` double(11,2) DEFAULT NULL COMMENT '订单的优惠金额，与real_money合起来就是amount，此部分不参与分润',
  `pay_card_num` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '支付方式为充值卡支付时的卡号',
  `is_archive` int(1) NOT NULL DEFAULT '0' COMMENT '是否归档，解绑后设置：0,未归档 1,归档',
  `service_mode_detail_id` int(11) DEFAULT NULL COMMENT '具体的服务方式ID',
  `service_start_time` datetime DEFAULT NULL COMMENT '服务开始时间',
  `service_end_time` datetime DEFAULT NULL COMMENT '服务结束时间（预计）',
  `early_end` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否提前结束',
  `early_end_time` datetime DEFAULT NULL COMMENT '提前结束时间',
  `refund` decimal(12,2) NOT NULL DEFAULT '0.00' COMMENT '退款金额',
  `refund_version` int(11) NOT NULL DEFAULT '0' COMMENT '退款版本号',
  `launch_area_id` int(11) DEFAULT NULL COMMENT '投放点id',
  `launch_area_name` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '投放点名称',
  `abnormal_reason` tinyint(2) DEFAULT NULL COMMENT '异常原因',
  `original_price` decimal(12,2) DEFAULT NULL COMMENT '订单(商品)原价，优惠前的价格',
  `card_id` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '使用的卡券ID',
  `card_code` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '使用的卡券Code',
  `card_discount` decimal(12,2) DEFAULT NULL COMMENT '卡券优惠金额',
  `renew_order_no` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '续费单号',
  `remark` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `tree_path` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用户路径',
  `end_type` int(1) DEFAULT '1' COMMENT '订单结束方式：1正常结束 2job定时结束',
  `refund_result` varchar(450) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '退款理由',
  PRIMARY KEY (`order_no`),
  KEY `idx_ctime` (`ctime`),
  KEY `idx_user_id` (`user_id`),
  KEY `index_sys_user_id_ctime` (`sys_user_id`,`ctime`),
  KEY `index_sno_ctime` (`sno`,`ctime`),
  KEY `index_order_status` (`order_status`,`order_no`),
  KEY `index_user_id` (`user_id`,`order_status`,`ctime`),
  KEY `index_sys_user_id` (`ctime`,`order_status`,`sys_user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='订单表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `order_data_flow`
--

DROP TABLE IF EXISTS `order_data_flow`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `order_data_flow` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `order_no` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '对应的订单id',
  `sno` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '对应的设备sno',
  `mac` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '对应的设备mac',
  `route` int(1) NOT NULL COMMENT '数据方向：1业务云到设备，2设备到业务云',
  `type` int(2) NOT NULL COMMENT '类型：1设备原状态，2下发的指令，3设备使用中，4设备异常，5设备结束使用，6设备其他上报',
  `data` text COLLATE utf8mb4_unicode_ci COMMENT '数据内容',
  `remark` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
  `sys_user_id` int(11) NOT NULL COMMENT '设备拥有者',
  `ctime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_order_no` (`order_no`)
) ENGINE=InnoDB AUTO_INCREMENT=632250 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='订单指令跟踪表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `order_ext_by_quantity`
--

DROP TABLE IF EXISTS `order_ext_by_quantity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `order_ext_by_quantity` (
  `order_no` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '主键,与订单保持一致',
  `ctime` datetime NOT NULL COMMENT '添加时间',
  `utime` datetime DEFAULT NULL COMMENT '更新时间',
  `quantity` double(7,2) NOT NULL COMMENT '购买的量',
  `price` double(6,2) NOT NULL COMMENT '单价,元',
  `unit` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '单位,立方',
  `service_type` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '服务类型',
  `last_rest` double(7,2) DEFAULT NULL COMMENT '如果存在续费单：上个订单剩余量',
  `last_used` double(7,2) DEFAULT NULL COMMENT '如果存在续费单：上个订单已使用量',
  PRIMARY KEY (`order_no`),
  UNIQUE KEY `order_no` (`order_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='订单扩展记录表(按量)';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `order_ext_by_time`
--

DROP TABLE IF EXISTS `order_ext_by_time`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `order_ext_by_time` (
  `order_no` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '主键,与订单保持一致',
  `ctime` datetime NOT NULL COMMENT '添加时间',
  `utime` datetime DEFAULT NULL COMMENT '更新时间',
  `start_time` datetime NOT NULL COMMENT '服务开始时间',
  `end_time` datetime NOT NULL COMMENT '服务结束时间',
  `duration` double(5,2) NOT NULL COMMENT '购买时长',
  `price` double(6,2) NOT NULL COMMENT '单价,元',
  `unit` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '单位,分钟/小时/天',
  PRIMARY KEY (`order_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='订单扩展表(按时)';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `order_ext_port`
--

DROP TABLE IF EXISTS `order_ext_port`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `order_ext_port` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键,自增长',
  `ctime` datetime NOT NULL COMMENT '添加时间',
  `utime` datetime DEFAULT NULL COMMENT '更新时间',
  `order_no` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '订单号',
  `sno` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '设备sno',
  `port` int(1) DEFAULT NULL COMMENT '出水口号',
  PRIMARY KEY (`id`),
  KEY `sno_index` (`sno`),
  KEY `order_no` (`order_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='订单扩展表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `order_pay_record`
--

DROP TABLE IF EXISTS `order_pay_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `order_pay_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `order_no` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '订单号',
  `ctime` datetime NOT NULL,
  `utime` datetime DEFAULT NULL COMMENT '更新时间',
  `pay_type` int(2) NOT NULL COMMENT '支付类型:1,公众号支付;2,微信APP支付;3,支付宝支付;4,充值卡支付',
  `params` varchar(1000) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '支付提交参数',
  `status` int(2) DEFAULT '1' COMMENT '订单状态,0:创建 1:支付中 2:支付完成 3:服务中 4:订单完成 5:订单失败',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=155325 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='订单支付记录表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `order_share_profit`
--

DROP TABLE IF EXISTS `order_share_profit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `order_share_profit` (
  `order_no` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '订单号',
  `share_profit_level` tinyint(4) NOT NULL COMMENT '分润层级 总部:1',
  `share_profit_user` int(11) NOT NULL COMMENT '分润对象用户',
  `order_money` decimal(11,2) NOT NULL COMMENT '订单金额',
  `share_profit_percent` decimal(6,2) DEFAULT NULL COMMENT '分润比例 0~100',
  `share_money` decimal(11,2) DEFAULT NULL COMMENT '分润金额',
  `share_profit_bill_no` varchar(32) DEFAULT NULL COMMENT '分润账单号',
  `ctime` datetime NOT NULL COMMENT '创建时间',
  `utime` datetime DEFAULT NULL COMMENT '更新时间',
  `status` int(1) DEFAULT NULL COMMENT '分润状态：2待分润，4分润成功 5分润失败',
  `pay_type` int(1) DEFAULT NULL COMMENT '支付类型',
  `trade_no` varchar(45) DEFAULT NULL COMMENT '分润执行商户订单号',
  `payment_no` varchar(45) DEFAULT NULL COMMENT '分润成功时的微信订单号',
  `payment_time` datetime DEFAULT NULL COMMENT '微信付款成功时间',
  `is_try_again` int(1) NOT NULL DEFAULT '0' COMMENT '是否使用trade_no重试支付，如果此字段为1，则分润单不可修改，只再次支付',
  `is_generate` int(1) DEFAULT '0' COMMENT '是否已生成分润单',
  `personal` varchar(45) DEFAULT NULL COMMENT '归属人姓名',
  `share_benefit_result` varchar(1150) DEFAULT NULL COMMENT '分润结果',
  PRIMARY KEY (`order_no`,`share_profit_level`),
  KEY `level_index` (`share_profit_level`),
  KEY `trade_no_index` (`trade_no`),
  KEY `user_status_index` (`share_profit_user`,`status`),
  KEY `order_no_index` (`order_no`,`share_profit_user`,`is_generate`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单的分润信息';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `order_status_flow`
--

DROP TABLE IF EXISTS `order_status_flow`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `order_status_flow` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键,自增',
  `order_no` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `pre_status` int(2) DEFAULT NULL COMMENT '订单前置状态',
  `now_status` int(2) NOT NULL COMMENT '当前状态',
  `ctime` datetime NOT NULL,
  `creator_id` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '操作者',
  `remark` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  KEY `idx_order_no` (`order_no`)
) ENGINE=InnoDB AUTO_INCREMENT=808566 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='订单状态流转表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `order_timer`
--

DROP TABLE IF EXISTS `order_timer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `order_timer` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `order_no` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '关联订单号',
  `sno` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '设备SNO',
  `week_day` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '定时周几执行，多个日期用逗号隔开：1周一；2周二；3周三；4周四；5周五；6周六；7周日',
  `time` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '执行时间，24小时制，如: 14:30:00',
  `is_enable` int(1) NOT NULL DEFAULT '1' COMMENT '是否启用：0，否；1，是',
  `is_expire` int(1) NOT NULL DEFAULT '0' COMMENT '是否过期，订单过期后所有定时无效',
  `is_deleted` int(1) NOT NULL DEFAULT '0' COMMENT '是否删除：0，否；1，是',
  `command` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '控制指令内容',
  `ctime` datetime NOT NULL COMMENT '创建时间',
  `utime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `order_timer_order_no_index` (`order_no`),
  KEY `order_timer_device_sno_index` (`sno`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='订单定时任务';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `panel`
--

DROP TABLE IF EXISTS `panel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `panel` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `utime` datetime DEFAULT NULL COMMENT '更新时间',
  `ctime` datetime NOT NULL COMMENT '创建时间',
  `type` int(11) NOT NULL COMMENT '1.数据，2.图表',
  `module` int(11) NOT NULL COMMENT '1.设备分析，2.用户分析，3.订单分析',
  `module_item` int(11) NOT NULL COMMENT '模块中的具体项，比如当前设备总数',
  `sort` int(11) NOT NULL DEFAULT '0' COMMENT '排序',
  `uri` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '图表数据时前端请求的服务uri',
  `condition` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '后续使用的条件等信息',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_resolve_item` (`type`,`module_item`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `personal_panel`
--

DROP TABLE IF EXISTS `personal_panel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `personal_panel` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `ctime` datetime NOT NULL COMMENT '创建时间',
  `utime` datetime DEFAULT NULL COMMENT '更新时间',
  `user_id` int(11) NOT NULL COMMENT '系统账号id',
  `panel_id` int(11) NOT NULL COMMENT '面板项id',
  `item_value` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '定时任务回写的值',
  `is_show` int(1) NOT NULL DEFAULT '0' COMMENT '是否显示，0.不显示，1.显示',
  `sort` int(11) NOT NULL COMMENT '排序',
  `item_odd` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '附属值',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_user_id_panel_id` (`user_id`,`panel_id`) USING BTREE,
  KEY `idx_user_id` (`user_id`) USING BTREE COMMENT '用户id'
) ENGINE=InnoDB AUTO_INCREMENT=20263 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `product` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键,自增长',
  `ctime` datetime NOT NULL COMMENT '添加时间',
  `utime` datetime DEFAULT NULL COMMENT '更新时间',
  `name` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '产品名称',
  `img_url` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '产品图片地址',
  `gizwits_product_key` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '机智云产品key',
  `gizwits_product_secret` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '机智云产品secret',
  `status` int(2) NOT NULL DEFAULT '1' COMMENT '产品状态,1:启用, 0:禁用',
  `category_id` int(5) NOT NULL COMMENT '产品类型',
  `category_name` varchar(30) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '产品类型名称',
  `manufacturer_id` int(11) NOT NULL COMMENT '所属厂商的关联的系统账号',
  `brand_id` int(11) DEFAULT NULL COMMENT '所属品牌',
  `communicate_type` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '通讯方式',
  `sys_user_id` int(11) NOT NULL COMMENT '创建人',
  `is_deleted` int(1) NOT NULL DEFAULT '0' COMMENT '删除状态，0:未删除,1:已删除',
  `gizwits_enterprise_id` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '机智云平台企业id',
  `gizwits_enterprise_secret` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `auth_id` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '产品授权id',
  `auth_secret` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '产品授权密钥',
  `subkey` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用于消息分发，唯一即可',
  `events` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '消息类型，发送给机智云平台',
  `qrcode_type` varchar(10) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'WEB' COMMENT '生成二维码的方式:WEB,网页链接;WEIXIN,微信硬件;',
  `location_type` varchar(10) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'GIZWITS' COMMENT '获取设备坐标方式:GIZWITS,机智云接口;GD,高德接口(需要相关数据点支撑)',
  `gizwits_appid` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '机智云AppId',
  `gizwits_appsecret` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `wx_product_id` varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '微信硬件productId',
  `network_type` int(2) NOT NULL DEFAULT '0' COMMENT '通信类型，0.移动；1.联通',
  PRIMARY KEY (`id`),
  KEY `idx_category` (`category_id`),
  KEY `idx_product_key` (`gizwits_product_key`),
  KEY `idx_sys_user_id` (`sys_user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='产品表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `product_category`
--

DROP TABLE IF EXISTS `product_category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `product_category` (
  `id` int(5) NOT NULL AUTO_INCREMENT COMMENT '主键,自增长',
  `ctime` datetime NOT NULL COMMENT '添加时间',
  `utime` datetime DEFAULT NULL COMMENT '更新时间',
  `name` varchar(30) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '类别名称',
  `remark` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '描述',
  `parent_category_id` int(5) DEFAULT NULL COMMENT '父级类别ID',
  `sys_user_id` int(11) NOT NULL COMMENT '创建人',
  `sys_user_name` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '创建人登录名',
  `is_deleted` int(1) NOT NULL DEFAULT '0' COMMENT '是否删除：0未删除 1已删除',
  PRIMARY KEY (`id`),
  KEY `index_id` (`parent_category_id`,`sys_user_id`),
  KEY `idx_sys_user_id` (`sys_user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='产品类型';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `product_command_config`
--

DROP TABLE IF EXISTS `product_command_config`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `product_command_config` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `product_id` int(11) NOT NULL COMMENT '产品ID',
  `command_type` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '指令类型：SERVICE,收费类型指令；CONTROL,控制类型指令；STATUS,状态类型指令',
  `status_command_type` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '状态指令类型,只有在command_type为STATUS时有值：FREE,空闲指令；USING,使用中指令；FINISH,设备使用完成指令',
  `name` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '指令名称',
  `command` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '下发指令',
  `is_show` int(1) NOT NULL DEFAULT '1' COMMENT '是否在后台展示：0,不展示；1,展示',
  `ctime` datetime NOT NULL,
  `utime` datetime DEFAULT NULL,
  `is_deleted` int(1) DEFAULT '0' COMMENT '是否删除：0,否；1,删除',
  `is_free` int(1) NOT NULL DEFAULT '0' COMMENT '是否免费，0，收费，1，免费',
  `working_mode` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '工作模式',
  `is_clock_correct` int(1) NOT NULL DEFAULT '0' COMMENT '是否需要时钟校准,0 否,1 是',
  `calculate_value` int(11) DEFAULT NULL COMMENT '换算数据点单位',
  `error_range` int(11) DEFAULT NULL COMMENT '误差范围',
  `clock_correct_datapoint` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '时钟校准的数据点',
  `identity_name` varchar(60) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '数据点标识名',
  `ref_dp` varchar(60) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '续费时参考的数据点',
  `show_type` int(1) DEFAULT '1' COMMENT '展示形式：1 文本 2饼状图 3进度条',
  PRIMARY KEY (`id`),
  KEY `index_product_id` (`product_id`)
) ENGINE=InnoDB AUTO_INCREMENT=113 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='产品指令配置表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `product_data_point`
--

DROP TABLE IF EXISTS `product_data_point`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `product_data_point` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键,自增长',
  `product_id` int(11) NOT NULL COMMENT '产品ID',
  `ctime` datetime NOT NULL COMMENT '添加时间',
  `utime` datetime DEFAULT NULL COMMENT '更新时间',
  `show_name` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '显示名称',
  `identity_name` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '标志名称',
  `read_write_type` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '读写类型',
  `data_type` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '数据类型',
  `remark` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
  `value_limit` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '值，比如枚举，数值，布尔等，从接口返回中解析',
  `is_monit` int(1) NOT NULL DEFAULT '1' COMMENT '监控数据点: 0,不监控;1,监控',
  `device_alarm_rank` int(1) DEFAULT '1' COMMENT '1:级别1；2:级别2；3:级别3...',
  `is_deleted` int(1) DEFAULT '0' COMMENT '是否删除：0未删除 1已删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_identity` (`identity_name`,`product_id`),
  KEY `index_product_id` (`product_id`)
) ENGINE=InnoDB AUTO_INCREMENT=614 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='产品数据点';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `product_data_point_ext`
--

DROP TABLE IF EXISTS `product_data_point_ext`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `product_data_point_ext` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `product_id` int(11) NOT NULL COMMENT '产品id',
  `data_id` int(11) NOT NULL COMMENT '数据点id，product_data_point.id',
  `name` varchar(45) NOT NULL COMMENT '指令名称',
  `identity_name` varchar(60) NOT NULL COMMENT '标志名称',
  `show_enable` bit(1) NOT NULL DEFAULT b'1' COMMENT '是否在后台展示：0,不展示；1,展示',
  `ctime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `utime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `vendor` tinyint(4) NOT NULL DEFAULT '0' COMMENT '第三方api提供商，0：无；1：和风；2：阿里',
  `param` tinyint(4) NOT NULL COMMENT '1：温度；2：湿度；3：pm2.5；4：空气质量',
  PRIMARY KEY (`id`),
  UNIQUE KEY `prod_data_id` (`product_id`,`data_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='产品指令配置扩展表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `product_operation_history`
--

DROP TABLE IF EXISTS `product_operation_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `product_operation_history` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `ctime` datetime NOT NULL COMMENT '创建时间',
  `product_id` int(11) DEFAULT NULL COMMENT '产品id',
  `device_sno` varchar(32) DEFAULT NULL COMMENT '设备sno',
  `operate_type` int(11) NOT NULL COMMENT '操作类型',
  `ip` varchar(20) DEFAULT NULL COMMENT 'ip地址',
  `sys_user_id` int(11) NOT NULL COMMENT '操作人',
  `sys_user_name` varchar(255) NOT NULL COMMENT '操作人',
  PRIMARY KEY (`id`),
  KEY `idx_device_sno` (`device_sno`),
  KEY `idx_product_id` (`product_id`)
) ENGINE=InnoDB AUTO_INCREMENT=888 DEFAULT CHARSET=latin1 COMMENT='产品操作记录';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `product_properties`
--

DROP TABLE IF EXISTS `product_properties`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `product_properties` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键,自增长',
  `ctime` datetime NOT NULL COMMENT '添加时间',
  `utime` datetime DEFAULT NULL COMMENT '更新时间',
  `property_key` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '属性key',
  `property_name` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '属性名称',
  `tips` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '提示语',
  `category_id` int(5) NOT NULL COMMENT '产品类型',
  `category_name` varchar(30) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '产品类型名称',
  `is_not_null` int(1) NOT NULL COMMENT '是否必填,1:是 0:否',
  `is_select_value` int(1) NOT NULL COMMENT '是否选择值,1:是,选择 0:否,填写',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_property_key` (`property_key`),
  KEY `idx_category` (`category_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='产品属性定义表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `product_properties_option_value`
--

DROP TABLE IF EXISTS `product_properties_option_value`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `product_properties_option_value` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键,自增长',
  `ctime` datetime NOT NULL COMMENT '添加时间',
  `utime` datetime DEFAULT NULL COMMENT '更新时间',
  `property_key` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '属性key',
  `property_name` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '属性名称',
  `property_value` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '属性值',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='产品属性值表,提供选择';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `product_service_detail`
--

DROP TABLE IF EXISTS `product_service_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `product_service_detail` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ctime` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '创建时间',
  `utime` datetime DEFAULT NULL COMMENT '修改时间',
  `service_mode_id` int(11) NOT NULL COMMENT '收费模式的id',
  `product_id` int(11) NOT NULL COMMENT '产品的id',
  `service_type` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '收费类型',
  `service_type_id` int(11) DEFAULT NULL COMMENT '收费类型的id',
  `price` double(8,2) NOT NULL COMMENT '单价',
  `num` double(8,2) NOT NULL COMMENT '数量',
  `unit` varchar(420) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '单位',
  `status` int(11) DEFAULT '1' COMMENT '状态：0：启用，1:禁用',
  `sys_user_id` int(11) NOT NULL COMMENT '系统用户的id，创建者',
  `sys_user_name` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '创建人姓名',
  `command` varchar(500) COLLATE utf8mb4_unicode_ci NOT NULL,
  `is_deleted` int(1) NOT NULL DEFAULT '0' COMMENT '是否删除：0,否；1,是',
  `name` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '收费详情名称，非必填',
  PRIMARY KEY (`id`),
  KEY `index2` (`service_mode_id`,`service_type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=157 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='产品服务详情表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `product_service_mode`
--

DROP TABLE IF EXISTS `product_service_mode`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `product_service_mode` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键,自增长',
  `ctime` datetime NOT NULL COMMENT '添加时间',
  `utime` datetime DEFAULT NULL COMMENT '更新时间',
  `name` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '服务方式名称',
  `unit` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '单位',
  `product_id` int(11) NOT NULL COMMENT '所属产品ID',
  `service_type` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '服务类型',
  `sys_user_id` int(11) NOT NULL COMMENT '创建人id',
  `sys_user_name` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '创建人姓名',
  `status` int(11) DEFAULT '1' COMMENT '状态：0:禁用，1:启用',
  `service_type_id` int(11) DEFAULT NULL COMMENT '服务类型ID对应product_command_config的ID',
  `command` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '在没收费模式详情的时候需要的指令,如免费模式时需要下发的指令',
  `is_deleted` int(1) NOT NULL DEFAULT '0' COMMENT '是否删除：0,否；1,是',
  `is_free` int(1) NOT NULL DEFAULT '0' COMMENT '是否收费：0，收费，1，免费，，数据从收费模式指令处获得',
  `working_mode` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '工作模式',
  `unit_price` double(8,2) DEFAULT NULL COMMENT '单价',
  PRIMARY KEY (`id`),
  KEY `index_id` (`product_id`)
) ENGINE=InnoDB AUTO_INCREMENT=74 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='产品(或者设备)服务方式';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `product_to_properties`
--

DROP TABLE IF EXISTS `product_to_properties`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `product_to_properties` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键,自增长',
  `ctime` datetime NOT NULL COMMENT '添加时间',
  `utime` datetime DEFAULT NULL COMMENT '更新时间',
  `product_id` int(11) NOT NULL COMMENT '产品ID',
  `product_name` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '产品名称',
  `property_id` int(11) NOT NULL COMMENT '属性id',
  `property_key` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '属性key',
  `property_name` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '属性名称',
  `property_value` varchar(256) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '属性值',
  PRIMARY KEY (`id`),
  KEY `index_id` (`product_id`,`property_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='产品属性关系表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `recharge_money`
--

DROP TABLE IF EXISTS `recharge_money`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `recharge_money` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键,自增长',
  `ctime` datetime NOT NULL COMMENT '添加时间',
  `utime` datetime DEFAULT NULL COMMENT '更新时间',
  `type` int(1) NOT NULL COMMENT '类型 1，固定充值额，2，自定义充值额',
  `charge_money` double(12,2) DEFAULT NULL COMMENT '充值金额',
  `discount_money` double(12,2) DEFAULT NULL COMMENT '赠送金额',
  `rate` decimal(3,2) DEFAULT '0.00' COMMENT '优惠比例',
  `sys_user_id` int(11) DEFAULT NULL COMMENT '创建者id',
  `project_id` int(11) DEFAULT NULL COMMENT '项目id：1艾芙芮  2卡励',
  `sort` int(1) DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`id`),
  KEY `idx_sys_user_id` (`sys_user_id`),
  KEY `idx_project_id` (`project_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='充值优惠表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `refund_apply`
--

DROP TABLE IF EXISTS `refund_apply`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `refund_apply` (
  `refund_no` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '退款单id',
  `status` int(2) NOT NULL COMMENT '状态：1待审核，2审核通过，3审核不通过，4已退款',
  `order_no` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '对应的订单id',
  `amount` double(12,2) NOT NULL COMMENT '退款金额',
  `path` int(2) NOT NULL COMMENT '退款路径，枚举值和支付类型一样',
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `user_mobile` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用户手机号',
  `user_alipay_account` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用户支付宝帐号',
  `user_alipay_real_name` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用户支付宝真实姓名',
  `refund_reason` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '退款原因',
  `audit_reason` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '审核原因',
  `auditor_id` int(11) DEFAULT NULL COMMENT '审核人id',
  `audit_time` datetime DEFAULT NULL COMMENT '审核时间',
  `refunder_id` int(11) DEFAULT NULL COMMENT '退款人id',
  `refund_time` datetime DEFAULT NULL COMMENT '退款时间',
  `sys_user_id` int(11) NOT NULL COMMENT '和订单的sys_user_id一致',
  `ctime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `utime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `nickname` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用户昵称',
  PRIMARY KEY (`refund_no`),
  KEY `idx_order_no` (`order_no`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_auditor_id` (`auditor_id`),
  KEY `idx_refunder_id` (`refunder_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='退款申请表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `refund_base`
--

DROP TABLE IF EXISTS `refund_base`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `refund_base` (
  `refund_no` varchar(20) NOT NULL,
  `amount` decimal(12,2) NOT NULL COMMENT '退款金额',
  `ctime` datetime NOT NULL COMMENT '退款时间',
  `order_no` varchar(20) NOT NULL COMMENT '订单号',
  `trade_no` varchar(31) NOT NULL COMMENT '支付交易号',
  `refund_method` tinyint(4) NOT NULL COMMENT '退款方式：1微信，2：支付宝，3：充值卡',
  PRIMARY KEY (`refund_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `share_benefit_rule`
--

DROP TABLE IF EXISTS `share_benefit_rule`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `share_benefit_rule` (
  `id` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '主键ID',
  `share_benefit_rule_name` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '分润规则名称',
  `sys_account_id` int(11) DEFAULT NULL COMMENT '运营商关联系统用户',
  `operator_name` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '运营商名称',
  `start_time` datetime NOT NULL COMMENT '账单首次生成时间',
  `frequency` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '对账单生成频率: DAY,WEEK,MONTH,YEAR',
  `last_execute_time` datetime DEFAULT NULL COMMENT '上一次分润执行的时间',
  `ctime` datetime NOT NULL COMMENT '创建时间',
  `utime` datetime DEFAULT NULL COMMENT '修改时间',
  `sys_user_id` int(11) NOT NULL COMMENT '分润规则的所有者',
  `is_deleted` int(1) NOT NULL DEFAULT '0',
  `operator_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `index_sys_user_id` (`sys_user_id`,`sys_account_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='分润规则表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `share_benefit_rule_detail`
--

DROP TABLE IF EXISTS `share_benefit_rule_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `share_benefit_rule_detail` (
  `id` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '主键ID',
  `rule_id` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '分润规则主表ID',
  `name` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '详细分润规则名称',
  `share_percentage` decimal(6,2) NOT NULL DEFAULT '0.00' COMMENT '分润比例，显示的是百分数',
  `share_type` varchar(10) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'SINGLE' COMMENT '分润类型，使用设备：ALL,所有设备；SINGLE，个别设备；',
  `ctime` datetime NOT NULL COMMENT '创建时间',
  `utime` datetime DEFAULT NULL COMMENT '修改时间',
  `is_deleted` int(1) NOT NULL DEFAULT '0',
  `level` int(1) DEFAULT NULL COMMENT '分润比例层级：1厂商 2代理商 3运营商 4子运营商',
  PRIMARY KEY (`id`),
  KEY `index_ruleId` (`name`,`rule_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='分润规则详细表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `share_benefit_rule_detail_device`
--

DROP TABLE IF EXISTS `share_benefit_rule_detail_device`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `share_benefit_rule_detail_device` (
  `id` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '主键ID',
  `rule_detail_id` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '分润规则详细表ID',
  `sno` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '设备SNO',
  `ctime` datetime NOT NULL COMMENT '创建时间',
  `utime` datetime DEFAULT NULL,
  `is_deleted` int(1) NOT NULL DEFAULT '0',
  `share_percentage` decimal(6,2) DEFAULT NULL COMMENT '上级为本运营商设置的分润比例',
  `children_percentage` decimal(6,2) DEFAULT '0.00' COMMENT '此设备在本运营商下级的分润比例',
  PRIMARY KEY (`id`),
  KEY `index_rule_detail_id` (`rule_detail_id`),
  KEY `index_sno` (`sno`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='分润规则详细设备表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `share_benefit_rule_modify_limit`
--

DROP TABLE IF EXISTS `share_benefit_rule_modify_limit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `share_benefit_rule_modify_limit` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键,自增长',
  `ctime` datetime NOT NULL COMMENT '添加时间',
  `utime` datetime DEFAULT NULL COMMENT '更新时间',
  `sys_user_id` int(11) NOT NULL COMMENT '创建人(厂商绑定的系统帐号)',
  `before_bill_limit` int(11) NOT NULL COMMENT '首次账单生成前可修改次数',
  `period_unit` int(11) NOT NULL COMMENT '周期单位 1:每年, 2:每月',
  `period_limit` int(11) NOT NULL COMMENT '周期内可修改次数',
  `start_time` datetime NOT NULL COMMENT '可允许修改时段 开始时间',
  `end_time` datetime NOT NULL COMMENT '可允许修改时段 结束时间',
  `weekdays` varchar(20) NOT NULL DEFAULT '1,2,3,4,5,6,7' COMMENT '可允许修改时段 1:(星期一) ... 7:(星期日)',
  PRIMARY KEY (`id`),
  UNIQUE KEY `sys_user_id` (`sys_user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='分润规则修改次数限制';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `share_benefit_rule_modify_record`
--

DROP TABLE IF EXISTS `share_benefit_rule_modify_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `share_benefit_rule_modify_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键,自增长',
  `ctime` datetime NOT NULL COMMENT '添加时间',
  `utime` datetime DEFAULT NULL COMMENT '更新时间',
  `rule_id` varchar(50) NOT NULL COMMENT '分润规则ID',
  `sys_user_id` int(11) NOT NULL COMMENT '修改者',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='分润规则修改记录';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `share_benefit_sheet`
--

DROP TABLE IF EXISTS `share_benefit_sheet`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `share_benefit_sheet` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `sheet_no` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '分润账单号',
  `operator_name` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '运营商名称',
  `status` int(2) NOT NULL COMMENT '账单状态：0，创建；1，待审核;2，待分润；3,执行分润中；4，分润成功；5，分润失败；',
  `pay_type` int(2) NOT NULL DEFAULT '1' COMMENT '分润支付类型：1，微信；2，支付宝；3，线下；',
  `order_count` int(11) NOT NULL DEFAULT '0' COMMENT '订单数',
  `total_money` double(11,2) NOT NULL DEFAULT '0.00' COMMENT '所有订单的总合计金额',
  `share_money` double(11,2) NOT NULL DEFAULT '0.00' COMMENT '当前运营商的分润金额',
  `ctime` datetime NOT NULL,
  `utime` datetime DEFAULT NULL,
  `trade_no` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '分润执行商户订单号',
  `payment_no` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '分润成功时的微信订单号',
  `payment_time` datetime DEFAULT NULL COMMENT '微信付款成功时间',
  `audit_time` datetime DEFAULT NULL COMMENT '审核时间',
  `pay_account` varchar(32) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '支付账号，存储的是公众号的信息wx_id',
  `receiver_openid` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '收款人openid',
  `receiver_name` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '收款人名称',
  `sys_user_id` int(11) DEFAULT NULL COMMENT '创建人(生成分润单时把运营商关联的系统账号写进来)',
  `is_try_again` int(1) NOT NULL DEFAULT '0' COMMENT '是否使用trade_no重试支付，如果此字段为1，则分润单不可修改，只再次支付',
  `sys_account_id` int(11) DEFAULT NULL COMMENT '运营商的账号',
  `rule_id` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `operator_id` int(11) DEFAULT NULL,
  `wx_total_money` double(11,2) DEFAULT NULL COMMENT '微信订单合计',
  `card_total_money` double(11,2) DEFAULT NULL COMMENT '刷卡订单合计',
  `alipay_total_money` double(11,2) DEFAULT NULL COMMENT '支付宝订单合计',
  `other_total_money` double(11,2) DEFAULT NULL COMMENT '其他订单合计',
  `share_profit_batch_id` int(11) DEFAULT NULL COMMENT '分润批次',
  PRIMARY KEY (`id`),
  KEY `index_status` (`status`),
  KEY `index_id` (`sheet_no`)
) ENGINE=InnoDB AUTO_INCREMENT=59581 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='分润账单表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `share_benefit_sheet_action_record`
--

DROP TABLE IF EXISTS `share_benefit_sheet_action_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `share_benefit_sheet_action_record` (
  `id` int(11) NOT NULL COMMENT '主键ID',
  `sheet_id` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '分润单ID',
  `action_type` int(2) NOT NULL DEFAULT '0' COMMENT '操作类型：0，创建分润单；1，审核通过；2、重新审核；3，执行分润',
  `user_id` int(11) NOT NULL COMMENT '操作者',
  `ctime` datetime NOT NULL COMMENT '操作时间',
  PRIMARY KEY (`id`),
  KEY `index_id` (`sheet_id`,`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='分润单操作记录';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `share_benefit_sheet_order`
--

DROP TABLE IF EXISTS `share_benefit_sheet_order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `share_benefit_sheet_order` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sheet_no` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '分润单ID',
  `operator_id` int(11) DEFAULT NULL COMMENT '运营商ID',
  `device_sno` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '设备sno',
  `order_no` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '订单号',
  `share_rule_detail_id` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '详细分润规则ID',
  `share_percentage` double(10,2) NOT NULL COMMENT '直属运营商分润比例',
  `share_money` double(11,4) DEFAULT NULL COMMENT '订单在该运营商的分润金额，四舍五入保留4位小数',
  `order_amount` double(12,5) DEFAULT NULL COMMENT '订单金额',
  `status` int(2) NOT NULL COMMENT '状态：1、待审核；2、审核通过；3、审核不通过；4、执行分润中；5、分润成功；',
  `ctime` datetime NOT NULL,
  `utime` datetime DEFAULT NULL,
  `sys_account_id` int(11) DEFAULT NULL COMMENT '运营商或代理商的账号ID',
  `children_share_percentage` double(3,2) DEFAULT NULL COMMENT '下一级在该订单的分润比例',
  PRIMARY KEY (`id`),
  KEY `index_id` (`sheet_no`,`order_no`)
) ENGINE=InnoDB AUTO_INCREMENT=473670 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='所有要参与分润的订单';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `share_benefit_sheet_pay_record`
--

DROP TABLE IF EXISTS `share_benefit_sheet_pay_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `share_benefit_sheet_pay_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `sheet_id` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '分润单ID',
  `trade_no` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '交易订单号',
  `amount` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '分润金额',
  `content` varchar(1500) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '分润执行消息体',
  `status` int(1) NOT NULL DEFAULT '1' COMMENT '状态：1，执行分润；2，分润成功；3，分润失败；',
  `user_id` int(11) NOT NULL COMMENT '操作者',
  `ctime` datetime NOT NULL,
  `utime` datetime DEFAULT NULL,
  `callback_content` varchar(1500) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '支付反馈结果',
  PRIMARY KEY (`id`),
  KEY `index_id` (`sheet_id`,`trade_no`,`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='分润单支付记录';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `share_profit_batch`
--

DROP TABLE IF EXISTS `share_profit_batch`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `share_profit_batch` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '分润批次ID',
  `frequency` varchar(8) NOT NULL COMMENT '账单生成周期 DAY,WEEK,MONTH,YEAR',
  `period_start_time` datetime NOT NULL COMMENT '周期开始时间',
  `period_end_time` datetime NOT NULL COMMENT '生成时间 周期结束时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COMMENT='分润批次';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `share_profit_summary`
--

DROP TABLE IF EXISTS `share_profit_summary`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `share_profit_summary` (
  `id` varchar(32) NOT NULL COMMENT '分润账单汇总ID',
  `batch_id` int(11) NOT NULL COMMENT '分润批次ID',
  `sys_user_id` int(11) NOT NULL COMMENT '用户及其下级的分润账单汇总',
  `order_count` int(11) NOT NULL DEFAULT '0' COMMENT '订单总数',
  `order_money` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '订单总金额',
  `share_money` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '分润账单总金额',
  `status` int(11) NOT NULL DEFAULT '1' COMMENT '状态 1:未支付 2:部分已支付 3:已完成',
  `ctime` datetime NOT NULL COMMENT '创建时间',
  `utime` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `batch_id` (`batch_id`,`sys_user_id`),
  KEY `sys_user_id` (`sys_user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='分润账单汇总';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `share_profit_summary_detail`
--

DROP TABLE IF EXISTS `share_profit_summary_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `share_profit_summary_detail` (
  `summary_id` varchar(32) NOT NULL COMMENT '分润账单汇总ID',
  `bill_no` varchar(32) NOT NULL COMMENT '分润账单号',
  PRIMARY KEY (`summary_id`,`bill_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='分润账单汇总明细';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `stat_device_hour`
--

DROP TABLE IF EXISTS `stat_device_hour`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `stat_device_hour` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键,自增',
  `ctime` datetime NOT NULL COMMENT '添加时间',
  `product_id` int(11) DEFAULT NULL COMMENT '产品id',
  `sys_user_id` int(11) DEFAULT NULL COMMENT '归属系统用户id',
  `zero` int(11) DEFAULT '0' COMMENT '0点设备数量',
  `ONE` int(11) DEFAULT '0' COMMENT '1点设备数量',
  `two` int(11) DEFAULT '0' COMMENT '2点设备数量',
  `three` int(11) DEFAULT '0' COMMENT '3点设备数量',
  `four` int(11) DEFAULT '0' COMMENT '4点设备数量',
  `five` int(11) DEFAULT '0' COMMENT '5点设备数量',
  `six` int(11) DEFAULT '0' COMMENT '6点设备数量',
  `seven` int(11) DEFAULT '0' COMMENT '7点设备数量',
  `eight` int(11) DEFAULT '0' COMMENT '8点设备数量',
  `nine` int(11) DEFAULT '0' COMMENT '9点设备数量',
  `ten` int(11) DEFAULT '0' COMMENT '10点设备数量',
  `eleven` int(11) DEFAULT '0' COMMENT '11点设备数量',
  `twelve` int(11) DEFAULT '0' COMMENT '12点设备数量',
  `thriteen` int(11) DEFAULT '0' COMMENT '13点设备数量',
  `fourteen` int(11) DEFAULT '0' COMMENT '14点设备数量',
  `fifteen` int(11) DEFAULT '0' COMMENT '15点设备数量',
  `siteen` int(11) DEFAULT '0' COMMENT '16点设备数量',
  `seventeen` int(11) DEFAULT '0' COMMENT '17点设备数量',
  `eighteen` int(11) DEFAULT '0' COMMENT '18点设备数量',
  `nineteen` int(11) DEFAULT '0' COMMENT '19点设备数量',
  `twenty` int(11) DEFAULT '0' COMMENT '20点设备数量',
  `twenty_one` int(11) DEFAULT '0' COMMENT '21点设备数量',
  `twenty_two` int(11) DEFAULT '0' COMMENT '22点设备数量',
  `twenty_three` int(11) DEFAULT '0' COMMENT '23点设备数量',
  PRIMARY KEY (`id`),
  KEY `idx_ctime` (`ctime`),
  KEY `idx_sys_user_id` (`sys_user_id`),
  KEY `idx_product_id` (`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='设备在线时段分析统计表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `stat_device_location`
--

DROP TABLE IF EXISTS `stat_device_location`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `stat_device_location` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键,自增',
  `ctime` datetime NOT NULL COMMENT '添加时间',
  `sys_user_id` int(11) DEFAULT NULL COMMENT '归属系统用户id',
  `province_id` int(11) DEFAULT NULL COMMENT '省id',
  `province` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '省名称',
  `device_count` int(11) DEFAULT '0' COMMENT '省对应的用户数量',
  `product_id` int(11) DEFAULT NULL COMMENT '产品id',
  `proportion` double(12,2) DEFAULT '0.00' COMMENT '设备占比',
  PRIMARY KEY (`id`),
  KEY `idx_ctime` (`ctime`),
  KEY `idx_sys_user_id` (`sys_user_id`),
  KEY `idx_product_id` (`product_id`)
) ENGINE=InnoDB AUTO_INCREMENT=71880 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='设备地图分布统计表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `stat_device_order_widget`
--

DROP TABLE IF EXISTS `stat_device_order_widget`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `stat_device_order_widget` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键,自增',
  `ctime` datetime NOT NULL COMMENT '添加时间',
  `utime` datetime NOT NULL COMMENT '修改时间',
  `sys_user_id` int(11) DEFAULT NULL COMMENT '归属系统用户id',
  `product_id` int(11) DEFAULT NULL COMMENT '产品id',
  `total_count` int(11) DEFAULT '0' COMMENT '当前设备总数',
  `new_count` int(11) DEFAULT '0' COMMENT '今日设备新增数',
  `ordered_percent` double(12,2) DEFAULT '0.00' COMMENT '设备订单率（昨天下单设备数-今天下单设备数）/今天总',
  `alarm_count` int(11) DEFAULT '0' COMMENT '当前设备故障数',
  `warn_count` int(11) DEFAULT '0' COMMENT '当前设备警告数',
  `warn_record` int(11) DEFAULT '0' COMMENT '今日告警记录',
  `alarm_percent` double(12,2) DEFAULT '0.00' COMMENT '当前设备故障率',
  `order_count_today` int(11) DEFAULT '0' COMMENT '今日订单数量',
  `order_count_yesterday` int(11) DEFAULT '0' COMMENT '昨日订单数量',
  `order_new_percent_yesterday` double(12,2) DEFAULT '0.00' COMMENT '昨天订单新增率',
  `order_count_month` int(11) DEFAULT '0' COMMENT '本月订单数',
  `order_count_before_yesterday` int(11) DEFAULT NULL COMMENT '前天订单数',
  `share_order_count` int(11) DEFAULT '0',
  `share_order_money` double(12,2) DEFAULT '0.00',
  `ordered_count` int(11) DEFAULT '0' COMMENT '今天下单设备数',
  `online_device_count` int(11) DEFAULT NULL COMMENT '在线设备数',
  `activated_device_count` int(11) DEFAULT NULL COMMENT '已激活设备数',
  `activated_device_count_today` int(11) DEFAULT NULL COMMENT '今日新激活设备数',
  `order_total_count` int(11) DEFAULT NULL COMMENT '所有订单数量',
  `order_finish_count` int(11) DEFAULT NULL COMMENT '所有已完成的订单数量',
  PRIMARY KEY (`id`),
  KEY `idx_ctime` (`ctime`),
  KEY `idx_utime` (`utime`),
  KEY `idx_sys_user_id` (`sys_user_id`),
  KEY `idx_product_id` (`product_id`)
) ENGINE=InnoDB AUTO_INCREMENT=58013 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='设备订单看板数据统计表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `stat_device_trend`
--

DROP TABLE IF EXISTS `stat_device_trend`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `stat_device_trend` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键,自增',
  `ctime` datetime NOT NULL COMMENT '添加时间',
  `sys_user_id` int(11) DEFAULT '0' COMMENT '归属系统用户id',
  `new_count` int(11) DEFAULT '0' COMMENT '新增上线数量',
  `ordered_percent` double(12,2) DEFAULT '0.00' COMMENT '设备订单率',
  `ordered_count` int(11) DEFAULT '0' COMMENT '含有订单的设备数',
  `product_id` int(11) DEFAULT NULL COMMENT '对应的产品id',
  `active_count` int(11) DEFAULT '0' COMMENT '设备活跃数',
  `previous_deivce_total` int(11) DEFAULT NULL COMMENT '之前的设备总数',
  `new_activated_count` int(11) DEFAULT NULL COMMENT '今日新激活设备数',
  `fault_count` int(11) DEFAULT NULL COMMENT '故障设备数',
  `alert_count` int(11) DEFAULT NULL COMMENT '报警设备数',
  PRIMARY KEY (`id`),
  KEY `idx_ctime` (`ctime`),
  KEY `idx_sys_user_id` (`sys_user_id`),
  KEY `idx_product_id` (`product_id`)
) ENGINE=InnoDB AUTO_INCREMENT=57722 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='设备趋势统计表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `stat_fault_alert_type`
--

DROP TABLE IF EXISTS `stat_fault_alert_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `stat_fault_alert_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键，自增',
  `ctime` datetime DEFAULT NULL COMMENT '创建时间',
  `sno` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '设备号',
  `sys_user_id` int(11) DEFAULT NULL COMMENT '归属用户id',
  `show_name` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '显示名称',
  `identity_name` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '标志名称',
  `remark` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
  `count` int(11) DEFAULT '0' COMMENT '统计数量',
  `product_id` int(11) DEFAULT NULL COMMENT '产品id',
  PRIMARY KEY (`id`),
  KEY `idx_ctime` (`ctime`),
  KEY `idx_sys_user_id` (`sys_user_id`),
  KEY `idx_product_id` (`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `stat_order`
--

DROP TABLE IF EXISTS `stat_order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `stat_order` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键,自增',
  `ctime` datetime NOT NULL COMMENT '添加时间',
  `sys_user_id` int(11) DEFAULT NULL COMMENT '归属系统用户id',
  `sno` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '设备序列号',
  `operator_id` int(11) DEFAULT NULL COMMENT '运营商id',
  `order_amount` double(12,2) DEFAULT '0.00' COMMENT '订单总金额',
  `order_count` int(11) DEFAULT '0' COMMENT '订单数量',
  `ordered_percent` double(12,2) DEFAULT '0.00' COMMENT '订单增长率',
  `product_id` int(11) DEFAULT NULL COMMENT '产品id',
  `agent_id` int(11) DEFAULT NULL COMMENT '代理商id',
  `launch_area_id` int(11) DEFAULT NULL COMMENT '投放点id',
  `refund_count` int(11) DEFAULT NULL COMMENT '退款数',
  `refund_amount` double(12,2) DEFAULT NULL COMMENT '退款金额',
  `generated_share_amount` double(12,2) DEFAULT NULL COMMENT '已生成分润单的分润金额',
  `ungenerate_share_order_amount` double(12,2) DEFAULT NULL COMMENT '未生成分润单的订单金额',
  PRIMARY KEY (`id`),
  KEY `idx_operator_id` (`operator_id`),
  KEY `idx_ctime` (`ctime`),
  KEY `idx_sys_user_id` (`sys_user_id`),
  KEY `idx_sno` (`sno`),
  KEY `idx_product_id` (`product_id`),
  KEY `idx_agent_id` (`agent_id`),
  KEY `idx_launch_area_id` (`launch_area_id`)
) ENGINE=InnoDB AUTO_INCREMENT=122211 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='订单分析统计表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `stat_user_hour`
--

DROP TABLE IF EXISTS `stat_user_hour`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `stat_user_hour` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键,自增',
  `ctime` datetime NOT NULL COMMENT '添加时间',
  `sys_user_id` int(11) DEFAULT NULL COMMENT '归属系统用户id',
  `zero` int(11) DEFAULT '0' COMMENT '0点用户控制设备数量',
  `ONE` int(11) DEFAULT '0' COMMENT '1点用户控制设备数量',
  `two` int(11) DEFAULT '0' COMMENT '2点用户控制设备数量',
  `three` int(11) DEFAULT '0' COMMENT '3点用户控制设备数量',
  `four` int(11) DEFAULT '0' COMMENT '4点用户控制设备数量',
  `five` int(11) DEFAULT '0' COMMENT '5点用户控制设备数量',
  `six` int(11) DEFAULT '0' COMMENT '6点用户控制设备数量',
  `seven` int(11) DEFAULT '0' COMMENT '7点用户控制设备数量',
  `eight` int(11) DEFAULT '0' COMMENT '8点用户控制设备数量',
  `nine` int(11) DEFAULT '0' COMMENT '9点用户控制设备数量',
  `ten` int(11) DEFAULT '0' COMMENT '10点用户控制设备数量',
  `eleven` int(11) DEFAULT '0' COMMENT '11点用户控制设备数量',
  `twelve` int(11) DEFAULT '0' COMMENT '12点用户控制设备数量',
  `thriteen` int(11) DEFAULT '0' COMMENT '13点用户控制设备数量',
  `fourteen` int(11) DEFAULT '0' COMMENT '14点用户控制设备数量',
  `fifteen` int(11) DEFAULT '0' COMMENT '15点用户控制设备数量',
  `siteen` int(11) DEFAULT '0' COMMENT '16点用户控制设备数量',
  `seventeen` int(11) DEFAULT '0' COMMENT '17点用户控制设备数量',
  `eighteen` int(11) DEFAULT '0' COMMENT '18点用户控制设备数量',
  `nineteen` int(11) DEFAULT '0' COMMENT '19点用户控制设备数量',
  `twenty` int(11) DEFAULT '0' COMMENT '20点用户控制设备数量',
  `twenty_one` int(11) DEFAULT '0' COMMENT '21点用户控制设备数量',
  `twenty_two` int(11) DEFAULT '0' COMMENT '22点用户控制设备数量',
  `twenty_three` int(11) DEFAULT '0' COMMENT '23点用户控制设备数量',
  PRIMARY KEY (`id`),
  KEY `idx_ctime` (`ctime`),
  KEY `idx_sys_user_id` (`sys_user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='控制设备时段用户统计表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `stat_user_location`
--

DROP TABLE IF EXISTS `stat_user_location`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `stat_user_location` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键,自增',
  `ctime` datetime NOT NULL COMMENT '添加时间',
  `sys_user_id` int(11) DEFAULT NULL COMMENT '归属系统用户id',
  `province_id` int(11) DEFAULT NULL COMMENT '省id',
  `province` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '省名称',
  `user_count` int(11) DEFAULT '0' COMMENT '省对应的用户数量',
  `proportion` double(12,2) DEFAULT '0.00' COMMENT '用户百分比',
  PRIMARY KEY (`id`),
  KEY `idx_sys_user_id` (`sys_user_id`),
  KEY `idx_ctime` (`ctime`)
) ENGINE=InnoDB AUTO_INCREMENT=606943 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户地图分布统计表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `stat_user_trend`
--

DROP TABLE IF EXISTS `stat_user_trend`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `stat_user_trend` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键,自增',
  `ctime` datetime NOT NULL COMMENT '添加时间',
  `sys_user_id` int(11) DEFAULT NULL COMMENT '归属系统用户id',
  `new_count` int(11) DEFAULT '0' COMMENT '新增用户数量',
  `active_count` int(11) DEFAULT '0' COMMENT '活跃用户数量',
  `total_count` int(11) DEFAULT '0' COMMENT '用户总数',
  `male` int(11) DEFAULT '0' COMMENT '男性用户数量',
  `female` int(11) DEFAULT '0' COMMENT '女性用户数量',
  `zero` int(11) DEFAULT '0' COMMENT '使用该产品0次用户数量',
  `one_two` int(11) DEFAULT '0' COMMENT '使用该产品1~2次用户数量',
  `three_four` int(11) DEFAULT '0' COMMENT '使用该产品3~4次用户数量',
  `five_six` int(11) DEFAULT '0' COMMENT '使用该产品5~6次用户数量',
  `seven_eight` int(11) DEFAULT '0' COMMENT '使用该产品7~8次用户数量',
  `nine_ten` int(11) DEFAULT '0' COMMENT '使用该产品9~10次用户数量',
  `ten_more` int(11) DEFAULT '0' COMMENT '使用该产品10次及其以上用户数量',
  PRIMARY KEY (`id`),
  KEY `idx_ctime` (`ctime`),
  KEY `idx_sys_user_id` (`sys_user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=34638 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户趋势及性别，使用次数统计表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `stat_user_widget`
--

DROP TABLE IF EXISTS `stat_user_widget`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `stat_user_widget` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键,自增',
  `ctime` datetime NOT NULL COMMENT '添加时间',
  `utime` datetime NOT NULL COMMENT '修改时间',
  `sys_user_id` int(11) DEFAULT NULL COMMENT '归属系统用户id',
  `total_count` int(11) DEFAULT '0' COMMENT '当前用户总数',
  `new_percent` double(12,2) DEFAULT '0.00' COMMENT '昨天用户增长率',
  `active_count` int(11) DEFAULT '0' COMMENT '今日活跃用户数',
  `active_percent` double(12,2) DEFAULT '0.00' COMMENT '昨天用户活跃率',
  `new_count` int(11) DEFAULT '0' COMMENT '今天新增用户数',
  `ordered_count` int(11) DEFAULT NULL COMMENT '所有使用过租赁服务的用户总数',
  `new_ordered_count` int(11) DEFAULT NULL COMMENT '新增使用过租赁服务的用户数',
  PRIMARY KEY (`id`),
  KEY `idx_ctime` (`ctime`),
  KEY `idx_utime` (`utime`),
  KEY `idx_sys_user_id` (`sys_user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=34639 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='设备订单看板数据统计表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sys_config`
--

DROP TABLE IF EXISTS `sys_config`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_config` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键,自增长',
  `ctime` datetime NOT NULL COMMENT '添加时间',
  `utime` datetime DEFAULT NULL COMMENT '更新时间',
  `config_key` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '配置key',
  `config_value` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '配置value',
  `remark` varchar(200) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '描述',
  `status` int(1) NOT NULL DEFAULT '1' COMMENT '0:禁用 1:启用',
  `sys_user_id` int(11) DEFAULT NULL COMMENT '操作人员',
  `sys_user_name` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `index-confing` (`config_key`,`status`)
) ENGINE=InnoDB AUTO_INCREMENT=152 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='系统配置表,用来动态添加常量配置';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sys_message`
--

DROP TABLE IF EXISTS `sys_message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_message` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键,自增长',
  `ctime` datetime NOT NULL COMMENT '添加时间',
  `utime` datetime DEFAULT NULL COMMENT '更新时间',
  `addresser_id` int(11) DEFAULT NULL COMMENT '发件人ID',
  `addresser_name` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '发件人名称',
  `title` text COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '标题',
  `content` text COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '内容',
  `is_read` int(1) NOT NULL DEFAULT '0' COMMENT '是否已读：0 未读，1已读',
  `is_send` int(1) NOT NULL DEFAULT '1' COMMENT '是否发送：0，为发送，1，已发送',
  `is_bind_wx` int(1) DEFAULT NULL COMMENT '是否绑定微信',
  `message_type` tinyint(3) DEFAULT NULL COMMENT '消息类型：1工单消息 2设备消息 3分润账单 4 租赁消息',
  `is_deleted` int(1) DEFAULT '0' COMMENT '是否删除：0未删除 1已删除',
  `is_send_all` int(1) DEFAULT '0' COMMENT '是否发送给全部用户：0不是全部用户 1全部系统用户 2全部微信用户 3全部app用户',
  `mac` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '设备Mac',
  `is_fixed` int(1) DEFAULT '1' COMMENT '是否解决：0未解决 1已解决',
  `command` varchar(450) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '故障，滤芯，租赁消息的触发条件',
  PRIMARY KEY (`id`),
  KEY `addresser_id_index` (`addresser_id`),
  KEY `index_ctime` (`ctime`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='系统消息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sys_message_to_user`
--

DROP TABLE IF EXISTS `sys_message_to_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_message_to_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键,自增长',
  `ctime` datetime NOT NULL COMMENT '添加时间',
  `utime` datetime DEFAULT NULL COMMENT '更新时间',
  `sys_message_id` int(11) DEFAULT NULL COMMENT '消息系统id',
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  `username` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用户名称',
  `role_id` int(11) DEFAULT NULL,
  `role_name` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `is_read` int(1) NOT NULL DEFAULT '0' COMMENT '是否已读：0 未读，1已读',
  `is_send` int(1) NOT NULL DEFAULT '1' COMMENT '是否发送：0，为发送，1，已发送',
  `is_deleted` int(1) DEFAULT '0' COMMENT '是否删除：0未删除 1已删除',
  `user_type` int(1) DEFAULT '2' COMMENT '用户类型：1系统用户 2微信用户/app 用户',
  PRIMARY KEY (`id`),
  KEY `user_id_index` (`user_id`),
  KEY `role_id_index` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='系统消息用户表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sys_permission`
--

DROP TABLE IF EXISTS `sys_permission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键,自增长',
  `ctime` datetime NOT NULL COMMENT '添加时间',
  `utime` datetime DEFAULT NULL COMMENT '更新时间',
  `permission_key` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '菜单标识,前端使用',
  `permission_name` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '菜单名称',
  `p_permission_id` int(11) DEFAULT NULL COMMENT '父级别菜单ID',
  `icon` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'logo字符串',
  `uri` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '菜单uri',
  `permission_type` int(1) NOT NULL COMMENT '1:导航菜单，2:功能模块',
  `sort` int(6) NOT NULL DEFAULT '0' COMMENT '菜单排序',
  `sys_user_id` int(11) DEFAULT NULL COMMENT '操作人员ID',
  `sys_user_name` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '操作人员名称',
  `is_deleted` int(1) DEFAULT '0' COMMENT '是否删除：0未删除，1已删除',
  PRIMARY KEY (`id`),
  KEY `idx_pmenu_id` (`p_permission_id`),
  KEY `idx_sort` (`sort`),
  KEY `idx_ctime` (`ctime`),
  KEY `idx_key` (`permission_key`)
) ENGINE=InnoDB AUTO_INCREMENT=807 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='系统权限(菜单)表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sys_role`
--

DROP TABLE IF EXISTS `sys_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键,自增长',
  `ctime` datetime NOT NULL COMMENT '添加时间',
  `utime` datetime DEFAULT NULL COMMENT '更新时间',
  `role_name` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '角色名称',
  `remark` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注描述',
  `sys_user_id` int(11) DEFAULT NULL COMMENT '操作人员ID',
  `sys_user_name` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '操作人员名称',
  `is_share_data` int(1) NOT NULL DEFAULT '0' COMMENT '是否共享数据,1:共享,共享sys_user_id的数据给拥有此角色的人,0:不共享',
  `share_benefit_type` int(1) DEFAULT '1' COMMENT '分润权限类型，1：无，2：入账，3：收款',
  `is_share_service_mode` int(1) DEFAULT '0' COMMENT '是否共享收费模块数据：0否 1是',
  `is_add_more_service_mode` int(1) DEFAULT '0' COMMENT '设备是否能添加多个收费模式：0否 1是',
  `is_deleted` int(1) DEFAULT '0' COMMENT '是否删除：0未删除，1已删除',
  PRIMARY KEY (`id`),
  KEY `idx_name` (`role_name`),
  KEY `idx_ctime` (`ctime`)
) ENGINE=InnoDB AUTO_INCREMENT=141 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='系统角色表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sys_role_to_permission`
--

DROP TABLE IF EXISTS `sys_role_to_permission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_role_to_permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键,自增长',
  `ctime` datetime NOT NULL COMMENT '添加时间',
  `utime` datetime DEFAULT NULL COMMENT '更新时间',
  `role_id` int(11) NOT NULL COMMENT '角色ID',
  `permission_id` int(11) NOT NULL COMMENT '权限(菜单)ID',
  `sys_user_id` int(11) DEFAULT NULL COMMENT '操作人员ID',
  `sys_user_name` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '操作人员名称',
  `is_deleted` int(1) DEFAULT '0' COMMENT '是否删除：0未删除，1已删除',
  PRIMARY KEY (`id`),
  KEY `idx_ctime` (`ctime`),
  KEY `idx_role_menu` (`role_id`,`permission_id`)
) ENGINE=InnoDB AUTO_INCREMENT=34629 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='角色权限关系表(多对多)';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sys_role_to_version`
--

DROP TABLE IF EXISTS `sys_role_to_version`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_role_to_version` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) NOT NULL COMMENT '角色ID',
  `version_id` int(11) NOT NULL COMMENT '版本ID',
  `sys_user_id` int(11) DEFAULT NULL COMMENT '操作人员ID',
  `sys_user_name` varchar(50) DEFAULT NULL COMMENT '操作人员名称',
  `ctime` datetime DEFAULT CURRENT_TIMESTAMP,
  `utime` datetime DEFAULT CURRENT_TIMESTAMP,
  `is_deleted` int(1) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `idx_role_version` (`role_id`,`version_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2911 DEFAULT CHARSET=utf8mb4 COMMENT='角色对应的系统版本';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sys_user`
--

DROP TABLE IF EXISTS `sys_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键,自增长',
  `ctime` datetime NOT NULL COMMENT '添加时间',
  `utime` datetime DEFAULT NULL COMMENT '更新时间',
  `username` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户名',
  `password` varchar(128) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '密码',
  `nick_name` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '昵称',
  `real_name` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '真实姓名',
  `gender` varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '性别',
  `avatar` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '头像地址',
  `birthday` datetime DEFAULT NULL COMMENT '生日',
  `mobile` varchar(11) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '手机',
  `address` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '地址',
  `email` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '邮件地址',
  `remark` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
  `sys_user_id` int(11) DEFAULT NULL COMMENT '操作人员ID',
  `sys_user_name` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '操作人员名称',
  `is_enable` int(1) NOT NULL DEFAULT '1' COMMENT '启用标识，0：禁用，1：启用',
  `tree_path` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '用户路径，比如,0,2,5,',
  `code` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '验证码',
  `is_admin` int(1) NOT NULL DEFAULT '0' COMMENT '是否是管理员，厂商，代理商，运营商的直接系统用户 0否，1是',
  `parent_admin_id` int(11) DEFAULT NULL COMMENT '上一层级管理员Id',
  `sys_name` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '系统名称',
  `sys_logo` text COLLATE utf8mb4_unicode_ci COMMENT '系统图标',
  `is_deleted` int(1) DEFAULT '0' COMMENT '是否删除：0未删除 1已删除',
  PRIMARY KEY (`id`),
  KEY `idx_user_pass` (`username`,`password`),
  KEY `idx_nickname` (`nick_name`),
  KEY `idx_ctime` (`ctime`),
  KEY `idx_sys_user_id` (`sys_user_id`),
  KEY `idx_is_admin` (`is_admin`,`parent_admin_id`)
) ENGINE=InnoDB AUTO_INCREMENT=596 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='系统用户表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sys_user_ext`
--

DROP TABLE IF EXISTS `sys_user_ext`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_user_ext` (
  `sys_user_id` int(11) NOT NULL COMMENT '系统用户ID',
  `sys_user_name` varchar(45) CHARACTER SET utf8mb4 DEFAULT NULL,
  `ctime` datetime NOT NULL COMMENT '添加时间',
  `utime` datetime DEFAULT NULL COMMENT '更新时间',
  `wx_appid` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '微信appid,必须加密之后存储,分润时候使用',
  `wx_partner_id` varchar(100) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '微信支付商户号, 必须加密之后存储,分润时候使用',
  `wx_app_secret` varchar(100) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '微信支付key,必须加密之后存储,分润时候使用',
  `wx_partner_secret` varchar(100) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '微信支付时,必须加密之后存储,分润时候使用',
  `wx_id` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '微信id',
  `wx_token` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `wx_pay_body` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `wx_subscribe_msg` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `wx_template_id` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '模版id(用于给维护人员推送消息)',
  `wx_open_id` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'openId(用于定位维护人员的微信，推送信息)',
  `receiver_open_id` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '收款人openId',
  `receiver_wx_name` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '收款人微信实名',
  `alipay_appid` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '支付宝生活号应用ID',
  `alipay_redirect_url` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '生活号授权回调地址',
  `alipay_public_key` varchar(3000) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '支付宝公钥',
  `alipay_private_key` varchar(3000) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用户RSA2私钥',
  `alipay_notify_url` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '支付宝支付异步回调地址',
  `alipay_return_url` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '支付宝支付完成跳转页面地址',
  `alipay_self_public_key` varchar(2000) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '支付宝生成公钥',
  `alipay_partner` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '支付宝合作身份者ID',
  `alipay_account` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '支付宝账号(支持邮箱和手机号2种格式)',
  `alipay_account_name` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '支付宝账户名(即支付宝账号的人的姓名)',
  `qrcode_host` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用于生成设备二维码，对应二维码内容中的域名',
  `wx_cert_path` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '微信支付证书路径',
  `wx_mini_qrcode` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '微信小程序二维码',
  `is_deleted` int(1) DEFAULT '0' COMMENT '是否删除：0未删除，1已删除',
  `wx_frontend_path` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '微信公众号前端相对路径',
  `wx_app_cert_path` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '微信app支付证书路径',
  `wx_pay_app_id` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '微信app支付应用ID',
  `wx_pay_app_secret` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '微信app支付秘钥',
  PRIMARY KEY (`sys_user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='系统用户扩展表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sys_user_share_data`
--

DROP TABLE IF EXISTS `sys_user_share_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_user_share_data` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sys_user_id` int(11) NOT NULL COMMENT '系统用户ID',
  `share_data` varchar(100) NOT NULL COMMENT '共享给下级的数据',
  `ctime` datetime NOT NULL COMMENT '添加时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `sys_user_id` (`sys_user_id`,`share_data`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统用户共享数据表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sys_user_to_role`
--

DROP TABLE IF EXISTS `sys_user_to_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_user_to_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键,自增长',
  `ctime` datetime NOT NULL COMMENT '添加时间',
  `utime` datetime DEFAULT NULL COMMENT '更新时间',
  `user_id` int(11) NOT NULL COMMENT '用户ID',
  `role_id` int(11) NOT NULL COMMENT '角色ID',
  `sys_user_id` int(11) DEFAULT NULL COMMENT '操作人员ID',
  `sys_user_name` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '操作人员名称',
  `is_deleted` int(1) DEFAULT '0' COMMENT '是否删除：0未删除，1已删除',
  PRIMARY KEY (`id`),
  KEY `idx_role_menu` (`user_id`,`role_id`),
  KEY `idx_ctime` (`ctime`)
) ENGINE=InnoDB AUTO_INCREMENT=631 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户角色关系表(多对多)';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sys_version`
--

DROP TABLE IF EXISTS `sys_version`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_version` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `version_name` varchar(50) DEFAULT NULL COMMENT '版本名',
  `version_code` varchar(50) DEFAULT NULL COMMENT '版本标识',
  `p_permission_id` int(11) DEFAULT NULL COMMENT '对应的权限',
  `sort` int(6) DEFAULT NULL COMMENT '排序',
  `sys_user_id` int(11) DEFAULT NULL COMMENT '操作人员ID',
  `sys_user_name` varchar(50) DEFAULT NULL COMMENT '操作人员名称',
  `ctime` datetime DEFAULT CURRENT_TIMESTAMP,
  `utime` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `idx_p_id` (`p_permission_id`)
) ENGINE=InnoDB AUTO_INCREMENT=690 DEFAULT CHARSET=utf8mb4 COMMENT='系统版本';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `trade_alipay`
--

DROP TABLE IF EXISTS `trade_alipay`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `trade_alipay` (
  `trade_no` varchar(31) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '主键，与trade_base表关联',
  `alipay_id` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '支付宝单号',
  `appid` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '支付宝应用APPID',
  `seller_id` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '收款支付宝账号对应的支付宝唯一用户号',
  `subject` varchar(256) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '订单标题',
  `trade_status` varchar(30) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '交易状态：交易完成TRADE_FINISHED  交易成功TRADE_SUCCESS',
  PRIMARY KEY (`trade_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='支付宝交易表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `trade_base`
--

DROP TABLE IF EXISTS `trade_base`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `trade_base` (
  `trade_no` varchar(31) NOT NULL COMMENT '交易号，主键，按照一定规则生成',
  `ctime` datetime NOT NULL COMMENT '创建时间',
  `utime` datetime DEFAULT NULL COMMENT '更改时间',
  `status` int(1) NOT NULL COMMENT '交易状态，1交易创建，2交易成功，3交易失败',
  `total_fee` double(12,2) NOT NULL COMMENT '交易金额，单位为分',
  `order_no` varchar(20) NOT NULL COMMENT '业务订单号，',
  `order_type` int(2) NOT NULL COMMENT '1用户消费订单，2分润订单，3充值订单',
  `notify_url` varchar(300) DEFAULT NULL COMMENT '发起交易时传递的回调URL',
  `nofify_time` datetime DEFAULT NULL COMMENT '交易回调时间',
  `trade_type` int(1) NOT NULL DEFAULT '1' COMMENT '交易类型：1:微信公众号，2app微信，3支付宝，4充值卡，5余额，6微信支付',
  PRIMARY KEY (`trade_no`),
  KEY `idx_order_no` (`order_no`),
  KEY `index_order_no` (`order_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `trade_weixin`
--

DROP TABLE IF EXISTS `trade_weixin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `trade_weixin` (
  `trade_no` varchar(31) NOT NULL COMMENT '主键，与trade_base表关联',
  `transaction_id` varchar(64) DEFAULT NULL COMMENT '微信单号',
  `appid` varchar(64) DEFAULT NULL COMMENT '微信开放平台审核通过的应用APPID',
  `mch_id` varchar(64) DEFAULT NULL COMMENT '微信支付分配的商户号',
  `body` varchar(256) DEFAULT NULL COMMENT '商品描述',
  `time_end` varchar(16) DEFAULT NULL COMMENT '微信端,支付完成时间',
  PRIMARY KEY (`trade_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键,',
  `username` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户名',
  `ctime` datetime NOT NULL COMMENT '添加时间',
  `utime` datetime DEFAULT NULL COMMENT '更新时间',
  `nickname` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '别名',
  `openid` varchar(128) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '微信用户的unionId',
  `third_party` int(2) DEFAULT NULL COMMENT '第三方平台,1:微信 2:支付宝 3:百度 4:新浪',
  `password` varchar(64) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '密码',
  `mobile` varchar(11) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '手机,绑定时程序上控制唯一',
  `email` varchar(128) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '电子邮件,绑定时程序上控制唯一',
  `gender` varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '性别',
  `avatar` varchar(300) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '头像地址',
  `birthday` datetime DEFAULT NULL COMMENT '生日',
  `province` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '所属省份',
  `city` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '所属城市',
  `address` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '详细地址',
  `remark` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
  `sys_user_id` int(11) DEFAULT NULL COMMENT '微信用户所属运营商用户ID',
  `status` int(2) NOT NULL DEFAULT '1' COMMENT '用户状态,1:正常 2:黑名单 ',
  `move_in_black_time` datetime DEFAULT NULL COMMENT '移入黑名单时间',
  `move_out_black_time` datetime DEFAULT NULL COMMENT '移出黑名单时间',
  `authorization_time` datetime DEFAULT NULL COMMENT '授权时间',
  `alipay_unionid` varchar(128) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '支付宝用户ID',
  `sina_unionid` varchar(128) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '微博用户ID',
  `baidu_unionid` varchar(128) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '百度用户ID',
  `code` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '验证码',
  `info_state` int(11) DEFAULT NULL COMMENT '用户信息状态 1:由用户编辑的用户信息 2:从第三方获取的用户信息',
  `last_login_time` datetime NOT NULL COMMENT '最后一次登录时间',
  `is_deleted` int(1) DEFAULT '0' COMMENT '是否删除：0未删除 1已删除',
  `tencent_unionid` varchar(128) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '腾讯用户ID',
  `tencent_nickname` varchar(128) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '腾讯用户昵称',
  `wx_nickname` varchar(128) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '微信用户昵称',
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_username` (`username`,`id`),
  KEY `index_openId` (`openid`),
  KEY `index_mobile` (`mobile`),
  KEY `index_sys_user_id` (`sys_user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=145675 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户表,不要前缀,因为用户模块计划抽象成通用功能';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_bind_device`
--

DROP TABLE IF EXISTS `user_bind_device`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_bind_device` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键,自增长',
  `ctime` datetime DEFAULT NULL COMMENT '添加时间',
  `utime` datetime DEFAULT NULL COMMENT '更新时间',
  `sno` varchar(25) DEFAULT NULL COMMENT '对应设备序列号',
  `mac` varchar(25) DEFAULT NULL COMMENT '对应设备MAC',
  `account_num` varchar(25) DEFAULT NULL COMMENT '用户账号',
  `user_id` int(11) DEFAULT NULL COMMENT '所属用户id',
  `is_deleted` int(11) DEFAULT NULL COMMENT '是否删除：0未删除 1已删除',
  `is_manage` int(11) DEFAULT NULL COMMENT '是否是管理员：0不是 1是',
  `mobile` int(6) DEFAULT NULL COMMENT '手机号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_card`
--

DROP TABLE IF EXISTS `user_card`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_card` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL COMMENT '用户ID',
  `card_id` varchar(32) NOT NULL COMMENT '卡券ID',
  `code` varchar(32) NOT NULL COMMENT '卡券code',
  `receive_channel` int(11) NOT NULL COMMENT '领取渠道 1:微信 2:app',
  `status` int(11) NOT NULL COMMENT '卡券状态 1:正常 2:已使用 3:已失效',
  `received_time` datetime DEFAULT NULL COMMENT '卡券领取时间',
  `consumed_time` datetime DEFAULT NULL COMMENT '卡券使用时间',
  `begin_time` datetime DEFAULT NULL COMMENT '卡券生效时间',
  `end_time` datetime DEFAULT NULL COMMENT '卡券过期时间',
  `ctime` datetime NOT NULL COMMENT '数据创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `card_id` (`card_id`,`code`),
  KEY `user_id` (`user_id`,`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户的卡券';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_charge_card`
--

DROP TABLE IF EXISTS `user_charge_card`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_charge_card` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `ctime` datetime NOT NULL COMMENT '创建时间',
  `utime` datetime DEFAULT NULL COMMENT '更新时间',
  `card_num` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '充值卡号',
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `user_name` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用户名称',
  `mobile` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '手机号',
  `money` double(12,2) NOT NULL DEFAULT '0.00' COMMENT '卡内余额',
  `status` int(11) NOT NULL DEFAULT '1' COMMENT '充值卡状态，1,使用中 2,暂停',
  `bind_card_time` datetime DEFAULT NULL COMMENT '绑定时间',
  `is_bind_wx` int(11) NOT NULL DEFAULT '0' COMMENT '是否绑定微信，0,未绑定 1,已绑定',
  `sys_user_id` int(11) DEFAULT NULL COMMENT '创建人id',
  PRIMARY KEY (`id`),
  KEY `index_card_num` (`card_num`,`user_name`,`mobile`),
  KEY `index_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='充值卡';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_charge_card_operation_record`
--

DROP TABLE IF EXISTS `user_charge_card_operation_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_charge_card_operation_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `ctime` datetime NOT NULL COMMENT '创建时间',
  `card_num` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '充值卡号',
  `operate_type` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '操作类型,ENABLE和DISABLE',
  `remark` varchar(256) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '操作说明',
  `ip` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '访问ip',
  `sys_user_id` int(11) NOT NULL COMMENT '创建人',
  `sys_user_name` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '创建人名称',
  PRIMARY KEY (`id`),
  KEY `index_card_num` (`card_num`),
  KEY `index_sys_user_id` (`sys_user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='充值卡操作记录(启用/禁用)';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_charge_card_order`
--

DROP TABLE IF EXISTS `user_charge_card_order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_charge_card_order` (
  `order_no` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '充值单号',
  `ctime` datetime NOT NULL COMMENT '创建时间',
  `utime` datetime DEFAULT NULL COMMENT '更新时间',
  `pay_time` datetime DEFAULT NULL COMMENT '支付时间',
  `card_num` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '充值卡号',
  `money` double(12,2) NOT NULL DEFAULT '0.00' COMMENT '充值金额',
  `pay_type` int(11) NOT NULL COMMENT '支付方式',
  `status` int(11) NOT NULL COMMENT '充值订单状态',
  `username` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '充值人姓名',
  `user_id` int(11) DEFAULT NULL COMMENT '充值人id',
  PRIMARY KEY (`order_no`),
  KEY `index_card_num` (`card_num`,`pay_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='充值卡充值订单';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_device`
--

DROP TABLE IF EXISTS `user_device`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_device` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键,自增长',
  `ctime` datetime NOT NULL COMMENT '添加时间',
  `utime` datetime DEFAULT NULL COMMENT '更新时间',
  `sno` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '对应设备序列号',
  `mac` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '对应设备MAC',
  `wechat_device_id` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '微信设备ID',
  `openid` varchar(128) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'openId',
  `user_id` int(11) NOT NULL COMMENT '所属用户id',
  `is_bind` int(1) DEFAULT '1' COMMENT '是否绑定',
  `owner_id` int(11) DEFAULT NULL COMMENT '拥有者经销商或者运营商',
  `is_deleted` int(1) DEFAULT '0' COMMENT '是否删除：0:未删除 1：删除',
  `mobile` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `is_manager` int(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `index_sno` (`sno`),
  KEY `index_openId` (`openid`)
) ENGINE=InnoDB AUTO_INCREMENT=78548 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户绑定设备表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_move_record`
--

DROP TABLE IF EXISTS `user_move_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_move_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `ctime` datetime NOT NULL COMMENT '创建时间',
  `user_id` int(11) NOT NULL COMMENT '移入/出的用户',
  `move_type` int(11) NOT NULL COMMENT '移动类型，1:移入黑名单 2:移出黑名单',
  `sys_user_id` int(11) NOT NULL COMMENT '操作人员',
  `sys_user_name` varchar(30) DEFAULT NULL COMMENT '操作人员名称',
  PRIMARY KEY (`id`),
  KEY `index_user_id` (`user_id`),
  KEY `index_sys_user_id` (`sys_user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='用户移动记录（移入/出黑名单）';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_wallet`
--

DROP TABLE IF EXISTS `user_wallet`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_wallet` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键,自增长',
  `ctime` datetime NOT NULL COMMENT '添加时间',
  `utime` datetime DEFAULT NULL COMMENT '更新时间',
  `wallet_type` int(1) NOT NULL COMMENT '钱包类型，1，余额，2，押金, 3,赠送',
  `wallet_name` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '钱包名称,枚举中获取值',
  `money` double(12,2) NOT NULL DEFAULT '0.00' COMMENT '钱数',
  `username` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '所属用户',
  `sys_user_id` int(11) DEFAULT NULL COMMENT '用户所属的运营商，暂时只针对“押金”类型',
  `user_id` int(11) DEFAULT NULL COMMENT ' 用户id',
  PRIMARY KEY (`id`),
  KEY `idx_username` (`username`,`wallet_type`),
  KEY `index_wallet_type` (`wallet_type`)
) ENGINE=InnoDB AUTO_INCREMENT=47 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户钱包表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_wallet_charge_order`
--

DROP TABLE IF EXISTS `user_wallet_charge_order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_wallet_charge_order` (
  `charge_order_no` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '主键,充值单号',
  `ctime` datetime NOT NULL COMMENT '添加时间',
  `utime` datetime DEFAULT NULL COMMENT '更新时间',
  `wallet_type` int(1) NOT NULL COMMENT '钱包ID',
  `fee` double(12,2) NOT NULL COMMENT '操作金额',
  `balance` double(12,2) NOT NULL DEFAULT '0.00' COMMENT ' 余额',
  `status` int(1) NOT NULL COMMENT '充值状态：1：创建，2：支付中，3：支付完成，4：支付失败，5：订单完成，6：订单失败',
  `username` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '所属用户',
  `wallet_name` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `pay_time` datetime DEFAULT NULL,
  `pay_type` int(2) DEFAULT NULL,
  `discount_money` double(12,2) DEFAULT '0.00' COMMENT '用户的充值赠送金额,此部分的金额说明money中有多少金额是优惠金额',
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  PRIMARY KEY (`charge_order_no`),
  KEY `idx_username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户钱包充值单表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_wallet_use_record`
--

DROP TABLE IF EXISTS `user_wallet_use_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_wallet_use_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键,自增长',
  `trade_no` varchar(31) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '交易号',
  `ctime` datetime NOT NULL COMMENT '添加时间',
  `utime` datetime DEFAULT NULL COMMENT '更新时间',
  `wallet_type` int(1) NOT NULL COMMENT '钱包类型，1，余额，2，押金',
  `wallet_name` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '钱包名称,枚举中获取值',
  `fee` double(12,2) NOT NULL COMMENT '操作金额',
  `balance` double(12,2) NOT NULL DEFAULT '0.00' COMMENT ' 余额',
  `operation_type` int(1) NOT NULL COMMENT '操作类型：1,充值，2,消费',
  `username` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '所属用户',
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  PRIMARY KEY (`id`),
  KEY `idx_username` (`username`),
  KEY `index_trade_no` (`trade_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户钱包操作记录表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_wx_ext`
--

DROP TABLE IF EXISTS `user_wx_ext`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_wx_ext` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_openid` varchar(60) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '微信用户的unionid，对应user表的openid',
  `openid` varchar(60) CHARACTER SET utf8mb4 NOT NULL COMMENT '微信用户openid，每个用户对一个公众号生成一个openid',
  `wx_id` varchar(45) CHARACTER SET utf8mb4 NOT NULL COMMENT '微信公众号ID，是微信公众号的唯一标识',
  `sys_user_id` int(11) DEFAULT NULL COMMENT '微信用户所属运营用户ID',
  `ctime` datetime NOT NULL,
  `utime` datetime DEFAULT NULL,
  `status` int(1) NOT NULL DEFAULT '1' COMMENT '用户状态: 1,正常; 2,黑名单',
  `move_in_black_time` datetime DEFAULT NULL COMMENT '移入黑名单时间',
  `move_out_black_time` datetime DEFAULT NULL COMMENT '移出黑名单时间',
  `authorization_time` datetime DEFAULT NULL COMMENT '授权时间',
  `is_deleted` int(1) DEFAULT '0' COMMENT '是否删除：0未删除 1已删除',
  PRIMARY KEY (`id`),
  KEY `index_user_openId` (`user_openid`),
  KEY `index3_openId` (`openid`),
  KEY `index_wxId` (`wx_id`)
) ENGINE=InnoDB AUTO_INCREMENT=133271 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='微信用户信息';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `work_order`
--

DROP TABLE IF EXISTS `work_order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `work_order` (
  `id` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '工单id',
  `status` tinyint(3) NOT NULL COMMENT '工单状态：1待受理，2受理中，3已完成，4已关闭',
  `mac` text COLLATE utf8mb4_unicode_ci COMMENT '设备mac地址',
  `launch_area_id` int(11) DEFAULT NULL COMMENT '投放点id',
  `launch_area_name` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '投放点名字',
  `type` tinyint(3) NOT NULL COMMENT '工单类型：1装机，2维护，3拆换',
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  `user_name` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用户姓名',
  `user_mobile` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用户手机',
  `address` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '地址',
  `maintainer_id` int(11) DEFAULT NULL COMMENT '维护人员id',
  `maintainer_name` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT ' 维护人员姓名',
  `maintainer_mobile` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT ' 维护人员电话',
  `description` text COLLATE utf8mb4_unicode_ci COMMENT '工单描述',
  `source` tinyint(3) NOT NULL COMMENT '来源：1手工新建，2转化，3App上报',
  `creator_id` int(11) NOT NULL COMMENT '创建人id',
  `creator_name` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '创建人名称',
  `ctime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `utime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `complete_time` datetime DEFAULT NULL COMMENT '完成时间',
  `del` bit(1) DEFAULT b'0' COMMENT '是否删除',
  `handler_id` int(11) DEFAULT NULL COMMENT '处理人id',
  `handler_name` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '处理人名称',
  `picture_url` text COLLATE utf8mb4_unicode_ci COMMENT '照片url',
  `work_date` date DEFAULT NULL COMMENT '上门日期',
  `work_start_time` time DEFAULT NULL COMMENT '上门时段开始时间',
  `work_end_time` time DEFAULT NULL COMMENT '上门时段结束时间',
  `user_description` text COLLATE utf8mb4_unicode_ci COMMENT '用户描述',
  PRIMARY KEY (`id`),
  KEY `idx_creator_id` (`creator_id`,`status`,`type`),
  KEY `idx_maintainer_id` (`maintainer_id`,`status`,`type`),
  KEY `idx_handler_id` (`handler_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='工单';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `work_time`
--

DROP TABLE IF EXISTS `work_time`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `work_time` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `start_time` time NOT NULL COMMENT '开始时间',
  `end_time` time NOT NULL COMMENT '结束时间',
  `sys_user_id` int(11) NOT NULL COMMENT '创建人id',
  `ctime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `utime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `del` bit(1) DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`),
  KEY `idx_sys_user_id` (`sys_user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='上门时段';
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-09-16 17:30:23
