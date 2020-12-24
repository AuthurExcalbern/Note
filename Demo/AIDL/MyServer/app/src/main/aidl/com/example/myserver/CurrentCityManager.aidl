package com.example.myserver;


import com.example.myserver.callback.CurrentCityChangeCallBack;

interface CurrentCityManager {
    void registerCurrentCityListener(CurrentCityChangeCallBack listener);
    void unregisterCurrentCityListener(CurrentCityChangeCallBack listener);
}