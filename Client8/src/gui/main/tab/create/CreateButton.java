package gui.main.tab.create;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import application.Application;
import application.Invoker;
import attribute.Country;
import attribute.MusicGenre;
import attribute.NotificationType;
import exception.ConnectionException;
import exception.ExecutionCancelled;
import gui.CustomButton;
import gui.TranslatedContainer;
import gui.event.ErrorEvent;
import gui.event.NotificationDisplayRequestEvent;
import gui.main.MainFrame;

import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;

public class CreateButton extends CustomButton implements TranslatedContainer{
	private static final long serialVersionUID = 1L;

	CreateButton createButton;
	CreateTab createTab;
	
	public CreateButton() {
		super("create_button", new Font(Font.DIALOG, Font.BOLD, 20));
		this.createButton = this;
		
		setActionListener();
	}
	
	private void setActionListener() {
		this.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String stringName = createTab.getNameField().getText();
				int errors = 0;
				if(stringName.isEmpty()) {
					createTab.getNameField().error();
					errors++;
				}
				String stringX = createTab.getxField().getText();
				if(stringX.isEmpty()) {
					createTab.getxField().error();
					errors++;
				}
				String stringY = createTab.getyField().getText();
				if(stringY.isEmpty()) {
					createTab.getyField().error();
					errors++;
				}
				String stringParticipants = createTab.getParticipantsField().getText();
				if(stringParticipants.isEmpty()) {
					createTab.getParticipantsField().error();
					errors++;
				}
				String stringFrontmanName = createTab.getFrontManNameField().getText();
				if(stringFrontmanName.isEmpty()) {
					createTab.getFrontManNameField().error();
					errors++;
				}
				String stringWeight = createTab.getWeightField().getText();
				if(stringWeight.isEmpty()) {
					createTab.getWeightField().error();
					errors++;
				}
				String stringHeight = createTab.getHeightField().getText();
				if(stringHeight.isEmpty()) {
					createTab.getHeightField().error();
					errors++;
				}
				
				MusicGenre genre = (MusicGenre)createTab.getMusicGenreComboBox().getSelectedItem();
				Country country = (Country)createTab.getCountryGenreComboBox().getSelectedItem();
				attribute.Color color = (attribute.Color)createTab.getColorGenreComboBox().getSelectedItem();
				if(color == null) {
					createTab.getColorGenreComboBox().error();
					errors++;
				}
				
				if(errors > 0) {
					return;
				}
				
				List<String> args = new ArrayList<String>();
				args.add("add");
				args.add(stringName);
				args.add(stringX);
				args.add(stringY);
				args.add(stringParticipants);
				args.add(genre.toName());
				args.add(stringFrontmanName);
				args.add(stringHeight);
				args.add(stringWeight);
				args.add(color.toName());
				args.add(country.toName());
				
				MainFrame mainFrame = (MainFrame) SwingUtilities.getWindowAncestor(createButton);
				
				try {
					Invoker.silentExecute(args);
				}catch(ConnectionException exception) {
					mainFrame.dispatchEvent(new NotificationDisplayRequestEvent(mainFrame, NotificationType.ERROR, 4));
				}catch(ExecutionCancelled exception) {
					mainFrame.dispatchEvent(new ErrorEvent(mainFrame, exception.reason_id));
				}
			}
		});
	}
	
	public void setCreateTab(CreateTab createTab) {
		this.createTab = createTab;
	}
	
	@Override
	public void translate() {
		this.setText(Application.getLocalizationModule().getString("create_button"));
		super.translate();
	}
}
