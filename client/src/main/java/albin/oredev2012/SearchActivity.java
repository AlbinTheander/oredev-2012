package albin.oredev2012;

import albin.oredev2012.fragment.ItemOpener;
import albin.oredev2012.fragment.SearchFragment;
import albin.oredev2012.fragment.SessionDetailFragment;
import albin.oredev2012.fragment.SpeakerDetailFragment;
import albin.oredev2012.model.Item;
import albin.oredev2012.model.Session;
import albin.oredev2012.model.Speaker;
import android.annotation.SuppressLint;
import android.os.Build;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.ViewGroup;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.FragmentById;
import com.googlecode.androidannotations.annotations.OptionsItem;
import com.googlecode.androidannotations.annotations.ViewById;

@EActivity(R.layout.search_activity)
public class SearchActivity extends FragmentActivity implements ItemOpener {

	@FragmentById
	protected SearchFragment searchFragment;

	@FragmentById
	protected SessionDetailFragment sessionDetailFragment;

	@FragmentById
	protected SpeakerDetailFragment speakerDetailFragment;

	@ViewById
	protected ViewGroup detailHolder;

	@AfterViews
	protected void init() {
		searchFragment.setItemOpener(this);
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.hide(sessionDetailFragment);
		ft.hide(speakerDetailFragment);
		ft.commit();
	}

	@SuppressLint("NewApi")
	@AfterViews
	protected void initViews() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@OptionsItem(android.R.id.home)
	public void goHome() {
		finish();
	}

	@Override
	public void openItem(Item item) {
		if (detailHolder == null) {
			openInActivity(item);
		} else {
			openInFragment(item);
		}
	}

	private void openInFragment(Item item) {
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		if (item instanceof Speaker) {
			ft.hide(sessionDetailFragment);
			ft.show(speakerDetailFragment);
			speakerDetailFragment.setSpeakerId(item.getId());
		} else if (item instanceof Session) {
			ft.hide(speakerDetailFragment);
			ft.show(sessionDetailFragment);
			sessionDetailFragment.setSessionId(item.getId());
		}
		ft.commit();
	}

	private void openInActivity(Item item) {
		if (item instanceof Session) {
			SessionDetailActivity_.intent(this).sessionId(item.getId()).start();
		} else if (item instanceof Speaker) {
			SpeakerDetailActivity_.intent(this).speakerId(item.getId()).start();
		}
	}

}
