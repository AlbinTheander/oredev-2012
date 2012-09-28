package albin.oredev2012.ui;

import albin.oredev2012.R;
import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.googlecode.androidannotations.annotations.EViewGroup;
import com.googlecode.androidannotations.annotations.ViewById;

@EViewGroup(R.layout.session_list_item)
public class SessionListItemView extends LinearLayout {
	
	@ViewById
	protected TextView name;
	
	@ViewById
	protected TextView track;

	public SessionListItemView(Context context) {
		super(context);
	}
	
	public void bind(String name, String track) {
		this.name.setText(name);
		this.track.setText(track);
	}
}
