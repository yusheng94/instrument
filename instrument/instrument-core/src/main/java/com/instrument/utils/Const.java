package com.instrument.utils;

/**
 * 
 * The class Const.
 *
 * Description:	配置相关信息
 *
 * @author: zengbo
 * @since: 2016年4月19日
 * @version: $Revision$ $Date$ $LastChangedBy$
 *
 */
public interface Const {

	int INDEX = 0; //要批量使用的那个字段，在excel中的列号（从0开始）
	String SAVEPATH = "E:\\"; //脚本文件要存放本地的路径
	String S0001 = "SUCCESS_GENERATE_SQL";//生成脚本成功
	String S0002 = "SUCCESS_UPLOAD_FILE";//上传文件成功
	String E0001 = "FILE_FORMATE_ERROR"; //上传文件格式错误，只允许.xlsx文件
	String E0002 = "FILE_UPLOAD_FAILED"; //文件上传失败
	String E0003 = "GENERAGET_SQL_FALIED"; //生成脚本文件失败
}
