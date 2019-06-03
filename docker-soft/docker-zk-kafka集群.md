#### 实现目标

> kafka集群在docker网络中可用，和zookeeper处于同一网络
> 宿主机可以访问zookeeper集群和kafka的broker list
> docker重启时集群自动重启
> 集群的数据文件映射到宿主机器目录中
> 使用yml文件和$ docker-compose up -d命令创建或重建集群

```
$ docker-compose up -d
```

#### zk集群的docker-compose.yml

```
version: '3.4'

services:
  zoo1:
    image: wurstmeister/zookeeper
    restart: unless-stopped
    hostname: zoo1
    ports:
      - "2181:2181"
    container_name: zookeeper
    extra_hosts:
      - "zoo1:10.1.70.39"
  kafka1:
    image: wurstmeister/kafka
    restart: always
    hostname: kafka1
    container_name: kafka1
    ports:
      - 9092:9092
    links:
      - zoo1
    environment:
      KAFKA_ADVERTISED_HOST_NAME: kafka1
      KAFKA_ADVERTISED_PORT: 9092
      KAFKA_ZOOKEEPER_CONNECT: zoo1:2181
      KAFKA_BROKER_ID: 1
      KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR: 1
   extra_hosts:
      - "kafka1:10.1.70.39"
    volumes:
      - ./kafka1/logs:/kafka
    depends_on:
      - zoo1

  kafka2:
    image: wurstmeister/kafka
    restart: always
    hostname: kafka2
    container_name: kafka2
    ports:
      - 9093:9092
    links:
      - zoo1
    environment:
      KAFKA_ADVERTISED_HOST_NAME: kafka2
      KAFKA_ADVERTISED_PORT: 9093
      KAFKA_ZOOKEEPER_CONNECT: zoo1:2181
      KAFKA_BROKER_ID: 2
      KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR: 1
    extra_hosts:
      - "kafka2:10.1.70.39"
    volumes:
      - ./kafka2/logs:/kafka
    depends_on:
      - kafka1

  kafka3:
    image: wurstmeister/kafka
    restart: always
    hostname: kafka3
    container_name: kafka3
    ports:
    - 9094:9092
    environment:
      KAFKA_ADVERTISED_HOST_NAME: kafka3
      KAFKA_ADVERTISED_PORT: 9094
      KAFKA_ZOOKEEPER_CONNECT: zoo1:2181
      KAFKA_BROKER_ID: 3
      KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR: 1
    extra_hosts:
      - "kafka3:10.1.70.39"
    links:
      - zoo1
    volumes:
      - ./kafka3/logs:/kafka
    depends_on:
      - kafka1
      - kafka2
      
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
    container_name: kafka-manager  
    command: -Dpidfile.path=/dev/null
```

#### kafka集群的docker-compose.yml

> kfkluster少拼了个c…

```
version: '2'

services:
  kafka1:
    image: wurstmeister/kafka
    restart: always
    hostname: kafka1
    container_name: kafka1
    ports:
      - 9092:9092
    environment:
      KAFKA_ADVERTISED_HOST_NAME: kafka1
      KAFKA_ADVERTISED_PORT: 9092
      KAFKA_ZOOKEEPER_CONNECT: zoo1:2181,zoo2:2181,zoo3:2181
    volumes:
      - ./kafka1/logs:/kafka
    external_links:
      - zoo1
      - zoo2
      - zoo3
    networks:
      - kafka

  kafka2:
    image: wurstmeister/kafka
    restart: always
    hostname: kafka2
    container_name: kafka2
    ports:
      - 9093:9093
    environment:
      KAFKA_ADVERTISED_HOST_NAME: kafka2
      KAFKA_ADVERTISED_PORT: 9093
      KAFKA_ZOOKEEPER_CONNECT: zoo1:2181,zoo2:2181,zoo3:2181
    volumes:
      - ./kafka2/logs:/kafka
    external_links:
      - zoo1
      - zoo2
      - zoo3
    networks:
      - bridge

  kafka3:
    image: wurstmeister/kafka
    restart: always
    hostname: kafka3
    container_name: kafka3
    ports:
    - 9094:9094
    environment:
      KAFKA_ADVERTISED_HOST_NAME: kafka3
      KAFKA_ADVERTISED_PORT: 9094
      KAFKA_ZOOKEEPER_CONNECT: zoo1:2181,zoo2:2181,zoo3:2181
    volumes:
      - ./kafka3/logs:/kafka
    external_links:
      - zoo1
      - zoo2
      - zoo3
    networks:
      - bridge
      
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
    networks:
      - bridge
      
networks:
  kafka:
    external:
      name: bridge
```

### 结果查看和测试

#### 宿主机命令行创建topic

### 结果查看和测试

#### 宿主机命令行创建topic

