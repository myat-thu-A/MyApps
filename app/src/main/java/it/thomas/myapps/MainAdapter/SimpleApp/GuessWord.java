package it.thomas.myapps.MainAdapter.SimpleApp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import it.thomas.myapps.R;

public class GuessWord extends AppCompatActivity {

    private TextView tvScore, tvGuessWord, tvMatchPlayed;
    private EditText etYourGuess;
    private Button btGuess;
    private int score, matchPlayed, played;
    private String guessWord, guess;
    private final String[] words = {"apple", "banana", "game", "computer",
            "orange", "flower", "cat", "ball", "bell", "iron"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guess_word);
        initUI();
        initListener();
    }

    private void initListener() {
        btGuess.setOnClickListener(v -> {
            if(played ==  5) {
                return;
            }
            played++;
            String myGuess = etYourGuess.getText().toString();
            if (guessWord.equals(myGuess.trim())) {
                score++;
                tvScore.setText(score + "/5");
            }
            matchPlayed++;
            tvMatchPlayed.setText("Match Played : " + matchPlayed);
            nextRound();

            if (matchPlayed == 5) {
                if(score > 2) {
                    tvGuessWord.setText("WINNER");
                } else {
                    tvGuessWord.setText("TRY AGAIN!");
                }
                etYourGuess.setText("");
                return;
            }
        });
    }

    private void nextRound() {
        guessWord = getRandomWord();
        String word = guessWord;
        tvGuessWord.setText(wordShuffle(word));
        etYourGuess.setText("");
    }

    private void initUI() {
        tvScore = findViewById(R.id.tvScore);
        tvGuessWord = findViewById(R.id.tvGuessWord);
        tvMatchPlayed = findViewById(R.id.tvMatchPlayed);
        etYourGuess = findViewById(R.id.etYourGuess);
        btGuess = findViewById(R.id.btGuess);
        guess = getRandomWord();
        tvGuessWord.setText(wordShuffle(guess));
    }

    private String wordShuffle(String str) {
        List<Character> chars = new ArrayList<>();
        for(char c : str.toCharArray()) {
            chars.add(c);
        }

        Collections.shuffle(chars);

        StringBuilder builder = new StringBuilder();
        for(char c : chars) {
            builder.append(c);
        }
        return builder.toString();
    }

    private String getRandomWord() {
        int rd = new Random().nextInt(words.length);
        guessWord = words[rd];
        return guessWord;
    }
}