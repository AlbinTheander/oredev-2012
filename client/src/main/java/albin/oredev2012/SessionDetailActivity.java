package albin.oredev2012;

import android.support.v4.app.FragmentActivity;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.Extra;
import com.googlecode.androidannotations.annotations.FragmentById;

@EActivity(R.layout.activity_session_detail)
public class SessionDetailActivity extends FragmentActivity {
	
	@Extra
	protected String sessionId;

    @FragmentById
    protected SessionDetailFragment sessionDetailFragment;
    
    @AfterViews
    protected void init() {
    	sessionDetailFragment.setSessionId(sessionId);
    }
}