```
$ pwd
/Users/shaozhipeng/Development/kafka_2.11-2.0.0/bin
$ ./kafka-topics.sh --create --zookeeper localhost:2184,localhost:2185,localhost:2186 --replication-factor 1 --partitions 1 --topic test1
```

#### Kafka Tool查看

[![image](http://images.icocoro.me/images/new/2018121701.png)](http://images.icocoro.me/images/new/2018121701.png)

#### docker ps查看正在运行的容器

[![image](http://images.icocoro.me/images/new/2018121702.png)](http://images.icocoro.me/images/new/2018121702.png)

#### 查看宿主机IP地址，并设置Host

> 这样宿主机就可以访问kafka集群了

[
  ](http://images.icocoro.me/images/new/2018121703.png)

````shell
FROM centos:6.6
 
ENV KAFKA_VERSION "1.0.0"
 
RUN mkdir /etc/yum.repos.d/backup &&\
	mv /etc/yum.repos.d/*.repo /etc/yum.repos.d/backup/ &&\
	curl -o /etc/yum.repos.d/CentOS-Base.repo http://mirrors.aliyun.com/repo/Centos-6.repo &&\
	sed -i "s/mirrors.aliyun.com/mirrors.aliyun.com/g" /etc/yum.repos.d/CentOS-Base.repo
 
RUN yum -y install nc vim lsof wget tar bzip2 unzip vim-enhanced passwd sudo yum-utils hostname net-tools rsync man git make automake cmake patch logrotate python-devel libpng-devel libjpeg-devel pwgen python-pip
 
RUN mkdir /opt/java &&\
	wget --no-cookies --no-check-certificate --header "Cookie: gpw_e24=http%3A%2F%2Fwww.oracle.com%2F; oraclelicense=accept-securebackup-cookie" "http://download.oracle.com/otn-pub/java/jdk/8u141-b15/336fa29ff2bb4ef291e347e091f7f4a7/jdk-8u141-linux-x64.tar.gz" -P /opt/java
 
RUN mkdir /opt/kafka &&\
	wget http://apache.fayea.com/kafka/$KAFKA_VERSION/kafka_2.11-$KAFKA_VERSION.tgz -P /opt/kafka
 
RUN tar zxvf /opt/java/jdk-8u141-linux-x64.tar.gz -C /opt/java &&\
	JAVA_HOME=/opt/java/jdk1.8.0_141 &&\
	sed -i "/^PATH/i export JAVA_HOME=$JAVA_HOME" /root/.bash_profile &&\
	sed -i "s%^PATH.*$%&:$JAVA_HOME/bin%g" /root/.bash_profile &&\
	source /root/.bash_profile
 
RUN tar zxvf /opt/kafka/kafka*.tgz -C /opt/kafka &&\
	sed -i 's/num.partitions.*$/num.partitions=3/g' /opt/kafka/kafka_2.11-$KAFKA_VERSION/config/server.properties
 
RUN echo "source /root/.bash_profile" > /opt/kafka/start.sh &&\
	echo "cd /opt/kafka/kafka_2.11-"$KAFKA_VERSION >> /opt/kafka/start.sh &&\
	#echo "sed -i 's%zookeeper.connect=.*$%zookeeper.connect=zookeeper:2181%g'  /opt/kafka/kafka_2.11-"$KAFKA_VERSION"/config/server.properties" >> /opt/kafka/start.sh &&\
	echo "[ ! -z $""ZOOKEEPER_CONNECT"" ] && sed -i 's%.*zookeeper.connect=.*$%zookeeper.connect='$""ZOOKEEPER_CONNECT'""%g'  /opt/kafka/kafka_2.11-"$KAFKA_VERSION"/config/server.properties" >> /opt/kafka/start.sh &&\
	echo "[ ! -z $""BROKER_ID"" ] && sed -i 's%broker.id=.*$%broker.id='$""BROKER_ID'""%g'  /opt/kafka/kafka_2.11-"$KAFKA_VERSION"/config/server.properties" >> /opt/kafka/start.sh &&\
	echo "[ ! -z $""BROKER_PORT"" ] && sed -i 's%port=.*$%port='$""BROKER_PORT'""%g'  /opt/kafka/kafka_2.11-"$KAFKA_VERSION"/config/server.properties" >> /opt/kafka/start.sh &&\
	echo "sed -i 's%#advertised.host.name=.*$%advertised.host.name='$""(hostname -i)'""%g'  /opt/kafka/kafka_2.11-"$KAFKA_VERSION"/config/server.properties" >> /opt/kafka/start.sh &&\
	echo "[ ! -z $""ADVERTISED_HOST_NAME"" ] && sed -i 's%.*advertised.host.name=.*$%advertised.host.name='$""ADVERTISED_HOST_NAME'""%g'  /opt/kafka/kafka_2.11-"$KAFKA_VERSION"/config/server.properties" >> /opt/kafka/start.sh &&\
	echo "sed -i 's%#host.name=.*$%host.name='$""(hostname -i)'""%g'  /opt/kafka/kafka_2.11-"$KAFKA_VERSION"/config/server.properties" >> /opt/kafka/start.sh &&\
	echo "[ ! -z $""HOST_NAME"" ] && sed -i 's%.*host.name=.*$%host.name='$""HOST_NAME'""%g'  /opt/kafka/kafka_2.11-"$KAFKA_VERSION"/config/server.properties" >> /opt/kafka/start.sh &&\
	echo "delete.topic.enable=true" >> /opt/kafka/kafka_2.11-$KAFKA_VERSION/config/server.properties &&\
        echo "group.initial.rebalance.delay.ms=0" >> /opt/kafka/kafka_2.11-$KAFKA_VERSION/config/server.properties &&\
	echo "bin/kafka-server-start.sh config/server.properties" >> /opt/kafka/start.sh &&\
	chmod a+x /opt/kafka/start.sh
 
RUN yum install -y nc
 
EXPOSE 9092
 
WORKDIR /opt/kafka/kafka_2.11-$KAFKA_VERSION
 
ENTRYPOINT ["sh", "/opt/kafka/start.sh"]
````

````shell
version: '2'
services:
  zookeeper0:
    image: zookeeper
    container_name: zookeeper0
    hostname: zookeeper0
    ports:
      - "2181:2181"
      - "2888:2888"
      - "3888:3888"
    expose:
      - 2181
      - 2888
      - 3888
    environment:
      ZOOKEEPER_PORT: 2181
      ZOOKEEPER_ID: 0
      ZOOKEEPER_SERVERS: server.0=zookeeper0:2888:3888 server.1=zookeeper1:28881:38881 server.2=zookeeper2:28882:38882
  zookeeper1:
    image: zookeeper
    container_name: zookeeper1
    hostname: zookeeper1
    ports:
      - "2182:2182"
      - "28881:28881"
      - "38881:38881"
    expose:
      - 2182
      - 2888
      - 3888
    environment:
      ZOOKEEPER_PORT: 2182
      ZOOKEEPER_ID: 1
      ZOOKEEPER_SERVERS: server.0=zookeeper0:2888:3888 server.1=zookeeper1:28881:38881 server.2=zookeeper2:28882:38882
  zookeeper2:
    image: zookeeper
    container_name: zookeeper2
    hostname: zookeeper2
    ports:
      - "2183:2183"
      - "28882:28882"
      - "38882:38882"
    expose:
      - 2183
      - 2888
      - 3888
    environment:
      ZOOKEEPER_PORT: 2183
      ZOOKEEPER_ID: 2
      ZOOKEEPER_SERVERS: server.0=zookeeper0:2888:3888 server.1=zookeeper1:28881:38881 server.2=zookeeper2:28882:38882
  kafka0:
    image: wurstmeister/kafka
    container_name: kafka0
    hostname: kafka0
    ports:
      - "9092:9092"
    environment:
      ZOOKEEPER_CONNECT: zookeeper0:2181,zookeeper1:2182,zookeeper2:2183
      BROKER_ID: 0
      BROKER_PORT: 9092
      ADVERTISED_HOST_NAME: kafka0
      HOST_NAME: kafka0
    volumes:
      - /var/run/docker.sock:/var/run/docker.sock
    depends_on:
        - zookeeper0
        - zookeeper1
        - zookeeper2
    expose:
      - 9092
  kafka1:
    image: wurstmeister/kafka
    container_name: kafka1
    hostname: kafka1
    ports:
      - "9093:9093"
    environment:
      ZOOKEEPER_CONNECT: zookeeper0:2181,zookeeper1:2182,zookeeper2:2183
      BROKER_ID: 1
      BROKER_PORT: 9093
      ADVERTISED_HOST_NAME: kafka1
      HOST_NAME: kafka1
    volumes:
      - /var/run/docker.sock:/var/run/docker.sock
    depends_on:
        - zookeeper0
        - zookeeper1
        - zookeeper2
    expose:
      - 9093
  kafka2:
    image: wurstmeister/kafka
    container_name: kafka2
    hostname: kafka2
    ports:
      - "9094:9094"
    environment:
      ZOOKEEPER_CONNECT: zookeeper0:2181,zookeeper1:2182,zookeeper2:2183
      BROKER_ID: 2
      BROKER_PORT: 9094
      ADVERTISED_HOST_NAME: kafka2
      HOST_NAME: kafka2
    volumes:
      - /var/run/docker.sock:/var/run/docker.sock
    depends_on:
        - zookeeper0
        - zookeeper1
        - zookeeper2
    expose:
      - 9094
````

