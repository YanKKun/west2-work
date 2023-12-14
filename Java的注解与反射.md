# 1、注解

## 1.1、什么是注解

Annotation

#### Annotation的作用：

- 不是程序本身，可以对程序作出解释（这一点和注解没什么区别）
- 可以被其他程序（比如：编译器等）读取

#### Annotation的格式：

注解是以"@注释名"在代码中存在的，还可以添加一些参数值，例如：@SuppressWarnings（value=”unchecked“） 内置注解

#### Annotation在哪里使用：

可以附加在package,class，method，field等上面，相当于给他们添加了额外的辅助信息，我们可以通过反射机制编程实现对这些元数据的访问

## 1.2、内置注解

![image-20231128192456561](C:\Users\12080\AppData\Roaming\Typora\typora-user-images\image-20231128192456561.png)

## 1.3、元注解

![image-20231128193154114](C:\Users\12080\AppData\Roaming\Typora\typora-user-images\image-20231128193154114.png)

## 1.4、自定义注解

![image-20231128194512459](C:\Users\12080\AppData\Roaming\Typora\typora-user-images\image-20231128194512459.png)

# 2.反射

## 2.1、java反射机制概述

![image-20231128202123145](C:\Users\12080\AppData\Roaming\Typora\typora-user-images\image-20231128202123145.png)

 ![image-20231128202350541](C:\Users\12080\AppData\Roaming\Typora\typora-user-images\image-20231128202350541.png)

![image-20231128202401299](C:\Users\12080\AppData\Roaming\Typora\typora-user-images\image-20231128202401299.png)



## 2.2、理解Class类并获取Class实例

![image-20231128203458976](C:\Users\12080\AppData\Roaming\Typora\typora-user-images\image-20231128203458976.png)

![image-20231128203554250](C:\Users\12080\AppData\Roaming\Typora\typora-user-images\image-20231128203554250.png)



![image-20231128204632695](C:\Users\12080\AppData\Roaming\Typora\typora-user-images\image-20231128204632695.png)



## 2.3、类的加载与ClassLoader

![image-20231128214326714](C:\Users\12080\AppData\Roaming\Typora\typora-user-images\image-20231128214326714.png)









## 2.4、创建运行时类的对象





## 2.5、获取运行时类的完整结构





## 2.6、调用运行时类的指定结构
