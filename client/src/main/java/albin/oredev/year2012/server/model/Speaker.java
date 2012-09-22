package albin.oredev.year2012.server.model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@Root(strict = false)
@DatabaseTable
public class Speaker {

	@Attribute
	@DatabaseField(id=true)
	private String id;

	@Attribute
	@DatabaseField
	private String name;

	@Attribute(name = "photoFile")
	@DatabaseField
	private String imageUrl;
	
	@Element
	@DatabaseField
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
