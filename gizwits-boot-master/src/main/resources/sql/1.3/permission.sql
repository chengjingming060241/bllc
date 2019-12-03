ALTER TABLE `sys_permission`
ADD COLUMN `is_deleted` INT(1) NULL DEFAULT 0 COMMENT '是否删除：0未删除，1已删除' ;


ALTER TABLE `sys_role_to_permission`
ADD COLUMN `is_deleted` INT(1) NULL DEFAULT 0 COMMENT '是否删除：0未删除，1已删除' ;

DROP INDEX idx_key ON sys_permission;
CREATE INDEX idx_key ON sys_permission (permission_key);