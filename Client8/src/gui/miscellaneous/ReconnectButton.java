package gui.miscellaneous;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import javax.swing.SwingUtilities;

import application.*;
import attribute.NotificationType;
import exception.ExecutionCancelled;
import gui.*;
import gui.event.NotificationDisplayRequestEvent;

public class ReconnectButton extends CustomButton implements TranslatedContainer{
	private static final long serialVersionUID = 1L;
	
	private static final Dimension preferredSize = new Dimension(40, 40);
	
	ReconnectButton reconnectButton;
	
	public ReconnectButton() {
		super();
		this.reconnectButton = this;
		
		this.setText("⟲");
		this.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 35));
		this.setToolTipKey("reconnect_button_tooltip");
		
		this.setPreferredSize(preferredSize);
		
		this.setActionListener();
	}
	
	private void setActionListener() {
		this.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				CustomFrame frame = (CustomFrame) SwingUtilities.getWindowAncestor(reconnectButton);
				NotificationDisplayRequestEvent notificationDisplayRequestEvent;
				
				List<String> args = new ArrayList<>();
				args.add("reconnect");
				try{
					Invoker.silentExecute(args);
					notificationDisplayRequestEvent = new NotificationDisplayRequestEvent(reconnectButton, NotificationType.SUCCESS, 200);
				}catch(ExecutionCancelled exception) {
					if (exception.reason_id == 10) {
						notificationDisplayRequestEvent = new NotificationDisplayRequestEvent(reconnectButton, NotificationType.INFO, exception.reason_id);
					}
					else{
						notificationDisplayRequestEvent = new NotificationDisplayRequestEvent(reconnectButton, NotificationType.ERROR, exception.reason_id);
					}
				}
				frame.dispatchEvent(notificationDisplayRequestEvent);
			}
		});
	}

	@Override
	public void translate() {
		super.translate();
	}
}
