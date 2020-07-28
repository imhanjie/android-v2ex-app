package com.imhanjie.v2ex.model

data class LocalUserInfo(
    var userName: String = "",
    var userAvatar: String = "",
    var a2Cookie: String = "",
    var moneyGold: Long = 0,
    var moneySilver: Long = 0,
    var moneyBronze: Long = 0
) {

    companion object {
        val EMPTY = LocalUserInfo()
    }

}