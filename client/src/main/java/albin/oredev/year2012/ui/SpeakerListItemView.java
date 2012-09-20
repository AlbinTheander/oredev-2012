package albin.oredev.year2012.ui;

import albin.oredev.year2012.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.googlecode.androidannotations.annotations.EViewGroup;
import com.googlecode.androidannotations.annotations.ViewById;

@EViewGroup(R.layout.speaker_list_item)
public class SpeakerListItemView extends LinearLayout {
	
	@ViewById
	protected TextView name;
	
	@ViewById
	protected ImageView image;

	public SpeakerListItemView(Context context) {
		super(context);
	}
	
	public void bind(String name, Bitmap image) {
		this.name.setText(name);
		this.image.setImageBitmap(image);
	}
}
