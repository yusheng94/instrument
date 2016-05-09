package com.instrument.controller;

import java.io.File;
import java.net.URISyntaxException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.instrument.bean.TempletBean;
import com.instrument.service.GenerateService;
import com.instrument.utils.CodeDes;
import com.instrument.utils.Const;
import com.instrument.utils.OperateFileUtils;

/**
 * 
 * The class GenerateController.
 *
 * Description:生成脚本
 *
 * @author: zengbo
 * @since: 2016年4月20日
 * @version: $Revision$ $Date$ $LastChangedBy$
 *
 */
@Controller
public class GenerateController {

	private static final Logger logger = LoggerFactory.getLogger(GenerateController.class);

	@Autowired
	private GenerateService generateService;

	@Resource(name = "uixml")
	private Map<String, TempletBean> uiMap;

	@Resource(name = "sqlxml")
	private Map<String, String> sqlMap;

	@Resource(name = "download")
	private Map<String, String> downlaodMap;

	/**
	 * 生成脚本
	 * @param map
	 * @return
	 */
	@RequestMapping("/generate")
	public @ResponseBody Map<String, String> generate(
			@RequestBody Map<String, Map<String, Object>> map) {
		logger.info("generate controller begin");
		String id = null;
		Map<String, Object> subMap = new LinkedHashMap<String, Object>();
		for (Map.Entry<String, Map<String, Object>> entry : map.entrySet()) {
			id = entry.getKey();
			subMap = entry.getValue();
		}
		return generateService.generateSql(sqlMap.get(id), subMap);
	}

	/**
	 * 上传文件
	 * @param file
	 * @return
	 * @throws URISyntaxException
	 */
	@RequestMapping(value = "/upload")
	public @ResponseBody Map<String, String> upload(@RequestParam("file") MultipartFile file)
			throws URISyntaxException {
		logger.info("upload filepath begin");
		//获取当前类加载器路径，用于存放文件
		String path = downlaodMap.get("upload_address");
		String fileName = file.getOriginalFilename();
		logger.info("path = " + path + "\t\n" + "fileName = " + fileName);

		//只允许上传.xlsx文件
		String type = fileName.substring(fileName.lastIndexOf("."), fileName.length());
		if (!".xlsx".equalsIgnoreCase(type)) {
			return CodeDes.getCode("E0001", Const.E0001);
		}
		File targetFile = new File(path, fileName);

		if (!targetFile.exists()) {
			targetFile.mkdirs();
		}

		//保存  
		try {
			file.transferTo(targetFile);
		} catch (Exception e) {
			logger.error("上传文件失败", e);
			return CodeDes.getCode("E0002", Const.E0002);
		}
		logger.info("targetFile = " + targetFile.getAbsolutePath());

		return CodeDes.getCode("S0002", Const.S0002);
	}

	/**
	 * 显示界面
	 * @return
	 */
	@RequestMapping("/show")
	public @ResponseBody Map<String, TempletBean> getUiXml() {
		return uiMap;
	}

	/**
	 * 下载文件
	 * @param id
	 * @return
	 */
	@RequestMapping("/download")
	public ResponseEntity<byte[]> download(String id) {
		logger.info("download begin ---- id = " + id);
		try {
			String path = downlaodMap.get("download_address");
			logger.info("path :" + path);

			File[] files = OperateFileUtils.listFile(path);
			if (files == null) {
				return null;
			}
			//把不是本次生成的文件都删除
			for (File file : files) {
				if (!id.equals(file.getName())) {
					OperateFileUtils.deleteFile(file);
				}
			}
			for (File file : files) {
				if (id.equals(file.getName())) {
					HttpHeaders headers = new HttpHeaders();
					String fileName = new String(file.getName().getBytes("UTF-8"), "iso-8859-1");//为了解决中文名称乱码问题  
					headers.setContentDispositionFormData("attachment", fileName);
					headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
					return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), headers,
							HttpStatus.CREATED);
				}
			}

		} catch (Exception e) {
			logger.error("下载失败", e);
		}

		return null;
	}
}
