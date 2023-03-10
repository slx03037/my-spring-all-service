package com.slx.springboot.aop.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @program: my-spring-all-service
 * @description: 日志切面
 * @author: shenlx
 * @create: 2023-01-06 19:00
 **/


@Aspect
/**
 *配置切面bean 和<bean.../>元素进行配置无区别，一样支持依赖注入来配置属性值
 * 如果启动了spring的”零配置“特性，一样可以让你spring自动搜索，并加载指定路径下的切面bean
 * 使用@Aspect修饰的类（切面类）和其他类一样可以有方法，成员变量定义，还可能包括切入点，增强处理定义
 * 使用@Aspect修饰类，Spring将不会把该Bean当成组件Bean处理，因此负责自动增强的后处理Bean将会略过该bean，不会对该Bean进行任何增强处理
 */
@Component
@Slf4j
public class SpringbootAspect {
    /**
     * 格式
     * execution(public * 包名.类名.方法名)
     * com.slx.springboot.aop.controller
     *
     * 切入点表达式的写法：
     *             关键字：execution(表达式)
     *             表达式：
     *                 访问修饰符  返回值  包名.包名.包名...类名.方法名(参数列表)
     *             标准的表达式写法：
     *                 public void com.itheima.service.impl.AccountServiceImpl.saveAccount()
     *             访问修饰符可以省略
     *                 void com.itheima.service.impl.AccountServiceImpl.saveAccount()
     *             返回值可以使用通配符，表示任意返回值
     *                 * com.itheima.service.impl.AccountServiceImpl.saveAccount()
     *             包名可以使用通配符，表示任意包。但是有几级包，就需要写几个*.
     *                 * *.*.*.*.AccountServiceImpl.saveAccount())
     *             包名可以使用..表示当前包及其子包
     *                 * *..AccountServiceImpl.saveAccount()
     *             类名和方法名都可以使用*来实现通配
     *                 * *..*.*()
     *             参数列表：
     *                 可以直接写数据类型：
     *                     基本类型直接写名称           int
     *                     引用类型写包名.类名的方式   java.lang.String
     *                 可以使用通配符表示任意类型，但是必须有参数
     *                 可以使用..表示有无参数均可，有参数可以是任意类型
     *             全通配写法：
     *                 * *..*.*(..)
     *
     *             实际开发中切入点表达式的通常写法：
     *                 切到业务层实现类下的所有方法
     *                     * com.itheima.service.impl.*.*(..)
     */
    @Pointcut("execution(public * com.slx.springboot.aop.controller.*.*(..))")
    public void pointcut() {
    }

    @Before("pointcut()")
    public void before(JoinPoint joinPoint) {
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        // 记录下请求内容
        System.out.println("URL : " + request.getRequestURL().toString());
        System.out.println("HTTP_METHOD : " + request.getMethod());
        System.out.println("IP : " + request.getRemoteAddr());
        System.out.println("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        System.out.println("ARGS : " + Arrays.toString(joinPoint.getArgs()));
    }

    @AfterReturning(returning = "ret", pointcut = "pointcut()")
    public void doAfterReturning(Object ret) throws Throwable {
        // 处理完请求，返回内容
        System.out.println("方法的返回值 : " + ret);
    }

    //后置异常通知
    @AfterThrowing("pointcut()")
    public void throwss(JoinPoint jp){
        System.out.println("方法异常时执行.....");
    }

    //后置最终通知,final增强，不管是抛出异常或者正常退出都会执行
    @After("pointcut()")
    public void after(JoinPoint jp){
        System.out.println("方法最后执行.....");
    }

    //环绕通知,环绕增强，相当于MethodInterceptor
    @Around("pointcut()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable{
        System.out.println("方法环绕start.....");
        try {
            Object o =  pjp.proceed();
            saveLog(pjp);
            System.out.println("方法环绕proceed，结果是 :" + o);
            return o;
        } catch (Throwable e) {
            throw e;
        }
    }

    private void saveLog(ProceedingJoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        // 请求的方法名
        String className = joinPoint.getTarget().getClass().getName();
        log.info("类名名：{}",className);
        String methodName = signature.getName();
        log.info("方法名：{}",className);
        // 请求的方法参数值
        Object[] args = joinPoint.getArgs();
        log.info("方法参数值：{}",args);
        // 请求的方法参数名称
        LocalVariableTableParameterNameDiscoverer u = new LocalVariableTableParameterNameDiscoverer();
        String[] paramNames = u.getParameterNames(method);
        log.info("方法参名称：{}",args);
        // 获取request
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String ip = request.getRemoteAddr();
        // HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        // 设置IP地址
        log.info("ip地址为：{}", ip);
        // 模拟一个用户名
        // 保存系统日志
    }
}
