# Docker-registry

## 一、docker-registry安装

````shell
mkdir -vp /usr/local/docker/registry
````

````shell
cd /usr/local/docker/registry
````

````shell
vim docker-compose.yml
````

````yaml
version: '3.1'
services:
  registry:
    image: registry
    restart: always
    container_name: registry
    ports:
      - 5000:5000
    volumes:
      - /usr/local/docker/registry/data:/var/lib/registry
````

## 二、registry-fronted安装

````shell
mkdir -vp /usr/local/docker/fronted
````

````shell
cd /usr/local/docker/fronted
````

````shell
vim docker-compose.yml
````

````yaml
version: '3.1'
services:
  frontend:
    restart: always
    image: konradkleine/docker-registry-frontend:v2
    container_name: registry-front
    ports:
      - 80:80
    volumes:
      - ./certs/frontend.crt:/etc/apache2/server.crt:ro
      - ./certs/frontend.key:/etc/apache2/server.key:ro
    environment:
      - ENV_DOCKER_REGISTRY_HOST=192.168.52.136
      - ENV_DOCKER_REGISTRY_PORT=5000
````

`访问端口为80`



## 三、镜像上传

修改`/etc/docker/daemon.json`

````shell
vim /etc/docker/daemon.json
````

加入

````json
"insecure-registries": ["192.168.1.103:5000"]
````

重启docker服务

````shell
systemctl daemon-reload
systemctl restart docker
````

#### 1、测试镜像上传

我们以 Nginx 为例测试镜像上传功能

```
## 拉取一个镜像
docker pull nginx

## 查看全部镜像
docker images

## 标记本地镜像并指向目标仓库（ip:port/image_name:tag，该格式为标记版本号）
docker tag nginx 192.168.1.103:5000/nginx

## 提交镜像到仓库
docker push 192.168.1.103:5000/nginx
```

#### 2、查看全部镜像

```
curl -XGET http://192.168.1.103:5000/v2/_catalog
```

#### 3、查看指定镜像

以 Nginx 为例，查看已提交的列表

```
curl -XGET http://192.168.1.103:5000/v2/nginx/tags/list
```

#### 4、测试拉取镜像

- 先删除镜像

```
docker rmi nginx
docker rmi 192.168.1.103:5000/nginx
```

- 再拉取镜像

```
docker pull 192.168.75.133:5000/nginx
```

访问：192.168.1.103	查看可视化界面



## 四、springboot 运行jar包

````shell
vim docker-compose.yml
````

````yaml
version: "3"
services:
  llnao-demo:
    image: 192.168.1.103:5000/llnao-demo
    restart: always
    ports:
      - "8080:8080"
````

