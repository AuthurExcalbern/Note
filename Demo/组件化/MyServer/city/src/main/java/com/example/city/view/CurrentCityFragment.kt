package com.example.city.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.city.R
import com.example.city.viewmodel.CurrentCityViewModel

class CurrentCityFragment: Fragment() {

    private lateinit var viewModel: CurrentCityViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.current_city_page, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.current_city_recyclerview)

        viewModel = ViewModelProvider(this).get(CurrentCityViewModel::class.java)

        viewModel.currentCity.observe(viewLifecycleOwner, Observer {
            val textView = view.findViewById<TextView>(R.id.show_select_city)
            textView.text = it.cityName
        })

        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = viewModel.mAdapter

        return view
    }
}