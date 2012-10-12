package albin.oredev2012.ui;

import java.util.List;

import albin.oredev2012.model.Session;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class SessionAdapter extends BaseAdapter {

	private Context context;

	private List<Session> sessions;
	
	public SessionAdapter(Context context, List<Session> sessions) {
		this.context = context;
		this.sessions = sessions;
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
