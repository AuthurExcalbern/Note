// UserStateChangeCallBack.aidl
package com.example.myserver.callback;

import com.example.myserver.bean.UserState;

interface UserStateChangeCallBack {
    void mCallBack(in UserState userState);
}