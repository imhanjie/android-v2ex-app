package com.imhanjie.v2ex.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.imhanjie.support.ext.post
import com.imhanjie.support.ext.toActivity
import com.imhanjie.v2ex.AppSession
import com.imhanjie.v2ex.BaseActivity
import com.imhanjie.v2ex.R
import com.imhanjie.v2ex.databinding.ActivityMainBinding
import com.imhanjie.v2ex.view.fragment.MainTabFragment
import com.imhanjie.v2ex.view.fragment.MeTabFragment
import com.imhanjie.v2ex.view.fragment.NodeTabFragment
import com.imhanjie.v2ex.view.fragment.NotificationsTabFragment
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
        checkAppSession()
    }

    private fun checkAppSession() {
        post {
            if (!AppSession.isLogin()) {
                toActivity<LoginActivity>()
            }
        }
    }

    private fun initNavBar() {
        with(vb.navBar) {
            addItem(BottomNavigationBar.Item(getString(R.string.tab_main), R.drawable.ic_tab_main))
            addItem(BottomNavigationBar.Item(getString(R.string.tab_node), R.drawable.ic_tab_node))
            addItem(BottomNavigationBar.Item(getString(R.string.tab_notification), R.drawable.ic_tab_notification))
            addItem(BottomNavigationBar.Item(getString(R.string.tab_me), R.drawable.ic_tab_me))
            onTabClickListener = { clickTabIndex, _ ->
                vb.viewPager.setCurrentItem(clickTabIndex, false)
            }
            firstSelected(DEFAULT_INDEX)
            initialise()
        }
    }

    private fun initViewPager() {
        // p.s 这里后续不能根据 position 拿到 Fragment 来进行操作，仅用作实例化，因为 Activity 重建后可能会复用以前的 Fragment。
        with(fragments) {
            add(TAB_MAIN, MainTabFragment())
            add(TAB_NODE, NodeTabFragment())
            add(TAB_NOTIFICATION, NotificationsTabFragment())
            add(TAB_ME, MeTabFragment())
        }
        with(vb) {
            viewPager.adapter = object : FragmentStateAdapter(this@MainActivity) {
                override fun getItemCount(): Int {
                    return fragments.size
                }

                override fun createFragment(position: Int): Fragment {
                    return fragments[position]
                }

            }
            viewPager.isUserInputEnabled = false
            viewPager.offscreenPageLimit = fragments.size
            viewPager.setCurrentItem(DEFAULT_INDEX, false)
        }
    }

}