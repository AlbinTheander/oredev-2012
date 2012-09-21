package albin.oredev.year2012;

import albin.oredev.year2012.server.model.Speaker;
import albin.oredev.year2012.ui.SpeakerAdapter;
import android.app.ListActivity;
import android.util.Log;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.Toast;

import com.googlecode.androidannotations.annotations.AfterInject;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ItemClick;

@EActivity(R.layout.main)
public class HelloAndroidActivity extends ListActivity {

	@Bean
	protected SpeakerAdapter adapter;

	@AfterInject
	protected void init() {
		setListAdapter(adapter);
	}
	
	@AfterViews 
	protected void initViews() {
		getListView().setOnScrollListener(new OnScrollListener() {
			
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				if (scrollState == OnScrollListener.SCROLL_STATE_FLING) {
					Log.d("Oredev", "Stop loading images");
					adapter.loadImages(false);
				} else {
					Log.d("Oredev", "Start loading images");
					adapter.loadImages(true);
				}
			}
			
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				// TODO Auto-generated method stub
				
			}
		});


	}
	
	@ItemClick(android.R.id.list)
	protected void openSpeaker(Speaker speaker) {
		Toast.makeText(this, speaker.getName(), Toast.LENGTH_SHORT).show();
	}

}

