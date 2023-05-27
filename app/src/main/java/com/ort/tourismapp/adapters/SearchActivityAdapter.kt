package com.ort.tourismapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ort.tourismapp.R
import com.ort.tourismapp.entities.Activity


class SearchActivityAdapter (
    var activitySearchList : MutableList<Activity>,
    var onClick : (Int) -> Unit
    ) : RecyclerView.Adapter<SearchActivityAdapter.SearchActivityHolder>(){

    class SearchActivityHolder(v : View) : RecyclerView.ViewHolder(v){
        private var v = v
        init {
            this.v = v
        }
        fun setTitle(title : String){
            val txtTitle : TextView = v.findViewById(R.id.txtActivity_ResultSearch)
            txtTitle.text = title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchActivityHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_activity_search_result, parent, false)
        return SearchActivityHolder(view)
    }

    override fun getItemCount(): Int {
        return activitySearchList.size
    }

    override fun onBindViewHolder(holder: SearchActivityHolder, position: Int) {
        holder.setTitle(activitySearchList[position].title)
    }

    fun filter(filteredActivities: MutableList<Activity>) {
        this.activitySearchList = filteredActivities
        notifyDataSetChanged()
    }



}