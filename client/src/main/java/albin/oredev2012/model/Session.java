package albin.oredev2012.model;

import java.util.Collections;
import java.util.List;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "Session")
public class Session {

	@DatabaseField(id = true)
	private String id;

	@DatabaseField
	private String name;
	
	@DatabaseField
	private String date;
	
	@DatabaseField
	private String time;

	@DatabaseField
	private String track;

	@DatabaseField
	private String description;

	@DatabaseField
	private String speakerIds;

	private List<Speaker> speakers = Collections.emptyList();

	// For serialization
	Session() {
	}

	public Session(String id, String name, String date, String time, String track, String description) {
		super();
		this.id = id;
		this.name = name;
		this.date = date;
		this.time = time;
		this.track = track;
		this.description = description;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}
	
	public String getDate() {
		return date;
	}
	
	public String getTime() {
		return time;
	}

	public String getTrack() {
		return track;
	}

	public String getDescription() {
		return description;
	}

	public List<Speaker> getSpeakers() {
		return speakers;
	}

	public String getSpeakerIds() {
		return speakerIds;
	}

	public void setSpeakers(List<Speaker> speakers) {
		this.speakers = speakers;
		StringBuilder sb = new StringBuilder(speakers.size() * 33);
		boolean first = true;
		for (Speaker speaker : speakers) {
			if (speaker != null) {
				if (first) {
					first = false;
				} else {
					sb.append(',');
				}
				sb.append(speaker.getId());
			}
		}
		speakerIds = sb.toString();
	}

}
