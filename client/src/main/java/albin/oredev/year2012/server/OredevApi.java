package albin.oredev.year2012.server;

import org.springframework.web.client.RestTemplate;

import albin.oredev.year2012.server.model.SpeakerList;

import com.googlecode.androidannotations.annotations.rest.Get;
import com.googlecode.androidannotations.annotations.rest.Rest;

@Rest("http://oredev.org")
public interface OredevApi {
	
	@Get("/speakers.xml")
	SpeakerList getSpeakers();
	
	RestTemplate getRestTemplate();

}
