# 1.操作数据库（了解）

操作数据库>操作数据库中的表>操作数据库中表的数据

 ***mysql关键字不区别大小写***

## 1.1操作数据库

##### 1.创建数据库

```sql
create database [if not exists] westos;
```

##### 2.删除数据库

```sql
drop DATABSE if exists westos
```

##### 3.使用数据库

```sql
-- 如果表名或字段名是特殊字符，就需要要带` `
use school
```

##### 4.查看数据库

```sql
show databases -- 查看所有数据库
```

对比：SQLyog的可视化操作

###### 学习思路：

- 对照sqlyog可视化历史记录查看sql


- 固定语法或关键字必须强行记住



## 1.2数据库的列类型

> ##### 数值

-  tinyint    十分小的数据       1个字节

- smallint   较小的的数据      2个字节

- mediumint  中等大小的数据   3个字节

- **int             标准的整数          4个字节**

- bigint        较大的数据          8个字节

- float          浮点数                  4个字节

- double      浮点数                  8个字节 （精度问题）

- decimal     字符串形式的浮点数  金融计算的时候，用decimal

  

>##### 字符串

- char      字符串固定大小的    0-255
- **varchar   可变字符串             0-65535**
- tinytest    微型文本                  2^8-1
- **test           文本串                      2^16-1**



> ##### 时间日期

java.utill.Date



- date   YYYY-MM-DD ,日期格式
- time   HH：mm ：ss 时间格式
- **datetime    YYYY-MM-DD HH：mm ：ss  最常用**
- **timestamp  时间戳，  1970.1.1到现在的毫秒数**
- year  年份表示



> ##### null

- 没有值，未知
- **注意，不要使用NULL进行运算，结果为NULL**





## 1.3 数据库的字段属性（重点）

##### Unsigned: 

- 无符号的整数
- 不能声明为负数 



##### zerofill：

- 0填充的
- 不足的位数，使用0来填充，int（3），5 ... 005



##### 自增：

- 通常理解为自增，自动在上一条记录的基础上+1（默认）
- 通常用来设计唯一的逐渐~ index，必须是整数类型
- 可以自定义设计主键自增的起始值和步长



##### 非空 NULL not null：

- 假设设置为not null ，如果不给它赋值就会报错
- NULL，如果不填写值，默认就是null



##### 默认：

- 设置默认的值
- sex，默认值为男，如果不指定该列的值，则会有默认的值
- DBeaver设置默认值加上"  "



##### 每个表都必须存在以下五个字段。未来做项目用的，表示每一个记录存在意义。

- `version``乐观锁
- is_delete 伪删除
- id 主键
- gmt_create 创建时间
- gmt_update 修改日期



## 1.4创建数据库表（重点）



```sql
-- 注意点，使用英文（），表的名称和字段尽量用 ``括起来
-- auto_increment 自增
-- 所有的语句后面加，（英文的），最后一个不用加
-- primary key 主键，一般一个表只有一个唯一的主键
create table if not exists `Student1`(
	`id` int(4) not null auto_increment comment '学号',
	`name` varchar(30) not null default '匿名' comment '姓名',
	`pwd` varchar(20) not null default '123456' comment '密码',
	`gender` varchar(2) not null default '男' comment '性别',
	`birthday` datetime default null comment '出生日期',
	`address` varchar(100) default null comment '家庭住址',
	`email` varchar(50) default null comment '邮箱',
	primary key(`id`)
)engine innodb default charset=utf8mb4
```

###### 格式

```sql
create table[if not exists]`表名`(
	`字段名` 列类型[属性] [索引] [注释]
	`字段名` 列类型[属性] [索引] [注释]
    `字段名` 列类型[属性] [索引] [注释]
)[表类型] [字符集设置][注释]
```

## 1.5 数据表的类型

```sql
SHOW CREATE DATABASE school -- 查看创建数据库的语句
SHOW CREATE TABLE student -- 查看创建表的定义语句
DESC student -- 显示表的结构
```

```sql
-- 关于数据库引擎
INNODB 默认使用
MYISAM 早些年使用
```

|              | MYISAM      | INNODB        |
| ------------ | ----------- | ------------- |
| 事务支持     | 不支持      | 支持          |
| 数据行锁定   | 不支持 表锁 | 支持          |
| 外键约束     | 不支持      | 支持          |
| 全文索引     | 支持        | 不支持        |
| 表空间的大小 | 较小        | 较大，约为2倍 |

常规使用操作：

- MYISAM 节约空间，速度较快
- INNODB 安全性高，事务的处理，多表多用户操作

> 在物理空间存在的位置

所有的数据库文件都存在data目录下

本质还是文件的存储

MySQL 引擎在物理文件上的区别

- InnoDB 在数据库表中只有一个*.frm 文件，以及上级目录下的 ibdata1文件
- MYISAM对应文件
  - *.frm - 表结构的定义文件
  - *.MYD 数据文件 （data）
  - *.MYI 索引文件 （Index）

> 设置数据库表的字符集编码

 ```sql
 CHARSET=utf8
 ```

不设置的话，会是mysql默认的字符集编码（不支持中文）

MySQL的默认编码是Latin1，不支持中文

在my.ini中配置默认的编码

```sql
character-set-server=utf8
```

## 1.6 修改删除表

> 修改

```sql
-- 修改表名 ALTER TABLE 旧表名 RENAME AS 新表名
ALTER TABLE teacher RENAME AS teacher1
-- 增加表的字段 ALTER TABLE 表名 ADD 字段名 列属性
ALTER TABLE teacher1 ADD age INT(11)
--修改表的字段 （重命名，修改约束！）
ALTER TABLE teacher1 MODIFY age varchar(11) --修改约束
ALTER TBALE teacher1 CHANGE age age1 INT(1) --修改字段名

-- 删除表的字段 ALTER TABLE 表名 DROP 字段名
 ALTER TABLE teacher1 DROP age1
