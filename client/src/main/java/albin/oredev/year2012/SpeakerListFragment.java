package albin.oredev.year2012;

import albin.oredev.year2012.model.Speaker;
import albin.oredev.year2012.ui.SpeakerAdapter;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;

import com.googlecode.androidannotations.annotations.AfterInject;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EFragment;
import com.googlecode.androidannotations.annotations.ViewById;

@EFragment(R.layout.fragment_speaker_list)
public class SpeakerListFragment extends ListFragment {

	@Bean
	protected SpeakerAdapter adapter;

	@ViewById(android.R.id.list)
	protected ListView list;

	@AfterInject
	protected void init() {
		setListAdapter(adapter);
	}

	@AfterViews
	protected void initViews() {
		list.setOnScrollListener(adapter);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		Speaker speaker = adapter.getSpeaker((int) adapter.getItemId(position));
		((SpeakerListActivity) getActivity()).openDetails(speaker);
	}

}
