package com.example.state.bean

import android.os.Parcel
import android.os.Parcelable

class UserState : Parcelable {

    val mall: String
        get() = _mall

    val userAction: String
        get() = _userAction

    private var _mall: String
    private var _userAction: String

    constructor(mall: String, userAction: String) {
        this._mall = mall
        this._userAction = userAction
    }

    private constructor(parcel: Parcel) {
        this._mall = parcel.readString()!!
        this._userAction = parcel.readString()!!
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(_mall)
        parcel.writeString(_userAction)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<UserState> {
        override fun createFromParcel(parcel: Parcel): UserState {
            return UserState(parcel)
        }

        override fun newArray(size: Int): Array<UserState?> {
            return arrayOfNulls(size)
        }
    }

    override fun toString(): String {
        return "Mall: $_mall, Action: $_userAction"
    }
}