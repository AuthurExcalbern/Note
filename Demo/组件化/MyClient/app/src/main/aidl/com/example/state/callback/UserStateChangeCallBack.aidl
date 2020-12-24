// UserStateChangeCallBack.aidl
package com.example.state.callback;

import com.example.state.bean.UserState;

interface UserStateChangeCallBack {
    void mCallBack(in UserState userState);
}