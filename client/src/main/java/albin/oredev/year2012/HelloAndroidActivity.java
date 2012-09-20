package albin.oredev.year2012;

import albin.oredev.year2012.server.model.Speaker;
import albin.oredev.year2012.ui.SpeakerAdapter;
import android.app.ListActivity;
import android.widget.Toast;

import com.googlecode.androidannotations.annotations.AfterInject;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ItemClick;

@EActivity(R.layout.main)
public class HelloAndroidActivity extends ListActivity {

	@Bean
	protected SpeakerAdapter adapter;

	@AfterInject
	protected void init() {
		setListAdapter(adapter);
	}
	
	@ItemClick(android.R.id.list)
	protected void openSpeaker(Speaker speaker) {
		Toast.makeText(this, speaker.getName(), Toast.LENGTH_SHORT).show();
	}

}

