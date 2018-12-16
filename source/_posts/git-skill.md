---
title: Git上的骚操作
date: 2018-05-22 19:44:12
tags: [Git]
categories:
thumbnail:
---

# Git上的骚操作

1. git reflog

2018RCTF考到的一个题，git 本地记录包含flag，只有git reflog才能看到

然后恢复用 git reset --hard [log_id]

2. GitHack

Git是源码泄漏的一个点，如果目标网站没有对.git/下的文件访问做限制，即可用GitHack脚本dump下源码，[FreeBuf教程](http://www.freebuf.com/sectool/66096.html)

<font color=red>需要windows版python2</font>

- 使用方法

```
GitHack.py http://www.example.com/.git/
```

- [项目地址](https://github.com/lijiejie/GitHack)

  或者<a download='GitHack.py' href='/files/GitHack.py'>从此下载</a>
