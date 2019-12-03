alter table sys_permission modify column permission_type int(1) not null comment '1:导航菜单，2:功能模块';

create table sys_version (
  `id` INT(11) AUTO_INCREMENT,
  `version_name` varchar(50) COMMENT '版本名',
  `version_code` varchar(50) COMMENT '版本标识',
  `p_permission_id` int(11) comment '对应的权限',
  `sort` int(6) comment '排序',
	sys_user_id int null comment '操作人员ID',
	sys_user_name varchar(50) null comment '操作人员名称',
  `ctime` datetime default now(),
  `utime` datetime default now(),
  PRIMARY KEY (id),
  KEY `idx_p_id` (p_permission_id)
)COMMENT '系统版本';

create table sys_role_to_version (
  `id` INT(11) AUTO_INCREMENT,
  role_id int not null comment '角色ID',
  version_id int not null comment '版本ID',
  sys_user_id int null comment '操作人员ID',
  sys_user_name varchar(50) null comment '操作人员名称',
  `ctime` datetime default now(),
  `utime` datetime default now(),
  PRIMARY KEY (id),
  KEY `idx_role_version` (role_id, version_id)
)COMMENT '角色对应的系统版本';

alter table sys_user add column sys_name VARCHAR (50) DEFAULT null comment '系统名称';
alter table sys_user add column sys_logo text DEFAULT null comment '系统图标';


ALTER TABLE `sys_role_to_version`
ADD COLUMN `is_deleted` INT(1) NULL DEFAULT 0 AFTER `utime`;
ALTER TABLE `sys_user`
ADD COLUMN `is_deleted` INT(1) NULL DEFAULT 0 COMMENT '是否删除：0未删除 1已删除' ;

ALTER TABLE `sys_role`
ADD COLUMN `is_deleted` INT(1) NULL DEFAULT 0 COMMENT '是否删除：0未删除，1已删除' ;

ALTER TABLE `sys_user_to_role`
ADD COLUMN `is_deleted` INT(1) NULL DEFAULT 0 COMMENT '是否删除：0未删除，1已删除' ;

ALTER TABLE `sys_permission`
ADD COLUMN `is_deleted` INT(1) NULL DEFAULT 0 COMMENT '是否删除：0未删除，1已删除' ;

ALTER TABLE `sys_role_to_permission`
ADD COLUMN `is_deleted` INT(1) NULL DEFAULT 0 COMMENT '是否删除：0未删除，1已删除' ;

ALTER TABLE `sys_user_ext`
ADD COLUMN `is_deleted` INT(1) NULL DEFAULT 0 COMMENT '是否删除：0未删除，1已删除' ;

