# Docker-安装kafka

https://blog.csdn.net/sinat_31908303/article/details/80447383

````yaml
version: '2'
services:
  zookeeper:
    image: wurstmeister/zookeeper   ## 镜像
    ports:
      - "2181:2181"                 ## 对外暴露的端口号
  kafka:
    image: wurstmeister/kafka       ## 镜像
    volumes: 
        - /etc/localtime:/etc/localtime ## 挂载位置（kafka镜像和宿主机器之间时间保持一直）
    ports:
      - "9092:9092"
    environment:
      KAFKA_ADVERTISED_HOST_NAME: 192.168.150.130   ## 修改:宿主机IP
      KAFKA_ZOOKEEPER_CONNECT: zookeeper:2181       ## 卡夫卡运行是基于zookeeper的
  kafka-manager:  
    image: sheepkiller/kafka-manager                ## 镜像：开源的web管理kafka集群的界面
    environment:
        ZK_HOSTS: 192.168.150.130                   ## 修改:宿主机IP
    ports:  
      - "9000:9000"                                 ## 暴露端口 
````




````yml
version: '2'

services:
  zoo1:
    image: wurstmeister/zookeeper
    restart: unless-stopped
    hostname: zoo1
    ports:
      - "12181:2181"
    container_name: zookeeper

  # kafka version: 1.1.0
  # scala version: 2.12
  kafka1:
    image: wurstmeister/kafka
    ports:
      - "9092:9092"
    environment:
      KAFKA_ADVERTISED_HOST_NAME: xxx # 这里为宿主机host name
      KAFKA_ZOOKEEPER_CONNECT: "zoo1:2181"
      KAFKA_BROKER_ID: 1
      KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR: 1
    depends_on:
      - zoo1
    container_name: kafka
  kafka-manager:
    image: hlebalbau/kafka-manager
    restart: unless-stopped
    ports:
      - "10085:9000"
    environment:
      ZK_HOSTS: "zoo1:2181"
      APPLICATION_SECRET: "random-secret"
      KAFKA_MANAGER_AUTH_ENABLED: "true"
      KAFKA_MANAGER_USERNAME: "admin"
      KAFKA_MANAGER_PASSWORD: "TL2oo8tl2oo8"
    depends_on:
      - zoo1
    container_name: kafka-manager  
    command: -Dpidfile.path=/dev/null


