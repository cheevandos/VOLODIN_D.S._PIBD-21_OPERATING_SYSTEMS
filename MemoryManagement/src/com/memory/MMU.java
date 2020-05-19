package com.memory;

import static com.info.SystemInfo.*;
import com.info.Actions;

public class MMU {
	
	private RAM ram;
	private Integer missingPage;
	
	public MMU() {
		missingPage = null;
	}
	
	public void linkToRAM(RAM ram) {
		this.ram = ram;
	}
	
	public Integer getPageFaultInfo() {
		return missingPage;
	}
	
	public void resetRefBits() {
		TableRecord[] table = ram.getPageTable();
		for (TableRecord record : table) {
			if (record.isPresented()) {
				record.setRefBit(false);
			}
		}
	}
	
	public Integer getPhysicalAddress(int virtualAddress, Actions action) {
		int virtualPage = getVirtualPageNumber(virtualAddress);
		System.out.println("Virtual page: " + virtualPage);
		int offset = getOffset(virtualAddress);
		System.out.println("Offset: " + offset);
		System.out.println("Checking page table...");
		Integer frame = getPageFrame(virtualPage, action);
		if (frame != null) {
			System.out.println("Page found");
			return convertToAddress(frame, offset);
		} else {
			System.out.println("Page is missing...");
			missingPage = virtualPage;
			return null;
		}
	}
	
	private int getVirtualPageNumber(int virtualAddress) {
		if (virtualAddress >= 0 && virtualAddress < VIRTUAL_MEMORY.getValue()) {
			return virtualAddress / PAGE_SIZE.getValue();
		} else {
			throw new IllegalArgumentException("Address out of range");
		}
	}
	
	private int getOffset(int virtualAddress) {
		if (virtualAddress >= 0 && virtualAddress < VIRTUAL_MEMORY.getValue()) {
			return virtualAddress % PAGE_SIZE.getValue();
		} else {
			throw new IllegalArgumentException("Address out of range");
		}
	}
	
	private int convertToAddress(int pageFrame, int offset) {
		if (pageFrame >= 0 && pageFrame < MEMORY_FRAMES.getValue()) {
			int address = pageFrame * PAGE_SIZE.getValue() + offset;
			return address;
		} else {
			throw new IllegalArgumentException("Frame out of range");
		}
	}
	
	private Integer getPageFrame(int virtualPage, Actions action) {
		TableRecord record = ram.getTableRecord(virtualPage);
		if (record.isPresented()) {
			record.setRefBit(true);
			if (action == Actions.WRITE) {
				record.setModBit(true);
			}
			return new Integer(record.getFrame());
		} else {
			return null;
		}
	}
}
