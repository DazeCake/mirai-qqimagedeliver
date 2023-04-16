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
wget -P ./plugins https://download.fastgit.org/DazeCake/mirai-qqimagedeliver/releases/download/v0.3.0/mirai-qqimagedeliver-0.3.0.mirai2.jar
```

### 登录

建议先使用`screen`等软件将程序挂起

```
./mcl
login qq号 密码
```

之后根据提示操作，若仍有疑惑或者出现登录失败，请参考 

- [无法登录的临时处理方案](https://mirai.mamoe.net/topic/223/%E6%97%A0%E6%B3%95%E7%99%BB%E5%BD%95%E7%9A%84%E4%B8%B4%E6%97%B6%E5%A4%84%E7%90%86%E6%96%B9%E6%A1%88)
- [使用篇 /02/ 在控制台登录 | 数据消散 (mrxiaom.top)](https://wiki.mrxiaom.top/zh/mirai/1-2)

## 检查运行状态

**部分服务器需要主动放行端口**

访问 `http://你的IP:49875/status` ，此时应显示

```
通知服务器正常运行中
当前Bot: xxxxxxxxxx
状态: 在线
终端填写地址: 本机ip:49875
```

说明通知服务已经部署好了，可以在速通填写 `你的IP:49875` 接收来自速通的通知
