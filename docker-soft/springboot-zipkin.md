application.yml配置

````yml
spring:
  rabbitmq:
    host: ${RABBIT_MQ_HOST:10.1.70.95}
    port: ${RABBIT_MQ_PORT:5672}
    username: ${RABBIT_MQ_USERNAME:guest}
    password: ${RABBIT_MQ_PASSWORD:guest}
  zipkin:
    service:
      name: ${spring.application.name}
    enabled: true
    sender:
      type: rabbit
  sleuth:
    enabled: true
    sampler:
      probability: 1.0
    http:
      legacy:
        enabled: true
    opentracing:
      enabled: true
````

```xml
		<dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-zipkin</artifactId>
        </dependency>
        <dependency>
            <groupId>io.zipkin.brave</groupId>
            <artifactId>brave</artifactId>
            <version>5.6.1</version>
        </dependency>
```

