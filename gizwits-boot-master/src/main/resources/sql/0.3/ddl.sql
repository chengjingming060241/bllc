ALTER TABLE gizwits_lease.sys_role ADD share_benefit_type INT(1) DEFAULT 1 NULL COMMENT '分润权限类型，1：无，2：入账，3：收款';
ALTER TABLE gizwits_lease.sys_user_ext ADD receiver_open_id VARCHAR(45) NULL COMMENT '收款人openId';
ALTER TABLE gizwits_lease.sys_user_ext ADD receiver_wx_name VARCHAR(45) NULL COMMENT '收款人微信实名';
ALTER TABLE `gizwits_lease`.`sys_user`
ADD COLUMN `code` VARCHAR(45) NULL COMMENT '验证码' AFTER `tree_path`;