package com.filesystem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import static com.systeminfo.SystemInfo.*;

public class Main {

	private ArrayList<FatRecord> fat;
	private ArrayList<DiskCluster> dataRegion;
	private HashMap<String, Integer> fileNames;
	
	public Main() {
		init();
	}
	
	private int getFreeSpace() {
		int freeClusters = 0;
		for (FatRecord rec : fat) {
			if (!rec.isBusy()) {
				freeClusters++;
			}
		}
		return freeClusters * CLUSTER_SIZE.getValue();
	}
	
	private void init() {
		fat = new ArrayList<FatRecord>();
		dataRegion = new ArrayList<DiskCluster>();
		for (int i = 0; i < CLUSTERS.getValue(); i++) {
			fat.add(i, new FatRecord());
			dataRegion.add(i, new DiskCluster());
		}
		fileNames = new HashMap<String, Integer>();
	}
	
	public void writeFile(File newFile) {
		char[]data = newFile.getData();
		String fileName = newFile.getName();
		if (getFreeSpace() < data.length) {
			System.out.println("Not enougn space on disk");
		} else {
			int fileStart = firstFreeCluster();
			fileNames.put(fileName, fileStart);
			if (data.length > 0) {
				write(data, fileStart);
			} else {
				System.out.println("File data is empty");
			}
		}
	}
	
	private void write(char[] data, int start) {
		fat.get(start).setBusy(true);
		if (data.length > CLUSTER_SIZE.getValue()) {
			char[] next = Arrays.copyOfRange(data, CLUSTER_SIZE.getValue(), data.length);
			data = Arrays.copyOfRange(data, 0, CLUSTER_SIZE.getValue());
			int nextFree = firstFreeCluster();
			fat.get(start).setNext(nextFree);
			write(next, nextFree);
		} else {
			fat.get(start).setNext(EOC.getValue());
		}
		System.out.println("Writing block " + String.valueOf(data) + "...");
		dataRegion.get(start).setData(data);
		System.out.println("Complete!");
	}
	
	private int firstFreeCluster() {
		for (int i = 0; i < fat.size(); i++) {
			if (!fat.get(i).isBusy()) {
				return i;
			}
		}
		return -1;
	}
	
	public File getFile(String fileName) {
		char[] data = new char[0];
		Integer cluster = fileNames.get(fileName);
		if (cluster == null) {
			System.out.println("File not found");
		}
		while(fat.get(cluster).getNext() != EOC.getValue()) {
			char[] block = dataRegion.get(cluster).getData();
			char[] temp = new char[data.length + block.length];
			for (int i = 0; i < data.length; i++) {
				temp[i] = data[i];
			}
			for (int i = data.length, j = 0; i < temp.length; i++, j++) {
				temp[i] = block[j];
			}
			data = temp;
			cluster = fat.get(cluster).getNext();
		}
		char[] block = dataRegion.get(cluster).getData();
		char[] temp = new char[data.length + block.length];
		for (int i = 0; i < data.length; i++) {
			temp[i] = data[i];
		}
		for (int i = data.length, j = 0; i < temp.length; i++, j++) {
			temp[i] = block[j];
		}
		data = temp;
		return new File(fileName, data);
	}
	
	public void deleteFile(String fileName) {
		Integer fileStart = fileNames.get(fileName);
		if (fileStart == null) {
			System.out.println("File not found");
		}
		while(fat.get(fileStart).getNext() != EOC.getValue()) {
			fat.get(fileStart).setBusy(false);
			fileStart = fat.get(fileStart).getNext();
		}
		fat.get(fileStart).setBusy(false);
		fileNames.remove(fileName);
		System.out.println("File removed");
	}
}
