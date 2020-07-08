package com.example.sangstagramapp.login;

import com.google.gson.annotations.SerializedName;

public class JoinData {
    @SerializedName("id")
    private String id;

    @SerializedName("password")
    private String password;

    public JoinData(String id, String password) {
        this.id = id;
        this.password = password;
    }
}