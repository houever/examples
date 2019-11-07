### pom配置

````xml
<plugin>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-maven-plugin</artifactId>
				<configuration>
					<mainClass>${app.main.class}</mainClass>
				</configuration>
			</plugin>
			<plugin>
				<groupId>com.google.cloud.tools</groupId>
				<artifactId>jib-maven-plugin</artifactId>
				<version>1.7.0</version>
				<configuration>
					<from>
						<image>openjdk:alpine</image>
					</from>
					<to>
						<image>${docker.registry}/${project.artifactId}</image>
						<tags>
							<tag>latest</tag>
						</tags>
						<auth>
							<username>admin</username>
							<password>Harbor12345</password>
						</auth>
					</to>
					<container>
						<mainClass>${app.main.class}</mainClass>
						<!--使用当前时间-->
						<useCurrentTimestamp>true</useCurrentTimestamp>
						<!--容器在运行时暴露的端口-->
						<ports>
							<port>9008</port>
						</ports>
						<!--虚拟机配置-->
						<jvmFlags>
							<jvmFlag>-Xms512m</jvmFlag>
							<jvmFlag>-Xmx512m</jvmFlag>
						</jvmFlags>
						<environment>
							<spring.profiles.active>prod</spring.profiles.active>
							<TZ>Asia/Shanghai</TZ>
						</environment>
						<format>OCI</format>
					</container>
					<!--如果私有镜像仓库没有启用https，设置allowInsecureRegistries参数为true-->
					<allowInsecureRegistries>true</allowInsecureRegistries>
					<sendCredentialsOverHttp>true</sendCredentialsOverHttp>
				</configuration>
				<!--绑定jib:build到 Maven生命周期，例如package-->
				<executions>
					<execution>
						<phase>package</phase>
						<goals>
							<goal>build</goal>
						</goals>
					</execution>
				</executions>
			</plugin>
````

### 构建指令

````shell
mvn compile jib:build -DsendCredentialsOverHttp=true
````

或者

````shell
mvn compile jib:build \
-Dimage_tag=pa-1.5.0${BUILD_TAG} \
-Dregistry_url=${REGISTRY_URL} \
-Dregistry_username=${REGISTRY_USERNAME} \
-Dregistry_password=${REGISTRY_PASSWORD}
————————————————
版权声明：本文为CSDN博主「水淹萌龙」的原创文章，遵循 CC 4.0 BY-SA 版权协议，转载请附上原文出处链接及本声明。
原文链接：https://blog.csdn.net/qq_21047625/article/details/99643894
````

# jib自定义entrypoint

````xml
<container>
						<ports>
							<port>8080</port>
						</ports>
						<useCurrentTimestamp>true</useCurrentTimestamp>
						<entrypoint>
							<arg>/bin/sh</arg>
							<arg>-c</arg>
							<arg>java ${JAVA_OPTS} -cp /app/resources/:/app/classes/:/app/libs/* com.example.JibDemoApplication</arg>
						</entrypoint>
					</container>

````

## 运行

```shell
docker run -p 8080:8080 -e JAVA_OPTS='-Xms512m -Xmx512m' --rm jib-demo:20180903
```