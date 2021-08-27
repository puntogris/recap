package com.puntogris.recap.ui.search

import androidx.fragment.app.viewModels
import com.puntogris.recap.R
import com.puntogris.recap.databinding.FragmentSearchRecapBinding
import com.puntogris.recap.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchRecapFragment : BaseFragment<FragmentSearchRecapBinding>(R.layout.fragment_search_recap) {

    private val viewModel: SearchViewModel by viewModels()

    override fun initializeViews() {

    }
}