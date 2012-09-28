package albin.oredev2012;

import android.app.Activity;

import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EActivity;

@EActivity(R.layout.activity_launch)
public class LaunchActivity extends Activity {

	
	@Click(R.id.speakers_button)
	public void openSpeakers() {
		SpeakerListActivity_.intent(this).start();
	}
	
	@Click(R.id.sessions_button)
	public void openSessions() {
		SessionListActivity_.intent(this).start();
	}
    
}
