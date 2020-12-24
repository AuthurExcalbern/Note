### adb 调试

查看窗口视图层级：
`adb shell dumpsys SurfaceFlinger`

查看Systemui进程：
`adb shell ps -A | grep systemui`
`adb shell kill 进程id`

查看Settings值：
以systemuitoolsplugin中的setSecureSettings为例，其输入的key为mz_use_flyme_navigation_bar那么我们可以通过如下命令获取key对应的值
`adb shell settings get secure mz_use_flyme_navigation_bar`

adb devices 未找到设备
- 未安装手机驱动（ADB）：可以在 Windows 系统下使用驱动精灵/驱动人生安装驱动
- 手机未打开调试模式：开启开发者选项，并打开USB调试
- adb 未能获取到设备：记录手机硬件Id（VID）到 .android 目录下的 adb_usb.ini 文件

adb杀死某进程：
- 使用root权限调试：`adb root `
- 使`/system`部分置于可写入的模式: `adb remount`
- 未能进入remount就调用：`adb disable-verity` -> `adb reboot`
- 查找PID：`adb shell ps | grep 进程包名`
- `adb shell kill PID`

adb查看日记：
- `adb logcat [TAG:日记等级] | grep 关键字`
- 出现`wait for device`问题
  - `adb kill-server`
  - `adb start-server`

[adb logcat 命令](http://note.youdao.com/noteshare?id=b09df7db570c01ae0c3bca56cd232603)


### Linux 常用命令

[Linux 常用命令学习](https://www.runoob.com/w3cnote/linux-common-command-2.html)

- `ls` 查看文件
- `cd` 切换目录
- `pwd` 查看当前工作目录路径
- `mkdir` 创建文件夹
- `rm` 删除一个目录中的一个或多个文件或目录，如果没有使用 -r 选项，则 rm 不会删除目录。如果使用 rm 来删除文件，通常仍可以将该文件恢复原状。
- `rmdir` 从一个目录中删除一个或多个子目录项，删除某目录时也必须具有对其父目录的写权限(不能删除非空目录)
- `mv` 移动文件或修改文件名，根据第二参数类型（如目录，则移动文件；如为文件则重命令该文件）。当第二个参数为目录时，可刚多个文件以空格分隔作为第一参数，移动多个文件到参数 2 指定的目录中。
- `cp` 将源文件复制至目标文件，或将多个源文件复制至目标目录。命令行复制，如果目标文件已经存在会提示是否覆盖，而在 shell 脚本中，如果不加 -i 参数，则不会提示，而是直接覆盖！
- `cat`
  + 一次显示整个文件:`cat filename`
  + 从键盘创建一个文件:`cat > filename`
  + 将几个文件合并为一个文件:`cat file1 file2 > file`
- `more` 功能类似于cat,more会以一页一页的显示方便使用者逐页阅读，而最基本的指令就是按空白键（space）就往下一页显示，按 b 键就会往回（back）一页显示。
- `less` less 与 more 类似，但使用 less 可以随意浏览文件，而 more 仅能向前移动，却不能向后移动，而且 less在查看之前不会加载整个文件。
- `head` 用来显示档案的开头至标准输出中，默认 head 命令打印其相应文件的开头 10 行。
- `tail` 用于显示指定文件末尾内容，不指定文件时，作为输入信息进行处理。常用查看日志文件。
- `which` 查找某个文件
- `whereis` 只能用于程序名的搜索，而且只搜索二进制文件（参数-b）、man说明文件（参数-m）和源代码文件（参数-s）。
- `find` 用于在文件树中查找文件，并作出相应的处理。
- `chmod` 用于改变 linux 系统文件或目录的访问权限。
- `chown` 将指定文件的拥有者改为指定的用户或组
- `ln` 功能是为文件在另外一个位置建立一个同步的链接，当在不同目录需要该问题时，就不需要为每一个目录创建同样的文件，通过 ln 创建的链接（link）减少磁盘占用量。(软件链接及硬链接)
- `date` 显示或设定系统的日期与时间。
- `cal` 可以用户显示公历（阳历）
- `grep` 强大的文本搜索命令
- `ps` 用来查看当前运行的进程状态，一次性查看，如果需要动态连续结果使用 top
- `kill` 发送指定的信号到相应进程。不指定型号将发送SIGTERM（15）终止指定进程。如果任无法终止该程序可用"-KILL" 参数，其发送的信号为SIGKILL(9)，将强制结束进程，使用ps命令或者jobs命令可以查看进程号。root用户将影响用户的进程，非root用户只能影响自己的进程。
- `free` 显示系统内存使用情况，包括物理内存、交互区内存(swap)和内核缓冲区内存。
