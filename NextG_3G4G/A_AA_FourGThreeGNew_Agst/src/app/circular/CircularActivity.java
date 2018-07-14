
package app.circular;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import app.circular.CircleDisplay.SelectionListener;

import com.appnextg.fourg.R;

public class CircularActivity extends Activity implements SelectionListener {

    private CircleDisplay mCircleDisplay;
    TextView feature1, feature2, feature3, feature4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.circularmain);

        mCircleDisplay = (CircleDisplay) findViewById(R.id.circleDisplay);

        mCircleDisplay.setAnimDuration(4000);
        mCircleDisplay.setValueWidthPercent(55f);
        mCircleDisplay.setFormatDigits(1);
        mCircleDisplay.setDimAlpha(80);
        mCircleDisplay.setSelectionListener(this);
        mCircleDisplay.setTouchEnabled(false);
        mCircleDisplay.setUnit("%");
        mCircleDisplay.setStepSize(0.9f);
        mCircleDisplay.showValue(100f, 100f, true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.circularmain, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // if(item.getItemId() == R.id.refresh) mCircleDisplay.showValue((float) (Math.random() * 1000f), 1000f, true);
        if (item.getItemId() == R.id.github) {
            finish();
        }
        return true;
    }

    @Override
    public void onSelectionUpdate(float val, float maxval) {
        Log.i("Main", "Selection update: " + val + ", max: " + maxval);
    }


    public void onValueSelected(float val, float maxval) {
        Log.i("Main", "Selection complete: " + val + ", max: " + maxval);
    }

    private Context getContext() {
        return CircularActivity.this;
    }
}
