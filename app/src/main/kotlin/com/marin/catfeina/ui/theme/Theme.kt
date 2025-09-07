// =============================================================================
// Arquivo: com.marin.catfeina.ui.theme.Theme.kt
// Descrição: Define o tema principal do aplicativo Catfeina para Jetpack Compose.
//            Este arquivo configura os esquemas de cores (ColorScheme) para os
//            modos claro e escuro, integra a tipografia (Typography) e as
//            formas (Shapes) definidas em outros arquivos do tema.
//            Também inclui lógica para suportar cores dinâmicas (Material You)
//            em dispositivos Android 12+ e para respeitar a preferência de
//            tema do sistema do usuário.
// =============================================================================
package com.marin.catfeina.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

// Esquema de Cores para o Tema Escuro
private val DarkColorScheme = darkColorScheme(
    primary = DarkPrimary,
    onPrimary = DarkOnPrimary,
    primaryContainer = DarkPrimaryContainer,
    onPrimaryContainer = DarkOnPrimaryContainer,
    secondary = DarkSecondary,
    onSecondary = DarkOnSecondary,
    background = DarkBackground,
    onBackground = DarkOnBackground,
    surface = DarkSurface,
    onSurface = DarkOnSurface,
    // Preencha outras cores conforme necessário: tertiary, error, etc.
    // Exemplo:
    // tertiary = DarkTertiary,
    // onTertiary = DarkOnTertiary,
    // error = DarkError,
    // onError = DarkOnError,
    // surfaceVariant = DarkSurfaceVariant,
    // onSurfaceVariant = DarkOnSurfaceVariant,
    // outline = DarkOutline
)

// Esquema de Cores para o Tema Claro
private val LightColorScheme = lightColorScheme(
    primary = LightPrimary,
    onPrimary = LightOnPrimary,
    primaryContainer = LightPrimaryContainer,
    onPrimaryContainer = LightOnPrimaryContainer,
    secondary = LightSecondary,
    onSecondary = LightOnSecondary,
    background = LightBackground,
    onBackground = LightOnBackground,
    surface = LightSurface,
    onSurface = LightOnSurface,
    // Preencha outras cores conforme necessário
    // Exemplo:
    // tertiary = LightTertiary,
    // onTertiary = LightOnTertiary,
    // error = LightError,
    // onError = LightOnError,
    // surfaceVariant = LightSurfaceVariant,
    // onSurfaceVariant = LightOnSurfaceVariant,
    // outline = LightOutline

    /* Outras cores padrão serão sobrescritas se você as definir aqui.
      As cores não definidas explicitamente usarão os padrões do Material3.
    */
)

@Composable
fun CatfeinaTheme( // Renomeie para o nome do seu tema, ex: CatfeinaTheme
    darkTheme: Boolean = isSystemInDarkTheme(), // Respeita a configuração do sistema por padrão
    // Dynamic color é um recurso do Android 12+ (Material You)
    // Se ativado, as cores do app são baseadas no papel de parede do usuário.
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    // Você pode adicionar aqui configurações de StatusBar e NavigationBar se desejar
    // Por exemplo, usando Accompanist System UI Controller (se ainda estiver usando)
    // ou as APIs mais recentes do Compose para isso.

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}
