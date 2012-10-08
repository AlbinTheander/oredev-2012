package albin.oredev2012;

import albin.oredev2012.imageCache.ImageCache;
import albin.oredev2012.model.Session;
import albin.oredev2012.repo.Repository;
import android.support.v4.app.Fragment;
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

	@ViewById(R.id.name)
	protected TextView nameView;
	
	@ViewById(R.id.description)
	protected TextView descriptionView;

	@Bean
	protected Repository repo;

	@Bean
	protected ImageCache imageCache;

	private String sessionId;

	private Session session;

	@AfterInject
	protected void afterInject() {
		if (isInitalized())
			initInBackground();
	}

	@AfterViews
	protected void afterViews() {
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
		descriptionView.setText(session.getDescription());
	}

}
