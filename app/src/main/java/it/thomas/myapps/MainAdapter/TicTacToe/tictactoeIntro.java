package it.thomas.myapps.MainAdapter.TicTacToe;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import it.thomas.myapps.R;

public class tictactoeIntro extends AppCompatActivity {

    EditText etPlayerOne, etPlayerTwo;
    Button btStart;
    public static final String PLAYER_ONE = "PlayerOne", PLAYER_TWO = "PlayerTwo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tictactoe_intro);
        initUI();
        initListerner();
    }

    private void initListerner() {
        btStart.setOnClickListener(v -> {
            var PlayerOne = etPlayerOne.getText();
            var PlayerTwo = etPlayerTwo.getText();
            if (PlayerOne != null && PlayerOne.length() > 0 && PlayerTwo != null && PlayerTwo.length() > 0) {
                var intent = new Intent(this, TicTacToe.class);
                intent.putExtra(PLAYER_ONE, PlayerOne.toString());
                intent.putExtra(PLAYER_TWO, PlayerTwo.toString());
                startActivity(intent);
                this.finish();
            }
        });
    }

    private void initUI() {
        etPlayerOne = findViewById(R.id.et_player_one);
        etPlayerTwo = findViewById(R.id.et_player_two);
        btStart = findViewById(R.id.bt_start);
    }
}