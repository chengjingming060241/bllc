ALTER TABLE `sys_role`
ADD COLUMN `is_share_service_mode` INT(1) NULL DEFAULT 0 COMMENT '是否共享收费模块数据：0否 1是' AFTER `share_benefit_type`,
ADD COLUMN `is_add_more_service_mode` INT(1) NULL DEFAULT 0 COMMENT '设备是否能添加多个收费模式：0否 1是' AFTER `is_share_service_mode`;

ALTER TABLE `sys_role`
ADD COLUMN `is_deleted` INT(1) NULL DEFAULT 0 COMMENT '是否删除：0未删除 1已删除' AFTER `is_add_more_service_mode`;


ALTER TABLE `sys_user_to_role`
ADD COLUMN `is_deleted` INT(1) NULL DEFAULT 0 COMMENT '是否删除：0未删除 1已删除';

ALTER TABLE `sys_role_to_version`
ADD COLUMN `is_deleted` INT(1) NULL DEFAULT 0 COMMENT '是否删除：0未删除，1已删除' ;

