package com.info;

public enum SystemInfo {
	VIRTUAL_MEMORY(65536),
	PHYSICAL_MEMORY(32768),
	PAGE_SIZE(4096),
	VIRTUAL_PAGES(16),
	MEMORY_FRAMES(8),
	TLB_RECORDS(4),
	;
	private int value = 0;
	private SystemInfo(int value) {
		this.value = value;
	}
	public int getValue() {
		return value;
	}
}
