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
	 * ģ������
	 * @return
	 */
	String module() default "";
	/**
	 * ����
	 * @return
	 */
	String method() default "";
	/**
	 * ����
	 * @return
	 */
    String description() default "";
	
}
