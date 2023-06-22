package com.ort.tourismapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ort.tourismapp.R
import com.ort.tourismapp.entities.Guide
import com.google.firebase.storage.FirebaseStorage


class GuideAdapter(
    var guideList : MutableList<Guide>,
    var onClick : (Int) -> Unit
    ) : RecyclerView.Adapter<GuideAdapter.GuideHolder>() {

    class GuideHolder (v : View) : RecyclerView.ViewHolder(v){
        private var v = v
        init {
            this.v = v
        }
        fun setName(name : String){
            val txtName : TextView = v.findViewById(R.id.txtName)
            txtName.text = name
        }
        fun setCity(city : String){
            val txtCity : TextView = v.findViewById(R.id.txtCity)
            txtCity.text = city
        }
        fun setRate(rate : Int){
            val txtRate : TextView = v.findViewById(R.id.textRate)
            txtRate.text = rate.toString()
        }

        fun getBtn() : Button {
            return v.findViewById(R.id.btnGuide)
        }

        fun getImage(): ImageView{
            return v.findViewById(R.id.image_guide)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GuideHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_guide, parent, false)
        return GuideHolder(view)
    }

    override fun getItemCount(): Int {
        return guideList.size
    }

    override fun onBindViewHolder(holder: GuideHolder, position: Int) {
        holder.setName("${guideList[position].name} ${guideList[position].lastname}")
        holder.setCity(guideList[position].city)
        holder.setRate(guideList[position].rate)
        holder.getBtn().setOnClickListener{
            onClick(position)
        }

        Glide.with(holder.getImage())
            .load(guideList[position].displayPhoto)
            .centerCrop()
            .into(holder.getImage())
    }


}