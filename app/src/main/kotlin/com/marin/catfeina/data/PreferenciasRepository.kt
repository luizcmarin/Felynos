// =============================================================================
// Arquivo: com.marin.catfeina.data.PreferenciasRepository.kt
// Descrição: Repositório responsável por gerenciar as preferências do usuário
//            armazenadas localmente utilizando Jetpack DataStore Preferences.
//            Especificamente, este repositório lida com a leitura e escrita
//            da preferência de tema do aplicativo (claro, escuro ou padrão
//            do sistema), representada pelo enum PreferenciaTema.
//            Fornece um Flow para observar mudanças na preferência de tema
//            e uma função suspend para atualizá-la.
// =============================================================================
package com.marin.catfeina.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.marin.catfeina.dominio.PreferenciaTema // Assumindo que PreferenciaTema está em 'dominio'
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// Define o nome do DataStore
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "catfeina_preferences")

class PreferenciasRepository(private val context: Context) {

    private object PreferencesKeys {
        val PREFERENCIA_TEMA = stringPreferencesKey("PREFERENCIA_TEMA")
        // Você também pode ter uma chave booleana separada se preferir não usar enum/string
        // val IS_DARK_MODE_KEY = booleanPreferencesKey("is_dark_mode")
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
}
