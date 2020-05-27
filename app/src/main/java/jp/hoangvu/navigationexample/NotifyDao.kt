package jp.hoangvu.navigationexample

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface NotifyDao {

    @Query("SELECT * FROM notify_table ORDER BY id ASC")
    fun getListNotify(): LiveData<List<NotifyData>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun add(notifyData: NotifyData)

    @Query("DELETE FROM notify_table WHERE id = :id")
    suspend fun delete(id: Int)

    @Query("UPDATE notify_table SET is_latest = 0 WHERE is_visible = 1")
    suspend fun refreshLatestNotification()

    @Query("UPDATE notify_table SET is_visible = 1 WHERE is_visible = 0")
    suspend fun updateVisibleNotification()

    @Query("UPDATE notify_table SET is_visible = 0 WHERE is_visible = 1")
    suspend fun updateInvisibleNotification()

    @Delete
    suspend fun deleteEntity(data: NotifyData)
}