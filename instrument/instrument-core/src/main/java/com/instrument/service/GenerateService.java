package com.instrument.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.instrument.utils.CodeDes;
import com.instrument.utils.Const;
import com.instrument.utils.OperateFileUtils;
import com.instrument.utils.OprateXlsFileUtils;
import com.instrument.utils.ToZipFileUtils;

/**
 * 
 * The class GenerateService.
 *
 * Description:生成脚本service
 *
 * @author: zengbo
 * @since: 2016年4月18日
 * @version: $Revision$ $Date$ $LastChangedBy$
 *
 */
@Service
public class GenerateService {

	private static final Logger logger = LoggerFactory.getLogger(GenerateService.class);

	@Resource(name = "download")
	private Map<String, String> downlaodMap;

	/**
	 * 生成sql脚本
	 * @param sqlTempalte
	 * @param map
	 */
	public Map<String, String> generateSql(String sqlTempalte, Map<String, Object> map) {

		String sql = "";
		//是否批量
		List<List<String>> excel = getUploadFile();
		if (null == excel) {
			sql = ParseSql(sqlTempalte, map);
		} else {

			//获取某一列的标题
			String title = excel.get(Const.INDEX).get(Const.INDEX);
			for (List<String> clo : excel) {
				if (clo.get(Const.INDEX).equalsIgnoreCase(title)) {
					title = clo.get(Const.INDEX).toUpperCase().trim();
					continue;
				}
				map.put(title, clo.get(Const.INDEX));
				sql += ParseSql(sqlTempalte, map) + "\t\n";
			}
		}

		try {
			String path = downlaodMap.get("download_address");
			String uuid = UUID.randomUUID().toString();
			File file =
					OperateFileUtils.writeToFile(OperateFileUtils.createFile(path)
							.getAbsolutePath(), uuid + ".sql", sql);
			ToZipFileUtils.toZipFile(file.getAbsolutePath());
			OperateFileUtils.deleteFile(file);

			return CodeDes.getCode("address", uuid + ".sql.zip");
		} catch (Exception e) {
			logger.error("操作文件失败", e);
			return CodeDes.getCode("E0003", Const.E0003);
		}
	}

	/**
	 * 获取上传文件内容
	 * @return
	 */
	public List<List<String>> getUploadFile() {
		String filepath = downlaodMap.get("upload_address");
		logger.info("filepath :　" + filepath);
		File[] file = OperateFileUtils.listFile(filepath);
		if (file == null) {
			return null;
		}
		File tempFile = new File(filepath, file[0].getName());

		//获取文件后缀
		filepath = tempFile.getAbsolutePath();
		String type = filepath.substring(filepath.lastIndexOf("."), filepath.length());
		logger.info("filepath = " + filepath + "\t\n" + "type = " + type);
		//读excel文件
		List<List<String>> excel = new ArrayList<List<String>>();
		if (".xlsx".equalsIgnoreCase(type)) {
			excel = OprateXlsFileUtils.readXlsx(filepath);
		}
		//读取之后删除文件
		OperateFileUtils.deleteFile(tempFile);
		return excel;
	}

	/**
	 * 用值替换sql
	 * @param sqlString
	 * @param map
	 * @return
	 */
	public String ParseSql(String sqlString, Map<String, Object> map) {

		logger.info("ParseSql begin" + sqlString);
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			sqlString =
					sqlString.replace("#{" + entry.getKey() + "}", "'"
							+ entry.getValue().toString() + "'");
			logger.info("after replace" + entry.getKey() + ":" + entry.getValue());
		}
		return sqlString;
	}

}
