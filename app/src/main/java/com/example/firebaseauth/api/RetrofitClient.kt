package com.example.firebaseauth.api


import android.util.Log
import com.example.firebaseauth.BuildConfig

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

object RetrofitClient {

    private const val BASEURL = "https://demo.bhanushainfosoft.com/restrobee-demo/api/"

    val retrofitClient: Retrofit.Builder by lazy {

        val levelType: Level
        if (BuildConfig.BUILD_TYPE.contentEquals("debug"))
            levelType = Level.BODY else levelType = Level.NONE
        val logging = HttpLoggingInterceptor()
        logging.setLevel(levelType)

        val okhttpClient = OkHttpClient.Builder()
        okhttpClient.addInterceptor(logging)

        okhttpClient.addInterceptor(object : Interceptor {
            @Throws(IOException::class)
            override fun intercept(chain: Interceptor.Chain): Response {
                val newRequest: Request = chain.request().newBuilder()
                   /* .addHeader("Authorization", "Bearer ${TokenClass.token}")*/
                    .build()
                return chain.proceed(newRequest)
            }
        }).build()


        Retrofit.Builder()
            .baseUrl(BASEURL)
            .client(okhttpClient.build())
            .addConverterFactory(GsonConverterFactory.create())
    }

    val apiInterface: ApiInterface by lazy {
        Log.e("response", "ApiInterface run")
        retrofitClient
            .build()
            .create(ApiInterface::class.java)
    }
}