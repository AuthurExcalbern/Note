package com.example.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.example.city.view.CurrentCityFragment
import com.example.state.view.StateChangeFragment
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_main)

        val viewPager = findViewById<ViewPager>(R.id.my_viewpager)
        val currentCityFragment = CurrentCityFragment()
        val stateChangeFragment = StateChangeFragment()

        val fragmentList = ArrayList<Fragment>()
        fragmentList.add(currentCityFragment)
        fragmentList.add(stateChangeFragment)

        val fragmentAdapter = MyFragmentAdapter(fragmentList, this.supportFragmentManager)
        viewPager.adapter = fragmentAdapter

        val tabLayout = findViewById<TabLayout>(R.id.tabLayout)
        tabLayout.setupWithViewPager(viewPager)

    }
}