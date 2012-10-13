package albin.oredev2012.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SessionsByDateCollection {
	
	private Map<String, List<Session>> sessionsByDate = new LinkedHashMap<String, List<Session>>();
	
	public SessionsByDateCollection() {
	}
	
	public SessionsByDateCollection(List<Session> sessions) {
		for(Session session: sessions) {
			putSession(session);
		}
		for(String date: sessionsByDate.keySet()) {
			Collections.sort(sessionsByDate.get(date), new SessionByDateComparator());
		}
	}
	
	private void putSession(Session session) {
		List<Session> sessions = sessionsByDate.get(session.getDate());
		if (sessions == null) {
			sessions = new ArrayList<Session>();
			sessionsByDate.put(session.getDate(), sessions);
		}
		sessions.add(session);
	}

	public List<String> getDates() {
		return new ArrayList<String>(sessionsByDate.keySet());
	}
	
	public List<Session> getSessions(String date) {
		return sessionsByDate.get(date);
	}

	public static class SessionByDateComparator implements Comparator<Session> {

		@Override
		public int compare(Session lhs, Session rhs) {
			return lhs.getTime().compareTo(rhs.getTime());
		}
	
	}

}
