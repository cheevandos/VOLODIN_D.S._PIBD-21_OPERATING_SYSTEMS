package com.filesystem;

public class File {

	private String fileName;
	private char[] fileData;
	
	public File(String name, char[] data) {
		fileName = name;
		fileData = data;
	}
	
	public String getName() {
		return fileName;
	}
	
	public char[] getData() {
		return fileData;
	}
}
