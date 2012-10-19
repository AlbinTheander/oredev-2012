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
import android.view.View;
import android.widget.TextView;

import com.googlecode.androidannotations.annotations.AfterInject;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Background;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EFragment;
import com.googlecode.androidannotations.annotations.UiThread;
import com.googlecode.androidannotations.annotations.ViewById;

@EFragment(R.layout.session_list_pager_fragment)
public class SessionListPagerFragment extends Fragment {

	@ViewById
	protected ViewPager sessionListPager;

	@ViewById
	protected View yesterday;

	@ViewById
	protected TextView today;

	@ViewById
	protected View tomorrow;

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

	@Click(R.id.yesterday)
	public void goToYesterday() {
		switchDay(-1);
	}

	@Click(R.id.tomorrow)
	public void goToTomorrow() {
		switchDay(1);
	}

	private void switchDay(int i) {
		int currentIndex = sessionListPager.getCurrentItem();
		sessionListPager.setCurrentItem(currentIndex + i, true);
	}

	@UiThread
	protected void initPager() {
		setActivityTitle(getActivity(), sessions.getDates().get(0));
		SessionListPagerAdapter adapter = new SessionListPagerAdapter(
				getActivity(), sessions);
		sessionListPager.setAdapter(adapter);
		updateDays();
		sessionListPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				LocalDate date = sessions.getDates().get(position);
				setActivityTitle(getActivity(), date);
				updateDays();
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

	private void updateDays() {
		int index = sessionListPager.getCurrentItem();
		int maxIndex = sessionListPager.getAdapter().getCount() - 1;
		if (index == 0) {
			yesterday.setVisibility(View.INVISIBLE);
		} else {
			yesterday.setVisibility(View.VISIBLE);
		}
		if (index == maxIndex) {
			tomorrow.setVisibility(View.INVISIBLE);
		} else {
			tomorrow.setVisibility(View.VISIBLE);
		}
		today.setText(StringUtils.capitalize(sessions.getDates().get(index).dayOfWeek().getAsText()));

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
