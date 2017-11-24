package com.test.viableapp.fragments.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;


public abstract class BaseFragment extends Fragment {

    private BaseFragmentCallback mCallback;

    public interface BaseFragmentCallback {

        void pushFragment(Fragment fragment);

    }

    protected abstract View createView(LayoutInflater inflater, ViewGroup container, @Nullable Bundle savedInstanceState);

    protected abstract void initialize(View view, @Nullable Bundle savedInstanceState);

    protected abstract void customize(@Nullable Bundle savedInstanceState);

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity mActivity = (Activity) context;
        if (mActivity instanceof BaseFragmentCallback) {
            mCallback = (BaseFragmentCallback) getActivity();
        } else {
            throw new ClassCastException(String.format("%s must implement BaseFragment.Callback", mActivity.toString()));
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = createView(inflater, container, savedInstanceState);

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        initialize(view, savedInstanceState);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (isAdded()) {
            customize(savedInstanceState);
        }
    }
}
