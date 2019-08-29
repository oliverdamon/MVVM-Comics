package com.example.mangavinek

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import org.jetbrains.anko.startActivity
import java.lang.Exception

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        showMainActivity()
    }

    private fun showMainActivity() {
        Handler().postDelayed({
            try {
                startActivity<MainActivity>()
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                finish()
            }
        }, 2000)
    }
}