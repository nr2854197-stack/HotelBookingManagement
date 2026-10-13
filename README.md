# LocalStay — Hotel Booking Management App (Java)

Android app (Java, Room, OpenStreetMap) for local / small-hotel booking.

## Screens
| Screen | What it does |
|---|---|
| Login | Sign up / log in, stays logged in, Logout from Home |
| Home | Search by city; filters: min rating, max price, room type; sort by rating / price |
| Hotel details | **In-app map** with pin, Directions (opens Maps app), Save to favorites, live room availability, guest reviews + rate your stay |
| Booking | Date pickers, room type, guests, payment method (Cash at hotel / UPI / Card – demo), live total, sold-out protection |
| My Bookings | Upcoming / Completed / Cancelled, tap for full details, cancel (releases the room) |
| **Dashboard** | Your stats: total / upcoming / completed / cancelled bookings, total spent, saved hotels, next trip, recent activity |
| Saved hotels | Your favorites list |
| **Map** | Every hotel as a pin on one map; tap a pin to open the hotel |
| **Owner Dashboard** | Hotels listed, rooms available, total/active bookings, revenue, registered users; add / delete hotels; availability per room type; latest bookings of all guests |

## Owner Dashboard access (demo rule)
Sign up / log in with email **admin@localstay.com** (any password you choose the first time).
An "Owner Dashboard" chip then appears on Home. (A real app must enforce roles on a server.)

## Map notes
- Uses **OpenStreetMap via osmdroid** — no API key, no billing.
- Needs an internet connection to download map tiles (app is otherwise offline-capable).
- Hotels store latitude/longitude; owners enter them when adding a hotel
  (long-press a spot in Google Maps to read its coordinates).

## Open in Android Studio
1. **Extract the zip first** (don't open the .zip itself).
2. File > Open > the extracted `HotelBookingApp` folder (the one containing `build.gradle`).
3. Sync Now (needs internet for first-time library download), then Run on an emulator/device (minSdk 21).
   - If the Gradle wrapper jar is missing, Android Studio regenerates it, or run `gradle wrapper`.
4. Uninstall any older copy of the app first — the database schema changed and is recreated
   (accounts/bookings from the old version are cleared).

## Architecture
```
com.example.hotelbooking
├── *Activity.java            UI (talk only to repositories)
├── repository/               interfaces + Room implementations + RepositoryProvider
├── db/                       Room entities, DAOs, AppDatabase (v2)
├── models/                   Hotel, RoomType, Booking, Review
├── session/SessionManager    login session, admin check
├── util/                     AppExecutors (background threads), MapUtil, StatCards, RoomTypeCodec
├── data/SeedData             6 starter hotels
└── adapters/                 RecyclerView adapters
```
To move to a real backend, write Firebase/Retrofit implementations of the repository
interfaces and swap them in `RepositoryProvider` — no screen changes needed.

## Still to add before a public release
Real payment gateway (Razorpay/UPI SDK), password hashing / Firebase Auth, server-side
roles for owners, photo upload for hotels, push notifications, proper Room migrations.
