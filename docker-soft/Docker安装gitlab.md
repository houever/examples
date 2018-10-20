# Docker安装gitlab

https://hub.docker.com/r/twang2218/gitlab-ce-zh/

## 一、docker-compose安装

````xml
version: '2'
services:
  web:
    image: 'twang2218/gitlab-ce-zh:11.1.4'
    restart: always
    hostname: '192.168.1.107'
    container_name: gitlab
    environment:
      TZ: 'Asia/Shanghai'
      GITLAB_OMNIBUS_CONFIG: |
        external_url 'http://192.168.1.107:80'
        gitlab_rails['gitlab_shell_ssh_port'] = 22
        unicorn['port'] = 8888
        nginx['listen_port'] = 80
    ports:
      - '80:80'
      - '443:443'
      - '22:22'
    volumes:
      - /usr/local/docker/gitlab/config:/etc/gitlab
      - /usr/local/docker/gitlab/data:/var/opt/gitlab
      - /usr/local/docker/gitlab/logs:/var/log/gitlab
````

## 二、命令行安装

````
docker network create gitlab-net
````

```
docker run -d \
    --hostname 192.168.52.131 \
    -p 80:80 \
    -p 443:443 \
    -p 22:22 \
    --name gitlab \
    --restart unless-stopped \
    -v gitlab-config:/etc/gitlab \
    -v gitlab-logs:/var/log/gitlab \
    -v gitlab-data:/var/opt/gitlab \
    --network gitlab-net \
    twang2218/gitlab-ce-zh:11.1.4
```

如果需要进入容器修改配置文件，可以用 `docker exec` 命令进入容器：

```
$ docker exec -it gitlab bash
root@09f6e32c528c:/# vi /etc/gitlab/gitlab.rb
root@09f6e32c528c:/# gitlab-ctl reconfigure
Starting Chef Client, version 12.12.15
resolving cookbooks for run list: ["gitlab"]
...
```

如需停止服务，直接运行 `docker stop gitlab`。

如需卸载服务及相关内容，可以执行：

```
docker stop gitlab
docker rm gitlab
docker network rm gitlab-net
docker volume rm gitlab-config gitlab-datagitlab-logs
```

### 三、22端口占用，修改22端口

### 关闭SELinux的两种方法

#### 1 永久方法 – 需要重启服务器

修改/etc/selinux/config文件中设置

SELINUX=disabled 

然后重启服务器。reboot

#### 2 临时方法 – 设置系统参数

使用命令setenforce 0

附：

setenforce 1 设置SELinux 成为enforcing模式

setenforce 0 设置SELinux 成为permissive模式

谢谢！

<https://hub.docker.com/r/denghui/gitlab-ce-zh/>

<https://hub.docker.com/r/twang2218/gitlab-ce-zh/>





````
vim /etc/ssh/sshd_config
````

找到“#Port 22”，这一行直接键入“yyp”复制该行到下一行，然后把两行的“#”号即注释去掉，修改成：

Port 22

Port 10086

SSH默认监听端口是22，如果你不强制说明别的端口，”Port 22”注不注释都是开放22访问端口。上面我保留了22端口，防止之后因为各种权限和配置问题，导致连22端口都不能访问了，那就尴尬了。等一切都ok了，再关闭22端口。

Ok，继续，我增加了10086端口，大家修改端口时候最好挑10000~65535之间的端口号，10000以下容易被系统或一些特殊软件占用，或是以后新应用准备占用该端口的时候，却被你先占用了，导致软件无法运行。

**第二步：**如果你关闭了**SELinux****，**可以忽略第二步。

先查看SELinux开放给ssh使用的端口

```
semanage port -l|grep ssh
```

我的系统打印如下：

ssh_port_t                    tcp      22

可知，SELinux没有给SSH开放10086端口，那么我们来添加该端口：

```
semanage port -a -t ssh_port_t -p tcp 10086
```

完成后，再次查看

```
semanage port -l|grep ssh
```

ssh_port_t                    tcp      22，10086

**第三步：**如果你关闭了防火墙，可以忽略第三步，话说防火墙不开启太危险了，建议开启。

先查看防火墙是否开启了10086端口：

```
firewall-cmd --permanent --query-port=10086/tcp
```

打印结果如下：

no

表示没有开放10086端口，那么添加下该端口：

```
firewall-cmd --permanent --add-port=10086/tcp
```

打印结果如下：

success

重新加载防火墙策略：



## 三、使用ssh下载报错

下面的代码很重要

````
gitlab_rails['gitlab_shell_ssh_port'] = 22
````



https://blog.csdn.net/yym6789/article/details/53807640