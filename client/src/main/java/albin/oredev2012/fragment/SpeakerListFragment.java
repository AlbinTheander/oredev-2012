package albin.oredev2012.fragment;

import albin.oredev2012.R;
import albin.oredev2012.model.Speaker;
import albin.oredev2012.ui.SpeakerAdapter;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;

import com.googlecode.androidannotations.annotations.AfterInject;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EFragment;
import com.googlecode.androidannotations.annotations.ViewById;

@EFragment(R.layout.speaker_list_fragment)
public class SpeakerListFragment extends ListFragment {

	public interface SpeakerOpener {
		void openSpeaker(Speaker speaker);
	}

	@Bean
	protected SpeakerAdapter adapter;

	@ViewById(android.R.id.list)
	protected ListView list;

	private SpeakerOpener opener;

	public void setSpeakerOpener(SpeakerOpener opener) {
		this.opener = opener;
	}

	@AfterInject
	protected void init() {
		initViews();
	}

	@AfterViews
	protected void initViews() {
		if (list != null && adapter != null) {
			setListAdapter(adapter);
			list.setAdapter(adapter);
			list.setOnScrollListener(adapter);
		}
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		if (opener != null) {
			Speaker speaker = adapter.getSpeaker((int) adapter
					.getItemId(position));
			opener.openSpeaker(speaker);
		}
	}

}
