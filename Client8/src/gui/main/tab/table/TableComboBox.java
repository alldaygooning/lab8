package gui.main.tab.table;

import java.awt.Color;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;

import com.formdev.flatlaf.FlatClientProperties;

import gui.GraphicUtilities;

public class TableComboBox<E> extends JComboBox<E>{
	private static final long serialVersionUID = 1L;

	TableComboBox<E> comboBox;
	
	public TableComboBox() {
		super();
		this.comboBox = this;
		this.putClientProperty(FlatClientProperties.STYLE, "borderColor: #00000000");
		this.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				comboBox.setBackground(GraphicUtilities.secondaryColor);
				comboBox.setForeground(Color.WHITE);
				super.focusGained(e);
			}
			
			@Override
			public void focusLost(FocusEvent e) {
				comboBox.setBackground(Color.WHITE);
				comboBox.setForeground(GraphicUtilities.textColor);
				super.focusLost(e);
			}
		});
		((JLabel)this.getRenderer()).setHorizontalAlignment(JLabel.RIGHT);
		addErrorClearConditionListners();
	}

	public TableComboBox(Object[] values) {
		this();
		for(Object value: values) {
			@SuppressWarnings("unchecked")
			E castedValue = (E)value;
			this.addItem(castedValue);
		}
	}
	
	public void error() {
		this.putClientProperty("JComponent.outline", "error");
		this.setBackground(new Color(250, 200, 190));
	}
	public void clearError() {
		this.putClientProperty("JComponent.outline", "");
		this.setBackground(new Color(255, 255, 255));
	}
	private void addErrorClearConditionListners() {
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				comboBox.clearError();
			}
		});
		this.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				comboBox.clearError();
			}
		});
	}
}