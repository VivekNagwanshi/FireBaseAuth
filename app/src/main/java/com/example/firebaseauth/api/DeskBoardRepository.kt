package com.example.firebaseauth.api

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.firebaseauth.DeskBoardResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object DeskBoardRepository {

    val serviceSetterGetter = MutableLiveData<DeskBoardResponse>()

    fun getServicesApiCall(userId: String, userFlag: String): MutableLiveData<DeskBoardResponse> {

        val hasMap = HashMap<String, String>()
        hasMap["user_id"] = userId
        hasMap["user_flag"] = userFlag
        // hasMap["access_token"] =accessToken
        val call = RetrofitClient.apiInterface.deskBoard(hasMap)

        call!!.enqueue(object : Callback<DeskBoardResponse?> {
            override fun onFailure(call: Call<DeskBoardResponse?>, t: Throwable) {
                Log.e("response", "onFailure run")
                Log.e("DEBUG : ", t.message.toString())
            }

            override fun onResponse(
                call: Call<DeskBoardResponse?>,
                response: Response<DeskBoardResponse?>
            ) {
                Log.e("response", "onResponse runvivek")
                Log.e("DEBUG : ", response.body().toString())
                if (response.isSuccessful) {
                    serviceSetterGetter.postValue(response.body())
                }
            }
        })
        return serviceSetterGetter
    }
}