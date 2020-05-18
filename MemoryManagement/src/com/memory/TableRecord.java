package com.memory;

import static com.info.SystemInfo.*;

public class TableRecord {
	
	private int pageFrame;
	private boolean isPresented;
	private boolean isModified;
	private boolean isReferenced;
	
	public TableRecord() {
		pageFrame = -1;
		isPresented = false;
		isModified = false;
		isReferenced = false;
	}
	
	public void setFrame(int frame) {
		if  (frame >= 0 && frame < VIRTUAL_PAGES.getValue()) {
			pageFrame = frame;
			isReferenced = true;
			isPresented = true;
		} else {
			throw new IllegalArgumentException("Frame out of bounds");
		}
	}
	
	public void reset() {
		pageFrame = -1;
		isPresented = false;
		isModified = false;
		isReferenced = false;
	}
	
	public int getFrame() {
		if (isPresented) {
			return pageFrame;
		} else {
			return -1;
		}
	}
	
	public boolean getModBit() {
		return isModified;
	}
	
	public void setModBit(boolean modBit) {
		isModified = modBit;
	}
	
	public boolean getRefBit() {
		return isReferenced;
	}
	
	public void setRefBit(boolean refBit) {
		isReferenced = refBit;
	}
	
	public boolean isPresented() {
		return isPresented;
	}
}
