package albin.oredev.year2012.server.model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(strict = false)
public class Speaker {

	@Attribute
	private String id;

	@Attribute
	private String name;

	@Attribute(name = "photoFile")
	private String imageUrl;
	
	@Element
	private String biography;

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getImageUrl() {
		return imageUrl;
	}
	
	public String getBiography() {
		return biography;
	}

	@Override
	public String toString() {
		return name;
	}
}
