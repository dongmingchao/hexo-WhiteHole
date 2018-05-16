---
title: Python学习笔记
date: 2017-12-02 00:56:26
tags: [Python,笔记]
categories: Python
thumbnail: http://dedsec.club/forhexo/img/python/python-logo.png
---

<h1 id="head"><font face="华文彩云">Python学习笔记 <font size=2>by DedSec</font></font></h1>
<font color=red>2017/1/22 23:59:31</font>  

------

<font color=apple face="楷体"><center>重要变量类型</center></font>

| 父类                 | 子类                                     | <center>特性</center> |
| ------------------ | -------------------------------------- | ------------------- |
| <b>[序列](#iter)     | 可索引使用的数据(组)                            |                     |
| <b>[字典](#dict)     | 唯一的<b><font color=gree>映射类型</font></b> | 视为无序【按照内部优先级排序】     |
| <b>[函数](#function) |                                        | 实现某一功能的算法           |
| 序列                 | <b>[列表](#list)                         | 可以灵活混合各种类型的数据组      |
| 序列                 | <b>[元组](#tuple)                        | 数据不能改变的列表           |
| 序列                 | <b>[字符串](#str)                         | 与元组类似               |

------

<font color=apple face="楷体"><center>全局关键字</center></font>

|                      |                     |
| -------------------- | ------------------- |
| <b>[def](#def)       | def(ine)声明一个函数      |
| <b>[lambda](#lambda) | 类似#define，建立无名函数并返回 |

------

<font color=apple face="楷体"><center>全局方法</center></font>


|                函数名                 | 功能                                       |
| :--------------------------------: | ---------------------------------------- |
|     <b>[help(function)](#help)     | 显示函数相关帮助文档                               |
|       <b>[type(any)](#_type)       | 返回该参数的类型                                 |
|                dir                 | 返回一个数组，包含所有的属性和方法名                       |
|              \__doc__              | 返回一个字符串，包含有关的帮助                          |
|                vars                | 返回一个字典，包含所有的属性的详细信息                      |
|         <b>[range](#range)         | 一个从指定数到指定数的序列                            |
|   <b>[sorted(iterable)](#sorted)   | 返回从大到小排好序的<b><font color=gree>列表，<font color=red>参数类型必须单一，可比</font> |
| <b>[reversed(iterable)](#reversed) | 返回与源列表反转之后的<b><font color=gree>序列        |
|           <b>[zip](#zip)           | 将两个序列合并，返回一个多个元组的集合(zip类对象)              |

------

<h4 id="lambda"><font color=darkturquoise>lambda</font>[🔙](#head)</h4>

```python
>>> lambda x:x**2
<function <lambda> at 0x04433810>//返回一个函数
>>> f=lambda x:x**2
>>> f(5)
25
```

<h4 id="def"><font color=darkturquoise>def</font><b>[🔙](#head)</h4>

```python
>>> def f(x):
	return x**2

>>> f(5)
25
```

<h4 id="sorted"><font color=darkturquoise>sorted(iterable)</font>[🔙](#head)</h4>

```python
>>> x=[6,5,1,2,3,4,8]
>>> sorted(x)
[1, 2, 3, 4, 5, 6, 8]
```

<h4 id="reversed"><font color=darkturquoise>reversed(iterable)</font>[🔙](#head)</h4>

```python
>>> x=[1,3.14,'string']
>>> reversed(x)
<list_reverseiterator object at 0x03BB7510>//返回一个序列
>>> list(reversed(x))
['string', 3.14, 1]
```

<h4 id="zip"><font color=darkturquoise>zip(iterable1,iterable2...)</font>[🔙](#head)</h4>

```python
>>> t1=(1,2,3,4)
>>> t2=(5,6,7,8)
>>> zip(t1,t2)
<zip object at 0x0355F1C0>//返回一个zip
>>> tuple(zip(t1,t2))
((1, 5), (2, 6), (3, 7), (4, 8))
>>> t3=(9,10,11,12,13)
>>> tuple(zip(t1,t2,t3))
((1, 5, 9), (2, 6, 10), (3, 7, 11), (4, 8, 12))
```

<h4 id="range"><font color=darkturquoise>range(start_integer,stop_integer[,step_integer])</font>[🔙](#head)</h4>

```python
>>> range(10)
range(0, 10)             //返回一个range
>>> x=range(10)
>>> type(x)
<class 'range'>
```

> <font color=darkblue face="宋体">range_obj.start</font>
> <font color=gray face="华文隶书">返回首数</font>  

```python
>>> x.start
0
```

> <font color=darkblue face="宋体">range_obj.stop</font>
> <font color=gray face="华文隶书">返回尾数</font>  

```python
>>> x.stop
10
```

> <font color=darkblue face="宋体">range_obj.count(integer)</font>
> <font color=gray face="华文隶书">查询一个数,若不在range中返回0,存在返回1</font>  

```python
>>> x.count(10)
0
>>> x.count(0)
1
```

> <font color=darkblue face="宋体">range_obj.step</font>
> <font color=gray face="华文隶书">返回步长</font>  

```python
>>> y=range(2,10,2)
>>> y.step
2
```

> <font color=darkblue face="宋体">range_obj.index(integer)</font>
> <font color=gray face="华文隶书">根据<a>`元素`</a>查对应<a>`编号`</a></font>  

```python
>>> list(y)
[2, 4, 6, 8]
>>> y.index(8)
3
```

> <font color=darkblue face="宋体">range_obj[integer]</font>
> <font color=gray face="华文隶书">根据<a>`编号`</a>查对应<a>`元素`</a></font>  

```python
>>> y[1]
4
```

------

<h4 id="list"><font color=orange>列表(list)</font>[🔙](#head)</h4>

> <font color=darkblue face="宋体">list(iterable)</font>
> <font color=gray face="华文隶书">序列转为列表</font>  
> <font color=darkblue face="宋体">list.sort(list_obj)</font>
> <font color=gray face="华文隶书">按从小到大排序</font>  
> <font color=darkblue face="宋体">list.reverse(list_obj)</font>
> <font color=gray face="华文隶书">反转列表</font>

```python
>>> x=[2,3,1,4,5,7,6,1,2,0]
>>> list.sort(x)
>>> x
[0, 1, 1, 2, 2, 3, 4, 5, 6, 7]
```

> <font color=darkblue face="宋体">list.insert(list_obj,index,object)或者list_obj.insert(index,object)</font>
> <font color=gray face="华文隶书">插入一个元素</font>

```python
>>> x=['one',1,0.1]
>>>> list.insert(x,2,2)
>>> x
['one', 1, 2, 0.1]
>>> x.insert(1,'insert')
>>> x
['one', 'insert', 1, 2, 0.1]
```

> <font color=darkblue face="宋体">list_obj.remove(value)</font>
> <font color=gray face="华文隶书">从列表中去除一个值</font> 

```python
>>> x.remove(2)
>>> x
['one', 'insert', 1, 0.1]
```

> <font color=darkblue face="宋体">list_obj.extend(iterable)</font>
> <font color=gray face="华文隶书">从尾部追加一个序列</font>

```python
>>> y='hello'
>>> x.extend(y)
>>> x
['one', 'insert', 1, 0.1, 'h', 'e', 'l', 'l', 'o']
```

> <font color=darkblue face="宋体">list_obj.pop(index)</font>
> <font color=gray face="华文隶书">踢出一个元素(默认从尾部)，返回那个元素</font>

```python
>>> x.pop()
'h'
>>> x
['one', 'insert', 1, 0.1, 'h', 'e', 'l', 'l', 'o', 'd', 'f', 'g']
>>> x.pop(1)
'insert'
>>> x
['one', 1, 0.1, 'h', 'e', 'l', 'l', 'o', 'd', 'f', 'g']
```

> <font color=darkblue face="宋体">list_obj.clear()</font>
> <font color=gray face="华文隶书">清空列表</font>  
> <font color=darkblue face="宋体">list_obj.copy()</font>
> <font color=gray face="华文隶书">复制列表</font>  
> <font color=darkblue face="宋体">list_obj.append(value)</font>
> <font color=gray face="华文隶书">从尾部添加一个元素</font>  
> <font color=darkblue face="宋体">list_obj.index(value)</font>
> <font color=gray face="华文隶书">根据<a>`元素`</a>查对应<a>`编号`</a></font>

------

<h4 id="dict"><font color=orange>字典(dict)</font>[🔙](#head)</h4>
<h5 id="dictbuild"><font color=sloblue>字典的建立</font>

> <font color=gray face="华文隶书">直接法</font>
> <font color=darkblue face="宋体">dictname={键1:值1,键2:值2}</font><font color=black>

```python
>>> dict1={1:'one','π':3.14}
>>> dict1[1]
'one'
>>> dict1['π']
3.14
```

> <font color=gray face="华文隶书">赋值法</font>
> <font color=darkblue face="宋体">dict(键1=值1,键2=值2)</font>

```python
>>> dict2=dict(1=10,2=20)
SyntaxError: keyword can't be an expression//用这种方法键名必须符合变量命名规则，且使用时需要当成字符串
>>> dict2=dict(a1=10,a2=20)
>>> dict2
{'a2': 20, 'a1': 10}
>>> dict2['a1']
10
```

> <font color=gray face="华文隶书">元组法</font>
> <font color=darkblue face="宋体">dict(((键1,值1)(键2,值2)))</font>

```python
>>> dict1=dict(((1,10),(2,20)))
>>> dict1
{1: 10, 2: 20}
>>> dict1[1]
10
```

<h5 id="dictfunction"><font color=sloblue>字典的方法</font>

> <font color=darkblue face="宋体">fromkeys()</font>

```python
>>> dict.fromkeys((1,2,3),(10,20))
{1: (10, 20), 2: (10, 20), 3: (10, 20)}
```

------

#### 注释

父类: 系统基本类

子类: 基于父类的具备更多功能的类，父类可以直接拓展成子类并获得相关特性——一个序列可以变成元组（不能改变值）可以变成列表（可以改变值）

list : 所有后面list.function(list_obj,x) <==> list_obj.function(x)

复制: 在Python中“复制”的含义等同于C语言中的赋值，而“赋值”的含义变成了给一个对象贴标签(即引用)