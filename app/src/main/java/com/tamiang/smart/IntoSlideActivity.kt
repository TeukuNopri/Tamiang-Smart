package com.tamiang.smart

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_into_slide.*

class IntoSlideActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_into_slide)

        btn_lanjut.setOnClickListener {
            startActivity(Intent(applicationContext, LoginActivity::class.java))
            finish()
        }
    }
}