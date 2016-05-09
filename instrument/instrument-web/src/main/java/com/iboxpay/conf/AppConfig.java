package com.iboxpay.conf;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Controller;

import com.iboxpay.test.WatchFile;

/** 
* Comments:                                             
* Author：        zengbo                
* Create Date： 2016-3-20 
* Version:    1.0.0                     
*/
@Configuration
@ComponentScan(basePackages = "com.instrument", excludeFilters = { @ComponentScan.Filter(type = FilterType.ANNOTATION, value = { Controller.class }) })
@Import({ TemplateConf.class, MsgConfig.class, WatchFile.class })
public class AppConfig {

}
