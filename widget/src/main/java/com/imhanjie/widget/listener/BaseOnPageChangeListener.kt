package com.imhanjie.widget.listener

import androidx.viewpager.widget.ViewPager.OnPageChangeListener

/**
 * Description
 *
 * @author hanjie
 * @date 2019-05-09
 */
abstract class BaseOnPageChangeListener : OnPageChangeListener {

    override fun onPageScrolled(i: Int, v: Float, i1: Int) {}

    override fun onPageSelected(i: Int) {}

    override fun onPageScrollStateChanged(i: Int) {}

}