---
title: JavaFX—0x01
tags:
  - JavaFX
  - 教程
  - 桌面应用
categories: JavaFX教程
thumbnail: 'http://dedsec.club/forhexo/img/javafx/javafx.png'
date: 2017-11-06 23:48:33
---

# JavaFX教程—Controller

上一节讲了如何从空白开始建立JavaFX应用，但是这个应用是‘死’的，它没有任何交互的能力，这节来讲一讲如何让应用感应到交互操作

### 最好用的操作 -> `setOnAction`

现在，创建一个按钮`Button`，并让它加入到已经存在的面板上。

```java
Button click = new Button("点我");
root.getChildren().add(click);
click.setOnAction(this::forClick);
```

为了让它能在点击的时候做一些事，写一个方法，根据setOnAction的规定，必需存在一个类型是`ActionEvent`的参数（这里包括以后，一定要注意是引入javafx包中的，而不是awt中的，也不是beans中的，由于命名相同所以容易混淆），代码如下

```java
void forClick(ActionEvent event){
    System.out.println("你点击了按钮");
}
```

这样就可以让按钮click被点击的时候，在控制台输出一句"你点击了按钮"。

上面使用了“方法引用”的特性，有一个缺点是，方法的实现在start方法外部，主面板VBox root在start内部，如果想要使用面板，在面板上添加控件，就必须将root单独抽取出来作为属性，如果是经常使用的面板当然推荐这么做，但是如果是临时性的面板，显然将方法声明到start内部更好。

下面就使用lambda表达式将点击触发的函数直接写到start内部，通过点击在面板上添加一个Label并显示一行字。

```java
click.setOnAction(event -> root.getChildren().add(new Label("你点击了按钮")));
```

![效果](http://dedsec.club//forhexo/img/javafx/javafx01.gif)

### 建立控制器类

基本的交互操作已经可以轻松实现了，但是，为了增强Java代码的工程性，通常使用一个单独的类来存放所有控件的交互操作，这个类就叫做控制器。

这个类可以是任何名字，但是根据规范，默认是Controller（因为如果使用默认的JavaFX应用模版，你会发现有个类名为Controller，这就是自动生成的控制器类）

新建Controller.java后，为了使controller中的操作可以获得界面上的控件，可以写一个setApp方法，将包含面板和控件的类传入。这里我的包含面板和控件的类是默认的Main。

```java
private Main app;
public void setApp(Main app){
    this.app = app;
}
```

那么规范化以后，刚刚上面的方法就可以这样写

首先在Main.java中将VBox root作用域提升为属性，同时设置一个界面的Controller对象。

```java
VBox root = new VBox();
Controller controller = new Controller();
```

给新的Controller对象设置服务的界面

```java
controller.setApp(this);
```

然后在Controller.java中写一个forClick方法

```java
void forClick(ActionEvent event){
    app.root.getChildren().add(new Label("你点击了按钮"));
}
```

然后在Main.java中使用“方法引用”调用Controller中的forClick方法。

```java
click.setOnAction(controller::forClick);
```

运行之后，结果应该和刚才一样。

Button的OnAction触发点是鼠标点击，但是不同的控件OnAction的默认触发点不同，比如TextField的触发点就是获取焦点后敲击回车。
