package com.example.metricconverter;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.android.material.textfield.TextInputLayout;

public class MainActivity extends AppCompatActivity {

    private AutoCompleteTextView categoryAutoCompleteTextView;
    private AutoCompleteTextView fromUnitAutoCompleteTextView;
    private AutoCompleteTextView toUnitAutoCompleteTextView;
    private TextInputLayout fromUnitTextInputLayout;
    private TextInputLayout toUnitTextInputLayout;
    private EditText valueEditText;
    private TextView resultTextView;
    private LinearLayout rootLayout;

    private String[] daftarMetrik;
    private String satuan1 = "";
    private String satuan2 = "";

    private void initComponents() {
        categoryAutoCompleteTextView = findViewById(R.id.categoryAutoCompleteTextView);
        fromUnitAutoCompleteTextView = findViewById(R.id.fromUnitAutoCompleteTextView);
        toUnitAutoCompleteTextView = findViewById(R.id.toUnitAutoCompleteTextView);
        valueEditText = findViewById(R.id.valueEditText);
        resultTextView = findViewById(R.id.resultTextView);
        fromUnitTextInputLayout = findViewById(R.id.fromUnitTextInputLayout);
        toUnitTextInputLayout = findViewById(R.id.toUnitTextInputLayout);
        rootLayout = findViewById(R.id.rootLayout);
    }

    private void initListener() {
        rootLayout.setOnClickListener(view -> {
            if (getCurrentFocus() != null) {
                getCurrentFocus().clearFocus();
            }
        });

        categoryAutoCompleteTextView.setOnItemClickListener((parent, view, position, id) -> {
            String selectedItem = parent.getItemAtPosition(position).toString();
            if (selectedItem.equals(daftarMetrik[0])) setSatuanAdapter(R.array.length_units);
            if (selectedItem.equals(daftarMetrik[1])) setSatuanAdapter(R.array.mass_units);
            if (selectedItem.equals(daftarMetrik[2])) setSatuanAdapter(R.array.time_units);
            if (selectedItem.equals(daftarMetrik[3])) setSatuanAdapter(R.array.current_units);
            if (selectedItem.equals(daftarMetrik[4])) setSatuanAdapter(R.array.temperature_units);
            if (selectedItem.equals(daftarMetrik[5])) setSatuanAdapter(R.array.light_units);
            if (selectedItem.equals(daftarMetrik[6])) setSatuanAdapter(R.array.substance_units);

            categoryAutoCompleteTextView.clearFocus();
            resetAllInput();
            toggleSatuanSelect(true);
        });

        fromUnitAutoCompleteTextView.setOnItemClickListener((parent, view, position, id) -> {
            fromUnitAutoCompleteTextView.clearFocus();
            satuan1 = parent.getItemAtPosition(position).toString();
            if (!satuan2.isEmpty() && !valueEditText.getText().toString().isEmpty()) {
                convertMetrik(Double.parseDouble(valueEditText.getText().toString()));
            }
        });

        toUnitAutoCompleteTextView.setOnItemClickListener((parent, view, position, id) -> {
            toUnitAutoCompleteTextView.clearFocus();
            satuan2 = parent.getItemAtPosition(position).toString();
            if (!satuan1.isEmpty() && !valueEditText.getText().toString().isEmpty()) {
                convertMetrik(Double.parseDouble(valueEditText.getText().toString()));
            }
        });

        valueEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().isEmpty() || s.toString().equals("0")) {
                    resultTextView.setVisibility(View.GONE);
                    return;
                }
                if (satuan1.isEmpty() || satuan2.isEmpty()) return;
                convertMetrik(Double.parseDouble(s.toString()));
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void resetAllInput() {
        fromUnitAutoCompleteTextView.setText("");
        toUnitAutoCompleteTextView.setText("");
        valueEditText.setText("");
        resultTextView.setText("");
        resultTextView.setVisibility(View.GONE);
    }

