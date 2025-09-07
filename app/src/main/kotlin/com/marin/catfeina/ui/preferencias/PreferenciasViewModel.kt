// =============================================================================
// Arquivo: com.marin.catfeina.ui.preferencias.PreferenciasViewModel.kt
// Descrição: ViewModel responsável por gerenciar a lógica de negócios
//            relacionada às preferências do usuário, especificamente a
//            preferência de tema do aplicativo (claro, escuro, sistema).
//            Ele interage com o PreferenciasRepository para obter e atualizar
//            a configuração de tema e expõe essa configuração como um StateFlow
//            para ser observado pela UI (por exemplo, MainActivity).
//            Utiliza Hilt para injeção de dependência.
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
    private val preferenciasRepository: PreferenciasRepository // Convenção: nome do parâmetro em camelCase
) : ViewModel() {

    // Expõe a preferência de tema atual como StateFlow
    // Renomeado de currentPreferenciaTema para preferenciaTema para consistência com MainActivity
    val preferenciaTema: StateFlow<PreferenciaTema> = preferenciasRepository.preferenciaTemaFlow
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L), // Sufixo L para Long
            initialValue = PreferenciaTema.SYSTEM // Valor inicial antes do DataStore carregar
        )

    /**
     * Atualiza a preferência de tema do usuário no DataStore.
     * @param novaPreferencia A nova configuração de tema a ser aplicada.
     */
    fun updatePreferenciaTema(novaPreferencia: PreferenciaTema) {
        viewModelScope.launch {
            preferenciasRepository.updatePreferenciaTema(novaPreferencia)
        }
    }
}
