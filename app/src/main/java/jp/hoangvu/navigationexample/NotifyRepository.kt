package jp.hoangvu.navigationexample

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations


class NotifyRepository(private val notifyDao: NotifyDao) {

    suspend fun insert(notifyData: NotifyData) {
        notifyDao.add(notifyData)
    }

    suspend fun refresh() {
        notifyDao.refreshLatestNotification()
    }

    fun getList(): LiveData<List<NotifyData>> {
        return notifyDao.getListNotify()
    }

    suspend fun delete(id: Int) {
        notifyDao.delete(id)
    }

    suspend fun update(isVisible: Boolean) {
        if (isVisible) {
            notifyDao.updateInvisibleNotification()
        } else {
            notifyDao.updateVisibleNotification()
        }

    }

    suspend fun deleteEntity(data: NotifyData) {
        notifyDao.deleteEntity(data)
    }

    fun getVisibleList(): LiveData<List<NotifyData>> =
        Transformations.map(getList()) {
            mutableListOf<NotifyData>().apply {
                it.filter { it.isVisible }
                    .forEach {
                        add(it)
                    }
            }
        }
}