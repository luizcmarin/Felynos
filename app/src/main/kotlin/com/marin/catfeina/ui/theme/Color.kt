// =============================================================================
// Arquivo: com.marin.catfeina.ui.theme.Color.kt
// Descrição: Define as paletas de cores primárias e secundárias, bem como
//            outras cores semânticas (background, surface, error, etc.)
//            para os temas claro e escuro do aplicativo Catfeina.
//            Estas cores são usadas para construir os ColorSchemes no arquivo
//            Theme.kt, seguindo as diretrizes do Material Design 3.
// =============================================================================
// Arquivo: com/marin/catfeina/ui/theme/Color.kt
package com.marin.catfeina.ui.theme

import androidx.compose.ui.graphics.Color

// Paleta "Catfeina Poesia" - Modo Claro e Escuro

// --- Neutros Quentes (Beges, Cremes, Marrons Suaves) ---
val Cream100 = Color(0xFFFDFCF7) // Fundo principal modo claro, quase branco mas quente
val Cream95 = Color(0xFFFBF5EB)
val Cream90 = Color(0xFFF5F0E6)
val Cream80 = Color(0xFFE9E0D1)

val Beige100 = Color(0xFFF0EBE0) // Superfícies claras
val Beige90 = Color(0xFFE6DACC)
val Beige80 = Color(0xFFDCD0B9)
val Beige40 = Color(0xFFA89F8D) // Contraste médio
val Beige20 = Color(0xFF6B6559)
val Beige10 = Color(0xFF3F3B33)

val SepiaBrown = Color(0xFF705D53)      // Cor de texto principal (modo claro), primário sutil
val DarkSepiaBrown = Color(0xFF493C36) // Variação mais escura para containers, etc.

val WarmGray90 = Color(0xFFEAE8E4)
val WarmGray80 = Color(0xFFD2CEC8)
val WarmGray60 = Color(0xFF9F9A93)
val WarmGray40 = Color(0xFF706B64)
val WarmGray20 = Color(0xFF433F3A)  // Fundo principal modo escuro
val WarmGray10 = Color(0xFF2E2A26)  // Superfícies escuras

// --- Cor de Destaque (Bordô Discreto) ---
val DeepBordeaux80 = Color(0xFFE5BDBA) // Primário/Terciário modo claro (mais claro para contraste)
val DeepBordeaux60 = Color(0xFFA1514B) // Primário/Terciário principal
val DeepBordeaux40 = Color(0xFF803732) // Primário/Terciário modo escuro
val DeepBordeaux30 = Color(0xFF652924)
val DeepBordeaux20 = Color(0xFF4D1F1C)
val DeepBordeaux10 = Color(0xFF3B1715)

// --- Cores de Feedback (Erro) - Padrão M3 são boas ---
val ErrorRed100 = Color(0xFFFFFFFF)
val ErrorRed90 = Color(0xFFFFDAD6) // Error Container (Light)
val ErrorRed80 = Color(0xFFFFB4AB) // Error (Light)
val ErrorRed40 = Color(0xFFBA1A1A) // Error (Dark) / OnErrorContainer (Light)
val ErrorRed20 = Color(0xFF690005) // OnError (Dark) / ErrorContainer (Dark)
val ErrorRed10 = Color(0xFF410002) // OnErrorContainer (Dark)

