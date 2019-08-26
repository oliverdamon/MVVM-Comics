package com.example.mangavinek.core.util

import android.content.Context
import android.content.res.Configuration.SCREENLAYOUT_SIZE_LARGE
import android.content.res.Configuration.SCREENLAYOUT_SIZE_MASK


fun isTablet(context: Context): Boolean {
    return context.resources.configuration.screenLayout and
            SCREENLAYOUT_SIZE_MASK >= SCREENLAYOUT_SIZE_LARGE
}

fun maxNumberGridLayout(context: Context): Int{
    return if (isTablet(context)) 4 else 3
}