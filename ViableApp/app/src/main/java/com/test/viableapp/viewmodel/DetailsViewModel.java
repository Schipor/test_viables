package com.test.viableapp.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.BindingAdapter;
import android.graphics.Color;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.test.viableapp.http.models.RandomUser;
import com.test.viableapp.utils.DateUtils;


public class DetailsViewModel extends BaseObservable {

    private RandomUser user;

    public DetailsViewModel(RandomUser user) {
        this.user = user;
    }

    @Bindable
    public String getName() {
        if (user.getName() != null) {
            return String.format("%1$s %2$s %3$s", user.getName().getTitle(), user.getName().getFirst(), user.getName().getLast());
        } else {
            return "";
        }
    }

    @Bindable
    public String getAge() {
        return DateUtils.getUserAge(user.getDob());
    }

    @Bindable
    public String getEmail() {
        return user.getEmail();
    }

    @Bindable
    public String getPhoto() {
        if (user.getPicture() != null) {
            return user.getPicture().getLarge();
        } else {
            return null;
        }
    }

    @BindingAdapter("imageUrl")
    public static void setImageUrl(ImageView imageView, String url) {
        if (url != null) {
            RequestOptions option = new RequestOptions();
            option.dontAnimate();
            option.centerCrop();
            option.diskCacheStrategy(DiskCacheStrategy.ALL);
            Glide.with(imageView.getContext()).applyDefaultRequestOptions(option).load(url).into(imageView);
        } else {
            imageView.setBackgroundColor(Color.GRAY);
        }
    }
}
