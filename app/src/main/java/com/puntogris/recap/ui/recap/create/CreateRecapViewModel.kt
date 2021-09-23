package com.puntogris.recap.ui.recap.create

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.puntogris.recap.data.repo.draft.DraftRepository
import com.puntogris.recap.model.Recap
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CreateRecapViewModel @Inject constructor(
    private val draftRepository: DraftRepository
): ViewModel() {

    private val _recap = MutableLiveData(Recap())
    val recap: LiveData<Recap> = _recap

   fun updateRecap(title: String, season: Int, episode: Int){
       _recap.value?.apply {
           this.title = title
           this.season = season
           this.episode = episode
       }
   }
}