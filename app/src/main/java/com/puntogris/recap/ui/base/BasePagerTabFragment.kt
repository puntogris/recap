package com.puntogris.recap.ui.base

import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import androidx.paging.CombinedLoadStates
import com.puntogris.recap.utils.PagingStateListener
import com.puntogris.recap.utils.scrollToTop

abstract class BasePagerTabFragment<T: ViewDataBinding>(@LayoutRes override val layout: Int):
    BaseBindingFragment<T>(layout), PagerTabConfiguration{

    private lateinit var stateListener: PagingStateListener

    override fun initializeViews() {
        super.initializeViews()
        setupRecyclerView()
        subscribeReSelectedTabListener()
    }

    private fun setupRecyclerView(){
        recyclerView.adapter = adapter

        stateListener = object : PagingStateListener {
            override operator fun invoke(loadState: CombinedLoadStates){
                onAdapterLoadStateChanged(loadState)
            }
        }

        adapter.addLoadStateListener(stateListener)
    }

    private fun subscribeReSelectedTabListener(){
        viewModel.reselectedTabId.observe(viewLifecycleOwner){
            if (it == id) recyclerView.scrollToTop()
        }
    }

    override fun onDestroyView() {
        adapter.removeLoadStateListener(stateListener)
        super.onDestroyView()
    }
}