    private void convertMetrik(double nilaiAwal) {
        if (satuan1.equals(satuan2)) {
            setHasil(nilaiAwal, "");
            return;
        }
            // Panjang
        if (satuan1.equals("Meter") && satuan2.equals("Kilometer")) setHasil(nilaiAwal / 1000, "km");
        else if (satuan1.equals("Kilometer") && satuan2.equals("Meter")) setHasil(nilaiAwal * 1000, "m");
        else if (satuan1.equals("Meter") && satuan2.equals("Sentimeter")) setHasil(nilaiAwal * 100, "cm");
        else if (satuan1.equals("Sentimeter") && satuan2.equals("Meter")) setHasil(nilaiAwal / 100, "m");
        else if (satuan1.equals("Meter") && satuan2.equals("Milimeter")) setHasil(nilaiAwal * 1000, "mm");
        else if (satuan1.equals("Milimeter") && satuan2.equals("Meter")) setHasil(nilaiAwal / 1000, "m");

            // Massa
        else if (satuan1.equals("Gram") && satuan2.equals("Kilogram")) setHasil(nilaiAwal / 1000, "kg");
        else if (satuan1.equals("Kilogram") && satuan2.equals("Gram")) setHasil(nilaiAwal * 1000, "g");
        else if (satuan1.equals("Gram") && satuan2.equals("Miligram")) setHasil(nilaiAwal * 1000, "mg");
        else if (satuan1.equals("Miligram") && satuan2.equals("Gram")) setHasil(nilaiAwal / 1000, "g");
        else if (satuan1.equals("Kilogram") && satuan2.equals("Ton")) setHasil(nilaiAwal / 1000, "t");
        else if (satuan1.equals("Ton") && satuan2.equals("Kilogram")) setHasil(nilaiAwal * 1000, "kg");

            // Waktu
        else if (satuan1.equals("Detik") && satuan2.equals("Menit")) setHasil(nilaiAwal / 60, "min");
        else if (satuan1.equals("Menit") && satuan2.equals("Detik")) setHasil(nilaiAwal * 60, "s");
        else if (satuan1.equals("Menit") && satuan2.equals("Jam")) setHasil(nilaiAwal / 60, "h");
        else if (satuan1.equals("Jam") && satuan2.equals("Menit")) setHasil(nilaiAwal * 60, "min");
        else if (satuan1.equals("Jam") && satuan2.equals("Hari")) setHasil(nilaiAwal / 24, "d");
        else if (satuan1.equals("Hari") && satuan2.equals("Jam")) setHasil(nilaiAwal * 24, "h");

            // Arus Listrik
        else if (satuan1.equals("Ampere") && satuan2.equals("Miliampere")) setHasil(nilaiAwal * 1000, "mA");
        else if (satuan1.equals("Miliampere") && satuan2.equals("Ampere")) setHasil(nilaiAwal / 1000, "A");
        else if (satuan1.equals("Miliampere") && satuan2.equals("Mikroampere")) setHasil(nilaiAwal * 1000, "µA");
        else if (satuan1.equals("Mikroampere") && satuan2.equals("Miliampere")) setHasil(nilaiAwal / 1000, "mA");
        else if (satuan1.equals("Mikroampere") && satuan2.equals("Nanoampere")) setHasil(nilaiAwal * 1000, "nA");
        else if (satuan1.equals("Nanoampere") && satuan2.equals("Mikroampere")) setHasil(nilaiAwal / 1000, "µA");

            // Suhu
        else if (satuan1.equals("Celcius") && satuan2.equals("Fahrenheit")) setHasil((nilaiAwal * 9/5) + 32, "°F");
        else if (satuan1.equals("Fahrenheit") && satuan2.equals("Celcius")) setHasil((nilaiAwal - 32) * 5/9, "°C");
        else if (satuan1.equals("Celcius") && satuan2.equals("Kelvin")) setHasil(nilaiAwal + 273.15, "K");
        else if (satuan1.equals("Kelvin") && satuan2.equals("Celcius")) setHasil(nilaiAwal - 273.15, "°C");
        else if (satuan1.equals("Fahrenheit") && satuan2.equals("Kelvin")) setHasil((nilaiAwal - 32) * 5/9 + 273.15, "K");
        else if (satuan1.equals("Kelvin") && satuan2.equals("Fahrenheit")) setHasil((nilaiAwal - 273.15) * 9/5 + 32, "°F");

            // Intensitas Cahaya
        else if (satuan1.equals("Candela") && satuan2.equals("Lumen")) setHasil(nilaiAwal * 12.57, "lm");
        else if (satuan1.equals("Lumen") && satuan2.equals("Candela")) setHasil(nilaiAwal / 12.57, "cd");
        else if (satuan1.equals("Lumen") && satuan2.equals("Lux")) setHasil(nilaiAwal, "lx");
        else if (satuan1.equals("Lux") && satuan2.equals("Lumen")) setHasil(nilaiAwal * 1, "lm");

            // Jumlah Zat
        else if (satuan1.equals("Mole") && satuan2.equals("Kilomole")) setHasil(nilaiAwal / 1000, "kmol");
        else if (satuan1.equals("Kilomole") && satuan2.equals("Mole")) setHasil(nilaiAwal * 1000, "mol");
        else if (satuan1.equals("Mole") && satuan2.equals("Millimole")) setHasil(nilaiAwal * 1000, "mmol");
        else if (satuan1.equals("Millimole") && satuan2.equals("Mole")) setHasil(nilaiAwal / 1000, "mol");
    }

    private void setHasil(double res, String prefix) {
        resultTextView.setText(String.format("%s %s", Double.toString(res).replaceAll("0*$", "").replaceAll("\\.$", ""), prefix));
        resultTextView.setVisibility(View.VISIBLE);
    }

    private void setSatuanAdapter(int arrStringRes) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.dropdown_select_item, getResources().getStringArray(arrStringRes));
        fromUnitAutoCompleteTextView.setAdapter(adapter);
        toUnitAutoCompleteTextView.setAdapter(adapter);
    }

    private void initMetrikDropdownAdapterAndListener() {
        daftarMetrik = getResources().getStringArray(R.array.conversion_categories);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.dropdown_select_item, daftarMetrik);
        categoryAutoCompleteTextView.setAdapter(adapter);
    }

    private void toggleSatuanSelect(boolean state) {
        fromUnitTextInputLayout.setEnabled(state);
        toUnitTextInputLayout.setEnabled(state);
        valueEditText.setEnabled(state);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initComponents();
        initListener();

        initMetrikDropdownAdapterAndListener();
        toggleSatuanSelect(false);
    }
}