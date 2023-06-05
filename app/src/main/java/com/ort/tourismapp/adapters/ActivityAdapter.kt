package com.ort.tourismapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.ort.tourismapp.R
import com.ort.tourismapp.entities.Activity
import com.ort.tourismapp.entities.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ActivityAdapter(
    var activityList : MutableList<Activity>,
    var userRepository: UserRepository,
    var likedList : MutableList<String> = mutableListOf(),
    val userId: String = Firebase.auth.currentUser!!.uid,
    var onClick : (Int) -> Unit
    ) : RecyclerView.Adapter<ActivityAdapter.ActivityHolder>() {
    class ActivityHolder (v : View) : RecyclerView.ViewHolder(v){
        private var v = v
        init {
            this.v = v
        }
        fun setTitle(title : String){
            val txtTitle : TextView = v.findViewById(R.id.txtTitle)
            txtTitle.text = title
        }
        fun setCity(desc : String){
            val txtCity : TextView = v.findViewById(R.id.txtCity)
            txtCity.text = desc
        }
        fun setRate(rate : Int){
            val txtRate : TextView = v.findViewById(R.id.txtRate)
            txtRate.text = rate.toString()
        }
        fun getCard() : CardView {
            return v.findViewById(R.id.activityCard)
        }
        fun getBtn() : Button {
            return v.findViewById(R.id.btnActivity)
        }
        fun getCheckFavorito(list : MutableList<String>, activity : Activity) : CheckBox {
            var checkfav : CheckBox = v.findViewById(R.id.checkLike)

            checkfav.isChecked = list.contains(activity.title)

            return checkfav

        }
        fun getImage(): ImageView {
            return v.findViewById(R.id.image_activity)
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActivityHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_activity, parent, false)
        return ActivityHolder(view)
    }
    override fun getItemCount(): Int {
        return activityList.size
    }
    override fun onBindViewHolder(holder: ActivityHolder, position: Int) {
        val scope = CoroutineScope(Dispatchers.Main)
        scope.launch {
            likedList= userRepository.getFavouritesActivities(userId)

        holder.setTitle(activityList[position].title)
        holder.setCity(activityList[position].city)
        holder.setRate(activityList[position].rate)

        holder.getCheckFavorito(likedList, activityList[position])

            .setOnCheckedChangeListener { _, isChecked ->
                scope.launch {
                    if (isChecked) {
                        userRepository.addFavouriteActivity(userId, activityList[position].title)
                    } else if (!isChecked) {
                        userRepository.deleteFavouriteActivity(userId, activityList[position].title)
                    }
                }
            }
            }

        holder.getBtn().setOnClickListener{
            onClick(position)
        }
        Glide.with(holder.getImage())
            .load(activityList[position].activityPhoto)
            .centerCrop()
            .into(holder.getImage())
    }
        //TODO BOTON AGREGAR A FAVORITOS hacer que boton agregue actividad a lista de actividades favoritas y se vuelva naranja
        /*holder.getBtnFavorito().setOnClickListener{
            onClick(position)
        }*/
}