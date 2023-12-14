## Mybatis

# 1 简介

## 1.1什么是Mybatis

- MyBatis 是一款优秀的**持久层框架**，

- 它支持自定义 SQL、存储过程以及高级映射。MyBatis 免除了几乎所有的 JDBC 代码以及设置参数和获取结果集的工作。
- MyBatis 可以通过简单的 XML 或注解来配置和映射原始类型、接口和 Java POJO（Plain Old Java Objects，普通老式 Java 对象）为数据库中的记录。

## 1.2 持久化

数据持久化

- 持久化就是将程序的数据在持久状态和瞬时状态转化的过程
- 内存：断电即失
- 数据库（jdbc），io文件持久化。

##### 为什么需要持久化

- 有一些对象不能让他丢掉

- 内存太贵



### 1.3持久层

Dao层，Service层，Controller层

- 完成持久化工作的代码块
- 层界限十分明显 



## 1.4为什么需要Mybatis

- 方便
- 传统的JDBC代码太复杂，简化，框架
- 将数据存入数据库中
- 不用Mybatis也可以，更容易上手 
- 优点：
  - 简单易学
  - 灵活
  - sql和代码的分离，提高了可维护性
  - 提供映射标签，支持对象与数据库的orm字段关系映射
  - 提供对象关系映射标签，支持对象关系组建维护
  - 提供xml标签，支持编写动态sql

# 2 第一个Mybatis程序

搭建环境-->导入Mybatis-->编写代码-->测试

## 2.1 搭建环境

新建数据库

新建项目

## 2.2 创建一个模块

- 编写mybatis的核心配置文件

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "https://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
    <environments default="development">
        <environment id="development">
            <transactionManager type="JDBC"/>
            <dataSource type="POOLED">
                <property name="driver" value="com.mysql.cj.jdbc.Driver"/>
                <property name="url" value="jdbc:mysql://localhost:3306/mybatis?useSSL=fals&amp;eallowPublicKeyRetrieval=true&amp;serverTimezone=UTC"/>
                <property name="username" value="root"/>
                <property name="password" value="120808"/>
            </dataSource>
        </environment>
    </environments>
    <mappers>
        <mapper resource="org/mybatis/example/BlogMapper.xml"/>
    </mappers>
</configuration>
```

- 编写mybatis工具类

```java
package utils;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;

public class MybatisUtlis {

    private static SqlSessionFactory sqlSessionFactory;

