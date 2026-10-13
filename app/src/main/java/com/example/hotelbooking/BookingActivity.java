package com.example.hotelbooking;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hotelbooking.databinding.ActivityBookingBinding;
import com.example.hotelbooking.models.Booking;
import com.example.hotelbooking.models.Hotel;
import com.example.hotelbooking.models.RoomType;
import com.example.hotelbooking.repository.BookingRepository;
import com.example.hotelbooking.repository.RepositoryProvider;
import com.example.hotelbooking.session.SessionManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class BookingActivity extends AppCompatActivity {

    private static final List<String> PAYMENT_METHODS = Arrays.asList("Cash at hotel", "UPI", "Card");

    private ActivityBookingBinding binding;
    private Calendar checkInCal;
    private Calendar checkOutCal;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBookingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        int hotelId = getIntent().getIntExtra("hotel_id", -1);
        BookingRepository bookingRepository = RepositoryProvider.bookingRepository(this);
        SessionManager session = new SessionManager(this);

        RepositoryProvider.hotelRepository(this).getHotelById(hotelId, hotel -> {
            if (hotel == null) {
                Toast.makeText(this, "Hotel not found", Toast.LENGTH_SHORT).show();
                finish();
                return;
            }
            setupForm(hotel, bookingRepository, session);
        });
    }

    private int nightsCount() {
        if (checkInCal == null || checkOutCal == null) return 0;
        long diff = checkOutCal.getTimeInMillis() - checkInCal.getTimeInMillis();
        return (int) TimeUnit.MILLISECONDS.toDays(diff);
    }

    private void setupForm(Hotel hotel, BookingRepository bookingRepository, SessionManager session) {
        binding.tvBookingHotelName.setText("Booking: " + hotel.getName());

        List<String> roomNames = new ArrayList<>();
        for (RoomType rt : hotel.getRoomTypes()) {
            roomNames.add(rt.getName() + " — ₹" + rt.getPricePerNight() + "/night ("
                    + (rt.getAvailable() > 0 ? rt.getAvailable() + " left" : "sold out") + ")");
        }
        ArrayAdapter<String> roomTypeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, roomNames);
        roomTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerRoomType.setAdapter(roomTypeAdapter);

        ArrayAdapter<String> paymentAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, PAYMENT_METHODS);
        paymentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerPayment.setAdapter(paymentAdapter);

        binding.spinnerRoomType.post(() -> updateTotal(hotel));

        binding.etCheckIn.setOnClickListener(v -> {
            Calendar cal = Calendar.getInstance();
            DatePickerDialog dialog = new DatePickerDialog(this, (view, y, m, d) -> {
                Calendar picked = Calendar.getInstance();
                picked.set(y, m, d, 0, 0, 0);
                checkInCal = picked;
                binding.etCheckIn.setText(dateFormat.format(picked.getTime()));
                // A check-out on/before the new check-in is no longer valid.
                if (checkOutCal != null && !checkOutCal.after(checkInCal)) {
                    checkOutCal = null;
                    binding.etCheckOut.setText("");
                }
                updateTotal(hotel);
            }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
            dialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
            dialog.show();
        });

        binding.etCheckOut.setOnClickListener(v -> {
            Calendar base = checkInCal != null ? (Calendar) checkInCal.clone() : Calendar.getInstance();
            base.add(Calendar.DAY_OF_MONTH, 1);
            DatePickerDialog dialog = new DatePickerDialog(this, (view, y, m, d) -> {
                Calendar picked = Calendar.getInstance();
                picked.set(y, m, d, 0, 0, 0);
                checkOutCal = picked;
                binding.etCheckOut.setText(dateFormat.format(picked.getTime()));
                updateTotal(hotel);
            }, base.get(Calendar.YEAR), base.get(Calendar.MONTH), base.get(Calendar.DAY_OF_MONTH));
            dialog.getDatePicker().setMinDate(base.getTimeInMillis());
            dialog.show();
        });

        binding.spinnerRoomType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updateTotal(hotel);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        binding.btnConfirmBooking.setOnClickListener(v -> {
            String guestsText = binding.etGuests.getText().toString().trim();
            int guests = guestsText.isEmpty() ? 1 : Integer.parseInt(guestsText);
            int nights = nightsCount();
            int selectedIndex = binding.spinnerRoomType.getSelectedItemPosition();
            String userEmail = session.getEmail();

            if (checkInCal == null || checkOutCal == null || nights <= 0) {
                Toast.makeText(this, "Please pick a valid check-in and check-out date", Toast.LENGTH_SHORT).show();
                return;
            }
            if (guests < 1) {
                Toast.makeText(this, "At least 1 guest is required", Toast.LENGTH_SHORT).show();
                return;
            }
            if (selectedIndex < 0 || selectedIndex >= hotel.getRoomTypes().size()) {
                Toast.makeText(this, "Please select a room type", Toast.LENGTH_SHORT).show();
                return;
            }
            if (userEmail == null) {
                Toast.makeText(this, "Please log in again", Toast.LENGTH_SHORT).show();
                return;
            }

            RoomType roomType = hotel.getRoomTypes().get(selectedIndex);
            if (roomType.getAvailable() <= 0) {
                Toast.makeText(this, "That room type is sold out", Toast.LENGTH_SHORT).show();
                return;
            }

            int total = roomType.getPricePerNight() * nights;
            String method = PAYMENT_METHODS.get(binding.spinnerPayment.getSelectedItemPosition());
            Booking booking = new Booking(hotel.getId(), hotel.getName(), roomType.getName(),
                    dateFormat.format(checkInCal.getTime()), dateFormat.format(checkOutCal.getTime()),
                    guests, total, method);

            if ("Cash at hotel".equals(method)) {
                booking.setPaymentStatus("Pay at hotel");
                save(booking, bookingRepository, userEmail);
            } else {
                new AlertDialog.Builder(this)
                        .setTitle("Pay ₹" + total)
                        .setMessage("Demo payment via " + method + ".\nIn a real app this opens the payment gateway (e.g. Razorpay).")
                        .setPositiveButton("Pay now", (d, w) -> {
                            booking.setPaymentStatus("Paid");
                            save(booking, bookingRepository, userEmail);
                        })
                        .setNegativeButton("Cancel", null)
                        .show();
            }
        });
    }

    private void save(Booking booking, BookingRepository repository, String userEmail) {
        binding.btnConfirmBooking.setEnabled(false);
        repository.addBooking(booking, userEmail, saved -> {
            if (saved == null) {
                binding.btnConfirmBooking.setEnabled(true);
                Toast.makeText(this, "Sorry, that room type just sold out", Toast.LENGTH_LONG).show();
                return;
            }
            Toast.makeText(this, "Booking confirmed!", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, MyBookingsActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        });
    }

    private void updateTotal(Hotel hotel) {
        int selectedIndex = binding.spinnerRoomType.getSelectedItemPosition();
        int nights = nightsCount();
        if (selectedIndex < 0 || selectedIndex >= hotel.getRoomTypes().size() || nights <= 0) {
            binding.tvTotalPrice.setText("Select valid dates to see the total");
            return;
        }
        int price = hotel.getRoomTypes().get(selectedIndex).getPricePerNight();
        binding.tvTotalPrice.setText("Total: ₹" + (price * nights) + " for " + nights + " night(s)");
    }
}
