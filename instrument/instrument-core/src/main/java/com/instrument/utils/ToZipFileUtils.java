package com.instrument.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * The class ToZipFileUtils.
 *
 * Description: 压缩文件为ZIP格式
 *
 * @author: zengbo
 * @since: 2016年4月18日
 * @version: $Revision$ $Date$ $LastChangedBy$
 *
 */
public class ToZipFileUtils {

	private static final Logger logger = LoggerFactory.getLogger(ToZipFileUtils.class);

	/**
	 * 
	 * @param filePath 文件路径
	 * @return
	 * @throws IOException
	 */
	public static File toZipFile(String filePath) throws IOException {
		logger.info("filePath = " + filePath);
		File target = null;
		File source = new File(filePath);
		if (source.exists()) {

			// 压缩文件名 = 源文件名.zip
			String zipName = source.getName() + ".zip";
			target = new File(source.getParent(), zipName);
			logger.info("source = " + target);
			if (target.exists()) {
				target.delete(); // 删除旧的zip文件
			}
			ZipOutputStream zos = null;
			try {
				zos = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(target)));
				// 添加对应的文件Entry
				addEntry("/", source, zos);
			} catch (IOException e) {
				throw new RuntimeException(e);
			} finally {
				if (zos != null) {
					zos.close();
				}
			}
		}
		return target;
	}

	private static void addEntry(String base, File source, ZipOutputStream zos) throws IOException {
		// 按目录分级，形如：/aaa/bbb.txt
		String entry = base + source.getName();
		if (source.isDirectory()) {
			for (File file : source.listFiles()) {
				// 递归列出目录下的所有文件，添加文件Entry
				addEntry(entry + "/", file, zos);
			}
		} else {
			BufferedInputStream bis = null;
			try {
				byte[] buffer = new byte[1024 * 10];
				bis = new BufferedInputStream(new FileInputStream(source), buffer.length);
				int read = 0;
				zos.putNextEntry(new ZipEntry(entry));
				while ((read = bis.read(buffer, 0, buffer.length)) != -1) {
					zos.write(buffer, 0, read);
				}
				zos.closeEntry();
			} finally {
				if (bis != null) {
					bis.close();
				}
			}
		}
	}
}
