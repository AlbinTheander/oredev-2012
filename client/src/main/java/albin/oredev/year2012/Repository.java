package albin.oredev.year2012;

import java.util.List;

import org.springframework.http.converter.xml.SimpleXmlHttpMessageConverter;

import albin.oredev.year2012.server.OredevApi;
import albin.oredev.year2012.server.model.Speaker;
import albin.oredev.year2012.server.model.SpeakerList;

import com.googlecode.androidannotations.annotations.AfterInject;
import com.googlecode.androidannotations.annotations.EBean;
import com.googlecode.androidannotations.annotations.rest.RestService;
import com.googlecode.androidannotations.api.Scope;

@EBean(scope=Scope.Singleton)
public class Repository {
	
	private SpeakerList speakerList;
	
	@RestService
	protected OredevApi oredevApi;
	
	@AfterInject
	protected void configure() {
		oredevApi.getRestTemplate().getMessageConverters().add(new SimpleXmlHttpMessageConverter());
	}
	
	public List<Speaker> getSpeakers() {
		if (speakerList == null) {
			speakerList = oredevApi.getSpeakers();
		}
		return speakerList.getSpeakers();
	}

}
