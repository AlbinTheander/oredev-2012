package albin.oredev.year2012;

import java.util.List;

import org.springframework.http.converter.xml.SimpleXmlHttpMessageConverter;

import albin.oredev.year2012.db.DatabaseHelper;
import albin.oredev.year2012.server.OredevApi;
import albin.oredev.year2012.server.model.Speaker;
import android.content.Context;

import com.googlecode.androidannotations.annotations.AfterInject;
import com.googlecode.androidannotations.annotations.EBean;
import com.googlecode.androidannotations.annotations.RootContext;
import com.googlecode.androidannotations.annotations.rest.RestService;
import com.googlecode.androidannotations.api.Scope;
import com.j256.ormlite.dao.RuntimeExceptionDao;

@EBean(scope=Scope.Singleton)
public class Repository {
	
	private List<Speaker> speakerList;
	
	@RestService
	protected OredevApi oredevApi;
	
	@RootContext
	protected Context context;
	
	@AfterInject
	protected void configure() {
		oredevApi.getRestTemplate().getMessageConverters().add(new SimpleXmlHttpMessageConverter());
	}
	
	public List<Speaker> getSpeakers() {
		if (speakerList == null) {
			speakerList = getSpeakerListFromDb();
		}
		if (speakerList == null || speakerList.size() == 0) {
			speakerList = oredevApi.getSpeakers().getSpeakers();
			storeSpeakerListInDb();
		}
		return speakerList;
	}


	private List<Speaker> getSpeakerListFromDb() {
		DatabaseHelper db = new DatabaseHelper(context);
		RuntimeExceptionDao<Speaker, String> speakerDao = db.getSpeakerDao();
		List<Speaker> speakers = speakerDao.queryForAll();
		db.close();
		return speakers;
	}

	private void storeSpeakerListInDb() {
		DatabaseHelper db = new DatabaseHelper(context);
		RuntimeExceptionDao<Speaker, String> speakerDao = db.getSpeakerDao();
		for(Speaker speaker: speakerList) {
			speakerDao.create(speaker);
		}
		db.close();
	}
}
