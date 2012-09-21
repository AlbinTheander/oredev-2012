package albin.oredev.year2012.server.model;

import java.util.List;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root(strict=false, name="program")
public class SpeakerList {
	
	@ElementList
	private List<Speaker> speakers;

	public List<Speaker> getSpeakers() {
		return speakers;
	}
}
