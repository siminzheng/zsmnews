package com.example.zsmnews.jpush;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Copyright(c) 2020 极光
 * Description
 */
public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

    private int spanCount;           //列数
    private int spacing;             //间隔
    private boolean includeEdge;     //是否包含边缘

    public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
        this.spanCount = spanCount;
        this.spacing = spacing;
        this.includeEdge = includeEdge;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        //这里是关键，需要根据你有几列来判断
        int position = parent.getChildAdapterPosition(view); // item position
        int column = position % spanCount; // item column
        int childCount = parent.getAdapter().getItemCount();

        if (includeEdge) {
            outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
            outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

            outRect.bottom = 20; // item bottom
            if (position < spanCount) { // top edge
                outRect.top = 20;
            }

            boolean isLastRow = isLastRow(position, spanCount, childCount);
            if (isLastRow) {
                outRect.bottom = 30;
            }

        } else {
            outRect.left = 0; // column * ((1f / spanCount) * spacing)
            outRect.right = 0; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
            if (position >= spanCount) {
                outRect.top = 20; // item top
            }
            outRect.bottom = 0;
        }
    }

    private boolean isLastRow(int pos, int spanCount, int childCount) {
        int lines = childCount % spanCount == 0 ? childCount / spanCount : childCount / spanCount + 1;
        return lines == pos / spanCount + 1;
    }
}
