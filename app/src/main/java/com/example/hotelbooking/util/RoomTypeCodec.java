package com.example.hotelbooking.util;

import com.example.hotelbooking.models.RoomType;

import java.util.ArrayList;
import java.util.List;

/** Converts room types to/from the compact "name:price:available;..." text stored in the DB. */
public final class RoomTypeCodec {

    private RoomTypeCodec() { }

    public static List<RoomType> decode(String raw) {
        List<RoomType> list = new ArrayList<>();
        if (raw == null || raw.isEmpty()) return list;
        for (String part : raw.split(";")) {
            String[] p = part.split(":");
            if (p.length == 3) {
                list.add(new RoomType(p[0], Integer.parseInt(p[1]), Integer.parseInt(p[2])));
            }
        }
        return list;
    }

    public static String encode(List<RoomType> types) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < types.size(); i++) {
            if (i > 0) sb.append(";");
            RoomType rt = types.get(i);
            sb.append(rt.getName()).append(":").append(rt.getPricePerNight()).append(":").append(rt.getAvailable());
        }
        return sb.toString();
    }
}
