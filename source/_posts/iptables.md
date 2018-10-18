---
title: iptables使用简录
date: 2018-09-30 01:12:48
tags: [速查,linux]
categories:
thumbnail:
---

# iptables使用简录

管理linux的入站出站规则，以及一些网络方面的功能实现

<font color=red>注意：鄙人在网络方面的理论无甚研究，文中涉及到某些名词可能并不是专业名词，还请辨证看待。我将会用</font><font color=apple>这个颜色</font><font color=red>标注出来</font>

## 常用参数

```bash
iptables -L -t nat
```

| Options   | 作用                                                     | 可选参数                                 |
| --------- | -------------------------------------------------------- | ---------------------------------------- |
| `-L`      | 列出列表，等效于 `--list`                                | 无                                       |
| `-t`      | 指定目标<font color=apple>子环路</font>                  | nat etc.(具体要使用指令查看已存在的环路) |
| `-i`      | 指定网络接口(interface)                                  | eth0 etc.                                |
| `-p`      | 指定使用的网络协议(protocol)                             | tcp、udp                                 |
| `--dport` | 目的地端口(destination port)                             |                                          |
| `-s`      | 来源IP，等效于`--source`                                 |                                          |
| `-d`      | 目的地IP，等效于`--destination`                          |                                          |
| `-j`      | 使用协议                                                 | DNAT、SNAT etc.                          |
| `-A`      | 在目标链的规则中追加一条，新规则在所有规则的下面(append) | 链名(INPUT etc.)                         |
| `-I`      | 在目标链的规则中插入一条，新规则在所有规则的上面(insert) | 链名(INPUT etc.)                         |

## 例子

### 配置NAT

路由器：外网172.21.12.21 内网 192.168.1.1

客户机：192.168.1.119

要求：将客户机的3389映射到路由器的12001端口上，使其可以用172.21.12.21:12001使用xrdp服务

1. 配置SNAT

    ```bash
    iptables -t nat -A zone_lan_postrouting -p tcp -s 192.168.1.0/24 --destination 192.168.1.119 --dport 3389 -j SNAT --to 192.168.1.1
    ```

2. 配置DNAT

    ```bash
    iptables -t nat -A zone_lan_prerouting -p tcp -s 192.168.1.0/24 --destination 172.21.12.21 --dport 12001 -j DNAT --to 192.168.1.119:3389
    iptables -t nat -A zone_wan_prerouting -p tcp --dport 12001 -j DNAT --to 192.168.1.119:3389
    ```

注意：实际上的iptables的默认配置不尽相同，其中的`zone_lan_postrouting` `zone_wan_prerouting` `zone_lan_prerouting` 需要根据实际存在的链名而变