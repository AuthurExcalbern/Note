package com.example.mynewsapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.mynewsapp.R
import com.example.mynewsapp.adapter.NewsRecyclerViewAdapter
import com.example.mynewsapp.di.ItemDecoration
import com.example.mynewsapp.models.NewsPresentation
import com.example.mynewsapp.models.states.NewsViewState
import com.example.mynewsapp.viewmodel.NewsViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var refreshLayout: SwipeRefreshLayout
    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerAdapter: NewsRecyclerViewAdapter
    private lateinit var viewModel: NewsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        refreshLayout = findViewById(R.id.swipe_refresh_layout)

        recyclerView = findViewById(R.id.news_recyclerview)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(ItemDecoration(this))

        recyclerAdapter = NewsRecyclerViewAdapter(this)
        recyclerView.adapter = recyclerAdapter

        viewModel = ViewModelProvider(this).get(NewsViewModel::class.java)

        viewModel.newsViewState.observe(this) { state ->

            handleLoading(state)

            state.newsList.let {
                if (it.isNotEmpty()) {
                    Log.d(TAG, "handle news")
                    handleNews(it)
                } else {
                    Log.d(TAG, "News List is empty")
                }
            }

            handleError(state)

        }

        refreshLayout.setOnRefreshListener {
            viewModel.getRemoteNews()
        }
    }

    private fun handleLoading(state: NewsViewState) {
        refreshLayout.isRefreshing = state.isLoading
    }

    private fun handleError(state: NewsViewState) {
        state.error?.run {
            Log.d(TAG, getString(this.message))
        }
    }

    private fun handleNews(newsList: List<NewsPresentation>) {
        recyclerAdapter.setList(newsList)
    }

    companion object {
        const val TAG = "MainActivity"
    }
}