package jp.hoangvu.navigationexample

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class NotificationViewModel : ViewModel() {
    private val _notifications = MutableLiveData<List<NotifyData>>()
    val notifications: LiveData<List<NotifyData>>
        get() = _notifications

    private val _requestHide = MutableLiveData<Unit>()
    val requestHide: LiveData<Unit>
        get() = _requestHide

    private val _requestOpen = MutableLiveData<Unit>()
    val requestOpen: LiveData<Unit>
        get() = _requestOpen

    private val _requestAdd = MutableLiveData<Unit>()
    val requestAdd: LiveData<Unit>
        get() = _requestAdd

    fun initialize() {
        _notifications.value = makeDummyData()
    }

    private fun makeDummyData() = mutableListOf<NotifyData>().apply {
        add(NotifyData("Item1"))
        add(NotifyData("Item2"))
    }

    fun hide() {
        _requestHide.value = Unit
    }

    fun open() {
        _requestOpen.value = Unit
    }

    fun add() {
        _requestAdd.value = Unit
    }
}
