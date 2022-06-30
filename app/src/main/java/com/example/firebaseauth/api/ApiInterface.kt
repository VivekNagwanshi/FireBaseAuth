package com.example.firebaseauth.api

import com.example.firebaseauth.DeskBoardResponse
import retrofit2.Call
import retrofit2.http.*


interface ApiInterface {

    @GET("dashboard")
    fun deskBoard(@QueryMap dataModal: HashMap<String, String>): Call<DeskBoardResponse?>?


/* @POST("user_registration")
 @FormUrlEncoded
 fun singUpPost(@FieldMap dataModal: HashMap<String, String>): Call<SignUpRespons?>?

  @POST("get_boards?")
   @FormUrlEncoded
   fun userDetailesPost(@FieldMap dataModal: HashMap<String, String>): Call<HomeResponse?>?*/

}