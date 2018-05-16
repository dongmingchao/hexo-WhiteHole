---
title: 项目-SNP-用Java打造自己的后端
date: 2018-02-08 16:30:58
tags: [教程,项目]
categories: Java
thumbnail:
---

# 用Java打造自己的后端程序

现在web编程风气年高，甚至和桌面程序达到了平起平坐的地位，由于其在浏览器中运行，只需要占用少量存储空间且又轻松跨平台，大有取代传统桌面程序的趋势。那么有编程经验的同学看到标题，就要问了，现在主流的后端程序，PHP，JSP都很好用，又何必一遍又一遍的造这个轮子呢？我们学习别人的东西固然重要，能够熟练运用更是值得称赞，然而如果我们想要用的精妙，达到更高层的境界，自然需要对它进行深刻的剖析的理解，这时候通过重造轮子的方法来学习就可以看到一些平常看不到的细节。

那首先我们就需要了解一些基础的知识。

1. ## <font color=red>后端</font>和<font color=blue>前端</font>的传纸条
    做过全栈的同学肯定知道，前后端交互其实就是两步，第一，写好纸条，就是`做好Form表单`；第二就是瞄准你要扔的地方（然后趁老师不注意扔过去，咳咳跑题了），就是`get/post的url链接`。将做好的Form表单通过get/post提交到url链接上，后端就可以通过这个链接收到Form中的信息。
