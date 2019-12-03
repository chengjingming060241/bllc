ALTER TABLE `sys_user`
ADD COLUMN `is_deleted` INT(1) NULL DEFAULT 0 COMMENT '是否删除：0未删除 1已删除' ;

ALTER TABLE `sys_user_ext`
ADD COLUMN `is_deleted` INT(1) NULL DEFAULT 0 COMMENT '是否删除：0未删除，1已删除' ;

ALTER TABLE `sys_user`
DROP INDEX `idx_username` ;

CREATE TABLE `sys_user_share_data` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sys_user_id` int(11) NOT NULL COMMENT '系统用户ID',
  `share_data` varchar(100) NOT NULL COMMENT '共享给下级的数据',
  `ctime` datetime NOT NULL COMMENT '添加时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `sys_user_id` (`sys_user_id`,`share_data`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统用户共享数据表';