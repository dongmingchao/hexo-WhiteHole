---
title: Docker常用操作速查
date: 2017-11-06 20:14:11
tags: Docker
categories: 速查
thumbnail: http://dedsec.club/forhexo/img/docker.png
---

# Docker常用操作速查

### 镜像操作

#### 从官网查询镜像

```bash
docker search [镜像名]
```

#### 从官网下载镜像

```bash
docker pull [镜像名]
```

#### 运行镜像

```bash
docker run [参数] [镜像名]
```

参数解释：
- `-i` 交互模式
- `-t` 启用终端
- `-d` 保持后台运行
- `--name` 设置容器名字
- `-v`映射目录，使用`$PWD`表示当前目录，本地目录:容器目录

#### 删除镜像

```bash
docker rmi [参数] [镜像名]
```

#### 打包容器为镜像

```bash
docker commit [参数] [容器名/标签] [镜像名]:[版本号]
```

默认值：版本号=latest
可选参数：
- `-m` 描述信息
- `-a` 指定作者

### 容器操作

#### 列出当前正在运行的容器

```bash
docker ps
```

可选参数：
- `-a` 列出未被删除的容器

#### 运行容器

```bash
docker start
```

#### 进入容器的命令行

```bash
docker exec -it [容器名字/标签] /bin/bash
```

#### 停止容器

```bash
docker stop
```

#### 删除容器

```bash
docker rm [容器名字/标签]
docker rm $(docker ps -a -q) #删除未运行的容器
```
可选参数：
- `-f` 强制删除容器
- `-v` 删除与容器链接的分卷
- `-l` 删除链接的容器

### 容器实例
更多容器实例推荐在[这里](http://www.runoob.com/docker/docker-install-nginx.html)
#### MySQL

```bash
docker pull mysql
mkdir -p docker_v/mysql/conf
cd docker_v/mysql/conf
touch my.cnf
docker run -p 3306:3306 --name mysql -v /opt/docker_v/mysql/conf:/etc/mysql/conf.d -e MYSQL_ROOT_PASSWORD=[密码] -d mysql
```

参数解释：
- `-p` 端口映射 本地:容器
- `-e MYSQL_ROOT_PASSWORD` 设置初始密码
- `-d` 保持后台运行