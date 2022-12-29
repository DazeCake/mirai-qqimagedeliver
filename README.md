# mirai-qqimagedeliver
[qqimagedeliver](https://github.com/tkkcc/qqimagedeliver)的mirai实现

## 说明

[qqimagedeliver](https://github.com/tkkcc/qqimagedeliver)的mirai实现，使用Mirai v2.13.2 的短信登录的新特性，简化登录难度

## 快速部署

### 安装需要的软件

```shell
sudo apt install screen
sudo apt install unzip
sudo apt install openjdk-11-jdk
```



### 安装MCL

```shell
mkdir mcl
cd mcl
wget https://download.fastgit.org/iTXTech/mirai-console-loader/releases/download/v2.1.2/mcl-2.1.2.zip
unzip mcl-2.1.2.zip
chmod +x mcl
mkdir plugins
wget -P ./plugins https://download.fastgit.org/DazeCake/mirai-qqimagedeliver/releases/download/v0.2.0/mirai-qqimagedeliver-0.1.0.mirai2.jar
```

### 登录

建议先使用`screen`等软件将程序挂起

```
./mcl
login qq号 密码 ANDROID_PAD
```

之后根据提示操作，若仍有疑惑，请访问 [使用篇 /02/ 在控制台登录 | 数据消散 (mrxiaom.top)](https://wiki.mrxiaom.top/zh/mirai/1-2)

## 检查运行状态

**部分服务器需要主动放行端口**

访问 `http://你的IP:49875/status` ，此时应显示

```
通知服务器正常运行中
当前Bot: xxxxxxxxxx
状态: 在线
终端填写地址: http://本机ip:49875/
```

说明通知服务已经部署好了，可以使用 `http://你的IP:49875/` 接收来自速通的通知
