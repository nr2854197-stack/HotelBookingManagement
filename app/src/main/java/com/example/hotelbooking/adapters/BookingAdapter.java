package com.example.hotelbooking.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hotelbooking.R;
import com.example.hotelbooking.databinding.ItemBookingBinding;
import com.example.hotelbooking.models.Booking;

import java.util.List;

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.BookingViewHolder> {

    public interface OnCancelListener { void onCancel(Booking booking); }
    public interface OnBookingClickListener { void onClick(Booking booking); }

    private List<Booking> bookings;
    private final OnCancelListener cancelListener;
    private final OnBookingClickListener clickListener;

    public BookingAdapter(List<Booking> bookings, OnCancelListener cancelListener,
                          OnBookingClickListener clickListener) {
        this.bookings = bookings;
        this.cancelListener = cancelListener;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public BookingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BookingViewHolder(
                ItemBookingBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BookingViewHolder holder, int position) {
        Booking b = bookings.get(position);
        holder.binding.tvBHotelName.setText(b.getHotelName());
        holder.binding.tvBDetails.setText(b.getRoomType() + " • " + b.getCheckIn() + " to " + b.getCheckOut()
                + " • " + b.getGuests() + " guest(s)\n₹" + b.getTotalPrice() + " • "
                + b.getPaymentMethod() + " (" + b.getPaymentStatus() + ")");
        holder.binding.tvBStatus.setText(b.getStatus());

        int colorRes;
        switch (b.getStatus()) {
            case "Cancelled": colorRes = R.color.text_secondary; break;
            case "Completed": colorRes = R.color.primary; break;
            default: colorRes = R.color.accent;
        }
        holder.binding.tvBStatus.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), colorRes));

        holder.binding.btnCancelBooking.setVisibility("Upcoming".equals(b.getStatus()) ? View.VISIBLE : View.GONE);
        holder.binding.btnCancelBooking.setOnClickListener(v -> cancelListener.onCancel(b));
        holder.itemView.setOnClickListener(v -> clickListener.onClick(b));
    }

    @Override
    public int getItemCount() { return bookings.size(); }

    public void updateData(List<Booking> newBookings) {
        this.bookings = newBookings;
        notifyDataSetChanged();
    }

    static class BookingViewHolder extends RecyclerView.ViewHolder {
        final ItemBookingBinding binding;

        BookingViewHolder(ItemBookingBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
