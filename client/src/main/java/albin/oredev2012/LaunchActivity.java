package albin.oredev2012;

import albin.oredev2012.repo.Repository;
import android.app.Activity;
import android.view.View;

import com.googlecode.androidannotations.annotations.AfterInject;
import com.googlecode.androidannotations.annotations.Background;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.UiThread;
import com.googlecode.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_launch)
public class LaunchActivity extends Activity {

	@Bean
	protected Repository repo;

	@ViewById
	protected View loadingProgress;
	
	@AfterInject
	protected void init() {
		repo.preloadData();
	}

	@Click(R.id.speakers_button)
	protected void openSpeakers() {
		SpeakerListActivity_.intent(this).start();
	}

	@Click(R.id.sessions_button)
	protected void openSessions() {
		SessionListActivity_.intent(this).start();
	}

	@Click(R.id.refresh_button)
	protected void refreshFromServer() {
		setLoadingProgressVisible(true);
		refreshData();
	}

	@Background
	protected void refreshData() {
		repo.refreshFromServer();
		setLoadingProgressVisible(false);
	}

	@UiThread
	protected void setLoadingProgressVisible(boolean visible) {
		if (visible) {
			loadingProgress.setVisibility(View.VISIBLE);
		} else {
			loadingProgress.setVisibility(View.INVISIBLE);
		}
	}

}
