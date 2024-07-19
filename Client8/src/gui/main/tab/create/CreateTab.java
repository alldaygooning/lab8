package gui.main.tab.create;

import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import com.formdev.flatlaf.FlatClientProperties;

import gui.*;
import gui.main.tab.table.TableComboBox;

public class CreateTab extends CustomTab implements TranslatedContainer{
	private static final long serialVersionUID = 1L;
	
	private static final String titleKey = "create_tab";
	
	CustomLabel nameLabel = new CustomLabel("enter_name");
	CustomLabel xLabel = new CustomLabel("enter_x");
	CustomLabel yLabel = new CustomLabel("enter_y");
	CustomLabel genreLabel = new CustomLabel("enter_genre");
	CustomLabel participantsLabel = new CustomLabel("enter_participants");
	
	CustomLabel frontmanNameLabel = new CustomLabel("enter_frontman_name");
	CustomLabel frontmanHeightLabel = new CustomLabel("enter_frontman_height");
	CustomLabel frontmanWeightLabel = new CustomLabel("enter_frontman_weight");
	CustomLabel frontmanCountryLabel = new CustomLabel("enter_frontman_country");
	CustomLabel frontmanColorLabel = new CustomLabel("enter_frontman_color");
	
	CustomField nameField = new CustomField(25);
	CustomField xField = new CustomField(25);
	CustomField yField = new CustomField(25);
	CustomField participantsField = new CustomField(25);
	TableComboBox<attribute.MusicGenre> musicGenreComboBox = new TableComboBox<attribute.MusicGenre>(attribute.MusicGenre.values());
	
	CustomField frontManNameField = new CustomField(25);
	CustomField heightField = new CustomField(25);
	CustomField weightField = new CustomField(25);
	TableComboBox<attribute.Color> colorGenreComboBox = new TableComboBox<attribute.Color>(attribute.Color.values());
	TableComboBox<attribute.Country> countryGenreComboBox = new TableComboBox<attribute.Country>(attribute.Country.values());
	
	
	CreateButton createButton = new CreateButton();
	RandomButton randomButton = new RandomButton();
	
	public CreateTab() {
		super(titleKey);
		
		this.setLayout(new GridBagLayout());
		
		setLabels();
		setFieldsAndBoxes();
		setMainButtons();
	}
	
	private void setLabels() {
		GraphicUtilities.addToGBL(this, nameLabel, 0, 0, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_END, 1, 1);
		GraphicUtilities.addToGBL(this, xLabel, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_END, 1, 1);
		GraphicUtilities.addToGBL(this, yLabel, 0, 2, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_END, 1, 1);
		GraphicUtilities.addToGBL(this, participantsLabel, 0, 3, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_END, 1, 1);
		GraphicUtilities.addToGBL(this, genreLabel, 0, 4, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_END, 1, 1);
		
		GraphicUtilities.addToGBL(this, frontmanNameLabel, 2, 0, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_END, 1, 1);
		GraphicUtilities.addToGBL(this, frontmanHeightLabel, 2, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_END, 1, 1);
		GraphicUtilities.addToGBL(this, frontmanWeightLabel, 2, 2, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_END, 1, 1);
		GraphicUtilities.addToGBL(this, frontmanColorLabel, 2, 3, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_END, 1, 1);
		GraphicUtilities.addToGBL(this, frontmanCountryLabel, 2, 4, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_END, 1, 1);
		
		CustomLabel dummy = new CustomLabel();
		GraphicUtilities.addToGBL(this, dummy, 0, 6, 1, GridBagConstraints.BOTH, GridBagConstraints.LINE_END, 1, 20);
	}
	
