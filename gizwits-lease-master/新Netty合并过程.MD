###Netty代码合并步骤：
#####1、netty模组中，pom.xml依赖部分，将groupId为io.netty的依赖，替换成以下内容：
    <dependency>
        <groupId>com.gizwits.noti</groupId>
        <artifactId>noti-client</artifactId>
        <version>1.1.1</version>
    </dependency>

#####项目总的pom.xml依赖部分，添加
    <!--需要安装lombok插件并开启annotation-->
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <version>1.16.18</version>
    </dependency>


#####2、删除netty模组中所有java代码
（自己备份与release分支不同逻辑的业务处理方法）

#####3、将releas分支的netty模组中的所有java代码copy到当前项目里
（如果netty的业务处理部分和产品不一样，或者有定制的地方，请修改PushEventRouter中依赖的Handler里面的内容，例如设备上线通知，在DeviceOnlineHandler里。）

#####4、在idea安装lombok插件

#####5、在netty模组的application.yml中，配置gizwits.noti.enable为true