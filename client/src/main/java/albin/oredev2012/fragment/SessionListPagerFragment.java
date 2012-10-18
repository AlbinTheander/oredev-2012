package albin.oredev2012.fragment;

import java.util.List;

import org.joda.time.LocalDate;
import org.springframework.util.StringUtils;

import albin.oredev2012.R;
import albin.oredev2012.model.Session;
import albin.oredev2012.model.SessionsByDateCollection;
import albin.oredev2012.repo.Repository;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;

import com.googlecode.androidannotations.annotations.AfterInject;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Background;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EFragment;
import com.googlecode.androidannotations.annotations.UiThread;
import com.googlecode.androidannotations.annotations.ViewById;

@EFragment(R.layout.session_list_pager_fragment)
public class SessionListPagerFragment extends Fragment {

	@ViewById
	protected ViewPager sessionListPager;

	@Bean
	protected Repository repo;

	private SessionsByDateCollection sessions;

	@SuppressLint("NewApi")
	@AfterInject
	@AfterViews
	protected void initViews() {
		if (sessionListPager == null || repo == null)
			return;
		getSessions();
	}

	@Background
	protected void getSessions() {
		List<Session> sessionList = repo.getSessions();
		sessions = new SessionsByDateCollection(sessionList);
		initPager();
	}

	@UiThread
	protected void initPager() {
		setActivityTitle(getActivity(), sessions.getDates().get(0));
		SessionListPagerAdapter adapter = new SessionListPagerAdapter(
				getActivity(), sessions);
		sessionListPager.setAdapter(adapter);
		sessionListPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				LocalDate date = sessions.getDates().get(position);
				setActivityTitle(getActivity(), date);
			}

			@Override
			public void onPageScrolled(int position, float positionOffset,
					int positionOffsetPixels) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrollStateChanged(int state) {
				// TODO Auto-generated method stub

			}
		});
	}

	private static void setActivityTitle(Activity activity, LocalDate date) {
		String dayName = StringUtils.capitalize(date.dayOfWeek().getAsText());
		activity.setTitle(dayName);
	}

	private static class SessionListPagerAdapter extends FragmentPagerAdapter {

		private final SessionsByDateCollection sessions;
		private final FragmentActivity activity;

		public SessionListPagerAdapter(FragmentActivity activity,
				SessionsByDateCollection sessions) {
			super(activity.getSupportFragmentManager());
			this.activity = activity;
			this.sessions = sessions;
		}

		@Override
		public Fragment getItem(int position) {
			LocalDate date = sessions.getDates().get(position);
			Bundle args = new Bundle();
			args.putSerializable(SessionListFragment.ARG_SESSION_DATE, date);
			SessionListFragment fragment = (SessionListFragment) SessionListFragment_
					.instantiate(activity,
							SessionListFragment_.class.getName(), args);
			return fragment;
		}

		@Override
		public int getCount() {
			return sessions.getDates().size();
		}

	}

}
