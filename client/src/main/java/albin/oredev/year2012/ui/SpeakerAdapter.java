package albin.oredev.year2012.ui;

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

import com.googlecode.androidannotations.annotations.AfterInject;
import com.googlecode.androidannotations.annotations.Background;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EBean;
import com.googlecode.androidannotations.annotations.RootContext;
import com.googlecode.androidannotations.annotations.UiThread;

@EBean
public class SpeakerAdapter extends BaseAdapter implements
		OnImageLoadedListener {

	@Bean
	protected Repository repo;

	@Bean
	protected ImageCache imageCache;

	@RootContext
	protected Context context;

	private List<Speaker> speakers = Collections.emptyList();

	private boolean loadImages = true;

	@AfterInject
	protected void init() {
		loadSpeakers();
	}

	@Background
	protected void loadSpeakers() {
		List<Speaker> newSpeakers = repo.getSpeakers();
		updateSpeakers(newSpeakers);
	}

	@UiThread
	protected void updateSpeakers(List<Speaker> newSpeakers) {
		speakers = newSpeakers;
		notifyDataSetChanged();
	}

	public void loadImages(boolean loadImages) {
		this.loadImages  = loadImages;
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
			view =  (SpeakerListItemView) convertView;
		} else {
			view = SpeakerListItemView_.build(context);
		}
		Speaker speaker = speakers.get(position);
		Bitmap bitmap = imageCache.getImage(speaker.getImageUrl(), loadImages, this);
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

}
