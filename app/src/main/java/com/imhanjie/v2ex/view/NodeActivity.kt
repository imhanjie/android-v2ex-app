package com.imhanjie.v2ex.view

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.imhanjie.support.ext.toast
import com.imhanjie.v2ex.BaseActivity
import com.imhanjie.v2ex.R
import com.imhanjie.v2ex.databinding.ActivityNodeBinding
import com.imhanjie.v2ex.vm.BaseViewModel
import com.imhanjie.v2ex.vm.NodeViewModel

class NodeActivity : BaseActivity<ActivityNodeBinding>() {

    private lateinit var vm: NodeViewModel

    @Suppress("UNCHECKED_CAST")
    override fun initViewModels(): List<BaseViewModel> {
        val nodeName: String = intent.getStringExtra("name")
            ?: throw IllegalArgumentException("缺少 name 参数")
        vm = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return NodeViewModel(nodeName, application) as T
            }
        }).get(NodeViewModel::class.java)
        return listOf(vm)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val title: String = intent.getStringExtra("title")
            ?: throw IllegalArgumentException("缺少 title 参数")

        vb.topBar.setTitleText(title)
        vb.topBar.setOnRightClickListener(View.OnClickListener {
            vm.doFavoriteNode()
        })

        vm.isFavoriteLiveData.observe(this) {
            val (isFavorite, isManual) = it
            if (isManual) {
                toast(if (isFavorite) R.string.tips_favorite_success else R.string.tips_un_favorite_success)
            }
            vb.topBar.setRightIcon(if (isFavorite) R.drawable.ic_favorite else R.drawable.ic_un_favorite)
        }
        vm.nodeLiveData.observe(this) {

        }
    }

}