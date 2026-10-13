package com.example.hotelbooking;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hotelbooking.databinding.ActivityMapBinding;
import com.example.hotelbooking.models.Hotel;
import com.example.hotelbooking.repository.RepositoryProvider;
import com.example.hotelbooking.util.MapUtil;

import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.overlay.Marker;

/** Full-screen map showing every hotel; tapping a pin opens that hotel. */
public class MapActivity extends AppCompatActivity {

    private ActivityMapBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MapUtil.init(this);
        binding = ActivityMapBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.mapViewAll.setTileSource(TileSourceFactory.MAPNIK);
        binding.mapViewAll.setMultiTouchControls(true);
        binding.mapViewAll.getController().setZoom(7.0);
        binding.mapViewAll.getController().setCenter(new GeoPoint(22.6, 72.7)); // Gujarat

        RepositoryProvider.hotelRepository(this).getAllHotels(hotels -> {
            for (Hotel hotel : hotels) {
                Marker marker = new Marker(binding.mapViewAll);
                marker.setPosition(new GeoPoint(hotel.getLatitude(), hotel.getLongitude()));
                marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
                marker.setTitle(hotel.getName());
                marker.setSnippet("₹" + hotel.getPricePerNight() + "/night • ★ " + hotel.getRating());
                marker.setOnMarkerClickListener((mk, view) -> {
                    Intent intent = new Intent(this, HotelDetailsActivity.class);
                    intent.putExtra("hotel_id", hotel.getId());
                    startActivity(intent);
                    return true;
                });
                binding.mapViewAll.getOverlays().add(marker);
            }
            binding.mapViewAll.invalidate();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        binding.mapViewAll.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        binding.mapViewAll.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding.mapViewAll.onDetach();
    }
}
