---
title: 透过代理连接SSH
date: 2018-10-17 22:52:48
tags: [服务器管理]
categories:
thumbnail:
---

# 透过代理连接SSH

虽然折腾PcmanFM没什么成效，却终于知道如何让SSH通过代理了。这么一来，使用GitHub和Launchpad都方便了不少。

这是通过SSH的ProxyCommand来完成的。可以用

```
man ssh_config 
```

来查看相关信息。

## 通过SSH代理(SSH over SSH)

使用nc命令(netcat)实现，假设本地SSH代理的监听端口是3000，则ProxyCommand为

```
ProxyCommand nc -x 127.0.0.1:3000 %h %p
```

其中%h表示目标地址，%p是目标端口。这句可以用在命令行里，例如

```
ssh -oProxyCommand="nc -x 127.0.0.1:3000 %h %p" git@github.com
```

或者写入config文件(参见[使用SSH CONFIG](https://www.lainme.com/doku.php/blog/2011/02/%E4%BD%BF%E7%94%A8ssh_config))

```
Host 名称
  HostName 域名/IP
  User 用户
  ProxyCommand nc -x 127.0.0.1:3000 %h %p
```

nc也可以用于HTTPS代理，这需要指定所使用的协议，即添加 -X connect 参数。比如ssh_config中的例子

```
ProxyCommand /usr/bin/nc -X connect -x 192.0.2.0:8080 %h %p
```

netcat也有很多其他用途，有兴趣可以看看

## 通过HTTP代理(SSH over HTTP)

需要corkscrew这个软件

```
sudo aptitude install corkscrew
```

基本的语句是

```
ProxyCommand corkscrew 代理服务器地址 端口 %h %p
```

如果HTTP代理需要用户名/密码验证,则需要写上代理验证文件。假设代理服务器是192.168.0.1:808。用户名密码是name:pass，打算存放在~/.ssh/proxyauth。则有

```
ProxyCommand corkscrew 192.168.0.1 808 %h %p ~/.ssh/proxyauth
```

新建~/.ssh/proxyauth文件，写上

```
name:pass
```