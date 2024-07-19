package gui.miscellaneous.notification;

import java.awt.*;
import java.awt.event.*;

import javax.swing.SwingConstants;

import gui.CustomButton;

public class CloseNotificationButton extends CustomButton{
	private static final long serialVersionUID = 1L;

	CloseNotificationButton closeNotificationButton;
	
	public CloseNotificationButton() {
		this.closeNotificationButton = this;
		
		this.makeTransparent();
		this.setForeground(Color.BLACK);
		
		this.setBounds(220, 15, 20, 20);
		
		this.setText("x");
		this.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
		this.setMargin(new Insets(2, 2, 2, 2));
		this.setHorizontalAlignment(SwingConstants.CENTER);
		this.setVerticalAlignment(SwingConstants.CENTER);
		
		setActionListner();
	}
	
	private void setActionListner() {
		this.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				NotificationPanel notification = (NotificationPanel)closeNotificationButton.getParent();
				notification.remove();
			}
		});
	}
	
	private void makeTransparent() {
		this.setOpaque(false);
		this.setContentAreaFilled(false);
		this.setBorderPainted(false);
	}
}
