package albin.oredev2012;

import albin.oredev2012.fragment.SessionDetailFragment;
import albin.oredev2012.fragment.SessionListFragment;
import albin.oredev2012.fragment.SessionListFragment.SessionOpener;
import albin.oredev2012.fragment.SessionListPagerFragment;
import albin.oredev2012.model.Session;
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

@EActivity(R.layout.activity_session_list)
public class SessionListActivity extends FragmentActivity implements
		SessionOpener {

	@FragmentById
	protected SessionListPagerFragment sessionListPagerFragment;

	@FragmentById
	protected SessionDetailFragment sessionDetailFragment;

	@ViewById(R.id.session_detail_fragment)
	protected View sessionDetailFragmentView;

	@SuppressLint("NewApi")
	@AfterViews
	protected void initViews() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}
	
	@Override
	public void onAttachFragment(Fragment fragment) {
		if (fragment instanceof SessionListFragment) {
			SessionListFragment frag = (SessionListFragment) fragment;
			frag.setSessionOpener(this);
		}
		super.onAttachFragment(fragment);
	}

	@OptionsItem(android.R.id.home)
	public void goBack() {
		finish();
	}

	@Override
	public void openSession(Session session) {
		if (sessionDetailFragmentView != null) {
			sessionDetailFragment.setSessionId(session.getId());
		} else {
			SessionDetailActivity_.intent(this).sessionId(session.getId())
					.start();
		}
	}

}
