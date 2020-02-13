package com.example.mangavinek.core.helper

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Parcelable
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.annotation.DrawableRes
import androidx.fragment.app.FragmentManager

inline fun <reified T : Activity> Context.startActivity(vararg params: Pair<String, Any?>) {
        val intent = Intent (this, T::class.java)
        params.forEach {
            intent.addExtra(it.first, it.second)
        }
        startActivity(intent)
    }

inline fun <reified T: Fragment> FragmentManager.startFragment(@DrawableRes idContainer: Int, vararg params: Pair<String, Any?>) {
    val fragment = T::class.java.newInstance() as Fragment
    val bundle = Bundle()

    params.forEach {
        bundle.addPut(it.first, it.second)
    }

    fragment.arguments = bundle

    this.beginTransaction()
        .replace(idContainer, fragment)
        .commit()
}

fun Intent.addExtra(key: String, value: Any?) {
    when (value) {
        is Long -> putExtra(key, value)
        is String -> putExtra(key, value)
        is Boolean -> putExtra(key, value)
        is Float -> putExtra(key, value)
        is Double -> putExtra(key, value)
        is Int -> putExtra(key, value)
        is Parcelable -> putExtra(key, value)
    }
}

fun Bundle.addPut(key: String, value: Any?) {
    when (value) {
        is Long -> putLong(key, value)
        is String -> putString(key, value)
        is Boolean -> putBoolean(key, value)
        is Float -> putFloat(key, value)
        is Double -> putDouble(key, value)
        is Int -> putInt(key, value)
        is Parcelable -> putParcelable(key, value)
    }
}