package com.puntogris.recap.feature_recap.presentation.create_recap

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.puntogris.recap.feature_recap.domain.model.Recap
import com.puntogris.recap.feature_recap.domain.use_case.PublishRecapUseCase
import com.puntogris.recap.feature_recap.domain.use_case.SaveRecapDraftUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class CreateRecapViewModel @Inject constructor(
    private val saveRecapDraftUseCase: SaveRecapDraftUseCase,
    private val publishRecapUseCase: PublishRecapUseCase
) : ViewModel() {

    private val _showMenu = MutableLiveData(false)
    val showMenu: LiveData<Boolean> = _showMenu

    fun toggleMenu() {
        _showMenu.value = !_showMenu.value!!
    }

    private val _recap = MutableStateFlow(Recap())
    val recap: StateFlow<Recap> = _recap.asStateFlow()

    fun updateRecap(title: String, season: Int, episode: Int) {
        _recap.value.apply {
            this.title = title
            this.season = season
            this.episode = episode
        }
    }

    fun updateRecapBody(body: String) {
        _recap.value.body = body
    }

    suspend fun saveRecapLocally() = saveRecapDraftUseCase(recap.value)

    fun setRecap(recap: Recap) {
        _recap.value = recap
    }

    suspend fun publishRecap() = publishRecapUseCase(recap.value)

}