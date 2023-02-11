package com.tamiang.smart

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_network_error.*

class NetworkErrorActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_network_error)

        btn_cek_wifi.setOnClickListener {
            val networkConnection = NetworkConnection(applicationContext)
            networkConnection.observe(this, Observer { isConnected ->
                if(isConnected){
                    startActivity(Intent(applicationContext, MainActivity::class.java))
                }
            } )
        }
    }
}