---
title: WAF绕过
date: 2018-03-14 14:55:27
tags:
categories:
thumbnail:
---





内网攻击

更改POST的content-type  x-www-formencoded  — mutipart/data

写两行 Content-Disposition，后面的语句会生效

改数据包一次上传两个文件

Content=Disposition； name=“file”；；filename=“xx.php”，应该是apache，tomcat才有

filename部分的错误：去掉引号，只留一个引号，单引号

#### SQL注入

##### 空白符绕过

```regex
union\s+select
/\*\w+\*/
```

可当成空格来用：%09 0A 0B 00 20 0C A0 /**/

正则表达式空白符： %09 0A 0B 00 20

##### 函数分隔符绕过

concat%250C()

##### 注释绕过

#### 路径绕过

多个../会被拦截，使用./与//与../交替

#### 文件目录扫描绕过

