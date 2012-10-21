package albin.oredev2012.ui;

import org.joda.time.LocalTime;

import albin.oredev2012.R;
import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.googlecode.androidannotations.annotations.EViewGroup;
import com.googlecode.androidannotations.annotations.ViewById;

@EViewGroup(R.layout.session_list_time_item)
public class SessionListTimeItemView extends LinearLayout {

	@ViewById
	protected View content;

	@ViewById
	protected TextView time;

	public SessionListTimeItemView(Context context) {
		super(context);
	}

	public void bind(LocalTime time, boolean isExpanded) {
		this.time.setText(time.toString("HH:mm"));
		if (isExpanded) {
			content.setBackgroundResource(R.drawable.list_group_header_expanded_bg);
		} else {
			content.setBackgroundResource(R.drawable.list_group_header_collapsed_bg);
		}
	}

}
