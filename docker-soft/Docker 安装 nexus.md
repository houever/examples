# Docker 安装 nexus

## 一、docker-compose安装

````yml
version: '3.1'
services:
  nexus:
    restart: always
    image: sonatype/nexus3
    container_name: nexus
    ports:
      - 8081:8081
    volumes:
      - /usr/local/docker/nexus/data:/nexus-data
````



*注：* 启动时如果出现权限问题可以使用：`chmod 777 /usr/local/docker/nexus/data` 赋予数据卷目录可读可写的权限 

测试登录:http://ip:port

用户名：admin 密码：admin123 

## 二、settings.xml配置

请使用`Ctrl+F`

替换所有

`192.168.52.131:8081`

````xml
<?xml version="1.0" encoding="UTF-8"?>

<settings xmlns="http://maven.apache.org/SETTINGS/1.0.0" 
          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" 
          xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.0.0 http://maven.apache.org/xsd/settings-1.0.0.xsd">
	<localRepository>${user.home}/.m2/repository</localRepository>
	<interactiveMode>true</interactiveMode>
    <offline>false</offline>
    <pluginGroups>
        <pluginGroup>org.mortbay.jetty</pluginGroup>
        <pluginGroup>org.jenkins-ci.tools</pluginGroup>
    </pluginGroups>
	
	  <servers> 
  <server>    
      <id>nexus-releases</id>    
      <username>admin</username>    
      <password>admin123</password>    
    </server>    
    <server>    
      <id>nexus-snapshots</id>    
      <username>admin</username>    
      <password>admin123</password>    
    </server>    
  </servers> 
    <mirrors>     
    <mirror>     
      <id>nexus-releases</id>     
      <mirrorOf>*</mirrorOf>     
      <url>http://192.168.52.131:8081/repository/maven-public/</url>     
    </mirror>    
    <mirror>     
      <id>nexus-snapshots</id>     
      <mirrorOf>*</mirrorOf>     
      <url>http://192.168.52.131:8081/repository/maven-snapshots/</url>     
    </mirror>     
  </mirrors> 
    <profiles>    
   <profile>    
      <id>nexus</id>    
      <repositories>    
        <repository>    
          <id>nexus-releases</id>    
          <url>http://nexus-releases</url>    
          <releases><enabled>true</enabled></releases>    
          <snapshots><enabled>true</enabled></snapshots>    
        </repository>    
        <repository>    
          <id>nexus-snapshots</id>    
          <url>http://nexus-snapshots</url>    
          <releases><enabled>true</enabled></releases>    
          <snapshots><enabled>true</enabled></snapshots>    
        </repository>    
      </repositories>    
      <pluginRepositories>    
         <pluginRepository>    
                <id>nexus-releases</id>    
                 <url>http://nexus-releases</url>    
                 <releases><enabled>true</enabled></releases>    
                 <snapshots><enabled>true</enabled></snapshots>    
               </pluginRepository>    
               <pluginRepository>    
                 <id>nexus-snapshots</id>    
                  <url>http://nexus-snapshots</url>    
                <releases><enabled>true</enabled></releases>    
                 <snapshots><enabled>true</enabled></snapshots>    
             </pluginRepository>    
         </pluginRepositories>    
    </profile>    
  </profiles>    
    <activeProfiles>    
      <activeProfile>nexus</activeProfile>    
  </activeProfiles>    
</settings>
````

## 三、上传jar包到nexus

### 1、第一种方式：

````shell
mvn deploy:deploy-file -DgroupId=com.alibaba -DartifactId=dubbo -Dversion=2.8.4 -Dpackaging=jar -Dfile=/Users/zhangyong/Documents/software/dubbo-2.8.4.jar -Durl=http://192.168.52.131:8081/repository/maven-releases/ -DrepositoryId=nexus-releases
````

DrepositoryId和settings.xml里配置的id一样

### 2、第二种方式代码的pom.xml中直接接入

````xml
<distributionManagement>  
        <repository>  
            <id>nexus-releases</id>  
            <name>maven-releases</name>  
           <url>http://192.168.52.131:8081/repository/maven-releases/</url>  
        </repository>  
</distributionManagement> 
````

````shell
mvn deploy
````

点击maven-central

更换中央仓库的代理资源路径为阿里云的中央仓库

Proxy

Remote storage:Location of the remote repository being proxied

`http://maven.aliyun.com/nexus/content/repositories/central/`