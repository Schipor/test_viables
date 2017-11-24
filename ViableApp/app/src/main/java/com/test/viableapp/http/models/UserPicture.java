package com.test.viableapp.http.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author Sebastian Schipor <sebastian.schipor@osf-global.com> on 24/11/2017.
 */

public class UserPicture implements Parcelable{

    @SerializedName("large")
    @Expose
    private String large;
    @SerializedName("medium")
    @Expose
    private String medium;
    @SerializedName("thumbnail")
    @Expose
    private String thumbnail;

    protected UserPicture(Parcel in) {
        large = in.readString();
        medium = in.readString();
        thumbnail = in.readString();
    }

    public static final Creator<UserPicture> CREATOR = new Creator<UserPicture>() {
        @Override
        public UserPicture createFromParcel(Parcel in) {
            return new UserPicture(in);
        }

        @Override
        public UserPicture[] newArray(int size) {
            return new UserPicture[size];
        }
    };

    public String getLarge() {
        return large;
    }

    public void setLarge(String large) {
        this.large = large;
    }

    public String getMedium() {
        return medium;
    }

    public void setMedium(String medium) {
        this.medium = medium;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(large);
        dest.writeString(medium);
        dest.writeString(thumbnail);
    }
}
