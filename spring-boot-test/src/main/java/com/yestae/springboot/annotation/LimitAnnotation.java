package com.yestae.springboot.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * 限流注解
 *
 * @Package com.yestae 
 * @ClassName LimitAnnotation 
 * @author wangpeng
 * @date 2019年3月25日 下午5:25:02 
 *
 */
@java.lang.annotation.Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface LimitAnnotation {
	//限流数量
	String limit() default "5";
	//限流业务类型
	String bizType() default "";
	//几秒内允许访问
	int sec() default 5;
}
