## 简介

集成Swagger2自动生成API文档，同时可转换成PDF格式

启动应用后访问：http://localhost:9095/swagger-ui.html

## 许可证

基于 MIT 协议发布: <http://www.opensource.org/licenses/MIT>

官方文档： https://github.com/springfox/springfox

Swagger是一个规范和完整的框架，用于生成、描述、调用和可视化 RESTful 风格的 Web 服务；总体目标是使客户端和文件系统作为服务器以同样的速度来更新；文件的方法，参数和模型紧密集成到服务器端的代码，允许API来始终保持同步。主要用来：

接口文档在线自动生成
功能测试
Swagger是一组开源项目，其中主要要项目如下：

Swagger-tools：提供各种与Swagger进行集成和交互的工具。例如模式检验、Swagger 1.2文档转换成Swagger 2.0文档等功能
Swagger-core：用于Java/Scala的的Swagger实现。与JAX-RS(Jersey、Resteasy、CXF...)、Servlets和Play框架进行集成
Swagger-js：用于JavaScript的Swagger实现
Swagger-node-express：Swagger模块，用于node.js的Express web应用框架
Swagger-ui：一个无依赖的HTML、JS和CSS集合，可以为Swagger兼容API动态生成优雅文档
Swagger-codegen：一个模板驱动引擎，通过分析用户Swagger资源声明以各种语言生成客户端代码

#SpringBoot集成Swagger
创建新的SpringBoot项目springboot-swagger-first，集成Swagger，步骤如下：
pom文件添加Swagger依赖，需要使用到Springfox-swagger2、springfox-swagger-ui两个jar包

@Configuration
@EnableConfigurationProperties(SpringfoxConfigurationProperties.class)
@ConditionalOnProperty(value = "springfox.documentation.enabled", havingValue = "true", matchIfMissing =true)
@Import({
OpenApiDocumentationConfiguration.class,
SpringDataRestConfiguration.class,
BeanValidatorPluginsConfiguration.class,
Swagger2DocumentationConfiguration.class,
SwaggerUiWebFluxConfiguration.class,
SwaggerUiWebMvcConfiguration.class
})
@AutoConfigureAfter({ WebMvcAutoConfiguration.class, JacksonAutoConfiguration.class,
HttpMessageConvertersAutoConfiguration.class, RepositoryRestMvcAutoConfiguration.class })
public class OpenApiAutoConfiguration {
}


注解	示例	描述
@ApiModel	@ApiModel (value = "用户对象")	描述一个实体对象
@ApiModelProperty	@ApiModelProperty (value = "用户 ID", required = true, example = "1000")	描述属性信息，执行描述，是否必须，给出示例
@Api	@Api (value = "用户操作 API (v1)", tags = "用户操作接口")	用在接口类上，为接口类添加描述
@ApiOperation	@ApiOperation (value = "新增用户")	描述类的一个方法或者说一个接口
@ApiParam	@ApiParam (value = "用户名", required = true)	描述单个参数

