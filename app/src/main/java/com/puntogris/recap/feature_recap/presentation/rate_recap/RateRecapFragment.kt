package com.puntogris.recap.feature_recap.presentation.rate_recap

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.puntogris.recap.R
import com.puntogris.recap.databinding.FragmentRateRecapBinding
import com.puntogris.recap.core.presentation.base.BaseBindingFragment
import com.puntogris.recap.core.utils.Result
import com.puntogris.recap.core.utils.launchAndRepeatWithViewLifecycle
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RateRecapFragment : BaseBindingFragment<FragmentRateRecapBinding>(R.layout.fragment_rate_recap) {

    private val viewModel: RateRecapViewModel by viewModels()
    private val args: RateRecapFragmentArgs by navArgs()

    override fun initializeViews() {

        subscribeUserRatingUi()
    }

    private fun subscribeUserRatingUi(){
        launchAndRepeatWithViewLifecycle(Lifecycle.State.CREATED) {
            when(viewModel.checkRatingData(args.recap.id)){
                is Result.Error -> {}
                is Result.Success -> {}
            }
        }
    }

    private fun navigateToRatingDialog(){
        val action = RateRecapFragmentDirections.actionRateRecapFragmentToSelectRatingDialog(args.recap.id)
        findNavController().navigate(action)
    }
}