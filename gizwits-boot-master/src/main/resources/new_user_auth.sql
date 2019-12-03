ALTER TABLE `sys_user`
ADD COLUMN `is_admin` INT(1) NOT NULL DEFAULT 0 COMMENT '是否是管理员，厂商，代理商，运营商的直接系统用户 0否，1是' ,
ADD COLUMN `parent_admin_id` INT(11) NULL COMMENT '上一层级管理员Id'  ;

ALTER TABLE `sys_user`
ADD INDEX `idx_is_admin` (`is_admin` ASC, `parent_admin_id` ASC);

