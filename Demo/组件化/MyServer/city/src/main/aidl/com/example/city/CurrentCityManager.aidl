package com.example.city;


import com.example.city.callback.CurrentCityChangeCallBack;

interface CurrentCityManager {
    void registerCurrentCityListener(CurrentCityChangeCallBack listener);
    void unregisterCurrentCityListener(CurrentCityChangeCallBack listener);
}