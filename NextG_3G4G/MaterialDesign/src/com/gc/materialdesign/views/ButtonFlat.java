package com.gc.materialdesign.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.TextView;

import com.gc.materialdesign.R;

public class ButtonFlat extends ButtonRectangle {
	
	public ButtonFlat(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	@Override
	protected void onInitDefaultValues(){
		textButton = new TextView(getContext());
		minHeight = 36;
		minWidth = 88;
		rippleSpeed = 100f;
		defaultTextColor =  Color.parseColor("#1E88E5");
		backgroundResId = R.drawable.background_transparent;
		rippleColor = Color.parseColor("#88DDDDDD");
		//setBackgroundResource(R.drawable.background_transparent);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		//super.onDraw(canvas);//ä¸�è°ƒç”¨çˆ¶ç±»çš„onDraw()æ–¹æ³•ã€‚å› ä¸ºè¿™ä¼šç”¨ButtonRectangleçš„onDraw()
		if (x != -1) {
			
			Paint paint = new Paint();
			paint.setAntiAlias(true);
			if (rippleColor == null) {
				paint.setColor(Color.parseColor("#88DDDDDD"));
			}else {
				paint.setColor(rippleColor);
			}
			canvas.drawCircle(x, y, radius, paint);
			if(radius > getHeight()/rippleSize)
				radius += rippleSpeed;
			if(radius >= getWidth()){
				x = -1;
				y = -1;
				radius = getHeight()/rippleSize;
				if (isEnabled() && clickAfterRipple == true && onClickListener != null) {
					onClickListener.onClick(this);
				}
			}
		}		
		invalidate();
	}
	
	@Override
	public void setBackgroundColor(int color) {
		super.setBackgroundColor(color);
		if (!settedRippleColor) {
			// å¦‚æžœä¹‹å‰�æ²¡æœ‰è®¾ç½®è¿‡æ¶Ÿæ¼ªé¢œè‰²ï¼Œé‚£ä¹ˆå°±ç”¨é»˜è®¤çš„
			rippleColor = Color.parseColor("#88DDDDDD");
		}
	}
	
	
}
