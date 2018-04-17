package com.hengyushop.demo.train;

import java.io.Serializable;
import java.util.ArrayList;

public class TrainInfoBean implements Serializable {
	private TrainInfoChild child;
	private ArrayList<TrainInfoChild> infoChilds;
	
	public ArrayList<TrainInfoChild> getInfoChilds() {
		return infoChilds;
	}

	public void setInfoChilds(ArrayList<TrainInfoChild> infoChilds) {
		this.infoChilds = infoChilds;
	}

	public TrainInfoChild getChild() {
		return child;
	}

	public void setChild(TrainInfoChild child) {
		this.child = child;
	}

}
