##1.导入Thymeleaf与Shiro整合依赖包
<!--thymeleaf整合shiro-->
<dependency>
    <groupId>com.github.theborakompanioni</groupId>
    <artifactId>thymeleaf-extras-shiro</artifactId>
    <version>2.0.0</version>
</dependency>

##Shiro配置类ShiroConfig中进行配置
// 整合ShiroDialect : 用来整合 Shiro 和 Thymeleaf
@Bean
public ShiroDialect getShiroDialect() {
    return new ShiroDialect();
}

##前端首页index.html代码导入命名空间
<html lang="en" xmlns:th="http://www.thymeleaf.org"
      xmlns:shiro="http://www.thymeleaf.org/thymeleaf-extras-shiro">