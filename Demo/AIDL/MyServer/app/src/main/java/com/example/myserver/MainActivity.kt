package com.example.myserver

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.example.myserver.adapter.MyFragmentAdapter
import com.example.myserver.view.CurrentCityFragment
import com.example.myserver.view.StateChangeFragment
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_main)

        val viewPager = findViewById<ViewPager>(R.id.my_viewpager)
        val cityFragment = CurrentCityFragment()
        val stateFragment = StateChangeFragment()

        val fragmentList = ArrayList<Fragment>()
        fragmentList.add(cityFragment)
        fragmentList.add(stateFragment)

        val fragmentAdapter = MyFragmentAdapter(fragmentList, this.supportFragmentManager)
        viewPager.adapter = fragmentAdapter

        val tabLayout = findViewById<TabLayout>(R.id.tabLayout)
        tabLayout.setupWithViewPager(viewPager)

    }
}