package com.imhanjie.v2ex.view.widget

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.text.Html
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.imhanjie.support.ext.dpi
import com.imhanjie.v2ex.V2exApp

class GlideImageGetter(private val container: TextView) : Html.ImageGetter {

    companion object {
        var maxWidth = 0
    }

    override fun getDrawable(source: String): Drawable? {
        val urlDrawable = MyDrawable()
        val target = object : SimpleTarget<Bitmap>() {
            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                if (maxWidth == 0) {
                    maxWidth = container.measuredWidth
                }
                val targetWidth: Int
                val targetHeight: Int
                if (resource.width > maxWidth) {
                    targetWidth = maxWidth
                    targetHeight = maxWidth * resource.height / resource.width
                } else {
                    targetWidth = resource.width
                    targetHeight = resource.height
                }
                val drawable = BitmapDrawable(container.resources, resource).apply {
                    setBounds(0, 0, targetWidth, targetHeight)
                }
                urlDrawable.apply {
                    setBounds(0, 0, targetWidth, targetHeight)
                    this.drawable = drawable
                }
                container.text = container.text
            }
        }
        Glide.with(V2exApp.INSTANCE)
            .asBitmap()
            .load(source)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .override(150.dpi, 150.dpi)
            .transform(RoundedCorners(5.dpi))
            .into(target)
        return urlDrawable
    }

    inner class MyDrawable : BitmapDrawable(
        container.resources, Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888)
    ) {

        var drawable: Drawable? = null

        override fun draw(canvas: Canvas) {
            drawable?.draw(canvas)
//            canvas.drawRoundRect(0f, 0f, maxWidth.toFloat(), 200f, 5f.dp(), 5f.dp(), Paint().apply {
//                isAntiAlias = true
//                color = ContextCompat.getColor(container.context, R.color.widget_window_background)
//            })
        }
    }

}