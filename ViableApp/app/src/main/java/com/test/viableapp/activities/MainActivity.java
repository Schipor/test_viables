package com.test.viableapp.activities;
import android.os.Bundle;

import com.test.viableapp.R;
import com.test.viableapp.activities.base.BaseMainActivity;
import com.test.viableapp.fragments.UsersListFragment;

public class MainActivity extends BaseMainActivity {

    @Override
    public int getLayoutResource() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //on first load show list fragment
        pushFragment(new UsersListFragment());
    }
}
