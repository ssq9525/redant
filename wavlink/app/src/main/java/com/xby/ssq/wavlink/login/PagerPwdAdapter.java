package com.xby.ssq.wavlink.login;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;

import com.xby.ssq.wavlink.R;

public class PagerPwdAdapter extends FragmentPagerAdapter {

    private int[] mTitleImage;
    private Context mContext;
    private String[] mTitle;

    public PagerPwdAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.mContext = context;

    }

    @Override
    public CharSequence getPageTitle(int position) {

        if (position==0) return this.mContext.getResources().getString(R.string.menu_local);
        return this.mContext.getResources().getString(R.string.menu_remote);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position)
        {
            case 0:
                return new LoginFragment();

            case 1:
                return new RemoteFragment();//LocalFragment

            default:
                break;
        }
        return new RemoteFragment();
    }

    @Override
    public int getCount() {
        return 2;
    }


}