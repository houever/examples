# docker安装配置

## 一、安装docker

​	升级系统

````shell
yum  update
````

# [centos7 安装docker-ce ，最新版本docker，docker阿里云加速](https://www.cnblogs.com/fyc119/p/7499931.html)

直接用yum install docker -y安装的docker版本为1.12，但是docker发展很快，现在都17.06.2了。docker-ce是指docker的社区版

卸载老版本的 docker 及其相关依赖

```
sudo yum remove docker docker-common container-selinux docker-selinux docker-engine
```

安装 yum-utils，它提供了 yum-config-manager，可用来管理yum源

```
sudo yum install -y yum-utils
```

添加yum源

```
sudo yum-config-manager --add-repo https://download.docker.com/linux/centos/docker-ce.repo
```

更新yum索引

```
sudo yum makecache fast
```

安装 docker-ce

```
sudo yum install docker-ce
```

启动 docker

```
sudo systemctl start docker
```

验证是否安装成功

```
sudo docker info
```

### 如何使用Docker加速器

#### 针对Docker客户端版本大于1.10的用户

您可以通过修改daemon配置文件`/etc/docker/daemon.json`来使用加速器：

```
sudo mkdir -p /etc/docker
sudo tee /etc/docker/daemon.json <<-'EOF'
{
  "registry-mirrors": ["https://w5z91a3d.mirror.aliyuncs.com"]
}
EOF
sudo systemctl daemon-reload
sudo systemctl restart docker
```



## 二、docker-compose安装

`````

​````
yum -y install epel-release
​````

​````
yum install python-pip
​````

​````
pip install --upgrade pip
​````

​````
pip install docker-compose
​````

​````
docker-compose
​````

​````
docker-compose -version
​````

如果报错则执行

​````
pip install --upgrade backports.ssl_match_hostname
​````

## 三、配置docker 镜像加速器

​````
sudo cp -n /lib/systemd/system/docker.service /etc/systemd/system/docker.service sudo sed -i "s|ExecStart=/usr/bin/docker daemon|ExecStart=/usr/bin/docker daemon --registry-mirror=https://unwy5ykd.mirror.aliyuncs.com|g" /etc/systemd/system/docker.service
​````

 ````
sudo sed -i "s|ExecStart=/usr/bin/dockerd|ExecStart=/usr/bin/dockerd --registry-mirror=https://unwy5ykd.mirror.aliyuncs.com|g" /etc/systemd/system/docker.service sudo systemctl daemon-reload sudo service docker restart
 ````



​````
systemctl daemon-reload 
​````



​````
service docker restart
​````



如果启动不了 看这个



http://blog.csdn.net/u011403655/article/details/50524071
`````