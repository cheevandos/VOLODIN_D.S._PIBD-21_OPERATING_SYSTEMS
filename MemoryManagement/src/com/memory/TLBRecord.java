package com.memory;

import static com.info.SystemInfo.*;

public class TLBRecord {
	
	private boolean isValid;
	private int virtualPage;
	private boolean isModified;
	private int pageFrame;
	
	public TLBRecord (boolean validBit, int page, int frame) {
		if (page >= 0 && page < VIRTUAL_PAGES.getValue() && frame >= 0 
				&& frame < MEMORY_FRAMES.getValue()) {
			virtualPage = page;
			pageFrame = frame;
			isValid = validBit;
		} else {
			throw new IllegalArgumentException("Virtual page or frame out of range");
		}
	}
	
	public boolean getValidBit() {
		return isValid;
	}
	
	public void setValidBit(boolean validBit) {
		isValid = validBit;
	}
	
	public boolean getModBit() {
		return isModified;
	}
	
	public void setModBit(boolean modBit) {
		isModified = modBit;
	}
	
	public int getVirtualPage() {
		return virtualPage;
	}
	
	public int getPageFrame() {
		return pageFrame;
	}
}
