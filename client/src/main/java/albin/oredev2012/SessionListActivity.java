package albin.oredev2012;

import android.os.Build;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.FragmentById;
import com.googlecode.androidannotations.annotations.OptionsItem;

@EActivity(R.layout.activity_session_list)
public class SessionListActivity extends FragmentActivity {

	@FragmentById
	protected SessionListFragment sessionListFragment;

	@AfterViews
	protected void init() {
		setTitle(R.string.sessions_title);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@OptionsItem(android.R.id.home)
	public void goBack() {
		finish();
	}

}
