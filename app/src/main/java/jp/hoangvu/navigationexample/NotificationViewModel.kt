package jp.hoangvu.navigationexample

import android.app.Application
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NotificationViewModel(application: Application) : AndroidViewModel(application) {
    private val notifyRepository: NotifyRepository

    init {
        val notifyDao = NotifyRoomDatabase.create(application).notifyDao()
        notifyRepository = NotifyRepository(notifyDao)
    }

    private val _notifications = MutableLiveData<List<NotifyData>>()
    val notifications: LiveData<List<NotifyData>>
        get() = _notifications

    private val _requestHide = MutableLiveData<Int>()
    val requestHide: LiveData<Int>
        get() = _requestHide

    private val _requestOpen = MutableLiveData<Int>()
    val requestOpen: LiveData<Int>
        get() = _requestOpen

    private val _requestAdd = MutableLiveData<Unit>()
    val requestAdd: LiveData<Unit>
        get() = _requestAdd

    fun initialize() {
        _notifications.value = makeDummyData()
        //_notifications.value = notifyRepository.notifyList
    }

    private fun makeDummyData() = mutableListOf<NotifyData>().apply {
        add(NotifyData(title = "Item1"))
        add(NotifyData(title = "Item2"))
    }

    fun hide() {
        _requestHide.value = View.GONE
    }

    fun open() {
        _requestHide.value = View.VISIBLE
    }

    fun add() {
        viewModelScope.launch(Dispatchers.IO) {
            notifyRepository.insert(NotifyData(title = "Please take a look"))
        }
    }
}
