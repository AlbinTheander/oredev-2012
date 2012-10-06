package albin.oredev2012.server.model;


public class DTOValidator {
	
	private static final String[] VALID_ROOMS = {"Room 1", "Room2", "Room3", "Room4", "Room5", "Room6", "Theatre", "Workshop"};

	public boolean isValid(TrackDTO track) {
		for(String validTrack: VALID_ROOMS) {
			if (validTrack.equals(track.name)) {
				return true;
			}
		}
		return false;
	}
	public boolean isValid(SessionDTO session) {
		return true;
	}

}
