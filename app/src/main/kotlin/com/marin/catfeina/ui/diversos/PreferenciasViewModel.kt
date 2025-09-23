/*
 * Arquivo: com.marin.catfeina.ui.diversos.PreferenciasViewModel.kt
 * @project Catfeina
 * @description
 * ViewModel para a tela de Preferências. Gerencia o estado da seleção de tema
 * e interage com o [UserPreferencesRepository] para persistir as escolhas.
 */
package com.marin.catfeina.ui.diversos // Pacote 'diversos'

import android.util.Log // Adicionar para logging
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marin.catfeina.data.datastore.UserPreferencesRepository
import com.marin.catfeina.ui.theme.ThemeChoice
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onEach // Adicionar para logging
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel para a [PreferenciasScreen].
 * Responsável por expor a preferência de tema atual e permitir sua atualização.
 *
 * @property userPreferencesRepository Repositório para acessar e modificar as preferências do usuário.
 */
@HiltViewModel
class PreferenciasViewModel @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    init {
        Log.d("PreferenciasVM", "ViewModel inicializado. Repositório: $userPreferencesRepository") // Log de inicialização
    }

    /**
     * Expõe a [ThemeChoice] atualmente selecionada como um [StateFlow].
     * O estado é coletado do [UserPreferencesRepository.selectedThemeFlow] e compartilhado
     * enquanto houver inscritos ativos, com um buffer para o último valor emitido.
     * Inicia com [ThemeChoice.PADRAO] até que o valor real seja carregado do DataStore.
     */
    val currentTheme: StateFlow<ThemeChoice> = userPreferencesRepository.selectedThemeFlow
        .onEach { themeChoice -> // Log para cada emissão do repositório
            Log.d("PreferenciasVM", "Coletado do repositório: ${themeChoice.name}")
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L), // Mantém o flow ativo por 5s após o último observador
            initialValue = ThemeChoice.PADRAO
        )
    // Você poderia adicionar um .onEach aqui também para ver o que o stateIn está emitindo, mas o acima já é bom.

    /**
     * Atualiza a preferência de tema do usuário.
     * Lança uma coroutine no [viewModelScope] para chamar o método de atualização
     * no [userPreferencesRepository].
     *
     * @param newTheme A nova [ThemeChoice] selecionada pelo usuário.
     */
    fun updateTheme(newTheme: ThemeChoice) {
        Log.d("PreferenciasVM", "Função updateTheme chamada com: ${newTheme.name}") // Log
        viewModelScope.launch {
            Log.d("PreferenciasVM", "Atualizando tema no repositório para: ${newTheme.name}") // Log
            userPreferencesRepository.updateSelectedTheme(newTheme)
            Log.d("PreferenciasVM", "Tema atualizado no repositório.") // Log
        }
    }
}

