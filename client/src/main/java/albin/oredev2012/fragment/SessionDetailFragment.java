package albin.oredev2012.fragment;

import albin.oredev2012.R;
import albin.oredev2012.imageCache.ImageCache;
import albin.oredev2012.imageCache.ImageCache.OnImageLoadedListener;
import albin.oredev2012.model.Session;
import albin.oredev2012.model.Speaker;
import albin.oredev2012.repo.Repository;
import albin.oredev2012.ui.SpeakerListItemView;
import albin.oredev2012.ui.SpeakerListItemView_;
import albin.oredev2012.util.FormatUtil;
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

@EFragment(R.layout.session_detail_fragment)
public class SessionDetailFragment extends Fragment {

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
		return imageCache != null && descriptionView != null
				&& sessionId != null;
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
		startTimeView.setText(FormatUtil.format(session.getTime()));
		trackView.setText(session.getTrack());
		descriptionView.setText(session.getDescription());
		addSpeakers();
	}

	private void addSpeakers() {
		speakersView.removeAllViews();
		for (Speaker speaker : session.getSpeakers()) {
			if (speaker == null)
				continue;
			String name = speaker.getName();
			Bitmap speakerImage = imageCache.getImage(speaker.getImageUrl(),
					true, new OnImageLoadedListener() {

						@Override
						public void onImageLoaded(String url, Bitmap bitmap) {
							initViews();
						}
					});
			SpeakerListItemView speakerItem = SpeakerListItemView_
					.build(getActivity());
			speakerItem.bind(name, speakerImage);
			speakersView.addView(speakerItem);
		}
	}

}
