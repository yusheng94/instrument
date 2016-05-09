package com.iboxpay.conf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
public class MsgConfig {

	private static final Logger logger = LoggerFactory.getLogger(MsgConfig.class);

	@Bean(name = { "download" })
	public static PropertiesFactoryBean mapper() {
		logger.info("download properties");
		PropertiesFactoryBean bean = new PropertiesFactoryBean();
		bean.setLocations(new ClassPathResource("download.properties"));
		return bean;
	}
}
