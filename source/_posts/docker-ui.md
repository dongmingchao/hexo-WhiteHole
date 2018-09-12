---
title: Docker的高效使用
date: 2018-06-26 21:18:28
tags: docker
categories:
thumbnail:
---

# Docker的高效使用

## 通过网页界面化使用Docker

- [Portainer](https://portainer.io/)

  ```bash 
  $ docker volume create portainer_data
  $ docker run -d -p 80:9000 -v /var/run/docker.sock:/var/run/docker.sock -v portainer_data:/data portainer/portainer
  ```

  优点：

  1. 下载量小
  2. 不会在docker ps的时候出现一大堆容器，这个只会有一个容器做管理，剩下的都是自己指定的容器，清理起来很方便
  3. 简单来说就是API整合，没有复杂的功能也没有复杂的冗余，适合给服务器装

  缺点：

  1. 没有集群之类的高级操作
  2. 没中文

- [Rancher](https://www.cnrancher.com/)

  ```bash
  $ docker run -d --restart=unless-stopped -p 80:8080 rancher/server:stable
  ```

  优点：

  1. 有中文
  2. 有集群等的高级操作
  3. 界面比上一位好看一点

  缺点：

  1. 好像需要6/7个容器才能启动工作，下载量大，在国内堪忧的网络现状真的很容易让人心态崩掉
  2. 启动之后就别想着用docker ps看容器了，都是一大堆的自动生成的容器
  3. 好像自己创建分卷的时候会自动生成一大堆分卷，手动删很困难（用UI删当然快很多）

  总之，这个适合给工作环境装。