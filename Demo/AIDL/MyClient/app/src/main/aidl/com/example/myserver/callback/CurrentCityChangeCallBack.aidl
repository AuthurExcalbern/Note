package com.example.myserver.callback;

import com.example.myserver.bean.CurrentCity;

interface CurrentCityChangeCallBack {
    void mCallBack(in CurrentCity newCity);
}