package com.imhanjie.v2ex.view

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.lifecycle.observe
import com.imhanjie.support.ext.toActivity
import com.imhanjie.support.ext.toast
import com.imhanjie.v2ex.BaseActivity
import com.imhanjie.v2ex.R
import com.imhanjie.v2ex.common.ExtraKeys
import com.imhanjie.v2ex.common.MissingArgumentException
import com.imhanjie.v2ex.common.ViewModelProvider
import com.imhanjie.v2ex.databinding.ActivityNodeBinding
import com.imhanjie.v2ex.view.fragment.NodePageFragment
import com.imhanjie.v2ex.vm.BaseViewModel
import com.imhanjie.v2ex.vm.NodeViewModel

class NodeActivity : BaseActivity<ActivityNodeBinding>() {

    private lateinit var vm: NodeViewModel

    private lateinit var nodeName: String

    companion object {
        fun start(from: Any, title: String, name: String) {
            from.toActivity<NodeActivity>(
                bundleOf(
                    ExtraKeys.NODE_TITLE to title,
                    ExtraKeys.NODE_NAME to name
                )
            )
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun initViewModels(): List<BaseViewModel> {
        nodeName = intent.getStringExtra(ExtraKeys.NODE_NAME) ?: throw MissingArgumentException("nodeName")
        vm = ViewModelProvider(this) { NodeViewModel(nodeName, application) }
        return listOf(vm)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val title: String? = intent.getStringExtra(ExtraKeys.NODE_TITLE)
        vb.topBar.setTitleText(title)
        vb.topBar.setOnRightClickListener(View.OnClickListener {
            vm.doFavoriteNode()
        })

        vm.node.observe(this) {
            vb.topBar.setTitleText(it.title)
        }

        vm.isFavorite.observe(this) {
            val (isFavorite, isManual) = it
            if (isManual) {
                toast(if (isFavorite) R.string.tips_favorite_success else R.string.tips_un_favorite_success)
            }
            vb.topBar.setRightIcon(if (isFavorite) R.drawable.ic_favorite else R.drawable.ic_un_favorite)
        }

        val preFragment = supportFragmentManager.findFragmentById(R.id.list_container)
        if (preFragment == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.list_container, NodePageFragment())
                .commit()
        }
    }

}