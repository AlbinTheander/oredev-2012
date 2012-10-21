package albin.oredev2012.fragment;

import albin.oredev2012.R;
import albin.oredev2012.model.Item;
import albin.oredev2012.ui.SearchAdapter;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.widget.EditText;
import android.widget.ListView;

import com.googlecode.androidannotations.annotations.AfterInject;
import com.googlecode.androidannotations.annotations.AfterTextChange;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EFragment;
import com.googlecode.androidannotations.annotations.ItemClick;
import com.googlecode.androidannotations.annotations.ViewById;

@EFragment(R.layout.search_fragment)
public class SearchFragment extends Fragment {

	@ViewById
	protected ListView list;

	@ViewById
	protected EditText search;

	@Bean
	protected SearchAdapter adapter;
	
	private ItemOpener opener;
	
	public SearchFragment() {
		setRetainInstance(true);
	}
	
	public void setItemOpener(ItemOpener opener) {
		this.opener = opener;
	}

	@AfterViews
	@AfterInject
	protected void init() {
		if (list != null && adapter != null) {
			list.setAdapter(adapter);
		}
	}

	@AfterTextChange
	protected void search(Editable query) {
		adapter.getFilter().filter(query);
		list.setSelection(0);
	}
	
	@ItemClick(R.id.list)
	protected void onListItemClick(Item item) {
		if (opener != null) {
			opener.openItem(item);
		}
	}
	
}
