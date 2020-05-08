package com.imhanjie.v2ex.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import com.imhanjie.v2ex.BaseActivity
import com.imhanjie.v2ex.R
import com.imhanjie.v2ex.databinding.ActivityMainBinding
import com.imhanjie.v2ex.vm.BaseViewModel
import com.imhanjie.widget.nav.BottomNavigationBar

class MainActivity : BaseActivity<ActivityMainBinding>() {

    private val fragments = arrayListOf<Fragment>()

    companion object {
        private const val TAB_MAIN = 0
        private const val TAB_NODE = 1
        private const val TAB_NOTIFICATION = 2
        private const val TAB_ME = 3
        private const val DEFAULT_INDEX = TAB_MAIN
    }

    override fun initViewModels(): List<BaseViewModel> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initNavBar()
        initViewPager()
    }

    private fun initNavBar() {
        with(vb.navBar) {
            addItem(BottomNavigationBar.Item("首页", R.drawable.ic_tab_main))
            addItem(BottomNavigationBar.Item("节点", R.drawable.ic_tab_node))
            addItem(BottomNavigationBar.Item("消息", R.drawable.ic_tab_notification))
            addItem(BottomNavigationBar.Item("我的", R.drawable.ic_tab_me))
            onTabClickListener = { clickTabIndex, _ ->
                vb.viewPager.setCurrentItem(clickTabIndex, false)
            }
            firstSelected(DEFAULT_INDEX)
            initialise()
        }
    }

    private fun initViewPager() {
        with(fragments) {
            add(TAB_MAIN, MainTabFragment())
            add(TAB_NODE, NodeTabFragment())
            add(TAB_NOTIFICATION, NotificationTabFragment())
            add(TAB_ME, MeTabFragment())
        }
        vb.viewPager.adapter = object : FragmentPagerAdapter(supportFragmentManager) {
            override fun getItem(position: Int): Fragment {
                return fragments[position]
            }

            override fun getCount(): Int {
                return fragments.size
            }
        }
        vb.viewPager.offscreenPageLimit = fragments.size
        vb.viewPager.setCurrentItem(DEFAULT_INDEX, false)
    }

}