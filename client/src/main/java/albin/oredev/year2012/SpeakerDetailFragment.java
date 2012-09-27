package albin.oredev.year2012;

import albin.oredev.year2012.imageCache.ImageCache;
import albin.oredev.year2012.imageCache.ImageCache.OnImageLoadedListener;
import albin.oredev.year2012.model.Speaker;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.app.Fragment;
import android.text.Layout;
import android.text.SpannableString;
import android.text.style.LeadingMarginSpan;
import android.util.DisplayMetrics;
import android.util.FloatMath;
import android.view.Display;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.googlecode.androidannotations.annotations.AfterInject;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Background;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EFragment;
import com.googlecode.androidannotations.annotations.UiThread;
import com.googlecode.androidannotations.annotations.ViewById;

@EFragment(R.layout.fragment_speaker_detail)
public class SpeakerDetailFragment extends Fragment {

	@ViewById(R.id.name)
	protected TextView nameView;
	
	@ViewById(R.id.image)
	protected ImageView imageView;

	@ViewById(R.id.biography)
	protected TextView biographyView;

	@Bean
	protected Repository repo;

	@Bean
	protected ImageCache imageCache;

	private String speakerId;

	private Speaker speaker;

	@AfterInject
	protected void afterInject() {
		setRetainInstance(false);
		if (isInitalized())
			initInBackground();
	}

	@AfterViews
	protected void afterViews() {
		if (isInitalized())
			initInBackground();
	}
	
	public void setSpeakerId(String speakerId) {
		this.speakerId = speakerId;
		if (isInitalized())
			initInBackground();
	}

	private boolean isInitalized() {
		return imageCache != null && imageView != null && speakerId != null;
	}

	@Background
	protected void initInBackground() {
		speaker = repo.getSpeaker(speakerId);
		if (speaker == null)
			return;
		Bitmap image = imageCache.getImage(speaker.getImageUrl(), true,
				new OnImageLoadedListener() {

					@Override
					public void onImageLoaded(String url, Bitmap bitmap) {
						setImage(bitmap);
					}
				});
		initViews();
		if (image != null)
			setImage(image);
	}

	@UiThread
	protected void initViews() {
		nameView.setText(speaker.getName());
		wrapTextAroundImage();
	}

	@UiThread
	protected void setImage(Bitmap bitmap) {
		imageView.setImageBitmap(bitmap);
		wrapTextAroundImage();
	}

	private void wrapTextAroundImage() {
		Display display = getActivity().getWindowManager().getDefaultDisplay();
		DisplayMetrics metrics = new DisplayMetrics();
		display.getMetrics(metrics);

		// Get height and width of the image and height of the text line
		ViewGroup.MarginLayoutParams vlp = (MarginLayoutParams) imageView
				.getLayoutParams();
		int height = vlp.height + vlp.topMargin + vlp.bottomMargin;
		int width = vlp.width + vlp.leftMargin + vlp.rightMargin;
		float pixelHeight = height;
		float pixelTextLineHeight = biographyView.getLineHeight();
		// Set the span according to the number of lines and width of the image
		int lines = (int) FloatMath.ceil(pixelHeight / pixelTextLineHeight);
		SpannableString ss = new SpannableString(speaker.getBiograhpy());
		// For an html text you can use this line: SpannableStringBuilder ss =
		// (SpannableStringBuilder)Html.fromHtml(text);
		ss.setSpan(new MyLeadingMarginSpan2(lines, width), 0, ss.length(), 0);
		biographyView.setText(ss);

		// Align the text with the image by removing the rule that the text is
		// to the right of the image
		RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) biographyView
				.getLayoutParams();
		int[] rules = params.getRules();
		rules[RelativeLayout.RIGHT_OF] = 0;
	}

	class MyLeadingMarginSpan2 implements LeadingMarginSpan.LeadingMarginSpan2 {
		private int margin;
		private int lines;

		MyLeadingMarginSpan2(int lines, int margin) {
			this.margin = margin;
			this.lines = lines;
		}

		/* Возвращает значение, на которе должен быть добавлен отступ */
		@Override
		public int getLeadingMargin(boolean first) {
			if (first) {
				/*
				 * Данный отступ будет применен к количеству строк возвращаемых
				 * getLeadingMarginLineCount()
				 */
				return margin;
			} else {
				// Отступ для всех остальных строк
				return 0;
			}
		}

		@Override
		public void drawLeadingMargin(Canvas c, Paint p, int x, int dir,
				int top, int baseline, int bottom, CharSequence text,
				int start, int end, boolean first, Layout layout) {
		}

		/*
		 * Возвращает количество строк, к которым должен быть применен отступ
		 * возвращаемый методом getLeadingMargin(true) Замечание: Отступ
		 * применяется только к N строкам первого параграфа.
		 */
		@Override
		public int getLeadingMarginLineCount() {
			return lines;
		}
	};
}
