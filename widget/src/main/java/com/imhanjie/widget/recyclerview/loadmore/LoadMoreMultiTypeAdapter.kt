package com.imhanjie.widget.recyclerview.loadmore

import com.drakeet.multitype.MultiTypeAdapter

class LoadMoreMultiTypeAdapter(items: List<Any>) : MultiTypeAdapter(items) {

    var loadMoreIvd: LoadMoreItemViewDelegate = LoadMoreItemViewDelegate()

    init {
        register(FooterItem::class.java, loadMoreIvd)
    }

}