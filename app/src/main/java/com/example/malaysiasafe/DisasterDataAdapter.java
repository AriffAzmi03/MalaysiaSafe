package com.example.malaysiasafe;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DisasterDataAdapter extends RecyclerView.Adapter<DisasterDataAdapter.DisasterViewHolder> {

    private JSONArray disasterData;

    public DisasterDataAdapter(JSONArray disasterData) {
        this.disasterData = disasterData;
    }

    @Override
    public DisasterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_disaster_data, parent, false);
        return new DisasterViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(DisasterViewHolder holder, int position) {
        try {
            JSONObject data = disasterData.getJSONObject(position);
            holder.locationTextView.setText("Location: " + data.getString("location"));
            holder.infoTextView.setText("Details: " + data.getString("info"));
            holder.centerTextView.setText("Evacuation Center: " + data.getString("center"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return disasterData.length();
    }

    public static class DisasterViewHolder extends RecyclerView.ViewHolder {
        TextView locationTextView, infoTextView, centerTextView;

        public DisasterViewHolder(View itemView) {
            super(itemView);
            locationTextView = itemView.findViewById(R.id.locationTextView);
            infoTextView = itemView.findViewById(R.id.infoTextView);
            centerTextView = itemView.findViewById(R.id.centerTextView);
        }
    }
}

