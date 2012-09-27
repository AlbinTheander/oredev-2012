package albin.oredev2012.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class Speaker {
	
	@DatabaseField(id=true)
	private String id;
	
	@DatabaseField
	private String name;
	
	@DatabaseField
	private String imageUrl;
	
	@DatabaseField
	private String biograhpy;
	
	// For serialization
	Speaker() {
	}

	public Speaker(String id, String name, String imageUrl, String biograhpy) {
		this.id = id;
		this.name = name;
		this.imageUrl = imageUrl;
		this.biograhpy = biograhpy;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public String getBiograhpy() {
		return biograhpy;
	}
	
	
	

}
