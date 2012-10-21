package albin.oredev2012.fragment;

import java.util.List;

import org.joda.time.LocalDate;

import albin.oredev2012.R;
import albin.oredev2012.model.Session;
import albin.oredev2012.model.SessionsByDateCollection;
import albin.oredev2012.repo.Repository;
import albin.oredev2012.ui.SessionAdapter;
import albin.oredev2012.util.Logg;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EFragment;
import com.googlecode.androidannotations.annotations.ViewById;

@EFragment(R.layout.session_list_fragment)
public class SessionListFragment extends Fragment {

	public static final String ARG_SESSION_DATE = "session_id";

	@Bean
	protected Repository repo;

	@ViewById
	protected ExpandableListView list;

	protected SessionAdapter adapter;

	private ItemOpener opener;

	public SessionListFragment() {
		setRetainInstance(true);
	}

	public void setItemOpener(ItemOpener opener) {
		this.opener = opener;
	}

	@AfterViews
	public void init() {
		LocalDate sessionDate = (LocalDate) getArguments()
				.get(ARG_SESSION_DATE);
		Logg.d("Showing session list for " + sessionDate);
		List<Session> allSessions = repo.getSessions();
		SessionsByDateCollection sessionsByDate = new SessionsByDateCollection(
				allSessions);
		List<Session> sessions = sessionsByDate.getSessions(sessionDate);
		adapter = new SessionAdapter(getActivity(), sessions);
		list.setAdapter(adapter);
		list.setOnChildClickListener(new SessionClickListener());
	}

	private class SessionClickListener implements OnChildClickListener {

		@Override
		public boolean onChildClick(ExpandableListView parent, View v,
				int groupPosition, int childPosition, long id) {
			if (opener != null) {
				Session session = adapter.getSession(groupPosition,
						childPosition);
				opener.openItem(session);
				return true;
			}
			return false;
		}

	}

}
