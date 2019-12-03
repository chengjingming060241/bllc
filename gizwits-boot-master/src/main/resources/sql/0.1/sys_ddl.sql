-- -----------  start  系统级别表  start -----------------------

CREATE TABLE `sys_config` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键,自增长',
  `ctime` datetime NOT NULL COMMENT '添加时间',
  `utime` datetime DEFAULT NULL COMMENT '更新时间',
  `config_key` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '配置key',
  `config_value` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '配置value',
  `remark` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '描述',
  `status` int(1) NOT NULL DEFAULT '1' COMMENT '0:禁用 1:启用',
  `sys_user_id` int(11) DEFAULT NULL COMMENT '操作人员',
  `sys_user_name` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_config_key_st` (`config_key`,`status`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='系统配置表,用来动态添加常量配置'

CREATE TABLE `sys_permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键,自增长',
  `ctime` datetime NOT NULL COMMENT '添加时间',
  `utime` datetime DEFAULT NULL COMMENT '更新时间',
  `permission_key` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '菜单标识,前端使用',
  `permission_name` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '菜单名称',
  `p_permission_id` int(11) DEFAULT NULL COMMENT '父级别菜单ID',
  `icon` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'logo字符串',
  `uri` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '菜单uri',
  `permission_type` int(1) NOT NULL COMMENT '1:左侧菜单 2:头部菜单 3:表格菜单 4:数据类型权限',
  `sort` int(6) NOT NULL COMMENT '菜单排序',
  `sys_user_id` int(11) DEFAULT NULL COMMENT '操作人员ID',
  `sys_user_name` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '操作人员名称',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_key` (`permission_key`),
  KEY `idx_pmenu_id` (`p_permission_id`),
  KEY `idx_sort` (`sort`),
  KEY `idx_ctime` (`ctime`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='系统权限(菜单)表'


CREATE TABLE `sys_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键,自增长',
  `ctime` datetime NOT NULL COMMENT '添加时间',
  `utime` datetime DEFAULT NULL COMMENT '更新时间',
  `role_name` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '角色名称',
  `remark` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注描述',
  `sys_user_id` int(11) DEFAULT NULL COMMENT '操作人员ID',
  `sys_user_name` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '操作人员名称',
  `is_share_data` int(1) NOT NULL DEFAULT '0' COMMENT '是否共享数据,1:共享,共享sys_user_id的数据给拥有此角色的人,0:不共享',
  PRIMARY KEY (`id`),
  KEY `idx_name` (`role_name`),
  KEY `idx_ctime` (`ctime`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='系统角色表'



CREATE TABLE `sys_role_to_permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键,自增长',
  `ctime` datetime NOT NULL COMMENT '添加时间',
  `utime` datetime DEFAULT NULL COMMENT '更新时间',
  `role_id` int(11) NOT NULL COMMENT '角色ID',
  `permission_id` int(11) NOT NULL COMMENT '权限(菜单)ID',
  `sys_user_id` int(11) DEFAULT NULL COMMENT '操作人员ID',
  `sys_user_name` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '操作人员名称',
  PRIMARY KEY (`id`),
  KEY `idx_ctime` (`ctime`),
  KEY `idx_role_menu` (`role_id`,`permission_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='角色权限关系表(多对多)'



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
  `is_enable` INT(1) DEFAULT 0 NOT NULL COMMENT '启用标识，0：启用，1：禁用',
  `tree_path` VARCHAR(255) DEFAULT ' ' NOT NULL COMMENT '用户路径，比如,0,2,5,',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_username` (`username`),
  KEY `idx_user_pass` (`username`,`password`),
  KEY `idx_nickname` (`nick_name`),
  KEY `idx_ctime` (`ctime`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='系统用户表'

CREATE TABLE `sys_user_to_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键,自增长',
  `ctime` datetime NOT NULL COMMENT '添加时间',
  `utime` datetime DEFAULT NULL COMMENT '更新时间',
  `user_id` int(11) NOT NULL COMMENT '角色ID',
  `role_id` int(11) NOT NULL COMMENT '权限(菜单)ID',
  `sys_user_id` int(11) DEFAULT NULL COMMENT '操作人员ID',
  `sys_user_name` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '操作人员名称',
  PRIMARY KEY (`id`),
  KEY `idx_role_menu` (`user_id`,`role_id`),
  KEY `idx_ctime` (`ctime`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户角色关系表(多对多)'



-- -----------   end 系统级别表 end  ---------------------
