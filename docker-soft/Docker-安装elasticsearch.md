# Docker-安装elasticsearch

Es 官方网址

https://www.elastic.co/guide/en/elasticsearch/reference/5.6/docker.html

````yaml
version: '2'
services:
  elasticsearch1:
    image: docker.elastic.co/elasticsearch/elasticsearch:5.6.12
    container_name: elasticsearch1
    environment:
      - cluster.name=docker-cluster
      - bootstrap.memory_lock=true
      - "ES_JAVA_OPTS=-Xms512m -Xmx512m"
    ulimits:
      memlock:
        soft: -1
        hard: -1
    mem_limit: 1g
    volumes:
      - esdata1:/usr/share/elasticsearch/data
    ports:
      - 9200:9200
    networks:
      - esnet
  elasticsearch2:
    image: docker.elastic.co/elasticsearch/elasticsearch:5.6.12
    environment:
      - cluster.name=docker-cluster
      - bootstrap.memory_lock=true
      - "ES_JAVA_OPTS=-Xms512m -Xmx512m"
      - "discovery.zen.ping.unicast.hosts=elasticsearch1"
    ulimits:
      memlock:
        soft: -1
        hard: -1
    mem_limit: 1g
    volumes:
      - esdata2:/usr/share/elasticsearch/data
    networks:
      - esnet

volumes:
  esdata1:
    driver: local
  esdata2:
    driver: local

networks:
  esnet:
````

版本适配

https://github.com/spring-projects/spring-data-elasticsearch

## 一、搭建单节点es

### 1、安装es

以springboot2.0.4为例，对应的spring-data-elasticsearch 版本为3.0.9

对应的elasticsearch的版本为5.6.10

````shell
cd /usr/local

mkdir -p es
````

`vim elasticsearch.yml`

`elasticsearch.yml`内容如下

````yaml
http.host: 0.0.0.0
transport.host: 0.0.0.0
network.host: 0.0.0.0
http.port: 9200
http.cors.enabled: true
http.cors.allow-origin: "*"
# Uncomment the following lines for a production cluster deployment
#transport.host: 0.0.0.0
#discovery.zen.minimum_master_nodes: 1
````

`vim docker-compose.yml`

````yaml
version: '2'
services:
  elasticsearch:
    image: elasticsearch:5.6.10
    container_name: elasticsearch
    restart: always
    environment:
      - TZ='Asia/Shanghai'
      - cluster.name=docker-cluster
      - bootstrap.memory_lock=true
      - "ES_JAVA_OPTS=-Xms512m -Xmx512m"
    ulimits:
      memlock:
        soft: -1
        hard: -1
    mem_limit: 1g
    volumes:
      - esdata1:/usr/share/elasticsearch/data
      - $PWD/elasticsearch.yml:/usr/share/elasticsearch/config/elasticsearch.yml
    ports:
      - 9200:9200
      - 9300:9300
volumes:
  esdata1:
    driver: local

````

*报错处理*

`[1]: max virtual memory areas vm.max_map_count [65530] is too low, increase to at least [262144]`

处理方法

修改`etc/sysctl.conf`

````shell
vim /etc/sysctl.conf

最末尾加入：vm.max_map_count=262144
````

或者

````
echo "vm.max_map_count=262144" >> /etc/sysctl.conf
````

查看是否生效

````
sysctl -p
````

### 2、安装es-head

#### Gruntfile.js

````shell
vim Gruntfile.js
````

````js
connect:{
                server: {
                    options: {
                        /* 默认监控：127.0.0.1,修改为：0.0.0.0 */
                        hostname: '0.0.0.0',
                        port: 9100,
                        base: '.',
                        keepalive: true
                    }
            }
}
````

#### app.js

````shell
vim app.js
````

````js
/* 修改localhost为elasticsearch集群地址，Docker部署中，一般是elasticsearch宿主机地址 */
this.base_uri = this.config.base_uri || this.prefs.get("app-base_uri") || "http://localhost:9200";
````

````shell
vim docker-compose.yml
````

````yaml
version: '2.0'
services:
    elasticsearch-head:
    	restart: always
        image: mobz/elasticsearch-head:5
        container_name: es-head
        volumes:
            /* 修改挂载位置 */
           - $PWD/Gruntfile.js:/usr/src/app/Gruntfile.js
           - $PWD/app.js:/usr/src/app/_site/app.js        
        ports:
           - "9100:9100" 
````

#### ik分词器

````shell
docker exec -it elasticsearch bash
````

````
cd plugins/
````

````
wget https://github.com/medcl/elasticsearch-analysis-ik/releases/download/v5.6.10/elasticsearch-analysis-ik-5.6.10.zip
````

````shell
unzip elasticsearch-analysis-ik-5.6.10.zip
````

````shell
rm elasticsearch-analysis-ik-5.6.10.zip
````



#### 安装  elasticsearch-kopf 插件 

````shell

````



安装ik分词器

