package gui;

import java.awt.*;
import java.awt.image.BufferedImage;

import javax.swing.*;

import application.Application;

public class CustomButton extends JButton{
	private static final long serialVersionUID = 1L;
	
	String textKey;
	String toolTipKey;
	
	public CustomButton() {
		this.setForeground(GraphicUtilities.textColor);
		this.setFont(new Font(Font.DIALOG, Font.BOLD, 13));
	}
	
	public CustomButton(String textKey) {
		this();
		this.textKey = textKey;
		this.setText();
	}
	
	public CustomButton(String textKey, Font font) {
		this(textKey);
		this.setFont(font);
	}
	
	public void setScaledIcon(String path, int scaleWidth, int scaleHeight, int scaleMode) {
		ImageIcon icon = getImageIconScaled(path, scaleWidth, scaleHeight, scaleMode);
		this.setIcon(icon);
	}
	
	public void setScaledIcon(String path, int scaleWidth, int scaleHeight, int scaleMode, boolean enabled) {
		if(!enabled) {
			ImageIcon icon = getImageIconScaled(path, scaleWidth, scaleHeight, scaleMode);
			BufferedImage bi = new BufferedImage(icon.getImage().getWidth(null), icon.getImage().getHeight(null), BufferedImage.TYPE_INT_ARGB);
			Graphics2D g2d = (Graphics2D) bi.getGraphics();
			icon.paintIcon(null, g2d, 0, 0);
			g2d.dispose();
			
			for(int y = 0; y < bi.getHeight(); y++)
			    for(int x = 0; x < bi.getWidth(); x++){
			        int rgb = bi.getRGB(x, y);
			        switch(rgb){
			        case 0: bi.setRGB(x, y, (new Color(200, 200, 200).getRGB()));
			        	break;
			        }
			    }
			icon = new ImageIcon(bi);
			this.setIcon(icon);
			return;
		}
		this.setScaledIcon(path, scaleWidth, scaleHeight, scaleMode);
	}
	
	private ImageIcon getImageIconScaled(String path, int scaleWidth, int scaleHeight, int scaleMode) {
		ImageIcon icon = new ImageIcon(path);
		return new ImageIcon(icon.getImage().getScaledInstance(scaleWidth, scaleHeight, scaleMode));
	}

	protected void translate() {
		if(textKey != null) {
			this.setText();
		}
		if(toolTipKey != null) {
			this.setToolTip();
		}
		this.revalidate();
	}
	
	public void setToolTipKey(String toolTipKey) {
		this.toolTipKey = toolTipKey;
		this.setToolTip();
	}
	
	private void setText() {
		this.setText(Application.getLocalizationModule().getString(textKey));
	}
	
	private void setToolTip() {
		this.setToolTipText(Application.getLocalizationModule().getString(toolTipKey));
	}
}

