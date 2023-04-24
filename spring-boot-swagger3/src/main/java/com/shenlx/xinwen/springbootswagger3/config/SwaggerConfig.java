package com.shenlx.xinwen.springbootswagger3.config;

/**
 * @program: my-spring-all-service
 * @description: 定制一个基本的文档示例
 * 一切的东西还是需要配置类手动配置
 * @author: shenlx
 * @create: 2023-04-24 15:34
 **/

import com.shenlx.xinwen.springbootswagger3.config.entity.SwaggerProperties;
import com.shenlx.xinwen.springbootswagger3.response.ResponseStatus;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.*;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.schema.ScalarType;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.RequestParameter;
import springfox.documentation.service.Response;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.service.*;
import org.springframework.http.HttpMethod;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @EnableOpenApi 这个注解文档解释如下：
 * Indicates that Swagger support should be enabled.
 * This should be applied to a Spring java config and should have an accompanying '@Configuration'
 * annotation.
 * Loads all required beans defined in @see SpringSwaggerConfig
 * 只有在配置类标注了 @EnableOpenApi 这个注解才会生成Swagger文档
 * @EnableConfigurationProperties 这个注解使开启自定义的属性配置，这是作者自定义的 Swagger 配置。
 */
@EnableOpenApi
@Configuration
@EnableConfigurationProperties(value = {SwaggerProperties.class})
public class SwaggerConfig{
    /**
     * 配置属性
     */
    @Autowired
    private SwaggerProperties properties;

    /**
     * 源码解析
     * public Docket(DocumentationType documentationType) {
     *   this.documentationType = documentationType;
     * }
     *
     * public class DocumentationType extends SimplePluginMetadata {
     *   public static final DocumentationType SWAGGER_12 = new DocumentationType("swagger", "1.2");
     *   public static final DocumentationType SWAGGER_2 = new DocumentationType("swagger", "2.0");
     *   public static final DocumentationType OAS_30 = new DocumentationType("openApi", "3.0");
     *   // ...
     * }
     *
     */

    /**
     * 如何携带公共的请求参数？
     * 不同的架构可能发请求的时候除了携带 TOKEN ，还会携带不同的参数，比如请求的平台，版本等等，这
     * 些每个请求都要携带的参数称之为公共参数。
     * 那么如何在 Swagger 中定义公共的参数呢？比如在请求头中携带。
     * 在 Docket 中的方法 globalRequestParameters() 可以设置公共的请求参数，接收的参数是一个
     * List<RequestParameter> ，因此只需要构建一个 RequestParameter 集合即可，如下：
     * @return
     */
    @Bean
    public Docket frontParamterApi() {
        RequestParameter parameter = new RequestParameterBuilder() //构建一个公共请求参数platform，放在在header
                .name("platform")//参数名称
                .description("请求的平台")//描述
                .in(ParameterType.HEADER)//放在header中
                .required(true)//是否必传
                .build();
        List<RequestParameter> parameters = Collections.singletonList(parameter);//构建一个请求参数集合
        return new Docket(DocumentationType.OAS_30)
                .globalRequestParameters(parameters);
    }

    /**
     * 如何添加授权信息？
     * 现在项目API肯定都需要权限认证，否则不能访问，比如请求携带一个 TOKEN 。
     * 在Swagger中也是可以配置认证信息，这样在每次请求将会默认携带上。
     * 在 Docket 中有如下两个方法指定授权信息，分别是 securitySchemes() 和 securityContexts() 。在配置
     * 类中的配置如下，在构建Docket的时候设置进去即可：
     * @return
     */
    @Bean
    public Docket frontApi() {
        RequestParameter parameter = new RequestParameterBuilder()
                .name("platform")
                .description("请求头")
                .in(ParameterType.HEADER)
                .required(true)
                .build();
        List<RequestParameter> parameters = Collections.singletonList(parameter);
        return new Docket(DocumentationType.OAS_30)
                .enable(properties.getFront().getEnable())//是否开启，根据环境配置
                .groupName(properties.getFront().getGroupName())
                .apiInfo(frontApiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage(properties.getFront().getBasePackage()))//指定扫描的包
                .paths(PathSelectors.any())
                .build()
                .securitySchemes(securitySchemes())
                .securityContexts(securityContexts());
    }

