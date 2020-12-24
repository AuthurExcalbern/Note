package com.example.myclient.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.example.city.bean.CurrentCity
import com.example.city.callback.CurrentCityChangeCallBack
import com.example.myclient.data.RetrofitHelper
import com.example.state.bean.UserState
import com.example.state.callback.UserStateChangeCallBack

class MainViewModel: ViewModel() {

    val currentCity: LiveData<CurrentCity>
        get() = _currentCity
    private val _currentCity = MutableLiveData<CurrentCity>()

    val userState: LiveData<UserState>
        get() =_userState
    private val _userState = MutableLiveData<UserState>()

    init {
        _currentCity.value = CurrentCity(-1, "Default")
        _userState.value = UserState("Default Mall", "Default Action")
    }


    private val supportCityList = RetrofitHelper.getSupportCityList()

    val mCurrentCityChangeCallBack = object : CurrentCityChangeCallBack.Stub() {
        override fun mCallBack(newCity: CurrentCity) {
            if (isSupportCity(newCity)) {
                Log.d(TAG, "debug 1")
                _currentCity.postValue(newCity)
            }
        }
    }

    val mUserStateChangeCallBack = object : UserStateChangeCallBack.Stub() {
        override fun mCallBack(newUserState: UserState?) {
            Log.d(TAG, "callback")
            _userState.postValue(newUserState)
        }
    }

    private fun isSupportCity(newCity: CurrentCity): Boolean {
        for (i in supportCityList) {
            if (i.name == newCity.cityName) return true
        }
        return false
    }

    companion object {
        private const val TAG: String = "MainViewModel"
    }
}