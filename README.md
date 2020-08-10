# springboot-beetlsql
测试springboot+beetlsql
马天歌
【0】设计数据库表：这里有admin 123456的用户可供使用
运行sql文件夹下的sql，mysql数据库
【1】导入依赖
<dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-configuration-processor</artifactId>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-jdbc</artifactId>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-thymeleaf</artifactId>
        </dependency>
        <dependency>
            <groupId>org.apache.shiro</groupId>
            <artifactId>shiro-spring</artifactId>
            <version>${shiro.version}</version>
        </dependency>
        <dependency>
            <groupId>org.apache.shiro</groupId>
            <artifactId>shiro-ehcache</artifactId>
            <version>${shiro.version}</version>
        </dependency>
        <dependency>
            <groupId>org.apache.shiro</groupId>
            <artifactId>shiro-aspectj</artifactId>
            <version>${shiro.version}</version>
        </dependency>
        <dependency>
            <groupId>com.ibeetl</groupId>
            <artifactId>beetlsql</artifactId>
            <version>${beetlsql.version}</version>
        </dependency>
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>druid</artifactId>
            <version>${druid.version}</version>
        </dependency>
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
        </dependency>
        <dependency>
            <groupId>com.google.code.gson</groupId>
            <artifactId>gson</artifactId>
        </dependency>

        <dependency>
            <groupId>com.github.theborakompanioni</groupId>
            <artifactId>thymeleaf-extras-shiro</artifactId>
            <version>1.2.1</version>
        </dependency>
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>fastjson</artifactId>
            <version>1.2.31</version>
        </dependency>

        <dependency>
            <groupId>org.apache.commons</groupId>
            <artifactId>commons-lang3</artifactId>
            <version>3.5</version>
        </dependency>
        <dependency>
            <groupId>com.github.axet</groupId>
            <artifactId>kaptcha</artifactId>
            <version>0.0.9</version>
        </dependency>
        <dependency>
            <groupId>commons-net</groupId>
            <artifactId>commons-net</artifactId>
            <version>3.6</version>
        </dependency>
[2]配置application.yml文件，配置链接数据库驱动，修改你的数据库账户/密码;port中可修改端口号
spring:
  datasource:
    driverClassName: com.mysql.jdbc.Driver
    url: jdbc:mysql://localhost:3306/user?useUnicode=true&characterEncoding=UTF-8&useSSL=false
    username: root
    password: 123456
    filter: stat,wall
    initialSize: 5
    minIdle: 1
    maxActive: 30
server:
  port: 8088
【3】配置权限shiroCondig,配置Remal验证授权(AuthRealm)



【4】添加了redis+shiro存储session的功能,在application.properties中配置redis相关参数(我配置的是单机版的,如果需要配置集群逗号分隔添加地址)
-- #redis：redis.servers这里配置的是单机版,如果配置集群用逗号分割例如：localhost:7000;localhost:7001;localhost:7002;localhost:7003;localhost:7004;localhost:7005
redis.servers=localhost:6379
redis.maxWaitMillis=5000
redis.maxTotal=1000
redis.minIdle=8
redis.maxIdle=100
redis.testOnBorrow=true
redis.connectionTimeout=10000
redis.cluster.soTimeout=800
redis.cluster.maxRedirections=6
redis.password=123456
redis.enable=true

