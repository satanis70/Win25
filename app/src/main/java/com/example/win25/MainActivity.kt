package com.example.win25

import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.example.win25.adapter.ViewPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadBackground()
        val adapter = ViewPagerAdapter(supportFragmentManager, lifecycle)
        view_pager.adapter = adapter
        TabLayoutMediator(tab_layout, view_pager){tab, position->
            when(position){
                0->{
                    tab.text = "Home"
                }
                1->{
                    tab.text = "Statistics"
                }
                2->{
                    tab.text = "Account"
                }
            }
        }.attach()
    }

    private fun loadBackground(){
        Glide.with(this).load("http://49.12.202.175/win24/gradient.png")
            .into(object : SimpleTarget<Drawable?>() {
                override fun onResourceReady(
                    resource: Drawable,
                    transition: Transition<in Drawable?>?
                ){
                    val constraint = findViewById<ConstraintLayout>(R.id.main_constraint)
                    constraint.background = resource
                }
            })
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}