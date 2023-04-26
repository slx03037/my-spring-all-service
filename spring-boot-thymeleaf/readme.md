3.2 使用 Thymeleaf
根据 Spring Boot 自动配置原理，先看一下 Thymeleaf 的配置类，从中可以看出 Thymeleaf 的相关配置。我们可以知道 默认存放目录是 templates 文件夹，文件后缀为 .html 且开启了缓存

@ConfigurationProperties(prefix = "spring.thymeleaf")
public class ThymeleafProperties {

	private static final Charset DEFAULT_ENCODING = StandardCharsets.UTF_8;

	public static final String DEFAULT_PREFIX = "classpath:/templates/";

	public static final String DEFAULT_SUFFIX = ".html";
	/**
	 * Whether to enable template caching.
	 */
	private boolean cache = true;


## 为了在开发中编写模版文件时不用重启，可以在配置中关闭缓存。
# 关闭模版缓存
spring.thymeleaf.cache=false
# 如果需要进行其他的配置，可以参考配置类：ThymeleafProperties
# org.springframework.boot.autoconfigure.thymeleaf.ThymeleafProperties
