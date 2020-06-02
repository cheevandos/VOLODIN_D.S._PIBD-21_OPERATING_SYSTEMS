package com.systeminfo;

public enum SystemInfo {
	CLUSTER_SIZE(4),
	CLUSTERS(64),
	EOC(-1);
	;
	private int value = 0;
	private SystemInfo(int value) {
		this.value = value;
	}
	public int getValue() {
		return value;
	}
}
