// =============================================================================
// Arquivo: com.marin.catfeina.ui.preferencias.PreferenciasViewModel.kt
// Descrição: ViewModel responsável por gerenciar a lógica de negócios
//            relacionada às preferências do usuário, como tema do aplicativo
//            e tamanho da fonte do conteúdo da poesia.
//            Interage com PreferenciasRepository e expõe as configurações
//            como StateFlows para a UI.
// =============================================================================
package com.marin.catfeina.ui.preferencias

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marin.catfeina.data.PreferenciasRepository
import com.marin.catfeina.dominio.PreferenciaTema
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PreferenciasViewModel @Inject constructor(
    private val preferenciasRepository: PreferenciasRepository
) : ViewModel() {

    val preferenciaTema: StateFlow<PreferenciaTema> = preferenciasRepository.preferenciaTemaFlow
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = PreferenciaTema.SYSTEM
        )

    fun updatePreferenciaTema(novaPreferencia: PreferenciaTema) {
        viewModelScope.launch {
            preferenciasRepository.updatePreferenciaTema(novaPreferencia)
        }
    }

    // --- LÓGICA PARA O TAMANHO DA FONTE ---

    val fontSizeMultiplier: StateFlow<Float> = preferenciasRepository.fontSizeMultiplierFlow
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = 1.0f // Valor inicial antes do DataStore carregar
        )

    /**
     * Atualiza o multiplicador de tamanho de fonte para o conteúdo da poesia.
     * @param novoMultiplicador O novo multiplicador a ser aplicado (ex: 0.8f, 1.0f, 1.2f).
     */
    fun updateFontSizeMultiplier(novoMultiplicador: Float) {
        viewModelScope.launch {
            // Você pode adicionar validações aqui se desejar, ex:
            // val clampedMultiplier = novoMultiplicador.coerceIn(0.7f, 2.0f)
            // preferenciasRepository.updateFontSizeMultiplier(clampedMultiplier)
            preferenciasRepository.updateFontSizeMultiplier(novoMultiplicador)
        }
    }
}
