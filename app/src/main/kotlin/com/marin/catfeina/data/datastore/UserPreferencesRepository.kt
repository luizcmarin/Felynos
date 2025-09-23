/*
 * Arquivo: com.marin.catfeina.data.datastore.UserPreferencesRepository.kt
 * @project Catfeina
 * @description
 * Repositório para gerenciar as preferências do usuário armazenadas no DataStore,
 * especificamente a preferência do tema do aplicativo.
 */
package com.marin.catfeina.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.marin.catfeina.ui.theme.ThemeChoice
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Extensão para fornecer uma instância singleton do [DataStore] de preferências para o aplicativo.
 */
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "catfeina_settings")

/**
 * Repositório responsável por ler e escrever as preferências do usuário, como a escolha do tema.
 * Utiliza [DataStore] para persistência de dados.
 *
 * @property context Contexto da aplicação, injetado pelo Hilt.
 */
@Singleton
class UserPreferencesRepository @Inject constructor(@ApplicationContext private val context: Context) {

    /**
     * Objeto privado para armazenar as chaves de preferência de forma organizada.
     */
    private object PreferencesKeys {
        val SELECTED_THEME = stringPreferencesKey("selected_theme")
    }

    /**
     * Um [Flow] que emite a [ThemeChoice] atualmente selecionada pelo usuário.
     * Se nenhuma preferência for encontrada ou ocorrer um erro, emite [ThemeChoice.PADRAO] como fallback.
     * Trata exceções de IO que podem ocorrer durante a leitura do DataStore.
     */
    val selectedThemeFlow: Flow<ThemeChoice> = context.dataStore.data
        .catch { exception ->
            // Trata erros de leitura do DataStore (ex: IOException)
            if (exception is IOException) {
                // Logar o erro ou emitir um valor padrão
                // Log.e("UserPrefsRepo", "Erro ao ler preferências de tema.", exception)
                emit(androidx.datastore.preferences.core.emptyPreferences()) // Emite preferências vazias para continuar com o padrão
            } else {
                throw exception
            }
        }
        .map { preferences ->
            val themeName = preferences[PreferencesKeys.SELECTED_THEME] ?: ThemeChoice.PADRAO.name
            try {
                ThemeChoice.valueOf(themeName)
            } catch (e: IllegalArgumentException) {
                // Em caso de valor inválido salvo (ex: enum renomeado), retorna o padrão
                // Log.w("UserPrefsRepo", "Valor de tema inválido encontrado: $themeName", e)
                ThemeChoice.PADRAO
            }
        }

    /**
     * Atualiza a [ThemeChoice] selecionada pelo usuário no DataStore.
     *
     * @param theme A nova [ThemeChoice] a ser salva.
     */
    suspend fun updateSelectedTheme(theme: ThemeChoice) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.SELECTED_THEME] = theme.name
        }
    }
}
