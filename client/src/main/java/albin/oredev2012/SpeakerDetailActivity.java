package albin.oredev2012;

import albin.oredev2012.fragment.SpeakerDetailFragment;
import android.annotation.SuppressLint;
import android.os.Build;
import android.support.v4.app.FragmentActivity;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.Extra;
import com.googlecode.androidannotations.annotations.FragmentById;
import com.googlecode.androidannotations.annotations.OptionsItem;

@EActivity(R.layout.speaker_detail_activity)
public class SpeakerDetailActivity extends FragmentActivity {

	@Extra
	protected String speakerId;

	@FragmentById
	protected SpeakerDetailFragment speakerDetailFragment;

	@SuppressLint("NewApi")
	@AfterViews
	protected void initViews() {
		speakerDetailFragment.setSpeakerId(speakerId);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@OptionsItem(android.R.id.home)
	public void goHome() {
		finish();
	}

}
