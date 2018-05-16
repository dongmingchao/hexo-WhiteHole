---
title: MongoDB速查
date: 2018-03-06 13:12:33
tags: [MongoDB,速查,数据库]
categories:
thumbnail:
---

# MongoDB速查

所有数据库

```bash
show dbs
```

使用数据库，没有会新建

```bash
use [数据库名]
```
所有表

```bash
show tables
```
所有表内容

```bash
db.[表名].find().pretty()
```
`pretty()` 以更好看的形式展现

创建表

```
db.createCollection(name,options)
```

[`options`]

- `capped` : bool [false] => true 创建固定大小的表，同时指定size
- `size` : int => 指定最大值
- `max` : int => 指定最多条目的数量
- `autoIndexId` : bool [false]  => 自动创建_id索引

添加文档

```bash
db.[表名].insert(obj)
```

删除文档

```bash
db.[表名].remove(obj)
```

更新文档

```bash
db.[表名].update(oldObj,{[updateMethod]:newObj})
```

[`updateMethod`]

- $set => 用法：{ $set : { field : value } }
  就是相当于sql的set field = value，全部数据类型都支持$set。

- $inc => 用法：{ $inc : { field : value } }
  意思对一个数字字段field增加value

- $unset => 用法：{ $unset : { field : 1} }
  删除字段

- $push => 用法：{ $push : { field : value } }

  把value追加到field里面去，field一定要是数组类型才行，如果field不存在，会新增一个数组类型加进去.

- $pushAll => 用法：{ $pushAll : { field : value_array } }
  同$push,只是一次可以追加多个值到一个数组字段内。

- $addToSet => 用法：{ $addToSet : { field : value } }

  增加一个值到数组内，而且只有当这个值不在数组内才增加。

- $pop => 用法：删除最后一个值：{ $pop : { field : 1 } }
  删除第一个值：{ $pop : { field : -1  } }
  注意，只能删除一个值，也就是说只能用1或-1，而不能用2或-2来删除两条。mongodb 1.1及以后的版本才可以用。

- $pull => 用法：{$pull : { field : value } }

  从数组field内删除一个等于value值。

- $pullAll => 用法：{ $pullAll : { field : value_array } }

  同$pull,可以一次删除数组内的多个值。

- $ => 只使用在oldObj是一个数组的时候，相当于oldObj，默认只匹配第一个

- $[] => 

  用法：类似$，但是匹配所有符合条件的结果

更多骚操作参考[官方更新操作符](https://docs.mongodb.com/manual/reference/operator/update/positional-all/)
