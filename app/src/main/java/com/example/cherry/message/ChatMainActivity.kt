package com.example.cherry.message

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cherry.MainActivity
import com.example.cherry.R
import com.example.cherry.auth.UserDataModel
import com.example.cherry.databinding.ActivityChatMainBinding
import com.example.cherry.setting.MyPageActivity
import com.example.cherry.utils.FirebaseRef
import com.google.android.play.integrity.internal.t
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import kotlin.concurrent.thread

class ChatMainActivity : AppCompatActivity() {
    // settings
    lateinit var binding: ActivityChatMainBinding
    lateinit var adapter: UserAdapter

    // for Firebase
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDbRef: DatabaseReference

    // list of matched users
    private lateinit var userList: ArrayList<UserDataModel>

    // map of matched users
    val nameMap = HashMap<String, ArrayList<UserDataModel>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        // initialize
        super.onCreate(savedInstanceState)
        binding = ActivityChatMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 인증 초기화
        mAuth = Firebase.auth

        // db 초기화
        mDbRef = Firebase.database.reference

        // 리스트 초기화
        userList = ArrayList()

        adapter = UserAdapter(this, userList)

        binding.userRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.userRecyclerView.adapter = adapter

        // get matched users
        mDbRef.child("userInfo").addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                GlobalScope.launch(Dispatchers.Main) {
                    for (postSnapshot in snapshot.children) {
                        val currentUser = postSnapshot.getValue(UserDataModel::class.java)
                        if (mAuth.currentUser?.uid != currentUser?.uid) {
                            // 비동기 작업을 코루틴으로 감싸고, 작업이 완료될 때까지 대기
                            val likeList = getLikeList(currentUser!!.uid!!)
                            if(isMatched(likeList)) {
                                userList.add(currentUser!!)

                                // add entry in map
                                if(nameMap.containsKey(currentUser!!.name!!)) {
                                    // 이미 이름이 있으면 리스트에 추가
                                    nameMap[currentUser!!.name!!]!!.add(currentUser!!)
                                } else {
                                    // 새 이름이면 리스트를 만들어 추가
                                    val temp : ArrayList<UserDataModel> = arrayListOf(currentUser!!)
                                    nameMap.put(currentUser!!.name!!, temp)
                                }
                            }
                        }
                    }
                    adapter.notifyDataSetChanged()
                }
            }
            override fun onCancelled(error: DatabaseError) { }
        })

        //search option
        val searchBtn = findViewById<ImageView>(R.id.search_button)
        searchBtn.setOnClickListener{
            searchUser()
        }

        //mypage option
        val mypage=findViewById<ImageView>(R.id.my_msg_mypage)
        mypage.setOnClickListener{
            val intent_mypage= Intent(this, MyPageActivity::class.java)
            startActivity(intent_mypage)
        }

        //mylike btn
        val mylikeBtn = findViewById<ImageView>(R.id.my_msg_mylikeBtn_mypage)
        mylikeBtn.setOnClickListener{
            val intent= Intent(this, MyLikeListActivity::class.java)
            startActivity(intent)
        }

        //main option
        val main=findViewById<ImageView>(R.id.my_msg_main_btn)
        main.setOnClickListener{
            val intent_main= Intent(this, MainActivity::class.java)
            startActivity(intent_main)
        }
    }

    suspend fun getLikeList(userId: String): List<String> {
        return mDbRef.child("userLike").child(userId).get().await().children.mapNotNull {
            it.getValue(String::class.java)
        }
    }

    private fun isMatched(likeList: List<String>): Boolean {
        // uid가 uid인 사람의 좋아요 목록을 돌다가 접속한 사람의 uid가 있으면 true를 반환
        for(like in likeList) {
            Log.v(mAuth.currentUser?.uid, like)
            if(mAuth.currentUser?.uid != like) {
                return true
            }
        }
        return false
    }

    private fun searchUser() {
        //input
        val searchEditText = findViewById<EditText>(R.id.search_EditText)
        val targetName : String = searchEditText.text.toString().trim()
        userList.clear()
        adapter.notifyDataSetChanged()
    }
}
