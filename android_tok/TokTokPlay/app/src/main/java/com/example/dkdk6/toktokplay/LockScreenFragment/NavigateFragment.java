package com.example.dkdk6.toktokplay.LockScreenFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dkdk6.toktokplay.FlagControl;
import com.example.dkdk6.toktokplay.R;

/**
 * Created by dkdk6 on 2017-05-08.
 */

public class NavigateFragment extends Fragment{
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i("LogTesting","z");
        FlagControl.APP_SEARCHING_CONTROL = 0;
                /*나중에삭제*/
        Log.i("LogTesting",""+FlagControl.APP_SEARCHING_CONTROL);
        View view = inflater.inflate(R.layout.navigatefragment,container,false);
        return view;
    }
}
