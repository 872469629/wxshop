package com.thinkgem.jeesite.modules.inter.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CityArea implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	public String id;

	public String n;
	
	public List<String> a=new ArrayList<String>();

	public String getN() {
		return n;
	}

	public void setN(String n) {
		this.n = n;
	}

	public List<String> getA() {
		return a;
	}

	public void setA(List<String> a) {
		this.a = a;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	
	
}
