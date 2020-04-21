package com.imhanjie.v2ex.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.imhanjie.v2ex.BaseActivity
import com.imhanjie.v2ex.databinding.ActivityMainBinding
import com.imhanjie.v2ex.vm.MyViewModel

class MainActivity : BaseActivity<ActivityMainBinding, MyViewModel>() {

    private lateinit var fragmentAdapter: FragmentStateAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fragmentAdapter = object : FragmentStateAdapter(this) {
            override fun getItemCount(): Int {
                return vm.tabs.size
            }

            override fun createFragment(position: Int): Fragment {
                return TabFragment.newInstance(vm.tabs[position])
            }
        }
        vb.viewPager.getChildAt(0).overScrollMode = View.OVER_SCROLL_NEVER
        vb.viewPager.adapter = fragmentAdapter
        TabLayoutMediator(vb.tabLayout, vb.viewPager) { tab, position ->
            tab.text = vm.tabs[position].name
        }.attach()
    }

}
