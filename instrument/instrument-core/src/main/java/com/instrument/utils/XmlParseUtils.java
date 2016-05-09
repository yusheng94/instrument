package com.instrument.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.instrument.bean.InputBean;
import com.instrument.bean.TempletBean;

/**
 * 
 * The class XmlParseUtils.
 *
 * Description:解析xml文件
 *
 * @author: zengbo
 * @since: 2016年4月26日
 * @version: $Revision$ $Date$ $LastChangedBy$
 *
 */
public class XmlParseUtils {

	private static final Logger logger = LoggerFactory.getLogger(XmlParseUtils.class);

	/**
	 * 解析xml文档
	 * @param fileName
	 */
	@SuppressWarnings("unchecked")
	public static TempletBean parserXml(String fileName) {
		TempletBean templetBean = new TempletBean();
		List<InputBean> list = new ArrayList<InputBean>();
		File inputXml = new File(fileName);
		SAXReader saxReader = new SAXReader();
		try {
			//根节点的属性（第一级）
			Document document = saxReader.read(inputXml);
			Element root = document.getRootElement();
			templetBean.setId(root.attributeValue("id"));
			templetBean.setDesc(root.attributeValue("desc"));
			InputBean inputBean = null;
			//第二级子节点
			for (Iterator<Element> i = root.elementIterator(); i.hasNext();) {
				Element node = i.next();
				inputBean = new InputBean();
				inputBean.setId(node.attributeValue("id"));
				inputBean.setType(node.attributeValue("type"));
				inputBean.setLabel(node.attributeValue("label"));
				inputBean.setValue(node.attributeValue("value"));
				list.add(inputBean);
			}

		} catch (DocumentException e) {
			logger.error("读取文件失败", e);
		}
		logger.info("dom4j parserXml end");
		templetBean.setList(list);
		return templetBean;
	}

	@SuppressWarnings("unchecked")
	public static Map<String, String> parserSqlXml(String fileName) {
		Map<String, String> map = new HashMap<String, String>();
		File inputXml = new File(fileName);
		SAXReader saxReader = new SAXReader();
		try {
			//根节点的属性（第一级）
			Document document = saxReader.read(inputXml);
			Element root = document.getRootElement();
			//第二级子节点
			for (Iterator<Element> i = root.elementIterator(); i.hasNext();) {
				Element node = i.next();
				map.put(node.attributeValue("id"), node.getText());
			}

		} catch (DocumentException e) {
			logger.error("读取文件失败", e);
		}
		return map;
	}
}
