package com.example.city.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.city.adapter.CurrentCityRecyclerViewAdapter
import com.example.city.bean.CurrentCity
import com.example.city.data.Repository
import com.example.city.service.MyCurrentCityService

class CurrentCityViewModel : ViewModel() {

    val currentCity: LiveData<CurrentCity>
        get() =_currentCity
    private val _currentCity = MutableLiveData<CurrentCity>()

    private val data = Repository.getCityList()

    val mAdapter = CurrentCityRecyclerViewAdapter(data) {
        _currentCity.value = CurrentCity(it, data[it])

        val num = MyCurrentCityService.mListenerList.beginBroadcast()
        for(i in 0 until num) {
            val listener = MyCurrentCityService.mListenerList.getBroadcastItem(i)
            listener.mCallBack(CurrentCity(it,data[it]))
        }
        MyCurrentCityService.mListenerList.finishBroadcast()

    }
}