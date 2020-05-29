package com.imhanjie.v2ex.view

import android.os.Bundle
import com.imhanjie.v2ex.BaseActivity
import com.imhanjie.v2ex.R
import com.imhanjie.v2ex.databinding.ActivityFavoriteTopicsBinding
import com.imhanjie.v2ex.view.fragment.FavoriteTopicsFragment
import com.imhanjie.v2ex.vm.BaseViewModel

class FavoriteTopicsActivity : BaseActivity<ActivityFavoriteTopicsBinding>() {

    override fun initViewModels(): List<BaseViewModel> {
        return emptyList()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager
            .beginTransaction()
            .add(R.id.list_container, FavoriteTopicsFragment())
            .commit()
    }

}