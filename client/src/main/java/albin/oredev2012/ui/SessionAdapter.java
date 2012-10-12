package albin.oredev2012.ui;

import java.util.List;

import albin.oredev2012.model.Session;
import albin.oredev2012.model.SessionsByDateCollection;
import albin.oredev2012.repo.Repository;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

import com.googlecode.androidannotations.annotations.AfterInject;
import com.googlecode.androidannotations.annotations.Background;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EBean;
import com.googlecode.androidannotations.annotations.RootContext;
import com.googlecode.androidannotations.annotations.UiThread;

@EBean
public class SessionAdapter extends BaseExpandableListAdapter {

	@Bean
	protected Repository repo;

	@RootContext
	protected Context context;

	private SessionsByDateCollection sessions = new SessionsByDateCollection();

	@AfterInject
	protected void init() {
		loadSessions();
	}

	@Background
	protected void loadSessions() {
		List<Session> newSessions = repo.getSessions();
		SessionsByDateCollection sessions = new SessionsByDateCollection(
				newSessions);
		updateSessions(sessions);
	}

	@UiThread
	protected void updateSessions(SessionsByDateCollection newSessions) {
		sessions = newSessions;
		notifyDataSetChanged();
	}

	@UiThread
	protected void fireUpdate() {
		notifyDataSetChanged();
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		String date = sessions.getDates().get(groupPosition);
		return sessions.getSessions(date).get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		SessionListItemView view;

		if (convertView instanceof SessionListItemView) {
			view = (SessionListItemView) convertView;
		} else {
			view = SessionListItemView_.build(context);
		}
		Session session = (Session) getChild(groupPosition, childPosition);
		view.bind(session);
		return view;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		String date = sessions.getDates().get(groupPosition);
		return sessions.getSessions(date).size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return sessions.getDates().get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		return sessions.getDates().size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		SessionListHeaderItemView view;
		if (convertView instanceof SessionListItemView) {
			view = (SessionListHeaderItemView) convertView;
		} else {
			view = SessionListHeaderItemView_.build(context);
		}
		view.bind(sessions.getDates().get(groupPosition));
		return view;
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

}
