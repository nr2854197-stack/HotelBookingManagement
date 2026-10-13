package com.example.hotelbooking;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hotelbooking.databinding.ActivityDashboardBinding;
import com.example.hotelbooking.models.Booking;
import com.example.hotelbooking.repository.BookingRepository;
import com.example.hotelbooking.repository.FavoriteRepository;
import com.example.hotelbooking.repository.RepositoryProvider;
import com.example.hotelbooking.session.SessionManager;
import com.example.hotelbooking.util.StatCards;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/** Personal dashboard: trip stats, spending, next trip and recent activity. */
public class DashboardActivity extends AppCompatActivity {

    private ActivityDashboardBinding binding;
    private SessionManager session;
    private BookingRepository bookingRepository;
    private FavoriteRepository favoriteRepository;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        session = new SessionManager(this);
        bookingRepository = RepositoryProvider.bookingRepository(this);
        favoriteRepository = RepositoryProvider.favoriteRepository(this);

        binding.tvProfileName.setText(session.getName());
        binding.tvProfileEmail.setText(session.getEmail());

        binding.btnDashBrowse.setOnClickListener(v -> finish());
        binding.btnDashBookings.setOnClickListener(v -> startActivity(new Intent(this, MyBookingsActivity.class)));
        binding.btnDashSaved.setOnClickListener(v -> startActivity(new Intent(this, FavoritesActivity.class)));
        binding.btnDashMap.setOnClickListener(v -> startActivity(new Intent(this, MapActivity.class)));
    }

    @Override
    protected void onResume() {
        super.onResume();
        String email = session.getEmail();
        if (email == null) return;
        bookingRepository.getBookingsForUser(email, bookings ->
                favoriteRepository.getFavoriteHotels(email, favs -> render(bookings, favs.size())));
    }

    private void render(List<Booking> bookings, int savedCount) {
        int upcoming = 0, completed = 0, cancelled = 0, spent = 0;
        Booking next = null;
        Date nextDate = null;

        for (Booking b : bookings) {
            switch (b.getStatus()) {
                case "Cancelled":
                    cancelled++;
                    break;
                case "Completed":
                    completed++;
                    spent += b.getTotalPrice();
                    break;
                default:
                    upcoming++;
                    spent += b.getTotalPrice();
                    Date d = parse(b.getCheckIn());
                    if (d != null && (nextDate == null || d.before(nextDate))) {
                        nextDate = d;
                        next = b;
                    }
            }
        }

        binding.statsGrid.removeAllViews();
        StatCards.add(binding.statsGrid, getLayoutInflater(), String.valueOf(bookings.size()), "Total bookings");
        StatCards.add(binding.statsGrid, getLayoutInflater(), String.valueOf(upcoming), "Upcoming");
        StatCards.add(binding.statsGrid, getLayoutInflater(), String.valueOf(completed), "Completed");
        StatCards.add(binding.statsGrid, getLayoutInflater(), String.valueOf(cancelled), "Cancelled");
        StatCards.add(binding.statsGrid, getLayoutInflater(), "₹" + spent, "Total spent");
        StatCards.add(binding.statsGrid, getLayoutInflater(), String.valueOf(savedCount), "Saved hotels");

        if (next == null) {
            binding.tvNextTrip.setText("No upcoming trips — find your next stay!");
        } else {
            binding.tvNextTrip.setText(next.getHotelName() + "\n" + next.getCheckIn() + " → " + next.getCheckOut()
                    + " • " + next.getRoomType() + " • ₹" + next.getTotalPrice());
        }

        if (bookings.isEmpty()) {
            binding.tvRecent.setText("Nothing yet.");
        } else {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < Math.min(3, bookings.size()); i++) {
                Booking b = bookings.get(i);
                if (i > 0) sb.append("\n");
                sb.append("• ").append(b.getHotelName()).append(" — ").append(b.getStatus())
                        .append(" (₹").append(b.getTotalPrice()).append(")");
            }
            binding.tvRecent.setText(sb.toString());
        }
    }

    private Date parse(String s) {
        try {
            return dateFormat.parse(s);
        } catch (ParseException e) {
            return null;
        }
    }
}
