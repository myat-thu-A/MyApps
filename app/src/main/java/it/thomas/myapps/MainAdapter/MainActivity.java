package it.thomas.myapps.MainAdapter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.thomas.myapps.MainAdapter.UserDatabase.MyDatabase;
import it.thomas.myapps.MainAdapter.UserDatabase.SignUp;
import it.thomas.myapps.MainAdapter.UserDatabase.User;
import it.thomas.myapps.MainAdapter.UserDatabase.UserDao;
import it.thomas.myapps.R;
import it.thomas.myapps.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private UserDao userDao;
    private Map<String, String> dbUserMap = new HashMap<>();

    private final String myEmail = "myatt2311@gmail.com";
    private final String myPassword = "myatthu23";

    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(binding.getRoot());
        initUI();
        initData();
        initListener();
    }

    private void initData() {
        userDao = MyDatabase.getInstance(this).getUserDao();
        List<User> userList = userDao.getAllUsers();
        Log.d("myat", String.valueOf(userList.size()));
        for (User user : userList) {
            String mail = user.getEmail();
            String pwd = user.getPassword();
            dbUserMap.put(mail, pwd);
            Log.d("myat", dbUserMap.get(mail));
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initUI() {
        binding.imageView.setOnTouchListener(new OnSwipeTouchListener(getApplicationContext()){
            public void onSwipeTop() {
            }

            public void onSwipeRight() {
                if (count == 0) {
                    binding.imageView.setImageResource(R.drawable.good_night_img);
                    binding.textView.setText("Night");
                    count = 1;
                } else {
                    binding.imageView.setImageResource(R.drawable.good_morning_img);
                    binding.textView.setText("Morning");
                    count = 0;
                }
            }

            @SuppressLint("ClickableViewAccessibility")
            public void onSwipeLeft() {
                if (count == 0) {
                    binding.imageView.setImageResource(R.drawable.good_night_img);
                    binding.textView.setText("Night");
                    count = 1;
                } else {
                    binding.imageView.setImageResource(R.drawable.good_morning_img);
                    binding.textView.setText("Morning");
                    count = 0;
                }
            }

            public void onSwipeBottom() {
            }
        });

    }


    private void initListener() {
        binding.btMainSignUp.setOnClickListener(v -> {
            Intent intent = new Intent(this, SignUp.class);
            startActivity(intent);
        });

        binding.btMainSignIn.setOnClickListener(v -> {
            String inputEmail = binding.etMainEmail.getText().toString();
            String inputPwd = binding.etMainPassword.getText().toString();
            User user = new User(inputEmail, inputPwd);

            if (dbUserMap.containsKey(inputEmail) && dbUserMap.containsValue(inputPwd)) {
                Intent intent = new Intent(this, AllApp.class);
                startActivity(intent);
                Toast.makeText(this, "Login Success!", Toast.LENGTH_SHORT).show();
            } else {
                Log.d("myat", user.getEmail() + user.getPassword());
                Toast.makeText(this, "Wrong email and password!", Toast.LENGTH_SHORT).show();
            }
        });



    }
}