package com.instrument.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * The class OperateFileUtils.
 *
 * Description: 文件操作
 *
 * @author: zengbo
 * @since: 2016年4月19日
 * @version: $Revision$ $Date$ $LastChangedBy$
 *
 */
public class OperateFileUtils {

	private static final Logger logger = LoggerFactory.getLogger(OperateFileUtils.class);

	/**
	 * 
	 * @param filename 文件路径
	 * @param content  文件内容
	 * @throws IOException
	 */
	public static File writeToFile(String path, String filename, String content) throws IOException {

		logger.info("writeToFile begin");
		BufferedWriter bw = null;
		try {
			File file = new File(path, filename);

			if (!file.exists()) {
				file.createNewFile();
			}

			bw = new BufferedWriter(new FileWriter(file.getAbsoluteFile()));
			logger.info("文件路径 = " + file.getAbsolutePath());
			bw.write(content);

			return file;

		} finally {
			if (bw != null) {
				bw.close();
			}
		}
	}

	/**
	 * 文件删除
	 * @param file
	 */
	public static void deleteFile(File file) {
		if (file.exists()) {
			file.delete();
		}
	}

	/**
	 * 创建文件路径
	 * @param path
	 */
	public static File createFile(String path) {
		File f = new File(path);
		if (!f.exists()) {
			f.mkdirs();
		}

		return f;
	}

	/**
	 * 列出某个目录下所有文件
	 */
	public static File[] listFile(String filepath) {
		File file = new File(filepath);

		//判断文件目录是否存在
		if (!file.exists()) {
			return null;
		}

		//是否是一个目录
		if (file.isDirectory()) {
			File[] tempList = file.listFiles();

			//判断目录下是否有文件
			if (tempList == null || tempList.length == 0) {
				return null;
			}

			return tempList;
		}

		return null;
	}
}