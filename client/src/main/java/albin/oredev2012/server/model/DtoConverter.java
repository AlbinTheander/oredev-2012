package albin.oredev2012.server.model;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import albin.oredev2012.model.Session;
import albin.oredev2012.model.Speaker;
import albin.oredev2012.server.model.SessionDTO.SpeakerId;
import albin.oredev2012.util.StringUtil;

public class DtoConverter {

	private LinkedHashMap<String, Session> sessions;
	private LinkedHashMap<String, Speaker> speakers;
	private DTOValidator validator;

	public DtoConverter(ProgramDTO programDTO) {
		validator = new DTOValidator();
		initSpeakerList(programDTO);
		initSessionList(programDTO);
		resolveSpeakersForSessions(programDTO);
	}

	private void resolveSpeakersForSessions(ProgramDTO programDTO) {
		for (TrackDTO track : programDTO.tracks) {
			for (SessionDTO sessionDTO : track.sessions) {
				Session session = sessions.get(sessionDTO.id);
				if (session == null)
					continue;
				List<Speaker> sessionSpeakers = new ArrayList<Speaker>();
				for (SpeakerId speakerId : sessionDTO.speakers) {
					Speaker speaker = speakers.get(speakerId.id);
					if (speaker != null)
						sessionSpeakers.add(speaker);
				}
				session.setSpeakers(sessionSpeakers);
			}
		}
	}

	public List<Speaker> getSpeakers() {
		return new ArrayList<Speaker>(speakers.values());
	}

	public List<Session> getSessions() {
		return new ArrayList<Session>(sessions.values());
	}

	private void initSpeakerList(ProgramDTO programDTO) {
		speakers = new LinkedHashMap<String, Speaker>();
		for (SpeakerDTO dto : programDTO.speakers) {
			String biography = StringUtil.replace(dto.biography, "\n", " ");
			speakers.put(dto.id, new Speaker(dto.id, dto.name, dto.photoFile,
					biography));
		}
	}

	private void initSessionList(ProgramDTO programDTO) {
		sessions = new LinkedHashMap<String, Session>();
		for (TrackDTO track : programDTO.tracks) {
			if (validator.isValid(track)) {
				for (SessionDTO dto : track.sessions) {
					sessions.put(dto.id, new Session(dto.id, dto.name,
							track.name, dto.description));
				}
			}
		}
	}

}