    static {

        try {
            //使用Mybatis获取sqlSessionFactory对象
            String resource = "mybatis.config.xml";
            InputStream inputStream = Resources.getResourceAsStream(resource);
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    //既然有了SqlSessionFactory，顾名思义，我们就可以从中获得SqlSession的实例
    //SqlSession 完全包含了面向数据库执行SQL命令所需的所有方法

    public static SqlSession getSqlSession(){
       return sqlSessionFactory.openSession();
    }

}

```



## 2.3 编写代码

- 实体类

```java
package pojo;

//实体类
public class User {
    private int id;
    private String name;
    private String pwd;

    public User() {
    }

    public User(int id, String name, String pwd) {
        this.id = id;
        this.name = name;
        this.pwd = pwd;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", pwd='" + pwd + '\'' +
                '}';
    }
}

```



- Dao接口

```java
public interface UserDao {
    List<User> getUserList();
}
```



- 接口实现类由原来的UserDaoImpl转变成一个Mapper配置文件

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "https://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="dao.UserDao">
<select id="getUserList" resultType="pojo.User">
     select * from mybatis.user
</select>
</mapper>
```

## 2.4 测试

核心配置文件中注册mappers

```xml
    <build>
		<resources>
            <resource>
                <directory>src/main/resources</directory>
                <includes>
                    <include>**/*.properties</include>
                    <include>**/*.xml</include>
                </includes>
                <filtering>true</filtering>
            </resource>
            <resource>
                <directory>src/main/java</directory>
                <includes>
                    <include>**/*.properties</include>
                    <include>**/*.xml</include>
                </includes>
                <filtering>true</filtering>
            </resource>
        </resources>
    </build>
```



- junit测试

```
  @Test
    public void test(){

        //第一步：获得sqlseesion对象
        SqlSession sqlSession = MybatisUtlis.getSqlSession();
        //执行SQL
        UserDao mapper = sqlSession.getMapper(UserDao.class);
        List<User> userList = mapper.getUserList();

        for (User user : userList) {
            System.out.println(user);
        }
        //关闭sqlSession
        sqlSession.close();
    }
```

# 3 CRUD

### 1、namespace

namespace中的包名要和Dao/mapper 接口的包名一致

### 2、select

选择，查询语句  

- id：就是对应的namespace中的方法名 
- resultType：Sql语句执行的返回值
- parameterType：参数类型

### 3、insert

```java
public void addUser(){
        SqlSession sqlSession = MybatisUtlis.getSqlSession();

        UserDao mapper = sqlSession.getMapper(UserDao.class);

         mapper.addUser(new User(17,"xiaom","123456"));

        sqlSession.commit();

        sqlSession.close();

    }
```

```xml
<insert id="addUser" parameterType="pojo.User">
          insert into mybatis.User (id,name,pwd) values(#{id},#{name},#{pwd})
     </insert>
```

### 4、update

```xml
<update id="updateUser" parameterType="pojo.User">
          update mybatis.User set name = #{name},pwd=#{pwd}  where id =#{id};
     </update>
```

```java
  @Test
    public void updateUser(){
        SqlSession sqlSession = MybatisUtlis.getSqlSession();

        UserDao mapper = sqlSession.getMapper(UserDao.class);

        int res = mapper.updateUser(new User(3,"xiaoxiao","123123"));

        if(res>0){
            System.out.println("修改成功");
        }
        sqlSession.commit();
        sqlSession.close();
    }
```

### 5、delete

```xml
<delete id="userDelete" parameterType="int">
          delete from mybatis.User where id = #{id}
     </delete>
```

```java
    @Test
    public void userDelete(){
        SqlSession sqlSession = MybatisUtlis.getSqlSession();

        UserDao mapper = sqlSession.getMapper(UserDao.class);

        int res = mapper.userDelete(4);

        if(res>0){
            System.out.println("删除成功");
        }

        sqlSession.commit();
        sqlSession.close();
    }
```

## 6.万能Map

假设，我们的实体类，或者数据库中的表，字段或者参数过多，我们应当考虑使用Map

```java
 //万能的Map
    int addUser2(Map<String, Object> map);
```

```xml
  <insert id="addUser2" parameterType="map">
        insert into mybatis.user (id,name,pwd) values(#{userid},#{username},#{password})
    </insert>

```

```java
 @Test
    public void addUser2(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();

        UserMapper mapper = sqlSession.getMapper(UserMapper.class);

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userid",8);
        map.put("username","xiaoha");
        map.put("password","322311");

        mapper.addUser2(map);
        sqlSession.commit();
        sqlSession.close();
    }
```

Map传递参数，直接在sql中取出key即可 parameterType="map"

对象传递参数，直接在sql中取对象的属性即可 parameterType="Object"

只有一个基本类型参数的情况下，可以直接用sql取出 可以忽略

多个参数用Map，**或者注解**

## 7 模糊查询

1.java代码执行的时候，传递通配符% %

```java
List<User> userList = mapper.getUserLike("%hong%");
```

2.在sql拼接中使用通配符

```xml
select * from mybatis.user where name like "%"#{value}"%"
```



# 4 配置解析

## 1、核心配置文件

- mybatis-config.xml
- Mybatis的配置文件包含了会深深影响MyBatis行为的设置和属性信息。

```xml
configuration（配置）
properties（属性）
settings（设置）
typeAliases（类型别名）
typeHandlers（类型处理器）
objectFactory（对象工厂）
plugins（插件）
environments（环境配置）
environment（环境变量）
transactionManager（事务管理器）
dataSource（数据源）
databaseIdProvider（数据库厂商标识）
mappers（映射器）
```

## 2、环境配置（environments）

MyBatis 可以配置成适应多种环境

**尽管可以配置多个环境，但每个 SqlSessionFactory 实例只能选择一种环境。**

学会使用配置多套运行环境

Mybatis默认的事务管理器就是JDBC，连接池：POOLED

## 3、属性（properties）

通过properties属性来实现引用配置文件

这些属性可以在外部进行配置，并可以进行动态替换。你既可以在典型的 Java 属性文件中配置这些属性，也可以在 properties 元素的子元素中设置。【db.properties】

编写一个配置文件

db.properties

```properties
driver = com.mysql.cj.jdbc.Driver
url = jdbc:mysql://localhost:3306/mybatis?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
username = root
password = 120808
```

在核心配置文件中映入

```xml
  <!--引入外部配置文件-->
    <properties resource="db.properties"/>
```

- 可以直接引入外部文件
- 可以在其中增加一些属性字段
- 如果两个文件都有同一个字段优先引入外部文件



## 4 类型别名

- 类型别名是为java类型设置一个短的名字
- 存在的意义仅限于减少完全限定名的冗余

```xml
 <!--可以给实体类起别名-->
    <typeAliases>
        <typeAlias type="pojo.User" alias="User"/>
    </typeAliases>
```

也可以指定一个包名，MyBatis会在包名下面搜索需要的java Bean

扫描实体类的包，它的默认别名就为这个类的类名，首字母小写

```xml
    <typeAliases>
        <package name="pojo"/>
    </typeAliases>
```



在实体类比较少的时候，使用第一种方式

如果实体类十分多，建议使用第二种

第一种可以Diy别名，第二种则不行，如果非要改，需要在实体类上增加注解

```java
@Alias("hello")
public class User {
```

##  5 设置

mybatis中极其重要的调整设置，他们会改变mybatis的运行时行为

![image-20231201174150502](C:\Users\12080\AppData\Roaming\Typora\typora-user-images\image-20231201174150502.png)

![image-20231201174212204](C:\Users\12080\AppData\Roaming\Typora\typora-user-images\image-20231201174212204.png)

## 6 其他配置

- [typeHandlers（类型处理器）](https://mybatis.org/mybatis-3/zh/configuration.html#typeHandlers)
- [objectFactory（对象工厂）](https://mybatis.org/mybatis-3/zh/configuration.html#objectFactory)
- [plugins（插件）](https://mybatis.org/mybatis-3/zh/configuration.html#plugins)
  - mybatis-generator-core
  - mybatis-plus
  - 通用map

7 映射器（mappers）

MapperRegistry：注册绑定我们的Mapper文件；

方式一

```xml
    <mappers>
        <mapper resource="dao/UserMapper.xml"/>
    </mappers>
```

方式二 使用class文件绑定注册

```xml
    <mappers>
        <mapper resource="dao.UserMapper"/>
    </mappers>
```

方式三：使用扫码包进行注入绑定

```xml
    <mappers>
        <mapper resource="dao"/>
    </mappers>
```

注意点：

- 接口和他的Mapper配置文件必须同名
- 接口和他的Mapper配置文件必须在同一个包下



## 8 生命周期和作用域

作用域和生命周期类别是至关重要的，因为错误的使用会导致非常严重的**并发问题**。

![image-20231201185714227](C:\Users\12080\AppData\Roaming\Typora\typora-user-images\image-20231201185714227.png)

#### SqlSessionFactoryBuilder

- 一旦创建了 SqlSessionFactory，就不再需要它了
- 局部变量

#### SqlSessionFactory

- 可以想象为数据库连接池
- SqlSessionFactory 一旦被创建就应该在应用的运行期间一直存在，**没有任何理由丢弃它或重新创建另一个实例**
- 因此 SqlSessionFactory 的最佳作用域是应用作用域

#### SqlSession

- 连接到连接池的一个请求
- SqlSession 的实例不是线程安全的，因此是不能被共享的，所以它的最佳的作用域是请求或方法作用域。
- 用完关闭，否则资源被占用

![image-20231201190417796](C:\Users\12080\AppData\Roaming\Typora\typora-user-images\image-20231201190417796.png)

这里面的每一个Mapper，就代表一个具体的业务

# 5 解决属性名和字段名不一致的问题

## 1 问题

数据库中的字段

![image-20231201190635166](C:\Users\12080\AppData\Roaming\Typora\typora-user-images\image-20231201190635166.png)

测试实体类字段不一致的情况

```java
public class User {
    private int id;
    private String name;
    private String password;
}
```

测试出现问题

![image-20231201191254226](C:\Users\12080\AppData\Roaming\Typora\typora-user-images\image-20231201191254226.png)

```java
//select * from mybatis.user where id = #{id}
//类型处理器
//select id，name，pwd from mybatis.user where id = #{id}
```



解决方法：

- 起别名

```xml
   <select id="getUserById" parameterType="int" resultType="User">
        select id,name,pwd as password from mybatis.user where id = #{id}
    </select>
```

##  2 resultMap

结果集映射

```xml
id 	name 	pwd
id 	name 	password
```

```xml
    <!--结果集映射-->
    <resultMap id="UserMap" type="User">
        <!--column数据库中的字段，property实体类中的属性-->
        <result column="id" property="id"/>
        <result column="name" property="name"/>
        <result column="pwd" property="password"/>
    </resultMap>

    <select id="getUserById" parameterType="int" resultMap="UserMap">
        select * from mybatis.user where id = #{id}
    </select>
```

- `resultMap` 元素是 MyBatis 中最重要最强大的元素
- ResultMap 的设计思想是，对简单的语句做到零配置，对于复杂一点的语句，只需要描述语句之间的关系就行了
- 你完全可以不用显式地配置它们

# 6 日志

## 6.1 日志工厂

如果一个数据库操作，出现了异常，需要排错。日志就是最好的助手

曾经：sout，debug

现在：日志工厂

![image-20231201193621283](C:\Users\12080\AppData\Roaming\Typora\typora-user-images\image-20231201193621283.png)

- SLF4J 
- LOG4J（3.5.9 起废弃）
- LOG4J2 
- JDK_LOGGING 
- COMMONS_LOGGING
- STDOUT_LOGGING  [掌握]
- NO_LOGGING

在mybatis中具体使用哪个日志，在设置中设定

**STDOUT_LOGGING标准日志输出**

```xml
 <settings>
        <setting name="logImpl" value="STDOUT_LOGGING"/>
   </settings>
```

# 7 分页

- 减少数据的处理量



## 7.1 使用Limit分页

```sql
语法：select * from  `user` limit startIndex,pageSize;
select * from  `user` limit 0,2;
```



使用Mybatis实现分页，核心SQL

​	1.接口

```java
    List<User> getUserByLimit(Map<String, Object> map);
```

​	2.Mapper.xml

```xml
    <select id="getUserByLimit" parameterType="map" resultMap="UserMap">
        select * from mybatis.user limit #{startIndex},#{pageSize}
    </select>
```

​	3.测试

```java
    @Test
    public void getUserByLimit(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("startIndex",0);
            map.put("pageSize",2);
            List<User> userList = mapper.getUserByLimit(map);
            for (User user : userList) {
                System.out.println(user);
            }
        sqlSession.close();
    }
```



## 7.2 RowBounds分页

不再使用SQL实现分页

​	1.接口

```java
List<User> getUserByRowBounds();
```

​	2.mapper.xml

```xml
  <select id="getUserByRowBounds" resultMap="UserMap">
    select * from mybatis.user
    </select>
```

​	3.测试 

```java
 @Test
    public void getUserByRowBounds(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();

        //RowBounds实现
        RowBounds rowBounds = new RowBounds(1, 2);

        //通过java代码层面实现分页
        List<User> userList = sqlSession.selectList("dao.UserMapper.getUserByRowBounds",null,rowBounds);
        for (User user : userList) {
            System.out.println(user);
        }
        sqlSession.close();
    }
```



## 7.3 分页插件

![image-20231201201626152](C:\Users\12080\AppData\Roaming\Typora\typora-user-images\image-20231201201626152.png)

# 8 使用注解开发

## 8.1 面向接口编程

大家之前都学过面向对象编程，也学习过接口，但在真正的开发中，很多时候我们会选择面向接口编程**根本原因:解耦，可拓展，提高复用，分层开发中，上层不用管具体的实现，大家都遵守共同的标准，使得开发变得容易，规范性更好**

在一个面向对象的系统中，系统的各种功能是由许许多多的不同对象协作完成的。在这种情况下，各个对象内部是如何实现自己的,对系统设计人员来讲就不那么重要了;

而各个对象之间的协作关系则成为系统设计的关键。小到不同类之间的通信，大到各模块之间的交互，在系统设计之初都是要着重考虑的，这也是系统设计的主要工作内容。面向接口编程就是指按照这种思想来编程

**关于接口的理解**

接口从更深层次的理解，应是定义 (规范，约束) 与实现 (名实分离的原则)的分离

接口的本身反映了系统设计人员对系统的抽象理解。

接口应有两类：

- 第一类是对一个 个体的抽象，它可对应为一个抽象体(abstract class);

- 第二类是对一个 个体某一方面的抽象，即形成一个抽象面 interface) ;

- 一个体有可能有多个抽象面。抽象体与抽象面是有区别的。



**三个面向区别**

- 面向对象是指，我们考虑问题时，以对象为单位，考虑它的属性及方法

- 面向过程是指，我们考虑问题时，以一个具体的流程 (事务过程)为单位，考虑它的实现
- 接口设计与非接口设计是针对复用技术而言的，与面向对象(过程)不是一个问题,更多的体现就是对系统整体的架构

## 8.2 使用注解开发

1.注解在接口上实现

```java
    @Select("select * from user")
    List<User> getUsers();
```

2.需要在核心配置文件中绑定接口

```xml
    <mappers>
         <mapper class="dao.UserMapper"/>
     </mappers>
```

3.测试



本质：反射机制实现

底层：动态代理



**Mybatis详细的执行流程**



## 8.3 CRUD

我们可以在工具类创建的时候实现自动提交事务

```java
    public static SqlSession getSqlSession(){return sqlSessionFactory.openSession(true);}
```



编写接口，增加注解

```java
    @Select("select * from user")
    List<User> getUsers();

    //方法存在多个参数，所有参数前面都必须要用@Param
    @Select("select * from user where id = #{id}")
    User getUserById(@Param("id") int id);

    @Insert("insert into user (id,name,pwd) values(#{id},#{name},#{password})")
    int addUser(User user);

    @Update("update user set name=#{name},pwd=#{password} where id = #{id}")
    int updateUser(User user);

    @Delete("delete from user where id = #{id}")
    int deleteUser(@Param("id") int id);
```

测试

注意：必须要将接口注册绑定到核心配置文件中

**关于@Param()注解**

- 基本类型的参数或者String类型，需要加上
- 引用类型不需要加
- 如果只有一个基本类型的话，可以忽略，但是建议大家都加上
- 我们在SQL中引用的就是我们这里@Param()中设定的属性名



**#{}   ￥{} 区别**

￥会被SQL注入



# 9 Lombok

使用步骤：

1.在IDEA中安装Lombok插件

2.在项目中导入lombok的jar包

3.在实体类上加注解

```java
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private int id;
    private String name;
    private String password;

}
```



```java
@Getter and @Setter
@FieldNameConstants
@ToString
@EqualsAndHashCode
@AllArgsConstructor, @RequiredArgsConstructor and @NoArgsConstructor
@Log, @Log4j, @Log4j2, @Slf4j, @XSlf4j, @CommonsLog, @JBossLog, @Flogger, @CustomLog
@Data 
@Builder
@SuperBuilder
@Singular
@Jacksonized
@Delegate
@Value
@Accessors
@Tolerate
@Wither
@With
@SneakyThrows
```



说明：

```java
@Data：无参构造，get，set，tostring，hashcode，equals
@AllArgsConstructor
@NoArgsConstructor
```



# 10 多对一处理

多对一：

![image-20231201214512821](C:\Users\12080\AppData\Roaming\Typora\typora-user-images\image-20231201214512821.png)

- 多个学生，对应一个老师
- 对于学生，**关联**  多个学生关联一个老师 【多对一】
- 对于老师，**集合**  一个老师有很多学生 【一对多】



SQL：

物理外键

![image-20231201220236533](C:\Users\12080\AppData\Roaming\Typora\typora-user-images\image-20231201220236533.png)

```java
CREATE TABLE `teacher` (
  `id` INT(10) NOT NULL,
  `name` VARCHAR(30) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8

INSERT INTO teacher(`id`, `name`) VALUES (1, '秦老师'); 

CREATE TABLE `student` (
  `id` INT(10) NOT NULL,
  `name` VARCHAR(30) DEFAULT NULL,
  `tid` INT(10) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fktid` (`tid`),
  CONSTRAINT `fktid` FOREIGN KEY (`tid`) REFERENCES `teacher` (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8INSERT INTO `student` (`id`, `name`, `tid`) VALUES ('1', '小明', '1'); 
INSERT INTO `student` (`id`, `name`, `tid`) VALUES ('2', '小红', '1'); 
INSERT INTO `student` (`id`, `name`, `tid`) VALUES ('3', '小张', '1'); 
INSERT INTO `student` (`id`, `name`, `tid`) VALUES ('4', '小李', '1'); 
INSERT INTO `student` (`id`, `name`, `tid`) VALUES ('5', '小王', '1');
```



## 测试环境搭建

1.导入lombok

2.新建实体类Teacher，Student

3.建立Mapper接口

4.建立Mapper.xml文件

5.在核心配置文件中绑定注册我们的Mapper接口或者文件

6.测试查询是否能够成功



## 按照查询嵌套处理

```xml
    <!--
        1.查询所有的学生信息
        2.根据查询出来的学生的tid，寻找对应的老师
    -->
    <select id="getStudent" resultMap="StudentTeacher">
        select * from student
    </select>
    <resultMap id="StudentTeacher" type="Student">
        <id property="id" column="id"/>
        <id property="name" column="name"/>
        <association property="teacher" column="tid" javaType="Teacher" select="getTeacher"/>
        <!--复杂的属性需要单独处理 对象：association 集合：collection -->
    </resultMap>

    <select id="getTeacher" resultType="Teacher">
        select * from teacher where id = #{id}
    </select>
```



## 按照结果嵌套处理

```xml
    <select id="getStudent" resultMap="StudentTeacher2">
        select s.id sid , s.name sname , t.name tname
        from student s,teacher t
        where s.tid = t.id
    </select>
    
    <resultMap id="StudentTeacher2" type="Student">
        <result property="id" column="sid"/>
        <result property="name" column="sname"/>
        <association property="teacher" javaType="Teacher">
            <result property="name" column="tname"/>
        </association>
    </resultMap>
```



Mysql 多对一查询方式：

- 子查询
- 联表查询



# 11 一对多处理

  比如：一个老师拥有多个学生

对应老师，就是一对多的关系



## 环境搭建

实体类

```java
public class Teacher {
    private int id;
    private String name;
    //一个老师拥有多个学生
    private List<Student> students;
}

public class Student {
    private int id;
    private String name;
    private int tid;
}

```



## 按照结果嵌套处理

```xml
 <select id="getTeacher" resultMap="TeacherStudent">
        select s.id sid ,s.name sname, t.name tname,t.id tid
        from Teacher t, student s
        where s.tid = tid and tid = #{tid}
    </select>
<resultMap id="TeacherStudent" type="Teacher">
    <result property="id" column="tid"/>
    <result property="name" column="tname"/>
    <!--集合中的泛型信息，我们用ofType获取-->
    <collection property="students" ofType="Student">
        <result property="id" column="sid"/>
        <result property="name" column="sname"/>
        <result property="tid" column="tid"/>
    </collection>
</resultMap>
```



## 按照查询嵌套处理

```xml
<select id="getTeacher2" resultMap="TeacherStudent2">
    select * from mybatis.teacher where id = #{id}
</select>
<resultMap id="TeacherStudent2" type="Teacher">
    <collection property="students"  javaType="ArrayList" ofType="Student" select="getStudentByTeacherId" column="id"/>
</resultMap>
    <select id="getStudentByTeacherId" resultType="Student">
        select * from mybatis.student where tid = #{tid}
    </select>
```



## 小结

1.关联-association  **多对一**

2.集合-collection      **一对多**

3.javaType    &    ofType

​	1.JavaType用来指定实体类中属性的类型

​	2.ofType 用来指定映射到List或者集合中的pojo类型，泛型中的约束类型



注意点：

- 保证SQL的可读性，尽量保证通俗易懂
- 注意一对多和多对一中，属性名和字段的问题
- 如果问题不好排查错误，可以使用日志



# 12 动态SQL

**什么时动态SQL：指根据不同的条件生成不同的SQL语句**

   

```xml
如果你之前用过 JSTL 或任何基于类 XML 语言的文本处理器，你对动态 SQL 元素可能会感觉似曾相识。在 MyBatis 之前的版本中，需要花时间了解大量的元素。借助功能强大的基于 OGNL 的表达式，MyBatis 3 替换了之前的大部分元素，大大精简了元素种类，现在要学习的元素种类比原来的一半还要少。

if
choose (when, otherwise)
trim (where, set)
foreach
```



## 搭建环境

```sql
CREATE TABLE `blog`(
`id` VARCHAR(50) NOT NULL COMMENT '博客id',
`title` VARCHAR(100) NOT NULL COMMENT '博客标题',
`author` VARCHAR(30) NOT NULL COMMENT '博客作者',
`create_time` DATETIME NOT NULL COMMENT '创建时间',
`views` INT(30) NOT NULL COMMENT '浏览量'
)ENGINE=INNODB DEFAULT CHARSET=utf8
```



创建一个基础工程

1.导包

2.编写配置文件

3.编写实体类

```sql
@Data
public class Blog {
    private int id;
    private String title;
    private String author;
    private Date createTime;
    private int views;
}

```

4.编写实体类对应Mapper接口和Mapper.xml文件



## IF

```xml
<select id="queryBlogIF" parameterType="map" resultType="blog">
    select * from mybatis.blog where 1=1
    <if test="title!=null">
        and title = #{title}
    </if>
    <if test="author != null">
        and author = #{author}
    </if>
```



## choose（when，otherwise）

```xml
<select id="queryBlogChoose" parameterType="map" resultType="blog">
    select * from mybatis.blog
    <where>
    <choose>
        <when test="title!=null">
             title = #{title}
        </when>
        <when test="author!=null">
            and author =#{author}
        </when>
        <otherwise>
            and views = #{views}
        </otherwise>
    </choose>
    </where>


</select>
```



## trim（where，set）

```xml
select * from mybatis.blog
    <where>
    <if test="title!=null">
        and title = #{title}
    </if>
    <if test="author != null">
        and author = #{author}
    </if>
    </where>
```

**where** **自动去掉多余的and**

```xml
  <update id="updateBlog" parameterType="map">
        update mybatis.blog
        <set>
            <if test="title!=null">
                title = #{title},
            </if>
            <if test="author!=null">
                author = #{author},
            </if>
        </set>
        where id = #{id}
    </update>
```

**set** **自动去掉多余的逗号**



所谓的动态SQL，本质还是SQL语句，只是可以在SQL层面，去执行逻辑代码

if

where，set，choose，when



## Foreach

```sql
select * from user where 1=1 and

<foreach item="id" index="index" collection="ids"
        open=" (" separator="," close=")" >
          #{item}

（id=1 or id =2 or id=3）


```

<img src="C:\Users\12080\AppData\Roaming\Typora\typora-user-images\image-20231202105616734.png" alt="image-20231202105616734" style="zoom:200%;" />







## SQL片段

有的时候，可以将一些功能的部分抽取出来，方便复用

1.使用SQL标签抽取公共的部分

```xml
    <sql id="if-title-author">
        <if test="title!=null">
            title = #{title},
        </if>
        <if test="author!=null">
            author = #{author},
        </if>
    </sql>
```

2.在需要使用的地方使用include标签引用即可

```xml
    <update id="updateBlog" parameterType="map">
        update mybatis.blog
        <set>
            <include refid="if-title-author"/>
        </set>
        where id = #{id}
    </update>
```



注意事项：

- 最好基于单表来定义SQL片段
- 不要存在where标签 









# 13 缓存

## 13.1 简介

```xml
查询：连接数据库，耗资源
	一次查询的结果给他暂存在一个可以直接取到的地方 内存：缓存
再次查询相同数据的时候，直接走缓存，不用走数据库
```



1.什么时缓存 Cache

- 存在内存中的临时数据
- 将用户经常查询的数据放在缓存(内存)中，用户去查询数据就不用从磁盘上(关系型数据库数据文件)查询，从缓存中查询，从而提高查询效率，解决了高并发系统的性能问题。

2.为什么要使用缓存

- 减少和数据库交互的次数，减少系统开销，提高系统效率

3.什么样的数据能使用缓存

- 经常查询并且不经常改变的数据



## 13.2 Mybatis缓存

- Mybatis包含一个非常强大的查询缓存特性，他可以非常方便地定制和配置缓存，缓存可以极大地提升查询效率

- Mybatis系统中默认定义了两种缓存 **一级缓存**和**二级缓存** 
  - 默认情况下，只有一级缓存开启。 (SqlSession级别的缓存，也称为本地缓存)
  - 二级缓存需要手动开启和配置，他是基于namespace级别的缓存。
  - 为了提高扩展性，MyBatis定义了缓存接口Cache。我们可以通过实现Cache接口来自定义二级缓存

## 13.3 一级缓存

- 一级缓存也叫本地缓存: SqlSession
- 与数据库同一次会话期间查询到的数据会放在本地缓存中
- 以后如果需要获取相同的数据，直接从缓存中拿，没必须再去查询数据库



缓存失效的情况：

- 查询不同的东西
- 增删改操作，可能会改变原来的数据，所有必定会刷新缓存
- 查询不同的Mapper
- 手动清除缓存

小结：一级缓存默认开启，只在一次SqlSeesion种有效，也就是拿到连接到关闭连接这个区间段

一级缓存相当于map集合

## 13.4 二级缓存

- 二级缓存也叫全局缓存，一级缓存作用域太低了，所以诞生了二级缓存。
- 基于namespace级别的缓存，一个名称空间，对应一个二级缓存;
- 工作机制
  - 一个会话查询一条数据，这个数据就会被放在当前会话的一级缓存中;
  - 如果当前会话关闭了，这个会话对应的一级缓存就没了;但是我们想要的是，会话关闭了，一级缓存中的数据被保存到二级缓存中;
  - 新的会话查询信息，就可以从二级缓存中获取内容;
  - 不同的mapper查出的数据会放在自己对应的缓存 (map) 中



步骤：

1.开启全局缓存

```xml
<setting name="cacheEnabled" value="true"/>
```

2.在要使用二级缓存的Mapper中开启

```xml
    <!--在当前Mapper.xml种使用二级缓存-->
    <cache/>

```

也可以自定义参数

```xml
<cache eviction="FIFO"
       flushInterval="60000"
       size="512"
       readOnly="true"/>
```



## 

