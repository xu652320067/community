## 码匠社区

## 资料
https://spring.io/guides
https://spring.io/guides/gs/serving-web-content/

## 工具



### 5将配置文件分离,写到properties中去
在配置文件中定义Client_id,Client_secret,Redirect_uri
通过注解的形式注入AutorizeController.java中

### 6保持登录状态cokie和session
session相当于银行账户,cokie就相当于银行卡,只有拿到银行卡才能知道账号是谁的,才能操作余额<br>
浏览器就相当于"人",服务器相当于"银行",本身http是无状态的,如何让服务器记录状态,需要每次携带一张银行卡(cokie)来.<br>
写一个cokie和session来表示登录状态,没有登录的时候只显示"登录",登录成功后只显示"我"<br>
在AuthorizeController中写

### 7数据库
添加maven依赖h2,创建User表

### 8springboot中添加mybatis,添加pom依赖
在application.properties中添加数据库连接池<br>
新建mapper.UserMapper,model.User<br>

### 9登录成功后通过java代码往前端写入cokie
使用response写cookie,将cookie写入token中,在返回
首页的时候需要将cookie里面key为token的信息获取到(indexController)然后
去数据库中查询看数据库中是否存在(userMapper),以此来验证是否登录成功