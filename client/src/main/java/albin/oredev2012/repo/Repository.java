package albin.oredev2012.repo;

import java.sql.SQLException;
import java.util.List;

import org.springframework.http.converter.xml.SimpleXmlHttpMessageConverter;

import albin.oredev2012.db.DatabaseHelper;
import albin.oredev2012.db.OredevDb;
import albin.oredev2012.model.Session;
import albin.oredev2012.model.Speaker;
import albin.oredev2012.server.OredevApi;
import albin.oredev2012.server.model.DtoConverter;
import albin.oredev2012.server.model.ProgramDTO;
import albin.oredev2012.util.Logg;
import android.content.Context;

import com.googlecode.androidannotations.annotations.AfterInject;
import com.googlecode.androidannotations.annotations.EBean;
import com.googlecode.androidannotations.annotations.RootContext;
import com.googlecode.androidannotations.annotations.rest.RestService;
import com.googlecode.androidannotations.api.Scope;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.table.TableUtils;

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

	public Speaker getSpeaker(String speakerId) {
		List<Speaker> speakers = getSpeakers();
		for(Speaker speaker: speakers) {
			if (speakerId.equals(speaker.getId()))
				return speaker;
		}
		return null;
	}

	public void refreshFromServer() {
		loadDataFromServer();
		storeDataInDb();
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
		DtoConverter converter = new DtoConverter(program);
		sessionList = converter.getSessions();
		speakerList = converter.getSpeakers();
	}


	private void loadDataFromDb() {
		OredevDb oredevDb = new OredevDb(context);
		sessionList = oredevDb.getSessions();
		speakerList = oredevDb.getSpeakers();
	}

	private void storeDataInDb() {
		DatabaseHelper db = new DatabaseHelper(context);
		try {
			TableUtils.clearTable(db.getConnectionSource(), Speaker.class);
			TableUtils.clearTable(db.getConnectionSource(), Session.class);
			RuntimeExceptionDao<Speaker, String> speakerDao = db.getSpeakerDao();
			for(Speaker speaker: speakerList) {
				speakerDao.create(speaker);
			}
			RuntimeExceptionDao<Session, String> sessionDao = db.getSessionDao();
			for(Session session: sessionList) {
				sessionDao.create(session);
			}
		} catch (SQLException e) {
			Logg.d("Couldn't store data in database", e);
		} finally {
			db.close();
		}
	}
}