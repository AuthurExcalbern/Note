package com.example.myclient.data

import com.example.myclient.bean.SupportCity
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {
    fun getSupportCityList(): List<SupportCity> {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://URL")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val supportCityService = retrofit.create(SupportCityService::class.java)
        val call : Call<List<SupportCity>> = supportCityService.getSupportCityList()

        return call.enqueue()
    }
}

private fun <T> Call<T>.enqueue() : List<SupportCity> {
    val localSupportCityList = ArrayList<SupportCity>()
    localSupportCityList.add(SupportCity(1,"北京"))
    localSupportCityList.add(SupportCity(2,"上海"))
    localSupportCityList.add(SupportCity(3,"广州"))
    localSupportCityList.add(SupportCity(4,"深圳"))
    return localSupportCityList
}
