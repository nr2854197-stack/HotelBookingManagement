package com.example.hotelbooking;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.hotelbooking.adapters.HotelAdapter;
import com.example.hotelbooking.databinding.ActivityHomeBinding;
import com.example.hotelbooking.models.Hotel;
import com.example.hotelbooking.repository.HotelRepository;
import com.example.hotelbooking.repository.RepositoryProvider;
import com.example.hotelbooking.session.SessionManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private ActivityHomeBinding binding;
    private HotelAdapter adapter;
    private HotelRepository hotelRepository;
    private SessionManager session;

    private final List<String> roomOptions = Arrays.asList("Any room", "Standard", "Deluxe", "Suite");
    private final List<String> sortOptions = Arrays.asList("Top rated", "Price: low to high", "Price: high to low");

    private List<TextView> cityChips;
    private boolean isUpdatingFromChip = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        session = new SessionManager(this);
        hotelRepository = RepositoryProvider.hotelRepository(this);

        binding.tvGreeting.setText("Hi " + session.getName() + ", find your stay");
        binding.tvNavAdmin.setVisibility(session.isAdmin() ? View.VISIBLE : View.GONE);

        // RecyclerView setup
        adapter = new HotelAdapter(new ArrayList<>(), hotel -> {
            Intent intent = new Intent(this, HotelDetailsActivity.class);
            intent.putExtra("hotel_id", hotel.getId());
            startActivity(intent);
        });
        binding.rvHotels.setLayoutManager(new LinearLayoutManager(this));
        binding.rvHotels.setAdapter(adapter);

        // Room filter adapter
        ArrayAdapter<String> roomAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, roomOptions);
        roomAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerRoomFilter.setAdapter(roomAdapter);

        // Sort adapter
        ArrayAdapter<String> sortAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, sortOptions);
        sortAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerSort.setAdapter(sortAdapter);

        // Setup City Quick Chips
        setupCityChips();

        // Dynamic search as user types
        binding.etSearchCity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String query = s.toString().trim();
                binding.btnClearSearch.setVisibility(query.isEmpty() ? View.GONE : View.VISIBLE);
                if (!isUpdatingFromChip) {
                    syncCityChipsWithQuery(query);
                }
                loadHotels();
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        binding.btnClearSearch.setOnClickListener(v -> {
            binding.etSearchCity.setText("");
            highlightChip(binding.chipCityAll);
            loadHotels();
        });

        // Dynamic Filter Listeners (triggers instant reload on any selection change)
        binding.spinnerRoomFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                loadHotels();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        binding.spinnerSort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                loadHotels();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        binding.etMaxPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                loadHotels();
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });

        // Reset buttons
        binding.tvResetFilters.setOnClickListener(v -> resetAllFilters());
        binding.btnEmptyReset.setOnClickListener(v -> resetAllFilters());

        // Top Navigation listeners
        binding.tvNavDashboard.setOnClickListener(v -> startActivity(new Intent(this, DashboardActivity.class)));
        binding.tvNavMap.setOnClickListener(v -> startActivity(new Intent(this, MapActivity.class)));
        binding.tvNavSaved.setOnClickListener(v -> startActivity(new Intent(this, FavoritesActivity.class)));
        binding.tvNavBookings.setOnClickListener(v -> startActivity(new Intent(this, MyBookingsActivity.class)));
        binding.tvNavAdmin.setOnClickListener(v -> startActivity(new Intent(this, AdminActivity.class)));
        binding.tvNavLogout.setOnClickListener(v -> {
            session.logout();
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });
    }

    private void setupCityChips() {
        cityChips = Arrays.asList(
                binding.chipCityAll,
                binding.chipCityAhmedabad,
                binding.chipCitySurat,
                binding.chipCityGandhinagar,
                binding.chipCityVadodara,
                binding.chipCityRajkot
        );

        binding.chipCityAll.setOnClickListener(v -> selectCityChip(binding.chipCityAll, ""));
        binding.chipCityAhmedabad.setOnClickListener(v -> selectCityChip(binding.chipCityAhmedabad, "Ahmedabad"));
        binding.chipCitySurat.setOnClickListener(v -> selectCityChip(binding.chipCitySurat, "Surat"));
        binding.chipCityGandhinagar.setOnClickListener(v -> selectCityChip(binding.chipCityGandhinagar, "Gandhinagar"));
        binding.chipCityVadodara.setOnClickListener(v -> selectCityChip(binding.chipCityVadodara, "Vadodara"));
        binding.chipCityRajkot.setOnClickListener(v -> selectCityChip(binding.chipCityRajkot, "Rajkot"));
    }

    private void selectCityChip(TextView selectedChip, String cityName) {
        highlightChip(selectedChip);
        isUpdatingFromChip = true;
        binding.etSearchCity.setText(cityName);
        if (!cityName.isEmpty()) {
            binding.etSearchCity.setSelection(cityName.length());
        }
        isUpdatingFromChip = false;
        loadHotels();
    }

    private void highlightChip(TextView selectedChip) {
        for (TextView chip : cityChips) {
            boolean isSelected = chip == selectedChip;
            chip.setBackgroundResource(isSelected ? R.drawable.bg_city_chip_selected : R.drawable.bg_city_chip_unselected);
            chip.setTextColor(ContextCompat.getColor(this, isSelected ? R.color.white : R.color.text_primary));
            chip.setTypeface(null, isSelected ? android.graphics.Typeface.BOLD : android.graphics.Typeface.NORMAL);
        }
    }

    private void syncCityChipsWithQuery(String query) {
        TextView matchingChip = null;
        if (query.isEmpty()) {
            matchingChip = binding.chipCityAll;
        } else {
            for (TextView chip : cityChips) {
                if (chip != binding.chipCityAll && chip.getText().toString().equalsIgnoreCase(query)) {
                    matchingChip = chip;
                    break;
                }
            }
        }
        highlightChip(matchingChip);
    }

    private void resetAllFilters() {
        isUpdatingFromChip = true;
        binding.etSearchCity.setText("");
        isUpdatingFromChip = false;
        highlightChip(binding.chipCityAll);
        binding.spinnerRoomFilter.setSelection(0);
        binding.spinnerSort.setSelection(0);
        binding.etMaxPrice.setText("");
        loadHotels();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadHotels();
    }

    private void loadHotels() {
        String city = binding.etSearchCity.getText().toString().trim();

        String maxPriceText = binding.etMaxPrice.getText().toString().trim();
        int maxPrice = maxPriceText.isEmpty() ? Integer.MAX_VALUE : Integer.parseInt(maxPriceText);

        int roomPos = binding.spinnerRoomFilter.getSelectedItemPosition();
        String roomType = roomPos <= 0 ? "" : roomOptions.get(roomPos);
        int sortPos = binding.spinnerSort.getSelectedItemPosition();

        boolean hasActiveFilter = !city.isEmpty() || !maxPriceText.isEmpty() || roomPos > 0;
        binding.tvResetFilters.setVisibility(hasActiveFilter ? View.VISIBLE : View.GONE);

        // Always pass minRating = 0f so that ALL hotels in the city appear!
        hotelRepository.search(city, 0f, maxPrice, roomType, results -> {
            List<Hotel> sorted = new ArrayList<>(results);
            if (sortPos == 1) {
                Collections.sort(sorted, (a, b) -> Integer.compare(a.getPricePerNight(), b.getPricePerNight()));
            } else if (sortPos == 2) {
                Collections.sort(sorted, (a, b) -> Integer.compare(b.getPricePerNight(), a.getPricePerNight()));
            } // else: sorted by rating

            adapter.updateData(sorted);

            if (sorted.isEmpty()) {
                binding.rvHotels.setVisibility(View.GONE);
                binding.layoutEmptyState.setVisibility(View.VISIBLE);
                binding.tvResultsLabel.setText("0 hotels found");
                if (!city.isEmpty()) {
                    binding.tvEmptyTitle.setText("No hotels in \"" + city + "\"");
                    binding.tvEmptyMessage.setText("We don't have hotels listed in this city yet. Try Ahmedabad, Surat, Vadodara, Gandhinagar, or Rajkot.");
                } else {
                    binding.tvEmptyTitle.setText("No hotels match filters");
                    binding.tvEmptyMessage.setText("Try increasing your maximum budget or changing room type.");
                }
            } else {
                binding.rvHotels.setVisibility(View.VISIBLE);
                binding.layoutEmptyState.setVisibility(View.GONE);
                if (city.isEmpty()) {
                    binding.tvResultsLabel.setText("Showing all " + sorted.size() + " hotel(s)");
                } else {
                    binding.tvResultsLabel.setText(sorted.size() + " hotel(s) found in \"" + city + "\"");
                }
            }
        });
    }
}
