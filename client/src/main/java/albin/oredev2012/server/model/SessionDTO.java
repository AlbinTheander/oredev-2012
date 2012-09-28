package albin.oredev2012.server.model;

import java.util.List;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root(strict=false, name="Session")
public class SessionDTO {
	
	@Attribute
	String id;
	
	@Attribute
	String name;
	
	@Attribute
	String startTime;
	
	@Attribute
	String length;
	
	@Element(required=false)
	String description;
	
	@ElementList
	List<SpeakerId> speakers;
	
	@Root(name="speaker")
	static class SpeakerId {
		@Attribute
		String id;
	}
	

}
