# jenkins安装配置



## 一、下载

````
wget -P /home/jenkins http://mirrors.jenkins.io/war-stable/latest/jenkins.war && \

firewall-cmd --zone=public --add-port=8080/tcp --permanent && \

systemctl reload firewalld && \

nohup java -jar /home/jenkins/jenkins.war &

````

当控制台输出带“nohup.out”字样时可按ctrl+c退出，然后执行如下执行

```
vi /home/jenkins/nohup.out
```

找到文件中如下字样

```
信息: Pre-instantiating singletons in org.springframework.beans.factory.support.DefaultListableBeanFactory@24f2b2ff: defining beans [filter,legacy]; root of factory hierarchy
七月 15, 2017 9:11:15 上午 jenkins.install.SetupWizard init
信息:

*************************************************************
*************************************************************
*************************************************************

Jenkins initial setup is required. An admin user has been created and a password generated.
Please use the following password to proceed to installation:

9a8e9681d8f74a5894be57d618c6878b

This may also be found at: /root/.jenkins/secrets/initialAdminPassword

*************************************************************
*************************************************************
*************************************************************

七月 15, 2017 9:11:24 上午 hudson.model.UpdateSite updateData
信息: Obtained the latest update center data file for UpdateSource default
七月 15, 2017 9:11:26 上午 hudson.model.UpdateSite updateData
信息: Obtained the latest update center data file for UpdateSource default
七月 15, 2017 9:11:27 上午 hudson.WebAppMain$3 run
信息: Jenkins is fully up and running
七月 15, 2017 9:11:27 上午 hudson.model.DownloadService$Do1234567891011121314151617181920212223242526
```

将其中的密钥（9a8e9681d8f74a5894be57d618c6878b）复制出来，在浏览器运行<http://192.168.0.200:8080/> ，第一次登陆要求输入密钥，将刚刚复制的密钥复制进去，然后根据资料填写相关信息，插件安装则选择默认安装。 
等待一段时间安装完毕后再登陆进去。



## 二、配置ssl

[为Jenkins增加ssl]: https://www.cnblogs.com/EasonJim/p/6648552.html

https://www.cnblogs.com/EasonJim/p/6648552.html



jenkins启动会报错 ssl异常

````
cd $JAVA_HOME/bin
````

1. 在服务器上创建一个新的密钥库。这将在当前目录中放置一个“keystore”文件。 

````
keytool -genkeypair -keysize 2048 -keyalg RSA -alias jenkins -keystore keystore
````

2. 验证密钥库是否已创建（您的指纹会有所不同） 

````
keytool -list -keystore keystore
````

3. 创建证书请求。这将在当前目录中创建一个“certreq.csr”文件。 

````
keytool -certreq -alias jenkins -keyalg RSA -file certreq.csr -keystore keystore
````



启动命令

````
nohup java -jar /home/jenkins/jenkins.war  --httpsKeyStore=/usr/local/jdk/jdk1.8.0_91/bin/keystore --httpsKeyStorePassword=123456 &
````



## 三、修改镜像

打开Jenkins插件管理，可选插件为空，无法选择自己需要的插件进行下载。

#### 解决方案

打开插件管理的“高级”选项，在升级站点填写 
[URLhttp://mirror.xmission.com/jenkins/updates/update-center.json](http://mirror.xmission.com/jenkins/updates/update-center.json) 
然后点击“立即获取“。





或者



可能是由于Jenkins的更新网站被QIANG，因此，请使用

  http://mirror.xmission.com/jenkins/updates/update-center.json 来进行更新 