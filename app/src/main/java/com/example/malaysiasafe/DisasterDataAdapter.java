package com.example.malaysiasafe;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DisasterDataAdapter extends BaseAdapter {
    private final Context context;
    private final ArrayList<DisasterData> disasterList;
    private final DatabaseReference disasterDataRef;

    public DisasterDataAdapter(Context context, ArrayList<DisasterData> disasterList) {
        this.context = context;
        this.disasterList = disasterList;
        this.disasterDataRef = FirebaseDatabase.getInstance()
                .getReference("DisasterData");
    }

    @Override
    public int getCount() {
        return disasterList.size();
    }

    @Override
    public DisasterData getItem(int position) {
        return disasterList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_disaster_data, parent, false);
        }

        TextView locationView = convertView.findViewById(R.id.location_text);
        TextView infoView = convertView.findViewById(R.id.info_text);
        TextView centerView = convertView.findViewById(R.id.center_text);
        Button deleteButton = convertView.findViewById(R.id.delete_button);
        Button updateButton = convertView.findViewById(R.id.update_button);

        DisasterData disasterData = disasterList.get(position);
        locationView.setText(disasterData.getLocation());
        infoView.setText(disasterData.getInfo());
        centerView.setText(disasterData.getCenter());

        // Delete Button
        // deleteButton.setOnClickListener(v -> deleteDisasterData(disasterData.getId(), position));
        // Inside DisasterDataAdapter
        deleteButton.setOnClickListener(v -> {
            String id = disasterData.getId(); // Get the ID of the disaster data
            if (id != null && !id.isEmpty()) {
                ((OfflineModeActivity) context).deleteDisasterData(id);  // Pass only the id here
            } else {
                Toast.makeText(context, "Invalid ID for deletion", Toast.LENGTH_SHORT).show();
            }
        });


        // Update Button
        updateButton.setOnClickListener(v -> {
            String id = disasterData.getId();
            if (id == null || id.isEmpty()) {
                Toast.makeText(context, "Error: Cannot update data without a valid ID.", Toast.LENGTH_SHORT).show();
            } else {
                showUpdateDialog(disasterData);
            }
        });

        return convertView;
    }

    private void deleteDisasterData(String id, int position) {
        disasterDataRef.child(id).removeValue().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                disasterList.remove(position); // Remove the item from the list
                notifyDataSetChanged(); // Refresh the adapter
                Toast.makeText(context, "Data deleted successfully", Toast.LENGTH_SHORT).show();
            } else {
                String error = task.getException() != null ? task.getException().getMessage() : "Unknown error";
                Toast.makeText(context, "Failed to delete data" + error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showUpdateDialog(DisasterData disasterData) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.update_disaster_dialog, null);
        dialogBuilder.setView(dialogView);

        EditText editLocation = dialogView.findViewById(R.id.edit_location);
        EditText editInfo = dialogView.findViewById(R.id.edit_info);
        EditText editCenter = dialogView.findViewById(R.id.edit_center);
        Button updateButton = dialogView.findViewById(R.id.update_button);

        editLocation.setText(disasterData.getLocation());
        editInfo.setText(disasterData.getInfo());
        editCenter.setText(disasterData.getCenter());

        AlertDialog dialog = dialogBuilder.create();

        updateButton.setOnClickListener(v -> {
            String newLocation = editLocation.getText().toString().trim();
            String newInfo = editInfo.getText().toString().trim();
            String newCenter = editCenter.getText().toString().trim();

            if (newLocation.isEmpty() || newInfo.isEmpty() || newCenter.isEmpty()) {
                Toast.makeText(context, "All fields must be filled", Toast.LENGTH_SHORT).show();
                return;
            }
            // Update Firebase and local list
            Map<String, Object> updates = new HashMap<>();
            updates.put("location", newLocation);
            updates.put("info", newInfo);
            updates.put("center", newCenter);

            Log.d("UpdateDebug", "Updating ID: " + disasterData.getId() + " with " + updates);

            disasterDataRef.child(disasterData.getId()).updateChildren(updates)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            // Update the local list and refresh
                            disasterData.setLocation(newLocation);
                            disasterData.setInfo(newInfo);
                            disasterData.setCenter(newCenter);
                            notifyDataSetChanged();
                            Log.d("UpdateSuccess", "Data updated for ID: " + disasterData.getId());
                            Toast.makeText(context, "Data updated successfully", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        } else {
                            String errorMessage = task.getException() != null ? task.getException().getMessage() : "Unknown error";
                            Log.e("UpdateError", "Update failed for ID: " + disasterData.getId() + " Error: " + errorMessage);
                            Toast.makeText(context, "Failed to update data" + errorMessage, Toast.LENGTH_SHORT).show();
                        }
                    });
        });

        dialog.show();
    }
}
