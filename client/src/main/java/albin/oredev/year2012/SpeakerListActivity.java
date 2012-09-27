package albin.oredev.year2012;

import albin.oredev.year2012.model.Speaker;
import albin.oredev.year2012.ui.SpeakerAdapter;
import android.app.ListActivity;

import com.googlecode.androidannotations.annotations.AfterInject;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ItemClick;

@EActivity(R.layout.activity_speaker_list)
public class SpeakerListActivity extends ListActivity {

	@Bean
	protected SpeakerAdapter adapter;

	@AfterInject
	protected void init() {
		setTitle(R.string.speakers_title);
		setListAdapter(adapter);
	}
	
	@AfterViews
	protected void initViews() {
		getListView().setOnScrollListener(adapter);
	}

	@ItemClick(android.R.id.list)
	protected void openSpeaker(Speaker speaker) {
		SpeakerDetailActivity_.intent(this).speakerId(speaker.getId()).start();
	}

}
