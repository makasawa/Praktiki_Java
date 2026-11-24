package ru.practice.lab6;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends Activity {
    private EditText[] fields;
    @Override public void onCreate(Bundle state) {
        super.onCreate(state);
        setContentView(R.layout.activity_main);
        fields = new EditText[]{findViewById(R.id.etlength), findViewById(R.id.etwidth), findViewById(R.id.etheight)};
        for (EditText field : fields) {
            field.setOnFocusChangeListener((view, focused) -> {
                if (!focused) read(field);
            });
        }
        findViewById(R.id.btnCalculate).setOnClickListener(v -> calculate(false));
        findViewById(R.id.btnNext).setOnClickListener(v -> calculate(true));
    }

    private double read(EditText field) {
        try {
            double value = Double.parseDouble(field.getText().toString().trim().replace(',', '.'));
            if (Double.isFinite(value) && value > 0) {
                field.setError(null);
                return value;
            }
        } catch (NumberFormatException ignored) { }
        field.setError(getString(R.string.invalid));
        return Double.NaN;
    }

    private void calculate(boolean next) {
        double area = 2 * (read(fields[0]) + read(fields[1])) * read(fields[2]);
        if (!Double.isFinite(area)) {
            Snackbar.make(fields[0], R.string.invalid, Snackbar.LENGTH_SHORT).show();
            return;
        }
        double litres = area * 0.1;
        String[] units = getResources().getStringArray(R.array.units);
        ((TextView)findViewById(R.id.tvResult)).setText(getString(R.string.result, area, units[0], litres, units[1]));
        if (next) startActivity(new Intent(this, SecondActivity.class).putExtra("area", area).putExtra("litres", litres));
    }
}
