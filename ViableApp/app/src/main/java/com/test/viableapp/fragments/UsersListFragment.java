package com.test.viableapp.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.test.viableapp.R;
import com.test.viableapp.fragments.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UsersListFragment extends BaseFragment {

    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;


    @Override
    protected View createView(LayoutInflater inflater, ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_users_list, container, false);
    }

    @Override
    protected void initialize(View view, @Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this, view);
        showBackButton(false);
        setCustomTitle("Random Users");
    }

    @Override
    protected void customize(@Nullable Bundle savedInstanceState) {
        swipeRefresh.setColorSchemeColors(ContextCompat.getColor(getActivity(), R.color.colorAccent));
        swipeRefresh.setDistanceToTriggerSync(400);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getRandomUsers();
            }
        });
    }

    private void getRandomUsers() {

    }
}
