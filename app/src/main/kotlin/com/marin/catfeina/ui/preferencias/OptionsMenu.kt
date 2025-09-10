// =============================================================================
// Arquivo: com.marin.catfeina.ui.preferencias.OptionsMenu.kt
// Descrição: Este arquivo define o Composable `OptionsMenu`, que representa
//            o menu suspenso (dropdown menu) acessível através da TopAppBar
//            na tela principal. Ele permite ao usuário configurar preferências
//            globais do aplicativo, como o tema visual (claro, escuro, sistema)
//            e o multiplicador do tamanho da fonte para o conteúdo das poesias.
//            Também pode incluir links para outras seções, como a tela "Sobre".
// Autor: [Seu Nome/Apelido ou Nome do Projeto]
// Data de Criação: [Data da Criação da Refatoração - ex: 08 de Agosto de 2024]
// =============================================================================
package com.marin.catfeina.ui.preferencias

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.marin.catfeina.R
import com.marin.catfeina.dominio.DropdownMenuItemWithCheck
import com.marin.catfeina.dominio.PreferenciaTema
import com.marin.catfeina.ui.Icones

/**
 * Representa o menu de opções (geralmente exibido a partir de um ícone de três pontos na TopAppBar)
 * que permite ao usuário configurar preferências como tema e tamanho da fonte, além de
 * fornecer acesso a outras seções, como "Sobre".
 *
 * @param expanded Controla se o menu está visível ou não.
 * @param onDismissRequest Callback invocado quando o usuário solicita o fechamento do menu
 *                         (ex: clicando fora dele).
 * @param preferenciaTemaAtual O [PreferenciaTema] atualmente selecionado no aplicativo.
 * @param onTemaChange Callback invocado quando o usuário seleciona uma nova [PreferenciaTema].
 * @param currentFontSizeMultiplier O multiplicador de tamanho de fonte atual (ex: 1.0f para normal).
 * @param onFontSizeMultiplierChange Callback invocado quando o usuário altera o valor do multiplicador
 *                                   de tamanho da fonte através do Slider.
 * @param onNavigateToSobre Callback invocado quando o usuário clica no item de menu para navegar
 *                          para a tela "Sobre".
 */
