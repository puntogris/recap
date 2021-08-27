package com.puntogris.recap.ui.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.puntogris.recap.R
import com.puntogris.recap.databinding.FragmentSearchUserBinding
import com.puntogris.recap.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchUserFragment : BaseFragment<FragmentSearchUserBinding>(R.layout.fragment_search_user) {

    private val viewModel: SearchViewModel by viewModels()

    override fun initializeViews() {

    }
}