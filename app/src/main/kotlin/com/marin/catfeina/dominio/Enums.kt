// =============================================================================
// Arquivo: com.marin.catfeina.dominio.Enums.kt
// Descrição: Coleção de enums utilitárias.
// =============================================================================
package com.marin.catfeina.dominio

import androidx.annotation.StringRes
import com.marin.catfeina.R

/**
 * Enumeração que define poesias das poesias.
 */
enum class CategoriaPoesiaEnum(@StringRes val displayNameResId: Int) {
    POESIA(R.string.categoria_poesia),
    EXTRAS(R.string.label_extras),
    TEATRO(R.string.categoria_teatro)
}

enum class PreferenciaTema {
    SYSTEM, LIGHT, DARK
}

enum class MascoteEstado {
    FILHOTE, JOVEM, MESTRE, LENDARIO
}
