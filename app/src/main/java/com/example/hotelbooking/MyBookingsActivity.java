package com.example.hotelbooking;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.hotelbooking.adapters.BookingAdapter;
import com.example.hotelbooking.databinding.ActivityMyBookingsBinding;
import com.example.hotelbooking.models.Booking;
import com.example.hotelbooking.repository.BookingRepository;
import com.example.hotelbooking.repository.RepositoryProvider;
import com.example.hotelbooking.session.SessionManager;

import java.util.ArrayList;

public class MyBookingsActivity extends AppCompatActivity {

    private ActivityMyBookingsBinding binding;
    private BookingAdapter adapter;
    private BookingRepository bookingRepository;
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMyBookingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        bookingRepository = RepositoryProvider.bookingRepository(this);
        session = new SessionManager(this);

        adapter = new BookingAdapter(new ArrayList<>(),
                booking -> new AlertDialog.Builder(this)
                        .setTitle("Cancel booking?")
                        .setMessage("Cancel your stay at " + booking.getHotelName() + "? The room will be released.")
                        .setPositiveButton("Yes, cancel", (d, w) -> bookingRepository.cancelBooking(booking.getId(), this::refresh))
                        .setNegativeButton("Keep it", null)
                        .show(),
                this::showDetails);

        binding.rvBookings.setLayoutManager(new LinearLayoutManager(this));
        binding.rvBookings.setAdapter(adapter);
    }

    private void showDetails(Booking b) {
        String msg = "Booking ID: #" + b.getId()
                + "\nRoom: " + b.getRoomType()
                + "\nCheck-in: " + b.getCheckIn()
                + "\nCheck-out: " + b.getCheckOut()
                + "\nGuests: " + b.getGuests()
                + "\nTotal: ₹" + b.getTotalPrice()
                + "\nPayment: " + b.getPaymentMethod() + " (" + b.getPaymentStatus() + ")"
                + "\nStatus: " + b.getStatus();
        new AlertDialog.Builder(this).setTitle(b.getHotelName()).setMessage(msg)
                .setPositiveButton("Close", null).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        refresh();
    }

    private void refresh() {
        String email = session.getEmail();
        if (email == null) return;
        bookingRepository.getBookingsForUser(email, bookings -> {
            adapter.updateData(bookings);
            binding.tvEmptyState.setVisibility(bookings.isEmpty() ? View.VISIBLE : View.GONE);
            binding.rvBookings.setVisibility(bookings.isEmpty() ? View.GONE : View.VISIBLE);
        });
    }
}