@Composable
fun OptionsMenu(
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    preferenciaTemaAtual: PreferenciaTema,
    onTemaChange: (PreferenciaTema) -> Unit,
    currentFontSizeMultiplier: Float,
    onFontSizeMultiplierChange: (Float) -> Unit,
    onNavigateToSobre: () -> Unit
) {
    // Definindo um passo menor para o slider e botões
    val fontSizeStep = 0.05f
    // Novo range para o slider
    val sliderValueRange = 0.4f..2.0f // De 40% a 200%
    val haptic = LocalHapticFeedback.current

    DropdownMenu(
        expanded = expanded,
        onDismissRequest = onDismissRequest,
        modifier = Modifier.width(280.dp)
    ) {
        // SEÇÃO DE TEMA
        Text(
            text = stringResource(R.string.tema_do_aplicativo),
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 4.dp)
        )
        DropdownMenuItemWithCheck(
            text = stringResource(R.string.tema_claro),
            isSelected = preferenciaTemaAtual == PreferenciaTema.LIGHT,
            onClick = {
                onTemaChange(PreferenciaTema.LIGHT)
            }
        )
        DropdownMenuItemWithCheck(
            text = stringResource(R.string.tema_escuro),
            isSelected = preferenciaTemaAtual == PreferenciaTema.DARK,
            onClick = {
                onTemaChange(PreferenciaTema.DARK)
            }
        )
        DropdownMenuItemWithCheck(
            text = stringResource(R.string.tema_padrao_sistema),
            isSelected = preferenciaTemaAtual == PreferenciaTema.SYSTEM,
            onClick = {
                onTemaChange(PreferenciaTema.SYSTEM)
            }
        )

        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

        // SEÇÃO DE TAMANHO DA FONTE - COM MELHORIAS
        Text(
            text = stringResource(R.string.tamanho_da_fonte_conteudo),
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 4.dp)
        )
        Column(
            modifier = Modifier
                .padding(horizontal = 12.dp, vertical = 8.dp), // Padding geral para esta seção
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "${(currentFontSizeMultiplier * 100).toInt()}%", // Mostrar como porcentagem
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp) // Um pouco mais de espaço abaixo da porcentagem
            )

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.medium, // Bordas arredondadas para o Card
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(1.dp) // Uma cor de fundo sutil para o Card
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.5.dp) // Uma elevação muito sutil, quase nula
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    // Usar Arrangement.SpaceBetween ou .spacedBy() se quiser mais controle fino
                    horizontalArrangement = Arrangement.Center, // Mantido Center, mas pode ajustar
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 4.dp, vertical = 4.dp) // Padding interno do Card
                ) {
                    FilledTonalIconButton(
                        onClick = {
                            val newValue = (currentFontSizeMultiplier - fontSizeStep).coerceIn(sliderValueRange)
                            onFontSizeMultiplierChange(newValue)
                            haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove) // feedback tátil
                        },
                        enabled = currentFontSizeMultiplier > sliderValueRange.start,
                        modifier = Modifier.size(40.dp),
                        colors = IconButtonDefaults.filledTonalIconButtonColors(
                            containerColor = MaterialTheme.colorScheme.secondaryContainer,
                            contentColor = MaterialTheme.colorScheme.onSecondaryContainer)
                        ) {
                        Icon(
                            Icones.Menos, // Seu ícone customizado
                            contentDescription = stringResource(R.string.diminuir_fonte),
                            modifier = Modifier.size(20.dp) // Ajuste o tamanho do ícone interno
                        )
                    }

                    Slider(
                        value = currentFontSizeMultiplier,
                        onValueChange = { newValue ->
                            val roundedValue = (newValue / fontSizeStep).toInt() * fontSizeStep
                            onFontSizeMultiplierChange(roundedValue.coerceIn(sliderValueRange))
                            },
                        onValueChangeFinished = {
                            // Feedback tátil quando o usuário solta o slider
                            haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                        },
                        valueRange = sliderValueRange,
                        steps = ((sliderValueRange.endInclusive - sliderValueRange.start) / fontSizeStep).toInt() - 1,
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 8.dp), // Espaçamento entre botões e slider
                        colors = SliderDefaults.colors(
                            thumbColor = MaterialTheme.colorScheme.primary,
                            activeTrackColor = MaterialTheme.colorScheme.primary,
                            inactiveTrackColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
                            activeTickColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.5f),
                            inactiveTickColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)
                        )
                    )

                    FilledTonalIconButton(
                        onClick = {
                            val newValue = (currentFontSizeMultiplier + fontSizeStep).coerceIn(sliderValueRange)
                            onFontSizeMultiplierChange(newValue)
                            haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove) // feedback tátil
                        },
                        enabled = currentFontSizeMultiplier < sliderValueRange.endInclusive,
                        modifier = Modifier.size(40.dp) // Ajuste o tamanho
                    ) {
                        Icon(
                            Icones.Mais, // Seu ícone customizado
                            contentDescription = stringResource(R.string.aumentar_fonte),
                            modifier = Modifier.size(20.dp) // Ajuste o tamanho do ícone
                        )
                    }
                }
            }
        }

        HorizontalDivider(
            modifier = Modifier.padding(vertical = 8.dp),
            thickness = DividerDefaults.Thickness, // Use o valor padrão do tema
            color = DividerDefaults.color // Use a cor padrão do tema
        )

        // SEÇÃO DE OUTROS ITENS (SOBRE, ETC.)
        DropdownMenuItem(
            text = { Text(stringResource(id = R.string.titulo_sobre)) }, // Verifique se R.string.titulo_sobre existe
            onClick = {
                onNavigateToSobre()
                onDismissRequest() // Fechar o menu ao navegar para "Sobre"
            },
            leadingIcon = {
                Icon(
                    imageVector = Icones.Sobre, // Verifique se Icones.Sobre existe
                    contentDescription = null // O texto do item já descreve a ação
                )
            }
        )
    }
}
