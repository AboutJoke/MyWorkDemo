package com.sylvan.myworkdemo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.act_recyclerview.*
import java.util.ArrayList

class RecyclerViewActivity : AppCompatActivity() {
    private val ss = arrayOf    ("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "v", "w", "x", "y", "z")
    private val sl = arrayListOf("n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z")
    private var list: ArrayList<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_recyclerview)
        recyclerview.layoutManager = LinearLayoutManager(this)

//        var i = 0
//        while (i < ss.size) {
//            i++
//
//        }

        list = arrayListOf()
        for (i in 0 until ss.size) {
            list!!.add(ss[i])
        }
        val simpleAdapter = SimpleAdapter(list!!)
        recyclerview.adapter = simpleAdapter


        recyclerview.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val canScrollVertically = recyclerview.canScrollVertically(1)
                if (!canScrollVertically){
                    simpleAdapter.setData(sl)
                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

            }
        })
    }


    internal inner class SimpleAdapter(private val list: ArrayList<String>) :
        RecyclerView.Adapter<SimpleAdapter.ViewHolder>() {

        override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
            val inflate = View.inflate(this@RecyclerViewActivity, R.layout.item_recyclerview_act, null)
            return ViewHolder(inflate)
        }

        override fun onBindViewHolder(holder: ViewHolder, i: Int) {
            holder.mTxt.text = list[i]
            holder.mDesc.text = list[i]+"123"
        }

        override fun getItemCount(): Int {
            return list.size
        }

        internal inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
            var mTxt: TextView = itemView.findViewById(R.id.txt)
            var mDesc: TextView = itemView.findViewById(R.id.desc)
        }

        fun setData(newList: ArrayList<String>){
            for (i in 0 until newList.size){
                list.add(newList[i])
            }
            notifyDataSetChanged()
        }
    }

}