package com.jimbonlemu.aplikasicatatanharian

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.jimbonlemu.aplikasicatatanharian.getHelper.Get

class SplashScreen : AppCompatActivity() {

    private val splashTimeout: Long = 3000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen)

        Handler().postDelayed(
            {
                Get.offAll(this, ListNoteScreen::class.java)
            }, splashTimeout
        )
    }
}
