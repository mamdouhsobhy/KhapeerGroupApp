package com.example.khapeergroup

import android.app.Application
import com.example.khapeergroup.core.presentation.common.SharedPrefs
import com.example.khapeergroup.core.presentation.utilities.LocaleHelper
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class KhapeerBaseApplication : Application() {
    @Inject
    lateinit var sharedPrefs: SharedPrefs

    override fun onCreate() {
        super.onCreate()
        LocaleHelper.onAttach(applicationContext)
        LocaleHelper.setLocale(sharedPrefs.getPreferredLocale())
    }
}