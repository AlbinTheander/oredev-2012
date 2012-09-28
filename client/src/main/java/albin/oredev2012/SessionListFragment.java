package albin.oredev2012;

import albin.oredev2012.model.Session;
import albin.oredev2012.ui.SessionAdapter;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.googlecode.androidannotations.annotations.AfterInject;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EFragment;
import com.googlecode.androidannotations.annotations.ViewById;

@EFragment(R.layout.fragment_session_list)
public class SessionListFragment extends ListFragment {

	@Bean
	protected SessionAdapter adapter;

	@ViewById(android.R.id.list)
	protected ListView list;

	@AfterInject
	protected void init() {
		setListAdapter(adapter);
	}


	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		Session session = adapter.getSession((int) adapter.getItemId(position));
		Toast.makeText(getActivity(), session.getName(), Toast.LENGTH_SHORT).show();
	}

}
