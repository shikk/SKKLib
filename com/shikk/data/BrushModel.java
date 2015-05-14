package com.shikk.data;

import java.util.ArrayList;
import java.util.List;

public class BrushModel {
	private int id;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	private String name;
	private Boolean checked;
//	private int appId;
	private List<Float> brushRate;
	private String injectFilePath;
	
	public BrushModel(){
		brushRate = new ArrayList<>();
		checked = new Boolean(false);
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Boolean getChecked() {
		return checked;
	}
	public void setChecked(Boolean checked) {
		this.checked = checked;
	}
	public List<Float> getBrushRate() {
		return brushRate;
	}
	public void setBrushRate(List<Float>  brushRate) {
		this.brushRate = brushRate;
	}
	public String getInjectFilePath() {
		return injectFilePath;
	}
	public void setInjectFilePath(String injectFilePath) {
		this.injectFilePath = injectFilePath;
	}
	
	
}
