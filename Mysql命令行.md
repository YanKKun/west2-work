# Mysql命令行

## 1.命令行连接

命令行连接

```sql
-u root -p123456--连接数据库mysg1

update mysql.user set 

authentication_string=password('123456') where 

user='root' and Host =Tocalhost'; 
  -- 修改用户密码

flush privileges;-- 刷新权限

-- 所有的语句都使用:结尾

show databases;-- 查看所有的数据库

mysq1> use schoo7-- 切换数据库 use 数据库名

Database changed

show tables; -- 查看数据库中所有的表

describe student; -- 显示数据库中所有的表的信息

create database westos; -- 创建一个数据库

exit;--退出连接

-- 单行注释 (SOL 的本来的注释)

```

