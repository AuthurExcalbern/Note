package com.example.city.bean

import android.os.Parcel
import android.os.Parcelable

class CurrentCity : Parcelable {

    val cityId: Int
        get() = _cityId
    val cityName: String
        get() = _cityName

    private var _cityId: Int
    private var _cityName: String

    constructor(cityId: Int, cityName: String) {
        this._cityId = cityId
        this._cityName = cityName
    }

    private constructor(parcel: Parcel) {
        this._cityId = parcel.readInt()
        this._cityName = parcel.readString()!!
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(_cityId)
        parcel.writeString(_cityName)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CurrentCity> {
        override fun createFromParcel(parcel: Parcel): CurrentCity {
            return CurrentCity(parcel)
        }

        override fun newArray(size: Int): Array<CurrentCity?> {
            return arrayOfNulls(size)
        }
    }

    override fun toString(): String {
        return "ID: $_cityId, Name: $_cityName"
    }
}