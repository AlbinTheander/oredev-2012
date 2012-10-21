package albin.oredev2012.model;

import java.util.Collections;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "Session")
public class Session extends Item {

	@DatabaseField(id = true)
	private String id;

	@DatabaseField
	private String name;
	
	@DatabaseField(dataType=DataType.DATE_TIME)
	private DateTime startTime;
	
	@DatabaseField(dataType=DataType.DATE_TIME)
	private DateTime endTime;

	@DatabaseField
	private String track;

	@DatabaseField
	private String description;

	@DatabaseField
	private String speakerIds;

	private List<Speaker> speakers = Collections.emptyList();


	Session() {
		// For serialization
	}

	public Session(String id, String name, DateTime startTime, DateTime endTime, String track, String description) {
		super();
		this.id = id;
		this.name = name;
		this.startTime = startTime;
		this.endTime = endTime;
		this.track = track;
		this.description = description;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}
	
	public LocalDate getDate() {
		return startTime.toLocalDate();
	}
	
	public LocalTime getStartTime() {
		return startTime.toLocalTime();
	}
	
	public Interval getTime() {
		Interval interval = new Interval(startTime, endTime);
		return interval;
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
	
	@Override
	public boolean contains(CharSequence s) {
		if (name != null && name.toLowerCase().contains(s))
			return true;
		if (description != null && description.toLowerCase().contains(s))
			return true;
		return false;
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
