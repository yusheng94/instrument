package com.iboxpay.conf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.HandlerAdapter;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

/** 
* Comments:  spring-mvc.xml                                          
* Author：        zengbo                
* Create Date： 2016-3-20 
* Version:    1.0.0                     
*/
@Configuration
//用于在基于Java类定义Bean配置中开启MVC支持，和XML中的<mvc:annotation-driven>功能一样；
@EnableWebMvc
@ComponentScan(basePackages = "com.instrument", useDefaultFilters = false, includeFilters = { @ComponentScan.Filter(type = FilterType.ANNOTATION, value = { Controller.class }) })
public class MvcConfig extends WebMvcConfigurationSupport {

	private static final Logger logger = LoggerFactory.getLogger(MvcConfig.class);

	/*
	 *  <注册servlet适配器>
	 * <只需要在自定义的servlet上用@Controller("映射路径")标注即可>
	 */
	@Bean
	public HandlerAdapter servletHandlerAdapter() {
		logger.info("HandlerAdapter");
		return new RequestMappingHandlerAdapter();
	}

	/*
	 * 描述 : <RequestMappingHandlerMapping需要显示声明，否则不能注册自定义的拦截器>
	 */
	@Bean
	public RequestMappingHandlerMapping requestMappingHandlerMapping() {
		logger.info("RequestMappingHandlerMapping");
		return new RequestMappingHandlerMapping();
	}

	/*
	 * <HandlerMapping需要显示声明，否则不能注册资源访问处理器>
	 */
	@Bean
	public HandlerMapping resourceHandlerMapping() {
		logger.info("HandlerMapping");
		return super.resourceHandlerMapping();
	}

	/*
	 * <资源访问处理器>
	 * @param registry
	 */
	@Override
	protected void addResourceHandlers(ResourceHandlerRegistry registry) {
		logger.info("addResourceHandlers");
		registry.addResourceHandler("/html/*").addResourceLocations("/WEB-INF/html/");
		registry.addResourceHandler("/js/*").addResourceLocations("/WEB-INF/js/");
		registry.addResourceHandler("/img/*").addResourceLocations("/WEB-INF/img/");
		registry.addResourceHandler("/css/*").addResourceLocations("/WEB-INF/css/");
		registry.addResourceHandler("/fonts/**").addResourceLocations("/WEB-INF/fonts/");
	}

	/**
	 * 文件上传
	 * @return
	 */
	@Bean(name = "multipartResolver")
	public CommonsMultipartResolver commonsMultipartResolver() {
		logger.info("CommonsMultipartResolver");
		CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
		commonsMultipartResolver.setDefaultEncoding("UTF-8");
		return new CommonsMultipartResolver();
	}

	/*
	 * <RequestMappingHandlerAdapter需要显示声明，否则不能注册通用属性编辑器>
	 */
	@Bean
	public RequestMappingHandlerAdapter requestMappingHandlerAdapter() {
		logger.info("RequestMappingHandlerAdapter");
		return super.requestMappingHandlerAdapter();
	}
}
