package com.puntogris.recap.presentation.user.draft

import android.content.Context
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.puntogris.recap.R
import com.puntogris.recap.databinding.FragmentUserDraftsBinding
import com.puntogris.recap.feature_recap.domain.model.Recap
import com.puntogris.recap.core.presentation.base.BasePagerTabFragment
import com.puntogris.recap.core.utils.UiListener
import com.puntogris.recap.feature_profile.presentation.profile.UserFragment
import com.puntogris.recap.feature_profile.presentation.UserViewModel
import com.puntogris.recap.core.utils.SimpleResult
import com.puntogris.recap.core.utils.launchAndRepeatWithViewLifecycle
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UserDraftsFragment : BasePagerTabFragment<FragmentUserDraftsBinding>(R.layout.fragment_user_drafts) {

    override val viewModel: UserViewModel by viewModels(ownerProducer = {requireParentFragment()})

    override val adapter = UserDraftsAdapter(::onDraftClickListener)

    override val recyclerView: RecyclerView
        get() = binding.recyclerView

    private lateinit var uiListener: UiListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        uiListener = (context as UiListener)
    }

    override fun initializeViews() {
        super.initializeViews()
        collectUiState()
    }

    private fun collectUiState(){
        launchAndRepeatWithViewLifecycle {
            viewModel.getDrafts().collect {
                adapter.submitData(it)
            }
        }
    }

    override fun onAdapterLoadStateChanged(state: CombinedLoadStates) {
        binding.contentLoadingLayout.registerState(state.refresh is LoadState.Loading)
    }

    private fun onDraftClickListener(recap: Recap){
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.action)
            .setNegativeButton(android.R.string.cancel, null)
            .setItems(R.array.draft_actions){ _, i ->
                if (i == 0) (requireParentFragment() as UserFragment).navigateToCreateRecap(recap)
                //else onDeleteDraftAction(recap)
            }
            .create().show()
    }

}