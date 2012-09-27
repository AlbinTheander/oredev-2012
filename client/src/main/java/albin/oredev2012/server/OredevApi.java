package albin.oredev2012.server;

import org.springframework.web.client.RestTemplate;

import albin.oredev2012.server.model.ProgramDTO;

import com.googlecode.androidannotations.annotations.rest.Get;
import com.googlecode.androidannotations.annotations.rest.Rest;

@Rest("http://oredev.org")
public interface OredevApi {
	
	@Get("/speakers.xml")
	ProgramDTO getSpeakers();
	
	RestTemplate getRestTemplate();

}
