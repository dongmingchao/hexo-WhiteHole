---
title: CSP内容安全策略绕过
date: 2018-05-23 13:35:13
tags: [xss,前端]
categories: [xss]
thumbnail:
---

# Content Security Policy 内容安全策略绕过

在网站上[检测CSP设置](https://csp-evaluator.withgoogle.com/)

## 什么是CSP？

来自[MDN](https://developer.mozilla.org/zh-CN/docs/Web/Security/CSP/CSP_policy_directives)的解释：
内容安全策略 (CSP, Content Security Policy) 是一个附加的安全层，用于帮助检测和缓解某些类型的攻击，包括跨站脚本 (XSS) 和数据注入等攻击。 这些攻击可用于实现从数据窃取到网站破坏或作为恶意软件分发版本等用途。

## 简单来说
CSP即是：通过在http(s)报文头添加`Content-Security-Policy`字段，规定此html文档其中不同类型的资源的来源，比如如下这个例子

```json
Content-Security-Policy: default-src 'self' trustedscripts.foo.com
```

## 关键字

`'none'`
    代表空集；即不匹配任何 URL。两侧单引号是必须的。
`'self'`
    代表和文档同源，包括相同的 URL 协议和端口号。两侧单引号是必须的。
`'unsafe-inline'`
    允许使用内联资源，如内联的`<script>` 元素、javascript: URL、内联的事件处理函数和内联的 `<style>` 元素。两侧单引号是必须的。
`'unsafe-eval'`
    允许使用 eval() 等通过字符串创建代码的方法。两侧单引号是必须的。 
`data:`
    允许 `data: URI` 作为内容来源。这是不安全的，因为攻击者可以精心构造 data: URI 来攻击。请谨慎地使用这个源，并确保不要用于脚本。
`mediastream:`
    允许 mediastream: URI 作为内容源。
例子2：
```json
Content-Security-Policy: default-src 'self'; img-src 'self' data:; media-src mediastream:
```
### 范围性指令

| 指令          | 作用                                                         |
| ------------- | ------------------------------------------------------------ |
| `default-src` | default-src 指令定义了那些没有被更精确指令指定的（默认）安全策略。该指令包含了以下指令：`child-src`  `connect-src`  `font-src`  `img-src`  `media-src`  `object-src`  `script-src`  `style-src` |
| `child-src`   | child-src 指定定义了 web workers 以及嵌套的浏览上下文（`<frame>` 和 `<iframe>` ）的源。推荐使用该指令，而不是被废弃的 frame-src 指令。对于 web workers，不符合要求的请求会被当做致命网络错误。 |
| `connect-src` | connect-src 指令定义了请求、XMLHttpRequest、WebSocket 和 EventSource 的连接来源。在火狐23版本之前，使用xhr-src限制XMLHttpRequest的使用 |
| `font-src`    | font-src 指令定义了通过 @font-face加载字体的有效源。         |
| `img-src`     | img-src 定义图片和图标的有效源                               |
| `media-src`   | media-src 定义`<audio>` 和 `<video>` 标签的有效源.             |
| `object-src`  | object-src 定义`<object>`, `<embed>`, 和 `<applet>` 的有效源.   |
| `script-src`  | script-src 规定js的合法源，无论'unsafe-inline' 和 'unsafe-eval'出现在default-src或者script-src，都会使inline script和eval()被禁用 |
| `style-src`   | 规定样式表的合法源，包括额外加载的和行内的和style标签的，不在允许列表的样式表将不会被请求和加载，无论是在`default-src`或是`style-src`注明`'unsafe-inline'`都会使行内使用被禁用 |

写法均如下：

```json
default-src source-list
```

注意：Firefox目前要求使用与`report-uri`相同的URL方案和端口作为受内容安全策略保护的内容。

### 不包括在`default-src`中的指令

#### `base-uri`

`base-uri` 指令定义了 URI，它可以作为文档的基准 URL。如果没有指定值，那么任何 URI 都被允许。如果没有指定这条指令，浏览器会使用 `base` 元素中的 URL。 

`<base>`

```html
<base href="http://www.example.com/page.html">
<base target="_blank" href="http://www.example.com/page.html">
```

#### `plugin-types`

`plugin-types`指令指定通过`<object>`或`<embed>`可以调用的有效MIME。

#### `referrer`

 `referrer` 指明离开的上一个页面的url

#### `report-uri`

The `report-uri` 指令指明检测到XSS攻击后报告给的服务器地址，会向服务器返回一个json格式的报文

```json
{
  "csp-report": {
    "document-uri": "http://example.org/page.html",
    "referrer": "http://evil.example.com/",
    "blocked-uri": "http://evil.example.com/evil.js",
    "violated-directive": "script-src 'self' https://apis.google.com",
    "original-policy": "script-src 'self' https://apis.google.com; report-uri http://example.org/my_amazing_csp_report_parser"
  }
}
```

来自：[MDN](https://developer.mozilla.org/zh-CN/docs/Web/Security/CSP/CSP_policy_directives)

推荐阅读：[Content Security Policy 入门教程](http://www.ruanyifeng.com/blog/2016/09/csp.html)