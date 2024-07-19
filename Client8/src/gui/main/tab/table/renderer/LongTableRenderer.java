package gui.main.tab.table.renderer;

import java.awt.*;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;

import application.Application;
import gui.CustomLabel;
import gui.GraphicUtilities;
import module.LocalizationModule;

public class LongTableRenderer implements TableCellRenderer{

	CustomLabel label;
	LocalizationModule lm = Application.getLocalizationModule();
	
	public LongTableRenderer() {
		label = new CustomLabel();
		label.setOpaque(true);
	}
	
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		if(value != null) {
			Long number = (Long)value;
			label.setText(lm.getFormattedLong(number));
		}
		if(isSelected) {
			label.setBackground(GraphicUtilities.secondaryColor);
			label.setTextColor(Color.WHITE);
		}
		else {
			label.setBackground(GraphicUtilities.transparent);
			label.setTextColor(Color.BLACK);;
		}
		label.setHorizontalAlignment(JLabel.CENTER);
		return label;
	}
}