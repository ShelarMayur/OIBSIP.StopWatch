package com.example.stopwatch;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView mStopwatchTextView, afterPause;
    private Button mStartButton, mStopButton, mPauseButton;
    private long mStartTime = 0L;
    private Handler mHandler = new Handler();
    private long mTimeInMilliseconds = 0L;
    private long mTimeSwapBuff = 0L;
    private long mUpdateTime = 0L;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mStopwatchTextView = findViewById(R.id.timerTextView);
        afterPause = findViewById(R.id.afterPause);
        mStartButton = findViewById(R.id.start);
        mStopButton = findViewById(R.id.stop);
        mPauseButton = findViewById(R.id.pause);

        mStopwatchTextView.setText("00:00:00");

        mStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mStartTime = SystemClock.uptimeMillis();
                mHandler.postDelayed(updateTimer, 0);
            }
        });

        mPauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTimeSwapBuff += mTimeInMilliseconds;
                mHandler.removeCallbacks(updateTimer);
                if(afterPause.getText().toString().equals((""))){
                    afterPause.setText(mStopwatchTextView.getText().toString());
                } else {
                    afterPause.setText(afterPause.getText().toString() + "\n" + mStopwatchTextView.getText().toString());
                }
            }
        });

        mStopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mStartTime = 0L;
                mTimeInMilliseconds = 0L;
                mTimeSwapBuff = 0L;
                mUpdateTime = 0L;
                mStopwatchTextView.setText("00:00:00");
                afterPause.setText("");
            }
        });
    }
    private Runnable updateTimer = new Runnable() {
        public void run() {
            mTimeInMilliseconds = SystemClock.uptimeMillis() - mStartTime;
            mUpdateTime = mTimeSwapBuff + mTimeInMilliseconds;
            int seconds = (int) (mUpdateTime / 1000);
            int minutes = seconds / 60;
            seconds = seconds % 60;
            int milliseconds = (int) (mUpdateTime % 1000);
            mStopwatchTextView.setText(""+ minutes + ":" + String.format("%02d",seconds)+":"+String.format("%03d",milliseconds));
            mHandler.postDelayed(this, 0);
        }
    };
}