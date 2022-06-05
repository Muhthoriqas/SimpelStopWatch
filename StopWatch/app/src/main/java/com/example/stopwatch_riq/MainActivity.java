package com.example.stopwatch_riq;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    //Set Button dan Text
    Button btnMulai, btnStop, btnTap, btnClear, clearTap;
    TextView txtTimer;

    LinearLayout container;
    Handler customHandler = new Handler();

    //Set awal timer
    long startTime =0L, timeInMilliseconds=0L, timeSwapBuff=0L,updateTime=0L;


    Runnable updateTimeThread = new Runnable() {
        @Override
        //Ketika tekan mulai
        public void run() {
            timeInMilliseconds = SystemClock.uptimeMillis()-startTime;
            updateTime = timeSwapBuff+timeInMilliseconds;
            int secs = (int) (updateTime/1000);
            int mins = (int) secs/60;
            int milliseconds = (int) (updateTime%1000);
            txtTimer.setText("" + mins + ":" + String.format("%02d",secs)+":"+ String.format("%03d",milliseconds));
            customHandler.postDelayed(this,0);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnMulai = findViewById(R.id.btnMulai);
        btnStop = findViewById(R.id.btnStop);
        btnTap = findViewById(R.id.btnTap);
        btnClear = findViewById(R.id.btnClear);
        clearTap = findViewById(R.id.ClearTap);

        txtTimer = findViewById(R.id.nilaiWaktu);
        container= (LinearLayout) findViewById(R.id.container);

        //Click Mulai
        btnMulai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startTime = SystemClock.uptimeMillis();
                customHandler.postDelayed(updateTimeThread,0);
            }
        });

        //Click Stop
        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timeSwapBuff += timeInMilliseconds;
                customHandler.removeCallbacks(updateTimeThread);
            }
        });

        //Click Tap
        btnTap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View addView = inflater.inflate(R.layout.row,null);
                TextView txtValue = (TextView) addView.findViewById(R.id.txtContent);
                txtValue.setText(txtTimer.getText());
                container.addView(addView);
            }
        });

        //Click Clear
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timeSwapBuff=0L;
                startTime=0L;
                timeInMilliseconds=0L;
                updateTime=0L;

                txtTimer.setText("0:00:000");
            }
        });

        clearTap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                container.removeAllViews();
            }
        });


    }
}