package it.thomas.myapps.MainAdapter.TicTacToe;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Set;
import java.util.TreeSet;

import it.thomas.myapps.R;
import it.thomas.myapps.databinding.ActivityTicTacToeBinding;

public class TicTacToe extends AppCompatActivity {

    private ActivityTicTacToeBinding binding;
    private char turn = 'o';
    private String playerO, playerX;
    private int[][] winningPoints = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 9},
            {1, 4, 7},
            {2, 5, 8},
            {3, 6, 9},
            {1, 5, 9},
            {7, 5, 3}
    };

    private final Set<Integer> playerOpoints = new TreeSet<>();
    private final Set<Integer> playerXpoints = new TreeSet<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTicTacToeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initDataFromIntent();
    }

    private void initDataFromIntent() {
        var intent = getIntent();
        if (intent != null) {
            playerO = intent.getStringExtra(tictactoeIntro.PLAYER_ONE);
            playerX = intent.getStringExtra(tictactoeIntro.PLAYER_TWO);
            binding.tvPlayerOne.setText(playerO);
            binding.tvPlayerTwo.setText(playerX);
            changePlayerTurn();
        }
    }

    private void changePlayerTurn() {
        if (turn == 'o') {
            binding.tvTurn.setText(playerO + "Turn!");
        } else {
            binding.tvTurn.setText(playerX + "Turn!");
        }
    }

    public void onCardClicked(View view) {
        CardView cv = (CardView) view;
        ImageView iv = (ImageView) ((LinearLayout) cv.getChildAt(0)).getChildAt(0);
        Integer point = Integer.valueOf(cv.getTooltipText().toString());
        if (validToClick(point)){
            if (turn == 'o') {
                //Player One Clicked
                playerOpoints.add(point);
                iv.setImageResource(R.drawable.o_white);
                checkResult();
                turn = 'x';
            } else {
                //Player Two Clicked
                playerXpoints.add(point);
                iv.setImageResource(R.drawable.x_white);
                checkResult();
                turn = 'o';
            }
            checkEndgame();
            changePlayerTurn();
        } else {
            Toast.makeText(this, "This Block is already selected, please check another one", Toast.LENGTH_LONG).show();
        }
    }

    private void checkEndgame() {
        if (playerOpoints.size() + playerXpoints.size() == 9) {
            showDrawDialog();
        }
    }

    private void showDrawDialog() {
        showDialog("It is a draw!");
    }

    private void checkResult() {
        if (turn == 'o') {
            if (playerOpoints.size() < 3) return;
            for (int[] winningPoint : winningPoints) {
                if(playerOpoints.contains(winningPoint[0]) && playerOpoints.contains(winningPoint[1]) && playerOpoints.contains(winningPoint[2])) {
                    showWinningDialog();
                }
            }
        } else {
            if (playerXpoints.size() < 3) return;
            for (int[] winningPoint : winningPoints) {
                if(playerXpoints.contains(winningPoint[0]) && playerXpoints.contains(winningPoint[1]) && playerXpoints.contains(winningPoint[2])) {
                    showWinningDialog();
                }
            }
        }
    }

    private void showDialog(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        AlertDialog dialog = builder
                .setTitle("Tic Tac Toe")
                .setMessage(message)
                .setPositiveButton("Play Again", (((dialog1, which) -> resetGame())))
                .setNegativeButton("Exit", (((dialog1, which) -> {
                    dialog1.cancel();
                    TicTacToe.this.finish();
                })))
                .create();
        dialog.show();
    }

    private void resetGame() {
        binding.iv1.setImageDrawable(null);
        binding.iv2.setImageDrawable(null);
        binding.iv3.setImageDrawable(null);
        binding.iv4.setImageDrawable(null);
        binding.iv5.setImageDrawable(null);
        binding.iv6.setImageDrawable(null);
        binding.iv7.setImageDrawable(null);
        binding.iv8.setImageDrawable(null);
        binding.iv9.setImageDrawable(null);
        playerOpoints.clear();
        playerXpoints.clear();
    }

    private void showWinningDialog() {
        String message = String.format("Player %s won", String.valueOf(turn).toUpperCase());
        showDialog(message);
    }

    private boolean validToClick(Integer point) {
        return !(playerOpoints.contains(point) || playerXpoints.contains(point));
    }
}