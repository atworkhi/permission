## **自定义权限管理：**
`基于springmvc搭建一套属于自己的权限管理`
`基于RBAC实现，易于扩展，界面管理`
### 开发功能
* 用户模块

`部门列表(树形结构)，增删改查及分页，用户的增删改查与分页，所属部门等`
* 权限模块

`权限列表(树形结构)，增删改查，新增权限(选择上级模块) 权限点的列表(增删改查)
权限点所属的权限模块，类型(菜单、按钮等)，角色列表(管理员、用户等)的增删改查，
角色与权限  角色与用户的关系 权限更新日志的管理等`
* 权限拦截

`在切面(Filter)做权限拦截 确定某用户是否拥有某个权限`
* 辅助类

`redis的封装和使用 | 树形结构的构建 | 权限操作恢复`

### 表结构设计 RBAC模型
![avatar](table.png)
**数据库字段见 init.sql**

日志表：是对数据操作状态的更新，及操作日志的记录
### 编码实现
springmvc:
```
pom.xml:
    <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <springframework.version>4.3.10.RELEASE</springframework.version>
    </properties>
        <!--spring配置-->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-beans</artifactId>
            <version>${springframework.version}</version>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-context</artifactId>
            <version>${springframework.version}</version>
        </dependency>
        <!--spring mvc + spring web-->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-web</artifactId>
            <version>${springframework.version}</version>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-webmvc</artifactId>
            <version>${springframework.version}</version>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-jdbc</artifactId>
            <version>${springframework.version}</version>
        </dependency>
        <!--mybatis-->
        <dependency>
            <groupId>org.mybatis</groupId>
            <artifactId>mybatis</artifactId>
            <version>3.4.0</version>
        </dependency>
        <dependency>
            <groupId>org.mybatis</groupId>
            <artifactId>mybatis-spring</artifactId>
            <version>1.2.3</version>
        </dependency>
        <!--数据源-->
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>druid</artifactId>
            <version>1.0.20</version>
        </dependency>
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>5.1.30</version>
        </dependency>
        <!--lombok 注解工具类-->
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <version>1.16.12</version>
        </dependency>
        <!--jackson guava谷歌封装的工具框架-->
        <dependency>
            <groupId>com.fasterxml.jackson.datatype</groupId>
            <artifactId>jackson-datatype-guava</artifactId>
            <version>2.5.3</version>
        </dependency>
```
web.xml配置：
```
<?xml version="1.0" encoding="UTF-8"?>

<web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_3_1.xsd"
         version="3.1">

  <display-name>Archetype Created Web Application</display-name>
  <!--spring监听器-->
  <listener>
    <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
  </listener>
  <!--spring bean配置文件所在的目录-->
  <context-param>
    <param-name>contextConfigLocation</param-name>
    <param-value>classpath:applicationContext.xml</param-value>
  </context-param>
  <!--springmvc 配置 -->
  <servlet>
    <servlet-name>spring</servlet-name>
    <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
    <init-param>
      <param-name>contextConfigLocation</param-name>
      <param-value>classpath:springServlet.xml</param-value>
    </init-param>
    <load-on-startup>1</load-on-startup>
  </servlet>
  <servlet-mapping>
    <servlet-name>spring</servlet-name>
    <url-pattern>/</url-pattern>
  </servlet-mapping>
  <!--编码过滤器配置-->
  <filter>
    <filter-name>encodingFilter</filter-name>
    <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
    <init-param>
      <param-name>encoding</param-name>
      <param-value>UTF-8</param-value>
    </init-param>
    <init-param>
      <param-name>forceEncoding</param-name>
      <param-value>true</param-value>
    </init-param>
  </filter>
  <filter-mapping>
    <filter-name>encodingFilter</filter-name>
    <url-pattern>/*</url-pattern>
  </filter-mapping>
  <!--欢迎页面-->
  <welcome-file-list>
    <welcome-file>index.jsp</welcome-file>
  </welcome-file-list>
</web-app>
注意:在resources文件夹下新建 springServlet.xml与applicationContext.xml
```
springServlet.xml配置：
```
spring mvc 配置：
    <beans xmlns="http://www.springframework.org/schema/beans"
           xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
           xmlns:context="http://www.springframework.org/schema/context"
           xmlns:mvc="http://www.springframework.org/schema/mvc"
    
           xsi:schemaLocation="http://www.springframework.org/schema/beans
           http://www.springframework.org/schema/beans/spring-beans.xsd
           http://www.springframework.org/schema/context
           http://www.springframework.org/schema/context/spring-context.xsd
           http://www.springframework.org/schema/mvc http://www.springframework.org/schema/mvc/spring-mvc.xsd">
    
        <!--上下文相关的注解会被管理-->
        <context:annotation-config></context:annotation-config>
    
        <!--启动注解驱动的springmvc功能-->
        <mvc:annotation-driven/>
    
        <!--启动包扫描功能-->
        <context:component-scan base-package="com.hanxx.permission.controller" />
        <context:component-scan base-package="com.hanxx.permission.service" />
    
        <!--配置bean-->
        <!--获取requestMapping请求的URL和方法-->
        <bean class="org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping" />
        <!--返回自定义的视图格式-->
        <bean class="org.springframework.web.servlet.view.BeanNameViewResolver" />
        <!--json返回处理-->
        <bean id="jsonView" class="org.springframework.web.servlet.view.json.MappingJackson2JsonView" />
    
        <!--jsp返回视图解析器-->
        <bean class="org.springframework.web.servlet.view.InternalResourceViewResolver">
            <!--前缀-->
            <property name="prefix" value="/views/" />
            <!--后缀-->
            <property name="suffix" value=".jsp" />
        </bean>
    
    </beans>
```
applicationContext.xml配置：
```
 <!--spring相关的配置-->
    <!--获取外界配置文件-->
    <bean id="placeholderConfigurer" class="org.springframework.beans.factory.config.PropertyPlaceholderConfigurer">
        <property name="ignoreUnresolvablePlaceholders" value="true" />
        <property name="locations">
            <list>
                <value>classpath:db.properties</value>
            </list>
        </property>
    </bean>

    <!--数据源的配置-->
    <bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource" init-method="init" destroy-method="close">
        <property name="driverClassName" value="${db.driverClassName}"/>
        <property name="url" value="${db.url}" />
        <property name="username" value="${db.username}" />
        <property name="password" value="${db.password}" />
        <!--初始化链接-->
        <property name="initialSize" value="3"/>
        <!--最小空余的连接数-->
        <property name="minIdle" value="3"/>
        <!--最大的连接池数-->
        <property name="maxActive" value="20" />
        <!--最大等待时间-->
        <property name="maxWait" value="60000" />
        <!--属性类型是字符串，通过别名的方式配置扩展插件，-->
        <!--常用的插件有：-->
        <!--监控统计用的filter:stat-->
        <!--日志用的filter:log4j-->
        <!--防御sql注入的filter:wall-->
        <property name="filters" value="stat,wall" />
    </bean>

    <!--sqlSessionFactory 整个mybatis-->
    <bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
        <property name="configLocation" value="classpath:mybatisConfig.xml"/>
        <property name="dataSource" ref="dataSource" />
        <property name="mapperLocations" value="classpath:mapper/*.xml" />
    </bean>
    <!--mapper扫描-->
    <bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
        <property name="basePackage" value="com.hanxx.permission.dao"/>
        <property name="sqlSessionFactoryBeanName" value="sqlSessionFactory" />
    </bean>
    <!--声明式事务-->
    <bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
        <property name="dataSource" ref="dataSource" />
    </bean>

    <tx:annotation-driven transaction-manager="transactionManager" />

    <!--druid配置-->
    <bean id="stat-filter" class="com.alibaba.druid.filter.stat.StatFilter">
        <property name="slowSqlMillis" value="3000" />
        <property name="logSlowSql" value="true" />
        <property name="mergeSql" value="true" />
    </bean>
    <bean id="wall-filter" class="com.alibaba.druid.wall.WallFilter">
        <property name="dbType" value="mysql"/>
    </bean>
```
配置druid监控：
```
web.xml:
<!--druid的访问-->
  <servlet>
    <servlet-name>DruidSevlet</servlet-name>
    <servlet-class>com.alibaba.druid.support.http.StatViewServlet</servlet-class>
    <!--登录名密码-->
    <init-param>
      <param-name>loginUsername</param-name>
      <param-value>druid</param-value>
    </init-param>
    <init-param>
      <param-name>loginPassword</param-name>
      <param-value>druid</param-value>
    </init-param>
  </servlet>
  <servlet-mapping>
    <servlet-name>DruidSevlet</servlet-name>
    <url-pattern>/sys/druid/*</url-pattern>
  </servlet-mapping>
  <!--druid的拦截请求-->
  <filter>
    <filter-name>DruidFilter</filter-name>
    <filter-class>com.alibaba.druid.support.http.WebStatFilter</filter-class>
    <!--过滤掉的拦截参数-->
    <init-param>
      <param-name>exclusions</param-name>
      <param-value>*.js,*.css,*.png,*.ico,*.gif,/sys/druid/*</param-value>
    </init-param>
  </filter>
  <filter-mapping>
    <filter-name>DruidFilter</filter-name>
    <url-pattern>/*</url-pattern>
  </filter-mapping>
```
mybatis config 配置：
```
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>

    <settings>
        <setting name="safeRowBoundsEnabled" value="true"/>
        <!--不允许缓存sql-->
        <setting name="cacheEnabled" value="false"/>
        <!--生成主键策略-->
        <setting name="useGeneratedKeys" value="true" />
    </settings>
    <!--<typeAliases>-->
        <!---->
    <!--</typeAliases>-->
    <!--<plugins>-->
        <!--<plugin interceptor=""></plugin>-->
    <!--</plugins>-->
</configuration> 
```
配置logback：
```
依赖：
        <!--logback-->
        <dependency>
            <groupId>ch.qos.logback</groupId>
            <artifactId>logback-core</artifactId>
            <version>1.1.8</version>
        </dependency>
        <dependency>
            <groupId>ch.qos.logback</groupId>
            <artifactId>logback-classic</artifactId>
            <version>1.1.8</version>
        </dependency>
        <dependency>
            <groupId>org.slf4j</groupId>
            <artifactId>slf4j-api</artifactId>
            <version>1.7.22</version>
        </dependency>
配置：logback.xml:
<?xml version="1.0" encoding="UTF-8" ?>
<configuration scan="true" scanPeriod="60 seconds">
    <appender name="STDOUT" class="ch.qos.logback.core.ConsoleAppender">
        <!--标准输出日志格式-->
        <encoder>
            <!--日期 进程 级别 日志名称 日志内容 换行-->
            <pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger - %msg%n</pattern>
        </encoder>
    </appender>

    <!--&lt;!&ndash;根据设置对日志进行压缩 输出 归档等&ndash;&gt;-->
    <!--<appender name="permission" class="ch.qos.logback.core.rolling.RollingFileAppender">-->
        <!--&lt;!&ndash;位置&ndash;&gt;-->
        <!--<file>${catalina.home}/logs/permission.log</file>-->
        <!--&lt;!&ndash;压缩规则&ndash;&gt;-->
        <!--<rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">-->
            <!--<FileNamePattern>${catalina.home}/logs/permission.%d{yyyy-MM-dd-HH}.log.gz</FileNamePattern>-->
        <!--</rollingPolicy>-->
        <!--&lt;!&ndash;日志输出格式&ndash;&gt;-->
        <!--<layout class="ch.qos.logback.classic.PatternLayout">-->
            <!--<pattern>-->
                <!--%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger - %msg%n-->
            <!--</pattern>-->

        <!--</layout>-->
    <!--</appender>-->
    <!--&lt;!&ndash;使用自定义的appender&ndash;&gt;-->
    <!--<logger name="xxx" level="INFO">-->
        <!--<appender-ref ref="permission"/>-->
    <!--</logger>-->

    <!--trace < debug <info < warn <error-->
    <root level="INFO">
        <appender-ref ref="STDOUT"></appender-ref>
    </root>
</configuration>  
```
开发环境验证：
```

```