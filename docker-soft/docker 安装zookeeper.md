# docker 安装zookeeper

## 单机安装

````shell
docker run --privileged=true -d --name zookeeper --publish 2181:2181  -d --restart always zookeeper:latest
````

````yaml
version: "3"
services:
  zk:
    image: zookeeper:latest
    restart: always
    container_name: zk
    ports:
      - "2181:2181"
````

## 集群安装

### 单机集群

````yaml
version: '3.1'
services:
  zoo1:
    image: zookeeper
    restart: always
    hostname: zoo1
    ports:
      - 2181:2181
    environment:
      ZOO_MY_ID: 1
      ZOO_SERVERS: server.1=0.0.0.0:2888:3888 server.2=zoo2:2888:3888 server.3=zoo3:2888:3888

  zoo2:
    image: zookeeper
    restart: always
    hostname: zoo2
    ports:
      - 2182:2181
    environment:
      ZOO_MY_ID: 2
      ZOO_SERVERS: server.1=zoo1:2888:3888 server.2=0.0.0.0:2888:3888 server.3=zoo3:2888:3888

  zoo3:
    image: zookeeper
    restart: always
    hostname: zoo3
    ports:
      - 2183:2181
    environment:
      ZOO_MY_ID: 3
      ZOO_SERVERS: server.1=zoo1:2888:3888 server.2=zoo2:2888:3888 server.3=0.0.0.0:2888:3888
````

### `ZOO_TICK_TIME`

Defaults to `2000`. ZooKeeper’s `tickTime`

> 默认2秒
>
> 基本事件单元，以毫秒为单位。**它用来控制心跳和超时，默认情况下最小的会话超时时间为两倍的 tickTime** 
>
> 例如session超时：N*tickTime

### `ZOO_INIT_LIMIT`

Defaults to `5`. ZooKeeper’s `initLimit`

> 用于集群，允许从节点连接并同步到master节点的初始化连接时间，以tickTime的倍数来表示

### `ZOO_SYNC_LIMIT`

Defaults to `2`. ZooKeeper’s `syncLimit`

> 用于集群，master主节点与从节点之间发送消息，请求和应答时间长度（心跳机制）

### `ZOO_MAX_CLIENT_CNXNS`

Defaults to `60`. ZooKeeper’s `maxClientCnxns`

> 

### `ZOO_STANDALONE_ENABLED`

Defaults to `false`. Zookeeper’s [`standaloneEnabled`](http://zookeeper.apache.org/doc/trunk/zookeeperReconfig.html#sc_reconfig_standaloneEnabled)

> 

### `ZOO_AUTOPURGE_PURGEINTERVAL`

Defaults to `0`. Zookeeper’s [`autoPurge.purgeInterval`](https://zookeeper.apache.org/doc/current/zookeeperAdmin.html#sc_advancedConfiguration)

> 

### `ZOO_AUTOPURGE_SNAPRETAINCOUNT`

Defaults to `3`. Zookeeper’s [`autoPurge.snapRetainCount`](https://zookeeper.apache.org/doc/current/zookeeperAdmin.html#sc_advancedConfiguration)

> 

## Replicated mode

Environment variables below are mandatory if you want to run Zookeeper in replicated mode.

### `ZOO_MY_ID`

The id must be unique within the ensemble and should have a value between 1 and 255. Do note that this variable will not have any effect if you start the container with a `/data` directory that already contains the `myid`file.

### `ZOO_SERVERS`

This variable allows you to specify a list of machines of the Zookeeper ensemble. Each entry has the form of `server.id=host:port:port`. Entries are separated with space. Do note that this variable will not have any effect if you start the container with a `/conf` directory that already contains the `zoo.cfg` file.