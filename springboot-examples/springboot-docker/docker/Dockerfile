FROM java:8
MAINTAINER houqijun
VOLUME /tmp
ADD springboot-docker.jar /app.jar
EXPOSE 8761
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","-Dspring.profiles.active=dev","/app.jar"]