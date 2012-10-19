package albin.oredev2012.ui;

import albin.oredev2012.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.Button;

public class RotatedTextButton extends Button{

	private int angle;

	public RotatedTextButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(attrs);
	}

	public RotatedTextButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(attrs);
	}


	public RotatedTextButton(Context context) {
		super(context);
	}

	private void init(AttributeSet attrs) {
		TypedArray styles = getContext().obtainStyledAttributes(attrs, R.styleable.rotated_text_button);
		angle = styles.getInteger(R.styleable.rotated_text_button_text_angle, -30);
		styles.recycle();
		
	}

	@Override
	protected void onDraw(Canvas canvas) {
		canvas.save();
		canvas.rotate(angle, canvas.getWidth()/2, canvas.getHeight()/2);
		super.onDraw(canvas);
		canvas.restore();
	}
	
}
