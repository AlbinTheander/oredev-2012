package albin.oredev2012.server.model;

import java.util.ArrayList;
import java.util.List;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import albin.oredev2012.model.Session;

@Root(strict = false, name = "track")
public class TrackDTO {

	@Attribute
	private String name;

	@ElementList
	private List<SessionDTO> sessions;

	public String getName() {
		return name;
	}

	public List<SessionDTO> getSessions() {
		return sessions;
	}

	public List<Session> toSessionList() {
		List<Session> result = new ArrayList<Session>();
		for (SessionDTO sessionDto : sessions) {
			Session session = new Session(sessionDto.id, sessionDto.name, name,
					sessionDto.description);
			result.add(session);
		}
		return result;
	}

}
