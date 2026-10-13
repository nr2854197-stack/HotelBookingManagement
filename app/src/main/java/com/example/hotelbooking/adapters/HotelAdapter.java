package com.example.hotelbooking.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hotelbooking.databinding.ItemHotelBinding;
import com.example.hotelbooking.models.Hotel;

import java.util.List;

public class HotelAdapter extends RecyclerView.Adapter<HotelAdapter.HotelViewHolder> {

    public interface OnHotelClickListener {
        void onClick(Hotel hotel);
    }

    private List<Hotel> hotels;
    private final OnHotelClickListener listener;

    public HotelAdapter(List<Hotel> hotels, OnHotelClickListener listener) {
        this.hotels = hotels;
        this.listener = listener;
    }

    @NonNull
    @Override
    public HotelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemHotelBinding binding = ItemHotelBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new HotelViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull HotelViewHolder holder, int position) {
        Hotel hotel = hotels.get(position);
        holder.binding.tvHotelName.setText(hotel.getName());
        holder.binding.tvHotelCity.setText(hotel.getCity() + " • " + hotel.getAddress());
        holder.binding.tvHotelRating.setText("★ " + hotel.getRating());
        if (hotel.getFacilities() != null && !hotel.getFacilities().isEmpty()) {
            holder.binding.tvHotelAmenities.setText(String.join(" • ", hotel.getFacilities()));
            holder.binding.tvHotelAmenities.setVisibility(android.view.View.VISIBLE);
        } else {
            holder.binding.tvHotelAmenities.setVisibility(android.view.View.GONE);
        }
        holder.binding.tvHotelPrice.setText("₹" + hotel.getPricePerNight() + "/night");
        holder.itemView.setOnClickListener(v -> listener.onClick(hotel));
    }

    @Override
    public int getItemCount() {
        return hotels.size();
    }

    public void updateData(List<Hotel> newHotels) {
        this.hotels = newHotels;
        notifyDataSetChanged();
    }

    static class HotelViewHolder extends RecyclerView.ViewHolder {
        final ItemHotelBinding binding;

        HotelViewHolder(ItemHotelBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
