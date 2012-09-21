package albin.oredev.year2012.ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import albin.oredev.year2012.Repository;
import albin.oredev.year2012.server.model.Speaker;
import albin.oredev.year2012.ui.ImageCache.OnImageLoadedListener;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
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
		OnImageLoadedListener, SectionIndexer {

	@Bean
	protected Repository repo;

	@Bean
	protected ImageCache imageCache;

	@RootContext
	protected Context context;

	private List<Speaker> speakers = Collections.emptyList();

	private boolean loadImages = true;

	private Section[] sections = new Section[0];

	@AfterInject
	protected void init() {
		loadSpeakers();
	}

	@Background
	protected void loadSpeakers() {
		List<Speaker> newSpeakers = repo.getSpeakers();
		Section[] sections = buildSections(newSpeakers);
		updateSpeakers(newSpeakers, sections);

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
	public void loadImages(boolean loadImages) {
		this.loadImages = loadImages;
		if (loadImages)
			notifyDataSetChanged();
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
		Bitmap bitmap = imageCache.getImage(speaker.getImageUrl(), loadImages,
				this);
		view.bind(speaker.getName(), bitmap);
		return view;
	}

	@Override
	public void onImageLoaded(String url, Bitmap bitmap) {
		fireUpdate();
	}

	@UiThread
	protected void fireUpdate() {
		notifyDataSetChanged();
	}

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
				return i == 0 ? 0 : i-1;
		}
		return sections.length-1;
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

}
