package com.imhanjie.v2ex.view.widget

import android.content.Context
import android.text.Spannable
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ImageSpan
import android.text.style.URLSpan
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.text.HtmlCompat
import cc.shinichi.library.ImagePreview
import com.imhanjie.support.e


class RichTextView @JvmOverloads constructor(
    ctx: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatTextView(ctx, attrs, defStyleAttr) {

    init {
        movementMethod = LinkMovementMethod.getInstance()
    }

    companion object {
        val IMAGE_SUFFIX = arrayOf(
            ".png", ".jpg", ".jpeg", ".gif"
        )
    }

    fun setRichContent(content: String?) {
        var result: CharSequence? = content
        content?.let {
            result = HtmlCompat.fromHtml(
                it,
                HtmlCompat.FROM_HTML_MODE_LEGACY,
                GlideImageGetter(this),
                null
            )
        }
        val s: Spannable = SpannableString(result)
        val urlSpans = s.getSpans(0, s.length, URLSpan::class.java)
        for (span in urlSpans) {
            val start = s.getSpanStart(span)
            val end = s.getSpanEnd(span)
            val url = span.url
            s.removeSpan(span)
            s.setSpan(object : URLSpan(url) {
                override fun onClick(widget: View) {
                    e("click: ${getURL()}")
                    val clickUrl = getURL()
                    for (suffix in IMAGE_SUFFIX) {
                        if (clickUrl.endsWith(suffix)) {
                            imagePreview(clickUrl)
                            return
                        }
                    }
                    super.onClick(widget)
                }

                override fun updateDrawState(ds: TextPaint) {
                    super.updateDrawState(ds)
                    ds.isUnderlineText = false
                }
            }, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        }

        val imageSpans = s.getSpans(0, s.length, ImageSpan::class.java)
        val imageUrls = mutableListOf<String>()
        val imagePositions = mutableListOf<String>()
        for (span in imageSpans) {
            val source = span.source!!
            imageUrls.add(source)
            val start: Int = s.getSpanStart(span)
            val end: Int = s.getSpanEnd(span)
            imagePositions.add("$start/$end")
        }
        for (span in imageSpans) {
            val start: Int = s.getSpanStart(span)
            val end: Int = s.getSpanEnd(span)
            val clickableSpan = object : ClickableSpan() {
                override fun onClick(widget: View) {
                    val url = imageUrls[imagePositions.indexOf("$start/$end")]
                    imagePreview(url)
                }
            }
            val clickSpans = s.getSpans(start, end, ClickableSpan::class.java)
            for (clickSpan in clickSpans) {
                s.removeSpan(clickSpan)
            }
            s.setSpan(clickableSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        }

        super.setText(s)
    }

    fun imagePreview(url: String) {
        ImagePreview
            .getInstance()
            .setContext(context)
            .setIndex(0)
            .setImage(url)
            .setShowDownButton(true)
            .setShowCloseButton(true)
            .setEnableDragClose(true)
            .setEnableUpDragClose(true)
            .start()
    }

}