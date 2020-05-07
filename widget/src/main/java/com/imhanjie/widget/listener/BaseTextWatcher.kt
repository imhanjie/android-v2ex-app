package com.imhanjie.widget.listener

import android.text.Editable
import android.text.TextWatcher

/**
 * Created by hanjie on 2017/6/7.
 */
abstract class BaseTextWatcher : TextWatcher {

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}

    override fun afterTextChanged(s: Editable) {}

}