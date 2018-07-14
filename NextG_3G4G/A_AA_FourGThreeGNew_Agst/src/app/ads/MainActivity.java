package app.ads;


import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.appnextg.fourg.R;


public class MainActivity extends AppCompatActivity implements
        View.OnClickListener, StringPickerDialog.OnClickListener {

    private static final String TAG = StringPickerDialog.class.getSimpleName();

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //ds
        setContentView(R.layout.test);
        mTextView = (TextView) findViewById(R.id.text);
        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Bundle bundle = new Bundle();
                bundle.putStringArray(getString(R.string.pindi_string_picker_dialog_title), getStringArray());
                StringPickerDialog dialog = new StringPickerDialog();
                dialog.setArguments(bundle);
                dialog.show(getSupportFragmentManager(), TAG);
            }
        });
    }

    @Override
    public void onClick(View v) {

    }

    public void onClick(String value) {
        mTextView.setText(value);
    }

    private String[] getStringArray() {
        return new String[]{"System Language", "English UK", "English US", "French", "German", "Arebic", "US Eng", "Nepali"};
    }

}