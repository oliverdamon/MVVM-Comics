package com.example.mangavinek.core.helper

import android.app.Dialog
import android.content.Context
import com.example.mangavinek.R

class SplashDialog(private val context: Context) {

    private var dialogSplash: Dialog? = null

    fun initSplash(){
        dialogSplash = Dialog(context, R.style.SplashFullScreen)
        dialogSplash?.setContentView(R.layout.layout_splash)
    }

    fun showSplashScreen() {
        dialogSplash?.show()
    }

    fun hideSplashScreen() = dialogSplash?.dismiss()

}