package jp.hoangvu.navigationexample

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "notify_table")
data class NotifyData(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = "title")
    val title: String
) : Serializable