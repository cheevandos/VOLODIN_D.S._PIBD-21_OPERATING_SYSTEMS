package com.memory;

import java.util.ArrayList;
import java.util.Random;
import static com.info.SystemInfo.*;
import com.info.Actions;

public class Program {
	
	private RAM ram;
	private ROM rom;
	private MMU mmu;
	private int timer;
	
	public Program() {
		timer = 5;
		ram = new RAM();
		rom = new ROM();
		mmu = new MMU();
		mmu.linkToRAM(ram);
	}
	
	public void load() {
		ram.loadData(rom.getData());
	}
	
	public void perform() {
		int count = 50;
		Random rnd = new Random();
		int i = 0;
		while (i < count) {
			int vAddress = rnd.nextInt(VIRTUAL_MEMORY.getValue());
			System.out.print("VIRTUAL ADDRESS: " + vAddress + " ");
			Actions action;
			boolean read = rnd.nextBoolean();
			if (read) {
				action = Actions.READ;
			} else {
				action = Actions.WRITE;
			}
			System.out.println("ACTION: " + action.toString());
			Integer pAddress = mmu.getPhysicalAddress(vAddress, action);
			if (pAddress == null) {
				handlePageFault(mmu.getPageFaultInfo());
				System.out.println("Retrying the request...");
				pAddress = mmu.getPhysicalAddress(vAddress, action);
			}
			System.out.println("PHYSICAL ADDRESS: " + pAddress.intValue());
			ram.getAccess(pAddress, action);
			if (i % timer == 0) {
				mmu.resetRefBits();
			}
			i++;
		}
	}
	
	public void handlePageFault(int virtualPage) {
		TableRecord[] table = ram.getPageTable();
		Integer frame = ram.getFreeFrame();
		if (frame != null) {
			rom.getPage(virtualPage);
			ram.loadPage(frame);
			table[virtualPage].setFrame(frame);
			return;
		}
		ArrayList<Integer> class0 = new ArrayList<Integer>();
		ArrayList<Integer> class1 = new ArrayList<Integer>();
		ArrayList<Integer> class2 = new ArrayList<Integer>();
		ArrayList<Integer> class3 = new ArrayList<Integer>();
		for (TableRecord rec : table) {
			if (rec.isPresented()) {
				if (!rec.getRefBit() && !rec.getModBit()) {
					class0.add(rec.getFrame());
				}
				if (!rec.getRefBit() && rec.getModBit()) {
					class1.add(rec.getFrame());
				}
				if (rec.getRefBit() && !rec.getModBit()) {
					class2.add(rec.getFrame());
				}
				if (rec.getRefBit() && rec.getModBit()) {
					class3.add(rec.getFrame());
				}
			}
		}
		if (!class0.isEmpty()) {
			replacePage(class0, table, virtualPage);
			return;
		}
		if (!class1.isEmpty()) {
			replacePage(class1, table, virtualPage);
			return;
		}
		if (!class2.isEmpty()) {
			replacePage(class2, table, virtualPage);
			return;
		}
		if (!class3.isEmpty()) {
			replacePage(class3, table, virtualPage);
		}	
	}
	
	public void replacePage(ArrayList<Integer> pageClass,
			TableRecord[] table, int virtualPage) {
		Random rnd = new Random();
		int index = rnd.nextInt(pageClass.size());
		int page = pageClass.get(index).intValue();
		ram.removePage(page);
		rom.getPage(virtualPage);
		ram.loadPage(page);
		for (TableRecord rec : table) {
			if (rec.getFrame() == page) {
				rec.reset();
			}
		}
		table[virtualPage].setFrame(page);
	}
	
	public void resetRefBits() {
		TableRecord[] table = ram.getPageTable();
		for (TableRecord record : table) {
			record.setRefBit(false);
		}
	}
	
	public void displaySysInfo() {
		System.out.println("Algorithm: NRU");
		System.out.println("Virtual memory: " + VIRTUAL_MEMORY.getValue());
		System.out.println("Physical memory: " + PHYSICAL_MEMORY.getValue());
		System.out.println("Page size: " + PAGE_SIZE.getValue());
		System.out.println("Virtual pages: " + VIRTUAL_PAGES.getValue());
		System.out.println("Memory frames: " + MEMORY_FRAMES.getValue());
		System.out.println();
	}
	
	public static void main(String[] args) {
		Program program = new Program();
		program.load();
		program.displaySysInfo();
		program.perform();
	}
}
