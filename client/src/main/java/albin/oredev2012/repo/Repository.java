package albin.oredev2012.repo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.converter.xml.SimpleXmlHttpMessageConverter;

import albin.oredev2012.db.DatabaseHelper;
import albin.oredev2012.model.Session;
import albin.oredev2012.model.Speaker;
import albin.oredev2012.server.OredevApi;
import albin.oredev2012.server.model.ProgramDTO;
import albin.oredev2012.server.model.SpeakerDTO;
import albin.oredev2012.server.model.TrackDTO;
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
	
	private List<Session> sessionList;
	
	@RestService
	protected OredevApi oredevApi;
	
	@RootContext
	protected Context context;
	
	@AfterInject
	protected void configure() {
		oredevApi.getRestTemplate().getMessageConverters().add(new SimpleXmlHttpMessageConverter());
	}
	
	public List<Speaker> getSpeakers() {
		loadData();
		return speakerList;
	}

	public List<Session> getSessions() {
		loadData();
		return sessionList;
	}

	private void loadData() {
		if (speakerList == null) {
			loadDataFromDb();
		}
		if (speakerList == null || speakerList.size() == 0) {
			loadDataFromServer();
			storeDataInDb();
		}
	}

	private void loadDataFromServer() {
		ProgramDTO program = oredevApi.getProgram();
		List<Speaker> speakers = new ArrayList<Speaker>();
		for(SpeakerDTO speakerDto: program.getSpeakers()) {
			Speaker speaker = speakerDto.toSpeaker();
			speakers.add(speaker);
		}
		List<Session> sessions = new ArrayList<Session>();
		for(TrackDTO trackDto: program.getTracks()) {
			sessions.addAll(trackDto.toSessionList());
		}
		speakerList = speakers;
		sessionList = sessions;
	}


	private void loadDataFromDb() {
		DatabaseHelper db = new DatabaseHelper(context);
		RuntimeExceptionDao<Speaker, String> speakerDao = db.getSpeakerDao();
		List<Speaker> speakers = speakerDao.queryForAll();
		RuntimeExceptionDao<Session, String> sessionDao = db.getSessionDao();
		List<Session> sessions = sessionDao.queryForAll();
		db.close();
		sessionList = sessions;
		speakerList = speakers;
	}

	private void storeDataInDb() {
		DatabaseHelper db = new DatabaseHelper(context);
		RuntimeExceptionDao<Speaker, String> speakerDao = db.getSpeakerDao();
		for(Speaker speaker: speakerList) {
			speakerDao.create(speaker);
		}
		RuntimeExceptionDao<Session, String> sessionDao = db.getSessionDao();
		for(Session session: sessionList) {
			sessionDao.create(session);
		}
		db.close();
	}

	public Speaker getSpeaker(String speakerId) {
		List<Speaker> speakers = getSpeakers();
		for(Speaker speaker: speakers) {
			if (speakerId.equals(speaker.getId()))
				return speaker;
		}
		return null;
	}
}
