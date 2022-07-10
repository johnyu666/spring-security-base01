# SpringBoot结合SpringSecurity的前后端分离
> 设计思路：从/user/login进行post方式的登录，并反回一个token, 
> 利用该token做为键将用户认证的信息存入到内存的cache中；
> 在其它资源的访问中，前端携带请求头token，后端使用此token进行授权处理。

## 资源地址：

1. 登录： http://localhost:8080/user/login
2. 访问资源： http://localhost:8080/books/find 和
 http://localhost:8080/books/del 。
3. 退出： http://localhost:8080/user/logout

## 用户和角色：
1. john/123: USER,ADMIN
2. tom/123: USER

## 受保护资源：
1. /books/find
可以被USER，ADMIN访问
2. /books/del
可以被ADMIN访问


## 项目改进：
1. 加入JWT 令牌 （更加安全）
2. 加入Redist （分布式）

