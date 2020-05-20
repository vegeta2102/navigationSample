package jp.hoangvu.navigationexample

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [NotifyData::class],
    version = 1,
    exportSchema = false
)
abstract class NotifyRoomDatabase : RoomDatabase() {
    abstract fun notifyDao(): NotifyDao

    companion object {
        @Volatile
        private var INSTANCE: NotifyRoomDatabase? = null

        fun create(context: Context): NotifyRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NotifyRoomDatabase::class.java,
                    "notify_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }

        }
    }
}