	private void setFieldsAndBoxes() {
		musicGenreComboBox.putClientProperty(FlatClientProperties.STYLE, "borderColor: #c0c0c0");
		colorGenreComboBox.putClientProperty(FlatClientProperties.STYLE, "borderColor: #c0c0c0");
		colorGenreComboBox.setSelectedIndex(-1);
		countryGenreComboBox.putClientProperty(FlatClientProperties.STYLE, "borderColor: #c0c0c0");
		
		GraphicUtilities.addToGBL(this, nameField, 1, 0, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.LINE_START, 1, 2);
		GraphicUtilities.addToGBL(this, xField, 1, 1, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.LINE_START, 1, 2);
		GraphicUtilities.addToGBL(this, yField, 1, 2, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.LINE_START, 1, 2);
		GraphicUtilities.addToGBL(this, participantsField, 1, 3, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.LINE_START, 1, 2);
		GraphicUtilities.addToGBL(this, musicGenreComboBox, 1, 4, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 1, 2);
		
		GraphicUtilities.addToGBL(this, frontManNameField, 3, 0, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.LINE_START, 1, 2);
		GraphicUtilities.addToGBL(this, heightField, 3, 1, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.LINE_START, 1, 2);
		GraphicUtilities.addToGBL(this, weightField, 3, 2, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.LINE_START, 1, 2);
		GraphicUtilities.addToGBL(this, colorGenreComboBox, 3, 3, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 1, 2);
		GraphicUtilities.addToGBL(this, countryGenreComboBox, 3, 4, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 1, 2);
		
		xField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				String text = xField.getText();
				if (text.isEmpty()) {
					xField.error();
					return;
				}
				try {
					Double.valueOf(text);
				}catch(NumberFormatException exception) {
					xField.setText(String.valueOf(Double.MIN_VALUE));
				}
				super.focusLost(e);
			}
		});
		
		yField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				String text = yField.getText();
				if (text.isEmpty()) {
					yField.error();
					return;
				}
				try {
					Double number = Double.valueOf(text);
					if(number > 587) {
						yField.setText(String.valueOf(587.0));
					}
				}catch(NumberFormatException exception) {
					yField.setText(String.valueOf(Double.MIN_VALUE));
				}
				super.focusLost(e);
			}
		});
		
		heightField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				String text = heightField.getText();
				if (text.isEmpty()) {
					heightField.error();
					return;
				}
				try {
					Long number = Long.valueOf(text);
					if(number < 1) {
						heightField.setText(String.valueOf(1));
					}
				}catch(NumberFormatException exception) {
					heightField.setText(String.valueOf(1));
				}
				super.focusLost(e);
			}
		});
		
		participantsField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				String text = participantsField.getText();
				if (text.isEmpty()) {
					participantsField.error();
					return;
				}
				try {
					Long number = Long.valueOf(text);
					if(number < 1) {
						participantsField.setText(String.valueOf(1));
					}
				}catch(NumberFormatException exception) {
					participantsField.setText(String.valueOf(1));
				}
				super.focusLost(e);
			}
		});
		
		weightField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				String text = weightField.getText();
				if (text.isEmpty()) {
					weightField.error();
					return;
				}
				try {
					Integer number = Integer.valueOf(text);
					if(number < 1) {
						weightField.setText(String.valueOf(1));
					}
				}catch(NumberFormatException exception) {
					weightField.setText(String.valueOf(1));
				}
				super.focusLost(e);
			}
		});

	}

	private void setMainButtons() {
		randomButton.setCreateTab(this);
		createButton.setCreateTab(this);
		
		GraphicUtilities.addToGBL(this, randomButton, 4, 3, 1, GridBagConstraints.NONE, GridBagConstraints.EAST, 1, 0.5);
		GraphicUtilities.addToGBL(this, createButton, 4, 4, 1, GridBagConstraints.NONE, GridBagConstraints.EAST, 1, 0.5);
	}
	
	@Override
	public void translate() {
		TranslatedContainer.super.translate();
		super.translate();
	}
	
	public TableComboBox<attribute.MusicGenre> getMusicGenreComboBox() {
		return musicGenreComboBox;
	}

	public TableComboBox<attribute.Color> getColorGenreComboBox() {
		return colorGenreComboBox;
	}

	public TableComboBox<attribute.Country> getCountryGenreComboBox() {
		return countryGenreComboBox;
	}

	public CustomField getNameField() {
		return nameField;
	}

	public CustomField getxField() {
		return xField;
	}

	public CustomField getyField() {
		return yField;
	}

	public CustomField getParticipantsField() {
		return participantsField;
	}

	public CustomField getFrontManNameField() {
		return frontManNameField;
	}

	public CustomField getHeightField() {
		return heightField;
	}

	public CustomField getWeightField() {
		return weightField;
	}
}
