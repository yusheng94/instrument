package com.instrument.utils;

import java.io.FileInputStream;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * The class OprateXlsFileUtils.
 *
 * Description:读取xlsx文件
 *
 * @author: zengbo
 * @since: 2016年4月27日
 * @version: $Revision$ $Date$ $LastChangedBy$
 *
 */
public class OprateXlsFileUtils {

	private static final Logger logger = LoggerFactory.getLogger(OprateXlsFileUtils.class);

	/**
	 * 读取xlsx格式的excle
	 * @param path 文件路径
	 * @return excle表内容
	 */
	@SuppressWarnings("resource")
	public static List<List<String>> readXlsx(String path) {
		logger.info("readXlsx begin... " + path);
		List<List<String>> result = null;
		try (InputStream is = new FileInputStream(path)) {
			XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
			result = new ArrayList<List<String>>();
			//获取第一页
			XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);
			//忽略表头
			for (int rowNum = xssfSheet.getFirstRowNum(); rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
				XSSFRow xssfRow = xssfSheet.getRow(rowNum);

				int minColIx = xssfRow.getFirstCellNum();
				int maxColIx = xssfRow.getLastCellNum();
				List<String> rowList = new ArrayList<String>();
				//遍历该行，获取处理每个cell元素
				for (int colIx = minColIx; colIx < maxColIx; colIx++) {
					XSSFCell cell = xssfRow.getCell(colIx);
					if (cell == null) {
						continue;
					}
					rowList.add(getStringVal(cell));
				}
				result.add(rowList);
			}
		} catch (Exception e) {
			logger.error("打开xlsx文件失败 ", e);
		}
		logger.info("readXlsx end... " + result);
		return result;
	}

	/**
	 * 处理数据类型
	 * @param cell
	 * @return
	 */
	private static String getStringVal(Cell cell) {
		logger.info("getStringVal begin... " + cell.toString());
		switch (cell.getCellType()) {
			case Cell.CELL_TYPE_BOOLEAN:
				logger.info("getStringVal end... ");
				return cell.getBooleanCellValue() ? "TRUE" : "FALSE";
			case Cell.CELL_TYPE_FORMULA: //公式处理
				logger.info("getStringVal end... ");
				return cell.getCellFormula();
			case Cell.CELL_TYPE_NUMERIC:
				if (DateUtil.isCellDateFormatted(cell)) {
					DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					logger.info("getStringVal end... ");
					return sdf.format(cell.getDateCellValue());
				} else {
					cell.setCellType(Cell.CELL_TYPE_STRING);
					logger.info("getStringVal end... ");
					return cell.getStringCellValue();
				}
			case Cell.CELL_TYPE_STRING:
				logger.info("getStringVal end... ");
				return cell.getStringCellValue();
			case Cell.CELL_TYPE_BLANK:
				logger.info("getStringVal end... ");
				return "";

			default:
				logger.info("getStringVal end... ");
				return "Unknown Cell Type: " + cell.getCellType();
		}
	}
}
