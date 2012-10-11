package albin.oredev2012.ui;

import java.util.List;

import albin.oredev2012.R;
import albin.oredev2012.model.Session;
import albin.oredev2012.model.Speaker;
import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.googlecode.androidannotations.annotations.EViewGroup;
import com.googlecode.androidannotations.annotations.ViewById;

@EViewGroup(R.layout.session_list_item)
public class SessionListItemView extends LinearLayout {

	@ViewById
	protected TextView time;
	
	@ViewById
	protected TextView name;

	@ViewById
	protected TextView speakers;

	public SessionListItemView(Context context) {
		super(context);
	}

	public void bind(Session session) {
		this.time.setText(session.getTime());
		this.name.setText(session.getName());
		CharSequence speakerNames = getSpeakerNames(session);
		
		this.speakers.setText(speakerNames);
	}

	private CharSequence getSpeakerNames(Session session) {
		List<Speaker> speakers = session.getSpeakers();
		if (speakers == null)
			return "";
		StringBuilder speakerNames = new StringBuilder();
		boolean first = true;
		for(Speaker speaker: speakers) {
			if (speaker == null) {
				continue;
			}
			if (first) {
				first = false;
			} else {
				speakerNames.append(", ");
			}
			speakerNames.append(speaker.getName());
		}
		return speakerNames;
	}
}
