package com.imhanjie.v2ex.view

import androidx.core.text.HtmlCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.imhanjie.v2ex.GlideImageGetter
import com.imhanjie.v2ex.databinding.ItemReplyBinding
import com.imhanjie.v2ex.parser.model.Reply
import com.imhanjie.widget.recyclerview.base.BaseItemViewDelegate
import com.imhanjie.widget.recyclerview.base.VBViewHolder

class ReplyAdapter : BaseItemViewDelegate<Reply, ItemReplyBinding>() {

    override fun bindTo(holder: VBViewHolder<ItemReplyBinding>, position: Int, item: Reply) {
        val vb = holder.vb
        Glide.with(vb.root)
            .load(item.userAvatar)
            .transform(CircleCrop())
            .into(vb.userAvatar)
        vb.userName.text = item.userName
        vb.time.text = item.time
        vb.no.text = "#${item.no}"
//        val html =
//            "迫于疫情，这星期开始 work from home 了<a target=\"_blank\" href=\"https://i.imgur.com/RYgyrJH.jpg\" rel=\"nofollow noopener\"><img src=\"https://i.imgur.com/RYgyrJH.jpg\" class=\"embedded_image\" rel=\"noreferrer\"></a>"
        val res = HtmlCompat.fromHtml(
            item.content,
            HtmlCompat.FROM_HTML_MODE_LEGACY,
            GlideImageGetter(vb.content),
            null
        )
        vb.content.text = res
    }

}