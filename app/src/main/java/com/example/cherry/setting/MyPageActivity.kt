package com.example.cherry.setting

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.cherry.MainActivity
import com.example.cherry.R
import com.example.cherry.auth.IntroActivity
import com.example.cherry.auth.UserDataModel
import com.example.cherry.message.ChatMainActivity
import com.example.cherry.message.MyLikeListActivity
import com.example.cherry.message.MyMsgActivity
import com.example.cherry.utils.FirebaseRef
import com.example.cherry.utils.FirebaseUtils
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class MyPageActivity : AppCompatActivity() {
    private val uid = FirebaseUtils.getUid()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_page)

        //mylike btn
        val mylogoutBtn = findViewById<ImageView>(R.id.logoutBtn)
        mylogoutBtn.setOnClickListener{
            val auth=Firebase.auth
            auth.signOut()

            val intent_Intro=Intent(this, IntroActivity::class.java)
            startActivity(intent_Intro)
        }

        /*
        //back btn
        val mybackBtn = findViewById<ImageView>(R.id.backBtn_mypage)
        mybackBtn.setOnClickListener{
            val intent_main=Intent(this, MainActivity::class.java)
            startActivity(intent_main)
        }
        */

        //chatting option
        val chatting=findViewById<ImageView>(R.id.my_page_chatting)
        chatting.setOnClickListener{
            val intent_chatting=Intent(this, ChatMainActivity::class.java)
            startActivity(intent_chatting)
        }

        //mylike btn
        val mylikeBtn = findViewById<ImageView>(R.id.my_page_mylikeBtn_mypage)
        mylikeBtn.setOnClickListener{
            val intent=Intent(this, MyLikeListActivity::class.java)
            startActivity(intent)
        }

        //main option
        val main=findViewById<ImageView>(R.id.my_page_main_btn)
        main.setOnClickListener{
            val intent_main=Intent(this, MainActivity::class.java)
            startActivity(intent_main)
        }

        val webviewBtn = findViewById<Button>(R.id.WebView_btn)
        webviewBtn.setOnClickListener {
            val webUrl = "https://www.instagram.com/accounts/login/"
            val intent = Intent(applicationContext, WebViewActivity::class.java)
            intent.putExtra("WEB_URL", webUrl)
            startActivity(intent)
        }
        getMyData()
    }

    //get my data form firebase
    private fun getMyData(){
        val myImage =findViewById<ImageView>(R.id.myImage)
        val myemail = findViewById<TextView>(R.id.myemail)
        val location=findViewById<TextView>(R.id.mylocation)
        val name=findViewById<TextView>(R.id.myname)

        val postListener = object : ValueEventListener {
            //dataSnapshot : firebase instore data
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val data = dataSnapshot.getValue(UserDataModel::class.java)

                //get text
                myemail.text=data!!.email
                name.text=data!!.name
                location.text=data!!.location

                //get image
                val storageRef = Firebase.storage.reference.child(data.uid + ".png")
                storageRef.downloadUrl.addOnCompleteListener(OnCompleteListener { task ->

                    if(task.isSuccessful) {
                        Glide.with(baseContext)
                            .load(task.result)
                            .into(myImage)
                    }

                })
                if (data.issuccess) {
                    val authenticationStatusTextView = findViewById<TextView>(R.id.authenticationStatusTextView)
                    authenticationStatusTextView.visibility = View.VISIBLE
                    val webviewBtn = findViewById<Button>(R.id.WebView_btn)
                    webviewBtn.visibility = View.GONE
                } else {
                    val authenticationStatusTextView = findViewById<TextView>(R.id.authenticationStatusTextView)
                    authenticationStatusTextView.visibility = View.GONE
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
            }
        }
        FirebaseRef.userInfoRef.child(uid).addValueEventListener(postListener)
    }

}