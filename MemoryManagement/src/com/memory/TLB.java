package com.memory;

import java.util.ArrayList;
import com.info.Actions;

public class TLB {

	private ArrayList<TLBRecord> bufferTable;
	
	public TLB() {
		bufferTable = new ArrayList<TLBRecord>();
	}
	
	public TLBRecord getRecord(int virtualPage, Actions action) {
		TLBRecord record = null;
		for (TLBRecord rec : bufferTable) {
			if (rec.getVirtualPage() == virtualPage) {
				record = rec;
				if (action == Actions.WRITE) {
					rec.setModBit(true);
				}
			}
		}
		return record;
	}
	
	public ArrayList<TLBRecord> getTable() {
		return bufferTable;
	}
}
