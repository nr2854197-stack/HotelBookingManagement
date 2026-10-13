package com.example.hotelbooking.db;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class BookingDao_Impl implements BookingDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<BookingEntity> __insertionAdapterOfBookingEntity;

  private final EntityDeletionOrUpdateAdapter<BookingEntity> __updateAdapterOfBookingEntity;

  public BookingDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfBookingEntity = new EntityInsertionAdapter<BookingEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `bookings` (`id`,`hotelId`,`hotelName`,`roomType`,`checkIn`,`checkOut`,`guests`,`totalPrice`,`status`,`userEmail`,`paymentMethod`,`paymentStatus`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final BookingEntity entity) {
        statement.bindLong(1, entity.id);
        statement.bindLong(2, entity.hotelId);
        if (entity.hotelName == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.hotelName);
        }
        if (entity.roomType == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.roomType);
        }
        if (entity.checkIn == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.checkIn);
        }
        if (entity.checkOut == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.checkOut);
        }
        statement.bindLong(7, entity.guests);
        statement.bindLong(8, entity.totalPrice);
        if (entity.status == null) {
          statement.bindNull(9);
        } else {
          statement.bindString(9, entity.status);
        }
        if (entity.userEmail == null) {
          statement.bindNull(10);
        } else {
          statement.bindString(10, entity.userEmail);
        }
        if (entity.paymentMethod == null) {
          statement.bindNull(11);
        } else {
          statement.bindString(11, entity.paymentMethod);
        }
        if (entity.paymentStatus == null) {
          statement.bindNull(12);
        } else {
          statement.bindString(12, entity.paymentStatus);
        }
      }
    };
    this.__updateAdapterOfBookingEntity = new EntityDeletionOrUpdateAdapter<BookingEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `bookings` SET `id` = ?,`hotelId` = ?,`hotelName` = ?,`roomType` = ?,`checkIn` = ?,`checkOut` = ?,`guests` = ?,`totalPrice` = ?,`status` = ?,`userEmail` = ?,`paymentMethod` = ?,`paymentStatus` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final BookingEntity entity) {
        statement.bindLong(1, entity.id);
        statement.bindLong(2, entity.hotelId);
        if (entity.hotelName == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.hotelName);
        }
        if (entity.roomType == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.roomType);
        }
        if (entity.checkIn == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.checkIn);
        }
        if (entity.checkOut == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.checkOut);
        }
        statement.bindLong(7, entity.guests);
        statement.bindLong(8, entity.totalPrice);
        if (entity.status == null) {
          statement.bindNull(9);
        } else {
          statement.bindString(9, entity.status);
        }
        if (entity.userEmail == null) {
          statement.bindNull(10);
        } else {
          statement.bindString(10, entity.userEmail);
        }
        if (entity.paymentMethod == null) {
          statement.bindNull(11);
        } else {
          statement.bindString(11, entity.paymentMethod);
        }
        if (entity.paymentStatus == null) {
          statement.bindNull(12);
        } else {
          statement.bindString(12, entity.paymentStatus);
        }
        statement.bindLong(13, entity.id);
      }
    };
  }

  @Override
  public long insert(final BookingEntity booking) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      final long _result = __insertionAdapterOfBookingEntity.insertAndReturnId(booking);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(final BookingEntity booking) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfBookingEntity.handle(booking);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public List<BookingEntity> getForUser(final String userEmail) {
    final String _sql = "SELECT * FROM bookings WHERE userEmail = ? ORDER BY id DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (userEmail == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, userEmail);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfHotelId = CursorUtil.getColumnIndexOrThrow(_cursor, "hotelId");
      final int _cursorIndexOfHotelName = CursorUtil.getColumnIndexOrThrow(_cursor, "hotelName");
      final int _cursorIndexOfRoomType = CursorUtil.getColumnIndexOrThrow(_cursor, "roomType");
      final int _cursorIndexOfCheckIn = CursorUtil.getColumnIndexOrThrow(_cursor, "checkIn");
      final int _cursorIndexOfCheckOut = CursorUtil.getColumnIndexOrThrow(_cursor, "checkOut");
      final int _cursorIndexOfGuests = CursorUtil.getColumnIndexOrThrow(_cursor, "guests");
      final int _cursorIndexOfTotalPrice = CursorUtil.getColumnIndexOrThrow(_cursor, "totalPrice");
      final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
      final int _cursorIndexOfUserEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "userEmail");
      final int _cursorIndexOfPaymentMethod = CursorUtil.getColumnIndexOrThrow(_cursor, "paymentMethod");
      final int _cursorIndexOfPaymentStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "paymentStatus");
      final List<BookingEntity> _result = new ArrayList<BookingEntity>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final BookingEntity _item;
        final int _tmpId;
        _tmpId = _cursor.getInt(_cursorIndexOfId);
        final int _tmpHotelId;
        _tmpHotelId = _cursor.getInt(_cursorIndexOfHotelId);
        final String _tmpHotelName;
        if (_cursor.isNull(_cursorIndexOfHotelName)) {
          _tmpHotelName = null;
        } else {
          _tmpHotelName = _cursor.getString(_cursorIndexOfHotelName);
        }
        final String _tmpRoomType;
        if (_cursor.isNull(_cursorIndexOfRoomType)) {
          _tmpRoomType = null;
        } else {
          _tmpRoomType = _cursor.getString(_cursorIndexOfRoomType);
        }
        final String _tmpCheckIn;
        if (_cursor.isNull(_cursorIndexOfCheckIn)) {
          _tmpCheckIn = null;
        } else {
          _tmpCheckIn = _cursor.getString(_cursorIndexOfCheckIn);
        }
        final String _tmpCheckOut;
        if (_cursor.isNull(_cursorIndexOfCheckOut)) {
          _tmpCheckOut = null;
        } else {
          _tmpCheckOut = _cursor.getString(_cursorIndexOfCheckOut);
        }
        final int _tmpGuests;
        _tmpGuests = _cursor.getInt(_cursorIndexOfGuests);
        final int _tmpTotalPrice;
        _tmpTotalPrice = _cursor.getInt(_cursorIndexOfTotalPrice);
        final String _tmpStatus;
        if (_cursor.isNull(_cursorIndexOfStatus)) {
          _tmpStatus = null;
        } else {
          _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
        }
        final String _tmpUserEmail;
        if (_cursor.isNull(_cursorIndexOfUserEmail)) {
          _tmpUserEmail = null;
        } else {
          _tmpUserEmail = _cursor.getString(_cursorIndexOfUserEmail);
        }
        final String _tmpPaymentMethod;
        if (_cursor.isNull(_cursorIndexOfPaymentMethod)) {
          _tmpPaymentMethod = null;
        } else {
          _tmpPaymentMethod = _cursor.getString(_cursorIndexOfPaymentMethod);
        }
        final String _tmpPaymentStatus;
        if (_cursor.isNull(_cursorIndexOfPaymentStatus)) {
          _tmpPaymentStatus = null;
        } else {
          _tmpPaymentStatus = _cursor.getString(_cursorIndexOfPaymentStatus);
        }
        _item = new BookingEntity(_tmpId,_tmpHotelId,_tmpHotelName,_tmpRoomType,_tmpCheckIn,_tmpCheckOut,_tmpGuests,_tmpTotalPrice,_tmpStatus,_tmpUserEmail,_tmpPaymentMethod,_tmpPaymentStatus);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<BookingEntity> getAll() {
    final String _sql = "SELECT * FROM bookings ORDER BY id DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfHotelId = CursorUtil.getColumnIndexOrThrow(_cursor, "hotelId");
      final int _cursorIndexOfHotelName = CursorUtil.getColumnIndexOrThrow(_cursor, "hotelName");
      final int _cursorIndexOfRoomType = CursorUtil.getColumnIndexOrThrow(_cursor, "roomType");
      final int _cursorIndexOfCheckIn = CursorUtil.getColumnIndexOrThrow(_cursor, "checkIn");
      final int _cursorIndexOfCheckOut = CursorUtil.getColumnIndexOrThrow(_cursor, "checkOut");
      final int _cursorIndexOfGuests = CursorUtil.getColumnIndexOrThrow(_cursor, "guests");
      final int _cursorIndexOfTotalPrice = CursorUtil.getColumnIndexOrThrow(_cursor, "totalPrice");
      final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
      final int _cursorIndexOfUserEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "userEmail");
      final int _cursorIndexOfPaymentMethod = CursorUtil.getColumnIndexOrThrow(_cursor, "paymentMethod");
      final int _cursorIndexOfPaymentStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "paymentStatus");
      final List<BookingEntity> _result = new ArrayList<BookingEntity>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final BookingEntity _item;
        final int _tmpId;
        _tmpId = _cursor.getInt(_cursorIndexOfId);
        final int _tmpHotelId;
        _tmpHotelId = _cursor.getInt(_cursorIndexOfHotelId);
        final String _tmpHotelName;
        if (_cursor.isNull(_cursorIndexOfHotelName)) {
          _tmpHotelName = null;
        } else {
          _tmpHotelName = _cursor.getString(_cursorIndexOfHotelName);
        }
        final String _tmpRoomType;
        if (_cursor.isNull(_cursorIndexOfRoomType)) {
          _tmpRoomType = null;
        } else {
          _tmpRoomType = _cursor.getString(_cursorIndexOfRoomType);
        }
        final String _tmpCheckIn;
        if (_cursor.isNull(_cursorIndexOfCheckIn)) {
          _tmpCheckIn = null;
        } else {
          _tmpCheckIn = _cursor.getString(_cursorIndexOfCheckIn);
        }
        final String _tmpCheckOut;
        if (_cursor.isNull(_cursorIndexOfCheckOut)) {
          _tmpCheckOut = null;
        } else {
          _tmpCheckOut = _cursor.getString(_cursorIndexOfCheckOut);
        }
        final int _tmpGuests;
        _tmpGuests = _cursor.getInt(_cursorIndexOfGuests);
        final int _tmpTotalPrice;
        _tmpTotalPrice = _cursor.getInt(_cursorIndexOfTotalPrice);
        final String _tmpStatus;
        if (_cursor.isNull(_cursorIndexOfStatus)) {
          _tmpStatus = null;
        } else {
          _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
        }
        final String _tmpUserEmail;
        if (_cursor.isNull(_cursorIndexOfUserEmail)) {
          _tmpUserEmail = null;
        } else {
          _tmpUserEmail = _cursor.getString(_cursorIndexOfUserEmail);
        }
        final String _tmpPaymentMethod;
        if (_cursor.isNull(_cursorIndexOfPaymentMethod)) {
          _tmpPaymentMethod = null;
        } else {
          _tmpPaymentMethod = _cursor.getString(_cursorIndexOfPaymentMethod);
        }
        final String _tmpPaymentStatus;
        if (_cursor.isNull(_cursorIndexOfPaymentStatus)) {
          _tmpPaymentStatus = null;
        } else {
          _tmpPaymentStatus = _cursor.getString(_cursorIndexOfPaymentStatus);
        }
        _item = new BookingEntity(_tmpId,_tmpHotelId,_tmpHotelName,_tmpRoomType,_tmpCheckIn,_tmpCheckOut,_tmpGuests,_tmpTotalPrice,_tmpStatus,_tmpUserEmail,_tmpPaymentMethod,_tmpPaymentStatus);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public BookingEntity getById(final int id) {
    final String _sql = "SELECT * FROM bookings WHERE id = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfHotelId = CursorUtil.getColumnIndexOrThrow(_cursor, "hotelId");
      final int _cursorIndexOfHotelName = CursorUtil.getColumnIndexOrThrow(_cursor, "hotelName");
      final int _cursorIndexOfRoomType = CursorUtil.getColumnIndexOrThrow(_cursor, "roomType");
      final int _cursorIndexOfCheckIn = CursorUtil.getColumnIndexOrThrow(_cursor, "checkIn");
      final int _cursorIndexOfCheckOut = CursorUtil.getColumnIndexOrThrow(_cursor, "checkOut");
      final int _cursorIndexOfGuests = CursorUtil.getColumnIndexOrThrow(_cursor, "guests");
      final int _cursorIndexOfTotalPrice = CursorUtil.getColumnIndexOrThrow(_cursor, "totalPrice");
      final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
      final int _cursorIndexOfUserEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "userEmail");
      final int _cursorIndexOfPaymentMethod = CursorUtil.getColumnIndexOrThrow(_cursor, "paymentMethod");
      final int _cursorIndexOfPaymentStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "paymentStatus");
      final BookingEntity _result;
      if (_cursor.moveToFirst()) {
        final int _tmpId;
        _tmpId = _cursor.getInt(_cursorIndexOfId);
        final int _tmpHotelId;
        _tmpHotelId = _cursor.getInt(_cursorIndexOfHotelId);
        final String _tmpHotelName;
        if (_cursor.isNull(_cursorIndexOfHotelName)) {
          _tmpHotelName = null;
        } else {
          _tmpHotelName = _cursor.getString(_cursorIndexOfHotelName);
        }
        final String _tmpRoomType;
        if (_cursor.isNull(_cursorIndexOfRoomType)) {
          _tmpRoomType = null;
        } else {
          _tmpRoomType = _cursor.getString(_cursorIndexOfRoomType);
        }
        final String _tmpCheckIn;
        if (_cursor.isNull(_cursorIndexOfCheckIn)) {
          _tmpCheckIn = null;
        } else {
          _tmpCheckIn = _cursor.getString(_cursorIndexOfCheckIn);
        }
        final String _tmpCheckOut;
        if (_cursor.isNull(_cursorIndexOfCheckOut)) {
          _tmpCheckOut = null;
        } else {
          _tmpCheckOut = _cursor.getString(_cursorIndexOfCheckOut);
        }
        final int _tmpGuests;
        _tmpGuests = _cursor.getInt(_cursorIndexOfGuests);
        final int _tmpTotalPrice;
        _tmpTotalPrice = _cursor.getInt(_cursorIndexOfTotalPrice);
        final String _tmpStatus;
        if (_cursor.isNull(_cursorIndexOfStatus)) {
          _tmpStatus = null;
        } else {
          _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
        }
        final String _tmpUserEmail;
        if (_cursor.isNull(_cursorIndexOfUserEmail)) {
          _tmpUserEmail = null;
        } else {
          _tmpUserEmail = _cursor.getString(_cursorIndexOfUserEmail);
        }
        final String _tmpPaymentMethod;
        if (_cursor.isNull(_cursorIndexOfPaymentMethod)) {
          _tmpPaymentMethod = null;
        } else {
          _tmpPaymentMethod = _cursor.getString(_cursorIndexOfPaymentMethod);
        }
        final String _tmpPaymentStatus;
        if (_cursor.isNull(_cursorIndexOfPaymentStatus)) {
          _tmpPaymentStatus = null;
        } else {
          _tmpPaymentStatus = _cursor.getString(_cursorIndexOfPaymentStatus);
        }
        _result = new BookingEntity(_tmpId,_tmpHotelId,_tmpHotelName,_tmpRoomType,_tmpCheckIn,_tmpCheckOut,_tmpGuests,_tmpTotalPrice,_tmpStatus,_tmpUserEmail,_tmpPaymentMethod,_tmpPaymentStatus);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
