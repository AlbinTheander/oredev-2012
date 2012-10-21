package albin.oredev2012.repo;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.Callable;

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
import com.googlecode.androidannotations.annotations.Background;
import com.googlecode.androidannotations.annotations.EBean;
import com.googlecode.androidannotations.annotations.RootContext;
import com.googlecode.androidannotations.annotations.rest.RestService;
import com.googlecode.androidannotations.api.Scope;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.table.TableUtils;

@EBean(scope = Scope.Singleton)
public class Repository {

	private List<Speaker> speakerList;

	private List<Session> sessionList;

	@RestService
	protected OredevApi oredevApi;

	@RootContext
	protected Context context;

	@AfterInject
	protected void configure() {
		oredevApi.getRestTemplate().getMessageConverters()
				.add(new SimpleXmlHttpMessageConverter());
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
		for (Speaker speaker : speakers) {
			if (speakerId.equals(speaker.getId())) {
				return speaker;
			}
		}
		return null;
	}

	public Session getSession(String sessionId) {
		List<Session> sessions = getSessions();
		for (Session session : sessions) {
			if (sessionId.equals(session.getId())) {
				return session;
			}
		}
		return null;
	}

	@Background
	public void preloadData() {
		loadData();
	}

	public synchronized void refreshFromServer() {
		loadDataFromServer();
		storeDataInDb();
	}

	private synchronized void loadData() {
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
			final RuntimeExceptionDao<Speaker, String> speakerDao = db
					.getSpeakerDao();
			speakerDao.callBatchTasks(new Callable<Void>() {

				@Override
				public Void call() {
					for (Speaker speaker : speakerList) {
						speakerDao.create(speaker);
					}
					return null;
				}
			});
			final RuntimeExceptionDao<Session, String> sessionDao = db
					.getSessionDao();
			sessionDao.callBatchTasks(new Callable<Void>() {

				@Override
				public Void call() {
					for (Session session : sessionList) {
						sessionDao.create(session);
					}
					return null;
				}
			});
		} catch (SQLException e) {
			Logg.d("Couldn't store data in database", e);
		} finally {
			db.close();
		}
	}
}
