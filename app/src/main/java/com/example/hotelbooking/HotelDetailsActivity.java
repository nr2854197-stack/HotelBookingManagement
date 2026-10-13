package com.example.hotelbooking;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hotelbooking.databinding.ActivityHotelDetailsBinding;
import com.example.hotelbooking.models.Hotel;
import com.example.hotelbooking.models.Review;
import com.example.hotelbooking.models.RoomType;
import com.example.hotelbooking.repository.FavoriteRepository;
import com.example.hotelbooking.repository.RepositoryProvider;
import com.example.hotelbooking.repository.ReviewRepository;
import com.example.hotelbooking.session.SessionManager;
import com.example.hotelbooking.util.MapUtil;

import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.overlay.Marker;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class HotelDetailsActivity extends AppCompatActivity {

    private ActivityHotelDetailsBinding binding;
    private SessionManager session;
    private FavoriteRepository favoriteRepository;
    private ReviewRepository reviewRepository;
    private Hotel hotel;
    private boolean isFavorite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MapUtil.init(this); // must run before the MapView is inflated
        binding = ActivityHotelDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        session = new SessionManager(this);
        favoriteRepository = RepositoryProvider.favoriteRepository(this);
        reviewRepository = RepositoryProvider.reviewRepository(this);

        int hotelId = getIntent().getIntExtra("hotel_id", -1);
        RepositoryProvider.hotelRepository(this).getHotelById(hotelId, h -> {
            if (h == null) {
                Toast.makeText(this, "Hotel not found", Toast.LENGTH_SHORT).show();
                finish();
                return;
            }
            hotel = h;
            bind();
            setupMap();
            loadFavorite();
            loadReviews();
        });
    }

    private void bind() {
        binding.tvName.setText(hotel.getName());
        binding.tvAddress.setText(hotel.getAddress());
        binding.tvRating.setText("★ " + hotel.getRating() + "  •  From ₹" + hotel.getPricePerNight() + "/night");
        binding.tvFacilities.setText(String.join(" • ", hotel.getFacilities()));

        StringBuilder rooms = new StringBuilder();
        for (RoomType rt : hotel.getRoomTypes()) {
            if (rooms.length() > 0) rooms.append("\n");
            rooms.append(rt.getName()).append(" — ₹").append(rt.getPricePerNight()).append("/night  •  ");
            rooms.append(rt.getAvailable() > 0 ? rt.getAvailable() + " left" : "SOLD OUT");
        }
        binding.tvRoomTypes.setText(rooms.toString());

        binding.btnBookNow.setOnClickListener(v -> {
            Intent intent = new Intent(this, BookingActivity.class);
            intent.putExtra("hotel_id", hotel.getId());
            startActivity(intent);
        });

        binding.btnDirections.setOnClickListener(v -> {
            String uri = String.format(Locale.US, "geo:%f,%f?q=%f,%f(%s)",
                    hotel.getLatitude(), hotel.getLongitude(),
                    hotel.getLatitude(), hotel.getLongitude(), Uri.encode(hotel.getName()));
            try {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(uri)));
            } catch (ActivityNotFoundException e) {
                Toast.makeText(this, "No maps app installed for directions", Toast.LENGTH_SHORT).show();
            }
        });

        binding.btnFavorite.setOnClickListener(v -> {
            String email = session.getEmail();
            if (email == null) return;
            favoriteRepository.setFavorite(email, hotel.getId(), !isFavorite, state -> {
                isFavorite = state;
                updateFavoriteButton();
            });
        });

        binding.btnSubmitReview.setOnClickListener(v -> submitReview());
    }

    /** In-app OpenStreetMap view centred on the hotel with a pin. */
    private void setupMap() {
        binding.mapView.setTileSource(TileSourceFactory.MAPNIK);
        binding.mapView.setMultiTouchControls(true);
        // Let the map be dragged without the surrounding ScrollView stealing the gesture.
        binding.mapView.setOnTouchListener((v, event) -> {
            v.getParent().requestDisallowInterceptTouchEvent(true);
            return false;
        });

        GeoPoint point = new GeoPoint(hotel.getLatitude(), hotel.getLongitude());
        binding.mapView.getController().setZoom(15.0);
        binding.mapView.getController().setCenter(point);

        Marker marker = new Marker(binding.mapView);
        marker.setPosition(point);
        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        marker.setTitle(hotel.getName());
        marker.setSnippet(hotel.getAddress());
        binding.mapView.getOverlays().add(marker);
        binding.mapView.invalidate();
    }

    private void loadFavorite() {
        String email = session.getEmail();
        if (email == null) return;
        favoriteRepository.isFavorite(email, hotel.getId(), fav -> {
            isFavorite = fav;
            updateFavoriteButton();
        });
    }

    private void updateFavoriteButton() {
        binding.btnFavorite.setText(isFavorite ? "♥ Saved" : "♡ Save");
    }

    private void loadReviews() {
        reviewRepository.getReviews(hotel.getId(), reviews -> {
            binding.reviewsContainer.removeAllViews();
            if (reviews.isEmpty()) {
                binding.tvReviewsHeader.setText("Guest reviews — none yet, be the first!");
                return;
            }
            float sum = 0;
            for (Review r : reviews) sum += r.getRating();
            binding.tvReviewsHeader.setText(String.format(Locale.US,
                    "Guest reviews — ★ %.1f (%d)", sum / reviews.size(), reviews.size()));

            int pad = (int) (8 * getResources().getDisplayMetrics().density);
            for (Review r : reviews) {
                TextView tv = new TextView(this);
                tv.setPadding(0, pad, 0, pad);
                tv.setTextColor(androidx.core.content.ContextCompat.getColor(this, R.color.text_primary));
                tv.setText("★ " + (int) r.getRating() + "  " + r.getUserName() + " • " + r.getDate()
                        + "\n" + r.getComment());
                binding.reviewsContainer.addView(tv);
            }
        });
    }

    private void submitReview() {
        float rating = binding.ratingBarInput.getRating();
        if (rating < 1) {
            Toast.makeText(this, "Please select a star rating", Toast.LENGTH_SHORT).show();
            return;
        }
        String comment = binding.etReviewComment.getText().toString().trim();
        if (comment.isEmpty()) comment = "(no comment)";
        String date = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());

        Review review = new Review(0, hotel.getId(), session.getName(), rating, comment, date);
        reviewRepository.addReview(review, () -> {
            binding.ratingBarInput.setRating(0);
            binding.etReviewComment.setText("");
            Toast.makeText(this, "Thanks for your review!", Toast.LENGTH_SHORT).show();
            loadReviews();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        binding.mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        binding.mapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding.mapView.onDetach();
    }
}
