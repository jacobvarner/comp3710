package edu.auburn.eng.csse.comp3710.jsv0004.challenge02;

import android.support.v7.app.AppCompatActivity;
import android.content.Context;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.telephony.PhoneStateListener;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public static int counter = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView counterText = (TextView) findViewById(R.id.counterText);
        String counterString = String.valueOf(counter);
        counterText.setText(counterString);

        TelephonyManager telephonyManager =
                (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);

        PhoneStateListener callStateListener = new PhoneStateListener() {
            public void onCallStateChanged(int state, String incomingNumber)
            {
                if(state==TelephonyManager.CALL_STATE_RINGING){
                    Log.i("challenge02", "Phone is ringing");
                    counter = -1;
                }
                if(state==TelephonyManager.CALL_STATE_OFFHOOK){
                    Log.i("challenge02", "Phone is off hook");
                }

                if(state==TelephonyManager.CALL_STATE_IDLE){
                    Log.i("challenge02", "Phone is idle");
                }
            }
        };
        telephonyManager.listen(callStateListener,PhoneStateListener.LISTEN_CALL_STATE);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("challenge02", "onStart");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("challenge02", "onPause");

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("challenge02", "onResume");
        counter++;
        Log.i("challenge02", "counter = " + counter);
        String counterString = String.valueOf(counter);
        TextView counterText = (TextView) findViewById(R.id.counterText);
        counterText.setText(counterString);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("challenge02", "onStop");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("challenge02", "onRestart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("challenge02", "onDestroy");
    }
}
