package com.ank.mysite.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Syslog {
	
	/**
	 * 模块名称
	 * @return
	 */
	String module() default "";
	/**
	 * 方法
	 * @return
	 */
	String method() default "";
	/**
	 * 描述
	 * @return
	 */
    String description() default "";
	
}
