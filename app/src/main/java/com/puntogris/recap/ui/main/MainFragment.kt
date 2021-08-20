package com.puntogris.recap.ui.main

import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import com.puntogris.recap.R
import com.puntogris.recap.databinding.FragmentMainBinding
import com.puntogris.recap.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : BaseFragment<FragmentMainBinding>(R.layout.fragment_main) {

    private val viewModel: MainViewModel by viewModels()

    override fun initializeViews() {



        binding.button.setOnClickListener {
            binding.editTextTextPersonName.setBold()
            binding.editTextTextPersonName.html = "Ysususujdkdk<b>kfkfk</b>"
        }
    }
}