```



> 删除

```sql
-- 删除表（如果表存在再删除）
DROP TABLE IF EXISTS teacher1
```

**<u>所有的创建和删除操作尽量加上判断，以免报错</u>**



注意点：

- `` 字段名，使用这个包裹
- 注释 -- /**/
- sql 关键字大小写不敏感，建议写小写
- 所有的符号用英文

# 2. MySQL数据管理

## 2.1 外键（了解即可）

> 方式一，在创建表的时候，增加约束（麻烦，比较复杂）

```sql
create table `grade`(
	`gradeid` INT(10) not null auto_increment comment '年级id',
	`gradename` varchar(50) not null comment '年级名称',
	primary key (`gradeid`)
)engine=innodb default charset=utf8

-- 学生表的gradeid字段要去引用年级表的gradeid
-- 定义外键key
-- 给这个外键添加约束 （执行引用）
create table if not exists `Student`(
	`id` int(4) not null auto_increment comment '学号',
	`name` varchar(30) not null default '匿名' comment '姓名',
	`pwd` varchar(20) not null default '123456' comment '密码',
	`gender` varchar(2) not null default '男' comment '性别',
	`gradeid` int(10) not null comment '学生的年级',
	`birthday` datetime default null comment '出生日期',
	`address` varchar(100) default null comment '家庭住址',
	`email` varchar(50) default null comment '邮箱',
	primary key(`id`),
	key `FK_gradeid` (`gradeid`),
	constraint `FK_gradeid` foreign key (`gradeid`) references `grade`(`gradeid`)
)engine=innodb default charset=utf8
```



删除有外键关系的表的时候，必须先删除引用别人的表（从表），再删除被引用的表（主表）



> 方式二： 创建表成功后，添加外键约束

```sql
create table `grade`(
	`gradeid` INT(10) not null auto_increment comment '年级id',
	`gradename` varchar(50) not null comment '年级名称',
	primary key (`gradeid`)
)engine=innodb default charset=utf8

-- 学生表的gradeid字段要去引用年级表的gradeid
-- 定义外键key
-- 给这个外键添加约束 （执行引用）
create table if not exists `Student`(
	`id` int(4) not null auto_increment comment '学号',
	`name` varchar(30) not null default '匿名' comment '姓名',
	`pwd` varchar(20) not null default '123456' comment '密码',
	`gender` varchar(2) not null default '男' comment '性别',
	`gradeid` int(10) not null comment '学生的年级',
	`birthday` datetime default null comment '出生日期',
	`address` varchar(100) default null comment '家庭住址',
	`email` varchar(50) default null comment '邮箱',
	primary key(`id`),
	key `FK_gradeid` (`gradeid`),
	constraint `FK_gradeid` foreign key (`gradeid`) references `grade`(`gradeid`)
)engine=innodb default charset=utf8

-- 创建表的时候没有外键关系
alter table `student` 
add constraint 	`FK_gradeid` foreign key(`gradeid`) references `grade`(`gradeid`);

