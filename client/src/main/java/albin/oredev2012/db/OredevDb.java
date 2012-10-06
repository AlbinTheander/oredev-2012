package albin.oredev2012.db;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import albin.oredev2012.model.Session;
import albin.oredev2012.model.Speaker;
import android.content.Context;

import com.j256.ormlite.dao.RuntimeExceptionDao;

public class OredevDb {

	private LinkedHashMap<String, Session> sessions = new LinkedHashMap<String, Session>();
	private LinkedHashMap<String, Speaker> speakers = new LinkedHashMap<String, Speaker>();

	public OredevDb(Context context) {
		DatabaseHelper db = new DatabaseHelper(context);
		RuntimeExceptionDao<Speaker, String> speakerDao = db.getSpeakerDao();
		List<Speaker> speakers = speakerDao.queryForAll();
		RuntimeExceptionDao<Session, String> sessionDao = db.getSessionDao();
		List<Session> sessions = sessionDao.queryForAll();
		db.close();
		for (Speaker speaker : speakers) {
			this.speakers.put(speaker.getId(), speaker);
		}
		for (Session session : sessions) {
			this.sessions.put(session.getId(), session);
		}
		resolveSpeakersForSessions();
	}

	private void resolveSpeakersForSessions() {
		for (Session session : sessions.values()) {
			String[] speakerIds = session.getSpeakerIds().split(",");
			List<Speaker> sessionSpeakers = new ArrayList<Speaker>();
			for (String speakerId : speakerIds) {
				Speaker speaker = speakers.get(speakerId);
				sessionSpeakers.add(speaker);
			}
			session.setSpeakers(sessionSpeakers);
		}
	}

	public List<Speaker> getSpeakers() {
		return new ArrayList<Speaker>(speakers.values());
	}

	public List<Session> getSessions() {
		return new ArrayList<Session>(sessions.values());
	}
}
