---
title: JavaFX--0x00
date: 2017-11-05 23:29:05
tags: [JavaFX,教程,桌面应用]
categories: JavaFX教程
thumbnail: http://dedsec.club/forhexo/img/javafx/javafx.png
---

# JavaFX教程--0x00

---
JavaFX是Java最新的GUI体系，代替了界面略丑的swing，而且使用了与web设计相仿的设计理念，使用者可以使用fxml快速构建JavaFX程序界面，也可以使用JavaFX的CSS来实现快速更换主题等等的目地。就是感觉运行速度上略低于swing，所以swing仍是开发小型工具的首选。

<font color=red>如果您使用OpenJDK构建JavaFX应用，需要单独下载JavaFX源码编译(不推荐)，否则将报错相关的程序包不存在。请移步[OpenJFX开源项目](https://wiki.openjdk.java.net/display/OpenJFX/Building+OpenJFX)</font>

## 开始动手--第一个JavaFX程序

#### 从空白开始

新建一个项目，可以是普通的项目(推荐)，如果你的IDE提供JavaFX项目，也可以新建JavaFX项目，这样IDE会引入一些相关文件，但是可能有些初学者会觉得IDE提供的文件过多有些混乱，所以这里统一从普通项目开始。
请善用IDE的自动导入包功能

##### 1. 让类继承Application类

```java
public class Main extends Application
```

##### 2. 重写start方法
extends之后IDE应该就会报错，原因是继承`Application`类之后必需重写`start`方法

```java
@Override
public void start(Stage primaryStage) throws Exception
```

##### 3. 设置开始的布局
在start方法內写入你希望所有容器的父容器的类型
可选类型：
- BorderPane 边界式布局
- AnchorPane 锚点布局，使用坐标确定控件位置
- VBox 相当于只有一列但无限长的网格式布局
- HBox 相当于只有一行但无限长的网格式布局
- GridPane  网格式布局
- Accordion 手风琴式布局
- FlowPane  流式布局

还有一部分这里就不再列出，可以参考官方文档，有关各种界面的表现可以参考[这里](http://www.yiibai.com/javafx/)。我这里使用VBox。

```java
VBox root = new VBox();
```

##### 4. 显示布局
在JavaFX中，规定最下层是Stage，由Stage呈放Scene，再由Scene呈放Pane。
在start方法中继续添加：

```java
primaryStage.setTitle("Hello World");//设置标题
primaryStage.setScene(new Scene(root, 300, 275));//设置宽高
primaryStage.show();//显示出来
```

最后，将main方法改成这样：

```java
public static void main(String[] args) {
   launch(args);
}
```

运行，会显示如下，不同的操作系统细节会略微不同，笔者是MacOS的运行结果。

![javafx001](http://dedsec.club/forhexo/img/javafx/javafx001.png)

