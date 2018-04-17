package com.hengyushop.demo.train;

import java.io.Serializable;
import java.util.Map;

public class MapSer implements Serializable {
	private Map<String, TrainPersonItem> map;

	public Map<String, TrainPersonItem> getMap() {
		return map;
	}

	public void setMap(Map<String, TrainPersonItem> map) {
		this.map = map;
	}

}
