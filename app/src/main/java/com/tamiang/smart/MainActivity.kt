package com.tamiang.smart

import android.animation.ObjectAnimator
import android.content.Intent
import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.tamiang.smart.sharepreflogin.SharedPrefManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        progressBarMain.max = 100
        val currentProgress = 100

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            progressBarMain.progressDrawable.colorFilter =
                BlendModeColorFilter(Color.YELLOW, BlendMode.SRC_IN)
        }else {
            progressBarMain.progressDrawable
                .setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_IN)
        }

        ObjectAnimator.ofInt(progressBarMain, "Progress", currentProgress)
            .setDuration(2000)
            .start()

        Handler().postDelayed({
            val eksekusi = SharedPrefManager.getInstance(applicationContext).login.no_rkm_medis

            if(eksekusi != null){
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
                finish()
            }else{
                val intent = Intent(this, IntoSlideActivity::class.java)
                startActivity(intent)
                finish()
            }


        }, 2200)



    }
}