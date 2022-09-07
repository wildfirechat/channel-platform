# 野火频道服务
野火频道管理系统，支持多频道接入。提供频道菜单、自动回复、发布文章、CMS等管理功能。使用前需要部署野火IM服务。

## 野火频道系统
野火频道系统包括3部分。第一部分是此项目，为野火频道服务的后端服务，给管理后台页面和客户端页面提供服务；第二部分为野火频道管理页面，管理员可以登录并管理频道；第三部分为客户端页面，在用户手机展示文章内容。

使用野火频道服务必须部署后端服务、管理后台页面和客户端页面。

## 依赖
依赖Java1.8/MySQL 5.7+

## 配置
修改 ```src/main/resources/application-{dev|prod}.yml```文件，配置正确的mysql地址。另外修改```src/main/resources/logback-spring.yml```文件，修改```<fileNamePattern>/usr/local/wx/logs/wx.%d.log</fileNamePattern>``` 行指定存在可读写的路径。

## 编译
```shell
mvn clean package
```

## 运行
```shell
java -jar channel-api.jar
```

## 登录
需要部署后端管理页面，具体方法参考管理页面项目。使用用户名密码 ```admin/admin123``` 来进行登录，登录后可以修改管理员密码和添加更多管理员。

## 对接野火IM服务
1. 在野火IM服务创建频道，频道```auto```参数为1，```callback```为 ```http://{频道后端IP}:8890/wx/{频道ID}```。
2. 在管理页面添加频道，填入IM服务地址（端口是80），频道ID和频道密钥。

## 测试
测试菜单，自动回复，文章等功能。

## 项目来源
项目Base于[wx-api](https://github.com/niefy/wx-api)。
