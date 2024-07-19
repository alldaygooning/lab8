package gui.main.tab.table.selection;

import javax.swing.*;

public class MusicBandSelectionModel extends DefaultListSelectionModel{
	private static final long serialVersionUID = 1L;
	
	private RowSelector rowSelector;
	
	public MusicBandSelectionModel() {
		this.setSelectionMode(SINGLE_SELECTION);
	}
	
	public void addRowSelector(RowSelector rowSelector) {
		this.rowSelector = rowSelector;
	}
	
	@Override
	public void addSelectionInterval(int index0, int index1) {
		int start = Math.min(index0, index1);
		int finish = Math.max(index0, index1);
		for(int i = start; i <= finish; i++) {
			if(rowSelector.rowSelectable(i)) {
				super.addSelectionInterval(i, i);
			}
		}
	}
	
	@Override
	public void setSelectionInterval(int index0, int index1) {
		super.clearSelection();
		if(rowSelector.rowSelectable(index0)) {
			super.setSelectionInterval(index0, index1);
		}
		
	}
	
	@Override
	public void insertIndexInterval(int index, int length, boolean before) {
		super.insertIndexInterval(index, length, before);
	}
	
	@Override
	public void removeSelectionInterval(int index0, int index1) {
		super.removeSelectionInterval(index0, index1);
	}
}
