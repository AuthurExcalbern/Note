package com.example.myserver.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myserver.MyApplication.Companion.context
import com.example.myserver.R

class CurrentCityRecyclerViewAdapter(
    private val cityData: List<String>,
    private var mOnItemClickListener: MyRecyclerViewOnClickInterface
): RecyclerView.Adapter<CurrentCityRecyclerViewAdapter.MyViewHolder>() {

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val view = layoutInflater.inflate(R.layout.current_city_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = holder.itemView.findViewById<TextView>(R.id.city_item)
        item.text = cityData[position]
        item.setOnClickListener {
            //回调
            mOnItemClickListener.onItemClick(position)
        }
    }

    override fun getItemCount(): Int = cityData.size
}