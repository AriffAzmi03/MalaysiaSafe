package com.example.malaysiasafe;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.io.File;
import java.util.List;

public class ReportListAdapter extends android.widget.BaseAdapter {

    private Context context;
    private List<Report> reportList;

    public ReportListAdapter(Context context, List<Report> reportList) {
        this.context = context;
        this.reportList = reportList;
    }

    @Override
    public int getCount() {
        return reportList.size();
    }

    @Override
    public Object getItem(int position) {
        return reportList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.report_item, parent, false);
        }

        // Bind data to views
        TextView locationTextView = convertView.findViewById(R.id.reportLocation);
        TextView typeTextView = convertView.findViewById(R.id.reportType);
        TextView severityTextView = convertView.findViewById(R.id.reportSeverity);
        TextView descriptionTextView = convertView.findViewById(R.id.reportDescription);
        TextView timeTextView = convertView.findViewById(R.id.reportTime);
        ImageView imageView = convertView.findViewById(R.id.reportImage);

        Report report = reportList.get(position);

        locationTextView.setText(report.getLocation());
        typeTextView.setText(report.getType());
        severityTextView.setText(report.getSeverity());
        descriptionTextView.setText(report.getDescription());
        timeTextView.setText(report.getTime());

        // Load image
        String imagePath = report.getImageUrl();
        if (imagePath != null && !imagePath.isEmpty()) {
            File imageFile = new File(imagePath);
            if (imageFile.exists()) {
                Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
                imageView.setImageBitmap(bitmap);
            } else {
                imageView.setImageResource(R.drawable.ic_image_placeholder); // Fallback image
            }
        } else {
            imageView.setImageResource(R.drawable.ic_image_placeholder); // Fallback image
        }

        return convertView;
    }
}
