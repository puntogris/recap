package com.puntogris.recap.feature_recap.presentation.create_recap

import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.navGraphViewModels
import com.puntogris.recap.R
import com.puntogris.recap.core.presentation.base.BaseBindingFragment
import com.puntogris.recap.core.utils.getIntOrNull
import com.puntogris.recap.core.utils.getString
import com.puntogris.recap.core.utils.registerToolbarBackButton
import com.puntogris.recap.core.utils.showSnackBar
import com.puntogris.recap.databinding.FragmentRecapHeaderBinding
import com.puntogris.recap.feature_recap.presentation.create_recap.RecapHeaderValidator.NotValid
import com.puntogris.recap.feature_recap.presentation.create_recap.RecapHeaderValidator.Valid
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RecapHeaderFragment :
    BaseBindingFragment<FragmentRecapHeaderBinding>(R.layout.fragment_recap_header) {

    private val viewModel: CreateRecapViewModel by navGraphViewModels(R.id.createRecapGraph) { defaultViewModelProviderFactory }
    private val args: RecapHeaderFragmentArgs by navArgs()

    override fun initializeViews() {
        binding.fragment = this

        registerToolbarBackButton(binding.toolbar)

        args.recap?.let {
            viewModel.setRecap(it)
        }
    }

    fun navigateToRecapBody() {
        val title = binding.recapTitle.getString()
        val season = binding.recapSeason.getIntOrNull()
        val episode = binding.recapEpisode.getIntOrNull()

        val validation = RecapHeaderValidator.from(title, season, episode)

        when (validation) {
            is NotValid -> showSnackBar(getString(validation.error))
            is Valid -> lifecycleScope.launch {
                with(viewModel) {
                    updateRecap(title, season!!, episode!!)
                    saveRecapLocally()
                }
                findNavController().navigate(R.id.action_headerRecapFragment_to_recapBodyFragment)
            }
        }
    }

}
