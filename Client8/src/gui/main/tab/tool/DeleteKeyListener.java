package gui.main.tab.tool;

import java.awt.event.*;
import java.util.*;

import javax.swing.SwingUtilities;
import javax.swing.Timer;

import application.Invoker;
import attribute.NotificationType;
import exception.*;
import gui.CustomFrame;
import gui.event.*;
import gui.main.tab.table.MusicBandTable;
import gui.main.tab.table.MusicBandTableModel;
import gui.main.tab.tool.help.HoldProgressBarController;

public class DeleteKeyListener extends KeyAdapter{
	
	MusicBandTable musicBandTable;
	MusicBandTableModel musicBandTableModel;
	
	Timer holdTimer;
	HoldProgressBarController barController;
	private final int delay = 2000;
	
	boolean isPressed = false;
	
	public DeleteKeyListener(MusicBandTable musicBandTable) {
		this.musicBandTable = musicBandTable;
		this.musicBandTableModel = (MusicBandTableModel) musicBandTable.getModel();
		
		barController = new HoldProgressBarController();
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() != KeyEvent.VK_DELETE || isPressed) {
			return;
		}
		isPressed = true;
		
		holdTimer = new Timer(delay, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				clear();
			}
		});
		holdTimer.setRepeats(false);
		holdTimer.start();
		barController.startProgressBar(delay);
		super.keyPressed(e);
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() != KeyEvent.VK_DELETE) {
			return;
		}
		isPressed = false;
		barController.stopProgressBar();
		
		if(holdTimer.isRunning()) {
			holdTimer.stop();
			removeSelectedRow();
		}
		super.keyReleased(e);
	}
	
	private void clear() {
		String command = "clear";
		List<String> sendArgs = new ArrayList<String>();
		sendArgs.add(command);
		boolean executedSuccessfully = executeDeletion(sendArgs);
		if(executedSuccessfully) {
			musicBandTableModel.fireTableDataChanged();
		}
	}
	
	private void removeSelectedRow() {
		int selectedRow = musicBandTable.getSelectedRow();
		if(selectedRow == -1) {
			return;
		}
		
		String selectedId = String.valueOf(musicBandTable.getRowId(selectedRow));
		String command = "remove";
		
		List<String> sendArgs = new ArrayList<String>();
		sendArgs.add(command);
		sendArgs.add(selectedId);
		boolean executedSuccessfully = executeDeletion(sendArgs);
		if (executedSuccessfully) {
			musicBandTableModel.fireTableRowsDeleted(selectedRow, selectedRow);
		}
	}
	
	private boolean executeDeletion(List<String> args) {
		try {
			Invoker.silentExecute(args);
			return true;
		}catch(ConnectionException exception) {
			CustomFrame customFrame = (CustomFrame) SwingUtilities.getWindowAncestor(musicBandTable);
			customFrame.dispatchEvent(new NotificationDisplayRequestEvent(customFrame, NotificationType.ERROR, 4));
		}catch(ExecutionCancelled exception) {
			CustomFrame customFrame = (CustomFrame) SwingUtilities.getWindowAncestor(musicBandTable);
			customFrame.dispatchEvent(new ErrorEvent(customFrame, exception.reason_id));
		}
		return false;
	}
	
	public HoldProgressBarController getBarController() {
		return this.barController;
	}
}

