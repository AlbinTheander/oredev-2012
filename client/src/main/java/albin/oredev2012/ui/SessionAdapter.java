package albin.oredev2012.ui;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalTime;

import albin.oredev2012.model.Session;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

public class SessionAdapter extends BaseExpandableListAdapter {

	private Context context;

	private List<List<Session>> sessions;
	
	public SessionAdapter(Context context, List<Session> sessions) {
		this.context = context;
		LocalTime lastTime = null;
		List<Session> currentList = null;
		this.sessions = new ArrayList<List<Session>>();
		for(Session s: sessions) {
			if (!s.getStartTime().equals(lastTime)) {
				lastTime = s.getStartTime();
				currentList = new ArrayList<Session>();
				this.sessions.add(currentList);
			}
			currentList.add(s);
		}
		
	}

	public Session getSession(int groupPosition, int childPosition) {
		return sessions.get(groupPosition).get(childPosition);
	}


	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return sessions.get(groupPosition).get(childPosition);
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
		Session session = sessions.get(groupPosition).get(childPosition);
		view.bind(session);
		return view;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return sessions.get(groupPosition).size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return sessions.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		return sessions.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		SessionListTimeItemView view;
		if (convertView instanceof SessionListTimeItemView) {
			view = (SessionListTimeItemView) convertView;
		} else {
			view = SessionListTimeItemView_.build(context);
		}
		view.bind(sessions.get(groupPosition).get(0).getStartTime());
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
