package com.example.hotelbooking.util;

import android.view.LayoutInflater;
import android.widget.GridLayout;

import com.example.hotelbooking.databinding.ItemStatBinding;

/** Adds a small "number + label" card to a 2-column GridLayout (used by both dashboards). */
public final class StatCards {

    private StatCards() { }

    public static void add(GridLayout grid, LayoutInflater inflater, String value, String label) {
        ItemStatBinding b = ItemStatBinding.inflate(inflater, grid, false);
        b.tvStatValue.setText(value);
        b.tvStatLabel.setText(label);

        float density = grid.getResources().getDisplayMetrics().density;
        int margin = (int) (6 * density);
        GridLayout.LayoutParams lp = new GridLayout.LayoutParams(
                GridLayout.spec(GridLayout.UNDEFINED, 1f), GridLayout.spec(GridLayout.UNDEFINED, 1f));
        lp.width = 0;
        lp.setMargins(margin, margin, margin, margin);
        grid.addView(b.getRoot(), lp);
    }
}
