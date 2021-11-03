package com.puntogris.recap.ui.recap.create

import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.navGraphViewModels
import com.puntogris.recap.R
import com.puntogris.recap.databinding.FragmentRecapHeaderBinding
import com.puntogris.recap.ui.base.BaseBindingFragment
import com.puntogris.recap.ui.recap.create.RecapHeaderValidator.*
import com.puntogris.recap.utils.getIntOrNull
import com.puntogris.recap.utils.getString
import com.puntogris.recap.utils.registerToolbarBackButton
import com.puntogris.recap.utils.showSnackBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RecapHeaderFragment :BaseBindingFragment<FragmentRecapHeaderBinding>(R.layout.fragment_recap_header) {

    private val viewModel: CreateRecapViewModel by navGraphViewModels(R.id.createRecapGraph){defaultViewModelProviderFactory}
    private val args: RecapHeaderFragmentArgs by navArgs()

    override fun initializeViews() {
        binding.fragment = this

        registerToolbarBackButton(binding.toolbar)

        args.recap?.let {
            viewModel.setRecap(it)
        }
    }

    fun navigateToRecapBody(){
        val title = binding.recapTitle.getString()
        val season = binding.recapSeason.getIntOrNull()
        val episode = binding.recapEpisode.getIntOrNull()

        val validation = RecapHeaderValidator.from(title, season, episode)

        when(validation){
            is NotValid -> showSnackBar(getString(validation.error))
            is Valid -> lifecycleScope.launch {
                with(viewModel){
                    updateRecap(title, season!!, episode!!)
                    saveRecapLocally()
                }
                findNavController().navigate(R.id.action_headerRecapFragment_to_recapBodyFragment)
            }
        }
    }
}
