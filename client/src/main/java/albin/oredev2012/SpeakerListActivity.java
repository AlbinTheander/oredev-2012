package albin.oredev2012;

import albin.oredev2012.fragment.ItemOpener;
import albin.oredev2012.fragment.SpeakerDetailFragment;
import albin.oredev2012.fragment.SpeakerListFragment;
import albin.oredev2012.model.Item;
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
public class SpeakerListActivity extends FragmentActivity implements ItemOpener {

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
			frag.setItemOpener(this);
		}
		super.onAttachFragment(fragment);
	}

	@Override
	public void openItem(Item item) {
		if (speakerDetailView != null) {
			speakerDetailFragment.setSpeakerId(item.getId());
		} else {
			SpeakerDetailActivity_.intent(this).speakerId(item.getId()).start();
		}
	}
}
