package application;

import java.util.*;
import java.util.stream.*;

import attribute.*;
import gui.main.DataUpdateListener;
import item.MusicBand;
import item.MusicBandBuilder;

public class MusicBandHandler {
	private Map<Integer, MusicBand> collection = new HashMap<Integer, MusicBand>();
	
	private gui.main.DataUpdateListener dataUpdateListener;
	
	public void updateBands(Map<Integer, MusicBand> bands) {
		List<MusicBand> ownerBands = collection.values().stream()
				.filter(band -> band.getOwner().equals(User.getLogin())).toList();
		this.collection = bands;
		ownerBands.forEach(band -> collection.put(band.getId(), band));
		notifyDataUpdateListener();
	}
	
	public Map<Integer, MusicBand> getBands() {
		return this.collection;
	}
	
	private synchronized void sort() {
		collection = collection.entrySet().stream().
				sorted(Map.Entry.comparingByValue()).
				collect(Collectors.toMap(
						Map.Entry::getKey, Map.Entry::getValue, (entry1, entry2) -> entry1, LinkedHashMap::new));
	}
	
	public String show(Optional<Integer> input) {
		sort();
		final int ITEMS_ON_PAGE = 25;
		Integer page = input.orElse(1);
		int totalPages = 1 + collection.size()/ITEMS_ON_PAGE;
		return(TextColor.bold("Группы коллекции:") + "\n" + collection.values().stream()
				.skip((page-1)*ITEMS_ON_PAGE)
				.limit(ITEMS_ON_PAGE)
				.map(MusicBand::toString)
				.collect(Collectors.joining("\n")) +
				"\nСтраница " + TextColor.bold(String.valueOf(page) + " из " + String.valueOf(totalPages)) 
				+ ". Используйте " + TextColor.bold("show [номер страницы]") + " для навигации по списку групп.");
	}
	
	public void add(int id, List<String> args) {
		MusicBand band = MusicBandBuilder.build(User.getLogin(), args);
		band.setId(id);
		collection.put(band.getId(), band);
		notifyDataUpdateListener();
	}
	
	public void update(List<String> args) {
		int id = Integer.parseInt(args.get(0));
		args.remove(0);
		MusicBand band = MusicBandBuilder.build(User.getLogin(), args);
		band.setId(id);
		collection.put(band.getId(), band);
		notifyDataUpdateListener();
	}
	
	public void removeById(int id) {
		collection.remove(id);
		notifyDataUpdateListener();
	}
	
	public void clear() {
		Iterator<MusicBand> iterator = collection.values().iterator();
		while(iterator.hasNext()) {
			MusicBand band = iterator.next();
			if(band.getOwner().equals(User.getLogin())) {
				iterator.remove();
			}
		}
		notifyDataUpdateListener();
	}
	
	public void addDataUpdateListener(DataUpdateListener dataUpdateListener) {
		this.dataUpdateListener = dataUpdateListener;
	}
	
	private void notifyDataUpdateListener() {
		if(dataUpdateListener != null) {
			dataUpdateListener.setData(collection.values().stream().toList());
			dataUpdateListener.dataUpdated();
		}
	}
}
