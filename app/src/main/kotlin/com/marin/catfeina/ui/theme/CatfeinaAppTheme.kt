/*
 * Arquivo: com.marin.catfeina.ui.theme.CatfeinaAppTheme.kt
 * @project Catfeina
 * @description
 * Composable principal para aplicar o tema Material 3 selecionado ao aplicativo Catfeina.
 * Suporta múltiplos temas base (Primavera, Verão) e modo claro/escuro para cada um.
 * AGORA COM ANIMAÇÃO NA TROCA DE TEMA.
 */
package com.marin.catfeina.ui.theme

import android.app.Activity
// import android.os.Build // Não é mais necessário para dynamicColor
import androidx.compose.animation.animateColorAsState
// import androidx.compose.foundation.isSystemInDarkTheme // Não é mais necessário se AUTO foi removido
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
// import androidx.compose.material3.dynamicDarkColorScheme // Não é mais necessário
// import androidx.compose.material3.dynamicLightColorScheme // Não é mais necessário
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.toArgb
// import androidx.compose.ui.platform.LocalContext // Não é mais necessário para dynamicColor
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.marin.catfeina.ui.theme.primavera.AppTypographyPadrao
import com.marin.catfeina.ui.theme.primavera.darkSchemePrimavera
import com.marin.catfeina.ui.theme.primavera.lightSchemePrimavera
import com.marin.catfeina.ui.theme.verao.AppTypographyVerao
import com.marin.catfeina.ui.theme.verao.darkSchemeVerao
import com.marin.catfeina.ui.theme.verao.lightSchemeVerao
// import com.marin.catfeina.ui.theme.ThemeState // REMOVER ESTA IMPORTAÇÃO se ela existir

/**
 * Composable que aplica o tema selecionado ao conteúdo do aplicativo.
 *
 * @param selectedBaseTheme O tema base (ex: Primavera, Verão) a ser aplicado.
 * @param useDarkTheme Indica se o modo escuro deve ser usado.
 * @param content O conteúdo Composable ao qual o tema será aplicado.
 */
@Composable
fun CatfeinaAppTheme(
    selectedBaseTheme: BaseTheme, // = BaseTheme.PRIMAVERA, // Remova o valor padrão se sempre for fornecido
    useDarkTheme: Boolean,        // = false,                 // Remova o valor padrão se sempre for fornecido
    // REMOVIDO: selectedThemeState: ThemeState = ThemeState.AUTO,
    // REMOVIDO: useDynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    // val context = LocalContext.current // Não é mais necessário
    val view = LocalView.current

    // REMOVIDO: Lógica de useDarkThemeActually baseada em ThemeState.AUTO
    // val useDarkThemeActually = when (selectedThemeState) {
    //     ThemeState.CLARO -> false
    //     ThemeState.ESCURO -> true
    //     ThemeState.AUTO -> isSystemInDarkTheme()
    // }
    // AGORA 'useDarkTheme' é passado diretamente.

    // 2. Determina o ColorScheme base (antes da animação)
    // REMOVIDA: Lógica de dynamicColor
    val colorScheme: ColorScheme = when (selectedBaseTheme) {
        BaseTheme.PRIMAVERA -> {
            if (useDarkTheme) darkSchemePrimavera else lightSchemePrimavera // MUDANÇA: usa o parâmetro useDarkTheme
        }
        BaseTheme.VERAO -> {
            if (useDarkTheme) darkSchemeVerao else lightSchemeVerao // MUDANÇA: usa o parâmetro useDarkTheme
        }
        // Adicione outros BaseThemes aqui se necessário
    }

    // 3. Seleciona a Tipografia com base no tema base
    val selectedTypography: Typography = when (selectedBaseTheme) {
        BaseTheme.PRIMAVERA -> AppTypographyPadrao
        BaseTheme.VERAO -> AppTypographyVerao
        // Adicione outros BaseThemes aqui se necessário
    }

    // *** INÍCIO DAS MODIFICAÇÕES PARA ANIMAÇÃO DE COR (Permanece igual) ***
    val animatedBackgroundColor by animateColorAsState(
        targetValue = colorScheme.background,
        label = "animatedBackgroundColor"
    )
    val animatedSurfaceColor by animateColorAsState(
        targetValue = colorScheme.surface,
        label = "animatedSurfaceColor"
    )
    val animatedPrimaryColor by animateColorAsState(
        targetValue = colorScheme.primary,
        label = "animatedPrimaryColor"
    )
    val animatedOnBackgroundColor by animateColorAsState(
        targetValue = colorScheme.onBackground,
        label = "animatedOnBackgroundColor"
    )
    val animatedOnSurfaceColor by animateColorAsState(
        targetValue = colorScheme.onSurface,
        label = "animatedOnSurfaceColor"
    )

    val effectiveColorScheme = colorScheme.copy(
        background = animatedBackgroundColor,
        surface = animatedSurfaceColor,
        primary = animatedPrimaryColor,
        onBackground = animatedOnBackgroundColor,
        onSurface = animatedOnSurfaceColor
    )
    // *** FIM DAS MODIFICAÇÕES PARA ANIMAÇÃO DE COR ***

    // 4. Efeitos colaterais para a aparência das barras de sistema
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
             WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !useDarkTheme
            WindowCompat.getInsetsController(window, view).isAppearanceLightNavigationBars = !useDarkTheme
        }
    }

    // 5. Aplica o MaterialTheme com o ColorScheme efetivo (animado), tipografia e formas
    MaterialTheme(
        colorScheme = effectiveColorScheme,
        typography = selectedTypography,
        content = content
    )
}

