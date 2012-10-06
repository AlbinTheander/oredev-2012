package albin.oredev2012.server.model;

import java.util.List;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root(strict = false, name = "track")
public class TrackDTO {

	@Attribute
	 String name;

	@ElementList
	List<SessionDTO> sessions;

}
