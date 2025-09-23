/*
 * Arquivo: com.marin.catfeina.ui.theme.CatfeinaAppTheme.kt
 * @project Catfeina
 * @description
 * Composable principal para aplicar o tema Material 3 selecionado ao aplicativo Catfeina.
 * Suporta múltiplos temas de cores selecionáveis pelo usuário, tema claro/escuro e cores dinâmicas (Material You).
 */
package com.marin.catfeina.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.marin.catfeina.ui.theme.padrao.AppTypographyPadrao
import com.marin.catfeina.ui.theme.padrao.darkSchemePadrao
import com.marin.catfeina.ui.theme.padrao.lightSchemePadrao
import com.marin.catfeina.ui.theme.verao.AppTypographyVerao
import com.marin.catfeina.ui.theme.verao.darkSchemeVerao
import com.marin.catfeina.ui.theme.verao.lightSchemeVerao


/**
 * Composable que aplica o tema selecionado ao conteúdo do aplicativo.
 *
 * @param selectedTheme A escolha de tema feita pelo usuário (ou padrão).
 * @param useDarkTheme Indica se o tema escuro do sistema deve ser usado. Padrão é [isSystemInDarkTheme].
 * @param useDynamicColor Indica se as cores dinâmicas (Material You) devem ser usadas no Android 12+.
 *                        Se `true` e disponível, sobrepõe o `selectedTheme`. Padrão é `true`.
 * @param content O conteúdo Composable ao qual o tema será aplicado.
 */
@Composable
fun CatfeinaAppTheme(
    selectedTheme: ThemeChoice = ThemeChoice.PADRAO,
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    useDynamicColor: Boolean = true, // Permite ao usuário (eventualmente) desabilitar Material You
    content: @Composable () -> Unit
) {
    // 1. Determina o ColorScheme base com base no tema selecionado e no modo claro/escuro
    val baseColorScheme: ColorScheme = when (selectedTheme) {
        ThemeChoice.PADRAO -> { // Supondo que ThemeChoice tenha PADRAO
            if (useDarkTheme) darkSchemePadrao else lightSchemePadrao
        }
        ThemeChoice.VERAO -> {  // Supondo que ThemeChoice tenha VERAO
            if (useDarkTheme) darkSchemeVerao else lightSchemeVerao
        }
        // Adicione outros cases conforme necessário
    }

    // 2. Tenta aplicar cores dinâmicas (Material You) se habilitado e disponível
    val effectiveColorScheme: ColorScheme = if (useDynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        val context = LocalContext.current
        if (useDarkTheme) {
            dynamicDarkColorScheme(context)
        } else {
            dynamicLightColorScheme(context)
        }
    } else {
        baseColorScheme // Usa o esquema base se cores dinâmicas não estiverem disponíveis/habilitadas
    }

    val selectedTypography: Typography = when (selectedTheme) {
        ThemeChoice.PADRAO -> AppTypographyPadrao
        ThemeChoice.VERAO -> AppTypographyVerao
    }

    // 3. Aplica o MaterialTheme com o ColorScheme efetivo, tipografia e formas
    MaterialTheme(
        colorScheme = effectiveColorScheme,
        typography = selectedTypography,
        content = content
    )
}
