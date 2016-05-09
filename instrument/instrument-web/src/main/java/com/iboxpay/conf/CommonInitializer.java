package com.iboxpay.conf;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.core.annotation.Order;
import org.springframework.web.WebApplicationInitializer;

import ch.qos.logback.ext.spring.web.LogbackConfigListener;

/** 
* Comments:  web.xml                                           
* Author：        zengbo                
* Create Date： 2016-3-20 
* Version:    1.0.0                     
*/
@Order(1)
public class CommonInitializer implements WebApplicationInitializer {

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		/*
		 * 加载日志文件、添加Listener
		 */
		servletContext.setInitParameter("logbackConfigLocation", "classpath:logback.xml");
		servletContext.addListener(LogbackConfigListener.class);

	}
}
