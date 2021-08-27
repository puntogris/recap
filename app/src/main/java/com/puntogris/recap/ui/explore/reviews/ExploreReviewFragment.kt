package com.puntogris.recap.ui.explore.reviews

import androidx.fragment.app.viewModels
import com.puntogris.recap.R
import com.puntogris.recap.databinding.FragmentExploreRecapBinding
import com.puntogris.recap.ui.base.BaseFragment
import com.puntogris.recap.ui.explore.ExploreViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExploreReviewFragment : BaseFragment<FragmentExploreRecapBinding>(R.layout.fragment_explore_review) {

    private val viewModel: ExploreViewModel by viewModels()

    override fun initializeViews() {

    }
}