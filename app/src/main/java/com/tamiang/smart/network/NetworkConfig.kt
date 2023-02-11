package com.tamiang.smart.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkConfig {

    private val client = OkHttpClient.Builder().apply {
        addInterceptor(MyInterceptor())
    }.build()

    private fun getRetrofitClient(): Retrofit{
        return Retrofit.Builder()
            .baseUrl("https://88ced0486778.ngrok.io/api/pasien/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getInstance(): NetworkService{
        return getRetrofitClient().create(NetworkService::class.java)
    }
}