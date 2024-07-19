package wrapper;

import java.io.Serializable;
import java.util.Set;

import item.*;

public class Response implements Serializable{
	private static final long serialVersionUID = 1L;
	
	public boolean successful;
	public int reason_id;
	
	public String text;
	public Set<MusicBand> musicBands;
	
	public Response(boolean successful, int reason_id) {
		this.successful = successful;
		this.reason_id = reason_id;
	}
	
	public Response(boolean successful) {
		this.successful = successful;
	}
	
	public Response(boolean successful, Set<MusicBand> musicBands) {
		this.successful = successful;
		this.musicBands = musicBands;
	}
	
	public Response(boolean successful, String text) {
		this.successful = successful;
		this.text = text;
	}
}
