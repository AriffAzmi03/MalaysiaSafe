package com.example.malaysiasafe;



import android.content.Context;

import android.view.LayoutInflater;

import android.view.View;

import android.view.ViewGroup;

import android.widget.BaseAdapter;

import android.widget.TextView;



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



        Report report = reports.get(position);



        locationView.setText(report.getLocation());

        timeView.setText(report.getTime());

        typeView.setText(report.getType());



        return convertView;

    }

}



