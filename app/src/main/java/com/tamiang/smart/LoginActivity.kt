package com.tamiang.smart

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.tamiang.smart.models.LoginResponse
import com.tamiang.smart.network.NetworkConfig
import com.tamiang.smart.sharepreflogin.SharedPrefManager
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*


class LoginActivity : AppCompatActivity() {

    var formatDate = SimpleDateFormat("yyyy-MM-dd", Locale.US)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val sharedPreference = this.getSharedPreferences("TOKEN_FCM", Context.MODE_PRIVATE)
        val token = sharedPreference?.getString("token", "").toString()

        btn_action_login.setOnClickListener {
            val rekam_medik = et_login_medik.text.toString().trim()
            val nik         = et_login_nik.text.toString().trim()
            val tgl_lahir   = et_login_tgl_lahir.text.toString().trim()
            val token       = token
            val device      = Build.MANUFACTURER.toString()

            checkLogin(rekam_medik, nik, tgl_lahir, token, device)
        }

        et_login_tgl_lahir.setOnClickListener {
            val getDate: Calendar = Calendar.getInstance()
            val datePicker = DatePickerDialog(this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, DatePickerDialog.OnDateSetListener { datePicker, i, i2, i3 ->

                val selectDate: Calendar = Calendar.getInstance()
                selectDate.set(Calendar.YEAR, i)
                selectDate.set(Calendar.MONTH, i2)
                selectDate.set(Calendar.DAY_OF_MONTH, i3)

                val date: String = formatDate.format(selectDate.time)
                et_login_tgl_lahir.setText(date)
            },getDate.get(Calendar.YEAR), getDate.get(Calendar.MONTH), getDate.get(Calendar.DAY_OF_MONTH))
            datePicker.show()
        }

    }

    private fun checkLogin(no_rkm_medik: String?, nik: String?, tgl_lahir: String?, token: String?, device: String?){
        pb_login.visibility = View.VISIBLE
        NetworkConfig.getInstance().cekLogin(no_rkm_medik, nik, tgl_lahir, token, device)
            .enqueue(object: Callback<LoginResponse>{
                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {

                    pb_login.visibility = View.GONE

                    if(response.body()?.metaData?.kode == 202){
                        Toast.makeText(applicationContext, response.body()?.metaData?.message.toString(), Toast.LENGTH_LONG).show()
                    }else if(response.body()?.metaData?.kode == 201){
                        Toast.makeText(applicationContext, response.body()?.metaData?.message.toString(), Toast.LENGTH_LONG).show()
                    }else{
                        SharedPrefManager.getInstance(application).saveUser(response.body()?.response!!)

                        val intent = Intent(applicationContext, HomeActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

                        startActivity(intent)

                        et_login_medik.text.clear()
                        et_login_nik.text.clear()
                        et_login_tgl_lahir.text.clear()

                        finish()
                    }
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Log.e("Network Error", t.message.toString())
                }
            })
    }
}