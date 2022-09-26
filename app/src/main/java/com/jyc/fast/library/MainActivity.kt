package com.jyc.fast.library

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jyc.library.fast.lib.util.ActivityManager
import com.jyc.library.fast.lib.util.crash.CrashHandler

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}