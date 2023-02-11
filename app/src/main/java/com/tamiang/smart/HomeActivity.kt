package com.tamiang.smart

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import com.tamiang.smart.fragments.BerandaFragment
import com.tamiang.smart.fragments.DaftarPoliFragment
import com.tamiang.smart.fragments.InfoKamarFragment
import com.tamiang.smart.sharepreflogin.SharedPrefManager
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setSupportActionBar(toolbar_home)

        navigation_view.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_beranda -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.view_home, BerandaFragment()).commit()
                }

                R.id.nav_berobat -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.view_home, DaftarPoliFragment()).commit()
                }

                R.id.nav_kamar -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.view_home, InfoKamarFragment()).commit()
                }

                R.id.nav_keluar -> {
                    SharedPrefManager.getInstance(applicationContext).clearSession()
                    finish()
                    startActivity(Intent(applicationContext, LoginActivity::class.java))
                    true
                }
            }
            drawer.closeDrawer(GravityCompat.START)
            true
        }

        val headerView: View = navigation_view.getHeaderView(0)
        val navHeaderName: TextView = headerView.findViewById(R.id.tv_menu_nm_pasien)

        navHeaderName.setText(SharedPrefManager.getInstance(applicationContext).login.nm_pasien.toString())


        val drawerToggle = ActionBarDrawerToggle(this, drawer, R.string.open, R.string.close)
        drawer.addDrawerListener(drawerToggle)
        drawerToggle.syncState()

        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_nav)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        supportFragmentManager.beginTransaction().replace(R.id.view_home, BerandaFragment()).commit()
    }

    override fun onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onOptionsItemSelected(item)
        return when (item.itemId) {
            android.R.id.home -> {
                drawer.openDrawer(GravityCompat.START)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}