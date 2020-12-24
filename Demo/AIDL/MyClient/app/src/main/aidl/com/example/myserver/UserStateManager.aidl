// UserStateManager.aidl
package com.example.myserver;

import com.example.myserver.callback.UserStateChangeCallBack;

interface UserStateManager {
    void registerUserStateListener(UserStateChangeCallBack listener);
    void unregisterUserStateListener(UserStateChangeCallBack listener);
}