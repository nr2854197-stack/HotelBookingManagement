package com.example.hotelbooking;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.hotelbooking.adapters.HotelAdapter;
import com.example.hotelbooking.databinding.ActivityFavoritesBinding;
import com.example.hotelbooking.repository.FavoriteRepository;
import com.example.hotelbooking.repository.RepositoryProvider;
import com.example.hotelbooking.session.SessionManager;

import java.util.ArrayList;

public class FavoritesActivity extends AppCompatActivity {

    private ActivityFavoritesBinding binding;
    private HotelAdapter adapter;
    private FavoriteRepository favoriteRepository;
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFavoritesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        session = new SessionManager(this);
        favoriteRepository = RepositoryProvider.favoriteRepository(this);

        adapter = new HotelAdapter(new ArrayList<>(), hotel -> {
            Intent intent = new Intent(this, HotelDetailsActivity.class);
            intent.putExtra("hotel_id", hotel.getId());
            startActivity(intent);
        });
        binding.rvFavorites.setLayoutManager(new LinearLayoutManager(this));
        binding.rvFavorites.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        String email = session.getEmail();
        if (email == null) return;
        favoriteRepository.getFavoriteHotels(email, hotels -> {
            adapter.updateData(hotels);
            binding.tvEmptyFav.setVisibility(hotels.isEmpty() ? View.VISIBLE : View.GONE);
            binding.rvFavorites.setVisibility(hotels.isEmpty() ? View.GONE : View.VISIBLE);
        });
    }
}
