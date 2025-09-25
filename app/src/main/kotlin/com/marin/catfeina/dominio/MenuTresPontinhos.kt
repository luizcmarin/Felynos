/*
 * Arquivo: com.marin.catfeina.dominio.MenuTresPontinhos.kt
 * @project Catfeina
 * @description Define o Composable para o menu de opções (três pontinhos)
 *              utilizado em telas como InformativoScreen. Permite ações como
 *              copiar, compartilhar, e alterar o estado do tema (claro/escuro).
 */
package com.marin.catfeina.dominio

// import androidx.compose.material.icons.filled.BrightnessAuto // REMOVIDO - Não é mais usado
// import com.marin.catfeina.ui.theme.ThemeState // REMOVIDO - Não é mais usado aqui
import android.content.Context
import android.widget.Toast
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.marin.catfeina.ui.theme.ThemeViewModel

@Composable
fun MenuTresPontinhos(
    expanded: MutableState<Boolean>,
    onDismissRequest: () -> Unit,
    themeViewModel: ThemeViewModel,
    informativoTitulo: String?,
    informativoConteudo: String,
    onNavigateToPreferences: (() -> Unit)? = null // Opcional: callback para navegar para Preferências
) {
    val context = LocalContext.current
    val fontSizeState = remember { mutableFloatStateOf(16f) }

    // Observa o estado claro/escuro do ViewModel
    val isCurrentlyDark by themeViewModel.isDarkMode.collectAsState() // MUDANÇA: usa isDarkMode

    // REMOVIDO: Observa o estado do tema (Claro, Escuro, Auto) do ViewModel
    // val currentThemeState by themeViewModel.currentThemeState.collectAsState()
    // REMOVIDO: Observa o tema base (Primavera, Verão)
    // val currentBaseTheme by themeViewModel.currentBaseTheme.collectAsState()

    DropdownMenu(
        expanded = expanded.value,
        onDismissRequest = onDismissRequest
    ) {
        DropdownMenuItem(
            text = { Text("Copiar Texto") },
            onClick = {
                onDismissRequest()
                val fullText = "${informativoTitulo ?: ""}\n\n$informativoConteudo".trim()
                if (fullText.isNotEmpty()) {
                    copyTextToClipboard(context, fullText)
                    Toast.makeText(context, "Texto copiado!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Nada para copiar.", Toast.LENGTH_SHORT).show()
                }
            }
        )
        DropdownMenuItem(
            text = { Text("Compartilhar") },
            onClick = {
                onDismissRequest()
                val shareText =
                    "${informativoTitulo ?: "Informativo Catfeina"}\n\n$informativoConteudo".trim()
                if (shareText.isNotEmpty()) {
                    shareText(context, shareText)
                } else {
                    Toast.makeText(context, "Nada para compartilhar.", Toast.LENGTH_SHORT).show()
                }
            }
        )
        DropdownMenuItem(
            text = { Text("Ouvir (TTS)") },
            onClick = {
                onDismissRequest()
                Toast.makeText(
                    context,
                    "TTS: $informativoConteudo (placeholder)",
                    Toast.LENGTH_LONG
                ).show()
            }
        )
        DropdownMenuItem(
            text = { Text("Ajustar Fonte") },
            onClick = {
                onDismissRequest()
                fontSizeState.floatValue = if (fontSizeState.floatValue == 16f) 20f else 16f
                Toast.makeText(
                    context,
                    "Ajustar Fonte (placeholder): ${fontSizeState.floatValue}sp",
                    Toast.LENGTH_SHORT
                ).show()
            }
        )
        DropdownMenuItem(
            text = {
                // MUDANÇA: Texto baseado no estado booleano isCurrentlyDark
                Text(if (isCurrentlyDark) "Mudar para Tema Claro" else "Mudar para Tema Escuro")
            },
            onClick = {
                onDismissRequest()
                themeViewModel.toggleDarkMode() // MUDANÇA: Chama toggleDarkMode()
                Toast.makeText(
                    context,
                    "Tema alterado.",
                    Toast.LENGTH_SHORT
                ).show()
            },
            leadingIcon = {
                Icon(
                    // MUDANÇA: Ícone baseado no estado booleano isCurrentlyDark
                    imageVector = if (isCurrentlyDark) Icons.Filled.LightMode else Icons.Filled.DarkMode,
                    contentDescription = "Alterar Tema Claro/Escuro"
                )
            }
        )
        // Adiciona o item para "Preferências" se o callback for fornecido
        if (onNavigateToPreferences != null) {
            DropdownMenuItem(
                text = { Text("Preferências de Tema") },
                leadingIcon = { Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = "Preferências")}, // Exemplo de ícone
                onClick = {
                    onDismissRequest()
                    onNavigateToPreferences()
                }
            )
        }
    }
}

private fun copyTextToClipboard(context: Context, text: String) {
    // ... (função permanece a mesma) ...
}

private fun shareText(context: Context, text: String) {
    // ... (função permanece a mesma) ...
}


