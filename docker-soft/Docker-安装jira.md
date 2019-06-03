# Docker-安装jira



<https://blog.csdn.net/Aria_Miazzy/article/details/85345733>

````yaml
version: '3'
services:
  atlassian-jira:
    image: cptactionhank/atlassian-jira
    ports:
      - "9271:8080"
    volumes:
      - ./jira:/var/atlassian/jira
      - ./logs:/opt/atlassian/jira/logs
    restart:
      always
````

默认用户名密码：


````yml
admin	123456

````