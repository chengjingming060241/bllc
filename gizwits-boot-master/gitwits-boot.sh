#!/bin/bash
# 用图形界面创建多模块web工程感觉略慢，用命令行的方式会十分的快。以下介绍命令行的方式创建一个多模块的Java web工程。


# 1、 创建一个父项目，命令如下：

 mvn archetype:generate -DgroupId=com.jxwifi -DartifactId=jxwifi-yyl -DinteractivMode=false
    命令完成之后发现有了一个jxwif-yyl文件夹，进入这个文件夹，修改pom.xml，把打包类型由jar修改成pom。如果不修改这个参数，后面将无法创建子模块。父项目是没有-DarchetypeArtifactId这个参数的

# 2、在这个文件夹里创建需要的普通maven模块，命令如下：
mvn archetype:generate -DgroupId=com.jxwifi -DartifactId=jxwifi-yyl-common  -DarchetypeArtifactId=maven-archetype-quickstart
-DinteractivMode=false
mvn archetype:generate -DgroupId=com.jxwifi -DartifactId=jxwifi-yyl-model  -DarchetypeArtifactId=maven-archetype-quickstart
-DinteractivMode=false
mvn archetype:generate -DgroupId=com.jxwifi -DartifactId=jxwifi-yyl-dao  -DarchetypeArtifactId=maven-archetype-quickstart
-DinteractivMode=false
mvn archetype:generate -DgroupId=com.jxwifi -DartifactId=jxwifi-yyl-service  -DarchetypeArtifactId=maven-archetype-quickstart
-DinteractivMode=false

#3、Web模块创建命令如下

 mvn archetype:generate -DgroupId=com.jxwifi -DartifactId=jxwifi-yyl-app -DarchetypeArtifactId=maven-archetype-webapp
-DinteractivMode=false

mvn archetype:generate -DgroupId=com.jxwifi -DartifactId=jxwifi-yyl-job  -DarchetypeArtifactId=maven-archetype-webapp
-DinteractivMode=false

mvn archetype:generate -DgroupId=com.jxwifi -DartifactId=jxwifi-yyl- -DarchetypeArtifactId=maven-archetype-webapp
-DinteractivMode=false

mvn archetype:generate -DgroupId=com.jxwifi -DartifactId=jxwifi-yyl-backend -DarchetypeArtifactId=maven-archetype-webapp
-DinteractivMode=false
