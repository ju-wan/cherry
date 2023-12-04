package com.example.cherry.slider

import android.content.Context
import android.media.MediaPlayer.OnCompletionListener
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cherry.R
import com.example.cherry.auth.UserDataModel
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class CardStackAdapter(val context : Context, val items : List<UserDataModel>) : RecyclerView.Adapter<CardStackAdapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardStackAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view : View = inflater.inflate(R.layout.item_card,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: CardStackAdapter.ViewHolder, position: Int) {
        holder.binding(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val image = itemView.findViewById<ImageView>(R.id.profileImageArea)
        val name=itemView.findViewById<TextView>(R.id.itemName)
        val gender=itemView.findViewById<TextView>(R.id.itemGender)
        val city=itemView.findViewById<TextView>(R.id.itemCity)
        val age=itemView.findViewById<TextView>(R.id.itemAge)
        val checkImageView = itemView.findViewById<ImageView>(R.id.authImageView)

        //how to show
        fun binding(data : UserDataModel){
            //init card image
            val storageRef = Firebase.storage.reference.child(data.uid + ".png")
            storageRef.downloadUrl.addOnCompleteListener(OnCompleteListener { task ->
                if(task.isSuccessful) {
                    Glide.with(context)
                        .load(task.result)
                        .into(image)
                }
            })

            //init text
            name.text=data.name
            gender.text=data.gender
            city.text=data.location
            age.text=data.age

            if (data.issuccess) {
                checkImageView.visibility = View.VISIBLE
            } else {
                checkImageView.visibility = View.GONE
            }
        }
    }
}

