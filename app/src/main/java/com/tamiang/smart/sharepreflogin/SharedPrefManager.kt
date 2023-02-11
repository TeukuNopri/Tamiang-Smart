package com.tamiang.smart.sharepreflogin

import android.content.Context
import com.tamiang.smart.models.Login

class SharedPrefManager private constructor(private val mCtx: Context){
    val isLoggedIn : Boolean

    get(){
        val sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getInt("id", -1) != -1
    }

    val login: Login
    get(){
        val sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        return Login(
            sharedPreferences.getString("no_rkm_medis", null),
            sharedPreferences.getString("nm_pasien", null),
            sharedPreferences.getString("no_ktp", null),
            sharedPreferences.getString("tgl_lahir", null),
            sharedPreferences.getString("tmp_lahir", null),
            sharedPreferences.getString("no_tlp", null),
            sharedPreferences.getString("jk", null),
            sharedPreferences.getString("alamatpj", null),
            sharedPreferences.getString("kelurahanpj", null),
            sharedPreferences.getString("kecamatanpj", null),
            sharedPreferences.getString("kabupatenpj", null),
            sharedPreferences.getString("propinsipj", null),
            sharedPreferences.getString("email", null),
            sharedPreferences.getString("umur", null),
        )
    }

    fun saveUser(login: Login){
        val sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        editor.putString("no_rkm_medis", login.no_rkm_medis)
        editor.putString("nm_pasien", login.nm_pasien)
        editor.putString("no_ktp", login.no_ktp)
        editor.putString("tgl_lahir", login.tgl_lahir)
        editor.putString("tmp_lahir", login.tmp_lahir)
        editor.putString("no_tlp", login.no_tlp)
        editor.putString("jk", login.jk)
        editor.putString("alamatpj", login.alamatpj)
        editor.putString("kelurahanpj", login.kelurahanpj)
        editor.putString("kecamatanpj", login.kecamatanpj)
        editor.putString("kabupatenpj", login.kabupatenpj)
        editor.putString("propinsipj", login.propinsipj)
        editor.putString("email", login.email)
        editor.putString("umur", login.umur)

        editor.apply()
    }

    fun clearSession(){
        val sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }

    companion object{
        private val SHARED_PREF_NAME = "login"
        private var mInstance : SharedPrefManager?= null
        @Synchronized
        fun getInstance(mCtx: Context): SharedPrefManager{
            if(mInstance == null){
                mInstance = SharedPrefManager(mCtx)
            }

            return mInstance as SharedPrefManager
        }
    }
}