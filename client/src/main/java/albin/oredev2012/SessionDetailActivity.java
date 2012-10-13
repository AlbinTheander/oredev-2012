package albin.oredev2012;

import android.annotation.SuppressLint;
import android.os.Build;
import android.support.v4.app.FragmentActivity;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.Extra;
import com.googlecode.androidannotations.annotations.FragmentById;
import com.googlecode.androidannotations.annotations.OptionsItem;

@EActivity(R.layout.activity_session_detail)
public class SessionDetailActivity extends FragmentActivity {
	
	@Extra
	protected String sessionId;

    @FragmentById
    protected SessionDetailFragment sessionDetailFragment;
    
    @SuppressLint("NewApi")
	@AfterViews
	protected void initViews() {
		sessionDetailFragment.setSessionId(sessionId);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}
	
	@OptionsItem(android.R.id.home)
	public void goHome() {
		finish();
	}
}