3. 安装ik 中文分词插件 进入容器 $ sudo 
4. docker exec -it imageId /bin/bash $ 
5. cd plugins $ wget https://github.com/medcl/elasticsearch-analysis-ik/releases/download/v5.6.4/elasticsearch-analysis-ik-5.6.4.zip 
6. $ unzip elasticsearch-analysis-ik-5.6.4.zip 
7. $ rm elasticsearch-analysis-ik-5.6.4.zip 
8. 利用 ./bin/elasticsearch-plugin install https://github.com/medcl/elasticsearch-analysis-ik/releases/download/v5.6.4/elasticsearch-analysis-ik-5.6.4.zip  会出问题： java.io.FileNotFoundException: /usr/share/elasticsearch/config/analysis-ik/IKAnalyze 
9. --------------------- 本文来自 mangues 的CSDN 博客 ，全文地址请点击：https://blog.csdn.net/u012915455/article/details/78952068?utm_source=copy  

## 二、集群安装

### 1、单机集群安装

````
vim es1.yml
````

````yaml
#集群名称 所有节点要相同
cluster.name: "es-master"
#本节点名称
node.name: master
#作为master节点
node.master: true
#是否存储数据
node.data: true
# head插件设置
http.cors.enabled: true
http.cors.allow-origin: "*"
#设置可以访问的ip 这里全部设置通过
network.bind_host: 0.0.0.0
#设置节点 访问的地址 设置master所在机器的ip
network.publish_host: 192.168.52.140 

discovery.zen.ping.unicast.hosts: ["192.168.52.140:9300","192.168.52.140:9301","192.168.52.140:9302"]
````

````
vim es2.yml
````

````yaml
cluster.name: "es-master"
#子节点名称
node.name: node
#不作为master节点
node.master: false
node.data: true

http.cors.enabled: true
http.cors.allow-origin: "*"

network.bind_host: 0.0.0.0
network.publish_host: 192.168.52.140
#设置master地址
discovery.zen.ping.unicast.hosts: [192.168.52.40]
````

````es3.yml
vim es3.yml
````

````yaml
cluster.name: "es-master"
#子节点名称
node.name: node
#不作为master节点
node.master: false
node.data: true

http.cors.enabled: true
http.cors.allow-origin: "*"

network.bind_host: 0.0.0.0
network.publish_host: 192.168.52.140
#设置master地址
discovery.zen.ping.unicast.hosts: [192.168.52.140]

````

````shell
vim docker-compose.yml
````

````yaml
version: '2.0'
services:
  es-master:
    image: elasticsearch:5.6.12
    container_name: es-master
    volumes:
      - ./es1.yml:/usr/share/elasticsearch/config/elasticsearch.yml
      - ./data1:/usr/share/elasticsearch/data
    environment:
      - ES_CLUSTERNAME=es-master
    ports:
      - 9200:9200
      - 9300:9300
  es-2:
    image: elasticsearch:5.6.12
    container_name: es-2
    volumes:
      - ./es2.yml:/usr/share/elasticsearch/config/elasticsearch.yml
      - ./data2:/usr/share/elasticsearch/data
    environment:
      - ES_CLUSTERNAME=es-master
    ports:
      - 9201:9200
      - 9301:9300
    links:
      - es-master:elasticsearch 
  es-3:
    image: elasticsearch:5.6.12
    container_name: es-3
    volumes:
      - ./es3.yml:/usr/share/elasticsearch/config/elasticsearch.yml
      - ./data3:/usr/share/elasticsearch/data
    environment:
      - ES_CLUSTERNAME=es-master
    ports:
      - 9202:9200
      - 9302:9300
    links:
      - es-master:elasticsearch
  elasticsearch-head:
    image: mobz/elasticsearch-head:5
    container_name: head
    volumes:
      - ./Gruntfile.js:/usr/src/app/Gruntfile.js
      - ./app.js:/usr/src/app/_site/app.js        
    ports:
      - 9100:9100          
    links:
      - es-master:elasticsearch
````

### 2、多机集群安装

````yaml
# ================= Elasticsearch Configuration ===================
 
# #配置es的集群名称, es会自动发现在同一网段下的es,如果在同一网段下有多个集群,就可以用这个属性来区分不同的集群｡
 
 cluster.name: winy-es-cluster
 
# #节点名称
 
 node.name: node-002
 
# #指定该节点是否有资格被选举成为node
 
 node.master: true
 
# #指定该节点是否存储索引数据,默认为true｡
 
 node.data: true
 
# #设置绑定的ip地址还有其它节点和该节点交互的ip地址,本机ip
 
 network.host: 192.168.95.129
 
# #指定http端口,你使用head､kopf等相关插件使用的端口
 
 http.port: 9200
 
# #设置节点间交互的tcp端口,默认是9300｡
 
 transport.tcp.port: 9300
 
#设置集群中master节点的初始列表,可以通过这些节点来自动发现新加入集群的节点｡
 
 discovery.zen.ping.unicast.hosts: ["192.168.95.129:9300","192.168.95.130:9300"]
 
#如果要使用head,那么需要增加新的参数,使head插件可以访问es
 
 http.cors.enabled: true
 
 http.cors.allow-origin: "*"
 
 #防止脑裂，告诉cluster集群master节点数量，设置为（master/2）+1
 
 discovery.zen.minimum_master_nodes=2
 
 #节点之间通信超时等待时长
 discovery.zen.ping.timeout=10
````

## 三、脑裂问题的预防

https://blog.csdn.net/WuyZhen_CSDN/article/details/73744233