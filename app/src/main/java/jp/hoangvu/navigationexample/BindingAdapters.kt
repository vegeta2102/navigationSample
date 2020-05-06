package jp.hoangvu.navigationexample

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView

@Suppress("UNCHECKED_CAST")
@BindingAdapter("data")
fun <T> setRecyclerViewProperties(recyclerView: RecyclerView, data: T?) {
    recyclerView.adapter?.let { adapter ->
        if (adapter is BindableAdapter<*> && data != null) {
            (adapter as BindableAdapter<T>).setData(data)
        }
    }
}