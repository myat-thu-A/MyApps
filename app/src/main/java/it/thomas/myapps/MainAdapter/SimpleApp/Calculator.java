package it.thomas.myapps.MainAdapter.SimpleApp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import it.thomas.myapps.R;

public class Calculator extends AppCompatActivity {

    Button bt0, bt1, bt2, bt3, bt4, bt5, bt6, bt7, bt8, bt9, btAdd, btSub, btMul, btDiv, btEqu, btClear;

    EditText etCalculator;
    TextView tvOperation, tvOperator;

    private String operator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);
        initUI();
    }

    private void initUI() {
        bt0 = findViewById(R.id.bt0);
        bt1 = findViewById(R.id.bt1);
        bt2 = findViewById(R.id.bt2);
        bt3 = findViewById(R.id.bt3);
        bt4 = findViewById(R.id.bt4);
        bt5 = findViewById(R.id.bt5);
        bt6 = findViewById(R.id.bt6);
        bt7 = findViewById(R.id.bt7);
        bt8 = findViewById(R.id.bt8);
        bt9 = findViewById(R.id.bt9);
        btAdd = findViewById(R.id.btAdd);
        btSub = findViewById(R.id.btSub);
        btMul = findViewById(R.id.btMul);
        btDiv = findViewById(R.id.btDiv);
        btEqu = findViewById(R.id.btEqual);
        btClear = findViewById(R.id.btClear);
        etCalculator = findViewById(R.id.etCalculator);
        tvOperation = findViewById(R.id.tvOperation);
        tvOperator = findViewById(R.id.tvOperator);
    }

    public void onNumberClicked(View view) {
        Button btn = (Button) view;
        Log.d("Myat", btn.getText().toString());
        if (etCalculator.getText().toString().equals("0"))
            etCalculator.setText(btn.getText().toString());
        else
            etCalculator.append(btn.getText().toString());
    }

    public void onOperatorClicked(View view) {
        int operator = view.getId();
        if (operator == R.id.btAdd) {
            this.operator = "+";
            tvOperation.setText(etCalculator.getText().toString());
            etCalculator.setText("0");
            tvOperator.setText(this.operator);
        } else if (operator == R.id.btSub) {
            this.operator = "-";
            tvOperation.setText(etCalculator.getText().toString());
            etCalculator.setText("0");
            tvOperator.setText(this.operator);
        } else if (operator == R.id.btMul) {
            this.operator = "*";
            tvOperation.setText(etCalculator.getText().toString());
            etCalculator.setText("0");
            tvOperator.setText(this.operator);
        } else if (operator == R.id.btDiv) {
            this.operator = "/";
            tvOperation.setText(etCalculator.getText().toString());
            etCalculator.setText("0");
            tvOperator.setText(this.operator);
        } else if (operator == R.id.btEqual) {
            int firstNum = Integer.parseInt(tvOperation.getText().toString());
            int secondNum = Integer.parseInt(etCalculator.getText().toString());
            calculate(firstNum, secondNum);
            tvOperation.setText("");
            tvOperator.setText("");
        }
    }

    private void calculate(int firstNum, int secondNum) {
        int result = 0;
        if(this.operator.equals("+")){
            result = firstNum + secondNum;
        } else if (this.operator.equals("-")) {
            result = firstNum - secondNum;
        } else if (this.operator.equals("*")) {
            result = firstNum * secondNum;
        } else {
            result = firstNum / secondNum;
        }
        etCalculator.setText(String.valueOf(result));
    }

    public void onClearClicked(View view) {
        etCalculator.setText("");
    }
}