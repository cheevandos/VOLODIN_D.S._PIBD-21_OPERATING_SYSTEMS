package com.memory;

import static com.info.SystemInfo.*;
import com.info.Actions;
import java.util.Random;

public class RAM {
	
	private TableRecord[] table;
	private boolean[] frames;
	
	public RAM() {
		table = new TableRecord[VIRTUAL_PAGES.getValue()];
		frames = new boolean[MEMORY_FRAMES.getValue()];
	}
	
	public TableRecord getTableRecord(int virtualPage) {
		if (virtualPage >= 0 && virtualPage < VIRTUAL_PAGES.getValue()) {
			return table[virtualPage];
		} else {
			throw new IllegalArgumentException("Virtual page out of range");
		}
	}
	
	public void getAccess(int address, Actions action) {
		if (address >=0 && address < PHYSICAL_MEMORY.getValue()) {
			if (action == Actions.READ) {
				System.out.print("READ FROM " + address + ": ");
				System.out.println(new Random().nextInt(128));
				System.out.println();
			}
			if (action == Actions.WRITE) {
				System.out.print("WRITE TO " + address + ": ");
				System.out.println(new Random().nextInt(128));
				System.out.println();
			}
		}
	}
	
	public void loadData(TableRecord[] table) {
		this.table = table;
	}
	
	public TableRecord[] getPageTable() {
		return table;
	}
	
	public void removePage(int index) {
		if (index >= 0 && index < MEMORY_FRAMES.getValue()) {
			System.out.println("Page " + index + " removed from RAM");
			frames[index] = false;
		} else {
			throw new IllegalArgumentException("Index of of range");
		}
	}
	
	public void loadPage(int index) {
		if (index >= 0 && index < MEMORY_FRAMES.getValue()) {
			System.out.println("Page loaded to frame " + index);
			frames[index] = true;
		} else {
			throw new IllegalArgumentException("Index of of range");
		}
	}
	
	public Integer getFreeFrame() {
		for (int i = 0; i < frames.length; i++) {
			if (!frames[i]) {
				return i;
			}
		}
		return null;
	}
}
