package com.shikk.data;

import java.util.ArrayList;
import java.util.Date;

public class BrushData {
	private long id;
	private Date time; // 刷的时间
	private Date firstInstTime;
	private long appId;
	private int newQuality;
	private int remainQuality;
	private String deviceImei; // 添加留存量的时候 需要将新增量的信息一同保存 
	private String deviceName; // 添加留存量的时候 需要将新增量的信息一同保存 
	private String brushed_models;
	private ArrayList<Float> daylyRate;
	public BrushData() {
		daylyRate = new ArrayList<>();
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	
	public Date getFirstInstTime() {
		return firstInstTime;
	}
	public void setFirstInstTime(Date firstInstTime) {
		this.firstInstTime = firstInstTime;
	}
	public long getAppId() {
		return appId;
	}
	public void setAppId(long appId) {
		this.appId = appId;
	}
	public int getNewQuality() {
		return newQuality;
	}
	public void setNewQuality(int newQuality) {
		this.newQuality = newQuality;
	}
	public int getRemainQuality() {
		return remainQuality;
	}
	public void setRemainQuality(int remainQuality) {
		this.remainQuality = remainQuality;
	}
	public ArrayList<Float> getDaylyRate() {
		return daylyRate;
	}
	public void setDaylyRate(ArrayList<Float> daylyRate) {
		this.daylyRate = daylyRate;
	}
	public String getDeviceImei() {
		return deviceImei;
	}
	public void setDeviceImei(String deviceImei) {
		this.deviceImei = deviceImei;
	}
	public String getDeviceName() {
		return deviceName;
	}
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
	public String getBrushed_models() {
		return brushed_models;
	}
	public void setBrushed_models(String brushed_models) {
		this.brushed_models = brushed_models;
	}
	
}
