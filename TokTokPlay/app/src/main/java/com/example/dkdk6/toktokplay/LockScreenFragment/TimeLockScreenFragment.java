package com.example.dkdk6.toktokplay.LockScreenFragment;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.os.Handler;

import com.example.dkdk6.toktokplay.Activity.LockScreenActivity;
import com.example.dkdk6.toktokplay.R;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by dkdk6 on 2017-05-08.
 */

public class TimeLockScreenFragment extends Fragment {
    private TextView timeValueDate, timeValueTime, timeValueA;
    private Handler handler;
    private int touchCount = 0;
    private static final int TIMER_TICK_ID = 0;
    private ImageView mainImage;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.timefragment, container, false);
        mainImage = (ImageView)view.findViewById(R.id.titleView);
        timeValueDate = (TextView) view.findViewById(R.id.timeFragmenDate);
        timeValueTime = (TextView) view.findViewById(R.id.timeFragmentTime);
        timeValueA = (TextView) view.findViewById(R.id.timeFragmentTimeA);
        mainImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                touchCount++;
                if (touchCount >= 2) {
                    touchCount = 0;
                    ((LockScreenActivity) getActivity()).finish();
                }
            }
        });
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == TIMER_TICK_ID) {
                    Date today = new Date();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy년 MM월 dd일");
                    SimpleDateFormat timeFormat = new SimpleDateFormat("hh : mm");
                    SimpleDateFormat timeFormat3 = new SimpleDateFormat("a");
                    timeValueDate.setText("" + dateFormat.format(today));
                    timeValueTime.setText("" + timeFormat.format(today));
                    timeValueA.setText("" + timeFormat3.format(today));
                    touchCount = 0;
                    sendEmptyMessageDelayed(TIMER_TICK_ID, 1000);
                }
            }
        };
        handler.sendEmptyMessage(TIMER_TICK_ID);
          return view;
    }

}
