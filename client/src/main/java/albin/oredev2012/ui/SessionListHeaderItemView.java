package albin.oredev2012.ui;

import albin.oredev2012.R;
import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.googlecode.androidannotations.annotations.EViewGroup;
import com.googlecode.androidannotations.annotations.ViewById;

@EViewGroup(R.layout.session_header_list_item)
public class SessionListHeaderItemView extends LinearLayout {

	@ViewById
	protected TextView date;
	
	public SessionListHeaderItemView(Context context) {
		super(context);
	}

	public void bind(String date) {
		this.date.setText(date);
	}

}
