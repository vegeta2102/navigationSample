package jp.hoangvu.navigationexample

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.observe
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import jp.hoangvu.navigationexample.databinding.FragmentNotificationDriversBinding


class NotificationFragment : Fragment(R.layout.fragment_notification_drivers) {

    private val viewModel: NotificationViewModel by lazy {
        ViewModelProvider(requireActivity()).get(NotificationViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = DataBindingUtil.bind<FragmentNotificationDriversBinding>(view)
        binding ?: return

        binding.apply {
            viewModel = this@NotificationFragment.viewModel
            lifecycleOwner = viewLifecycleOwner
            listNotify.adapter = NotifyAdapter(viewModel = this@NotificationFragment.viewModel)
            listNotify.addItemDecoration(createDividerItemDecoration(listNotify.context))
        }

        viewModel.initialize()
        observeViewModel()
    }

    private fun observeViewModel() {
        with(viewModel) {
            requestHide.observe(viewLifecycleOwner) {
                Log.d("Notify", "Click")
            }

            requestOpen.observe(viewLifecycleOwner) {
                Log.d("Notify", "Click button")
            }
        }
    }

    private fun createDividerItemDecoration(context: Context): RecyclerView.ItemDecoration {
        return VerticalColorDividerItemDecoration(
            color = ContextCompat.getColor(context, R.color.divider),
            leftPaddingPixels = context.resources.getDimensionPixelSize(R.dimen.dimen_56dp),
            heightPixels = context.resources.getDimensionPixelSize(R.dimen.divider_height),
            isLastDividerVisible = false
        )
    }
}
