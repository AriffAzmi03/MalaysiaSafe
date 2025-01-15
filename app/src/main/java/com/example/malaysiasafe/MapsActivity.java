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

        // Add a marker for a disaster-prone area
        Marker disasterMarker = new Marker(map);
        disasterMarker.setPosition(new GeoPoint(4.5, 102.0)); // Example location
        disasterMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        disasterMarker.setTitle("Disaster-Prone Area");
        map.getOverlays().add(disasterMarker);

        // Add a polygon to mark a disaster-prone area
        Polygon disasterArea = new Polygon(map);
        List<GeoPoint> polygonPoints = new ArrayList<>();
        polygonPoints.add(new GeoPoint(4.4, 101.9));
        polygonPoints.add(new GeoPoint(4.4, 102.1));
        polygonPoints.add(new GeoPoint(4.6, 102.1));
        polygonPoints.add(new GeoPoint(4.6, 101.9));
        disasterArea.setPoints(polygonPoints);
        disasterArea.setFillColor(0x44FF0000); // Semi-transparent red
        disasterArea.setStrokeColor(0xFFFF0000); // Red border
        disasterArea.setTitle("High-Risk Flood Zone");
        map.getOverlays().add(disasterArea);

        // Add a polyline for an evacuation route
        Polyline evacuationRoute = new Polyline(map);
        List<GeoPoint> routePoints = new ArrayList<>();
        routePoints.add(new GeoPoint(4.5, 101.9));
        routePoints.add(new GeoPoint(4.55, 101.95));
        routePoints.add(new GeoPoint(4.6, 102.0));
        evacuationRoute.setPoints(routePoints);
        evacuationRoute.setColor(0xFF0000FF); // Blue color
        evacuationRoute.setTitle("Evacuation Route");
        map.getOverlays().add(evacuationRoute);

        // Add button to navigate to User Dashboard
        Button dashboardButton = findViewById(R.id.dashboardButton);
        dashboardButton.setOnClickListener(v -> navigateToUserDashboard());
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
