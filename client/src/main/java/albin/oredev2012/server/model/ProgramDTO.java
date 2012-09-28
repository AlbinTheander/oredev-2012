package albin.oredev2012.server.model;

import java.util.List;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root(strict=false, name="program")
public class ProgramDTO {
	
	@ElementList
	private List<SpeakerDTO> speakers;
	
	@ElementList
	private List<TrackDTO> tracks;

	public List<SpeakerDTO> getSpeakers() {
		return speakers;
	}
	
	public List<TrackDTO> getTracks() {
		return tracks;
	}

}
