# elasticsearch整合springboot使用

elk安装   https://github.com/deviantony/docker-elk

参考博客https://www.jianshu.com/p/c2f6e80b2756

##### 安装elk
version:6.4.2

````shell
yum install git
````

````shell
cd /usr/local
````

````shll
git clone https://github.com/deviantony/docker-elk.git
````

````shell
cd docker-elk
````

````shell
docker-compose up -d
````

##### 安装elasticsearch-head

````shell
cd /usr/local
mkdir -vp el-head
cd el-head
vim docker-compose.yml
````

````yaml
version: '2.0'
services:
  elasticsearch-head:
    image: mobz/elasticsearch-head:5
    container_name: head
    ports:
      - 9100:9100
    volumes:
      - ./Gruntfile.js:/usr/src/app/Gruntfile.js
      - ./app.js:/usr/src/app/_site/app.js
````

启动

````shell
docker-compose up -d
````

##### 安装ik分词器

https://github.com/medcl/elasticsearch-analysis-ik/tree/v6.4.3

进入容器

````shell
docker exec -it docker-elk_elasticsearch_1 bash
````

````shell
cd plugiins
````

进入容器

```shell
mkdir ik
cd ik
```

```shell
wget https://github.com/medcl/elasticsearch-analysis-ik/releases/download/v6.4.2/elasticsearch-analysis-ik-6.4.2.zip
```

````shell
[root@f33b7e503ef9 ik]# ll
total 5828
-rw-r--r--. 1 root root  263965 May  6  2018 commons-codec-1.9.jar
-rw-r--r--. 1 root root   61829 May  6  2018 commons-logging-1.2.jar
drwxr-xr-x. 2 root root    4096 Aug 26 17:52 config
-rw-r--r--. 1 root root   52400 Oct 15 16:05 elasticsearch-analysis-ik-6.4.2.jar
-rw-r--r--. 1 root root 4502526 Oct 15 08:07 elasticsearch-analysis-ik-6.4.2.zip
-rw-r--r--. 1 root root  736658 May  6  2018 httpclient-4.5.2.jar
-rw-r--r--. 1 root root  326724 May  6  2018 httpcore-4.4.4.jar
-rw-r--r--. 1 root root    1805 Oct 15 16:05 plugin-descriptor.properties
-rw-r--r--. 1 root root     125 Oct 15 16:05 plugin-security.policy


[root@f33b7e503ef9 ik]# rm -rf elasticsearch-analysis-ik-6.4.2.zip 
[root@f33b7e503ef9 ik]# ../../bin/elasticsearch-plugin list
ik
ingest-geoip
ingest-user-agent
[root@f33b7e503ef9 ik]# exit
````

##### 安装hanlp插件

github:<https://github.com/shikeio/elasticsearch-analysis-hanlp>

````shell
cd plugins
````

````shell
mkdir analysis-hanlp
cd analysis-hanlp
````

````shell
wget https://github.com/shikeio/elasticsearch-analysis-hanlp/releases/download/6.4.2/analysis-hanlp-6.4.2.zip
````

````shell
unzip analysis-hanlp-6.4.2.zip
rm -rf analysis-hanlp-6.4.2.zip
````

```shell
vi ../../config/jvm.options
```

````shell
-Djava.security.policy=file://usr/share/elasticsearch/plugins/analysis-hanlp/plugin-security.policy
## heap dumps
````

````shell
docker restart elasticsearch
````

1

`````shell

`````

1







##### 操作kibana

https://www.elastic.co/guide/en/elasticsearch/client/index.html

进入Dev Tools

- 创建一个index

  ````json
  PUT /mytest
  {
    "settings": {
      "number_of_shards": 5,
      "number_of_replicas": 1
    }
  }
  ````

- 删除一个index

  ````json
  DELETE /mytest
  ````

- 添加数据

  请求方式依然是PUT

  ```
  PUT /索引库名/_mapping/类型名称
  {
    "properties": {
      "字段名": {
        "type": "类型",
        "index": true，
        "store": true，
        "analyzer": "分词器"
      }
    }
  }
  ```

  - 类型名称：就是前面将的type的概念，类似于数据库中的不同表
    字段名：任意填写	，可以指定许多属性，例如：
  - type：类型，可以是text、long、short、date、integer、object等
  - index：是否索引，默认为true
  - store：是否存储，默认为false
  - analyzer：分词器，这里的`ik_max_word`即使用ik分词器

- 查询

  ````json
  get mytest
  {
      "query":{
          "match_all":{}
      }
  }
  ````

- 存数据

  ````json
  
  ````

- 加入文档

和java交互

https://www.elastic.co/guide/cn/elasticsearch/guide/current/_talking_to_elasticsearch.html