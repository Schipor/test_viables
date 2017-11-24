package com.test.viableapp.http.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class UserId implements Parcelable {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("value")
    @Expose
    private String value;

    protected UserId(Parcel in) {
        name = in.readString();
        value = in.readString();
    }

    public static final Creator<UserId> CREATOR = new Creator<UserId>() {
        @Override
        public UserId createFromParcel(Parcel in) {
            return new UserId(in);
        }

        @Override
        public UserId[] newArray(int size) {
            return new UserId[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(value);
    }
}
