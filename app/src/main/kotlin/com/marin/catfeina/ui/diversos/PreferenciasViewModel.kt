/*
 * Arquivo: com.marin.catfeina.ui.diversos.PreferenciasViewModel.kt * @project Catfeina
 * @description
 * ViewModel para a tela de Preferências. Gerencia o estado da seleção de tema base
 * (ex: Primavera, Verão) e o estado claro/escuro, interagindo
 * com o [UserPreferencesRepository] para persistir as escolhas.
 */
package com.marin.catfeina.ui.diversos

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marin.catfeina.data.datastore.UserPreferencesRepository
import com.marin.catfeina.ui.theme.BaseTheme
// import com.marin.catfeina.ui.theme.ThemeState // REMOVIDO - Não usamos mais o enum ThemeState aqui
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PreferenciasViewModel @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    private companion object {
        const val TAG = "PreferenciasVM"
    }

    init {
        Log.d(TAG, "ViewModel inicializado. Repositório: $userPreferencesRepository")
    }

    /**
     * Expõe o [BaseTheme] atualmente selecionado como um [StateFlow].
     */
    val currentBaseTheme: StateFlow<BaseTheme> = userPreferencesRepository.selectedBaseThemeFlow
        .onEach { baseTheme ->
            Log.d(TAG, "Coletado BaseTheme do repositório: ${baseTheme.name}")
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = BaseTheme.PRIMAVERA // Valor inicial padrão
        )

    /**
     * Expõe se o modo escuro está ativo (true) ou claro (false).
     */
    val isDarkMode: StateFlow<Boolean> = userPreferencesRepository.isDarkModeEnabledFlow // MUDANÇA
        .onEach { isDark ->
            Log.d(TAG, "Coletado isDarkMode do repositório: $isDark")
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = false // Padrão: Modo Claro
        )

    /**
     * Atualiza o [BaseTheme] selecionado pelo usuário.
     */
    fun updateBaseTheme(newBaseTheme: BaseTheme) {
        Log.d(TAG, "Função updateBaseTheme chamada com: ${newBaseTheme.name}")
        viewModelScope.launch {
            userPreferencesRepository.updateSelectedBaseTheme(newBaseTheme)
            Log.d(TAG, "BaseTheme atualizado no repositório.")
        }
    }

    /**
     * Alterna o estado claro/escuro.
     */
    fun toggleDarkMode() { // MUDANÇA
        viewModelScope.launch {
            val currentIsDark = isDarkMode.value // Pega o valor atual do StateFlow
            userPreferencesRepository.updateIsDarkMode(!currentIsDark)
            Log.d(TAG, "Modo escuro alternado para: ${!currentIsDark}")
        }
    }

    // REMOVA as funções antigas relacionadas ao ThemeState (enum)
    // fun updateThemeState(newThemeState: ThemeState) { ... }
    // fun toggleThemeState() { ... }
}

