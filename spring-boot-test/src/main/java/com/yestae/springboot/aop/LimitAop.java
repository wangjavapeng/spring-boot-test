package com.yestae.springboot.aop;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.yestae.springboot.annotation.LimitAnnotation;
import com.yestae.springboot.service.RedisService;

/**
 * 限流Aop，每个IP多少时间能访问多少次，公司网关出口会出现整个公司访问限制
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
        // 返回当前连接点签名
        MethodSignature signature = (MethodSignature)point.getSignature();
        Method method = signature.getMethod();
        // 获取目标method上的限流注解@LimitAnnotation
        if (method.isAnnotationPresent(LimitAnnotation.class)) {
        	LimitAnnotation limiter = method.getAnnotation(LimitAnnotation.class);
        	String bizType = limiter.bizType();	//接口业务类型
            String limit = limiter.limit();
            String sec = String.valueOf(limiter.sec());
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            HttpServletRequest request = attributes.getRequest();
            String ip = getIp(request);
            //获取用户id，然后进行限流
            
             //被限流了
             if(redisService.isLimit(bizType + "-" + ip + "-" + System.currentTimeMillis() / 1000 / 60, limit, sec)){
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
            	return "ip: " + ip + " aop拦截，操作太频繁啦。。。";
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
	
	private String getIp(HttpServletRequest request) {
		if (request == null) {
			return "127.0.0.1";
		}
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("X-Forwarded-For");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("X-Real-IP");
		}

		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip.replaceAll(":", ".");
	}
	
}
