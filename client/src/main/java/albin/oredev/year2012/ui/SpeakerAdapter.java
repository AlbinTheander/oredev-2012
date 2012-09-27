package albin.oredev.year2012.ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import albin.oredev.year2012.Repository;
import albin.oredev.year2012.imageCache.ImageCache;
import albin.oredev.year2012.imageCache.ImageCache.OnImageLoadedListener;
import albin.oredev.year2012.model.Speaker;
import albin.oredev.year2012.util.Gate;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.SectionIndexer;

import com.googlecode.androidannotations.annotations.AfterInject;
import com.googlecode.androidannotations.annotations.Background;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EBean;
import com.googlecode.androidannotations.annotations.RootContext;
import com.googlecode.androidannotations.annotations.UiThread;

@EBean
public class SpeakerAdapter extends BaseAdapter implements
		OnImageLoadedListener, SectionIndexer, OnScrollListener {

	@Bean
	protected Repository repo;

	@Bean
	protected ImageCache imageCache;

	@RootContext
	protected Context context;

	private List<Speaker> speakers = Collections.emptyList();

	private Section[] sections = new Section[0];

	private Gate imageLoadingGate = new Gate();

	@AfterInject
	protected void init() {
		loadSpeakers();
	}

	@Background
	protected void loadSpeakers() {
		List<Speaker> newSpeakers = repo.getSpeakers();
		Section[] sections = buildSections(newSpeakers);
		updateSpeakers(newSpeakers, sections);
		preloadImages(newSpeakers);
	}

	private void preloadImages(List<Speaker> speakers) {
		Iterator<Speaker> iterator = speakers.iterator();
		while (iterator.hasNext()) {
			if (!imageLoadingGate.passThrough(1000)) {
				continue;
			}
			Speaker speaker = iterator.next();
			imageCache.cache(speaker.getImageUrl(), this);
		}
	}

	private Section[] buildSections(List<Speaker> speakers) {
		char lastName = ' ';
		List<Section> sections = new ArrayList<Section>();
		for (int i = 0; i < speakers.size(); i++) {
			Speaker speaker = speakers.get(i);
			char speakerSection = speaker.getName().charAt(0);
			if (speakerSection != lastName) {
				sections.add(new Section(Character.toString(speakerSection), i));
				lastName = speakerSection;
			}
		}
		return sections.toArray(new Section[sections.size()]);
	}

	@UiThread
	protected void updateSpeakers(List<Speaker> newSpeakers,
			Section[] newSections) {
		speakers = newSpeakers;
		sections = newSections;
		notifyDataSetChanged();
	}

	@UiThread
	protected void fireUpdate() {
		notifyDataSetChanged();
	}

	@UiThread
	protected void loadImages(boolean loadImages) {
		if (loadImages && !imageLoadingGate.isOpen()) {
			notifyDataSetChanged();
		}
		if (loadImages) {
			imageLoadingGate.open();
		} else {
			imageLoadingGate.close();
		}
	}

	@Override
	public int getCount() {
		return speakers.size();
	}

	@Override
	public Object getItem(int id) {
		return speakers.get(id);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		SpeakerListItemView view;

		if (convertView instanceof SpeakerListItemView) {
			view = (SpeakerListItemView) convertView;
		} else {
			view = SpeakerListItemView_.build(context);
		}
		Speaker speaker = speakers.get(position);
		Bitmap bitmap = imageCache.getImage(speaker.getImageUrl(),
				imageLoadingGate.isOpen(), this);
		view.bind(speaker.getName(), bitmap);
		return view;
	}

	// ----------------------------------------------------
	// Start of OnImageLoaded implementation
	// ----------------------------------------------------

	@Override
	public void onImageLoaded(String url, Bitmap bitmap) {
		fireUpdate();
	}

	// ----------------------------------------------------
	// End of OnImageLoaded implementation
	// ----------------------------------------------------

	// ----------------------------------------------------
	// Start of AbsListView.OnScrollListener callbacks
	// ----------------------------------------------------

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.AbsListView.OnScrollListener#onScroll(android.widget.
	 * AbsListView, int, int, int)
	 */
	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.widget.AbsListView.OnScrollListener#onScrollStateChanged(android
	 * .widget.AbsListView, int)
	 */
	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		if (scrollState == OnScrollListener.SCROLL_STATE_FLING) {
			loadImages(false);
		} else {
			loadImages(true);
		}
	}

	// ----------------------------------------------------
	// End of AbsListView.OnScrollListener callbacks
	// ----------------------------------------------------

	// ----------------------------------------------------
	// Start of SectionIndexer implementation
	// ----------------------------------------------------

	@Override
	public int getPositionForSection(int section) {
		return sections[section].pos;
	}

	@Override
	public int getSectionForPosition(int position) {
		if (sections.length == 0)
			return 0;
		for (int i = 0; i < sections.length; i++) {
			if (sections[i].pos > position)
				return i == 0 ? 0 : i - 1;
		}
		return sections.length - 1;
	}

	@Override
	public Object[] getSections() {
		return sections;
	}

	protected class Section {
		String name;
		int pos;

		public Section(String name, int pos) {
			this.name = name;
			this.pos = pos;
		}

		@Override
		public String toString() {
			return name;
		}
	}

	// ----------------------------------------------------
	// End of SectionIndexer implementation
	// ----------------------------------------------------

}
