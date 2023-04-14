@Null:被注释的元素必须为 null
@NotNull:被注释的元素必须不为 null
@AssertTrue:被注释的元素必须为 true
@AssertFalse:被注释的元素必须为 false
@Min(value):被注释的元素必须是一个数字，其值必须大于等于指定的最小值
@Max(value):被注释的元素必须是一个数字，其值必须小于等于指定的最大值
@DecimalMin(value) :被注释的元素必须是一个数字，其值必须大于等于指定的最小值
@DecimalMax(value) :被注释的元素必须是一个数字，其值必须小于等于指定的最大值
@Size(max, min): 被注释的元素的大小必须在指定的范围内
@Digits (integer, fraction): 被注释的元素必须是一个数字，其值必须在可接受的范围内
@Past: 被注释的元素必须是一个过去的日期
@Future: 被注释的元素必须是一个将来的日期
@Pattern(value): 被注释的元素必须符合指定的正则表达式
@Email: 被注释的元素必须是电子邮箱地址
@Length : 被注释的字符串的大小必须在指定的范围内
@NotEmpty 被注释的字符串的必须非空
@Range 被注释的元素必须在合适的范围内


spring-boot-starter-validation做了什么？
这个启动器的自动配置类是 ValidationAutoConfiguration ，最重要的代码就是注入了一个 Validator
（校验器）的实现类，代码如下：
这个有什么用呢？ Validator 这个接口定义了校验的方法，如下：            
@Bean
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
@ConditionalOnMissingBean(Validator.class)
public static LocalValidatorFactoryBean defaultValidator() {
        LocalValidatorFactoryBean factoryBean = new LocalValidatorFactoryBean();
MessageInterpolatorFactory interpolatorFactory = new MessageInterpolatorFactory();
factoryBean.setMessageInterpolator(interpolatorFactory.getObject());
return factoryBean;
}
这个有什么用呢？ Validator 这个接口定义了校验的方法，如下：
<T> Set<ConstraintViolation<T>> validate(T object, Class<?>... groups);
<T> Set<ConstraintViolation<T>> validateProperty(T object,
String propertyName,
Class<?>... groups);
<T> Set<ConstraintViolation<T>> validateValue(Class<T> beanType,
String propertyName,
Object value,
Class<?>... groups);


