package com.test.viableapp.fragments;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.test.viableapp.R;
import com.test.viableapp.adapters.RandomUsersAdapter;
import com.test.viableapp.fragments.base.BaseFragment;
import com.test.viableapp.http.ApiManager;
import com.test.viableapp.http.impl.HttpCallback;
import com.test.viableapp.http.models.HttpError;
import com.test.viableapp.http.models.RandomUser;
import com.test.viableapp.widget.RoundedImageView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UsersListFragment extends BaseFragment implements RandomUsersAdapter.Callback {

    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private int page = 0;
    private int perPage = 20;
    private LinearLayoutManager layoutManager;
    private RandomUser.Response users;
    private RandomUsersAdapter adapter;

    //lazy load
    private int previousTotal = 0;
    private boolean loading = true;
    private int visibleThreshold = 1;
    private int firstVisibleItem, visibleItemCount, totalItemCount;
    private boolean existsMoreRecords = true;

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
                resetPagination();
                getRandomUsers();
            }
        });
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new RandomUsersAdapter(
                users == null || users.getResults() == null ? new ArrayList<RandomUser>() : new ArrayList<>(users.getResults()),
                this
        );
        recyclerView.setAdapter(adapter);
        handlePagination();

        if (users == null) {
            getRandomUsers();
        }
    }

    @Override
    public void onDestroyView() {
        //clear adapter and recyclerview if is not displayed
        //this prevents memory leaks
        recyclerView.setAdapter(null);
        adapter = null;
        super.onDestroyView();
    }

    private void getRandomUsers() {
        showLoading();
        ApiManager.instance().getUsers(
                page,
                perPage,
                new HttpCallback<RandomUser.Response>() {
                    @Override
                    public void onSuccess(RandomUser.Response response) {
                        swipeRefresh.setRefreshing(false);
                        dismissLoading();
                        if (response != null && response.getResults() != null) {
                            if (page == 0) {
                                users = response;
                                adapter.refresh(new ArrayList<RandomUser>(response.getResults()));
                            } else {
                                users.append(response.getResults());
                                adapter.addItems(new ArrayList<RandomUser>(users.getResults()));
                            }
                        }
                    }

                    @Override
                    public void onError(HttpError error) {
                        swipeRefresh.setRefreshing(false);
                        dismissLoading();
                        showAlert(null, error.getErrorMessage(), "OK", null, null);
                    }
                }
        );
    }

    private RequestOptions getDefaultOptions() {

        RequestOptions option = new RequestOptions();
        option.dontAnimate();
        option.centerCrop();
        option.diskCacheStrategy(DiskCacheStrategy.ALL);
        return option;
    }

    private void handlePagination() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                visibleItemCount = recyclerView.getChildCount();

                totalItemCount = layoutManager.getItemCount();
                firstVisibleItem = layoutManager.findFirstVisibleItemPosition();

                if (loading) {
                    if (totalItemCount > previousTotal) {
                        loading = false;
                        previousTotal = totalItemCount;
                    }
                }
                if (!loading && (totalItemCount - visibleItemCount)
                        <= (firstVisibleItem + visibleThreshold) && existsMoreRecords
                        && (swipeRefresh != null && !swipeRefresh.isRefreshing())) {
                    // End has been reached
                    loading = true;
                    page++;
                    getRandomUsers();
                }
            }
        });
    }

    private void resetPagination() {
        page = 0;
        totalItemCount = 0;
        firstVisibleItem = 0;
        previousTotal = 0;
        existsMoreRecords = true;
    }

    @Override
    public void onItemClicked(RandomUser user) {
        pushFragment(UserDetailsFragment.newInstance(user));
    }

    @Override
    public void setPhoto(final RoundedImageView imageView, String photoUrl) {
        if (photoUrl == null) {
            imageView.setImageBitmap(null);
        } else {
            Glide.with(imageView.getContext()).asBitmap()
                    .load(photoUrl)
                    .apply(getDefaultOptions())
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                            imageView.setImageBitmap(resource);
                        }
                    });
        }
    }
}
