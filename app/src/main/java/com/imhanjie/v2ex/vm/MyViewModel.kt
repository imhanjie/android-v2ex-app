package com.imhanjie.v2ex.vm

import com.imhanjie.v2ex.model.TopicTab

class MyViewModel : BaseViewModel() {


    val tabs = mutableListOf<TopicTab>().apply {
        add(TopicTab("全部", "all"))
        add(TopicTab("技术", "tech"))
        add(TopicTab("创意", "creative"))
        add(TopicTab("好玩", "play"))
        add(TopicTab("Apple", "apple"))
        add(TopicTab("酷工作", "jobs"))
        add(TopicTab("交易", "deals"))
        add(TopicTab("城市", "city"))
        add(TopicTab("问与答", "qna"))
        add(TopicTab("最热", "hot"))
        add(TopicTab("R2", "r2"))
    }

}