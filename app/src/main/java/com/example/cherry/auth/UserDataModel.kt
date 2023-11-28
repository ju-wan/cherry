package com.example.cherry.auth

data class UserDataModel (
    val uid:String? =null,
    val gender:String? =null,
    val email:String?=null,
    val password:String?=null,
    val name:String?=null,
    val location:String?=null,
    val age:String?=null,
    val issuccess : Boolean = false
)