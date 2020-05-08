package com.imhanjie.v2ex.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.ViewModelProvider
import com.imhanjie.v2ex.BaseLazyFragment
import com.imhanjie.v2ex.databinding.FragmentTabMainBinding
import com.imhanjie.v2ex.vm.MainTabViewModel

class MainTabFragment : BaseLazyFragment<FragmentTabMainBinding>() {

    private lateinit var vm: MainTabViewModel

    override fun getViewModels() = listOf(vm)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vm = ViewModelProvider(this).get(MainTabViewModel::class.java)
    }

    override fun initViews() {
        vb.viewPager.adapter = object : FragmentPagerAdapter(childFragmentManager) {
            override fun getItem(position: Int): Fragment {
                return TabFragment.newInstance(vm.tabs[position])
            }

            override fun getCount(): Int {
                return vm.tabs.size
            }

            override fun getPageTitle(position: Int): CharSequence? {
                return vm.tabs[position].title
            }

        }
        vb.tabLayout.setupWithViewPager(vb.viewPager)
        vb.viewPager.offscreenPageLimit = vm.tabs.size


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

    override fun onLazyLoad() {
    }

    override fun onResumeAfterLazyLoad() {
        super.onResumeAfterLazyLoad()
    }

}