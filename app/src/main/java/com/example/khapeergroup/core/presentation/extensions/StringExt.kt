package com.example.khapeergroup.core.presentation.extensions

import android.annotation.SuppressLint
import android.util.Patterns
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

fun String.isEmail() : Boolean {
    return Patterns.EMAIL_ADDRESS.matcher(this).matches()
}

@SuppressLint("SimpleDateFormat")
fun String.getFormatDate() : String {

    val dateFormat = SimpleDateFormat("yyyy-MM-dd")

    val date: Date = dateFormat.parse(this)

    val dfFormat: DateFormat =
        SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    return dfFormat.format(date)

}

@SuppressLint("SimpleDateFormat")
fun String.getFormatDateTimeType() : String {

    val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")

    val date: Date = dateFormat.parse(this)

    val dfFormat: DateFormat =
        SimpleDateFormat("MM/dd HH:mm a")
    return dfFormat.format(date)

}

@SuppressLint("SimpleDateFormat")
fun String.getUpcomingEventDateFormat(): String {

    val dateFormat = SimpleDateFormat("MM/dd/yyyy - HH:mm")

    val date: Date = dateFormat.parse(this)

    val dfFormat: DateFormat =
        SimpleDateFormat("MMMM/yyyy")
    return dfFormat.format(date)

}

@SuppressLint("SimpleDateFormat")
fun String.getPastEventDateFormat(): String {

    val dateFormat = SimpleDateFormat("MM/dd/yyyy - HH:mm")

    val date: Date = dateFormat.parse(this)

    val dfFormat: DateFormat =
        SimpleDateFormat("yyyy")
    return dfFormat.format(date)

}