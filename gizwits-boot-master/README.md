# 打包流程

1. checkout release 分支，更新 build.properties 文件中的版本号；
2. merge 需要的 features 分支到 release 分支；
3. 提交并 push release 分支；

# 注意事项

* 每次打包完成后会自动上传至 https://archiva.gizwits.com/repository/internal/com/gizwits/gizwits-boot/
* 会在 Gitlab 上用指定的版本号打 Tag
