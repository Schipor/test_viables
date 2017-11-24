package com.test.viableapp.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.test.viableapp.R;
import com.test.viableapp.widget.ZoomImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FullScreenPhotoActivity extends AppCompatActivity {

    public static final String EXTRA_URL = "EXTRA_URL";

    @BindView(R.id.full_screen)
    ZoomImageView imageView;
    @BindView(R.id.action_close)
    ImageView actionClose;

    private String imageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_photo);
        ButterKnife.bind(this);
        if (getIntent() != null) {
            if (getIntent().hasExtra(EXTRA_URL)) {
                imageUrl = getIntent().getStringExtra(EXTRA_URL);
                RequestOptions option = new RequestOptions();
                option.dontAnimate();
                option.centerCrop();
                option.diskCacheStrategy(DiskCacheStrategy.ALL);
                Glide.with(imageView.getContext()).applyDefaultRequestOptions(option).load(imageUrl).into(imageView);
            }
        }
    }

    @OnClick(R.id.action_close)
    public void onClose() {
        onBackPressed();
    }
}
