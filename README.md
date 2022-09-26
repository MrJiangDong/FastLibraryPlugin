#### FastLibraryPlugin

在你的Application中注册以下两个工具

```java
//指定一个全局Crash处理器
CrashHandler.init()

//全局收集Activity
ActivityManager.instance.init(this)
```

