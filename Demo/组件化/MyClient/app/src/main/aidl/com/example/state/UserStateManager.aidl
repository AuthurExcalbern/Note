// UserStateManager.aidl
package com.example.state;

import com.example.state.callback.UserStateChangeCallBack;

interface UserStateManager {
    void registerUserStateListener(UserStateChangeCallBack listener);
    void unregisterUserStateListener(UserStateChangeCallBack listener);
}