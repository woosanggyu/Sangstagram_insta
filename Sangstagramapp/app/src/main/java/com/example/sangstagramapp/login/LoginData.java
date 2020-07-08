package com.example.sangstagramapp.login;

import com.google.gson.annotations.SerializedName;

public class LoginData{
    @SerializedName("id")
    String id;

    @SerializedName("password")
    String password;

    public LoginData(String id, String password) {
        this.id = id;
        this.password = password;
    }
}