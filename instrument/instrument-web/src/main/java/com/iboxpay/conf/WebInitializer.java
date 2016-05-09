package com.iboxpay.conf;

import javax.servlet.Filter;

import org.springframework.core.annotation.Order;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/** 
* Comments:  spring-servlet.xml                                           
* Author：        zengbo                
* Create Date： 2016-3-20 
* Version:    1.0.0                     
*/
@Order(2)
public class WebInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    /* 
     * 应用上下文，除web部分 
     */
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[] { AppConfig.class };
    }

    /* 
     * web上下文 
     */
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[] { MvcConfig.class };
    }

    /* 
    * DispatcherServlet的映射路径 
    */
    @Override
    protected String[] getServletMappings() {
        return new String[] { "/" };
    }

    /* 
    * 注册过滤器，映射路径与DispatcherServlet一致，
    * 路径不一致的过滤器需要注册到另外的WebApplicationInitializer中 
    */
    @Override
    protected Filter[] getServletFilters() {
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");
        characterEncodingFilter.setForceEncoding(true);
        return new Filter[] { characterEncodingFilter };
    }

}
