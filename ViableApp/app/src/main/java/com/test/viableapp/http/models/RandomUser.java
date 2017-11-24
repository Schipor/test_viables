package com.test.viableapp.http.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class RandomUser implements Parcelable {

    public class Response {
        @SerializedName("results")
        @Expose
        private List<RandomUser> results;

        public List<RandomUser> getResults() {
            return results;
        }

        public void setResults(List<RandomUser> results) {
            this.results = results;
        }

        public void append(List<RandomUser> results) {
            if (this.results == null) {
                this.results = new ArrayList<>();
            }
            if (results != null)
                this.results.addAll(results);
        }
    }

    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("name")
    @Expose
    private UserName name;
    @SerializedName("location")
    @Expose
    private UserLocation location;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("login")
    @Expose
    private UserLogin login;
    @SerializedName("dob")
    @Expose
    private String dob;
    @SerializedName("registered")
    @Expose
    private String registered;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("cell")
    @Expose
    private String cell;
    @SerializedName("id")
    @Expose
    private UserId id;
    @SerializedName("picture")
    @Expose
    private UserPicture picture;
    @SerializedName("nat")
    @Expose
    private String nat;

    protected RandomUser(Parcel in) {
        gender = in.readString();
        name = in.readParcelable(UserName.class.getClassLoader());
        location = in.readParcelable(UserLocation.class.getClassLoader());
        email = in.readString();
        login = in.readParcelable(UserLogin.class.getClassLoader());
        dob = in.readString();
        registered = in.readString();
        phone = in.readString();
        cell = in.readString();
        id = in.readParcelable(UserId.class.getClassLoader());
        picture = in.readParcelable(UserPicture.class.getClassLoader());
        nat = in.readString();
    }

    public static final Creator<RandomUser> CREATOR = new Creator<RandomUser>() {
        @Override
        public RandomUser createFromParcel(Parcel in) {
            return new RandomUser(in);
        }

        @Override
        public RandomUser[] newArray(int size) {
            return new RandomUser[size];
        }
    };

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public UserName getName() {
        return name;
    }

    public void setName(UserName name) {
        this.name = name;
    }

    public UserLocation getLocation() {
        return location;
    }

    public void setLocation(UserLocation location) {
        this.location = location;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserLogin getLogin() {
        return login;
    }

    public void setLogin(UserLogin login) {
        this.login = login;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getRegistered() {
        return registered;
    }

    public void setRegistered(String registered) {
        this.registered = registered;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCell() {
        return cell;
    }

    public void setCell(String cell) {
        this.cell = cell;
    }

    public UserId getId() {
        return id;
    }

    public void setId(UserId id) {
        this.id = id;
    }

    public UserPicture getPicture() {
        return picture;
    }

    public void setPicture(UserPicture picture) {
        this.picture = picture;
    }

    public String getNat() {
        return nat;
    }

    public void setNat(String nat) {
        this.nat = nat;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(gender);
        dest.writeParcelable(name, flags);
        dest.writeParcelable(location, flags);
        dest.writeString(email);
        dest.writeParcelable(login, flags);
        dest.writeString(dob);
        dest.writeString(registered);
        dest.writeString(phone);
        dest.writeString(cell);
        dest.writeParcelable(id, flags);
        dest.writeParcelable(picture, flags);
        dest.writeString(nat);
    }
}
