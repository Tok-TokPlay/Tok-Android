package com.example.dkdk6.toktokplay.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.dkdk6.toktokplay.LockScreenFragment.NavigateFragment;
import com.example.dkdk6.toktokplay.LockScreenFragment.TimeLockScreenFragment;

/**
 * Created by dkdk6 on 2017-05-08.
 * 뷰페이저에 기본적으로 뷰를 그려주는 애 -> 뷰페이저
 * 여기에다 뭐를 그려라 알려주는게 어댑터!
 */

public class LockScreenFragmentPagerAdapter extends FragmentStatePagerAdapter {
    public static final int PosTimeLock= 0;
    public static final int PosNavigate = 1;
    TimeLockScreenFragment timeLockScreenFragment;
    NavigateFragment navigateFragment;
    public LockScreenFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
            navigateFragment = new NavigateFragment();
            timeLockScreenFragment = new TimeLockScreenFragment();
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case  PosTimeLock:
                return timeLockScreenFragment;
            case PosNavigate :
                return navigateFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
