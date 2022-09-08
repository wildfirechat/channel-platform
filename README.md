# 野火频道服务
野火频道管理系统，支持多频道接入。提供频道菜单、自动回复、发布文章、CMS等管理功能。使用前需要部署野火IM服务。

## 野火频道系统
野火频道系统包括3部分。[server](./server)为野火频道服务的后端服务，给管理后台页面和客户端页面提供服务；[manage](./manage)为野火频道管理页面，管理员可以登录并管理频道；[client](./client)为客户端页面，在用户手机展示文章内容。

使用野火频道服务必须部署后端服务、管理后台页面和客户端页面。

## 依赖
依赖Java1.8/MySQL 5.7+

## 配置
修改 ```src/main/resources/application-{dev|prod}.yml```文件，配置正确的mysql地址。另外修改```src/main/resources/logback-spring.yml```文件，修改```<fileNamePattern>/usr/local/wx/logs/wx.%d.log</fileNamePattern>``` 行指定存在可读写的路径。

## 编译manage
进入到[manage](./manage)目录，执行下述命令
```
npm install
npm run build
```

## 编译client
进入到[client](./client)目录，执行下述命令
```
npm install
npm run build
```

*** 注意，要先编译```manage```再编译```client```，因为前端页面编译后会拷贝到后端的static目录，后编译```manage```会覆盖所有的资源。 ***

## 编译后端
进入到[server](./server)目录，执行下述命令
```shell
mvn clean package
```
生产软件包```channel-api.jar```在```target```目录下。

## 配置
修改 ```server/config/application-{dev|prod}.yml```文件，配置正确的mysql地址。另外修改```server/config/logback-spring.yml```文件，修改```<fileNamePattern>/usr/local/wx/logs/wx.%d.log</fileNamePattern>``` 行指定存在可读写的路径。

## 运行
把```server/config```目录拷贝到```channel-api.jar```同目录，然后执行下面语句：
```shell
java -jar channel-api.jar
```

## 登录
需要部署后端管理页面，具体方法参考管理页面项目。使用用户名密码 ```admin/123456``` 来进行登录，登录后可以修改管理员密码和添加更多管理员。

## 对接野火IM服务
1. 在野火IM服务创建频道，频道```auto```参数为1，```callback```为 ```http://{频道后端IP}:8890/{频道ID}```。
2. 在管理页面添加频道，填入IM服务地址（端口是80），频道ID和频道密钥。

## 测试
测试菜单，自动回复，文章等功能。

## 前后端分离
默认编译```client```和```manage```会把前端页面放到```server```的```static```目录。也可以前后端分离部署，把前端页面单独部署。

## 项目来源
项目Base于[wx-api](https://github.com/niefy/wx-api)。
