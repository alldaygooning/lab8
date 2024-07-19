package gui.main.tab.table.editor;

import java.awt.Component;
import java.awt.event.*;

import javax.swing.*;

import gui.CustomField;

public class LongTableEditor extends DefaultCellEditor{
	private static final long serialVersionUID = 1L;
	
	long min;
	long max;
	String text;
	
	CustomField textField;
	
	public LongTableEditor(long min, long max) {
		super(new CustomField(50));
		this.textField = (CustomField)getComponent();
		this.min = min;
		this.max = max;
	}
	
	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		textField.setText(String.valueOf((Long)value));
		textField.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				fireEditingStopped();
			}
		});
		
		return textField;
	}
	
	@Override
	public Object getCellEditorValue() {
		String text = textField.getText();
		try {
			Long number = Long.valueOf(text);
			if (number > max) {
				textField.setText(String.valueOf(max));
			}
			else if(number < min) {
				textField.setText(String.valueOf(min));
			}
			else {
				textField.setText(String.valueOf(number));
			}
		}catch(NumberFormatException e) {
			textField.setText(String.valueOf(min));
		}
		return textField.getText();
	}
}
