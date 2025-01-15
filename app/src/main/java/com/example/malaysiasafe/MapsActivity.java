package com.example.malaysiasafe;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.Polygon;
import org.osmdroid.views.overlay.Polyline;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends AppCompatActivity {
    private MapView map;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Initialize the osmdroid configuration
        Context ctx = getApplicationContext();
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));

        // Set up the map
        map = findViewById(R.id.map);
        map.setTileSource(TileSourceFactory.MAPNIK);
        map.setBuiltInZoomControls(true);
        map.setMultiTouchControls(true);

        // Set default zoom level and center
        IMapController mapController = map.getController();
        mapController.setZoom(10.0);
        GeoPoint startPoint = new GeoPoint(4.210484, 101.975766); // Center point in Malaysia
        mapController.setCenter(startPoint);

        // Add disaster-prone areas and evacuation routes
        addDisasterAndEvacuationData();

        // Add button to navigate to User Dashboard
        Button dashboardButton = findViewById(R.id.dashboardButton);
        dashboardButton.setOnClickListener(v -> navigateToUserDashboard());
    }

    private void addDisasterAndEvacuationData() {
        // Disaster-Prone Area 1
        Marker disasterMarker1 = new Marker(map);
        disasterMarker1.setPosition(new GeoPoint(4.5, 102.0)); // Example location
        disasterMarker1.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        disasterMarker1.setTitle("Disaster-Prone Area 1");
        map.getOverlays().add(disasterMarker1);

        // Disaster Area Polygon 1
        Polygon disasterArea1 = new Polygon(map);
        List<GeoPoint> polygonPoints1 = new ArrayList<>();
        polygonPoints1.add(new GeoPoint(4.4, 101.9));
        polygonPoints1.add(new GeoPoint(4.4, 102.1));
        polygonPoints1.add(new GeoPoint(4.6, 102.1));
        polygonPoints1.add(new GeoPoint(4.6, 101.9));
        disasterArea1.setPoints(polygonPoints1);
        disasterArea1.setFillColor(0x44FF0000); // Semi-transparent red
        disasterArea1.setStrokeColor(0xFFFF0000); // Red border
        disasterArea1.setTitle("High-Risk Flood Zone");
        map.getOverlays().add(disasterArea1);

        // Evacuation Route 1
        Polyline evacuationRoute1 = new Polyline(map);
        List<GeoPoint> routePoints1 = new ArrayList<>();
        routePoints1.add(new GeoPoint(4.5, 101.9));
        routePoints1.add(new GeoPoint(4.55, 101.95));
        routePoints1.add(new GeoPoint(4.6, 102.0));
        evacuationRoute1.setPoints(routePoints1);
        evacuationRoute1.setColor(0xFF0000FF); // Blue color
        evacuationRoute1.setTitle("Evacuation Route 1");
        map.getOverlays().add(evacuationRoute1);

        // Additional Disaster-Prone Area 2
        Marker disasterMarker2 = new Marker(map);
        disasterMarker2.setPosition(new GeoPoint(4.8, 103.0)); // Example location
        disasterMarker2.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        disasterMarker2.setTitle("Disaster-Prone Area 2");
        map.getOverlays().add(disasterMarker2);

        // Disaster Area Polygon 2
        Polygon disasterArea2 = new Polygon(map);
        List<GeoPoint> polygonPoints2 = new ArrayList<>();
        polygonPoints2.add(new GeoPoint(4.7, 102.8));
        polygonPoints2.add(new GeoPoint(4.7, 103.2));
        polygonPoints2.add(new GeoPoint(4.9, 103.2));
        polygonPoints2.add(new GeoPoint(4.9, 102.8));
        disasterArea2.setPoints(polygonPoints2);
        disasterArea2.setFillColor(0x44FF0000); // Semi-transparent red
        disasterArea2.setStrokeColor(0xFFFF0000); // Red border
        disasterArea2.setTitle("Earthquake Risk Zone");
        map.getOverlays().add(disasterArea2);

        // Evacuation Route 2
        Polyline evacuationRoute2 = new Polyline(map);
        List<GeoPoint> routePoints2 = new ArrayList<>();
        routePoints2.add(new GeoPoint(4.8, 102.9));
        routePoints2.add(new GeoPoint(4.85, 103.0));
        routePoints2.add(new GeoPoint(4.9, 103.1));
        evacuationRoute2.setPoints(routePoints2);
        evacuationRoute2.setColor(0xFF0000FF); // Blue color
        evacuationRoute2.setTitle("Evacuation Route 2");
        map.getOverlays().add(evacuationRoute2);
    }

    private void navigateToUserDashboard() {
        // Explicit Intent to navigate to UserDashboardActivity
        Intent intent = new Intent(MapsActivity.this, UserDashboardActivity.class);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        map.onResume(); // needed for compass, my location overlays, etc.
    }

    @Override
    public void onPause() {
        super.onPause();
        map.onPause(); // needed for compass, my location overlays, etc.
    }
}
