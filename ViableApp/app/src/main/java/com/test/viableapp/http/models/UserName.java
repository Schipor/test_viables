package com.test.viableapp.http.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserName implements Parcelable {

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("first")
    @Expose
    private String first;
    @SerializedName("last")
    @Expose
    private String last;

    protected UserName(Parcel in) {
        title = in.readString();
        first = in.readString();
        last = in.readString();
    }

    public static final Creator<UserName> CREATOR = new Creator<UserName>() {
        @Override
        public UserName createFromParcel(Parcel in) {
            return new UserName(in);
        }

        @Override
        public UserName[] newArray(int size) {
            return new UserName[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(first);
        dest.writeString(last);
    }
}
