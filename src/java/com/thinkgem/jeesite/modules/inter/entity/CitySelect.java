package com.thinkgem.jeesite.modules.inter.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CitySelect implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	public String id;

	public String n;//省份
	
	public List<CityArea> c=new ArrayList<CityArea>();

	public String getN() {
		return n;
	}

	public void setN(String n) {
		this.n = n;
	}

	public List<CityArea> getC() {
		return c;
	}

	public void setC(List<CityArea> c) {
		this.c = c;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	
	
	
}
