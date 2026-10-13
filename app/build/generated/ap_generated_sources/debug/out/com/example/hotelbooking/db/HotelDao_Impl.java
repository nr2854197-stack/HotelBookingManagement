package com.example.hotelbooking.db;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.room.util.StringUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Integer;
import java.lang.Override;
import java.lang.String;
import java.lang.StringBuilder;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class HotelDao_Impl implements HotelDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<HotelEntity> __insertionAdapterOfHotelEntity;

  private final EntityDeletionOrUpdateAdapter<HotelEntity> __updateAdapterOfHotelEntity;

  private final SharedSQLiteStatement __preparedStmtOfDeleteById;

  public HotelDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfHotelEntity = new EntityInsertionAdapter<HotelEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `hotels` (`id`,`name`,`city`,`address`,`rating`,`pricePerNight`,`latitude`,`longitude`,`facilitiesCsv`,`roomTypesRaw`) VALUES (?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final HotelEntity entity) {
        statement.bindLong(1, entity.id);
        if (entity.name == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.name);
        }
        if (entity.city == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.city);
        }
        if (entity.address == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.address);
        }
        statement.bindDouble(5, entity.rating);
        statement.bindLong(6, entity.pricePerNight);
        statement.bindDouble(7, entity.latitude);
        statement.bindDouble(8, entity.longitude);
        if (entity.facilitiesCsv == null) {
          statement.bindNull(9);
        } else {
          statement.bindString(9, entity.facilitiesCsv);
        }
        if (entity.roomTypesRaw == null) {
          statement.bindNull(10);
        } else {
          statement.bindString(10, entity.roomTypesRaw);
        }
      }
    };
    this.__updateAdapterOfHotelEntity = new EntityDeletionOrUpdateAdapter<HotelEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `hotels` SET `id` = ?,`name` = ?,`city` = ?,`address` = ?,`rating` = ?,`pricePerNight` = ?,`latitude` = ?,`longitude` = ?,`facilitiesCsv` = ?,`roomTypesRaw` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final HotelEntity entity) {
        statement.bindLong(1, entity.id);
        if (entity.name == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.name);
        }
        if (entity.city == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.city);
        }
        if (entity.address == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.address);
        }
        statement.bindDouble(5, entity.rating);
        statement.bindLong(6, entity.pricePerNight);
        statement.bindDouble(7, entity.latitude);
        statement.bindDouble(8, entity.longitude);
        if (entity.facilitiesCsv == null) {
          statement.bindNull(9);
        } else {
          statement.bindString(9, entity.facilitiesCsv);
        }
        if (entity.roomTypesRaw == null) {
          statement.bindNull(10);
        } else {
          statement.bindString(10, entity.roomTypesRaw);
        }
        statement.bindLong(11, entity.id);
      }
    };
    this.__preparedStmtOfDeleteById = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM hotels WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public void insertAll(final List<HotelEntity> hotels) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfHotelEntity.insert(hotels);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void insert(final HotelEntity hotel) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfHotelEntity.insert(hotel);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(final HotelEntity hotel) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfHotelEntity.handle(hotel);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteById(final int id) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteById.acquire();
    int _argIndex = 1;
    _stmt.bindLong(_argIndex, id);
    try {
      __db.beginTransaction();
      try {
        _stmt.executeUpdateDelete();
        __db.setTransactionSuccessful();
      } finally {
        __db.endTransaction();
      }
    } finally {
      __preparedStmtOfDeleteById.release(_stmt);
    }
  }

  @Override
  public int maxId() {
    final String _sql = "SELECT IFNULL(MAX(id), 0) FROM hotels";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _result;
      if (_cursor.moveToFirst()) {
        _result = _cursor.getInt(0);
      } else {
        _result = 0;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public int count() {
    final String _sql = "SELECT COUNT(*) FROM hotels";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _result;
      if (_cursor.moveToFirst()) {
        _result = _cursor.getInt(0);
      } else {
        _result = 0;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<HotelEntity> getAll() {
    final String _sql = "SELECT * FROM hotels ORDER BY rating DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
      final int _cursorIndexOfCity = CursorUtil.getColumnIndexOrThrow(_cursor, "city");
      final int _cursorIndexOfAddress = CursorUtil.getColumnIndexOrThrow(_cursor, "address");
      final int _cursorIndexOfRating = CursorUtil.getColumnIndexOrThrow(_cursor, "rating");
      final int _cursorIndexOfPricePerNight = CursorUtil.getColumnIndexOrThrow(_cursor, "pricePerNight");
      final int _cursorIndexOfLatitude = CursorUtil.getColumnIndexOrThrow(_cursor, "latitude");
      final int _cursorIndexOfLongitude = CursorUtil.getColumnIndexOrThrow(_cursor, "longitude");
      final int _cursorIndexOfFacilitiesCsv = CursorUtil.getColumnIndexOrThrow(_cursor, "facilitiesCsv");
      final int _cursorIndexOfRoomTypesRaw = CursorUtil.getColumnIndexOrThrow(_cursor, "roomTypesRaw");
      final List<HotelEntity> _result = new ArrayList<HotelEntity>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final HotelEntity _item;
        final int _tmpId;
        _tmpId = _cursor.getInt(_cursorIndexOfId);
        final String _tmpName;
        if (_cursor.isNull(_cursorIndexOfName)) {
          _tmpName = null;
        } else {
          _tmpName = _cursor.getString(_cursorIndexOfName);
        }
        final String _tmpCity;
        if (_cursor.isNull(_cursorIndexOfCity)) {
          _tmpCity = null;
        } else {
          _tmpCity = _cursor.getString(_cursorIndexOfCity);
        }
        final String _tmpAddress;
        if (_cursor.isNull(_cursorIndexOfAddress)) {
          _tmpAddress = null;
        } else {
          _tmpAddress = _cursor.getString(_cursorIndexOfAddress);
        }
        final float _tmpRating;
        _tmpRating = _cursor.getFloat(_cursorIndexOfRating);
        final int _tmpPricePerNight;
        _tmpPricePerNight = _cursor.getInt(_cursorIndexOfPricePerNight);
        final double _tmpLatitude;
        _tmpLatitude = _cursor.getDouble(_cursorIndexOfLatitude);
        final double _tmpLongitude;
        _tmpLongitude = _cursor.getDouble(_cursorIndexOfLongitude);
        final String _tmpFacilitiesCsv;
        if (_cursor.isNull(_cursorIndexOfFacilitiesCsv)) {
          _tmpFacilitiesCsv = null;
        } else {
          _tmpFacilitiesCsv = _cursor.getString(_cursorIndexOfFacilitiesCsv);
        }
        final String _tmpRoomTypesRaw;
        if (_cursor.isNull(_cursorIndexOfRoomTypesRaw)) {
          _tmpRoomTypesRaw = null;
        } else {
          _tmpRoomTypesRaw = _cursor.getString(_cursorIndexOfRoomTypesRaw);
        }
        _item = new HotelEntity(_tmpId,_tmpName,_tmpCity,_tmpAddress,_tmpRating,_tmpPricePerNight,_tmpLatitude,_tmpLongitude,_tmpFacilitiesCsv,_tmpRoomTypesRaw);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public HotelEntity getById(final int id) {
    final String _sql = "SELECT * FROM hotels WHERE id = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
      final int _cursorIndexOfCity = CursorUtil.getColumnIndexOrThrow(_cursor, "city");
      final int _cursorIndexOfAddress = CursorUtil.getColumnIndexOrThrow(_cursor, "address");
      final int _cursorIndexOfRating = CursorUtil.getColumnIndexOrThrow(_cursor, "rating");
      final int _cursorIndexOfPricePerNight = CursorUtil.getColumnIndexOrThrow(_cursor, "pricePerNight");
      final int _cursorIndexOfLatitude = CursorUtil.getColumnIndexOrThrow(_cursor, "latitude");
      final int _cursorIndexOfLongitude = CursorUtil.getColumnIndexOrThrow(_cursor, "longitude");
      final int _cursorIndexOfFacilitiesCsv = CursorUtil.getColumnIndexOrThrow(_cursor, "facilitiesCsv");
      final int _cursorIndexOfRoomTypesRaw = CursorUtil.getColumnIndexOrThrow(_cursor, "roomTypesRaw");
      final HotelEntity _result;
      if (_cursor.moveToFirst()) {
        final int _tmpId;
        _tmpId = _cursor.getInt(_cursorIndexOfId);
        final String _tmpName;
        if (_cursor.isNull(_cursorIndexOfName)) {
          _tmpName = null;
        } else {
          _tmpName = _cursor.getString(_cursorIndexOfName);
        }
        final String _tmpCity;
        if (_cursor.isNull(_cursorIndexOfCity)) {
          _tmpCity = null;
        } else {
          _tmpCity = _cursor.getString(_cursorIndexOfCity);
        }
        final String _tmpAddress;
        if (_cursor.isNull(_cursorIndexOfAddress)) {
          _tmpAddress = null;
        } else {
          _tmpAddress = _cursor.getString(_cursorIndexOfAddress);
        }
        final float _tmpRating;
        _tmpRating = _cursor.getFloat(_cursorIndexOfRating);
        final int _tmpPricePerNight;
        _tmpPricePerNight = _cursor.getInt(_cursorIndexOfPricePerNight);
        final double _tmpLatitude;
        _tmpLatitude = _cursor.getDouble(_cursorIndexOfLatitude);
        final double _tmpLongitude;
        _tmpLongitude = _cursor.getDouble(_cursorIndexOfLongitude);
        final String _tmpFacilitiesCsv;
        if (_cursor.isNull(_cursorIndexOfFacilitiesCsv)) {
          _tmpFacilitiesCsv = null;
        } else {
          _tmpFacilitiesCsv = _cursor.getString(_cursorIndexOfFacilitiesCsv);
        }
        final String _tmpRoomTypesRaw;
        if (_cursor.isNull(_cursorIndexOfRoomTypesRaw)) {
          _tmpRoomTypesRaw = null;
        } else {
          _tmpRoomTypesRaw = _cursor.getString(_cursorIndexOfRoomTypesRaw);
        }
        _result = new HotelEntity(_tmpId,_tmpName,_tmpCity,_tmpAddress,_tmpRating,_tmpPricePerNight,_tmpLatitude,_tmpLongitude,_tmpFacilitiesCsv,_tmpRoomTypesRaw);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<HotelEntity> getByIds(final List<Integer> ids) {
    final StringBuilder _stringBuilder = StringUtil.newStringBuilder();
    _stringBuilder.append("SELECT * FROM hotels WHERE id IN (");
    final int _inputSize = ids == null ? 1 : ids.size();
    StringUtil.appendPlaceholders(_stringBuilder, _inputSize);
    _stringBuilder.append(") ORDER BY rating DESC");
    final String _sql = _stringBuilder.toString();
    final int _argCount = 0 + _inputSize;
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, _argCount);
    int _argIndex = 1;
    if (ids == null) {
      _statement.bindNull(_argIndex);
    } else {
      for (Integer _item : ids) {
        if (_item == null) {
          _statement.bindNull(_argIndex);
        } else {
          _statement.bindLong(_argIndex, _item);
        }
        _argIndex++;
      }
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
      final int _cursorIndexOfCity = CursorUtil.getColumnIndexOrThrow(_cursor, "city");
      final int _cursorIndexOfAddress = CursorUtil.getColumnIndexOrThrow(_cursor, "address");
      final int _cursorIndexOfRating = CursorUtil.getColumnIndexOrThrow(_cursor, "rating");
      final int _cursorIndexOfPricePerNight = CursorUtil.getColumnIndexOrThrow(_cursor, "pricePerNight");
      final int _cursorIndexOfLatitude = CursorUtil.getColumnIndexOrThrow(_cursor, "latitude");
      final int _cursorIndexOfLongitude = CursorUtil.getColumnIndexOrThrow(_cursor, "longitude");
      final int _cursorIndexOfFacilitiesCsv = CursorUtil.getColumnIndexOrThrow(_cursor, "facilitiesCsv");
      final int _cursorIndexOfRoomTypesRaw = CursorUtil.getColumnIndexOrThrow(_cursor, "roomTypesRaw");
      final List<HotelEntity> _result = new ArrayList<HotelEntity>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final HotelEntity _item_1;
        final int _tmpId;
        _tmpId = _cursor.getInt(_cursorIndexOfId);
        final String _tmpName;
        if (_cursor.isNull(_cursorIndexOfName)) {
          _tmpName = null;
        } else {
          _tmpName = _cursor.getString(_cursorIndexOfName);
        }
        final String _tmpCity;
        if (_cursor.isNull(_cursorIndexOfCity)) {
          _tmpCity = null;
        } else {
          _tmpCity = _cursor.getString(_cursorIndexOfCity);
        }
        final String _tmpAddress;
        if (_cursor.isNull(_cursorIndexOfAddress)) {
          _tmpAddress = null;
        } else {
          _tmpAddress = _cursor.getString(_cursorIndexOfAddress);
        }
        final float _tmpRating;
        _tmpRating = _cursor.getFloat(_cursorIndexOfRating);
        final int _tmpPricePerNight;
        _tmpPricePerNight = _cursor.getInt(_cursorIndexOfPricePerNight);
        final double _tmpLatitude;
        _tmpLatitude = _cursor.getDouble(_cursorIndexOfLatitude);
        final double _tmpLongitude;
        _tmpLongitude = _cursor.getDouble(_cursorIndexOfLongitude);
        final String _tmpFacilitiesCsv;
        if (_cursor.isNull(_cursorIndexOfFacilitiesCsv)) {
          _tmpFacilitiesCsv = null;
        } else {
          _tmpFacilitiesCsv = _cursor.getString(_cursorIndexOfFacilitiesCsv);
        }
        final String _tmpRoomTypesRaw;
        if (_cursor.isNull(_cursorIndexOfRoomTypesRaw)) {
          _tmpRoomTypesRaw = null;
        } else {
          _tmpRoomTypesRaw = _cursor.getString(_cursorIndexOfRoomTypesRaw);
        }
        _item_1 = new HotelEntity(_tmpId,_tmpName,_tmpCity,_tmpAddress,_tmpRating,_tmpPricePerNight,_tmpLatitude,_tmpLongitude,_tmpFacilitiesCsv,_tmpRoomTypesRaw);
        _result.add(_item_1);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<HotelEntity> search(final String city, final float minRating, final int maxPrice,
      final String roomType) {
    final String _sql = "SELECT * FROM hotels WHERE (? = '' OR LOWER(city) LIKE '%' || LOWER(?) || '%' OR LOWER(address) LIKE '%' || LOWER(?) || '%') AND rating >= ? AND pricePerNight <= ? AND (? = '' OR roomTypesRaw LIKE '%' || ? || ':%') ORDER BY rating DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 7);
    int _argIndex = 1;
    if (city == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, city);
    }
    _argIndex = 2;
    if (city == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, city);
    }
    _argIndex = 3;
    if (city == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, city);
    }
    _argIndex = 4;
    _statement.bindDouble(_argIndex, minRating);
    _argIndex = 5;
    _statement.bindLong(_argIndex, maxPrice);
    _argIndex = 6;
    if (roomType == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, roomType);
    }
    _argIndex = 7;
    if (roomType == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, roomType);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
      final int _cursorIndexOfCity = CursorUtil.getColumnIndexOrThrow(_cursor, "city");
      final int _cursorIndexOfAddress = CursorUtil.getColumnIndexOrThrow(_cursor, "address");
      final int _cursorIndexOfRating = CursorUtil.getColumnIndexOrThrow(_cursor, "rating");
      final int _cursorIndexOfPricePerNight = CursorUtil.getColumnIndexOrThrow(_cursor, "pricePerNight");
      final int _cursorIndexOfLatitude = CursorUtil.getColumnIndexOrThrow(_cursor, "latitude");
      final int _cursorIndexOfLongitude = CursorUtil.getColumnIndexOrThrow(_cursor, "longitude");
      final int _cursorIndexOfFacilitiesCsv = CursorUtil.getColumnIndexOrThrow(_cursor, "facilitiesCsv");
      final int _cursorIndexOfRoomTypesRaw = CursorUtil.getColumnIndexOrThrow(_cursor, "roomTypesRaw");
      final List<HotelEntity> _result = new ArrayList<HotelEntity>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final HotelEntity _item;
        final int _tmpId;
        _tmpId = _cursor.getInt(_cursorIndexOfId);
        final String _tmpName;
        if (_cursor.isNull(_cursorIndexOfName)) {
          _tmpName = null;
        } else {
          _tmpName = _cursor.getString(_cursorIndexOfName);
        }
        final String _tmpCity;
        if (_cursor.isNull(_cursorIndexOfCity)) {
          _tmpCity = null;
        } else {
          _tmpCity = _cursor.getString(_cursorIndexOfCity);
        }
        final String _tmpAddress;
        if (_cursor.isNull(_cursorIndexOfAddress)) {
          _tmpAddress = null;
        } else {
          _tmpAddress = _cursor.getString(_cursorIndexOfAddress);
        }
        final float _tmpRating;
        _tmpRating = _cursor.getFloat(_cursorIndexOfRating);
        final int _tmpPricePerNight;
        _tmpPricePerNight = _cursor.getInt(_cursorIndexOfPricePerNight);
        final double _tmpLatitude;
        _tmpLatitude = _cursor.getDouble(_cursorIndexOfLatitude);
        final double _tmpLongitude;
        _tmpLongitude = _cursor.getDouble(_cursorIndexOfLongitude);
        final String _tmpFacilitiesCsv;
        if (_cursor.isNull(_cursorIndexOfFacilitiesCsv)) {
          _tmpFacilitiesCsv = null;
        } else {
          _tmpFacilitiesCsv = _cursor.getString(_cursorIndexOfFacilitiesCsv);
        }
        final String _tmpRoomTypesRaw;
        if (_cursor.isNull(_cursorIndexOfRoomTypesRaw)) {
          _tmpRoomTypesRaw = null;
        } else {
          _tmpRoomTypesRaw = _cursor.getString(_cursorIndexOfRoomTypesRaw);
        }
        _item = new HotelEntity(_tmpId,_tmpName,_tmpCity,_tmpAddress,_tmpRating,_tmpPricePerNight,_tmpLatitude,_tmpLongitude,_tmpFacilitiesCsv,_tmpRoomTypesRaw);
        _result.add(_item);
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
