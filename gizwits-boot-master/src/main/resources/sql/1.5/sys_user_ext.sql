alter table sys_user_ext add `qrcode_host` varchar(255) DEFAULT null comment '用于生成设备二维码，对应二维码内容中的域名';
alter table sys_user_ext add `wx_cert_path` varchar(255) DEFAULT null comment '微信公众号支付证书路径';
alter table sys_user_ext add `wx_mini_qrcode` varchar(255) DEFAULT null comment '微信小程序二维码';



alter table sys_user_ext add `wx_frontend_path` varchar(255) DEFAULT null comment '微信公众号前端相对路径';
alter table sys_user_ext add `wx_app_cert_path` varchar(255) DEFAULT null comment '微信app支付证书路径';
alter table sys_user_ext add `wx_pay_app_id` varchar(255) DEFAULT null comment '微信app支付应用ID';
alter table sys_user_ext add `wx_pay_app_secret` varchar(255) DEFAULT null comment '微信app支付秘钥';