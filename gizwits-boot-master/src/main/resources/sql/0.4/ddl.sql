ALTER TABLE `sys_user_ext`
ADD COLUMN `alipay_appid` VARCHAR(50) NULL COMMENT '支付宝生活号应用ID' ,
ADD COLUMN `alipay_redirect_url` VARCHAR(200) NULL COMMENT '生活号授权回调地址' ,
ADD COLUMN `alipay_public_key` VARCHAR(3000) NULL COMMENT '支付宝公钥' ,
ADD COLUMN `alipay_private_key` VARCHAR(3000) NULL COMMENT '用户RSA2私钥' ,
ADD COLUMN `alipay_notify_url` VARCHAR(200) NULL COMMENT '支付宝支付异步回调地址' ,
ADD COLUMN `alipay_return_url` VARCHAR(200) NULL COMMENT '支付宝支付完成跳转页面地址' ;
ADD COLUMN `alipay_self_public_key` VARCHAR(2000) NULL COMMENT '支付宝生成公钥' ;

ALTER TABLE `sys_user_ext`
ADD COLUMN `alipay_partner` VARCHAR(100) NULL COMMENT '支付宝合作身份者ID' AFTER `alipay_self_public_key`;
ALTER TABLE `sys_user_ext`
ADD COLUMN `alipay_account` VARCHAR(100) NULL COMMENT '支付宝账号(支持邮箱和手机号2种格式)' AFTER `alipay_partner`,
ADD COLUMN `alipay_account_name` VARCHAR(100) NULL COMMENT '支付宝账户名(即支付宝账号的人的姓名)' AFTER `alipay_account`;
