package com.example.khapeergroup.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.khapeergroup.auth.presentation.ActivityAuth
import com.example.khapeergroup.core.presentation.base.BaseActivity
import com.example.khapeergroup.core.presentation.common.SharedPrefs
import com.example.khapeergroup.databinding.ActivitySplashBinding
import com.example.khapeergroup.home.presentation.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ActivitySplash : BaseActivity<ActivitySplashBinding>() {

    @Inject
    lateinit var sharedPrefs: SharedPrefs

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Handler(Looper.getMainLooper()).postDelayed({
            if(sharedPrefs.getToken().isEmpty()) {
                val intent = Intent(this, ActivityAuth::class.java)
                startActivity(intent)
                finish()
            }else{
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }

        }, SPLASH_TIME_OUT)
    }

    companion object {
        private const val SPLASH_TIME_OUT: Long = 3000
    }
}