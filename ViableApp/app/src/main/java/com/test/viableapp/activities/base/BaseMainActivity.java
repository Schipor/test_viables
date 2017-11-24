package com.test.viableapp.activities.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.test.viableapp.R;
import com.test.viableapp.fragments.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public abstract class BaseMainActivity extends AppCompatActivity implements BaseFragment.BaseFragmentCallback {

    @BindView(R.id.action_back)
    ImageView actionBack;
    @BindView(R.id.main_title)
    TextView mainTitle;
    @BindView(R.id.activity_base_fragment_container)
    FrameLayout fragmentContainer;

    private FragmentManager mFragmentManager;

    public abstract int getLayoutResource();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResource());
        ButterKnife.bind(this);

        mFragmentManager = getSupportFragmentManager();


    }

    @OnClick(R.id.action_back)
    public void onActionBackClicked() {
        //created custom back pressed to handle the fragment stack if required
        onBackPressed();
    }

    @Override
    public void pushFragment(Fragment fragment) {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        Fragment inflatedFragment = mFragmentManager.findFragmentById(fragmentContainer.getId());

        if (fragment == null) return;

        if (inflatedFragment != null &&
                inflatedFragment.getClass().getSimpleName().equals(fragment.getClass().getSimpleName())) {
            //prevent adding duplicate fragment
            return;
        }

        transaction.setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        transaction.replace(fragmentContainer.getId(), fragment, fragment.getClass().getSimpleName());
        transaction.addToBackStack(fragment.getClass().getSimpleName());
        if (isFinishing() || isDestroyed()) {
            return;
        }
        transaction.commitAllowingStateLoss();
    }
}
