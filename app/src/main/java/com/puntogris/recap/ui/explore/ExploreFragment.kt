package com.puntogris.recap.ui.explore

import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.puntogris.recap.R
import com.puntogris.recap.databinding.FragmentExploreBinding
import com.puntogris.recap.models.Recap
import com.puntogris.recap.ui.base.BaseFragment
import com.rubensousa.decorator.ColumnProvider
import com.rubensousa.decorator.GridBoundsMarginDecoration
import com.rubensousa.decorator.GridMarginDecoration
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExploreFragment : BaseFragment<FragmentExploreBinding>(R.layout.fragment_explore) {

    private val viewModel: ExploreViewModel by viewModels()

    override fun initializeViews() {

        setupRecyclerViewAdapter()
    }

    private fun setupRecyclerViewAdapter(){
        val adapter = ExploreAdapter({ onRecapShortClick(it) },{ onRecapLongClick(it) })
        binding.recyclerView.adapter = adapter

        val list = mutableListOf<Recap>()
        for (i in 1..10){
            list.add(Recap(title = i.toString(),
                image = "https://es.web.img2.acsta.net/pictures/20/08/16/19/46/2263125.jpg"))
        }

        adapter.submitList(list)
    }

    private fun onRecapShortClick(recap: Recap){

    }

    private fun onRecapLongClick(recap: Recap){

    }

}