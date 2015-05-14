package com.shikk.data;

import java.util.ArrayList;
import java.util.List;

public class InjectEvent {
	private List<String> eventCommands;
	private float eventPercent;
	
	public InjectEvent(){
		eventPercent = 1;
		eventCommands = new ArrayList<>();
	}
	
	public List<String> getEventCommands() {
		return eventCommands;
	}
	public void setEventCommands(List<String> eventCommands) {
		this.eventCommands = eventCommands;
	}
	public float getEventPercent() {
		return eventPercent;
	}
	public void setEventPercent(float eventPercent) {
		this.eventPercent = eventPercent;
	}
	
	
}
