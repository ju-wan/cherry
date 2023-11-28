package com.example.cherry.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.cherry.MainActivity
import com.example.cherry.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Spinner
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import com.example.cherry.utils.FirebaseRef
import com.google.firebase.database.ktx.database
import com.google.firebase.storage.ktx.storage
import java.io.ByteArrayOutputStream

class JoinActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    //add profileImage
    lateinit var profileImage:ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join)

        auth = Firebase.auth

        profileImage=findViewById<ImageView>(R.id.ImageArea)

        val getAction = registerForActivityResult(
            ActivityResultContracts.GetContent(),
            ActivityResultCallback { uri ->
                profileImage.setImageURI(uri)
            }
        )

        profileImage.setOnClickListener {
            getAction.launch("image/*")
        }

        //back button
        val backBtn=findViewById<ImageView>(R.id.button6)
        backBtn.setOnClickListener{
            finish()
        }

        //spinner 1
        var location : String = ""
        var spinner1:Spinner = findViewById(R.id.spinner1)
        var spinner_item_1 = resources.getStringArray(R.array.spinner1_arr)
        var spinner_Adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, spinner_item_1)

        spinner1.adapter = spinner_Adapter
        spinner1.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                location = spinner_item_1[position]
                //spinner 2
                var spinner2:Spinner = findViewById(R.id.spinner2)

                //seoul,gyeonggi
                if(location=="서울/경기"){
                    var spinner_item_2 = resources.getStringArray(R.array.Seoul_Gyeonggi)
                    var spinner_Adapter_2 = ArrayAdapter(this@JoinActivity, android.R.layout.simple_spinner_dropdown_item, spinner_item_2)

                    spinner2.adapter = spinner_Adapter_2

                    spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                            location = location.plus(spinner_item_2[position])
                        }
                        override fun onNothingSelected(parent: AdapterView<*>) {

                        }
                    }
                }

                //incheon
                if(location=="인천"){
                    var spinner_item_2 = resources.getStringArray(R.array.Incheon_arr)
                    var spinner_Adapter_2 = ArrayAdapter(this@JoinActivity, android.R.layout.simple_spinner_dropdown_item, spinner_item_2)

                    spinner2.adapter = spinner_Adapter_2

                    spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                            location = location.plus(spinner_item_2[position])
                        }
                        override fun onNothingSelected(parent: AdapterView<*>) {

                        }
                    }
                }

                //gangwon
                if(location=="강원도"){
                    var spinner_item_2 = resources.getStringArray(R.array.Gangwon_arr)
                    var spinner_Adapter_2 = ArrayAdapter(this@JoinActivity, android.R.layout.simple_spinner_dropdown_item, spinner_item_2)

                    spinner2.adapter = spinner_Adapter_2

                    spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                            location = location.plus(spinner_item_2[position])
                        }
                        override fun onNothingSelected(parent: AdapterView<*>) {

                        }
                    }
                }

                //jeolla
                if(location=="전라도"){
                    var spinner_item_2 = resources.getStringArray(R.array.Jeolla_arr)
                    var spinner_Adapter_2 = ArrayAdapter(this@JoinActivity, android.R.layout.simple_spinner_dropdown_item, spinner_item_2)

                    spinner2.adapter = spinner_Adapter_2

                    spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                            location = location.plus(spinner_item_2[position])
                        }
                        override fun onNothingSelected(parent: AdapterView<*>) {

                        }
                    }
                }

                //chungcheong
                if(location=="충청도"){
                    var spinner_item_2 = resources.getStringArray(R.array.Chungcheong_arr)
                    var spinner_Adapter_2 = ArrayAdapter(this@JoinActivity, android.R.layout.simple_spinner_dropdown_item, spinner_item_2)

                    spinner2.adapter = spinner_Adapter_2

                    spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                            location = location.plus(spinner_item_2[position])
                        }
                        override fun onNothingSelected(parent: AdapterView<*>) {

                        }
                    }
                }

                //gyeongsang
                if(location=="경상도"){
                    var spinner_item_2 = resources.getStringArray(R.array.Gyeongsang_arr)
                    var spinner_Adapter_2 = ArrayAdapter(this@JoinActivity, android.R.layout.simple_spinner_dropdown_item, spinner_item_2)

                    spinner2.adapter = spinner_Adapter_2

                    spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                            location = location.plus(spinner_item_2[position])
                        }
                        override fun onNothingSelected(parent: AdapterView<*>) {

                        }
                    }
                }

                //jeju
                if(location=="제주도"){
                    var spinner_item_2 = resources.getStringArray(R.array.Jeju_arr)
                    var spinner_Adapter_2 = ArrayAdapter(this@JoinActivity, android.R.layout.simple_spinner_dropdown_item, spinner_item_2)

                    spinner2.adapter = spinner_Adapter_2

                    spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                            location = location.plus(spinner_item_2[position])
                        }
                        override fun onNothingSelected(parent: AdapterView<*>) {

                        }
                    }
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }
            //회원가입btn_press
            val joinBtn=findViewById<ImageView>(R.id.button7)
            joinBtn.setOnClickListener {
                val email = findViewById<EditText>(R.id.signup_Email).text.toString()
                val password = findViewById<EditText>(R.id.signup_pw).text.toString()
                val gender = findViewById<EditText>(R.id.signup_gender).text.toString()
                val name = findViewById<EditText>(R.id.signup_name).text.toString()
                val age = findViewById<EditText>(R.id.signup_age).text.toString()

                //new_account
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        //sucess
                        if (task.isSuccessful) {
                            Toast.makeText(this, "회원가입 성공!", Toast.LENGTH_SHORT).show()

                            val user = auth.currentUser
                            val uid = user?.uid.toString()

                            //make usermodel
                            val userModel = UserDataModel(
                                uid,
                                gender,
                                email,
                                password,
                                name,
                                location,
                                age
                            )

                            FirebaseRef.userInfoRef.child(uid).setValue(userModel)

                            uploadImage(uid)

                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                        }
                        //fail
                        else {
                            Toast.makeText(
                                this,
                                "회원가입 실패!: ${task.exception?.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            }
        }

        //image -> profileimage
        private fun uploadImage(uid : String){
            val storage = Firebase.storage
            val storageRef = storage.reference.child(uid + ".png")

            // Get the data from an ImageView as bytes

            profileImage.isDrawingCacheEnabled = true
            profileImage.buildDrawingCache()
            val bitmap = (profileImage.drawable as BitmapDrawable).bitmap
            val baos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
            val data = baos.toByteArray()

            var uploadTask = storageRef.putBytes(data)
            uploadTask.addOnFailureListener {
                // Handle unsuccessful uploads
            }.addOnSuccessListener { taskSnapshot ->
                // taskSnapshot.metadata contains file metadata such as size, content-type, etc.
                // ...
            }
        }
}