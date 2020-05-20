package jp.hoangvu.navigationexample


class NotifyRepository(private val notifyDao: NotifyDao) {

    suspend fun insert(notifyData: NotifyData) {
        notifyDao.add(notifyData)
    }
}