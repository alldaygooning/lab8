package gui.main.tab.table.renderer;

import java.awt.Color;
import java.awt.Component;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;

import application.Application;
import gui.*;
import module.LocalizationModule;

public class IntegerTableRenderer implements TableCellRenderer{

	CustomLabel label;
	LocalizationModule lm = Application.getLocalizationModule();
	
	public IntegerTableRenderer() {
		label = new CustomLabel();
		label.setOpaque(true);
	}
	
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		if(isSelected) {
			label.setBackground(GraphicUtilities.secondaryColor);
			label.setTextColor(Color.WHITE);
		}
		else {
			label.setBackground(GraphicUtilities.transparent);
			label.setTextColor(Color.BLACK);;
		}
		label.setText(lm.getFormattedNumber((Integer)value));
		label.setHorizontalAlignment(JLabel.CENTER);
		return label;
	}
}
