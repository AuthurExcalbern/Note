package com.example.mynewsapp.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mynewsapp.R
import com.example.mynewsapp.models.NewsPresentation
import com.example.mynewsapp.view.MainActivity
import com.example.mynewsapp.view.NewsDetailActivity

internal class NewsRecyclerViewAdapter(
    private val context: Context
): RecyclerView.Adapter<NewsRecyclerViewAdapter.MyViewHolder>() {

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private var newsData: List<NewsPresentation> = emptyList()

    fun setList(newsList: List<NewsPresentation>) {
        newsData = newsList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val view = layoutInflater.inflate(R.layout.news_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            val intent = Intent(context, NewsDetailActivity::class.java)
            intent.putExtra("data", newsData[position])
            context.startActivity(intent)
        }

        val title = holder.itemView.findViewById<TextView>(R.id.news_title)
        val time = holder.itemView.findViewById<TextView>(R.id.news_time)
        val image = holder.itemView.findViewById<ImageView>(R.id.news_image)

        title.text = newsData[position].title
        time.text = newsData[position].time
        Glide.with(context).load(newsData[position].pic).into(image)
    }

    override fun getItemCount(): Int = newsData.size
}