package albin.oredev2012.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.LocalDate;

public class SessionsByDateCollection {
	
	private Map<LocalDate, List<Session>> sessionsByDate = new LinkedHashMap<LocalDate, List<Session>>();
	
	public SessionsByDateCollection() {
	}
	
	public SessionsByDateCollection(List<Session> sessions) {
		for(Session session: sessions) {
			putSession(session);
		}
		for(LocalDate date: sessionsByDate.keySet()) {
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

	public List<LocalDate> getDates() {
		return new ArrayList<LocalDate>(sessionsByDate.keySet());
	}
	
	public List<Session> getSessions(LocalDate date) {
		return sessionsByDate.get(date);
	}

	public static class SessionByDateComparator implements Comparator<Session> {

		@Override
		public int compare(Session lhs, Session rhs) {
			return lhs.getStartTime().compareTo(rhs.getStartTime());
		}
	
	}

}
