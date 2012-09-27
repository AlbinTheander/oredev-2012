package albin.oredev.year2012;

import android.support.v4.app.FragmentActivity;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.Extra;
import com.googlecode.androidannotations.annotations.FragmentById;

@EActivity(R.layout.activity_speaker_detail)
public class SpeakerDetailActivity extends FragmentActivity {

	@Extra
	protected String speakerId;
	
	@FragmentById
	protected SpeakerDetailFragment speakerDetailFragment;
	
	
	@AfterViews
	protected void initViews() {
		speakerDetailFragment.setSpeakerId(speakerId);
	}
	
}
