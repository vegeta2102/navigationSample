package jp.hoangvu.navigationexample

import android.app.Application
import android.util.Log
import android.view.View
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NotificationViewModel(application: Application) : AndroidViewModel(application) {

    enum class State {
        FETCH,
        IGNORE
    }

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

    private val _animateBackground = MutableLiveData<Int>()
    val animateBackground: LiveData<Int>
        get() = _animateBackground

    private val _stateList = MutableLiveData<State>()

    init {
        _stateList.value = State.FETCH
        _requestHide.value = View.GONE
        observeList()
    }

    private fun observeList() {
        MediatorLiveData<Int>().apply {
            val observer = Observer<Any?> {
                val list = _notifications.value ?: emptyList()
                this.value = when {
                    _stateList.value == State.IGNORE -> View.GONE
                    _stateList.value == State.FETCH && list.isNotEmpty() -> View.VISIBLE
                    else -> View.GONE
                }
            }
            addSource(_stateList.distinctUntilChanged(), observer)
            addSource(_notifications, observer)
        }
            .distinctUntilChanged()
            .observeForever {
                _requestHide.value = it
            }
    }

    fun initialize() {
        // _notifications.value = makeDummyData()
        notifyRepository.getVisibleList().observeForever {
            Log.d("List", it.toString())
            _notifications.value = it
        }
        _notifications.observeForever {
            Log.d("_notifications", "Change")
        }
    }

    private fun makeDummyData() = mutableListOf<NotifyData>().apply {
        //add(NotifyData(title = "Item1", isVisible = true))
        //add(NotifyData(title = "Item2", isVisible = true))
    }

    fun hide() {
        _stateList.value = State.IGNORE
        viewModelScope.launch {
            delay(500)
            notifyRepository.update(isVisible = true)
        }
    }

    fun open() {
        _stateList.value = State.FETCH
        viewModelScope.launch {
            notifyRepository.update(isVisible = false)
        }
    }

    fun clickHere(data: NotifyData) {
        _stateList.value = State.FETCH
        viewModelScope.launch {
            notifyRepository.deleteEntity(data)
        }
    }

    fun add() {
        _stateList.value = State.FETCH
        _notifications.observeOnce {
            Log.d("One", "Really")
            _animateBackground.value = it.firstOrNull()?.id
        }
        viewModelScope.launch {
            notifyRepository.insert(
                NotifyData(
                    title = "Please take a look",
                    isVisible = true,
                    isLatest = true
                )
            )
            delay(300)
            notifyRepository.refresh()
        }

    }

    fun delete() {
        _stateList.value = State.FETCH
        viewModelScope.launch {
            notifications.value?.firstOrNull()?.id?.let {
                Log.d("Thread 2", Thread.currentThread().toString())
                notifyRepository.delete(it)
            }
        }
    }
}
