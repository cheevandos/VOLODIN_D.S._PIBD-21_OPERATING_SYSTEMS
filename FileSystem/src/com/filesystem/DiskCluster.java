package com.filesystem;

import static com.systeminfo.SystemInfo.*;

public class DiskCluster {

	private char[] data;
	
	public DiskCluster() {
		data = new char[CLUSTER_SIZE.getValue()];
	}
	
	public char[] getData() {
		return data;
	}
	
	public void setData(char[] data) {
		this.data = data;
	}
}
