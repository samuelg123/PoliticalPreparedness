package com.example.android.politicalpreparedness.common.extensions

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Context
import android.content.Intent
import android.provider.Settings
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.android.politicalpreparedness.common.base.entity.ResultWrapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.ResolverStyle
import java.time.temporal.TemporalAccessor
import java.util.*


fun Fragment.setTitle(title: String) {
    if (activity is AppCompatActivity) {
        (activity as AppCompatActivity).supportActionBar?.title = title
    }
}

fun Fragment.setDisplayHomeAsUpEnabled(bool: Boolean) {
    if (activity is AppCompatActivity) {
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(
            bool
        )
    }
}

//animate changing the view visibility
fun View.fadeIn() {
    this.visibility = View.VISIBLE
    this.alpha = 0f
    this.animate().alpha(1f).setListener(object : AnimatorListenerAdapter() {
        override fun onAnimationEnd(animation: Animator) {
            this@fadeIn.alpha = 1f
        }
    })
}

//animate changing the view visibility
fun View.fadeOut() {
    this.animate().alpha(0f).setListener(object : AnimatorListenerAdapter() {
        override fun onAnimationEnd(animation: Animator) {
            this@fadeOut.alpha = 1f
            this@fadeOut.visibility = View.GONE
        }
    })
}

fun Context.toast(message: String, duration: Int = Toast.LENGTH_LONG) {
    Toast.makeText(this, message, duration).show()
}

fun Context.toast(@StringRes resId: Int, duration: Int = Toast.LENGTH_LONG) {
    Toast.makeText(this, resId, duration).show()
}

fun Fragment.toast(message: String, duration: Int = Toast.LENGTH_LONG) {
    Toast.makeText(requireContext(), message, duration).show()
}

fun Fragment.toast(@StringRes resId: Int, duration: Int = Toast.LENGTH_LONG) {
    Toast.makeText(requireContext(), resId, duration).show()
}

fun Fragment.showEnableGPSDialog() {
    AlertDialog.Builder(requireContext())
        .setTitle("GPS not found")
        .setMessage("Enable now?")
        .setPositiveButton("Yes") { _, _ ->
            requireContext().startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
        }
        .setNegativeButton("No") { _, _ -> }
        .show()
}

fun <T : Any?> Flow<T>.handleErrors(): Flow<ResultWrapper<T>> = flow {
    try {
        collect { value -> emit(ResultWrapper.Success(value)) }
    } catch (e: Throwable) {
        emit(ResultWrapper.Error(e.message))
    }
}


fun String.toDate(): Date {
    val ta: TemporalAccessor = DateTimeFormatter.ISO_INSTANT.parse(this)
    val i: Instant = Instant.from(ta)
    return Date.from(i)
}

fun String.toDate(format: String, zoneId: ZoneId = ZoneId.systemDefault()): Date {
    val formatter: DateTimeFormatter =
        DateTimeFormatter.ofPattern(format)
            .withResolverStyle(ResolverStyle.SMART)
    val dt = LocalDate.parse(this, formatter).atStartOfDay(zoneId).toInstant()
    return Date.from(dt)
}

fun Date.toIso8601String(zoneId: ZoneId = ZoneId.systemDefault()): String =
    DateTimeFormatter.ISO_DATE_TIME
        .withLocale(Locale.getDefault())
//        .withZone(ZoneOffset.UTC)
        .withZone(zoneId)
        .format(this.toInstant())

fun Date.formatted(format: String): String =
    DateTimeFormatter.ofPattern(format)
        .withLocale(Locale.getDefault())
//        .withZone(ZoneOffset.UTC)
        .withZone(ZoneId.systemDefault())
        .format(this.toInstant())