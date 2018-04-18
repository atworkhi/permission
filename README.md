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