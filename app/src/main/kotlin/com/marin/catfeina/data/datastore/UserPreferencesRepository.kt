/*
 * Arquivo: com.marin.catfeina.data.datastore.UserPreferencesRepository.kt
 * @project Catfeina
 * @description
 * Repositório para gerenciar as preferências do usuário armazenadas no DataStore,
 * especificamente o tema base selecionado (ex: Primavera, Verão) e se o modo
 * escuro está ativo para esse tema.
 */
package com.marin.catfeina.data.datastore

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey // MUDANÇA: Para modo escuro
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.marin.catfeina.ui.theme.BaseTheme
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "catfeina_user_settings")

@Singleton
class UserPreferencesRepository @Inject constructor(@ApplicationContext private val context: Context) {

    private companion object {
        const val TAG = "UserPrefsRepo"
    }

    private object PreferencesKeys {
        val SELECTED_BASE_THEME = stringPreferencesKey("selected_base_theme")
        // VAL SELECTED_THEME_STATE = stringPreferencesKey("selected_theme_state") // REMOVIDO
        val IS_DARK_MODE = booleanPreferencesKey("is_dark_mode_enabled") // NOVO: para Claro/Escuro
    }

    val selectedBaseThemeFlow: Flow<BaseTheme> = context.dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                Log.e(TAG, "Erro ao ler tema base.", exception)
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            val themeName = preferences[PreferencesKeys.SELECTED_BASE_THEME] ?: BaseTheme.PRIMAVERA.name
            try {
                BaseTheme.valueOf(themeName)
            } catch (e: IllegalArgumentException) {
                Log.w(TAG, "Tema base inválido no DataStore: $themeName. Usando padrão.", e)
                BaseTheme.PRIMAVERA
            }
        }

    /**
     * Um [Flow] que emite se o modo escuro está ativo (true) ou claro (false).
     * Se nenhuma preferência for encontrada, emite 'false' (modo claro) como padrão.
     */
    val isDarkModeEnabledFlow: Flow<Boolean> = context.dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                Log.e(TAG, "Erro ao ler preferência de modo escuro.", exception)
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            preferences[PreferencesKeys.IS_DARK_MODE] ?: false // Padrão: Modo Claro (false)
        }

    suspend fun updateSelectedBaseTheme(baseTheme: BaseTheme) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.SELECTED_BASE_THEME] = baseTheme.name
            Log.d(TAG, "Tema base atualizado para: ${baseTheme.name}")
        }
    }

    /**
     * Atualiza o estado de modo escuro no DataStore.
     *
     * @param isDarkMode true para ativar o modo escuro, false para o modo claro.
     */
    suspend fun updateIsDarkMode(isDarkMode: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.IS_DARK_MODE] = isDarkMode
            Log.d(TAG, "Modo escuro atualizado para: $isDarkMode")
        }
    }

    // REMOVA a função antiga:
    // suspend fun updateSelectedThemeState(themeState: ThemeState) { ... }
    // REMOVA o flow antigo:
    // val selectedThemeStateFlow: Flow<ThemeState> = ...
}

