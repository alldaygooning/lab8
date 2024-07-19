package thread;

import java.io.IOException;
import java.net.URI;
import java.net.http.*;
import java.time.Instant;

import org.json.*;

import application.Application;
import attribute.TextColor;
import module.ConnectionModule;

public class RefreshingThread extends Thread{
	private final int timeBetweenRefreshing = 5000;
	public long lastUpdated;
	@Override
	public void run() {
		HttpClient httpClient = ConnectionModule.getHttpClient();
		while(true) {
			lastUpdated = Instant.now().toEpochMilli();
			JSONArray json = new JSONArray(Application.MusicBandHandler.getMap().values());
			String httpRequestBody = "{\"file_data\": "+json.toString()+"}";
			HttpRequest httpRequest = HttpRequest.newBuilder()
					.uri(URI.create("https://api.jsonsilo.com/api/v1/manage/bde7920d-6abe-4cf8-8cc8-cc0d016ca744"))
					.method("PATCH", HttpRequest.BodyPublishers.ofString(httpRequestBody))
					.header("Content-Type", "application/json")
					.header("accept", "application/json")
					.header("X-MAN-API", System.getenv("apiKey"))
					.build();
			try {
				HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
				if (httpResponse.statusCode() == 200) {
					Application.logger.info(TextColor.bold("JSON refreshed successfully " + lastUpdated));
				}
				else {
					Application.logger.info("Error refreshing JSON: " + httpResponse.statusCode());
				}
				Thread.sleep(timeBetweenRefreshing);
			}catch(InterruptedException | IOException e) {
				e.printStackTrace();
				httpClient = ConnectionModule.getHttpClient();
				continue;
			}
		}
	}
	
	public long getLastUpdate() {
		return this.lastUpdated;
	}
}
