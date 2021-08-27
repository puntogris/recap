package com.puntogris.recap.ui.explore.recaps

import androidx.fragment.app.viewModels
import com.puntogris.recap.R
import com.puntogris.recap.databinding.FragmentExploreRecapBinding
import com.puntogris.recap.models.Recap
import com.puntogris.recap.ui.base.BaseFragment
import com.puntogris.recap.ui.explore.ExploreAdapter
import com.puntogris.recap.ui.explore.ExploreViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExploreRecapFragment : BaseFragment<FragmentExploreRecapBinding>(R.layout.fragment_explore_recap) {

    private val viewModel: ExploreViewModel by viewModels()

    override fun initializeViews() {

        setupRecyclerViewAdapter()
    }

    private fun setupRecyclerViewAdapter(){
        val adapter = ExploreAdapter({ onRecapShortClick(it) },{ onRecapLongClick(it) })
        binding.recyclerView.adapter = adapter

        val list = mutableListOf<Recap>()
        for (i in 1..10){
            list.add(
                Recap(title = i.toString(),
                image = "https://es.web.img2.acsta.net/pictures/20/08/16/19/46/2263125.jpg")
            )
        }

        adapter.submitList(list)
    }

    private fun onRecapShortClick(recap: Recap){

    }

    private fun onRecapLongClick(recap: Recap){

    }

}