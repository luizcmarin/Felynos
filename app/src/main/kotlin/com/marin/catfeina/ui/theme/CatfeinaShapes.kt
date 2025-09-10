// =============================================================================
// Arquivo: com.marin.catfeina.ui.theme.CatfeinaShapes.kt
// Descrição: Define as configurações de forma (Shapes) para os componentes
//            Material Design 3 no aplicativo Catfeina.
//            Especifica os raios dos cantos para diferentes tamanhos de
//            componentes (extraSmall, small, medium, large, extraLarge),
//            permitindo uma aparência visual consistente.
// =============================================================================
package com.marin.catfeina.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

val CatfeinaShapes = Shapes(
    extraSmall = RoundedCornerShape(4.dp),
    small = RoundedCornerShape(8.dp),
    medium = RoundedCornerShape(12.dp), // Forma padrão para muitos componentes como Cards
    large = RoundedCornerShape(16.dp),
    extraLarge = RoundedCornerShape(28.dp) // Usado para componentes como NavigationDrawer
)

// Você também pode definir formas customizadas se precisar:
// val MyCustomShape = RoundedCornerShape(topStart = 16.dp, topEnd = 0.dp, bottomStart = 16.dp, bottomEnd = 0.dp)
