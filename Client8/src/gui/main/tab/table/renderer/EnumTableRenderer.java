package gui.main.tab.table.renderer;

import java.awt.*;

import javax.swing.*;
import javax.swing.table.*;

import application.User;
import gui.*;
import gui.main.tab.table.TableComboBox;

public class EnumTableRenderer<E extends Enum<E>> implements TableCellRenderer{

	TableComboBox<E> comboBox;
	CustomLabel label;
	
	public EnumTableRenderer(Class<E> enumType) {
		comboBox = new TableComboBox<E>(enumType.getEnumConstants());
		label = new CustomLabel();
	}
	
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		String owner = (String)table.getValueAt(row, 12);
		if(owner.equals(User.getLogin())) {
			if(isSelected) {
				comboBox.setBackground(GraphicUtilities.secondaryColor);
				comboBox.setForeground(Color.WHITE);
			}
			else {
				comboBox.setBackground(GraphicUtilities.transparent);
				comboBox.setForeground(Color.BLACK);
			}
			comboBox.setSelectedItem(value);
			
			return comboBox;
		}
		label.setText(value.toString());
		label.setHorizontalAlignment(JLabel.CENTER);
		return label;
	}
}
