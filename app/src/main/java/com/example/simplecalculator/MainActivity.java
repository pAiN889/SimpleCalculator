package com.example.simplecalculator;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private CalculatorLogic calculator;
    private TextView tvExpression;
    private TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        androidx.core.view.WindowCompat.getInsetsController(getWindow(), getWindow().getDecorView())
                .setAppearanceLightStatusBars(true);
        androidx.core.view.WindowCompat.getInsetsController(getWindow(), getWindow().getDecorView())
                .setAppearanceLightNavigationBars(true);

        setContentView(R.layout.activity_main);

        calculator = new CalculatorLogic();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            int padding16 = (int) (16 * getResources().getDisplayMetrics().density);
            int padding24 = (int) (24 * getResources().getDisplayMetrics().density);
            v.setPadding(
                    systemBars.left + padding16,
                    systemBars.top + padding16,
                    systemBars.right + padding16,
                    systemBars.bottom + padding24
            );
            return insets;
        });

        initViews();
        setupClickListeners();
        updateDisplay();
    }

    private void initViews() {
        tvExpression = findViewById(R.id.tv_expression);
        tvResult = findViewById(R.id.tv_result);
    }

    private void setupClickListeners() {
        // Digits
        int[] numberBtnIds = {
                R.id.btn_0, R.id.btn_1, R.id.btn_2, R.id.btn_3, R.id.btn_4,
                R.id.btn_5, R.id.btn_6, R.id.btn_7, R.id.btn_8, R.id.btn_9
        };

        for (int id : numberBtnIds) {
            Button btn = findViewById(id);
            if (btn != null) {
                btn.setOnClickListener(v -> {
                    char digit = btn.getText().toString().charAt(0);
                    calculator.onDigit(digit);
                    updateDisplay();
                });
            }
        }

        // Decimal
        Button btnDot = findViewById(R.id.btn_dot);
        if (btnDot != null) {
            btnDot.setOnClickListener(v -> {
                calculator.onDecimal();
                updateDisplay();
            });
        }

        // Operators
        int[] operatorBtnIds = {
                R.id.btn_add, R.id.btn_subtract, R.id.btn_multiply, R.id.btn_divide
        };

        for (int id : operatorBtnIds) {
            Button btn = findViewById(id);
            if (btn != null) {
                btn.setOnClickListener(v -> {
                    String op = btn.getText().toString();
                    calculator.onOperator(op);
                    updateDisplay();
                });
            }
        }

        // Equals
        Button btnEquals = findViewById(R.id.btn_equals);
        if (btnEquals != null) {
            btnEquals.setOnClickListener(v -> {
                calculator.onEquals();
                updateDisplay();
            });
        }

        // Clear (AC)
        Button btnAc = findViewById(R.id.btn_ac);
        if (btnAc != null) {
            btnAc.setOnClickListener(v -> {
                calculator.clear();
                updateDisplay();
            });
        }

        // Delete (DEL)
        Button btnDel = findViewById(R.id.btn_del);
        if (btnDel != null) {
            btnDel.setOnClickListener(v -> {
                calculator.onDelete();
                updateDisplay();
            });
        }

        // Toggle Sign (±)
        Button btnPlusMinus = findViewById(R.id.btn_plus_minus);
        if (btnPlusMinus != null) {
            btnPlusMinus.setOnClickListener(v -> {
                calculator.onToggleSign();
                updateDisplay();
            });
        }

        // Percent (%)
        Button btnPercent = findViewById(R.id.btn_percent);
        if (btnPercent != null) {
            btnPercent.setOnClickListener(v -> {
                calculator.onPercent();
                updateDisplay();
            });
        }
    }

    private void updateDisplay() {
        if (tvExpression != null) {
            tvExpression.setText(calculator.getExpression());
        }
        if (tvResult != null) {
            tvResult.setText(calculator.getCurrentInput());
        }
    }
}
