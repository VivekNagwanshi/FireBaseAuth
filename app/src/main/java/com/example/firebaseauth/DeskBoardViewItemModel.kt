package com.example.firebaseauth

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.firebaseauth.api.DeskBoardRepository


class DeskBoardViewItemModel : ViewModel() {

    var servicesLiveData: MutableLiveData<DeskBoardResponse>? = null

    fun deskBoardPost(userId: String, flag: String): LiveData<DeskBoardResponse>? {
        Log.d("response", "getUser run")
        servicesLiveData = DeskBoardRepository.getServicesApiCall(userId, flag)
        return servicesLiveData

    }
}