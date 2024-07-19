package gui.main.tab.table.editor;

import java.awt.Component;
import java.awt.event.*;

import javax.swing.*;

import gui.CustomField;

public class StringTableEditor extends DefaultCellEditor{
	private static final long serialVersionUID = 1L;
	
	String text;
	
	CustomField textField;
	
	public StringTableEditor() {
		super(new CustomField(50));
		this.textField = (CustomField)getComponent();
	}
	
	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		textField.setText((String)value);
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
		if(text.isEmpty()) {
			textField.setText("empty");
		}
		return textField.getText();
	}
}
