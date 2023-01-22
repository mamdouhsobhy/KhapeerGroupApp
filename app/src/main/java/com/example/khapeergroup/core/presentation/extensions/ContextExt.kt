package com.example.khapeergroup.core.presentation.extensions

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import android.app.Activity
import com.example.khapeergroup.home.presentation.MainActivity
import com.example.khapeergroup.R

fun Context.showToast(message: String, connectionError: Boolean = false) {
    if (connectionError) {
        Toast.makeText(this, getString(R.string.check_internet_connections), Toast.LENGTH_LONG)
            .show()
    } else {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
    Log.d("error", message)
}

fun Context.showGenericAlertDialog(message: String, errorCode: Int = 0) {
    AlertDialog.Builder(this).apply {
        setMessage(message)
        setPositiveButton("ok") { dialog, _ ->
            dialog.dismiss()
        }
    }.show()

    if (errorCode == 403) {
        startActivity(Intent(this, MainActivity::class.java))
        (this as Activity).finish()
    }

}