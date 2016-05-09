package com.iboxpay.conf;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import com.instrument.bean.TempletBean;
import com.instrument.utils.XmlParseUtils;

@Configuration
public class TemplateConf {

	private static final Logger logger = LoggerFactory.getLogger(TemplateConf.class);

	@javax.annotation.Resource(name = "uixml")
	Map<String, TempletBean> uiMap;

	@javax.annotation.Resource(name = "sqlxml")
	Map<String, String> sqlMap;

	@Bean(name = "uixml")
	public Map<String, TempletBean> getUIXml() throws IOException {
		logger.info("Templateconf getUIXml");
		uiMap = new HashMap<String, TempletBean>();
		PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		Resource[] resources = resolver.getResources("classpath:ui/*ui.xml");
		String path = null;
		for (Resource resource : resources) {
			path = resource.getURI().getPath();
			TempletBean templetBean = XmlParseUtils.parserXml(path);
			uiMap.put(templetBean.getId(), templetBean);
		}
		return uiMap;
	}

	@Bean(name = "sqlxml")
	public Map<String, String> getSqlXml() throws IOException {
		logger.info("Templateconf getSqlXml");
		sqlMap = new HashMap<String, String>();
		PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		Resource resource = resolver.getResource("classpath:sql/sql.xml");
		String path = resource.getURI().getPath();
		sqlMap = XmlParseUtils.parserSqlXml(path);

		for (Map.Entry<String, String> submap : sqlMap.entrySet()) {
			logger.info(submap.getKey() + ":" + submap.getValue());
		}
		return sqlMap;
	}
}
