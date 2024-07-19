package gui.main.tab;

import java.awt.Font;
import java.util.Collection;
import java.util.List;

import javax.swing.*;

import gui.*;
import gui.main.MusicBandUpdateListener;
import gui.main.tab.create.CreateTab;
import gui.main.tab.table.TableTab;
import gui.main.tab.visual.VisualTab;
import item.MusicBand;
import module.GraphicModule;

public class MainTabbedPane extends JTabbedPane implements TranslatedContainer{
	private static final long serialVersionUID = 1L;
	
	TableTab tableTab;
	CreateTab createTab;
	VisualTab visualTab;
	
	MusicBandUpdateListener dataUpdateListener;

	public MainTabbedPane() {
		super();
		this.setFont(new Font(Font.DIALOG, Font.BOLD, 20));
		
		this.setTabs();
		this.setListeners();
	}
	
	private void setTabs() {
		tableTab = new TableTab();
		createTab = new CreateTab();
		visualTab = new VisualTab();
		
		this.addTab(tableTab.getTitle(), tableTab);
		this.addTab(createTab.getTitle(), createTab);
		this.addTab(visualTab.getTitle(), visualTab);
		
		this.setSelectedIndex(0);
	}
	
	private void setListeners() {
		dataUpdateListener = new MusicBandUpdateListener();
		GraphicModule.addDataUpdateListener(dataUpdateListener);
		
		MusicBandUpdateListener tableDataUpdateListener = new MusicBandUpdateListener() {
			@Override
			public void dataUpdated() {
				tableTab.updateTableData();
			}
			
			@Override
			public Collection<?> getData() {
				return dataUpdateListener.getData();
			}
		};
		MusicBandUpdateListener visualizationDataUpdateListener = new MusicBandUpdateListener() {
			@Override
			public void dataUpdated() {
				visualTab.draw();
			}
			
			@Override
			public Collection<?> getData() {
				return dataUpdateListener.getData();
			}
		};
		
		dataUpdateListener.addDataUpdateListener(tableDataUpdateListener);
		dataUpdateListener.addDataUpdateListener(visualizationDataUpdateListener);
		
		tableTab.addDataUpdateListener(tableDataUpdateListener);
		visualTab.addDataUpdateListener(visualizationDataUpdateListener);
	}
	

	@Override
	public void translate() {
		for(int i = 0; i < this.getTabCount(); i++) {
			CustomTab customTab = (CustomTab) this.getComponent(i);
			this.setTitleAt(i, customTab.getTitle());
		}
		TranslatedContainer.super.translate();
		this.revalidate();
	}
	
	@SuppressWarnings("unchecked")
	public List<MusicBand> getData(){
		return (List<MusicBand>) dataUpdateListener.getData();
	}
}
