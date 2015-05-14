package com.shikk.data;

import java.io.File;
import java.util.ArrayList;

public class TargetApp {
	private long appId;
	private String appName;
	private String versionName;
	private int    versionCode;
	private String pkgName;
	private File injectFile;
	private String baseVmName;
	private String modelIds;
	private float UIDPercent;
	private ArrayList<Float> brushRate; //{0.30f,0.25f,0.20f,0.15f}
	private ArrayList<Integer> maxNewQuality;// {300,500,600,400,800};
	
	public TargetApp(){
		brushRate = new ArrayList<Float>();
		for (int i = 0; i < 35; i++) {
			brushRate.add(0.0f);
		}
		maxNewQuality = new ArrayList<Integer>();
		for (int i = 0; i < 7; i++) {
			maxNewQuality.add(0);
		}
	}
	
	public long getAppId() {
		return appId;
	}
	public void setAppId(long appId) {
		this.appId = appId;
	}
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public String getVersionName() {
		return versionName;
	}
	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}
	public int getVersionCode() {
		return versionCode;
	}
	public void setVersionCode(int versionCode) {
		this.versionCode = versionCode;
	}
	public String getPkgName() {
		return pkgName;
	}
	public void setPkgName(String pkgName) {
		this.pkgName = pkgName;
	}

	
	public ArrayList<Float> getBrushRate() {
		return brushRate;
	}

	public void setBrushRate(ArrayList<Float> brushRate) {
		this.brushRate = brushRate;
		if (brushRate.size()<35) {
			int count = 35-this.brushRate.size();
			for (int i = 0; i < count; i++) {
				this.brushRate.add(0.0f);
			}
		}
	}

	public ArrayList<Integer> getMaxNewQuality() {
		return maxNewQuality;
	}

	public void setMaxNewQuality(ArrayList<Integer> maxNewQuality) {
		this.maxNewQuality = maxNewQuality;
		if (maxNewQuality.size()<7) {
			int count = 7-this.maxNewQuality.size();
			for (int i = 0; i < count; i++) {
				this.maxNewQuality.add(0);
			}
		}
	}

	public static void main(String[] args) {
		TargetApp app = new TargetApp();
	}

	public File getInjectFile() {
		return injectFile;
	}

	public void setInjectFile(File injectFile) {
		this.injectFile = injectFile;
	}

	public String getBaseVmName() {
		return baseVmName;
	}

	public void setBaseVmName(String baseVmName) {
		this.baseVmName = baseVmName;
	}

	public String getModelIds() {
		return modelIds;
	}

	public void setModelIds(String modelIds) {
		this.modelIds = modelIds;
	}

	public float getUIDPercent() {
		return UIDPercent;
	}

	public void setUIDPercent(float uIDPercent) {
		UIDPercent = uIDPercent;
	}
	
	
}
