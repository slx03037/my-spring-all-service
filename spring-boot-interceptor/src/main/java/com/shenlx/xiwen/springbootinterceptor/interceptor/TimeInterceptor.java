package com.shenlx.xiwen.springbootinterceptor.interceptor;

import com.shenlx.xiwen.springbootinterceptor.aspect.RepeatSubmit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-03-14 15:28
 **/

/**
 * 重复请求的拦截器
 *
 * @Component：该注解将其注入到IOC容器中
 */

@Component
@Slf4j
public class TimeInterceptor implements HandlerInterceptor {

    /**
     * Redis的API
     */
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    /**
     * preHandler方法，在controller方法之前执行
     *
     * 判断条件仅仅是用了uri，实际开发中根据实际情况组合一个唯一识别的条件。
     */


    /**
     * preHandler方法，在controller方法之前执行
     * <p>
     * 判断条件仅仅是用了uri，实际开发中根据实际情况组合一个唯一识别的条件。
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        /**
         *          该方法会在控制器方法前执行，其返回值表示是否知道如何写一个接口。中断
         *         后续操作。当其返回值为 true 时，表示继续向下执行；当其返回值为 false 时，会中断后续的所
         *         有操作（包括调用下一个拦截器和控制器类中的方法执行等）。
         */

        System.out.println("处理拦截之前");
        request.setAttribute("startTime", System.currentTimeMillis());
        System.out.println(((HandlerMethod) handler).getBean().getClass().getName());
        System.out.println(((HandlerMethod) handler).getMethod().getName());

        if (handler instanceof HandlerMethod) {
            //只拦截标注了@RepeatSubmit该注解
            HandlerMethod method = (HandlerMethod) handler;
            //标注在方法上的@RepeatSubmit
            RepeatSubmit repeatSubmitByMethod =
                    AnnotationUtils.findAnnotation(method.getMethod(), RepeatSubmit.class);
            //标注在controler类上的@RepeatSubmit
            RepeatSubmit repeatSubmitByCls =
                    AnnotationUtils.findAnnotation(method.getMethod().getDeclaringClass(), RepeatSubmit.class);
            //没有限制重复提交，直接跳过
            if (Objects.isNull(repeatSubmitByMethod) && Objects.isNull(repeatSubmitByCls)){
                return true;
            }

            // todo: 组合判断条件，这里仅仅是演示，实际项目中根据架构组合条件
            //请求的URI
            String uri = request.getRequestURI();
            //存在即返回false，不存在即返回true
            //注意：标注在方法上的超时时间会覆盖掉类上的时间，因为如下一段代码：
            Boolean ifAbsent = stringRedisTemplate.opsForValue().setIfAbsent(uri, "",
                    Objects.nonNull(repeatSubmitByMethod) ? repeatSubmitByMethod.seconds() : repeatSubmitByCls.seconds(),
                    TimeUnit.SECONDS);
            //如果存在，表示已经请求过了，直接抛出异常，由全局异常进行处理返回指定信息
            if (ifAbsent != null && !ifAbsent){
                //throw new RepeatSubmitException();
                throw new RuntimeException();
            }

        }
        return true;

    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        /**
         * 该方法会在控制器方法调用之后，且解析视图之前执行。可以通过此方法对
         * 请求域中的模型和视图做出进一步的修改。
         */
        System.out.println("开始处理拦截");
        Long start = (Long) httpServletRequest.getAttribute("startTime");
        System.out.println("【拦截器】耗时 " + (System.currentTimeMillis() - start));
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        /**
         * 该方法会在整个请求完成，即视图渲染结束之后执行。可以通过此方法
         * 实现一些资源清理、记录日志信息等工作。
         */
        System.out.println("处理拦截之后");
        Long start = (Long) httpServletRequest.getAttribute("startTime");
        System.out.println("【拦截器】耗时 " + (System.currentTimeMillis() - start));
        System.out.println("异常信息 " + e);
    }
}
