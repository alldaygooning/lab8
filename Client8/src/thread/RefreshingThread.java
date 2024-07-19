package thread;

import java.io.*;
import java.net.URI;
import java.net.http.*;
import java.time.*;
import java.util.*;

import org.json.*;

import application.*;
import attribute.*;
import item.*;
import module.ConnectionModule;

public class RefreshingThread extends Thread{
	
	private final int timeBetweenRefreshing = 10000;
	private static MusicBandHandler musicBandHandler;
	int loop = 0;
	
	@Override
	public void run() {
		HttpClient httpClient = ConnectionModule.getHttpClient();
		HttpRequest httpRequest;
		while(!Thread.currentThread().isInterrupted()) {
			try {
				if (!ConnectionModule.isConnected() || !User.isAuthorized()) {
					synchronized(this){
						this.wait();
					}
				}
				httpRequest = HttpRequest.newBuilder()
						.uri(URI.create("https://api.jsonsilo.com/public/bde7920d-6abe-4cf8-8cc8-cc0d016ca744"))
						.GET()
						.build();
			
				HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
				if (httpResponse.statusCode() != 200) {
					System.out.println("Error occured trying to fetch updated JSON: " + httpResponse.statusCode());
					continue;
				}
				Map<Integer, MusicBand> musicBands = parse(httpResponse.body());
				musicBandHandler.updateBands(musicBands);
				loop++;
				sleep(timeBetweenRefreshing);
			}catch(InterruptedException e) {
				Thread.currentThread().interrupt();
			}catch(IOException e) {
				System.out.println("refresh thread error:");
				e.printStackTrace();
			}
		}
	}
	
	private Map<Integer, MusicBand> parse(String source) {
		Map<Integer, MusicBand> bands = new HashMap<Integer, MusicBand>();
		JSONArray jsonArray = new JSONArray(source);
		jsonArray.forEach(object -> {
			JSONObject jsonBand = (JSONObject) object;
			JSONObject jsonPerson = jsonBand.getJSONObject("frontMan");
			Person person = new Person(new PersonBuilder()
					.name(jsonPerson.getString("name"))
					.height(jsonPerson.optLongObject("height", null))
					.weight(jsonPerson.getInt("weight"))
					.hairColor(Color.valueOf(jsonPerson.getString("hairColor")))
					.nationality(Country.valueOfNullable(jsonPerson.optString("country", null)))
					);
			MusicBand band = new MusicBand(new MusicBandBuilder()
					.id(jsonBand.getInt("id"))
					.name(jsonBand.getString("name"))
					.coordinates(new Coordinates(jsonBand.getDouble("x"), jsonBand.getDouble("y")))
					.creationDate(LocalDate.parse(jsonBand.getString("creationDate")))
					.numberOfParticipants(jsonBand.getLong("numberOfParticipants"))
					.genre(MusicGenre.valueOfNullable(jsonBand.optString("genre", null)))
					.frontMan(person)
					.owner(jsonBand.getString("owner"))
					);
			if(loop == 0 || !band.getOwner().equals(User.getLogin())) {
				bands.put(band.getId(), band);
			}
		});
		return bands;
	}
	
	public void setHandler(MusicBandHandler handler) {
		RefreshingThread.musicBandHandler = handler;
	}
	
	public MusicBandHandler getHandler() {
		return musicBandHandler;
	}
}
