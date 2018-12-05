package com.sylvan.myworkdemo

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.sylvan.myworkdemo.pip.PipViewActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        anim.setOnClickListener { oneCoinAnim() }
        pip.setOnClickListener {startPip()}
    }

    fun oneCoinAnim() {
        val intent = Intent(this, AnimationActivity::class.java)
        startActivity(intent)
    }

    fun startPip(){
        val intent = Intent(this, PipViewActivity::class.java)
        startActivity(intent)
    }
}
