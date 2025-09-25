/*
 * Arquivo: com.marin.catfeina.ui.theme.ThemeViewModel.kt
 * @project Catfeina
 * @description ViewModel responsável por gerenciar e fornecer o tema base selecionado
 *              (ex: Primavera, Verão) e se o modo escuro está ativo para a UI.
 *              Interage com o UserPreferencesRepository para persistir e recuperar essas escolhas.
 */
package com.marin.catfeina.ui.theme

import android.util.Log // Adicionado para logging
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marin.catfeina.data.datastore.UserPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel que gerencia a seleção do tema base e o estado claro/escuro da aplicação.
 *
 * Este ViewModel observa as preferências do usuário a partir do [UserPreferencesRepository]
 * e expõe o [BaseTheme] atual e um booleano [isDarkMode] como [StateFlow]s separados.
 * Ele também fornece funções para definir explicitamente o tema base e para alternar
 * o estado claro/escuro.
 *
 * @property userPreferencesRepository O repositório para ler e salvar as preferências de tema do usuário.
 */
@HiltViewModel
class ThemeViewModel @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    private companion object {
        const val TAG = "ThemeViewModel" // Para logging
    }

    init {
        Log.d(TAG, "ThemeViewModel inicializado.")
    }

    /**
     * Um [StateFlow] que emite o [BaseTheme] atual selecionado pelo usuário.
     * O valor inicial padrão é [BaseTheme.PRIMAVERA].
     */
    val currentBaseTheme: StateFlow<BaseTheme> = userPreferencesRepository.selectedBaseThemeFlow
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = BaseTheme.PRIMAVERA
        )

    /**
     * Um [StateFlow] que emite 'true' se o modo escuro estiver ativo, 'false' caso contrário.
     * O valor inicial padrão é 'false' (modo claro).
     */
    val isDarkMode: StateFlow<Boolean> = userPreferencesRepository.isDarkModeEnabledFlow // MUDANÇA: Usa o novo flow
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = false // Padrão: Modo Claro
        )

    // REMOVIDO: currentThemeState: StateFlow<ThemeState>
    // val currentThemeState: StateFlow<ThemeState> = userPreferencesRepository.selectedThemeStateFlow
    //     .stateIn(
    //         scope = viewModelScope,
    //         started = SharingStarted.WhileSubscribed(5000L),
    //         initialValue = ThemeState.AUTO
    //     )

    // REMOVIDO: O flow combinado não faz mais sentido com a simplificação
    // val combinedThemeInfo: StateFlow<Pair<BaseTheme, ThemeState>> = ...

    /**
     * Define explicitamente o tema base do aplicativo.
     * A nova escolha de tema base é persistida através do [userPreferencesRepository].
     *
     * @param newBaseTheme O [BaseTheme] para o qual o tema deve ser definido.
     */
    fun setBaseTheme(newBaseTheme: BaseTheme) { // Parâmetro renomeado para clareza
        Log.d(TAG, "setBaseTheme chamado com: ${newBaseTheme.name}")
        viewModelScope.launch {
            userPreferencesRepository.updateSelectedBaseTheme(newBaseTheme)
        }
    }

    /**
     * Alterna o estado claro/escuro do aplicativo.
     * A nova escolha de estado é persistida através do [userPreferencesRepository].
     */
    fun toggleDarkMode() { // MUDANÇA: Nome e lógica
        Log.d(TAG, "toggleDarkMode chamado.")
        viewModelScope.launch {
            val currentIsDark = isDarkMode.value // Pega o valor atual do StateFlow isDarkMode
            userPreferencesRepository.updateIsDarkMode(!currentIsDark)
            Log.d(TAG, "Modo escuro alternado. Novo estado esperado: ${!currentIsDark}")
        }
    }

    // REMOVIDO: setThemeState(themeState: ThemeState)
    // fun setThemeState(themeState: ThemeState) {
    //     viewModelScope.launch {
    //         userPreferencesRepository.updateSelectedThemeState(themeState)
    //     }
    // }

    // REMOVIDO: toggleThemeState()
    // fun toggleThemeState() {
    //     viewModelScope.launch {
    //         val newState = when (currentThemeState.value) {
    //             ThemeState.CLARO -> ThemeState.ESCURO
    //             ThemeState.ESCURO -> ThemeState.AUTO
    //             ThemeState.AUTO -> ThemeState.CLARO
    //         }
    //         userPreferencesRepository.updateSelectedThemeState(newState)
    //     }
    // }
}

