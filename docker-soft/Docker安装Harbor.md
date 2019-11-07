## 1，安装Harbor

下载离线安装包



降级docker，需要18.06

````shell
yum remove docker \
                  docker-client \
                  docker-client-latest \
                  docker-common \
                  docker-latest \
                  docker-latest-logrotate \
                  docker-logrotate \
                  docker-selinux \
                  docker-engine-selinux \
                  docker-engine
                  
                  
sudo yum remove docker docker-engine docker-common \
    docker-client docker-client-latest docker-latest docker-latest-logrotate \
    docker-logrotate docker-selinux docker-engine-selinux
rpm -qa |grep docker*
ll /var/lib/docker/


````

### 7.2.配置docker的yum仓库

\# 安装依赖包

```
yum install yum-utils lvm2 device-mapper-persistent-data -y
```

\# 配置stable库

```
yum-config-manager --add-repo https://download.docker.com/linux/centos/docker-ce.repo
```

\# 就是这条命令，晚上很容易不成功

\# 禁用edge和test库

```
yum-config-manager --disable docker-ce-edge docker-ce-test
# yum-config-manager --enable docker-ce-edge docker-ce-test
```

\# 备注：

1）yum-utils提供yum-config-manager和utility工具
2）lvm2和device-mapper-persistent-data提供devicemapper的存储驱动
3）即使不使用edge库或test库也必须安装
4）建议上午安装，如果下载超时可以手动下载然后上传到/etc/yum.repos.d/目录
5）Note: Starting with Docker 17.06, stable releases are also pushed to the edge and test repositories.
6）如果启用edge和test库，yum安装时会安装最新版的docker，一般为test测试版，如果要安装最新的稳定版需要禁用该选项



### 7.3.安装docker-ce

\# 查看可安装的docker-ce列表

```
yum list docker-ce --showduplicates
# yum list docker-ce --showduplicates | sort -r    # 倒序排列
```

\# 安装最新版docker-ce

```
yum install docker-ce
```

\# 要安装指定版本docker，可以从上面的列表选择对应的版本号

```
yum install docker-ce-<VERSION STRING>
yum install docker-ce-18.06.1.ce-3.el7
```

\# 附：升级docker-ce

```
yum -y upgrade <包名>
```

\# 注意：

1）如果提示需要接受GPGkey，需要与以下fingerprint匹配：
060A 61C5 1B55 8A7F 742B 77AA C52F EB6B 621E 9F35
2）安转完成会自动创建docker用户组，需要手动创建docker用户



### 7.4.启动docker配置开机自启动

```
systemctl start docker
systemctl enable docker
ps -ef |grep docker
```



### 7.5.检查确认docker是否安装成功

```
docker run hello-world
```



## 2，客户端推送镜像至harbor

修改 daemon.json

````shell
vim /etc/docker/daemon.json
{
        "registry-mirrors": ["https://w5z91a3d.mirror.aliyuncs.com"],
        "insecure-registries": [ "10.1.70.63"]
}
````

重启docker

````shell
systemctl daemon-reload // 1，加载docker守护线程
systemctl restart docker // 2，重启docker
````

