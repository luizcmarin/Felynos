// =============================================================================
// Arquivo: com.marin.catfeina.ui.theme.Color.kt
// Descrição: Define as paletas de cores primárias e secundárias, bem como
//            outras cores semânticas (background, surface, error, etc.)
//            para os temas claro e escuro do aplicativo Catfeina.
//            Estas cores são usadas para construir os ColorSchemes no arquivo
//            Theme.kt, seguindo as diretrizes do Material Design 3.
// =============================================================================
package com.marin.catfeina.ui.theme

import androidx.compose.ui.graphics.Color

// Cores para o Tema Claro (Exemplo)
val LightPrimary = Color(0xFF6200EE)
val LightOnPrimary = Color.White
val LightPrimaryContainer = Color(0xFFBB86FC)
val LightOnPrimaryContainer = Color.Black
val LightSecondary = Color(0xFF03DAC6)
val LightOnSecondary = Color.Black
val LightBackground = Color(0xFFFFFFFF)
val LightOnBackground = Color.Black
val LightSurface = Color(0xFFFFFFFF)
val LightOnSurface = Color.Black
// ... defina outras cores (tertiary, error, etc.)

// Cores para o Tema Escuro (Exemplo)
val DarkPrimary = Color(0xFFBB86FC)
val DarkOnPrimary = Color.Black
val DarkPrimaryContainer = Color(0xFF3700B3)
val DarkOnPrimaryContainer = Color.White
val DarkSecondary = Color(0xFF03DAC6) // Pode ser a mesma ou diferente
val DarkOnSecondary = Color.Black
val DarkBackground = Color(0xFF121212)
val DarkOnBackground = Color.White
val DarkSurface = Color(0xFF121212) // Frequentemente a mesma que o background no tema escuro
val DarkOnSurface = Color.White
// ... defina outras cores (tertiary, error, etc.)
