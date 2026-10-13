package com.example.hotelbooking.data;

import com.example.hotelbooking.models.Hotel;
import com.example.hotelbooking.models.RoomType;

import java.util.Arrays;
import java.util.List;

/** Starter catalog inserted on launch. Owners can add/delete hotels from the Admin dashboard. */
public final class SeedData {

    private SeedData() { }

    public static List<Hotel> getHotels() {
        return Arrays.asList(
                // ==================== AHMEDABAD (7 Hotels | ₹950 - ₹3200) ====================
                new Hotel(1, "Sunrise Residency", "Ahmedabad", "Near CG Road, Navrangpura, Ahmedabad", 4.5f, 1800,
                        23.0300, 72.5580,
                        Arrays.asList("Free WiFi", "AC", "Breakfast", "Parking"),
                        Arrays.asList(new RoomType("Standard", 1800, 5), new RoomType("Deluxe", 2500, 3))),

                new Hotel(2, "Green Valley Inn", "Ahmedabad", "Satellite Road, Ahmedabad", 4.2f, 1400,
                        23.0258, 72.5083,
                        Arrays.asList("Free WiFi", "Parking", "Restaurant"),
                        Arrays.asList(new RoomType("Standard", 1400, 8), new RoomType("Deluxe", 2100, 3))),

                new Hotel(3, "Sabarmati River Retreat", "Ahmedabad", "Ashram Road, Ahmedabad", 4.7f, 2800,
                        23.0410, 72.5710,
                        Arrays.asList("Free WiFi", "AC", "River View", "Pool", "Breakfast"),
                        Arrays.asList(new RoomType("Deluxe", 2800, 4), new RoomType("Suite", 3400, 2))),

                new Hotel(4, "Heritage Haveli Stay", "Ahmedabad", "Old City, Kalupur, Ahmedabad", 4.4f, 2100,
                        23.0225, 72.5950,
                        Arrays.asList("Free WiFi", "AC", "Heritage Architecture", "Restaurant"),
                        Arrays.asList(new RoomType("Standard", 2100, 4), new RoomType("Deluxe", 2900, 2))),

                new Hotel(5, "City Express Lodge", "Ahmedabad", "Near Railway Station, Maninagar, Ahmedabad", 3.9f, 950,
                        23.0030, 72.6020,
                        Arrays.asList("Free WiFi", "Room Service", "24/7 Desk"),
                        Arrays.asList(new RoomType("Standard", 950, 10), new RoomType("Deluxe", 1350, 4))),

                new Hotel(6, "The Grand Orchid", "Ahmedabad", "SG Highway, Bodakdev, Ahmedabad", 4.6f, 3200,
                        23.0450, 72.5120,
                        Arrays.asList("Free WiFi", "AC", "Gym", "Pool", "Breakfast"),
                        Arrays.asList(new RoomType("Deluxe", 3200, 5), new RoomType("Suite", 3500, 2))),

                new Hotel(7, "Comfort Transit Hub", "Ahmedabad", "Near Airport Road, Hansol, Ahmedabad", 4.1f, 1600,
                        23.0720, 72.6280,
                        Arrays.asList("Free WiFi", "AC", "Airport Shuttle", "Breakfast"),
                        Arrays.asList(new RoomType("Standard", 1600, 6), new RoomType("Deluxe", 2300, 3))),

                // ==================== SURAT (7 Hotels | ₹900 - ₹3400) ====================
                new Hotel(8, "Riverfront Suites", "Surat", "Adajan, Surat", 4.5f, 2400,
                        21.1959, 72.7933,
                        Arrays.asList("Free WiFi", "AC", "Breakfast", "Gym"),
                        Arrays.asList(new RoomType("Deluxe", 2400, 6), new RoomType("Suite", 3300, 2))),

                new Hotel(9, "Diamond City Inn", "Surat", "Ring Road, Surat", 4.3f, 1750,
                        21.1890, 72.8280,
                        Arrays.asList("Free WiFi", "AC", "Parking", "Restaurant"),
                        Arrays.asList(new RoomType("Standard", 1750, 7), new RoomType("Deluxe", 2400, 4))),

                new Hotel(10, "Dumas Beach Resort", "Surat", "Near Dumas Beach, Surat", 4.6f, 3100,
                        21.0820, 72.7050,
                        Arrays.asList("Free WiFi", "AC", "Sea Breeze View", "Pool", "Restaurant"),
                        Arrays.asList(new RoomType("Deluxe", 3100, 4), new RoomType("Suite", 3500, 2))),

                new Hotel(11, "Tapi Vista Hotel", "Surat", "Nanpura, Surat", 4.2f, 1900,
                        21.1820, 72.8120,
                        Arrays.asList("Free WiFi", "AC", "River View", "Room Service"),
                        Arrays.asList(new RoomType("Standard", 1900, 5), new RoomType("Deluxe", 2600, 3))),

                new Hotel(12, "Silk Route Residency", "Surat", "Varachha, Surat", 4.0f, 1250,
                        21.2150, 72.8520,
                        Arrays.asList("Free WiFi", "AC", "Parking"),
                        Arrays.asList(new RoomType("Standard", 1250, 8), new RoomType("Deluxe", 1800, 4))),

                new Hotel(13, "Metro Budget Stay", "Surat", "Near Surat Railway Station, Surat", 3.8f, 900,
                        21.2050, 72.8410,
                        Arrays.asList("Free WiFi", "24/7 Desk", "Luggage Storage"),
                        Arrays.asList(new RoomType("Standard", 900, 12))),

                new Hotel(14, "The Royal Palace Stay", "Surat", "Piplod, Surat", 4.7f, 3400,
                        21.1620, 72.7750,
                        Arrays.asList("Free WiFi", "AC", "Gym", "Spa", "Restaurant"),
                        Arrays.asList(new RoomType("Deluxe", 3400, 5), new RoomType("Suite", 3500, 2))),

                // ==================== GANDHINAGAR (7 Hotels | ₹920 - ₹3350) ====================
                new Hotel(15, "City Comfort Stay", "Gandhinagar", "Sector 21, Gandhinagar", 4.6f, 2100,
                        23.2156, 72.6369,
                        Arrays.asList("Free WiFi", "AC", "Pool", "Breakfast"),
                        Arrays.asList(new RoomType("Deluxe", 2100, 4), new RoomType("Suite", 3200, 1))),

                new Hotel(16, "Capital Greens Residency", "Gandhinagar", "Sector 11, Near CH Road, Gandhinagar", 4.4f, 1850,
                        23.2210, 72.6480,
                        Arrays.asList("Free WiFi", "AC", "Garden View", "Breakfast"),
                        Arrays.asList(new RoomType("Standard", 1850, 6), new RoomType("Deluxe", 2600, 3))),

                new Hotel(17, "Infocity Tech Lodge", "Gandhinagar", "Near Infocity, Kudasan, Gandhinagar", 4.3f, 1650,
                        23.1890, 72.6320,
                        Arrays.asList("Free WiFi", "AC", "Work Desk", "Cafeteria"),
                        Arrays.asList(new RoomType("Standard", 1650, 8), new RoomType("Deluxe", 2350, 4))),

                new Hotel(18, "Serene Garden Hotel", "Gandhinagar", "Sector 8, Gandhinagar", 4.1f, 1300,
                        23.2280, 72.6610,
                        Arrays.asList("Free WiFi", "AC", "Parking", "Room Service"),
                        Arrays.asList(new RoomType("Standard", 1300, 7), new RoomType("Deluxe", 1900, 3))),

                new Hotel(19, "Economy Central Inn", "Gandhinagar", "Sector 28, Gandhinagar", 3.8f, 920,
                        23.2450, 72.6520,
                        Arrays.asList("Free WiFi", "Room Service", "Parking"),
                        Arrays.asList(new RoomType("Standard", 920, 10))),

                new Hotel(20, "Akshardham View Suites", "Gandhinagar", "Sector 20, Near Akshardham, Gandhinagar", 4.7f, 2900,
                        23.2290, 72.6730,
                        Arrays.asList("Free WiFi", "AC", "Monument View", "Restaurant", "Breakfast"),
                        Arrays.asList(new RoomType("Deluxe", 2900, 5), new RoomType("Suite", 3450, 2))),

                new Hotel(21, "GIFT City Grand", "Gandhinagar", "GIFT City Road, Gandhinagar", 4.8f, 3350,
                        23.1610, 72.6840,
                        Arrays.asList("Free WiFi", "AC", "Business Center", "Gym", "Breakfast"),
                        Arrays.asList(new RoomType("Deluxe", 3350, 6), new RoomType("Suite", 3500, 2))),

                // ==================== VADODARA (7 Hotels | ₹900 - ₹3150) ====================
                new Hotel(22, "Budget Nest Lodge", "Vadodara", "Alkapuri, Vadodara", 3.9f, 900,
                        22.3100, 73.1700,
                        Arrays.asList("Free WiFi", "Parking"),
                        Arrays.asList(new RoomType("Standard", 900, 10), new RoomType("Deluxe", 1400, 3))),

                new Hotel(23, "Sayaji Royal Court", "Vadodara", "Near Sayaji Garden, Fatehgunj, Vadodara", 4.6f, 2600,
                        22.3210, 73.1870,
                        Arrays.asList("Free WiFi", "AC", "Garden View", "Pool", "Breakfast"),
                        Arrays.asList(new RoomType("Deluxe", 2600, 5), new RoomType("Suite", 3400, 2))),

                new Hotel(24, "Palace View Residency", "Vadodara", "Near Laxmi Vilas Palace, Vadodara", 4.7f, 3150,
                        22.2930, 73.1920,
                        Arrays.asList("Free WiFi", "AC", "Heritage View", "Restaurant", "Breakfast"),
                        Arrays.asList(new RoomType("Deluxe", 3150, 4), new RoomType("Suite", 3500, 2))),

                new Hotel(25, "Baroda Heritage Inn", "Vadodara", "RC Dutt Road, Alkapuri, Vadodara", 4.3f, 1950,
                        22.3120, 73.1650,
                        Arrays.asList("Free WiFi", "AC", "Parking", "Restaurant"),
                        Arrays.asList(new RoomType("Standard", 1950, 6), new RoomType("Deluxe", 2700, 3))),

                new Hotel(26, "Sardar Estate Stay", "Vadodara", "Waghodia Road, Vadodara", 4.0f, 1350,
                        22.2980, 73.2320,
                        Arrays.asList("Free WiFi", "AC", "Parking"),
                        Arrays.asList(new RoomType("Standard", 1350, 8), new RoomType("Deluxe", 1850, 4))),

                new Hotel(27, "Express Transit Hotel", "Vadodara", "Near Vadodara Junction Station, Vadodara", 3.8f, 980,
                        22.3110, 73.1820,
                        Arrays.asList("Free WiFi", "24/7 Desk", "Room Service"),
                        Arrays.asList(new RoomType("Standard", 980, 12))),

                new Hotel(28, "The Emerald Grand", "Vadodara", "Vasna - Bhayli Road, Vadodara", 4.5f, 2850,
                        22.2880, 73.1410,
                        Arrays.asList("Free WiFi", "AC", "Gym", "Breakfast", "Pool"),
                        Arrays.asList(new RoomType("Deluxe", 2850, 5), new RoomType("Suite", 3450, 2))),

                // ==================== RAJKOT (7 Hotels | ₹900 - ₹3200) ====================
                new Hotel(29, "Heritage Courtyard", "Rajkot", "Race Course Road, Rajkot", 4.3f, 1600,
                        22.3039, 70.8022,
                        Arrays.asList("Free WiFi", "Restaurant", "Parking"),
                        Arrays.asList(new RoomType("Standard", 1600, 7), new RoomType("Deluxe", 2300, 3))),

                new Hotel(30, "Saurashtra Pride Hotel", "Rajkot", "Kalawad Road, Rajkot", 4.5f, 2350,
                        22.2850, 70.7680,
                        Arrays.asList("Free WiFi", "AC", "Gym", "Breakfast", "Parking"),
                        Arrays.asList(new RoomType("Standard", 2350, 5), new RoomType("Deluxe", 2950, 3))),

                new Hotel(31, "Imperial Palace Stay", "Rajkot", "Yagnik Road, Rajkot", 4.7f, 3200,
                        22.2960, 70.7950,
                        Arrays.asList("Free WiFi", "AC", "Pool", "Spa", "Breakfast"),
                        Arrays.asList(new RoomType("Deluxe", 3200, 5), new RoomType("Suite", 3500, 2))),

                new Hotel(32, "Jubilee Park Inn", "Rajkot", "Jawahar Road, Near Jubilee Garden, Rajkot", 4.1f, 1450,
                        22.3060, 70.8080,
                        Arrays.asList("Free WiFi", "AC", "Room Service", "Parking"),
                        Arrays.asList(new RoomType("Standard", 1450, 6), new RoomType("Deluxe", 2100, 3))),

                new Hotel(33, "Kathiyawad Comforts", "Rajkot", "Dhebar Road, Rajkot", 4.2f, 1800,
                        22.2890, 70.8040,
                        Arrays.asList("Free WiFi", "AC", "Traditional Restaurant", "Breakfast"),
                        Arrays.asList(new RoomType("Standard", 1800, 6), new RoomType("Deluxe", 2500, 3))),

                new Hotel(34, "City Express Lodge", "Rajkot", "Near Rajkot Junction, Station Road, Rajkot", 3.7f, 900,
                        22.3120, 70.8010,
                        Arrays.asList("Free WiFi", "24/7 Desk", "Luggage Storage"),
                        Arrays.asList(new RoomType("Standard", 900, 10))),

                new Hotel(35, "The Royal Landmark", "Rajkot", "150 Feet Ring Road, Rajkot", 4.6f, 2950,
                        22.2780, 70.7820,
                        Arrays.asList("Free WiFi", "AC", "Restaurant", "Conference Room", "Breakfast"),
                        Arrays.asList(new RoomType("Deluxe", 2950, 5), new RoomType("Suite", 3480, 2)))
        );
    }
}
