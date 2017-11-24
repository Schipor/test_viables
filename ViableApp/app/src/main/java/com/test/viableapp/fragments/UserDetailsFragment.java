package com.test.viableapp.fragments;


import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.test.viableapp.R;
import com.test.viableapp.activities.FullScreenPhotoActivity;
import com.test.viableapp.databinding.FragmentUserDetailsBinding;
import com.test.viableapp.fragments.base.BaseFragment;
import com.test.viableapp.fragments.callback.DetailsLayoutCallback;
import com.test.viableapp.http.models.RandomUser;
import com.test.viableapp.viewmodel.DetailsViewModel;


public class UserDetailsFragment extends BaseFragment implements DetailsLayoutCallback {

    private RandomUser user;
    private FragmentUserDetailsBinding binding;

    public UserDetailsFragment() {
        // Required empty public constructor
    }

    public static UserDetailsFragment newInstance(RandomUser user) {
        UserDetailsFragment fragment = new UserDetailsFragment();
        Bundle args = new Bundle();
        args.putParcelable(EXTRA_OBJECT, user);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected View createView(LayoutInflater inflater, ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_user_details,
                container,
                false
        );
        return binding.getRoot();
    }

    @Override
    protected void initialize(View view, @Nullable Bundle savedInstanceState) {
        getCustomArguments();
        showBackButton(true);
        setCustomTitle("Details");
    }

    private void getCustomArguments() {
        if (getArguments() != null) {
            if (getArguments().containsKey(EXTRA_OBJECT)) {
                user = getArguments().getParcelable(EXTRA_OBJECT);
            }
        }
    }

    @Override
    protected void customize(@Nullable Bundle savedInstanceState) {
        binding.setCallback(this);
        binding.setModel(new DetailsViewModel(user));
    }

    @Override
    public void onPhoneClicked(String phone) {
        if (phone == null)
            return;
        //open dialer
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse(String.format("tel:%s", phone)));
        startActivity(intent);
    }

    @Override
    public void onEmailClicked(String email) {
        if (email == null)
            return;
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Viable Test APP");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{email});
        Intent mailer = Intent.createChooser(intent, null);
        startActivity(mailer);
    }

    @Override
    public void onPhotoClicked(String imageUrl) {
        Intent intent = new Intent(getActivity(), FullScreenPhotoActivity.class);
        intent.putExtra(FullScreenPhotoActivity.EXTRA_URL, imageUrl);
        startActivity(intent);
    }

}
