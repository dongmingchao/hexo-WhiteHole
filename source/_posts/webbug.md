---
title: Web的漏洞整理
date: 2018-02-19 15:35:52
tags: [Web,漏洞]
categories:
thumbnail:
---

# Web相关的漏洞简单整理
想起多少写多少。。。
1. ### PHP数组绕过

    碰到有检测参数是否为数组，可以构建`file[key]=value&file[key2]=value2`这样的报文构建payload对象来绕过

2. ### PHP序列化

    生效范围：PHP5 < 5.6.25 PHP7 < 7.0.10  
    `O:4:"Demo":1:{s:4:"data";s:15:"malicious value";}`反序列化的时候如果将第一个4改为较大的数即可绕过`__wakeup()`

3. ### PHP序列化+session

    注意以下几个函数
    ```php
    ini_set('session.serialize_handler', 'php');
    ini_set('session.serialize_handler', 'php_binary');
    ini_set('session.serialize_handler', 'php_serialize');
    ```
    在不同页面靠session传值，如果以上函数出现且设置的值不同，则可能存在该漏洞，可使用`session_encode()`函数查看不同序列化引擎的不同结果

4. ### XXE

    注意是否有类似json的交互，content-type是否可改动

5. ### 注入

    反斜杠使单引号失效的解决方法：在单引号前加%df

    ```mysql
    order by #测列数
    and 1=2 union select 1,2,3 # 测可显示列
    select table_name from infomation_schema.tables where table_schema=database() #获取表名
    select group_concat(table_name) from infomation_schema.tables where table_schema=database() #获取所有表名
    select group_concat(column_name) from infomation_schema.columns where table_name=char() #获取所有字段名
    ```

    ​

6. ### 源码泄漏

    一般网站都会有的防爬文件robots.txt

    Apache的.htaccess文件

    文件后缀.filename.swp .bak之类

    Git遗留

1. ### PHP文件包含

    如果猜测代码有include函数存在，且可以`include(any.php)`，则可以绕过php的解析读取到源码，最通常的一种做法就是使用php://filter
    例如：

    ```php
    php://filter/read=convert.base64-encode/resource=exp.php
    ```

    或者使用
    1. `data://`
    2. `php://input`
    3. 00截断
    4. ./构造长目录截断，win下>256字节，linux下>4096字节，<font color=darkred>要求php版本小于5.2.8</font>

    详细参见[这里](https://zhuanlan.zhihu.com/p/26308699)