package com.ort.tourismapp.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.ort.tourismapp.R
import com.ort.tourismapp.entities.Activity
import com.ort.tourismapp.entities.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class ActivityAdapter(
    var activityList : MutableList<Activity>,
    var userRepository: UserRepository = UserRepository(),

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

        fun getBtnFavorito() :Button {
            return v.findViewById(R.id.btnFavorito)
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

        holder.setTitle(activityList[position].title)
        holder.setCity(activityList[position].city)
        holder.setRate(activityList[position].rate)

        holder.getBtn().setOnClickListener {
            onClick(position)
        }
        holder.getBtnFavorito().setOnClickListener {
            scope.launch {
                Log.d("getBtnFavoritos","entra boton")
                async {
                    Log.d("async","entra async")
                    userRepository.addDeleteFavouriteActivity(userId, activityList[position].title)
                }
            }
        }
        //TODO BOTON AGREGAR A FAVORITOS hacer que boton agregue actividad a lista de actividades favoritas y se vuelva naranja
        /*holder.getBtnFavorito().setOnClickListener{
            onClick(position)
        }*/
    }
}