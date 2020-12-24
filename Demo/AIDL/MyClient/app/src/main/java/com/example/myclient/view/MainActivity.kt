package com.example.myclient.view

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.*
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.myclient.R
import com.example.myclient.data.RetrofitHelper
import com.example.myclient.viewmodel.MainLifecycleObserver
import com.example.myclient.viewmodel.MainViewModel
import com.example.myserver.*
import com.example.myserver.bean.CurrentCity
import com.example.myserver.bean.UserState
import com.example.myserver.callback.CurrentCityChangeCallBack
import com.example.myserver.callback.UserStateChangeCallBack

class MainActivity : AppCompatActivity() {

    private lateinit var showCurrentCity: TextView
    private lateinit var showUserState: TextView

    private lateinit var viewModel: MainViewModel

    companion object{
        private const val TAG: String = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        showCurrentCity = findViewById(R.id.show_current_city)
        showUserState = findViewById(R.id.show_state)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        lifecycle.addObserver(MainLifecycleObserver(viewModel.mCurrentCityChangeCallBack, viewModel.mUserStateChangeCallBack))

        viewModel.currentCity.observe(this, Observer { showCurrentCity.text = it.cityName })
        viewModel.userState.observe(this, Observer { showUserState.text = it.toString() })
    }

}