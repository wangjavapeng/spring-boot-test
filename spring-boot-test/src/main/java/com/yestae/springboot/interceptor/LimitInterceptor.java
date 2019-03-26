//package com.yestae.springboot.interceptor;
//
//import java.io.PrintWriter;
//import java.lang.reflect.Method;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.method.HandlerMethod;
//import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
//
//import com.yestae.springboot.annotation.LimitAnnotation;
//import com.yestae.springboot.service.RedisService;
//
//public class LimitInterceptor extends HandlerInterceptorAdapter {
//	@Autowired
//	private RedisService redisService;
//	
//	 @Override
//	    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//		 try {
//            if(handler instanceof HandlerMethod){
//                HandlerMethod handlerMethod = (HandlerMethod) handler;
//                Method method = handlerMethod.getMethod();
//                
//                if(!method.isAnnotationPresent(LimitAnnotation.class)){
//                	return true;
//                }
//                
//                //限流
//                LimitAnnotation limitAnnotation = method.getAnnotation(LimitAnnotation.class);
//                String bizType = limitAnnotation.bizType();	//接口业务类型
//                String limit = limitAnnotation.limit();
//                String sec = String.valueOf(limitAnnotation.sec());
//                String ip = getIp(request);
//                //被限流了
//                if(redisService.isLimit(bizType + "-" + ip + "-" + System.currentTimeMillis() / 1000 / 60, limit, sec)){
//                	response.setContentType("application/json;charset=utf-8");
//            		response.setHeader("Cache-Control", "no-store");
//            		response.setHeader("Pragma", "no-cache");
//            		response.setDateHeader("Expires", 0);
//            		PrintWriter out = response.getWriter();
//            		out.print("ip: " + ip + " aop拦截，操作太频繁啦，请一分钟后再试。。。");
//            		out.flush();
//            		out.close();
//            		return false;
//                }else{
//                	return true;
//                }
//                
//             }
//	     } catch (Exception e) {
//	    	 e.printStackTrace();
//	    	 return false;
//	     }
//	 return true;
//    }
//
//	private String getIp(HttpServletRequest request) {
//		if (request == null) {
//			return "127.0.0.1";
//		}
//		String ip = request.getHeader("x-forwarded-for");
//		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//			ip = request.getHeader("Proxy-Client-IP");
//		}
//		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//			ip = request.getHeader("X-Forwarded-For");
//		}
//		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//			ip = request.getHeader("WL-Proxy-Client-IP");
//		}
//		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//			ip = request.getHeader("X-Real-IP");
//		}
//
//		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//			ip = request.getRemoteAddr();
//		}
//		return ip.replaceAll(":", ".");
//	}
//}
