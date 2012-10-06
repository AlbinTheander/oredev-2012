package albin.oredev2012.server.model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(strict = false, name = "speaker")
public class SpeakerDTO {

	@Attribute
	String id;

	@Attribute
	String name;

	@Attribute
	String photoFile;

	@Element
	String biography;

}
