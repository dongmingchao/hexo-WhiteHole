---
title: 杂项很杂的知识点
date: 2018-09-12 12:55:21
tags: [MISC]
categories:
thumbnail:
---

# 杂项知识点

## 图片隐写

**stegsolve**到red的0通道时，发现全黑，则猜测为LSB隐写

LSB隐写： 对于图像文件LSB的特征很明显，通常将信息隐藏在某一个颜色通道中。我们可以查看图片的每个像素点的RGB值，或者使用stegsolve工具进行查看。 （引用）

善用**image combiner**功能（或使用脚本将两图片异或）

[网鼎杯一道杂项](https://blog.csdn.net/qq_37432787/article/details/82193330)