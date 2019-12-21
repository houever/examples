application.yml

````yml
spring:
  datasource:
    url: jdbc:mysql://${MYSQL_HOST:10.1.70.95}:3306/mycloud-admin?allowMultiQueries=true&useUnicode=true&characterEncoding=UTF8&autoReconnect=true&useSSL=false
    username: root
    # Jasypt加密 可到common-utils中找到JasyptUtil加解密工具类生成加密结果 格式为ENC(加密结果)
    password: 123456
    type: com.alibaba.druid.pool.DruidDataSource
    driver-class-name: com.mysql.jdbc.Driver
    initialSize: 1
    maxActive: 20
    minIdle: 3
    maxWait: 60000
    timeBetweenEvictionRunsMillis: 60000
    minEvictableIdleTimeMillis: 300000
    validationQuery: SELECT 'x'
    testWhileIdle: true
    testOnBorrow: false
    testOnReturn: false
    poolPreparedStatements: true
    maxPoolPreparedStatementPerConnectionSize: 20
    #配置监控统计拦截的filters，去掉后监控界面sql无法统计，'wall'用于防火墙
    filters: stat,wall,slf4j
    druid:
      filter:
        stat:
          slow-sql-millis: 200
          log-slow-sql: true
        web-stat-filter:
          enabled: true
          url-pattern: /druid/*
          exclusions: /druid/*,*.gif,*.png,*.jpg,*.html,*.js,*.css,*.ico
        stat-view-servlet:
          url-pattern: /druid/*
          enabled: true
          reset-enable: false
          login-username: admin
          login-password: admin
          allow:
          deny:
    #通过connectProperties属性来打开mergeSql功能；慢SQL记录
    connectionProperties: druid.stat.mergeSql=true;druid.stat.slowSqlMillis=5000
````

