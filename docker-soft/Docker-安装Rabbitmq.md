# 环境搭建

````yaml
version: '3.1'
services:
  rabbitmq:
    image: rabbitmq:3.7-management
    restart: always
    container_name: mymq
    ports:
      - 5672:5672
      - 15672:15672
````

