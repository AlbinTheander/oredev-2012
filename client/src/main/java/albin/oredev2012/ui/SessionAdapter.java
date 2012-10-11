package albin.oredev2012.ui;

import java.util.Collections;
import java.util.List;

import albin.oredev2012.model.Session;
import albin.oredev2012.repo.Repository;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.googlecode.androidannotations.annotations.AfterInject;
import com.googlecode.androidannotations.annotations.Background;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EBean;
import com.googlecode.androidannotations.annotations.RootContext;
import com.googlecode.androidannotations.annotations.UiThread;

@EBean
public class SessionAdapter extends BaseAdapter  {

	@Bean
	protected Repository repo;
	
	@RootContext
	protected Context context;

	private List<Session> sessions = Collections.emptyList();

	@AfterInject
	protected void init() {
		loadSesions();
	}

	@Background
	protected void loadSesions() {
		List<Session> newSessions = repo.getSessions();
		updateSessions(newSessions);
	}

	@UiThread
	protected void updateSessions(List<Session> newSessions) {
			sessions = newSessions;
			notifyDataSetChanged();
	}

	@UiThread
	protected void fireUpdate() {
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return sessions.size();
	}
	
	public Session getSession(int id) {
		return sessions.get(id);
	}

	@Override
	public Object getItem(int id) {
		return getSession(id);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		SessionListItemView view;

		if (convertView instanceof SessionListItemView) {
			view = (SessionListItemView) convertView;
		} else {
			view = SessionListItemView_.build(context);
		}
		Session session = sessions.get(position);
		view.bind(session);
		return view;
	}

}
