package com.example.malaysiasafe;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

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

    @NonNull
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

        // Handle delete action
        deleteButton.setOnClickListener(view -> deleteDisasterData(disasterData.getId(), position));

        // Handle update action
        updateButton.setOnClickListener(view -> showUpdateDialog(disasterData));

        return convertView;
    }

    private void deleteDisasterData(String id, int position) {
        disasterDataRef.child(id).removeValue().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(context, "Data deleted successfully", Toast.LENGTH_SHORT).show();
                disasterList.remove(position);
                notifyDataSetChanged();
            } else {
                Toast.makeText(context, "Failed to delete data", Toast.LENGTH_SHORT).show();
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

        updateButton.setOnClickListener(view -> {
            String newLocation = editLocation.getText().toString().trim();
            String newInfo = editInfo.getText().toString().trim();
            String newCenter = editCenter.getText().toString().trim();

            if (newLocation.isEmpty() || newInfo.isEmpty() || newCenter.isEmpty()) {
                Toast.makeText(context, "All fields must be filled", Toast.LENGTH_SHORT).show();
            } else {
                disasterDataRef.child(disasterData.getId())
                        .setValue(new DisasterData(newLocation, newInfo, newCenter))
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Toast.makeText(context, "Data updated successfully", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                notifyDataSetChanged();
                            } else {
                                Toast.makeText(context, "Failed to update data", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        dialog.show();
    }
}
