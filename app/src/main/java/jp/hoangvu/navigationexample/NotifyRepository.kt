package jp.hoangvu.navigationexample

import androidx.lifecycle.LiveData


class NotifyRepository(private val notifyDao: NotifyDao) {

    suspend fun insert(notifyData: NotifyData) {
        notifyDao.add(notifyData)
    }

    fun getList(): LiveData<List<NotifyData>> {
        return notifyDao.getListNotify()
    }

    suspend fun delete(id: Int) {
        notifyDao.delete(id)
    }
}