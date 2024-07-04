package it.thomas.myapps.MainAdapter.SimpleApp;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import it.thomas.myapps.R;

public class FontConverter extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener{

    private Typeface zawgyi, unicode;
    Button btConvert, btCopy, btClear;
    EditText etInput, etOutput;
    RadioGroup rgFonts;
    RadioButton rbUni2Zg, rbZg2Uni;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_font_converter);
        initUI();
        initTypeface();
        initListeners();
    }

    private void initListeners() {
        rgFonts.setOnCheckedChangeListener(this);
        btConvert.setOnClickListener(v -> {
            String input = etInput.getText().toString();
            if (rbUni2Zg.isChecked()) {
                String converted = RabbitConverter.uni2zg(input);
                etOutput.setText(converted);
            } else {
                String converted = RabbitConverter.zg2uni(input);
                etOutput.setText(converted);
            }
        });

        btClear.setOnClickListener(v -> {
            etInput.getText().clear();
            etOutput.getText().clear();
        });

        btCopy.setOnClickListener(v -> {
            if (!etOutput.getText().toString().isEmpty()) {
                ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("font_converter", etOutput.getText().toString());
                clipboardManager.setPrimaryClip(clipData);
                Toast.makeText(this, "Output text copied", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "No output text to copy", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initTypeface() {
        zawgyi = ResourcesCompat.getFont(this, R.font.zawgyi);
        unicode = ResourcesCompat.getFont(this, R.font.unicode);
    }

    private void initUI() {
        btConvert = findViewById(R.id.btConvert);
        btCopy = findViewById(R.id.btCopy);
        btClear = findViewById(R.id.btClear);
        etInput = findViewById(R.id.etInput);
        etOutput = findViewById(R.id.etOutput);
        rgFonts = findViewById(R.id.rgFonts);
        rbUni2Zg = findViewById(R.id.rbUni2Zg);
        rbZg2Uni = findViewById(R.id.rbZg2Uni);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (checkedId == R.id.rbUni2Zg) {
            Toast.makeText(getApplicationContext(), "Unicode to Zawgyi", Toast.LENGTH_SHORT).show();
            etInput.setTypeface(unicode);
            etOutput.setTypeface(zawgyi);
        } else {
            Toast.makeText(getApplicationContext(), "Zawgyi to Unicode", Toast.LENGTH_SHORT).show();
            etInput.setTypeface(zawgyi);
            etOutput.setTypeface(unicode);
        }
    }
}