package com.imhanjie.v2ex.common

data class LocalUserInfo(
    var userName: String = "",
    var userAvatar: String = "",
    var a2Cookie: String = ""
) {

    companion object {
        val EMPTY = LocalUserInfo()
    }

}