package com.example.cherry.message

import android.R
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.transition.Transition
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.example.cherry.auth.UserDataModel
import com.example.cherry.utils.FirebaseUtils
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage


class UserAdapter(private val context: Context, private val userList: ArrayList<UserDataModel>)
    : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view: View = LayoutInflater.from(context).
        inflate(com.example.cherry.R.layout.user_layout, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        // 데이터 담기
        val currentUser = userList[position]

        // 화면에 데이터 보여주기
        holder.nameText.text = currentUser.name
        val storageRef = Firebase.storage.reference.child(currentUser.uid + ".png")
        storageRef.downloadUrl.addOnCompleteListener(OnCompleteListener { task ->
            if(task.isSuccessful) {
                Glide.with(context)
                    .load(task.result)
                    .into(holder.image)
            }
        })

        // 아이템 클릭 이벤트
        holder.itemView.setOnClickListener {
            val intent = Intent(context, ChatActivity::class.java)

            // 넘길 데이터
            intent.putExtra("name", currentUser.name)
            intent.putExtra("uId", currentUser.uid)

            context.startActivity(intent)
        }

        holder.outButton.setOnClickListener {
            val msgBuilder = AlertDialog.Builder(context)
            msgBuilder.setTitle("채팅방 나가기")
            msgBuilder.setMessage("채팅방을 나가시겠습니까?")
            msgBuilder.setPositiveButton("나가기") { dialog, _ ->
                // 파이어베이스에서 채팅방 삭제
                val senderRoom = userList[position].uid + FirebaseUtils.getUid()
                val mDbRef = FirebaseDatabase.getInstance().reference
                Log.v("test", senderRoom)
                mDbRef.child("chats").child(senderRoom).removeValue()
                    .addOnCompleteListener { removeTask ->
                        if (removeTask.isSuccessful) {
                            // 사용자 목록에서 삭제
                            val removedUser = userList.removeAt(holder.adapterPosition)
                            notifyItemRemoved(holder.adapterPosition)
                            dialog.dismiss()
                        } else {
                            // 삭제 실패 처리
                            // 필요에 따라 처리할 내용을 추가하세요
                        }
                    }
            }

            msgBuilder.setNegativeButton("취소") { dialog, _ ->
                // 취소 버튼이 클릭되었을 때 실행되는 코드
                dialog.dismiss()
            }

            // 다이얼로그 표시
            val dialog = msgBuilder.create()
            dialog.show()
        }
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    class UserViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val nameText: TextView = itemView.findViewById(com.example.cherry.R.id.name_text)
        val image = itemView.findViewById<ImageView>(com.example.cherry.R.id.profile_image)
        val outButton = itemView.findViewById<ImageView>(com.example.cherry.R.id.out_btn)
    }
}