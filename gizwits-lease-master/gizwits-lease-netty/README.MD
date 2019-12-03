# NOTI 模块说明

##依赖
1. [机智云SNoti API](http://docs.gizwits.com/zh-cn/Cloud/NotificationAPI.html)
2. [noti-netty-client](https://github.com/Bestfeel/noti-netty-client)

##使用说明
1. 在包 `com.gizwits.lease.ohmynoti.handler`下包含了各种事件的`handler`
2. 在`handler`方法`onApplicationEvent`中实现业务即可

## TODO
- [ ] 服务器主动断开重连
- [ ] 控制设备
- [x] 事件
  - [ ] 数据点改变
  - [ ] 重置
  - [x] 告警
  - [x] 故障
  - [x] 绑定
  - [x] 解绑
  - [x] 下线
  - [x] 上线
  - [x] 数据点
  - [x] 透传
  - [x] 中控添加子设备
  - [x] 中控删除子设备
  
## CHANGE LOG
1. 2017-12-13 初始化noti 模块

## 建议
有任何使用问题或者建议请联系 `jcliu@gizwits.com`
