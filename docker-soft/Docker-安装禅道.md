# Docker-安装zentao

````yaml
version: '2'
services:
  zentao:
    image: idoop/zentao:latest
    container_name: zentao
    privileged=true
    ports:
      - "80:80"
    volumes:
      - /data/zbox/:/opt/zbox/
    environment:
      - USER="root"
      - PASSWD="password"
      - BIND_ADDRESS="false"
      - SMTP_HOST="163.177.90.125 smtp.exmail.qq.com"

````

默认用户名密码：


````yml
admin	123456

````