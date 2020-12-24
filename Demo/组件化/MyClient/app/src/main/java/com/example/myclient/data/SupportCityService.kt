package com.example.myclient.data

import com.example.myclient.bean.SupportCity
import retrofit2.Call
import retrofit2.http.GET

interface SupportCityService {
    @GET("URL")
    fun getSupportCityList() : Call<List<SupportCity>>
}