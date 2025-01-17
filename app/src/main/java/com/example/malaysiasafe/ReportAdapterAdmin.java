package com.example.malaysiasafe;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ReportAdapterAdmin extends RecyclerView.Adapter<ReportAdapterAdmin.ViewHolder> {

    private final Context context;
    private final List<Report> reports;

    public ReportAdapterAdmin(Context context, List<Report> reports) {
        this.context = context;
        this.reports = reports;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_report_admin, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Report report = reports.get(position);

        // Bind report details to the TextViews
        holder.location.setText("Location: " + report.getLocation());
        holder.type.setText("Type: " + report.getType());
        holder.severity.setText("Severity: " + report.getSeverity());
        holder.description.setText("Description: " + report.getDescription());
        holder.time.setText("Time: " + report.getTime());
    }

    @Override
    public int getItemCount() {
        return reports.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView location, type, severity, description, time;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            // Initialize TextViews for report details
            location = itemView.findViewById(R.id.reportLocation);
            type = itemView.findViewById(R.id.reportType);
            severity = itemView.findViewById(R.id.reportSeverity);
            description = itemView.findViewById(R.id.reportDescription);
            time = itemView.findViewById(R.id.reportTime);
        }
    }
}


