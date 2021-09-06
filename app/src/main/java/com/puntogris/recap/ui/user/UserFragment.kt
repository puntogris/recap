package com.puntogris.recap.ui.user

import androidx.fragment.app.viewModels
import com.puntogris.recap.R
import com.puntogris.recap.databinding.FragmentUserBinding
import com.puntogris.recap.ui.base.BaseFragment
import com.puntogris.recap.utils.registerToolbarBackButton
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserFragment : BaseFragment<FragmentUserBinding>(R.layout.fragment_user) {

    private val viewModel: UserViewModel by viewModels()

    override fun initializeViews() {
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        registerToolbarBackButton(binding.toolbar)

        binding.contentLoadingLayout.hide()
    }
}