-- alter table 表 add constraint 约束名 foreign key（作为外键的列） refernces 哪个表 哪个字段
```

以上都是物理外键，数据库级别的外键，不建议使用（避免数据库过多造成困扰，了解即可  ）

**最佳实践**

- 数据库就是单纯的表，用来存数据，只有行（数据）和列（字段）

- 使用多张表的数据，使用外键（程序去实现）

  

## 2.2 DML语言（全部记住）

**数据库意义：**数据存储，数据管理

DML语言: 数据操作语言

- Insert
- update
- delete









## 2.3 添加

> insert

```sql
-- 插入语句（添加）
-- insert into 表名（[字段名1，字段2，...]）values（'值1')，('值2')，(...）
insert into `grade`(`gradename`) values('大四')

-- 由于主键自增我们可以省略
insert into `grade` values('大三')

-- 一般写插入语句，我们一定要数据和字段一一对应

-- 插入多个字段
insert into `grade`(`gradename`) values('大二'),('大一'),('大三')

insert into `student`(`name`,`pwd`,`gender`) values('zhangsan','aaaaaa','男')
insert into `student`(`name`,`pwd`,`gender`) values('lisi','bbbbbb','男')
insert into `student`(`name`,`pwd`,`gender`) values('wangwu','cccccc','男')
```

语法： **insert into 表名（[字段名1，字段2，...]）values（'值1')，('值2')，(...）**

注意事项：

 1.字段和字段用英文逗号隔开

 2.字段可以省略，后面的值必须一一对应，不能少

 3.可以同时插入多条数据，VALUES后面的值，需要使用逗号隔开即可 **values（'值1')，('值2')，(...）**



## 2.4 修改

> update     修改谁   （条件）    set原来的值=新值

```sql
--  修改学员名字,带了条件
update `student` set `name`='123' where id=1;

-- 不指定条件的情况下，会改动所有表
update  `student` set `name`='321';

-- 语法
-- update 表名 set colnum_name = value , [colnum_name = value,...] where []
```

条件： where 子句 运算符 id等于某个值，大于某个值，在某个区间内修改...

操作符会返回布尔值

| 操作符          | 含义         | 范围       | 结果  |
| --------------- | ------------ | ---------- | ----- |
| =               | 等于         | 5=6        | false |
| <> 或 ！=       | 不等于       | 5<>6       | true  |
| >               | 大于         |            |       |
| <               | 小于         |            |       |
| >=              | 大于等于     |            |       |
| <=              | 小于等于     |            |       |
| between a and b | 在某个范围内 | [a,b]      |       |
| and             | 我和你&&     | 5>1&&1>2   | false |
| or              | 我或你\|\|   | 5>1\|\|1>2 | true  |

```sql
-- 通过多个条件定位数据
update  `student` set `name`='133' where `name`='123' and gender ='男';
```

语法： **update 表名 set colnum_name = value , [colnum_name = value,...] where [条件]**

注意：

- colnum_name是数据库的列，尽量带上``
- 条件，筛选的条件,如果没有指定，则会修改所有的列
- value，是一个具体的值，也可以是一个变量 
- 多个设置的属性之间，用英文逗号隔开

```sql
update  `student` set `birthday`=current_date()  where `name`='133' and gender ='男';
```



## 2.5 删除

> delete

语法：delete from 表名 [where （条件）]

```sql
-- 删除数据  (避免这样写)
delete from `student`;

-- 删除数据 
delete from `student` where id=1; 
```



> TRUNCATE命令

作用： 完全清空一个数据库表，表的结构和索引约束不会变

```sql
-- 清空 student 表
truncate `student`
```



> delete的 TRUNCATE区别

- 相同点：都能删除数据，都不会删除表结构
- 不同：
  - TRUNCATE 重新设置 自增列 计数器会归零
  - TRUNCATE 不会影响事务



```sql
-- 测试delete 和 TRUNCATE 区别
create table `test`(
	`id` int(4) not null auto_increment,
	`coll` varchar(20) not null,
	primary key(`id`)


)engine=innodb default charset =utf8

insert  into `test`(`coll`) values ('1'),('2'),('3')

delete from `test` -- 不会影响自增

truncate `test`  -- 自增会归零

```

了解即可： delete删除的问题，重启数据库，现象

- innodb 自增列会从1开始（存在内存当中的，断电即失）
- MyISAM 继续从上一个自增量开始（存在文件中的，不会丢失）

# 3.DQL查询数据（最重点）

## 3.1 DQL

(Data Query LANGUAGE：数据查询语言)

- 所有的查询操作都用它 Select
- 简单的查询，复杂的查询它都能做
- **数据库中最核心的语言**
- 使用频率最高的语句



## 3.2 指定查询字段

```sql
-- 查询全部的学生   select 字段 from 表
select * from student 

-- 查询指定字段
select `studentno`,`STUDENTNAME` from student

-- 别名，给结果起一个名字 as 可以给字段起别名 可以给表起别名
select `studentno` as 学号,`STUDENTNAME` as 学生姓名 from student as 学生表

-- 函数 concat（a，b）
select concat('姓名：',studentname) as 新名字 from student 

```

语法：**select 字段，... from 表**



> 有时候，列名字不是那么的见名知意，起别名 **AS** 
>
> 字段名 as 别名   表名 as 别名





> 去重复 distinct

作用：去除select查询出来的结果中重复的数据，重复的数据只显示一条

```sql
-- 查询有哪些同学参加了考试，成绩
select * from result -- 查询全部的考试成绩
--查询有那些同学参加了考试
select `studentno` from result 
-- 发现重复数据，去重
select distinct `studentno` from result
```



> 数据库的列（表达式）

```sql
-- 查询系统版本
select version() -- （函数）
-- 用来计算
select 100*3-1 as 计算结果  -- （表达式）
-- 查询自增的步长
select @@auto_increment_increment -- （变量）

--学员考试成绩 +1分查看
select `studentno`,`studentresult`+1 as '提分后' from result
```

**数据库中的表达式：文本值，列，Null，函数，计算表达式，系统变量...**

select 表达式 from 表



## 3.3 where 条件子句

作用： 检索数据中符合条件的值

搜索的条件由一个或者多个表达式组成   结果为布尔值

> 逻辑运算符

| 运算符        | 语法                  | 描述   |
| ------------- | --------------------- | ------ |
| and    &&     | a and b      a&&b     | 逻辑与 |
| or       \|\| | a or b         a\|\|b | 逻辑或 |
| not     ！    | not a           ！a   | 逻辑非 |

尽量使用英文字母

```sql
-- 查询考试成绩在95-100之间
select studentno,`studentresult` from result 
where studentresult>=95 and  studentresult <=100

-- 模糊查询（区间）
select studentno,`studentresult` from `result`
where  studentresult between 95 and 100
 
-- 除了1000号学生之外的同学的成绩
select  studentno,`studentresult` from result 
where studentno!=1000
  
-- not
select  studentno,`studentresult` from result 
where  not  studentno =1000
```



> 模糊查询：比较运算符

| 运算符      | 语法                     | 描述                              |
| ----------- | ------------------------ | --------------------------------- |
| is null     | a is null                | 如果操作符为null，则结果为真      |
| is not null | a is not null            | 如果操作符为 not null，则结果为真 |
| between     | a between b and c        | 若a在b和c之间，则结果为真         |
| **like**    | a like b                 | SQL匹配，如果a匹配b，则结果为真   |
| **in**      | a in （a1，a2，a3，...） | 假设a在a1或者a2...则结果为真      |

```sql
-- =============== 模糊查询 =====================
select studentno,`studentresult`from result

-- 查询考试成绩在95-100之间
select studentno,`studentresult` from result 
where studentresult>=95 and  studentresult <=100

-- 模糊查询（区间）
select studentno,`studentresult` from `result`
where  studentresult between 95 and 100
 
 -- 除了1000号学生之外的同学的成绩
select  studentno,`studentresult` from result 
where studentno!=1000
  
  -- not
select  studentno,`studentresult` from result 
where  not  studentno =1000

-- =============== Like =====================
-- 查询姓刘的同学
-- like结合   %（代表0到任意个字符） _（一个字符）
select `studentno`,`studentname` from `student`
where studentname like '刘%'

-- 查询姓刘的同学，名字后面只有一个字的
select `studentno`,`studentname` from `student`
where studentname like '刘_'

-- 查询姓刘的同学，名字后面只有两个个字的
select `studentno`,`studentname` from `student`
where studentname like '刘__'-- 两个下划线

-- 查询名字中间有嘉字的同学
select `studentno`,`studentname` from `student` 
where studentname like '%嘉%'


-- ======== in（具体的一个或者多个值）=============
-- 查询1001，1002，1003号学员信息
select `studentno`,`studentname` from `student`
where studentno in(1001,1002,1003)

-- 查询在北京的学生
select `studentno`,`studentname` from `student`
where  address in ('安徽'，'河南洛阳')


-- ========= null    not null ================

-- 查询地址为空的学生 null ''
select `studentno`,`studentname` from `student`
where address is null or address=''

-- 查询有出生日期的同学 不为空
select `studentno`,`studentname` from `student`
where borndate is not null 
```



## 3.4 联表查询

> JOIN 对比

```sql
-- =========== 联表查询 join ==================

-- 查询参加考试的同学（学号，姓名，科目编号，分数）
select * from `student` 
select * from `result`

/* 思路
 * 1. 分析需求，分析查询的字段来自哪些表，（连接查询）
 * 2. 确定使用哪种连接查询  7种
 * 确定交叉点（这两个表中哪个数据是相同的）
 * 判断的条件：学生表中的studentno = 成绩表 studentno
 */

select s.`studentno`,`studentname`,`subjectno`,`studentresult`
from `student`  as s
inner join `result` as r
where s.`studentno` = r.`studentno`

-- right join
select s.`studentno`,`studentname`,`subjectno`,`studentresult`
from `student` s
right join `result` r
on s.`studentno` =r.`studentno` 

-- left join
select s.`studentno`,`studentname`,`subjectno`,`studentresult`
from `student` s
left join `result` r
on s.`studentno` =r.`studentno`  
 
```

| 操作       | 描述                                       |
| ---------- | ------------------------------------------ |
| Inner join | 如果一个表中至少有一个匹配，就返回行       |
| left join  | 会从左表中返回所有的值，即使右表中没有匹配 |
| right join | 会从右表中返回所有的值，即使左表中没有匹配 |



> 自连接

自己的表和自己的表连接，核心：**一张表拆为两张一样的表即可**

父类表

| categoryid | categoryname |
| ---------- | ------------ |
| 2          | 信息技术     |
| 3          | 软件开发     |
| 5          | 美术设计     |

子类

| pid  | categoryid | categoryname |
| ---- | ---------- | ------------ |
| 3    | 4          | 数据库       |
| 2    | 8          | 办公信息     |
| 3    | 6          | web开发      |
| 5    | 7          | ps技术       |

操作：查询父类对应的子类关系

| 父类     | 子类     |
| -------- | -------- |
| 信息技术 | 办公信息 |
| 软件开发 | 数据库   |
| 软件开发 | web开发  |
| 美术设计 | ps技术   |

```sql
CREATE TABLE `category`(
 `categoryid` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主题id',
 `pid` INT(10) NOT NULL COMMENT '父id',
 `categoryname` VARCHAR(50) NOT NULL COMMENT '主题名字',
PRIMARY KEY (`categoryid`) 
 ) ENGINE=INNODB  AUTO_INCREMENT=9 DEFAULT CHARSET=utf8; 

INSERT INTO `category` (`categoryid`, `pid`, `categoryname`) 
VALUES ('2','1','信息技术'),
('3','1','软件开发'),
('5','1','美术设计'),
('4','3','数据库'),
('8','2','办公信息'),
('6','3','web开发'),
('7','5','ps技术');

-- 查询父子信息

select a.`categoryname` as '父栏目',b.`categoryname` as '子栏目'
from `category` as a,`category` as b
where a.categoryid =b.pid

-- 查询学员所属的年级（学号，学生的姓名，年级名字）
select studentno,studentname,`gradename`
from student s
inner join `grade` g
on s.gradeid =g.gradeid 
```



## 3.5 分页和排序

> 排序

```sql
-- ==================== 分页 limit 和排序order by ===================

-- 排序：升序 ASC，降序 DESC
-- order by 通过哪个字段排序，怎么排

-- 查询参加 数据库结构-1 考试的同学（学号，姓名，科目编号，分数）
-- 根据成绩降序排序
select s.`studentno`,`studentname`,`subjectname`,`studentresult`
from student s 
inner join `result` r 
on s.studentno =r.studentno 
inner join subject s2 
on r.subjectno =s2.subjectno 
where subjectname ='数据库结构-1'
order by studentresult desc 

```

语法：**order by 字段 ASC / DESC**

> 分页

```sql
-- 为什么要分页
-- 缓解数据库压力，给人的体验更好，   瀑布流

-- 分页，每页只显示五条数据
-- 语法：limit 起始值 页面的大小
-- 网页应用：当前，总的页数，页面的大小
-- limit 0，5    1~5
-- limit 1，5    2~6
-- limit 6，5    7~12
select s.`studentno`,`studentname`,`subjectname`,`studentresult`
from student s 
inner join `result` r 
on s.studentno =r.studentno 
inner join subject s2 
on r.subjectno =s2.subjectno 
where subjectname ='数据库结构-1'
order by studentresult desc 
limit 0，5


-- 第一页   limit 0，5
-- 第二页   limit 5，5
-- 第三页   limit 10，5
-- 第N页    limit 5*（N-1），5 
-- 【pagesize：页面大小，，n，当前页】
-- 【（n-1）*pagesize：起始值】
-- 【n:当前页】
-- 【数据总数/页面大小 = 总页数】

```

语法：**limit 起始值，步数**



## 3.6 子查询

where（这个值是计算出来的）

本质：在where语句中嵌套一个子查询语句

where（select * from）

```sql
-- ======================= where =====================

-- 1. 查询数据库结构-1 的所有考试结果（学号，科目编号，成绩），降序排列
-- 方式一：使用连接查询
select  r.`studentno`,r.`subjectno`,`studentresult`
from `result` r
inner join subject s 
on r.subjectno =s.subjectno 
where subjectname='查询数据库结构-1'
order by studentresult desc 

-- 方式二： 使用子查询（由里及外）
select studentno,subjectno,studentreuslt
from `result` r 
where studentno =(
select subjectno  from subject where subjectname = '所有数据库结构-1'
)
order by studentresult DESC
-- 查询所有数据库结构-1 的学生学号


-- 分数不小于80分的学生的学号和姓名
select distinct s.studentno,studentname
from student s 
inner join `result` r 
on r.studentno =s.studentno 
where studentresult >=80

-- 在这个基础上增加一个科目，高等数学-2  查询高等数学-2 的编号
select distinct s.studentno,studentname
from student s 
inner join `result` r 
on r.studentno =s.studentno 
where studentresult >=80 and subjectno = (
select subjectno from subject s2  
where subjectname = '高等数学-2'
)
```



## 3.7 分组和过滤

```sql
-- 查询不同课程的平均分，最高分，最低分,平均分大于80分
-- 核心：根据不同的课程分组
select subjectname,avg(studentresult),max(studentresult),min(studentresult)  
from `result` r 
inner join subject s 
on r.subjectno =s.subjectno
group by r.subjectno 
having avg(studentresult)>=80 
```



# 4,MySQL函数

官网地址：[MySQL ：： MySQL 8.2 参考手册 ：： 12.1 内置函数和运算符参考](https://dev.mysql.com/doc/refman/8.2/en/built-in-function-reference.html)

## 4.1 常用函数

```sql
-- ===================常用函数======================

-- 数学运算
select abs() -- 绝对值
select ceiling elling() -- 向上取整
select floor()  -- 向下取整
select rand()  -- 放回一个0~1之间的随机数
select sign(-10) -- 判断一个数的符号 负数返回-1 正数返回1

-- 字符串函数
select char_length('JISHI') -- 字符串长度 
select concat('1','2','43') -- 拼接字符串
select insert ('我爱hello',1,2,'chaojiai') -- 查询，从某个位置开始替换某个长度
select lower('KKKOQ')  -- 转小写字母
select upper('adsasQS') -- 转大写字母
select instr('qwer','w') -- 返回第一次出现子串的索引  
select replace('qwer','qw','er') -- 替换出现的指定字符串
select substr('qwerty',2,3) -- 返回指定的子字符串 (源字符串，截取的位置，截取的长度)
select reverse('1234') -- 反转 

-- 查询姓周的同学，名字改为邹
select replace(studentname,'周','邹')  from student
where studentname like '周%'

-- 时间和日期函数（重要）
select current_date() -- 获取当前日期 
select curdate() -- 获取当前日期
select now() -- 获取当前时间 
select localtime() -- 本地时间
select sysdate() -- 系统时间  

-- 系统
select  user() -- 用户
select  version() 
```



## 4.2 聚合函数（常用）

| 函数名  | 描述   |
| ------- | ------ |
| count() | 计数   |
| sum()   | 求和   |
| avg()   | 平均值 |
| max()   | 最大   |
| min()   | 最小   |
| ....... |        |



```sql
-- ================= 聚合函数 ======================
-- 都能统计 表中的数据 查询一个表中有多少个记录就使用count
select count(studentname) from student;  -- count（字段）会忽略所有的null值
select count(*) from student;  -- count(*) 不会忽略null值 本质 计算行数
select count(1) from result;   -- count(1) 不会忽略null值 本质 计算行数


select sum(studentresult) as 总和 from result
select avg(studentresult) as 平均分 from result
select max(studentresult) as 最高分 from result
select min(studentresult) as 最低分 from result

```



## 4.3 数据库级别的MD5加密

什么是MD5：

主要是增强算法复杂度和不可逆性

MD5不可逆，具体值和md5是一样的

MD5破解网站的原理，背后有一个字典，MD5加密后的值

```sql
-- ============测试 MD5加密================
create TABLE `testmd5`(
	`id` int(4) NOT NULL,
	`name` varchar(20) NOT NULL,
	`pwd` varchar(50) NOT NULL,
	PRIMARY key(`id`)
)engine=innodb DEFAULT charset=utf8

-- 明文密码
insert  into testmd5 value (1,'zhangsan','123456'),(2,'zhangsan','123456'),(3,'zhangsan','123456')

-- 加密
update testmd5 set pwd=MD5(pwd) -- 加密全部的密码

-- 插入的时候加密
insert  into testmd5 value (4,'zhangsan',MD5('123456'))

-- 如何校验：将用户传递进来的密码，进行MD5加密，然后比对加密后的值
select * from testmd5 where `name`='zhangsan' and pwd = md5('123456')

```



# 5. 事务

## 5.1 什么是事务

**要么都成功**，**要么都失败**

-----

1. SQL执行 A 给 B 转账   A 1000 --> 200  B 200
2. SQL执行 B 收到 A 的钱  A 800   -->  B 400

---------

将一组SQL放在一个批次去执行

> 事务原则：ACID 原则   原子性，一致性，隔离性，持久性  （脏读，幻读...）

CNDS



> 隔离导致的问题

脏读

不可读

虚读



> 执行事务

```sql
-- ================= 事务 ==================
-- mysql 是默认开启事务自动提交的
set autocommit = 0; -- 关闭
set autocommit = 1; -- 开启（默认）

-- 手动处理事务
set autocommit =0 -- 关闭自动条件

-- 事务开启
start transaction  -- 标记一个事务的开始，从这个之后的sql都在同一个事务内

insert xx
insert xx

-- 提交：持久化（成功）
commit
-- 回滚：回到原来的样子（失败）
rollback

-- 事务结束
set autocommit =1 -- 开启自动提交

-- 了解
savepoint 保存点名 -- 设置一个事务的保存点
rollback to savepoint 保存点名 -- 回滚到保存点
release savepoint 保存点名 -- 撤销保存点
```



> 模拟场景
>

```sql
-- 转账
create database shop character set utf8 collate utf8_general_ci
use shop

create table `account`(
	`id` int(3) not null auto_increment,
	`name` varchar(30) not null,
	`money` decimal(9,2) not null,
	primary key(`id`)

)engine=innodb default charset=utf8

insert into account (`name`,`money`)
values ('A','2000'),('B','10000')

-- 模拟转账：事务
set autocommit = 0; -- 关闭自动提交
start transaction -- 开启一个事务 
update account set money = money-500 where `name` = 'A'
update account set money = money+500 where `name` = 'B'

commit; --  提交事务
rollback; -- 回滚

set autocommit =1;
```

 

# 6. 索引

> MVSOL官方对索引的定义为: **索引(ndex) 是帮助MVSOL高效获取数据的数据结构**，提取句子主千，就可以得到索引的本
> 质:索引是数据结构

## 6.1 索引的分类

> 在一个表中，主键索引只能有一个，唯一索引可以有多个

- 主键索引  （Primary key）
  - 唯一的标识，不可重复，只能有一个列作为主键

- 唯一索引  （ unique key） 
  - 避免重复的列出现，唯一索引可以重复，多个列都可以标识为唯一索引

- 常规索引  （key/index）
  - 默认的，index/key 关键字来设置

- 全文索引  （fulltext）
  - 在特定的数据库引擎下才有
  - 快速定位数据



基础语法

```sql
-- 索引的使用
-- 1.在创建表的时候给字段增加索引
-- 2.创建完毕后，增加索引 

-- 显示所有的索引信息
show index from student
 
-- 增加一个全文索引 (索引名)（列名）
alter table school.`student` add fulltext index `studentname`(`studentname`);

-- explain 分析sql执行的状况
explain select * from student; -- 非全文索引 

select * from student where match(studentname) against('刘')
```



## 6.2 测试索引

```sql
DROP FUNCTION IF EXISTS mock_data;
-- 写函数之前必须要写，标志:$$
DELIMITER $$
CREATE FUNCTION mock_data()
returns INT DETERMINISTIC
-- 注意returns，否则报错。
BEGIN
DECLARE num INT DEFAULT 1000000;
-- num 作为截止数字，定义为百万，
DECLARE i INT DEFAULT 0;
WHILE i<num DO
   INSERT INTO `app_user`(`name`,`email`,`phone`,`gender`,`password`,`age`)VALUES(CONCAT('用户',i),'19224305@qq.com',CONCAT('13', FLOOR(RAND()*(999999999-100000000)+100000000)),FLOOR(RAND()*2),UUID(), FLOOR(RAND()*100));
   SET i = i + 1;
END WHILE;
RETURN i;
END;
SELECT mock_data(); -- 执行此函数 生成一百万条数`app_user`
```



## 6.3 索引原则

- 索引不是越多越好
- 不要对经常变动的数据加索引
- 小数据量的表不需要加索引
- 索引一般加在常用于查询的字段上



> 索引的数据结构

Hash 类型的索引

Btree：inndb默认的数据结构

[索引底层解读](http://blog.codinglabs.org/articles/theory-of-mysql-index.html)



# 7.权限管理和备份

## 7.1 用户管理

> SQL yog 可视化管理

![image-20231124173855753](C:\Users\12080\AppData\Roaming\Typora\typora-user-images\image-20231124173855753.png)

> SQL 命令操作

用户表： mysql.user

本质：对这张表进行增删改查

## 7.2 MySQL备份

拷贝文件

# 8.规范数据库设计

## 8.1 为什么需要设计

**当数据库比较复杂就需要设计**

#### 糟糕的数据库设计：

- 数据冗余，浪费空间
- 数据库插入和删除都会麻烦，异常 【屏蔽物理外键】
- 程序的性能差

#### 良好的数据库设计

- 节省内容空间
- 保证数据库的完整性
- 方便开发系统

#### 软件开发中，关于数据库的设计

- 分析需求：分析业务的需要处理的数据库的需求
- 概要设计：设计关系图E-R图



#### 设计数据库的步骤：（个人博客）

- 收集信息，分析需求
  - 用户表（用户登录注销，用户的个人信息，写博客，创建分类）
  - 分类表（文章分类，谁创建的 ）
  - 文章表（文章的信息）
  - 评论表
  - 友链表（友链信息）
  - 自定义表（系统信息，某个关键的字，或者一些主字段） key： value
  - 说说表（发表心情.. id... content...create_time）
- 标识实体（把需求落实）
- 标识实体 之间的关系
  - 写博客：user --> blog
  - 创建分类：user --> category
  - 关注：user --> user
  - 友链：links
  - 评论：user -->user-blog

## 8.2 三大范式

#### 为什么需要数据规范化

- 信息重复
- 更新异常
- 插入异常
  - 无法正常显示信息
- 删除异常
  - 丢失有效的信息



> 三大范式

#### 第一范式（1NF）

原子性：保证每一列不可再分

要求数据库表的每一列都是不可分割的原子数据项

#### 第二范式（2NF）

前提：满足第一范式

每张表只描述一件事情

#### 第三范式（3NF）

前提：满足第一范式和第二范式

第三范式需要确保数据表中的每一列都和主键直接相关，而不能间接相关



（规范数据库的设计）

**规范性和性能的问题**

关联查询的表不得超过三张表

- 考虑商业化的需求和目标，（成本，用户体验）
- 在规范性能的问题的时候，需要适当考虑规范性
- 故意给某些表增加一些冗余的字段。（从多表查询变成单表查询）
- 故意增加一些计算列（从大数据量降低为小数据量的查询：索引） 

# 9. JDBC（重点）

## 9.1 数据库驱动

程序通过数据库驱动，和数据库打交道

 

## 9.2 JDBC

SUN公司为了简化开发人员的（对数据库的同意）操作，提供了一个（java数据库）规范，俗称JDBC

这些规范的实现由具体的厂商去做

对于开发人员来说，只需要掌握JDBC接口的操作即可

 需要导入数据库驱动包 mysql-connector-java



## 9.3 第一个JDBC程序

> 创建测试数据库

```sql
CREATE DATABASE `jdbcStudy` CHARACTER SET utf8 COLLATE utf8_general_ci;

USE `jdbcStudy`;

CREATE TABLE `users`(
 `id` INT PRIMARY KEY,
 `NAME` VARCHAR(40),
 `PASSWORD` VARCHAR(40),
 `email` VARCHAR(60),
 birthday DATE
);

 INSERT INTO `users`(`id`,`NAME`,`PASSWORD`,`email`,`birthday`)
VALUES('1','zhangsan','123456','zs@sina.com','1980-12-04'),
('2','lisi','123456','lisi@sina.com','1981-12-04'),
('3','wangwu','123456','wangwu@sina.com','1979-12-04')
```

1.创建一个普通项目

2.导入数据库驱动

3.编写测试代码



> DriverManager

```java
//1. 加载驱动
Class.forName("com.mysql.cj.jdbc.Driver");//固定写法
connection = DriverManager.getConnection(url, username, password);

// connection 代表数据库
// 数据库设置自动提交
// 事务提交
// 事务回滚
connection.rollback();
connection.commit();
connection.setAutocommit():
```



> URL

```java
 String url = "jdbc:mysql://localhost:3306/jdbcstudy?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";

// mysql -- 3306
//协议： //主机地址：端口号/数据库名？参数1&参数2&参数3

// oralce -- 1521
//jdbc：oracle：thin：@?localhost：1521：sid
```



> Statement执行SQL的对象    PrepareStatement执行SQL的对象

```java
String sql = "select * from users"; // 编写SQL

statement.executeQuery();//查询操作返回ResultSet
statement.execute();//执行任何SQL
statement.executeUpdate();// 更新、插入、删除。都是用这个返回一个受影响的行数
```



> ResultSet查询的结果集：封装了索引的查询结果

获得指定的数据类型

```java
resultSet.getObject();// 在不知道列类型的情况下使用
// 如果知道列的类型就使用指定的类型
resultSet.getString();
resultSet.getInt();
resultSet.getFloat();
resultSet.getDouble();
....
```

遍历，指针

```java
resultSet.beforeFirst();// 移动到最前面
resultSet.afterLast();// 移动到最后面
resultSet.next();// 移动到下一个数据
resultSet.previous();//移动到前一行
resultSet.absolute(row);//移动到指定行
```



> 释放资源

```java
 //6.释放连接
resultSet.close();
statement.close();
connection.close();
```



## 9.4 statement对象

**Jdbc中的statement对象用于向数据库发送SQL语句，想完成对数据库的增删改查，只需要通过这个对象向数据库发送增删改查语句即可。**

Statement对象的executeUpdate方法，用于向数据库发送增、删、改的sql语句，executeUpdate执行完后，将会返回一个整数 (即增删改语句导致了数据库几行数据发生了变化)。

Statement.executeQuery方法用于向数据库发送查询语句，executeQuery方法返回代表查询结果的ResultSet对象.



> CRUD操作-create

使用executeUpdate(String sql)方法完成数据添加操作

```java
Statement st = conn.createStatement();
String sql = "insert into user(...) value(...)";
int num = st.executeUpdate(sql);
if(num>0){
    sout("插入成功");
}
```



> CRUD操作-delete

使用executeUpdate(String sql)方法完成数据删除操作

```java
Statement st = conn.createStatement();
String sql = "delete from user where id=1";
int num = st.executeUpdate(sql);
if(num>0){
    sout("删除成功");
}
```



> CRUD操作-update

使用executeUpdate(String sql)方法完成数据修改操作

```java
Statement st = conn.createStatement();
String sql = "update user set name='' where name='' ";
int num = st.executeUpdate(sql);
if(num>0){
    sout("修改成功");
}
```



> CRUD操作-read

使用executeQuery(String sql)方法完成数据查询操作

```java
Statement st = conn.createStatement();
String sql = "select * from user  where id =1 ";
ResultSet rs = st.executeQuery(sql);
while(rs.next()){
    //根据获取列的数据类型，分别调用rs的相应方法映射到java对象中
}
```



> 代码实现

1.提取工具类

2.编写增删改的方法， executeUpdate

#### 1、Insert

```java
package demo2.utils;

import demo2.JdbcUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TestInsert {
    public static void main(String[] args) {
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;

        try {
           conn= JdbcUtils.getConnection();//获取数据库连接
           st = conn.createStatement();//获取SQL的执行对象
            String sql = "insert into users (`id`,`NAME`,`PASSWORD`,email,birthday)" +
                    "value(4,'xiaoming','123456','123456@qq.com','2020-10-1')";
            int i =st.executeUpdate(sql);
            if(i>0){
                System.out.println("charuchengong");
            }
        } catch (SQLException e) {
           e.printStackTrace();
        } finally {
            JdbcUtils.release(conn,st,rs);
        }
    }
}

```

#### 2、Delete

```java
package demo2.utils;

import demo2.JdbcUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TestDelete {
    public static void main(String[] args) {
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;

        try {
            conn= JdbcUtils.getConnection();//获取数据库连接
            st = conn.createStatement();//获取SQL的执行对象
            String sql = "delete from  users where id=4;";
            int i =st.executeUpdate(sql);
            if(i>0){
                System.out.println("charuchengong");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.release(conn,st,rs);
        }
    }
}

```

#### 3、Update

```java
package demo2.utils;

import demo2.JdbcUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TestUpdate {
    public static void main(String[] args) {
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;

        try {
            conn= JdbcUtils.getConnection();//获取数据库连接
            st = conn.createStatement();//获取SQL的执行对象

            String sql = "update users set `NAME` ='xiaohong',`email` ='12312312@qq' where id=1";

            int i =st.executeUpdate(sql);
            if(i>0){
                System.out.println("更新成功");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.release(conn,st,rs);
        }
    }
}

```

#### 4、Read

```java
package demo2.utils;

import demo2.JdbcUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TestSelect {
    public static void main(String[] args) {
        Connection conn =null;
        Statement st = null;
        ResultSet rs = null;

        try {
            conn = JdbcUtils.getConnection();
            st = conn.createStatement();

            //SQL
            String sql = "select * from users where id = 1";

            rs = st.executeQuery(sql);//查询完毕会返回一个结果集

            if(rs.next()){
                System.out.println(rs.getString("NAME"));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            JdbcUtils.release(conn,st,rs);
        }


    }
}

```



> SQL注入的问题

sql存在漏洞，会被攻击导致数据泄露，SQL会被拼接



## 9.5 PreparedStatement对象

PreparedStatement可以防止SQL注入。效率更高。



1.新增

```java
package demo3;

import demo2.JdbcUtils;
import java.util.Date;
import java.sql.*;

public class TestInsert {
    public static void main(String[] args) {
        Connection conn =null;
        PreparedStatement st =null;


        try {
          conn =  JdbcUtils.getConnection();

            //区别
            //使用？占位符代替参数
            String sql = " insert into users(id,NAME,PASSWORD,email,birthday) values(?,?,?,?,?)";

            st = conn.prepareStatement(sql);//预编译SQL

            //手动参数赋值
            st.setInt(1,4);
            st.setString(2,"xiaogang");
            st.setString(3,"123456");
            st.setString(4,"123456@qq.com");
            //  sql.Date 数据库
            //  util.Date java new Date.().getTime()；
            st.setDate(5,new java.sql.Date(new Date().getTime()));

            //执行
            int i = st.executeUpdate();
            if(i>0){
                System.out.println("插入成功");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            JdbcUtils.release(conn,st,null);
        }
    }
}

```

2.删除

```java
package demo3;

import demo2.JdbcUtils;
import java.util.Date;
import java.sql.*;

public class TestDelete {
    public static void main(String[] args) {
        Connection conn =null;
        PreparedStatement st =null;


        try {
            conn =  JdbcUtils.getConnection();

            //区别
            //使用？占位符代替参数
            String sql = "Delete from users where id =? ";

            st = conn.prepareStatement(sql);//预编译SQL

            //手动参数赋值
            st.setInt(1,4);

            //执行
            int i = st.executeUpdate();
            if(i>0){
                System.out.println("删除成功");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            JdbcUtils.release(conn,st,null);
        }
    }
}

```

3.更新

```java
package demo3;

import demo2.JdbcUtils;

import java.sql.*;

public class TestUpdate {
    public static void main(String[] args) {
        Connection conn =null;
        PreparedStatement st =null;


        try {
            conn =  JdbcUtils.getConnection();

            //区别
            //使用？占位符代替参数
            String sql = "update users set  NAME =? where id =?  ";

            st = conn.prepareStatement(sql);//预编译SQL

            //手动参数赋值
            st.setString(1,"小红");
            st.setInt(2,1);
            //执行
            int i = st.executeUpdate();
            if(i>0){
                System.out.println("更新成功");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            JdbcUtils.release(conn,st,null);
        }
    }
}

```

4.查询

```java
package demo3;

import demo2.JdbcUtils;
import java.util.Date;
import java.sql.*;

public class TestSelect {
    public static void main(String[] args) {
        Connection conn =null;
        PreparedStatement st =null;
        ResultSet rs= null;

        try {
            conn =  JdbcUtils.getConnection();

            //区别
            //使用？占位符代替参数
            String sql = "Select * from users where id =? ";

            st = conn.prepareStatement(sql);//预编译SQL

            //手动参数赋值
            st.setInt(1,2);

            //执行
            rs = st.executeQuery();
            while(rs.next()){
                System.out.println(rs.getString("NAME"));
            }

        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            JdbcUtils.release(conn,st,rs);
        }
    }
}

```

5.防止SQL注入

```java
package demo2.utils;

import demo2.JdbcUtils;

import java.sql.*;

public class SQL注入 {
    public static void main(String[] args) {
        //login("xiaohong","123456");
        login("lisi","123456");


    }

    //登录业务
    public static void login(String username,String password){
        Connection conn =null;
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            conn = JdbcUtils.getConnection();
            // PreparedStatement 防止SQL注入的本质，把传递进来的参数当作字符
            // 假设其中存在转义字符，直接忽略
            //SQL
            String sql = "select * from users where `NAME`= ?and `PASSWORD`=?";

            st = conn.prepareStatement(sql);
            st.setString(1,username);
            st.setString(2,password);


            rs = st.executeQuery();//查询完毕会返回一个结果集

            while (rs.next()){
                System.out.println(rs.getString("NAME"));
                System.out.println(rs.getString("password"));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            JdbcUtils.release(conn,st,rs);
        }

    }
}

```



## 9.7 IDEA连接数据库

用database插件

## 9.8 事务

要么都成功，要么都失败

> ACID原则

原子性：要么全部完成，要么都不完成

一致性：总数不变

**隔离性：多个进程互不干扰**

持久性：一旦提交不可逆，持久化到数据库



隔离性的问题：

脏读：一个事务读取了另一个没有提交的事务

不可重复读：在同一个事务内重复读取表中的数据，表数据发生了改变

虚读（幻读）：在一个事务内，读取到了别人插入的数据，导致前后读出来结果不一致



> 代码实现

1、开启事务 conn.setAutoCommit(false);

2、一组业务执行完毕，提交事务

3、可以在catch语句中显示的定义，回滚语句，但默认失败就回滚

```java
package demo4;

import com.mysql.cj.jdbc.JdbcConnection;
import demo2.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TestTransaction1 {
    public static void main(String[] args) {
        Connection conn =null;
        PreparedStatement st =null;
        ResultSet rs =null;

        try{
            conn = JdbcUtils.getConnection();
            //关闭数据库的自动提交，自动会开启事务
            conn.setAutoCommit(false);//开启事务


            String sql1 ="update account set money = money - 100 where name ='A'";
            st =conn.prepareStatement(sql1);
            st.executeUpdate();

            String sql2 ="update account set money = money + 100 where name ='B'";
            st = conn.prepareStatement(sql2);
            st.executeUpdate();

            //业务完成，提交事务
            conn.commit();
            System.out.println("GOOD");

        }catch (SQLException e){
           try {
               conn.rollback();
           }catch (SQLException e1){
               e1.printStackTrace();
           }
           e.printStackTrace();
        }finally {
            JdbcUtils.release(conn,st,rs);
        }


    }
}

```



## 9.9 数据库连接池

数据库连接 --- 执行完毕 --- 释放

连接 --- 释放 十分浪费系统资源

**池化技术：准备一些预先的资源，过来的就连接预先准备好的**

常用连接数 10个

最小连接数：10

最大连接数：15   业务最高承载上限

等待超时：100ms



编写连接池，实现一个接口 DataSource



> 开源数据源实现  拿来即用

DBCP

C3P0

Druid：阿里巴巴



使用了这些数据库连接池之后，我们在项目开发中就不需要编写连接数据库的代码

