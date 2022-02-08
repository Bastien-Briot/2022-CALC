package com.briobas.calc;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    public enum Ops {
        PLUS("+"),
        MOINS("-"),
        FOIS("*"),
        DIV("/"),
        DOT("."),
        POURCENT("%");

        private final String name;
        Ops(String name) {
            this.name = name;
        }
        @NonNull
        public String toString() {
            return name;
        }
    }
    private TextView screen;
    private double op1 = 0;
    private double op2 = 0;
    private Ops operator = null;
    private boolean isOp1 = true;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button BT_Point = findViewById(R.id.BTN_Virgule);
        screen = findViewById(R.id.resultTXT);
        Button btnEgal = findViewById(R.id.BTN_Egal);
        btnEgal.setOnClickListener(v -> {
            String s = screen.getText().toString();
            String[] valueString = s.split("[+/%\\-*]");
            if (valueString[1] == null) { screen.setText("Error"); }
            if (valueString[0].equals(".") || valueString[1].equals(".")) { screen.setText("Error"); } else {
                op1 = Double.parseDouble(valueString[0]);
                op2 = Double.parseDouble(valueString[1]);
                Log.v("Nombre1", String.valueOf(op1));
                Log.v("Nombre2", String.valueOf(op2));
                compute();
                BT_Point.setEnabled(true);
            }

    });

    Button BT_Clear = findViewById(R.id.BTN_Reset);
        BT_Clear.setOnClickListener(view -> {
        clear();
        BT_Point.setEnabled(true);
    });

    Button BT_Pourcent = findViewById(R.id.BTN_Pourcent);
        BT_Pourcent.setOnClickListener(view -> {
        operator = Ops.POURCENT;
        compute();
    });
        BT_Point.setOnClickListener(view -> {
        screen.append(".");
        BT_Point.setEnabled(false);
    });

}

    public void compute() {
        if (isOp1) {
            if (operator == Ops.POURCENT) {
                double val = Double.parseDouble(screen.getText().toString());
                double valpourcent = val/100;
                screen.setText(String.valueOf(valpourcent));
            }
        } else {
            switch (operator) {
                case PLUS: op1 += op2; break;
                case MOINS: op1 -= op2; break;
                case FOIS: op1 *= op2; break;
                case DIV: op1 /= op2; break;
                default: return;
            }
            op2 = 0;
            isOp1 = true;
            screen.setText(String.valueOf(op1));
        }
    }


    private void clear() {
        op1 = 0;
        op2 = 0;
        operator = null;
        isOp1 = true;
        screen.setText("");
    }

    @SuppressLint("NonConstantResourceId")
    public void setOperator(View v) {
        switch (v.getId()) {
            case R.id.BTN_Plus:
                operator = Ops.PLUS;
                screen.append("+");
                break;
            case R.id.BTN_Moins:
                operator = Ops.MOINS;
                screen.append("-");
                break;
            case R.id.BTN_Fois:
                operator = Ops.FOIS;
                screen.append("*");
                break;
            case R.id.BTN_Diviser:
                operator = Ops.DIV;
                screen.append("/");
                break;
            case R.id.BTN_Virgule:
                operator = Ops.DOT;
                screen.append(".");
                break;
            case R.id.BTN_Pourcent:
                operator = Ops.POURCENT;
                break;
            default:
                return;
        }
        isOp1 = false;
    }


    public void addNumber(View v) {
        try {
            int val = Integer.parseInt(((Button) v).getText().toString());
            screen.append(String.valueOf(val));
        } catch (NumberFormatException | ClassCastException e) {
            Log.v("Erreur: ", String.valueOf(e));
        }
    }

}