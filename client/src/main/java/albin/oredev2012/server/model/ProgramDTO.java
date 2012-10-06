package albin.oredev2012.server.model;

import java.util.List;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root(strict = false, name = "program")
public class ProgramDTO {

	@ElementList
	List<SpeakerDTO> speakers;

	@ElementList
	List<TrackDTO> tracks;

}
