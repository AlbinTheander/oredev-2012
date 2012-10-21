package albin.oredev2012.ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import albin.oredev2012.imageCache.ImageCache;
import albin.oredev2012.imageCache.ImageCache.OnImageLoadedListener;
import albin.oredev2012.model.Item;
import albin.oredev2012.model.Session;
import albin.oredev2012.model.Speaker;
import albin.oredev2012.repo.Repository;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;

import com.googlecode.androidannotations.annotations.AfterInject;
import com.googlecode.androidannotations.annotations.Background;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EBean;
import com.googlecode.androidannotations.annotations.RootContext;
import com.googlecode.androidannotations.annotations.UiThread;

@EBean
public class SearchAdapter extends BaseAdapter implements Filterable {

	@Bean
	protected Repository repo;

	@RootContext
	protected Context context;

	@Bean
	protected ImageCache imageCache;

	private List<Item> activeItems = Collections.emptyList();
	private List<Item> allItems = null;

	private Filter filter;

	private final OnImageLoadedListener refreshListOnImageLoaded = new OnImageLoadedListener() {

		@Override
		public void onImageLoaded(String url, Bitmap bitmap) {
			notifyDataSetChanged();
		}
	};

	@AfterInject
	protected void init() {
		initInBackground();
	}

	@Background
	protected void initInBackground() {
		List<Session> sessions = repo.getSessions();
		List<Speaker> speakers = repo.getSpeakers();
		List<Item> items = new ArrayList<Item>(sessions);
		items.addAll(speakers);
		finishInit(items);
	}

	@UiThread
	protected void finishInit(List<Item> items) {
		allItems = items;
	}

	@Override
	@UiThread
	public void notifyDataSetChanged() {
		super.notifyDataSetChanged();
	};

	@Override
	public int getViewTypeCount() {
		return 2;
	}

	@Override
	public int getItemViewType(int position) {
		if (activeItems.get(position) instanceof Session) {
			return 0;
		} else {
			return 1;
		}
	}

	@Override
	public int getCount() {
		return activeItems.size();
	}

	@Override
	public Object getItem(int position) {
		return activeItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = null;
		Object item = getItem(position);
		if (item instanceof Session) {
			Session session = (Session) item;
			view = getSessionView(convertView, session);
		} else if (item instanceof Speaker) {
			Speaker speaker = (Speaker) item;
			view = getSpeakerView(convertView, speaker);
		}
		return view;
	}

	private View getSpeakerView(View convertView, Speaker speaker) {
		SpeakerListItemView speakerView;
		if (convertView == null) {
			speakerView = SpeakerListItemView_.build(context);
		} else {
			speakerView = (SpeakerListItemView) convertView;
		}
		Bitmap speakerImage = imageCache.getImage(speaker.getImageUrl(), true,
				refreshListOnImageLoaded);
		speakerView.bind(speaker.getName(), speakerImage);
		return speakerView;
	}

	private View getSessionView(View convertView, Session session) {
		SessionListItemView sessionView;
		if (convertView == null) {
			sessionView = SessionListItemView_.build(context);
		} else {
			sessionView = (SessionListItemView) convertView;
		}
		sessionView.bind(session);
		return sessionView;
	}

	@Override
	public Filter getFilter() {
		if (filter == null) {
			filter = new SearchFilter();
		}
		return filter;
	}

	private class SearchFilter extends Filter {

		private final FilterResults EMPTY_RESULT = new FilterResults();
		{
			EMPTY_RESULT.count = 0;
		}

		@Override
		protected FilterResults performFiltering(CharSequence constraint) {
			if (allItems == null || constraint == null
					|| constraint.length() < 3) {
				return EMPTY_RESULT;
			}
			List<Item> items = new ArrayList<Item>();
			for (Item item : allItems) {
				if (item.contains(constraint)) {
					items.add(item);
				}
			}
			FilterResults result = new FilterResults();
			result.values = items;
			result.count = items.size();
			return result;
		}

		@SuppressWarnings("unchecked")
		@Override
		protected void publishResults(CharSequence constraint,
				FilterResults results) {
			if (results.count == 0) {
				activeItems = Collections.emptyList();
			} else {
				List<Item> items = (List<Item>) results.values;
				activeItems = items;
			}
			notifyDataSetInvalidated();
		}

	}

}
