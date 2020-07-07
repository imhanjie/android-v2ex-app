package com.imhanjie.v2ex.view.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.imhanjie.support.ext.toActivity
import com.imhanjie.v2ex.AppSession
import com.imhanjie.v2ex.BaseFragment
import com.imhanjie.v2ex.common.Event
import com.imhanjie.v2ex.common.LiveDataBus
import com.imhanjie.v2ex.common.TopicTab
import com.imhanjie.v2ex.databinding.FragmentTabMainBinding
import com.imhanjie.v2ex.view.CreateTopicActivity
import com.imhanjie.v2ex.vm.MainTabViewModel

class MainTabFragment : BaseFragment<FragmentTabMainBinding>() {

    private lateinit var vm: MainTabViewModel

    override fun initViewModels() = listOf(vm)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vm = ViewModelProvider(this).get(MainTabViewModel::class.java)
    }

    override fun initViews() {
        vb.topBar.setOnClickListener {
            LiveDataBus.get().post(Event.MAIN_SCROLL_TO_TOP, Any())
        }
        vb.topBar.setOnRightClickListener(View.OnClickListener {
            toActivity<CreateTopicActivity>()
        })
        AppSession.getLoginState().observe(this) { isLogin ->
            vb.topBar.setRightVisibility(if (isLogin) View.VISIBLE else View.INVISIBLE)
        }

        (vb.viewPager.getChildAt(0) as RecyclerView).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        vb.viewPager.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount(): Int {
                return TopicTab.values().size
            }

            override fun createFragment(position: Int): Fragment {
                return TabFragment.newInstance(TopicTab.values()[position])
            }
        }
        vb.viewPager.offscreenPageLimit = TopicTab.values().size
        TabLayoutMediator(vb.tabLayout, vb.viewPager) { tab, position ->
            tab.text = TopicTab.values()[position].title
        }.attach()

//        vb.topBar.setOnRightClickListener(View.OnClickListener {
//            val targetUiMode = when (configSp.getInt("ui_mode", AppCompatDelegate.MODE_NIGHT_NO)) {
//                AppCompatDelegate.MODE_NIGHT_YES -> AppCompatDelegate.MODE_NIGHT_NO
//                AppCompatDelegate.MODE_NIGHT_NO -> AppCompatDelegate.MODE_NIGHT_YES
//                else -> AppCompatDelegate.MODE_NIGHT_NO
//            }
//            configSp.putInt("ui_mode", targetUiMode)
//            AppCompatDelegate.setDefaultNightMode(targetUiMode)
//        })
    }

}