/*
 * Copyright 2015 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package me.aidoc.client.util.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * A {@link RecyclerView.ItemDecoration} which draws dividers (along the right & bottom)
 * for certain {@link RecyclerView.ViewHolder} types.
 */
public class GridItemDividerDecoration extends RecyclerView.ItemDecoration {


    private final float dividerSize;
    private final Paint paint;

    public GridItemDividerDecoration(float dividerSize,
                                     @ColorInt int dividerColor) {

        this.dividerSize = dividerSize;
        paint = new Paint();
        paint.setColor(dividerColor);
        paint.setStyle(Paint.Style.FILL);
    }

    public GridItemDividerDecoration(@NonNull Context context,
                                     @DimenRes int dividerSizeResId,
                                     @ColorRes int dividerColorResId) {
        this(
                context.getResources().getDimensionPixelSize(dividerSizeResId),
                ContextCompat.getColor(context, dividerColorResId));
    }

    @Override
    public void onDrawOver(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
        if (parent.isAnimating()) return;

        final int childCount = parent.getChildCount();
        final RecyclerView.LayoutManager lm = parent.getLayoutManager();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            RecyclerView.ViewHolder viewHolder = parent.getChildViewHolder(child);

            if (requiresDivider(viewHolder)) {
                final int right = lm.getDecoratedRight(child);
                final int bottom = lm.getDecoratedBottom(child);
                // draw the bottom divider
                canvas.drawRect(lm.getDecoratedLeft(child),
                        bottom - dividerSize,
                        right,
                        bottom,
                        paint);
                // draw the right edge divider
                canvas.drawRect(right - dividerSize,
                        lm.getDecoratedTop(child),
                        right,
                        bottom - dividerSize,
                        paint);
            }

        }
    }

    private boolean requiresDivider(RecyclerView.ViewHolder viewHolder) {

        return true;
    }

}
