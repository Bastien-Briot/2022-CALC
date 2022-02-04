package com.briobas.calc;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    public enum Ops {
        PLUS("+"),
        MOINS("-"),
        FOIS("*"),
        DIV("/"),
        DOT("."),
        POURCENT("%");

        private String name = "";

        Ops(String name) {
            this.name = name;
        }

        public String toString() {
            return name;
        }
    }


    private TextView screen;
    private double op1 = 0;
    private double op2 = 0;
    private Ops operator = null;
    private boolean isOp1 = true;
    private Button BT_Point;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button BT_Point = findViewById(R.id.BTN_Virgule);
        screen = (TextView) findViewById(R.id.resultTXT);
        Button btnEgal = (Button) findViewById(R.id.BTN_Egal);
        btnEgal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Double s = Double.parseDouble(screen.getText().toString());
                    String[] sheesh = String.valueOf(s).split("\\+");
                    Double[] doubleArr = new Double[2];
                    op1 = doubleArr[0] = Double.parseDouble(sheesh[0]);
                    op2 = doubleArr[1] = Double.parseDouble(sheesh[1]);
                    Log.v("Nombre1", String.valueOf(op1));
                    Log.v("Nombre2", String.valueOf(op2));
                    compute();
                    BT_Point.setEnabled(true);
                } catch(NumberFormatException | ClassCastException e) {
                    Log.v("error: ", String.valueOf(e));
                }

            }
        });

        Button BT_Clear = findViewById(R.id.BTN_Reset);
        BT_Clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clear();
                BT_Point.setEnabled(true);
            }
        });

        Button BT_Pourcent = findViewById(R.id.BTN_Pourcent);
        BT_Pourcent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                operator = Ops.POURCENT;
                compute();
            }
        });
        BT_Point.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                screen.append(".");
                BT_Point.setEnabled(false);
            }
        });

    }

    private void updateDisplay() {
        Button BT_Point = findViewById(R.id.BTN_Virgule);
        double v = op1;
        if (!isOp1) {
            v = op2;
        }
        screen.append(String.valueOf(String.format("%.0f", v)));
    }


    public void compute() {
        if (isOp1) {
            if (operator == Ops.POURCENT) {
                screen.setText(String.valueOf(op1 /= 100));
            }
        } else {
            switch (operator) {
                case PLUS:
                    op1 += op2;
                    break;
                case MOINS:
                    op1 -= op2;
                    break;
                case FOIS:
                    op1 *= op2;
                    break;
                case DIV:
                    op1 /= op2;
                    break;

                default:
                    return;
            }
            op2 = 0;
            isOp1 = true;
            updateDisplay();
        }
    }


    private void clear() {
        op1 = 0;
        op2 = 0;
        operator = null;
        isOp1 = true;
        updateDisplay();
    }

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

            if (isOp1) {
                op1 = op1 * 10 + val;
                updateDisplay();
            } else {
                op2 = op2 * 10 + val;
                updateDisplay();
            }
        } catch (NumberFormatException | ClassCastException e) {
        }
    }

}