package it.thomas.myapps.MainAdapter;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.List;

import it.thomas.myapps.MainAdapter.DateCounter.DateCounter;
import it.thomas.myapps.MainAdapter.Mp3Playlist.Mp3Playlist;
import it.thomas.myapps.MainAdapter.MyNote.MyNote;
import it.thomas.myapps.MainAdapter.SimpleApp.Calculator;
import it.thomas.myapps.MainAdapter.SimpleApp.DiceRoller;
import it.thomas.myapps.MainAdapter.SimpleApp.FontConverter;
import it.thomas.myapps.MainAdapter.SimpleApp.GuessWord;
import it.thomas.myapps.MainAdapter.TicTacToe.tictactoeIntro;
import it.thomas.myapps.MainAdapter.TodoApp.TodoApp;
import it.thomas.myapps.MainAdapter.Voucher.Voucher;
import it.thomas.myapps.R;
import it.thomas.myapps.databinding.ActivityAllAppBinding;

public class AllApp extends AppCompatActivity {

    private ActivityAllAppBinding binding;
    private MainAdapter mainAdapter;

    static AppData[] appData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAllAppBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initData();
        initUI();
        initListeners();

    }

    private void initData() {
        appData = new AppData[]
                {
                        new AppData("Guess Word", R.drawable.guess, new Intent(this, GuessWord.class)),
                        new AppData("Font Converter", R.drawable.convert, new Intent(this, FontConverter.class)),
                        new AppData("Calculator", R.drawable.calculator, new Intent(this, Calculator.class)),
                        new AppData("Dice Roller", R.drawable.dice2, new Intent(this, DiceRoller.class)),
                        new AppData("Date Counter", R.drawable.datecounter, new Intent(this, DateCounter.class)),
                        new AppData("Tic Tac Toe", R.drawable.tictactoe, new Intent(this, tictactoeIntro.class)),
                        new AppData("Mp3 Player", R.drawable.player, new Intent(this, Mp3Playlist.class)),
                        new AppData("Voucher", R.drawable.voucher, new Intent(this, Voucher.class)),
                        new AppData("Note", R.drawable.note, new Intent(this, MyNote.class)),
                        new AppData("Todo App", R.drawable.todo, new Intent(this, TodoApp.class))
                };

    }

    private void initListeners() {
        mainAdapter.setAppClickListener(new AppClickListener() {
            @Override
            public void onClick(int index) {
                AppData data = appData[index];
                Intent intent = data.intent();
                if(intent != null) {
                    startActivity(intent);
                }
            }
        });
    }

    private void initUI() {

        mainAdapter = new MainAdapter();
        mainAdapter.setAppDatas(List.of(appData));

        binding.rvAllapp.setAdapter(mainAdapter);
        binding.rvAllapp.setLayoutManager(new LinearLayoutManager(this));
    }


}