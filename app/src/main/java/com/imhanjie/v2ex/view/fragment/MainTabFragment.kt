package com.imhanjie.v2ex.view.fragment

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.imhanjie.support.e
import com.imhanjie.v2ex.BaseFragment
import com.imhanjie.v2ex.databinding.FragmentTabMainBinding
import com.imhanjie.v2ex.vm.MainTabViewModel

class MainTabFragment : BaseFragment<FragmentTabMainBinding>() {

    private lateinit var vm: MainTabViewModel

    override fun getViewModels() = listOf(vm)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vm = ViewModelProvider(this).get(MainTabViewModel::class.java)
    }

    override fun initViews() {
        (vb.viewPager.getChildAt(0) as RecyclerView).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        vb.viewPager.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount(): Int {
                return vm.tabs.size
            }

            override fun createFragment(position: Int): Fragment {
                return TabFragment.newInstance(vm.tabs[position])
            }
        }
        vb.viewPager.offscreenPageLimit = vm.tabs.size
        TabLayoutMediator(vb.tabLayout, vb.viewPager) { tab, position ->
            tab.text = vm.tabs[position].title
        }.attach()

        vb.topBar.setOnRightClickListener(View.OnClickListener {
            val targetUiMode = when (configSp.getInt("ui_mode", AppCompatDelegate.MODE_NIGHT_NO)) {
                AppCompatDelegate.MODE_NIGHT_YES -> AppCompatDelegate.MODE_NIGHT_NO
                AppCompatDelegate.MODE_NIGHT_NO -> AppCompatDelegate.MODE_NIGHT_YES
                else -> AppCompatDelegate.MODE_NIGHT_NO
            }
            configSp.putInt("ui_mode", targetUiMode)
            AppCompatDelegate.setDefaultNightMode(targetUiMode)
        })
    }

    override fun onResume() {
        super.onResume()
        e("MainTabFragment: onResume()")
    }

    override fun onPause() {
        super.onPause()
        e("MainTabFragment: onPause()")
    }

}