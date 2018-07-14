package com.checkspeed;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.view.View;

import com.appnextg.fourg.R;

public class SpeedMeter extends View {
    private Bitmap meter_bg, meter_center_wheels, needle, button_bg,
            speed_text_bg;
    private Paint paint;
    private float max_angle = 0, max_value = 0, min_angle = 0;
    private float angle_of_deviation = 0, scale = 0;
    private String speed = "";

    public SpeedMeter(Context context) {
        super(context);

        paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeCap(Paint.Cap.ROUND);

        scale = getResources().getDisplayMetrics().density;
        paint.setTextSize((int) (25 * scale));

        needle = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.arrows);
        // meter_center_wheel=BitmapFactory.decodeResource(context.getResources(),
        // R.drawable.meter_center_wheel);
        meter_bg = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.version_meter);
        // button_bg=BitmapFactory.decodeResource(context.getResources(),
        // R.drawable.ic_launcher);
        speed_text_bg = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.speed_text_bg);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(meter_bg, getWidth() / 2 - meter_bg.getWidth() / 2,
                getHeight() / 10, null);

        Matrix matrix = new Matrix();
        matrix.setRotate(angle_of_deviation);
        Bitmap rotate = Bitmap.createBitmap(needle, 0, 0, needle.getWidth(),
                needle.getHeight(), matrix, true);
        canvas.drawBitmap(
                rotate,
                getWidth() / 2 - rotate.getWidth() / 2,
                (getHeight() / 18 + meter_bg.getHeight())
                        - rotate.getHeight() / 2, null);

        // canvas.drawBitmap(meter_center_wheel,
        // getWidth()/2-meter_center_wheel.getWidth()/2,
        // (getHeight()/10+meter_bg.getHeight()/2)-meter_center_wheel.getHeight(),
        // null);

        // canvas.drawBitmap(button_bg, getWidth()/2-button_bg.getWidth()/2,
        // (getHeight()/10+meter_bg.getHeight()), null);

        // canvas.drawBitmap(speed_text_bg,
        // getWidth()/2-speed_text_bg.getWidth()/2,
        // (getHeight()/10)+paint.getTextSize()/2, paint);
        canvas.drawText("" + speed,
                getWidth() / 2 - paint.measureText("" + speed) / 2,
                paint.getTextSize(), paint);
    }

    public void calculateAngleOfDeviation(float value, String str) {
        speed = str;
        if (value <= 1024) {
            max_angle = 60;
            max_value = 1024;
            min_angle = max_angle / max_value;
            angle_of_deviation = min_angle * value;
        } else if (value > 1024 && value <= 5120) {
            float remaining_value = value - 1024;
            max_angle = 60;
            max_value = 4096;
            min_angle = max_angle / max_value;
            angle_of_deviation = 60 + (min_angle * remaining_value);
        } else if (value > 5120 && value <= 10240) {
            float remaining_value = value - 5120;
            max_angle = 30;
            max_value = 5120;
            min_angle = max_angle / max_value;
            angle_of_deviation = 120 + (min_angle * remaining_value);
        } else if (value > 10240 && value <= 20480) {
            float remaining_value = value - 10240;
            max_angle = 30;
            max_value = 10240;
            min_angle = max_angle / max_value;
            angle_of_deviation = 150 + (min_angle * remaining_value);
        }
        this.invalidate();
    }
}
