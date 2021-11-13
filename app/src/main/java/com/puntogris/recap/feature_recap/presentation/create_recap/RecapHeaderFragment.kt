package com.puntogris.recap.feature_recap.presentation.create_recap

import androidx.lifecycle.lifecycleScope
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
        val season = binding.recapSeason.getIntOrNull() ?: 0
        val episode = binding.recapEpisode.getIntOrNull() ?: 0

        RecapHeaderValidator.from(title, season, episode).let {
            when (it) {
                is NotValid -> showSnackBar(getString(it.error))
                is Valid -> lifecycleScope.launch {
                    viewModel.updateRecap(title, season, episode)
                    viewModel.saveRecapLocally()
                }
            }
        }
    }
}
