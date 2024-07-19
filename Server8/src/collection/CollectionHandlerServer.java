package collection;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import application.Application;
import item.*;
import module.DatabaseModule;
import exception.ExecutionCancelled;




public class CollectionHandlerServer{
	volatile Map<Integer, MusicBand> collection = new  ConcurrentHashMap<Integer, MusicBand>();
	
	public void loadCollection(List<MusicBand> musicBands) {
		musicBands.forEach(band -> collection.put(band.getId(), band));
		Application.logger.info("Collection loaded.");
	}
	
	public String info() {
		return (collection.getClass() + ";" + collection.size());
	}
	
	public Map<Integer, MusicBand> getMap(){
		return collection;
	}
	
	public int add(String owner, List<String> args) { 
		MusicBand band = MusicBandBuilder.build(owner, args);
		DatabaseModule.insertMusicBand(band);
		collection.put(band.getId(), band);
		return (band.getId());
	}
	
	public void update(String owner, List<String> args) {
		int id = Integer.parseInt(args.get(0));
		args.remove(0);
		MusicBand band = collection.get(id);
		if (!band.getOwner().equals(owner)) {
			throw new ExecutionCancelled(58);
		}
		band = MusicBandBuilder.build(owner, args);
		band.setId(id);
		DatabaseModule.updateMusicBand(band);
		collection.put(band.getId(), band);
	}
	
	public void remove(String owner, List<String> args) {
		int id = Integer.parseInt(args.get(0));
		if(!collection.containsKey(id)) {
			throw new ExecutionCancelled(60);
		}
		MusicBand band = collection.get(id);
		if(!band.getOwner().equals(owner)) {
			throw new ExecutionCancelled(58);
		}
		collection.remove(id);
		DatabaseModule.removeById(id);
	}
	
	public int clear(String user, List<String> args) {
		int i = 0;
		Iterator<MusicBand> iterator = collection.values().iterator();
		while(iterator.hasNext()) {
			MusicBand band = iterator.next();
			if(band.getOwner().equals(user)) {
				DatabaseModule.removeById(band.getId());
				iterator.remove();
				i++;
			}
		}
		return i;
	}
}
