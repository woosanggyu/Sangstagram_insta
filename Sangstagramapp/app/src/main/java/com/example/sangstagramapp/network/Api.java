package com.example.sangstagramapp.network;


import com.example.sangstagramapp.login.JoinData;
import com.example.sangstagramapp.login.JoinResponse;
import com.example.sangstagramapp.login.LoginData;
import com.example.sangstagramapp.login.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface Api{
    @POST("/api/signin")
    Call<LoginResponse> userLogin(@Body LoginData data);

    @POST("/api/signup")
    Call<JoinResponse> userJoin(@Body JoinData data);
}
