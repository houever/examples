# Docker-安装kafka

https://blog.csdn.net/sinat_31908303/article/details/80447383

````shell
vim /etc/hosts
````

加入下面代码

````shell
kafka_host	10.1.70.71
````

````yaml
version: '2'

services:
  zoo1:
    image: wurstmeister/zookeeper
    restart: unless-stopped
    hostname: zoo1
    ports:
      - "2181:2181"
    container_name: zookeeper
  kafka1:
    image: wurstmeister/kafka
    ports:
      - "9092:9092"
    environment:
      KAFKA_ADVERTISED_HOST_NAME: kafka_host # 这里为宿主机host name
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
      - "9000:9000"
    environment:
      ZK_HOSTS: "zoo1:2181"
      APPLICATION_SECRET: "random-secret"
      KAFKA_MANAGER_AUTH_ENABLED: "true"
      KAFKA_MANAGER_USERNAME: "admin"
      KAFKA_MANAGER_PASSWORD: "123456"
    depends_on:
      - zoo1
    container_name: kafka-manager  
    command: -Dpidfile.path=/dev/null
````
