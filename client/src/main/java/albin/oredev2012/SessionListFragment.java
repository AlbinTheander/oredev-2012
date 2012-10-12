package albin.oredev2012;

import albin.oredev2012.model.Session;
import albin.oredev2012.ui.SessionAdapter;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.Toast;

import com.googlecode.androidannotations.annotations.AfterInject;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EFragment;
import com.googlecode.androidannotations.annotations.ItemClick;
import com.googlecode.androidannotations.annotations.ViewById;

@EFragment(R.layout.fragment_session_list)
public class SessionListFragment extends Fragment {

	@Bean
	protected SessionAdapter adapter;

	@ViewById(android.R.id.list)
	protected ExpandableListView list;

	@AfterInject
	protected void init() {
		initWhenDone();
	}
	
	@AfterViews
	protected void initViews() {
		initWhenDone();
	}

	private void initWhenDone() {
		if (list == null || adapter == null)
			return;
		list.setAdapter(adapter);
		list.setOnChildClickListener(new OnChildClickListener() {
			
			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				Session session = (Session) adapter.getChild(groupPosition, childPosition);
				SessionDetailActivity_.intent(getActivity()).sessionId(session.getId()).start();
				return true;
			}
		});
	}
	
	@ItemClick(android.R.id.list)
	protected void onItemClicked(Object item) {
		Toast.makeText(getActivity(), "Clicked on " + item, Toast.LENGTH_SHORT).show();
	}



}
