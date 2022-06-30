package com.example.firebaseauth

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.firebaseauth.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private var deskBoardData: ArrayList<ItemDataItem?> = ArrayList()
    lateinit var deskBoardViewItemModel: DeskBoardViewItemModel
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: DeskBoardAdapter
    private lateinit var mAuth: FirebaseAuth
    private lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        mAuth = FirebaseAuth.getInstance()
        sharedPreferences = this.getSharedPreferences("MySharedPref", MODE_PRIVATE)
        binding.rvMain.layoutManager = LinearLayoutManager(this)
        deskBoardViewItemModel = ViewModelProvider(this)[DeskBoardViewItemModel::class.java]
        adapter = DeskBoardAdapter(deskBoardData as List<ItemDataItem>)
        binding.rvMain.adapter = adapter
        callDeskBoardApi()
        binding.signOut.setOnClickListener {
            mAuth.signOut()
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.putBoolean("isLogin", false)
            editor.apply()
            editor.commit()
            val i = Intent(this@MainActivity, LoginActivity::class.java)
            startActivity(i)
            finish()
        }
    }

    private fun callDeskBoardApi() {
        val userId = "6"
        val userFlag = "R"
        deskBoardViewItemModel.deskBoardPost(userId, userFlag)?.observe(this, Observer {
            Log.e("Response", it.toString())
            val list = it.response?.itemData
            if (list != null) {
                Log.e("response", "HomeData added ")
              deskBoardData.addAll(list)
            }
            val adapter = DeskBoardAdapter(deskBoardData as List<ItemDataItem>)
            binding.rvMain.adapter = adapter
        })
    }
}