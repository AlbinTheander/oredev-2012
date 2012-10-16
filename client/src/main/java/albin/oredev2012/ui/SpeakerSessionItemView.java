package albin.oredev2012.ui;

import albin.oredev2012.R;
import albin.oredev2012.model.Session;
import albin.oredev2012.util.FormatUtil;
import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.googlecode.androidannotations.annotations.EViewGroup;
import com.googlecode.androidannotations.annotations.ViewById;

@EViewGroup(R.layout.speaker_session_item)
public class SpeakerSessionItemView extends LinearLayout {

	@ViewById
	protected TextView name;

	@ViewById
	protected TextView track;
	
	@ViewById
	protected TextView time;
	
	@ViewById
	protected TextView date;

	public SpeakerSessionItemView(Context context) {
		super(context);
	}

	public void bind(Session session) {
		track.setText(session.getTrack());
		name.setText(session.getName());
		date.setText(session.getDate().dayOfWeek().getAsText());
		time.setText(FormatUtil.format(session.getTime()));
	}

}
