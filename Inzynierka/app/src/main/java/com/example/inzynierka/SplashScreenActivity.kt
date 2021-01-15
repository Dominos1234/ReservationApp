package com.example.inzynierka

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_splash_screen.*

class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        logo.alpha = 0f
        logo.animate().setDuration(3000).alpha(1f).withEndAction {
            var i = Intent(this, LoginActivity::class.java)

            val sharedPreferences = getSharedPreferences("User", Context.MODE_PRIVATE)
            if (sharedPreferences.getString("sessionId", "") != ""){
                i = Intent(this, CategoriesActivity::class.java)
            }
            startActivity(i)
            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)
            finish()
        }

    }
}
