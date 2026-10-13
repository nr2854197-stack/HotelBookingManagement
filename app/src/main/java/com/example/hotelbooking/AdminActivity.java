package com.example.hotelbooking;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hotelbooking.databinding.ActivityAdminBinding;
import com.example.hotelbooking.databinding.DialogAddHotelBinding;
import com.example.hotelbooking.databinding.ItemAdminHotelBinding;
import com.example.hotelbooking.models.Booking;
import com.example.hotelbooking.models.Hotel;
import com.example.hotelbooking.models.RoomType;
import com.example.hotelbooking.repository.BookingRepository;
import com.example.hotelbooking.repository.HotelRepository;
import com.example.hotelbooking.repository.RepositoryProvider;
import com.example.hotelbooking.repository.UserRepository;
import com.example.hotelbooking.session.SessionManager;
import com.example.hotelbooking.util.StatCards;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** Hotel-owner dashboard: business stats, availability per room type, add/delete hotels, all bookings. */
public class AdminActivity extends AppCompatActivity {

    private ActivityAdminBinding binding;
    private HotelRepository hotelRepository;
    private BookingRepository bookingRepository;
    private UserRepository userRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (!new SessionManager(this).isAdmin()) {
            Toast.makeText(this, "Owner access only", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        hotelRepository = RepositoryProvider.hotelRepository(this);
        bookingRepository = RepositoryProvider.bookingRepository(this);
        userRepository = RepositoryProvider.userRepository(this);

        binding.btnAddHotel.setOnClickListener(v -> showAddHotelDialog());
        load();
    }

    private void load() {
        hotelRepository.getAllHotels(hotels ->
                bookingRepository.getAllBookings(bookings ->
                        userRepository.getUserCount(users -> render(hotels, bookings, users))));
    }

    private void render(List<Hotel> hotels, List<Booking> bookings, int users) {
        int active = 0, revenue = 0, roomsLeft = 0;
        for (Booking b : bookings) {
            if (!"Cancelled".equals(b.getStatus())) {
                active++;
                revenue += b.getTotalPrice();
            }
        }
        for (Hotel h : hotels) {
            for (RoomType rt : h.getRoomTypes()) roomsLeft += rt.getAvailable();
        }

        binding.adminStatsGrid.removeAllViews();
        StatCards.add(binding.adminStatsGrid, getLayoutInflater(), String.valueOf(hotels.size()), "Hotels listed");
        StatCards.add(binding.adminStatsGrid, getLayoutInflater(), String.valueOf(roomsLeft), "Rooms available");
        StatCards.add(binding.adminStatsGrid, getLayoutInflater(), String.valueOf(bookings.size()), "Total bookings");
        StatCards.add(binding.adminStatsGrid, getLayoutInflater(), String.valueOf(active), "Active bookings");
        StatCards.add(binding.adminStatsGrid, getLayoutInflater(), "₹" + revenue, "Revenue");
        StatCards.add(binding.adminStatsGrid, getLayoutInflater(), String.valueOf(users), "Registered users");

        // Hotels + live room availability
        binding.adminHotelsContainer.removeAllViews();
        for (Hotel h : hotels) {
            ItemAdminHotelBinding row = ItemAdminHotelBinding.inflate(getLayoutInflater(), binding.adminHotelsContainer, false);
            row.tvAHName.setText(h.getName() + " — " + h.getCity());
            StringBuilder info = new StringBuilder();
            for (RoomType rt : h.getRoomTypes()) {
                if (info.length() > 0) info.append("  |  ");
                info.append(rt.getName()).append(": ").append(rt.getAvailable()).append(" left @ ₹").append(rt.getPricePerNight());
            }
            row.tvAHInfo.setText(info.toString());
            row.btnAHDelete.setOnClickListener(v -> new AlertDialog.Builder(this)
                    .setTitle("Delete hotel?")
                    .setMessage("Remove " + h.getName() + " from the app?")
                    .setPositiveButton("Delete", (d, w) -> hotelRepository.deleteHotel(h.getId(), this::load))
                    .setNegativeButton("Keep", null)
                    .show());
            binding.adminHotelsContainer.addView(row.getRoot());
        }

        // Latest bookings across all guests
        binding.adminBookingsContainer.removeAllViews();
        if (bookings.isEmpty()) {
            TextView none = new TextView(this);
            none.setText("No bookings yet.");
            none.setTextColor(androidx.core.content.ContextCompat.getColor(this, R.color.text_secondary));
            binding.adminBookingsContainer.addView(none);
        }
        int pad = (int) (6 * getResources().getDisplayMetrics().density);
        List<Booking> recent = new ArrayList<>(bookings);
        Collections.sort(recent, (a, b) -> Integer.compare(b.getId(), a.getId()));
        for (int i = 0; i < Math.min(10, recent.size()); i++) {
            Booking b = recent.get(i);
            TextView tv = new TextView(this);
            tv.setPadding(0, pad, 0, pad);
            tv.setTextColor(androidx.core.content.ContextCompat.getColor(this, R.color.text_primary));
            tv.setText("#" + b.getId() + "  " + b.getHotelName() + " • " + b.getRoomType() + " • "
                    + b.getCheckIn() + " → " + b.getCheckOut() + "\n₹" + b.getTotalPrice() + " • "
                    + b.getPaymentMethod() + " (" + b.getPaymentStatus() + ") • " + b.getStatus()
                    + "\nby " + b.getUserEmail());
            binding.adminBookingsContainer.addView(tv);
        }
    }

    private void showAddHotelDialog() {
        DialogAddHotelBinding form = DialogAddHotelBinding.inflate(getLayoutInflater());
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Add a hotel")
                .setView(form.getRoot())
                .setPositiveButton("Add", null) // overridden below so invalid input keeps the dialog open
                .setNegativeButton("Cancel", null)
                .create();
        dialog.show();

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener((View v) -> {
            String name = form.etHName.getText().toString().trim();
            String city = form.etHCity.getText().toString().trim();
            String address = form.etHAddress.getText().toString().trim();
            String facilitiesCsv = form.etHFacilities.getText().toString().trim();

            if (name.isEmpty() || city.isEmpty() || address.isEmpty()) {
                Toast.makeText(this, "Name, city and address are required", Toast.LENGTH_SHORT).show();
                return;
            }
            int price, rooms;
            double lat, lng;
            try {
                price = Integer.parseInt(form.etHPrice.getText().toString().trim());
                rooms = Integer.parseInt(form.etHRooms.getText().toString().trim());
                lat = Double.parseDouble(form.etHLat.getText().toString().trim());
                lng = Double.parseDouble(form.etHLng.getText().toString().trim());
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Enter valid price, rooms, latitude and longitude", Toast.LENGTH_SHORT).show();
                return;
            }
            if (price <= 0 || rooms <= 0) {
                Toast.makeText(this, "Price and rooms must be more than 0", Toast.LENGTH_SHORT).show();
                return;
            }
            if (lat < -90 || lat > 90 || lng < -180 || lng > 180) {
                Toast.makeText(this, "Latitude/longitude out of range", Toast.LENGTH_SHORT).show();
                return;
            }

            List<String> facilities = new ArrayList<>();
            for (String f : facilitiesCsv.split(",")) {
                if (!f.trim().isEmpty()) facilities.add(f.trim());
            }
            List<RoomType> roomTypes = new ArrayList<>();
            roomTypes.add(new RoomType("Standard", price, rooms));

            // id 0 is a placeholder; the repository assigns the real id.
            Hotel hotel = new Hotel(0, name, city, address, 4.0f, price, lat, lng, facilities, roomTypes);
            hotelRepository.addHotel(hotel, () -> {
                dialog.dismiss();
                Toast.makeText(this, "Hotel added", Toast.LENGTH_SHORT).show();
                load();
            });
        });
    }
}
