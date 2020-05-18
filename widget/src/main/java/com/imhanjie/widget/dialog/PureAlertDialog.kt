package com.imhanjie.widget.dialog

import android.app.Dialog
import android.content.Context
import android.view.Gravity
import android.view.View
import com.imhanjie.widget.R
import com.imhanjie.widget.databinding.WidgetDialogAlertBinding

class PureAlertDialog(ctx: Context) : BaseCustomDialog<WidgetDialogAlertBinding>(ctx) {

    override val windowGravity: Int
        get() = Gravity.CENTER

    override val windowAnimation: Int
        get() = R.style.WidgetCenterDialogAnimation

    private var positiveClickListener: ((dialog: Dialog) -> Unit)? = null
    private var negativeClickListener: ((dialog: Dialog) -> Unit)? = null
    private var neutralClickListener: ((dialog: Dialog) -> Unit)? = null

    override fun initView(root: View) {
        with(vb) {
            vb.btnPositive.setOnClickListener {
                positiveClickListener?.invoke(this@PureAlertDialog)
                dismiss()
            }
            vb.btnNegative.setOnClickListener {
                negativeClickListener?.invoke(this@PureAlertDialog)
                dismiss()
            }
            vb.btnNeutral.setOnClickListener {
                neutralClickListener?.invoke(this@PureAlertDialog)
                dismiss()
            }
        }
    }

    fun withTitle(title: CharSequence?): PureAlertDialog {
        vb.tvTitle.text = title
        vb.tvTitle.visibility = if (title == null) View.GONE else View.VISIBLE
        return this
    }

    fun withContent(content: CharSequence?): PureAlertDialog {
        vb.tvContent.text = content
        return this
    }

    fun withCancelable(cancelable: Boolean): PureAlertDialog {
        setCancelable(cancelable)
        return this
    }

    fun withPositiveText(text: CharSequence?): PureAlertDialog {
        vb.btnPositive.text = text
        vb.btnPositive.visibility = if (text == null) View.GONE else View.VISIBLE
        return this
    }

    fun withPositiveClick(clickListener: ((dialog: Dialog) -> Unit)?): PureAlertDialog {
        this.positiveClickListener = clickListener
        return this
    }

    fun withNegativeText(text: CharSequence?): PureAlertDialog {
        vb.btnNegative.text = text
        vb.btnNegative.visibility = if (text == null) View.GONE else View.VISIBLE
        return this
    }

    fun withNegativeClick(clickListener: ((dialog: Dialog) -> Unit)?): PureAlertDialog {
        this.negativeClickListener = clickListener
        return this
    }

    fun withNeutralText(text: CharSequence?): PureAlertDialog {
        vb.btnNeutral.text = text
        vb.btnNeutral.visibility = if (text == null) View.GONE else View.VISIBLE
        return this
    }

    fun withNeutralClick(clickListener: ((dialog: Dialog) -> Unit)?): PureAlertDialog {
        this.neutralClickListener = clickListener
        return this
    }


}