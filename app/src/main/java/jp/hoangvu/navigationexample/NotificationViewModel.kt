package jp.hoangvu.navigationexample

import android.app.Application
import android.util.Log
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

    init {
        notifyRepository.getVisibleList().observeForever {
            _requestHide.value = if (it.isEmpty().not()) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }
    }

    fun initialize() {
        _notifications.value = makeDummyData()
        notifyRepository.getList().observeForever {
            _notifications.value = it
        }
    }

    private fun makeDummyData() = mutableListOf<NotifyData>().apply {
        add(NotifyData(title = "Item1", isVisible = true))
        add(NotifyData(title = "Item2", isVisible = true))
    }

    fun hide() {
        viewModelScope.launch(Dispatchers.IO) {
            notifyRepository.update(isVisible = true)
        }
    }

    fun open() {
        viewModelScope.launch(Dispatchers.IO) {
            notifyRepository.update(isVisible = false)
        }
    }

    fun add() {
        Log.d("ViewModel", "Hey hey")
        viewModelScope.launch(Dispatchers.IO) {
            notifyRepository.insert(NotifyData(title = "Please take a look", isVisible = true))
        }
    }

    fun delete() {
        viewModelScope.launch(Dispatchers.IO) {
            notifications.value?.firstOrNull()?.id?.let {
                notifyRepository.delete(it)
            }
        }
    }
}
