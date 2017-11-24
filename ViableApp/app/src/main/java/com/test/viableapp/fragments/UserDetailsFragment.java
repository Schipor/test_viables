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
import com.test.viableapp.databinding.FragmentUserDetailsBinding;
import com.test.viableapp.fragments.base.BaseFragment;
import com.test.viableapp.fragments.callback.LayoutCallback;
import com.test.viableapp.http.models.RandomUser;
import com.test.viableapp.viewmodel.DetailsViewModel;


public class UserDetailsFragment extends BaseFragment implements LayoutCallback {

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
    public void onEmailClicked(String email) {
        if (email == null)
            return;
        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto",email, null));
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Viable Test APP");
        intent.putExtra(Intent.EXTRA_EMAIL, email);
        Intent mailer = Intent.createChooser(intent, null);
        startActivity(mailer);
    }

}
