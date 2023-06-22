spring-boot-shiro

#步骤
1.spring-boot-shiro-authentication
2.spring-boot-shiro-remember-me
3.spring-boot-shiro-authorization
4.spring-boot-shiro-thymeleaf-tag

##项目说明
项目为mvc的war项目，idea启动只需要配置本地的（local）tomcat

##参考连接：
https://www.cnblogs.com/progor/p/10970971.html

###shiro标签
##也可以在jsp中进行授权。

##首先导入标签库：
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>

<shiro:guest></shiro:guest>：当用户没进行认证时，显示标签中的内容。
<shiro:userDO></shiro:userDO>：当用户进行认证了，显示标签中的内容。
<shiro:authenticated></shiro:authenticated>：当用户已经认证时，显示标签中的内容。
<shiro:notAuthenticated></shiro:notAuthenticated>：当用户未认证的时候显示标签中的内容（包括“remember me”的时候）
<shiro:principal />:用来获取用户凭证(用户名等)(从AuthenticationInfo中获取)，标签所在的位置将被替换成凭证信息
<shiro:principal property="username" />:如果存入的用户凭证是一个对象，那么可以使用property指定获取这个对象中的属性。
<shiro:hasRole name="角色"></shiro:hasRole>:拥有指定角色，将显示标签中的内容。
<shiro:hasAnyRoles name="角色1,角色2..."></shiro:hasAnyRoles>：只要拥有多个角色中的一个就显示标签中的内容。
<shiro:lacksRole name="角色"></shiro:lacksRole>：没有某个角色将不显示标签中的内容
<shiro:hasPermission name="行为"></shiro:hasPermission>：如果拥有某个行为的权限，那么显示标签中的内容
<shiro:lacksPermission name="行为"></shiro:lacksPermission>：如果没有拥有某个行为，那么显示标签中内容

##用例
<!-- 一个未登录的场景 -->
<shiro:guest>
    Hi there!  Please <a href="login.jsp">Login</a> or <a href="signup.jsp">Signup</a> today!
</shiro:guest>

<!-- 已登录过，准备切换其他用户的场景 -->
<shiro:userDO>
    Welcome back John!  Not John? Click <a href="login.jsp">here<a> to login.
</shiro:userDO>

<!-- 显示登录用户的用户名的场景 -->
Hello, <shiro:principal/>, how are you today?

<!-- 用户已经认证通过的场景 -->
<shiro:authenticated>
    <a href="/logout">退出</a>.
</shiro:authenticated>

<!-- 拥有某个角色的场景 -->
<shiro:hasRole name="administrator">
    <a href="createUser.jsp">创建用户</a>
</shiro:hasRole>

<!-- 拥有某个行为的场景 -->
<shiro:hasPermission name="userDO:create">
    <a href="createUser.jsp">创建用户</a>
</shiro:hasPermission>


##remember me
remember me 主要用于再次访问时仍然保留认证状态的场景。例如，离开某个网站后，两三天再打开仍然保留你的登录信息。
remember me的功能的一个前提是在认证时使用了setRememberMe :

rememberme权限级别
记住我的权限并不是authc，而是user【用户已经身份验证/记住我】
所以做实验的要记得修改拦截器链。
maxAge:过期时间
httpOnly:禁止使用js脚本读取到cookie信息
【其他的不太常用，有兴趣的自查。还有domain之类的】
一种配置方法：
