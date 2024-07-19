package gui.main.tab.create;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.ImageIcon;

import attribute.Country;
import attribute.MusicGenre;
import gui.*;
import module.FactoryModule;

public class RandomButton extends CustomButton{
	private static final long serialVersionUID = 1L;

	private static final String pathToIcon = "content" + File.separator + "random.png";
	private static final Dimension preferredSize = new Dimension(60, 50);
	
	CreateTab createTab;
	RandomButton randomButton;
	
	public RandomButton() {
		super();
		this.randomButton = this;
		
		ImageIcon lanugageIcon = new ImageIcon(pathToIcon);
		this.setIcon(new ImageIcon(lanugageIcon.getImage().getScaledInstance(50, 40, Image.SCALE_SMOOTH)));
		
		this.setPreferredSize(preferredSize);
		this.setMinimumSize(preferredSize);
		
		setActionListener();
	}
	
	public void setCreateTab(CreateTab createTab) {
		this.createTab = createTab;
	}
	
	private void setActionListener() {
		this.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				java.util.List<String> args = FactoryModule.generateRandomBand();
				createTab.getNameField().setText(args.get(0));
				createTab.getxField().setText(args.get(1));
				createTab.getyField().setText(args.get(2));
				createTab.getParticipantsField().setText(args.get(3));
				createTab.getMusicGenreComboBox().setSelectedItem(MusicGenre.valueOfNullable(args.get(4)));
				createTab.getFrontManNameField().setText(args.get(5));
				createTab.getHeightField().setText(args.get(6));
				createTab.getWeightField().setText(args.get(7));
				createTab.getColorGenreComboBox().setSelectedItem(attribute.Color.valueOf(args.get(8)));
				createTab.getCountryGenreComboBox().setSelectedItem(Country.valueOfNullable(args.get(9)));
				
				createTab.getxField().clearError();
				createTab.getNameField().clearError();
				createTab.getyField().clearError();
				createTab.getParticipantsField().clearError();
				createTab.getFrontManNameField().clearError();
				createTab.getHeightField().clearError();
				createTab.getWeightField().clearError();
				createTab.getColorGenreComboBox().clearError();
			}
		});
	}
}