    /**
     * 设置授权信息
     */
    private List<SecurityScheme> securitySchemes() {
        ApiKey apiKey = new ApiKey("BASE_TOKEN", "token", In.HEADER.toValue());
        return Collections.singletonList(apiKey);
    }
    /**
     * 授权信息全局应用
     */
    private List<SecurityContext> securityContexts() {
        return Collections.singletonList(
                SecurityContext.builder()
                .securityReferences(Collections.singletonList(new
                        SecurityReference("BASE_TOKEN", new AuthorizationScope[]{new AuthorizationScope("global", "")})))
                        .build()
        );
    }


    @Bean
    public Docket frontCommonApi() {
        return new Docket(DocumentationType.OAS_30)
                //是否开启，根据环境配置
                .enable(properties.getFront().getEnable())
                .groupName(properties.getFront().getGroupName())
                .apiInfo(frontApiInfo())
                .select()
                //指定扫描的包
                .apis(RequestHandlerSelectors.basePackage(properties.getFront().getBasePackage()))
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * 前台API信息
     */
    private ApiInfo frontApiInfo() {
        return new ApiInfoBuilder()
                .title(properties.getFront().getTitle())
                .description(properties.getFront().getDescription())
                .version(properties.getFront().getVersion())
                .contact( //添加开发者的一些信息
                        new Contact(properties.getFront().getContactName(),
                                properties.getFront().getContactUrl(),
                                properties.getFront().getContactEmail()))
                .build();
    }

    /**
     * 后台API
     */
    @Bean
    public Docket backApi() {
        return new Docket(DocumentationType.OAS_30)
//是否开启，根据环境配置
                .enable(properties.getBack().getEnable())
                .groupName("后台管理")
                .apiInfo(backApiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage(properties.getBack().getBasePackage()))
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * 后台API信息
     */
    private ApiInfo backApiInfo() {
        return new ApiInfoBuilder()
                .title(properties.getBack().getTitle())
                .description(properties.getBack().getDescription())
                .version(properties.getBack().getVersion())
                .contact( //添加开发者的一些信息
                        new Contact(properties.getBack().getContactName(),
                                properties.getBack().getContactUrl(),
                                properties.getBack().getContactEmail()))
                .build();
    }
    /**
     * @return swagger config
     */
    @Bean
    public Docket openApi() {
        return new Docket(DocumentationType.OAS_30)
                .groupName("Test group")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any())
                .build()
                .globalRequestParameters(getGlobalRequestParameters())
                .globalResponses(HttpMethod.GET, getGlobalResponse());
    }

    /**
     * @return global response code->description
     */
    private List<Response> getGlobalResponse() {
        return ResponseStatus.HTTP_STATUS_ALL.stream().map(
                a -> new ResponseBuilder().code(a.getResponseCode()).description(a.getDescription()).build())
                .collect(Collectors.toList());
    }

    /**
     * @return global request parameters
     */
    private List<RequestParameter> getGlobalRequestParameters() {
        List<RequestParameter> parameters = new ArrayList<>();
        parameters.add(new RequestParameterBuilder()
                .name("AppKey")
                .description("App Key")
                .required(false)
                .in(ParameterType.QUERY)
                .query(q -> q.model(m -> m.scalarModel(ScalarType.STRING)))
                .required(false)
                .build());
        return parameters;
    }
    /**
     * @return api info
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Swagger API")
                .description("test api")
                .contact(new Contact("pdai", "http://pdai.tech", "suzhou.daipeng@gmail.com"))
                .termsOfServiceUrl("http://xxxxxx.com/")
                .version("1.0")
                .build();
    }

    /**
     * PathSelectors常用方法有：
     * any() // 任何请求都扫描
     * none() // 任何请求都不扫描
     * regex(final String pathRegex) // 通过正则表达式控制
     * ant(final String antPattern) // 通过ant()控制
     * @return
     */
    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.OAS_30).
                groupName("swagger-3").
                apiInfo(apiInfo()).
                select().
                // 通过 .select() 方法去配置扫描接口, RequestHandlerSelectors 配置如何扫描接口
                        apis(RequestHandlerSelectors.basePackage("com.swagger.first.controller")).
                // apis(RequestHandlerSelectors.any()). // 扫描所有, 项目中的所有接口都会被扫描到
                // apis(RequestHandlerSelectors.none()). // 不扫描接口
                // 通过类上的注解扫描, Controller.class : 只扫描有 @Controller 注解的类
                // apis(RequestHandlerSelectors.withClassAnnotation(Controller.class)).
                // 通过方法上的注解扫描, GetMapping.class : 只扫描 get 请求, 方法上有注解 @GetMapping()
                // apis(RequestHandlerSelectors.withMethodAnnotation(GetMapping.class)).
                        build();
    }
}
