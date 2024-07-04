package it.thomas.myapps.MainAdapter.SimpleApp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Random;

import it.thomas.myapps.R;

public class DiceRoller extends AppCompatActivity {

    TextView tvDice;
    Button btRoll;
    ImageView imageView;

    int[] imgRes = {R.drawable.dice_1, R.drawable.dice_2, R.drawable.dice_3, R.drawable.dice_4,
            R.drawable.dice_5, R.drawable.dice_6};
    int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_dice_roller);
        initUI();
        initListener();

    }

    private void initListener() {
        btRoll.setOnClickListener(v -> {
            index = new Random().nextInt(imgRes.length);
            imageView.setImageResource(imgRes[index]);
            tvDice.setText(String.valueOf(index+1));
        });
    }

    private void initUI() {
        btRoll = findViewById(R.id.btRoll);
        tvDice = findViewById(R.id.tvShow);
        imageView = findViewById(R.id.imageView);
        imageView.setImageResource(imgRes[0]);
    }
}