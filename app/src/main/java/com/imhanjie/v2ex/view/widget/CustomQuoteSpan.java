package com.imhanjie.v2ex.view.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.Layout;
import android.text.style.LeadingMarginSpan;
import android.text.style.LineBackgroundSpan;
import android.util.TypedValue;

/**
 * android.text.style.QuoteSpan hard-codes the strip color and gap. :(
 */
public class CustomQuoteSpan implements LeadingMarginSpan, LineBackgroundSpan {

    private static final int DP_TOP_BOTTOM_GAP = 8;

    private final Context context;
    private final int backgroundColor;
    private final int stripeColor;
    private final float stripeWidth;
    private final float gap;

    public CustomQuoteSpan(Context context, int backgroundColor, int stripeColor, float stripeWidth, float gap) {
        this.context = context;
        this.backgroundColor = backgroundColor;
        this.stripeColor = stripeColor;
        this.stripeWidth = stripeWidth;
        this.gap = gap;
    }

    @Override
    public int getLeadingMargin(boolean first) {
        return (int) (stripeWidth + gap);
    }

    @Override
    public void drawLeadingMargin(Canvas c, Paint p, int x, int dir, int top, int baseline, int bottom,
                                  CharSequence text, int start, int end, boolean first, Layout layout) {
        Paint.Style style = p.getStyle();
        int paintColor = p.getColor();

        p.setStyle(Paint.Style.FILL);
        p.setColor(stripeColor);

        c.drawRect(x, top - dp(DP_TOP_BOTTOM_GAP), x + dir * stripeWidth, bottom + dp(DP_TOP_BOTTOM_GAP), p);

        p.setStyle(style);
        p.setColor(paintColor);
    }

    @Override
    public void drawBackground(Canvas c, Paint p, int left, int right, int top, int baseline, int bottom, CharSequence text, int start, int end, int lnum) {
        int paintColor = p.getColor();
        p.setColor(backgroundColor);
        c.drawRect(left, top - dp(DP_TOP_BOTTOM_GAP), right, bottom + dp(DP_TOP_BOTTOM_GAP), p);
        p.setColor(paintColor);
    }

    private float dp(float value) {
        return TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                value,
                context.getResources().getDisplayMetrics()
        );
    }

}
