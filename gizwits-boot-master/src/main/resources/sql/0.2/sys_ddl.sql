DROP TABLE IF EXISTS `sys_user_ext`;
CREATE TABLE `sys_user_ext` (
  `sys_user_id` int(11) NOT NULL COMMENT '系统用户ID',
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
  `sys_user_name` varchar(45) CHARACTER SET utf8mb4 DEFAULT NULL,
  PRIMARY KEY (`sys_user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='系统用户扩展表'

-- 修改sys_user表
ALTER TABLE sys_user ADD is_enable int(1) DEFAULT 1 COMMENT '启用标识，0：禁用，1：启用';
ALTER TABLE sys_user ADD tree_path VARCHAR(255) DEFAULT '' COMMENT '用户路径，比如,0,2,5,';