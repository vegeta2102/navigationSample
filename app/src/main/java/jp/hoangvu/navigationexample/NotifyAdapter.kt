package jp.hoangvu.navigationexample

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

class NotifyAdapter(
    private val viewModel: NotificationViewModel
) : RecyclerView.Adapter<NotifyAdapter.ViewHolder>(),
    BindableAdapter<List<NotifyData>> {

    private var listNotify: List<NotifyData>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding =
            DataBindingUtil.inflate<ViewDataBinding>(layoutInflater, viewType, parent, false)
        return ViewHolder(binding, viewModel)
    }

    override fun getItemCount(): Int {
        return listNotify?.count() ?: 0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        listNotify?.let {
            holder.bind(it[position])
        }
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.layout_notify_item
    }

    override fun setData(data: List<NotifyData>) {
        this.listNotify = mutableListOf<NotifyData>().apply {
            addAll(data)
        }
        notifyDataSetChanged()
    }

    class ViewHolder(
        private val binding: ViewDataBinding,
        val viewModel: NotificationViewModel
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: NotifyData) {
            binding.setVariable(BR.data, item)
            binding.setVariable(BR.viewModel, viewModel)
            binding.executePendingBindings()
        }
    }
}