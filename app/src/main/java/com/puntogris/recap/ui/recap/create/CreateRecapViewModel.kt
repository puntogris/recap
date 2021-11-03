package com.puntogris.recap.ui.recap.create

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.puntogris.recap.data.repository.DraftRepositoryImpl
import com.puntogris.recap.model.Recap
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CreateRecapViewModel @Inject constructor(
    private val draftRepository: DraftRepositoryImpl
): ViewModel() {

    private val _showMenu = MutableLiveData(false)
    val showMenu: LiveData<Boolean> = _showMenu

    fun toggleMenu(){
        _showMenu.value = !_showMenu.value!!
    }

    private val _recap = MutableLiveData(Recap())
    val recap: LiveData<Recap> = _recap

    fun updateRecap(title: String, season: Int, episode: Int){
       _recap.value?.apply {
           this.title = title
           this.season = season
           this.episode = episode
       }
    }

    fun updateRecapBody(body: String){
        _recap.value?.body = body
    }

    suspend fun saveRecapLocally() = draftRepository.saveRecapLocalDb(recap.value!!)

    fun setRecap(recap: Recap){
        _recap.value = recap
    }
}