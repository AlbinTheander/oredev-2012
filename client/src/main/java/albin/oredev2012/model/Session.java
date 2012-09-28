package albin.oredev2012.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName="Session")
public class Session {

	@DatabaseField(id=true)
	private String id;
	
	@DatabaseField
	private String name;

	@DatabaseField
	private String track;
	
	@DatabaseField
	private String description;
	
	// For serialization
	Session() {
	}
	
	public Session(String id, String name, String track, String description) {
		super();
		this.id = id;
		this.name = name;
		this.track = track;
		this.description = description;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}
	
	public String getTrack() {
		return track;
	}

	public String getDescription() {
		return description;
	}
	
}
