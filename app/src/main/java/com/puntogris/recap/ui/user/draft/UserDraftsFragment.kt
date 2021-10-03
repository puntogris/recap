package com.puntogris.recap.ui.user.draft

import android.content.Context
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.puntogris.recap.R
import com.puntogris.recap.databinding.FragmentUserDraftsBinding
import com.puntogris.recap.model.Recap
import com.puntogris.recap.ui.base.BasePagerTabFragment
import com.puntogris.recap.ui.main.UiListener
import com.puntogris.recap.ui.user.UserFragment
import com.puntogris.recap.ui.user.UserViewModel
import com.puntogris.recap.utils.SimpleResult
import dagger.hilt.android.AndroidEntryPoint
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
        viewModel.getDraftsLiveData().observe(viewLifecycleOwner) {
            adapter.submitData(lifecycle, it)
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
                else onDeleteDraftAction(recap)
            }
            .create().show()
    }

    private fun onDeleteDraftAction(recap: Recap){
        lifecycleScope.launch {
            when(viewModel.deleteDraft(recap)){
                SimpleResult.Failure -> uiListener.showSnackBar("Error al eliminar borrador")
                SimpleResult.Success -> onDeleteDraftSuccess(recap)
            }
        }
    }

    private fun onDeleteDraftSuccess(recap: Recap){
        uiListener.showSnackBar("Draft eliminado"){
            lifecycleScope.launch {
                viewModel.undoDraftDeletion(recap)
            }
        }
    }

}