package gui.main.tab.table;

import java.time.LocalDate;
import java.util.*;

import javax.swing.table.*;

import application.*;
import attribute.*;
import exception.*;
import gui.event.*;
import gui.main.*;
import gui.miscellaneous.ErrorListener;
import gui.miscellaneous.notification.NotificationListener;
import item.MusicBand;

public class MusicBandTableModel extends AbstractTableModel {
	private static final long serialVersionUID = 1L;
	
	List<MusicBand> musicBands;
	TableColumnAdjuster tableColumnAdjuster;
	
	NotificationListener notificationListener;
	ErrorListener errorListener;
	DataUpdateListener dataUpdateListener;
	
	int updateNumber = 0;
	
	@Override
	public int getRowCount() {
		if (musicBands != null) {
			return musicBands.size();
		}
		return 0;
	}

	@Override
	public int getColumnCount() {
		return 13;
	}

	@Override
	public Object getValueAt(int row, int column) {
		if (musicBands == null) {
			return null;
		}
		MusicBand band = musicBands.get(row);
		switch(column) {
		case 0:
			return band.getId();
		case 1:
			return band.getName();
		case 2:
			return band.getCoordinates().getX();
		case 3:
			return band.getCoordinates().getY();
		case 4:
			return band.getCreationDate();
		case 5:
			return band.getNumberOfParticipants();
		case 6:
			return band.getGenre();
		case 7:
			return band.getFrontMan().getName();
		case 8:
			return band.getFrontMan().getHeight();
		case 9:
			return band.getFrontMan().getWeight();
		case 10:
			return band.getFrontMan().getHairColor();
		case 11:
			return band.getFrontMan().getNationality();
		case 12:
			return band.getOwner();
		}
		return band;
	}
	
	@Override
	public String getColumnName(int column) {
		switch (column){
	case 0:
		return Application.getLocalizationModule().getString("table_id");
	case 1:
		return Application.getLocalizationModule().getString("table_name");
	case 2:
		return Application.getLocalizationModule().getString("table_x");
	case 3:
		return Application.getLocalizationModule().getString("table_y");
	case 4:
		return Application.getLocalizationModule().getString("table_creation_date");
	case 5:
		return Application.getLocalizationModule().getString("table_participants");
	case 6:
		return Application.getLocalizationModule().getString("table_music_genre");
	case 7:
		return Application.getLocalizationModule().getString("table_frontman_name");
	case 8:
		return Application.getLocalizationModule().getString("table_frontman_height");
	case 9:
		return Application.getLocalizationModule().getString("table_frontman_weight");
	case 10:
		return Application.getLocalizationModule().getString("table_frontman_hair_color");
	case 11:
		return Application.getLocalizationModule().getString("table_frontman_nationality");
	case 12:
		return Application.getLocalizationModule().getString("table_owner");
	default:
		return "";
		}
	}

	@Override
	public Class<?> getColumnClass(int column) {
		switch(column) {
		case 0: return Integer.class;
		case 1: return String.class;
		case 2: return Double.class;
		case 3: return Double.class;
		case 4: return LocalDate.class;
		case 5: return Long.class;
		case 6: return MusicGenre.class;
		case 7: return String.class;
		case 8: return Long.class;
		case 9: return Integer.class;
		case 10: return attribute.Color.class;
		case 11: return attribute.Country.class;
		case 12: return String.class;
		default: return null;
		}
	}
	
	@Override
	public boolean isCellEditable(int row, int col) {
		if (col == 0 || col == 4 || col == 12) {
			return false;
		}
		MusicBand band = musicBands.get(row);
		if(!band.getOwner().equals(User.getLogin())) {
			return false;
		}
        return true;
    }

	@Override
	public void fireTableDataChanged() {
		this.fetchData();
		super.fireTableDataChanged();
		if (updateNumber == 0) {
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		updateNumber++;
		this.adjustColumns();
	}
	
	@Override
	public void setValueAt(Object value, int row, int column) {
		MusicBand band = musicBands.get(row);
		List<String> args = band.getArgs();
		
		switch(column) {
		case 1: args.set(0, (String)value);
			break;
		case 2: args.set(1, (String)value);
			break;
		case 3: args.set(2, (String)value);
			break;
		case 5: args.set(3, (String)value);
			break;
		case 6: args.set(4, ((MusicGenre)value).toName());
			break;
		case 7: args.set(5, ((String)value));
			break;
		case 8: args.set(6, (String)value);
			break;
		case 9: args.set(7, (String)value);
			break;
		case 10: args.set(8, ((attribute.Color)value).toName());
			break;
		case 11: args.set(9, ((Country)value).toName());
			break;
		}
		
		String command = "update";
		String id = String.valueOf(band.getId());
		
		List<String> sendArgs = new ArrayList<String>();
		sendArgs.add(command);
		sendArgs.add(id);
		sendArgs.addAll(args);
		
		try {
			Invoker.silentExecute(sendArgs);
			this.fireTableRowsUpdated(row, row);
		}catch(ConnectionException e) {
			notificationListener.displayNotification(new NotificationDisplayRequestEvent(this, NotificationType.ERROR, 4));
		}catch(ExecutionCancelled e) {
			errorListener.errorOccured(new ErrorEvent(this, e.reason_id));
		}
	}
	
	@SuppressWarnings("unchecked")
	public void fetchData() {
		musicBands = (List<MusicBand>) dataUpdateListener.getData();
	}
	
	public void addDataUpdateListener(DataUpdateListener dataUpdateListener) {
		this.dataUpdateListener = dataUpdateListener;
	}
	
	@Override
	public void fireTableRowsUpdated(int firstRow, int lastRow) {
		this.fetchData();
		super.fireTableRowsUpdated(firstRow, lastRow);
	}
	
	@Override
	public void fireTableRowsDeleted(int firstRow, int lastRow) {
		this.fetchData();
		super.fireTableRowsDeleted(firstRow, lastRow);
	}
	
	
	public void addNotificationListener(NotificationListener notificationListener) {
		this.notificationListener = notificationListener;
	}
	public void addErrorListener(ErrorListener errorListener) {
		this.errorListener = errorListener;
	}
	public void setColumnAdjuster(TableColumnAdjuster tableColumnAdjuster) {
		this.tableColumnAdjuster = tableColumnAdjuster;
	}
	public void adjustColumns() {
		tableColumnAdjuster.adjustColumns();
	}
}