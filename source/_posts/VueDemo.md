---
title: VueDemo
toc: true
date: 2019-03-19 17:36:49
tags:
categories:
---



# Vue Proxy Demo

`index.html`

```html
<html>

<head>
    <meta charset="UTF-8">
    <meta name="viewport"
        content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Vue 双向绑定</title>
</head>

<body>
    <div id="app">
        <input type="text" v-model="num">
        <input type="button" value="增加" v-click="add">
        <input type="button" value="减去" v-click="sub">
        <div v-bind="num"></div>
    </div>
</body>
<script src="./main.js"></script>
<script>
    window.onload = function () {
        window.$app = new Vue({
            el: '#app',
            data: {
                num: 0
            },
            methods: {
                add() {
                    this.num++
                },
                sub() {
                    this.num--
                }
            }
        })
    }
</script>

</html>
```

`main.js`

```js
class Watcher {
    constructor(node, attr, data, key) {
        this.node = node
        this.attr = attr
        this.data = data
        this.key = key
    }
    update() {
        this.node[this.attr] = this.data[this.key]
    }
}
class Vue {
    constructor(options) {
        this.$el = document.querySelector(options.el)
        this.$methods = options.methods
        this._binding = {}
        this._observe(options.data)
        this._compile(this.$el)
    }

    _pushWatcher(watcher) {
        if (!this._binding[watcher.key]) this._binding[watcher.key] = []
        this._binding[watcher.key].push(watcher)
    }

    _observe(data) {
        var that = this

        // 把代理器返回的对象存到 this.$data 里面
        this.$data = new Proxy(data, {
            set(target, key, value) {
                // 利用 Reflect 还原默认的赋值操作
                let res = Reflect.set(target, key, value)
                // 这行就是监控代码了
                that._binding[key].map(item => { item.update() })
                return res
            }
        })
    }
    _compile(root) {
        const nodes = Array.prototype.slice.call(root.children)
        let data = this.$data
        nodes.map(node => {
            // 如果不是末尾节点，就递归
            if (node.children.length > 0) this._complie(node)
            //  处理 v-bind 指令
            if (node.hasAttribute('v-bind')) {
                let key = node.getAttribute('v-bind')
                this._pushWatcher(new Watcher(node, 'innerHTML', data, key))
            }
            //  处理 v-model 指令
            if (node.hasAttribute('v-model')) {
                let key = node.getAttribute('v-model')
                this._pushWatcher(new Watcher(node, 'value', data, key))
                node.addEventListener('input', () => { data[key] = node.value })
            }
            //  处理 v-click 指令
            if (node.hasAttribute('v-click')) {
                let methodName = node.getAttribute('v-click')
                let mothod = this.$methods[methodName].bind(data)
                node.addEventListener('click', mothod)
            }
        })
    }
}
```

