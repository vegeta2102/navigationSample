package jp.hoangvu.navigationexample

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import jp.hoangvu.navigationexample.databinding.FragmentMainBindingImpl

class MainFragment : Fragment(R.layout.fragment_main) {

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
    }

    override fun onStart() {
        super.onStart()
        Navigation.findNavController(
            requireActivity(), R.id.nav_host_fragment
        ).addOnDestinationChangedListener(onDestinationChangedListener)
    }

    override fun onStop() {
        super.onStop()
        Navigation.findNavController(
            requireActivity(), R.id.nav_host_fragment
        ).removeOnDestinationChangedListener(onDestinationChangedListener)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initDataBinding(view)
        observeViewModel()
    }

    private fun initDataBinding(view: View) {
        val binding = DataBindingUtil.bind<FragmentMainBindingImpl>(view)
        binding ?: return
        binding.apply {
            viewModel = this@MainFragment.viewModel
            lifecycleOwner = viewLifecycleOwner
        }
    }

    private fun observeViewModel() {
        with(viewModel) {
            requestViewTransaction.observe(viewLifecycleOwner) {
                findNavController().navigate(R.id.action_mainFragment_to_viewTransactionFragment2)
                Log.d("TAG", "action_mainFragment_to_viewTransactionFragment2")
            }

            requestSendMoney.observe(viewLifecycleOwner) {
                findNavController().navigate(R.id.action_mainFragment_to_chooseRecipientFragment)
                Log.d("TAG", "action_chooseRecipientFragment_to_specifyAmountFragment")
            }

            requestViewBalance.observe(viewLifecycleOwner) {
                findNavController().navigate(R.id.action_mainFragment_to_viewBalanceFragment2)
                Log.d("TAG", "action_mainFragment_to_viewBalanceFragment2")
            }
        }
    }

    private val onDestinationChangedListener =
        NavController.OnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {

            }
        }
}
