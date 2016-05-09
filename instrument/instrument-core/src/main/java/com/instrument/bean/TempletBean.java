package com.instrument.bean;

import java.util.List;

public class TempletBean {

	private List<InputBean> list;
	private String id;
	private String desc;

	public List<InputBean> getList() {
		return list;
	}

	public void setList(List<InputBean> list) {
		this.list = list;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

}
