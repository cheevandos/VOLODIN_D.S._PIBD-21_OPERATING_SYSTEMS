package com.filesystem;

public class FatRecord {

	private boolean isBusy;
	private int next;
	
	public FatRecord() {
		isBusy = false;
		next = 0;
	}
	
	public boolean isBusy() {
		return isBusy;
	}
	
	public void setBusy(boolean busy) {
		isBusy = busy;
	}
	
	public int getNext() {
		return next;
	}
	
	public void setNext(int next) {
		this.next = next;
	}
}
