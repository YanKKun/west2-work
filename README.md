# 这是用来存储西二作业的仓库

## Work3

### 技术栈

- mysql
- maven
- mybatis

### 项目结构

##### mysql

1.创建了两张基础表，goods和orders表，对应商品和订单

- goods增加了exist字段用于处理商品下架问题

2.创建了一张中间表，goods_orders，用于处理多对多问题

##### java

- pojo
  - Orders 订单的实体类
  - Goods 商品的实体类
  - GoodsOrders 中间表的实体类

- dao
  - Orders实体类和Goods实体类对应的mapper接口
- utils
  - MybatisUtils用于创建Sqlsession
  - myException包含了自定义异常类
  - GoodsUtils 将商品的CRUD操作放在里面了，方便使用，好根据参数抛出异常。ps：不知道怎么在mapper调用方法时增加判断条件从而抛出异常
  - OrdersUtils 同GoodsUtils
- resources
  - 各个mapper接口对应的xml文件
  - mybatis-config.xml配置文件
  - db.propeties 其中放置了连接数据库要用的数据，方便连接数据库
- test
  - GoodsTest 测试商品的CRUD
  - OrdersTest 测试订单的CRUD
  - GoodsOrdersTest  测试商品下架后订单输出商品输出

### 项目功能

- 商品和订单的CRUD
- 实现了分页查询，根据价格、下单时间排序查询
- 商品废弃后输出订单

### 项目问题

- 感觉方法有点多、有点杂
- 商品废弃应该有更好的解决方案没想出来
- 只做到了输出时排序，数据库内没排序。输出订单时订单内容排序没做好

### 项目启动

已将测试数据写在test里面，异常的数据也写了，写了结果在异常数据下，方便查看
