// =============================================================================
// Arquivo: com.marin.catfeina.data.PreferenciasRepository.kt
// Descrição: Repositório responsável por gerenciar as preferências do usuário
//            armazenadas localmente utilizando Jetpack DataStore Preferences.
//            Lida com a preferência de tema e o multiplicador de tamanho de
//            fonte para o conteúdo da poesia.
// =============================================================================
package com.marin.catfeina.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey // Importar floatPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.marin.catfeina.dominio.PreferenciaTema
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

// Define o nome do DataStore
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "catfeina_preferences")

@Singleton // Adicionar @Singleton se estiver usando Hilt para o repositório
class PreferenciasRepository @Inject constructor(
    // Injetar @ApplicationContext se estiver usando Hilt para o repositório
    // import dagger.hilt.android.qualifiers.ApplicationContext
    // private val context: Context
    // Se não estiver injetando context via Hilt, pode manter como estava
    private val context: Context
) {

    private object PreferencesKeys {
        val PREFERENCIA_TEMA = stringPreferencesKey("PREFERENCIA_TEMA")
        val FONT_SIZE_MULTIPLIER = floatPreferencesKey("FONT_SIZE_MULTIPLIER") // NOVA CHAVE
    }

    // Fluxo para observar a configuração do tema
    val preferenciaTemaFlow: Flow<PreferenciaTema> = context.dataStore.data
        .map { preferences ->
            val themeName = preferences[PreferencesKeys.PREFERENCIA_TEMA] ?: PreferenciaTema.SYSTEM.name
            try {
                PreferenciaTema.valueOf(themeName)
            } catch (e: IllegalArgumentException) {
                PreferenciaTema.SYSTEM // Valor padrão em caso de erro
            }
        }

    // Função para atualizar a configuração do tema
    suspend fun updatePreferenciaTema(preferenciaTema: PreferenciaTema) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.PREFERENCIA_TEMA] = preferenciaTema.name
        }
    }

    // --- LÓGICA PARA O TAMANHO DA FONTE ---

    // Fluxo para observar o multiplicador de tamanho da fonte
    val fontSizeMultiplierFlow: Flow<Float> = context.dataStore.data
        .map { preferences ->
            // Retorna o valor salvo ou 1.0f (tamanho normal) como padrão
            preferences[PreferencesKeys.FONT_SIZE_MULTIPLIER] ?: 1.0f
        }

    // Função para atualizar o multiplicador de tamanho da fonte
    suspend fun updateFontSizeMultiplier(multiplier: Float) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.FONT_SIZE_MULTIPLIER] = multiplier
        }
    }
}
