package gui.main.tab.table.editor;

import java.awt.Component;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.table.TableCellEditor;

import gui.main.tab.table.*;

public class EnumTableEditor<E extends Enum<E>> extends AbstractCellEditor implements TableCellEditor{
	private static final long serialVersionUID = 1L;
	
	TableComboBox<E> comboBox;

	public EnumTableEditor(Class<E> enumType) {
		this.comboBox = new TableComboBox<E>(enumType.getEnumConstants());
	}
	
	@Override
	public Object getCellEditorValue() {
		return comboBox.getSelectedItem();
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		comboBox.setSelectedItem(value);
		comboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				fireEditingStopped();
			}
		});
		return comboBox;
	}
}
