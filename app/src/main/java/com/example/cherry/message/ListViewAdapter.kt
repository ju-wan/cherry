package com.example.cherry.message

import android.content.Context
import android.service.autofill.UserData
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.cherry.R
import com.example.cherry.auth.UserDataModel
import com.example.cherry.utils.FirebaseRef
import com.example.cherry.utils.FirebaseUtils
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.InternalCoroutinesApi

class ListViewAdapter(val context: Context, val items : MutableList<UserDataModel>) : BaseAdapter() {
    private var check_matching:Boolean=false
    override fun getCount(): Int {
        return items.size
    }

    override fun getItem(p0: Int): Any {
        return items[p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    //connect view
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        //item connect
        var convertView = convertView
        if (convertView == null) {
            convertView = LayoutInflater.from(parent?.context).inflate(R.layout.list_view_item, parent, false)
        }

        //can see liker's name in listview
        val name = convertView!!.findViewById<TextView>(R.id.name)
        name.text=items[position].name

        //can see liker's age in listview
        val age=convertView!!.findViewById<TextView>(R.id.age)
        age.text=items[position].age

        //to check that there a match with user
        val match_check=convertView!!.findViewById<ImageView>(R.id.heart)
        val match_mail=convertView!!.findViewById<ImageView>(R.id.mail)

        val currentUid = FirebaseUtils.getUid()
        val currentItemUid = items[position].uid // Assuming UserDataModel has a uid property

        checkMatching(currentItemUid.toString(), match_check,match_mail,currentUid)

        return convertView!!
    }

    private fun checkMatching(otherUid: String, match_check: ImageView,match_mail:ImageView,uid:String) {
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.children.count() == 0) {
                    // No matching, hide the match_check ImageView
                    match_check.visibility = View.GONE
                    match_mail.visibility = View.GONE
                } else {
                    var checkMatching = false
                    for (dataModel in dataSnapshot.children) {
                        if (dataModel.key.toString().equals(uid))
                            checkMatching = true
                    }
                    if (checkMatching) {
                        // There is a match, show the match_check ImageView
                        match_check.visibility = View.VISIBLE
                        match_mail.visibility = View.VISIBLE
                    } else {
                        // No match, hide the match_check ImageView
                        match_check.visibility = View.GONE
                        match_mail.visibility = View.GONE
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle onCancelled
            }
        }

        FirebaseRef.userLikeRef.child(otherUid).addValueEventListener(postListener)
    }
}