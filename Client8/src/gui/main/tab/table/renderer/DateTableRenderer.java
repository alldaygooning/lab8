package gui.main.tab.table.renderer;

import java.awt.Color;
import java.time.*;
import java.util.Date;
import java.awt.*;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;

import application.Application;
import gui.*;
import module.LocalizationModule;

public class DateTableRenderer implements TableCellRenderer{

	CustomLabel label;
	LocalizationModule lm = Application.getLocalizationModule();
	
	public DateTableRenderer() {
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
		LocalDate localDate = (LocalDate)value;
		Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
		label.setText(lm.getFormattedDate(date));
		label.setHorizontalAlignment(JLabel.CENTER);
		return label;
	}
}