package albin.oredev2012;

import albin.oredev2012.fragment.SpeakerDetailFragment;
import albin.oredev2012.fragment.SpeakerListFragment;
import albin.oredev2012.fragment.SpeakerListFragment.SpeakerOpener;
import albin.oredev2012.model.Speaker;
import android.annotation.SuppressLint;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.FragmentById;
import com.googlecode.androidannotations.annotations.OptionsItem;
import com.googlecode.androidannotations.annotations.ViewById;

@EActivity(R.layout.speaker_list_activity)
public class SpeakerListActivity extends FragmentActivity implements
		SpeakerOpener {

	@FragmentById
	protected SpeakerDetailFragment speakerDetailFragment;

	@ViewById(R.id.speaker_detail_fragment)
	protected View speakerDetailView;

	@SuppressLint("NewApi")
	@AfterViews
	protected void init() {
		setTitle(R.string.speakers_title);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@OptionsItem(android.R.id.home)
	public void goHome() {
		finish();
	}
	
	@Override
	public void onAttachFragment(Fragment fragment) {
		if (fragment instanceof SpeakerListFragment) {
			SpeakerListFragment frag = (SpeakerListFragment) fragment;
			frag.setSpeakerOpener(this);
		}
		super.onAttachFragment(fragment);
	}

	public void openSpeaker(Speaker speaker) {
		if (speakerDetailView != null)
			speakerDetailFragment.setSpeakerId(speaker.getId());
		else
			SpeakerDetailActivity_.intent(this).speakerId(speaker.getId())
					.start();
	}
}
