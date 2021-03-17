package com.abodsy.abodsy;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.abodsy.abodsy.Main_App.AccountFragment;
import com.abodsy.abodsy.Main_App.HomeFragment;
import com.abodsy.abodsy.Main_App.MyBookingsFragment;

public class TabAdapter extends FragmentPagerAdapter {

    /* TAB RELATED VARIABLES */
    final int total_tabs = 3;
//    private String tab_titles[] = new String[]{"HOME", "MY BOOKINGS", "ACCOUNT"};

    public TabAdapter(@NonNull FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    @NonNull
    @Override
    /* Return the Fragment associated with a specified position. */
    public Fragment getItem(int position) {
        if (position == 0) {
            return new HomeFragment();
        } else if (position == 1) {
            return new MyBookingsFragment();
        } else {
            return new AccountFragment();
        }
    }

    @Override
    public int getCount() {
        return total_tabs;
    }
}
