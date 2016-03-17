package edu.auburn.eng.csse.comp3710.jsv0004.challenge04;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    public static boolean cleared = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button1 = (Button) findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                addDigit(1);
            }
        });

        Button button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                addDigit(2);
            }
        });

        Button button3 = (Button) findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                addDigit(3);
            }
        });

        Button button4 = (Button) findViewById(R.id.button4);
        button4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                addDigit(4);
            }
        });

        Button button5 = (Button) findViewById(R.id.button5);
        button5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                addDigit(5);
            }
        });

        Button button6 = (Button) findViewById(R.id.button6);
        button6.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                addDigit(6);
            }
        });

        Button button7 = (Button) findViewById(R.id.button7);
        button7.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                addDigit(7);
            }
        });

        Button button8 = (Button) findViewById(R.id.button8);
        button8.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                addDigit(8);
            }
        });

        Button button9 = (Button) findViewById(R.id.button9);
        button9.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                addDigit(9);
            }
        });

        Button button0 = (Button) findViewById(R.id.button0);
        button0.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                addDigit(0);
            }
        });

        Button buttonC = (Button) findViewById(R.id.buttonC);
        buttonC.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                clearDisplay();
            }
        });

        Button button42 = (Button) findViewById(R.id.button42);
        button42.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                add42();
            }
        });
    }

    protected void addDigit(int digit) {
        EditText numberDisplay = (EditText) findViewById(R.id.numberDisplay);
        if (numberDisplay.getText().length() < 10) {
            if (cleared) {
                String digitString = String.valueOf(digit);
                numberDisplay.setText(digitString);
                cleared = false;
            } else {
                String before = numberDisplay.getText().toString();
                String after = before + digit;
                numberDisplay.setText(after);
            }
        }
    }

    protected void clearDisplay() {
        EditText numberDisplay = (EditText) findViewById(R.id.numberDisplay);
        numberDisplay.setText("0");
        cleared = true;
    }

    protected void add42() {
        EditText numberDisplay = (EditText) findViewById(R.id.numberDisplay);
        int before = Integer.parseInt(numberDisplay.getText().toString());
        int after = before + 42;
        String afterString = String.valueOf(after);
        numberDisplay.setText(afterString);
    }
}
