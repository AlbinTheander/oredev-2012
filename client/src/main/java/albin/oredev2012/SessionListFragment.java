package albin.oredev2012;

import java.util.List;

import albin.oredev2012.model.Session;
import albin.oredev2012.ui.SessionAdapter;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class SessionListFragment extends ListFragment {

	protected SessionAdapter adapter;
	private final List<Session> sessions;
	private String date;

	public SessionListFragment(String date, List<Session> sessions) {
		this.date = date;
		this.sessions = sessions;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		adapter = new SessionAdapter(getActivity(), sessions);
		setListAdapter(adapter);
		return super.onCreateView(inflater, container, savedInstanceState);
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		Session session = adapter.getSession((int) adapter.getItemId(position));
		SessionDetailActivity_.intent(getActivity()).sessionId(session.getId()).start();
	}

}
