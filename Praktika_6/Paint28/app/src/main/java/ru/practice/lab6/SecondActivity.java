package ru.practice.lab6;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class SecondActivity extends Activity {
    @Override public void onCreate(Bundle state) {
        super.onCreate(state);
        setContentView(R.layout.activity_second);
        String[] units = getResources().getStringArray(R.array.units);
        ((TextView)findViewById(R.id.tvResult)).setText(getString(R.string.result,
                getIntent().getDoubleExtra("area", 0), units[0], getIntent().getDoubleExtra("litres", 0), units[1]));
        findViewById(R.id.btnBack).setOnClickListener(view -> finish());
    }
}
