package albin.oredev2012.fragment;

import java.util.List;

import albin.oredev2012.R;
import albin.oredev2012.model.Session;
import albin.oredev2012.ui.SessionAdapter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;

public class SessionListFragment extends Fragment {

	public interface SessionOpener {
		void openSession(Session session);
	}

	protected SessionAdapter adapter;
	private List<Session> sessions;
	private SessionOpener opener;

	public SessionListFragment() {
		setRetainInstance(true);
	}

	public SessionListFragment(String date, List<Session> sessions) {
		this.sessions = sessions;
		setRetainInstance(true);
	}

	public void setSessionOpener(SessionOpener opener) {
		this.opener = opener;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		adapter = new SessionAdapter(getActivity(), sessions);
		View view = inflater.inflate(R.layout.fragment_session_list, null);
		ExpandableListView listView = (ExpandableListView) view
				.findViewById(R.id.list);
		listView.setAdapter(adapter);
		listView.setOnChildClickListener(new SessionClickListener());
		return view;
	}

	private class SessionClickListener implements OnChildClickListener {

		@Override
		public boolean onChildClick(ExpandableListView parent, View v,
				int groupPosition, int childPosition, long id) {
			if (opener != null) {
				Session session = adapter.getSession(groupPosition,
						childPosition);
				opener.openSession(session);
				return true;
			}
			return false;
		}

	}

}
