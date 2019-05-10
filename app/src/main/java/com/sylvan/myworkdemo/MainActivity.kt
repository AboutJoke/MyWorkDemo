package com.sylvan.myworkdemo

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
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

    fun startRecyclerView(view: View) {
        val intent = Intent(this, RecyclerViewActivity::class.java)
        startActivity(intent)
    }

    fun startProgress(view: View) {
        val intent = Intent(this, ProgressActivity::class.java)
        startActivity(intent)
    }

    fun startDraw(view: View){
        val intent = Intent(this, MyTrapezoidActivity::class.java)
        startActivity(intent)
    }

    fun startRating(view: View){
        val intent = Intent(this, MyRatingBarAct::class.java)
        startActivity(intent)
    }

    fun startDialog() {

    }
}
