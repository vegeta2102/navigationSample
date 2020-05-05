package jp.hoangvu.navigationexample

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.shopify.livedataktx.PublishLiveDataKtx

class MainViewModel : ViewModel() {
    private val _requestViewTransaction = PublishLiveDataKtx<Unit>()
    val requestViewTransaction: LiveData<Unit>
        get() = _requestViewTransaction

    private val _requestSendMoney = PublishLiveDataKtx<Unit>()
    val requestSendMoney: LiveData<Unit>
        get() = _requestSendMoney

    private val _requestViewBalance = PublishLiveDataKtx<Unit>()
    val requestViewBalance: LiveData<Unit>
        get() = _requestViewBalance

    fun selectSendMoney() {
        _requestSendMoney.value = Unit
    }

    fun selectViewBalance() {
        _requestViewTransaction.value = Unit
    }

    fun selectViewTransaction() {
        _requestViewTransaction.value = Unit
    }
}