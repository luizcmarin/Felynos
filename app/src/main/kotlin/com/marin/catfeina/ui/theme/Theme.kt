// =============================================================================
// Arquivo: com.marin.catfeina.ui.theme.Theme.kt
// Descrição: Define o tema principal do aplicativo Catfeina para Jetpack Compose.
//            Este arquivo configura os esquemas de cores (ColorScheme) para os
//            modos claro e escuro, integra a tipografia e as formas definidas.
//            Também inclui lógica para suportar cores dinâmicas (Material You)
//            e para respeitar a preferência de tema do sistema do usuário.
// =============================================================================
package com.marin.catfeina.ui.theme

import android.os.Build
import androidx.annotation.ChecksSdkIntAtLeast
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

private val LightCatfeinaColorScheme = lightColorScheme(
    primary = DeepBordeaux60,             // Bordô como destaque principal
    onPrimary = Color.White,
    primaryContainer = DeepBordeaux80,      // Tom mais claro do bordô para containers
    onPrimaryContainer = DeepBordeaux20,    // Texto escuro sobre container primário

    secondary = SepiaBrown,               // Marrom sépia como secundário sutil
    onSecondary = Color.White,
    secondaryContainer = Beige90,           // Container secundário bege claro
    onSecondaryContainer = DarkSepiaBrown,  // Texto escuro sobre container secundário

    tertiary = Beige40,                   // Terciário neutro, para menos destaque
    onTertiary = DarkSepiaBrown,
    tertiaryContainer = Beige80,
    onTertiaryContainer = Beige10,

    error = ErrorRed80,
    onError = ErrorRed20,
    errorContainer = ErrorRed90,
    onErrorContainer = ErrorRed10,

    background = Cream100,                // Fundo principal bem claro e quente
    onBackground = DarkSepiaBrown,        // Texto principal escuro (boa legibilidade)

    surface = Cream95,                  // Superfícies um pouco diferentes do fundo
    onSurface = DarkSepiaBrown,           // Texto sobre superfícies

    surfaceVariant = Beige90,             // Variação de superfície (ex: cards, campos de texto)
    onSurfaceVariant = SepiaBrown,        // Texto sobre variação de superfície

    outline = Beige80,                    // Divisores e bordas sutis
    outlineVariant = WarmGray80,

    inverseSurface = WarmGray20,
    inverseOnSurface = Cream95,
    inversePrimary = DeepBordeaux40, // Primário invertido (para uso em snackbars, etc.)

    scrim = Color(0x99000000) // Padrão é ok
)

private val DarkCatfeinaColorScheme = darkColorScheme(
    primary = DeepBordeaux40,             // Bordô mais claro para modo escuro
    onPrimary = Color.White,
    primaryContainer = DeepBordeaux20,      // Container primário escuro
    onPrimaryContainer = DeepBordeaux80,    // Texto claro sobre container

    secondary = Beige40,                  // Secundário bege mais claro no modo escuro
    onSecondary = DarkSepiaBrown,
    secondaryContainer = Beige20,           // Container secundário escuro
    onSecondaryContainer = Beige90,         // Texto claro sobre container

    tertiary = Beige80,                   // Terciário neutro claro
    onTertiary = Beige10,
    tertiaryContainer = Beige40,
    onTertiaryContainer = Cream90,

    error = ErrorRed40,
    onError = Color.White,
    errorContainer = ErrorRed20,
    onErrorContainer = ErrorRed90,

    background = WarmGray10,              // Fundo escuro quente
    onBackground = Cream90,               // Texto principal claro (boa legibilidade)

    surface = WarmGray20,                 // Superfícies escuras um pouco diferentes do fundo
    onSurface = Cream95,                  // Texto sobre superfícies

    surfaceVariant = Beige20,             // Variação de superfície escura
    onSurfaceVariant = Beige80,           // Texto sobre variação de superfície

    outline = WarmGray40,                 // Divisores e bordas
    outlineVariant = Beige40,

    inverseSurface = Cream90,
    inverseOnSurface = WarmGray10,
    inversePrimary = DeepBordeaux60,

    scrim = Color(0xA6000000)
)

