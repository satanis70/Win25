package com.example.win25.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.win25.fragments.AccountFragment
import com.example.win25.fragments.MainFragment
import com.example.win25.fragments.StatsFragment

class ViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle): FragmentStateAdapter(fragmentManager, lifecycle){
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0->{
                MainFragment()
            }
            1->{
                StatsFragment()
            }
            2->{
                AccountFragment()
            }
            else->{
                Fragment()
            }
        }
    }
}