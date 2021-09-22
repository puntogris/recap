package com.puntogris.recap.ui.recap.create

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.puntogris.recap.data.repo.draft.DraftRepository
import com.puntogris.recap.model.Draft
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CreateRecapViewModel @Inject constructor(
    private val draftRepository: DraftRepository
): ViewModel() {

    private val _draft = MutableLiveData(Draft())
    val draft: LiveData<Draft> = _draft

    fun updateDraftHtml(html: String){
        _draft.value!!.html = html
    }

    fun setDraft(draft: Draft){
        _draft.value = draft
    }
}