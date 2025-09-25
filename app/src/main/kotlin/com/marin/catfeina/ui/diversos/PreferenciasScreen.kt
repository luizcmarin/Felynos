/*
 * Arquivo: com.marin.catfeina.ui.diversos.PreferenciasScreen.kt
 * @project Catfeina
 * @description
 * Composable que representa a tela de configurações de preferências do aplicativo,
 * permitindo ao usuário alterar o tema base (ex: Primavera, Verão) e
 * o modo claro/escuro.
 */
package com.marin.catfeina.ui.diversos

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton // Para BaseTheme
import androidx.compose.material3.Switch // MUDANÇA: Usaremos Switch para Claro/Escuro
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.marin.catfeina.ui.theme.BaseTheme
// import com.marin.catfeina.ui.theme.ThemeState // REMOVIDO: Não usamos mais o enum ThemeState
import com.marin.catfeina.ui.theme.ThemeViewModel // MUDANÇA: Usar ThemeViewModel
import java.util.Locale

/**
 * Tela de preferências onde o usuário pode configurar opções do aplicativo,
 * como a seleção do tema visual (base) e o modo claro/escuro.
 *
 * @param viewModel Instância de [ThemeViewModel] injetada pelo Hilt,
 *                  usada para obter e atualizar as preferências de tema.
 */
@Composable
fun PreferenciasScreen(
    viewModel: ThemeViewModel = hiltViewModel() // MUDANÇA: Injeta ThemeViewModel
) {
    // Coleta os estados atuais do ThemeViewModel
    val currentBaseTheme by viewModel.currentBaseTheme.collectAsState()
    val isDarkMode by viewModel.isDarkMode.collectAsState() // MUDANÇA: Coleta isDarkMode

    // REMOVIDO: val currentThemeState by viewModel.currentThemeState.collectAsState()

    Column(
        modifier = Modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        // Seção para Tema Base (Primavera, Verão, etc.)
        Text(
            text = "Escolha o Tema Principal",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = "Selecione a paleta de cores base para o aplicativo.",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Column(Modifier.selectableGroup()) {
            BaseTheme.entries.forEach { theme ->
                Row(
                    Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .selectable(
                            selected = (theme == currentBaseTheme),
                            onClick = { viewModel.setBaseTheme(theme) }, // MUDANÇA: usa setBaseTheme
                            role = Role.RadioButton
                        )
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = (theme == currentBaseTheme),
                        onClick = null // O clique é tratado no Row
                    )
                    Text(
                        text = theme.name.lowercase(Locale.getDefault())
                            .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() },
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
        HorizontalDivider()
        Spacer(modifier = Modifier.height(24.dp))

        // Seção para Modo Claro/Escuro
        Text(
            text = "Escolha a Aparência",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = "Selecione entre o modo claro ou escuro.", // MUDANÇA: Texto simplificado
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Row( // MUDANÇA: Usar um Switch para alternar claro/escuro
            Modifier
                .fillMaxWidth()
                .height(56.dp)
                .selectable(
                    selected = isDarkMode, // O estado "selecionado" do grupo é o próprio isDarkMode
                    onClick = { viewModel.toggleDarkMode() }, // MUDANÇA: Chama toggleDarkMode
                    role = Role.Switch
                )
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = if (isDarkMode) "Modo Escuro" else "Modo Claro",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.weight(1f) // Ocupa o espaço disponível
            )
            Switch(
                checked = isDarkMode,
                onCheckedChange = { viewModel.toggleDarkMode() } // Pode ser null se o clique do Row já faz
            )
        }
        // REMOVIDO: A seção antiga com RadioButtons para ThemeState.CLARO, ThemeState.ESCURO, ThemeState.AUTO
    }
}


