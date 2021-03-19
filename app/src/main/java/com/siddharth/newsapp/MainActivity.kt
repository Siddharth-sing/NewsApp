package com.siddharth.newsapp

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest


class MainActivity : AppCompatActivity(), NewsItemClicked {

    private lateinit var mAdapter: NewsListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView : RecyclerView = findViewById(R.id.recyclerView)


        println("adapter 1")
        recyclerView.layoutManager = LinearLayoutManager(this)
        println("adapter 2")
        fetch()
        println("adapter 3")
        mAdapter = NewsListAdapter(this)
        println("adapter 4")

        recyclerView.adapter = mAdapter
        println("adapter 5")
    }




  private fun fetch() {

      val url = "https://saurav.tech/NewsAPI/top-headlines/category/entertainment/in.json"


      val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
          {
              val newsJsonArray = it.getJSONArray("articles")
              val newsArray = ArrayList<News>()
              for(i in 0 until newsJsonArray.length() )
              {
                  val newsJsonObject = newsJsonArray.getJSONObject(i)
                  val news = News(
                      newsJsonObject.getString("title"),
                      newsJsonObject.getString("author"),
                      newsJsonObject.getString("url"),
                      newsJsonObject.getString("urlToImage")
                  )
                  newsArray.add(news)
              }
              mAdapter.updateNews(newsArray)
          },
          {
              Toast.makeText(this,"Error",Toast.LENGTH_SHORT).show()
          }
      )
      MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
  }

    override fun onItemClicked(clickedItem: News) {

        val builder = CustomTabsIntent.Builder()
        val customTabsIntent = builder.build()
        customTabsIntent.launchUrl(this, Uri.parse(clickedItem.url));
    }
}