2. ## <font color=green>HTTP协议</font>的解析
    服务器和浏览器既然要互相传纸条，就一定要使用同一种语言，HTTP协议就是这其中最简单最好学的“语言”。其实就是一种格式了，关于这个格式，在[MDN](https://developer.mozilla.org/zh-CN/docs/Web/HTTP/Headers)和RFC文档中有详细的定义。简单来讲就是`第一行是「请求方法」「空格」「使用的协议/版本号」，接下来都是以冒号分割的键值对形式`，如下是我用Java代码获取的Safari请求的请求头（后面会讲到怎么写）
    ```
    GET / HTTP/1.1
    Host: 127.0.0.1:9000
    Upgrade-Insecure-Requests: 1
    Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8
    User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_3) AppleWebKit/604.5.6 (KHTML, like Gecko) Version/11.0.3 Safari/604.5.6
    Accept-Language: zh-cn
    Accept-Encoding: gzip, deflate
    Connection: keep-alive
    ```
3. ## 写<font color=red>后端</font>用<font color=#2faaaa>Socket</font>编程？没错！
    其实在我没有做这个东西之前，我一直认为web后端使用很神奇的东西写的，但其实后端就是实现了服务器和浏览器之间的socket通信的程序，但因为HTTP协议是面向无链接的，默认的socket程序如果不手动close的话就会一直保持链接，在浏览器的一边呈现的就是一直在转圈圈读载入条。

    多说无益，我们现在就写一个hello world。先写一个简短的程序来获取到浏览器的请求头，局部部分如下

    ```java
    while (online) {
            try {
                server = socket.accept();
                outLP.println("已链接: " + server.getRemoteSocketAddress());
                inRemote = server.getInputStream();
                outRemote = server.getOutputStream();
                PrintWriter outRP = new PrintWriter(outRemote, true);
                Scanner inRS = new Scanner(inRemote);
                while (inRS.hasNextLine()) {
                    String got = inRS.nextLine();
                    if (got.equals("")) break;
                    outLP.println(got);
                }
                server.close();//由于http是无链接的，所以必须断开socket，表示传输完成
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    ```
    变量表
    ```java
    int port;
    ServerSocket socket;
    Socket server;
    InputStream inLocal;
    OutputStream outLocal;
    InputStream inRemote;
    OutputStream outRemote;
    boolean online;
    ```

    文件下载
    - [HelloWorld.java](https://res.dedsec.club/HelloWorld.java)

    动手试试，你就会发现，哎？不对啊？浏览器怎么一次发这么多请求啊？我在测试的时候，点一次链接，Safari请求了3次，FireFox请求了10次，这是因为如果浏览器没有接收到任何信息，甚至404都没有的时候，就会认为丢包了，会重复发包确认。所以我们要让浏览器知道我们收到包，必须回应一下，当然回应的格式也应该按照HTTP协议的格式来。在这里新建一个类叫`Header`

    属性表
    ```java
    String protocol;
    String protocolVersion;
    int status_number;
    String status;
    String Date;
    String ContentType;
    String charset;
    ```

    当然这上面的属性是一般服务器响应头不可或缺的部分，你也可以添加一些自己的东西“搞事”

    为了方便我重写toString将header转化成标准HTTP格式

    ```java
    @Override
    public String toString() {
        return protocol + '/' + protocolVersion + ' '
                + status_number + ' '
                + status + '\n' +
                "Date: " + Date + '\n' +
                "Content-Type: " + ContentType + ';' +
                "charset="+charset + '\n';
    }
    ```

    然后在接收浏览器信息之后把我们的信息发出去，注意先发头再发内容。

    ```java
    while (inRS.hasNextLine()) {
        String got = inRS.nextLine();
        if (got.equals("")) break;
        outLP.println(got);
    }
    outRP.println(new Header());
    outRP.println("Hello World");
    server.close();
    ```

    试着访问下
    ![HelloWorld2_1](https://res.dedsec.club/snp/HelloWorld2.png)
    ![HelloWorld2_2](https://res.dedsec.club/snp/HelloWorld2_2.png)

    文件下载
    - [HelloWorld_2.java](https://res.dedsec.club/HelloWorld_2.java)
4. ## 用<font color=blue>前端</font>传过来的报文“搞事情”
    看到这里呢，有灵性的同学们就已经懂了这中间到底是怎么回事了，接下来就很简单了，我们对从浏览器里接受的报文做一个处理，方便我们在后面使用它，为了以后方便，我们新建一个叫Request的类，属性表如下

    ```java
    Scanner raw;
    InputStream inputStream;
    String method;
    String url;
    HashMap<String, String> param_GET;
    HashMap<String, Object> param_POST;
    String protocol;
    String protocolVersion;
    HashMap<String, String> header = new HashMap<>();
    ```

    我们一边接受浏览器传来的报文，一遍进行分类存储，这里呢我选择使用HashMap的形式将原本header中的键值对存储下来（开发版本还未考虑优化）

    在这个类的构造器中就一次性的将所有信息归类，注意对Post和multipart/form-data请求的特殊处理，在传输文件时尽量使用二进制形式，这样面对上传等一些特殊要求就不会出现问题。
5. ## <font color=red>后端</font>的“偷梁换柱”
    到目前为止，我们完成了静态网页的提供，但是，一门后端语言之所以被称谓后端语言就是因为它可以提供动态改变网页内容的能力，所以我们新建一个叫Response的类，提供在返回html文档的时候的改变其内容的能力。

    我在这里，关于如何控制网页该如何改变，使用什么形式的指令，我想了很久，现在 比较火的PHP，JSP都是标签化了，我当然也可以做成这样，但是，我在使用PHP的时候对其随意的代码风格十分讨厌，而JSP又显得冗长。后端语言中我最喜欢的就是node，因为它小巧快捷，透明，跨平台，直接使用js作为后端语言又减少了学习成本，想想，它唯一的缺点可能就是只有异步这一种编程方式了吧，真的是成也异步，败也异步。由此，我就想做一款同步模型的Node。

    我挑选了json这一文件格式作为源码文件格式，整个文件分成几个部分，由几个固定关键字区分：scope——变量表，script——脚本逻辑处理，return——返回格式调整。（这其中也借鉴了一些Angular）可以看出这样做，虽然对于大片大片的网页处理能力变差，却在ajax异步处理能力上大幅上升。给后端的定位就是，只提供数据，组织数据格式，剩下全部抛给前端，利用浏览器解析js的强大能力实现整个网站的渲染和呈现。这样做好处众多，一来减轻了服务器的压力，二来通过更赞成异步来构建网页，展现的网页将更流畅，体验更好，三来由于交互的数据量不再是大量的整个网页，速度和流量上都有无可比拟的优势。

    在解析脚本的地方，目前的处理方法是，将一个语句归为3个部分，变量+操作符+表达式（这样的结构很死，这也是我目前最不满意的地方，希望以后知识增加之后，可以改成python或者js的方式），处理完表达式之后，再识别操作符操作变量。
6. ## 「<font color=#ff0ff0>扩展</font>」反射式提供别人开发插件的API
    这个几乎是现代所有有前途的软件的通用做法， 提供一套api，这样可以大幅度提升软件的生命周期，可以有大量的新鲜血液注入。

    目前是利用java强大的反射，实现对Java函数中的最多3参函数支持，返回值规定为String

由于后面的几个部分涉及到的内容较多，基础原理也已讲明，故没有提供详细教程，对后续还不清楚的同学可以看这里的[Github源码](https://github.com/dongmingchao/SNP)