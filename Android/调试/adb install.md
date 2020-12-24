## adb install

```
应用程序安装-将一个包推送到设备上并安装它。
 
adb install test.apk
APP安装-将多个APK推送到一个包的设备上并安装它们
 
adb install-multiple test.apk test2.apk
应用程序安装-将一个或多个包推送到设备上，并以原子方式安装它们。
 
adb install-multi-package test.apk demo.apk
替换现有应用程序
 
重新安装现有的应用程序，保存其数据
adb install -r test.apk
允许测试包
 
adb install -t test.apk
允许版本代码降级
 
仅可调试器包
adb install -d test.apk
授予所有运行时权限
 
授予应用程序清单中列出的所有权限
adb install -g test.apk
使应用程序作为临时安装应用程序安装。
 
adb install --instant test.apk
使用快速部署
 
adb install --fastdeploy test.apk
始终按APK到设备和调用包管理器作为单独的步骤
 
adb install --no-streaming test.apk
```

## adb uninstall

remove this app package from the device

```
adb uninstall test.apk
```

Keep the data and cache directories around after package removal

```
adb uninstall -k test.apk
```