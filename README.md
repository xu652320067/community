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

### 7数据库