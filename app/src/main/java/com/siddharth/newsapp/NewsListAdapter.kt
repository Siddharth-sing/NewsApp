package com.siddharth.newsapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.bumptech.glide.Glide
import org.w3c.dom.Text

class NewsListAdapter(private val listener: NewsItemClicked ) : Adapter<NewsViewHolder>() {

    private val items: ArrayList<News> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.newsitem,parent,false)
        val viewHolder = NewsViewHolder(view)
        view.setOnClickListener{
            listener.onItemClicked(items[viewHolder.adapterPosition])
        }
        return viewHolder
    }

    override fun getItemCount(): Int {
        return items.size
    }
    override fun onBindViewHolder(holder: NewsViewHolder, position: Int){

        val currentItem = items[position]
        holder.titleView.text = currentItem.title
        holder.author.text=currentItem.author
        Glide.with(holder.itemView.context).load(currentItem.imageUrl).into(holder.image)
    }

    fun updateNews(updatedNews : ArrayList<News>)
    {
        items.addAll(updatedNews)
        notifyDataSetChanged()
    }

}

class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val titleView : TextView = itemView.findViewById(R.id.titleView)
    val image:ImageView = itemView.findViewById(R.id.imgNews)
    val author:TextView = itemView.findViewById(R.id.txtAuthor)

}

interface NewsItemClicked{

    fun onItemClicked(clickedItem: News)
}