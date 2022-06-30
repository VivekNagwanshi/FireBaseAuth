package com.example.firebaseauth

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.firebaseauth.databinding.ActivityOtpVerificationBinding
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit


class OtpVerificationActivity : AppCompatActivity() {
    private lateinit var mAuth: FirebaseAuth
    private lateinit var verificationId: String
    private lateinit var mCallback: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    private lateinit var binding: ActivityOtpVerificationBinding
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var mobileNumber:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otp_verification)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_otp_verification)
        verificationId = intent.getStringExtra("verificationId").toString()
        mobileNumber = intent.getStringExtra("MobileNumber").toString()
        sharedPreferences = this.getSharedPreferences("MySharedPref", MODE_PRIVATE)
        mAuth = FirebaseAuth.getInstance()
        binding.btnVerify.setOnClickListener {
            otpVerify(binding.edtOtp.text.toString())
        }
        binding.resendOtp.setOnClickListener {
            resendOtp()
        }
    }



    private fun otpVerify(code: String) {
        val credential = PhoneAuthProvider.getCredential(verificationId, code)
        signInWithCredential(credential)
    }

    private fun signInWithCredential(credential: PhoneAuthCredential?) {

        if (credential != null) {
            mAuth.signInWithCredential(credential)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val editor: SharedPreferences.Editor = sharedPreferences.edit()
                        editor.putBoolean("isLogin", true)
                        editor.apply()
                        editor.commit()
                        val i = Intent(this@OtpVerificationActivity, MainActivity::class.java)
                        startActivity(i)
                        finish()
                    } else {
                        Toast.makeText(
                            this@OtpVerificationActivity,
                            task.exception?.message,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
        }
    }
    private fun resendOtp() {
        mCallback = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onCodeSent(
                s: String,
                forceResendingToken: PhoneAuthProvider.ForceResendingToken
            ) {
                super.onCodeSent(s, forceResendingToken)
                verificationId = s
            }

            override fun onVerificationCompleted(phoneAuthCredential: PhoneAuthCredential) {
                val code = phoneAuthCredential.smsCode
                if (code != null) {
                    Toast.makeText(
                        this@OtpVerificationActivity,
                        "OTP send Successfully",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

            override fun onVerificationFailed(e: FirebaseException) {
                Log.e("message", e.message.toString())
                Toast.makeText(this@OtpVerificationActivity, e.message, Toast.LENGTH_LONG).show()
            }
        }
        val options = PhoneAuthOptions.newBuilder(mAuth)
            .setPhoneNumber(mobileNumber) // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(this) // Activity (for callback binding)
            .setCallbacks(mCallback) // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }
}