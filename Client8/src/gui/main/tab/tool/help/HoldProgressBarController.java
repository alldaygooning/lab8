package gui.main.tab.tool.help;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

public class HoldProgressBarController {
	
	HoldProgressBar holdProgressBar;
	Timer timer;
	
	private final int updateDelay = 10;
	int increment;
	
	public HoldProgressBarController() {
		
	}
	
	public void setHoldProgressBar(HoldProgressBar holdProgressBar) {
		this.holdProgressBar = holdProgressBar;
	}
	
	public void startProgressBar(int delay) {
		holdProgressBar.setVisible(true);
		
		holdProgressBar.setMaximum(delay - delay/10);
		
		timer = new Timer(updateDelay, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int current = holdProgressBar.getValue();
				holdProgressBar.setValue(current + updateDelay);
				if(holdProgressBar.getValue() == holdProgressBar.getMaximum()) {
					timer.stop();
				}
			}
		});
		
		timer.setRepeats(true);
		timer.start();
	}
	
	public void stopProgressBar() {
		timer.stop();
		
		holdProgressBar.setValue(0);
		holdProgressBar.setVisible(false);
	}
}
