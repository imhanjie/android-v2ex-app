package com.imhanjie.widget.recyclerview;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 仅支持 LinearLayoutManager 的 item 间距
 *
 * @author hanjie
 * @date 2019/4/17
 */

public class SpaceItemDecoration extends RecyclerView.ItemDecoration {

    /**
     * item 上下间的间距
     */
    private int mItemSpace;

    /**
     * item 左侧间距
     */
    private int mLeftSpace;

    /**
     * item 右侧间距
     */
    private int mRightSpace;

    public SpaceItemDecoration(int itemSpace, int leftSpace, int rightSpace) {
        mItemSpace = itemSpace;
        mLeftSpace = leftSpace;
        mRightSpace = rightSpace;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        if (parent.getAdapter() == null) {
            return;
        }
        outRect.left = mLeftSpace;
        outRect.right = mRightSpace;
        outRect.bottom = mItemSpace;
        if (parent.getChildAdapterPosition(view) == 0) {
            outRect.top = mItemSpace;
        }
    }

}
