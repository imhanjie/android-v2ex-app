package com.imhanjie.v2ex.view.widget

import android.content.Context
import android.text.*
import android.text.style.ClickableSpan
import android.text.style.ImageSpan
import android.text.style.URLSpan
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.text.HtmlCompat
import cc.shinichi.library.ImagePreview
import com.imhanjie.support.ext.toActivity
import com.imhanjie.v2ex.common.RegexPattern
import com.imhanjie.v2ex.view.TopicActivity


class RichTextView @JvmOverloads constructor(
    ctx: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatTextView(ctx, attrs, defStyleAttr) {

    init {
        movementMethod = ClickableMovementMethod.getInstance()
        isClickable = false
        isLongClickable = false
    }

    fun setRichContent(content: String?) {
        var result: CharSequence = content ?: ""
        result = HtmlCompat.fromHtml(
            formatHtml(result.toString()),
            HtmlCompat.FROM_HTML_MODE_LEGACY,
            GlideImageGetter(this),
            Html.TagHandler { opening, tag, output, xmlReader ->

            }
        )
        val s: Spannable = SpannableString(result)
        val urlSpans = s.getSpans(0, s.length, URLSpan::class.java)
        for (span in urlSpans) {
            val start = s.getSpanStart(span)
            val end = s.getSpanEnd(span)
            val url = span.url
            s.removeSpan(span)
            s.setSpan(object : URLSpan(url) {
                override fun onClick(widget: View) {
                    val clickUrl = getURL()
                    if (RegexPattern.IMAGE_URL_PATTERN.matcher(clickUrl).find()) {
                        imagePreview(clickUrl)
                        return
                    }
                    if (RegexPattern.TOPIC_URL_PATTERN.matcher(clickUrl).find()) {
                        /*
                         * eg:    http://v2ex.com/t/123#reply123
                         *     || http://v2ex.com/t/123?p=1
                         *     || /t/123#reply123
                         *     || /t/123?p=1
                         * 由于通过了正则表达式，所以下面 split 不会抛出异常
                         */
                        val topicId = if (clickUrl.contains("#")) {
                            clickUrl.split("#")[0].split("/").last().toLong()
                        } else {
                            clickUrl.split("?")[0].split("/").last().toLong()
                        }
                        context.toActivity<TopicActivity>(
                            mapOf("topicId" to topicId)
                        )
                        return
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

    /**
     * 格式化处理 html
     */
    private fun formatHtml(content: String): String {
        var result = content
//            .replace("<li>", "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;•&nbsp;")
//            .replace("</li>", "</p>")
        result = removeTailTag(result, "div")
        result = removeTailTag(result, "p")
        return result
    }

    /**
     * 移除尾部元素标签
     */
    private fun removeTailTag(content: String, tagName: String): String {
        var result = content
        var tagStart = "<$tagName"
        val tagEnd = "</$tagName>"
        if (content.endsWith(tagEnd)) {
            val index = content.lastIndexOf(tagStart)
            tagStart = content.substring(index, content.indexOf(">", index) + 1)
            result = result.substring(0, index) + result.substring(index).replace(tagStart, "").replace(tagEnd, "")
        }
        return result
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