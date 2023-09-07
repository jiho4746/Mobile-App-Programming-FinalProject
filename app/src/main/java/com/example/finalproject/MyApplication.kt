package com.example.finalproject

import androidx.multidex.MultiDexApplication
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import com.kakao.sdk.common.KakaoSdk
import com.tickaroo.tikxml.TikXml
import com.tickaroo.tikxml.retrofit.TikXmlConverterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MyApplication : MultiDexApplication() {
    companion object{
        lateinit var  auth: FirebaseAuth
        var email:String? = null

        lateinit var db : FirebaseFirestore
        lateinit var storage : FirebaseStorage

        var networkServiceXml : NetworkService

        val parser = TikXml.Builder().exceptionOnUnreadXml(false).build()
        val retrofitxml : Retrofit
            get() = Retrofit.Builder()
                .baseUrl("http://data.uiryeong.go.kr/")
                .addConverterFactory(TikXmlConverterFactory.create(parser))
                .build()
        init{
            networkServiceXml = retrofitxml.create(NetworkService::class.java)
        }

        fun checkAuth():Boolean{
            val currentUser = auth.currentUser
            return currentUser?.let{
                email = currentUser.email
                currentUser.isEmailVerified
            }?:let{
                false
            }
        }
    }

    override fun onCreate()
    {
        super.onCreate()
        auth = Firebase.auth

        KakaoSdk.init(this, "2461446945563f43182ffcd7f4159c43")
        
        //초기화
        db = FirebaseFirestore.getInstance()
        storage = Firebase.storage
    }
}