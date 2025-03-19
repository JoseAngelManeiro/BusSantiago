package org.galio.bussantiago.data.local

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import org.galio.bussantiago.core.Either
import org.galio.bussantiago.core.Either.Left
import org.galio.bussantiago.core.Either.Right
import org.galio.bussantiago.core.model.BusStopFavorite
import org.galio.bussantiago.data.exception.DatabaseException

private const val DATABASE_NAME = "stops.db"
private const val DATABASE_VERSION = 2

internal class FavoriteDataSourceImpl(
  context: Context
) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION),
  FavoriteDataSource {

  private val database: SQLiteDatabase by lazy { writableDatabase }

  override fun onCreate(database: SQLiteDatabase) {
    database.execSQL(
      "CREATE TABLE " + Tables.FAVOURITE + " (" +
        BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
        FavouriteColumns.CODE + " TEXT, " +
        FavouriteColumns.NAME + " TEXT);"
    )
  }

  override fun onUpgrade(database: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
    database.execSQL("DROP TABLE IF EXISTS " + Tables.STOP)
    database.execSQL("DROP TABLE IF EXISTS " + Tables.LINE)
    database.execSQL("DROP TABLE IF EXISTS " + Tables.JOURNEY)
    database.execSQL("DROP TABLE IF EXISTS " + Tables.TIME)
  }

  override fun getAll(): Either<Exception, List<BusStopFavorite>> {
    return try {
      val busStopFavorites = mutableListOf<BusStopFavorite>()
      val cursor = database.query(
        Tables.FAVOURITE,
        null,
        null,
        null,
        null,
        null,
        null
      )
      if (cursor != null) {
        while (cursor.moveToNext()) {
          busStopFavorites.add(readBusStopFavorite(cursor))
        }
      }
      Right(busStopFavorites)
    } catch (e: Exception) {
      Left(DatabaseException())
    }
  }

  override fun remove(busStopFavorite: BusStopFavorite): Either<Exception, Unit> {
    return try {
      val whereClause = FavouriteColumns.CODE + "=? AND " + FavouriteColumns.NAME + "=?"
      val whereArgs = arrayOf(busStopFavorite.code, busStopFavorite.name)
      database.delete(Tables.FAVOURITE, whereClause, whereArgs)
      Right(Unit)
    } catch (e: Exception) {
      Left(DatabaseException())
    }
  }

  override fun save(busStopFavorite: BusStopFavorite): Either<Exception, Unit> {
    return try {
      val values = ContentValues().apply {
        put(FavouriteColumns.CODE, busStopFavorite.code)
        put(FavouriteColumns.NAME, busStopFavorite.name)
      }
      database.insert(Tables.FAVOURITE, null, values)
      Right(Unit)
    } catch (e: Exception) {
      Left(DatabaseException())
    }
  }

  private fun readBusStopFavorite(cursor: Cursor) =
    BusStopFavorite(
      code = cursor.getString(cursor.getColumnIndexOrThrow(FavouriteColumns.CODE)),
      name = cursor.getString(cursor.getColumnIndexOrThrow(FavouriteColumns.NAME))
    )
}
