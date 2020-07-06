package com.imhanjie.v2ex.common

import com.imhanjie.v2ex.BuildConfig

object SpConstants {
    const val FILE_APP_SESSION = "app_session"
    const val USER_NAME = "user_name"
    const val USER_AVATAR = "user_avatar"
    const val A2_COOKIE = "a2_cookie"
    const val MONEY_GOLD = "money_gold"
    const val MONEY_SILVER = "money_silver"
    const val MONEY_BRONZE = "money_bronze"

    const val FILE_APP_CONFIG = "app_config"
    const val UI_MODE = "ui_mode"
}

object ExtraKeys {
    const val NODE = BuildConfig.APPLICATION_ID + ".node"
    const val TITLE = BuildConfig.APPLICATION_ID + ".title"
    const val CONTENT = BuildConfig.APPLICATION_ID + ".content"
    const val NODE_TITLE = BuildConfig.APPLICATION_ID + ".node_title"
    const val NODE_NAME = BuildConfig.APPLICATION_ID + ".node_name"
    const val USER_NAME = BuildConfig.APPLICATION_ID + ".user_name"
    const val TOPIC_ID = BuildConfig.APPLICATION_ID + ".topic_id"
    const val FROM_FAVORITE_TOPICS = BuildConfig.APPLICATION_ID + ".from_favorite_topics"
}

object Event {
    const val MAIN_SCROLL_TO_TOP = "main_scroll_to_top"
}