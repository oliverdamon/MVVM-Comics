package com.example.mangavinek.core.customview

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import com.example.mangavinek.R

class ItemReload @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0):
    LinearLayout(context, attrs, defStyleAttr) {

    var loading: ProgressBar
    var buttonRetry: ImageView

    init {
        inflate(context, R.layout.loading_error, this)
        loading = findViewById(R.id.progress_loading_bottom)
        buttonRetry = findViewById(R.id.image_refresh_bottom_default2)
    }

    fun showLoading(){
        loading.visibility = View.VISIBLE
        buttonRetry.visibility = View.GONE
    }

    fun showErrorRetry(){
        buttonRetry.visibility = View.VISIBLE
        loading.visibility = View.GONE
    }
}