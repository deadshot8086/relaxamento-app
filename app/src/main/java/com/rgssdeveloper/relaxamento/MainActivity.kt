package com.rgssdeveloper.relaxamento

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.rgssdeveloper.relaxamento.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        window.decorView.windowInsetsController!!.hide(android.view.WindowInsets.Type.statusBars())
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

//    override fun onBackPressed() {
//        val navHost = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
//        navHost?.let { navFragment ->
//            navFragment.childFragmentManager.primaryNavigationFragment?.let { fragment ->
//                if (fragment is MusicPlayFragment) {
//                    finish()
//                } else {
//                    super.onBackPressed()
//                }
//            }
//        }
//    }
}