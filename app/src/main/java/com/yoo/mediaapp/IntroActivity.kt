package com.yoo.mediaapp

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class IntroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

        supportActionBar?.hide()
        window.statusBarColor=Color.parseColor("#ffffff")
        handler()

    }
    private fun handler(){
        Handler(mainLooper).postDelayed({
            startActivity(Intent(this@IntroActivity, MainActivity::class.java))
            finish()
        }, 2000)
    }

}