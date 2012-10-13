package albin.oredev2012;

import java.util.List;

import albin.oredev2012.model.Session;
import albin.oredev2012.model.SessionsByDateCollection;
import albin.oredev2012.repo.Repository;
import android.annotation.SuppressLint;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;

import com.googlecode.androidannotations.annotations.AfterInject;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Background;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.OptionsItem;
import com.googlecode.androidannotations.annotations.UiThread;
import com.googlecode.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_session_list)
public class SessionListActivity extends FragmentActivity {
	
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
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}
	

	@Background
	protected void getSessions() {
		List<Session> sessionList = repo.getSessions();
		sessions = new SessionsByDateCollection(sessionList);
		initPager();
	}
	
	@UiThread
	protected void initPager() {
		setTitle(sessions.getDates().get(0));
		SessionListPagerAdapter adapter = new SessionListPagerAdapter(getSupportFragmentManager(), sessions);
		sessionListPager.setAdapter(adapter);
		sessionListPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int position) {
				setTitle(sessions.getDates().get(position));
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


	@OptionsItem(android.R.id.home)
	public void goBack() {
		finish();
	}
	
	protected static class SessionListPagerAdapter extends FragmentPagerAdapter {

		private final SessionsByDateCollection sessions;

		public SessionListPagerAdapter(FragmentManager fm, SessionsByDateCollection sessions) {
			super(fm);
			this.sessions = sessions;
		}

		@Override
		public Fragment getItem(int position) {
			String date = sessions.getDates().get(position);
			SessionListFragment fragment = new SessionListFragment(date, sessions.getSessions(date));
			return fragment;
		}

		@Override
		public int getCount() {
			return sessions.getDates().size();
		}
		
	}

}
