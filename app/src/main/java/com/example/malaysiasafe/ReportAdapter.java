package com.example.malaysiasafe;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

public class ReportAdapter extends BaseAdapter {

    private final Context context;
    private final List<Report> reports;
    private final DatabaseReference databaseReference;

    public ReportAdapter(Context context, List<Report> reports) {
        this.context = context;
        this.reports = reports;

        // Initialize Firebase Database reference
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance("https://malaysiasafe-150fb-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference("Reports")
                .child(currentUser.getUid());
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
        TextView typeView = convertView.findViewById(R.id.reportType);
        TextView severityView = convertView.findViewById(R.id.reportSeverity);
        TextView descriptionView = convertView.findViewById(R.id.reportDescription);
        TextView timeView = convertView.findViewById(R.id.reportTime);
        Button deleteButton = convertView.findViewById(R.id.deleteButton);


        Report report = reports.get(position);

        locationView.setText(report.getLocation());
        timeView.setText(report.getTime());
        typeView.setText(report.getType());
        descriptionView.setText(report.getDescription());
        severityView.setText(report.getSeverity());

        // Handle delete button click
        deleteButton.setOnClickListener(view -> {
            deleteReport(report, position);
        });

        return convertView;
    }

    private void deleteReport(Report report, int position) {
        // Remove the report from Firebase
        databaseReference.child(report.getId()) // Assuming the `Report` class has a unique `id` field
                .removeValue()
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(context, "Report deleted", Toast.LENGTH_SHORT).show();
                    reports.remove(position); // Remove the report from the local list
                    notifyDataSetChanged(); // Refresh the list view
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(context, "Failed to delete report: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
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



