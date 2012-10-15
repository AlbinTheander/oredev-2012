package albin.oredev2012.fragment;

import org.joda.time.Interval;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import albin.oredev2012.R;
import albin.oredev2012.imageCache.ImageCache;
import albin.oredev2012.imageCache.ImageCache.OnImageLoadedListener;
import albin.oredev2012.model.Session;
import albin.oredev2012.model.Speaker;
import albin.oredev2012.repo.Repository;
import albin.oredev2012.ui.SpeakerListItemView;
import albin.oredev2012.ui.SpeakerListItemView_;
import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.googlecode.androidannotations.annotations.AfterInject;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Background;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EFragment;
import com.googlecode.androidannotations.annotations.UiThread;
import com.googlecode.androidannotations.annotations.ViewById;

@EFragment(R.layout.fragment_session_detail)
public class SessionDetailFragment extends Fragment {
	
	private static final DateTimeFormatter FORMAT = DateTimeFormat.forPattern("HH:mm");

	@ViewById(R.id.name)
	protected TextView nameView;
	
	@ViewById(R.id.description)
	protected TextView descriptionView;
	
	@ViewById(R.id.start_time)
	protected TextView startTimeView;
	
	@ViewById(R.id.track)
	protected TextView trackView;
	
	@ViewById(R.id.speaker_info)
	protected LinearLayout speakersView;

	@Bean
	protected Repository repo;
	
	@Bean
	protected ImageCache imageCache;

	private String sessionId;

	private Session session;

	@AfterViews
	@AfterInject
	protected void afterInject() {
		if (isInitalized())
			initInBackground();
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
		if (isInitalized())
			initInBackground();
	}

	private boolean isInitalized() {
		return imageCache != null && descriptionView != null && sessionId != null;
	}

	@Background
	protected void initInBackground() {
		session = repo.getSession(sessionId);
		if (session == null)
			return;
		initViews();
	}

	@UiThread
	protected void initViews() {
		nameView.setText(session.getName());
		CharSequence time = getTime();
		startTimeView.setText(time);
		trackView.setText(session.getTrack());
		descriptionView.setText(session.getDescription());
		addSpeakers();
	}

	private CharSequence getTime() {
		StringBuffer sb = new StringBuffer();
		Interval interval = session.getTime();
		FORMAT.printTo(sb, interval.getStart());
		sb.append(" - ");
		FORMAT.printTo(sb, interval.getEnd());
		return sb;
	}

	private void addSpeakers() {
		speakersView.removeAllViews();
		for(Speaker speaker: session.getSpeakers()) {
			if (speaker == null)
				continue;
			String name = speaker.getName();
			Bitmap speakerImage = imageCache.getImage(speaker.getImageUrl(), true, new OnImageLoadedListener() {
				
				@Override
				public void onImageLoaded(String url, Bitmap bitmap) {
					initViews();
				}
			});
			SpeakerListItemView speakerItem = SpeakerListItemView_.build(getActivity());
			speakerItem.bind(name, speakerImage);
			speakersView.addView(speakerItem);
		}
	}

}
