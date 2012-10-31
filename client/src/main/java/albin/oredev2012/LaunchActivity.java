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

@EActivity(R.layout.launch_activity)
public class LaunchActivity extends Activity {

	@Bean
	protected Repository repo;

	@ViewById
	protected View loadingProgress;

	@AfterInject
	protected void init() {
		loadData(false);
	}

	@Click(R.id.speakers_button)
	protected void openSpeakers() {
		SpeakerListActivity_.intent(this).start();
	}

	@Click(R.id.sessions_button)
	protected void openSessions() {
		SessionListActivity_.intent(this).start();
	}

	@Click(R.id.search_button)
	protected void search() {
		SearchActivity_.intent(this).start();
	}

	@Click(R.id.refresh_button)
	protected void refreshFromServer() {
		loadData(true);
	}

	@Background
	protected void loadData(boolean forceReload) {
		setLoadingProgressVisible(true);
		if (forceReload) {
			repo.refreshFromServer();
		} else {
			repo.refreshFromServer();
		}
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
