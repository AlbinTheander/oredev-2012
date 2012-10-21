package albin.oredev2012.fragment;

import albin.oredev2012.R;
import albin.oredev2012.model.Speaker;
import albin.oredev2012.ui.SpeakerAdapter;
import android.support.v4.app.Fragment;
import android.widget.ListView;

import com.googlecode.androidannotations.annotations.AfterInject;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EFragment;
import com.googlecode.androidannotations.annotations.ItemClick;
import com.googlecode.androidannotations.annotations.ViewById;

@EFragment(R.layout.speaker_list_fragment)
public class SpeakerListFragment extends Fragment {

	@ViewById
	protected ListView list;

	@Bean
	protected SpeakerAdapter adapter;

	private ItemOpener opener;

	public void setItemOpener(ItemOpener opener) {
		this.opener = opener;
	}

	@AfterInject
	@AfterViews
	protected void init() {
		if (list != null && adapter != null) {
			list.setAdapter(adapter);
			list.setOnScrollListener(adapter);
		}
	}

	@ItemClick(R.id.list)
	public void onListItemClick(Speaker speaker) {
		if (opener != null) {
			opener.openItem(speaker);
		}
	}

}