/**
 * Modelo para cores de gradiente.
 */
@Immutable
data class GradientColors(
    val top: Color = Color.Unspecified,
    val bottom: Color = Color.Unspecified,
    val container: Color = Color.Unspecified,
)

/**
 * CompositionLocal para [GradientColors].
 */
val LocalGradientColors = staticCompositionLocalOf { GradientColors() }

/**
 * Modelo para cor de fundo e elevação tonal.
 */
@Immutable
data class BackgroundTheme(
    val color: Color = Color.Unspecified,
    val tonalElevation: Dp = Dp.Unspecified,
)

/**
 * CompositionLocal para [BackgroundTheme].
 */
val LocalBackgroundTheme = staticCompositionLocalOf { BackgroundTheme() }

/**
 * Modelo para tonalidade de ícones.
 */
@Immutable
data class TintTheme(
    val iconTint: Color = Color.Unspecified,
)

/**
 * CompositionLocal para [TintTheme].
 */
val LocalTintTheme = staticCompositionLocalOf { TintTheme() }

@Composable
fun CatfeinaTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true, // Material You ativado por padrão
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && supportsDynamicTheming() -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkCatfeinaColorScheme
        else -> LightCatfeinaColorScheme
    }

    val defaultGradientColors = GradientColors(
        top = colorScheme.inverseOnSurface, // Exemplo: cor de texto em superfície invertida
        bottom = colorScheme.primaryContainer, // Exemplo: container primário
        container = colorScheme.surface, // Exemplo: superfície
    )

    val emptyGradientColors = GradientColors( // Usado para cores dinâmicas
        container = colorScheme.surfaceColorAtElevation(2.dp)
    )

    val gradientColors = if (dynamicColor && supportsDynamicTheming()) {
        emptyGradientColors
    } else {
        defaultGradientColors
    }

    val defaultBackgroundTheme = BackgroundTheme(
        color = colorScheme.surface,
        tonalElevation = 2.dp, // Exemplo de elevação tonal
    )

    val backgroundTheme = defaultBackgroundTheme // Simples por enquanto, poderia variar se tivesse múltiplos temas base

    val defaultTintTheme = TintTheme() // Sem tonalidade específica por padrão
    val dynamicTintTheme = TintTheme(iconTint = colorScheme.primary) // Usa primário para tonalidade com Material You

    val tintTheme = if (dynamicColor && supportsDynamicTheming()) {
        dynamicTintTheme
    } else {
        defaultTintTheme
    }

    // Você pode adicionar aqui configurações de StatusBar e NavigationBar se desejar
    // Por exemplo, usando Accompanist System UI Controller (se ainda estiver usando)
    // ou as APIs mais recentes do Compose para isso.
    // Exemplo com SideEffect para cores da barra de status (requer alguma forma de SystemUiController):
    /*
    val systemUiController = rememberSystemUiController() // Se estiver usando Accompanist
    SideEffect {
        systemUiController.setStatusBarColor(
            color = colorScheme.background, // Ou outra cor desejada
            darkIcons = !darkTheme
        )
        systemUiController.setNavigationBarColor(
            color = colorScheme.background, // Ou outra cor desejada
            darkIcons = !darkTheme
        )
    }
    */

    CompositionLocalProvider(
        LocalGradientColors provides gradientColors,
        LocalBackgroundTheme provides backgroundTheme,
        LocalTintTheme provides tintTheme,
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = CatfeinaTypography,
            shapes = CatfeinaShapes,
            content = content
        )
    }
}

@ChecksSdkIntAtLeast(api = Build.VERSION_CODES.S)
fun supportsDynamicTheming() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S

