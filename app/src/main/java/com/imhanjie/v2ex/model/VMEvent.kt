package com.imhanjie.v2ex.model

class VMEvent(val event: Event, val text: String = "") {

    enum class Event {
        SHOW_LOADING,
        HIDE_LOADING,
        TOAST,
        ERROR
    }

}