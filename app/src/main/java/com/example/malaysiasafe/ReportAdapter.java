package com.example.malaysiasafe;

import android.content.Context;

import android.graphics.Bitmap;

import android.graphics.BitmapFactory;

import android.os.AsyncTask;

import android.view.LayoutInflater;

import android.view.View;

import android.view.ViewGroup;

import android.widget.BaseAdapter;

import android.widget.ImageView;

import android.widget.TextView;

import java.io.InputStream;

import java.net.URL;

import java.util.List;

public class ReportAdapter extends BaseAdapter {

    private final Context context;
    private final List<Report> reports;

    public ReportAdapter(Context context, List<Report> reports) {

        this.context = context;

        this.reports = reports;

    }

    @Override

    public int getCount() {

        return reports.size();

    }

    @Override

    public Object getItem(int position) {

        return reports.get(position);

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

        TextView locationView = convertView.findViewById(R.id.reportLocation);

        TextView timeView = convertView.findViewById(R.id.reportTime);

        TextView typeView = convertView.findViewById(R.id.reportType);

        TextView descriptionView = convertView.findViewById(R.id.reportDescription);

        TextView severityView = convertView.findViewById(R.id.reportSeverity);

        ImageView imageView = convertView.findViewById(R.id.reportImage); // Add this

        Report report = reports.get(position);

        locationView.setText(report.getLocation());

        timeView.setText(report.getTime());

        typeView.setText(report.getType());

        descriptionView.setText(report.getDescription()); // Set description

        severityView.setText(report.getSeverity()); // Set severity

        // Load image if available
        if (report.getImageUrl() != null && !report.getImageUrl().isEmpty()) {
            loadImageFromUrl(report.getImageUrl(), imageView);
        } else {
            imageView.setImageResource(R.drawable.ic_image_placeholder); // Set a placeholder image
        }

        return convertView;
    }

    // Method to load image from URL
    private void loadImageFromUrl(String url, ImageView imageView) {
        new LoadImageTask(imageView).execute(url);
    }

    // AsyncTask to load the image in the background
    private static class LoadImageTask extends AsyncTask<String, Void, Bitmap> {
        private final ImageView imageView;

        LoadImageTask(ImageView imageView) {
            this.imageView = imageView;
        }

        @Override
        protected Bitmap doInBackground(String... urls) {
            String url = urls[0];
            Bitmap bitmap = null;
            try {
                InputStream input = new URL(url).openStream();
                bitmap = BitmapFactory.decodeStream(input);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        // Method to load image from URL
        private void loadImageFromUrl(String url, ImageView imageView) {
            new loadimagetask(imageView).execute(url);
        }

        // AsyncTask to load the image in the background
        private static class loadimagetask extends AsyncTask<String, Void, Bitmap> {
            private final ImageView imageView;

            loadimagetask(ImageView imageView) {
                this.imageView = imageView;
            }

            @Override
            protected Bitmap doInBackground(String... urls) {
                String url = urls[0];
                Bitmap bitmap = null;
                try {
                    InputStream input = new URL(url).openStream();
                    bitmap = BitmapFactory.decodeStream(input);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return bitmap;
            }

            @Override
            protected void onPostExecute(Bitmap result) {
                if (result != null) {
                    imageView.setImageBitmap(result);
                } else {
                    imageView.setImageResource(R.drawable.ic_image_placeholder); // Set a placeholder image if loading fails
                }
            }
        }
    }
}


