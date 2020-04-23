package com.imhanjie.widget.recyclerview.loadmore

class LoadMoreItems : ArrayList<Any>() {

    private var footerItem: FooterItem = FooterItem()

    init {
        add(0, footerItem)
    }

    override fun add(element: Any): Boolean {
        add(size - 1, element)
        return true
    }

    override fun addAll(elements: Collection<Any>): Boolean {
        return super.addAll(size - 1, elements)
    }

    override fun clear() {
        super.clear()
        add(0, footerItem)
    }

    // --------------------------------------------------------------------------------

    /**
     * 外部不要直接调用 size() 方法，使用 getItemSize()
     */
    val itemSize: Int
        get() = size - 1

    val footerPosition: Int
        get() = size - 1

    fun setFooterType(type: FooterType) {
        (get(size - 1) as? FooterItem)?.let {
            it.type = type
        }
    }

    fun getFooterType(): FooterType {
        val item = get(size - 1)
        return if (item is FooterItem) item.type else FooterType.NO_MORE
    }

}