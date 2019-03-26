package com.yestae.springboot.aop;

import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.ReflectUtils;
import org.springframework.stereotype.Component;

import com.yestae.springboot.annotation.LimitAnnotation;
import com.yestae.springboot.service.RedisService;

/**
 * 限流Aop
 * @author wangpeng
 *
 */
@Aspect
@Component
public class LimitAop {
	
	@Autowired
	private RedisService redisService;
	
	@Pointcut("@annotation(com.yestae.springboot.annotation.LimitAnnotation)")
	public void limit(){}
	
	@Around("limit()")
	public Object before(ProceedingJoinPoint point) throws Throwable{
        // 返回目标对象
        Object target = point.getTarget();
        String targetName = target.getClass().getName();
        // 返回当前连接点签名
        String methodName = point.getSignature().getName();
        // 获得参数列表
        Object[] arguments = point.getArgs();

        Class<?> targetClass = Class.forName(targetName);
        // 获取参数类型数组
        Class<?>[] argTypes = ReflectUtils.getClasses(arguments);
        // 获取目标method,考虑方法的重载等问题
        Method method = targetClass.getDeclaredMethod(methodName, argTypes);
        // 获取目标method上的限流注解@Limiter
        if (method.isAnnotationPresent(LimitAnnotation.class)) {
        	LimitAnnotation limiter = method.getAnnotation(LimitAnnotation.class);
            // 以 class + method + parameters为key，避免重载、重写带来的混乱
        	String bizType = limiter.bizType();	//接口业务类型
            String limit = limiter.limit();
            String sec = String.valueOf(limiter.sec());
             
             //被限流了
             if(redisService.isLimit(bizType + System.currentTimeMillis() / 1000 / 60, limit, sec)){
//            	ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
//             	HttpServletResponse response = attributes.getResponse();
//             	response.setContentType("application/json;charset=utf-8");
//         		response.setHeader("Cache-Control", "no-store");
//         		response.setHeader("Pragma", "no-cache");
//         		response.setDateHeader("Expires", 0);
//         		PrintWriter out = response.getWriter();
//         		out.print("aop拦截，操作太频繁啦。。。");
//         		out.flush();
//         		out.close();
            	return "aop拦截，操作太频繁啦。。。";
             }
        }
        //处理目标
        return point.proceed();
	}
	
	@After("limit()")
	public void after(JoinPoint joinPoint) throws Throwable{
        // 记录下请求内容
        System.err.println("-------------------aop拦截结束-----------------");  
	}
	
}
