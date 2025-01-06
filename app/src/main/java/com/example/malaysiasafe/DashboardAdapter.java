package com.example.malaysiasafe;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class DashboardAdapter extends BaseAdapter {
    private final Context context;
    private final String[] gridItems;
    private final int[] gridIcons;

    public DashboardAdapter(Context context, String[] gridItems, int[] gridIcons) {
        this.context = context;
        this.gridItems = gridItems;
        this.gridIcons = gridIcons;
    }

    @Override
    public int getCount() {
        return gridItems.length;
    }

    @Override
    public Object getItem(int position) {
        return gridItems[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.grid_item, parent, false);
        }

        ImageView icon = convertView.findViewById(R.id.gridIcon);
        TextView title = convertView.findViewById(R.id.gridTitle);

        icon.setImageResource(gridIcons[position]);
        title.setText(gridItems[position]);

        return convertView;
    }
}
