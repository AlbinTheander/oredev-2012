package albin.oredev2012.model;

import java.util.ArrayList;
import java.util.List;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class Speaker extends Item {
	
	@DatabaseField(id=true)
	private String id;
	
	@DatabaseField
	private String name;
	
	@DatabaseField
	private String imageUrl;
	
	@DatabaseField
	private String biograhpy;
	
	private List<Session> sessions = new ArrayList<Session>();
	
	Speaker() {
		// For serialization
	}

	public Speaker(String id, String name, String imageUrl, String biograhpy) {
		this.id = id;
		this.name = name;
		this.imageUrl = imageUrl;
		this.biograhpy = biograhpy;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public String getBiograhpy() {
		return biograhpy;
	}
	
	public void addSession(Session session) {
		sessions.add(session);
	}
	
	public List<Session> getSessions() {
		return sessions;
	}
	
	@Override
	public boolean contains(CharSequence s) {
		return name.toLowerCase().contains(s) || biograhpy.toLowerCase().contains(s);
	}

}
