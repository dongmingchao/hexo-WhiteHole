net stop w32time  #停止Windows Time服务
w32tm /unregister  #取消注册Windows Time服务
w32tm /register  #重新注册Windows Time服务
net start w32time # 重新启动Windows Time服务
w32tm /resync /nowait  #重新进行Windows时间同步