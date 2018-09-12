---
title: GitHooks
date: 2018-08-26 17:15:21
tags: [Git]
categories:
thumbnail:
---

# Git Hooks 玩法

在开发中经常会碰到一种情况，就是将修改后的代码push到服务器上，然后再登陆服务器去部署，一次手动可以，次次手动就很烦人了，（特别是当你的服务器网速很慢的时候），这时候就希望能够有种自动化的东西可以在我们每次更新代码的时候自动执行，部署服务器，Git Hooks就是因为这种需求产生的，那这种神器要怎么使用呢？

## 1.建立一个仓库

```bash
git init example.git
```

这里使用--bare建立空仓库也可以

## 2.建立一个当代码更新时候触发的脚本

```bash
cd example.git/.git/hooks
vim post-receive
```

这里的`post-receive`就是被触发的脚本，可以是任何可执行的脚本类型，举个例子

```bash
#!/bin/bash
unset GIT_DIR
cd [工作路径]
git checkout -f
git log -1 >> LOG
```

注意这里的<u>unset GIT_DIR</u>非常重要，默认路径是.git这个文件夹，如果是裸仓库就是仓库文件夹，但如果要改变脚本工作路径就必须写上这个

## 3. 非空仓库需要做的额外工作

如果不是裸仓库，git默认其仅可通过clone或pull来更新追踪文件区，这里我们用它当服务器，当然要允许push更新文件区了，所以需要下面一条命令

```bash
git config --add receive.denyCurrentBranch ignore
```

## 4.参阅

- git在一次作用范围内的不同生命周期可触发的脚本 [Git钩子](https://git-scm.com/book/zh/v2/%E8%87%AA%E5%AE%9A%E4%B9%89-Git-Git-%E9%92%A9%E5%AD%90)

