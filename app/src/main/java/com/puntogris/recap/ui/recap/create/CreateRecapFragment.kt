package com.puntogris.recap.ui.recap.create

import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.puntogris.recap.R
import com.puntogris.recap.databinding.FragmentCreateRecapBinding
import com.puntogris.recap.ui.base.BaseFragment
import com.puntogris.recap.utils.getIntOrNull
import com.puntogris.recap.utils.getString
import com.puntogris.recap.utils.registerToolbarBackButton
import com.puntogris.recap.utils.showSnackBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateRecapFragment : BaseFragment<FragmentCreateRecapBinding>(R.layout.fragment_create_recap) {

    private val viewModel: CreateRecapViewModel by navGraphViewModels(R.id.createRecapGraph){defaultViewModelProviderFactory}

    override fun initializeViews() {
        binding.fragment = this
        registerToolbarBackButton(binding.toolbar)
    }

    fun navigateToRecapBody(){
        val title = binding.recapTitle.getString()
        val season = binding.recapSeason.getIntOrNull()
        val episode = binding.recapEpisode.getIntOrNull()

        var errorResId = 0

        if (title.isEmpty()) errorResId = R.string.snack_recap_title_required
        if (season == null)  errorResId = R.string.snack_recap_season_required
        if (episode == null) errorResId = R.string.snack_recap_episode_required

        if (errorResId != 0){
            showSnackBar(getString(errorResId))
        }else{
            viewModel.updateRecap(title, season!!, episode!!)
            findNavController().navigate(R.id.action_createRecapFragment_to_recapBodyFragment)
        }
    }
}
