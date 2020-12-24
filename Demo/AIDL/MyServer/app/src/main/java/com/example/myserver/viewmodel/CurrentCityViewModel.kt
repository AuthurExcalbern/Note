package com.example.myserver.viewmodel

import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.example.myserver.R
import com.example.myserver.adapter.CurrentCityRecyclerViewAdapter
import com.example.myserver.bean.CurrentCity
import com.example.myserver.data.Repository
import com.example.myserver.service.MyCurrentCityService

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