package com.filesystem;

public class File {

	private String fileName;
	private int firstBlock;
	
	public File(String name, int first) {
		fileName = name;
		firstBlock = first;
	}
	
	public String getName() {
		return fileName;
	}
	
	public int getFirstBlock() {
		return firstBlock;
	}
}
