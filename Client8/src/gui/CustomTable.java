package gui;

import java.time.LocalDate;

import javax.swing.*;
import javax.swing.table.*;

import gui.main.tab.table.*;
import gui.main.tab.table.editor.*;
import gui.main.tab.table.renderer.*;
import gui.main.tab.table.selection.RowSelector;

public abstract class CustomTable extends JTable{
	private static final long serialVersionUID = 1L;
	
	protected RowSelector rowSelector;
	protected TableColumnAdjuster tableColumnAdjuster;

	public CustomTable() {
		setTableRenderers();
		setTableEditors();
	}

	protected abstract boolean rowSelectable(int rowIndex);
	
	private void setTableRenderers() {
		DefaultTableCellRenderer defaultRenderer = new DefaultTableCellRenderer();
		defaultRenderer.setHorizontalAlignment(JLabel.CENTER);
		this.setDefaultRenderer(String.class, defaultRenderer);
		
		this.setDefaultRenderer(Integer.class, new IntegerTableRenderer());
		this.setDefaultRenderer(Long.class, new LongTableRenderer());
		this.setDefaultRenderer(LocalDate.class, new DateTableRenderer());
		this.setDefaultRenderer(Double.class, new DoubleTableRenderer());
	}
	
	private void setTableEditors() {
		this.setDefaultEditor(Integer.class, new IntegerTableEditor(1, Integer.MAX_VALUE));
		this.setDefaultEditor(Long.class, new LongTableEditor(1, Long.MAX_VALUE));
		this.setDefaultEditor(String.class, new StringTableEditor());
		this.setDefaultEditor(Double.class, new DoubleTableEditor(Double.MIN_VALUE, Double.MAX_VALUE));
	}
	
	public abstract void updateTableData();
}
